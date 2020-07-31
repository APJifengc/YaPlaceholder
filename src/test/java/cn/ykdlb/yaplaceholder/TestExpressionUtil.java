package cn.ykdlb.yaplaceholder;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * A math util to calculate expression.
 * <del>(So why don't users use math placeholder?)</del>
 * (Invalid now.)
 *
 * @author Yoooooory
 */
public class TestExpressionUtil {

    private static final Map<String, Integer> calcPriority = new HashMap<String, Integer>() {{
        put("+", 1);
        put("-", 1);
        put("*", 2);
        put("/", 2);
        // "#" == "%"
        put("#", 2);
        put("(", 3);
        put("^", 4);
    }};

    /**
     * Calculate expression. <strong style="color: red">( Invalid now )</strong>
     *
     * @param expression The expression needs to be calculate.
     * @return The result of the expression.
     * @throws IllegalArgumentException Throws when the expression is invalid.
     */
    @Deprecated
    public static double calculate(String expression) throws IllegalArgumentException {
        expression = expression.replace(" ", "");
        Stack<Double> numbers = new Stack<>();
        Stack<String> operators = new Stack<>();
        // Use to build operator and number strings.
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (Character.isDigit(c) || c == '.' || (sb.length() == 0 && c == '-')) {
                // The char is a part of number: build number.
                sb.append(c);
            } else {
                // The char is an operator: build operator / calculate expression.
                if (sb.length() > 0) {
                    ///
                    try {
                        // sb containing number - push number and build operator.
                        numbers.push(Double.parseDouble(sb.toString()));
//                        if (expression.length())
                    } catch (NullPointerException | NumberFormatException e) {
                        // otherwise - build operator
                        sb.append(c);
                        throw new IllegalArgumentException(String.format("Cannot solve expression '%s' char '%c' at %d: %s", expression, c, i, e.getMessage()));
                    }
                    sb.delete(0, sb.length());
                    ///
                }
                String oper = sb.toString();
                if (")".equals(oper)) {
                    // Special
                    while (true) {
                        try {
                            String op = operators.pop();
                            if (!"(".equals(op)) {
                                double num = numbers.pop();
                                numbers.push(calculateResult(op, numbers.pop(), num));
                            } else {
                                break;
                            }
                        } catch (Exception e) {
                            throw new IllegalArgumentException(String.format("Cannot solve expression '%s' char '%c' at %d: %s", expression, c, i, e.getMessage()));
                        }
                    }
                } else if (!operators.empty() && calcPriority.get(oper) < calcPriority.get(operators.peek())) {
                    // Calculate
                    while (true) {
                        if (operators.empty() || calcPriority.get(oper) < calcPriority.get(operators.peek())) {
                            operators.push(oper);
                            break;
                        }
                        double num = numbers.pop();
                        try {
                            numbers.push(calculateResult(operators.pop(), numbers.pop(), num));
                        } catch (Exception e) {
                            throw new IllegalArgumentException(String.format("Cannot solve expression '%s' char '%c' at %d: %s", expression, c, i, e.getMessage()));
                        }
                    }
                } else {
                    // Push on to the stack
                    operators.push(oper);
                }
            }
        }
        while (true) {
            if (operators.empty()) {
                break;
            } else {
                if (sb.length() > 0) {
                    numbers.push(Double.parseDouble(sb.toString()));
                } else {
                    throw new IllegalArgumentException(String.format("Cannot solve expression '%s' string '%s'.", expression, sb.toString()));
                }
                double num = numbers.pop();
                numbers.push(calculateResult(operators.pop(), numbers.pop(), num));
            }
        }
        try {
            return numbers.pop();
        } catch (EmptyStackException e) {
            throw new IllegalArgumentException(String.format("Cannot solve expression '%s': %s", expression, e.getMessage()));
        }
    }

    private static double calculateResult(String op, double num1, double num2) {
        switch (op) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "/":
                return num1 / num2;
            case "#":
                return num1 % num2;
            case "^":
                return Math.pow(num1, num2);
        }
        throw new IllegalArgumentException("Unknown operator '" + op + "'");
    }

    public static boolean parseExpression(String expression) {
        expression = expression.replace(" ", "");
        return true;
    }

}