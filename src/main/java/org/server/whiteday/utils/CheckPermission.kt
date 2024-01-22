package org.server.whiteday.utils

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object CheckPermission {
    fun checkPermission(sender: CommandSender): Boolean {
        return sender is Player && sender.isOp
    }
}