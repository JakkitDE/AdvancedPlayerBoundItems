package de.tomstahlberg.advancedplayerbounditems.commands;

import de.tomstahlberg.advancedplayerbounditems.AdvancedPlayerBoundItems;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public class AdminRemoveTagCommand implements CommandExecutor {
    private Plugin plugin = AdvancedPlayerBoundItems.plugin;
    private String prefix = "&6&lGolden&3&lSky &8x ";
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)){
            Player player = (Player) commandSender;
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+" " +
                    "&cNur ein Spieler kann den Befehl ausführen."));
        }else{
            Player player = (Player) commandSender;
            if(player.hasPermission("advancedplayerbounditems.adminremovetag") || player.isOp()){
                ItemStack itemStack = player.getInventory().getItemInMainHand();
                ItemMeta itemMeta = itemStack.getItemMeta();
                PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
                NamespacedKey enchantNamespacedKey = new NamespacedKey(plugin, "EnchantedBy");
                NamespacedKey fixedNamespacedKey = new NamespacedKey(plugin, "FixedBy");

                if(strings[0].equalsIgnoreCase("fixed")){
                    if(persistentDataContainer.has(fixedNamespacedKey, PersistentDataType.STRING)){
                        persistentDataContainer.remove(fixedNamespacedKey);
                        itemStack.setItemMeta(itemMeta);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+" &aDer Fixed-Tag wurde entfernt."));
                    }else{
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+" &cDas Item hatte kein Fixed-Tag."));
                    }
                }else if(strings[0].equalsIgnoreCase("enchanted")){
                    if(persistentDataContainer.has(enchantNamespacedKey, PersistentDataType.STRING)){
                        persistentDataContainer.remove(enchantNamespacedKey);
                        itemStack.setItemMeta(itemMeta);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+" &aDer Enchanted-Tag wurde entfernt."));
                    }else{
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+" &cDas Item hatte kein Enchanted-Tag."));
                    }
                }else{
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+" &cBitte verwende &e/adminremovetag <fixed|enchanted>&c."));
                }
            }
        }
        return false;
    }
}
