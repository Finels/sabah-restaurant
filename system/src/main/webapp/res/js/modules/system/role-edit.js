$(function () {
    $("#cancelAddRole").on("click", function () {
        parent.common.closeEditDialog();
    });

    $("#submitAddRole").on("click", function () {
        $("#addRoleForm").submit();
    });

    $("#addRoleForm").validate({
        rules: {
            name: {
                required: true,
                remote: {
                    url: common.getCtx() + "/sysRole/checkRoleName",
                    type: "post",
                    dataType: "json",
                    data: {
                        'id': $("#id").val()
                    }
                }
            },
            code: {
                required: true
            }
        },
        messages: {
            name: {
                required: "请输入角色名称！",
                remote: "系统中已存在该角色！"
            },
            code: {
                required: "请选择角色编码！"
            }
        }
    });
});