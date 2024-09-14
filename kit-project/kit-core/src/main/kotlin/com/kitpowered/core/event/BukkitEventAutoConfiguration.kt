package com.kitpowered.core.event

import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Role

@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@AutoConfiguration
class BukkitEventAutoConfiguration {

    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Bean
    fun eventHandlerAnnotationProcessor(applicationContext: ApplicationContext): BukkitEventHandlerAnnotationProcessor {
        return BukkitEventHandlerAnnotationProcessor(applicationContext)
    }
}