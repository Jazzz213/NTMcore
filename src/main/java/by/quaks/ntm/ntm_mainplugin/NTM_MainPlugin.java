package by.quaks.ntm.ntm_mainplugin;

import by.quaks.ntm.ntm_mainplugin.commands.IgnoreCommand;
import by.quaks.ntm.ntm_mainplugin.commands.PMuteCommand;
import by.quaks.ntm.ntm_mainplugin.commands.TellCommand;
import by.quaks.ntm.ntm_mainplugin.commands.BurpCommand;
import by.quaks.ntm.ntm_mainplugin.listeners.ChatEventListener;
import by.quaks.ntm.ntm_mainplugin.listeners.ClickOnPlayerEventListener;
import by.quaks.ntm.ntm_mainplugin.listeners.MuteListener;
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
        getCommand("burp").setExecutor(new BurpCommand());
        getCommand("ignore").setExecutor(new IgnoreCommand());
        getCommand("permmute").setExecutor(new PMuteCommand());
        Bukkit.getPluginManager().registerEvents(new ChatEventListener(), this);
        Bukkit.getPluginManager().registerEvents(new ClickOnPlayerEventListener(), this);
        Bukkit.getPluginManager().registerEvents(new MuteListener(),this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        //System.out.println("NTM - Основной плагин выключен");
        getLogger().info("NTM - Основной плагин выключен");
    }
}
