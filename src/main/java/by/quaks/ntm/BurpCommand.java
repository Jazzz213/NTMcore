package by.quaks.ntm;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BurpCommand implements CommandExecutor {
    /**
     * Executes the given command, returning its success.
     * <br>
     * If false is returned, then the "usage" plugin.yml entry for this command
     * (if defined) will be sent to the player.
     *
     * @param sender  Source of the command
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    Passed command arguments
     * @return true if a valid command, otherwise false
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;

            //.getLogger().info("playsound minecraft:entity.player.burp player @a[distance=..20] "+player.getLocation().getX()+" "+player.getLocation().getY()+" "+player.getLocation().getZ());
            player.getServer().dispatchCommand(player.getServer().getConsoleSender(),"execute at "+player.getName()+" run playsound minecraft:entity.player.burp player @a[distance=..20] ~ ~ ~");
        }
        return true ;
    }
}
