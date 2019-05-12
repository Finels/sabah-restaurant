///////////////////////////////////////////////////////////
// 公共js方法
///////////////////////////////////////////////////////////
//重写trim
String.prototype.trim = function () {
    return this.replace(/(^[\s\xA0]+|[\s\xA0]+$)/g, '');
};

dialog.defaults.okValue = "确定";
dialog.defaults.cancelValue = "取消";
dialog.defaults.autofocus = true;
// dialog.defaults.padding = '15px';

dialog.notice = function (options) {
    var opt = options || {},
        api, aConfig, hide, wrap, top,
        duration = 800;

    var config = {
        id: '_Notice',
        left: '100%',
        top: '100%',
        fixed: true,
        drag: false,
        resize: false,
        follow: null,
        lock: false,
        backdropOpacity: 0.2,
        esc: true,
        init: function (here) {
            api = this;
            aConfig = dialog.config;
            wrap = $(dialog.node);
            top = parseInt(wrap[0].style.top);
            hide = top + wrap[0].offsetHeight;

            wrap.css('top', hide + 'px')
                .animate({top: top + 'px'}, duration, function () {
                    opt.init && opt.init.call(api, here);
                });
        },
        close: function (here) {
            wrap.animate({top: hide + 'px'}, duration, function () {
                opt.close && opt.close.call(this, here);
                aConfig.close = $.noop;
                api.close();
            });

            return false;
        }
    };

    for (var i in opt) {
        if (opt.hasOwnProperty(i)) {
            if (config[i] === undefined) {
                config[i] = opt[i];
            }
        }
    }

    return dialog(config);
};

var common = {};

common.getCtx = function () {
    return ctx;
};

common.genPwd = function (pwd, salt, rand) {
    return common.md5(common.md5Pwd(pwd, salt) + (rand || ''));
};

common.md5Pwd = function (pwd, salt) {
    return common.md5(pwd + '{' + salt + '}');
};

common.md5 = function (str) {
    return hex_md5(str);
};

common.notice = function (title, content, width, time) {
    dialog.notice({
        title: title,
        width: width || 220,
        content: content,
        time: time || null
    });
};

common.extendDialog = function () {
    if ($("#dialog_temp_iframe").html() != undefined)
        return;
    //高度
    var obj = $("div.ui-dialog").first();
    var dialogHeight = obj.height();
    var dialogWidth = obj.width();
    var htm = "<iframe id=\"dialog_temp_iframe\" src=\"javascript:false\" style=\"border:0px;position:absolute; visibility:inherit; top:0px; left:0px; width:" + dialogWidth + "px; height:" + dialogHeight + "px; z-index:-40;filter='progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)';\"></iframe>";
    obj.after(htm);
}

/**
 * 默认关闭后不删除生成的DOM
 * @param id
 * @param isRemove
 */
common.closeDialog = function (id, isRemove) {
    var d = dialog.get(id);
    if (d) {
        d.close();
        isRemove && d.remove();
    }
};

common.showDialog = function (options) {
    return dialog(options).showModal();
};

common.showLoading = function (msg) {
    var tip = msg || '正在处理，请稍后...';
    var defaultMsg = '<div><i class="loading"></i>' + tip + '</div>';

    return common.showDialog({
        id: '_LoadingDlg',
        content: defaultMsg
    });
};

common.hideLoading = function () {
    common.closeDialog('_LoadingDlg', true);
};

// 显示消息
common.showMsg = function (msg) {
    return common.showDialog({
        title: '提示',
        content: msg,
        width: 200,
        ok: true
    });
};

common.showResultMsg = function (msg, callback) {
    return common.showDialog({
        title: '提示',
        content: msg,
        width: 200,
        ok: callback
    });
};

// 确认框
common.showConfirm = function (msg, ok, cancel) {
    return common.showDialog({
        title: '提示',
        content: msg,
        width: 200,
        ok: ok,
        cancel: cancel || true
    });
};

