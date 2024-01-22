package org.server.whiteday

import org.server.whiteday.option.inventory.RemoveInv

object EventManager {
    fun registerEvents() {
        Main.instance?.let {
            it.server.pluginManager.run{
                registerEvents(RemoveInv(), it)
            }
        }
    }
}