package com.dami.fakeBuildings.BuildingInitializations;

import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BuildingCreator {

    private static final Map<UUID,PlayerBuildingSettings> playerBuildingSettings = new HashMap<>();

    public static PlayerBuildingSettings getPlayerBuildingSettings(UUID uuid){
        return playerBuildingSettings.get(uuid);
    }

    public static void addValue(){

    }


    public static class PlayerBuildingSettings{

        protected Location pos1;

        protected Location pos2;

        protected String buildingName;

        public void setPos1(Location pos1) {
            this.pos1 = pos1;
        }

        public void setPos2(Location pos2) {
            this.pos2 = pos2;
        }

        public void setBuildingName(String buildingName) {
            this.buildingName = buildingName;
        }

        public boolean isComplete(){
            return pos1 != null && pos2 != null && buildingName != null;
        }

        public boolean isValid(){
            if(!isComplete()) return false;

            if(pos1.getWorld() != pos2.getWorld()) return false;

            return true;
        }
    }
}