common.showTip = function (elemId, msg, position) {
    return dialog({
        id: 'tip_' + elemId,
        content: msg,
        padding: '8px',
        align: position || 'right',
        quickClose: false// 点击空白处快速关闭
    }).show(document.getElementById(elemId));
};

common.closeTip = function (elemId) {
    common.closeDialog('tip_' + elemId, true);
};

common.closeAllTips = function () {
    $.each(dialog.list, function (d) {
        if (d.indexOf('tip_') == 0) {
            common.closeDialog(d, true);
        }
    });
};

common.logout = function () {
    common.showConfirm('确定要退出系统吗？', function () {
        window.location.href = common.getCtx() + 'auth/logout';
    }, true);
};

common.back = function () {
    window.history.go(-1);
};

common.modifyPwd = function (id) {
    common.dialogEdit(
        "修改密码",
        common.getCtx() + "/sysUser/prePasswordEdit?id=" + id,
        {
            height: 180,
            width: 560
        }
    );
};

common.dialogEdit = function (title, url, options) {
    common.showDialog($.extend(true, {
        id: '_editDialog',
        title: title,
        width: '500px',
        url: common.addAjaxParamToUrl(url),
        ok: false
    }, options || {}));
};

common.closeEditDialog = function () {
    common.closeDialog('_editDialog', true);
};

common.commonCtx = function () {
    return $.extend(true, {}, {
        ctx: common.getCtx(),
        imagePath: common.getCtx() + '/images',
        stylePath: common.getCtx() + '/css'
    });
};

common.addAjaxParamToUrl = function (url) {
    if (url.indexOf("?") > -1) {
        return url += "&ajaxRequest=1";
    }
    return url += "?ajaxRequest=1";
};

common.dealUrl = function (url) {
    if (url.indexOf('http://') == 0 || url.indexOf('https://') == 0) {
        return url;
    }
    var ctx = common.getCtx();
    var randUrl = url;
    var idx = randUrl.indexOf(ctx);
    if (idx != 0) {
        randUrl = ctx + randUrl;
    }
    return randUrl;
};

common.navigate = function (url, params, target) {
    var paramStr = '';
    //if (params && !$.isEmptyObject(params)) {
    //    paramStr = $.param(params);
    //}
    if (params != '' && params != undefined) {
        if (url.indexOf('?') == -1) {
            paramStr = '?' + params;
        } else {
            paramStr = '&' + params;
        }
    }
    //linj增加 target跳转参数功能
    if (target) {
        window.open(common.dealUrl(url) + paramStr, target);
    } else {
        window.location.href = common.dealUrl(url) + paramStr;
    }
};

common.ajax = function (options) {
    return $.ajax(options);
};

common.ajaxGet = function (url, data, callback, dataType) {
    var randUrl = common.dealUrl(url);
    if (randUrl.indexOf('?') == -1) {
        randUrl += '?r=';
    } else {
        randUrl += '&r=';
    }
    randUrl += new Date().getTime();
    return $.get(randUrl, data, callback, dataType);
};

common.ajaxGetJson = function (url, data, callback) {
    return common.ajaxGet(url, data, callback, "json");
};

common.ajaxPost = function (url, data, callback, dataType) {
    return $.post(common.dealUrl(url), data, callback, dataType);
};

common.download = function (url) {
    window.location.href = common.dealUrl(url);
};

common.scrollToTop = function () {//滚动到页面顶部
    $(document.body).scrollTo({top: 0, left: 0}, 1000);
};

common.scrollToId = function (id, parent) { //滚动到指定的ID元素
    var doc = parent ? $("#" + parent) : $(document.body);
    var position = $("#" + id, doc).position();
    $(document.body).scrollTo({top: position.top - 90, left: position.left}, 800);
};

common.isEmpty = function (str) {
    if (str == undefined || str == null) {
        return true;
    }
    if (typeof(str) == "string") {
        str = str.trim();
        return (str == null || str == "");
    } else {
        return false;
    }
};


