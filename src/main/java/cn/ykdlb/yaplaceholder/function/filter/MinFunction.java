package cn.ykdlb.yaplaceholder.function.filter;

import cn.ykdlb.yaplaceholder.function.Function;
import cn.ykdlb.yaplaceholder.type.Type;
import cn.ykdlb.yaplaceholder.value.IntegerValue;
import cn.ykdlb.yaplaceholder.value.Value;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Yoooooory
 */
public class MinFunction extends Function {

    private final List<List<Type<?>>> paramsType = Collections.singletonList(
            Collections.singletonList(Type.get("Integer"))
    );

    public MinFunction(int column) {
        super(column);
    }

    @Override
    public String getName() {
        return "min";
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
        return new IntegerValue(Arrays.stream(params)
                .map(it -> ((IntegerValue) it).getValue())
                .min(Comparator.comparingInt(Integer::intValue)).orElseThrow(), 0);
    }
}
