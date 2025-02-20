package com.dami.fakeBuildings.Gui;

import com.dami.fakeBuildings.FakeBuilding.FakeBuildingManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class BuildingListGUI implements Listener {

    private final FakeBuildingManager fakeBuildingManager;
    private final String buildingName;

    public BuildingListGUI(JavaPlugin plugin, FakeBuildingManager fakeBuildingManager){

        this.fakeBuildingManager = fakeBuildingManager;
        this.buildingName = fakeBuildingManager.getBuildingName();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void openGui(Player player, int page){

        Component title = Component.text("Enter a " + buildingName + "(" + page + ")");

        Inventory inventory = Bukkit.createInventory(player, 57, title);

        for(int i = page * 49; i < 49 * page + 1; i++){
            if(i >= fakeBuildingManager.getBuildingNames().size()) break;

            ItemStack item = displayItem(fakeBuildingManager.getBuildingNames().get(i));
            inventory.setItem(i, item);
        }

        if(page > 0){
            ItemStack previous = new ItemStack(Material.ARROW);
            ItemMeta meta = previous.getItemMeta();
            meta.itemName(Component.text("Previous"));
            previous.setItemMeta(meta);
            inventory.setItem(49, previous);
        }

        if(fakeBuildingManager.getBuildingNames().size() > 49 * (page + 1)){
            ItemStack next = new ItemStack(Material.ARROW);
            ItemMeta meta = next.getItemMeta();
            meta.itemName(Component.text("Next"));
            next.setItemMeta(meta);
            inventory.setItem(53, next);
        }

        player.openInventory(inventory);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){

        if(event.getCurrentItem().getItemMeta().itemName().equals(Component.text("Next"))){

            openGui((Player) event.getWhoClicked(), 1);
        } else if(event.getCurrentItem().getItemMeta().itemName().equals(Component.text("Previous"))){
            openGui((Player) event.getWhoClicked(), -1);
        } else {
            // Open the building
            fakeBuildingManager.teleportPlayerToBuilding((Player) event.getWhoClicked(),event.getCurrentItem().getItemMeta().itemName().examinableName());
        }
    }

    private ItemStack displayItem(String buildingName){

        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta meta = item.getItemMeta();

        Component displayName = Component.text(buildingName);

        meta.itemName(displayName);

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        item.setItemMeta(meta);

        return item;
    }
}

