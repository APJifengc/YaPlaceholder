package cn.ykdlb.yaplaceholder.function.process;

import cn.ykdlb.yaplaceholder.function.Function;
import cn.ykdlb.yaplaceholder.type.Type;
import cn.ykdlb.yaplaceholder.value.BooleanValue;
import cn.ykdlb.yaplaceholder.value.Value;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class IfFunction extends Function {

    private final List<List<Type<?>>> paramsType = Collections.singletonList(
            Arrays.asList(Type.get("Boolean"), Type.get("Object"), Type.get("Object"))
    );

    public IfFunction(int column) {
        super(column);
    }

    @Override
    public String getName() {
        return "if";
    }

    @Override
    public List<List<Type<?>>> getParamsType() {
        return paramsType;
    }

    @Override
    public boolean isLastVarParam() {
        return false;
    }

    @Override
    public Value<?> invoke(Value<?>[] params) {
        return ((BooleanValue) params[0]).getValue() ? params[1] : params[2];
    }
}
