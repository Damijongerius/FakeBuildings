package com.dami.fakeBuildings.ConfigReload;

import com.dami.fakeBuildings.Utils.KeyValuePair;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;

public class ConfigManager extends IConfigReloader implements IReloadExecutor {

    private final Map<String, KeyValuePair<Savable,Class<? extends Savable>>> configs = new HashMap<>();

    private final Map<String, KeyValuePair<Savable,Class<? extends Savable>>> unReloadableConfigs = new HashMap<>();

    public ConfigManager(){
        super();

    }

    @Override
    public void reloadConfigs() {

        configs.forEach((name, config) -> {

            Class<? extends Savable> clazz = config.value();

            Savable newSave = Savable.loadFromFile(clazz, name);

            if(newSave == null){
                newSave = initializeClass(clazz);

                if(newSave == null){
                    return;
                }

                newSave.loadBaseConfig();
            }

            newSave.saveToFileAsync(name);

            configs.replace(name, KeyValuePair.of(newSave, clazz));
        });

        for (IConfigReloadListener listener : listeners) {
            listener.onConfigsReload(this);
        }

        unReloadableConfigs.forEach((name, config) -> config.key().saveToFileAsync(name));
    }

    @Override
    public void saveConfigs() {
        configs.forEach((name, config) -> config.key().saveToFileAsync(name));

        unReloadableConfigs.forEach((name, config) -> config.key().saveToFileAsync(name));
    }

    public Savable getConfig(String path){
        if(configs.containsKey(path)){
            return configs.get(path).key();
        }else if(unReloadableConfigs.containsKey(path)){
            return unReloadableConfigs.get(path).key();
        }

        Bukkit.getLogger().severe("Config not found: " + path);
        return null;
    }

    public void addConfig(Savable config, String path, Class<? extends Savable> clazz) {
        configs.put(path, KeyValuePair.of(config, clazz));
    }

    public void addConfig(Savable config, String path, Class<? extends Savable> clazz, boolean reloadable) {
        if(reloadable){
            addConfig(config, path, clazz);
            return;
        }

        unReloadableConfigs.put(path, KeyValuePair.of(config, clazz));
    }

    public void initializeConfig(String path, Class<? extends Savable> clazz){
        initializeConfig(path, clazz, true);
    }

    public void initializeConfig(String path, Class<? extends Savable> clazz, boolean reloadable){
        Savable savable = Savable.loadFromFile(clazz, path);

        if(savable == null){
            savable = initializeClass(clazz);

            if(savable == null){
                return;
            }

            savable.loadBaseConfig();
        }

        savable.saveToFileAsync(path);

        addConfig(savable, path, clazz, reloadable);
    }

    private Savable initializeClass(Class<? extends Savable> clazz){
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            Bukkit.getLogger().info("Error initializing class: " + clazz.getName());
        }

        return null;
    }
}
