package com.kitpowered.plugin.lifecycle

import com.kitpowered.core.KitPlugin
import com.kitpowered.plugin.KitMasterApplication
import jakarta.annotation.PostConstruct
import org.bukkit.plugin.PluginManager
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class LifecycleManager(
    private val pluginManager: PluginManager
) {
    @Value("\${kit.testValue}")
    private var test: String = ""

    @PostConstruct
    fun a() {
        println(test)
    }

    fun onEnable(kitPlugin: KitPlugin<KitMasterApplication>) {
//        pluginManager.loadPlugin()
    }
}