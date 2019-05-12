package com.fasteam.system.domain;

import java.io.Serializable;

/**
 * 数据字典
 * Created by aaron on 2016/12/21.
 */
public class Dictionary implements Serializable {
    private static final long serialVersionUID = -6506676075765014413L;
    private Integer id;
    private String type;
    private String code;
    private String name;
    private Integer enabled;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }
}
