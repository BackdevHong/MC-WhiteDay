package org.server.whiteday.game.command

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter


object GameTabComplater : TabCompleter {
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): MutableList<String> {
        val list: MutableList<String> = ArrayList()

        list.add("start")
        list.add("stop")
        return list;
    }

}