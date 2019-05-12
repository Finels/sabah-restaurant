$(function () {
    //用户组tree
    var zNodes = eval($("#sysGroupNodes").val());
    //var userSessionId = $("#userSessionId").val();
    //var isAdmin = $("#isAdmin").val();
    if (zNodes.length != 0) {
        var setting = {
            view: {
                expandSpeed: "",
                addHoverDom: addAndEditHoverDom,
                removeHoverDom: removeHoverDom,
                selectedMulti: false
            },
            edit: {
                drag: {
                    isMove: false,
                    isCopy: false
                },
                showRenameBtn: false,
                removeTitle: "删除该用户组",
                enable: true
            },
            async: {
                enable: true,
                url: common.getCtx() + "/sysGroup/getChildNode",
                autoParam: ["id"]
            },
            callback: {
                onClick: zTreeOnClick,
                beforeRemove: beforeRemove
            }
        };

        function addAndEditHoverDom(treeId, treeNode) {
            var sObj = $("#" + treeNode.tId + "_span");
            if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0) return;
            var addStr;
            //if(treeNode.userId == userSessionId || (isAdmin == 'true')) {
                addStr = "<span id='addBtn_" + treeNode.tId + "' onfocus='this.blur();'><span class='button selfAdd' title='新增用户组' id='selfAddBtn_" + treeNode.tId + "'></span><span class='button selfEdit' title='修改用户组' id='selfEditBtn_" + treeNode.tId + "'></span></span>";
            //} else {
            //    addStr = "<span id='addBtn_" + treeNode.tId + "' onfocus='this.blur();'><span class='button selfAdd' title='新增用户组' id='selfAddBtn_" + treeNode.tId + "'></span></span>";
            //}
            sObj.after(addStr);
            var addBtn = $("#selfAddBtn_" + treeNode.tId);
            if (addBtn) addBtn.bind("click", function () {
                common.dialogEdit(
                    "新增用户组",
                    common.getCtx() + "sysGroup/preEdit?fatherId=" + treeNode.id +
                    "&fatherName=" + treeNode.name +
                    "&fatherCode=" + treeNode.code,
                    {
                        height: 480,
                        width: 540
                    }
                );
                return false;
            });

            var editBtn = $("#selfEditBtn_" + treeNode.tId);
            if (editBtn) editBtn.bind("click", function () {
                //if (treeNode.code == 'NI') {
                //    common.showMsg("顶级用户组不能修改！");
                //    return false;
                //}
                common.dialogEdit(
                    "修改用户组",
                    common.getCtx() + "sysGroup/preEdit?id=" + treeNode.id,
                    {
                        height: 480,
                        width: 540
                    }
                );
                return false;
            });
            //if (treeNode.userId == userSessionId || (isAdmin == 'true')) {
            //    $("#" + treeNode.tId + "_remove").show();
            //} else {
            //    $("#" + treeNode.tId + "_remove").hide();
            //}
        }

        function removeHoverDom(treeId, treeNode) {
            $("#addBtn_" + treeNode.tId).unbind().remove();
        }

        function beforeRemove(treeId, treeNode) {
            if (treeNode.code == 'NI') {
                common.showMsg("顶级用户组不能删除！");
                return false;
            }

            var code = treeNode.code;
            common.showConfirm("删除用户组将同时删除子用户组和对应的所有用户，是否确认删除组【" + treeNode.name + " 】？", function () {
                $.ajax({
                    url: common.getCtx() + "sysGroup/del",
                    data: {"code": code},
                    type: "post",
                    dataType: "json",
                    success: function (result) {
                        if (result.code == 200) {
                            common.showResultMsg(result.message || "删除成功！", function () {
                                common.search();
                                //$("#" + treeNode.tId).unbind().remove();
                            });
                        } else {
                            common.showMsg(result.message || '删除失败！');
                        }
                    },
                    error: function (result) {
                        common.showMsg(result.message || '删除失败！');
                    }
                });
            }, true);

            return false;
        }

        $.fn.zTree.init($("#groupTree"), setting, zNodes);

        //树节点添加click事件
        function zTreeOnClick(event, treeId, treeNode) {
            $("#userListIFrame").attr("src", common.getCtx() + "/sysUser/list?selectGroupCode=" + treeNode.code);
        }
    }
});