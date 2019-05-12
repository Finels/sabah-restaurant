function checkLogo() {
    var topLogoFile = document.getElementById("topLogoFile").value;
    var type = topLogoFile.substring(topLogoFile.lastIndexOf(".") + 1, topLogoFile.length).toLowerCase();
    if (type != "jpg" && type != "bmp" && type != "gif" && type != "png" && type != "jpeg") {
        document.getElementById("topLogoFile").value = "";
        document.getElementById("topLogoFile").outerHTML += '';//清空IE的
        parent.common.showMsg("请上传正确的图片格式！");
        return false;
    }
}

$(function () {
    $("#cancelBtn").on("click", function () {
        parent.common.closeEditDialog();
    });

    $("#saveBtn").on("click", function () {
        $("#editForm").submit();
    });

    $("#editForm").validate({
        rules: {
            name: {
                required: true,
                remote: {
                    url: common.getCtx() + "/sysGroup/checkGroupName",
                    type: "post",
                    dataType: "json",
                    data: {
                        'id': $("#id").val()
                    }
                }
            },
            cityName: {
                required: true
            },
            contactPhone: {
                phoneOrMobile: true
            }
        },
        messages: {
            name: {
                required: "请输入名称！",
                remote: "系统中已存在该名称！"
            },
            cityName: {
                required: "请选择城市！"
            }
        }
    });

    //城市tree
    var popMenu;
    var jsonStr = $("#areaTreeNodes").val();
    var zNodes = eval(jsonStr);
    var zTree;
    var setting = {
        callback: {
            onClick: zTreeOnClick
        }, async: {
            enable: true,
            url: common.getCtx() + "baseAreaCode/getCityNode",
            autoParam: ["areaCode"]
        }
    };
    var Tree = {
        initTree: function () {
            $.fn.zTree.init($("#cityTree"), setting, zNodes);
            zTree = $.fn.zTree.getZTreeObj("cityTree");
            $("#cityTree").width(240);
            $("#cityTree").height(290);
        }
    };

    //树节点添加click事件
    function zTreeOnClick(event, treeId, treeNode) {
        $("#cid").val(treeNode.areaCode);
        $("#pid").val(treeNode.pCode).focusout();
        $("#cityName").val(treeNode.name).focusout();
        popMenu.hide();
    }

    popMenu = $("#cityName").popup('cityTree', 300);
    Tree.initTree();

    //用户组tree
    var groupPopMenu;
    var groupZNodes = '';
    var groupZTree;
    var groupSetting = {
        callback: {
            onClick: groupZTreeOnClick
        }, async: {
            enable: true,
            url: common.getCtx() + "/sysGroup/getChildNode",
            autoParam: ["id"]
        }
    };
    var groupTree = {
        initTree: function () {
            $.fn.zTree.init($("#groupTree"), groupSetting, groupZNodes);
            groupZTree = $.fn.zTree.getZTreeObj("groupTree");
            $("#groupTree").width(240);
            $("#groupTree").height(260);
        }
    };

    //树节点添加click事件
    function groupZTreeOnClick(event, treeId, treeNode) {
        $("#fatherId").val(treeNode.id);
        $("#fatherCode").val(treeNode.code);
        $("#fatherName").val(treeNode.name).focusout();
        groupPopMenu.hide();
    }

    groupPopMenu = $("#fatherName").popup('groupTree', 300);
    groupTree.initTree();
});