package by.quaks.ntm.ntm_mainplugin;

import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TellCommand implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player p = (Player) sender;
            Server s = p.getServer();
            ConsoleCommandSender console = s.getConsoleSender();
            if(args.length==0){
                p.sendMessage(
                        ChatColor.RED+"Неизвестная или неполная команда:\n"+
                                ChatColor.GRAY+"/"+label+" "+ChatColor.RED+"[никнейм]"+ChatColor.RED+" [сообщение]");
            }
            if(args.length==1){
                Player receiver = Bukkit.getPlayerExact(args[0]);
                if(receiver!=null){
                p.sendMessage(
                        ChatColor.RED+"Неизвестная или неполная команда:\n"+
                        ChatColor.GRAY+"/"+label+" "+args[0]+ChatColor.RED+" [сообщение]");
                }else{
                    p.sendMessage(
                            ChatColor.RED+"Игрок оффлайн");
                }
            }
            if(args.length>1){
                Player receiver = Bukkit.getPlayerExact(args[0]);
                if(receiver!=null){
                    args[0]="";
                    String message = String.join(" ", args);
                    s.dispatchCommand(console, "tellraw " + p.getName() + " [\"\",{\"text\":\"[\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/msg " + receiver.getName() + " " + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"\\u041e\\u0442\\u0432\\u0435\\u0442\\u0438\\u0442\\u044c " + receiver.getName() + "\"}},{\"text\":\"\\u0412\\u044b\",\"color\":\"gold\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/msg " + receiver.getName() + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"\\u041e\\u0442\\u0432\\u0435\\u0442\\u0438\\u0442\\u044c " + receiver.getName() + "\"}},{\"text\":\" \",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/msg " + receiver.getName() + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"\\u041e\\u0442\\u0432\\u0435\\u0442\\u0438\\u0442\\u044c " + receiver.getName() + "\"}},{\"text\":\"\\u2192\",\"color\":\"gray\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/msg " + receiver.getName() + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"\\u041e\\u0442\\u0432\\u0435\\u0442\\u0438\\u0442\\u044c " + receiver.getName() + "\"}},{\"text\":\" \",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/msg " + receiver.getName() + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"\\u041e\\u0442\\u0432\\u0435\\u0442\\u0438\\u0442\\u044c " + receiver.getName() + "\"}},{\"text\":\"" + receiver.getName() + "\",\"color\":\"gold\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/msg " + receiver.getName() + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"\\u041e\\u0442\\u0432\\u0435\\u0442\\u0438\\u0442\\u044c " + receiver.getName() + "\"}},{\"text\":\"]\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/msg " + receiver.getName() + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"\\u041e\\u0442\\u0432\\u0435\\u0442\\u0438\\u0442\\u044c " + receiver.getName() + "\"}},{\"text\":\" :"+message+"\"}]");
                    s.dispatchCommand(console, "tellraw " + receiver.getName() + " [\"\",{\"text\":\"[\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/msg " + p.getName() + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"\\u041e\\u0442\\u0432\\u0435\\u0442\\u0438\\u0442\\u044c " + p.getName() + "\"}},{\"text\":\"" + p.getName() + "\",\"color\":\"gold\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/msg " + p.getName() + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"\\u041e\\u0442\\u0432\\u0435\\u0442\\u0438\\u0442\\u044c " + p.getName() + "\"}},{\"text\":\" \",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/msg " + p.getName() + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"\\u041e\\u0442\\u0432\\u0435\\u0442\\u0438\\u0442\\u044c " + p.getName() + "\"}},{\"text\":\"\\u2192\",\"color\":\"gray\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/msg " + p.getName() + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"\\u041e\\u0442\\u0432\\u0435\\u0442\\u0438\\u0442\\u044c " + p.getName() + "\"}},{\"text\":\" \",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/msg " + p.getName() + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"\\u041e\\u0442\\u0432\\u0435\\u0442\\u0438\\u0442\\u044c " + p.getName() + "\"}},{\"text\":\"\\u0412\\u044b\",\"color\":\"gold\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/msg " + p.getName() + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"\\u041e\\u0442\\u0432\\u0435\\u0442\\u0438\\u0442\\u044c " + p.getName() + "\"}},{\"text\":\"]\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/msg " + p.getName() + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"\\u041e\\u0442\\u0432\\u0435\\u0442\\u0438\\u0442\\u044c " + p.getName() + "\"}},{\"text\":\" :"+message+"\"}]");
                    console.sendMessage(ChatColor.GOLD + p.getName() + " → " + receiver.getName() +" :" + message);
                    receiver.playNote(receiver.getLocation(), Instrument.BELL, Note.flat(0, Note.Tone.A));
                }else{
                    p.sendMessage(
                            ChatColor.RED+"Игрок оффлайн");
                }
            }
            //Во тута должна быть логика сообщения и не забудь про колокольчик
            //и неверное использования тоже переопредели
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> list = Collections.emptyList();
        if(args.length==1){
            return null;
        } else{
            return list;
        }

    }
}
