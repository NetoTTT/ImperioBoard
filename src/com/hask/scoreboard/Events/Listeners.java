/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gmail.nossr50.api.ExperienceAPI
 *  com.gmail.nossr50.datatypes.skills.SkillType
 *  com.gmail.nossr50.events.experience.McMMOPlayerLevelUpEvent
 *  com.gmail.nossr50.events.experience.McMMOPlayerXpGainEvent
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 *  org.bukkit.scoreboard.Scoreboard
 */
package com.hask.scoreboard.Events;

import com.gmail.nossr50.api.ExperienceAPI;
import com.gmail.nossr50.datatypes.skills.SkillType;
import com.gmail.nossr50.events.experience.McMMOPlayerLevelUpEvent;
import com.gmail.nossr50.events.experience.McMMOPlayerXpGainEvent;
import com.hask.scoreboard.LScoreBoard;
import com.hask.scoreboard.Utils.PacketWrapper;
import com.hask.scoreboard.Utils.ScoreBoard;
import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;

public class Listeners
implements Listener {
    public static ArrayList<Player> cooldown = new ArrayList();
    public static HashMap<Player, Integer> ganhando = new HashMap();

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        final Player p = e.getPlayer();
        PacketWrapper.wrapPlayerList(e.getPlayer(), " \u00a72\u00a76\u00a7lImperial Factions   \u00a7fwww.impfactions.com.br\u00a7a ", "\u00a72 \n\u00a76F\u00f3rum: \u00a7fsmitecraft.com.br/forum\n\u00a76Team Speak 3: \u00a7fts.smitecraft.com.br\n\u00a76Facebook: \u00a7ffb.com/smitecraftbr\n \u00a76Twitter: \u00a7ftwitter.com/servidorsmite\n \u00a7b \n\u00a76Adquira VIP e Cash acessando: \u00a7floja.smitecraft.com.br\n\u00a7b");
        if (!cooldown.contains(p)) {
            cooldown.add(p);
        }
        new BukkitRunnable(){

            public void run() {
                if (cooldown.contains(p)) {
                    cooldown.remove(p);
                }
            }
        }.runTaskLater((Plugin)LScoreBoard.plugin, 60L);
        ScoreBoard.handle(p);
    }

    @EventHandler
    public void getGain(McMMOPlayerXpGainEvent e) {
        Player p = e.getPlayer();
        if (p.getScoreboard() != null) {
            Scoreboard sb = p.getScoreboard();
            SkillType skill = e.getSkill();
            String name = skill.getName();
            if (skill.getName().length() > 13) {
                name = name.substring(0, 13);
            }
            sb.getTeam("11").setPrefix("\u00a7a  " + name);
            sb.getTeam("11").setSuffix("\u00a7a:\u00a7f " + ExperienceAPI.getLevel((Player)p, (String)skill.getName()));
            if (!ganhando.containsKey(p)) {
                ganhando.put(p, 15);
            } else {
                ganhando.replace(p, 15);
            }
        }
    }

    @EventHandler
    public void getGainLevel(McMMOPlayerLevelUpEvent e) {
        Player p = e.getPlayer();
        if (p.getScoreboard() != null) {
            Scoreboard sb = p.getScoreboard();
            SkillType skill = e.getSkill();
            String name = skill.getName();
            if (skill.getName().length() > 13) {
                name = name.substring(0, 13);
            }
            sb.getTeam("11").setPrefix("\u00a7a  " + name);
            sb.getTeam("11").setSuffix("\u00a7a:\u00a7f " + ExperienceAPI.getLevel((Player)p, (String)skill.getName()));
            if (!ganhando.containsKey(p)) {
                ganhando.put(p, 15);
            } else {
                ganhando.replace(p, 15);
            }
        }
    }
}

