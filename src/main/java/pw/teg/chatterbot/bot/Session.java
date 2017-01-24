package pw.teg.chatterbot.bot;

import com.google.code.chatterbotapi.ChatterBotSession;
import org.bukkit.Bukkit;
import pw.teg.chatterbot.ChatterBot;

import java.util.Random;
import java.util.UUID;

public class Session {

    private UUID player;
    private ChatterBotSession session;
    private long expireTimestamp;
    private boolean autoReply;

    private static Random random = new Random(System.nanoTime());

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

    public void chatMessage(final ChatterBot plugin, final String message, final ChatCallback callback) {
        final boolean delayedResponse = plugin.getConf().isDelayedResponse();
        int minDelay = Math.max(plugin.getConf().getMinimumDelay(), 1);
        int maxDelay = Math.max(plugin.getConf().getMaximumDelay(), 1);
        final int delay = random.nextInt((maxDelay - minDelay) + 1) + minDelay;

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

                Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                    @Override
                    public void run() {
                        callback.callback(response);
                    }
                }, delayedResponse ? delay * 20L : 1L);
            }
        });
    }

    public interface ChatCallback {
        void callback(String response);
    }
}
