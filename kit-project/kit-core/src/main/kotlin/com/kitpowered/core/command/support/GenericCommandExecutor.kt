package com.kitpowered.core.command.support

import com.kitpowered.core.command.CommandContext
import com.kitpowered.core.util.ReflectionUtils
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.springframework.core.ResolvableType
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter
import kotlin.reflect.full.extensionReceiverParameter
import kotlin.reflect.jvm.javaMethod
import kotlin.reflect.jvm.jvmErasure

open class GenericCommandExecutor(
    private val function: KFunction<*>,
    private val instance: Any
) : CommandExecutor {

    final override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        val context = buildContext(sender, command, label, args)
        val arguments = getArguments(context, args)
        if (getCommandSenderType().value.isInstance(sender)) {
            execute(function, arguments)
            return true
        }
        return false
    }

    private fun getCommandSenderType(): Lazy<Class<*>> {
        return lazy {
            val contextReceiver = function.extensionReceiverParameter ?: return@lazy CommandSender::class.java
            if (contextReceiver.type.jvmErasure.java != CommandContext::class.java) {
                error("Extension receiver is not a command context")
            }
            val javaMethod = function.javaMethod ?: error("Function is not a java method")
            ResolvableType
                .forMethodParameter(javaMethod, 0, javaMethod.declaringClass)
                .getGeneric(0)
                .resolve(CommandSender::class.java)
        }
    }

    protected open fun execute(
        function: KFunction<*>,
        arguments: Map<KParameter, Any?>
    ) {
        ReflectionUtils.callBy(function, arguments)
    }

    private fun getArguments(context: CommandContext<*>, commandArguments: Array<String>): Map<KParameter, Any?> {
        return function.parameters.associateWith { parameter ->
            when (parameter.kind) {
                KParameter.Kind.EXTENSION_RECEIVER -> context
                KParameter.Kind.INSTANCE -> instance
                KParameter.Kind.VALUE -> when (parameter.type.jvmErasure) {
                    Array<String>::class -> commandArguments
                    List::class -> commandArguments.toList()
                    else -> UnsupportedOperationException("Unsupported parameter type: ${parameter.type.jvmErasure}")
                }
            }
        }
    }

    private fun buildContext(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<String>
    ): CommandContext<*> {
        return object : CommandContext<CommandSender> {
            override val sender: CommandSender
                get() = sender
            override val args: Array<String>
                get() = args
            override val label: String
                get() = label
            override val command: Command
                get() = command
        }
    }

}