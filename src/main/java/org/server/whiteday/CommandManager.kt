package org.server.whiteday

import org.server.whiteday.game.command.GameCommand
import org.server.whiteday.game.command.GameTabComplater
import org.server.whiteday.option.command.OptionCommand
import org.server.whiteday.option.command.OptionTabComplater

object CommandManager {
    fun registerCommands(){
        Main.instance?.let {
            it.server.run {
                getPluginCommand("game")!!.tabCompleter = GameTabComplater
                getPluginCommand("game")!!.setExecutor(GameCommand)

                getPluginCommand("option")!!.tabCompleter = OptionTabComplater
                getPluginCommand("option")!!.setExecutor(OptionCommand)
            }
        }
    }
}