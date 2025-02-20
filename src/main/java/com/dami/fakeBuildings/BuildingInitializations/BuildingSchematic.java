package com.dami.fakeBuildings.BuildingInitializations;

import com.dami.fakeBuildings.ConfigReload.Savable;
import com.dami.fakeBuildings.FakeBuilding.Door;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuildingSchematic extends Savable {

    private Vector metrics;

    private List<Integer> blocks;

    private Map<String,Vector> npcLocations;

    private List<Door> doors;

    public BuildingSchematic(Vector metrics, List<Integer> blocks) {
        this.metrics = metrics;
        this.blocks = blocks;
    }


    @Override
    public Map<String, Object> saveToYaml() {
        Map<String, Object> map = new HashMap<>();

        Map<String,Object> saveMetrics = new HashMap<>();
        saveMetrics.put("x", this.metrics.getX());
        saveMetrics.put("y", this.metrics.getY());
        saveMetrics.put("z", this.metrics.getZ());

        map.put("metrics", saveMetrics);

        Map<String,Object> saveNpcLocations = new HashMap<>();
        for (Map.Entry<String, Vector> entry : npcLocations.entrySet()) {
            Map<String,Object> saveNpcLocation = new HashMap<>();
            saveNpcLocation.put("x", entry.getValue().getX());
            saveNpcLocation.put("y", entry.getValue().getY());
            saveNpcLocation.put("z", entry.getValue().getZ());
            saveNpcLocations.put(entry.getKey(), saveNpcLocation);
        }

        map.put("npcLocations", saveNpcLocations);

        List<Map<String,Object>> saveDoors = new ArrayList<>();

        for(Door door : doors){
            saveDoors.add(door.saveToYaml());
        }

        map.put("blocks", blocks);

        return map;
    }

    @Override
    public void loadFromConfig(ConfigurationSection config) {
        ConfigurationSection metricsSection = config.getConfigurationSection("metrics");
        metrics = new Vector(metricsSection.getDouble("x"), metricsSection.getDouble("y"), metricsSection.getDouble("z"));

        ConfigurationSection npcLocationsSection = config.getConfigurationSection("npcLocations");
        npcLocations = new HashMap<>();
        for (String key : npcLocationsSection.getKeys(false)) {
            ConfigurationSection npcLocationSection = npcLocationsSection.getConfigurationSection(key);
            Vector npcLocation = new Vector(npcLocationSection.getDouble("x"), npcLocationSection.getDouble("y"), npcLocationSection.getDouble("z"));
            npcLocations.put(key, npcLocation);
        }

        blocks = config.getIntegerList("blocks");

        doors = new ArrayList<>();
        List<Map<String,Object>> doorsSection = (List<Map<String, Object>>) config.getList("doors");

        assert doorsSection != null;

        for(Map<String,Object> doorSection : doorsSection){
            Door door = new Door();
            door.loadFromConfig(doorSection);
            doors.add(door);
        }
    }

    @Override
    public void loadBaseConfig() {

    }
}
