package com.JH.dgather.frame.util.json;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

/**
 * Project: Lema
 * Package: com.lema.common.util
 * Description: 用于校验一个字符串是否是合法的JSON格式
 * Author: MaZhonghai
 * Date:2016/8/2 10:34
 */

public class JsonValidator {

    private static CharacterIterator iterator;
    private static char cha;
    private static int col;

    public JsonValidator() {
    }

    /**
     * 验证一个字符串是否是合法的JSON串
     *
     * @param jsonStr 要验证的字符串
     * @return true-合法 ，false-非法
     */
    public static boolean validate(String jsonStr) {
        jsonStr = jsonStr.trim();
        boolean ret = valid(jsonStr);
        return ret;
    }

    private static boolean valid(String jsonStr) {
        if ("".equals(jsonStr)) return true;

        boolean ret = true;
        iterator = new StringCharacterIterator(jsonStr);
        cha = iterator.first();
        col = 1;
        if (!value()) {
            ret = error("value", 1);
        } else {
            skipWhiteSpace();
            if (cha != CharacterIterator.DONE) {
                ret = error("end", col);
            }
        }

        return ret;
    }

    private static boolean value() {
        return literal("true") || literal("false") || literal("null") || string() || number() || object() || array();
    }

    private static boolean literal(String text) {
        CharacterIterator ci = new StringCharacterIterator(text);
        char t = ci.first();
        if (cha != t) return false;

        int start = col;
        boolean ret = true;
        for (t = ci.next(); t != CharacterIterator.DONE; t = ci.next()) {
            if (t != nextCharacter()) {
                ret = false;
                break;
            }
        }
        nextCharacter();
        if (!ret) error("literal " + text, start);
        return ret;
    }

    private static boolean array() {
        return aggregate('[', ']', false);
    }

    private static boolean object() {
        return aggregate('{', '}', true);
    }

    private static boolean aggregate(char entryCharacter, char exitCharacter, boolean prefix) {
        if (cha != entryCharacter) return false;
        nextCharacter();
        skipWhiteSpace();
        if (cha == exitCharacter) {
            nextCharacter();
            return true;
        }

        for (; ; ) {
            if (prefix) {
                int start = col;
                if (!string()) return error("string", start);
                skipWhiteSpace();
                if (cha != ':') return error("colon", col);
                nextCharacter();
                skipWhiteSpace();
            }
            if (value()) {
                skipWhiteSpace();
                if (cha == ',') {
                    nextCharacter();
                } else if (cha == exitCharacter) {
                    break;
                } else {
                    return error("comma or " + exitCharacter, col);
                }
            } else {
                return error("value", col);
            }
            skipWhiteSpace();
        }

        nextCharacter();
        return true;
    }

    private static boolean number() {
        if (!Character.isDigit(cha) && cha != '-') return false;
        int start = col;
        if (cha == '-') nextCharacter();
        if (cha == '0') {
            nextCharacter();
        } else if (Character.isDigit(cha)) {
            while (Character.isDigit(cha))
                nextCharacter();
        } else {
            return error("number", start);
        }
        if (cha == '.') {
            nextCharacter();
            if (Character.isDigit(cha)) {
                while (Character.isDigit(cha))
                    nextCharacter();
            } else {
                return error("number", start);
            }
        }
        if (cha == 'e' || cha == 'E') {
            nextCharacter();
            if (cha == '+' || cha == '-') {
                nextCharacter();
            }
            if (Character.isDigit(cha)) {
                while (Character.isDigit(cha))
                    nextCharacter();
            } else {
                return error("number", start);
            }
        }
        return true;
    }

    private static boolean string() {
        if (cha != '"') return false;

        int start = col;
        boolean escaped = false;
        for (nextCharacter(); cha != CharacterIterator.DONE; nextCharacter()) {
            if (!escaped && cha == '\\') {
                escaped = true;
            } else if (escaped) {
                if (!escape()) {
                    return false;
                }
                escaped = false;
            } else if (cha == '"') {
                nextCharacter();
                return true;
            }
        }
        return error("quoted string", start);
    }

    private static boolean escape() {
        int start = col - 1;
        if (" \\\"/bfnrtu".indexOf(cha) < 0) {
            return error("escape sequence  \\\",\\\\,\\/,\\b,\\f,\\n,\\r,\\t  or  \\uxxxx ", start);
        }
        if (cha == 'u') {
            if (!ishex(nextCharacter()) || !ishex(nextCharacter()) || !ishex(nextCharacter())
                    || !ishex(nextCharacter())) {
                return error("unicode escape sequence  \\uxxxx ", start);
            }
        }
        return true;
    }

    private static boolean ishex(char d) {
        return "0123456789abcdefABCDEF".indexOf(cha) >= 0;
    }

    private static char nextCharacter() {
        cha = iterator.next();
        ++col;
        return cha;
    }

    private static void skipWhiteSpace() {
        while (Character.isWhitespace(cha)) {
            nextCharacter();
        }
    }

    private static boolean error(String type, int col) {
        System.out.printf("type: %s, col: %s%s", type, col, System.getProperty("line.separator"));
        return false;
    }

    public static void main(String[] args) {
        //String jsonStr = "{\"website\":\"oschina.net\"}";
        String jsonStr = "{"
                + " \"ccobjtypeid\": \"1001\","
                + " \"fromuser\": \"李四\","
                + " \"touser\": \"张三\","
                + "  \"desc\": \"描述\","
                + "  \"subject\": \"主题\","
                + "  \"attach\": \"3245,3456,4345,4553\","
                + " \"data\": {"
                + "    \"desc\": \"测试对象\","
                + "     \"dataid\": \"22\","
                + "    \"billno\": \"TEST0001\","
                + "    \"datarelation\":["
                + " {"
                + "  \"dataname\": \"关联对象1\","
                + "  \"data\": ["
                + "      {"
                + "    \"dataid\": \"22\","
                + "          \"datalineid\": \"1\","
                + "          \"content1\": \"test1\","
                + "          \"content2\": \"test2\""
                + "      }"
                + "  ]"
                + " }"
                + " ]"
                + "  }"
                + " }";
        System.out.println(jsonStr + ":" + new JsonValidator().validate(jsonStr));
    }
}
