package com.dami.fakeBuildings.Commands;

import com.dami.fakeBuildings.BuildingInitializations.BuildingSchematic;
import com.dami.fakeBuildings.BuildingInitializations.BuildingSchematicCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BuildingSchematicCommand implements TabExecutor {

    public BuildingSchematicCommand(JavaPlugin plugin) {
        plugin.getCommand("buildingschematic").setExecutor(this);
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
                BuildingSchematic schem = BuildingSchematicCreator.createBuildingSchematic(player.getUniqueId());

                if(schem == null){
                    player.sendMessage("You must set both positions and a building name");
                    return false;
                }

                schem.saveToFileAsync("test");
                return true;
            }
            if(args[0].equalsIgnoreCase("delete")){
                //delete building schematic
                return true;
            }
            if(args[0].equalsIgnoreCase("list")){
                //list building schematics
                return true;
            }
            if(args[0].equalsIgnoreCase("pos1")){
                BuildingSchematicCreator.setPos1(player.getUniqueId(), player.getLocation());
                return true;
            }
            if(args[0].equalsIgnoreCase("pos2")){
                BuildingSchematicCreator.setPos2(player.getUniqueId(), player.getLocation());
                return true;
            }
        }

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String arg, @NotNull String[] args) {

        if(args.length == 0){
            return List.of("create", "delete", "list", "pos1", "pos2");
        }

        if(args.length == 1){
            if(args[0].equalsIgnoreCase("delete") || args[0].equalsIgnoreCase("list")){
                return List.of();
            }
        }

        return List.of();
    }
}
