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
public class SubStringFunction extends Function {

    private final List<List<Type<?>>> paramsType = Arrays.asList(
            Arrays.asList(Type.get("String"), Type.get("Integer")),
            Arrays.asList(Type.get("String"), Type.get("Integer"), Type.get("Integer"))
    );

    public SubStringFunction(int column) {
        super(column);
    }

    @Override
    public String getName() {
        return "substr";
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
        if (params.length == 2) {
            return new StringValue(params[0].getString().substring(((IntegerValue) params[1]).getValue()), 0);
        } else {
            return new StringValue(params[0].getString()
                    .substring(((IntegerValue) params[1]).getValue(), ((IntegerValue) params[2]).getValue()), 0);
        }
    }
}
