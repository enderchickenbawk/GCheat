package net.badlion.gcheat.listeners;

import org.bukkit.*;
import java.util.*;
import org.bukkit.event.player.*;
import org.bukkit.event.block.*;
import org.bukkit.potion.*;
import org.bukkit.event.*;

public class FoodFixListener implements Listener
{
    private Set<Material> unallowedItems;
    
    public FoodFixListener() {
        (this.unallowedItems = new HashSet<Material>()).add(Material.WOOD_AXE);
        this.unallowedItems.add(Material.WOOD_SWORD);
        this.unallowedItems.add(Material.STONE_AXE);
        this.unallowedItems.add(Material.STONE_SWORD);
        this.unallowedItems.add(Material.IRON_AXE);
        this.unallowedItems.add(Material.IRON_SWORD);
        this.unallowedItems.add(Material.GOLD_AXE);
        this.unallowedItems.add(Material.GOLD_SWORD);
        this.unallowedItems.add(Material.DIAMOND_AXE);
        this.unallowedItems.add(Material.DIAMOND_SWORD);
        this.unallowedItems.add(Material.MUSHROOM_SOUP);
        this.unallowedItems.add(Material.APPLE);
        this.unallowedItems.add(Material.COOKED_FISH);
        this.unallowedItems.add(Material.MELON);
        this.unallowedItems.add(Material.COOKED_BEEF);
        this.unallowedItems.add(Material.COOKED_CHICKEN);
        this.unallowedItems.add(Material.GRILLED_PORK);
        this.unallowedItems.add(Material.CARROT);
        this.unallowedItems.add(Material.BAKED_POTATO);
        this.unallowedItems.add(Material.PUMPKIN_PIE);
        this.unallowedItems.add(Material.GOLDEN_APPLE);
        this.unallowedItems.add(Material.GOLDEN_CARROT);
    }
    
    @EventHandler
    public void onPlayerEatWhileLookingAtFence(final PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && (event.getClickedBlock().getType() == Material.FENCE || event.getClickedBlock().getType() == Material.NETHER_FENCE) && event.getItem() != null && (this.unallowedItems.contains(event.getItem().getType()) || (event.getItem().getType() == Material.POTION && !Potion.fromItemStack(event.getItem()).isSplash()))) {
            event.setCancelled(true);
        }
    }
}
