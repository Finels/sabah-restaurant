package com.fasteam.system.dto;

/**
 * Created by Administrator on 2018/3/27.
 */
public class SysObjectNode {
    private String title;
    private Long id;
    private String type;
    private boolean expand;     //true 展开  根节点默认展开
    private boolean checked;    //true 选中
    private boolean disabled;   //
    private boolean disableCheckbox;   //

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isExpand() {
        return expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isDisableCheckbox() {
        return disableCheckbox;
    }

    public void setDisableCheckbox(boolean disableCheckbox) {
        this.disableCheckbox = disableCheckbox;
    }
}
