package com.kitpowered.core.configuration

import org.bukkit.configuration.file.FileConfiguration
import java.io.File

class BukkitFileConfiguration(
    private val delegate: FileConfiguration,
    private val originalFile: File,
) : FileConfiguration() {
    override fun saveToString(): String {
        return delegate.saveToString()
    }

    override fun loadFromString(contents: String) {
        return delegate.loadFromString(contents)
    }

    fun save() {
        delegate.save(originalFile)
    }

    fun load() {
        delegate.load(originalFile)
    }
}