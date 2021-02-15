package cn.ykdlb.yaplaceholder.function.math;

import cn.ykdlb.yaplaceholder.function.Function;
import cn.ykdlb.yaplaceholder.type.Type;
import cn.ykdlb.yaplaceholder.value.DecimalValue;
import cn.ykdlb.yaplaceholder.value.Value;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Yoooooory
 */
public class PowFunction extends Function {

    private final List<List<Type<?>>> paramsType = Collections.singletonList(
            Arrays.asList(Type.get("Decimal"), Type.get("Decimal"))
    );

    public PowFunction(int column) {
        super(column);
    }

    @Override
    public String getName() {
        return "pow";
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
        return new DecimalValue(Math.pow((double) params[0].getValue(), (double) params[1].getValue()), 0);
    }
}
