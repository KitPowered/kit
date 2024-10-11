package com.kitpowered.core

import org.bukkit.plugin.Plugin
import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Role

@Role(BeanDefinition.ROLE_SUPPORT)
@AutoConfiguration
class KitPluginAutoConfiguration {

    @ConditionalOnMissingBean
    @Role(BeanDefinition.ROLE_SUPPORT)
    @Bean
    fun plugin(applicationContext: ApplicationContext): Plugin {
        error("No plugin bean found in the application context")
    }

}