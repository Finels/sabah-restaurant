package com.fasteam.app.domain;

import com.fasteam.common.domain.BaseDomain;

/**
 * Description:  桌号
 * Copyright: © 2017 FanLei. All rights reserved.
 * Company: NULL
 *
 * @author FL
 * @version 1.0
 * @timestamp 2019/4/12
 */
public class TableNum extends BaseDomain {
    private String name;
    private Integer backup;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBackup() {
        return backup;
    }

    public void setBackup(Integer backup) {
        this.backup = backup;
    }
}
