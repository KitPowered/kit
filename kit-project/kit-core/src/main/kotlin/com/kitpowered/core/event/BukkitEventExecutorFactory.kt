package com.kitpowered.core.event

import org.bukkit.plugin.EventExecutor
import java.lang.reflect.Method

interface BukkitEventExecutorFactory {

    fun create(listener: Any, method: Method, eventType: Class<*>): EventExecutor

}