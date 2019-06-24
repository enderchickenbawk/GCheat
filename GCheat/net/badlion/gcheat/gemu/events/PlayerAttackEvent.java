package net.badlion.gcheat.gemu.events;

import org.bukkit.event.player.*;
import org.bukkit.event.*;
import org.bukkit.entity.*;

public class PlayerAttackEvent extends PlayerEvent
{
    private static final HandlerList handlers;
    
    public PlayerAttackEvent(final Player player) {
        super(player);
    }
    
    public HandlerList getHandlers() {
        return PlayerAttackEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerAttackEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