/**JS获取当前时间，获取当前时间的前后N天的时间start**************/
/*//当前日期
 var tcur=showTime(0);
 //前一天
 var tqyt=showTime(-1);
 //后一天
 var thyt=showTime(1);*/
function addByTransDate(dateParameter, num) {
    var translateDate = "", dateString = "", monthString = "", dayString = "";
    translateDate = dateParameter.replace("-", "/").replace("-", "/");
    var newDate = new Date(translateDate);
    newDate = newDate.valueOf();
    newDate = newDate + num * 24 * 60 * 60 * 1000;
    newDate = new Date(newDate);
    //如果月份长度少于2，则前加 0 补位
    if ((newDate.getMonth() + 1).toString().length == 1) {
        monthString = 0 + "" + (newDate.getMonth() + 1).toString();
    } else {
        monthString = (newDate.getMonth() + 1).toString();
    }
    //如果天数长度少于2，则前加 0 补位
    if (newDate.getDate().toString().length == 1) {
        dayString = 0 + "" + newDate.getDate().toString();
    } else {
        dayString = newDate.getDate().toString();
    }
    dateString = newDate.getFullYear() + "-" + monthString + "-" + dayString;
    return dateString;
}

function checkEndTime(startTime, endTime) {
    if (startTime == "" || endTime == "") {
        return true;
    }
    var start = new Date(startTime.replace("-", "/").replace("-", "/"));
    var end = new Date(endTime.replace("-", "/").replace("-", "/"));
    if (end < start) {
        return false;
    }
    return true;
}

function reduceByTransDate(dateParameter, num) {
    var translateDate = "", dateString = "", monthString = "", dayString = "";
    translateDate = dateParameter.replace("-", "/").replace("-", "/");
    var newDate = new Date(translateDate);
    newDate = newDate.valueOf();
    newDate = newDate - num * 24 * 60 * 60 * 1000;
    newDate = new Date(newDate);
    //如果月份长度少于2，则前加 0 补位
    if ((newDate.getMonth() + 1).toString().length == 1) {
        monthString = 0 + "" + (newDate.getMonth() + 1).toString();
    } else {
        monthString = (newDate.getMonth() + 1).toString();
    }
    //如果天数长度少于2，则前加 0 补位
    if (newDate.getDate().toString().length == 1) {
        dayString = 0 + "" + newDate.getDate().toString();
    } else {
        dayString = newDate.getDate().toString();
    }
    dateString = newDate.getFullYear() + "-" + monthString + "-" + dayString;
    return dateString;
}

//得到日期  主方法
common.showTime = function (pdVal) {
    var trans_day = "";
    var cur_date = new Date();
    var cur_year = new Date().format("yyyy");
    var cur_month = cur_date.getMonth() + 1;
    var real_date = cur_date.getDate();
    cur_month = cur_month > 9 ? cur_month : ("0" + cur_month);
    real_date = real_date > 9 ? real_date : ("0" + real_date);
    eT = cur_year + "-" + cur_month + "-" + real_date;
    if (pdVal > 0) {
        trans_day = addByTransDate(eT, pdVal);
    }
    else if (pdVal < 0) {
        trans_day = reduceByTransDate(eT, -pdVal);
    }
    else {
        trans_day = eT;
    }
    //处理
    return trans_day;
};
/**JS获取当前时间，获取当前时间的前后N天的时间end**************/

/**
 * 时间对象的格式化;
 */
Date.prototype.format = function (format) {
    /*
     * eg:format="yyyy-MM-dd hh:mm:ss";
     */
    var o = {
        "M+": this.getMonth() + 1, // month
        "d+": this.getDate(), // day
        "H+": this.getHours(), // hour
        "m+": this.getMinutes(), // minute
        "s+": this.getSeconds(), // second
        "q+": Math.floor((this.getMonth() + 3) / 3), // quarter
        "S": this.getMilliseconds()
        // millisecond
    };

    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4
        - RegExp.$1.length));
    }

    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1
                ? o[k]
                : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
};

