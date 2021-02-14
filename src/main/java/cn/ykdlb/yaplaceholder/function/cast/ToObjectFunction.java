package cn.ykdlb.yaplaceholder.function.cast;

import cn.ykdlb.yaplaceholder.function.Function;
import cn.ykdlb.yaplaceholder.type.Type;
import cn.ykdlb.yaplaceholder.value.IntegerValue;
import cn.ykdlb.yaplaceholder.value.ObjectValue;
import cn.ykdlb.yaplaceholder.value.Value;

import java.util.Collections;
import java.util.List;

public class ToObjectFunction extends Function {
    public ToObjectFunction(int column) {
        super(column);
    }

    @Override
    public String getName() {
        return "toObject";
    }

    @Override
    public List<Type<?>> getParamsType() {
        return Collections.singletonList(
                Type.get("Object")
        );
    }

    @Override
    public boolean isParamsArray() {
        return false;
    }

    @Override
    public ObjectValue invoke(Value<?>... params) {
        return ((ObjectValue) Type.get("Object").cast(params[0]));
    }
}
