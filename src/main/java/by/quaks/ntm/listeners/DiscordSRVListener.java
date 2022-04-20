package by.quaks.ntm.listeners;

import by.quaks.ntm.NTM;
import com.vdurmont.emoji.EmojiParser;
import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.api.ListenerPriority;
import github.scarsz.discordsrv.api.Subscribe;
import github.scarsz.discordsrv.api.events.*;
import github.scarsz.discordsrv.dependencies.jda.api.entities.Message;
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
    public static String removeUrl(String commentstr) {
        // rid of ? and & in urls since replaceAll can't deal with them
        String commentstr1 = commentstr.replaceAll("\\?", "").replaceAll("\\&", "");

        String urlPattern = "((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern p = Pattern.compile(urlPattern,Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(commentstr);
        int i = 0;
        while (m.find()) {
            commentstr = commentstr1.replaceAll(m.group(i).replaceAll("\\?", "").replaceAll("\\&", ""),"").trim();
            i++;
        }
        return commentstr;
    }
    public static TextComponent getUrls(String commentstr) {
        TextComponent t = new TextComponent();
        String urlPattern = "((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern p = Pattern.compile(urlPattern,Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(commentstr);
        int i = 0;
        while (m.find()) {
            //commentstr = commentstr.replaceAll(m.group(i),"").trim();
            TextComponent t1 = new TextComponent(" [Ссылка]");
            t1.setColor(ChatColor.GRAY);
            t1.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Перейти").color(ChatColor.GRAY).create()));
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
    @Subscribe(priority = ListenerPriority.MONITOR)
    public void AdminChatDiscordMessageReceived(DiscordGuildMessageReceivedEvent event) {
        if(event.getChannel().getId().equals(NTM.getInstance().getConfig().getString("channels.admin"))){
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
    @Subscribe
    public void discordMessageProcessed(DiscordGuildMessagePostProcessEvent event) {
        // modifying a Discord -> Minecraft message
        UUID authorLinkedUuid = DiscordSRV.getPlugin().getAccountLinkManager().getUuid(event.getAuthor().getId());
        //event.setProcessedMessage(event.getProcessedMessage().replace("cat", "dog")); // dogs are superior to cats, obviously
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
            return;
        } // UNLINK SYSTEM AYE
        event.setCancelled(true);
        TextComponent chat_type = new TextComponent("[D]");
        chat_type.setColor(ChatColor.of("#9e86ff"));
        chat_type.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder("Cообщение в Discord").color(ChatColor.GRAY).create()));
        TextComponent line = new TextComponent(" | ");
        line.setColor(ChatColor.GRAY);
        TextComponent name = new TextComponent(Bukkit.getOfflinePlayer(authorLinkedUuid).getName());
        name.setColor(ChatColor.of("#9EFF86"));
        name.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder("Скопировать: " + event.getAuthor().getAsTag()).color(ChatColor.GRAY).create()));
        name.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD,event.getAuthor().getAsTag()));
        TextComponent dot = new TextComponent(" • ");
        dot.setColor(ChatColor.GRAY);
        TextComponent result = new TextComponent();
        result.addExtra(chat_type);
        result.addExtra(line);
        result.addExtra(name);
        if(event.getMessage().getReferencedMessage()!=null){
            UUID authorLinkedUuid2 = DiscordSRV.getPlugin().getAccountLinkManager().getUuid(event.getMessage().getReferencedMessage().getAuthor().getId());
            String name1;
            if(authorLinkedUuid2!=null){
                name1 = Bukkit.getOfflinePlayer(authorLinkedUuid2).getName();
            }else{
                name1 = event.getMessage().getReferencedMessage().getAuthor().getName();
            }
            TextComponent reply = new TextComponent(", отвечая "+name1);
            reply.setColor(ChatColor.of("#9EFF86"));
            if(!event.getMessage().getReferencedMessage().getContentDisplay().equals("")){
                reply.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(name1 + " • " + event.getMessage().getReferencedMessage().getContentDisplay())
                        .color(ChatColor.GRAY)
                        .create()));
            }
            result.addExtra(reply);
        }
        result.addExtra(dot);
        result.addExtra(removeUrl(EmojiParser.parseToAliases(event.getMessage().getContentDisplay())));
        result.addExtra(getUrls(EmojiParser.parseToAliases(event.getMessage().getContentDisplay())));
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
        Bukkit.spigot().broadcast(result);
    }

    @Subscribe
    public void prefixHandler(GameChatMessagePreProcessEvent event){
        if(event.getMessage().startsWith("$") || event.getMessage().startsWith("!")){
            event.setMessage(event.getMessage().replaceFirst("[$!]", ""));
        }
        if((event.getPlayer().hasPermission("moderator")||event.getPlayer().isOp())&&event.getMessage().startsWith("@a")){
            event.setMessage(event.getMessage().replaceFirst("[@a]", ""));
            DiscordUtil.sendMessage(DiscordUtil.getTextChannelById("NTM.getInstance().getConfig().getString(\"channels.admin\")"),event.getMessage());
        }
    }

}
