package cn.ykdlb.yaplaceholder;

import cn.ykdlb.yaplaceholder.exception.InvalidFunctionException;
import cn.ykdlb.yaplaceholder.exception.UnknownFunctionException;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is the placeholder expansion class.
 *
 * @author APJifengc
 * @author Yoooooory
 */
public class YaPlaceholderExpansion extends PlaceholderExpansion {

    private final YaPlaceholder plugin;

    /**
     * Math operators' priority.
     */
    private final Map<String, Integer> mathPriority = new HashMap<String, Integer>() {{
        put("(", 14);
        put("*", 12); // num
        put("/", 12); // num
        put("#", 12); // num "#" = "%"
        put("+", 11); // num & str
        put("-", 11); // num
        put("+-", 11); // num
        put(">>", 10); // num
        put(">>>", 10); // num
        put("<<", 10); // num
        put("&", 7); // num
        put("^", 6); // num
        put("|", 5); // num
    }};

    private final Map<String, Integer> comparePriority = new HashMap<String, Integer>() {{
        put(">", 9); // num
        put(">=", 9); // num
        put("<", 9); // num
        put("<=", 9); // num
        put("==", 8); // obj
        put("!=", 8); // obj
        put("&&", 4); // bool
        put("||", 3); // bool
        // !
        // Future feature: ? :
        // Unnecessary: = += -= *= /= #= >>= <<= &= ^= |=
    }};

    /**
     * Math, comparison and condition operators' priority.
     */
    private final Map<String, Integer> priority = new HashMap<String, Integer>() {{
        putAll(mathPriority);
        putAll(comparePriority);
    }};

    /**
     * When the class constructed, the expansion will automatics register.
     *
     * @param plugin The instance of the plugin.
     */
    public YaPlaceholderExpansion(YaPlaceholder plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String getIdentifier() {
        return "e";
    }

    @Override
    public String getAuthor() {
        return plugin.getDescription().getAuthors().toString();
    }

    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        try {
            return getPlainString(parseExpression(player, params));
        } catch (Exception e) {
            e.printStackTrace();
            return "§4Error";
        }
    }

