package me.xTDKx.CB.Events;

import com.google.code.chatterbotapi.ChatterBotSession;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BotAutoDeactivateEvent extends Event {

    private Player player;
    private ChatterBotSession session;

    private HandlerList handlerList;

    public BotAutoDeactivateEvent(Player player, ChatterBotSession session) {
        this.player = player;
        this.session = session;
    }

    public Player getPlayer() {
        return player;
    }

    public ChatterBotSession getSession() {
        return session;
    }

    public HandlerList getHandlerList() {
        return handlerList;
    }

    public HandlerList getHandlers() {
        return handlerList;
    }

}
