package com.kitpowered.core.coroutines

import com.kitpowered.core.util.FoliaUtils
import kotlinx.coroutines.Runnable
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin

internal class AsyncBukkitCoroutineDispatcher : BukkitCoroutineDispatcher() {
    override fun dispatch(plugin: Plugin, block: Runnable) {
        if (FoliaUtils.isUsingFolia()) {
            Bukkit.getAsyncScheduler().runNow(plugin) {
                block.run()
            }
        } else {
            Bukkit.getScheduler().runTaskAsynchronously(plugin, block)
        }
    }
}