package com.kitpowered.core.event

import com.kitpowered.core.util.stdlib.uncheckedCast
import org.bukkit.Bukkit
import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.plugin.EventExecutor
import org.bukkit.plugin.Plugin
import org.springframework.aop.framework.AopInfrastructureBean
import org.springframework.aop.framework.AopProxyUtils
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.core.MethodIntrospector
import org.springframework.core.MethodParameter
import org.springframework.core.annotation.AnnotatedElementUtils
import org.springframework.core.annotation.AnnotationUtils
import java.lang.reflect.Method

class BukkitEventHandlerAnnotationProcessor(
    private val plugin: Plugin,
    private val eventExecutorFactory: BukkitEventExecutorFactory
) : BeanPostProcessor {

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
            val methodParameter = MethodParameter(method, 0)
                .withContainingClass(bean::class.java)
                .parameterType
            check(Event::class.java.isAssignableFrom(methodParameter)) {
                "first parameter of event handler must be a subclass of Event."
            }
            val eventHandler = AnnotationUtils.getAnnotation(method, EventHandler::class.java)!!
            val eventExecutor = eventExecutorFactory.create(bean, method, methodParameter)
            registerEvent(eventExecutor, methodParameter, eventHandler)
        }
        return bean
    }

    private fun registerEvent(
        eventExecutor: EventExecutor,
        eventType: Class<*>,
        eventHandler: EventHandler
    ) {
        Bukkit.getPluginManager().registerEvent(
            eventType.uncheckedCast(),
            object : Listener {},
            eventHandler.priority,
            eventExecutor,
            plugin
        )
    }

}