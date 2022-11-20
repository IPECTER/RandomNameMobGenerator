package com.github.ipecter.randomnamemobgenerator;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class Listener implements org.bukkit.event.Listener {

    private final ConfigManager config = ConfigManager.getInstance();
    private final String prefix = IridiumColorAPI.process("<GRADIENT:ffffff>RNMG</GRADIENT:000000> ") + "&7- ";

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if (config.isMotd()) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&fRandom Name Mob Generator (RNMG) &7/ &f개발&7: &6IPECTER 이팩터"));
        } else {
            if (player.isOp())
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&fRandom Name Mob Generator (RNMG) &7/ &f개발&7: &6IPECTER 이팩터"));
        }
    }
}