package com.kitpowered.core.service

import com.kitpowered.core.util.stdlib.uncheckedCast
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.ServicePriority
import org.bukkit.plugin.ServicesManager
import org.springframework.aop.framework.AopInfrastructureBean
import org.springframework.aop.framework.AopProxyUtils
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.context.ApplicationContext
import org.springframework.core.annotation.AnnotationUtils
import org.springframework.stereotype.Service
import org.springframework.util.ClassUtils

class BukkitServiceAnnotationProcessor(
    private val applicationContext: ApplicationContext
) : BeanPostProcessor {

    private val plugin: Plugin by lazy { applicationContext.getBean(Plugin::class.java) }
    private val servicesManager: ServicesManager by lazy { applicationContext.getBean(ServicesManager::class.java) }

    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any? {
        if (bean is AopInfrastructureBean) {
            return bean
        }
        val targetClass = AopProxyUtils.ultimateTargetClass(bean)
        if (!AnnotationUtils.isCandidateClass(targetClass, Service::class.java)) {
            return bean
        }
        if (AnnotationUtils.getAnnotation(targetClass, Service::class.java) == null) {
            return bean
        }
        ClassUtils.getAllInterfacesAsSet(bean).forEach { clazz ->
            servicesManager.register(clazz.uncheckedCast(), bean, plugin, ServicePriority.Normal)
        }
        return bean
    }
}