package cn.ykdlb.yaplaceholder;

import cn.ykdlb.yaplaceholder.exception.InvalidFunctionException;
import cn.ykdlb.yaplaceholder.exception.UnknownFunctionException;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is the placeholder expansion class.
 *
 * @author APJifengc
 * @author Yoooooory
 */
public class YaPlaceholderExpansion extends PlaceholderExpansion {
    private Map<String, Expression> map = new HashMap<>();

    private final YaPlaceholder plugin;

    /**
     * When the class constructed, the expansion will automatics register.
     *
     * @param plugin The instance of the plugin.
     */
    public YaPlaceholderExpansion(YaPlaceholder plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "e";
    }

    @Override
    public @NotNull String getAuthor() {
        return plugin.getDescription().getAuthors().toString();
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        if (!map.containsKey(params)) map.put(params, Expression.fromInfixExpression(params));
        return map.get(params).calculateValue(player);
    }
}