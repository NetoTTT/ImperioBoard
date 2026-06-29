/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.factions.entity.BoardColl
 *  com.massivecraft.factions.entity.Faction
 *  com.massivecraft.factions.entity.MPlayer
 *  com.massivecraft.massivecore.ps.PS
 *  net.startc.digitalzero94.eco.API_Economy
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.scoreboard.DisplaySlot
 *  org.bukkit.scoreboard.Objective
 *  org.bukkit.scoreboard.Scoreboard
 *  org.bukkit.scoreboard.Team
 *  ru.tehkode.permissions.PermissionUser
 *  ru.tehkode.permissions.bukkit.PermissionsEx
 */
package com.hask.scoreboard.Utils;

import com.hask.scoreboard.Events.Listeners;
import com.hask.scoreboard.Utils.Variaveis;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.ps.PS;
import com.hask.cash.api.HaskCashAPI;
import java.text.DecimalFormat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;

public class ScoreBoard {
    public static void handle(Player player) {
        MPlayer mp = MPlayer.get((Object)player);
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        if (board.getObjective(DisplaySlot.SIDEBAR) != null) {
            board.getObjective(DisplaySlot.SIDEBAR).unregister();
        }
        Objective objective = board.registerNewObjective("scoreboard", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName("   \u00a76Zona Protegida");
        Team team12 = board.registerNewTeam("12");
        team12.addEntry(ChatColor.AQUA.toString());
        team12.setPrefix("\u00a7a ");
        team12.setSuffix("\u00a7d ");
        Team team13 = board.registerNewTeam("11");
        team13.addEntry(ChatColor.BLACK.toString());
        team13.setPrefix("\u00a7a  \u00a7aN\u00edvel: ");
        team13.setSuffix("\u00a7f 0");
        Team team14 = board.registerNewTeam("10");
        team14.addEntry(ChatColor.BLUE.toString());
        team14.setPrefix("\u00a7a\u00a7l ");
        team14.setSuffix("\u00a78\u00a7l ");
        Team team15 = board.registerNewTeam("9");
        team15.addEntry(ChatColor.BOLD.toString());
        team15.setPrefix("\u00a7e  Poder: ");
        team15.setSuffix("\u00a7f" + (int)mp.getPower() + "/" + (int)mp.getPowerMax());
        Team team16 = board.registerNewTeam("8");
        team16.addEntry(ChatColor.DARK_AQUA.toString());
        team16.setPrefix("\u00a7a\u00a7d ");
        team16.setSuffix("\u00a73\u00a7l ");
        Team team17 = board.registerNewTeam("7");
        team17.addEntry(ChatColor.DARK_BLUE.toString());
        if (mp.hasFaction()) {
            team17.setPrefix("\u00a7a ");
            String facDisplay = getFacTag(mp);
            team17.setSuffix(facDisplay.length() > 14 ? facDisplay.substring(0, 14) : facDisplay);
        } else {
            team17.setPrefix("\u00a72  Sem fac\u00e7\u00e3o ");
            team17.setSuffix("\u00a7a ");
        }
        Team team18 = board.registerNewTeam("6");
        team18.addEntry(ChatColor.DARK_GRAY.toString());
        team18.setPrefix("\u00a7f\u00a7l ");
        team18.setSuffix("\u00a7f\u00a7a ");
        Team team19 = board.registerNewTeam("5");
        team19.addEntry(ChatColor.DARK_GREEN.toString());
        team19.setPrefix("\u00a72\u00a7l ");
        team19.setSuffix("\u00a71\u00a7a ");
        Team team20 = board.registerNewTeam("4");
        team20.addEntry(ChatColor.DARK_PURPLE.toString());
        team20.setPrefix("\u00a7d\u00a77 ");
        team20.setSuffix("\u00a76\u00a7a ");
        Team team21 = board.registerNewTeam("3");
        team21.addEntry(ChatColor.DARK_RED.toString());
        team21.setPrefix("\u00a78\u00a7l ");
        team21.setSuffix("\u00a71\u00a7l ");
        Team team22 = board.registerNewTeam("2");
        team22.addEntry(ChatColor.GOLD.toString());
        team22.setPrefix("\u00a76  Coins: \u00a7f");
        DecimalFormat df1 = new DecimalFormat("###,###,###.#");
        RegisteredServiceProvider<Economy> ecoRsp = Bukkit.getServicesManager().getRegistration(Economy.class);
        double balance = ecoRsp != null ? ecoRsp.getProvider().getBalance(player) : 0;
        String money = df1.format(balance).replaceAll("\\.", "a").replace(",", ".").replace("a", ",");
        team22.setSuffix("\u00a7f" + money);
        Team team23 = board.registerNewTeam("1");
        team23.addEntry(ChatColor.GRAY.toString());
        team21.setPrefix("\u00a73\u00a74 ");
        team21.setSuffix("\u00a75\u00a71 ");
        Team team24 = board.registerNewTeam("0");
        team24.addEntry(ChatColor.GREEN.toString());
        team24.setPrefix("\u00a7esualoja");
        team24.setSuffix("\u00a7e.com");
        objective.getScore(ChatColor.AQUA.toString()).setScore(12);
        objective.getScore(ChatColor.BLACK.toString()).setScore(11);
        objective.getScore(ChatColor.BLUE.toString()).setScore(10);
        objective.getScore(ChatColor.BOLD.toString()).setScore(9);
        objective.getScore(ChatColor.DARK_AQUA.toString()).setScore(8);
        objective.getScore(ChatColor.DARK_BLUE.toString()).setScore(7);
        objective.getScore(ChatColor.DARK_RED.toString()).setScore(3);
        objective.getScore(ChatColor.GOLD.toString()).setScore(2);
        objective.getScore(ChatColor.GRAY.toString()).setScore(1);
        objective.getScore(ChatColor.GREEN.toString()).setScore(0);
        Objective health = board.registerNewObjective("showhealth", "health");
        health.setDisplaySlot(DisplaySlot.BELOW_NAME);
        health.setDisplayName("\u00a7c\u2764");
        for (Player p : Bukkit.getOnlinePlayers()) {
            health.getScore((OfflinePlayer)p).setScore((int)p.getHealth());
        }
        for (String x : Variaveis.group_tag.keySet()) {
            Team group = board.registerNewTeam(Variaveis.group_char.get(x));
            group.setPrefix(Variaveis.group_tag.get(x).replace("&", "\u00a7"));
        }
        player.setScoreboard(board);
    }

    private static String getFacTag(MPlayer mp) {
        if (!mp.hasFaction()) return "";
        Faction fac = mp.getFaction();
        String tag = fac.getTag();
        if (tag != null && !tag.isEmpty()) {
            return FactionColl.get().getRankColor(fac) + "[" + tag + "]";
        }
        String name = mp.getFactionName();
        return "§a" + (name.length() > 12 ? name.substring(0, 12) : name);
    }

    public static void update() {
        if (Bukkit.getOnlinePlayers().size() == 0) {
            return;
        }
        for (Player on : Bukkit.getOnlinePlayers()) {
            if (on.getScoreboard().getObjective("scoreboard") == null) {
                ScoreBoard.handle(on);
                continue;
            }
            Scoreboard sb = on.getScoreboard();
            MPlayer mp = MPlayer.get((Object)on);
            for (Player nego : Bukkit.getOnlinePlayers()) {
                RegisteredServiceProvider<Permission> rsp = Bukkit.getServicesManager().getRegistration(Permission.class);
                if (rsp == null) continue;
                String group = rsp.getProvider().getPrimaryGroup(null, nego).toLowerCase();
                if (!Variaveis.group_char.containsKey(group)) continue;
                Team t1 = sb.getPlayerTeam((OfflinePlayer)nego);
                Team t2 = sb.getTeam(Variaveis.group_char.get(group));
                if (t1 == null) {
                    t2.addPlayer((OfflinePlayer)nego);
                    continue;
                }
                if (t1 == t2) continue;
                t1.removePlayer((OfflinePlayer)nego);
                t2.addPlayer((OfflinePlayer)nego);
            }
            String fac_name = null;
            Faction fac = BoardColl.get().getFactionAt(PS.valueOf((Entity)on));
            String worldName = on.getWorld().getName();
            if (worldName.equalsIgnoreCase("mina")) {
                fac_name = "\u00a77Mundo de Minera\u00e7\u00e3o";
            } else if (worldName.equalsIgnoreCase("VOID")) {
                fac_name = "\u00a7aMundo VIP";
            } else if (worldName.equalsIgnoreCase("Arenas")) {
                fac_name = "\u00a7cMundo de Arenas";
            } else if (worldName.equalsIgnoreCase("Eventos")) {
                fac_name = "\u00a73Mundo de Eventos";
            } else if (fac == null || fac.getId().equalsIgnoreCase("none")) {
                fac_name = "\u00a72Zona Livre";
            } else if (fac.getId().equalsIgnoreCase("safezone")) {
                fac_name = "\u00a76Zona Protegida";
            } else if (fac.getId().equalsIgnoreCase("warzone")) {
                fac_name = "\u00a7cZona de Guerra";
            } else {
                String t = fac.getTag();
                fac_name = (t != null && !t.isEmpty())
                    ? FactionColl.get().getRankColor(fac) + "[" + t + "]"
                    : "\u00a7a" + fac.getName();
            }
            sb.getObjective("scoreboard").setDisplayName("\u00a7a" + fac_name + "\u00a7a");
            if (!mp.hasFaction()) {
                sb.getTeam("7").setPrefix("\u00a72  Sem fac\u00e7\u00e3o");
                sb.getTeam("7").setSuffix("\u00a73\u00a7d ");
                sb.resetScores(ChatColor.DARK_GRAY.toString());
                sb.resetScores(ChatColor.DARK_PURPLE.toString());
                sb.resetScores(ChatColor.DARK_GREEN.toString());
            } else {
                sb.getTeam("7").setPrefix("\u00a7a  ");
                String facDisplay = getFacTag(mp);
                sb.getTeam("7").setSuffix(facDisplay.length() > 14 ? facDisplay.substring(0, 14) : facDisplay);
                if (sb.getEntryTeam("6") == null) {
                    sb.getObjective("scoreboard").getScore(ChatColor.DARK_GRAY.toString()).setScore(6);
                }
                if (sb.getEntryTeam("5") == null) {
                    sb.getObjective("scoreboard").getScore(ChatColor.DARK_GREEN.toString()).setScore(5);
                }
                if (sb.getEntryTeam("4") == null) {
                    sb.getObjective("scoreboard").getScore(ChatColor.DARK_PURPLE.toString()).setScore(4);
                }
                if (mp.getFaction().getOnlinePlayers().size() == mp.getFaction().getMPlayers().size()) {
                    sb.getTeam("6").setPrefix("\u00a7a    Todos");
                    sb.getTeam("6").setSuffix("\u00a7a online");
                } else {
                    sb.getTeam("6").setPrefix("\u00a7a    Online: \u00a7f");
                    sb.getTeam("6").setSuffix("\u00a7f" + mp.getFaction().getOnlinePlayers().size() + "/" + mp.getFaction().getMPlayers().size());
                }
                sb.getTeam("5").setPrefix("\u00a7a    Poder: \u00a7f");
                sb.getTeam("5").setSuffix("\u00a7f" + (int)mp.getFaction().getPower() + "/" + (int)mp.getFaction().getPowerMax());
                sb.getTeam("4").setPrefix("\u00a7a    Terras: \u00a7f");
                sb.getTeam("4").setSuffix("\u00a7f" + mp.getFaction().getLandCount());
            }
            DecimalFormat df1 = new DecimalFormat("###,###,###.#");
            RegisteredServiceProvider<Economy> ecoRsp2 = Bukkit.getServicesManager().getRegistration(Economy.class);
            double bal = ecoRsp2 != null ? ecoRsp2.getProvider().getBalance(on) : 0;
            String money = df1.format(bal);
            sb.getTeam("2").setSuffix("\u00a7f" + money);
            sb.getTeam("9").setSuffix("\u00a7f" + (int)mp.getPower() + "/" + (int)mp.getPowerMax());
            if (Listeners.ganhando.containsKey(on) && Listeners.ganhando.get(on) > 0) {
                Listeners.ganhando.replace(on, Listeners.ganhando.get(on) - 1);
                continue;
            }
            if (Listeners.cooldown.contains(on)) {
                sb.getTeam("11").setPrefix("\u00a7a  \u00a7aN\u00edvel: ");
                sb.getTeam("11").setSuffix("\u00a7f 0");
            } else {
                sb.getTeam("11").setPrefix("\u00a7a  \u00a7aN\u00edvel: ");
                sb.getTeam("11").setSuffix("\u00a7f" + Variaveis.getAllLevel(on));
            }
            on.setScoreboard(sb);
        }
    }
}

