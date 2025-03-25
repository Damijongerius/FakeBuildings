package com.dami.fakeBuildings.Structure;

import com.dami.Static.MaxInstanceCount;
import com.dami.Static.SavingTypes;
import com.dami.common.Annotations.Property;
import com.dami.common.IASCComponent;
import com.dami.common.ISaveProperties;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;


@Property(name = "building")
public class Building implements IASCComponent {

    private static final ISaveProperties properties;

    private String world;
    private Vector center;
    private Vector localSpawnPosition;

    // Rotation
    private float pitch;
    private float yaw;

    private String permission;

    private String buildingName;

    private Door exitDoor;

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

    public Building(String world, Vector center, Vector localSpawnPosition, float pitch, float yaw, String permission, String buildingName) {
        this.world = world;
        this.center = center;
        this.localSpawnPosition = localSpawnPosition;
        this.pitch = pitch;
        this.yaw = yaw;
        this.permission = permission;
        this.buildingName = buildingName;
    }

    public Building() {
    }

    public Location getCenterLocation(){

        World worldClass = Bukkit.getWorld(world);

        if(worldClass == null){
            return null;
        }

        return center.toLocation(worldClass);
    }

    public Location getSpawnLocation(){

        World worldClass = Bukkit.getWorld(world);

        if(worldClass == null){
            return null;
        }

        Vector spawnRotation = new Vector(yaw, pitch, 0);

        return center.clone().add(localSpawnPosition).toLocation(worldClass).setDirection(spawnRotation);
    }

    public String getPermission() {
        return permission;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public Door getExitDoor() {
        return exitDoor;
    }

    @Override
    public ISaveProperties getSaveProperties() {
        return properties;
    }

    @Override
    public String loggerIdentifier() {
        return "Building";
    }
}
