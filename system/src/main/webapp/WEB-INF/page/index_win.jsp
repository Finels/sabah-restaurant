<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="common/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTDHTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>系统桌面</title>
    <link href="${ctx}/css/normalize.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/css/animations.css" rel="stylesheet" type="text/css"/>
    <%@ include file="common/common-css.jsp" %>
    <link href="${ctx}/css/style.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/css/winStyle.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/js/tools/jquery/validate/jquery.validate.css" rel="stylesheet" type="text/css"/>
    <%@ include file="common/common-baseJs.jsp" %>
    <script src="${ctx}/js/tools/html5shiv.min.js" type="text/javascript"></script>
    <style>
        body {
            background: url(${ctx}/images/win/bg.jpg) no-repeat center center fixed;
            -webkit-background-size: cover;
            -moz-background-size: cover;
            -o-background-size: cover;
            background-size: cover;
            height: 0;
        }

        .metro-orange {
            background-image: linear-gradient(50deg, rgba(255, 119, 28, .5) 0%, rgba(255, 176, 28, .5) 100%);
        }

        .metro-pink {
            background-image: linear-gradient(50deg, rgba(227, 33, 65, .5) 0%, rgba(227, 34, 124, .5) 100%);
        }

        .metro-green {
            background-image: linear-gradient(50deg, rgba(61, 184, 8, .5) 0%, rgba(104, 184, 7, .5) 100%);
        }

        .metro-green2 {
            background-image: linear-gradient(50deg, rgba(0, 168, 184, .5) 0%, rgba(0, 184, 147, .5) 100%);
        }

        .metro-blue {
            background-image: linear-gradient(50deg, rgba(23, 88, 230, .5) 0%, rgba(23, 140, 229, .5) 100%);
        }

        .metro-purple {
            background-image: linear-gradient(50deg, rgba(91, 25, 255, .5) 0%, rgba(144, 18, 255, .5) 100%);
        }
    </style>
