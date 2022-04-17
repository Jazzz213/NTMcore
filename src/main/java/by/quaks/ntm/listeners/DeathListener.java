package by.quaks.ntm.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {
    @EventHandler
    public void deathListener(PlayerDeathEvent event){
        if(event.getEntity().getScoreboardTags().contains("muted.true")){
            event.setDeathMessage(null);
        }
    }
}
