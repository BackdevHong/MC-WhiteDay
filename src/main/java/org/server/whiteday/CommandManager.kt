package org.server.whiteday

import org.server.whiteday.game.command.GameCommand
import org.server.whiteday.game.command.GameTabComplater

object CommandManager {
    fun registerCommands(){
        Main.instance?.let {
            it.server.run {
                getPluginCommand("game")!!.tabCompleter = GameTabComplater
                getPluginCommand("game")!!.setExecutor(GameCommand)
            }
        }
    }
}