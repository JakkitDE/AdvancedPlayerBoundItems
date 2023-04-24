package de.tomstahlberg.advancedplayerbounditems.commands;

import de.tomstahlberg.advancedplayerbounditems.AdvancedPlayerBoundItems;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class FixCommand implements CommandExecutor {
    private Plugin plugin = AdvancedPlayerBoundItems.plugin;
    private String prefix = "&6&lGolden&3&lSky &8x ";
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)){
            Player player = (Player) commandSender;
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+" " +
                    "&cNur ein Spieler kann Items reparieren."));
        }else{
            Player player = (Player) commandSender;
            if(player.hasPermission("advancedplayerbounditems.fix") || player.isOp()){
                if(player.getInventory().getItemInMainHand() != null &&
                        player.getInventory().getItemInMainHand().getType() != Material.AIR) {
                    ItemStack itemStack = player.getInventory().getItemInMainHand();
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    if(itemMeta instanceof Damageable){
                        NamespacedKey namespacedKey = new NamespacedKey(plugin, "FixedBy");
                        PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
                        if(persistentDataContainer.has(namespacedKey, PersistentDataType.STRING)){
                            //wurde bereits von jemandem gefixt
                            UUID uuid = UUID.fromString(persistentDataContainer.get(namespacedKey, PersistentDataType.STRING));
                            if(player.getUniqueId().equals(uuid)){

                                /* Repair Item */
                                Damageable damageable = (Damageable) itemStack.getItemMeta();
                                damageable.setDamage(0);
                                itemStack.setItemMeta(damageable);
                                /* Set Tag if not exist */

                                player.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+" " +
                                        "&aDein Item wurde repaiert."));
                            }else{
                                //von jemandem anderen gefixt
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+" " +
                                        "&cDas Item wurde bereits von jemand anderem über &e/fix &crepariert, daher kannst du es nicht reparieren."));
                            }
                        }else{
                            //noch ungefixt
                            persistentDataContainer.set(namespacedKey, PersistentDataType.STRING, player.getUniqueId().toString());
                            itemStack.setItemMeta(itemMeta);

                            /* Repair Item */
                            Damageable damageable = (Damageable) itemStack.getItemMeta();
                            damageable.setDamage(0);
                            itemStack.setItemMeta(damageable);
                            /* Set Tag if not exist */

                            player.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+" " +
                                    "&aDein Item wurde repaiert. Ab sofort kannst nur noch du es verwenden."));
                        }





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
                        " Du benötigst den Rang &6König&c, &6Hochkönig &coder die entsprechenden Rechte über ein &6Paket &cingame."));
            }
        }
        return false;
    }
}