</head>
<body>
<div class="main_header" style="z-index: 10">
    <input type="hidden" id="maintenanceBaseUrl" value="${maintenanceBaseUrl}">
    <!-- 切换 -->
    <%--<div style="position: absolute;top: 5px;left: 50%;transform: translateX(-50%);">
        <div class="nav-panel-1">
            <ul>
                <li>
                    <a class="nav-btn press" href="javascript:void(0);">
                        <img class="nav-img nav-img-default" src="${ctx}/images/win/win.png">
                        <img class="nav-img nav-img-hover" src="${ctx}/images/win/win-blue.png">
                        <span>桌面</span>
                    </a>
                </li>
                <li>
                    <a class="nav-btn" href="javascript:void(0);">
                        <img class="nav-img nav-img-default" src="${ctx}/images/win/help.png">
                        <img class="nav-img nav-img-hover" src="${ctx}/images/win/help-blue.png">
                        <span>帮助</span>
                    </a>
                </li>
            </ul>
        </div>
    </div>--%>
    <c:if test="${env != 'xm'}">
        <a class="logo_link" href="javascript:void(0);">
            <img class="logo_img" src="${ctx}/images/logo/main_logo_${env}.png">
        </a>
    </c:if>
    <div class="user_center">
        <%--<div class="action_block">--%>
        <%--<c:if test="${env != 'xm'}">--%>
        <%--<span>--%>
        <%--<a href="<spring:url value="${maintenanceBaseUrl}#/trainingClient"/>" target="_blank">--%>
        <%--<img src="${ctx}/images/logo/index/training.png" style="width:28px;height: 32px; ">--%>
        <%--<span>培训专区</span>--%>
        <%--</a>--%>
        <%--</span>--%>
        <%--</c:if>--%>
        <%--</div>--%>
        <div class="action_block">
            <div class="user_box">
               <span>
                 <a href="javascript:void(0);">
                     <img src="${ctx}/images/main_user_ico.png">
                     <span class="user_name" title="${username}">欢迎您，${username}</span>
                 </a>
               </span>
            </div>
        </div>
        <%--<div class="action_block">--%>
        <%--<c:if test="${env != 'xm'}">--%>
        <%--<div style="position: relative; padding: 0 10px;">--%>
        <%--<span>--%>
        <%--<a class="user_message" href="javascript:void(0);" id="notice">--%>
        <%--<img src="${ctx}/images/bell.png">--%>
        <%--<i class="message_tip" id="messageTip" style="display: none"></i>--%>
        <%--</a>--%>
        <%--</span>--%>
        <%--<!-- 弹出 -->--%>
        <%--<div id="noticeListDiv" class="nav-notice-list-box"--%>
        <%--style="position: absolute; top: 70px; right: -25px; display: none">--%>
        <%--<div class="nav-notice-list-wrap">--%>
        <%--<div class="notice-list-scroll">--%>
        <%--<ul class="notice-list">--%>
        <%--<c:forEach items="${messageList}" var="message" varStatus="vs">--%>
        <%--<li class="notice-item-wrap clearfix notice-unread">--%>
        <%--<div class="notice-item">--%>
        <%--<div class="notice-html too-long">--%>
        <%--<div><p>${message.content}</p></div>--%>
        <%--</div>--%>
        <%--<div class="notice-sendtime"><fmt:formatDate--%>
        <%--value="${message.createTime}"--%>
        <%--pattern="yyyy-MM-dd HH:mm:ss"/></div>--%>
        <%--</div>--%>
        <%--</li>--%>
        <%--</c:forEach>--%>
        <%--</ul>--%>
        <%--</div>--%>

        <%--<div class="nav-notice-operate clearfix">--%>
        <%--<div class="read-all">--%>
        <%--全部标为已读--%>
        <%--</div>--%>
        <%--<a href="<spring:url value="/message/list"/>" target="_blank" class="see-all"></a>--%>
        <%--<a href="javascript:void(0)" id="noticeMoreBtn" target="_blank" class="see-all">--%>
        <%--查看全部 &gt;--%>
        <%--</a>--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--<!-- 弹出 -->--%>
        <%--</div>--%>
        <%--</c:if>--%>
        <%--</div>--%>
        <div class="action_block" onclick="logout();">
            <a href="javascript:void(0);">
                <img src="${ctx}/images/main_2.png">
                <span>退出</span>
            </a>
        </div>
    </div>
