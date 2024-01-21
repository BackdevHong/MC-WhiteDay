package org.server.whiteday

import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    companion object{
        var instance : Main? = null
    }
    override fun onEnable() {
        instance = this

        // Plugin startup logic
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
