package com.fasteam.common.query;

import java.io.Serializable;

/**
 * Description:  com.fasteam.appcommon.query
 * Copyright: © 2017 FanLei. All rights reserved.
 * Company: NULL
 *
 * @author FL
 * @version 1.0
 * @timestamp 2019/3/24
 */
public abstract class BaseQuery implements Serializable {
    private static final long serialVersionUID = 5273436658600825357L;
    private String order;

    public BaseQuery() {
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}

