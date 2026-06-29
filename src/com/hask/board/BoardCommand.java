package com.hask.board;

import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.entity.MPlayerColl;
import com.massivecraft.massivecore.ps.PS;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class BoardCommand implements CommandExecutor {

    private final HaskBoard plugin;

    public BoardCommand(HaskBoard plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("haskboard.admin")) {
            sender.sendMessage("§cSem permissao!");
            return true;
        }
        if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            plugin.reloadConfig();
            plugin.scoreboardManager.load();
            sender.sendMessage("§aHaskBoard recarregado!");
        } else if (args.length > 0 && args[0].equalsIgnoreCase("debug")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§cApenas jogadores.");
                return true;
            }
            Player p = (Player) sender;
            try {
                MPlayer mp = MPlayerColl.get().get(p);
                sender.sendMessage("§6§l=== Debug Zona ===");
                sender.sendMessage("§7MPlayer: §f" + (mp != null ? "OK" : "null"));
                sender.sendMessage("§7Tem facção: §f" + (mp != null ? mp.hasFaction() : "?"));
                if (mp != null && mp.hasFaction()) {
                    sender.sendMessage("§7Facção: §f" + mp.getFaction().getName());
                    sender.sendMessage("§7Facção ID: §f" + mp.getFaction().getId());
                }
                Faction zonaFac = BoardColl.get().getFactionAt(PS.valueOf((Entity) p));
                sender.sendMessage("§7Zona Fac: §f" + (zonaFac != null ? zonaFac.getName() : "null"));
                sender.sendMessage("§7Zona ID: §f" + (zonaFac != null ? zonaFac.getId() : "null"));
                sender.sendMessage("§7Zona isNone: §f" + (zonaFac != null ? zonaFac.isNone() : "?"));
                if (mp != null && mp.hasFaction() && zonaFac != null) {
                    sender.sendMessage("§7Match ID: §f" + zonaFac.getId().equals(mp.getFaction().getId()));
                    sender.sendMessage("§7Match Name: §f" + zonaFac.getName().equals(mp.getFaction().getName()));
                }
            } catch (Exception e) {
                sender.sendMessage("§cErro: " + e.getMessage());
            }
        } else {
            sender.sendMessage("§cUso: §f/haskboard reload|debug");
        }
        return true;
    }

}
