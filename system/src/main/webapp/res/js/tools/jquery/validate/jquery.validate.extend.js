// 手机号码验证
jQuery.validator.addMethod("mobile", function (value, element) {
    if (value == null || value == "") {
        return this.optional(element);
    }
    var length = value.length;
    var mobile = /^13[0-9]{9}|15[012356789][0-9]{8}|18[012356789][0-9]{8}|147[0-9]{8}$/;
    return this.optional(element) || (length == 11 && mobile.test(value));
}, "手机号码格式错误！");

// 电话号码验证   
jQuery.validator.addMethod("phone", function (value, element) {
    if (value == null || value == "") {
        return this.optional(element);
    }
    var tel = /^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/;
    return this.optional(element) || (tel.test(value));
}, "电话号码格式错误！");

//电话号码和手机号码验证
jQuery.validator.addMethod("phoneOrMobile", function (value, element) {
    if (value == null || value == "") {
        return this.optional(element);
    }
    var tel = /^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/;

    var length = value.length;
    // var mobile = /^13[0-9]{9}|15[012356789][0-9]{8}|18[012356789][0-9]{8}|147[0-9]{8}$/;
    var mobile = /^1[0-9]{10}$/;
    return this.optional(element) || (tel.test(value)) || (length == 11 && mobile.test(value));

}, "联系电话格式错误！");

//身份证验证
jQuery.validator.addMethod("idCard", function (value, element) {
    if (value == null || value == "") {
        return this.optional(element);
    }

    return this.optional(element) || isIdCardNo(value);
}, "身份证格式错误！");

// 密码校验
jQuery.validator.addMethod("strongPassword", function (value, element) {
    if (value == null || value == "") {
        return this.optional(element);
    }

    return this.optional(element) || checkPassword(value);
}, "请使用字符+数字+特殊字符的密码组合！");

// 邮政编码验证   
jQuery.validator.addMethod("zipCode", function (value, element) {
    if (value == null || value == "") {
        return this.optional(element);
    }
    var tel = /^[0-9]{6}$/;
    return this.optional(element) || (tel.test(value));
}, "邮政编码格式错误！");

// QQ号码验证   
jQuery.validator.addMethod("qq", function (value, element) {
    if (value == null || value == "") {
        return this.optional(element);
    }
    var tel = /^[1-9]\d{4,12}$/;
    return this.optional(element) || (tel.test(value));
}, "qq号码格式错误！");

// IP地址验证
jQuery.validator.addMethod("ip", function (value, element) {
    if (value == null || value == "") {
        return this.optional(element);
    }
    var ip = /^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;
    return this.optional(element) || (ip.test(value) && (RegExp.$1 < 256 && RegExp.$2 < 256 && RegExp.$3 < 256 && RegExp.$4 < 256));
}, "Ip地址格式错误！");

//URL校验
jQuery.validator.addMethod("url", function (value, element) {
    if (value == null || value == "") {
        return this.optional(element);
    }
    var urlRegex = "^((https|http|ftp|rtsp|mms)://)"
        + "(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?"
        + "(([0-9]{1,3}.){3}[0-9]{1,3}"
        + "|"
        + "([0-9a-z_!~*'()-]+.)*"
        + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]."
        + "[a-z]{2,6})"
        + "(:[0-9]{1,4})?"
        + "((/?)|"
        + "(/[0-9a-zA-Z_!~*'().;?:@&=+$,%#-]+)+/?)$";
    reg = new RegExp(urlRegex);
    return this.optional(element) || (reg.test(value));
}, "URL地址格式有误！");

// 字母和数字的验证
jQuery.validator.addMethod("chrnum", function (value, element) {
    if (value == null || value == "") {
        return this.optional(element);
    }
    var chrnum = /^([a-zA-Z0-9]+)$/;
    return this.optional(element) || (chrnum.test(value));
}, "只能输入数字和字母(字符A-Z, a-z, 0-9)！");

// 字母和数字的验证
jQuery.validator.addMethod("alpha_dash", function (value, element) {
    if (value == null || value == "") {
        return this.optional(element);
    }
    var chrnum = /^([a-zA-Z0-9_\-]+)$/;
    return this.optional(element) || (chrnum.test(value));
}, "只能输入数字、字母、-和下划线！");


// 中文的验证
jQuery.validator.addMethod("chinese", function (value, element) {
    if (value == null || value == "") {
        return this.optional(element);
    }
    var chinese = /^[\u4e00-\u9fa5]+$/;
    return this.optional(element) || (chinese.test(value));
}, "只能输入中文！");

// 下拉框验证
$.validator.addMethod("selectNone", function (value, element) {
    return value == "请选择";
}, "必须选择一项！");

// 字节长度验证
jQuery.validator.addMethod("byteRangeLength", function (value, element, param) {
    if (value == null || value == "") {
        return this.optional(element);
    }
    var length = value.length;
    for (var i = 0; i < value.length; i++) {
        if (value.charCodeAt(i) > 127) {
            length++;
        }
    }
    return this.optional(element) || (length >= param[0] && length <= param[1]);
}, $.validator.format("请确保输入的值在{0}-{1}个字节之间(一个中文字算2个字节)！"));

// 字符串不能相等
jQuery.validator.addMethod("nequalTo", function (value, element, param) {
    if (value == null || value == "") {
        return this.optional(element);
    }
    var targetValue = $(param).val();
    return this.optional(element) || (value != targetValue);
}, "两次输入不能相同！");

//身份证号码的验证规则
function isIdCardNo(num) {
    var len = num.length, re;
    if (len == 15)
        re = new RegExp(/^(\d{6})()?(\d{2})(\d{2})(\d{2})(\d{2})(\w)$/);
    else if (len == 18)
        re = new RegExp(/^(\d{6})()?(\d{4})(\d{2})(\d{2})(\d{3})(\w)$/);
    else {
        return false;
    }
    var a = num.match(re);
    if (a != null) {
        if (len == 15) {
            var D = new Date("19" + a[3] + "/" + a[4] + "/" + a[5]);
            var B = D.getYear() == a[3] && (D.getMonth() + 1) == a[4] && D.getDate() == a[5];
        }
        else {
            var D = new Date(a[3] + "/" + a[4] + "/" + a[5]);
            var B = D.getFullYear() == a[3] && (D.getMonth() + 1) == a[4] && D.getDate() == a[5];
        }
        if (!B) {
            return false;
        }
    }
    if (!re.test(num)) {
        return false;
    }
    return true;
}

function checkPassword(value) {
    var now = value;
    var re = new RegExp("[a-zA-Z]");
    var len1 = re.test(now);
    re = new RegExp("[0-9]");
    var len2 = re.test(now);
    // re = new RegExp("((?=[\x21-\x7e]+)[^A-Za-z0-9])");
    // var len3 = re.test(now);
    if (len1 && len2) {
        return true;
    }
    return false;
}