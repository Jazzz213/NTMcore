package by.quaks.ntm.ntm_mainplugin;

import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class TellCommand implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player p = ((Player) sender).getPlayer();
            //Во тута должна быть логика сообщения и не забудь про колокольчик
            //и неверное использования тоже переопредели
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> list = Arrays.asList();
        if(args.length==1){
            return null;
        } else{
            return list;
        }

    }
}
