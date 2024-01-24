package org.server.whiteday.game.command

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Sound
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.server.whiteday.Main
import org.server.whiteday.game.Game
import org.server.whiteday.game.task.GameTasks
import org.server.whiteday.utils.CheckPermission

object GameCommand : CommandExecutor {
    private var game: Game? = null
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
                if (game != null) {
                    if (game!!.getStatus()) {
                        sender.sendMessage("이미 게임이 진행중입니다!")
                        return false
                    }
                }
                game = Game()
                game!!.startGame()
                return true
            }

            args[0] == "stop" -> {
                if (game != null) {
                    if (!(game!!.getStatus())) {
                        sender.sendMessage("게임 진행중이 아니라서 게임을 종료할 수 없습니다.")
                        return false
                    }
                    game!!.forceStopGame()
                    return true
                }
                sender.sendMessage("게임 진행중이 아니라서 게임을 종료할 수 없습니다.")
                return false
            }
        }
        return false
    }
}
