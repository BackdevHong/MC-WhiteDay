package org.server.whiteday

object EventManager {
    fun registerEvents() {
        Main.instance?.let {
            it.server.pluginManager.run{

            }
        }
    }
}