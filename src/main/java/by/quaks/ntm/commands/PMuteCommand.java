package by.quaks.ntm.commands;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.util.DiscordUtil;
import net.dv8tion.jda.api.entities.Role;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PMuteCommand implements CommandExecutor {
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
            switch (args.length){
                case 0:
                    sender.sendMessage(
                            ChatColor.RED+"Неизвестная или неполная команда:\n"+
                                    ChatColor.GRAY+"/"+label+" "+ChatColor.RED+"[никнейм]");
                    break;
                case 1:
                    if(Bukkit.getOfflinePlayer(args[0]).hasPlayedBefore()){
                        Player monkey = (Player) Bukkit.getOfflinePlayer(args[0]);
                        if(!monkey.getScoreboardTags().contains("muted.true")) {
                            monkey.removeScoreboardTag("muted.false");
                            monkey.addScoreboardTag("muted.true");
                            sender.getServer().spigot().broadcast(
                                    new TextComponent(ChatColor.RED+monkey.getName()+" был замьючен навсегда"
                                    ));
                            sender.getServer().dispatchCommand(sender.getServer().getConsoleSender(),"discordsrv broadcast "+monkey.getName()+" был замьючен навсегда");
                            DiscordUtil.addRolesToMember(DiscordUtil.getMemberById(DiscordSRV.getPlugin().getAccountLinkManager().getDiscordId(monkey.getUniqueId())),DiscordUtil.getRole("960513488478949416"));
                        } else {
                            monkey.removeScoreboardTag("muted.true");
                            monkey.addScoreboardTag("muted.false");
                            sender.getServer().spigot().broadcast(
                                    new TextComponent(ChatColor.RED+monkey.getName()+" был размьючен"
                                    ));
                            sender.getServer().dispatchCommand(sender.getServer().getConsoleSender(),"discordsrv broadcast "+monkey.getName()+" был размьючен");
                            {
                                DiscordUtil.removeRolesFromMember(DiscordUtil.getMemberById(DiscordSRV.getPlugin().getAccountLinkManager().getDiscordId(monkey.getUniqueId())), DiscordUtil.getRole("960513488478949416"));
                            }
                        }
                    } else {
                        sender.sendMessage(
                                ChatColor.RED+"Ошибка: такого игрока не существует");
                    }
                    break;
                default:
                    sender.sendMessage(
                            ChatColor.RED+"Неизвестная или неполная команда:\n"+
                                    ChatColor.GRAY+"/"+label+" "+ChatColor.RED+"[никнейм]");
            }

        return true;
    }
}
