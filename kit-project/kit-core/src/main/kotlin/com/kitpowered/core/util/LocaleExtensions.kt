package com.kitpowered.core.util

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import java.util.*

fun CommandSender.getLocale(): Locale {
    return when (this) {
        is Player -> locale()
        else -> LocaleContextHolder.getLocale()
    }
}

fun MessageSource.getMessage(
    commandSender: CommandSender,
    code: String,
    args: Array<String> = emptyArray(),
    defaultMessage: String = "<message not found>"
): String {
    return getMessage(code, args, defaultMessage, commandSender.getLocale())
        ?: error("If defaultMessage is not null, the message must be found.")
}

fun MessageSource.findMessage(
    commandSender: CommandSender,
    code: String,
    args: Array<String> = emptyArray()
): String? {
    return getMessage(code, args, null, commandSender.getLocale())
}