</div>
<!-- 主要内容 -->
<div class="metro-container" style="margin-top: 140px; margin-bottom: 100px; white-space: nowrap;">
    <!-- 右下角联系方式 -->
    <%--<div style="position: fixed; bottom: 45px; right: 50px;">
        <ul class="info-contact">
            <li>
                <img src="${ctx}/images/win/phone.png">
                <span>联系我们</span>
            </li>
            <li>
                <img src="${ctx}/images/win/message.png">
                <span>反馈意见 </span>
            </li>
        </ul>
    </div>--%>

    <!-- 左边的方块格 -->
    <div class="metro-box" style="max-width: 840px;">
        <c:forEach items="${topResources}" var="resource">
            <c:if test="${resource.block == 1}">
                <c:choose>
                    <c:when test="${fn:contains(resource.url, '?')}">
                        <c:choose>
                            <c:when test="${fn:contains(resource.url, '#')}">
                                <a id="${resource.uniqueCode}"
                                   href="<spring:url value="${fn:substringBefore(resource.url,'#')}&uniqueCode=${resource.uniqueCode}#${fn:substringAfter(resource.url,'#')}"/>"
                                   target="_blank"
                                   class="${resource.styleClass}">
                                    <div class="metro-content">
                                        <img class="metro-image" src="${ctx}/images/win/${resource.icon}">
                                        <div class="metro-title">${resource.name}</div>
                                    </div>
                                </a>
                            </c:when>
                            <c:otherwise>
                                <a id="${resource.uniqueCode}"
                                   href="<spring:url value="${resource.url}&uniqueCode=${resource.uniqueCode}"/>"
                                   target="_blank"
                                   class="${resource.styleClass}">
                                    <div class="metro-content">
                                        <img class="metro-image" src="${ctx}/images/win/${resource.icon}">
                                        <div class="metro-title">${resource.name}</div>
                                    </div>
                                </a>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        <c:choose>
                            <c:when test="${fn:contains(resource.url, '#')}">
                                <a id="${resource.uniqueCode}"
                                   href="<spring:url value="${fn:substringBefore(resource.url,'#')}?uniqueCode=${resource.uniqueCode}#${fn:substringAfter(resource.url,'#')}"/>"
                                   target="_blank"
                                   class="${resource.styleClass}">
                                    <div class="metro-content">
                                        <img class="metro-image" src="${ctx}/images/win/${resource.icon}">
                                        <div class="metro-title">${resource.name}</div>
                                    </div>
                                </a>
                            </c:when>
                            <c:otherwise>
                                <a id="${resource.uniqueCode}"
                                   href="<spring:url value="${resource.url}?uniqueCode=${resource.uniqueCode}"/>"
                                   target="_blank"
                                   class="${resource.styleClass}">
                                    <div class="metro-content">
                                        <img class="metro-image" src="${ctx}/images/win/${resource.icon}">
                                        <div class="metro-title">${resource.name}</div>
                                    </div>
                                </a>
                            </c:otherwise>
                        </c:choose>
                    </c:otherwise>
                </c:choose>
            </c:if>
        </c:forEach>
    </div>

    <!-- 右边的方块格 -->
    <div class="metro-box" style="margin-left: 40px; max-width: 630px;">
        <c:set var="rightResourceNum" value="0"/>
        <c:forEach items="${topResources}" var="resource">
            <c:if test="${resource.block == 2}">
                <c:set var="rightResourceNum" value="${rightResourceNum+1}"/>
                <c:choose>
                    <c:when test="${fn:contains(resource.url, '?')}">
                        <c:choose>
                            <c:when test="${fn:contains(resource.url, '#')}">
                                <a id="${resource.uniqueCode}"
                                   href="<spring:url value="${fn:substringBefore(resource.url,'#')}&uniqueCode=${resource.uniqueCode}#${fn:substringAfter(resource.url,'#')}"/>"
                                   target="_blank"
                                   class="${resource.styleClass}">
                                    <div class="metro-content">
                                        <img class="metro-image" src="${ctx}/images/win/${resource.icon}">
                                        <div class="metro-title">${resource.name}</div>
                                    </div>
                                </a>
                            </c:when>
                            <c:otherwise>
                                <a id="${resource.uniqueCode}"
                                   href="<spring:url value="${resource.url}&uniqueCode=${resource.uniqueCode}"/>"
                                   target="_blank"
                                   class="${resource.styleClass}">
                                    <div class="metro-content">
                                        <img class="metro-image" src="${ctx}/images/win/${resource.icon}">
                                        <div class="metro-title">${resource.name}</div>
                                    </div>
                                </a>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        <c:choose>
                            <c:when test="${fn:contains(resource.url, '#')}">
                                <a id="${resource.uniqueCode}"
                                   href="<spring:url value="${fn:substringBefore(resource.url,'#')}?uniqueCode=${resource.uniqueCode}#${fn:substringAfter(resource.url,'#')}"/>"
                                   target="_blank"
                                   class="${resource.styleClass}">
                                    <div class="metro-content">
                                        <img class="metro-image" src="${ctx}/images/win/${resource.icon}">
                                        <div class="metro-title">${resource.name}</div>
                                    </div>
                                </a>
                            </c:when>
                            <c:otherwise>
                                <a id="${resource.uniqueCode}"
                                   href="<spring:url value="${resource.url}?uniqueCode=${resource.uniqueCode}"/>"
                                   target="_blank"
                                   class="${resource.styleClass}">
                                    <div class="metro-content">
                                        <img class="metro-image" src="${ctx}/images/win/${resource.icon}">
                                        <div class="metro-title">${resource.name}</div>
                                    </div>
                                </a>
                            </c:otherwise>
                        </c:choose>
                    </c:otherwise>
                </c:choose>
                <c:if test="${rightResourceNum%2==0}">
                    <div></div>
                </c:if>
            </c:if>
        </c:forEach>
    </div>

    <style>
        a, h1, h2, h3, h4, h5, h6, input, select, button, option, textarea, optgroup {
            font-family: inherit;
            outline: none;
        }
    </style>

    <div id="writeUserInfo" style="padding: 20px;display: none;">
        <div class="window">
            <form id="editForm" action="<spring:url value='/sysUser/update/info'/>" method="post">
                <input type="hidden" id="id" name="id" value="${user.id}">

                <div class="window_table">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <th>
                                <label>用户名：</label>
                            </th>
                            <td>
                                <span>${user.username}</span>
                            </td>
                        </tr>
                        <tr>
                            <th>
                                <label for="name">姓名：</label>
                            </th>
                            <td>
                                <input name="name" id="name" value="${user.name}" type="text"
                                       class="public_input1" maxlength="50"/>
                                <span class="red"> &nbsp;* </span>
                            </td>
                        </tr>
                        <tr>
                            <th><label for="idCard">身份证：</label></th>
                            <td>
                                <input name="idCard" id="idCard" type="text"
                                       class="public_input1" maxlength="30" value="${user.idCard}"/>
                                <span class="red"> &nbsp;* </span>
                            </td>
                        </tr>
                        <tr>
                            <th><label for="sex">性别：</label></th>
                            <td>
                                <select id="sex" name="sex">
                                    <option value=""
                                            <c:if test="${user.sex == null || user.sex == ''}">selected="selected"</c:if>>
                                        请选择性别
                                    </option>
                                    <option value="M"
                                            <c:if test="${user.sex == 'M'}">selected="selected"</c:if>>男
                                    </option>
                                    <option value="F" <c:if test="${user.sex == 'F'}">selected="selected"</c:if>>女
                                    </option>
                                </select>
                                <span class="red"> &nbsp;* </span>
                            </td>
                        </tr>
                        <tr>
                            <th><label for="telephone">手机：</label></th>
                            <td>
                                <input name="telephone" id="telephone" type="text"
                                       class="public_input1" maxlength="20" value="${user.telephone}"/>
                                <span class="red"> &nbsp;* </span>
                            </td>
                        </tr>
                        <%--<tr>
                            <th><label for="company">单位：</label></th>
                            <td>
                                <input name="company" id="company" type="text"
                                       class="public_input1" maxlength="100" value="${user.company}"/>
                                <span class="red"> &nbsp;* </span>
                            </td>
                        </tr>
                        <tr>
                            <th><label for="job">职位：</label></th>
                            <td>
                                <input name="job" id="job" type="text"
                                       class="public_input1" maxlength="50" value="${user.job}"/>
                                <span class="red"> &nbsp;* </span>
                            </td>
                        </tr>--%>
                    </table>
                </div>
            </form>
        </div>
        <div class="window_bar">
            <input type="button" class="window_but" id="saveBtn" value="保存"/>
        </div>
    </div>
