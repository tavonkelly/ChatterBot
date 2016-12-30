package pw.teg.chatterbot.event;

import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import pw.teg.chatterbot.bot.Session;

public class BotUnAssignEvent extends Event implements Cancellable {

    private CommandSender commandSender;
    private Session session;

    private boolean cancelled;
    private HandlerList handlerList;

    public BotUnAssignEvent(CommandSender commandSender, Session session) {
        this.commandSender = commandSender;
        this.session = session;
    }

    public CommandSender getCommandSender() {
        return commandSender;
    }

    public void setCommandSender(CommandSender commandSender) {
        this.commandSender = commandSender;
    }

    public Session getSession() {
        return session;
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
