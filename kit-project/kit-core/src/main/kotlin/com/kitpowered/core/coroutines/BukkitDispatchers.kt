@file:Suppress("FunctionName")

package com.kitpowered.core.coroutines

import com.kitpowered.core.coroutines.scheduling.CoroutinesBukkitAsyncScheduler
import com.kitpowered.core.coroutines.scheduling.CoroutinesBukkitMainScheduler
import com.kitpowered.core.coroutines.scheduling.CoroutinesBukkitRegionScheduler
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.bukkit.Location
import org.bukkit.World

object BukkitDispatchers {
    val Main: CoroutineDispatcher = CoroutinesBukkitMainScheduler()

    val Async: CoroutineDispatcher = CoroutinesBukkitAsyncScheduler()

    fun Dispatchers.Region(location: Location): CoroutineDispatcher {
        val world = location.world
        requireNotNull(world) { "Location world is null" }
        return Region(world, location.chunk.x, location.chunk.z)
    }

    fun Dispatchers.Region(world: World, chunkX: Int, chunkZ: Int): CoroutineDispatcher {
        return CoroutinesBukkitRegionScheduler(world, chunkX, chunkZ)
    }
}