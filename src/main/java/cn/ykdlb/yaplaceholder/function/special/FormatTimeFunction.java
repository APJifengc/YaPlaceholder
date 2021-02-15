package cn.ykdlb.yaplaceholder.function.special;

import cn.ykdlb.yaplaceholder.function.Function;
import cn.ykdlb.yaplaceholder.type.Type;
import cn.ykdlb.yaplaceholder.value.LongValue;
import cn.ykdlb.yaplaceholder.value.StringValue;
import cn.ykdlb.yaplaceholder.value.Value;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author Yoooooory
 */
public class FormatTimeFunction extends Function {

    private final List<List<Type<?>>> paramsType = Arrays.asList(
            Collections.emptyList(),
            Collections.singletonList(Type.get("Long")),
            Arrays.asList(Type.get("Long"), Type.get("String"))
    );

    public FormatTimeFunction(int column) {
        super(column);
    }

    @Override
    public String getName() {
        return "formatTime";
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
        long time;
        DateFormat format;
        switch (params.length) {
            case 0:
                time = System.currentTimeMillis();
                format = SimpleDateFormat.getDateTimeInstance();
                break;
            case 1:
                time = ((LongValue) params[0]).getValue();
                format = SimpleDateFormat.getDateTimeInstance();
                break;
            case 2:
                time = ((LongValue) params[0]).getValue();
                format = new SimpleDateFormat(((StringValue) params[1]).getValue());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + params.length);
        }
        return new StringValue(format.format(new Date(time)), 0);
    }
}
