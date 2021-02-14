package cn.ykdlb.yaplaceholder.operator.arithmetic;

import cn.ykdlb.yaplaceholder.operator.Operator;
import org.jetbrains.annotations.NotNull;

public class Divide extends Operator {
    public Divide(int column) {
        super(column);
    }

    @Override
    public int getPriority() {
        return 12;
    }

    @Override
    public @NotNull String getString() {
        return "/";
    }
}
