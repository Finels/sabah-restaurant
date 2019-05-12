package com.fasteam.common.constant;

/**
 * 行为类型
 */
public enum SysLogActionEnum {
    AC_QUERY("query", "查询"),
    AC_ADD("add", "新增"),
    AC_UPDATE("update", "修改"),
    AC_DEL("del", "删除"),
    AC_ANALYSIS("analysis", "分析"),
    AC_IMPORT("import", "导入"),
    AC_EXPORT("export", "导出"),
    AC_RESTART("restart", "重启"),
    AC_CLOSE("close", "关闭");

    private String code;
    private String name;

    SysLogActionEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