    /**
     * Parse a expression.
     *
     * @param player     A refer player.
     * @param expression The expression.
     * @return The value of the expression.
     */
    public Object parseExpression(OfflinePlayer player, String expression) throws InvalidFunctionException, UnknownFunctionException {
        Object obj = getDataValue(player, expression);
        if (obj != null) {
            return obj;
        }
        String exception = String.format("Cannot solve expression '%s'.", expression);
        Stack<Object> objects = new Stack<>();
        Stack<String> operators = new Stack<>();
        int index = -1;
        String stat = "";
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (index != -1) {
                String str = expression.substring(index, i);
                // Building content / calculate expression
                switch (stat) {
                    case "string":
                        // Building string.
                        if (c == '\'') {
                            // A string's end or content ('').
                            if (i + 1 < expression.length() && expression.charAt(i + 1) == '\'') {
                                // content
                                i++;
                            } else {
                                // end the build.
                                objects.push(getDataValue(player, expression.substring(index, i + 1)));
                                index = -1;
                                stat = "";
                            }
                        }
                        break;
                    case "number":
                        // Building number.
                        if (Character.isDigit(c)) {
                            continue;
                        } else if (c == '.') {
                            if (i + 1 < expression.length() && !str.contains(".") && Character.isDigit(expression.charAt(i + 1))) {
                                // A part of number
                                i++;
                            } else {
                                throw new IllegalArgumentException(exception);
                            }
                        } else {
                            // Not a part of number, end the build.
                            objects.push(getDataValue(player, str));
                            stat = "";
                            index = -1;
                            if (c == '-') {
                                index = i;
                                stat = "operator";
                            } else if (c != 'd' && c != 'D' && c != 'f' && c != 'F') {
                                i--;
                            }
                        }
                        break;
                    case "operator":
                        // Try to build operator.
                        if (c == '\'' || Character.isDigit(c) || (!"+".equals(str) && c == '-') || c == 't' || c == 'f' || c == '(') {
                            // Not a operator now.
                            stat = "";
                            String oper = str;
                            index = -1;
                            if (priority.containsKey(oper)) {
                                // Calc expression.
                                // 2-(1+2)*(1+3)
                                if (!operators.empty() && priority.get(oper) < priority.get(operators.peek())) {
                                    while (true) {
                                        if (operators.empty() || priority.get(oper) >= priority.get(operators.peek()) || "(".equals(operators.peek())) {
                                            operators.push(oper);
                                            break;
                                        }
                                        Object object = objects.pop();
                                        try {
                                            objects.push(calculateResult(operators.pop(), objects.pop(), object));
                                        } catch (Exception e) {
                                            throw new IllegalArgumentException(exception);
                                        }
                                    }
                                } else {
                                    // Push operator.
                                    operators.push(oper);
                                }
                            } else {
                                throw new IllegalArgumentException(exception);
                            }
                            i--;
                        }
                        break;
                    default:
                        throw new IllegalArgumentException(exception);
                }
            } else {
                // An element's start.
                if (c == ' ') {
                } else if (c == '\'') {
                    // A string's start.
                    stat = "string";
                } else if (Character.isDigit(c) || c == '-') {
                    // A number's start.
                    stat = "number";
                } else if (c == 't' || c == 'f') {
                    // A boolean's start, build.
                    int a = c == 't' ? 4 : 5;
                    if (i + a <= expression.length()) {
                        String substring = expression.substring(i, i + a);
                        i += a - 1;
                        if ("true".equals(substring)) {
                            objects.add(true);
                        } else if ("false".equals(substring)) {
                            objects.add(false);
                        } else {
                            throw new IllegalArgumentException(exception);
                        }
                    } else {
                        throw new IllegalArgumentException(exception);
                    }
                    continue;
                } else if (c == '$' || c == '_' || Character.isLetter(c)) {
                    boolean isString = false;
                    StringBuilder sb = new StringBuilder(String.valueOf(c));
                    Stack<Integer> indexes = new Stack<>();
                    while (true) {
                        c = expression.charAt(++i);
                        sb.append(c);
                        if (c == '\'') {
                            if (isString) {
                                if (i + 1 < expression.length() && expression.charAt(i + 1) == '\'') {
                                    // content
                                    sb.append("'");
                                    i++;
                                } else {
                                    // end the build.
                                    isString = false;
                                }
                            } else {
                                isString = true;
                            }
                        } else if (c == '(') {
                            if (!isString) {
                                indexes.push(sb.length() - 1);
                            }
                        } else if (c == ')' && !isString) {
                            if (!indexes.empty()) {
                                for (int j = indexes.pop() - 1; j >= 0; j--) {
                                    char cha = sb.charAt(j);
                                    if (!Character.isLetter(cha) && !Character.isDigit(cha)) {
                                        if (cha == '$' || cha == '_') {
                                            sb.replace(j, sb.length(), getDataString(parseFunction(player, sb.substring(j))));
                                        } else {
                                            sb.replace(j + 1, sb.length(), getDataString(parseFunction(player, sb.substring(j + 1))));
                                        }
                                        break;
                                    } else if (j == 0) {
                                        sb.replace(j, sb.length(), getDataString(parseFunction(player, sb.substring(j))));
                                        break;
                                    }
                                }
                                if (indexes.empty()) {
                                    objects.add(getDataValue(player, sb.toString()));
                                    break;
                                }
                            } else {
                                throw new IllegalArgumentException(exception);
                            }
                        }
                    }
                    continue;
                } else if (c == ')') {
                    // Special
                    while (true) {
                        try {
                            String op = operators.pop();
                            if (!"(".equals(op)) {
                                Object object = objects.pop();
                                objects.push(calculateResult(op, objects.pop(), object));
                            } else {
                                break;
                            }
                        } catch (Exception e) {
                            throw new IllegalArgumentException(exception);
                        }
                    }
                    continue;
                } else {
                    // maybe an operator..
                    stat = "operator";
                }
                index = i;
            }
        }
        if (index != -1) {
            objects.push(getDataValue(player, expression.substring(index)));
        }
        while (!operators.empty()) {
            Object object = objects.pop();
            objects.push(calculateResult(operators.pop(), objects.pop(), object));
        }
        try {
            return objects.pop();
        } catch (
                EmptyStackException e) {
            throw new IllegalArgumentException(exception);
        }

    }

    /**
     * Parse a function string.
     *
     * @param player A refer player.
     * @param func   The function string needs to be parsed.
     * @return The result of parsing.
     * @throws UnknownFunctionException Throws when the function is unknown.
     * @throws InvalidFunctionException Throws when cannot solve the string as a function.
     * @throws IllegalArgumentException Throws when function got some error.
     */
    public Object parseFunction(OfflinePlayer player, String func) throws UnknownFunctionException, InvalidFunctionException {
        List<Object> args = getArguments(player, func);
        if (args == null) {
            throw new InvalidFunctionException(String.format("Cannot solve '%s' as a function.", func));
        }
        String name = (String) args.get(0);
        if ("if".equalsIgnoreCase(name)) {
            if (args.size() == 4) {
                if (args.get(1) instanceof Boolean) {
                    if ((boolean) args.get(1)) {
                        return args.get(2);
                    } else {
                        return args.get(3);
                    }
                } else {
                    throw new IllegalArgumentException(String.format("The first argument of 'if' function should be a boolean, but got '%s'.", args.get(1)));
                }
            } else {
                throw new IllegalArgumentException(String.format("Function 'if' except 3 arguments but got %d arguments.", args.size() - 1));
            }
        } else if ("switch".equalsIgnoreCase(name)) {
            if (args.size() >= 3) {
                if (args.get(1) instanceof Integer) {
                    int index = (Integer) args.get(1);
                    if (index < args.size() - 2) {
                        return args.get(index + 2);
                    } else {
                        throw new IndexOutOfBoundsException(String.format("The number of elements are %d (index %d), but got switched for %s.", args.size() - 2, args.size() - 3, args.get(1)));
                    }
                } else {
                    throw new IllegalArgumentException(String.format("The first argument of 'switch' function should be a integer, but got '%s'.", args.get(1)));
                }
            } else {
                throw new IllegalArgumentException(String.format("Function 'switch' except at least 2 arguments but got %d arguments.", args.size() - 1));
            }
        } else if ("value".equalsIgnoreCase(name)) {
            if (args.size() == 2) {
                if (args.get(1) instanceof String) {
                    return parseExpression(player, (String) args.get(1));
                } else {
                    throw new IllegalArgumentException(String.format("The first argument of 'switch' function should be a string, but got '%s'.", args.get(1)));
                }
            } else {
                throw new IllegalArgumentException(String.format("Function 'value' except 1 arguments but got %d arguments.", args.size() - 1));
            }
        }
        throw new UnknownFunctionException(String.format("Unknown function '%s'.", args.get(0)));
    }

    /**
     * Get arguments of a minimal function string.
     *
     * @param player A refer player.
     * @param string A minimal function string.
     * @return The arguments of the string, the first element is the function name (String). Return null for a invalid function.
     */
    public List<Object> getArguments(OfflinePlayer player, String string) throws UnknownFunctionException, InvalidFunctionException {
        List<Object> list = new ArrayList<>();
        list.add(string.substring(0, string.indexOf('(')));
        StringBuilder sb = new StringBuilder();
        for (String str : string.substring(string.indexOf('(') + 1, string.length() - 1).split(",")) {
            if (sb.length() != 0) {
                sb.append(',');
            }
            sb.append(str);
            try {
                list.add(Objects.requireNonNull(parseExpression(player, sb.toString())));
                sb.delete(0, sb.length());
            } catch (Exception ignored) {
            }
        }
        return sb.length() > 0 ? null : list;
    }

    /**
     * Get the value of a string.
     *
     * @param player A refer player.
     * @param string The string.
     * @return The string's value. Null for unknown string.
     */
    public Object getDataValue(OfflinePlayer player, String string) {
        if ("''".equals(string)) {
            // Empty string
            return "";
        } else if (string.replace("''", "").matches("'[^']*'")) {
            // String
            string = PlaceholderAPI.setPlaceholders(player, string.replace("''", "'").replaceAll("([^\\\\])\\$", "$1%"));
            return string.substring(1, string.length() - 1);
        } else if (string.matches("-?\\d+\\.\\d+[dfDF]?")) {
            // Decimal (Double or Float)
            return Double.parseDouble(string);
        } else if (string.matches("-?\\d+")) {
            // Integer
            return Integer.parseInt(string);
        } else if ("true".equals(string) || "false".equals(string)) {
            // Boolean
            return Boolean.parseBoolean(string);
        }
        return null;
    }

    /**
     * Get plain string.
     *
     * @param object An object.
     * @return The plain string of the object.
     */
    String getPlainString(Object object) {
        if (object instanceof Double) {
            return BigDecimal.valueOf((Double) object).stripTrailingZeros().toPlainString();
        } else {
            return object.toString();
        }
    }

    /**
     * Get data string.
     *
     * @param object An object.
     * @return The data string of the object.
     */
    String getDataString(Object object) {
        if (object instanceof String) {
            return "'" + (String) ((String) object).replace("'", "''") + "'";
        } else {
            return object.toString();
        }
    }

    Object calculateResult(String oper, Object obj1, Object obj2) {
        if (obj1 instanceof Number && obj2 instanceof Number) {
            if (obj1 instanceof Integer && obj2 instanceof Integer) {
                // Integer only
                int num1 = (int) obj1;
                int num2 = (int) obj2;
                switch (oper) {
                    case "#":
                        return Math.floorMod(num1, num2);
                    case "<<":
                        return num1 << num2;
                    case ">>":
                        return num1 >> num2;
                    case ">>>":
                        return num1 >>> num2;
                    case "&":
                        return num1 & num2;
                    case "^":
                        return num1 ^ num2;
                    case "|":
                        return num1 | num2;
                }
            }
            double num1 = Double.parseDouble(obj1.toString());
            double num2 = Double.parseDouble(obj2.toString());
            switch (oper) {
                case "+":
                    return num1 + num2;
                case "-":
                case "+-":
                    return num1 - num2;
                case "*":
                    return num1 * num2;
                case "/":
                    return num1 / num2;
                case "#":
                    return num1 % num2;
                case "<":
                    return num1 < num2;
                case ">":
                    return num1 > num2;
                case "<=":
                    return num1 <= num2;
                case ">=":
                    return num1 >= num2;
            }
        } else if (obj1 instanceof Boolean && obj2 instanceof Boolean) {
            if ("&&".equals(oper)) {
                return (boolean) obj1 && (boolean) obj2;
            } else if ("||".equals(oper)) {
                return (boolean) obj1 || (boolean) obj2;
            }
        } else if (obj1 instanceof String && obj2 instanceof String) {
            if ("+".equals(oper)) {
                return (String) obj1 + (String) obj2;
            }
        }
        if ("==".equals(oper)) {
            return obj1.equals(obj2);
        } else if ("!=".equals(oper)) {
            return !obj1.equals(obj2);
        }
        throw new IllegalArgumentException();
    }

}