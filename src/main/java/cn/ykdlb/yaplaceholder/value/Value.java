package cn.ykdlb.yaplaceholder.value;

import cn.ykdlb.yaplaceholder.Component;
import cn.ykdlb.yaplaceholder.exception.UnknownDataTypeException;
import cn.ykdlb.yaplaceholder.operator.Operator;
import cn.ykdlb.yaplaceholder.type.Type;
import org.jetbrains.annotations.NotNull;

public abstract class Value<T> extends Component {
    private final T value;

    public Value(T value, int column) {
        super(column);
        this.value = value;
    }

    @NotNull
    public abstract String getString();

    @NotNull
    public abstract Value<?> operation(Operator operator, Value<?> value) throws UnsupportedOperationException;

    @NotNull
    public abstract Type<?> getType();

    @NotNull
    public T getValue() {
        return value;
    }

    public static Value<?> getValueFromString(String string, int column) throws UnknownDataTypeException {
        for (Type<?> type : Type.getTypeMap().values()) {
            if (type.isType(string)) {
                return type.getValue(string, column);
            }
        }
        throw new UnknownDataTypeException("Unknown data type for value " + string + " .");
    }

    @Override
    public String toString() {
        return "Value{" +
                "value=" + value +
                '}';
    }
}
