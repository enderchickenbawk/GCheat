package net.badlion.gcheat.listeners;

import org.bukkit.plugin.messaging.*;
import org.bukkit.event.*;
import org.bukkit.entity.*;
import org.bukkit.event.player.*;
import java.util.*;

public class VapeListener implements Listener, PluginMessageListener
{
    public static Set<UUID> vapers;
    
    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        event.getPlayer().sendMessage("§8 §8 §1 §3 §3 §7 §8 ");
    }
    
    public void onPluginMessageReceived(final String channel, final Player player, final byte[] data) {
        try {
            final String str = new String(data);
        }
        catch (Exception ex) {
            final String str = "";
        }
        VapeListener.vapers.add(player.getUniqueId());
    }
    
    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent event) {
        VapeListener.vapers.remove(event.getPlayer().getUniqueId());
    }
    
    static {
        VapeListener.vapers = new HashSet<UUID>();
    }
}
