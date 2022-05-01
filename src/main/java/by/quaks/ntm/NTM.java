package by.quaks.ntm;

import by.quaks.ntm.commands.*;
import by.quaks.ntm.files.MuteList;
import by.quaks.ntm.listeners.*;
import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.dependencies.jda.api.entities.User;
import github.scarsz.discordsrv.util.DiscordUtil;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Date;

public final class NTM extends JavaPlugin implements Listener {
    //test
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

    public void scheduleTimer(Plugin plugin, final World world) {
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            public void run() {
                long time = world.getTime();
                if (time == 13000) {
                    Date d2 = new Date();
                    for(OfflinePlayer p : plugin.getServer().getOfflinePlayers()){
                        if (MuteList.get().getBoolean(p.getName() + ".muted")){
                            Date d1 = (Date) MuteList.get().get(p.getName() + ".mute_date");
                            if (!d1.after(d2)) {
                                MuteList.get().set(p.getName() + ".muted", false);
                                MuteList.get().set(p.getName() + ".mute_date", null);
                                MuteList.save();
                                DiscordUtil.removeRolesFromMember(DiscordUtil.getMemberById(DiscordSRV.getPlugin().getAccountLinkManager().getDiscordId(p.getUniqueId())), DiscordUtil.getRole("960513488478949416"));
                            }
                        }
                    }
                }
            }
        }, 1, 1);
    }

    @Override
    public void onEnable() {
        scheduleTimer(this,Bukkit.getServer().getWorld("world"));
        MuteList.setup();
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


        main = this;
        DiscordSRV.api.subscribe(discordsrvListener);
        DiscordSRV.api.subscribe(deathListener);
        getLogger().info("NTM - Основной плагин запущен");
        getCommand("msg").setExecutor(new TellCommand());
        getCommand("msg").setTabCompleter(new TellCommand());
        getCommand("burp").setExecutor(new BurpCommand());
        getCommand("ignore").setExecutor(new IgnoreCommand());
        getCommand("mute").setExecutor(new MuteCommand());
        getCommand("reply").setExecutor(new ReplyCommand());
        getCommand("autograph").setExecutor(new AutographCommand());
        getCommand("report").setExecutor(new ReportCommand());
        getCommand("protect").setExecutor(new ProtectCommand());
        Bukkit.getPluginManager().registerEvents(new ChatEventListener(), this);
        Bukkit.getPluginManager().registerEvents(new ClickOnPlayerEventListener(), this);
        Bukkit.getPluginManager().registerEvents(new MuteListener(),this);
        Bukkit.getPluginManager().registerEvents(new DeathListener(),this);
        Bukkit.getPluginManager().registerEvents(new DisableMapCopy(),this);
        getServer().getPluginManager().registerEvents(this, this);
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
