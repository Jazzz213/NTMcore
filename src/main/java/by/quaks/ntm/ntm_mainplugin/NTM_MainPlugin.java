package by.quaks.ntm.ntm_mainplugin;

import by.quaks.ntm.ntm_mainplugin.commands.IgnoreCommand;
import by.quaks.ntm.ntm_mainplugin.commands.PMuteCommand;
import by.quaks.ntm.ntm_mainplugin.commands.TellCommand;
import by.quaks.ntm.ntm_mainplugin.commands.BurpCommand;
import by.quaks.ntm.ntm_mainplugin.listeners.ChatEventListener;
import by.quaks.ntm.ntm_mainplugin.listeners.ClickOnPlayerEventListener;
import by.quaks.ntm.ntm_mainplugin.listeners.DiscordSRVListener;
import by.quaks.ntm.ntm_mainplugin.listeners.MuteListener;
import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.dependencies.jda.api.entities.User;
import github.scarsz.discordsrv.util.DiscordUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class NTM_MainPlugin extends JavaPlugin implements Listener {

    private DiscordSRVListener discordsrvListener = new DiscordSRVListener(this);

    @Override
    public void onEnable() {
        DiscordSRV.api.subscribe(discordsrvListener);
        getServer().getPluginManager().registerEvents(this, this);
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

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerJoin(PlayerJoinEvent event) {
        String discordId = DiscordSRV.getPlugin().getAccountLinkManager().getDiscordId(event.getPlayer().getUniqueId());
        if (discordId == null) {
            event.getPlayer().sendMessage(ChatColor.RED + "You are not linked");
            return;
        }

        User user = DiscordUtil.getJda().getUserById(discordId);
        if (user == null) {
            event.getPlayer().sendMessage(ChatColor.YELLOW + "Couldn't find the user you're linked to");
            return;
        }

        event.getPlayer().sendMessage(ChatColor.GREEN + "You're linked to " + user.getAsTag());
    }



    @Override
    public void onDisable() {
        // Plugin shutdown logic
        //System.out.println("NTM - Основной плагин выключен");
        getLogger().info("NTM - Основной плагин выключен");
        DiscordSRV.api.unsubscribe(discordsrvListener);
    }
}
