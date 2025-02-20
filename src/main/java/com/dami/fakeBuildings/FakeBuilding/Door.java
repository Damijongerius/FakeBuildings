package com.dami.fakeBuildings.FakeBuilding;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Door {

    private Location start;
    private Location end;

    private List<Material> doorMaterials = new ArrayList<>();

    public Door( Location start, Location end, List<Material> doorMaterials) {
        this.start = start;
        this.end = end;
        this.doorMaterials = doorMaterials;
    }

    public Door() {
    }

    public boolean isInBounds(Location location){
        return location.getX() >= start.getX() && location.getX() <= end.getX() &&
                location.getY() >= start.getY() && location.getY() <= end.getY() &&
                location.getZ() >= start.getZ() && location.getZ() <= end.getZ();
    }


    public Map<String, Object> saveToYaml() {
        Map<String,Object> map = new HashMap<>();

        map.put("Start", start.serialize());
        map.put("End", end.serialize());

        List<String> stringMaterials = new ArrayList<>();

        for(Material material : doorMaterials){
            stringMaterials.add(material.name());
        }

        map.put("doorMaterials", stringMaterials);

        return map;
    }

    public void loadFromConfig(Map<String, Object> config) {
        start = (Location) config.get("Start");
        end = (Location) config.get("End");

        List<String> stringMaterials = (List<String>) config.get("doorMaterials");

        doorMaterials = new ArrayList<>();

        for(String stringMaterial : stringMaterials){
            doorMaterials.add(Material.valueOf(stringMaterial));
        }
    }

    public void loadFromConfig(ConfigurationSection config) {
        start = Location.deserialize((Map<String, Object>) config.get("Start"));
        end = Location.deserialize((Map<String, Object>) config.get("End"));

        List<String> stringMaterials = (List<String>) config.getList("doorMaterials");

        doorMaterials = new ArrayList<>();

        assert stringMaterials != null;

        for(String stringMaterial : stringMaterials){
            doorMaterials.add(Material.valueOf(stringMaterial));
        }
    }

}
