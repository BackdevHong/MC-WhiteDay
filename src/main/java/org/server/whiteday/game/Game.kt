package org.server.whiteday.game

import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.server.whiteday.Main
import org.server.whiteday.game.task.GameTasks

class Game {
    private var status : Boolean? = null
    private var taskManager : GameTasks? = null

    init {
        status = true
        taskManager = GameTasks(this)
    }

    fun startGame() {
        Main.instance.let { server ->
            println(status)
            server!!.server.onlinePlayers.forEach {
                it.playSound(it.location, Sound.BLOCK_GLASS_BREAK, 10.0F, 1F)
                it.addPotionEffect(
                    PotionEffect(
                    PotionEffectType.BLINDNESS,
                    999999,
                    255,
                    true,
                    false
                )
                )
                it!!.sendTitle("§x§f§f§f§f§f§f§l§oW§x§f§f§e§3§e§3§l§oH§x§f§f§c§6§c§6§l§oI§x§f§f§a§a§a§a§l§oT§x§f§f§8§e§8§e§l§oE§x§f§f§7§1§7§1§l§o §x§f§f§5§5§5§5§l§oD§x§f§f§3§9§3§9§l§oA§x§f§f§1§c§1§c§l§oY", "", 0, 200, 20)
            }
            Bukkit.getScheduler().runTaskLater(server, Runnable {
                taskManager!!.startTask()
            }, 30L)
        }
    }

//    private fun stopGame() {
//        if (status == false) {
//            return
//        }
//
//        status = false
//    }

    fun forceStopGame() {
        status = false
        taskManager!!.forceStopTask()
        Main.instance.let {
            it!!.server.onlinePlayers.forEach{ player ->
                player.health = 0.0
                player.sendTitle("", "", 0, 0, 0)
                player.sendMessage("게임이 강제로 종료되어 모두 사망합니다.")
            }
        }
    }

    fun getStatus(): Boolean {
        return status!!
    }

}