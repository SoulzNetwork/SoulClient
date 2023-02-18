package org.soulnetwork.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.soulnetwork.SoulClient;
import org.soulnetwork.config.StaticServerConfig;

import java.util.UUID;

public class AsyncChatListener implements Listener {

    public AsyncChatListener() {
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        String message = e.getMessage();

        if (!message.startsWith("!")) return;

        String playerName = player.getName();
        UUID playerUUID = player.getUniqueId();

        SoulClient
                .getInstance()
                .getClient()
                .request(
                        StaticServerConfig.key,
                        playerUUID,
                        playerName,
                        "Skript Command: " + message
                );
    }
}
