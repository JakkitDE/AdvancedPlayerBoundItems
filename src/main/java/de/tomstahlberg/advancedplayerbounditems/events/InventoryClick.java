package de.tomstahlberg.advancedplayerbounditems.events;

import de.tomstahlberg.advancedplayerbounditems.AdvancedPlayerBoundItems;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class InventoryClick implements Listener {
    private String prefix = "&6&lGolden&3&lSky &8x ";
    private static Plugin plugin = AdvancedPlayerBoundItems.plugin;
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if(event.getClickedInventory().getType() == InventoryType.ANVIL && event.getSlot() == 2 && event.getClickedInventory().getItem(event.getSlot()) != null){
            //check both items for existing tags
            try{
                ItemStack itemStack1 = event.getClickedInventory().getItem(0);
                ItemStack itemStack2 = event.getClickedInventory().getItem(1);
                if(!(anvilItemHasATag(itemStack1) && anvilItemHasATag(itemStack2))){
                    event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', prefix+"&cDas Item wurde modifiziert und kann nicht im Amboss verwendet werden."));
                    event.setCancelled(true);
                }
            }catch(Exception e){
                //do nothing cause one of itemstacks perhaps is null. No error.
            }

        }


    }
    private Boolean anvilItemHasATag(ItemStack itemStack){
        ItemMeta itemMeta = itemStack.getItemMeta();
        PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
        NamespacedKey enchantNamespacedKey = new NamespacedKey(plugin, "EnchantedBy");
        NamespacedKey fixedNamespacedKey = new NamespacedKey(plugin, "FixedBy");
        if(persistentDataContainer.has(enchantNamespacedKey, PersistentDataType.STRING)){
            return false;
        }else if(persistentDataContainer.has(fixedNamespacedKey, PersistentDataType.STRING)) {
            return false;
        }else{
            return true;
        }
    }
}
