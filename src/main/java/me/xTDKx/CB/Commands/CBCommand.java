package me.xTDKx.CB.Commands;

import com.google.code.chatterbotapi.ChatterBotSession;
import me.xTDKx.CB.ChatterBot;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class CBCommand implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdString, String[] args) {
        if (sender instanceof Player){
            final Player p = (Player) sender;
            if (p.hasPermission("chatterbot.use")){
                if (args.length == 0){
                    p.sendMessage(ChatColor.AQUA + "[ChatterBot] " + ChatColor.WHITE + "Correct usage: " + ChatColor.YELLOW + "/cb <message>");
                }else{
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0 ; i < args.length ; i++){
                        sb.append(" ").append(args[i]);
                    }
                    final String input = sb.toString().trim();

                    p.sendMessage(ChatColor.AQUA + "ChatterBot is thinking...");
                    if (ChatterBot.sessions.containsKey(p.getName())){
                        final ChatterBotSession cbSession = ChatterBot.sessions.get(p.getName());

                        Bukkit.getScheduler().runTaskAsynchronously(ChatterBot.instance, new Runnable() {
                            @Override
                            public void run() {
                                final StringBuilder sb = new StringBuilder();
                                try{
                                    sb.append(cbSession.think(input));
                                }catch (Exception e){
                                    e.printStackTrace();
                                }

                                Bukkit.getScheduler().runTask(ChatterBot.instance, new Runnable() {
                                    @Override
                                    public void run() {
                                        p.sendMessage(ChatColor.AQUA + "[ChatterBot]" + ChatColor.WHITE + ": " + sb.toString());
                                    }
                                });
                            }
                        });
                    } else {
                        final ChatterBotSession cbSession = ChatterBot.bot1.createSession();
                        ChatterBot.sessions.put(p.getName(), cbSession);

                        Bukkit.getScheduler().runTaskAsynchronously(ChatterBot.instance, new Runnable() {
                            @Override
                            public void run() {
                                final StringBuilder sb = new StringBuilder();
                                try{
                                    sb.append(cbSession.think(input));
                                }catch (Exception e){
                                    e.printStackTrace();
                                }

                                Bukkit.getScheduler().runTask(ChatterBot.instance, new Runnable() {
                                    @Override
                                    public void run() {
                                        p.sendMessage(ChatColor.AQUA + "[ChatterBot]" + ChatColor.WHITE + ": " + sb.toString());
                                    }
                                });
                            }
                        });
                    }
                }
            }else{
                p.sendMessage(ChatColor.RED + "You don't have permission for this command.");
            }
        }else{
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
        }

        return false;
    }
}
