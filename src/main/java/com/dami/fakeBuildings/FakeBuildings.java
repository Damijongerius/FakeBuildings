package com.dami.fakeBuildings;

import com.dami.fakeBuildings.Commands.BuildingSchematicCommand;
import com.dami.fakeBuildings.FakeBuilding.FakeBuildingManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class FakeBuildings extends JavaPlugin {


    @Override
    public void onEnable() {


        // Plugin startup logic
        FakeBuildingManager.loadConfigList(this);

        new BuildingSchematicCommand(this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        FakeBuildingManager.saveConfigList();
    }

}
