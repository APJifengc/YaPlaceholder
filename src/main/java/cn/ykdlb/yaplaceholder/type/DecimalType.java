package cn.ykdlb.yaplaceholder.type;

import cn.ykdlb.yaplaceholder.value.*;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class DecimalType extends Type<Double> {
    private static final Pattern regex = Pattern.compile("^-?\\d+(\\.\\d+)?([dDfF])$");

    @Override
    public @NotNull String getName() {
        return "Decimal";
    }

    @Override
    public @NotNull Value<Double> getValue(String shownText, int column) {
        return new DecimalValue(Double.parseDouble(shownText.substring(0, shownText.length() - 1)), column);
    }

    @Override
    public boolean isType(String shownText) {
        return regex.matcher(shownText).matches();
    }

    @Override
    public @NotNull Value<Double> cast(Value<?> value) throws ClassCastException {
        if (value instanceof ObjectValue) {
            try {
                if (value.getValue() instanceof Boolean) value = Type.get("Boolean").cast(value);
                if (value.getValue() instanceof Integer) value = Type.get("Integer").cast(value);
                if (value.getValue() instanceof String) value = Type.get("String").cast(value);
            } catch (ClassCastException e) {
                throw new ClassCastException("The value " + value.getString()+ " cannot cast to Boolean.");
            }
        }
        if (value instanceof BooleanValue) return new DecimalValue(
                ((BooleanValue) value).getValue() ? 1.0d : 0.0d,
                value.getColumn()
        );
        if (value instanceof IntegerValue) return new DecimalValue(
                ((IntegerValue) value).getValue(),
                value.getColumn()
        );
        if (value instanceof StringValue) return new DecimalValue(
                Double.parseDouble(((StringValue) value).getValue()),
                value.getColumn()
        );
        throw new ClassCastException("The type " + value.getType().getName() + " cannot cast to Double.");
    }
}
