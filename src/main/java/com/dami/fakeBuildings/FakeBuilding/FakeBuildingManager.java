package com.dami.fakeBuildings.FakeBuilding;

import com.dami.Static.MaxInstanceCount;
import com.dami.Static.SavingTypes;
import com.dami.common.Annotations.PrimaryKey;
import com.dami.common.Annotations.Property;
import com.dami.common.IASCComponent;
import com.dami.common.ISaveProperties;
import com.dami.fakeBuildings.Structure.Building;
import com.dami.fakeBuildings.Gui.BuildingListGUI;
import com.dami.fakeBuildings.Structure.Door;
import com.dami.handlers.SaveHandler;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@Property(name = "fakeBuildingManager")
public class FakeBuildingManager implements IASCComponent {

    private static final List<FakeBuildingManager> fakeBuildingManagers = new ArrayList<>();

    private static final ISaveProperties pr = new ISaveProperties() {
        @Override
        public SavingTypes preferredSavingType() {
            return SavingTypes.YML;
        }

        @Override
        public MaxInstanceCount maxSavingCount() {
            return MaxInstanceCount.UNLIMITED;
        }
    };

    protected BuildingListGUI buildingListGUI;

    @Property(name = "schematic")
    public String schematicName;

    @Property(name = "buildings")
    public final List<Building> buildings = new ArrayList<>();

    @PrimaryKey
    @Property(name = "buildingName")
    public String buildingName;

    @Property(name = "door")
    public Door door;

    public FakeBuildingManager(String buildingSchematic) {
        this.schematicName = buildingSchematic;

        fakeBuildingManagers.add(this);
    }

    public FakeBuildingManager() {
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

    public void playerClickedDoor(Player player){
        buildingListGUI.openGui(player,0);
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
            if(fakeBuildingManager.door.isInBounds(block.getLocation().toVector())){
                return fakeBuildingManager;
            }
        }
        return null;
    }

    public static void loadConfigList(SaveHandler saveHandler){
        IASCComponent[] components = saveHandler.loadAll(FakeBuildingManager.class);

        if(components == null){
            return;
        }

        for(IASCComponent component : components){
            FakeBuildingManager fakeBuildingManager = (FakeBuildingManager) component;
            fakeBuildingManagers.add(fakeBuildingManager);
        }
    }

    @Override
    public ISaveProperties getSaveProperties() {
        return pr;
    }

    @Override
    public String loggerIdentifier() {
        return "fakeBuildingManager";
    }
}
