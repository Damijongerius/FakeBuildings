package com.dami.fakeBuildings.Commands;

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
                //create building schematic
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
                //set position 1
                return true;
            }
            if(args[0].equalsIgnoreCase("pos2")){
                //set position 2
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
