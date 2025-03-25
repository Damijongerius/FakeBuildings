package com.dami.fakeBuildings.Serializer;

import com.dami.common.IASCComponent;
import com.dami.common.ISerializer;
import org.bukkit.util.Vector;
import org.yaml.snakeyaml.Yaml;

import java.util.HashMap;

public class BukkitVectorSerializer implements ISerializer {

    @Override
    public String yamlSerialize(Object o) {

        HashMap<String, Object> map = new HashMap<>();
        Vector vector = (Vector) o;

        map.put("x", vector.getX());
        map.put("y", vector.getY());
        map.put("z", vector.getZ());

        Yaml yaml = new Yaml();

        return yaml.dump(map);
    }

    @Override
    public Object yamlDeserialize(Object o) {

        String serialized = (String) o;

        Yaml yaml = new Yaml();

        HashMap<String, Object> map = yaml.load(serialized);

        return new Vector((double) map.get("x"), (double) map.get("y"), (double) map.get("z"));
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
