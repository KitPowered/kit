@file:Suppress("FunctionName")

package com.kitpowered.core.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.bukkit.Location
import org.bukkit.World

val Dispatchers.Async: CoroutineDispatcher by lazy { AsyncBukkitCoroutineDispatcher() }

fun Dispatchers.Region(location: Location): CoroutineDispatcher {
    val world = location.world
    requireNotNull(world) { "Location world is null" }
    return Region(world, location.chunk.x, location.chunk.z)
}

fun Dispatchers.Region(world: World, chunkX: Int, chunkZ: Int): CoroutineDispatcher {
    return RegionBukkitCoroutineDispatcher(world, chunkX, chunkZ)
}