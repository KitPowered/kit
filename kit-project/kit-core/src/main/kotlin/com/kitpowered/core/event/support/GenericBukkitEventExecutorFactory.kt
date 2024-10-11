package com.kitpowered.core.event.support

import com.kitpowered.core.event.BukkitEventExecutorFactory
import org.bukkit.plugin.EventExecutor
import java.lang.reflect.Method

open class GenericBukkitEventExecutorFactory : BukkitEventExecutorFactory {

    override fun create(listener: Any, method: Method, eventType: Class<*>): EventExecutor {
        return EventExecutor { _, event ->
            if (!eventType.isInstance(event)) {
                return@EventExecutor
            }
            method.invoke(listener, event)
        }
    }

}