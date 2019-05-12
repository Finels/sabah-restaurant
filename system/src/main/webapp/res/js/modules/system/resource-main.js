$(function () {
    var container = $("#resourceList");

    $("#searchResourceBtn", container).on("click", function () {
        $("#pageNo", container).val(1);
        $("#searchForm", container).submit();
    });

    $("#addResourceBtn", container).on("click", function () {
        common.dialogEdit(
            "新增资源",
            common.getCtx() + "/sysResource/preEdit",
            {
                height: 485,
                width: 550
            }
        );
    });

    $(".editResourceBtn", container).on("click", function () {
        common.dialogEdit(
            "修改资源",
            common.getCtx() + "/sysResource/preEdit?id=" + $(this).attr("resourceId"),
            {
                height: 485,
                width: 550
            }
        );
    });

    //上级资源tree
    var popMenu;
    var zNodes = "";
    var zTree;
    var setting = {
        callback: {
            onClick: zTreeOnClick
        }, async: {
            enable: true,
            url: common.getCtx() + "/sysResource/getResourceNode",
            autoParam: ["id"]
        }
    };
    var Tree = {
        initTree: function () {
            $.fn.zTree.init($("#resourceTree"), setting, zNodes);
            zTree = $.fn.zTree.getZTreeObj("resourceTree");
            $("#resourceTree").width(240);
            $("#resourceTree").height(250);
        }
    };

    //树节点添加click事件
    function zTreeOnClick(event, treeId, treeNode) {
        $("#fatherId").val(treeNode.id);
        $("#fatherName").val(treeNode.name).focusout();
        popMenu.hide();
    }

    popMenu = $("#fatherName").popup('resourceTree', 300);
    Tree.initTree();
    reSetFoot();
});