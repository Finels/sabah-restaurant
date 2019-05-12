$(function () {
    //获取角色下拉菜单
    $.ajax({
        type: "POST",
        url: common.getCtx() + "/sysRole/getRoles",
        cache: false,
        data: {
            userId: $("#id").val()
        },
        dataType: "json"
    }).then(function (data) {
        if (data) {
            $("#roles").append(data.result);
            $('#roles').multiselect("refresh");
        }
    });

    var noneSelectedText = "请选择用户角色";
    var roleNames = $("#roleNames").val();
    if(roleNames != '') {
        noneSelectedText = roleNames;
    }

    $("#roles").multiselect(
        {
            noneSelectedText: noneSelectedText,
            checkAllText: "全选",
            uncheckAllText: "取消",
            selectedList: 2,
            position: {
                my: 'left bottom',
                at: 'left top'
            },
            height: 150,
            maxHeight: 130,
            selectedText: function (numChecked, numTotal, checkedInputs) {
                var map = $(checkedInputs).map(function () {
                    return this.title;
                }).get().join(",");
                var map2 = $(checkedInputs).map(function () {
                    return this.value;
                }).get().join(",");
                var map3 = $(checkedInputs).map(function () {
                    return $(this).attr("label");
                }).get().join(",");
                var num = "已选" + numChecked + "个用户角色";
                $("#roleIds").val(map2);
                $("#roleNames").val(map);
                $("#roleCodes").val(map3);

                if (numChecked <= 2) {
                    return map;
                } else {
                    return num;
                }
            },
            uncheckAll: function () {
                $("#roleIds").val("");
                $("#roleNames").val("");
            },
            click: function (numChecked, numTotal, checkedInputs) {
                var map = $(checkedInputs).map(function () {
                    return this.title;
                }).get().join(",");
                var map2 = $(checkedInputs).map(function () {
                    return this.value;
                }).get().join(",");
                var map3 = $(checkedInputs).map(function () {
                    return $(this).attr("label");
                }).get().join(",");

                $("#roleIds").val(map2);
                $("#roleNames").val(map);
                $("#roleCodes").val(map3);
            }
        }
    ).multiselectfilter({
            label: "过滤",
            placeholder: ""
        });

    //用户组tree
    var popMenu;
    var zNodes = '';
    var zTree;
    var setting = {
        callback: {
            onClick: zTreeOnClick
        }, async: {
            enable: true,
            url: common.getCtx() + "sysGroup/getChildNode",
            autoParam: ["id"]
        }
    };
    var Tree = {
        initTree: function () {
            $.fn.zTree.init($("#groupTree"), setting, zNodes);
            zTree = $.fn.zTree.getZTreeObj("groupTree");
            //初始化选中节点
            var sNode;
            //<c:if test="${not empty groupid and groupid ne 0}">
            //sNode = zTree.getNodeByParam("id","${groupid}",null);
            //</c:if>
            if (sNode) {
                zTree.selectNode(sNode);
                $("#organizationid").val(sNode.id);
            }
            $("#groupTree").width(200);
            $("#groupTree").height(200);
        }
    };

    //树节点添加click事件
    function zTreeOnClick(event, treeId, treeNode) {
        var nodeType = treeNode.nodeType;
        $("#groupId").val(treeNode.id);
        $("#groupName").val(treeNode.name).focusout();
        popMenu.hide();
    }

    //返回或者取消
    $(".cacheAddUser").click(function () {
        window.location = common.getCtx() + "/sysUser/main";
    });

    //验证密码强度
    $("#password").keyup(function () {
        common.checkPassword();
    });

    $("#submitAddUser").on("click", function () {
        $("#addUserForm").submit();
    });

    $("#addUserForm").validate({
        ignore: ".ignore",
        submitHandler: function (form) {
            commonForm.submitForm(form, function () {
                window.location.href = common.getCtx() + "/sysUser/main";
            });
        },
        rules: {
            username: {
                required: true,
                rangelength: [4, 15],
                chrnum: true,
                remote: {
                    url: common.getCtx() + "/sysUser/checkUsername",
                    type: "post",
                    dataType: "json"
                },
                checkUserHadDel: true
            },
            password: {
                required: true,
                minlength: 6,
                strongPassword: true
            },
            passwordConfirm: {
                equalTo: "#password"
            },
            groupName: {
                required: true
            },
            roleIds: {
                required: true
            },
            userFrom: {
                required: true
            },
            userStatus: {
                required: true
            },
            auditing: {
                required: true
            },
            maxTask: {
                digits: true
            },
            name: {
                required: true
            },
            idCard: {
                idCard: true
            },
            telephone: {
                phoneOrMobile: true
            },
            qq: {
                qq: true
            },
            email: {
                email: true
            }
        },
        messages: {
            username: {
                required: "请输入用户名！",
                rangelength: $.validator.format("请输入长度在 {0} 到 {1} 之间的账号！"),
                remote: "系统中已存在该用户名称！"
            },
            password: {
                required: "请输入密码！",
                minlength: $.validator.format("最少要输入 {0} 个字符！")
            },
            passwordConfirm: {
                equalTo: "输入的密码不一致！"
            },
            groupName: {
                required: "请选择用户组！"
            },
            roleIds: {
                required: "请选择用户角色！"
            },
            userFrom: {
                required: "请选择用户类型！"
            },
            userStatus: {
                required: "请选择用户状态！"
            },
            auditing: {
                required: "请选择审核状态！"
            },
            name: {
                required: "请输入姓名！"
            }
        }
        /*,
         showErrors: function (errorMap, errorList) {
         $.each(this.successList, function (index, value) {
         $(value).removeClass("redBorder");
         return $(value).popover("hide");
         });
         return $.each(errorList, function (index, value) {
         var _popover;
         _popover = $(value.element).popover({
         trigger: "manual",
         placement: "right",
         content: value.message,
         template: "<div class=\"popover\"><div class=\"arrow\"></div><div class=\"popover-inner\"><div class=\"popover-content\"><p></p></div></div></div>"
         });
         // Bootstrap 3.x :
         _popover.data("bs.popover").options.content = value.message;
         // Bootstrap 2.x :
         //_popover.data("popover").options.content = value.message;
         $(value.element).addClass("redBorder");
         return $(value.element).popover("show");
         });
         }*/
    });

    popMenu = $("#groupName").popup('groupTree', 300);
    Tree.initTree();

});

function activate() {
    $.ajax({
        type: "POST",
        url: common.getCtx() + "/sysUser/activateUser?username=" + $("#username").val(),
        dataType: "json",
        success: function (result) {
            common.hideLoading();
            if (result.code == 200) {// 激活成功完成后.
                parent.common.showResultMsg(result.message || '激活成功！', function () {
                    window.location.href = common.getCtx() + "/sysUser/main";
                });
            } else {
                parent.common.showMsg(result.message || '激活失败！');
            }
        },
        error: function (result) {
            common.hideLoading();
            parent.common.showMsg(result.message || '激活失败！');
        }
    });
}

// 用户是否被删除过,如果是直接激活
jQuery.validator.addMethod("checkUserHadDel", function (value, element) {
    var result = true;
    $.ajax({
        type: "POST",
        url: common.getCtx() + "/sysUser/checkUserHadDel",
        cache: false,
        data: {
            username: value
        },
        dataType: "json",
        async: false
    }).then(function (response) {
        if (response.code == 200) {
            result = false;
        }
    });
    return result;
}, "系统中存在该账号，请直接 <input type='button' class='public_but' onclick='activate()' value='激活'/>！");