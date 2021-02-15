package cn.ykdlb.yaplaceholder.function.math;

import cn.ykdlb.yaplaceholder.function.Function;
import cn.ykdlb.yaplaceholder.type.Type;
import cn.ykdlb.yaplaceholder.value.DecimalValue;
import cn.ykdlb.yaplaceholder.value.IntegerValue;
import cn.ykdlb.yaplaceholder.value.Value;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Yoooooory
 */
public class SqrtFunction extends Function {

    private final List<List<Type<?>>> paramsType = Collections.singletonList(
            Collections.singletonList(Type.get("Decimal"))
    );

    public SqrtFunction(int column) {
        super(column);
    }

    @Override
    public String getName() {
        return "sqrt";
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
        return new DecimalValue(Math.sqrt((double) params[0].getValue()), 0);
    }
}
