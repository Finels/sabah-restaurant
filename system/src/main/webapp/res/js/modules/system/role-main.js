$(function () {
    var container = $("#roleList");

    $("#searchRoleBtn", container).on("click", function () {
        $("#searchForm", container).submit();
    });

    $("#addRoleBtn", container).on("click", function () {
        common.dialogEdit(
            "新增用户角色",
            common.getCtx() + "/sysRole/preEdit",
            {
                height: 280,
                width: 540
            }
        );
    });

    $(".editRoleBtn", container).on("click", function () {
        common.dialogEdit(
            "修改用户角色",
            common.getCtx() + "/sysRole/preEdit?id=" + $(this).attr("roleId"),
            {
                height: 280,
                width: 540
            }
        );
    });

    $(".authorizationRoleBtn", container).on("click", function () {
        common.dialogEdit(
            "【" + $(this).attr("roleName") + "】分配权限",
            common.getCtx() + "/sysRole/preAuthorization?id=" + $(this).attr("roleId"),
            {
                height: 400,
                width: 600
            }
        );
    });
});