package cn.ykdlb.yaplaceholder;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.List;

/**
 * This is the Placeholder Expansion class.
 *
 * @author APJifengc
 */
public class YaPlaceholderExpansion extends PlaceholderExpansion {
    private final YaPlaceholder plugin;

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
    public boolean canRegister() {
        return true;
    }

    @Override
    public String getIdentifier() {
        return "yaplaceholder";
    }

    @Override
    public String getAuthor() {
        return "APJifengc";
    }

    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        try {
            return getShownText(getValue(identifier, player));
        } catch (Exception e) {
            e.printStackTrace();
            return "§4Error: " + e.getMessage();
        }
    }

    /**
     * Get the string's value.
     *
     * @param string The string to get value.
     * @param player The player to get value.
     * @return The string's value.
     * @throws Exception Throws when the string can't analysis.
     */
    public String getValue(String string, Player player) throws Exception {
        if (string.contains("/&")) {
            return getValue(string.replace("/&","%"), player);
        } else {
            if (string.charAt(0) == '{' && string.charAt(string.length()-1) == '}') {
                char closeChar = 0;
                IntStream chars = string.substring(1,string.length()-1).chars();
                int startPos = 1;
                int pos = 1;
                List<String> params = new ArrayList<>();
                for (int character : chars.toArray()) {
                    if (closeChar != 0) {
                        if (character == closeChar) {
                            closeChar = 0;
                        }
                    } else {
                        if (character == '{') closeChar = '}';
                        if (character == '(') closeChar = ')';
                        if (character == '"') closeChar = '"';
                        if (character == '|') {
                            params.add(string.substring(startPos, pos));
                            startPos = pos +1;
                        }
                    }
                    pos++;
                }
                params.add(string.substring(startPos, pos));

                switch(params.get(0)) {
                    case "IF":
                        String param1 = getValue(params.get(1), player);
                        String param2 = getValue(params.get(2), player);
                        String param3 = getValue(params.get(3), player);
                        if (param1.equals("true")) {
                            return param2;
                        } else {
                            return param3;
                        }
                    case "TEXTJOIN":
                        List<String> texts = new ArrayList<>();
                        for (int i=1;i<params.size();i++) {
                            texts.add(getShownText(params.get(i)));
                        }
                        return "\"" + String.join (
                                getShownText(getValue(params.get(0), player)),
                                texts
                        ) + "\"";
                    case "REPLACE":
                        param1 = getValue(params.get(1), player);
                        param2 = getValue(params.get(2), player);
                        param3 = getValue(params.get(3), player);
                        return getShownText(param1).replace(getShownText(param2),getShownText(param3));
                    case "TIME":
                        String type = getValue(params.get(1), player);
                        switch (type) {
                            case "HOUR":
                                return String.valueOf(Calendar.getInstance().get(Calendar.HOUR));
                            case "MINUTE":
                                return String.valueOf(Calendar.getInstance().get(Calendar.MINUTE));
                            case "SECOND":
                                return String.valueOf(Calendar.getInstance().get(Calendar.SECOND));
                            case "DAY":
                                return String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                            case "MONTH":
                                return String.valueOf(Calendar.getInstance().get(Calendar.MONTH));
                            case "YEAR":
                                return String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
                            case "WEEKDAY":
                                return String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
                        }
                    case "SWITCH":
                        return params.get(Integer.parseInt(getValue(params.get(1), player))+1);
                    case "PARSE":
                        return getDataText(PlaceholderAPI.setPlaceholders(player, getShownText(getValue(params.get(1), player))));
                    case "APJ":
                        // 夹 带 私 货
                        return "牛逼";
                    default:
                        throw new Exception(params.get(0) + " is not a available function.");
                }
            } else if (string.charAt(0) == '(' && string.charAt(string.length()-1) == ')') {
                return getValue(string.substring(1, string.length() - 1), player);
            } else {
                char closeChar = 0;
                int pos = 0;
                String left,right;
                int[] chars = string.chars().toArray();
                // Priority 3
                for (int character : chars) {
                    if (closeChar != 0) {
                        if (character == closeChar) {
                            closeChar = 0;
                        }
                    } else {
                        if (character == '{') closeChar = '}';
                        if (character == '(') closeChar = ')';
                        if (character == '"') closeChar = '"';
                        if (character == '+') {
                            left = getValue(string.substring(0, pos), player);
                            right = getValue(string.substring(pos+1), player);
                            if (isString(left) || isString(right)) {
                                return "\"" + getShownText(left) + getShownText(right) +"\"";
                            }
                            if (isFloat(left) || isFloat(right)) {
                                return (Float.parseFloat(getShownText(left)) +
                                        Float.parseFloat(getShownText(right))) + "F";
                            }
                            return String.valueOf(Integer.parseInt(getShownText(left)) +
                                    Integer.parseInt(getShownText(right)));
                        }
                        if (character == '-') {
                            left = getValue(string.substring(0, pos), player);
                            right = getValue(string.substring(pos+1), player);
                            if (isFloat(left) || isFloat(right)) {
                                return (Float.parseFloat(getShownText(left)) -
                                        Float.parseFloat(getShownText(right))) + "F";
                            }
                            return String.valueOf(Integer.parseInt(getShownText(left)) -
                                    Integer.parseInt(getShownText(right)));
                        }
                        if (character == '>' && string.charAt(pos+1) == '=') {
                            left = getValue(string.substring(0, pos), player);
                            right = getValue(string.substring(pos+2), player);
                            if (Float.parseFloat(getShownText(left)) >=
                                    Float.parseFloat(getShownText(right))) {
                                return "true";
                            } else {
                                return "false";
                            }
                        }
                        if (character == '>') {
                            left = getValue(string.substring(0, pos), player);
                            right = getValue(string.substring(pos+1), player);
                            if (Float.parseFloat(getShownText(left)) >
                                    Float.parseFloat(getShownText(right))) {
                                return "true";
                            } else {
                                return "false";
                            }
                        }
                        if (character == '<' && string.charAt(pos+1) == '=') {
                            left = getValue(string.substring(0, pos), player);
                            right = getValue(string.substring(pos+2), player);
                            if (Float.parseFloat(getShownText(left)) <=
                                    Float.parseFloat(getShownText(right))) {
                                return "true";
                            } else {
                                return "false";
                            }
                        }
                        if (character == '<') {
                            left = getValue(string.substring(0, pos), player);
                            right = getValue(string.substring(pos+1), player);
                            if (Float.parseFloat(getShownText(left)) <
                                    Float.parseFloat(getShownText(right))) {
                                return "true";
                            } else {
                                return "false";
                            }
                        }
                        if (character == '=' && string.charAt(pos+1) == '=') {
                            left = getValue(string.substring(0, pos), player);
                            right = getValue(string.substring(pos+2), player);
                            if (left.equals(right)) {
                                return "true";
                            } else {
                                return "false";
                            }
                        }
                        if (character == '!' && string.charAt(pos+1) == '=') {
                            left = getValue(string.substring(0, pos), player);
                            right = getValue(string.substring(pos+2), player);
                            if (!left.equals(right)) {
                                return "true";
                            } else {
                                return "false";
                            }
                        }
                    }
                    pos++;
                }
                closeChar = 0;
                pos = 0;
                // Priority 2
                if (string.charAt(0) == '!') {
                    left = getValue(string.substring(1), player);
                    if (left.equals("true")) {
                        return "false";
                    } else {
                        return "true";
                    }
                }
                for (int character : chars) {
                    if (closeChar != 0) {
                        if (character == closeChar) {
                            closeChar = 0;
                        }
                    } else {
                        if (character == '{') closeChar = '}';
                        if (character == '(') closeChar = ')';
                        if (character == '"') closeChar = '"';
                        if (character == '%') {
                            left = getValue(string.substring(0, pos), player);
                            right = getValue(string.substring(pos+1
                            ), player);
                            if (isFloat(left) || isFloat(right)) {
                                return Float.parseFloat(getShownText(left)) %
                                        Float.parseFloat(getShownText(right)) + "F";
                            }
                            return String.valueOf(Integer.parseInt(getShownText(left)) %
                                    Integer.parseInt(getShownText(right)));
                        }
                        if (character == '*') {
                            left = getValue(string.substring(0, pos), player);
                            right = getValue(string.substring(pos+1), player);
                            if (isFloat(left) || isFloat(right)) {
                                return Float.parseFloat(getShownText(left)) *
                                        Float.parseFloat(getShownText(right)) + "F";
                            }
                            return String.valueOf(Integer.parseInt(getShownText(left)) *
                                    Integer.parseInt(getShownText(right)));
                        }
                        if (character == '/') {
                            left = getValue(string.substring(0, pos), player);
                            right = getValue(string.substring(pos+1), player);
                            if (isFloat(left) || isFloat(right)) {
                                return Float.parseFloat(getShownText(left)) /
                                        Float.parseFloat(getShownText(right)) + "F";
                            }
                            return String.valueOf(Integer.parseInt(getShownText(left)) /
                                    Integer.parseInt(getShownText(right)));
                        }
                    }
                    pos++;
                }
                closeChar = 0;
                pos = 0;
                // Priority 1
                for (int character : chars) {
                    if (closeChar != 0) {
                        if (character == closeChar) {
                            closeChar = 0;
                        }
                    } else {
                        if (character == '{') closeChar = '}';
                        if (character == '(') closeChar = ')';
                        if (character == '"') closeChar = '"';
                        if (character == '>' && string.charAt(pos+1) == '>') {
                            left = getValue(string.substring(0, pos), player);
                            right = getValue(string.substring(pos+2), player);
                            return String.valueOf(Integer.parseInt(getShownText(left)) >>
                                    Integer.parseInt(getShownText(right)));
                        }
                        if (character == '<' && string.charAt(pos+1) == '<') {
                            left = getValue(string.substring(0, pos), player);
                            right = getValue(string.substring(pos+2), player);
                            return String.valueOf(Integer.parseInt(getShownText(left)) <<
                                    Integer.parseInt(getShownText(right)));
                        }
                        if (character == '|' && string.charAt(pos+2) == '|') {
                            left = getValue(string.substring(0, pos), player);
                            right = getValue(string.substring(pos+1), player);
                            if (left.equals("true") || right.equals("true")) {
                                return "true";
                            } else {
                                return "false";
                            }
                        }
                        if (character == '&' && string.charAt(pos+1) == '&') {
                            left = getValue(string.substring(0, pos), player);
                            right = getValue(string.substring(pos+2), player);
                            if (left.equals("true") && right.equals("true")) {
                                return "true";
                            } else {
                                return "false";
                            }
                        }
                        if (character == '&') {
                            left = getValue(string.substring(0, pos), player);
                            right = getValue(string.substring(pos+1), player);
                            return String.valueOf(Integer.parseInt(getShownText(left)) &
                                    Integer.parseInt(getShownText(right)));
                        }
                        if (character == '|') {
                            left = getValue(string.substring(0, pos), player);
                            right = getValue(string.substring(pos+1), player);
                            return String.valueOf(Integer.parseInt(getShownText(left)) |
                                    Integer.parseInt(getShownText(right)));
                        }
                        if (character == '^') {
                            left = getValue(string.substring(0, pos), player);
                            right = getValue(string.substring(pos+1), player);
                            return String.valueOf(Integer.parseInt(getShownText(left)) ^
                                    Integer.parseInt(getShownText(right)));
                        }
                    }
                    pos++;
                }
                return string;
            }
        }
    }

    /**
     * Get a data's shown text.
     *
     * @param string The data string.
     * @return The shown text.
     */
    public String getShownText(String string) {
        if (isString(string)) {
            if (string.length() == 0) return string;
            return string.substring(1,string.length()-1);
        }
        if (Pattern.compile("^\\d+.\\d+F$").matcher(string).find()) {
            return string.substring(0,string.length()-1);
        }
        return string;
    }

    /**
     * Get a shown text's data text.
     *
     * @param string The shown string.
     * @return The data text.
     */
    public String getDataText(String string) {
        if (Pattern.compile("^\\d+.\\d+F$").matcher(string).find() ||
                Pattern.compile("^\\d+$").matcher(string).find() ||
                string.equals("true") || string.equals("false")) {
            return string;
        } else if (Pattern.compile("^\\d+.\\d+$").matcher(string).find()) {
            return string + "F";
        } else {
            return "\"" + string + "\"";
        }
    }
    /**
     * Detect is the data string String type.
     *
     * @param string The data string.
     * @return Is the data string String type.
     */
    public boolean isString(String string) {
        if (string.length() == 0) return true;
        return string.charAt(0) == '"' && string.charAt(string.length()-1) == '"';
    }

    /**
     * Detect is the data string Float type.
     *
     * @param string The data string.
     * @return Is the data string Float type.
     */
    public boolean isFloat(String string) {
        Matcher matcher = Pattern.compile("^\\d+.\\d+(F)?$").matcher(string);
        return matcher.find();
    }
}
