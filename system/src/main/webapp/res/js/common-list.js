$(function () {
    var elem = $("tr[id^=tableTr_]");
    elem.mouseover(function () {
        $(this).addClass('table_bgcolor1');
    }).mouseleave(function () {
        $(this).removeClass('table_bgcolor1');
    });
    var i = 0;
    elem.each(function () {
        var id = $(this).attr("id").split('_')[1];
        var firstTd = $(this).find("td:first");
        if (firstTd.find("div").hasClass("public_checkbox")) {
            if ($(this).hasClass("vtop")) {
                firstTd.attr("valign", "top");
            }
        } else {
            $(this).prepend([
                '<td width="40"', $(this).hasClass("vtop") ? 'valign="top"' : '', '>',
                '<div id="check_', id,
                '" class="public_checkbox" onclick="common.checkOne(', id, ')">',
                '</div>',
                '</td>'
            ].join(''));
        }
        ++i;
        if (i % 2 == 0) {
            $(this).addClass("table_bgcolor");
        }
    });
});

common.getSearchForm = function () {
    return $("#searchForm");
};

common.getSearchAction = function () {
    var action = common.getSearchForm().attr("action");
    var idx = action.lastIndexOf('/');
    if (idx > 0) {
        action = action.substring(0, idx);
    }
    return action;
};

common.getSearchActionPrefix = function () {
    return $("#searchActionPrefix").val();
};

// 列表相关操作
common.search = function (pageNo) {
    if (common.getSearchForm() && common.getSearchForm().length > 0) {
        common.showLoading('正在查询, 请稍后...');
    }
    //linj修改条件查询返回第一页防止更改条件后查询无数据
    if (pageNo && pageNo > 0) {
        $('#pageNo').val(pageNo);
    }
    common.getSearchForm().submit();
};

common.reset = function () {
    common.getSearchForm().reset();
    common.search();
};

common.viewSort = function (column) {
    $("#sortField").val(column);
    var so = $("#sortOrder").val();
    if (so == '' || so == 'asc') {
        so = 'desc';
    } else {
        so = 'asc';
    }
    $("#sortOrder").val(so);
    common.search();
};

common.checkOne = function (id, prefix) {
    if (prefix == undefined) {
        prefix = "";
    }
    var elem = $("#" + prefix + "check_" + id);
    if (elem.hasClass('public_checkbox_on')) {
        elem.removeClass('public_checkbox_on');
        $("." + prefix + "checkAll").removeClass('public_checkbox_on');
    } else {
        elem.addClass('public_checkbox_on');
        var allHasCheck = true;
        $("b[id^=" + prefix + "check_]").each(function () {
            if (!$(this).hasClass('public_checkbox_on')) {
                allHasCheck = false;
                return false;
            }
        });
        if (allHasCheck) {
            $("." + prefix + "checkAll").addClass('public_checkbox_on');
        }
    }
};

common.checkAll = function (prefix) {
    if (prefix == undefined) {
        prefix = "";
    }
    var elem = $("." + prefix + "checkAll");
    if (elem.hasClass('public_checkbox_on')) {
        elem.removeClass('public_checkbox_on');
        $("b[id^=" + prefix + "check_]").removeClass("public_checkbox_on");
    } else {
        elem.addClass('public_checkbox_on');
        $("b[id^=" + prefix + "check_]").addClass("public_checkbox_on");
    }
};

common.getAllCheckIds = function (prefix) {
    if (prefix == undefined) {
        prefix = "";
    }
    var ids = [];
    $("b[id^=" + prefix + "check_]").each(function () {
        if ($(this).hasClass('public_checkbox_on')) {
            var id = $(this).attr("id").split('_')[1];
            ids.push(id);
        }
    });
    return ids;
};

