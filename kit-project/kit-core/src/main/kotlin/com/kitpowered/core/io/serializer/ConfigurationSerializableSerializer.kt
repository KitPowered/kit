package com.kitpowered.core.io.serializer

import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.util.io.BukkitObjectOutputStream
import org.springframework.core.serializer.Serializer
import java.io.ByteArrayOutputStream
import java.io.OutputStream

class ConfigurationSerializableSerializer : Serializer<ConfigurationSerializable> {

    override fun serialize(
        configurationSerializable: ConfigurationSerializable,
        outputStream: OutputStream,
    ) {
        BukkitObjectOutputStream(outputStream).use { bukkitObjectOutputStream ->
            bukkitObjectOutputStream.writeObject(configurationSerializable)
        }
    }

    override fun serializeToByteArray(configurationSerializable: ConfigurationSerializable): ByteArray {
        ByteArrayOutputStream().use { byteArrayOutputStream ->
            serialize(configurationSerializable, byteArrayOutputStream)
            return byteArrayOutputStream.toByteArray()
        }
    }
}