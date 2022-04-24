package by.quaks.ntm.listeners;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class DisableMapCopy implements Listener {
    @EventHandler
    public void craftItem(PrepareItemCraftEvent e) {
        if(e.getRecipe()!=null){
            Material itemType = e.getRecipe().getResult().getType();
            //Bukkit.spigot().broadcast(new ComponentBuilder(e.getInventory().getType().name()).create());
            for (ItemStack i : e.getInventory().getMatrix()) {
                if (i != null) {
                    if (i.getType() == Material.FILLED_MAP && i.getItemMeta().getLore().contains("Некопируемая")) {
                        if (itemType == Material.AIR) {
                            e.getInventory().setResult(new ItemStack(Material.AIR));
                        }
                    }
                }
            }
        }
    }
    @EventHandler
    public void craftItem1(InventoryClickEvent e){
        HumanEntity p = e.getWhoClicked();
        if(e.getInventory().getType()==InventoryType.CARTOGRAPHY){
            if(e.getClickedInventory()!=null){
                if (e.getClickedInventory().getItem(0) != null && e.getClickedInventory().getItem(1) != null) {
                    if(e.getClickedInventory().getItem(0).getItemMeta().getLore()!=null){
                        if (e.getClickedInventory().getItem(0).getType() == Material.FILLED_MAP && e.getClickedInventory().getItem(0).getItemMeta().getLore().contains("Некопируемая") && e.getClickedInventory().getItem(1).getType() == Material.MAP) {
                            e.getClickedInventory().setItem(2, e.getClickedInventory().getItem(0));
                            p.getInventory().addItem(new ItemStack(Material.MAP, 1));
                            return;
                        }
                    }
                }
            }
        }
    }
}
