package org.server.whiteday

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    val config : FileConfiguration = getConfig()
    companion object{
        var instance : Main? = null
    }
    override fun onEnable() {
        instance = this
        config.addDefault("", true)

        CommandManager.registerCommands()

        // Plugin startup logic
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
