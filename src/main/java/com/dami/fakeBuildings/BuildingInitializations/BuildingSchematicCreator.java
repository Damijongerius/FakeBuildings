package com.dami.fakeBuildings.BuildingInitializations;

import com.dami.fakeBuildings.Listeners.DoorCreateClicksListener;
import com.dami.fakeBuildings.Structure.BuildingSchematic;
import com.dami.fakeBuildings.Structure.Door;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.*;

public class BuildingSchematicCreator {

    private static final Map<UUID,PlayerBuildingSettings> playerBuildingSettings = new HashMap<>();

    private BuildingSchematicCreator() {
        throw new IllegalStateException("Utility class");
    }

    public static PlayerBuildingSettings getPlayerBuildingSettings(UUID uuid){
        return playerBuildingSettings.get(uuid);
    }

    public static void setPos1(UUID uuid, Location pos1){
        PlayerBuildingSettings settings = playerBuildingSettings.computeIfAbsent(uuid, k -> new PlayerBuildingSettings());

        settings.setPos1(pos1);
    }

    public static void setPos2(UUID uuid, Location pos2){
        PlayerBuildingSettings settings = playerBuildingSettings.computeIfAbsent(uuid, k -> new PlayerBuildingSettings());

        settings.setPos2(pos2);
    }

    public static void setBuildingName(UUID uuid, String buildingName){
        PlayerBuildingSettings settings = playerBuildingSettings.computeIfAbsent(uuid, k -> new PlayerBuildingSettings());

        settings.setBuildingName(buildingName);
    }

    public static void setDoorSettings(UUID uuid, DoorCreateClicksListener.PlayerDoorSettings doorSettings){
        PlayerBuildingSettings settings = playerBuildingSettings.computeIfAbsent(uuid, k -> new PlayerBuildingSettings());

        settings.doorSettings = doorSettings;
    }

    public static BuildingSchematic createBuildingSchematic(UUID uuid){
        PlayerBuildingSettings settings = playerBuildingSettings.get(uuid);

        if(settings == null) return null;

        if(!settings.isValid()) return null;

        List<Integer> blocks = getBlocksInArea(settings.pos1, settings.pos2);

        Vector size = new Vector(
                Math.abs(settings.pos1.getBlockX() - settings.pos2.getBlockX()),
                Math.abs(settings.pos1.getBlockY() - settings.pos2.getBlockY()),
                Math.abs(settings.pos1.getBlockZ() - settings.pos2.getBlockZ())
        );

        BuildingSchematic schematic = new BuildingSchematic(settings.buildingName,size,blocks);

        if(settings.doorSettings != null){
            Vector start = settings.doorSettings.doorPos1;
            Vector end = settings.doorSettings.doorpos2;
            schematic.door = new Door(start,end,settings.doorSettings.doorMaterials);
        }

        return schematic;
    }

    private static List<Integer> getBlocksInArea(Location pos1, Location pos2){
        List<Integer> blocks = new ArrayList<>();

        int minX = Math.min(pos1.getBlockX(), pos2.getBlockX());
        int minY = Math.min(pos1.getBlockY(), pos2.getBlockY());
        int minZ = Math.min(pos1.getBlockZ(), pos2.getBlockZ());

        int maxX = Math.max(pos1.getBlockX(), pos2.getBlockX());
        int maxY = Math.max(pos1.getBlockY(), pos2.getBlockY());
        int maxZ = Math.max(pos1.getBlockZ(), pos2.getBlockZ());

        for(int x = minX; x <= maxX; x++){
            for(int y = minY; y <= maxY; y++){
                for(int z = minZ; z <= maxZ; z++){
                    blocks.add(pos1.getWorld().getBlockData(x,y,z).hashCode());

                    //Bukkit.createBlockData(pos1.getWorld().getBlockData(x,y,z).hashCode() + "");
                }
            }
        }

        return blocks;
    }

    public static class PlayerBuildingSettings{

        public Location pos1;

        public Location pos2;

        public String buildingName;

        public DoorCreateClicksListener.PlayerDoorSettings doorSettings;

        public void setPos1(Location pos1) {
            this.pos1 = pos1;
        }

        public void setPos2(Location pos2) {
            this.pos2 = pos2;
        }

        public void setBuildingName(String buildingName) {
            this.buildingName = buildingName;
        }

        public boolean isComplete(){
            return pos1 != null && pos2 != null && buildingName != null;
        }

        public boolean isValid(){
            if(!isComplete()) return false;

            if(pos1.getWorld() != pos2.getWorld()) return false;

            return true;
        }
    }
}
