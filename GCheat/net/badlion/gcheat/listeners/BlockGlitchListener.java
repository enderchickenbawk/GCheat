package net.badlion.gcheat.listeners;

import org.bukkit.scheduler.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import net.badlion.gcheat.*;
import org.bukkit.plugin.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;
import java.util.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;

public class BlockGlitchListener implements Listener
{
    private static final int BLOCK_BREAK_MILLIS = 1000;
    private final Set<UUID> pendingTp;
    private final Map<UUID, Long> blockBreakTime;
    
    public BlockGlitchListener() {
        this.pendingTp = new HashSet<UUID>();
        this.blockBreakTime = new HashMap<UUID, Long>();
    }
    
    @EventHandler
    public void onBlockPlace(final BlockPlaceEvent event) {
        if (!event.isCancelled() || event.getPlayer().getGameMode() == GameMode.CREATIVE || !event.getBlock().getType().isSolid()) {
            return;
        }
        final Player player = event.getPlayer();
        final Location loc = event.getPlayer().getLocation();
        if (loc.getY() > event.getBlock().getY() + 1 && this.pendingTp.add(player.getUniqueId())) {
            new BukkitRunnable() {
                public void run() {
                    if (!player.isDead() && player.getWorld() == loc.getWorld() && player.getLocation().distanceSquared(loc) < 100.0) {
                        player.teleport(loc);
                        player.sendMessage(Color.RED + "Block glitching is not allowed!");
                    }
                    BlockGlitchListener.this.pendingTp.remove(player.getUniqueId());
                }
            }.runTaskLater((Plugin)GCheat.plugin, 10L);
        }
    }
    
    @EventHandler
    public void onBlockBreak(final BlockBreakEvent event) {
        if (!event.isCancelled() || event.getPlayer().getGameMode() == GameMode.CREATIVE || !event.getBlock().getType().isSolid()) {
            return;
        }
        final long now = System.currentTimeMillis();
        final Iterator<Long> iter = this.blockBreakTime.values().iterator();
        while (iter.hasNext()) {
            if (now - iter.next() > 1000L) {
                iter.remove();
            }
        }
        this.blockBreakTime.put(event.getPlayer().getUniqueId(), now);
    }
    
    @EventHandler
    public void onAttack(final EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) {
            return;
        }
        final Player player = (Player)event.getDamager();
        if (this.hasRecentBlockBreak(player)) {
            event.setCancelled(true);
            player.sendMessage(Color.RED + "Block glitching is not allowed!");
        }
    }
    
    @EventHandler
    public void onRightClickEntity(final PlayerInteractEntityEvent event) {
        if (this.hasRecentBlockBreak(event.getPlayer())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(Color.RED + "Block glitching is not allowed!");
        }
    }
    
    private boolean hasRecentBlockBreak(final Player player) {
        final Long time = this.blockBreakTime.get(player.getUniqueId());
        if (time == null) {
            return false;
        }
        if (System.currentTimeMillis() - time < 1000L) {
            return true;
        }
        this.blockBreakTime.remove(player.getUniqueId());
        return false;
    }
}
