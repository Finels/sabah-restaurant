package com.fasteam.system.dto;


import com.fasteam.common.tree.CommonTreeNode;

/**
 * 地区树节点对象
 */
public class AreaTreeNode extends CommonTreeNode<AreaTreeNode> {
    private Long areaCode;    //编码
    private Long pCode;   //父级编码

    public Long getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(Long areaCode) {
        this.areaCode = areaCode;
    }

    public Long getpCode() {
        return pCode;
    }

    public void setpCode(Long pCode) {
        this.pCode = pCode;
    }
}
