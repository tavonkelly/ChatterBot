package pw.teg.chatterbot.event;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BotAssignEvent extends Event implements Cancellable {

    private CommandSender commandSender;
    private Player player;

    private boolean cancelled;
    private HandlerList handlerList;

    public BotAssignEvent(CommandSender commandSender, Player player) {
        this.commandSender = commandSender;
        this.player = player;
    }

    public CommandSender getCommandSender() {
        return commandSender;
    }

    public void setCommandSender(CommandSender commandSender) {
        this.commandSender = commandSender;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
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
