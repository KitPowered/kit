package com.kitpowered.core.coroutines.event

import com.kitpowered.core.event.support.GenericBukkitEventExecutorFactory
import kotlinx.coroutines.CoroutineScope
import org.bukkit.plugin.EventExecutor
import java.lang.reflect.Method
import kotlin.reflect.jvm.kotlinFunction

class CoroutinesBukkitEventExecutorFactory(
    private val coroutineScope: CoroutineScope
) : GenericBukkitEventExecutorFactory() {

    override fun create(listener: Any, method: Method, eventType: Class<*>): EventExecutor {
        val kotlinFunction = method.kotlinFunction

        if (kotlinFunction == null || !kotlinFunction.isSuspend) {
            return super.create(listener, method, eventType)
        }
        return CoroutinesBukkitEventExecutor(coroutineScope, listener, kotlinFunction, eventType)
    }

}