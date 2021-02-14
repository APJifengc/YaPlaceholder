package cn.ykdlb.yaplaceholder.operator.assignment;

import cn.ykdlb.yaplaceholder.operator.Operator;
import org.jetbrains.annotations.NotNull;

public class GreaterOrEquals extends Operator {
    public GreaterOrEquals(int column) {
        super(column);
    }

    @Override
    public int getPriority() {
        return 9;
    }

    @Override
    public @NotNull String getString() {
        return ">=";
    }
}
