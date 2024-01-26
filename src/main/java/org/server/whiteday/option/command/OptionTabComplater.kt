package org.server.whiteday.option.command

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter


object OptionTabComplater : TabCompleter {
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): MutableList<String> {
        when {
            args.size == 1 -> {
                return arrayListOf("randomLocation", "victimLocation", "jailLocation", "rootingLocation").toMutableList()
            }

            args[0] == "randomLocation" && args.size <= 2 -> {
                return arrayListOf("add", "remove").toMutableList()
            }

            args[0] == "victimLocation" && args.size <= 2 -> {
                return arrayListOf("set").toMutableList()
            }

            args[0] == "jailLocation" && args.size <= 2 -> {
                return arrayListOf("set").toMutableList()
            }

            args[0] == "rootingLocation" && args.size <= 2 -> {
                return arrayListOf("on", "off").toMutableList()
            }

            args.size > 2 -> {
                return arrayListOf("").toMutableList()
            }
        }
        return arrayListOf("").toMutableList()
    }
}