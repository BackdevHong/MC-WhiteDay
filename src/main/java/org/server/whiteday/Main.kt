package org.server.whiteday

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {

    companion object{
        var instance : Main? = null
        var customConfig : FileConfiguration? = null
    }

    override fun onEnable() {
        instance = this
        customConfig = this.config

        customConfig!!.addDefault("world.randomLocations", ArrayList<String>())
        customConfig!!.addDefault("world.rootingLocations", ArrayList<String>())
        customConfig!!.addDefault("world.victimLocation", "")
        customConfig!!.addDefault("world.jailLocation", "")
        customConfig!!.options().copyDefaults(true)
        saveConfig()

        CommandManager.registerCommands()
        EventManager.registerEvents()
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
