package cn.ykdlb.yaplaceholder;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
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
            // Register Placeholder Expansion
            new YaPlaceholderExpansion(this).register();
            this.getLogger().info("PlaceholderAPI successfully loaded.");
        } else {
            this.getLogger().info("Can't find PlaceholderAPI! Did you installed the PlaceholderAPI?");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {

    }
}
