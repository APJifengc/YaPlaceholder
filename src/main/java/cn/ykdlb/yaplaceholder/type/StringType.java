package cn.ykdlb.yaplaceholder.type;

import cn.ykdlb.yaplaceholder.operator.Operator;
import cn.ykdlb.yaplaceholder.value.StringValue;
import cn.ykdlb.yaplaceholder.value.Value;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class StringType extends Type<String> {
    private static final Pattern regex = Pattern.compile("^\".*\"$");
    @Override
    public @NotNull String getName() {
        return "String";
    }

    @Override
    public @NotNull Value<String> getValue(String shownText, int column) {
        return new StringValue(shownText.substring(1, shownText.length() - 1), column);
    }

    @Override
    public boolean isType(String shownText) {
        return regex.matcher(shownText).matches();
    }

    @Override
    public @NotNull Value<String> cast(Value<?> value) throws ClassCastException {
        return new StringValue(value.getValue().toString(), value.getColumn());
    }
}
