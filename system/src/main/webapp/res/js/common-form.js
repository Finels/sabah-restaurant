// 设置默认的Form Validation选项
var commonForm = {};

$(function () {
    if ($.validator) {
        $.validator.setDefaults({
            errorPlacement: function (error, element) {
                $(element).addClass("redBorder");
                if (element.attr("type") == "radio")
                    error.insertAfter(element.parent());
                else
                    error.insertAfter(element);
            },
            success: function (label, element) {
                $(element).removeClass("redBorder");
                label.remove();
            },
            submitHandler: function (form) {
                commonForm.submitForm(form);
            }
        });
    }
});

commonForm.submitForm = function (form, callback) {
    parent.common.showLoading('正在提交，请稍后...');
    $(form).ajaxSubmit({
        type: "post",
        dataType: "json",
        success: function (result) {
            parent.common.hideLoading();
            if (result.code == 200) {// 保存信息成功完成后.
                parent.common.showResultMsg(result.message || '保存成功！', function () {
                    if (callback) {
                        callback();
                    } else {
                        parent.common.search();
                    }
                });
            } else {
                parent.common.showMsg(result.message || '保存失败！');
            }
        },
        error: function (result) {
            parent.common.hideLoading();
            parent.common.showMsg(result.message || '保存失败！');
        }
    });
};

