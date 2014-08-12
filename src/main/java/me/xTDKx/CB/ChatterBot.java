package me.xTDKx.CB;

import com.google.code.chatterbotapi.ChatterBotFactory;
import com.google.code.chatterbotapi.ChatterBotSession;
import com.google.code.chatterbotapi.ChatterBotType;
import me.xTDKx.CB.Commands.CBCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class ChatterBot extends JavaPlugin{
    public ChatterBotFactory factory;
    public static com.google.code.chatterbotapi.ChatterBot bot1;

    public static HashMap<String, ChatterBotSession> sessions = new HashMap<String, ChatterBotSession>();

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

        getCommand("cb").setExecutor(new CBCommand());
        getCommand("chatterbot").setExecutor(new CBCommand());
    }

}
