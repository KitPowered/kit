package com.kitpowered.core.service

import com.kitpowered.core.util.stdlib.uncheckedCast
import org.bukkit.plugin.ServicesManager
import org.springframework.beans.factory.support.AbstractBeanDefinition
import org.springframework.beans.factory.support.BeanDefinitionBuilder
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.annotation.AnnotationBeanNameGenerator
import org.springframework.context.support.GenericApplicationContext

class BukkitServicesManagerContextInitializer(
    private val servicesManager: ServicesManager,
    private val annotationBeanNameGenerator: AnnotationBeanNameGenerator
) : ApplicationContextInitializer<GenericApplicationContext> {

    override fun initialize(applicationContext: GenericApplicationContext) {
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
                    setAttribute(AbstractBeanDefinition.ORDER_ATTRIBUTE, priority)
                }
            val beanName = annotationBeanNameGenerator.generateBeanName(definition, applicationContext)
            applicationContext.registerBeanDefinition(beanName, definition)
        }
    }
}