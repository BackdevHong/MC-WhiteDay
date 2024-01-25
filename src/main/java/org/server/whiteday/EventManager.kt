package org.server.whiteday

import org.server.whiteday.option.inventory.RemoveInv
import org.server.whiteday.option.root.Root

object EventManager {
    val root = Root()
    fun registerEvents() {
        Main.instance?.let {
            it.server.pluginManager.run{
                registerEvents(RemoveInv(), it)
                registerEvents(root, it)
                println("등록")
            }
        }
    }
}