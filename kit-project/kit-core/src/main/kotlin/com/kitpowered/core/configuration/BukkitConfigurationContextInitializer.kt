package com.kitpowered.core.configuration

import org.bukkit.plugin.Plugin
import org.springframework.boot.env.YamlPropertySourceLoader
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.support.GenericApplicationContext
import org.springframework.core.env.MutablePropertySources
import org.springframework.core.io.InputStreamResource
import java.io.InputStream

class BukkitConfigurationContextInitializer(
    private val plugin: Plugin
) : ApplicationContextInitializer<GenericApplicationContext> {

    private companion object {
        const val APPLICATION_YML = "application.yml"
    }

    override fun initialize(applicationContext: GenericApplicationContext) {
        val propertySources = applicationContext.environment.propertySources
        registerDataFolderPropertySource(propertySources)
        registerJarPropertySource(propertySources)
    }

    private fun registerDataFolderPropertySource(mutablePropertySources: MutablePropertySources) {
        val applicationFile = plugin.dataFolder.resolve(APPLICATION_YML)
        if (!applicationFile.exists()) {
            val resource = plugin.getResource(APPLICATION_YML) ?: return
            applicationFile.createNewFile()
            applicationFile.writeBytes(resource.readBytes())
        }
        registerInputStreamResource(mutablePropertySources, applicationFile.inputStream(), "pluginDataFolder")
    }

    private fun registerJarPropertySource(mutablePropertySources: MutablePropertySources) {
        val resource = plugin.getResource(APPLICATION_YML) ?: return
        registerInputStreamResource(mutablePropertySources, resource, "pluginJar")
    }

    private fun registerInputStreamResource(
        mutablePropertySources: MutablePropertySources,
        inputStream: InputStream,
        name: String
    ) {
        val inputStreamResource = InputStreamResource(inputStream)
        val propertySources = YamlPropertySourceLoader().load(name, inputStreamResource)
        propertySources.forEach { propertySource ->
            mutablePropertySources.addLast(propertySource)
        }
    }
}