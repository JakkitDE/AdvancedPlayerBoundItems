package de.tomstahlberg.advancedplayerbounditems.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class AdminRemoveTagTabComplete implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> argsList = new ArrayList<>();
        if(strings.length == 1){
            argsList.add("fixed");
            argsList.add("enchanted");
        }

        return argsList;
    }
}
