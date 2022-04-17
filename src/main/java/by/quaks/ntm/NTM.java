package by.quaks.ntm;

import by.quaks.ntm.commands.BurpCommand;
import by.quaks.ntm.commands.IgnoreCommand;
import by.quaks.ntm.commands.PMuteCommand;
import by.quaks.ntm.commands.TellCommand;
import by.quaks.ntm.listeners.*;
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

public final class NTM extends JavaPlugin implements Listener {

    private DiscordSRVListener discordsrvListener = new DiscordSRVListener(this);
    private DeathListenerSRV deathListener = new DeathListenerSRV(this);

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerJoin(PlayerJoinEvent event) {
        String discordId = DiscordSRV.getPlugin().getAccountLinkManager().getDiscordId(event.getPlayer().getUniqueId());
        if (discordId == null) {
            event.getPlayer().sendMessage(ChatColor.RED + "Ваш аккаунт не связан с учётной записью Discord, используйте /discord link");
            return;
        }

        User user = DiscordUtil.getJda().getUserById(discordId);
        if (user == null) {
            event.getPlayer().sendMessage(ChatColor.YELLOW + "Не удаётся найти Discord-аккаунт, к которому привязана ваша учётная запись");
            return;
        }

        //event.getPlayer().sendMessage(ChatColor.GREEN + "You're linked to " + user.getAsTag());
    }

    @Override
    public void onEnable() {
        DiscordSRV.api.subscribe(discordsrvListener);
        DiscordSRV.api.subscribe(deathListener);
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
        Bukkit.getPluginManager().registerEvents(new DeathListener(),this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        //System.out.println("NTM - Основной плагин выключен");
        getLogger().info("NTM - Основной плагин выключен");
        DiscordSRV.api.unsubscribe(discordsrvListener);
        DiscordSRV.api.unsubscribe(deathListener);
    }
}
