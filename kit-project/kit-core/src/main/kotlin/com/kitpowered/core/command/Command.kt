package com.kitpowered.core.command

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Command(
    val name: String,
    val description: String = "",
    val usage: String = "",
    val aliases: Array<String> = []
)