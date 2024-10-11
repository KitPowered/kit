package com.kitpowered.core.context

import java.io.InputStream
import java.net.URL
import java.util.*

internal class KitPluginClassLoader(
    private val classLoaders: List<ClassLoader>
) : ClassLoader() {
    private fun <T> findAny(finder: (ClassLoader) -> T?): T? {
        for (classLoader in classLoaders) {
            val value = finder(classLoader)
            if (value != null) {
                return value
            }
        }

        return null
    }

    override fun getResourceAsStream(name: String?): InputStream? {
        return findAny { it.getResourceAsStream(name) }
    }

    override fun findClass(name: String?): Class<*> {
        for (classLoader in classLoaders) {
            try {
                return classLoader.loadClass(name)
            } catch (_: ClassNotFoundException) {
            }
        }

        throw ClassNotFoundException(name)
    }

    override fun findResource(name: String?): URL? {
        return findAny { it.getResource(name) }
    }

    override fun findResources(name: String?): Enumeration<URL> {
        val result = classLoaders.flatMap {
            it.getResources(name).toList()
        }

        return Collections.enumeration(result)
    }
}