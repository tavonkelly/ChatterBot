package me.xTDKx.CB.Events;

import com.google.code.chatterbotapi.ChatterBotSession;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BotAssignEvent extends Event implements Cancellable {

    private CommandSender commandSender;
    private Player assingedTo;
    private ChatterBotSession session;

    private boolean cancelled;
    private HandlerList handlerList;

    public BotAssignEvent(CommandSender commandSender, Player assingedTo, ChatterBotSession session) {
        this.commandSender = commandSender;
        this.assingedTo = assingedTo;
        this.session = session;
    }

    public CommandSender getCommandSender() {
        return commandSender;
    }

    public void setCommandSender(CommandSender commandSender) {
        this.commandSender = commandSender;
    }

    public Player getAssingedTo() {
        return assingedTo;
    }

    public void setAssingedTo(Player assingedTo) {
        this.assingedTo = assingedTo;
    }

    public ChatterBotSession getSession() {
        return session;
    }

    public void setSession(ChatterBotSession session) {
        this.session = session;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public HandlerList getHandlerList() {
        return handlerList;
    }

    public HandlerList getHandlers() {
        return handlerList;
    }
}
