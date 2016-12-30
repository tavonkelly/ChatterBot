package pw.teg.chatterbot.command;

import com.google.common.base.Joiner;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pw.teg.chatterbot.ChatterBot;
import pw.teg.chatterbot.bot.Session;


public class CBCommand implements CommandExecutor {

    private ChatterBot plugin;

    public CBCommand(ChatterBot p) {
        plugin = p;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdString, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return true;
        }

        final Player p = (Player) sender;

        if (!p.hasPermission("chatterbot.use")) {
            p.sendMessage(ChatColor.RED + "You don't have permission for this command.");
            return true;
        }

        if (args.length == 0) {
            p.sendMessage(plugin.getConf().getBotName() + ChatColor.WHITE + " Correct usage: " + ChatColor.YELLOW + "/cb <message>");
            return true;
        }

        final String message = Joiner.on(" ").join(args);

        if (plugin.getConf().isLoudModeEnabled()) {
            p.chat(plugin.getConf().getLoudFormat().replace("%bot%", plugin.getConf().getBotName())
                    .replace("%message%", message));
        } else {
            p.sendMessage(ChatColor.AQUA + "[" + p.getName() + "]" + ChatColor.WHITE + ": " + message);
        }

        p.sendMessage(ChatColor.AQUA + "Thinking...");

        Session session = plugin.getBotController().getSessionFor(p.getUniqueId());

        if (session == null) {
            session = plugin.getBotController().newSession(p.getUniqueId(), -1);
        }

        session.chatMessage(plugin, message, new Session.ChatCallback() {
            @Override
            public void callback(String response) {
                if (plugin.getConf().isLoudModeEnabled()) {
                    Bukkit.broadcastMessage(plugin.getConf().getChatFormat()
                            .replace("%name%", plugin.getConf().getBotName())
                            .replace("%message%", message));
                } else {
                    p.sendMessage(plugin.getConf().getChatFormat()
                            .replace("%name%", plugin.getConf().getBotName())
                            .replace("%message%", message));
                }
            }
        });

        return true;
    }
}
