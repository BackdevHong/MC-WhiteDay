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
import org.server.whiteday.game.task.GameTasks
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
                        it.playSound(it.location, Sound.BLOCK_GLASS_BREAK, 10.0F, 1F)
                        it.addPotionEffect(PotionEffect(
                            PotionEffectType.BLINDNESS,
                            999999,
                            255,
                            true,
                            false
                        ))
                        it!!.sendTitle("§x§f§f§f§f§f§f§l§oW§x§f§f§e§3§e§3§l§oH§x§f§f§c§6§c§6§l§oI§x§f§f§a§a§a§a§l§oT§x§f§f§8§e§8§e§l§oE§x§f§f§7§1§7§1§l§o §x§f§f§5§5§5§5§l§oD§x§f§f§3§9§3§9§l§oA§x§f§f§1§c§1§c§l§oY", "", 0, 200, 20)
                    }
                    val taskManager = GameTasks()
                    Bukkit.getScheduler().runTaskLater(server, Runnable {
                        taskManager.startTask()
                    }, 30L)
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
