package com.fasteam.common.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.CRC32;

/**
 * Description:  com.fasteam.common.util
 * Copyright: © 2017 FanLei. All rights reserved.
 * Company: NULL
 *
 * @author FL
 * @version 1.0
 * @timestamp 2019/3/24
 */
public final class JavaUtil {
    private static final Logger LOG = LoggerFactory.getLogger(JavaUtil.class);

    private JavaUtil() {
    }

    public static String[] match(String s, String pattern) {
        Matcher m = Pattern.compile(pattern).matcher(s);
        if (!m.find()) {
            return null;
        } else {
            int n = m.groupCount();
            String[] ss = new String[n + 1];

            for(int i = 0; i <= n; ++i) {
                ss[i] = m.group(i);
            }

            return ss;
        }
    }

    public static List<String[]> matchAll(String s, String pattern) {
        Matcher m = Pattern.compile(pattern).matcher(s);
        ArrayList result = new ArrayList();

        while(m.find()) {
            int n = m.groupCount();
            String[] ss = new String[n + 1];

            for(int i = 0; i <= n; ++i) {
                ss[i] = m.group(i);
            }

            result.add(ss);
        }

        return result;
    }

    public static String[] firstMatch(String s, String pattern, int startIndex) {
        Matcher m = Pattern.compile(pattern).matcher(s);
        if (!m.find(startIndex)) {
            return null;
        } else {
            int n = m.groupCount();
            String[] ss = new String[n + 1];

            for(int i = 0; i <= n; ++i) {
                ss[i] = m.group(i);
            }

            return ss;
        }
    }

    public static boolean isMatch(String s, String pattern) {
        Matcher m = Pattern.compile(pattern).matcher(s);
        return m.find();
    }

    public static String[] matchWeak(String s, String pattern) {
        Matcher m = Pattern.compile(pattern, 2).matcher(s);
        if (!m.find()) {
            return null;
        } else {
            int n = m.groupCount();
            String[] ss = new String[n + 1];

            for(int i = 0; i <= n; ++i) {
                ss[i] = m.group(i);
            }

            return ss;
        }
    }

    public static String unescape(String s) {
        s = s.replaceAll("<pre>[\\s]*", "");
        s = s.replaceAll("</pre>.*$", "\n");
        s = s.replaceAll("</$", "\n");
        s = s.replaceAll("&#13;&#10; &#13;&#10;", "\n");
        s = s.replaceAll("&#9;", "\t");
        s = s.replaceAll("&#09;", "\t");
        s = s.replaceAll("&#10;", "\n");
        s = s.replaceAll("&#13;", "");
        s = s.replaceAll("&#32;", " ");
        s = s.replaceAll("&#33;", "!");
        s = s.replaceAll("&#35;", "#");
        s = s.replaceAll("&#36;", "\\$");
        s = s.replaceAll("&#37;", "\\%");
        s = s.replaceAll("&#38;", "&");
        s = s.replaceAll("&#39;", "'");
        s = s.replaceAll("&#40;", "(");
        s = s.replaceAll("&#41;", ")");
        s = s.replaceAll("&#42;", "*");
        s = s.replaceAll("&#43;", "+");
        s = s.replaceAll("&#44;", ",");
        s = s.replaceAll("&#45;", "-");
        s = s.replaceAll("&#46;", ".");
        s = s.replaceAll("&#47;", "/");
        s = s.replaceAll("&#58;", ":");
        s = s.replaceAll("&#59;", ";");
        s = s.replaceAll("&#60;", "<");
        s = s.replaceAll("&#61;", "=");
        s = s.replaceAll("&#62;", ">");
        s = s.replaceAll("&#63;", "?");
        s = s.replaceAll("&#64;", "@");
        s = s.replaceAll("&#91;", "[");
        s = s.replaceAll("&#92;", "\\\\");
        s = s.replaceAll("&#93;", "]");
        s = s.replaceAll("&#94;", "^");
        s = s.replaceAll("&#95;", "_");
        s = s.replaceAll("&#96;", "`");
        s = s.replaceAll("&#123;", "{");
        s = s.replaceAll("&#124;", "|");
        s = s.replaceAll("&#125;", "}");
        s = s.replaceAll("&#126;", "~");
        s = s.replaceAll("\r", "");
        s = s.replaceAll("\n", "\r\n");
        s = s.replaceAll("&#34;", "\"");
        s = s.replaceAll("\r\n\\.", "\r\n\\.\\.");
        Pattern p = null;
        Matcher m = null;
        p = Pattern.compile("&#(\\d*);");
        m = p.matcher(s);
        StringBuffer sb = new StringBuffer();

        while(m.find()) {
            m.appendReplacement(sb, numToString(m.group(1)));
        }

        m.appendTail(sb);
        String x = sb.toString().replaceAll("&#[^;]*;", "");
        return x;
    }

    public static String numToString(String num) {
        String result = "";
        int n = Integer.parseInt(num);
        if (n > 255 && n < 19968) {
            result = new String("&#" + n + ";");
        } else {
            result = new String("" + (char)n);
        }

        return result;
    }

    public static boolean isNullOrEmpty(String... strs) {
        for(int i = 0; i < strs.length; ++i) {
            if (strs[i] == null || strs[i].trim().length() == 0) {
                return true;
            }
        }

        return false;
    }

    public static String getThreadsPartitonID(String taskid) {
        return String.valueOf(Integer.valueOf(taskid) + 1);
    }

