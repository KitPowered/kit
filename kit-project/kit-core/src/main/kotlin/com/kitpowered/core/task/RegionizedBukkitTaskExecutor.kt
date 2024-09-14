package com.kitpowered.core.task

import io.papermc.paper.threadedregions.scheduler.AsyncScheduler
import io.papermc.paper.threadedregions.scheduler.GlobalRegionScheduler
import io.papermc.paper.threadedregions.scheduler.RegionScheduler
import org.bukkit.World
import org.bukkit.plugin.Plugin

class RegionizedBukkitTaskExecutor(
    private val globalRegionScheduler: GlobalRegionScheduler,
    private val regionScheduler: RegionScheduler,
    private val asyncScheduler: AsyncScheduler,
    private val plugin: Plugin
) : BukkitTaskExecutor {
    override fun executeAsynchronously(task: Runnable) {
        asyncScheduler.runNow(plugin) {
            task.run()
        }
    }

    override fun executeAt(world: World, chunkX: Int, chunkZ: Int, task: Runnable) {
        regionScheduler.run(plugin, world, chunkX, chunkZ) {
            task.run()
        }
    }

    override fun execute(task: Runnable) {
        globalRegionScheduler.execute(plugin, task)
    }
}