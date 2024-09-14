package com.kitpowered.core.coroutines.configuration

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.bukkit.configuration.file.FileConfiguration
import java.io.File

class CoroutinesFileConfiguration(
    private val delegate: FileConfiguration,
    private val originalFile: File,
) : FileConfiguration() {
    override fun saveToString(): String {
        return delegate.saveToString()
    }

    override fun loadFromString(contents: String) {
        return delegate.loadFromString(contents)
    }

    suspend fun save() {
        withContext(Dispatchers.IO) {
            delegate.save(originalFile)
        }
    }

    suspend fun load() {
        withContext(Dispatchers.IO) {
            delegate.load(originalFile)
        }
    }
}