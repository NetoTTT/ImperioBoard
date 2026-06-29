/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 */
package com.hask.scoreboard.Utils;

import com.hask.scoreboard.LScoreBoard;
import com.hask.scoreboard.Utils.ScoreBoard;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class UpdateScore {
    public static void updateScore() {
        new BukkitRunnable(){

            public void run() {
                ScoreBoard.update();
            }
        }.runTaskTimer((Plugin)LScoreBoard.plugin, 10L, 40L);
    }
}

