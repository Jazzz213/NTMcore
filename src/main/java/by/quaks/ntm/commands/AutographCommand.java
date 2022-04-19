package by.quaks.ntm.commands;

import by.quaks.ntm.NTM;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AutographCommand implements CommandExecutor {
    File autographFile = new File(NTM.getInstance().getDataFolder(), "autographConfig.yml");
    FileConfiguration autographConfig = YamlConfiguration.loadConfiguration(autographFile);
    List<String> autographItemList = autographConfig.getStringList("autographItemList");

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("autograph") || sender instanceof Player) {
            Player p = ((Player) sender).getPlayer();
            assert p != null;
            ItemStack item = p.getItemInHand();
            if (item.getType() == null || item.getType().toString().contains("AIR")) {
                p.sendMessage(autographConfig.getString("itemUndefined"));
            } else if (!autographItemList.contains(item.getType().toString())) {
                p.sendMessage(ChatColor.RED + autographConfig.getString("itemNotTheList"));
                return true;
            } else {
                ItemMeta itemM = item.getItemMeta();
                List<String> lore = new ArrayList<>();
                assert itemM != null;
                if (itemM.getLore() != null) {
                    lore = itemM.getLore();
                }
                if (lore.toString().contains("Подпись")) {
                    p.sendMessage(ChatColor.RED + autographConfig.getString("itemContainsMaxAutographs"));
                    return true;
                }
                lore.add(ChatColor.GRAY + "Подпись " + ChatColor.AQUA + ((Player) sender).getDisplayName());
                itemM.setLore(lore);
                item.setItemMeta(itemM);
            }
        }
        return true;
    }
}
