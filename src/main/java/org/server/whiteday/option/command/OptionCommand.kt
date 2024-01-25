package org.server.whiteday.option.command

import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.server.whiteday.EventManager
import org.server.whiteday.Main
import org.server.whiteday.option.root.Root
import org.server.whiteday.utils.CheckPermission
import java.io.File

object OptionCommand : CommandExecutor{
    private var root : Root? = null
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        when {
            !CheckPermission.checkPermission(sender) -> {
                sender.sendMessage("오피 권한을 가진 유저만 사용 가능한 명령어입니다.")
                return false
            }
            args.isEmpty() -> {
                sender.sendMessage("" + ChatColor.BLUE + "=========== WHITE_DAY OPTION COMMAND =============")
                sender.sendMessage("[WHITE_DAY] /option randomLocation add - 게임 내 랜덤 스폰 위치를 추가합니다.")
                sender.sendMessage("[WHITE_DAY] /option randomLocation remove - 게임 내 랜덤 스폰 위치를 제거합니다.")
                sender.sendMessage("" + ChatColor.BLUE + "================================================")
                sender.sendMessage("[WHITE_DAY] /option victimLocation set - 게임 내 술래 스폰 위치를 지정합니다.")
                sender.sendMessage("" + ChatColor.BLUE + "================================================")
                sender.sendMessage("[WHITE_DAY] /option jailLocation set - 게임 내 감옥 위치를 지정합니다.")
                sender.sendMessage("" + ChatColor.BLUE + "================================================")
                sender.sendMessage("[WHITE_DAY] /option rootingLocation on - 게임 내 상자 위치 지정 모드를 킵니다.")
                sender.sendMessage("[WHITE_DAY] /option rootingLocation off - 게임 내 상자 위치 지정 모드를 끕니다.")
                sender.sendMessage("[WHITE_DAY] /option rootingLocation remove - 게임 내 상자 위치를 제거합니다.")
                sender.sendMessage("" + ChatColor.BLUE + "================================================")
                return true
            }
            args[0] == "randomLocation" -> {
                when {
                    args.size < 2 -> {
                        sender.sendMessage("add, remove 둘 중 하나를 골라주세요.")
                        return true
                    }

                    args[1] == "add" -> {
                        val player : Player = sender as Player
                        Main.instance!!.config.load(File(Main.instance!!.dataFolder, "config.yml"))
                        val location :Location = player.location

                        Main.instance?.let {
                            val sec : MutableList<String> = it.config.getStringList("world.randomLocations")
                            if (sec.size > 50) {
                                sender.sendMessage("" + ChatColor.RED + ChatColor.BOLD + "랜덤 스폰 위치는 50개 이하여야 합니다.")
                                return false
                            }

                            val locStr : String = location.block.x.toString() + "," + location.block.y.toString() + "," + location.block.z.toString()

                            println(locStr)
                            for (k in sec) {
                                if (k == locStr) {
                                    sender.sendMessage("" + ChatColor.RED + ChatColor.BOLD + "똑같은 곳에 랜덤 스폰 위치를 지정할 수 없습니다.")
                                    return false
                                }
                            }

                            sec.add(locStr)
                            it.config.set("world.randomLocations", sec)
                            it.saveConfig()
                        }
                        sender.sendMessage("현재 위치가 랜덤 스폰 위치에 추가되었습니다.")
                        return true
                    }

                    args[1] == "remove" -> {
                        val inv = EventManager.removeInv
                        inv.resetItems("위치")
                        inv.openInventory(sender as Player)
                        return true
                    }
                }
                return true
            }
            args[0] == "victimLocation" -> {
                when {
                    args.size < 2 -> {
                        sender.sendMessage("뒤에 set을 적어주세요!")
                        return true
                    }

                    args[1] == "set" -> {
                        val player : Player = sender as Player
                        Main.instance!!.config.load(File(Main.instance!!.dataFolder, "config.yml"))
                        val location :Location = player.location

                        Main.instance?.let {
                            val locStr : String = location.block.x.toString() + "," + location.block.y.toString() + "," + location.block.z.toString()

                            if (locStr == it.config.getString("world.victimLocation")) {
                                sender.sendMessage("" + ChatColor.RED + ChatColor.BOLD + "똑같은 곳에 술래 스폰 위치를 지정할 수 없습니다.")
                                return false
                            }
                            it.config.set("world.victimLocation", locStr)
                            it.saveConfig()
                        }
                        sender.sendMessage("현재 위치를 술래 스폰 위치로 설정하였습니다.")
                        return true
                    }
                }
            }
            args[0] == "jailLocation" -> {
                when {
                    args.size < 2 -> {
                        sender.sendMessage("뒤에 set을 적어주세요!")
                        return true
                    }

                    args[1] == "set" -> {
                        val player : Player = sender as Player
                        Main.instance!!.config.load(File(Main.instance!!.dataFolder, "config.yml"))
                        val location :Location = player.location

                        Main.instance?.let {
                            val locStr : String = location.block.x.toString() + "," + location.block.y.toString() + "," + location.block.z.toString()

                            if (locStr == it.config.getString("world.jailLocation")) {
                                sender.sendMessage("" + ChatColor.RED + ChatColor.BOLD + "똑같은 곳에 감옥 위치를 지정할 수 없습니다.")
                                return false
                            }
                            it.config.set("world.jailLocation", locStr)
                            it.saveConfig()
                        }
                        sender.sendMessage("현재 위치를 감옥 위치로 설정하였습니다.")
                        return true
                    }
                }
            }
            args[0] == "rootingLocation" -> {
                when {
                    args.size < 2 -> {
                        sender.sendMessage("뒤에 on, off, remove을 적어주세요!")
                        return true
                    }

                    args[1] == "on" -> {
                        Main.instance!!.config.load(File(Main.instance!!.dataFolder, "config.yml"))

                        if (root != null) {
                            if (root!!.getStatus() == true) {
                                sender.sendMessage("이미 상자 설치 모드가 켜져있습니다.")
                                return false
                            }
                            root!!.setModeOn()
                            sender.sendMessage("상자 설치 모드가 켜졌습니다.")
                            return true
                        }
                        root = EventManager.root
                        root!!.setModeOn()
                        sender.sendMessage("상자 설치 모드가 켜졌습니다.")
                        return true
                    }

                    args[1] == "off" -> {
                        Main.instance!!.config.load(File(Main.instance!!.dataFolder, "config.yml"))
                        if (root != null) {
                            if (!(root!!.getStatus())!!) {
                                sender.sendMessage("이미 상자 설치 모드가 꺼져있습니다.")
                                return false
                            }
                            root!!.setModeOff()
                            sender.sendMessage("상자 설치 모드가 꺼졌습니다.")
                            return true
                        }
                        sender.sendMessage("이미 상자 설치 모드가 꺼져있습니다.")
                        return true
                    }

                    args[1] == "remove" -> {
                        val inv = EventManager.removeInv
                        inv.resetItems("상자")
                        inv.openInventory(sender as Player)
                        return true
                    }
                }
            }
        }

        return false
    }
}
