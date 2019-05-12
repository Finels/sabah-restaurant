<%@ page session="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="common/taglib.jsp" %>
<%@ page import="com.fasteam.common.constant.BaseConst" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">

    <link rel="stylesheet" href="${ctx}/css/normalize.css">
    <link rel="stylesheet" href="${ctx}/css/style_1.css">

    <script src="${ctx}/js/tools/jquery/jquery-1.12.3.min.js" type="text/javascript"></script>
    <script src="${ctx}/js/tools/jquery/jquery.placeholder.min.js" type="text/javascript"></script>
    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="${ctx}/js/tools/html5shiv.min.js" type="text/javascript"></script>
    <![endif]-->

    <title>登录页面</title>

    <style>
        html {
            background: url(${ctx}/images/loginPage/bg-global.jpg) center 0;
            background-repeat: no-repeat;
            background-size: cover;
            background-attachment: fixed;
        }

        .new-login-box {
            background: url(${ctx}/images/loginPage/login-frame.png) no-repeat;
        }

        .new-login-input {
            background: url(${ctx}/images/loginPage/input-default.png) no-repeat;
        }

        .new-login-input.press {
            background: url(${ctx}/images/loginPage/input-focus.png) no-repeat;
        }

        .new-login-input.small {
            background: url(${ctx}/images/loginPage/input-default-s.png) no-repeat;
        }

        .new-login-input.small.press {
            background: url(${ctx}/images/loginPage/input-focus-s.png) no-repeat;
        }
        .new-header {
            background: url(${ctx}/images/loginPage/full-header.png) center 0;
        }
    </style>
</head>
<body>
<!-- 预加载 -->
<img src="${ctx}/images/loginPage/input-focus.png" style="display: none;">
<img src="${ctx}/images/loginPage/input-focus-s.png" style="display: none;">

<!-- 头部 -->
<div class="new-header">
    <c:set var="env" value="<%=BaseConst.ENV%>"/>
    <img class="center-logo" src="${ctx}/images/logo/main_logo_${env}.png" style="margin-top: 18px;">
</div>

<!-- 登录框 -->
<div class="new-login-content">
    <div class="new-login-detail">

        <div class="new-login-wrapper">
            <div class="new-login-boxtitle" style="top: -20px;">用户登录</div>
            <div class="new-login-box">
                <!-- 数字证书登录 -->
                <div class="loginMethod">
                    <img src="${ctx}/images/loginPage/ssl-key.png" style="display: block;margin: 10px auto;">
                    <form id="sslLoginForm" action="${sslUrl}" method="post">
                        <input type="hidden" id="sslNeedCheckCode" name="needCheckCode"
                               value="${needCheckCode}"/>
                        <input type="hidden" id="sslReturnUrl" name="returnUrl" value="${returnUrl}"/>
                        <input type="hidden" id="sslCheckMode" name="checkMode" value="3"/>
                    </form>

                    <div style="margin-top: 60px;">
                        <div class="new-input-wrapper">
                            <div class="new-input-wrapper">
                                <div class="new-login-input" style="cursor: pointer;">
                                    <div class="pki-wrapper" id="sslLoginBtn">
                                        <img class="ssl-icon" src="${ctx}/images/loginPage/usb.png">
                                        <span class="ssl-text" style="width: 238px;">请插入数字证书，点击登录</span>
                                        <div class="ssl-sep"></div>
                                        <img class="ssl-go" src="${ctx}/images/loginPage/right-arrow.png">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div style="" class="check_field red <c:if test="${empty message}">hide</c:if>" id="errorMessage">
                        <label>${message}</label>
                    </div>
                </div>
                <div class="align-center" style="margin-top: 20px;">
                    <div class="check-box">
          <span>
            <img style="vertical-align: middle;" src="${ctx}/images/google-chrome.png">
            <label class="check-label">使用谷歌浏览器效果最佳！</label>
            <a class="fz-12 color-blue underline" target="_blank" href="${ctx}/js/Chrome_setup.exe">下载谷歌浏览器</a>
          </span>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>
<script>
    // 点击输入框，高亮外边框
    $('.new-login-input input')
        .focus(function () {
            $(this).parent().addClass('press');
        })
        .blur(function () {
            $(this).parent().removeClass('press');
        });

    //密码是否可见切换
    $('.toggle-password').click(function () {
        var $field = $(this).closest('.new-login-input').find('input')
        var type = $field.attr('type');
        if (type === 'text')
            $field.attr('type', 'password');
        else
            $field.attr('type', 'text');
    })

    // 记住密码
    function mutli_check(el) {
        var check_input = el.querySelector('.check-input');
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
    var sslNeedCheckCode = $("#sslNeedCheckCode").val();
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
    // ssl form
    var sslLoginForm = $("#sslLoginForm");
    $("#sslLoginBtn").click(function () {
        sslLoginForm.submit();
    });
</script>
</body>
</html>
