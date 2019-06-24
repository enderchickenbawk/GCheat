package net.badlion.gcheat.listeners;

import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;

public class NoFallListener implements Listener
{
    @EventHandler
    public void playerTakeFallDamageEvent(final EntityDamageEvent event) {
        if (event.getEntity() instanceof Player && event.getCause() == EntityDamageEvent.DamageCause.FALL) {
            System.out.println(event.getDamage());
        }
    }
}
