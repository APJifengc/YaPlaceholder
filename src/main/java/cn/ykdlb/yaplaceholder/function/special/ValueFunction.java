package cn.ykdlb.yaplaceholder.function.special;

import cn.ykdlb.yaplaceholder.function.Function;
import cn.ykdlb.yaplaceholder.type.Type;
import cn.ykdlb.yaplaceholder.value.BooleanValue;
import cn.ykdlb.yaplaceholder.value.StringValue;
import cn.ykdlb.yaplaceholder.value.Value;

import java.util.Collections;
import java.util.List;

public class ValueFunction extends Function {

    private final List<List<Type<?>>> paramsType = Collections.singletonList(
            Collections.singletonList(Type.get("String"))
    );

    public ValueFunction(int column) {
        super(column);
    }

    @Override
    public String getName() {
        return "value";
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
    public StringValue invoke(Value<?>[] params) {
        throw new UnsupportedOperationException("This function cannot be invoked!");
    }
}
