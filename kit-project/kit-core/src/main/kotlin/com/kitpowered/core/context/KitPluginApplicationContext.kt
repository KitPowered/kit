package com.kitpowered.core.context

import com.kitpowered.core.command.CommandAnnotationProcessor
import com.kitpowered.core.coroutines.BukkitDispatchers
import com.kitpowered.core.coroutines.command.CoroutinesCommandExecutorFactory
import com.kitpowered.core.coroutines.event.CoroutinesBukkitEventExecutorFactory
import com.kitpowered.core.event.BukkitEventHandlerAnnotationProcessor
import com.kitpowered.core.util.stdlib.uncheckedCast
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.ServicePriority
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.beans.factory.support.AbstractBeanDefinition
import org.springframework.beans.factory.support.BeanDefinitionBuilder
import org.springframework.context.annotation.FullyQualifiedAnnotationBeanNameGenerator
import org.springframework.context.support.GenericApplicationContext
import org.springframework.stereotype.Service
import org.springframework.util.ClassUtils
import kotlin.coroutines.CoroutineContext

class KitPluginApplicationContext(val plugin: Plugin) : GenericApplicationContext(), CoroutineScope,
    CoroutineContext.Element {

    companion object : CoroutineContext.Key<KitPluginApplicationContext>

    override val coroutineContext by lazy {
        CoroutineName(plugin.name) + SupervisorJob() + BukkitDispatchers.Main + this
    }

    override val key: CoroutineContext.Key<*>
        get() = Companion

    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
        beanFactory.registerSingleton(plugin.name, plugin)
        setupEventHandlerProcessor(beanFactory)
        setupCommandProcessor(beanFactory)
        registerBukkitServicesToBeanFactory()
    }

    override fun onRefresh() {
        registerServicesToBukkitServicesManager()
    }

    override fun refresh() {
        try {
            super.refresh()
        } catch (throwable: Throwable) {
            if (plugin.isEnabled) {
                Bukkit.getPluginManager().disablePlugin(plugin)
            }
            throw throwable
        }
    }

    private fun registerBukkitServicesToBeanFactory() {
        val servicesManager = Bukkit.getServicesManager()
        val registrations = servicesManager.knownServices.flatMap { type ->
            servicesManager.getRegistrations(type)
        }
        registrations.forEach { provider ->
            val type = provider.service
            val instance = provider.provider
            val priority = provider.priority.ordinal
            val definition = BeanDefinitionBuilder
                .rootBeanDefinition(type.uncheckedCast<Class<Any>>()) { instance }
                .beanDefinition
                .apply {
                    beanClassName = instance.javaClass.name
                    setAttribute(AbstractBeanDefinition.ORDER_ATTRIBUTE, priority)
                }
            val beanName = FullyQualifiedAnnotationBeanNameGenerator.INSTANCE.generateBeanName(definition, this)
            registerBeanDefinition(beanName, definition)
        }
    }

    /**
     * Register services to Bukkit services manager if the bean is annotated with [Service] and implements an interface.
     */
    private fun registerServicesToBukkitServicesManager() {
        getBeansWithAnnotation(Service::class.java).forEach { (_, bean) ->
            ClassUtils.getAllInterfacesAsSet(bean).forEach { clazz ->
                Bukkit.getServicesManager().register(clazz.uncheckedCast(), bean, plugin, ServicePriority.Normal)
            }
        }
    }

    private fun setupEventHandlerProcessor(beanFactory: ConfigurableListableBeanFactory) {
        val eventExecutorFactory = CoroutinesBukkitEventExecutorFactory(this)
        val eventHandlerAnnotationProcessor = BukkitEventHandlerAnnotationProcessor(plugin, eventExecutorFactory)
        beanFactory.addBeanPostProcessor(eventHandlerAnnotationProcessor)
    }

    private fun setupCommandProcessor(beanFactory: ConfigurableListableBeanFactory) {
        val commandExecutorFactory = CoroutinesCommandExecutorFactory(this)
        val commandAnnotationProcessor = CommandAnnotationProcessor(plugin, commandExecutorFactory)
        beanFactory.addBeanPostProcessor(commandAnnotationProcessor)
    }

}