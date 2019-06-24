package net.badlion.gcheat.listeners;

import org.bukkit.plugin.messaging.*;
import org.json.simple.parser.*;
import org.bukkit.entity.*;
import com.google.common.io.*;
import org.bukkit.event.player.*;
import org.bukkit.event.*;
import java.util.*;

public class BungeeCordListener implements PluginMessageListener, Listener
{
    private final JSONParser parser;
    public static final Map<UUID, Map<String, String>> forgeMods;
    
    public BungeeCordListener() {
        this.parser = new JSONParser();
    }
    
    public void onPluginMessageReceived(final String channel, final Player player, final byte[] data) {
        final ByteArrayDataInput input = ByteStreams.newDataInput(data);
        if ("ForgeMods".equals(input.readUTF())) {
            input.readUTF();
        }
    }
    
    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent event) {
        BungeeCordListener.forgeMods.remove(event.getPlayer().getUniqueId());
    }
    
    public static String getClientType(final Player player) {
        final Map<String, String> mods = BungeeCordListener.forgeMods.get(player.getUniqueId());
        if (mods != null) {
            if (mods.containsKey("gc")) {
                return "Hacked Client Type A";
            }
            if (mods.containsKey("ethylene")) {
                return "Hacked Client Type B";
            }
            if ("1.0".equals(mods.get("OpenComputers"))) {
                return "Hacked Client Type C";
            }
            if ("1.7.6.git".equals(mods.get("Schematica"))) {
                return "Hacked Client Type D";
            }
        }
        return null;
    }
    
    static {
        forgeMods = new HashMap<UUID, Map<String, String>>();
    }
}
