package com.dami.fakeBuildings.Serializer;

import com.dami.common.IASCComponent;
import com.dami.common.ISerializer;
import org.bukkit.Location;
import org.yaml.snakeyaml.Yaml;

import java.util.HashMap;

public class BukkitLocationSerializer implements ISerializer {

    @Override
    public String yamlSerialize(Object o) {

        Location location = (Location) o;

        Yaml yaml = new Yaml();

        return yaml.dump(location.serialize());

    }

    @Override
    public Object yamlDeserialize(Object o) {

        String serialized = (String) o;

        Yaml yaml = new Yaml();

        HashMap<String, Object> map = yaml.load(serialized);

        return Location.deserialize(map);

    }

    @Override
    public IASCComponent sqlSerialize(Object o) {
        return null; //ignore
    }

    @Override
    public Object sqlDeserialize(IASCComponent iascComponent) {
        return null; //ignore
    }
}
