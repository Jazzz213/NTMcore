package by.quaks.ntm.commands;

import by.quaks.ntm.files.MuteList;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class MuteCommand implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        switch (args.length) {
            case 0:
                sender.sendMessage(
                        ChatColor.RED + "Неизвестная или неполная команда:\n" +
                                ChatColor.GRAY + "/" + label + " " + ChatColor.RED + "[никнейм]");
                break;
            case 1:
                if (Bukkit.getOfflinePlayer(args[0]).hasPlayedBefore()) {
                    Player monkey = (Player) Bukkit.getOfflinePlayer(args[0]);
                    if (!MuteList.get().getBoolean(monkey.getName() + ".muted")) {
                        MuteList.get().set(monkey.getName() + ".muted",true);
//                        monkey.removeScoreboardTag("muted.false");
//                        monkey.addScoreboardTag("muted.true");
                        sender.getServer().spigot().broadcast(
                                new TextComponent(ChatColor.RED + monkey.getName() + " был замьючен навсегда"
                                ));
                        sender.getServer().dispatchCommand(sender.getServer().getConsoleSender(), "discordsrv broadcast " + monkey.getName() + " был замьючен навсегда");
                        //DiscordUtil.addRolesToMember(DiscordUtil.getMemberById(DiscordSRV.getPlugin().getAccountLinkManager().getDiscordId(monkey.getUniqueId())),DiscordUtil.getRole("960513488478949416"));
                        MuteList.get().set(monkey.getName() + ".muted", true);
                        Date d = new Date();
                        Calendar c = Calendar.getInstance();
                        c.setTime(d);
                        c.add(Calendar.YEAR, 12);
                        Date d1 = c.getTime();
                        MuteList.get().set(monkey.getName() + ".mute_date", d1);
                        MuteList.save();
                    } else {
                        MuteList.get().set(monkey.getName() + ".muted",false);
//                        monkey.removeScoreboardTag("muted.true");
//                        monkey.addScoreboardTag("muted.false");
                        sender.getServer().spigot().broadcast(
                                new TextComponent(ChatColor.RED + monkey.getName() + " был размьючен"
                                ));
                        sender.getServer().dispatchCommand(sender.getServer().getConsoleSender(), "discordsrv broadcast " + monkey.getName() + " был размьючен");
//                            {
//                                DiscordUtil.removeRolesFromMember(DiscordUtil.getMemberById(DiscordSRV.getPlugin().getAccountLinkManager().getDiscordId(monkey.getUniqueId())), DiscordUtil.getRole("960513488478949416"));
//                            }
                        MuteList.get().set(monkey.getName() + ".muted", false);
                        MuteList.get().set(monkey.getName() + ".mute_date",null);
                        MuteList.save();
                    }
                } else {
                    sender.sendMessage(
                            ChatColor.RED + "Ошибка: такого игрока не существует");
                }
                break;
            case 2:
                if (!Bukkit.getOfflinePlayer(args[0]).hasPlayedBefore()) {
                    sender.sendMessage(
                            ChatColor.RED + "Ошибка: такого игрока не существует");
                }
                if (!isNumeric(args[1])) {
                    sender.sendMessage(
                            ChatColor.RED + "Ошибка: дата указана не верно");
                    break;
                }
                sender.sendMessage(
                        ChatColor.RED + "Ошибка: дата указана не верно");
                break;
            case 3:
                if (!Bukkit.getOfflinePlayer(args[0]).hasPlayedBefore()) {
                    sender.sendMessage(
                            ChatColor.RED + "Ошибка: такого игрока не существует");
                }
                if (!isNumeric(args[1])) {
                    sender.sendMessage(
                            ChatColor.RED + "Ошибка: дата указана не верно");
                    break;
                }
                Player monkey = (Player) Bukkit.getOfflinePlayer(args[0]);
                if(args[2].equals("seconds")){
                    MuteList.get().set(monkey.getName() + ".muted", true);
                    Date d = new Date();
                    Calendar c = Calendar.getInstance();
                    c.setTime(d);
                    c.add(Calendar.SECOND, Integer.parseInt(args[1]));
                    Date d1 = c.getTime();
                    MuteList.get().set(monkey.getName() + ".mute_date", d1);
                    MuteList.save();
                    sender.getServer().spigot().broadcast(
                            new TextComponent(ChatColor.RED + monkey.getName() + " был замьючен на "+ ChatColor.YELLOW + args[1] + " секунд"
                            ));
                } else
                if(args[2].equals("minutes")){
                    MuteList.get().set(monkey.getName() + ".muted", true);
                    Date d = new Date();
                    Calendar c = Calendar.getInstance();
                    c.setTime(d);
                    c.add(Calendar.MINUTE, Integer.parseInt(args[1]));
                    Date d1 = c.getTime();
                    MuteList.get().set(monkey.getName() + ".mute_date", d1);
                    MuteList.save();
                    sender.getServer().spigot().broadcast(
                            new TextComponent(ChatColor.RED + monkey.getName() + " был замьючен на "+ ChatColor.YELLOW + args[1] + " минут"
                            ));
                } else
                if(args[2].equals("hours")){
                    MuteList.get().set(monkey.getName() + ".muted", true);
                    Date d = new Date();
                    Calendar c = Calendar.getInstance();
                    c.setTime(d);
                    c.add(Calendar.HOUR, Integer.parseInt(args[1]));
                    Date d1 = c.getTime();
                    MuteList.get().set(monkey.getName() + ".mute_date", d1);
                    MuteList.save();
                    sender.getServer().spigot().broadcast(
                            new TextComponent(ChatColor.RED + monkey.getName() + " был замьючен на "+ ChatColor.YELLOW + args[1] + " часов"
                            ));
                } else
                if(args[2].equals("days")){
                    MuteList.get().set(monkey.getName() + ".muted", true);
                    Date d = new Date();
                    Calendar c = Calendar.getInstance();
                    c.setTime(d);
                    c.add(Calendar.HOUR, Integer.parseInt(args[1])*24);
                    Date d1 = c.getTime();
                    MuteList.get().set(monkey.getName() + ".mute_date", d1);
                    MuteList.save();
                    sender.getServer().spigot().broadcast(
                            new TextComponent(ChatColor.RED + monkey.getName() + " был замьючен на "+ ChatColor.YELLOW + args[1] + " дней"
                            ));
                } else {
                    sender.sendMessage(
                            ChatColor.RED + "Ошибка: дата указана не верно");
                    break;
                }
                break;
            default:
                sender.sendMessage(
                        ChatColor.RED + "Неизвестная или неполная команда:\n" +
                                ChatColor.GRAY + "/" + label + " " + ChatColor.RED + "[никнейм]");
        }

        return true;
    }

    public static boolean isNumeric(String str) {
        try {
            double var1 = Double.parseDouble(str);
            return true;
        } catch (NumberFormatException var3) {
            return false;
        }
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if(args.length==1){return null;}
        if(args.length == 3){
            String[] l = {"seconds", "minutes", "hours", "days"};
            return Arrays.asList(l);
        }
        return Collections.emptyList();
    }
}
