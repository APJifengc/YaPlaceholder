package cn.ykdlb.yaplaceholder.value;

import cn.ykdlb.yaplaceholder.operator.Operator;
import cn.ykdlb.yaplaceholder.operator.assignment.Equals;
import cn.ykdlb.yaplaceholder.operator.assignment.NotEquals;
import cn.ykdlb.yaplaceholder.operator.logical.LogicalAnd;
import cn.ykdlb.yaplaceholder.operator.logical.LogicalOr;
import cn.ykdlb.yaplaceholder.type.BooleanType;
import cn.ykdlb.yaplaceholder.type.Type;
import org.jetbrains.annotations.NotNull;

public class BooleanValue extends Value<Boolean> {
    public BooleanValue(boolean value, int column) {
        super(value, column);
    }
    
    @Override
    public @NotNull String getString() {
        return getValue() ? "true" : "false";
    }

    @Override
    public @NotNull Value<?> operation(Operator operator, Value<?> value) throws UnsupportedOperationException {
        if (value instanceof ObjectValue) {
            try {
                value = Type.get("Integer").cast(value);
                return new BooleanValue(((IntegerValue) value).getValue() != 0, value.getColumn());
            } catch (ClassCastException e) {
                throw new ClassCastException("The value " + value.getString()+ " cannot cast to Boolean.");
            }
        }
        if (!(value instanceof BooleanValue)) throw new UnsupportedOperationException("The value " + value.getString() +
                " is not supported for the operator.");
        if (operator instanceof Equals) return new BooleanValue(
                ((BooleanValue) value).getValue() == this.getValue(),
                getColumn()
        );
        if (operator instanceof NotEquals) return new BooleanValue(
                ((BooleanValue) value).getValue() != this.getValue(),
                getColumn()
        );
        if (operator instanceof LogicalAnd) return new BooleanValue(
                ((BooleanValue) value).getValue() && this.getValue(),
                getColumn()
        );
        if (operator instanceof LogicalOr) return new BooleanValue(
                ((BooleanValue) value).getValue() || this.getValue(),
                getColumn()
        );
        throw new UnsupportedOperationException("The operator " + operator.getString() +
                " is not supported for the Boolean type.");
    }

    @Override
    public @NotNull Type<?> getType() {
        return Type.get("Boolean");
    }
}
