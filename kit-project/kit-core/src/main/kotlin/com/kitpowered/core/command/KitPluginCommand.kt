package com.kitpowered.core.command

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.PluginIdentifiableCommand
import org.bukkit.plugin.Plugin

class KitPluginCommand(
    private val plugin: Plugin,
    commandName: String,
    description: String,
    usage: String,
    aliases: List<String>,
    private val executor: CommandExecutor
) : Command(commandName, description, usage, aliases), PluginIdentifiableCommand {

    private companion object {
        private val unknownCommandMessage by lazy {
            try {
                Class
                    .forName("org.spigotmc.SpigotConfig")
                    .getField("unknownCommandMessage")
                    .get(null) as String
            } catch (throwable: Throwable) {
                "Unknown command. Type \"/help\" for help."
            }
        }
    }

    override fun getPlugin(): Plugin {
        return plugin
    }

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<String>): Boolean {
        if (!executor.onCommand(sender, this, commandLabel, args)) {
            if (unknownCommandMessage.isNotEmpty()) {
                sender.sendMessage(unknownCommandMessage)
            }
        }
        return true
    }
}