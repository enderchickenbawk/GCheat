package net.badlion.gcheat;

import org.bukkit.command.*;
import net.badlion.gcheat.listeners.*;

public class DisableGCheatCommand implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command command, final String s, final String[] args) {
        if (args.length != 2) {
            sender.sendMessage("Wrong");
            return true;
        }
        if (args[0].equalsIgnoreCase("add")) {
            GCheat.disabledPlayers.add(args[1].toLowerCase());
        }
        else if (args[0].equalsIgnoreCase("remove")) {
            GCheat.disabledPlayers.remove(args[1].toLowerCase());
        }
        else if (args[0].equalsIgnoreCase("ignore")) {
            if (GCheat.ignoredPlayers.contains(args[1].toLowerCase())) {
                GCheat.ignoredPlayers.remove(args[1].toLowerCase());
            }
            else {
                GCheat.ignoredPlayers.add(args[1].toLowerCase());
            }
        }
        else if (args[0].equalsIgnoreCase("disable")) {
            sender.sendMessage("anti cheat is now Active");
        }
        else if (args[0].equalsIgnoreCase("threshold")) {
            AutoClickerListener.CPS_THRESHOLD = Integer.parseInt(args[1]);
        }
        else if (args[0].equalsIgnoreCase("threshold_exp")) {
            AutoClickerListener.CPS_THRESHOLD_EXPERIMENTAL = Integer.parseInt(args[1]);
        }
        sender.sendMessage("done");
        return true;
    }
}
