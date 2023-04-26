package de.tomstahlberg.advancedplayerbounditems.events;

import de.tomstahlberg.advancedplayerbounditems.AdvancedPlayerBoundItems;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.BlockState;
import org.bukkit.block.TileState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class BlockBreak implements Listener {
    private String prefix = "&6&lGolden&3&lSky &8x ";
    private static Plugin plugin = AdvancedPlayerBoundItems.plugin;
    @EventHandler
    public void onBlockBreak (BlockBreakEvent event){
        if(event.getPlayer() == null)
            return;
        if(event.getPlayer().getInventory().getItemInMainHand() == null || event.getPlayer().getInventory().getItemInMainHand().getType() == Material.AIR)
            return;

        Player player = event.getPlayer();
        ItemStack itemStack = event.getPlayer().getInventory().getItemInMainHand();

        if(canDoEvent(itemStack, player) == false)
            event.setCancelled(true);

    }

    private Boolean canDoEvent(ItemStack itemStack, Player player){
        if(player.hasPermission("advancedplayerbounditems.bypass") || player.isOp())
            return true;
        ItemMeta itemMeta = itemStack.getItemMeta();
        PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
        NamespacedKey enchantNamespacedKey = new NamespacedKey(plugin, "EnchantedBy");
        NamespacedKey fixedNamespacedKey = new NamespacedKey(plugin, "FixedBy");
        if(persistentDataContainer.has(enchantNamespacedKey, PersistentDataType.STRING)){
            UUID uuid = UUID.fromString(persistentDataContainer.get(enchantNamespacedKey, PersistentDataType.STRING));
            if(uuid.equals(player.getUniqueId())){
                //player.sendMessage("Du hast enchantet und darfst.");
                return true;
            }else{
                //player.sendMessage("Jemand hat enchantet und darfst nicht.");
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix+"&cDieses Item wurde von einem anderen Spieler via Befehl verändert, daher darfst du es nicht verwenden."));
                return false;
            }
        }else if(persistentDataContainer.has(fixedNamespacedKey, PersistentDataType.STRING)) {
            UUID uuid = UUID.fromString(persistentDataContainer.get(fixedNamespacedKey, PersistentDataType.STRING));
            if(uuid.equals(player.getUniqueId())){
                //player.sendMessage("Du hast gefixt und darfst.");
                return true;
            }else{
                //player.sendMessage("Jemand hat gefixt und darfst nicht.");
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix+"&cDieses Item wurde von einem anderen Spieler via Befehl verändert, daher darfst du es nicht verwenden."));
                return false;
            }
        }else{
            //player.sendMessage("Kein Tag vorhanden, du darfst.");
            return true;
        }
    }
}
