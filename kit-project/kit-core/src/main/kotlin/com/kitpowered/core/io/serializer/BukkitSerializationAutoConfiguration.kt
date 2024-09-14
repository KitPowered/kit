package com.kitpowered.core.io.serializer

import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.core.serializer.Deserializer
import org.springframework.core.serializer.Serializer

@AutoConfiguration
class BukkitSerializationAutoConfiguration {

    @Bean
    fun configurationSerializableSerializer(): Serializer<ConfigurationSerializable> {
        return ConfigurationSerializableSerializer()
    }

    @Bean
    fun configurationSerializableDeserializer(): Deserializer<ConfigurationSerializable> {
        return ConfigurationSerializableDeserializer()
    }
}