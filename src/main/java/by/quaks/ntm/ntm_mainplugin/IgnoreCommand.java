package by.quaks.ntm.ntm_mainplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class IgnoreCommand implements CommandExecutor {
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
        if(sender instanceof Player){
            Player p = ((Player) sender);
            Bukkit.getLogger().info(((Player) sender).getScoreboardTags().toString());
            switch (args.length){
                case 0:
                    p.sendMessage(
                            ChatColor.RED+"Неизвестная или неполная команда:\n"+
                                    ChatColor.GRAY+"/"+label+" "+ChatColor.RED+"[никнейм]");
                    break;
                case 1:
                    if(Bukkit.getOfflinePlayer(args[0]).hasPlayedBefore()){
                        if(!p.getScoreboardTags().contains("ignore."+args[0])) {
                            p.addScoreboardTag("ignore."+args[0]);
                            p.sendMessage(
                                            ChatColor.GREEN + "Сообщения от данного игрока "+ChatColor.RED+"не будут"+ChatColor.GREEN+" доставляться"
                            );
                        } else {
                            p.removeScoreboardTag("ignore."+args[0]);
                            p.sendMessage(
                                            ChatColor.GREEN + "Сообщения от данного игрока "+ChatColor.RED+"будут"+ChatColor.GREEN+" доставляться"
                            );
                        }
                    } else {
                        p.sendMessage(
                                ChatColor.RED+"Ошибка: такого игрока не существует");
                    }
                    break;
                default:
                    p.sendMessage(
                            ChatColor.RED+"Неизвестная или неполная команда:\n"+
                                    ChatColor.GRAY+"/"+label+" "+ChatColor.RED+"[никнейм]");
            }
        }else{
            return false;
        }
        return true;
    }
}
