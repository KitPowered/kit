package com.kitpowered.core.coroutines

import com.kitpowered.core.util.FoliaUtils
import kotlinx.coroutines.MainCoroutineDispatcher
import kotlinx.coroutines.Runnable
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import kotlin.coroutines.CoroutineContext

class MainBukkitCoroutineDispatcher : MainCoroutineDispatcher() {

    override val immediate: MainCoroutineDispatcher
        get() = throw UnsupportedOperationException()

    private val delegate = MainBukkitCoroutineDispatcherDelegate()

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        delegate.dispatch(context, block)
    }

    private class MainBukkitCoroutineDispatcherDelegate : BukkitCoroutineDispatcher() {

        override fun dispatch(plugin: Plugin, block: Runnable) {
            if (FoliaUtils.isUsingFolia()) {
                Bukkit.getGlobalRegionScheduler().execute(plugin, block)
            } else {
                Bukkit.getScheduler().runTask(plugin, block)
            }
        }
    }
}