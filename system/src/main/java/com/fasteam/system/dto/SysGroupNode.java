package com.fasteam.system.dto;


import com.fasteam.common.tree.CommonTreeNode;

/**
 * 组织架构树节点对象
 */
public class SysGroupNode extends CommonTreeNode<SysGroupNode> {
    public static final String PARENT_ICON_OPEN = "/system/images/system_ico14.png";
    public static final String PARENT_ICON_CLOSE = "/system/images/system_ico14.png";
    public static final String CHILD_ICON = "/system/images/system_ico15.png";
    public static final int SHOW_LENGTH_ON_TREE = 10;           //在显示时要显示几个字符

    private String code; //节点编码 (地区编码)
    private Long userId;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
