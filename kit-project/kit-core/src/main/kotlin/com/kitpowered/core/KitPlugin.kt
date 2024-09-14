package com.kitpowered.core

import com.kitpowered.core.configuration.BukkitConfigurationContextInitializer
import com.kitpowered.core.configuration.BukkitConfigurationHolder
import com.kitpowered.core.coroutines.PluginCoroutineScope
import com.kitpowered.core.coroutines.configuration.CoroutinesFileConfiguration
import com.kitpowered.core.io.CascadingResourceLoader
import com.kitpowered.core.service.BukkitServicesManagerContextInitializer
import org.bukkit.plugin.java.JavaPlugin
import org.springframework.boot.Banner
import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.logging.LoggingSystem
import org.springframework.boot.logging.java.JavaLoggingSystem
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.FullyQualifiedAnnotationBeanNameGenerator
import org.springframework.core.annotation.AnnotationUtils
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.KClass

abstract class KitPlugin(
    private val applicationClass: Class<*>
) : JavaPlugin(), PluginCoroutineScope, BukkitConfigurationHolder<CoroutinesFileConfiguration> {
    private var applicationContext: ConfigurableApplicationContext? = null

    constructor(applicationClass: KClass<*>) : this(applicationClass.java)

    init {
        if (AnnotationUtils.findAnnotation(applicationClass, SpringBootApplication::class.java) == null) {
            throw IllegalStateException("Application class is not annotated with @SpringBootApplication")
        }
    }

    protected open fun configureApplication(applicationBuilder: SpringApplicationBuilder): SpringApplicationBuilder {
        return applicationBuilder
    }

    protected open fun onApplicationReady(applicationContext: ConfigurableApplicationContext) {}

    final override fun onEnable() {
        System.setProperty(LoggingSystem.SYSTEM_PROPERTY, JavaLoggingSystem.SYSTEM_PROPERTY)
        val applicationContext = buildApplicationContext()
        this.applicationContext = applicationContext
        config.load(dataFolder.resolve("config.yml"))
        onApplicationReady(applicationContext)
    }

    private fun buildApplicationContext(): ConfigurableApplicationContext {
        return SpringApplicationBuilder()
            .resourceLoader(CascadingResourceLoader(this, server.pluginManager))
            .sources(applicationClass)
            .web(WebApplicationType.NONE)
            .bannerMode(Banner.Mode.OFF)
            .logStartupInfo(false)
            .registerShutdownHook(false)
            .initializers(
                BukkitServicesManagerContextInitializer(
                    server.servicesManager,
                    FullyQualifiedAnnotationBeanNameGenerator.INSTANCE
                ),
                BukkitConfigurationContextInitializer(this)
            )
            .let { builder -> configureApplication(builder) }
            .run()
    }

    final override fun getConfig(): CoroutinesFileConfiguration {
        return CoroutinesFileConfiguration(super.getConfig(), dataFolder.resolve("config.yml"))
    }

    final override val coroutineContext: CoroutineContext
        get() = super.coroutineContext
}