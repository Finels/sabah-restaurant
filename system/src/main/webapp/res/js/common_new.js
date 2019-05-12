///////////////////////////////////////////////////////////
// 公共js方法
///////////////////////////////////////////////////////////
//重写trim
String.prototype.trim = function () {
    return this.replace(/(^[\s\xA0]+|[\s\xA0]+$)/g, '');
};

var dialog = {};
layer.config({
    // skin: 'layui-layer-molv'
});

/**
 * icon -1：没有图标
 *      0:  感叹号
 *      1:  正确
 *      2:  错误
 *      3:  问号
 *      4:  锁
 *      5： 难过
 *      6:  开心(默认值)
 * @param msg
 */
dialog.alert = function (msg, icon) {
    if (icon != '') {
        return layer.alert(msg, {icon: icon});
    } else {
        return layer.alert(msg, {icon: 6});
    }
};

dialog.tips = function (msg, dom, time) {
    return layer.tips(msg, dom, {
        tips: [1, "#3595CC"], time: time
    });
};

dialog.errorMsg = function (msg) {
    return layer.alert(msg, {icon: 5});
};

dialog.showResultMsg = function (msg, callback) {
    return layer.open({
        type: 0,   // 0 信息，1 页面 2 iframe 3 加载层 4 tips
        icon: 0,
        content: msg,
        btn: ['确定'],
        cancel: function () {
            callback();
        },
        yes: function (index) {
            callback();
            layer.close(index);
        }
    });
};

dialog.showSuccessResultMsg = function (msg, callback) {
    return layer.open({
        type: 0,   // 0 信息，1 页面 2 iframe 3 加载层 4 tips
        icon: 6,
        content: msg,
        btn: ['确定'],
        cancel: function () {
            callback();
        },
        yes: function (index) {
            callback();
            layer.close(index);
        }
    });
};

dialog.showConfirm = function (msg, callback) {
    return layer.confirm(msg, {icon: 0, title: '提示'}, function (index) {
        callback();
        layer.close(index);
    });
};

dialog.showConfirm2 = function (msg, yesCallback, cancelCallback) {
    return layer.confirm(msg, {icon: 0, title: '提示'}, function (index) {
        yesCallback();
        layer.close(index);
    }, function (index) {
        cancelCallback();
        layer.close(index);
    });
};

dialog.showLoading = function () {
    return layer.load(1, {
        content: '正在处理，请稍后...',
        shade: [0.3, '#000000'],
        success: function (layero) {
            layero.find('.layui-layer-content').css({
                'padding-top': '10px',
                'padding-left': '45px',
                'width': '200px'
            });
        }
    })
};

dialog.hideLoading = function (index) {
    return layer.closeAll('loading');
};

dialog.openPage = function (title, content, options) {
    return layer.open($.extend(true, {
        id: "_openPage",
        type: 1,
        title: title,
        content: content,
        maxmin: true
    }, options || {}));
    // return layer.open({
    //     id: "_openPage",
    //     type: 1,
    //     title: title,
    //     content: content,
    //     maxmin: true
    //        shadeClose: true
    // });
};


dialog.openPageByDom = function (title, dom, options) {
    return layer.open($.extend(true, {
        id: "_openPageByDom",
        type: 1,
        title: title,
        content: dom,
        maxmin: true,
        moveOut: true,
        scrollbar: false,
        end: function () {  //窗口销毁后触发
            dom.hide();
        }
    }, options || {}));
};

dialog.openIFrame = function (title, url, options) {
    return layer.open($.extend(true, {
        id: "_openIFrame",
        type: 2,
        title: title,
        content: url,
        maxmin: true,
        area: '600px',
        moveOut: true,
        scrollbar: false
        // success: function (layero, index) {
        //     layer.iframeAuto(index);
        // }
    }, options || {}));
};

dialog.closeAll = function () {
    return layer.closeAll();
};

dialog.closeIFrame = function () {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};

function setLocalHash(hash) {
    window.location.hash = hash;
}