$(function () {
    try {
        $('input[placeholder], textarea[placeholder]').placeholder();
    } catch (e) {
    }
    $(".manage_table table tr").hover(
        function () {
            $(this).addClass("manage_table_bg1");
        },
        function () {
            $(this).removeClass("manage_table_bg1");
        }
    );

    $(".attention_table table tr").hover(
        function () {
            $(this).addClass("attention_table_bg1");
        },
        function () {
            $(this).removeClass("attention_table_bg1");
        }
    );
    $(".clew_table2 table tr").hover(
        function () {
            $(this).addClass("clew_table2_bg1");
        },
        function () {
            $(this).removeClass("clew_table2_bg1");
        }
    );
    $(".clew_attention").hover(
        function () {
            $(this).addClass("clew_attention_bg1");
        },
        function () {
            $(this).removeClass("clew_attention_bg1");
        }
    );

    $(".grabble_txt").hover(
        function () {
            $(this).addClass("grabble_txt_bg1");
        },
        function () {
            $(this).removeClass("grabble_txt_bg1");
        }
    );

    $(".index_table").hover(
        function () {
            $(this).addClass("grabble_txt_bg1");
        },
        function () {
            $(this).removeClass("grabble_txt_bg1");
        }
    );

    $(".wb_table").hover(
        function () {
            $(this).addClass("grabble_txt_bg1");
        },
        function () {
            $(this).removeClass("grabble_txt_bg1");
        }
    );

    $(".turnoff_main_table table tbody tr").hover(
        function () {
            $(this).addClass("grabble_txt_bg1");
        },
        function () {
            $(this).removeClass("grabble_txt_bg1");
        }
    );

    $(".manage_table table tr:odd").addClass("manage_table_bg");
    $(".attention_table table tr:even").addClass("attention_table_bg");
    $(".grabble_txt:odd").addClass("grabble_txt_bg");
    $(".turnoff_main_table table tbody tr:even").addClass("grabble_txt_bg");
    $(".clew_table2 table tr:even").addClass("clew_table2_bg");

    $(".index_table:odd").addClass("grabble_txt_bg");
    $(".wb_table:odd").addClass("grabble_txt_bg");
    $(".clew_attention:odd").addClass("clew_attention_bg");
});

//验证密码强度
common.checkPassword = function () {
    var passwordVal = $("#password").val();
    var strongRegex = new RegExp("^(?=.{8,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*\\W).*$", "g");
    var mediumRegex = new RegExp("^(?=.{7,})(((?=.*[A-Z])(?=.*[a-z]))|((?=.*[A-Z])(?=.*[0-9]))|((?=.*[a-z])(?=.*[0-9]))).*$", "g");
    var enoughRegex = new RegExp("(?=.{6,}).*", "g");

    if (false == enoughRegex.test(passwordVal)) {
        //密码小于六位的时候，密码强度图片都为灰色
        $("#weakPassword").removeClass("add_table_con_table_bg");
        $("#strongPassword").removeClass("add_table_con_table_bg");
        $("#veryStrongPassword").removeClass("add_table_con_table_bg");
    } else if (strongRegex.test(passwordVal)) {
        //密码为八位及以上并且字母数字特殊字符三项都包括为强
        $("#weakPassword").removeClass("add_table_con_table_bg");
        $("#strongPassword").removeClass("add_table_con_table_bg");
        $("#veryStrongPassword").addClass("add_table_con_table_bg");
    } else if (mediumRegex.test(passwordVal)) {
        //密码为七位及以上并且字母、数字、特殊字符三项中有两项，强度是较强
        $("#weakPassword").removeClass("add_table_con_table_bg");
        $("#strongPassword").addClass("add_table_con_table_bg");
        $("#veryStrongPassword").removeClass("add_table_con_table_bg");
    } else {
        //如果密码为6为及以下，就算字母、数字、特殊字符三项都包括，强度也是弱的
        $("#weakPassword").addClass("add_table_con_table_bg");
        $("#strongPassword").removeClass("add_table_con_table_bg");
        $("#veryStrongPassword").removeClass("add_table_con_table_bg");
    }
    return true;
};

