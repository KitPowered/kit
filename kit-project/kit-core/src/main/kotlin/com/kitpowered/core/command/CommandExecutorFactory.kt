package com.kitpowered.core.command

import org.bukkit.command.CommandExecutor
import kotlin.reflect.KFunction

interface CommandExecutorFactory {

    fun create(function: KFunction<*>, instance: Any): CommandExecutor

}