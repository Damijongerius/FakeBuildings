package com.dami.fakeBuildings.FakeBuilding;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

public class Building {

    private String world;
    private Vector center;
    private Vector localSpawnPosition;

    // Rotation
    private float pitch;
    private float yaw;

    private String permission;

    private String buildingName;

    private Door exitDoor;

    public Building(String world, Vector center, Vector localSpawnPosition, float pitch, float yaw, String permission, String buildingName) {
        this.world = world;
        this.center = center;
        this.localSpawnPosition = localSpawnPosition;
        this.pitch = pitch;
        this.yaw = yaw;
        this.permission = permission;
        this.buildingName = buildingName;
    }

    public Building() {
    }

    public Location getCenterLocation(){

        World worldClass = Bukkit.getWorld(world);

        if(worldClass == null){
            return null;
        }

        return center.toLocation(worldClass);
    }

    public Location getSpawnLocation(){

        World worldClass = Bukkit.getWorld(world);

        if(worldClass == null){
            return null;
        }

        Vector spawnRotation = new Vector(yaw, pitch, 0);

        return center.clone().add(localSpawnPosition).toLocation(worldClass).setDirection(spawnRotation);
    }

    public String getPermission() {
        return permission;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public Door getExitDoor() {
        return exitDoor;
    }

    public Map<String, Object> saveToYaml() {
        Map<String, Object> map = new HashMap<>();

        map.put("world", world);
        Map<String, Object> saveCenter = new HashMap<>();
        saveCenter.put("x", this.center.getX());
        saveCenter.put("y", this.center.getY());
        saveCenter.put("z", this.center.getZ());

        map.put("center", saveCenter);

        Map<String, Object> saveSpawnPoint = new HashMap<>();
        saveSpawnPoint.put("x", this.localSpawnPosition.getX());
        saveSpawnPoint.put("y", this.localSpawnPosition.getY());
        saveSpawnPoint.put("z", this.localSpawnPosition.getZ());

        map.put("localSpawnPosition", saveSpawnPoint);

        map.put("pitch", pitch);
        map.put("yaw", yaw);

        map.put("permission", permission);
        map.put("buildingName", buildingName);

        return map;
    }

    public void loadFromConfig(ConfigurationSection config) {
        world = config.getString("world");

        ConfigurationSection centerSection = config.getConfigurationSection("center");
        center = new Vector(centerSection.getDouble("x"), centerSection.getDouble("y"), centerSection.getDouble("z"));

        ConfigurationSection localSpawnPositionSection = config.getConfigurationSection("localSpawnPosition");
        localSpawnPosition = new Vector(localSpawnPositionSection.getDouble("x"), localSpawnPositionSection.getDouble("y"), localSpawnPositionSection.getDouble("z"));

        pitch = (float) config.getDouble("pitch");
        yaw = (float) config.getDouble("yaw");

        permission =  config.getString("permission");
        buildingName = config.getString("buildingName");
    }

    public void loadFromConfig(Map<String,Object> config){
        world = (String) config.get("world");

        Map<String,Double> centerMap = (Map<String, Double>) config.get("center");
        center = new Vector(centerMap.get("x"), centerMap.get("y"), centerMap.get("z"));

        Map<String,Double> localSpawnPositionMap = (Map<String, Double>) config.get("localSpawnPosition");
        localSpawnPosition = new Vector(localSpawnPositionMap.get("x"), localSpawnPositionMap.get("y"), localSpawnPositionMap.get("z"));

        pitch = ((Double) config.get("pitch")).floatValue();
        yaw = ((Double) config.get("yaw")).floatValue();

        permission = (String) config.get("permission");
        buildingName = (String) config.get("buildingName");
    }
}
