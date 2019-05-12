package com.fasteam.app.domain;

import com.fasteam.common.domain.BaseDomain;

/**
 * Description:  菜品类别
 * Copyright: © 2017 FanLei. All rights reserved.
 * Company: NULL
 *
 * @author FL
 * @version 1.0
 * @timestamp 2019/4/12
 */
public class DishType extends BaseDomain {
    private String name;
    private Integer sort;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
