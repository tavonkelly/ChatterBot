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
    private ChatterBot plugin;

    public CBCommand(ChatterBot p){
        plugin = p;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdString, String[] args) {
        if (sender instanceof Player){
            final Player p = (Player) sender;
            if (p.hasPermission("chatterbot.use")){
                if (args.length == 0){
                    p.sendMessage(ChatterBot.chatterBotName + ChatColor.WHITE + " Correct usage: " + ChatColor.YELLOW + "/cb <message>");
                }else{
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0 ; i < args.length ; i++){
                        sb.append(" ").append(args[i]);
                    }
                    final String input = sb.toString().trim();
                    p.sendMessage(ChatColor.AQUA + "[" + p.getName() + "]" + ChatColor.WHITE + ": " + input);
                    p.sendMessage(ChatColor.AQUA + "Thinking...");
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
                                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("ChatterBot Format").replace("%name%", ChatterBot.chatterBotName).replace("%message%", sb.toString())));
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
                                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("ChatterBot Format").replace("%name%", ChatterBot.chatterBotName).replace("%message%", sb.toString())));
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
