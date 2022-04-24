package by.quaks.ntm.util;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatUtil {
    public static TextComponent genChatMessage(String chatType, String name, String message, String chatTypeDescription, String nameDescription, ClickEvent chatTypeClickEvent, ClickEvent nameClickEvent, ChatColor chatTypeColor, ChatColor nameColor, ChatColor messageColor) {
        TextComponent chat_type = new TextComponent(chatType);
        chat_type.setColor(chatTypeColor);
        chat_type.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(chatTypeDescription).color(ChatColor.GRAY).create()));
        chat_type.setClickEvent(chatTypeClickEvent);
        TextComponent line = new TextComponent(" | ");
        line.setColor(ChatColor.GRAY);
        TextComponent Tname = new TextComponent(name);
        Tname.setColor(nameColor);
        Tname.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(nameDescription).color(ChatColor.GRAY).create()));
        Tname.setClickEvent(nameClickEvent);
        TextComponent dot = new TextComponent(" • ");
        dot.setColor(ChatColor.GRAY);
        TextComponent Tmessage = new TextComponent(message);
        Tmessage.setColor(messageColor);
        TextComponent result = new TextComponent();
        result.addExtra(chat_type);
        result.addExtra(line);
        result.addExtra(Tname);
        result.addExtra(dot);
        result.addExtra(Tmessage);
        return result;
    }

    public static TextComponent genTellMessage(String name1, String name2, String message, String reply) {
        TextComponent names = new TextComponent(name1);
        names.setColor(ChatColor.GOLD);
        names.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Ответить " + reply)
                .color(ChatColor.GRAY)
                .create()));
        names.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tell " + reply + " "));
        TextComponent separator = new TextComponent(" → ");
        separator.setColor(ChatColor.GRAY);
        names.addExtra(separator);
        TextComponent names2 = new TextComponent(name2);
        names2.setColor(ChatColor.GOLD);
        names.addExtra(names2);
        TextComponent sk = new TextComponent("[");
        sk.addExtra(names);
        TextComponent sk2 = new TextComponent("]");
        sk2.setColor(ChatColor.WHITE);
        sk2.addExtra(" :" + message);
        sk.addExtra(sk2);

        return sk;
    }

    public static void sendGroupMessage(TextComponent message, Collection<? extends Player> res) {
        for (Player p : res) {
            p.spigot().sendMessage(message);
        }
    }

    public static void sendGroupMessage(TextComponent message, String peremission, boolean forOp) {
        Collection<? extends Player> res = Bukkit.getOnlinePlayers();
        for (Player p : res) {
            if (!forOp) {
                if (p.hasPermission(peremission)) {
                    p.spigot().sendMessage(message);
                }
            } else {
                if (p.hasPermission(peremission) || p.isOp()) {
                    p.spigot().sendMessage(message);
                }
            }
        }
    }

    public static void sendGroupMessageByTag(BaseComponent[] message, String tag, boolean forOp) {
        Collection<? extends Player> res = Bukkit.getOnlinePlayers();
        for (Player p : res) {
            if (!forOp) {
                if (p.getScoreboardTags().contains(tag)) {
                    p.spigot().sendMessage(message);
                }
            } else {
                if (p.getScoreboardTags().contains(tag) || p.isOp()) {
                    p.spigot().sendMessage(message);
                }
            }
        }
    }

    public static void sendPrivateMessage(TextComponent message, Player p) {
        p.spigot().sendMessage(message);
    }

    public static void sendNearby(TextComponent message, Player sender, int r) {
        for (Player other : Bukkit.getOnlinePlayers()) {
            if (sender.getWorld().getEnvironment().name().equals(other.getWorld().getEnvironment().name())) {
                if (other.getLocation().distance(sender.getLocation()) <= r) {
                    other.spigot().sendMessage(message);
                }
            }
        }
    }

    public static String removeUrl(String commentstr) {
        // rid of ? and & in urls since replaceAll can't deal with them
        String commentstr1 = commentstr.replaceAll("\\?", "").replaceAll("\\&", "");

        String urlPattern = "((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern p = Pattern.compile(urlPattern, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(commentstr);
        int i = 0;
        while (m.find()) {
            commentstr = commentstr1.replaceAll(m.group(i).replaceAll("\\?", "").replaceAll("\\&", ""), "").trim();
            i++;
        }
        return commentstr;
    }

    public static TextComponent getUrls(String commentstr) {
        TextComponent t = new TextComponent();
        String urlPattern = "((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern p = Pattern.compile(urlPattern, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(commentstr);
        int i = 0;
        while (m.find()) {
            //commentstr = commentstr.replaceAll(m.group(i),"").trim();
            TextComponent t1 = new TextComponent(" [Ссылка]");
            t1.setColor(ChatColor.GRAY);
            t1.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Перейти").color(ChatColor.GRAY).create()));
            t1.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, m.group(i)));
            t.addExtra(t1);
            i++;
        }
        return t;
    }
}
