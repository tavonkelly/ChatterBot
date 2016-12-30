package pw.teg.chatterbot.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pw.teg.chatterbot.ChatterBot;
import pw.teg.chatterbot.bot.Session;
import pw.teg.chatterbot.event.BotAssignEvent;
import pw.teg.chatterbot.event.BotUnAssignEvent;

public class CBAssign implements CommandExecutor {

    private ChatterBot plugin;

    public CBAssign(ChatterBot plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdString, String[] args) {
        if (!sender.hasPermission("chatterbot.admin")) {
            sender.sendMessage(ChatColor.RED + "You don't have permission for this command");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(plugin.getConf().getBotName() + ChatColor.GRAY + " Help:");
            sender.sendMessage(ChatColor.AQUA + "/cbassign <player> " + ChatColor.GRAY + "- Make ChatterBot reply to everything a player says");
            return true;
        }

        if (args.length == 1) {
            Player p = Bukkit.getPlayer(args[0]);

            if (p == null) {
                sender.sendMessage(plugin.getConf().getBotName() + ChatColor.RED + "Player not found");
                return true;
            }

            Session session = plugin.getBotController().getSessionFor(p.getUniqueId());

            if (session == null) {
                BotAssignEvent event = new BotAssignEvent(sender, p);

                if (!event.isCancelled()) {
                    plugin.getBotController().newSession(p.getUniqueId(), -1).setAutoReply(true);
                    event.getCommandSender().sendMessage(plugin.getConf().getBotName() + " " + ChatColor.AQUA + p.getName() + ChatColor.GRAY + " has been assigned");
                }
            } else {
                BotUnAssignEvent event = new BotUnAssignEvent(sender, session);

                if (!event.isCancelled()) {
                    plugin.getBotController().deleteSession(p.getUniqueId());
                    sender.sendMessage(plugin.getConf().getBotName() + " " + ChatColor.AQUA + p.getName() + ChatColor.GRAY + " has been un-assigned");
                }
            }

            return true;
        }

        sender.sendMessage(ChatColor.RED + "Correct usage: " + ChatColor.AQUA + "/cbassign <player>");
        return false;
    }
}
