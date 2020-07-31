package cn.ykdlb.yaplaceholder;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * This plugin can custom more advance placeholder.
 *
 * @author APJifengc
 */
public final class YaPlaceholder extends JavaPlugin {

    @Override
    public void onEnable() {
        // Load PlaceholderAPI
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            // Register placeholder expansion
            new YaPlaceholderExpansion(this).register();
            getLogger().info("YaPlaceholder loaded.");
        } else {
            getLogger().info("Can't find PlaceholderAPI! Did you installed the PlaceholderAPI?");
            getPluginLoader().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("YaPlaceholder uninstalled.");
    }



}