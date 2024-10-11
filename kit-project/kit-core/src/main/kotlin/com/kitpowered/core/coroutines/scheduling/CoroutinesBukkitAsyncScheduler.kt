package com.kitpowered.core.coroutines.scheduling

import com.kitpowered.core.util.FoliaUtils
import kotlinx.coroutines.Runnable
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin

internal class CoroutinesBukkitAsyncScheduler : BukkitCoroutineDispatcher() {

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