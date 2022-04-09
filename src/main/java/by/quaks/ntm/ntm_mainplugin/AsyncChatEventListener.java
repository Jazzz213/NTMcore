package by.quaks.ntm.ntm_mainplugin;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;

import java.awt.*;

public class AsyncChatEventListener implements Listener {
    ClickEvent global_suggest = new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "! ");
    TextComponent dot = new TextComponent(" • ");
    @EventHandler(ignoreCancelled = true)
    public void onPlayerChat(PlayerChatEvent event){
        event.setCancelled(true);
        TextComponent line = new TextComponent("| ");
        line.setColor(ChatColor.GRAY);
        Player p = event.getPlayer();
        Server s = p.getServer();
        ClickEvent msg_suggest = new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tell "+p.getName()+" ");
        TextComponent chatType = new TextComponent();
        TextComponent name = new TextComponent(p.getName());
        TextComponent result = new TextComponent();
        String msg = event.getMessage();
        if(!event.getMessage().startsWith("!")){
            chatType.setText("[L] ");
            chatType.setColor(ChatColor.GRAY);
            chatType.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Локальный чат").create()));
            chat_comps(line, p, msg_suggest, chatType, name, result);
            result.addExtra(event.getMessage());
            for (Player other : Bukkit.getOnlinePlayers()) {
                if (other.getLocation().distance(p.getLocation()) <= 100) {
                    p.spigot().sendMessage(result);
                }
            }
            //s.spigot().broadcast(result); - отправить всем
        }else{
            msg = msg.replaceFirst("!","");
            chatType.setText("[G] ");
            chatType.setColor(ChatColor.of("#f4f47c"));
            chatType.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Написать в глобольный чат").create()));
            chatType.setClickEvent(global_suggest);
            chat_comps(line, p, msg_suggest, chatType, name, result);
            result.addExtra(msg);
            s.spigot().broadcast(result);
        }
    }

    private void chat_comps(TextComponent line, Player p, ClickEvent msg_suggest, TextComponent chatType, TextComponent name, TextComponent result) {
        name.setColor(ChatColor.of("#9EFF86"));
        name.setClickEvent(msg_suggest);
        name.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("/tell "+p.getName()).create()));
        result.addExtra(chatType);
        result.addExtra(line);
        result.addExtra(name);
        result.addExtra(dot);
    }
}