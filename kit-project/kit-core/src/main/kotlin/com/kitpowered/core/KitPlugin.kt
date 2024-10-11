package com.kitpowered.core

import com.kitpowered.core.context.KitPluginConfigurer
import org.bukkit.plugin.java.JavaPlugin
import kotlin.reflect.KClass

abstract class KitPlugin(
    private val applicationClass: Class<*>
) : JavaPlugin() {

    private val kitApplication by lazy {
        KitPluginApplication(this, applicationClass)
    }

    constructor(applicationClass: KClass<*>) : this(applicationClass.java)

    protected open fun configure(configurer: KitPluginConfigurer) {}

    protected open fun onPluginReady() {}

    final override fun onEnable() {
        if (getResource("config.yml") != null) {
            saveDefaultConfig()
        }

        kitApplication.run()
        onPluginReady()
    }

    final override fun onDisable() {
        kitApplication.close()
    }

}