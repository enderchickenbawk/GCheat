package net.badlion.gcheat.bukkitevents;

import org.bukkit.event.player.*;
import org.bukkit.event.*;
import java.util.*;
import org.bukkit.entity.*;

public class GCheatGameStartEvent extends PlayerEvent
{
    private static final HandlerList handlers;
    private Map<String, Object> data;
    
    public GCheatGameStartEvent(final Player player) {
        super(player);
    }
    
    public GCheatGameStartEvent(final Player player, final Map<String, Object> data) {
        super(player);
        this.data = data;
    }
    
    public Map<String, Object> getData() {
        return this.data;
    }
    
    public HandlerList getHandlers() {
        return GCheatGameStartEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return GCheatGameStartEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
