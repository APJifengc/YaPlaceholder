package cn.ykdlb.yaplaceholder.type;

import cn.ykdlb.yaplaceholder.value.ObjectValue;
import cn.ykdlb.yaplaceholder.value.StringValue;
import cn.ykdlb.yaplaceholder.value.Value;
import org.jetbrains.annotations.NotNull;

public class ObjectType extends Type<Object> {
    @Override
    public @NotNull String getName() {
        return "Object";
    }

    @Override
    public @NotNull Value<Object> getValue(String shownText, int column) {
        return new ObjectValue(shownText, column);
    }

    @Override
    public boolean isType(String shownText) {
        return false;
    }

    @Override
    public @NotNull Value<Object> cast(Value<?> value) throws ClassCastException {
        return new ObjectValue(value.getValue(), value.getColumn());
    }
}
