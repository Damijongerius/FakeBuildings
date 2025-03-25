package com.dami.fakeBuildings;

import com.dami.Helpers.TypeHelper;
import com.dami.fakeBuildings.Commands.BuildingSchematicCommand;
import com.dami.fakeBuildings.FakeBuilding.FakeBuildingManager;
import com.dami.fakeBuildings.Listeners.DoorCreateClicksListener;
import com.dami.fakeBuildings.Listeners.FakeDoorListener;
import com.dami.fakeBuildings.Serializer.BukkitVectorSerializer;
import com.dami.handlers.SaveHandler;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public final class FakeBuildings extends JavaPlugin {

    private final SaveHandler saveHandler = new SaveHandler();

    @Override
    public void onEnable() {

        saveDefaultConfig();

        loadSaveHandler();

        new BuildingSchematicCommand(this, saveHandler);

        new FakeDoorListener(this);

        new DoorCreateClicksListener(this);

        FakeBuildingManager.loadConfigList(saveHandler);

        TypeHelper.addSerializer(Vector.class, new BukkitVectorSerializer());
        TypeHelper.addSerializer(Location.class, new BukkitVectorSerializer());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void loadSaveHandler(){
        saveHandler.AddYamlHandler(getDataPath().toString());
    }

}
