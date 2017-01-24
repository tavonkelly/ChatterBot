package pw.teg.chatterbot.config;

import com.google.code.chatterbotapi.ChatterBotType;
import org.bukkit.ChatColor;
import pw.teg.chatterbot.util.MainConfig;

import java.util.HashSet;
import java.util.Set;

public class Config {

    private String botName;
    private String chatFormat;
    private boolean loudModeEnabled;
    private String loudFormat;
    private boolean triggerWordsEnabled;
    private int triggerWordTimeout;
    private Set<String> triggerWordList;
    private int triggerPlayerAmount;
    private ChatterBotType chatterBotType;
    private boolean delayedResponse;
    private int minimumDelay;
    private int maximumDelay;

    public Config(MainConfig config) throws Exception {
        reloadConfig(config);
    }

    public String getBotName() {
        return botName;
    }

    public String getChatFormat() {
        return chatFormat;
    }

    public boolean isLoudModeEnabled() {
        return loudModeEnabled;
    }

    public String getLoudFormat() {
        return loudFormat;
    }

    public boolean isTriggerWordsEnabled() {
        return triggerWordsEnabled;
    }

    public int getTriggerWordTimeout() {
        return triggerWordTimeout;
    }

    public Set<String> getTriggerWordList() {
        return triggerWordList;
    }

    public int getTriggerPlayerAmount() {
        return triggerPlayerAmount;
    }

    public ChatterBotType getChatterBotType() {
        return chatterBotType;
    }

    public boolean isDelayedResponse() {
        return delayedResponse;
    }

    public int getMinimumDelay() {
        return minimumDelay;
    }

    public int getMaximumDelay() {
        return maximumDelay;
    }

    public void reloadConfig(MainConfig config) throws Exception {
        this.botName = color(config.getString("ChatterBot-Name"));
        this.chatFormat = color(config.getString("ChatterBot-Format"));
        this.loudModeEnabled = config.getBoolean("Loud-Mode");
        this.loudFormat = color(config.getString("Loud-Format"));
        this.triggerWordsEnabled = config.getBoolean("Trigger-Words");
        this.triggerWordTimeout = config.getInt("Trigger-Word-Timeout");
        this.triggerWordList = new HashSet<>(config.getStringList("Trigger-Word-List"));
        this.triggerPlayerAmount = config.getInt("Trigger-Player-Amount");

        for (ChatterBotType type : ChatterBotType.values()) {
            if (type.name().equalsIgnoreCase(config.getString("ChatterBot-Type"))) {
                this.chatterBotType = type;
                break;
            }
        }

        if (this.chatterBotType == null) {
            throw new Exception("Could not find ChatterBot type: " + config.getString("ChatterBot-Type"));
        }

        this.delayedResponse = config.getBoolean("Delayed-Response-Enabled");
        this.minimumDelay = config.getInt("Delayed-Minimum-Seconds");
        this.maximumDelay = config.getInt("Delayed-Maximum-Seconds");
    }

    // Helper
    private String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }


}
