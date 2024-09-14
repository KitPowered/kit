package com.kitpowered.core.io

import org.bukkit.plugin.PluginManager
import org.bukkit.plugin.java.JavaPlugin
import org.springframework.core.io.DefaultResourceLoader
import org.springframework.core.io.Resource
import org.springframework.util.ReflectionUtils

@Suppress("UnstableApiUsage", "DEPRECATION")
internal class CascadingResourceLoader(javaPlugin: JavaPlugin, pluginManager: PluginManager) :
    DefaultResourceLoader(getClassLoader(javaPlugin)) {
    private val resourceLoaders = mutableListOf<PublicizedResourceLoader>()

    init {
        val dependPluginNames = mutableSetOf<String>()
        dependPluginNames.addAll(javaPlugin.description.pluginDependencies)
        dependPluginNames.addAll(javaPlugin.description.pluginSoftDependencies)
        dependPluginNames.addAll(javaPlugin.description.loadBeforePlugins)

        dependPluginNames.addAll(javaPlugin.pluginMeta.pluginDependencies)
        dependPluginNames.addAll(javaPlugin.pluginMeta.pluginSoftDependencies)
        dependPluginNames.addAll(javaPlugin.pluginMeta.loadBeforePlugins)

        for (dependPluginName in dependPluginNames) {
            val dependPlugin = pluginManager.getPlugin(dependPluginName)
            if (dependPlugin is JavaPlugin) {
                val pluginClassLoader = getClassLoader(dependPlugin)
                val resourceLoader = PublicizedResourceLoader(pluginClassLoader)
                resourceLoaders.add(resourceLoader)
            }
        }
    }

    private companion object {
        private fun getClassLoader(javaPlugin: JavaPlugin): ClassLoader {
            val classLoaderMethod = ReflectionUtils.findMethod(JavaPlugin::class.java, "getClassLoader")
            requireNotNull(classLoaderMethod) { "A method named 'getClassLoader' not found." }
            return if (!classLoaderMethod.canAccess(javaPlugin)) {
                try {
                    classLoaderMethod.setAccessible(true)
                    ReflectionUtils.invokeMethod(classLoaderMethod, javaPlugin) as ClassLoader
                } finally {
                    classLoaderMethod.setAccessible(false)
                }
            } else {
                ReflectionUtils.invokeMethod(classLoaderMethod, javaPlugin) as ClassLoader
            }
        }
    }

    override fun getResource(location: String): Resource {
        val resourceFromSuper = super.getResource(location)
        if (!resourceFromSuper.exists()) {
            for (resourceLoader in resourceLoaders) {
                val resourceFromDependPlugin = resourceLoader.getResource(location)
                if (resourceFromDependPlugin.exists()) {
                    return resourceFromDependPlugin
                }
            }
        }
        return resourceFromSuper
    }

    override fun getResourceByPath(path: String): Resource {
        val resourceFromSuper = super.getResourceByPath(path)
        if (!resourceFromSuper.exists()) {
            for (resourceLoader in resourceLoaders) {
                val resourceFromDependPlugin = resourceLoader.getResourceByPath(path)
                if (resourceFromDependPlugin.exists()) {
                    return resourceFromDependPlugin
                }
            }
        }
        return resourceFromSuper
    }

    private class PublicizedResourceLoader(classLoader: ClassLoader) : DefaultResourceLoader(classLoader) {
        public override fun getResourceByPath(path: String): Resource {
            return super.getResourceByPath(path)
        }
    }
}
