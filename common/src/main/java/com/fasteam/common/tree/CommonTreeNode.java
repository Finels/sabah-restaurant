package com.fasteam.common.tree;

import java.util.List;

/**
 * Description:  com.fasteam.appcommon.tree
 * Copyright: © 2017 FanLei. All rights reserved.
 * Company: NULL
 *
 * @author FL
 * @version 1.0
 * @timestamp 2019/3/24
 */
public class CommonTreeNode<T> {
    private Long id;
    private String name;
    private NodeTypeEnum nodeType;
    private Boolean open;
    private List<T> children;
    private String icon;
    private String iconOpen;
    private String iconClose;
    private Boolean isParent;
    private String title;
    private Boolean isAsync = false;
    private Integer level;
    private Boolean checked = false;
    private Boolean addFlag;
    private Boolean editFlag;
    private Boolean delFlag;

    public CommonTreeNode() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NodeTypeEnum getNodeType() {
        return this.nodeType;
    }

    public void setNodeType(NodeTypeEnum nodeType) {
        this.nodeType = nodeType;
    }

    public Boolean getOpen() {
        return this.open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public List<T> getChildren() {
        return this.children;
    }

    public void setChildren(List<T> children) {
        this.children = children;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Boolean getIsParent() {
        return this.isParent;
    }

    public void setIsParent(Boolean isParent) {
        this.isParent = isParent;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getLevel() {
        return this.level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Boolean getIsAsync() {
        return this.isAsync;
    }

    public void setIsAsync(Boolean isAsync) {
        this.isAsync = isAsync;
    }

    public String getIconOpen() {
        return this.iconOpen;
    }

    public void setIconOpen(String iconOpen) {
        this.iconOpen = iconOpen;
    }

    public String getIconClose() {
        return this.iconClose;
    }

    public void setIconClose(String iconClose) {
        this.iconClose = iconClose;
    }

    public Boolean getChecked() {
        return this.checked;
    }

    public Boolean getAddFlag() {
        return this.addFlag;
    }

    public void setAddFlag(Boolean addFlag) {
        this.addFlag = addFlag;
    }

    public Boolean getEditFlag() {
        return this.editFlag;
    }

    public void setEditFlag(Boolean editFlag) {
        this.editFlag = editFlag;
    }

    public Boolean getDelFlag() {
        return this.delFlag;
    }

    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}