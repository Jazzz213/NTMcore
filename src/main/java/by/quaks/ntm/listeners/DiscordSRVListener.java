package by.quaks.ntm.listeners;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.api.ListenerPriority;
import github.scarsz.discordsrv.api.Subscribe;
import github.scarsz.discordsrv.api.events.*;
import github.scarsz.discordsrv.dependencies.jda.api.entities.Message;
import github.scarsz.discordsrv.dependencies.jda.api.entities.TextChannel;
import github.scarsz.discordsrv.dependencies.jda.api.entities.User;
import github.scarsz.discordsrv.util.DiscordUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DiscordSRVListener {
    TextComponent dot = new TextComponent(" • ");
    private final Plugin plugin;

    public DiscordSRVListener(Plugin plugin) {
        this.plugin = plugin;
    }
    /**
    @Subscribe
    public void discordReadyEvent(DiscordReadyEvent event) {
        // Example of using JDA's events
        // We need to wait until DiscordSRV has initialized JDA, thus we're doing this inside DiscordReadyEvent
        DiscordUtil.getJda().addEventListener(new JDAListener(plugin));

        // ... we can also do anything other than listen for events with JDA now,
        //plugin.getLogger().info("Chatting on Discord with " + DiscordUtil.getJda().getUsers().size() + " users!");
        // see https://ci.dv8tion.net/job/JDA/javadoc/ for JDA's javadoc
        // see https://github.com/DV8FromTheWorld/JDA/wiki for JDA's wiki
    }

    @Subscribe(priority = ListenerPriority.MONITOR)
    public void discordMessageReceived(DiscordGuildMessageReceivedEvent event) {
        // Example of logging a message sent in Discord

        //plugin.getLogger().info("Received a chat message on Discord: " + event.getMessage());
    }

    @Subscribe(priority = ListenerPriority.MONITOR)
    public void aMessageWasSentInADiscordGuildByTheBot(DiscordGuildMessageSentEvent event) {
        // Example of logging a message sent in Minecraft (being sent to Discord)

        //plugin.getLogger().info("A message was sent to Discord: " + event.getMessage());
    }

    @Subscribe
    public void accountsLinked(AccountLinkedEvent event) {
        // Example of broadcasting a message when a new account link has been made

        Bukkit.broadcastMessage(event.getPlayer().getName() + " just linked their MC account to their Discord user " + event.getUser() + "!");
    }
    @Subscribe
    public void accountUnlinked(AccountUnlinkedEvent event) {
        // Example of DM:ing user on unlink
        User user = DiscordUtil.getJda().getUserById(event.getDiscordId());

        // will be null if the bot isn't in a Discord server with the user (eg. they left the main Discord server)
        if (user != null) {

            // opens/retrieves the private channel for the user & sends a message to it (if retrieving the private channel was successful)
            user.openPrivateChannel().queue(privateChannel -> privateChannel.sendMessage("Your account has been unlinked").queue());
        }

        // Example of sending a message to a channel called "unlinks" (defined in the config.yml using the Channels option) when a user unlinks
        TextChannel textChannel = DiscordSRV.getPlugin().getDestinationTextChannelForGameChannelName("unlinks");

        // null if the channel isn't specified in the config.yml
        if (textChannel != null) {
            textChannel.sendMessage(event.getPlayer().getName() + " (" + event.getPlayer().getUniqueId() + ") has unlinked their associated Discord account: "
                    + (event.getDiscordUser() != null ? event.getDiscordUser().getName() : "<not available>") + " (" + event.getDiscordId() + ")").queue();
        } else {
            plugin.getLogger().warning("Channel called \"unlinks\" could not be found in the DiscordSRV configuration");
        }
    }**/
    @Subscribe(priority = ListenerPriority.MONITOR)
    public void AdminChatDiscordMessageReceived(DiscordGuildMessageReceivedEvent event) {
        // Example of logging a message sent in Discord

        //plugin.getLogger().info("Received a chat message on Discord: " + event.getMessage());
        if(event.getChannel().getId().equals("959935354536861727")){
            for (Player p1 : Bukkit.getOnlinePlayers()){
                if(p1.hasPermission("group.moderator")||p1.isOp()){
                    ClickEvent admin_suggest = new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "@");
                    TextComponent chatType = new TextComponent();
                    OfflinePlayer p = Bukkit.getOfflinePlayer(DiscordSRV.getPlugin().getAccountLinkManager().getUuid(event.getMember().getId()));
                    assert p != null;
                    TextComponent name = new TextComponent(p.getName());
                    TextComponent result = new TextComponent();
                    String msg = event.getMessage().getContentDisplay();
                    chatType.setText("[A] ");
                    chatType.setColor(ChatColor.RED);
                    chatType.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Админ-чат").create()));
                    chatType.setClickEvent(admin_suggest);
                    TextComponent line = new TextComponent("| ");
                    line.setColor(ChatColor.GRAY);
                    chat_comps(line, chatType, name, result);
                    result.addExtra(removeUrl(msg)+" ");
                    result.addExtra(getUrls(msg));
                    if(!event.getMessage().getAttachments().isEmpty()){
                        for (Message.Attachment a : event.getMessage().getAttachments()){
                            if(a.isImage()){
                                TextComponent t = new TextComponent(" [Изображение]");
                                toGray(t,a.getUrl());
                                result.addExtra(t);
                            } else if(a.isVideo()){
                                TextComponent t = new TextComponent(" [Видео]");
                                toGray(t,a.getUrl());
                                result.addExtra(t);
                            } else {
                                TextComponent t = new TextComponent(" [Вложение]");
                                toGray(t,a.getUrl());
                                result.addExtra(t);
                            }
                        }
                    }
                    p1.spigot().sendMessage(result);
                }
            }
        }
    }
    private String removeUrl(String commentstr)
    {
        String urlPattern = "((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern p = Pattern.compile(urlPattern,Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(commentstr);
        int i = 0;
        while (m.find()) {
            commentstr = commentstr.replaceAll(m.group(i),"").trim();
            i++;
        }
        return commentstr;
    }
    private TextComponent getUrls(String commentstr)
    {
        TextComponent t = new TextComponent();
        String urlPattern = "((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern p = Pattern.compile(urlPattern,Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(commentstr);
        int i = 0;
        while (m.find()) {
            commentstr = commentstr.replaceAll(m.group(i),"").trim();
            TextComponent t1 = new TextComponent("[Ссылка]");
            t1.setColor(ChatColor.GRAY);
            t1.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Перейти").create()));
            t1.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL,m.group(i)));
            t.addExtra(t1);
            i++;
        }
        return t;
    }

    private void toGray(TextComponent t, String link){
        t.setColor(ChatColor.GRAY);
        t.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL,link));
        t.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Нажмите чтобы посмотреть").create()));
    }
    private void chat_comps(TextComponent line, TextComponent chatType, TextComponent name, TextComponent result) {
        name.setColor(ChatColor.of("#9EFF86"));
        name.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Сообщение отправлено через Discord").create()));
        result.addExtra(chatType);
        result.addExtra(line);
        name.setUnderlined(true);
        result.addExtra(name);
        result.addExtra(dot);
    }
    @Subscribe
    public void discordMessageProcessed(DiscordGuildMessagePostProcessEvent event) {
        // Example of modifying a Discord -> Minecraft message
        UUID authorLinkedUuid = DiscordSRV.getPlugin().getAccountLinkManager().getUuid(event.getAuthor().getId());
        //event.setProcessedMessage(event.getProcessedMessage().replace("cat", "dog"));
        // dogs are superior to cats, obviously
        if(authorLinkedUuid!=null) {
            event.setProcessedMessage(event.getProcessedMessage().replace(event.getAuthor().getName(), Bukkit.getOfflinePlayer(authorLinkedUuid).getName()));
        } else {
            DiscordUtil.removeRolesFromMember(event.getMember(), DiscordUtil.getRole("960690850357186560"));
            event.getMessage().addReaction("\uD83C\uDDF7")
                    .queue(v2 -> event.getMessage().addReaction("\uD83C\uDDEA")                                    // реакция not x linked
                            .queue(v3 -> event.getMessage().addReaction("\uD83C\uDDF1")
                                    .queue(v4 -> event.getMessage().addReaction("\uD83C\uDDEE")
                                            .queue(v5 -> event.getMessage().addReaction("\uD83C\uDDF3")
                                                    .queue(v6 -> event.getMessage().addReaction("\uD83C\uDDF0")
                                                            .queue())))));
            User user = DiscordUtil.getJda().getUserById(event.getMember().getId());
            if (user != null) {
                user.openPrivateChannel().queue(privateChannel -> privateChannel.sendMessage("Перепривяжите свой аккаунт используя /discord link в игре").queue());
            }
            event.setCancelled(true);
        }
    }

    @Subscribe
    public void prefixRemover(GameChatMessagePreProcessEvent event){
        if(event.getMessage().startsWith("$") || event.getMessage().startsWith("!")){
            event.setMessage(event.getMessage().replaceFirst("[$!]", ""));
        }
        if((event.getPlayer().hasPermission("moderator")||event.getPlayer().isOp())&&event.getMessage().startsWith("@a")){
            event.setMessage(event.getMessage().replaceFirst("[@a]", ""));
            DiscordUtil.sendMessage(DiscordUtil.getTextChannelById("959935354536861727"),event.getMessage());
        }
    }

}
