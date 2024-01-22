package org.server.whiteday.game.command

import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object GameCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player && !sender.isOp) {
            sender.sendMessage("플레이어만 사용 가능한 명령어입니다.")
            return false
        }

        if (args.isEmpty()) {
            sender.sendMessage("" + ChatColor.BLUE + "==============================")
            sender.sendMessage("/game start - 게임을 시작합니다.")
            sender.sendMessage("/game stop - 게임을 강제로 종료합니다.")
            sender.sendMessage("" + ChatColor.BLUE + "==============================")
            return true
        } else if (args[0] == "start") {
            sender.sendMessage("" + ChatColor.BLUE + "START")
            return true
        } else if (args[0] == "stop") {
            sender.sendMessage("허허")
        }

        return false
    }
}
