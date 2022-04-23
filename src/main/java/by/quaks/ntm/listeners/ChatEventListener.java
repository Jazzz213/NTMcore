package by.quaks.ntm.listeners;

import by.quaks.ntm.NTM;
import by.quaks.ntm.util.ChatUtil;
import github.scarsz.discordsrv.util.DiscordUtil;
import github.scarsz.discordsrv.util.WebhookUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

public class ChatEventListener implements Listener{
    @EventHandler(ignoreCancelled = true)
    public void onPlayerChat(PlayerChatEvent event){
        event.setCancelled(true);
        Player p = event.getPlayer();
        String msg = event.getMessage();
        if(event.getMessage().startsWith("@")&&event.getPlayer().hasPermission("group.moderator")){
            msg = msg.replaceFirst("[@]","");
            ChatUtil.sendGroupMessage(
                    ChatUtil.genChatMessage(
                            "[A]", p.getName(), msg, "Администрация", "Написать "+p.getName(), new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,"@"), new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,"/tell "+p.getName()+" "), ChatColor.RED, ChatColor.of("#9EFF86"),ChatColor.WHITE
                    ),"group.moderator",true
            );
            WebhookUtil.deliverMessage(DiscordUtil.getTextChannelById(NTM.getInstance().getConfig().getString("channels.admin")),event.getPlayer(),msg);
            Bukkit.getLogger().info("[NTMP] [A] | "+p.getName()+" • "+event.getMessage());
            return;
        }
        if(event.getMessage().startsWith("$")){
            msg = msg.replaceFirst("[$]","");
            ChatUtil.sendGroupMessage(
                    ChatUtil.genChatMessage(
                            "[$]", p.getName(), msg, "Торговый чат", "Написать "+p.getName(), new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,"$"), new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,"/tell "+p.getName()+" "), ChatColor.GREEN, ChatColor.of("#9EFF86"),ChatColor.WHITE
                    ),Bukkit.getOnlinePlayers()
            );
            Bukkit.getLogger().info("[NTMP] [$] | "+p.getName()+" • "+event.getMessage());
            return;
        }
        if(event.getMessage().startsWith("!")){
            msg = msg.replaceFirst("[!]","");
            ChatUtil.sendGroupMessage(
                    ChatUtil.genChatMessage(
                            "[G]", p.getName(), msg, "Глобальный чат", "Написать "+p.getName(), new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,"!"), new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,"/tell "+p.getName()+" "), ChatColor.of("#f4f47c"), ChatColor.of("#9EFF86"),ChatColor.WHITE
                    ),Bukkit.getOnlinePlayers()
            );
            Bukkit.getLogger().info("[NTMP] [G] | "+p.getName()+" • "+event.getMessage());
            return;
        }
        ChatUtil.sendNearby(
                ChatUtil.genChatMessage(
                        "[L]", p.getName(), msg, "Локальный чат", "Написать "+p.getName(), new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,null), new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,"/tell "+p.getName()+" "), ChatColor.GRAY, ChatColor.of("#9EFF86"),ChatColor.WHITE
                ),p,100
        );
        Bukkit.getLogger().info("[NTMP] [L] | "+p.getName()+" • "+event.getMessage());

    }
}
