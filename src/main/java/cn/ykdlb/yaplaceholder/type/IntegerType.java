package cn.ykdlb.yaplaceholder.type;

import cn.ykdlb.yaplaceholder.operator.Operator;
import cn.ykdlb.yaplaceholder.value.*;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class IntegerType extends Type<Integer> {
    private static final Pattern regex = Pattern.compile("^-?\\d+$");
    @Override
    public @NotNull String getName() {
        return "Integer";
    }

    @Override
    public @NotNull Value<Integer> getValue(String shownText, int column) {
        return new IntegerValue(Integer.parseInt(shownText), column);
    }

    @Override
    public boolean isType(String shownText) {
        return regex.matcher(shownText).matches();
    }

    @Override
    public @NotNull Value<Integer> cast(Value<?> value) throws ClassCastException {
        if (value instanceof ObjectValue) {
            try {
                if (value.getValue() instanceof Boolean) value = Type.get("Boolean").cast(value);
                if (value.getValue() instanceof Double) value = Type.get("Decimal").cast(value);
                if (value.getValue() instanceof String) value = Type.get("String").cast(value);
            } catch (ClassCastException e) {
                throw new ClassCastException("The value " + value.getString()+ " cannot cast to Integer.");
            }
        }
        if (value instanceof BooleanValue) return new IntegerValue(
                ((BooleanValue) value).getValue() ? 1 : 0,
                value.getColumn()
        );
        if (value instanceof DecimalValue) return new IntegerValue(
                ((DecimalValue) value).getValue().intValue(),
                value.getColumn()
        );
        if (value instanceof StringValue) return new IntegerValue(
                Integer.parseInt(((StringValue) value).getValue()),
                value.getColumn()
        );
        throw new ClassCastException("The type " + value.getType().getName() + " cannot cast to Integer.");
    }

}
