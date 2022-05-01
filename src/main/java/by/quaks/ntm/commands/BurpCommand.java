package by.quaks.ntm.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BurpCommand implements CommandExecutor {
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
