package cn.ykdlb.yaplaceholder;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * This plugin can custom more advance placeholder.
 *
 * @author APJifengc
 */
public final class YaPlaceholder extends JavaPlugin {
    public PlaceholderAPI placeholderAPI;

    @Override
    public void onEnable() {
        // Load PlaceholderAPI
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            placeholderAPI = (PlaceholderAPI) Bukkit.getPluginManager().getPlugin("PlaceholderAPI");
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
