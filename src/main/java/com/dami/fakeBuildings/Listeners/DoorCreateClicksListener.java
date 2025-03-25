package com.dami.fakeBuildings.Listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class DoorCreateClicksListener implements Listener {

    private static DoorCreateClicksListener instance;

    private final Map<Player,Consumer<PlayerDoorSettings>> players = new HashMap<>();

    private final Map<Player,PlayerDoorSettings> playerDoorSettings = new HashMap<>();

    public DoorCreateClicksListener(JavaPlugin plugin){

        if(instance != null){
            return;
        }

        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        instance = this;
    }

    @EventHandler
    public void checkDoorClicks(PlayerInteractEvent event){
        if(!players.containsKey(event.getPlayer())){
            return;
        }

        if(event.getClickedBlock() == null){
            return;
        }

        event.setCancelled(true);

        PlayerDoorSettings settings = playerDoorSettings.get(event.getPlayer());

        if (settings.doorPos1 == null) {
            settings.doorPos1 = event.getClickedBlock().getLocation().toVector();
            event.getPlayer().sendMessage("first door position set, Please click the second door position");
        } else {
            settings.doorpos2 = event.getClickedBlock().getLocation().toVector();

            Consumer<PlayerDoorSettings> callback = players.get(event.getPlayer());

            event.getPlayer().sendMessage("second door position set");

            callback.accept(settings);

            players.remove(event.getPlayer());
        }
    }

    public static void listenToPlayer(Player player, Consumer<PlayerDoorSettings> callback){
        if(instance == null){
            return;
        }

        instance.players.put(player,callback);

        instance.playerDoorSettings.put(player,new PlayerDoorSettings(player));
    }

    public static class PlayerDoorSettings {
        public Vector doorPos1;
        public Vector doorpos2;

        public List<Material> doorMaterials = new ArrayList<>();

        public Player player;

        public PlayerDoorSettings(Player player){
            this.player = player;
        }
    }
}
