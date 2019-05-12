$(function () {
    $("#cancelBtn").on("click", function () {
        parent.common.closeEditDialog();
    });

    $("#saveBtn").on("click", function () {
        $("#editForm").submit();
    });

    //验证密码强度
    $("#password").keyup(function () {
        common.checkPassword();
    });

    $("#editForm").validate({
        rules: {
            password: {
                required: true,
                minlength: 6,
                strongPassword: true
            },
            passwordConfirm: {
                equalTo: "#password"
            }
        },
        messages: {
            password: {
                required: "请输入密码！",
                minlength: $.validator.format("最少要输入 {0} 个字符！")
            },
            passwordConfirm: {
                equalTo: "输入的密码不一致！"
            }
        }
    });
});