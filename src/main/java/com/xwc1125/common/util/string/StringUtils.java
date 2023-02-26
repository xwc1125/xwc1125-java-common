package com.xwc1125.common.util.string;

import com.xwc1125.common.util.string.text.StrFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * Title: StringUtils
 * </p>
 * <p>
 * Description: 字符串操作的公用类
 * </p>
 * <p>
 * <p>
 * </p>
 *
 * @author xwc1125
 * @date 2015-7-13下午2:08:17
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    public static final String SPACE = " ";
    public static final String EMPTY = "";
    public static final String LF = "\n";
    public static final String CR = "\r";
    /**
     * 空字符串
     */
    private static final String NULLSTR = "";

    /**
     * 下划线
     */
    private static final char SEPARATOR = '_';

    /**
     * @param @param  str
     * @param @return
     * @return Boolean
     * @Title isNotEmpty
     * @Description 是否全部不为空
     * @author xwc1125
     * @date 2016年4月1日 上午9:25:28
     */
    public static Boolean isNotEmpty(String... str) {
        if (str == null) {
            return false;
        }
        for (String s : str) {
            if (s == null || s.length() == 0 || s.trim().length() == 0 || s.equals("null") || s.equals("")) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param @param  str 原字符串
     * @param @param  strLength 需要达到的位数长
     * @param @param  subStr 补的值
     * @param @param  flag 是左侧补还是右侧补。true左补，false右补
     * @param @return
     * @return String
     * @Title deficiencyFill
     * @Description 字符串不足补值
     * @author xwc1125
     * @date 2016年1月19日 上午9:31:14
     */
    public static String deficiencyFill(String str, int strLength, String subStr, boolean flag) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        int strLen = str.length();
        StringBuffer sb = null;
        while (strLen < strLength) {
            sb = new StringBuffer();
            if (flag) {
                sb.append(subStr).append(str);// 左(前)补0
            } else {
                sb.append(str).append(subStr);// 右(后)补0
            }
            str = sb.toString();
            strLen = str.length();
        }
        return str;
    }

    /**
     * @param @param  ss
     * @param @return
     * @return boolean
     * @Title isEmpty
     * @Description TODO(describe the methods)
     * @author xwc1125
     * @date 2016年1月22日 下午2:36:09
     */
    public static boolean isEmpty(String... ss) {
        if (ss == null) {
            return true;
        }
        for (String s : ss) {
            if (s == null || s.length() == 0 || s.trim().length() == 0 || s.equals("null") || s.equals("")) {
                return true;
            }
        }
        return false;
    }

    /**
     * * 判断一个对象数组是否为空
     *
     * @param objects 要判断的对象数组
     *                * @return true：为空 false：非空
     */
    public static boolean isEmpty(Object[] objects) {
        return isNull(objects) || (objects.length == 0);
    }

    /**
     * * 判断一个对象是否为空
     *
     * @param object Object
     * @return true：为空 false：非空
     */
    public static boolean isNull(Object object) {
        return object == null;
    }

    /**
     * * 判断一个对象是否非空
     *
     * @param object Object
     * @return true：非空 false：空
     */
    public static boolean isNotNull(Object object) {
        return !isNull(object);
    }

    /**
     * <p>
     * Title: isContain
     * </p>
     * <p>
     * Description: 判断对象是否在数组中
     * </p>
     * <p>
     * <p>
     * </p>
     *
     * @tags @param str
     * @tags @param filter
     * @tags @return
     * @author xwc1125
     * @date 2016-3-28上午11:52:31
     */
    public static Boolean isContain(Object str, Object... filter) {
        if (filter == null || filter.length == 0 || str == null) {
            return false;
        }
        List<Object> l = Arrays.asList(filter);
        if (l.contains(str)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * <p>
     * Title: isListContain
     * </p>
     * <p>
     * Description: list中是否存在该元素
     * </p>
     * <p>
     * <p>
     * </p>
     *
     * @tags @param str
     * @tags @param filter
     * @tags @return
     * @author xwc1125
     * @date 2016-3-28下午12:02:19
     */
    public static Boolean isListContain(String str, List<String> filter) {
        if (filter == null || filter.size() == 0 || str == null) {
            return false;
        }
        if (filter.contains(str)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param @param  tempString 数据
     * @param @param  splitPattern 分隔符
     * @param @return
     * @return List<String>
     * @Title str2List
     * @Description 字符串转为List
     * @author xwc1125
     * @date 2016年6月13日 下午6:07:42
     */
    public static List<String> str2List(String tempString, String splitPattern) {
        if (isEmpty(tempString)) {
            return null;
        }
        List<String> list = new ArrayList<String>();
        String[] Str = tempString.split(splitPattern);
        list = Arrays.asList(Str);
        // for (int i = 0; i < Str.length; i++) {
        // String str = Str[i];
        // if (isEmpty(str)) {
        // list.add("");
        // } else {
        // list.add(str.trim());
        // }
        // }
        return list;
    }

    /**
     * 将以逗号分隔的字符串转换成字符串数组
     *
     * @param valStr
     * @return String[]
     */
    public static String[] StrList(String valStr) {
        int i = 0;
        String TempStr = valStr;
        String[] returnStr = new String[valStr.length() + 1 - TempStr.replace(",", "").length()];
        valStr = valStr + ",";
        while (valStr.indexOf(',') > 0) {
            returnStr[i] = valStr.substring(0, valStr.indexOf(','));
            valStr = valStr.substring(valStr.indexOf(',') + 1, valStr.length());

            i++;
        }
        return returnStr;
    }

    /**
     * @param @param  listStr
     * @param @return
     * @return List<String>
     * @Title getListFromListStr
     * @Description 将list的字符串转换为list
     * @author xwc1125
     * @date 2016年2月23日 上午9:26:37
     */
    public static List<String> getListFromListStr(String listStr) {
        List<String> receiversList = new ArrayList<String>();
        if (StringUtils.isEmpty(listStr)) {
            return receiversList;
        }
        try {
            listStr = listStr.replaceAll("\n", "");
            String[] arr = listStr.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(" ", "").split(",");
            receiversList = Arrays.asList(arr);
        } catch (Exception e) {
            receiversList.add(listStr);
        }
        return receiversList;
    }

    /**
     * <p>
     * Title: strToUnicode
     * </p>
     * <p>
     * Description: 字符串转换为Unicode
     * </p>
     * <p>
     * <p>
     * </p>
     *
     * @param str
     * @return
     * @author xwc1125
     * @date 2016年9月20日 下午5:08:24
     */
    public static String strToUnicode(String str) {
        str = (str == null ? "" : str);
        String tmp;
        StringBuffer sb = new StringBuffer(1000);
        char c;
        int i, j;
        sb.setLength(0);
        for (i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            sb.append("\\u");
            j = (c >>> 8); // 取出高8位
            tmp = Integer.toHexString(j);
            if (tmp.length() == 1) {
                sb.append("0");
            }
            sb.append(tmp);
            j = (c & 0xFF); // 取出低8位
            tmp = Integer.toHexString(j);
            if (tmp.length() == 1) {
                sb.append("0");
            }
            sb.append(tmp);

        }
        return (new String(sb));
    }

    /**
     * <p>
     * Title: unicodeToStr
     * </p>
     * <p>
     * Description: Unicode转换为字符串
     * </p>
     * <p>
     * <p>
     * </p>
     *
     * @param str
     * @return
     * @author xwc1125
     * @date 2016年9月20日 下午5:09:06
     */
    public static String unicodeToStr(String str) {
        str = (str == null ? "" : str);
        if (str.indexOf("\\u") == -1) {
            // 如果不是unicode码则原样返回
            return str;
        }

        StringBuffer sb = new StringBuffer(1000);

        for (int i = 0; i < str.length() - 6; ) {
            String strTemp = str.substring(i, i + 6);
            String value = strTemp.substring(2);
            int c = 0;
            for (int j = 0; j < value.length(); j++) {
                char tempChar = value.charAt(j);
                int t = 0;
                switch (tempChar) {
                    case 'a':
                        t = 10;
                        break;
                    case 'b':
                        t = 11;
                        break;
                    case 'c':
                        t = 12;
                        break;
                    case 'd':
                        t = 13;
                        break;
                    case 'e':
                        t = 14;
                        break;
                    case 'f':
                        t = 15;
                        break;
                    default:
                        t = tempChar - 48;
                        break;
                }

                c += t * ((int) Math.pow(16, (value.length() - j - 1)));
            }
            sb.append((char) c);
            i = i + 6;
        }
        return sb.toString();
    }

    /**
     * 首字母大写
     *
     * @param srcStr
     * @return
     */
    public static String firstCharacterToUpper(String srcStr) {
        if (isEmpty(srcStr)) {
            return "";
        }
        return srcStr.substring(0, 1).toUpperCase() + srcStr.substring(1);
    }

    /**
     * 替换字符串并让它的下一个字母为大写
     *
     * @param srcStr
     * @param org
     * @param ob
     * @return
     */
    public static String replaceUnderlineAndfirstToUpper(String srcStr, String org, String ob) {
        if (isEmpty(srcStr)) {
            return "";
        }
        String newString = "";
        int first = 0;
        while (srcStr.indexOf(org) != -1) {
            first = srcStr.indexOf(org);
            if (first != srcStr.length()) {
                newString = newString + srcStr.substring(0, first) + ob;
                srcStr = srcStr.substring(first + org.length(), srcStr.length());
                srcStr = firstCharacterToUpper(srcStr);
            }
        }
        newString = newString + srcStr;
        return newString;
    }

    public static String strJoin(CharSequence delimiter, Iterable<? extends CharSequence> elements) throws Exception {
        if (delimiter == null || elements == null) {
            throw new Exception("参数不能为空");
        }

        StringBuffer joiner = new StringBuffer();

        for (CharSequence cs : elements) {
            joiner.append(cs).append(delimiter);
        }
        int aa = joiner.lastIndexOf(delimiter + "");
        return joiner.substring(0, aa);
    }

    /**
     * 为数不足添加零
     *
     * @param str       字符串(一定需要是int)
     * @param strLength 需要补零的个数
     * @param isHigh    true：左补，false：右补
     * @return
     */
    public static String addZeroForNum(String str, int strLength, boolean isHigh) {
        int strLen = str.length();
        if (strLen < strLength) {
            while (strLen < strLength) {
                StringBuffer sb = new StringBuffer();
                if (isHigh) {
                    sb.append("0").append(str);// 左补0
                } else {
                    sb.append(str).append("0");//右补0
                }
                str = sb.toString();
                strLen = str.length();
            }
        }
        return str;
    }

    /**
     * 给小数数据进行添加零
     *
     * @param valueF
     * @param strLength
     * @param isHigh
     * @return
     */
    public static String addZeroForDouble(Double valueF, int strLength, boolean isHigh) {
        String numStr = valueF + "";
        String[] numArray = numStr.split("\\.");
        String numRight = numArray[0];//整数
        String numLeft = numArray[1];//小数部分
        if (isHigh) {
            //左补0
            int strLen = numRight.length();
            if (strLen < strLength) {
                while (strLen < strLength) {
                    StringBuffer sb = new StringBuffer();
                    sb.append("0").append(numRight);// 左补0
                    numRight = sb.toString();
                    strLen = numRight.length();
                }
            }
        } else {
            //右补0
            int strLen = numLeft.length();
            if (strLen < strLength) {
                while (strLen < strLength) {
                    StringBuffer sb = new StringBuffer();
                    sb.append(numLeft).append("0");//右补0
                    numLeft = sb.toString();
                    strLen = numLeft.length();
                }
            }
        }
        if (isHigh) {
            return numRight + "." + numLeft;
        } else {
            return numRight + "" + numLeft;
        }
    }

    public static boolean equalsIgnoreCase(String ok, String result) {
        if (isEmpty(ok, result)) {
            return false;
        }
        if (ok.toUpperCase().equals(result.toUpperCase())) {
            return true;
        }
        return false;
    }

    public static String getObjectValue(Object obj) {
        return obj == null ? "" : obj.toString();
    }

    /**
     * 截取字符串
     *
     * @param str   字符串
     * @param start 开始
     * @param end   结束
     * @return 结果
     */
    public static String substring(final String str, int start, int end) {
        if (str == null) {
            return NULLSTR;
        }

        if (end < 0) {
            end = str.length() + end;
        }
        if (start < 0) {
            start = str.length() + start;
        }

        if (end > str.length()) {
            end = str.length();
        }

        if (start > end) {
            return NULLSTR;
        }

        if (start < 0) {
            start = 0;
        }
        if (end < 0) {
            end = 0;
        }

        return str.substring(start, end);
    }

    /**
     * 格式化文本, {} 表示占位符<br>
     * 此方法只是简单将占位符 {} 按照顺序替换为参数<br>
     * 如果想输出 {} 使用 \\转义 { 即可，如果想输出 {} 之前的 \ 使用双转义符 \\\\ 即可<br>
     * 例：<br>
     * 通常使用：format("this is {} for {}", "a", "b") -> this is a for b<br>
     * 转义{}： format("this is \\{} for {}", "a", "b") -> this is \{} for a<br>
     * 转义\： format("this is \\\\{} for {}", "a", "b") -> this is \a for b<br>
     *
     * @param template 文本模板，被替换的部分用 {} 表示
     * @param params   参数值
     * @return 格式化后的文本
     */
    public static String format(String template, Object... params) {
        if (isEmpty(params) || isEmpty(template)) {
            return template;
        }
        return StrFormatter.format(template, params);
    }

    /**
     * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。 例如：HELLO_WORLD->HelloWorld
     *
     * @param name 转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     */
    public static String convertToCamelCase(String name) {
        StringBuilder result = new StringBuilder();
        // 快速检查
        if (name == null || name.isEmpty()) {
            // 没必要转换
            return "";
        } else if (!name.contains("_")) {
            // 不含下划线，仅将首字母大写
            return name.substring(0, 1).toUpperCase() + name.substring(1);
        }
        // 用下划线将原始字符串分割
        String[] camels = name.split("_");
        for (String camel : camels) {
            // 跳过原始字符串中开头、结尾的下换线或双重下划线
            if (camel.isEmpty()) {
                continue;
            }
            // 首字母大写
            result.append(camel.substring(0, 1).toUpperCase());
            result.append(camel.substring(1).toLowerCase());
        }
        return result.toString();
    }

    /**
     * 下划线转驼峰命名
     */
    public static String toUnderScoreCase(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        // 前置字符是否大写
        boolean preCharIsUpperCase = true;
        // 当前字符是否大写
        boolean curreCharIsUpperCase = true;
        // 下一字符是否大写
        boolean nexteCharIsUpperCase = true;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (i > 0) {
                preCharIsUpperCase = Character.isUpperCase(str.charAt(i - 1));
            } else {
                preCharIsUpperCase = false;
            }

            curreCharIsUpperCase = Character.isUpperCase(c);

            if (i < (str.length() - 1)) {
                nexteCharIsUpperCase = Character.isUpperCase(str.charAt(i + 1));
            }

            if (preCharIsUpperCase && curreCharIsUpperCase && !nexteCharIsUpperCase) {
                sb.append(SEPARATOR);
            } else if ((i != 0 && !preCharIsUpperCase) && curreCharIsUpperCase) {
                sb.append(SEPARATOR);
            }
            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }

    /**
     * 是否包含字符串
     *
     * @param str  验证字符串
     * @param strs 字符串组
     * @return 包含返回true
     */
    public static boolean inStringIgnoreCase(String str, String... strs) {
        if (str != null && strs != null) {
            for (String s : strs) {
                if (str.equalsIgnoreCase(trim(s))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取参数不为空值
     *
     * @param value defaultValue 要判断的value
     * @return value 返回值
     */
    public static <T> T nvl(T value, T defaultValue) {
        return value != null ? value : defaultValue;
    }

    public static String zeros(int n) {
        return repeat('0', n);
    }

    public static String repeat(char value, int n) {
        return (new String(new char[n])).replace("\u0000", String.valueOf(value));
    }
}
