package com.kitpowered.core.util.stdlib

object ClassLoaderUtils {

    fun <T> withClassLoader(classLoader: ClassLoader, block: () -> T): T {
        val genuineClassLoader = Thread.currentThread().contextClassLoader
        Thread.currentThread().contextClassLoader = classLoader
        return try {
            block()
        } finally {
            Thread.currentThread().contextClassLoader = genuineClassLoader
        }
    }
}