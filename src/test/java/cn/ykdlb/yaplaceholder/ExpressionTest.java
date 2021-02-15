package cn.ykdlb.yaplaceholder;

import cn.ykdlb.yaplaceholder.function.process.IfFunction;
import cn.ykdlb.yaplaceholder.function.process.SwitchFunction;
import cn.ykdlb.yaplaceholder.operator.arithmetic.Add;
import cn.ykdlb.yaplaceholder.operator.arithmetic.Divide;
import cn.ykdlb.yaplaceholder.operator.arithmetic.Multi;
import cn.ykdlb.yaplaceholder.operator.assignment.Equals;
import cn.ykdlb.yaplaceholder.operator.special.StartParam;
import cn.ykdlb.yaplaceholder.value.BooleanValue;
import cn.ykdlb.yaplaceholder.value.DecimalValue;
import cn.ykdlb.yaplaceholder.value.IntegerValue;
import cn.ykdlb.yaplaceholder.value.StringValue;
import org.bukkit.OfflinePlayer;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExpressionTest {

    public ExpressionTest() throws NoSuchFieldException, IllegalAccessException {
        init();
    }

    @Test
    public void fromInfixExpression() throws Exception {
        Expression test1 = Expression.fromInfixExpression("5+9");
        System.out.println("test1: " + test1);
        assertEquals(3, test1.size());
        assertInstanceOf(IntegerValue.class, test1.get(0));
        assertEquals(5, ((IntegerValue) test1.get(0)).getValue());
        assertInstanceOf(IntegerValue.class, test1.get(1));
        assertEquals(9, ((IntegerValue) test1.get(1)).getValue());
        assertInstanceOf(Add.class, test1.get(2));

        Expression test2 = Expression.fromInfixExpression("5+9*6.0d");
        System.out.println("test2: " + test2);
        assertEquals(5, test2.size());
        assertInstanceOf(IntegerValue.class, test2.get(0));
        assertEquals(5, ((IntegerValue) test2.get(0)).getValue());
        assertInstanceOf(IntegerValue.class, test2.get(1));
        assertEquals(9, ((IntegerValue) test2.get(1)).getValue());
        assertInstanceOf(DecimalValue.class, test2.get(2));
        assertEquals(6d, ((DecimalValue) test2.get(2)).getValue());
        assertInstanceOf(Multi.class, test2.get(3));
        assertInstanceOf(Add.class, test2.get(4));

        Expression test3 = Expression.fromInfixExpression("3+if(1==1,1d,2d)");
        System.out.println("test3: " + test3);
        assertEquals(9, test3.size());
        assertInstanceOf(IntegerValue.class, test3.get(0));
        assertEquals(3, ((IntegerValue) test3.get(0)).getValue());

        assertInstanceOf(StartParam.class, test3.get(1));

        assertInstanceOf(IntegerValue.class, test3.get(2));
        assertEquals(1, ((IntegerValue) test3.get(2)).getValue());

        assertInstanceOf(IntegerValue.class, test3.get(3));
        assertEquals(1, ((IntegerValue) test3.get(3)).getValue());

        assertInstanceOf(Equals.class, test3.get(4));

        assertInstanceOf(DecimalValue.class, test3.get(5));
        assertEquals(1d, ((DecimalValue) test3.get(5)).getValue());

        assertInstanceOf(DecimalValue.class, test3.get(6));
        assertEquals(2d, ((DecimalValue) test3.get(6)).getValue());

        assertInstanceOf(IfFunction.class, test3.get(7));

        assertInstanceOf(Add.class, test3.get(8));

        Expression test4 = Expression.fromInfixExpression("5*(1+2)/3");
        System.out.println("test4: " + test4);
        assertEquals(7, test4.size());
        assertEquals(5, ((IntegerValue) test4.get(0)).getValue());
        assertEquals(1, ((IntegerValue) test4.get(1)).getValue());
        assertEquals(2, ((IntegerValue) test4.get(2)).getValue());
        assertInstanceOf(Add.class, test4.get(3));
        assertInstanceOf(Multi.class, test4.get(4));
        assertEquals(3, ((IntegerValue) test4.get(5)).getValue());
        assertInstanceOf(Divide.class, test4.get(6));

        Expression test5 = Expression.fromInfixExpression("\"hihi(5+2!)\"");
        System.out.println("test5: " + test5);
        assertEquals(1, test5.size());
        assertEquals("hihi(5+2!)", ((StringValue) test5.get(0)).getValue());

        Expression test6 = Expression.fromInfixExpression("if(5)");
        System.out.println("test6: " + test6);
        assertTrue(test6.isError());

        Expression test7 = Expression.fromInfixExpression("switch(5)");
        System.out.println("test7: " + test7);
        assertFalse(test7.isError());

        Expression test8 = Expression.fromInfixExpression("switch(2,true,false)");
        System.out.println("test8: " + test8);
        assertEquals(5, test8.size());
        assertInstanceOf(StartParam.class, test8.get(0));
        assertEquals(2, ((IntegerValue) test8.get(1)).getValue());
        assertEquals(true, ((BooleanValue) test8.get(2)).getValue());
        assertEquals(false, ((BooleanValue) test8.get(3)).getValue());
        assertInstanceOf(SwitchFunction.class, test8.get(4));
    }

    @Test
    public void calculateValue() throws Exception {
        OfflinePlayer player = mock(OfflinePlayer.class);
        Expression test1 = Expression.fromInfixExpression("if(5==(2+switch(1,2,3)),9d,6f)*3D");
        System.out.println("test1: " + test1);
        assertEquals("18.0", test1.calculateValue(player));

        Expression test2 = Expression.fromInfixExpression("if(!(1==1),1,2)");
        System.out.println("test2:" + test2);
        assertEquals("2", test2.calculateValue(player));
    }

    public void init() throws NoSuchFieldException, IllegalAccessException {
        YaPlaceholder main = mock(YaPlaceholder.class);
        when(main.getLogger()).thenReturn(Logger.getGlobal());
        var field = YaPlaceholder.class.getDeclaredField("instance");
        field.setAccessible(true);
        field.set(YaPlaceholder.class, main);
        assertInstanceOf(YaPlaceholder.class, YaPlaceholder.getInstance());
        doCallRealMethod().when(main).init();
        main.init();
    }

    public long measureNanoTime(Runnable runnable) {
        var start = System.nanoTime();
        runnable.run();
        return System.nanoTime() - start;
    }
}