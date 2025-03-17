package com.dami.fakeBuildings.FakeBuilding;

import com.dami.fakeBuildings.BuildingInitializations.BuildingSchematic;
import com.dami.fakeBuildings.Gui.BuildingListGUI;
import com.dami.fakeBuildings.yml.Savable;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FakeBuildingManager extends Savable {

    private static final List<FakeBuildingManager> fakeBuildingManagers = new ArrayList<>();

    private static JavaPlugin plugin;

    private BuildingSchematic schematic;

    protected BuildingListGUI buildingListGUI;

    private final List<Building> buildings = new ArrayList<>();

    private String buildingName;

    private Door door;


    public FakeBuildingManager(BuildingSchematic buildingSchematic) {
        this.schematic = buildingSchematic;
        fakeBuildingManagers.add(this);
    }

    public String getBuildingName() {
        return buildingName;
    }

    public List<String> getBuildingNames() {
        List<String> buildingNames = new ArrayList<>();
        for (Building building : buildings) {
            buildingNames.add(building.getBuildingName());
        }
        return buildingNames;
    }

    public void teleportPlayerToBuilding(Player player, String buildingName){
        for(Building building : buildings){
            if(building.getBuildingName().equals(buildingName)){
                player.teleport(building.getCenterLocation());
                return;
            }
        }
    }

    public void PlayerClickedDoor(Player player){
        buildingListGUI.openGui(player,0);
    }


    @Override
    public Map<String, Object> saveToYaml() {
        Map<String, Object> map = new HashMap<>();

        schematic.saveToFileAsync("schematics/" + buildingName);

        map.put("buildingName", buildingName);

        List<Map<String, Object>> buildingList = new ArrayList<>();

        for (Building building : buildings) {
            buildingList.add(building.saveToYaml());
        }

        map.put("buildings", buildingList);

        map.put("door", door.saveToYaml());

        return map;
    }

    @Override
    public void loadFromConfig(ConfigurationSection config) {
        buildingName = config.getString("buildingName");

        schematic = Savable.loadFromFile(BuildingSchematic.class, "schematics/" + buildingName);

        List<Map<String, Object>> buildingList = (List<Map<String, Object>>) config.getList("buildings");

        for (Map<String, Object> buildingMap : buildingList) {
            Building building = new Building();
            building.loadFromConfig(buildingMap);
            buildings.add(building);
        }

        door = new Door();
        door.loadFromConfig(config.getConfigurationSection("door"));
    }

    public static FakeBuildingManager getFakeBuildingManager(String buildingName) {
        for (FakeBuildingManager fakeBuildingManager : fakeBuildingManagers) {
            if (fakeBuildingManager.getBuildingName().equals(buildingName)) {
                return fakeBuildingManager;
            }
        }
        return null;
    }

    public static void addFakeBuildingManager(FakeBuildingManager fakeBuildingManager) {
        fakeBuildingManagers.add(fakeBuildingManager);
    }

    public static void removeFakeBuildingManager(FakeBuildingManager fakeBuildingManager) {
        fakeBuildingManagers.remove(fakeBuildingManager);
    }

    public static List<FakeBuildingManager> getFakeBuildingManagers() {
        return fakeBuildingManagers;
    }

    public static FakeBuildingManager meetsCriteria(Block block){
        for(FakeBuildingManager fakeBuildingManager : fakeBuildingManagers){
            if(fakeBuildingManager.door.isInBounds(block.getLocation())){
                return fakeBuildingManager;
            }
        }
        return null;
    }

    public static void saveConfigList(){
        List<String> buildingNames = new ArrayList<>();

        for(FakeBuildingManager fakeBuildingManager : fakeBuildingManagers){
            buildingNames.add(fakeBuildingManager.getBuildingName());
            fakeBuildingManager.saveToFileAsync(fakeBuildingManager.getBuildingName());
        }

        BuildingList buildingList = new BuildingList(buildingNames);
        buildingList.saveToFileAsync("buildingList");
    }

    public static void loadConfigList(JavaPlugin plugin){

        FakeBuildingManager.plugin = plugin;

        BuildingList buildingList = Savable.loadFromFile(BuildingList.class, "buildingList");

        assert buildingList != null;

        buildingList.fakeBuildingManagers.forEach(s -> {
            FakeBuildingManager fakeBuildingManager = Savable.loadFromFile(FakeBuildingManager.class, s);
            assert fakeBuildingManager != null;

            fakeBuildingManager.buildingListGUI = new BuildingListGUI(plugin,fakeBuildingManager);

            fakeBuildingManagers.add(fakeBuildingManager);
        });
    }

    public static class BuildingList extends Savable{

        private final List<String> fakeBuildingManagers;

        public BuildingList(List<String> fakeBuildingManagers) {
            this.fakeBuildingManagers = fakeBuildingManagers;
        }

        @Override
        public Map<String, Object> saveToYaml() {
            return Map.of("fakeBuildingManagers", fakeBuildingManagers);
        }

        @Override
        public void loadFromConfig(ConfigurationSection config) {
            List<String> temp = (List<String>) config.getList("fakeBuildingManagers");

            assert temp != null;
            for (String tempString : temp) {
                FakeBuildingManager.addFakeBuildingManager(FakeBuildingManager.getFakeBuildingManager(tempString));
            }
        }
    }
}
