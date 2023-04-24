package de.tomstahlberg.advancedplayerbounditems.commands;

import de.tomstahlberg.advancedplayerbounditems.AdvancedPlayerBoundItems;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class AdminEnchantCommand implements CommandExecutor {
    private Plugin plugin = AdvancedPlayerBoundItems.plugin;
    private String prefix = "&6&lGolden&3&lSky &8x ";
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(!(commandSender instanceof Player)){
            Player player = (Player) commandSender;
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+" " +
                    "&cNur ein Spieler kann Items verzaubern."));
        }else{
            Player player = (Player) commandSender;
            if(player.hasPermission("advancedplayerbounditems.adminenchant") || player.isOp()){
                if(player.getInventory().getItemInMainHand() != null &&
                        player.getInventory().getItemInMainHand().getType() != Material.AIR) {
                    ItemStack itemStack = player.getInventory().getItemInMainHand();
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    if(itemMeta instanceof Damageable){
                        //enchant
                        enchantItem(player, itemStack, args);

                    }else{
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+" " +
                                "&cDas Item muss nicht repariert werden."));
                    }
                }else{
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+" " +
                            "&cDu musst ein Item in der Hand halten."));
                }
            }else{
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+" " +
                        "&cDu hast keine Rechte, deine Items über den Befehl zu reparieren." +
                        " Du benötigst den Rang &6Admin&c."));
            }
        }
        return false;
    }
    private void enchantItem(Player player, ItemStack itemStack, String[] args){
        NamespacedKey namespacedKeyEnchantment;
        try {
            namespacedKeyEnchantment = new NamespacedKey(plugin, args[0]);
        }catch (Exception e){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+" " +
                    "&cDie Verzauberung existiert nicht!"));
            return;
        }
        if(Enchantment.getByKey(NamespacedKey.minecraft(args[0])) == null){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+" " +
                    "&cDie Verzauberung existiert nicht!"));
            return;
        }

        Enchantment enchantment = Enchantment.getByKey(NamespacedKey.minecraft(args[0]));
        int level = Integer.valueOf(args[1]);


        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addEnchant(enchantment, level, true);
        itemStack.setItemMeta(itemMeta);
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+" " +
                "&aDein Item wurde verzaubert."));
    }
}
