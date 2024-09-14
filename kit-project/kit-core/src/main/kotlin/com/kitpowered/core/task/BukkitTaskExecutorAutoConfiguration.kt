package com.kitpowered.core.task

import com.kitpowered.core.util.condition.ConditionalOnFolia
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean

@AutoConfiguration
class BukkitTaskExecutorAutoConfiguration {

    @ConditionalOnFolia
    @Bean
    fun regionizedBukkitTaskExecutor(plugin: Plugin): BukkitTaskExecutor {
        return RegionizedBukkitTaskExecutor(
            Bukkit.getGlobalRegionScheduler(),
            Bukkit.getRegionScheduler(),
            Bukkit.getAsyncScheduler(),
            plugin
        )
    }

    @ConditionalOnMissingBean
    @Bean
    fun defaultBukkitTaskExecutor(plugin: Plugin): BukkitTaskExecutor {
        return DefaultBukkitTaskExecutor(Bukkit.getScheduler(), plugin)
    }
}