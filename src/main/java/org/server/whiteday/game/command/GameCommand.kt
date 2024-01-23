package org.server.whiteday.game.command

import org.bukkit.ChatColor
import org.bukkit.Sound
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.server.whiteday.Main
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
                Main.instance.let { server ->
                    server!!.server.onlinePlayers.forEach {
                        var count = 6
                        it.playSound(it.location, Sound.BLOCK_GLASS_BREAK, 10.0F, 1F)
                        it.addPotionEffect(PotionEffect(
                            PotionEffectType.BLINDNESS,
                            999999,
                            255,
                            true,
                            false
                        ))
                        val schedule2 = it.server.scheduler.scheduleSyncRepeatingTask(
                            server,
                            {
                                if (count < 2) {
                                    it.sendTitle("", "", 0, 0, 0)
                                    it.server.scheduler.cancelTasks(server)
                                }
                                it.sendTitle("화이트 데이", (count - 1).toString(), 0, 200, 20)
                                count -= 1
                            },
                            20,
                            20
                        )
                    }
                }
                return true
            }
            args[0] == "stop" -> {
                sender.sendMessage("허허")
            }
        }

        return false
    }
}
