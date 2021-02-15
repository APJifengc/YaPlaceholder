package cn.ykdlb.yaplaceholder.function.process;

import cn.ykdlb.yaplaceholder.function.Function;
import cn.ykdlb.yaplaceholder.type.Type;
import cn.ykdlb.yaplaceholder.value.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SwitchFunction extends Function {

    private final List<List<Type<?>>> paramsType = Collections.singletonList(
            Arrays.asList(Type.get("Integer"), Type.get("Object"))
    );

    public SwitchFunction(int column) {
        super(column);
    }

    @Override
    public String getName() {
        return "switch";
    }

    @Override
    public List<List<Type<?>>> getParamsType() {
        return paramsType;
    }

    @Override
    public boolean isLastVarParam() {
        return true;
    }

    @Override
    public Value<?> invoke(Value<?>[] params) {
        return params[((IntegerValue) params[0]).getValue()];
    }
}
