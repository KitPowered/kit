package com.kitpowered.core

import com.kitpowered.core.context.KitPluginApplicationContextFactory
import com.kitpowered.core.context.KitPluginClassLoader
import com.kitpowered.core.context.KitPluginConfigurer
import com.kitpowered.core.context.KitPluginResourceLoader
import com.kitpowered.core.util.stdlib.ClassLoaderUtils
import org.bukkit.plugin.Plugin
import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.support.GenericApplicationContext
import org.springframework.core.annotation.AnnotationUtils

class KitPluginApplication(
    private val plugin: Plugin,
    private val applicationClass: Class<*>
) : KitPluginConfigurer {

    private var banner: Banner? = null

    private var logStartupInfo: Boolean = false

    private var applicationContext: GenericApplicationContext? = null

    init {
        if (AnnotationUtils.findAnnotation(applicationClass, SpringBootApplication::class.java) == null) {
            throw IllegalStateException("Application class is not annotated with @SpringBootApplication")
        }
    }

    fun run() {
        System.setProperty(
            "org.springframework.boot.logging.LoggingSystem",
            "org.springframework.boot.logging.log4j2.Log4J2LoggingSystem"
        )
        val classLoaders = listOf(
            plugin::class.java.classLoader,
            AutoConfiguration::class.java.classLoader, // Library class loader
            KitPlugin::class.java.classLoader // Kit master plugin class loader
        )
        val classLoader = KitPluginClassLoader(classLoaders)
        return ClassLoaderUtils.withClassLoader(classLoader) {
            SpringApplicationBuilder()
                .resourceLoader(KitPluginResourceLoader(classLoader, plugin))
                .sources(applicationClass)
                .contextFactory(KitPluginApplicationContextFactory(plugin))
                .properties("spring.config.import=optional:${plugin.dataFolder.absolutePath}/")
                .let { builder ->
                    if (banner != null) {
                        builder
                            .bannerMode(Banner.Mode.CONSOLE)
                            .banner(banner)
                    } else {
                        builder
                            .bannerMode(Banner.Mode.OFF)
                    }
                }
                .logStartupInfo(this.logStartupInfo)
                .registerShutdownHook(false)
                .run()
        }
    }

    fun close() {
        applicationContext?.close()
    }

    override fun setBanner(banner: Banner): KitPluginConfigurer {
        this.banner = banner
        return this
    }

    override fun setLogStartupInfo(logStartupInfo: Boolean): KitPluginConfigurer {
        this.logStartupInfo = logStartupInfo
        return this
    }

}