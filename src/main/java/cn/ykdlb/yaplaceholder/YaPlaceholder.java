package cn.ykdlb.yaplaceholder;

import cn.ykdlb.yaplaceholder.function.Function;
import cn.ykdlb.yaplaceholder.operator.Operator;
import cn.ykdlb.yaplaceholder.operator.arithmetic.Add;
import cn.ykdlb.yaplaceholder.type.Type;
import cn.ykdlb.yaplaceholder.value.IntegerValue;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

/**
 * This plugin can custom more advance placeholder.
 *
 * @author APJifengc
 */
public class YaPlaceholder extends JavaPlugin {
    private static YaPlaceholder instance;

    public static YaPlaceholder getInstance() {
        return instance;
    }

    public YaPlaceholder() {
        instance = this;
    }

    private static final Reflections reflections = new Reflections("cn.ykdlb.yaplaceholder");

    @Override
    public void onEnable() {
        init();
        // Load PlaceholderAPI
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            // Register placeholder expansion
            new YaPlaceholderExpansion(this).register();
            getLogger().info("YaPlaceholder loaded.");
        } else {
            getLogger().severe("Can't find PlaceholderAPI! Did you installed the PlaceholderAPI?");
            getPluginLoader().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("YaPlaceholder uninstalled.");
    }

    public void init() {
        reflections.getSubTypesOf(Function.class).forEach(aClass -> {
            try {
                Function type = aClass.getConstructor(int.class).newInstance(0);
                Function.getFunctionMap().put(type.getName(), type);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        reflections.getSubTypesOf(Operator.class).forEach(aClass -> {
            try {
                Operator.getOperatorMap().put(aClass.getConstructor(int.class).newInstance(0).getString(), aClass);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        reflections.getSubTypesOf(Type.class).forEach(aClass -> {
            try {
                Type<?> type = aClass.getConstructor().newInstance();
                Type.getTypeMap().put(type.getName(), type);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}