package me.xTDKx.CB.Events;

import com.google.code.chatterbotapi.ChatterBotSession;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BotAutoActivateEvent extends Event implements Cancellable {

    private Player player;
    private ChatterBotSession session;
    private String[] activationWords;

    private HandlerList handlerList;
    private boolean cancelled;

    public BotAutoActivateEvent(Player player, ChatterBotSession session, String[] activationWords) {
        this.player = player;
        this.session = session;
        this.activationWords = activationWords;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public ChatterBotSession getSession() {
        return session;
    }

    public void setSession(ChatterBotSession session) {
        this.session = session;
    }

    public String[] getActivationWords() {
        return activationWords;
    }

    public void setActivationWords(String[] activationWords) {
        this.activationWords = activationWords;
    }

    public HandlerList getHandlerList() {
        return handlerList;
    }

    public HandlerList getHandlers() {
        return handlerList;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