</div>
<%--<script src="${ctx}/js/metro.js"></script>--%>
<script src="${ctx}/js/tools/layui/layui.all.js" type="text/javascript"></script>
<script src="${ctx}/js/tools/jquery/validate/jquery.validate.min.js" type="text/javascript"></script>
<script src="${ctx}/js/tools/jquery/validate/jquery.validate.extend.js" type="text/javascript"></script>
<script src="${ctx}/js/tools/jquery/validate/messages_zh.js" type="text/javascript"></script>
<script src="${ctx}/js/tools/jquery/jquery.form.js" type="text/javascript"></script>
<script src="${ctx}/js/common-form.js" type="text/javascript"></script>
<script src="${ctx}/js/common_new.js" type="text/javascript"></script>
<script type="text/javascript">
    layer.config({
        //skin: 'layui-layer-molv'
    });

    function logout() {
        layer.confirm("确定要退出系统吗？", {icon: 0, title: '提示'}, function (index) {
            window.location.href = '<spring:url value="/"/>' + 'auth/logout';
            layer.close(index);
        });
    }

    $(function () {
        $(".noPermission").click(function () {
            layer.alert("没有该模块权限！");
        });
        var countNoReadByUser = ${countNoReadByUser};
        if (countNoReadByUser > 0) {
            $("#messageTip").show();
            $("#noticeListDiv").show();
        } else {
            $("#messageTip").hide();
            $("#noticeListDiv").hide();
        }


        var openWriteUserInfo = '${openWriteUserInfo}';
        //暂时去掉用户首次登陆填写信息
//         if ('onOpen' != openWriteUserInfo) {
//             layer.open({
//                 id: "_openPageByDom",
//                 type: 1,
//                 title: "填写用户信息",
//                 content: $("#writeUserInfo"),
//                 maxmin: true,
//                 moveOut: true,
//                 closeBtn: 0,
//                 scrollbar: false,
//                 area: ['520px', '400px']
// //                end: function () {  //窗口销毁后触发
// //                    dom.hide();
// //                }
//             });
//         }

        $("#saveBtn").on("click", function () {
            $("#editForm").submit();
        });

        $("#editForm").validate({
            submitHandler: function (form) {
                $(form).ajaxSubmit({
                    type: "post",
                    dataType: "json",
                    success: function (result) {
                        if (result.code == 200) {
                            window.location.reload();
                        } else {
                            layer.alert("保存失败！");
                        }
                    },
                    error: function (result) {
                        layer.alert("保存失败！");
                    }
                });
            },
            rules: {
                name: {
                    required: true
                },
                idCard: {
                    required: true,
                    idCard: true
                },
                sex: {
                    required: true
                },
                telephone: {
                    required: true,
                    phoneOrMobile: true
                },
                company: {
                    required: true
                },
                job: {
                    required: true
                }
            },
            messages: {
                name: {
                    required: "请输入姓名！"
                },
                idCard: {
                    required: "请输入身份证！"
                },
                sex: {
                    required: "请选择性别！"
                },
                telephone: {
                    required: "请输入手机！"
                },
                company: {
                    required: "请输入单位！"
                },
                job: {
                    required: "请输入职位！"
                }
            }
        });


        $("#notice").on("click", function (e) {
            e.stopPropagation();
            $("#noticeListDiv").toggle();
        });

        $("body").click(function () {
            $("#noticeListDiv").hide();
        });

        $("#noticeListDiv").click(function (e) {
            e.stopPropagation();
        });

        $(".read-all").on("click", function () {
            $.ajax({
                url: '${ctx}/message/allPurpose',
                type: "post",
                dataType: "json",
                success: function (result) {
                    if (result.code == 200) {
                        //window.location.reload();
                        $("#messageTip").hide();
                        $("#noticeListDiv").hide();
                    } else {
                        layer.alert("全部标为已读失败！");
                    }
                },
                error: function (result) {
                    layer.alert("全部标为已读出错！");
                }
            });
        });
//        $("#pxzqBtn").click(function () {
//            var url = $("#maintenanceBaseUrl").val() + '/index_pxgl_dev.html';
//            window.open(url);
//        });
        $("#noticeMoreBtn").click(function () {
            var url = $("#maintenanceBaseUrl").val() + '#/messageClient';
//            var url = $("#maintenanceBaseUrl").val() + '/index_gggl_dev.html';
            window.open(url);

        });

    })
</script>
</body>
</html>