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

public class CBAssign implements CommandExecutor{
    public static HashMap<String, ChatterBotSession> assignie = new HashMap<String, ChatterBotSession>();

    private ChatterBot plugin;

    public CBAssign(ChatterBot p){
        plugin = p;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdString, String[] args) {
        if (sender.hasPermission("chatterbot.admin")){
            if (args.length == 0){
                sender.sendMessage(ChatterBot.chatterBotName + ChatColor.GRAY + " Help:");
                sender.sendMessage(ChatColor.AQUA + "/cb assign <player> " + ChatColor.GRAY + "- Make ChatterBot reply to everything a player says");
                sender.sendMessage(ChatColor.AQUA + "/cb reload " + ChatColor.GRAY + "- Reload ChatterBot's configuration files");
            } else if (args.length == 1){
                if (args[0].equalsIgnoreCase("assign")){
                    sender.sendMessage(ChatterBot.chatterBotName + ChatColor.RED + " Incorrect Usage: /cb assign <player>");
                } else if (args[0].equalsIgnoreCase("reload")){
                    plugin.reloadConfig();
                    sender.sendMessage(ChatterBot.chatterBotName + ChatColor.GRAY + " Configuration reloaded");
                } else {
                    sender.sendMessage(ChatterBot.chatterBotName + ChatColor.GRAY + " Help:");
                    sender.sendMessage(ChatColor.AQUA + "/cb assign <player> " + ChatColor.GRAY + "- Make ChatterBot reply to everything a player says");
                    sender.sendMessage(ChatColor.AQUA + "/cb reload " + ChatColor.GRAY + "- Reload ChatterBot's configuration files");
                }
            } else if (args.length == 2){
                if (Bukkit.getPlayer(args[1]) !=null){
                    Player p = Bukkit.getPlayer(args[1]);
                    if (assignie.containsKey(p.getName())){
                        assignie.remove(p.getName());
                        sender.sendMessage(ChatterBot.chatterBotName + " " + ChatColor.AQUA + p.getName() + ChatColor.GRAY + " has been un-assigned");
                    }else{
                        assignie.put(p.getName(), ChatterBot.bot1.createSession());
                        sender.sendMessage(ChatterBot.chatterBotName + " " + ChatColor.AQUA + p.getName() + ChatColor.GRAY + " has been assigned");
                    }
                }else{
                    sender.sendMessage(ChatterBot.chatterBotName + ChatColor.RED + "Player not found");
                }
            } else {
                sender.sendMessage(ChatterBot.chatterBotName + ChatColor.GRAY + " Help:");
                sender.sendMessage(ChatColor.AQUA + "/cb assign <player> " + ChatColor.GRAY + "- Make ChatterBot reply to everything a player says");
                sender.sendMessage(ChatColor.AQUA + "/cb reload " + ChatColor.GRAY + "- Reload ChatterBot's configuration files");
            }
        }else{
            sender.sendMessage(ChatColor.RED + "You don't have permission for this command");
        }
        return false;
    }
}
