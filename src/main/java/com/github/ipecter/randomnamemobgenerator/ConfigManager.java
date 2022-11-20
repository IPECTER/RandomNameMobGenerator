package com.github.ipecter.randomnamemobgenerator;

import com.github.ipecter.rtu.pluginlib.RTUPluginLib;
import com.iridium.iridiumcolorapi.IridiumColorAPI;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class ConfigManager {

    private boolean enablePlugin = true;
    private boolean motd = true;

    public boolean isEnablePlugin() {
        return enablePlugin;
    }

    public void setEnablePlugin(boolean enablePlugin) {
        this.enablePlugin = enablePlugin;
    }

    public boolean isMotd() {
        return motd;
    }

    public void setMotd(boolean motd) {
        this.motd = motd;
    }

    public List<EntityType> getMobList() {
        return mobList;
    }

    public void setMobList(List<EntityType> mobList) {
        this.mobList = mobList;
    }

    private List<EntityType> mobList = Collections.synchronizedList(new ArrayList<>());

    public final static ConfigManager getInstance() {
        return InnerInstanceClass.instance;
    }
    private static class InnerInstanceClass {
        private static final ConfigManager instance = new ConfigManager();
    }
    public void initConfigFiles() {
        initSetting(RTUPluginLib.getFileManager().copyResource("Setting.yml"));
        initMobList(RTUPluginLib.getFileManager().copyResource("MobList.yml"));
    }

    private void initSetting(File file) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        enablePlugin = config.getBoolean("enablePlugin");
        motd = config.getBoolean("motd");
    }

    private void initMobList(File file) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        mobList.clear();
        mobList.addAll(config.getStringList("list").stream().map(s -> EntityType.valueOf(s)).collect(Collectors.toList()));
    }
}
