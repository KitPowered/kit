package com.kitpowered.core.util.stdlib

object ClassLoaderUtils {
    fun withClassLoader(classLoader: ClassLoader, block: () -> Unit) {
        val genuineClassLoader = Thread.currentThread().contextClassLoader
        Thread.currentThread().contextClassLoader = classLoader
        try {
            block()
        } finally {
            Thread.currentThread().contextClassLoader = genuineClassLoader
        }
    }
}