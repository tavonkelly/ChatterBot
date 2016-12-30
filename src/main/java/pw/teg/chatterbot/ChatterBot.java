package pw.teg.chatterbot;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;
import pw.teg.chatterbot.bot.BotController;
import pw.teg.chatterbot.command.CBAssign;
import pw.teg.chatterbot.command.CBCommand;
import pw.teg.chatterbot.config.Config;
import pw.teg.chatterbot.listener.AsyncChat;
import pw.teg.chatterbot.util.MainConfig;

import java.io.File;
import java.io.IOException;

public class ChatterBot extends JavaPlugin {

    private MainConfig mainConfig;
    private File configFile;
    private Config config;

    private BotController botController;

    @Override
    public void onEnable() {
        boolean success = this.getDataFolder().mkdirs();

        if (!success) {
            this.getLogger().warning("Could not create config folder. Disabling plugin.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        configFile = new File(this.getDataFolder(), "config.yml");

        try {
            mainConfig = new MainConfig(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            this.getLogger().warning("Could not create config file: " + e.getMessage());
            this.getLogger().warning("Disabling plugin.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        try {
            this.config = new Config(mainConfig);
        } catch (Exception e) {
            this.getLogger().warning("Could not load config: " + e.getMessage());
            this.getLogger().warning("Disabling plugin.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        try {
            this.botController = new BotController(this);
        } catch (Exception e) {
            this.getLogger().warning("Could not create bot controller: " + e.getMessage());
            this.getLogger().warning("Disabling plugin.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        getCommand("cb").setExecutor(new CBCommand(this));
        getCommand("chatterbot").setExecutor(new CBCommand(this));
        getCommand("cbassign").setExecutor(new CBAssign(this));
        getCommand("chatterbotassign").setExecutor(new CBAssign(this));

        Bukkit.getPluginManager().registerEvents(new AsyncChat(this), this);
    }

    public Config getConf() {
        return config;
    }

    public BotController getBotController() {
        return botController;
    }

    @Override
    public void reloadConfig() {
        try {
            mainConfig.reload(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveDefaultConfig() {
        reloadConfig();
    }

}
