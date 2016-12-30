package pw.teg.chatterbot.bot;

import com.google.code.chatterbotapi.ChatterBotSession;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class Session {

    private UUID player;
    private ChatterBotSession session;
    private long expireTimestamp;
    private boolean autoReply;

    public Session(UUID player, ChatterBotSession session, long expireTimestamp, boolean autoReply) {
        this.player = player;
        this.session = session;
        this.expireTimestamp = expireTimestamp;
        this.autoReply = autoReply;
    }

    public UUID getPlayer() {
        return player;
    }

    public ChatterBotSession getSession() {
        return session;
    }

    public long getExpireTimestamp() {
        return expireTimestamp;
    }

    public void setExpireTimestamp(long expireTimestamp) {
        this.expireTimestamp = expireTimestamp;
    }

    public boolean isAutoReply() {
        return autoReply;
    }

    public void setAutoReply(boolean autoReply) {
        this.autoReply = autoReply;
    }

    public void chatMessage(final JavaPlugin plugin, final String message, final ChatCallback callback) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                final String response;

                try {
                    response = session.think(message);
                } catch (Exception e) {
                    plugin.getLogger().warning("ChatterBot could not think: " + e.getMessage());
                    callback.callback(null);
                    return;
                }

                Bukkit.getScheduler().runTask(plugin, new Runnable() {
                    @Override
                    public void run() {
                        callback.callback(response);
                    }
                });
            }
        });
    }

    public interface ChatCallback {
        void callback(String response);
    }
}
