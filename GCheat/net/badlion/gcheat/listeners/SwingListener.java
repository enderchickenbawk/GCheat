package net.badlion.gcheat.listeners;

import java.util.*;
import org.bukkit.event.*;
import net.badlion.gcheat.bukkitevents.*;
import net.badlion.gcheat.*;
import org.bukkit.event.player.*;
import org.bukkit.event.block.*;
import net.badlion.gcheat.gemu.events.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;

public class SwingListener implements Listener
{
    private Map<UUID, Long> hitTimes;
    private Map<UUID, SwingTracker> playerSwingTrackers;
    
    public SwingListener() {
        this.hitTimes = new HashMap<UUID, Long>();
        this.playerSwingTrackers = new HashMap<UUID, SwingTracker>();
    }
    
    @EventHandler
    public void onGCheatGameStartEvent(final GCheatGameStartEvent event) {
        this.createNewSwingTracker(event.getPlayer().getUniqueId(), event.getData());
    }
    
    @EventHandler
    public void onGCheatGameEndEvent(final GCheatGameEndEvent event) {
        final SwingTracker swingTracker = this.getSwingTracker(event.getPlayer());
        if (swingTracker != null && event.getData() != null) {
            swingTracker.setData(event.getData());
            GCheat.plugin.addSwingRecord(swingTracker);
            this.playerSwingTrackers.remove(event.getPlayer().getUniqueId());
        }
    }
    
    @EventHandler
    public void onPlayerInteractEvent(final PlayerInteractEvent event) {
        final SwingTracker swingTracker = this.getSwingTracker(event.getPlayer());
        if (swingTracker != null && this.isUsingSword(event.getPlayer()) && (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK)) {
            swingTracker.addSwing();
        }
    }
    
    @EventHandler
    public void onPlayerDamageEvent(final PlayerAttackEvent event) {
        final SwingTracker swingTracker = this.getSwingTracker(event.getPlayer());
        if (swingTracker != null && this.isUsingSword(event.getPlayer())) {
            swingTracker.addHit();
        }
        if (Math.random() < 0.004) {
            String client = BungeeCordListener.getClientType(event.getPlayer());
            if (VapeListener.vapers.contains(event.getPlayer().getUniqueId())) {
                client = "Hacked Client Type E";
            }
            if (client != null && GCheat.bannedUUIDS.add(event.getPlayer().getUniqueId())) {
                Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "ban " + event.getPlayer().getName() + " [GCheat] Unfair Advantage");
            }
        }
    }
    
    private void createNewSwingTracker(final UUID uuid, final Map<String, Object> data) {
        this.playerSwingTrackers.put(uuid, new SwingTracker(uuid, data));
    }
    
    private SwingTracker getSwingTracker(final Player player) {
        return this.playerSwingTrackers.get(player.getUniqueId());
    }
    
    private boolean isUsingSword(final Player player) {
        final ItemStack itemInHand = player.getItemInHand();
        if (itemInHand != null) {
            switch (itemInHand.getType()) {
                case WOOD_SWORD:
                case STONE_SWORD:
                case GOLD_SWORD:
                case IRON_SWORD:
                case DIAMOND_SWORD: {
                    return true;
                }
            }
        }
        return false;
    }
    
    public class SwingTracker
    {
        private UUID uuid;
        private int hits;
        private int swings;
        private Map<String, Object> data;
        
        public SwingTracker(final UUID uuid, final Map<String, Object> data) {
            this.hits = 0;
            this.swings = 0;
            this.uuid = uuid;
            this.data = data;
        }
        
        public UUID getUniqueId() {
            return this.uuid;
        }
        
        public void addHit() {
            ++this.hits;
        }
        
        public int getHits() {
            return this.hits;
        }
        
        public void addSwing() {
            ++this.swings;
        }
        
        public int getSwings() {
            return this.swings;
        }
        
        public double getSwingHitPercentage() {
            if (this.swings == 0) {
                return 0.0;
            }
            return this.hits / (double)this.swings * 100.0;
        }
        
        public Map<String, Object> getData() {
            return this.data;
        }
        
        public void setData(final Map<String, Object> data) {
            this.data = data;
        }
    }
}
