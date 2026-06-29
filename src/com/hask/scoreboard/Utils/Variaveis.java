/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gmail.nossr50.api.ExperienceAPI
 *  com.gmail.nossr50.api.SkillAPI
 *  org.bukkit.entity.Player
 */
package com.hask.scoreboard.Utils;

import com.gmail.nossr50.api.ExperienceAPI;
import com.gmail.nossr50.api.SkillAPI;
import com.hask.scoreboard.LScoreBoard;
import java.util.HashMap;
import org.bukkit.entity.Player;

public class Variaveis {
    public static HashMap<String, String> group_tag = new HashMap();
    public static HashMap<String, String> group_char = new HashMap();

    public static void setupTags() {
        for (String group : LScoreBoard.plugin.getConfig().getConfigurationSection("Groups").getKeys(false)) {
            group_tag.put(group.toLowerCase(), LScoreBoard.plugin.getConfig().getString("Groups." + group.toLowerCase() + ".tag"));
            group_char.put(group.toLowerCase(), LScoreBoard.plugin.getConfig().getString("Groups." + group.toLowerCase() + ".char"));
        }
    }

    public static int getAllLevel(Player p) {
        int sum = 0;
        for (String skill : SkillAPI.getSkills()) {
            sum += ExperienceAPI.getLevel((Player)p, (String)skill);
        }
        return sum;
    }
}

