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

package org.soulnetwork;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.soulnetwork.config.ServerConfig;
import org.soulnetwork.config.StaticServerConfig;
import org.soulnetwork.listener.AsyncChatListener;
import org.soulnetwork.listener.PlayerCommandListener;
import org.soulnetwork.listener.ServerCommandListener;

import java.io.File;
import java.io.IOException;

public final class SoulClient extends JavaPlugin {

    public static SoulClient instance;

    private final ServerClient client = new ServerClient();
    private final ServerConfig config = new ServerConfig();
    @Override
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void onEnable() {
        instance = this;
        if (!getDataFolder().exists())
            getDataFolder().mkdir();

        try {
            File file = new File(getDataFolder(), "config.json");

            if (!file.exists()) {
                file.createNewFile();
                saveResource("config.json", true);
            }


            config.load(new File(getDataFolder(), "config.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        StaticServerConfig.load(config);


        Bukkit.getPluginManager().registerEvents(new AsyncChatListener(), this);
        Bukkit.getPluginManager().registerEvents(new ServerCommandListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerCommandListener(), this);
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public void reloadConfig() {
        try {
            config.reload();
            StaticServerConfig.load(config);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static SoulClient getInstance() {
        return instance;
    }

    public ServerClient getClient() {
        return client;
    }

    public ServerConfig getServerConfig() {
        return config;
    }


}
