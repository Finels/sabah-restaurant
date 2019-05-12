package com.fasteam.common.util;

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Description:  com.fasteam.appcommon.util
 * Copyright: © 2017 FanLei. All rights reserved.
 * Company: NULL
 *
 * @author FL
 * @version 1.0
 * @timestamp 2019/3/24
 */
public final class StringUtil {
    private StringUtil() {
    }

    public static List strToList(String content) {
        return strToList(content, ",");
    }

    public static List strToList(String content, String pattern) {
        String[] strArray = content.split(pattern);
        return (List) Arrays.asList(strArray).stream().filter((a) -> {
            return StringUtils.isNotBlank(a);
        }).collect(Collectors.toList());
    }

    public static String listToStr(List list, String pattern) {
        return StringUtils.join(list, pattern);
    }

    public static boolean isNullOrEmpty(String s) {
        return s == null || s.trim().length() == 0;
    }

    public static String getBirthdayByIdCard(String idCard) {
        String birthday = "";
        if (15 == idCard.length()) {
            birthday = idCard.charAt(6) + String.valueOf(idCard.charAt(7));
            if (Integer.parseInt(birthday) < 10) {
                birthday = "20" + birthday;
            } else {
                birthday = "19" + birthday;
            }

            birthday = birthday + '-' + idCard.charAt(8) + idCard.charAt(9);
        }

        if (18 == idCard.length()) {
            birthday = String.valueOf(idCard.charAt(6)) + idCard.charAt(7) + idCard.charAt(8) + idCard.charAt(9) + "-" + idCard.charAt(10) + idCard.charAt(11) + "-" + idCard.charAt(12) + idCard.charAt(13);
        }

        return birthday;
    }

    public static String getSexByIdCard(String idCard) {
        String sex = "";
        if (15 == idCard.length()) {
            if (idCard.charAt(14) / 2 * 2 != idCard.charAt(14)) {
                sex = "1";
            } else {
                sex = "2";
            }
        }

        if (18 == idCard.length()) {
            if (idCard.charAt(16) / 2 * 2 != idCard.charAt(16)) {
                sex = "1";
            } else {
                sex = "2";
            }
        }

        return sex;
    }

    public static String getValidString(String str, int x) {
        return getASCLen(str, x) > 0 ? str.substring(0, getASCLen(str, x)) + "..." : str;
    }

    public static String getNoHtmlValidString(String str, int x) {
        str = str.replaceAll("<[^>]*>", "");
        return getASCLen(str, x) > 0 ? str.substring(0, getASCLen(str, x)) + ".." : str;
    }

    public static String getValidString2(String str, int x) {
        str = str.replaceAll("&nbsp;", "");
        return getASCLen(str, x) > 0 ? str.substring(0, getASCLen(str, x)) + ".." : str;
    }

    public static int getASCLen(String ss, int toSub) {
        if (ss == null) {
            return 0;
        } else {
            int sub = 0;
            int len = 0;
            char[] chars = ss.toCharArray();

            for(int i = 0; i < chars.length; ++i) {
                if (isChinese(chars[i])) {
                    len += 2;
                } else {
                    ++len;
                }

                if (len > toSub) {
                    sub = i;
                    break;
                }
            }

            return sub;
        }
    }

    public static boolean isChinese(char c) {
        return 19968 <= c && c <= '龻';
    }

    public static String trim(String str) {
        return StringUtils.isNotBlank(str) ? str.trim() : "";
    }

    public static String trimLastComma(StringBuffer oldStr) {
        return trimLastComma(oldStr.toString());
    }

    public static String trimLastComma(String oldStr) {
        if (oldStr.endsWith(",")) {
            oldStr = oldStr.substring(0, oldStr.length() - 1);
        }

        return oldStr;
    }

    public static boolean isNumeric(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        } else {
            int i = str.length();

            char chr;
            do {
                --i;
                if (i < 0) {
                    return true;
                }

                chr = str.charAt(i);
            } while(chr >= '0' && chr <= '9');

            return false;
        }
    }

    public static Integer[] stringToInteger(String str, String separator) {
        if (StringUtils.isBlank(str)) {
            return new Integer[0];
        } else {
            String[] strArray = str.split(separator);
            Integer[] iStr = new Integer[strArray.length];

            for(int i = 0; i < strArray.length; ++i) {
                iStr[i] = Integer.parseInt(strArray[i].trim());
            }

            return iStr;
        }
    }

    public static Long[] stringToLong(String str, String separator) {
        if (StringUtils.isBlank(str)) {
            return new Long[0];
        } else {
            String[] strArray = str.split(separator);
            List<String> strList = (List)Arrays.asList(strArray).stream().filter((a) -> {
                return StringUtils.isNotBlank(a);
            }).collect(Collectors.toList());
            Long[] lStr = new Long[strList.size()];

            for(int i = 0; i < strList.size(); ++i) {
                lStr[i] = Long.parseLong(((String)strList.get(i)).trim());
            }

            return lStr;
        }
    }

    public static String getNumFromContent(String content) {
        if (org.apache.commons.lang.StringUtils.isEmpty(content)) {
            return null;
        } else {
            Pattern pattern = Pattern.compile("\\d+\\.?\\d*");
            Matcher matcher = pattern.matcher(content);
            return matcher.find() ? matcher.group(0) : null;
        }
    }
}