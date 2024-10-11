package com.kitpowered.core.coroutines.command

import com.kitpowered.core.command.support.GenericCommandExecutorFactory
import kotlinx.coroutines.CoroutineScope
import org.bukkit.command.CommandExecutor
import kotlin.reflect.KFunction

class CoroutinesCommandExecutorFactory(
    private val coroutineScope: CoroutineScope
) : GenericCommandExecutorFactory() {

    override fun create(function: KFunction<*>, instance: Any): CommandExecutor {
        return CoroutinesCommandExecutor(coroutineScope, function, instance)
    }

}