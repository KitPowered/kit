package com.kitpowered.core.util.stdlib

fun <K, V> Map<K, V>.single(): Pair<K, V> {
    return this.entries.single().toPair()
}

fun <K, V> Map<K, V>.single(predicate: (Pair<K, V>) -> Boolean): Pair<K, V> {
    return this.entries.single { predicate(it.toPair()) }.toPair()
}

fun <K, V> Map<K, V>.singleOrNull(): Pair<K, V>? {
    return this.entries.singleOrNull()?.toPair()
}

fun <K, V> Map<K, V>.singleOrNull(predicate: (Pair<K, V>) -> Boolean): Pair<K, V>? {
    return this.entries.singleOrNull { predicate(it.toPair()) }?.toPair()
}