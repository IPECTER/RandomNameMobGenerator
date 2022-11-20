package com.github.ipecter.randomnamemobgenerator;

import com.github.ipecter.rtu.pluginlib.RTUPluginLib;
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

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (args.length == 1 && args[0] != null) {
            if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                if (sender.hasPermission("rnmg.reload")) {
                    configManager.initConfigFiles();
                    sender.sendMessage(RTUPluginLib.getTextManager().formatted(sender instanceof Player ? (Player) sender : null, "&a구성 파일을 다시 읽었습니다"));
                } else {
                    sender.sendMessage(RTUPluginLib.getTextManager().formatted(sender instanceof Player ? (Player) sender : null, "&c당신은 권한이 없습니다"));
                }
                return true;
            } else if (args[0].equalsIgnoreCase("spawn")) {
                if (sender.hasPermission("rnmg.reload")) {
                    spawn((Player) sender);
                } else {
                    sender.sendMessage(RTUPluginLib.getTextManager().formatted(sender instanceof Player ? (Player) sender : null, "&c당신은 권한이 없습니다"));
                }
            }
        } else if (args.length == 2 && args[1] != null) {
            if (args[0].equalsIgnoreCase("spawn")) {
                if (sender.hasPermission("rnmg.reload")) {
                    spawn((Player) sender, EntityType.valueOf(args[1]));
                } else {
                    sender.sendMessage(RTUPluginLib.getTextManager().formatted(sender instanceof Player ? (Player) sender : null, "&c당신은 권한이 없습니다"));
                }
            }
        }
        sender.sendMessage(RTUPluginLib.getTextManager().formatted(sender instanceof Player ? (Player) sender : null, "&c잘못된 사용법&7, &f/rnmg reload&7/&fspawn 를 사용하세요"));
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

    private void spawn(Player player) {
        Random random = new Random();
        int randomIndex = random.nextInt(configManager.getMobList().size());
        spawn(player, configManager.getMobList().get(randomIndex));
    }

    private void spawn(Player player, EntityType entityType) {
        Vector vector = player.getLocation().getDirection().clone().multiply(0.3).setY(0.3D);
        Entity entity = player.getWorld().spawnEntity(player.getLocation().getDirection().add(vector).toLocation(player.getWorld()), entityType);

        String name = RandomNickNameGenerator.getRandomNickName();

        entity.setCustomName(name);
        entity.setCustomNameVisible(true);

        player.sendMessage(RTUPluginLib.getTextManager().formatted(player, "&a이름: &f" + name + " &a몹: &f" + entityType.name() + " &a를 소환하였습니다"));
    }
}
