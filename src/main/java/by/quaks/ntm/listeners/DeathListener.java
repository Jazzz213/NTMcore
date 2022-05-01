package by.quaks.ntm.listeners;

import by.quaks.ntm.files.MuteList;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {
    @EventHandler
    public void deathListener(PlayerDeathEvent event){
        if(MuteList.get().getBoolean(event.getEntity().getName() + ".muted")){
            event.setDeathMessage(null);
        }
    }
}
