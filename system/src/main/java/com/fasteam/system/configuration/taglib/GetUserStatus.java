package com.fasteam.system.configuration.taglib;

import com.fasteam.system.constant.AuditingStatusEnum;
import com.fasteam.system.constant.UserStatusEnum;
import org.springframework.context.annotation.Configuration;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.io.Writer;
import java.util.Date;

/**
 * 构造用户状态
 */
@Configuration
public class GetUserStatus extends SimpleTagSupport {

    public int userFrom;
    public int userStatus;
    public Date validTimeBegin;
    public Date validTimeEnd;
    public String auditing;

    public void doTag() throws JspException, IOException {
        Writer out = getJspContext().getOut();
        String result = "";

        if (AuditingStatusEnum.NOT_REGISTERED.equals(auditing)) {
            result = AuditingStatusEnum.NOT_REGISTERED.getDescription();
        } else {
            if (UserStatusEnum.FORMAL.getCode() == userStatus) {
                result += UserStatusEnum.FORMAL.getDescription();
            } else if (UserStatusEnum.TRY.getCode() == userStatus) {
                result += UserStatusEnum.TRY.getDescription();
            }

            Date now = new Date();
            if ((validTimeBegin == null && validTimeEnd == null)
                    || (validTimeBegin == null && validTimeEnd != null && validTimeEnd.after(now))
                    || (validTimeBegin != null && validTimeBegin.before(now) && validTimeEnd == null)
                    || (validTimeBegin != null && validTimeBegin.before(now) && validTimeEnd != null && validTimeEnd.after(now))) {
                result += "有效";
            } else {
                result += "无效";
            }
        }
        out.write(result);
        out.flush();
    }

    public String getAuditing() {
        return auditing;
    }

    public void setAuditing(String auditing) {
        this.auditing = auditing;
    }

    public int getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(int userFrom) {
        this.userFrom = userFrom;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

    public Date getValidTimeBegin() {
        return validTimeBegin;
    }

    public void setValidTimeBegin(Date validTimeBegin) {
        this.validTimeBegin = validTimeBegin;
    }

    public Date getValidTimeEnd() {
        return validTimeEnd;
    }

    public void setValidTimeEnd(Date validTimeEnd) {
        this.validTimeEnd = validTimeEnd;
    }
}