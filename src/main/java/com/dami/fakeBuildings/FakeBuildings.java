package com.dami.fakeBuildings;

import com.dami.fakeBuildings.ConfigReload.ConfigManager;
import com.dami.fakeBuildings.FakeBuilding.FakeBuildingManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class FakeBuildings extends JavaPlugin {

    //private final ConfigManager configManager = new ConfigManager();

    @Override
    public void onEnable() {
        // Plugin startup logic
        FakeBuildingManager.loadConfigList(this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        FakeBuildingManager.saveConfigList();
        //configManager.saveConfigs();
    }

    public void initializeConfigs(){


        //configManager.reloadConfigs();
    }

    //public ConfigManager getConfigManager() {return configManager;}
}
