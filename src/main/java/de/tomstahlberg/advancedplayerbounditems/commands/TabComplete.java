package de.tomstahlberg.advancedplayerbounditems.commands;

import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;

import java.util.ArrayList;
import java.util.List;

public class TabComplete implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> argsList = new ArrayList<>();
        if(strings.length == 1){
            for(Enchantment enchantment : Enchantment.values()){
                argsList.add(enchantment.getKey().getKey());
            }
        }
        if(strings.length == 2){
            try {
                Enchantment enchantment = Enchantment.getByKey(NamespacedKey.minecraft(strings[0]));
                for(int i = 1; i<= enchantment.getMaxLevel(); i++){
                    argsList.add(String.valueOf(i));
                }
            }catch (Exception e){
                argsList.add("Unbekannte Verzauberung !");
            }

        }
        return argsList;
    }
}
