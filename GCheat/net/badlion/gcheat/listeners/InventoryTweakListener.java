package net.badlion.gcheat.listeners;

import net.badlion.gcheat.*;
import java.util.*;
import org.bukkit.event.block.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;

public class InventoryTweakListener implements Listener
{
    private GCheat plugin;
    private Set<UUID> inventoriesOpened;
    
    public InventoryTweakListener(final GCheat plugin) {
        this.inventoriesOpened = new HashSet<UUID>();
        this.plugin = plugin;
    }
    
    @EventHandler(ignoreCancelled = true)
    public void onPlayerDrinkSoup(final PlayerInteractEvent event) {
        if ((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && event.getItem() != null && event.getItem().getType() == Material.MUSHROOM_SOUP) {
            final Player player = event.getPlayer();
            if (this.inventoriesOpened.contains(player.getUniqueId())) {
                Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getServer().getConsoleSender(), "ban [Inventory Hacks] " + player.getName() + " is using inventory hacks");
            }
        }
    }
    
    @EventHandler
    public void inventoryClickEvent(final InventoryClickEvent event) {
        if (event.getInventory().getType().equals((Object)InventoryType.CRAFTING) && event.getRawSlot() > 8 && !this.inventoriesOpened.contains(event.getWhoClicked().getUniqueId())) {
            this.inventoriesOpened.add(event.getWhoClicked().getUniqueId());
        }
    }
    
    @EventHandler
    public void inventoryClickEvent(final InventoryCloseEvent event) {
        this.inventoriesOpened.remove(event.getPlayer().getUniqueId());
    }
    
    @EventHandler
    public void playerQuitEvent(final PlayerQuitEvent event) {
        if (this.inventoriesOpened.contains(event.getPlayer().getUniqueId())) {
            this.inventoriesOpened.remove(event.getPlayer().getUniqueId());
        }
    }
}
