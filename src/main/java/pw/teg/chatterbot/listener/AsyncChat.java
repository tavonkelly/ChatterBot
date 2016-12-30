package pw.teg.chatterbot.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import pw.teg.chatterbot.ChatterBot;
import pw.teg.chatterbot.bot.Session;

public class AsyncChat implements Listener {
    private ChatterBot plugin;

    public AsyncChat(ChatterBot p) {
        plugin = p;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        if (e.isCancelled()) {
            return;
        }

        Player player = e.getPlayer();
        String message = e.getMessage();
        Session session = plugin.getBotController().getSessionFor(e.getPlayer().getUniqueId());

        if (session != null) {
            if (!session.isAutoReply()) {
                return;
            }

            if (session.getExpireTimestamp() != -1) {
                session.setExpireTimestamp(System.currentTimeMillis()
                        + (plugin.getConf().getTriggerWordTimeout() * 1000));
            }

            session.chatMessage(plugin, e.getMessage(), new Session.ChatCallback() {
                @Override
                public void callback(String response) {
                    Bukkit.broadcastMessage(plugin.getConf().getChatFormat()
                            .replace("%name%", plugin.getConf().getBotName())
                            .replace("%message%", response));
                }
            });

            return;
        }

        int triggerPlayerAmt = plugin.getConf().getTriggerPlayerAmount();

        if (triggerPlayerAmt != -1 && Bukkit.getOnlinePlayers().size() <= triggerPlayerAmt) {
            session = plugin.getBotController().newSession(player.getUniqueId(), -1);
            session.setAutoReply(true);

            onChat(e);
            return;
        }

        for (String word : plugin.getConf().getTriggerWordList()) {
            for (String token : message.split(" ")) {
                if (!token.equalsIgnoreCase(word)) {
                    continue;
                }

                session = plugin.getBotController().newSession(player.getUniqueId()
                        , System.currentTimeMillis() + (plugin.getConf().getTriggerWordTimeout() * 1000));

                session.setAutoReply(true);

                session.chatMessage(plugin, message, new Session.ChatCallback() {
                    @Override
                    public void callback(String response) {
                        Bukkit.broadcastMessage(plugin.getConf().getChatFormat()
                                .replace("%name%", plugin.getConf().getBotName())
                                .replace("%message%", response));
                    }
                });
            }
        }
    }

}
