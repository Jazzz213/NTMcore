package by.quaks.ntm.listeners;

import by.quaks.ntm.NTM;
import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.util.DiscordUtil;
import github.scarsz.discordsrv.util.WebhookUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

public class ChatEventListener implements Listener{
    ClickEvent global_suggest = new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "!");
    ClickEvent trade_suggest = new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "$");
    ClickEvent admin_suggest = new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "@");
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


        if(event.getMessage().startsWith("@")&&event.getPlayer().hasPermission("group.moderator")){
            msg = msg.replaceFirst("@","");
            chatType.setText("[A] ");
            chatType.setColor(ChatColor.RED);
            chatType.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Админ-чат").create()));
            chatType.setClickEvent(admin_suggest);
            chat_comps(line, p, msg_suggest, chatType, name, result);
            result.addExtra(DiscordSRVListener.removeUrl(msg));
            result.addExtra(DiscordSRVListener.getUrls(msg));
            for (Player p1 : Bukkit.getOnlinePlayers()){
                if(p1.hasPermission("group.moderator")||p1.isOp()){
                    p1.spigot().sendMessage(result);
                }
            }
            //s.spigot().broadcast(result);
            WebhookUtil.deliverMessage(DiscordUtil.getTextChannelById(NTM.getInstance().getConfig().getString("channels.admin")),event.getPlayer(),msg);
            Bukkit.getLogger().info("[NTMP] [A] | "+p.getName()+" • "+event.getMessage());
            return;
            //s.spigot().broadcast(result); - отправить всем
        }
        if(event.getMessage().startsWith("$")){
            msg = msg.replaceFirst("[$]","");
            chatType.setText("[$] ");
            chatType.setColor(ChatColor.GREEN);
            chatType.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Торговый чат").create()));
            chatType.setClickEvent(trade_suggest);
            chat_comps(line, p, msg_suggest, chatType, name, result);
            result.addExtra(DiscordSRVListener.removeUrl(msg));
            result.addExtra(DiscordSRVListener.getUrls(msg));
            s.spigot().broadcast(result);
            Bukkit.getLogger().info("[NTMP] [$] | "+p.getName()+" • "+event.getMessage());
            return;
            //s.spigot().broadcast(result); - отправить всем
        }
        if(!event.getMessage().startsWith("!") && !event.getMessage().startsWith("$")){
            chatType.setText("[L] ");
            chatType.setColor(ChatColor.GRAY);
            chatType.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Локальный чат").color(ChatColor.GRAY).create()));
            chat_comps(line, p, msg_suggest, chatType, name, result);
            result.addExtra(DiscordSRVListener.removeUrl(event.getMessage()));
            result.addExtra(DiscordSRVListener.getUrls(event.getMessage()));;
            Bukkit.getLogger().info("[NTMP] [L] | "+p.getName()+" • "+event.getMessage());
            for (Player other : Bukkit.getOnlinePlayers()) {
                if(p.getWorld().getEnvironment().name().equals(other.getWorld().getEnvironment().name())){
                    if (other.getLocation().distance(p.getLocation()) <= 100) {
                        other.spigot().sendMessage(result);
                    }
                }
            }
            //s.spigot().broadcast(result); - отправить всем
        }else{
            msg = msg.replaceFirst("!","");
            chatType.setText("[G] ");
            chatType.setColor(ChatColor.of("#f4f47c"));
            chatType.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Написать в глобальный чат").color(ChatColor.GRAY).create()));
            chatType.setClickEvent(global_suggest);
            chat_comps(line, p, msg_suggest, chatType, name, result);
            result.addExtra(DiscordSRVListener.removeUrl(msg));
            result.addExtra(DiscordSRVListener.getUrls(msg));
            s.spigot().broadcast(result);
            Bukkit.getLogger().info("[NTMP] [G] | "+p.getName()+" • "+event.getMessage());
        }
    }

    private void chat_comps(TextComponent line, Player p, ClickEvent msg_suggest, TextComponent chatType, TextComponent name, TextComponent result) {
        name.setColor(ChatColor.of("#9EFF86"));
        name.setClickEvent(msg_suggest);
        name.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Написать "+p.getName()).color(ChatColor.GRAY).create()));
        result.addExtra(chatType);
        result.addExtra(line);
        result.addExtra(name);
        result.addExtra(dot);
    }
}