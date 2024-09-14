package com.kitpowered.core.coroutines

import org.bukkit.plugin.Plugin
import kotlin.coroutines.CoroutineContext

class PluginElement(val plugin: Plugin) : CoroutineContext.Element {
    override val key: CoroutineContext.Key<*>
        get() = Key

    companion object Key : CoroutineContext.Key<PluginElement>
}