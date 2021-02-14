package cn.ykdlb.yaplaceholder.operator.logical;

import cn.ykdlb.yaplaceholder.operator.Operator;
import org.jetbrains.annotations.NotNull;

public class LogicalOr extends Operator {
    public LogicalOr(int column) {
        super(column);
    }

    @Override
    public int getPriority() {
        return 20;
    }

    @Override
    public @NotNull String getString() {
        return "||";
    }
}
