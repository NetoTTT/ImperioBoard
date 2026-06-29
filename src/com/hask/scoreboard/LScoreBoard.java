/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 */
package com.hask.scoreboard;

import com.hask.scoreboard.Events.Listeners;
import com.hask.scoreboard.Utils.UpdateScore;
import com.hask.scoreboard.Utils.Variaveis;
import java.io.File;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class LScoreBoard
extends JavaPlugin {
    public static ServerSocket server = null;
    public List<PrintWriter> escritores = new ArrayList<PrintWriter>();
    Thread systemPlus = null;
    public static LScoreBoard plugin;

    public void onEnable() {
        plugin = this;
        plugin.setupConfig();
        this.registerEvents();
        this.setupEconomy();
        Variaveis.setupTags();
        UpdateScore.updateScore();
    }

    public static void sendMessage(String msg) {
        String jogadores = "";
        if (plugin.getServer().getOnlinePlayers().size() == 0) {
            jogadores = "Nenhum jogador conectado!";
        } else {
            for (Player p : plugin.getServer().getOnlinePlayers()) {
                jogadores = String.valueOf(String.valueOf(jogadores)) + p.getName() + ",";
            }
        }
        msg = String.valueOf(String.valueOf(Level.INFO.getLocalizedName())) + ";;;;;;" + msg + ";;;;;;" + System.currentTimeMillis() + ";;;;;;" + jogadores;
        for (PrintWriter w : LScoreBoard.plugin.escritores) {
            try {
                w.println(msg);
                w.flush();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    public static void sendMessage(String msg, Level l) {
        String jogadores = "";
        if (plugin.getServer().getOnlinePlayers().size() == 0) {
            jogadores = "Nenhum jogador conectado!";
        } else {
            for (Player p : plugin.getServer().getOnlinePlayers()) {
                jogadores = String.valueOf(String.valueOf(jogadores)) + p.getName() + ",";
            }
        }
        msg = String.valueOf(String.valueOf(l.getLocalizedName())) + ";;;;;;" + msg + ";;;;;;" + System.currentTimeMillis() + ";;;;;;" + jogadores;
        for (PrintWriter w : LScoreBoard.plugin.escritores) {
            try {
                w.println(msg);
                w.flush();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    private void setupConfig() {
        File config = new File(this.getDataFolder(), "config.yml");
        if (!config.exists()) {
            this.saveResource("config.yml", false);
        }
    }

    private void registerEvents() {
        Bukkit.getServer().getPluginManager().registerEvents((Listener)new Listeners(), (Plugin)this);
    }

    private boolean setupEconomy() {
        return true;
    }
}

