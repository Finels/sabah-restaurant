package com.fasteam.app.query;

import com.fasteam.common.query.BaseQuery;

/**
 * Description:  com.fasteam.app.query
 * Copyright: © 2017 FanLei. All rights reserved.
 * Company: NULL
 *
 * @author FL
 * @version 1.0
 * @timestamp 2019/4/13
 */
public class PrinterQuery extends BaseQuery {
    private Long createOperator;

    public Long getCreateOperator() {
        return createOperator;
    }

    public void setCreateOperator(Long createOperator) {
        this.createOperator = createOperator;
    }
}
