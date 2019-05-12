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
        submitHandler: function (form) {
            parent.common.showLoading('正在提交，请稍后...');
            $(form).ajaxSubmit({
                type: "post",
                dataType: "json",
                success: function (result) {
                    parent.common.hideLoading();
                    if (result.code == 200) {
                        parent.common.showResultMsg(result.message || '修改成功！', function () {
                            parent.common.closeEditDialog();
                        });
                    } else {
                        parent.common.showMsg(result.message || '修改失败！');
                    }
                },
                error: function (result) {
                    parent.common.hideLoading();
                    parent.common.showMsg(result.message || '修改失败！');
                }
            });
        },
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