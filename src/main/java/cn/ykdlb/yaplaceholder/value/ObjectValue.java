package cn.ykdlb.yaplaceholder.value;

import cn.ykdlb.yaplaceholder.operator.Operator;
import cn.ykdlb.yaplaceholder.operator.assignment.Equals;
import cn.ykdlb.yaplaceholder.operator.assignment.NotEquals;
import cn.ykdlb.yaplaceholder.type.Type;
import org.jetbrains.annotations.NotNull;

public class ObjectValue extends Value<Object> {
    public ObjectValue(Object value, int column) {
        super(value, column);
    }

    @Override
    public @NotNull String getString() {
        return getValue().toString();
    }

    @Override
    public @NotNull Value<?> operation(Operator operator, Value<?> value) throws UnsupportedOperationException {
        if (operator instanceof Equals) return new BooleanValue(getValue().equals(value.getValue()), getColumn());
        if (operator instanceof NotEquals) return new BooleanValue(!getValue().equals(value.getValue()), getColumn());
        throw new UnsupportedOperationException("The operator " + operator.getString() +
                " is not supported for the Object type.");
    }

    @Override
    public @NotNull Type<?> getType() {
        return Type.get("Object");
    }
}
