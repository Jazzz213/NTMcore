package by.quaks.ntm.util;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;

//Не используеться - до лучших времён
public class TextComponentBuilder {
    private TextComponent dummy = new TextComponent();

    public TextComponentBuilder() {
    }

    public TextComponentBuilder(String s) {
        dummy.addExtra(s);
    }

    public TextComponentBuilder(TextComponent s) {
        dummy = s;
    }

    public TextComponentBuilder append(TextComponent t){
        dummy.addExtra(t);
        return this;
    }

    public TextComponentBuilder hoverEvent(HoverEvent e){
        dummy.setHoverEvent(e);
        return this;
    }

    public TextComponentBuilder hoverEvent(String text, ChatColor c){
        dummy.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder(text).color(c).create()));
        return this;
    }

    public TextComponentBuilder clickEvent(ClickEvent e){
        dummy.setClickEvent(e);
        return this;
    }

    public TextComponentBuilder clickEvent(String text){
        dummy.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,text));
        return this;
    }

    public TextComponentBuilder color(ChatColor c){
        dummy.setColor(c);
        return this;
    }

    public TextComponent create() {
        return dummy;
    }
}
