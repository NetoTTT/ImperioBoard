package com.hask.board;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class HaskBoard extends JavaPlugin {

    public static HaskBoard instance;
    public ScoreboardManager scoreboardManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        scoreboardManager = new ScoreboardManager(this);
        scoreboardManager.load();
        scoreboardManager.start();

        getCommand("haskboard").setExecutor(new BoardCommand(this));

        getLogger().info("HaskBoardV2 ativo!");
    }

    @Override
    public void onDisable() {
        if (scoreboardManager != null) scoreboardManager.stop();
    }

}
