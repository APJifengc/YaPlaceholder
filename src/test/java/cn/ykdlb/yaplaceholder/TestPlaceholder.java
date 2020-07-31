package cn.ykdlb.yaplaceholder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Yoooooory
 */
public class TestPlaceholder {

    public static void main(String[] args) {
        Matcher m = Pattern.compile("([^()]+)\\([^()]*\\)").matcher("abc(def(1,2))");
        m.find();
        System.out.println(m.group(1));
    }

}