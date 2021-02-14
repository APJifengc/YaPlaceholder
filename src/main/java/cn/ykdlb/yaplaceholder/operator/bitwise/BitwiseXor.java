package cn.ykdlb.yaplaceholder.operator.bitwise;

import cn.ykdlb.yaplaceholder.operator.Operator;
import org.jetbrains.annotations.NotNull;

public class BitwiseXor extends Operator {
    public BitwiseXor(int column) {
        super(column);
    }

    @Override
    public int getPriority() {
        return 6;
    }

    @Override
    public @NotNull String getString() {
        return "^";
    }
}
