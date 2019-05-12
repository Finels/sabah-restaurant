package com.fasteam.system.configuration.taglib;

import com.fasteam.common.util.StringUtil;
import org.springframework.context.annotation.Configuration;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.io.Writer;

/**
 * 过滤字符串
 */
@Configuration
public class GetValidString extends SimpleTagSupport {
    private String string;
    private int length;

    /**
     * @throws JspException jsp
     * @throws IOException  io
     */
    public void doTag() throws JspException, IOException {
        Writer out = getJspContext().getOut();
        out.write(StringUtil.getValidString(string.replaceAll("alert", ""), length));
        out.flush();
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}