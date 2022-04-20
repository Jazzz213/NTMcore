package by.quaks.ntm.util;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatUtil {
    public static BaseComponent[] genChatMessage(String chatType, String name, String message, String chatTypeDescription, String nameDescription, ClickEvent chatTypeClickEvent, ClickEvent nameClickEvent, ChatColor chatTypeColor, ChatColor nameColor, ChatColor messageColor) {
        TextComponent URLs = getUrls(message);
        message = removeUrl(message);
        BaseComponent[] chatTypeComponent = new ComponentBuilder(chatType)
                .color(chatTypeColor)
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder(chatTypeDescription)
                        .color(ChatColor.GRAY)
                        .create()))
                .event(chatTypeClickEvent)
                .create();
        BaseComponent[] nameComponent = new ComponentBuilder(name)
                .color(nameColor)
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(nameDescription)
                        .color(ChatColor.GRAY)
                        .create()))
                .event(nameClickEvent)
                .create();
        BaseComponent[] separator = new ComponentBuilder(" | ")
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder().create()))
                .color(ChatColor.GRAY)
                .create();
        BaseComponent[] separator1 = new ComponentBuilder(" • ")
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder().create()))
                .color(ChatColor.GRAY)
                .create();
        BaseComponent[] messageComponent = new ComponentBuilder(message)
                .color(messageColor)
                .create();
        return new ComponentBuilder()
                .append(chatTypeComponent)
                .append(separator)
                .append(nameComponent)
                .append(separator1)
                .append(messageComponent)
                .append(URLs)
                .create();
    }
    public static BaseComponent[] genTellMessage(String name1, String name2, String message){
        BaseComponent[] names = new ComponentBuilder(name1)
                .color(ChatColor.GOLD)
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder("Ответить "+name1)
                        .color(ChatColor.GRAY)
                        .create()))
                .event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,"/tell "+name1))
                .append(new ComponentBuilder(" → ")
                        .color(ChatColor.GRAY)
                        .create())
                .append(new ComponentBuilder(name2)
                        .color(ChatColor.GOLD)
                        .create())
                .create();
        return new ComponentBuilder()
                .append(new ComponentBuilder("[")
                        .color(ChatColor.WHITE)
                        .create())
                .append(names)
                .append(new ComponentBuilder("]")
                        .color(ChatColor.WHITE)
                        .create())
                .append(new ComponentBuilder(" :"+message)
                        .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder().create()))
                        .event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,null))
                        .color(ChatColor.WHITE)
                        .create())
                .create();

    }
    public static void sendGroupMessage(BaseComponent[] message, Collection<? extends Player> res){
        for(Player p : res){
            p.spigot().sendMessage(message);
        }
    }
    public static void sendGroupMessage(BaseComponent[] message, String peremission, boolean forOp){
        Collection<? extends Player> res = Bukkit.getOnlinePlayers();
        for(Player p : res){
            if(!forOp){
                if (p.hasPermission(peremission)) {
                    p.spigot().sendMessage(message);
                }
            } else {
                if (p.hasPermission(peremission)||p.isOp()) {
                    p.spigot().sendMessage(message);
                }
            }
        }
    }
    public static void sendGroupMessageByTag(BaseComponent[] message, String tag, boolean forOp){
        Collection<? extends Player> res = Bukkit.getOnlinePlayers();
        for(Player p : res){
            if(!forOp){
                if (p.getScoreboardTags().contains(tag)) {
                    p.spigot().sendMessage(message);
                }
            } else {
                if (p.getScoreboardTags().contains(tag)||p.isOp()) {
                    p.spigot().sendMessage(message);
                }
            }
        }
    }
    public static void sendPrivateMessage(BaseComponent[] message, Player p){
        p.spigot().sendMessage(message);
    }
    public static void sendNearby(BaseComponent[] message,Player sender, int r){
        for (Player other : Bukkit.getOnlinePlayers()) {
            if(sender.getWorld().getEnvironment().name().equals(other.getWorld().getEnvironment().name())){
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
        Pattern p = Pattern.compile(urlPattern,Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(commentstr);
        int i = 0;
        while (m.find()) {
            commentstr = commentstr1.replaceAll(m.group(i).replaceAll("\\?", "").replaceAll("\\&", ""),"").trim();
            i++;
        }
        return commentstr;
    }

    public static TextComponent getUrls(String commentstr) {
        TextComponent t = new TextComponent();
        String urlPattern = "((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern p = Pattern.compile(urlPattern,Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(commentstr);
        int i = 0;
        while (m.find()) {
            //commentstr = commentstr.replaceAll(m.group(i),"").trim();
            TextComponent t1 = new TextComponent(" [Ссылка]");
            t1.setColor(ChatColor.GRAY);
            t1.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Перейти").color(ChatColor.GRAY).create()));
            t1.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL,m.group(i)));
            t.addExtra(t1);
            i++;
        }
        return t;
    }
}