    public static String getPostsPartitonID(String taskid, String tid) {
        String partitionid = tid.replaceAll("[^\\d]*", "").replaceAll("(\\d)*", "$1");
        Integer partition = 3 * Integer.valueOf(taskid) + Integer.valueOf(partitionid) % 3 - 1;
        return String.valueOf(partition);
    }

    public static String xmlToString(Node node) {
        try {
            Source source = new DOMSource(node);
            StringWriter stringWriter = new StringWriter();
            Result result = new StreamResult(stringWriter);
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.transform(source, result);
            return stringWriter.getBuffer().toString().replaceAll("<\\?.*\\?>", "");
        } catch (TransformerConfigurationException var6) {
            LOG.error("JavaUtil xmlToString error, TransformerConfigurationException", var6);
        } catch (TransformerException var7) {
            LOG.error("JavaUtil xmlToString error, TransformerException", var7);
        }

        return null;
    }

    public static String getNodeValue(Node node, String attrName) {
        if (node == null) {
            return null;
        } else {
            Node attrNode = node.getAttributes().getNamedItem(attrName);
            return attrNode != null && attrNode.getNodeValue() != null ? attrNode.getNodeValue().trim() : null;
        }
    }

    public static String getTagContent(Node node) {
        return xmlToString(node);
    }

    public static Long createId() {
        UUID uuid = UUID.randomUUID();
        return getCRC32(uuid.toString());
    }

    public static long getCRC32(String txt) {
        long crc = 0L;

        try {
            CRC32 c = new CRC32();
            c.reset();
            c.update(txt.getBytes("UTF-8"));
            crc = c.getValue();
        } catch (Exception var4) {
            LOG.error("获取字符串的CRC32时失败:", var4);
        }

        return crc;
    }

    public static String getClueType(String clue) {
        if (StringUtils.isEmpty(clue)) {
            return "";
        } else {
            String clueType = "";
            String qqReg = "([1-9]\\d{4,11})";
            String mailReg = "(([a-z]|[0-9]|_|\\.|-)+@[\\w[.-]]+\\.[\\w]+)";
            String phoneReg = "(1([358]\\d|47)\\d{8})";
            String telephoneReg = "(((010|02[0-9]|0[3-9]\\d{2})[-]?)[1-9]\\d{6,7})";
            String addressReg = "(([^\\w]*[省|自治区])?[^\\d]*[市|区|县](.*[道|路|街|村])?.{1,4}号)l";
            String ipReg = "(\\d{1,3}\\.){3}\\d{1,3}";
            if (clue.matches(phoneReg)) {
                clueType = "phone";
            } else if (clue.matches(telephoneReg)) {
                clueType = "telephone";
            } else if (clue.matches(qqReg)) {
                clueType = "qq";
            } else if (clue.matches(mailReg)) {
                clueType = "mail";
            } else if (clue.matches(addressReg)) {
                clueType = "address";
            } else if (clue.matches(ipReg)) {
                clueType = "ip";
            } else {
                clueType = "other";
            }

            return clueType;
        }
    }

    public static boolean isMail(String mail) {
        String reg = "(([a-z]|[0-9]|_|\\.|-)+@[\\w[.-]]+\\.[\\w]+)";
        return mail.matches(reg);
    }

    public static String changeSpecialSign(String str) {
        if (StringUtil.isNullOrEmpty(str)) {
            str = "";
        }

        str = str.replaceAll("<[^>]*>", "");
        str = str.replaceAll("&quot;", "\"");
        str = str.replaceAll("&amp;", "&");
        str = str.replaceAll("&lt;", "<");
        str = str.replaceAll("&gt;", ">");
        str = str.replaceAll("&nbsp;", " ");
        return str;
    }

    public static String extractKey(String summary) {
        String keySeq = "";
        String key = "";
        int m = summary.indexOf("<font color=red>");

        for(boolean var4 = false; m > -1; m = summary.indexOf("<font color=red>")) {
            int n = summary.indexOf("</font>");
            if (n > m) {
                key = summary.substring(m + 16, n);
                if (!isIdIndexOf(keySeq, key)) {
                    keySeq = keySeq + "," + key;
                }
            }

            summary = summary.substring(n + 7);
        }

        return keySeq.replaceAll(",", " ").trim();
    }

    public static boolean isIdIndexOf(String idSeq, String id) {
        boolean flag = false;
        String[] ids = idSeq.split(",");

        for(int i = 0; i < ids.length; ++i) {
            if (ids[i].equals(id)) {
                flag = true;
                break;
            }
        }

        return flag;
    }

    public static String changeSpecialSignForXml(String str) {
        if (StringUtil.isNullOrEmpty(str)) {
            str = "";
        }

        str = str.replaceAll("&", "&amp;");
        str = str.replaceAll("<", "&lt;");
        str = str.replaceAll(">", "&gt;");
        str = str.replaceAll("\"", "&quot; ");
        str = str.replaceAll("'", "&apos;");
        return str;
    }

    public static String filterEmoji(String source) {
        if (source != null) {
            Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[☀-⟿]", 66);
            Matcher emojiMatcher = emoji.matcher(source);
            if (emojiMatcher.find()) {
                source = emojiMatcher.replaceAll(" ");
                return source;
            } else {
                return source;
            }
        } else {
            return source;
        }
    }

    public static long getCrc32(String s) {
        long crc = 0L;

        try {
            CRC32 c = new CRC32();
            c.reset();
            c.update(s.getBytes());
            crc = c.getValue();
        } catch (Exception var4) {
            LOG.error("getCrc32 error", var4);
        }

        return crc;
    }

    public static void main(String[] args) {
    }
}