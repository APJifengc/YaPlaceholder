package cn.ykdlb.yaplaceholder.operator.assignment;

import cn.ykdlb.yaplaceholder.operator.Operator;
import org.jetbrains.annotations.NotNull;

public class NotEquals extends Operator {
    public NotEquals(int column) {
        super(column);
    }

    @Override
    public int getPriority() {
        return 8;
    }

    @Override
    public @NotNull String getString() {
        return "!=";
    }
}
