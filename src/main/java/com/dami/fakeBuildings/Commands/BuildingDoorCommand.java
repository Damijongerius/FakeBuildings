package com.dami.fakeBuildings.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BuildingDoorCommand implements TabExecutor {

    public BuildingDoorCommand(JavaPlugin plugin) {
        plugin.getCommand("buildingdoor").setExecutor(this);
    }
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(strings.length == 0){
            commandSender.sendMessage("You must specify a building door");
            return false;
        }

        //type name pos1 pos2 material
        if(strings.length == 1){
            if(strings[0].equalsIgnoreCase("create")){

                commandSender.sendMessage("You must specify a building name");

                return true;
            }

            if(strings[0].equalsIgnoreCase("delete")){

                commandSender.sendMessage("You must specify a building name");

                return true;
            }
            if(strings[0].equalsIgnoreCase("list")){
                commandSender.sendMessage("Building Doors:");

                return true;
            }
            if(strings[0].equalsIgnoreCase("help")){
                commandSender.sendMessage("Building Door Commands:");
                commandSender.sendMessage("/buildingdoor create <buildingName> <doorName> <material>");
                commandSender.sendMessage("/buildingdoor delete <buildingName> <doorName>");
                commandSender.sendMessage("/buildingdoor list");
                return true;
            }
        }

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {


        return List.of();
    }
}
