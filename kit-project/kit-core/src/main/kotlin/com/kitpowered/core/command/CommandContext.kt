package com.kitpowered.core.command

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.jetbrains.annotations.ApiStatus.NonExtendable

@NonExtendable
interface CommandContext<T : CommandSender> {

    val sender: T

    val args: Array<String>

    val label: String

    val command: Command

}