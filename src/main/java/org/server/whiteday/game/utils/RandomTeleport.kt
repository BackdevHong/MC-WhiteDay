package org.server.whiteday.game.utils

import org.bukkit.Location
import org.bukkit.entity.Player
import org.server.whiteday.Main


object RandomTeleport {
    private var locationList: MutableList<String>? = null

    init {
        locationList = Main.instance!!.config.getStringList("world.randomLocations")
    }

    fun randomTP (player : Player) {
        val random = locationList!!.random().split(",")
        val randomLoc = Location(player.location.world, random[0].toDouble(), random[1].toDouble(), random[2].toDouble())
        player.teleport(randomLoc)
    }
}