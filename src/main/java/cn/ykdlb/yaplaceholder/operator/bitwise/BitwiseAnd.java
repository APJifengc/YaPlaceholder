package cn.ykdlb.yaplaceholder.operator.bitwise;

import cn.ykdlb.yaplaceholder.operator.Operator;
import org.jetbrains.annotations.NotNull;

public class BitwiseAnd extends Operator {
    public BitwiseAnd(int column) {
        super(column);
    }

    @Override
    public int getPriority() {
        return 7;
    }

    @Override
    public @NotNull String getString() {
        return "&";
    }
}
