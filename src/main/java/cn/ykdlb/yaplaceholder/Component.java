package cn.ykdlb.yaplaceholder;

import java.lang.reflect.Constructor;

public class Component {
    private final int column;

    public Component(int column) {
        this.column = column;
    }

    public int getColumn() {
        return column;
    }
}
