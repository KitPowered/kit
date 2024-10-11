package com.kitpowered.core.coroutines.scheduling

import com.kitpowered.core.util.FoliaUtils
import kotlinx.coroutines.Runnable
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin

internal class CoroutinesBukkitMainScheduler : BukkitCoroutineDispatcher() {

    override fun dispatch(plugin: Plugin, block: Runnable) {
        if (FoliaUtils.isUsingFolia()) {
            Bukkit.getGlobalRegionScheduler().execute(plugin, block)
        } else {
            Bukkit.getScheduler().runTask(plugin, block)
        }
    }

}