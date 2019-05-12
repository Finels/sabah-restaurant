package com.fasteam.system.domain;


import com.fasteam.common.domain.BaseDomain;

/**
 * Created by aaron on 2016/12/26.
 */
public class BaseAreaCode extends BaseDomain {
    private Long code;
    private String name;
    private Long pcode;
    private String pname;
    private int level;
    private String alias;

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPcode() {
        return pcode;
    }

    public void setPcode(Long pcode) {
        this.pcode = pcode;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
