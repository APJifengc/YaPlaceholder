package cn.ykdlb.yaplaceholder.value;

import cn.ykdlb.yaplaceholder.operator.Operator;
import cn.ykdlb.yaplaceholder.operator.arithmetic.Add;
import cn.ykdlb.yaplaceholder.operator.arithmetic.Divide;
import cn.ykdlb.yaplaceholder.operator.arithmetic.Minus;
import cn.ykdlb.yaplaceholder.operator.arithmetic.Multi;
import cn.ykdlb.yaplaceholder.operator.assignment.*;
import cn.ykdlb.yaplaceholder.type.Type;
import org.jetbrains.annotations.NotNull;

public class DecimalValue extends Value<Double> {
    public DecimalValue(double value, int column) {
        super(value, column);
    }

    @Override
    public @NotNull String getString() {
        return String.valueOf(getValue());
    }

    @Override
    public @NotNull Value<?> operation(Operator operator, Value<?> value) throws UnsupportedOperationException {
        if (value instanceof ObjectValue) {
            try {
                if (value.getValue() instanceof Boolean) value = Type.get("Boolean").cast(value);
                if (value.getValue() instanceof Integer) value = Type.get("Integer").cast(value);
                if (value.getValue() instanceof String) value = Type.get("String").cast(value);
            } catch (ClassCastException e) {
                throw new ClassCastException("The value " + value.getString()+ " cannot cast to Boolean.");
            }
        }
        double doubleValue;
        if (value instanceof DecimalValue) {
            doubleValue = ((DecimalValue) value).getValue();
        } else if (value instanceof IntegerValue) {
            doubleValue = ((IntegerValue) value).getValue();
        } else {
            throw new UnsupportedOperationException("The value " + value.getString() +
                    " is not supported for the operator.");
        }
        if (operator instanceof Add) return new DecimalValue(
                getValue() + doubleValue,
                getColumn()
        );
        if (operator instanceof Minus) return new DecimalValue(
                getValue() - doubleValue,
                getColumn()
        );
        if (operator instanceof Multi) return new DecimalValue(
                getValue() * doubleValue,
                getColumn()
        );
        if (operator instanceof Divide) return new DecimalValue(
                getValue() / doubleValue,
                getColumn()
        );
        if (operator instanceof Equals) return new BooleanValue(
                getValue() == doubleValue,
                getColumn()
        );
        if (operator instanceof NotEquals) return new BooleanValue(
                getValue() != doubleValue,
                getColumn()
        );
        if (operator instanceof GreaterThan) return new BooleanValue(
                getValue() > doubleValue,
                getColumn()
        );
        if (operator instanceof GreaterOrEquals) return new BooleanValue(
                getValue() >= doubleValue,
                getColumn()
        );
        if (operator instanceof LessThan) return new BooleanValue(
                getValue() < doubleValue,
                getColumn()
        );
        if (operator instanceof LessOrEquals) return new BooleanValue(
                getValue() <= doubleValue,
                getColumn()
        );
        throw new UnsupportedOperationException("The operator " + operator.getString() +
                " is not supported for the Decimal type.");
    }

    @Override
    public @NotNull Type<?> getType() {
        return Type.get("Decimal");
    }
}
