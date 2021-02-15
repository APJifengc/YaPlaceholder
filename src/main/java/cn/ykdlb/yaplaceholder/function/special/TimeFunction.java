package cn.ykdlb.yaplaceholder.function.special;

import cn.ykdlb.yaplaceholder.function.Function;
import cn.ykdlb.yaplaceholder.type.Type;
import cn.ykdlb.yaplaceholder.value.IntegerValue;
import cn.ykdlb.yaplaceholder.value.LongValue;
import cn.ykdlb.yaplaceholder.value.StringValue;
import cn.ykdlb.yaplaceholder.value.Value;

import java.util.Collections;
import java.util.List;

/**
 * @author Yoooooory
 */
public class TimeFunction extends Function {

    private final List<List<Type<?>>> paramsType = Collections.singletonList(
            Collections.emptyList()
    );

    public TimeFunction(int column) {
        super(column);
    }

    @Override
    public String getName() {
        return "time";
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
    public LongValue invoke(Value<?>[] params) {
        return new LongValue(System.currentTimeMillis(), 0);
    }
}
