package com.XHWJ.xiaoAiTongXue.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class StringUtils {
    /**
     * 判断字符串是否为空
     *
     * @param str 字符串
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || "".equals(str) || str.trim().length() == 0;
    }

    /**
     * 判断字符串不是null或为空
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }



    static final char CN_CHAR_START = '\u4e00';
    static final char CN_CHAR_END = '\u9fa5';

    /**
     * 是否包含中文字符
     *
     * @param str 要判断的字符串
     * @return 是否包含中文字符
     */
    public static boolean containsChineseChar(String str) {
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] >= CN_CHAR_START && chars[i] <= CN_CHAR_END) return true;
        }
        return false;
    }

    /**
     * 分隔字符串,根据正则表达式分隔字符串,只分隔首个,剩下的的不进行分隔,如: 1,2,3,4 将分隔为 ['1','2,3,4']
     *
     * @param str   要分隔的字符串
     * @param regex 分隔表达式
     * @return 分隔后的数组
     */
    public static String[] splitFirst(String str, String regex) {
        return str.split(regex, 2);
    }

    /**
     * 将字符串转为String后进行分割，如果为字符串为空或者空字符,则返回null
     *
     * @param regex 分隔规则
     * @return 分隔后的字符串
     */
    public static final String[] toStringAndSplit(String str, String regex) {
        if (isEmpty(str)) return null;
        return String.valueOf(str).split(regex);
    }


    //判断是否是输入的手机号
    public static boolean isInputPhone(String str) throws PatternSyntaxException {
        String regExp = "^1\\d{10}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    //判断密码合法
    public static boolean isInputPassword(String str) throws PatternSyntaxException {
        String regExp = "^[A-Za-z0-9]+$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 打印链接，用于调试接口
     */
    public static String getRequstUrl(Map<String, String> params, String url) {
        if (params != null) {
            StringBuilder sb = new StringBuilder();
            for (String key : params.keySet()) {
                sb.append(key).append("=").append(params.get(key)).append("&");
            }
            String requestUrl = sb.toString();
            requestUrl = url + "?" + requestUrl.substring(0, requestUrl.length() - 1);
            return requestUrl;
        }
        return null;
    }

    public static String getRequstUrl(String value, String url) {
        if (!isEmpty(value)) {
            if (value.indexOf("[") != -1 && value.indexOf("]") != -1) {
                String replace1 = value.replace("[", "");
                String replace = replace1.replace("]", "");
                String requestUrl = url + "?" + replace;
                return requestUrl;
            }
        }
        return url;

    }

    /**
     * 将对象数据转换成String
     *
     * @param obj
     * @return
     */
    public static String toString(Object obj) {
        if (obj == null || "".equals(obj) || "null".equals(obj)) {
            return "";
        }
        return String.valueOf(obj);
    }

    /**
     * 将对象数据转换成Int
     *
     * @param obj
     * @return
     */
    public static int toInt(Object obj) {
        if (obj == null || "".equals(obj)) {
            return 0;
        }
        String str = String.valueOf(obj);
        return (int) Float.parseFloat(str);
    }

    /**
     * 将对象数据转换成Double
     *
     * @param obj
     * @return
     */
    public static double toDouble(Object obj) {
        if (obj == null || "".equals(obj)) {
            return 0;
        }
        String str = String.valueOf(obj);
        return Double.parseDouble((str));
    }

    /**
     * 截取字符串
     *
     * @param str 要截取的字符串
     * @param max 截取最大字节长度
     * @return 截取后的字符串
     */
    public static String getSubString(String str, int max) {
        if (!StringUtils.isEmpty(str)) {
            int j = 0;
            String name = "";
            for (int i = 0; i < str.length(); i++) {
                if (j >= max) {
                    return name;
                }
                if (str.substring(i, i + 1).matches("[\u4e00-\u9fa5]")) {
                    if (j >= max - 1) {
                        return name;
                    }
                    name += str.substring(i, i + 1);
                    j += 2;
                } else {
                    name += str.substring(i, i + 1);
                    ++j;
                }
            }
            return name;
        }
        return "";
    }

    public static String doubleToString(String strNum) {
        int n = strNum.indexOf(".");
        if (n > 0) {
            //截取小数点后的数字
            String dotNum = strNum.substring(n + 1);
            if ("0".equals(dotNum)) {
                return strNum + "0";
            } else {
                if (dotNum.length() == 1) {
                    return strNum + "0";
                } else {
                    return strNum;
                }
            }
        } else {
            return strNum + ".00";
        }
    }

    /**
     * 检测EditText输入是否有emoji表情
     *
     * @param source
     * @return
     */
    public static boolean containsEmoji(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmojiCharacter(codePoint)) {
                //如果不能匹配,则该字符是Emoji表情
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否是Emoji
     *
     * @param codePoint 比较的单个字符
     * @return
     */
    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) ||
                (codePoint == 0xA) || (codePoint == 0xD) ||
                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }


    public static String getStandardTime(String str1) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        Date date;
        String stringtime = null;
        try {
            date = sdf.parse(str1);
            String str = sdf.format(date);
            stringtime = str.substring(0, str.length() - 3);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringtime;
    }
    /**
     * 拼接字符串操作
     *
     * @param str
     * @return
     */
    public static String concat(Object... str) {
        if (str != null) {
            StringBuilder sb = new StringBuilder();
            for (Object s : str) {
                sb.append(s.toString());
            }
            return sb.toString();
        }
        return null;
    }

}
