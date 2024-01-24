package org.server.whiteday.option.root

import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
import org.server.whiteday.Main

class Root() : Listener {
    private var status : Boolean? = null
    private var player : Player? = null

    fun setModeOn(player: Player) {
        this.status = true
        this.player = player
    }

    fun setModeOff() {
        this.status = false
    }

    fun getStatus(): Boolean {
        return status!!
    }

    @EventHandler
    fun rootPlaceEvent(e : BlockPlaceEvent) {
        if (this.status == null || this.status == false) {
            println(status)
            return
        }

        if (e.block.type != Material.CHEST) {
            return
        }

        Main.instance?.let {
            val locStr: String =
                e.block.location.blockX.toString() + "," + e.block.location.blockY.toString() + "," + e.block.location.blockZ.toString()
            val sec: MutableList<String> = it.config.getStringList("world.rootingLocations")

            if (sec.size > 50) {
                this.player!!.sendMessage("" + ChatColor.RED + ChatColor.BOLD + "상자 위치는 50개 이하여야 합니다.")
                return
            }

            for (k in sec) {
                if (k == locStr) {
                    this.player!!.sendMessage("" + ChatColor.RED + ChatColor.BOLD + "이미 지정되어 있는 상자 위치입니다.")
                    return
                }
            }

            sec.add(locStr)
            it.config.set("world.rootingLocations", sec)
            it.saveConfig()
            this.player!!.sendMessage("상자 위치를 저장했습니다.")
            return
        }
    }

}