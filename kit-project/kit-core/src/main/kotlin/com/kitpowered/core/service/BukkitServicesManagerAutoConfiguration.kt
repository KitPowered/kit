package com.kitpowered.core.service

import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Role

@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@AutoConfiguration
class BukkitServicesManagerAutoConfiguration {
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Bean
    fun bukkitServicesManagerProcessor(applicationContext: ApplicationContext): BukkitServiceAnnotationProcessor {
        return BukkitServiceAnnotationProcessor(applicationContext)
    }
}