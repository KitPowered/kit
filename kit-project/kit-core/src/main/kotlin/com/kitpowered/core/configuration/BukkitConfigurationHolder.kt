package com.kitpowered.core.configuration

import org.bukkit.configuration.Configuration

interface BukkitConfigurationHolder<T : Configuration> {
    fun getConfig(): T
}