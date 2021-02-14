package cn.ykdlb.yaplaceholder.operator.special;

import cn.ykdlb.yaplaceholder.operator.Operator;
import org.jetbrains.annotations.NotNull;

public class Bracket extends Operator {
    public Bracket(int column) {
        super(column);
    }

    @Override
    public int getPriority() {
        return Integer.MIN_VALUE; // SUPREME
    }

    @Override
    public @NotNull String getString() {
        return "(";
    }
}
