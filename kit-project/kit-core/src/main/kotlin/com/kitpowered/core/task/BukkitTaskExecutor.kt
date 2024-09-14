package com.kitpowered.core.task

import org.bukkit.Location
import org.bukkit.World
import org.springframework.core.task.TaskExecutor

interface BukkitTaskExecutor : TaskExecutor {
    fun executeAsynchronously(task: Runnable)

    fun executeAt(world: World, chunkX: Int, chunkZ: Int, task: Runnable)

    fun executeAt(location: Location, task: Runnable) {
        this.executeAt(location.getWorld(), location.blockX shr 4, location.blockZ shr 4, task)
    }
}