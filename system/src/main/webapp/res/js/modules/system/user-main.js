$(function () {
    var container = $("#userList");

    $("#resetBtn").click(function () {
        $("#username").val("");
        $("#selectGroupCode").val("");
        $("#groupName").val("");
        $("#roleId").val("");
        $("#valid").val("");
        $("#auditing").val("");
    });

    $(".passwordReset", container).on("click", function () {
        common.dialogEdit(
            "重置密码",
            common.getCtx() + "/sysUser/prePasswordReset?id=" + $(this).attr("id"),
            {
                height: 180,
                width: 560
            }
        );
    });

    $("#searchUserBtn").on("click", function () {
        $("#pageNo").val(1);
        $("#searchForm", container).submit();
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
            url: common.getCtx() + "/sysGroup/getChildNode",
            autoParam: ["id"]
        }
    };
    var Tree = {
        initTree: function () {
            $.fn.zTree.init($("#groupTree"), setting, zNodes);
            zTree = $.fn.zTree.getZTreeObj("groupTree");
            $("#groupTree").width(200);
            $("#groupTree").height(250);
        }
    };

    //树节点添加click事件
    function zTreeOnClick(event, treeId, treeNode) {
        $("#selectGroupCode").val(treeNode.code);
        $("#groupName").val(treeNode.name).focusout();
        popMenu.hide();
    }

    popMenu = $("#groupName").popup('groupTree', 300);
    Tree.initTree();
    reSetFoot();
});