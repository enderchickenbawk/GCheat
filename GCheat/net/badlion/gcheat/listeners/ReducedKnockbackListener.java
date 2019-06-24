package net.badlion.gcheat.listeners;

import net.badlion.gcheat.gemu.events.*;
import net.badlion.gcheat.*;
import java.util.regex.*;
import org.bukkit.event.*;
import java.util.*;

public class ReducedKnockbackListener implements Listener
{
    private final Pattern reducesTheirKB;
    private static Map<UUID, List<Long>> lastTinyKB;
    
    public ReducedKnockbackListener() {
        this.reducesTheirKB = Pattern.compile("reduces their KB \\((\\d+)");
    }
    
    @EventHandler
    public void onGCheat(final GCheatEvent event) {
        if (event.getType() == GCheatEvent.Type.ANTI_KB) {
            final Matcher matcher = this.reducesTheirKB.matcher(event.getMsg());
            if (matcher.find()) {
                final int amount = Integer.parseInt(matcher.group(1));
                if (amount < 60) {
                    GCheat.handleTimeDetection(ReducedKnockbackListener.lastTinyKB, event.getPlayer().getUniqueId(), event.getPlayer().getName(), 60000, 10);
                }
            }
        }
    }
    
    static {
        ReducedKnockbackListener.lastTinyKB = new HashMap<UUID, List<Long>>();
    }
}
