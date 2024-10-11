package com.kitpowered.core.command.support

import com.kitpowered.core.command.CommandContext
import com.kitpowered.core.util.stdlib.print
import org.bukkit.command.ConsoleCommandSender
import org.junit.jupiter.api.Test
import org.springframework.core.ResolvableType
import kotlin.reflect.full.declaredMemberExtensionFunctions
import kotlin.reflect.jvm.javaMethod

class GenericCommandExecutorTests {

    @Test
    fun test() {

        val context = GenericCommandExecutorTests::class.declaredMemberExtensionFunctions.first().javaMethod!!
        ResolvableType.forMethodParameter(context, 0, this::class.java).resolveGenerics().first().print("type")
    }

    fun CommandContext<ConsoleCommandSender>.test() {

    }

}