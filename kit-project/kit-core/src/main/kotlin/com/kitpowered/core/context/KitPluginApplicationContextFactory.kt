package com.kitpowered.core.context

import org.bukkit.plugin.Plugin
import org.springframework.boot.ApplicationContextFactory
import org.springframework.boot.WebApplicationType
import org.springframework.context.ConfigurableApplicationContext

class KitPluginApplicationContextFactory(private val plugin: Plugin) : ApplicationContextFactory {

    override fun create(webApplicationType: WebApplicationType): ConfigurableApplicationContext {
        return KitPluginApplicationContext(plugin)
    }

}