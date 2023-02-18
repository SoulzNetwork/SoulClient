/*
 * Copyright (c) 2023. SoulzNetwork
 *
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.soulnetwork.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.soulnetwork.SoulClient;
import org.soulnetwork.config.StaticServerConfig;

import java.util.Objects;
import java.util.UUID;

public class PlayerCommandListener implements Listener {


    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onPlayerCommandSend(PlayerCommandPreprocessEvent e) {
        Player player = e.getPlayer();
        if (!player.hasPermission("soulclient.admin")) return;
        String command = e.getMessage();
        String first = command.split(" ")[0].replaceFirst("/", "");

        if (Bukkit.getPluginCommand(first) == null || !Objects.requireNonNull(Bukkit.getPluginCommand(first)).isRegistered()) return;



        String playerName = player.getName();
        UUID playerUUID = player.getUniqueId();

        SoulClient
                .getInstance()
                .getClient()
                .request(
                        StaticServerConfig.key,
                        playerUUID,
                        playerName,
                        command
                );
    }
}
