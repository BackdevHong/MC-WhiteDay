package org.server.whiteday.game.task

import org.bukkit.*
import org.bukkit.block.Chest
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask
import org.server.whiteday.Main
import org.server.whiteday.game.Game
import org.server.whiteday.game.utils.RandomMessage
import org.server.whiteday.game.utils.RandomTeleport
import java.io.File

class GameTasks(game : Game) {
    private var plugin: Plugin? = null
    private var config: FileConfiguration? = null
    private var task : BukkitTask? = null
    private var random : RandomTeleport? = null
    private var count: Int = 5
    private var game: Game? = null

    init {
        config = Main.instance!!.config
        random = RandomTeleport
        this.plugin = Main.instance
        this.game = game
    }

    fun startTask() {
        task = object : BukkitRunnable() {
            override fun run() {
                plugin!!.server.onlinePlayers.forEach {
                    it.playSound(it.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10.0F, 1F)
                    it.sendTitle("§x§f§f§f§f§f§f§l§oW§x§f§f§e§3§e§3§l§oH§x§f§f§c§6§c§6§l§oI§x§f§f§a§a§a§a§l§oT§x§f§f§8§e§8§e§l§oE§x§f§f§7§1§7§1§l§o §x§f§f§5§5§5§5§l§oD§x§f§f§3§9§3§9§l§oA§x§f§f§1§c§1§c§l§oY", count.toString(), 0, 200, 20)
                }
                if (count < 1) {
                    stopTask()
                } else {
                    count--
                }
            }
        }.runTaskTimer(plugin!!, 0, 20L)
    }

    fun stopTask() {
        task!!.cancel()
        Main.instance!!.config.load(File(Main.instance!!.dataFolder, "config.yml"))

        Main.instance?.let {
            val sec : MutableList<String> = it.config.getStringList("world.rootingLocations")
            val randomItems: List<String> = sec.shuffled().take(sec.size / 2)

            sec.forEach { item ->
                val loc = Location(it.server.getWorld("world"), item.split(",")[0].toDouble(), item.split(",")[1].toDouble(), item.split(",")[2].toDouble())
                val box = it.server.getWorld("world")!!.getBlockAt(loc).state as Chest
                val boxItem = ItemStack(Material.AIR)
                box.blockInventory.setItem(13, boxItem)
            }
            randomItems.forEach { item ->
                val loc = Location(it.server.getWorld("world"), item.split(",")[0].toDouble(), item.split(",")[1].toDouble(), item.split(",")[2].toDouble())
                val box = it.server.getWorld("world")!!.getBlockAt(loc).state as Chest
                val boxItem = ItemStack(Material.BOW)
                box.blockInventory.setItem(13, boxItem)
            }
        }

        val victim = Bukkit.getOnlinePlayers().random()
        plugin!!.server.onlinePlayers.forEach {
            when {
                it == victim -> {
                    it.sendMessage("당신은 유령입니다. 20초뒤 출발합니다.")
                }

                else -> {
                    random!!.randomTP(it)
                    it.removePotionEffect(PotionEffectType.BLINDNESS)
                }
            }
            it.playSound(it.location, Sound.AMBIENT_SOUL_SAND_VALLEY_MOOD, 10.0F, 1F)
            it.sendTitle(" ", ""+ ChatColor.RED + ChatColor.BOLD +"START", 0, 20 * 2, 20)
            it.sendMessage("<"+ChatColor.DARK_RED+ChatColor.BOLD+"???"+ChatColor.WHITE+">"+ ChatColor.DARK_RED + ChatColor.ITALIC + ChatColor.BOLD +" 난 너희들이 세상에서 " + ChatColor.UNDERLINE + "가장 끔찍하고, 고통스럽게 죽었으면 좋겠어..")
        }
        Bukkit.getScheduler().runTaskLater(
            plugin!!,
            Runnable {
                val vic = config!!.getString("world.victimLocation")!!.split(",")
                val loc = Location(victim.location.world, vic[0].toDouble(), vic[1].toDouble(), vic[2].toDouble())
                victim.teleport(loc)
                victim.removePotionEffect(PotionEffectType.BLINDNESS)
                Bukkit.broadcastMessage("<"+ChatColor.DARK_RED+ChatColor.BOLD+"???"+ChatColor.WHITE+">"+ ChatColor.DARK_RED + ChatColor.ITALIC + ChatColor.BOLD +" "+RandomMessage.randomMessage())
            },
            20L * 20)
    }

    fun forceStopTask() {
        Bukkit.getScheduler().cancelTasks(plugin!!)
    }
}