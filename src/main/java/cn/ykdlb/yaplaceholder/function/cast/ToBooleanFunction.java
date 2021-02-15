package cn.ykdlb.yaplaceholder.function.cast;

import cn.ykdlb.yaplaceholder.function.Function;
import cn.ykdlb.yaplaceholder.type.Type;
import cn.ykdlb.yaplaceholder.value.BooleanValue;
import cn.ykdlb.yaplaceholder.value.IntegerValue;
import cn.ykdlb.yaplaceholder.value.Value;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ToBooleanFunction extends Function {

    private final List<List<Type<?>>> paramsType = Collections.singletonList(
            Collections.singletonList(Type.get("Object"))
    );

    public ToBooleanFunction(int column) {
        super(column);
    }

    @Override
    public String getName() {
        return "toBoolean";
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
    public BooleanValue invoke(Value<?>[] params) {
        return ((BooleanValue) Type.get("Boolean").cast(params[0]));
    }
}
