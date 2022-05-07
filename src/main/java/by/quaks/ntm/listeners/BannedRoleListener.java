package by.quaks.ntm.listeners;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.util.DiscordUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Set;

public class BannedRoleListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        if(!e.getPlayer().isBanned()){
                DiscordUtil.removeRolesFromMember(DiscordUtil.getMemberById(DiscordSRV.getPlugin().getAccountLinkManager().getDiscordId(e.getPlayer().getUniqueId())), DiscordUtil.getRole("962827395784642580"));
        }
    }
}