common.reSetAllCheck = function () {
    if (window.baseDataIFrame) {
        $(".checkAll", window.baseDataIFrame.document).removeClass('public_checkbox_on');
        $("b[id*=check_]", window.baseDataIFrame.document).removeClass("public_checkbox_on");
        $(".sinacheckAll", window.weiBoIFrame.document).removeClass('public_checkbox_on');
        $(".qqcheckAll", window.weiBoIFrame.document).removeClass('public_checkbox_on');
        $("b[id*=check_]", window.weiBoIFrame.document).removeClass("public_checkbox_on");
        $(".checkAll", window.netIdentityIFrame.document).removeClass('public_checkbox_on');
        $("b[id^=check_]", window.netIdentityIFrame.document).removeClass("public_checkbox_on");
        if (window.fiveEightCityIFrame) {
            $(".checkAll", window.fiveEightCityIFrame.document).removeClass('public_checkbox_on');
            $("b[id^=check_]", window.fiveEightCityIFrame.document).removeClass("public_checkbox_on");
        }
        $(".checkAll", window.qqIFrame.document).removeClass('public_checkbox_on');
        $("b[id^=check_]", window.qqIFrame.document).removeClass("public_checkbox_on");
        $(".checkAll", window.qqGroupIFrame.document).removeClass('public_checkbox_on');
        $("b[id^=check_]", window.qqGroupIFrame.document).removeClass("public_checkbox_on");
        $(".checkAll", window.belongIFrame.document).removeClass('public_checkbox_on');
        $("b[id^=check_]", window.belongIFrame.document).removeClass("public_checkbox_on");
        $(".checkAll", window.searchEngineIFrame.document).removeClass('public_checkbox_on');
        $("b[id^=check_]", window.searchEngineIFrame.document).removeClass("public_checkbox_on");
    } else if (window.rightIFrame) {
        if (window.rightIFrame.baseDataIFrame) {
            $(".checkAll", window.rightIFrame.baseDataIFrame.document).removeClass('public_checkbox_on');
            $("b[id*=check_]", window.rightIFrame.baseDataIFrame.document).removeClass("public_checkbox_on");
            $(".sinacheckAll", window.rightIFrame.weiBoIFrame.document).removeClass('public_checkbox_on');
            $(".qqcheckAll", window.rightIFrame.weiBoIFrame.document).removeClass('public_checkbox_on');
            $("b[id*=check_]", window.rightIFrame.weiBoIFrame.document).removeClass("public_checkbox_on");
            $(".checkAll", window.rightIFrame.netIdentityIFrame.document).removeClass('public_checkbox_on');
            $("b[id^=check_]", window.rightIFrame.netIdentityIFrame.document).removeClass("public_checkbox_on");
            $(".checkAll", window.rightIFrame.fiveEightCityIFrame.document).removeClass('public_checkbox_on');
            $("b[id^=check_]", window.rightIFrame.fiveEightCityIFrame.document).removeClass("public_checkbox_on");
            $(".checkAll", window.rightIFrame.qqIFrame.document).removeClass('public_checkbox_on');
            $("b[id^=check_]", window.rightIFrame.qqIFrame.document).removeClass("public_checkbox_on");
            $(".checkAll", window.rightIFrame.qqGroupIFrame.document).removeClass('public_checkbox_on');
            $("b[id^=check_]", window.rightIFrame.qqGroupIFrame.document).removeClass("public_checkbox_on");
            $(".checkAll", window.rightIFrame.belongIFrame.document).removeClass('public_checkbox_on');
            $("b[id^=check_]", window.rightIFrame.belongIFrame.document).removeClass("public_checkbox_on");
            $(".checkAll", window.rightIFrame.searchEngineIFrame.document).removeClass('public_checkbox_on');
            $("b[id^=check_]", window.rightIFrame.searchEngineIFrame.document).removeClass("public_checkbox_on");
        } else {
            $(".checkAll", window.rightIFrame.document).removeClass('public_checkbox_on');
            $("b[id^=check_]", window.rightIFrame.document).removeClass("public_checkbox_on");
        }
    }
};

common.firstPage = function () {
    $("#pageNo").val(1);
    common.search();
};

common.lastPage = function () {
    $("#pageNo").val($("#totalPage").val());
    common.search();
};

common.prevPage = function () {
    var pageNo = parseInt($("#pageNo").val());
    if (pageNo > 1) {
        $("#pageNo").val(pageNo - 1);
        common.search();
    }
};

common.nextPage = function () {
    var pageNo = parseInt($("#pageNo").val());
    var totalPage = parseInt($("#totalPage").val());
    if (pageNo < totalPage) {
        $("#pageNo").val(pageNo + 1);
        common.search();
    }
};

common.edit = function (title, id, params, options) {
    var action = common.getSearchAction();
    var p = {};
    if (id) {
        p['did'] = id;
    }
    if (params && !$.isEmptyObject(params)) {
        $.extend(p, params);
    }
    var url = action + '/edit.do?' + $.param(p);
    common.dialogEdit(title, url, options);
};

common.del = function (id, callback) {
    var ids = [];
    var msg = '';
    if (id) {
        ids.push(id);
        msg = '确定要删除该条数据吗？';
    } else {
        ids = common.getAllCheckIds();
        if (ids.length < 1) {
            common.showMsg('请选择要删除的数据！');
            return;
        }
        msg = '确定要删除选中的数据吗？';
    }

    var action = common.getSearchActionPrefix();

    common.showConfirm(msg, function () {
        $.ajax({
            url: action + 'del',
            data: {"ids": ids.join(',')},
            type: "post",
            dataType: "json",
            success: function (result) {
                if (result.code == 200) {
                    common.showResultMsg(result.message || "删除成功！", function () {
                        if (callback) {
                            callback();
                        } else {
                            common.search();
                        }
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
};

common.preIFrameDel = function (iFrameId, id, action, callback) {
    var reject = false;
    $("b[id^=check_]").each(function () {
        if ($(this).hasClass('public_checkbox_on')) {
            reject = $(this).attr("reject");
            if (reject == 'true') {
                return false;
            }
        }
    });
    if (reject == 'true') {
        parent.common.showMsg("您选择的数据包含上级共享的数据，不能删除！");
    } else {
        common.iFrameDel(iFrameId, id, action, callback)
    }
};

common.iFrameDel = function (iFrameId, id, action, callback) {
    var ids = [];
    var msg = '';
    if (id) {
        ids.push(id);
        msg = '确定要删除该条数据吗？';
    } else {
        ids = common.getAllCheckIds();
        if (ids.length < 1) {
            parent.common.showMsg('请选择要删除的数据！');
            return;
        }
        msg = '确定要删除选中的数据吗？';
    }

    if (!action) {
        action = common.getSearchActionPrefix();
    }

    parent.common.showConfirm(msg, function () {
        $.ajax({
            url: action + '/del',
            data: {"ids": ids.join(',')},
            type: "post",
            dataType: "json",
            success: function (result) {
                if (result.code == 200) {
                    parent.common.showResultMsg(result.message || "删除成功！", function () {
                        if (callback) {
                            callback();
                        } else {
                            common.search();
                        }
                    });
                } else {
                    parent.common.showMsg(result.message || '删除失败！');
                }
            },
            error: function (result) {
                parent.common.showMsg(result.message || '删除失败！');
            }
        });
    }, true);
};

common.refreshCache = function (url, callback) {
    common.ajaxPost(url, function (result) {
        if (result.success) {
            common.showResultMsg("刷新缓存成功！", callback || $.noop);
        } else {
            common.showMsg(result.data || "刷新缓存失败！");
        }
    });
};
