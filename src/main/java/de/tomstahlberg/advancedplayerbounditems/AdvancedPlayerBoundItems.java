package de.tomstahlberg.advancedplayerbounditems;

import de.tomstahlberg.advancedplayerbounditems.commands.*;
import de.tomstahlberg.advancedplayerbounditems.events.BlockBreak;
import de.tomstahlberg.advancedplayerbounditems.events.Interact;
import de.tomstahlberg.advancedplayerbounditems.events.InventoryClick;
import de.tomstahlberg.advancedplayerbounditems.events.ItemPickup;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class AdvancedPlayerBoundItems extends JavaPlugin {
    public static Plugin plugin;
    @Override
    public void onEnable() {
        plugin = this;
        // Plugin startup logic
        //getServer().getPluginManager().registerEvents(new BlockBreak(), this);
        getServer().getPluginCommand("fix").setExecutor(new FixCommand());
        getServer().getPluginCommand("enchant").setExecutor(new EnchantCommand());
        getServer().getPluginCommand("enchant").setTabCompleter(new TabComplete());
        getServer().getPluginCommand("adminenchant").setTabCompleter(new TabComplete());
        getServer().getPluginCommand("adminenchant").setExecutor(new AdminEnchantCommand());

        getServer().getPluginCommand("adminremovetag").setExecutor(new AdminRemoveTagCommand());
        getServer().getPluginCommand("adminremovetag").setTabCompleter(new AdminRemoveTagTabComplete());

        getServer().getPluginManager().registerEvents(new BlockBreak(), this);
        getServer().getPluginManager().registerEvents(new Interact(), this);
        getServer().getPluginManager().registerEvents(new ItemPickup(), this);
        getServer().getPluginManager().registerEvents(new InventoryClick(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
