$(function () {
    var container = $("#editResource");

    function selectType() {
        var type = $("input[name='type']:checked", container).val();
        if (type === "1") {
            $(".featureShow").hide();
            $(".systemAndMenuShow").show();
            $(".menuAndFeatureShow").show();
            $(".menuShow").show();
        } else if (type === "2") {
            $(".systemAndMenuShow").hide();
            $(".menuShow").hide();
            $(".menuAndFeatureShow").show();
            $(".featureShow").show();
        } else {
            $(".featureShow").hide();
            $(".menuAndFeatureShow").hide();
            $(".menuShow").hide();
            $(".systemAndMenuShow").show();
        }
    }

    selectType();

    $("input[name='type']", container).on("click", function () {
        selectType();
    });

    $("#cancelBtn", container).on("click", function () {
        parent.common.closeEditDialog();
    });

    $("#saveBtn", container).on("click", function () {
        $("#editForm", container).submit();
    });

    $("#editForm", container).validate({
        rules: {
            name: {
                required: true
            },
            uniqueCode: {
                required: true,
                remote: {
                    url: common.getCtx() + "/sysResource/checkUniqueCode",
                    type: "post",
                    dataType: "json",
                    data: {
                        'id': $("#id").val()
                    }
                }
            },
            //url: {
            //    required: true
            //},
            weight: {
                required: true,
                number: true
            }
        },
        messages: {
            name: {
                required: "请输入资源名称！"
            },
            uniqueCode: {
                required: "请输入唯一编码！",
                remote: "该编码已存在！"
            },
            //url: {
            //    required: "请输入资源路径！"
            //},
            weight: {
                required: "请输入排序值！",
                number: "请输入数字！"
            }
        }
    });

    //上级资源tree
    var popMenu;
    var zNodes = '';
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
});