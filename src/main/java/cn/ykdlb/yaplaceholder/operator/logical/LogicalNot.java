package cn.ykdlb.yaplaceholder.operator.logical;

import cn.ykdlb.yaplaceholder.operator.Operator;
import org.jetbrains.annotations.NotNull;

public class LogicalNot extends Operator {
    public LogicalNot(int column) {
        super(column);
    }

    @Override
    public int getPriority() {
        return 4;
    }

    @Override
    public @NotNull String getString() {
        return "!";
    }
}
