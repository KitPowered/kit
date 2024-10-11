package com.kitpowered.core.command

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.PluginIdentifiableCommand
import org.bukkit.plugin.Plugin
import org.spigotmc.SpigotConfig

class KitPluginCommand(
    private val plugin: Plugin,
    commandName: String,
    description: String,
    usage: String,
    aliases: List<String>,
    private val executor: CommandExecutor
) : Command(commandName, description, usage, aliases), PluginIdentifiableCommand {

    override fun getPlugin(): Plugin {
        return plugin
    }

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<String>): Boolean {
        if (!executor.onCommand(sender, this, commandLabel, args)) {
            if (SpigotConfig.unknownCommandMessage.isNotEmpty()) {
                sender.sendMessage(SpigotConfig.unknownCommandMessage)
            }
        }
        return true
    }
}