package com.fasteam.common.util;

import org.apache.commons.lang.StringEscapeUtils;

import java.beans.PropertyEditorSupport;

/**
 * Description:  com.fasteam.common.util
 * Copyright: © 2017 FanLei. All rights reserved.
 * Company: NULL
 *
 * @author FL
 * @version 1.0
 * @timestamp 2019/3/24
 */
public class SecureStringEditor extends PropertyEditorSupport {
    private boolean escapeHTML;
    private boolean escapeJavaScript;
    private boolean escapeSQL;

    public SecureStringEditor() {
    }

    public SecureStringEditor(boolean escapeHTML, boolean escapeJavaScript, boolean escapeSQL) {
        this.escapeHTML = escapeHTML;
        this.escapeJavaScript = escapeJavaScript;
        this.escapeSQL = escapeSQL;
    }

    public void setAsText(String text) throws IllegalArgumentException {
        if (text == null) {
            this.setValue((Object)null);
        } else {
            String value = text;
            if (this.escapeHTML) {
                value = AutiXSS.HtmlEncode(text);
            }

            if (this.escapeJavaScript) {
                value = AutiXSS.escapeJavaScript(value);
            }

            if (this.escapeSQL) {
                value = StringEscapeUtils.escapeSql(value);
            }

            this.setValue(value);
        }

    }
}