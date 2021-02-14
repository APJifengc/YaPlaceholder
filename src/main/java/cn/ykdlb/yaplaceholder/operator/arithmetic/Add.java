package cn.ykdlb.yaplaceholder.operator.arithmetic;

import cn.ykdlb.yaplaceholder.operator.Operator;
import org.jetbrains.annotations.NotNull;

public class Add extends Operator {
    public Add(int column) {
        super(column);
    }

    @Override
    public int getPriority() {
        return 11;
    }

    @Override
    public @NotNull String getString() {
        return "+";
    }
}
