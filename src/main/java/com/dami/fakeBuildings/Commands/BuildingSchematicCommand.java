package com.dami.fakeBuildings.Commands;

import com.dami.common.IASCComponent;
import com.dami.fakeBuildings.Listeners.DoorCreateClicksListener;
import com.dami.fakeBuildings.Structure.BuildingSchematic;
import com.dami.fakeBuildings.BuildingInitializations.BuildingSchematicCreator;
import com.dami.handlers.SaveHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BuildingSchematicCommand implements TabExecutor {

    private SaveHandler saveHandler = new SaveHandler();

    public BuildingSchematicCommand(JavaPlugin plugin, SaveHandler saveHandler) {
        plugin.getCommand("buildingschematic").setExecutor(this);
        this.saveHandler = saveHandler;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String arg, @NotNull String[] args) {

        if(!(sender instanceof Player player)){
            sender.sendMessage("You must be a player to use this command");
            return false;
        }

        if(args.length == 0){
            player.sendMessage("You must specify a building schematic");
            return false;
        }

        if(args.length == 1){
            if(args[0].equalsIgnoreCase("create")){

                player.sendMessage("You must specify a building name");

                return true;
            }

            if(args[0].equalsIgnoreCase("delete")){

                player.sendMessage("You must specify a building name");

                return true;
            }

            if(args[0].equalsIgnoreCase("door")){

                BuildingSchematicCreator.PlayerBuildingSettings settings = BuildingSchematicCreator.getPlayerBuildingSettings(player.getUniqueId());

                if(settings == null){
                    player.sendMessage("You must create a building schematic first");
                    player.sendMessage("and set the positions");
                    return false;
                }

                if(settings.pos1 == null || settings.pos2 == null){
                    player.sendMessage("You must set both positions first");
                    return false;
                }

                player.sendMessage("specify the door by leftClicking for the start and rightClicking for the end");

                DoorCreateClicksListener.listenToPlayer(player, this::setDoor);

                return true;
            }

            if(args[0].equalsIgnoreCase("list")){
                List<Map<String,Object>> pks = saveHandler.getPrimaryKeysFromYaml(BuildingSchematic.class);

                try {
                    for (Map<String, Object> pk : pks) {
                        player.sendMessage(pk.get("name").toString());
                    }
                } catch (Exception e) {
                    Bukkit.getLogger().info("an error occurred");
                }

                return true;
            }
            if(args[0].equalsIgnoreCase("pos1")){
                player.sendMessage("pos1 set at [" + player.getLocation().getBlockX() + ", " + player.getLocation().getBlockY() + ", " + player.getLocation().getBlockZ() + "]");
                BuildingSchematicCreator.setPos1(player.getUniqueId(), player.getLocation());
                return true;
            }
            if(args[0].equalsIgnoreCase("pos2")){
                player.sendMessage("pos2 set at [" + player.getLocation().getBlockX() + ", " + player.getLocation().getBlockY() + ", " + player.getLocation().getBlockZ() + "]");
                BuildingSchematicCreator.setPos2(player.getUniqueId(), player.getLocation());
                return true;
            }
        }

        if(args.length == 2){
            if(args[0].equalsIgnoreCase("create")){
                BuildingSchematicCreator.setBuildingName(player.getUniqueId(), args[1]);

                BuildingSchematic schem = BuildingSchematicCreator.createBuildingSchematic(player.getUniqueId());

                player.sendMessage("Building Schematic created");

                if(schem == null){
                    player.sendMessage("You must set both positions and a building name");
                    return false;
                }

                saveHandler.save(schem);
                return true;
            }

            if(args[0].equalsIgnoreCase("delete")){

                String buildingName = args[1];

                BuildingSchematic schem = new BuildingSchematic(buildingName,null,null);

                IASCComponent[] component = saveHandler.loadByPrimaryKeys(schem);

                saveHandler.delete(component[0]);

                return true;
            }
        }

        return false;
    }

    private void setDoor(DoorCreateClicksListener.PlayerDoorSettings settings){
        BuildingSchematicCreator.setDoorSettings(settings.player.getUniqueId(),settings);
        settings.player.sendMessage("The exit door has set you can now create the building schematic");
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String arg, @NotNull String[] args) {

        if(args.length == 1){
            return List.of("create", "delete", "list", "pos1", "pos2");
        }

        if(args.length == 2){
            if(args[0].equalsIgnoreCase("delete")){
                List<Map<String,Object>> pks = saveHandler.getPrimaryKeysFromYaml(BuildingSchematic.class);

                List<String> buildingNames = new ArrayList<>();

                for(Map<String,Object> pk : pks){
                    buildingNames.add((String) pk.get("name"));
                }

                return buildingNames;
            }
        }

        return List.of();
    }
}
