package by.quaks.ntm.ntm_mainplugin;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class MuteListener implements Listener {
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event){
        if(event.getPlayer().getScoreboardTags().contains("muted.true")){
            event.getPlayer().sendMessage(ChatColor.RED+"Вы не можете писать в чат!");
            event.setCancelled(true);
        }
    }
}
