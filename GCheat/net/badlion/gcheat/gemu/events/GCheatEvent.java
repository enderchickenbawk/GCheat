package net.badlion.gcheat.gemu.events;

import org.bukkit.event.player.*;
import org.bukkit.event.*;
import org.bukkit.entity.*;

public class GCheatEvent extends PlayerEvent implements Cancellable
{
    private static final HandlerList handlers;
    private String msg;
    private Type type;
    private Level level;
    private boolean cancelled;
    
    public GCheatEvent(final Player player, final Type type, final Level level, final String msg) {
        super(player);
        this.cancelled = false;
        this.type = type;
        this.level = level;
        this.msg = msg;
    }
    
    public String getMsg() {
        return this.msg;
    }
    
    public HandlerList getHandlers() {
        return GCheatEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return GCheatEvent.handlers;
    }
    
    public Type getType() {
        return this.type;
    }
    
    public Level getLevel() {
        return this.level;
    }
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }
    
    static {
        handlers = new HandlerList();
    }
    
    public enum Level
    {
        CHAT_MOD, 
        TRIAL, 
        MOD, 
        ADMIN, 
        OP;
    }
    
    public enum Type
    {
        SPEED, 
        FLY, 
        AUTO_CLICKER, 
        KILL_AURA, 
        HOVER, 
        CRIT, 
        NO_FALL, 
        SPIDER, 
        INVENTORY, 
        TIMER, 
        DEBUG, 
        UNKNOWN, 
        ANTI_KB, 
        FREECAM, 
        PHASE, 
        FAST_EAT_MACHINE_GUN, 
        REGEN;
    }
}
