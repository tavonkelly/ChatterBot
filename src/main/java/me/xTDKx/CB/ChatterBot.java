package me.xTDKx.CB;

import com.google.code.chatterbotapi.ChatterBotFactory;
import com.google.code.chatterbotapi.ChatterBotSession;
import com.google.code.chatterbotapi.ChatterBotType;
import me.xTDKx.CB.Commands.CBAssign;
import me.xTDKx.CB.Commands.CBCommand;
import me.xTDKx.CB.Listeners.AsyncChat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class ChatterBot extends JavaPlugin{
    public ChatterBotFactory factory;
    public static com.google.code.chatterbotapi.ChatterBot bot1;

    public static HashMap<String, ChatterBotSession> sessions = new HashMap<String, ChatterBotSession>();

    public static String chatterBotName;

    public static ChatterBot instance;

    public ChatterBot(){
        instance = this;
    }

    @Override
    public void onEnable(){
        factory = new ChatterBotFactory();
        try {
            bot1 = factory.create(ChatterBotType.CLEVERBOT);
        }catch (Exception e){
            e.printStackTrace();
        }

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        chatterBotName = ChatColor.translateAlternateColorCodes('&', getConfig().getString("ChatterBot Name"));

        getCommand("cb").setExecutor(new CBCommand(this));
        getCommand("chatterbot").setExecutor(new CBCommand(this));
        getCommand("cbassign").setExecutor(new CBAssign(this));
        getCommand("chatterbotassign").setExecutor(new CBAssign(this));

        Bukkit.getPluginManager().registerEvents(new AsyncChat(this), this);
    }

}
