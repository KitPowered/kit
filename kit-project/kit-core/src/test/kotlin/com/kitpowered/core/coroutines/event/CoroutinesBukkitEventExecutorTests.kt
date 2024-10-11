package com.kitpowered.core.coroutines.event

import com.kitpowered.core.context.KitPluginApplicationContext
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock

class CoroutinesBukkitEventExecutorTests {

    @Test
    fun test() = runBlocking(KitPluginApplicationContext(mock()) + CoroutineName("test")) {

        val listener = TestListener()
        val executor = CoroutinesBukkitEventExecutor(
            this,
            listener,
            TestListener::onTestEvent,
            AsyncPlayerPreLoginEvent::class.java
        )
        executor.execute(mock(), mock<AsyncPlayerPreLoginEvent>())
    }

    class TestListener {
        suspend fun onTestEvent(event: AsyncPlayerPreLoginEvent) {
            coroutineScope {
                println("hi! " + this.coroutineContext[CoroutineName])
            }
        }
    }
}