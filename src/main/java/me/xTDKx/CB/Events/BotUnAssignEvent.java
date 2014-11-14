package me.xTDKx.CB.Events;

import com.google.code.chatterbotapi.ChatterBotSession;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BotUnAssignEvent extends Event implements Cancellable {

    private CommandSender commandSender;
    private Player unAssigned;
    private ChatterBotSession session;

    private boolean cancelled;
    private HandlerList handlerList;

    public BotUnAssignEvent(CommandSender commandSender, Player unAssigned, ChatterBotSession session) {
        this.commandSender = commandSender;
        this.unAssigned = unAssigned;
        this.session = session;
    }

    public CommandSender getCommandSender() {
        return commandSender;
    }

    public void setCommandSender(CommandSender commandSender) {
        this.commandSender = commandSender;
    }

    public Player getUnAssigned() {
        return unAssigned;
    }

    public void setUnAssigned(Player unAssigned) {
        this.unAssigned = unAssigned;
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
