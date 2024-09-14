package com.kitpowered.core.util.ansi

import org.slf4j.Logger
import org.springframework.boot.ansi.AnsiColor
import org.springframework.boot.ansi.AnsiOutput

fun AnsiColor.encode(string: String): String {
    return AnsiOutput.toString(this, string)
}

fun Logger.withColor(color: AnsiColor): Logger {
    return AnsiLogger(this, color)
}