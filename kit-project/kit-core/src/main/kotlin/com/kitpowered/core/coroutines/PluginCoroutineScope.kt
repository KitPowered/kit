package com.kitpowered.core.coroutines

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.bukkit.plugin.Plugin

interface PluginCoroutineScope : Plugin, CoroutineScope {

    override val coroutineContext
        get() = CoroutineName(name) + SupervisorJob() + Dispatchers.Main + PluginElement(this)
}