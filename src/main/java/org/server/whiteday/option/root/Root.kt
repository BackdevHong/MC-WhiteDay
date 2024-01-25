package org.server.whiteday.option.root

import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
import org.server.whiteday.Main

class Root() : Listener {
    private var status : Boolean? = false

    fun setModeOn() {
        this.status = true
        return
    }

    fun setModeOff() {
        this.status = false
        return
    }

    fun getStatus(): Boolean? {
        return status
    }

    @EventHandler
    fun rootPlaceEvent(e : BlockPlaceEvent) {
        if (this.status == false || this.status == null) {
            println(status)
            return
        }

        if (e.block.type != Material.CHEST) {
            return
        }

        Main.instance?.let {
            println("3")
            val locStr: String =
                e.block.location.blockX.toString() + "," + e.block.location.blockY.toString() + "," + e.block.location.blockZ.toString()
            val sec: MutableList<String> = it.config.getStringList("world.rootingLocations")

            if (sec.size > 50) {
                e.player.sendMessage("" + ChatColor.RED + ChatColor.BOLD + "상자 위치는 50개 이하여야 합니다.")
                return
            }

            for (k in sec) {
                if (k == locStr) {
                    e.player.sendMessage("" + ChatColor.RED + ChatColor.BOLD + "이미 지정되어 있는 상자 위치입니다.")
                    return
                }
            }

            sec.add(locStr)
            it.config.set("world.rootingLocations", sec)
            it.saveConfig()
            e.player.sendMessage("상자 위치를 저장했습니다.")
            return
        }
    }
}