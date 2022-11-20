package com.github.ipecter.randomnamemobgenerator;

import com.github.ipecter.rtu.pluginlib.RTUPluginLib;
import com.iridium.iridiumcolorapi.IridiumColorAPI;
import org.bukkit.ChatColor;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Command implements CommandExecutor, TabCompleter {

    private ConfigManager configManager = ConfigManager.getInstance();
    private final String prefix = IridiumColorAPI.process("<GRADIENT:ffffff>RNMG</GRADIENT:2b2b2b> ") + "&7- ";

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (args.length == 1 && args[0] != null) {
            if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                if (sender.hasPermission("rnmg.reload")) {
                    configManager.initConfigFiles();
                    sender.sendMessage(RTUPluginLib.getTextManager().formatted(sender instanceof Player ? (Player) sender : null, prefix + "&a구성 파일을 다시 읽었습니다"));
                } else {
                    sender.sendMessage(RTUPluginLib.getTextManager().formatted(sender instanceof Player ? (Player) sender : null, prefix + "&c당신은 권한이 없습니다"));
                }
                return true;
            } else if (args[0].equalsIgnoreCase("spawn")) {
                if (sender.hasPermission("rnmg.reload")) {
                    spawn(sender);
                } else {
                    sender.sendMessage(RTUPluginLib.getTextManager().formatted(sender instanceof Player ? (Player) sender : null, prefix + "&c당신은 권한이 없습니다"));
                }
                return true;
            }
        } else if (args.length == 2 && args[1] != null) {
            if (args[0].equalsIgnoreCase("spawn")) {
                if (sender.hasPermission("rnmg.reload")) {
                    spawn(sender, EntityType.valueOf(args[1].toUpperCase()));
                } else {
                    sender.sendMessage(RTUPluginLib.getTextManager().formatted(sender instanceof Player ? (Player) sender : null, prefix + "&c당신은 권한이 없습니다"));
                }
                return true;
            }
        }
        sender.sendMessage(RTUPluginLib.getTextManager().formatted(sender instanceof Player ? (Player) sender : null, prefix + "&c잘못된 사용법&7, &f/rnmg reload&7/&fspawn 를 사용하세요"));
        return true;
    }

    @Override
    public List<String> onTabComplete
            (CommandSender sender, org.bukkit.command.Command command, String alias, String[] args) {
        if (args.length == 1 && args[0] != null) {
            if (sender.hasPermission("rnmg.reload")) {
                return Arrays.asList("reload", "spawn");
            }
            return Arrays.asList("spawn");
        } else if (args.length == 2 && args[1] != null) {
            if (args[0].equalsIgnoreCase("spawn")) {
                return Arrays.stream(EntityType.values()).map(entityType -> entityType.name().toLowerCase()).collect(Collectors.toList());
            }
        }
        return Arrays.asList();
    }

    private void spawn(CommandSender sender) {
        Random random = new Random();
        int randomIndex = random.nextInt(configManager.getMobList().size());
        spawn(sender, configManager.getMobList().get(randomIndex));
    }

    private void spawn(CommandSender sender, EntityType entityType) {
        if (sender instanceof BlockCommandSender commandSender){
            Entity entity = commandSender.getBlock().getWorld().spawnEntity(commandSender.getBlock().getLocation(), entityType);

            String name = RandomNickNameGenerator.getRandomNickName();

            entity.setCustomName(RTUPluginLib.getTextManager().formatted(prefix + "&f" + name));
            entity.setCustomNameVisible(true);

            commandSender.sendMessage(RTUPluginLib.getTextManager().formatted(prefix + "&a이름: &f" + name + " &a몹 타입: &f" + entityType.name() + "&a을(를) 소환하였습니다"));
            return;
        }
        if (sender instanceof Player player) {
            Entity entity = player.getWorld().spawnEntity(player.getLocation(), entityType);

            String name = RandomNickNameGenerator.getRandomNickName();

            entity.setCustomName(ChatColor.translateAlternateColorCodes('&', "&7RNMG - &f" + name));
            entity.setCustomNameVisible(true);

            player.sendMessage(RTUPluginLib.getTextManager().formatted(player, prefix + "&a이름: &f" + name + " &a몹 타입: &f" + entityType.name() + "&a을(를) 소환하였습니다"));
            return;
        }
        sender.sendMessage(RTUPluginLib.getTextManager().formatted(prefix + "&c오직 플레이어 또는 커맨드 블럭에서만 실행할 수 있습니다"));
    }
}
