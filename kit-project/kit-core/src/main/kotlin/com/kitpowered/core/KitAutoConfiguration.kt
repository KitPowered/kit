package com.kitpowered.core

import com.kitpowered.core.util.stdlib.single
import org.bukkit.Bukkit
import org.bukkit.Server
import org.bukkit.plugin.PluginManager
import org.bukkit.plugin.ServicesManager
import org.bukkit.plugin.java.PluginClassLoader
import org.springframework.aop.framework.AopProxyUtils
import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Role

@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@AutoConfiguration
class KitAutoConfiguration {

    @Bean(destroyMethod = "")
    fun server(): Server {
        return Bukkit.getServer()
    }

    @Bean
    fun pluginManager(server: Server): PluginManager {
        return server.pluginManager
    }

    @Bean
    fun servicesManager(server: Server): ServicesManager {
        return server.servicesManager
    }

    @Suppress("UnstableApiUsage")
    @Bean
    fun kitPlugin(applicationContext: ApplicationContext): KitPlugin<*> {
        val applicationInstance = applicationContext
            .getBeansWithAnnotation(SpringBootApplication::class.java)
            .single()
            .second
        val classLoader = AopProxyUtils.ultimateTargetClass(applicationInstance).classLoader
        if (classLoader !is PluginClassLoader) {
            throw IllegalStateException("SpringBootApplication class loader is not a PluginClassLoader")
        }
        val plugin = classLoader.plugin ?: throw IllegalStateException("PluginClassLoader does not have plugin")
        if (plugin !is KitPlugin<*>) {
            throw IllegalStateException("Plugin is not KitPlugin")
        }
        return plugin
    }
}