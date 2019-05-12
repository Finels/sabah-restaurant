package com.fasteam.common.util;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Locale;

/**
 * Description:  用于适配XSS协议
 * Copyright: © 2017 FanLei. All rights reserved.
 * Company: NULL
 *
 * @author FL
 * @version 1.0
 */
public class AutiXSS {
    private static String EmptyString_JavaScript = "''";
    private static String EmptyString_VBS = "\"\"";
    private static String EmptyString = "";
    private static StringBuffer strb;
    private static StringCharacterIterator sci;

    public AutiXSS() {
    }

    private static String hex(char ch) {
        return Integer.toHexString(ch).toUpperCase(Locale.ENGLISH);
    }

    private static String escapeJavaStyleString(String str, boolean escapeSingleQuote, boolean escapeForwardSlash) {
        if (str == null) {
            return null;
        } else {
            StringBuilder res = new StringBuilder();
            int sz = str.length();

            for(int i = 0; i < sz; ++i) {
                char ch = str.charAt(i);
                if (ch > 4095) {
                    res.append(ch);
                } else if (ch > 255) {
                    res.append("\\u0").append(hex(ch));
                } else if (ch > 127) {
                    res.append("\\u00").append(hex(ch));
                } else if (ch < ' ') {
                    switch(ch) {
                        case '\b':
                            res.append('\\');
                            res.append('b');
                            break;
                        case '\t':
                            res.append('\\');
                            res.append('t');
                            break;
                        case '\n':
                            res.append('\\');
                            res.append('n');
                            break;
                        case '\u000b':
                        default:
                            if (ch > 15) {
                                res.append("\\u00").append(hex(ch));
                            } else {
                                res.append("\\u000").append(hex(ch));
                            }
                            break;
                        case '\f':
                            res.append('\\');
                            res.append('f');
                            break;
                        case '\r':
                            res.append('\\');
                            res.append('r');
                    }
                } else {
                    switch(ch) {
                        case '"':
                            res.append('\\');
                            res.append('"');
                            break;
                        case '\'':
                            if (escapeSingleQuote) {
                                res.append('\\');
                            }

                            res.append('\'');
                            break;
                        case '/':
                            if (escapeForwardSlash) {
                                res.append('\\');
                            }

                            res.append('/');
                            break;
                        case '\\':
                            res.append('\\');
                            res.append('\\');
                            break;
                        default:
                            res.append(ch);
                    }
                }
            }

            return res.toString();
        }
    }

    private static String encodeXss(String s) {
        if (s != null && !s.isEmpty()) {
            StringBuilder sb = new StringBuilder(s.length() + 16);

            for(int i = 0; i < s.length(); ++i) {
                char c = s.charAt(i);
                switch(c) {
                    case '"':
                        sb.append('"');
                        break;
                    case '&':
                        sb.append("&");
                        break;
                    case '\'':
                        sb.append("'");
                        break;
                    case '<':
                        sb.append("<");
                        break;
                    case '>':
                        sb.append(">");
                        break;
                    default:
                        sb.append(c);
                }
            }

            return sb.toString();
        } else {
            return s;
        }
    }

    private static String EncodeHtml(String strInput) {
        if (strInput == null) {
            return "";
        } else {
            String html = "";
            StringBuffer buffer = new StringBuffer();

            for(int i = 0; i < strInput.length(); ++i) {
                char c = strInput.charAt(i);
                switch(c) {
                    case '\n':
                    case '\r':
                        break;
                    case '"':
                        buffer.append("&quot;");
                        break;
                    case '&':
                        buffer.append("&amp;");
                        break;
                    case '<':
                        buffer.append("&lt;");
                        break;
                    case '>':
                        buffer.append("&gt;");
                        break;
                    default:
                        buffer.append(c);
                }
            }

            html = buffer.toString();
            return html;
        }
    }

    private static String EncodeHtmlAttribute(String strInput) {
        if (strInput.length() == 0) {
            return EmptyString;
        } else {
            StringBuilder builder = new StringBuilder(strInput.length() * 2);
            CharacterIterator it = new StringCharacterIterator(strInput);

            for(char ch = it.first(); ch != '\uffff'; ch = it.next()) {
                if ((ch <= '`' || ch >= '{') && (ch <= '@' || ch >= '[') && (ch <= '/' || ch >= ':') && ch != '.' && ch != ',' && ch != '-' && ch != '_') {
                    builder.append("&#" + ch + ";");
                } else {
                    builder.append(ch);
                }
            }

            return builder.toString();
        }
    }

