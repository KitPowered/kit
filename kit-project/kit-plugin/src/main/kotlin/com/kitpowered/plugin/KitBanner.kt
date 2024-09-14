package com.kitpowered.plugin

import com.kitpowered.core.util.ansi.withColor
import org.bukkit.plugin.Plugin
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.Banner
import org.springframework.boot.ansi.AnsiColor
import org.springframework.core.env.Environment
import java.io.File
import java.io.PrintStream
import java.time.LocalDate
import java.util.jar.JarFile
import kotlin.math.absoluteValue


class KitBanner(plugin: Plugin, private val pluginFile: File) : Banner {
    private val logger = LoggerFactory.getLogger(plugin.name)

    private val banners = listOf(
        BlueBanner(logger),
        GreenBanner(logger),
        RedBanner(logger)
    )

    override fun printBanner(environment: Environment?, sourceClass: Class<*>?, out: PrintStream) {
        getBanner().printBanner(environment, sourceClass, out)
    }

    private fun getBanner(): Banner {
        val absoluteHash = getTimestamp().hashCode()
        val epochDay = LocalDate.now().toEpochDay()
        val epochDayAsInt = (epochDay xor (epochDay ushr 32)).toInt()
        val index = (absoluteHash + epochDayAsInt).absoluteValue % banners.size
        return banners[index]
    }

    private fun getTimestamp(): String {
        return JarFile(pluginFile).use { jarFile ->
            jarFile.manifest.mainAttributes.getValue("Implementation-Timestamp")
        }
    }

    private class BlueBanner(private val logger: Logger) : Banner {
        override fun printBanner(environment: Environment?, sourceClass: Class<*>?, out: PrintStream) {
            with(logger.withColor(AnsiColor.BLUE)) {
                info("██╗  ██╗██╗████████╗")
                info("██║ ██╔╝██║╚══██╔══╝")
                info("█████╔╝ ██║   ██║   ")
                info("██╔═██╗ ██║   ██║   ")
                info("██║  ██╗██║   ██║   ")
                info("╚═╝  ╚═╝╚═╝   ╚═╝   ")
            }
        }
    }

    private class GreenBanner(private val logger: Logger) : Banner {
        override fun printBanner(environment: Environment?, sourceClass: Class<*>?, out: PrintStream) {
            with(logger.withColor(AnsiColor.GREEN)) {
                info(" ____ ____ ____ ")
                info("||K |||i |||t ||")
                info("||__|||__|||__||")
                info("|/__\\|/__\\|/__\\|")
            }
        }
    }

    private class RedBanner(private val logger: Logger) : Banner {
        override fun printBanner(environment: Environment?, sourceClass: Class<*>?, out: PrintStream) {
            with(logger.withColor(AnsiColor.BRIGHT_RED)) {
                info(" __    __  __    __     ")
                info("|  \\  /  \\|  \\  |  \\    ")
                info("| \$\$ /  \$\$ \\\$\$ _| \$\$_   ")
                info("| \$\$/  \$\$ |  \\|   \$\$ \\  ")
                info("| \$\$  \$\$  | \$\$ \\\$\$\$\$\$\$  ")
                info("| \$\$\$\$\$\\  | \$\$  | \$\$ __ ")
                info("| \$\$ \\\$\$\\ | \$\$  | \$\$|  \\")
                info("| \$\$  \\\$\$\\| \$\$   \\\$\$  \$\$")
                info(" \\\$\$   \\\$\$ \\\$\$    \\\$\$\$\$ ")
            }
        }
    }
}