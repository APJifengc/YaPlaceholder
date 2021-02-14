package cn.ykdlb.yaplaceholder.type;

import cn.ykdlb.yaplaceholder.YaPlaceholder;
import cn.ykdlb.yaplaceholder.value.Value;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class Type<T> {
    private static final Map<String, Type<?>> typeMap = new HashMap<>();

    public static Map<String, Type<?>> getTypeMap() {
        return typeMap;
    }

    public static Type<?> get(String name) {
        return typeMap.get(name);
    }

    @NotNull
    public abstract String getName();
    @NotNull
    public abstract Value<T> getValue(String shownText, int column);
    public abstract boolean isType(String shownText);
    @NotNull
    public abstract Value<T> cast(Value<?> value) throws ClassCastException;
}
