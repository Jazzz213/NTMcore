package by.quaks.ntm.ntm_mainplugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class NTM_MainPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("NTM - Основной плагин запущен");
        Bukkit.getPluginManager().registerEvents(new TestEvents(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("NTM - Основной плагин выключен");
    }
}
