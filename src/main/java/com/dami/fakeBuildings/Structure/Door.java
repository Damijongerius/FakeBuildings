package com.dami.fakeBuildings.Structure;

import com.dami.Static.MaxInstanceCount;
import com.dami.Static.SavingTypes;
import com.dami.common.Annotations.Property;
import com.dami.common.IASCComponent;
import com.dami.common.ISaveProperties;
import org.bukkit.Material;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

@Property(name = "door")
public class Door implements IASCComponent {

    private static final ISaveProperties properties;

    @Property(name = "start")
    public Vector start;

    @Property(name = "end")
    public Vector end;

    @Property(name = "doorMaterials")
    public List<Material> doorMaterials = new ArrayList<>();

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

    public Door( Vector start, Vector end, List<Material> doorMaterials) {
        this.start = start;
        this.end = end;
        this.doorMaterials = doorMaterials;
    }

    public Door() {
    }

    public boolean isInBounds(Vector location){
        return location.getX() >= start.getX() && location.getX() <= end.getX() &&
                location.getY() >= start.getY() && location.getY() <= end.getY() &&
                location.getZ() >= start.getZ() && location.getZ() <= end.getZ();
    }

    @Override
    public ISaveProperties getSaveProperties() {
        return properties;
    }

    @Override
    public String loggerIdentifier() {
        return "Door";
    }
}
