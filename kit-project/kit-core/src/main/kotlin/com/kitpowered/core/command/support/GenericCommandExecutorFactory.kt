package com.kitpowered.core.command.support

import com.kitpowered.core.command.CommandExecutorFactory
import org.bukkit.command.CommandExecutor
import kotlin.reflect.KFunction

open class GenericCommandExecutorFactory : CommandExecutorFactory {

    override fun create(function: KFunction<*>, instance: Any): CommandExecutor {
        return GenericCommandExecutor(function, instance)
    }

}