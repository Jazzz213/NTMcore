package by.quaks.ntm;

import by.quaks.ntm.commands.*;
import by.quaks.ntm.listeners.*;
import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.dependencies.jda.api.entities.User;
import github.scarsz.discordsrv.util.DiscordUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class NTM extends JavaPlugin implements Listener {

    private DiscordSRVListener discordsrvListener = new DiscordSRVListener(this);
    private DeathListenerSRV deathListener = new DeathListenerSRV(this);
    static NTM main;
    public static NTM getInstance(){
        return main;
    }
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
        main = this;
        DiscordSRV.api.subscribe(discordsrvListener);
        DiscordSRV.api.subscribe(deathListener);
        getLogger().info("NTM - Основной плагин запущен");
        getCommand("msg").setExecutor(new TellCommand());
        getCommand("msg").setTabCompleter(new TellCommand());
        getCommand("burp").setExecutor(new BurpCommand());
        getCommand("ignore").setExecutor(new IgnoreCommand());
        getCommand("permmute").setExecutor(new PMuteCommand());
        getCommand("reply").setExecutor(new ReplyCommand());
        getCommand("autograph").setExecutor(new AutographCommand());
        Bukkit.getPluginManager().registerEvents(new ChatEventListener(), this);
        Bukkit.getPluginManager().registerEvents(new ClickOnPlayerEventListener(), this);
        Bukkit.getPluginManager().registerEvents(new MuteListener(),this);
        Bukkit.getPluginManager().registerEvents(new DeathListener(),this);
        getServer().getPluginManager().registerEvents(this, this);
        File file = new File(getDataFolder() + File.separator + "config.yml");
        if (!file.exists()){
            getConfig().addDefault("channels.admin", "000000000000000000");
            getConfig().addDefault("channels.report", "000000000000000000");
            getConfig().options().copyDefaults(true);
            saveConfig();
        } else {
            CheckConfig();
            saveConfig();
            reloadConfig();
        }
    }
    public void CheckConfig() {
        if(getConfig().get("channels.admin") == null){
            getConfig().set("channels.admin", "000000000000000000");
            saveConfig();
            reloadConfig();
        }
        if(getConfig().get("channels.report") == null){
            getConfig().set("channels.report", "000000000000000000");
            saveConfig();
            reloadConfig();
            getConfig();
        }
    }
    @Override
    public void onDisable() {
        getLogger().info("NTM - Основной плагин выключен");
        DiscordSRV.api.unsubscribe(discordsrvListener);
        DiscordSRV.api.unsubscribe(deathListener);
    }
}