function containsSpecialSign(str) {
    var pattern = /^.*[\\;'<>?{}].*$/;
    if (!pattern.exec(str)) {
        return false;
    } else {
        return true;
    }
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

function checkNum(e) {
    var eve = window.event ? e.keyCode : e.which;
    return (eve >= 48 && eve <= 57) || eve == 8;
}

function checkNumAndComma(e) {
    var eve = window.event ? e.keyCode : e.which;
    return (eve >= 48 && eve <= 57) || eve == 8 || eve == 44;
}
function checkMoney(e) {
    var eve = window.event ? e.keyCode : e.which;
    return (eve >= 48 && eve <= 57) || eve == 8 || eve == 46;
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

function countContain(content, contain) {
    var regex = new RegExp(contain, 'g');
    var result = content.match(regex);
    return !result ? 0 : result.length;
}

var isIE = function () {
    return (window.navigator.userAgent.indexOf("MSIE") >= 1);
};

if (isIE()) {
    if (window.HTMLElement && Object.getOwnPropertyNames(HTMLElement.prototype).indexOf('dataset') === -1) {
        Object.defineProperty(HTMLElement.prototype, 'dataset', {
            get: function () {
                var attributes = this.attributes;
                var name = [],
                    value = [];
                var obj = {};
                for (var i = 0; i < attributes.length; i++) {
                    if (attributes[i].nodeName.slice(0, 5) == 'data-') {
                        name.push(attributes[i].nodeName.slice(5));
                        value.push(attributes[i].nodeValue);
                    }
                }
                for (var j = 0; j < name.length; j++) {
                    obj[name[j]] = value[j];
                }
                return obj;
            }
        });
    }

    window.performance.now = function () {
        return ((+new Date()) - performance.timing.navigationStart);
    };
}

if (!Array.prototype.includes) {
    Object.defineProperty(Array.prototype, 'includes', {
        value: function (searchElement, fromIndex) {

            // 1. Let O be ? ToObject(this value).
            if (this == null) {
                throw new TypeError('"this" is null or not defined');
            }

            var o = Object(this);

            // 2. Let len be ? ToLength(? Get(O, "length")).
            var len = o.length >>> 0;

            // 3. If len is 0, return false.
            if (len === 0) {
                return false;
            }

            // 4. Let n be ? ToInteger(fromIndex).
            //    (If fromIndex is undefined, this step produces the value 0.)
            var n = fromIndex | 0;

            // 5. If n ≥ 0, then
            //  a. Let k be n.
            // 6. Else n < 0,
            //  a. Let k be len + n.
            //  b. If k < 0, let k be 0.
            var k = Math.max(n >= 0 ? n : len - Math.abs(n), 0);

            // 7. Repeat, while k < len
            while (k < len) {
                // a. Let elementK be the result of ? Get(O, ! ToString(k)).
                // b. If SameValueZero(searchElement, elementK) is true, return true.
                // c. Increase k by 1.
                // NOTE: === provides the correct "SameValueZero" comparison needed here.
                if (o[k] === searchElement) {
                    return true;
                }
                k++;
            }

            // 8. Return false
            return false;
        }
    });
}

if (!String.prototype.includes) {
    String.prototype.includes = function (search, start) {
        if (typeof start !== 'number') {
            start = 0;
        }

        if (start + search.length > this.length) {
            return false;
        } else {
            return this.indexOf(search, start) !== -1;
        }
    };
}

Array.prototype.forEach = Array.prototype.forEach || function (fn, context) {
        for (var k = 0, length = this.length; k < length; k++) {
            if (typeof fn === "function" && Object.prototype.hasOwnProperty.call(this, k)) {
                fn.call(context, this[k], k, this);
            }
        }
    };

Array.prototype.map = Array.prototype.map || function (fn, context) {
        var arr = [];
        if (typeof fn === "function") {
            for (var k = 0, length = this.length; k < length; k++) {
                arr.push(fn.call(context, this[k], k, this));
            }
        }
        return arr;
    };

Array.prototype.filter = Array.prototype.filter || function (fn, context) {
        var arr = [];
        if (typeof fn === "function") {
            for (var k = 0, length = this.length; k < length; k++) {
                fn.call(context, this[k], k, this) && arr.push(this[k]);
            }
        }
        return arr;
    };

Array.prototype.some = Array.prototype.some || function (fn, context) {
        var passed = false;
        if (typeof fn === "function") {
            for (var k = 0, length = this.length; k < length; k++) {
                if (passed === true) break;
                passed = !!fn.call(context, this[k], k, this);
            }
        }
        return passed;
    };

Array.prototype.every = Array.prototype.every || function (fn, context) {
        var passed = true;
        if (typeof fn === "function") {
            for (var k = 0, length = this.length; k < length; k++) {
                if (passed === false) break;
                passed = !!fn.call(context, this[k], k, this);
            }
        }
        return passed;
    };

Array.prototype.reduce = Array.prototype.reduce || function (callback, initialValue) {
        var previous = initialValue, k = 0, length = this.length;
        if (typeof initialValue === "undefined") {
            previous = this[0];
            k = 1;
        }

        if (typeof callback === "function") {
            for (k; k < length; k++) {
                this.hasOwnProperty(k) && (previous = callback(previous, this[k], k, this));
            }
        }
        return previous;
    };


Array.prototype.reduceRight = Array.prototype.reduceRight || function (callback, initialValue) {
        var length = this.length, k = length - 1, previous = initialValue;
        if (typeof initialValue === "undefined") {
            previous = this[length - 1];
            k--;
        }
        if (typeof callback === "function") {
            for (k; k > -1; k -= 1) {
                this.hasOwnProperty(k) && (previous = callback(previous, this[k], k, this));
            }
        }
        return previous;
    };

Array.prototype.find = Array.prototype.find || function (fn, context) {
        if (typeof fn === "function") {
            for (var k = 0, length = this.length; k < length; k++) {
                if (fn.call(context, this[k], k, this)) {
                    return this[k];
                }
            }
        }
        return undefined;
    };

Array.prototype.findIndex = Array.prototype.findIndex || function (fn, context) {
        if (typeof fn === "function") {
            for (var k = 0, length = this.length; k < length; k++) {
                if (fn.call(context, this[k], k, this)) {
                    return k;
                }
            }
        }
        return -1;
    };

