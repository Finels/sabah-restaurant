package com.fasteam.common.constant;

/**
 * 系统日志描述
 */
public final class SysLogRemarkConst {
    private SysLogRemarkConst() {
    }
    //登录
    public static final String LOGIN_REMARK = "用户【%s】登录进入系统";
    //    public static final String LOGIN_SSO_REMARK = "用户【%s】单点登录进入系统";
//    public static final String LOGOUT_REMARK = "用户【%s】退出系统";
    //用户管理
    public static final String ADD_SYS_USER_REMARK = "用户【%s】新增系统用户：%s";
    public static final String EDIT_SYS_USER_REMARK = "用户【%s】修改系统用户：%s";
    public static final String DEL_SYS_USER_REMARK = "用户【%s】删除【%s】个系统用户";
    public static final String EDIT_PASSWORD_REMARK = "用户【%s】修改密码";
    public static final String EDIT_SYS_USER_AUDI = "用户【%s】将系统用户【%s】的审核状态从【%s】修改为【%s】;";
    public static final String EDIT_KEY_TYPE_LAYOUT = "用户【%s】修改线索发现首页布局";
    //角色管理
    public static final String ADD_ROLE_REMARK = "用户【%s】新增角色：%s";
    public static final String EDIT_ROLE_REMARK = "用户【%s】修改角色：%s";
    public static final String DEL_ROLE_REMARK = "用户【%s】删除【%s】个角色";
    public static final String EDIT_ROLE_AUTHORIZATION_REMARK = "用户【%s】修改【%s】角色的权限";
    //资源管理
    public static final String ADD_RESOURCE_REMARK = "用户【%s】新增资源：%s";
    public static final String EDIT_RESOURCE_REMARK = "用户【%s】修改资源：%s";
    public static final String DEL_RESOURCE_REMARK = "用户【%s】删除【%s】个资源";
    //用户组管理
    public static final String ADD_GROUP_REMARK = "用户【%s】新增用户组：%s";
    public static final String EDIT_GROUP_REMARK = "用户【%s】修改用户组：%s";
    public static final String DEL_GROUP_REMARK = "用户【%s】删除【%s】个用户组";
    //日志管理
    public static final String DEL_SYS_LOG_REMARK = "用户【%s】删除了：%s条日志";

    //权限
    public static final String AUTHORIZATION_ACTION_ADD = "用户【%s】将%s个%s级别内容%s给用户:%s";
    public static final String AUTHORIZATION_ACTION_DEL = "用户【%s】撤销%s_uid:%s的%s级别所有相关授权";


    //主题
    public static final String SUBJECT_ACTION_EDIT = "用户【%s】更新主题%s的状态为%s";

    //文件导出
    public static final String FILE_LOAD = "用户【%s】导出文件:%s";

    //表头管理
    public static final String MAINTENANCE_BASECATEGORY_ADD = "用户【%s】添加类别信息:%s";
    public static final String MAINTENANCE_BASECATEGORY_EDIT = "用户【%s】更新类别信息:%s";
    public static final String MAINTENANCE_BASECATEGORY_DEL = "用户【%s】删除类别信息:%s";
    public static final String MAINTENANCE_BASECOLUMN_ADD = "用户【%s】添加基础字段信息:%s";
    public static final String MAINTENANCE_BASECOLUMN_EDIT = "用户【%s】更新基础字段信息:%s";
    public static final String MAINTENANCE_BASECOLUMN_DEL = "用户【%s】删除基础字段信息:%s";
    public static final String MAINTENANCE_COLUMNMAPPER_QUERY = "用户【%s】查询字段映射信息";
    public static final String MAINTENANCE_COLUMNMAPPER_ADD = "用户【%s】添加字段映射信息:%s";
    public static final String MAINTENANCE_COLUMNMAPPER_EDIT = "用户【%s】更新字段映射信息:%s";
    public static final String MAINTENANCE_COLUMNMAPPER_DEL = "用户【%s】删除字段映射信息:%s";
    public static final String MAINTENANCE_COLUMNTYPE_ADD = "用户【%s】添加基础字段类型信息:%s";
    public static final String MAINTENANCE_COLUMNTYPE_EDIT = "用户【%s】更新基础字段类型信息:%s";
    public static final String MAINTENANCE_COLUMNTYPE_DEL = "用户【%s】删除基础字段类型信息:%s";
    public static final String MAINTENANCE_MESSAGE_ADD = "用户【%s】添加消息提醒信息:%s";
    public static final String MAINTENANCE_MESSAGE_EDIT = "用户【%s】更新消息提醒信息:%s";
    public static final String MAINTENANCE_MESSAGE_DEL = "用户【%s】删除消息提醒信息:%s";
    public static final String MAINTENANCE_MESSAGE_QUERY = "用户【%s】查询消息提醒信息";
    public static final String MAINTENANCE_FEEDBACK_ADD = "用户【%s】添加意见反馈信息:%s";
    public static final String MAINTENANCE_FEEDBACK_EDIT = "用户【%s】更新意见反馈信息:%s";
    public static final String MAINTENANCE_FEEDBACK_DEL = "用户【%s】删除意见反馈信息:%s";
    public static final String MAINTENANCE_TRAINING_ADD = "用户【%s】添加培训信息:%s";
    public static final String MAINTENANCE_TRAINING_EDIT = "用户【%s】更新培训信息:%s";
    public static final String MAINTENANCE_TRAINING_DEL = "用户【%s】删除培训信息:%s";



}