function gotoMenu(url, topMenuCode, secMenuCode, target) {
    common.navigate(url + '?_topMenu=' + topMenuCode + '&_secondMenu=' + secMenuCode + '&_thirdMenu=0', '', target);
}

function gotoTopMenu(url, topMenuCode, target) {
    common.navigate(url, '_topMenu=' + topMenuCode + '&_secondMenu=0&_thirdMenu=0', '', target);
}

function gotoSecMenu(url, topMenuCode, secMenuCode, target) {
    common.navigate(url, '_topMenu=' + topMenuCode + '&_secondMenu=' + secMenuCode + '&_thirdMenu=0', '', target);
}

function gotoThirdMenu(url, topMenuCode, secMenuCode, thirdMenuCode, target) {
    common.navigate(url, '_topMenu=' + topMenuCode + '&_secondMenu=' + secMenuCode + '&_thirdMenu=' + thirdMenuCode, '', target);
}

//根据传入iframe的id自动调整iframe的高度
function setIFrameHeight(id) {
    var FFextraHeight = 0;
    var navigator = window.navigator.userAgent;
    var iframe = document.getElementById(id);
    try {
        if (navigator.indexOf("IE 6.0") >= 1 || navigator.indexOf("Safari") >= 1) {
            iframe.height = iframe.contentWindow.document.documentElement.scrollHeight;
        } else if (navigator.indexOf("Firefox") >= 1 || navigator.indexOf("IE 7.0") >= 1 || navigator.indexOf("IE 8.0") >= 1 || navigator.indexOf("IE 9.0") >= 1 || navigator.indexOf("IE 10.0") >= 1) {
            if (iframe.contentDocument.body) {
                iframe.height = iframe.contentDocument.body.offsetHeight + FFextraHeight + 10;
            }
        } else {
            if (iframe.contentWindow.document.body) {
                iframe.height = Math.max(iframe.contentWindow.document.body.scrollHeight, iframe.contentWindow.document.documentElement.scrollHeight);
            }
        }
    } catch (e) {
        if (iframe.contentWindow.document.body) {
            iframe.height = Math.max(iframe.contentWindow.document.body.scrollHeight, iframe.contentWindow.document.documentElement.scrollHeight);
        }
    }

    reSetFoot();
}

function reSetFoot() {
    if ($(window).height() >= $(document).height() - 4) {
        $(".foot").addClass("footFix");
    } else {
        $(".foot").removeClass("footFix");
    }
}

function checkNum(e) {
    var eve = window.event ? e.keyCode : e.which;
    return (eve >= 48 && eve <= 57) || eve == 8;
}

function checkNumAndComma(e) {
    var eve = window.event ? e.keyCode : e.which;
    return (eve >= 48 && eve <= 57) || eve == 8 || eve == 44;
}

function checkNumAndSeparate(e) {
    var eve = window.event ? e.keyCode : e.which;
    return (eve >= 48 && eve <= 57) || eve == 8 || eve == 32 || eve == 44 || eve == 45;
}

function checkNumAndSeparateAndEnter(e) {
    var eve = window.event ? e.keyCode : e.which;
    return (eve >= 48 && eve <= 57) || eve == 8 || eve == 32 || eve == 44 || eve == 45 || eve == 46 || eve == 13;
}

// 判断属性是否为空
function isEmpty(str) {
    if (str == null || str == "") {
        return true;
    }
    str = str.trim();
    return (str == null || str == "");
}

function containsSpecialSign(str) {
    var pattern = /^.*[\\;'<>?{}].*$/;
    if (!pattern.exec(str))
        return false;
    else
        return true;
}