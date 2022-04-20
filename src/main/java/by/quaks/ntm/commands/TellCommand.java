package by.quaks.ntm.commands;

import by.quaks.ntm.util.ChatUtil;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class TellCommand implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player p = (Player) sender;
            if(args.length==0){
                p.sendMessage(
                        ChatColor.RED+"Неизвестная или неполная команда:\n"+
                                ChatColor.GRAY+"/"+label+" "+ChatColor.RED+"[никнейм] [сообщение]");
            }
            if(args.length==1){
                Player receiver = Bukkit.getPlayerExact(args[0]);
                if(receiver!=null){
                p.sendMessage(
                        ChatColor.RED+"Неизвестная или неполная команда:\n"+
                        ChatColor.GRAY+"/"+label+" "+args[0]+ChatColor.RED+" [сообщение]");
                }else{p.sendMessage(ChatColor.RED+"Игрок оффлайн");}}
            if(args.length>1){
                Player receiver = Bukkit.getPlayerExact(args[0]);
                if(receiver==null){
                    p.sendMessage(ChatColor.RED+"Игрок оффлайн");
                    return true;
                }
                if (receiver.getScoreboardTags().contains("ignore."+p.getName())){
                    p.sendMessage(ChatColor.RED + "Игрок вас игнорирует");
                    return true;
                }
                args[0]="";
                String message = String.join(" ", args);
                ChatUtil.sendPrivateMessage(
                        ChatUtil.genTellMessage(p.getName(),receiver.getName(),message,receiver.getName()),p
                );
                ChatUtil.sendPrivateMessage(
                        ChatUtil.genTellMessage(p.getName(),receiver.getName(),message,p.getName()),receiver
                );
                receiver.playNote(receiver.getLocation(), Instrument.BELL, Note.flat(0, Note.Tone.A));
                Bukkit.getLogger().info("["+p.getName()+" → "+receiver.getName()+"] :"+message);
                String[] p_tags = p.getScoreboardTags().toArray(new String[0]);
                String[] r_tags = receiver.getScoreboardTags().toArray(new String[0]);
                for(String tag : p_tags){
                    if(tag.startsWith("reply.")){
                        p.removeScoreboardTag(tag);
                    }
                }
                for(String tag : r_tags){
                    if(tag.startsWith("reply.")){
                        receiver.removeScoreboardTag(tag);
                    }
                }
                receiver.addScoreboardTag("reply."+p.getName());
                p.addScoreboardTag("reply."+receiver.getName());
            }
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
