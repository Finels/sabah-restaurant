package com.fasteam.system.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/29.
 */
public class SysObjectFatherNode extends SysObjectNode {

    private Boolean loading;    //false 未加载  默认null
    private List<SysObjectNode> children = new ArrayList<>();

    public void addChildren(SysObjectNode sysObjectNode) {
        if (sysObjectNode != null) {
            this.children.add(sysObjectNode);
        }
    }

    public Boolean getLoading() {
        return loading;
    }

    public void setLoading(Boolean loading) {
        this.loading = loading;
    }

    public List<SysObjectNode> getChildren() {
        return children;
    }

    public void setChildren(List<SysObjectNode> children) {
        this.children = children;
    }

}
