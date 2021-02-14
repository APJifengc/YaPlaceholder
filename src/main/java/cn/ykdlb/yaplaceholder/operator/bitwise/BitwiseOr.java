package cn.ykdlb.yaplaceholder.operator.bitwise;

import cn.ykdlb.yaplaceholder.operator.Operator;
import org.jetbrains.annotations.NotNull;

public class BitwiseOr extends Operator {
    public BitwiseOr(int column) {
        super(column);
    }

    @Override
    public int getPriority() {
        return 5;
    }

    @Override
    public @NotNull String getString() {
        return "|";
    }
}
