package net.badlion.gcheat.listeners;

import net.badlion.gcheat.gemu.events.*;
import java.util.*;
import org.bukkit.event.*;

public class GSyncListener implements Listener
{
    @EventHandler
    public void onGsync(final GSyncEvent event) {
        if (event.getArgs().size() < 5) {
            return;
        }
        final String subChannel = event.getArgs().get(0);
        if (subChannel.equals("GCheat")) {
            final String msg = event.getArgs().get(1);
            if (msg.equals("ForgeMod") && event.getArgs().size() == 5) {
                final UUID uuid = UUID.fromString(event.getArgs().get(2));
                final String mod = event.getArgs().get(3);
                final String s = event.getArgs().get(4);
            }
        }
    }
}
