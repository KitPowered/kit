package com.kitpowered.core.coroutines.scheduling

import com.kitpowered.core.util.FoliaUtils
import kotlinx.coroutines.Runnable
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.plugin.Plugin

internal class CoroutinesBukkitRegionScheduler(
    private val world: World,
    private val chunkX: Int,
    private val chunkZ: Int
) : BukkitCoroutineDispatcher() {

    override fun dispatch(plugin: Plugin, block: Runnable) {
        if (FoliaUtils.isUsingFolia()) {
            Bukkit.getRegionScheduler().execute(plugin, world, chunkX, chunkZ, block)
        } else {
            Bukkit.getScheduler().runTask(plugin, block)
        }
    }

}