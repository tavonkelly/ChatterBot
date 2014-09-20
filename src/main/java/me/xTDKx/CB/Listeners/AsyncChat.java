package me.xTDKx.CB.Listeners;

import com.google.code.chatterbotapi.ChatterBotSession;
import me.xTDKx.CB.ChatterBot;
import me.xTDKx.CB.Commands.CBAssign;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;

public class AsyncChat implements Listener {
    private ChatterBot plugin;
    private HashMap<String, Long> triggered = new HashMap<String, Long>();
    private HashMap<String, ChatterBotSession> sessions = new HashMap<String, ChatterBotSession>();

    public AsyncChat(ChatterBot p) {
        plugin = p;
    }

    @EventHandler
    public void onChat(final AsyncPlayerChatEvent e) {
        if (!e.isCancelled()) {
            if (CBAssign.assignie.containsKey(e.getPlayer().getName())) {
                final ChatterBotSession cbSession = CBAssign.assignie.get(e.getPlayer().getName());

                Bukkit.getScheduler().runTaskAsynchronously(ChatterBot.instance, new Runnable() {
                    @Override
                    public void run() {
                        final StringBuilder sb = new StringBuilder();
                        try {
                            sb.append(cbSession.think(e.getMessage()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        Bukkit.getScheduler().runTask(ChatterBot.instance, new Runnable() {
                            @Override
                            public void run() {
                                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("ChatterBot-Format").replace("%name%", ChatterBot.chatterBotName).replace("%message%", sb.toString())));
                            }
                        });
                    }
                });
            } else {
                for (String word : ChatterBot.instance.getConfig().getStringList("Trigger-Word-List")) {
                    if (e.getMessage().toLowerCase().contains(word.toLowerCase())) {
                        if (triggered.containsKey(e.getPlayer().getName())) {
                            triggered.put(e.getPlayer().getName(), System.currentTimeMillis() + (ChatterBot.instance.getConfig().getInt("Trigger-Word-Timeout") * 1000));

                            final ChatterBotSession cbSession = sessions.get(e.getPlayer().getName());

                            Bukkit.getScheduler().runTaskAsynchronously(ChatterBot.instance, new Runnable() {
                                @Override
                                public void run() {
                                    final StringBuilder sb = new StringBuilder();
                                    try {
                                        sb.append(cbSession.think(e.getMessage()));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    Bukkit.getScheduler().runTask(ChatterBot.instance, new Runnable() {
                                        @Override
                                        public void run() {
                                            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("ChatterBot-Format").replace("%name%", ChatterBot.chatterBotName).replace("%message%", sb.toString())));
                                        }
                                    });
                                }
                            });
                        } else {
                            final ChatterBotSession cbSession = ChatterBot.bot1.createSession();

                            triggered.put(e.getPlayer().getName(), System.currentTimeMillis() + (ChatterBot.instance.getConfig().getInt("Trigger-Word-Timeout") * 1000));
                            sessions.put(e.getPlayer().getName(), cbSession);

                            Bukkit.getScheduler().runTaskAsynchronously(ChatterBot.instance, new Runnable() {
                                @Override
                                public void run() {
                                    final StringBuilder sb = new StringBuilder();
                                    try {
                                        sb.append(cbSession.think(e.getMessage()));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    Bukkit.getScheduler().runTask(ChatterBot.instance, new Runnable() {
                                        @Override
                                        public void run() {
                                            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("ChatterBot-Format").replace("%name%", ChatterBot.chatterBotName).replace("%message%", sb.toString())));
                                        }
                                    });
                                }
                            });
                        }

                        return;
                    }
                }

                if (triggered.containsKey(e.getPlayer().getName())) {
                    if (!(System.currentTimeMillis() >= triggered.get(e.getPlayer().getName()))){

                        final ChatterBotSession cbSession = sessions.get(e.getPlayer().getName());

                        Bukkit.getScheduler().runTaskAsynchronously(ChatterBot.instance, new Runnable() {
                            @Override
                            public void run() {
                                final StringBuilder sb = new StringBuilder();
                                try {
                                    sb.append(cbSession.think(e.getMessage()));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                Bukkit.getScheduler().runTask(ChatterBot.instance, new Runnable() {
                                    @Override
                                    public void run() {
                                        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("ChatterBot-Format").replace("%name%", ChatterBot.chatterBotName).replace("%message%", sb.toString())));
                                    }
                                });
                            }
                        });
                    }
                }
            }
        }
    }

}
