package cn.ykdlb.yaplaceholder.value;

import cn.ykdlb.yaplaceholder.operator.Operator;
import cn.ykdlb.yaplaceholder.operator.arithmetic.*;
import cn.ykdlb.yaplaceholder.operator.assignment.*;
import cn.ykdlb.yaplaceholder.operator.bitwise.*;
import cn.ykdlb.yaplaceholder.type.Type;
import org.jetbrains.annotations.NotNull;

public class LongValue extends Value<Long> {
    public LongValue(long value, int column) {
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
                if (value.getValue() instanceof Double) value = Type.get("Decimal").cast(value);
                if (value.getValue() instanceof String) value = Type.get("String").cast(value);
            } catch (ClassCastException e) {
                throw new ClassCastException("The value " + value.getString()+ " cannot cast to Long.");
            }
        }
        long intValue;
        if (value instanceof DecimalValue) {
            intValue = ((DecimalValue) value).getValue().intValue();
        } else if (value instanceof LongValue) {
            intValue = ((LongValue) value).getValue();
        } else {
            throw new UnsupportedOperationException("The value " + value.getString() +
                    " is not supported for the operator.");
        }
        if (operator instanceof Add) return new LongValue(
                getValue() + intValue,
                getColumn()
        );
        if (operator instanceof Minus) return new LongValue(
                getValue() - intValue,
                getColumn()
        );
        if (operator instanceof Multi) return new LongValue(
                getValue() * intValue,
                getColumn()
        );
        if (operator instanceof Divide) return new LongValue(
                getValue() / intValue,
                getColumn()
        );
        if (operator instanceof Mod) return new LongValue(
                Math.floorMod(getValue(),intValue),
                getColumn()
        );
        if (operator instanceof BitwiseAnd) return new LongValue(
                getValue() & intValue,
                getColumn()
        );
        if (operator instanceof BitwiseOr) return new LongValue(
                getValue() | intValue,
                getColumn()
        );
        if (operator instanceof BitwiseXor) return new LongValue(
                getValue() ^ intValue,
                getColumn()
        );
        if (operator instanceof LeftShift) return new LongValue(
                getValue() << intValue,
                getColumn()
        );
        if (operator instanceof RightShift) return new LongValue(
                getValue() >> intValue,
                getColumn()
        );
        if (operator instanceof ZeroFillRightShift) return new LongValue(
                getValue() >>> intValue,
                getColumn()
        );
        if (operator instanceof Equals) return new BooleanValue(
                getValue() == intValue,
                getColumn()
        );
        if (operator instanceof NotEquals) return new BooleanValue(
                getValue() != intValue,
                getColumn()
        );
        if (operator instanceof GreaterThan) return new BooleanValue(
                getValue() > intValue,
                getColumn()
        );
        if (operator instanceof GreaterOrEquals) return new BooleanValue(
                getValue() >= intValue,
                getColumn()
        );
        if (operator instanceof LessThan) return new BooleanValue(
                getValue() < intValue,
                getColumn()
        );
        if (operator instanceof LessOrEquals) return new BooleanValue(
                getValue() <= intValue,
                getColumn()
        );
        throw new UnsupportedOperationException("The operator " + operator.getString() +
                " is not supported for the Long type.");
    }

    @Override
    public @NotNull Type<?> getType() {
        return Type.get("Long");
    }
}
