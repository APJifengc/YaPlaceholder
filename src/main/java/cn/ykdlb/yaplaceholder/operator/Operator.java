package cn.ykdlb.yaplaceholder.operator;

import cn.ykdlb.yaplaceholder.Component;
import cn.ykdlb.yaplaceholder.YaPlaceholder;
import cn.ykdlb.yaplaceholder.exception.UnknownOperatorException;
import cn.ykdlb.yaplaceholder.operator.arithmetic.*;
import cn.ykdlb.yaplaceholder.operator.assignment.*;
import cn.ykdlb.yaplaceholder.operator.bitwise.*;
import cn.ykdlb.yaplaceholder.operator.logical.*;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public abstract class Operator extends Component {
    private static final Map<String, Class<? extends Operator>> operatorMap = new HashMap<>();

    public static Map<String, Class<? extends Operator>> getOperatorMap() {
        return operatorMap;
    }

    public Operator(int column) {
        super(column);
    }

    public abstract int getPriority();
    @NotNull
    public abstract String getString();
    public static Operator getOperator(String str, int column) throws UnknownOperatorException {
        try {
            return operatorMap.get(str).getConstructor(int.class).newInstance(column);
        } catch (NullPointerException e) {
            throw new UnknownOperatorException("Unknown operator " + str + " .");
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnknownError("Unknown error.");
        }
    }
}
