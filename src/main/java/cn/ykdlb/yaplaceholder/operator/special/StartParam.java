package cn.ykdlb.yaplaceholder.operator.special;

import cn.ykdlb.yaplaceholder.operator.Operator;
import org.jetbrains.annotations.NotNull;

public class StartParam extends Operator {
    public StartParam(int column) {
        super(column);
    }

    @Override
    public int getPriority() {
        return Integer.MIN_VALUE; // SUPREME
    }

    @Override
    public @NotNull String getString() {
        return ")";
    }
}
