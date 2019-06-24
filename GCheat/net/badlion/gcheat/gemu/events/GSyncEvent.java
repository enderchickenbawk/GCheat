package net.badlion.gcheat.gemu.events;

import org.bukkit.event.*;
import java.util.*;

public class GSyncEvent extends Event
{
    private static final HandlerList handlers;
    private List<String> args;
    
    public GSyncEvent(final List<String> args) {
        this.args = args;
    }
    
    public List<String> getArgs() {
        return this.args;
    }
    
    public HandlerList getHandlers() {
        return GSyncEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return GSyncEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
