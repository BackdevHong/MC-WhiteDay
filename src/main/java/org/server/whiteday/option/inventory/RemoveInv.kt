package org.server.whiteday.option.inventory

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.server.whiteday.Main
import java.io.File

open class RemoveInv : Listener {
    private var inv : Inventory? = null
    private var type : String? = null

    init {
        inv = Bukkit.getServer().createInventory(null, 54, "삭제 GUI")
        type = null
    }

    fun resetItems(type: String?) {
        this.type = type
        initializeItems()
    }

    private fun initializeItems() {
        if (type == null) {
            return
        }

        inv?.clear()

        when (type) {
            "위치" -> {
                Main.instance!!.config.load(File(Main.instance!!.dataFolder, "config.yml"))
                Main.instance?.let {
                    val sec : MutableList<String> = it.config.getStringList("world.randomLocations")
                    sec.forEachIndexed { idx, value ->
                        inv?.addItem(createGuiItem(value, idx.toString()))
                    }
                }
            }
            "상자" -> {
                Main.instance!!.config.load(File(Main.instance!!.dataFolder, "config.yml"))
                Main.instance?.let {
                    val sec : MutableList<String> = it.config.getStringList("world.rootingLocations")
                    sec.forEachIndexed { idx, value ->
                        inv?.addItem(createGuiItem(value, idx.toString()))
                    }
                }
            }
        }
    }

    private fun createGuiItem(name:String, lore:String): ItemStack {
        val item = ItemStack(Material.PAPER, 1)
        val itemMeta : ItemMeta? = item.itemMeta

        itemMeta?.setDisplayName(name)
        itemMeta?.lore = arrayListOf(lore)

        item.setItemMeta(itemMeta)
        return item
    }

    fun openInventory(ent: HumanEntity) {
        ent.openInventory(inv!!)
    }

    @EventHandler
    fun onInventoryClick(e: InventoryClickEvent) {
        if (e.whoClicked.openInventory.topInventory.holder?.equals(inv?.holder) == false) return

        if (e.clickedInventory == e.whoClicked.openInventory.topInventory || e.isShiftClick) {
            if ((e.currentItem ?: ItemStack(Material.AIR)).type.isAir && !((e.cursor ?: ItemStack(Material.AIR)).type.isAir)) {
                e.whoClicked.sendMessage("" + ChatColor.RED + ChatColor.BOLD + "설정 메뉴에 아이템을 넣을 수 없습니다.")
            }
            e.isCancelled = true
        }

        if (e.clickedInventory?.type != InventoryType.CHEST) return

        e.isCancelled = true
        val clickedItem = e.getCurrentItem()

        if (clickedItem == null || clickedItem.type.isAir) return
        val p = e.whoClicked as Player

        when (type) {
            "위치" -> {
                Main.instance!!.config.load(File(Main.instance!!.dataFolder, "config.yml"))
                Main.instance?.let {
                    val sec : MutableList<String> = it.config.getStringList("world.randomLocations")
                    val index : Int = clickedItem.itemMeta?.lore.toString().replace("[^0-9]".toRegex(), "").toInt()
                    sec.removeAt(index)
                    it.config.set("world.randomLocations", sec)
                    it.saveConfig()
                }
                p.sendMessage(clickedItem.itemMeta!!.displayName + " 좌표가 삭제되었습니다.")
                p.closeInventory()
            }

            "상자" -> {
                Main.instance!!.config.load(File(Main.instance!!.dataFolder, "config.yml"))

                Main.instance?.let {
                    val sec : MutableList<String> = it.config.getStringList("world.rootingLocations")
                    val index : Int = clickedItem.itemMeta?.lore.toString().replace("[^0-9]".toRegex(), "").toInt()
                    val loc = Location(it.server.getWorld("world"), clickedItem.itemMeta!!.displayName.split(",")[0].toDouble(), clickedItem.itemMeta!!.displayName.split(",")[1].toDouble(), clickedItem.itemMeta!!.displayName.split(",")[2].toDouble())
                    it.server.getWorld("world")!!.getBlockAt(loc).type = Material.AIR
                    sec.removeAt(index)
                    it.config.set("world.rootingLocations", sec)
                    it.saveConfig()
                }

                p.sendMessage(clickedItem.itemMeta!!.displayName + " 좌표에 있는 상자가 삭제되었습니다.")
                p.closeInventory()
            }
        }
    }

    @EventHandler
    fun onInventoryDrag(e: InventoryDragEvent) {
        if (e.inventory.holder == inv?.holder) {
            for (k in e.rawSlots) {
                if (k <= 54){
                    e.isCancelled = true
                    e.whoClicked.sendMessage("" + ChatColor.RED + ChatColor.BOLD + "설정 메뉴에 아이템을 넣을 수 없습니다.")
                    break
                }
            }
        }
    }
}