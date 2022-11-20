package com.github.ipecter.randomnamemobgenerator;

import com.github.ipecter.rtu.pluginlib.RTUPluginLib;
import com.iridium.iridiumcolorapi.IridiumColorAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class RandomNameMobGenerator extends JavaPlugin {

    private final String prefix = IridiumColorAPI.process("<GRADIENT:ffffff>RNMG</GRADIENT:000000> ") + "&7- ";

    @Override
    public void onEnable() {
        ConfigManager.getInstance().initConfigFiles();
        setExecutor();
        Bukkit.getPluginManager().registerEvents(new Listener(), this);
        Bukkit.getLogger().info(RTUPluginLib.getTextManager().formatted(prefix + "&a활성화&f!"));
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info(RTUPluginLib.getTextManager().formatted(prefix + "&c비활성화&f!"));
    }

    @Override
    public void onLoad() {
        RTUPluginLib.init(this);
    }

    private void setExecutor() {
        getCommand("rnmg").setExecutor(new Command());
    }

}
