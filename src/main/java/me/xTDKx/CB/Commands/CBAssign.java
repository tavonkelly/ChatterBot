package me.xTDKx.CB.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CBAssign implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdString, String[] args) {
        if (sender.hasPermission("chatterbot.admin")){
            //Not done (Of course)
        }else{
            sender.sendMessage(ChatColor.RED + "You don't have permission for this command");
        }
        return false;
    }
}
