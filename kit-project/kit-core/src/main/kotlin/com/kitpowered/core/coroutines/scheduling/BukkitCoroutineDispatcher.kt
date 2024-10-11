package com.kitpowered.core.coroutines.scheduling

import com.kitpowered.core.context.KitPluginApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable
import org.bukkit.plugin.IllegalPluginAccessException
import org.bukkit.plugin.Plugin
import kotlin.coroutines.CoroutineContext

internal abstract class BukkitCoroutineDispatcher : CoroutineDispatcher() {

    abstract fun dispatch(plugin: Plugin, block: Runnable)

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        try {
            dispatch(getPlugin(context), block)
        } catch (_: IllegalPluginAccessException) {
        }
    }

    private fun getPlugin(coroutineContext: CoroutineContext): Plugin {
        return coroutineContext[KitPluginApplicationContext]?.plugin
            ?: error("CoroutineContext does not contain ${KitPluginApplicationContext::class.simpleName}")
    }

}