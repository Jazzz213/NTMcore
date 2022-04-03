package by.quaks.ntm.ntm_mainplugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class NTM_MainPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        //System.out.println("NTM - Основной плагин запущен");
        getLogger().info("NTM - Основной плагин запущен");
        getCommand("msg").setExecutor(new TellCommand());
        getCommand("msg").setTabCompleter(new TellCommand());
        Bukkit.getPluginManager().registerEvents(new AsnycChatEventListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        //System.out.println("NTM - Основной плагин выключен");
        getLogger().info("NTM - Основной плагин выключен");
    }
}
