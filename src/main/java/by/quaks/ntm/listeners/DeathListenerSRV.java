package by.quaks.ntm.listeners;

import github.scarsz.discordsrv.api.Subscribe;
import github.scarsz.discordsrv.api.events.DeathMessagePreProcessEvent;
import org.bukkit.plugin.Plugin;

public class DeathListenerSRV {
    private final Plugin plugin;

    public DeathListenerSRV(Plugin plugin) {
        this.plugin = plugin;
    }
        @Subscribe
        public void deathMessage(DeathMessagePreProcessEvent e){
            if(e.getPlayer().getScoreboardTags().contains("muted.true")){
                e.setCancelled(true);
            }
        }

}
