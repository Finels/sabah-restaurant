$(function () {
    //获取线索数据权限下拉菜单
    $.ajax({
        type: "POST",
        url: common.getCtx() + "/sysRole/getFindDatas",
        cache: false,
        data: {
            roleId: $("#id").val()
        },
        dataType: "json"
    }).then(function (data) {
        if (data) {
            $("#findDataScopes").append(data.result);
            $('#findDataScopes').multiselect("refresh");
        }
    });

    var noneSelectedText = "请选择线索数据权限";
    var findDataScopeName = $("#findDataScopeName").val();
    if(findDataScopeName != '') {
        noneSelectedText = findDataScopeName;
    }

    $("#findDataScopes").multiselect(
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
                var num = "已选" + numChecked + "个用户角色";
                $("#findDataScope").val("," + map2 + ",");
                $("#findDataScopeName").val(map);

                if (numChecked <= 2) {
                    return map;
                } else {
                    return num;
                }
            },
            uncheckAll: function () {
                $("#findDataScopeName").val("");
                $("#findDataScope").val("");
            },
            click: function (numChecked, numTotal, checkedInputs) {
                var map = $(checkedInputs).map(function () {
                    return this.title;
                }).get().join(",");
                var map2 = $(checkedInputs).map(function () {
                    return this.value;
                }).get().join(",");
                $("#findDataScopeName").val(map);
                $("#findDataScope").val(map2);
            }
        }
    ).multiselectfilter({
        label: "过滤",
        placeholder: ""
    });


    function dataAreaTempChange() {
        var type = $("input[name='dataAreaTemp']:checked").val();
        if (type == 'g') {
            $("#dataArea_l").attr("disabled", false);
            $("#share").attr("disabled", false);
        } else if (type == 'p') {
            $("#dataArea_l").removeAttr("checked").attr("disabled", true);
            $("#share").removeAttr("checked").attr("disabled", true);
        }
    }

    dataAreaTempChange();

    $(".dataAreaTemp").on("click", function () {
        dataAreaTempChange();
    });

    $("#cancelAuthorizationRole").on("click", function () {
        parent.common.closeEditDialog();
    });

    $("#submitAuthorizationRole").on("click", function () {
        var zTree = $.fn.zTree.getZTreeObj("authorityTree");
        var checkCount = zTree.getCheckedNodes(true);
        var authorityIds = "";
        for (var i = 0; i < checkCount.length; i++) {
            authorityIds += "," + checkCount[i].id;
        }

        $("#resources").val(authorityIds);

        var dataAreaTemp = $("input[type='radio'][name='dataAreaTemp']:checked").val();
        var dataArea_s = $("input[type='checkbox'][name='dataArea_s']:checked").val();
        var dataArea_l = $("input[type='checkbox'][name='dataArea_l']:checked").val();
        if (dataAreaTemp == undefined) {
            parent.common.showMsg("请选择数据权限！");
            return false;
        }
        if (dataArea_s == "s") {
            dataAreaTemp = dataAreaTemp + ",s";
        }
        if (dataArea_l == "l") {
            dataAreaTemp = dataAreaTemp + ",l";
        }

        $("#dataArea").val(dataAreaTemp);

        var baseDataScopeTemp = ",";
        $.each($('input[name=baseDataScopeTemp]:checkbox'), function(){
           if(this.checked) {
               baseDataScopeTemp = baseDataScopeTemp + $(this).val() + ",";
           }
        });
        $("#baseDataScope").val(baseDataScopeTemp);

        commonForm.submitForm($("#authorizationRoleForm"));
    });

    //功能菜单tree
    var zNodes = eval($("#resourceNodes").val());
    var setting = {
        check: {
            enable: true
        },
        data: {
            simpleData: {
                enable: true
            }
        }
    };

    $.fn.zTree.init($("#authorityTree"), setting, zNodes);
});