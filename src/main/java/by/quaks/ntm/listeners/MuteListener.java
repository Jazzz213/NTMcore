package by.quaks.ntm.listeners;

import by.quaks.ntm.files.MuteList;
import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.util.DiscordUtil;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;

import java.util.Date;

public class MuteListener implements Listener {
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (MuteList.get().getBoolean(event.getPlayer().getName() + ".muted")) {
            Date d1 = (Date) MuteList.get().get(event.getPlayer().getName() + ".mute_date");
            if (d1.after(new Date())) {
                event.getPlayer().sendMessage(ChatColor.RED + "Вы не можете писать в чат!");
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerChat(PlayerChatEvent event) {
        Date d1 = (Date) MuteList.get().get(event.getPlayer().getName() + ".mute_date");
        if (MuteList.get().getBoolean(event.getPlayer().getName() + ".muted") && !d1.after(new Date())) {
            MuteList.get().set(event.getPlayer().getName() + ".muted", false);
            MuteList.get().set(event.getPlayer().getName() + ".mute_date", null);
            MuteList.save();
            DiscordUtil.removeRolesFromMember(DiscordUtil.getMemberById(DiscordSRV.getPlugin().getAccountLinkManager().getDiscordId(event.getPlayer().getUniqueId())), DiscordUtil.getRole("960513488478949416"));
        }
    }
}
