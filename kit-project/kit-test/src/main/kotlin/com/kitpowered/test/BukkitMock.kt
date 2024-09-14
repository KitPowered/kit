package com.kitpowered.test

import be.seeseemelk.mockbukkit.MockBukkit
import be.seeseemelk.mockbukkit.WorldMock
import org.bukkit.World
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

object BukkitMock {
    fun createPlugin(
        pluginName: String = "plugin-${UUID.randomUUID()}",
        pluginVersion: String = "1.0.0",
    ): JavaPlugin {
        MockBukkit.ensureMocking()
        return MockBukkit.createMockPlugin(pluginName, pluginVersion)
    }

    fun createWorld(worldName: String = "world-${UUID.randomUUID()}"): World {
        MockBukkit.ensureMocking()
        val worldMock =
            WorldMock().apply {
                name = worldName
            }
        MockBukkit.getMock()!!.addWorld(worldMock)
        return worldMock
    }
}
