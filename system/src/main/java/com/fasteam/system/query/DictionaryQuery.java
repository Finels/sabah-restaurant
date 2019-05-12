package com.fasteam.system.query;


import com.fasteam.common.query.BaseQuery;

/**
 * 数据字典查询请求
 */
public class DictionaryQuery extends BaseQuery {
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
