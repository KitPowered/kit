package com.kitpowered.core.coroutines

import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.MainCoroutineDispatcher
import kotlinx.coroutines.internal.MainDispatcherFactory

@InternalCoroutinesApi
class MainBukkitCoroutineDispatcherFactory : MainDispatcherFactory {
    override val loadPriority: Int
        get() = 0

    override fun createDispatcher(allFactories: List<MainDispatcherFactory>): MainCoroutineDispatcher {
        return MainBukkitCoroutineDispatcher()
    }
}