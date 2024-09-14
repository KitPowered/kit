package com.kitpowered.core.task

import org.bukkit.Location
import org.bukkit.World
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitScheduler

class DefaultBukkitTaskExecutor(
    private val scheduler: BukkitScheduler,
    private val plugin: Plugin
) : BukkitTaskExecutor {
    override fun executeAsynchronously(task: Runnable) {
        scheduler.runTaskAsynchronously(plugin, task)
    }

    override fun executeAt(world: World, chunkX: Int, chunkZ: Int, task: Runnable) {
        scheduler.runTask(plugin, task)
    }

    override fun executeAt(location: Location, task: Runnable) {
        scheduler.runTask(plugin, task)
    }

    override fun execute(task: Runnable) {
        scheduler.runTask(plugin, task)
    }
}