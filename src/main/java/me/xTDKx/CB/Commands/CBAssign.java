package me.xTDKx.CB.Commands;

import com.google.code.chatterbotapi.ChatterBotSession;
import me.xTDKx.CB.ChatterBot;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class CBAssign implements CommandExecutor {
    public static HashMap<String, ChatterBotSession> assignie = new HashMap<String, ChatterBotSession>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdString, String[] args) {
        if (sender.hasPermission("chatterbot.admin")) {
            if (args.length == 0) {
                sender.sendMessage(ChatterBot.chatterBotName + ChatColor.GRAY + " Help:");
                sender.sendMessage(ChatColor.AQUA + "/cbassign <player> " + ChatColor.GRAY + "- Make ChatterBot reply to everything a player says");
            } else if (args.length == 1) {
                if (Bukkit.getPlayer(args[0]) != null) {
                    Player p = Bukkit.getPlayer(args[0]);
                    if (assignie.containsKey(p.getName())) {
                        assignie.remove(p.getName());
                        sender.sendMessage(ChatterBot.chatterBotName + " " + ChatColor.AQUA + p.getName() + ChatColor.GRAY + " has been un-assigned");
                    } else {
                        assignie.put(p.getName(), ChatterBot.bot1.createSession());
                        sender.sendMessage(ChatterBot.chatterBotName + " " + ChatColor.AQUA + p.getName() + ChatColor.GRAY + " has been assigned");
                    }
                } else {
                    sender.sendMessage(ChatterBot.chatterBotName + ChatColor.RED + "Player not found");
                }
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You don't have permission for this command");
        }
        return false;
    }
}
