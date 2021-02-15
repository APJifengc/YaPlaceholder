package cn.ykdlb.yaplaceholder.function.handle;

import cn.ykdlb.yaplaceholder.function.Function;
import cn.ykdlb.yaplaceholder.type.Type;
import cn.ykdlb.yaplaceholder.value.IntegerValue;
import cn.ykdlb.yaplaceholder.value.StringValue;
import cn.ykdlb.yaplaceholder.value.Value;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Yoooooory
 */
public class RepeatFunction extends Function {
    private final List<List<Type<?>>> paramsType = Collections.singletonList(
            Arrays.asList(Type.get("String"), Type.get("Integer"))
    );

    public RepeatFunction(int column) {
        super(column);
    }

    @Override
    public String getName() {
        return "repeat";
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
        var sb = new StringBuilder();
        String source = params[0].getString();
        int times = ((IntegerValue) params[1]).getValue();
        for (int i = 1; i <= times; i++) {
            sb.append(source.replace("{i}", String.valueOf(i)));
        }
        return new StringValue(sb.toString(), 0);
    }
}
