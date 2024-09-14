package com.kitpowered.core.util.stdlib

fun <T> T.print(
    prefix: String,
    delimiter: String = " ",
): T {
    println(prefix + delimiter + this)
    return this
}
