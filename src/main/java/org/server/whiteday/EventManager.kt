package org.server.whiteday

import org.server.whiteday.option.inventory.RemoveInv
import org.server.whiteday.option.root.Root

object EventManager {
    val root = Root()
    val removeInv = RemoveInv()
    fun registerEvents() {
        Main.instance?.let {
            it.server.pluginManager.run{
                registerEvents(removeInv, it)
                registerEvents(root, it)
                println("등록")
            }
        }
    }
}