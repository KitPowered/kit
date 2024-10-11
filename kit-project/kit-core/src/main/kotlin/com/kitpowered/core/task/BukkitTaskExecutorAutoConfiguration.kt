package com.kitpowered.core.task

import com.kitpowered.core.util.condition.ConditionalOnFolia
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Role

@Role(BeanDefinition.ROLE_SUPPORT)
@AutoConfiguration
class BukkitTaskExecutorAutoConfiguration {

    @ConditionalOnFolia
    @Role(BeanDefinition.ROLE_SUPPORT)
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
    @Role(BeanDefinition.ROLE_SUPPORT)
    @Bean
    fun defaultBukkitTaskExecutor(plugin: Plugin): BukkitTaskExecutor {
        return DefaultBukkitTaskExecutor(Bukkit.getScheduler(), plugin)
    }
}