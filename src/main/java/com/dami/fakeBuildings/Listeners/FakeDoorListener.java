package com.dami.fakeBuildings.Listeners;

import com.dami.fakeBuildings.FakeBuilding.FakeBuildingManager;
import com.dami.handlers.SaveHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class FakeDoorListener implements Listener {

    public FakeDoorListener(@NotNull JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void checkDoorClick(@NotNull PlayerInteractEvent event){
        if(event.getClickedBlock() == null) return;

        if(event.getAction() != org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK) return;

        FakeBuildingManager manager = FakeBuildingManager.meetsCriteria(event.getClickedBlock());

        if(manager != null){
            manager.PlayerClickedDoor(event.getPlayer());
        }
    }
}
