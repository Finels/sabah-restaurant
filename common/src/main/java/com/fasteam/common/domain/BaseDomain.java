package com.fasteam.common.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Description:  com.fasteam.appcommon.domain
 * Copyright: © 2017 FanLei. All rights reserved.
 * Company: NULL
 *
 * @author FL
 * @version 1.0
 * @timestamp 2019/3/24
 */
public abstract class BaseDomain implements Serializable {
    private static final long serialVersionUID = 642000376426338079L;
    private Long id;
    private Date createTime;
    private Long createOperator;
    private Date updateTime;
    private Long updateOperator;

    public BaseDomain() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateOperator() {
        return this.createOperator;
    }

    public void setCreateOperator(Long createOperator) {
        this.createOperator = createOperator;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateOperator() {
        return this.updateOperator;
    }

    public void setUpdateOperator(Long updateOperator) {
        this.updateOperator = updateOperator;
    }
}

