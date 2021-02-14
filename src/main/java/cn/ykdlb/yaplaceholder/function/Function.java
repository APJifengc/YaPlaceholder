package cn.ykdlb.yaplaceholder.function;

import cn.ykdlb.yaplaceholder.Component;
import cn.ykdlb.yaplaceholder.YaPlaceholder;
import cn.ykdlb.yaplaceholder.type.Type;
import cn.ykdlb.yaplaceholder.value.Value;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Function extends Component {
    private static final Map<String, Function> functionMap = new HashMap<>();

    public static Map<String, Function> getFunctionMap() {
        return functionMap;
    }

    public static Function get(String name) {
        return functionMap.get(name);
    }

    public static boolean has(String name) {
        return functionMap.containsKey(name);
    }

    public Function(int column) {
        super(column);
    }

    public abstract String getName();
    public abstract List<Type<?>> getParamsType();
    public abstract boolean isParamsArray();
    public abstract Value<?> invoke(Value<?>... params);
}
