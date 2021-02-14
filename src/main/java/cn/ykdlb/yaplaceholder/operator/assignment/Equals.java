package cn.ykdlb.yaplaceholder.operator.assignment;

import cn.ykdlb.yaplaceholder.operator.Operator;
import org.jetbrains.annotations.NotNull;

public class Equals extends Operator {
    public Equals(int column) {
        super(column);
    }

    @Override
    public int getPriority() {
        return 8;
    }

    @Override
    public @NotNull String getString() {
        return "==";
    }
}
