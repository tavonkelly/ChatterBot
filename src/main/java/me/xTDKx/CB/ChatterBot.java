package me.xTDKx.CB;

import com.google.code.chatterbotapi.ChatterBotFactory;
import com.google.code.chatterbotapi.ChatterBotSession;
import com.google.code.chatterbotapi.ChatterBotType;
import me.xTDKx.CB.Commands.CBAssign;
import me.xTDKx.CB.Commands.CBCommand;
import me.xTDKx.CB.Listeners.AsyncChat;
import me.xTDKx.CB.Util.MainConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ChatterBot extends JavaPlugin {
    public ChatterBotFactory factory;
    public static com.google.code.chatterbotapi.ChatterBot bot1;

    public static HashMap<String, ChatterBotSession> sessions = new HashMap<String, ChatterBotSession>();

    public static String chatterBotName;

    public static ChatterBot instance;

    public ChatterBot() {
        instance = this;
    }

    private MainConfig config;
    private final File configFile = new File(getDataFolder(), "config.yml");

    @Override
    public void onEnable() {

        try {
            getDataFolder().mkdirs();
            config = new MainConfig(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }

        factory = new ChatterBotFactory();
        try {
            bot1 = factory.create(ChatterBotType.valueOf(getConfig().getString("ChatterBot-Type")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        chatterBotName = ChatColor.translateAlternateColorCodes('&', getConfig().getString("ChatterBot-Name"));

        getCommand("cb").setExecutor(new CBCommand(this));
        getCommand("chatterbot").setExecutor(new CBCommand(this));
        getCommand("cbassign").setExecutor(new CBAssign());
        getCommand("chatterbotassign").setExecutor(new CBAssign());

        Bukkit.getPluginManager().registerEvents(new AsyncChat(this), this);
    }

    @Override
    public FileConfiguration getConfig() {
        return config;
    }

    @Override
    public void reloadConfig() {
        try {
            config.reload(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveDefaultConfig() {
        reloadConfig();
    }

}
