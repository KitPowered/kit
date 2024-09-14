package com.kitpowered.core.util.stdlib

@Suppress("UNCHECKED_CAST")
fun <T : Any> Any.uncheckedCast(): T {
    return this as T
}