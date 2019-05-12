<%@ page session="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="common/taglib.jsp" %>
<%@ page import="com.fasteam.common.constant.BaseConst" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>登录页面</title>
    <link href="${ctx}/css/normalize.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/css/animations.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/css/style.css" rel="stylesheet" type="text/css"/>
    <script src="${ctx}/js/tools/jquery/jquery-1.12.3.min.js" type="text/javascript"></script>
    <script src="${ctx}/js/tools/jquery/jquery.placeholder.min.js" type="text/javascript"></script>
    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="${ctx}/js/tools/html5shiv.min.js" type="text/javascript"></script>
    <![endif]-->
</head>
<style>
    .valign-middle {
        vertical-align: middle;
    }

    .color-fff {
        color: #fff;
    }

    .align-center {
        text-align: center;
    }

    .fz-12 {
        font-size: 12px;
    }

    .color-login {
        color: #4fd7fb;
    }
</style>
<body style="background: url(${ctx}/images/main_bg_2.jpg) center 0;">
<!-- 背景流星画板 -->
<div class="animate_bg">
    <img class="login_ball_1 pulse" src="${ctx}/images/login_ball_1.png">
    <img class="login_ball_2 pulse" src="${ctx}/images/login_ball_2.png">
    <img class="login_ball_3 pulse" src="${ctx}/images/login_ball_3.png">
    <img class="login_ball_4 pulse" src="${ctx}/images/login_ball_4.png">
</div>
<div class="main_header">
    <c:set var="env" value="<%=BaseConst.ENV%>"/>
    <c:if test="${env != 'xm'}">
        <a class="logo_link" href="javascript:void(0);">
            <img class="logo_img" src="${ctx}/images/logo/main_logo_${env}.png">
        </a>
    </c:if>
</div>
<!-- 主要内容 -->
<div class="main_content_1">
    <div class="login_box_1">
        <div class="login_title_1">用户登录</div>
        <form id="loginForm" action="<spring:url value="/auth/check"/>" method="post">
            <input type="hidden" id="needCheckCode" name="needCheckCode"
                   value="${needCheckCode}"/>
            <input type="hidden" id="returnUrl" name="returnUrl" value="${returnUrl}"/>
            <input type="hidden" id="checkMode" name="checkMode" value="0" />

            <div class="field_box">
                <div class="input_field input_field_1">
                    <img src="${ctx}/images/login_1.png">
                    <input id="username" name="username" type="text" value="${username}" class="input_box_1"
                           placeholder="请输入用户名">
                </div>

                <div class="input_field input_field_1">
                    <img src="${ctx}/images/login_2.png">
                    <input id="password" name="password" type="password" value="${password}" class="input_box_1"
                           placeholder="请输入密码">
                    <img class="password_view" src="${ctx}/images/login_4.png">
                </div>
                <c:if test="${needCheckCode eq '1'}">
                    <div class="input_field input_field_2">
                        <img src="${ctx}/images/login_3.png">
                        <input id="checkCode" name="checkCode" type="text" value="${checkCode}" class="input_box_2"
                               placeholder="请输入验证码">
                    </div>
                    <div class="captcha_field">
                        <img src="<spring:url value="/auth/captcha"/>?t=<%=System.currentTimeMillis()%>"
                             onclick="this.setAttribute('src','<spring:url
                                     value="/auth/captcha"/>?t='+new Date().getTime())"
                             title="点击刷新" alt="点击刷新"
                             style="cursor: pointer;height: 46px;border-radius: 5px;"
                             class="change_captcha"/>
                    </div>
                </c:if>
            </div>
        </form>
        <div class="check_field red <c:if test="${empty message}">hide</c:if>" id="errorMessage">
            <label>${message}</label>
        </div>
        <%--<div class="check_field">
            <div class="check_box" onclick="mutli_check(this);">
                <input class="check_input" type="checkbox" value="multi_1" name="multi">
                <img class="check_img unchecked" src="${ctx}/images/main_uncheck.png">
                <img class="check_img checked" src="${ctx}/images/main_checked.png">
                <label class="check_label">记住密码</label>
            </div>
        </div>--%>
        <a href="javascript:void(0);" class="login_btn_1" id="loginBtn">登录</a>
        <div class="align-center" style="margin-top: 15px;">
          <span>
            <img style="width: 18px;" class="valign-middle" src="${ctx}/images/google-chrome.png">
            <span class="color-fff fz-12">建议使用谷歌浏览器效果最佳！</span>
            <a class="fz-12 color-login" target="_blank" href="${ctx}/js/Chrome_setup.exe">下载谷歌浏览器</a>
          </span>
        </div>
    </div>
    <div style="clear:both;"></div>
</div>
<%--<script src="${ctx}/js/tools/jquery/jquery-brower.js" type="text/javascript"></script>--%>
<%--<script src="${ctx}/js/tools/layui/layui.all.js" type="text/javascript"></script>--%>
<script>
    //    if (jQuery.browser.msie && jQuery.browser.version <= 8) {
    //    }

    //IE8 placeholder 兼容性
    $('input').placeholder();

    //密码是否可见切换
    $('.password_view').click(function () {
        var $field = $(this).closest('.input_field').find('input');
        var type = $field.attr('type');
        if (type === 'text')
            $field.attr('type', 'password');
        else
            $field.attr('type', 'text');
    });

    // 点击输入框，高亮外边框
    $('.login_box_1 input')
        .focus(function () {
            $(this).closest('.input_field').addClass('press');
        })
        .blur(function () {
            $(this).closest('.input_field').removeClass('press');
        });

    // 复选框
    function mutli_check(el) {
        var check_input = el.querySelector('.check_input');
        var checked = check_input.checked;

        if (checked) {
            check_input.checked = false;
            el.className = el.className.replace(/\s?press/, '');
        }
        else {
            check_input.checked = true;
            el.className += ' press';
        }
    }

    var loginBtn = $("#loginBtn");
    var username = $("#username");
    var password = $("#password");
    var checkCode = $("#checkCode");
    var needCheckCode = $("#needCheckCode").val();

    // 回车触发事件
    var keyDownHandle = function (event) {
        if (event.keyCode == 13) {
            loginBtn.click();
        }
    };

    $(document).keydown(keyDownHandle);

    // 焦点
    if ($.trim(username.val()).length == 0 || username.val() == username.attr('placeholder')) {
        username.focus();
    } else if (password.val().length == 0) {
        password.focus();
    } else if (checkCode.val().length == 0) {
        checkCode.focus();
    } else {
        username.focus();
    }

    // form
    var loginForm = $("#loginForm");
    $("#loginBtn").click(function () {
        if ($.trim(username.val()).length == 0) {
            $("#errorMessage").text("请输入用户名！").show();
            username.focus();
            return;
        }
        if (password.val().length == 0) {
            $("#errorMessage").text("请输入密码！").show();
            password.focus();
            return;
        }
        if (needCheckCode == 1 && $.trim(checkCode.val()).length == 0) {
            $("#errorMessage").text("请输入验证码！").show();
            checkCode.focus();
            return;
        }
        loginForm.submit();
    });
</script>
</body>
</html>
