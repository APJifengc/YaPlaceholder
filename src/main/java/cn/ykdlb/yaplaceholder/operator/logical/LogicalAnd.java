package cn.ykdlb.yaplaceholder.operator.logical;

import cn.ykdlb.yaplaceholder.operator.Operator;
import org.jetbrains.annotations.NotNull;

public class LogicalAnd extends Operator {
    public LogicalAnd(int column) {
        super(column);
    }

    @Override
    public int getPriority() {
        return 4;
    }

    @Override
    public @NotNull String getString() {
        return "&&";
    }
}
