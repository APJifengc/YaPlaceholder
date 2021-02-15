package cn.ykdlb.yaplaceholder.type;

import cn.ykdlb.yaplaceholder.value.BooleanValue;
import cn.ykdlb.yaplaceholder.value.IntegerValue;
import cn.ykdlb.yaplaceholder.value.ObjectValue;
import cn.ykdlb.yaplaceholder.value.Value;
import org.jetbrains.annotations.NotNull;

public class BooleanType extends Type<Boolean> {
    @Override
    public @NotNull String getName() {
        return "Boolean";
    }

    @Override
    public @NotNull Value<Boolean> getValue(String shownText, int column) {
        if (shownText.equals("true")) return new BooleanValue(true, column);
        else return new BooleanValue(false, column);
    }

    @Override
    public boolean isType(String shownText) {
        return shownText.equals("true") || shownText.equals("false");
    }

    @Override
    public @NotNull Value<Boolean> cast(Value<?> value) throws ClassCastException {
        if (value instanceof IntegerValue)
            return new BooleanValue(((IntegerValue) value).getValue() != 0, value.getColumn());
        if (value instanceof ObjectValue) {
            try {
                value = Type.get("Integer").cast(value);
                return new BooleanValue(((IntegerValue) value).getValue() != 0, value.getColumn());
            } catch (ClassCastException e) {
                throw new ClassCastException("The value " + value.getString() + " cannot be cast to Boolean.");
            }
        }
        throw new ClassCastException("The type " + value.getType().getName() + " cannot be cast to Boolean.");
    }
}