    private static String EncodeJs(String strInput) {
        if (strInput.length() == 0) {
            return EmptyString_JavaScript;
        } else {
            StringBuilder builder = new StringBuilder("'");
            CharacterIterator it = new StringCharacterIterator(strInput);

            for(char ch = it.first(); ch != '\uffff'; ch = it.next()) {
                if ((ch <= '`' || ch >= '{') && (ch <= '@' || ch >= '[') && ch != ' ' && (ch <= '/' || ch >= ':') && ch != '.' && ch != ',' && ch != '-' && ch != '_') {
                    if (ch > 127) {
                        builder.append("\\u" + TwoByteHex(ch));
                    } else {
                        builder.append("\\x" + SingleByteHex(ch));
                    }
                } else {
                    builder.append(ch);
                }
            }

            builder.append("'");
            return builder.toString();
        }
    }

    private static String EncodeUrl(String strInput) {
        if (strInput.length() == 0) {
            return EmptyString;
        } else {
            StringBuilder builder = new StringBuilder(strInput.length() * 2);
            CharacterIterator it = new StringCharacterIterator(strInput);

            for(char ch = it.first(); ch != '\uffff'; ch = it.next()) {
                if ((ch <= '`' || ch >= '{') && (ch <= '@' || ch >= '[') && (ch <= '/' || ch >= ':') && ch != '.' && ch != '-' && ch != '_') {
                    if (ch > 127) {
                        builder.append("%u" + TwoByteHex(ch));
                    } else {
                        builder.append("%" + SingleByteHex(ch));
                    }
                } else {
                    builder.append(ch);
                }
            }

            return builder.toString();
        }
    }

    private static String EncodeVbs(String strInput) {
        if (strInput.length() == 0) {
            return EmptyString_VBS;
        } else {
            StringBuilder builder = new StringBuilder(strInput.length() * 2);
            boolean flag = false;
            CharacterIterator it = new StringCharacterIterator(strInput);

            for(char ch = it.first(); ch != '\uffff'; ch = it.next()) {
                if ((ch <= '`' || ch >= '{') && (ch <= '@' || ch >= '[') && ch != ' ' && (ch <= '/' || ch >= ':') && ch != '.' && ch != ',' && ch != '-' && ch != '_') {
                    if (flag) {
                        builder.append("\"");
                        flag = false;
                    }

                    builder.append("&chrw(" + (long)ch + ")");
                } else {
                    if (!flag) {
                        builder.append("&\"");
                        flag = true;
                    }

                    builder.append(ch);
                }
            }

            if (builder.length() > 0 && builder.charAt(0) == '&') {
                builder.delete(0, 1);
            }

            if (builder.length() == 0) {
                builder.insert(0, "\"\"");
            }

            if (flag) {
                builder.append("\"");
            }

            return builder.toString();
        }
    }

    private static String EncodeXml(String strInput) {
        return EncodeHtml(strInput);
    }

    private static String EncodeXmlAttribute(String strInput) {
        return EncodeHtmlAttribute(strInput);
    }

    public static String HtmlAttributeEncode(String s) {
        return EncodeHtmlAttribute(s);
    }

    public static String HtmlEncode(String s) {
        return EncodeHtml(s);
    }

    public static String xssEncode(String s) {
        return encodeXss(s);
    }

    public static String escapeJavaScript(String s) {
        return escapeJavaStyleString(s, true, false);
    }

    public static String JavaScriptEncode(String s) {
        return EncodeJs(s);
    }

    private static String SingleByteHex(char c) {
        long num = (long)c;
        return leftPad(Long.toString(num, 16), "0", 2);
    }

    private static String TwoByteHex(char c) {
        long num = (long)c;
        return leftPad(Long.toString(num, 16), "0", 4);
    }

    public static String UrlEncode(String s) {
        return EncodeUrl(s);
    }

    public static String VisualBasicScriptEncodeString(String s) {
        return EncodeVbs(s);
    }

    public static String XmlAttributeEncode(String s) {
        return EncodeXmlAttribute(s);
    }

    public static String XmlEncode(String s) {
        return EncodeXml(s);
    }

    private static String leftPad(String stringToPad, String padder, int size) {
        if (padder.length() == 0) {
            return stringToPad;
        } else {
            strb = new StringBuffer(size);
            sci = new StringCharacterIterator(padder);

            while(strb.length() < size - stringToPad.length()) {
                for(char ch = sci.first(); ch != '\uffff'; ch = sci.next()) {
                    if (strb.length() < size - stringToPad.length()) {
                        strb.insert(strb.length(), String.valueOf(ch));
                    }
                }
            }

            return strb.append(stringToPad).toString();
        }
    }
}