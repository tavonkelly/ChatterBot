package pw.teg.chatterbot.bot;

import com.google.code.chatterbotapi.ChatterBotFactory;
import com.google.code.chatterbotapi.ChatterBotType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BotController {

    private com.google.code.chatterbotapi.ChatterBot bot;
    private Map<UUID, Session> sessionMap = new HashMap<>();

    public BotController() throws Exception {
        ChatterBotFactory factory = new ChatterBotFactory();
        bot = factory.create(ChatterBotType.PANDORABOTS, "f52bc2a49e3458fa");
    }

    public Session getSessionFor(UUID uuid) {
        if (uuid == null) {
            throw new IllegalArgumentException("uuid is null");
        }

        Session session = sessionMap.get(uuid);

        if (session == null) {
            return null;
        }

        if (session.getExpireTimestamp() != -1 && session.getExpireTimestamp() <= System.currentTimeMillis()) {
            sessionMap.remove(uuid);
            return null;
        }

        return sessionMap.get(uuid);
    }

    public Session newSession(UUID uuid, long expireTimestamp) {
        if (uuid == null) {
            throw new IllegalArgumentException("uuid is null");
        }

        Session session = new Session(uuid, bot.createSession(), expireTimestamp, false);

        sessionMap.put(uuid, session);
        return session;
    }

    public void deleteSession(UUID uuid) {
        sessionMap.remove(uuid);
    }

}
