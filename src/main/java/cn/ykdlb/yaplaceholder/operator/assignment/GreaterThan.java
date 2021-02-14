package cn.ykdlb.yaplaceholder.operator.assignment;

import cn.ykdlb.yaplaceholder.operator.Operator;
import org.jetbrains.annotations.NotNull;

public class GreaterThan extends Operator {
    public GreaterThan(int column) {
        super(column);
    }

    @Override
    public int getPriority() {
        return 9;
    }

    @Override
    public @NotNull String getString() {
        return ">";
    }
}
