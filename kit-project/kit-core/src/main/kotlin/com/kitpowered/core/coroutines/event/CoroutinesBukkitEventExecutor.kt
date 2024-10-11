package com.kitpowered.core.coroutines.event

import com.kitpowered.core.util.ReflectionUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.bukkit.Bukkit
import org.bukkit.event.Event
import org.bukkit.event.Listener
import org.bukkit.plugin.EventExecutor
import kotlin.reflect.KFunction

class CoroutinesBukkitEventExecutor(
    private val coroutineScope: CoroutineScope,
    private val listener: Any,
    private val kotlinFunction: KFunction<*>,
    private val eventType: Class<*>
) : EventExecutor {

    override fun execute(unused: Listener, event: Event) {
        if (!eventType.isInstance(event)) {
            return
        }

        coroutineScope.launch(Dispatchers.Unconfined, CoroutineStart.UNDISPATCHED) {
            @Suppress("SENSELESS_COMPARISON")
            if (Bukkit.getServer() != null) {
                check(Bukkit.isPrimaryThread() != event.isAsynchronous)
            }
            ReflectionUtils.callSuspend(kotlinFunction, listener, event)
        }
    }

}