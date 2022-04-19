package by.quaks.ntm.commands;

import by.quaks.ntm.NTM;
import github.scarsz.discordsrv.dependencies.jda.api.EmbedBuilder;
import github.scarsz.discordsrv.dependencies.jda.api.entities.MessageEmbed;
import github.scarsz.discordsrv.util.DiscordUtil;
import github.scarsz.discordsrv.util.WebhookUtil;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.awt.Color;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ReportCommand implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(args.length==0){
                player.sendMessage(ChatColor.RED+"Репорт не был доставлен: опишите свою проблему");
                return true;
            }
            String message = String.join(" ", args);
            String world = player.getWorld().getEnvironment().name();
            if(world.equals(World.Environment.NETHER.name())){
                world = "Нижний мир";
            }
            if(world.equals(World.Environment.NORMAL.name())){
                world = "Верхний мир";
            }
            if(world.equals(World.Environment.THE_END.name())){
                world = "Энд";
            }
            MessageEmbed embed = new EmbedBuilder()
                    //.setAuthor(player.getName())
                    .setDescription(new Date()+"\n"+"```"+message+"```")
                    .setColor(Color.YELLOW)
                    .setFooter("X:"+player.getLocation().getBlockX()+" Y:"+player.getLocation().getBlockY()+" Z:"+player.getLocation().getBlockZ()+" "+world)
                    .build();
            WebhookUtil.deliverMessage(DiscordUtil.getTextChannelById(NTM.getInstance().getConfig().getString("channels.report")),player,"",embed);
            player.sendMessage(ChatColor.GREEN+"Ожидайте, скоро вам ответит администратор");
            {
                for (Player p1 : Bukkit.getOnlinePlayers()) {
                    if (p1.hasPermission("group.moderator")) {
                        TextComponent type = new TextComponent("[R]");
                        type.setColor(net.md_5.bungee.api.ChatColor.AQUA);
                        type.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder("Репорт")
                                .color(net.md_5.bungee.api.ChatColor.GRAY)
                                .create()));
                        TextComponent line = new TextComponent(" | ");
                        line.setColor(net.md_5.bungee.api.ChatColor.GRAY);
                        TextComponent name = new TextComponent(player.getName());
                        name.setColor(net.md_5.bungee.api.ChatColor.of("#9EFF86"));
                        name.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder("Написать "+player.getName())
                                .color(net.md_5.bungee.api.ChatColor.GRAY)
                                .create()));
                        name.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,"/tell "+player.getName()+" "));
                        TextComponent msg = new TextComponent();
                        msg.addExtra(type);
                        msg.addExtra(line);
                        msg.addExtra(name);
                        msg.addExtra(" • ");
                        msg.addExtra(message);
                        p1.spigot().sendMessage(msg);
                        p1.playNote(p1.getLocation(), Instrument.BELL, Note.flat(0, Note.Tone.A));
                    }
                }
            }
        }
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
