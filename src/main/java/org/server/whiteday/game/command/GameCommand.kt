package org.server.whiteday.game.command

import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.server.whiteday.utils.CheckPermission

object GameCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        when {
            !CheckPermission.checkPermission(sender) -> {
                sender.sendMessage("오피 권한을 가진 유저만 사용 가능한 명령어입니다.")
                return false
            }
            args.isEmpty() -> {
                sender.sendMessage("" + ChatColor.BLUE + "============== WHITE_DAY GAME COMMAND ================")
                sender.sendMessage("[WHITE_DAY] /game start - 게임을 시작합니다.")
                sender.sendMessage("[WHITE_DAY] /game stop - 게임을 강제로 종료합니다.")
                sender.sendMessage("" + ChatColor.BLUE + "====================================================")
                return true
            }
            args[0] == "start" -> {
                sender.sendMessage("" + ChatColor.BLUE + "START")
                return true
            }
            args[0] == "stop" -> {
                sender.sendMessage("허허")
            }
        }

        return false
    }
}
