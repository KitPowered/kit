package com.kitpowered.core.io.serializer

import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.util.io.BukkitObjectInputStream
import org.springframework.core.serializer.Deserializer
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStream

class ConfigurationSerializableDeserializer : Deserializer<ConfigurationSerializable> {

    override fun deserialize(inputStream: InputStream): ConfigurationSerializable {
        BukkitObjectInputStream(inputStream).use { bukkitObjectInputStream ->
            try {
                return bukkitObjectInputStream.readObject() as ConfigurationSerializable
            } catch (classNotFoundException: ClassNotFoundException) {
                throw IOException("Failed to deserialize object.", classNotFoundException)
            }
        }
    }

    override fun deserializeFromByteArray(serialized: ByteArray): ConfigurationSerializable {
        ByteArrayInputStream(serialized).use { byteArrayInputStream ->
            return deserialize(byteArrayInputStream)
        }
    }
}