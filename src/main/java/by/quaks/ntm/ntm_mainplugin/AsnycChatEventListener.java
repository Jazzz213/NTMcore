package by.quaks.ntm.ntm_mainplugin;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

public class AsnycChatEventListener implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onPlayerChat(PlayerChatEvent event) {
        event.setCancelled(true);
        Player p = event.getPlayer();
        Server s = p.getServer();
        double x = p.getLocation().getX();
        double y = p.getLocation().getY();
        double z = p.getLocation().getZ();
        if(!event.getMessage().startsWith("!")){
        s.dispatchCommand(s.getConsoleSender(),
                "execute positioned "+x+" "+y+" "+z+" run tellraw @a[distance=0..100] [\"\",{\"text\":\"[L]\",\"color\":\"gray\"},{\"text\":\" |\",\"color\":\"gray\"},{\"text\":\" "+p.getName()+"\",\"color\":\"gold\"},{\"text\":\" -\",\"color\":\"gray\"},{\"text\":\" "+event.getMessage()+"\"}]"
        );}else{
            event.setMessage(event.getMessage().replaceFirst("!",""));
            s.dispatchCommand(s.getConsoleSender(),
                    "tellraw @a [\"\",{\"text\":\"[G]\",\"color\":\"yellow\"},{\"text\":\" |\",\"color\":\"gray\"},{\"text\":\" "+p.getName()+"\",\"color\":\"gold\"},{\"text\":\" -\",\"color\":\"gray\"},{\"text\":\" "+event.getMessage()+"\"}]"
            );
        }
    }
}