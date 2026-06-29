package com.hask.board;

import com.hask.cash.api.HaskCashAPI;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.entity.MPlayerColl;
import com.massivecraft.massivecore.ps.PS;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScoreboardManager implements Listener, Runnable {

    private final HaskBoard plugin;
    private final List<String> lines = new ArrayList<>();
    private final Map<UUID, Scoreboard> boards = new HashMap<>();
    private String title;
    private int taskId = -1;
    private Economy economy;
    private int frame;
    private int animTick;
    private static final DecimalFormat FMT = new DecimalFormat("#,##0.00");

    private static final String[] COLORS = {
        "§0", "§1", "§2", "§3", "§4", "§5", "§6", "§7",
        "§8", "§9", "§a", "§b", "§c", "§d", "§e", "§f"
    };

    private static final Pattern CYCLE_PATTERN = Pattern.compile("\\{#cycle:([^}]+)\\}");
    private static final String RAINBOW = "§c§e§a§b§d§c§e§a§b§d§c§e§a§b§d§c§e§a§b§d";
    private static final String[] RAINBOW_COLORS = {"§c", "§6", "§e", "§a", "§b", "§d"};

    public ScoreboardManager(HaskBoard plugin) {
        this.plugin = plugin;
    }

    public void load() {
        lines.clear();
        title = plugin.getConfig().getString("title", "&a&lMeu Servidor");
        for (String raw : plugin.getConfig().getStringList("lines")) {
            lines.add(raw);
        }
        if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
            RegisteredServiceProvider<Economy> rsp = Bukkit.getServicesManager().getRegistration(Economy.class);
            if (rsp != null) economy = rsp.getProvider();
        }
        plugin.getLogger().info("Scoreboard carregado: " + lines.size() + " linhas.");
    }

    public void start() {
        int interval = plugin.getConfig().getInt("update-ticks", 20);
        taskId = Bukkit.getScheduler().runTaskTimer(plugin, this, 20L, Math.max(interval, 10)).getTaskId();
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public void stop() {
        if (taskId != -1) Bukkit.getScheduler().cancelTask(taskId);
    }

    @Override
    public void run() {
        frame++;
        animTick++;
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!p.hasPermission("haskboard.see")) continue;
            update(p);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        createBoard(p);
        // Forcar atualizacao 1 tick depois (para sobrescrever outros plugins)
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if (p.isOnline()) update(p);
        }, 1L);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        boards.remove(event.getPlayer().getUniqueId());
    }

    private void createBoard(Player player) {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = board.registerNewObjective("hask", "dummy");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        for (int i = 0; i < 16; i++) {
            Team team = board.registerNewTeam("t" + i);
            String entry = COLORS[i] + "§r";
            team.addEntry(entry);
        }

        boards.put(player.getUniqueId(), board);
        player.setScoreboard(board);
    }

    private void update(Player player) {
        Scoreboard board = boards.get(player.getUniqueId());
        if (board == null) { createBoard(player); board = boards.get(player.getUniqueId()); }

        Objective obj = board.getObjective("hask");
        if (obj == null) { createBoard(player); return; }

        obj.setDisplayName(applyAnim(color(title)));

        List<String> formatted = new ArrayList<>();
        for (String raw : lines) {
            String parsed = parse(player, raw);
            formatted.add(applyAnim(parsed));
        }

        for (int i = 0; i < 16; i++) {
            String entry = COLORS[i] + "§r";
            Team team = board.getTeam("t" + i);
            if (team == null) continue;

            if (i < formatted.size()) {
                String text = formatted.get(i);
                obj.getScore(entry).setScore(0);

                if (text.length() <= 16) {
                    team.setPrefix(text);
                    team.setSuffix("");
                } else {
                    String prefix = text.substring(0, 16);
                    String suffix = text.substring(16, Math.min(text.length(), 32));
                    if (prefix.endsWith("§")) {
                        prefix = prefix.substring(0, 15);
                        suffix = "§" + suffix;
                    }
                    String lastFormat = getLastFormat(prefix);
                    // Garantir que suffix nao ultrapasse 16 chars (limite do protocolo)
                    int maxSuffix = 16 - lastFormat.length();
                    if (maxSuffix < 0) maxSuffix = 0;
                    if (suffix.length() > maxSuffix) {
                        suffix = suffix.substring(0, maxSuffix);
                    }
                    team.setPrefix(prefix);
                    team.setSuffix(lastFormat + suffix);
                }
            } else {
                board.resetScores(entry);
                team.setPrefix("");
                team.setSuffix("");
            }
        }
    }

    private String parse(Player player, String raw) {
        String s = raw;

        s = s.replace("{player}", player.getName());
        s = s.replace("{displayname}", player.getDisplayName());
        s = s.replace("{online}", String.valueOf(Bukkit.getOnlinePlayers().size()));
        s = s.replace("{max_online}", String.valueOf(Bukkit.getMaxPlayers()));
        s = s.replace("{level}", String.valueOf(player.getLevel()));
        s = s.replace("{health}", String.valueOf((int) player.getHealth()));
        s = s.replace("{food}", String.valueOf(player.getFoodLevel()));
        s = s.replace("{world}", player.getWorld().getName());

        if (s.contains("{coins}") || s.contains("{money}")) {
            double bal = economy != null ? economy.getBalance(player) : 0;
            s = s.replace("{coins}", FMT.format(bal)).replace("{money}", FMT.format(bal));
        }

        if (s.contains("{cash}")) {
            try {
                double cash = HaskCashAPI.getCash(player);
                s = s.replace("{cash}", FMT.format(cash));
            } catch (NoClassDefFoundError e) {
                s = s.replace("{cash}", "0");
            }
        }

        if (s.contains("{faction}") || s.contains("{power}") || s.contains("{tag}") || s.contains("{zona}") || s.contains("{territorios}")) {
            try {
                MPlayer mp = MPlayerColl.get().get(player);
                if (mp.hasFaction()) {
                    s = s.replace("{faction}", mp.getFaction().getName());
                    s = s.replace("{tag}", mp.getFaction().getTag());
                } else {
                    s = s.replace("{faction}", "Sem facção").replace("{tag}", "");
                }
                s = s.replace("{power}", String.valueOf((int) mp.getPower()));
                s = s.replace("{power_max}", String.valueOf((int) mp.getPowerMax()));

                if (s.contains("{zona}") || s.contains("{zona_dono}") || s.contains("{territorios}")) {
                    Faction zonaFac = BoardColl.get().getFactionAt(PS.valueOf((Entity) player));
                    String zonaNome;
                    if (zonaFac == null || zonaFac.isNone()) {
                        zonaNome = "§2Zona Livre";
                        s = s.replace("{zona_dono}", "Ninguem");
                    } else if (zonaFac.isSafeZone()) {
                        zonaNome = "§6Zona Protegida";
                        s = s.replace("{zona_dono}", "Spawn");
                    } else if (zonaFac.isWarZone()) {
                        zonaNome = "§cZona de Guerra";
                        s = s.replace("{zona_dono}", "Guerra");
                    } else if (mp.hasFaction() && zonaFac.getName().equals(mp.getFaction().getName())) {
                        zonaNome = "§a" + zonaFac.getName();
                        s = s.replace("{zona_dono}", zonaFac.getName());
                    } else {
                        zonaNome = "§c" + zonaFac.getName();
                        s = s.replace("{zona_dono}", zonaFac.getName());
                    }
                    s = s.replace("{zona}", zonaNome);
                    if (s.contains("{territorios}")) {
                        int lands = mp.hasFaction() ? mp.getFaction().getLandCount() : 0;
                        s = s.replace("{territorios}", String.valueOf(lands));
                    }
                }
            } catch (Exception e) {
                s = s.replace("{faction}", "?").replace("{power}", "?").replace("{power_max}", "?").replace("{tag}", "?").replace("{zona}", "?").replace("{zona_dono}", "?");
            }
        }

        if (s.contains("{papi_") && Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            int start = s.indexOf("{papi_");
            int end = s.indexOf("}", start);
            if (start != -1 && end != -1) {
                String papiParam = s.substring(start + 6, end);
                String papiValue = me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(player, "%" + papiParam + "%");
                s = s.substring(0, start) + papiValue + s.substring(end + 1);
            }
        }

        return color(s);
    }

    private String applyAnim(String s) {
        // {#cycle:&c:&e:&a} — ciclo de cores
        Matcher m = CYCLE_PATTERN.matcher(s);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            String[] colors = m.group(1).split(":");
            String color = colors[(animTick / 2) % colors.length];
            m.appendReplacement(sb, Matcher.quoteReplacement(color));
        }
        m.appendTail(sb);
        s = sb.toString();

        // {#rainbow} — arco-iris
        if (s.contains("{#rainbow}")) {
            StringBuilder rb = new StringBuilder();
            int base = animTick % RAINBOW_COLORS.length;
            String[] parts = s.split("\\{#rainbow\\}");
            for (int i = 0; i < parts.length; i++) {
                rb.append(parts[i]);
                if (i < parts.length - 1) {
                    rb.append(RAINBOW_COLORS[(base + i) % RAINBOW_COLORS.length]);
                }
            }
            s = rb.toString();
        }

        // {#fade:&c:&a} — gradiente entre duas cores
        if (s.contains("{#fade:")) {
            Pattern fadePat = Pattern.compile("\\{#fade:([^}]+)\\}");
            Matcher fm = fadePat.matcher(s);
            StringBuffer fsb = new StringBuffer();
            while (fm.find()) {
                String[] colors = fm.group(1).split(":");
                int total = colors.length;
                int idx = (animTick / 2) % (total * 2 - 2);
                if (idx >= total) idx = total * 2 - 2 - idx;
                fm.appendReplacement(fsb, Matcher.quoteReplacement(colors[Math.min(idx, total - 1)]));
            }
            fm.appendTail(fsb);
            s = fsb.toString();
        }

        return s;
    }

    private String getLastFormat(String s) {
        StringBuilder fmt = new StringBuilder();
        boolean foundColor = false;
        for (int i = s.length() - 2; i >= 0; i--) {
            if (s.charAt(i) == '§') {
                String code = s.substring(i, i + 2);
                char c = code.charAt(1);
                if (c == 'r') break;
                if ("0123456789abcdef".indexOf(c) != -1) {
                    if (!foundColor) { fmt.insert(0, code); foundColor = true; }
                } else if ("klmno".indexOf(c) != -1) {
                    fmt.insert(0, code);
                }
            }
        }
        if (fmt.length() == 0) fmt.append("§f");
        return fmt.toString();
    }

    private String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

}
