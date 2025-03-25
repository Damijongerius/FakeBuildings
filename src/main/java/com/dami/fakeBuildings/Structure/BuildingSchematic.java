package com.dami.fakeBuildings.Structure;

import com.dami.Static.MaxInstanceCount;
import com.dami.Static.SavingTypes;
import com.dami.common.Annotations.PrimaryKey;
import com.dami.common.Annotations.Property;
import com.dami.common.IASCComponent;
import com.dami.common.ISaveProperties;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Map;

@Property(name="buildingSchematic")
public class BuildingSchematic implements IASCComponent {

    private static final ISaveProperties properties;

    @PrimaryKey
    @Property(name="name")
    public String name;

    @Property(name="metrics")
    public Vector metrics;

    @Property(name="blocks")
    public List<Integer> blocks;

    @Property(name="npcLocations")
    public Map<String,Vector> npcLocations;

    @Property(name="door")
    public Door door;

    static {
        properties = new ISaveProperties() {
            @Override
            public SavingTypes preferredSavingType() {
                return SavingTypes.YML;
            }

            @Override
            public MaxInstanceCount maxSavingCount() {
                return MaxInstanceCount.UNLIMITED;
            }
        };
    }

    public BuildingSchematic(String name, Vector metrics, List<Integer> blocks) {
        this.metrics = metrics;
        this.blocks = blocks;
        this.name = name;
    }


    @Override
    public ISaveProperties getSaveProperties() {
        return properties;
    }

    @Override
    public String loggerIdentifier() {
        return "buildingSchematic?";
    }
}
