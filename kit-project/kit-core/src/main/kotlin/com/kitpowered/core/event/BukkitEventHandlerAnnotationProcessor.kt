package com.kitpowered.core.event

import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.plugin.EventExecutor
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.PluginManager
import org.springframework.aop.framework.AopInfrastructureBean
import org.springframework.aop.framework.AopProxyUtils
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.context.ApplicationContext
import org.springframework.core.MethodIntrospector
import org.springframework.core.annotation.AnnotatedElementUtils
import org.springframework.core.annotation.AnnotationUtils
import java.lang.reflect.Method

class BukkitEventHandlerAnnotationProcessor(
    private val applicationContext: ApplicationContext
) : BeanPostProcessor {

    private val plugin: Plugin by lazy { applicationContext.getBean(Plugin::class.java) }
    private val pluginManager: PluginManager by lazy { applicationContext.getBean(PluginManager::class.java) }

    override fun postProcessBeforeInitialization(
        bean: Any,
        beanName: String,
    ): Any {
        if (bean is AopInfrastructureBean) {
            return bean
        }
        val targetClass = AopProxyUtils.ultimateTargetClass(bean)
        if (!AnnotationUtils.isCandidateClass(targetClass, EventHandler::class.java)) {
            return bean
        }

        val annotatedMethods: Set<Method> =
            MethodIntrospector.selectMethods(targetClass) { method ->
                AnnotatedElementUtils.isAnnotated(method, EventHandler::class.java)
            }
        if (annotatedMethods.isEmpty()) {
            return bean
        }

        for (method in annotatedMethods) {
            val methodParameter = method.parameters.firstOrNull()
            check(methodParameter != null) {
                "event handler must have at least one parameter."
            }
            check(Event::class.java.isAssignableFrom(methodParameter.type)) {
                "first parameter of event handler must be a subclass of Event."
            }
            @Suppress("UNCHECKED_CAST")
            val eventType = methodParameter.type as Class<out Event>
            val eventHandler = AnnotationUtils.getAnnotation(method, EventHandler::class.java)!!
            registerEvent(eventType, eventHandler, method, bean)
        }
        return bean
    }

    private fun registerEvent(
        eventType: Class<out Event>,
        eventHandler: EventHandler,
        method: Method,
        instance: Any,
    ) {
        val eventExecutor =
            EventExecutor { _, event ->
                method.invoke(instance, event)
            }
        pluginManager.registerEvent(eventType, object : Listener {}, eventHandler.priority, eventExecutor, plugin)
    }
}
