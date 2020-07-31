package cn.ykdlb.yaplaceholder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Yoooooory
 */
public class TestPlaceholder {

    public static void main(String[] args) {
        System.out.println(1+(1-1));
        System.out.println(1+2);
        System.out.println(1-2);
        System.out.println(1*2);
        System.out.println(1/2);
        System.out.println(1%2);
        System.out.println(1<<2);
        System.out.println(-1>>2);
        System.out.println(-1>>>2);
        System.out.println(1&2);
        System.out.println(1^2);
        System.out.println(1|2);
        System.out.println(true&&false);
        System.out.println(true||false);
        System.out.println(1>2);
        System.out.println(1<2);
        System.out.println(1>=2);
        System.out.println(1<=2);
        System.out.println(1==2);
        System.out.println(1!=2);
        System.out.println("true&&true".substring(0,3));
    }

}