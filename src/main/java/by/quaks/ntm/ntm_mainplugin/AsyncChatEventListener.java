package by.quaks.ntm.ntm_mainplugin;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

public class AsyncChatEventListener implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onPlayerChat(PlayerChatEvent event) {
        event.setCancelled(true);
        Player p = event.getPlayer();
        Server s = p.getServer();
        ConsoleCommandSender console = s.getConsoleSender();
        double x = p.getLocation().getX();
        double y = p.getLocation().getY();
        double z = p.getLocation().getZ();
        if(!event.getMessage().startsWith("!")){
        s.dispatchCommand(s.getConsoleSender(),
                "execute positioned "+x+" "+y+" "+z+" run tellraw @a[distance=0..100] [\"\",{\"text\":\"[L]\",\"color\":\"gray\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"!\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"\\u041d\\u0430\\u043f\\u0438\\u0441\\u0430\\u0442\\u044c \\u0432 \\u0433\\u043b\\u043e\\u0431\\u0430\\u043b\\u044c\\u043d\\u044b\\u0439 \\u0447\\u0430\\u0442\"}},{\"text\":\" |\",\"color\":\"gray\"},{\"text\":\" \"},{\"text\":\""+p.getName()+"\",\"color\":\"gold\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/tell "+p.getName()+"\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"\\u041d\\u0430\\u043f\\u0438\\u0441\\u0430\\u0442\\u044c \\u043b\\u0438\\u0447\\u043d\\u043e\\u0435 \\u0441\\u043e\\u043e\\u0431\\u0449\\u0435\\u043d\\u0438\\u0435\"}},{\"text\":\" -\",\"color\":\"gray\"},{\"text\":\" "+event.getMessage()+"\"}]");
                console.sendMessage(ChatColor.GRAY + p.getName() + " : " + event.getMessage());
        }else{
            event.setMessage(event.getMessage().replaceFirst("!",""));
            s.dispatchCommand(s.getConsoleSender(),
                    "tellraw @a [\"\",{\"text\":\"[G]\",\"color\":\"yellow\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"!\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"\\u041d\\u0430\\u043f\\u0438\\u0441\\u0430\\u0442\\u044c \\u0432 \\u0433\\u043b\\u043e\\u0431\\u0430\\u043b\\u044c\\u043d\\u044b\\u0439 \\u0447\\u0430\\u0442\"}},{\"text\":\" |\",\"color\":\"gray\"},{\"text\":\" \"},{\"text\":\""+p.getName()+"\",\"color\":\"gold\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/tell "+p.getName()+"\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"\\u041d\\u0430\\u043f\\u0438\\u0441\\u0430\\u0442\\u044c \\u043b\\u0438\\u0447\\u043d\\u043e\\u0435 \\u0441\\u043e\\u043e\\u0431\\u0449\\u0435\\u043d\\u0438\\u0435\"}},{\"text\":\" -\",\"color\":\"gray\"},{\"text\":\" "+event.getMessage()+"\"}]");
                    console.sendMessage(ChatColor.YELLOW + p.getName() + " : " + event.getMessage());
        }
    }
}