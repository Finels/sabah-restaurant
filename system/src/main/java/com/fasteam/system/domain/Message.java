package com.fasteam.system.domain;


import com.fasteam.common.domain.BaseDomain;

/**
 * 消息
 * Created by aaron on 2016/12/21.
 */
public class Message extends BaseDomain {
    private String content;
    private String readUserIds;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReadUserIds() {
        return readUserIds;
    }

    public void setReadUserIds(String readUserIds) {
        this.readUserIds = readUserIds;
    }
}
