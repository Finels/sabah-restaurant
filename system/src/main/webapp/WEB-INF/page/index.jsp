<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="common/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTDHTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>系统桌面</title>
    <link href="${ctx}/css/public.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/css/normalize.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/css/animations.css" rel="stylesheet" type="text/css"/>
    <%@ include file="common/common-css.jsp" %>
    <link href="${ctx}/css/style.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/js/tools/jquery/validate/jquery.validate.css" rel="stylesheet" type="text/css"/>
    <%@ include file="common/common-baseJs.jsp" %>
    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="${ctx}/js/tools/html5shiv.min.js" type="text/javascript"></script>
    <![endif]-->
    <%--<%@ include file="common/common-baseJs.jsp" %>--%>
    <!--[if IE 8]>
    <style>
        .metro_add_block_1 {
            float: left;
            width: 33.3%;
        }

        .metro_add_block_2 {
            float: left;
            width: 50%;
        }

        .metro_block_1:hover {
            background: #15192B;
        }

        .metro_block_2:hover {
            background: #15192B;
        }

    </style>
    <![endif]-->
</head>
<body style="background: url(${ctx}/images/main_bg_1.jpg) center 0;background-attachment: fixed;
        background-size: cover; min-width: 1168px;">
<!-- 背景流星画板 -->
<%--<canvas id="bgCanvas" class="bgCanvas"></canvas>--%>
<%--<div class="animate_bg">
    <img class="main_hill_1 floating" src="${ctx}/images/main_hill_1.png">
    <img class="main_hill_2 floating" src="${ctx}/images/main_hill_2.png">
    <img class="main_ball_1 pulse" src="${ctx}/images/main_ball_1.png">
    <img class="main_ball_2 pulse" src="${ctx}/images/main_ball_2.png">
    <img class="main_ball_3 pulse" src="${ctx}/images/main_ball_3.png">
    <img class="main_ball_4 pulse" src="${ctx}/images/main_ball_4.png">
</div>--%>

<div class="main_header">
    <input type="hidden" id="maintenanceBaseUrl" value="${maintenanceBaseUrl}">
    <a class="logo_link" href="javascript:void(0);">
        <img class="logo_img" src="${ctx}/images/logo/main_logo_${env}.png">
    </a>
    <div class="user_center">
        <%--<div class="action_block">
            <a href="javascript:void(0)" id="pxzqBtn" target="_blank">
                <img src="${ctx}/images/trainingArea.png">
                <span>培训专区</span>
            </a>
        </div>--%>
        <div class="action_block">
            <div class="user_box">
               <span>
                 <a href="javascript:void(0);">
                     <img src="${ctx}/images/main_user_ico.png">
                     <span class="user_name" title="${username}">${username}</span>
                 </a>
               </span>
                <%--<span>
                  <a class="user_message" href="#">
                      <img src="${ctx}/images/main_3.png">
                      <i class="message_tip"></i>
                  </a>
                </span>--%>
            </div>
        </div>
        <div class="action_block">
            <div style="position: relative; padding: 0 10px;">
             <span>
               <a class="user_message" href="javascript:void(0);" id="notice">
                   <img src="${ctx}/images//bell.png">
                   <%--<c:if test="${countNoReadByUser > 0}">
                       <i class="message_tip"></i>
                   </c:if>--%>
                   <i class="message_tip" id="messageTip" style="display: none"></i>
               </a>
             </span>
                <!-- 弹出 -->
                <div id="noticeListDiv" class="nav-notice-list-box"
                     style="position: absolute; top: 70px; right: -25px; display: none">
                    <div class="nav-notice-list-wrap">

                        <!--
                        <ul class="bdp-tab-wrap clearfix notice-tab">
                          <li class="bdp-tab-item notice-tab-item bdp-tab-active">
                            系统
                            <span class="unread-len">9</span>
                          </li>
                          <li class="bdp-tab-item notice-tab-item">
                            预警
                          </li>
                          <li class="bdp-tab-item notice-tab-item">
                            干货
                            <span class="unread-len">12</span>
                          </li>
                        </ul>
                      -->

                        <div class="notice-list-scroll">
                            <ul class="notice-list">
                                <c:forEach items="${messageList}" var="message" varStatus="vs">
                                    <li class="notice-item-wrap clearfix notice-unread">
                                        <div class="notice-item">
                                            <div class="notice-html too-long">
                                                <div><p>${message.content}</p></div>
                                            </div>
                                            <div class="notice-sendtime"><fmt:formatDate value="${message.createTime}"
                                                                                         pattern="yyyy-MM-dd HH:mm:ss"/></div>
                                        </div>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>

                        <div class="nav-notice-operate clearfix">
                            <div class="read-all">
                                全部标为已读
                            </div>
                            <a href="<spring:url value="/message/list"/>" target="_blank" class="see-all"></a>
                            <a href="javascript:void(0)" id="noticeMoreBtn" target="_blank" class="see-all">
                                查看全部 &gt;
                            </a>
                        </div>
                    </div>
                </div>
                <!-- 弹出 -->
            </div>
        </div>
        <div class="action_block" onclick="logout();">
            <a href="javascript:void(0);">
                <img src="${ctx}/images/main_2.png">
                <span>退出</span>
            </a>
        </div>
    </div>
</div>
<!-- 主要内容 -->
<div class="main_metro">
    <!-- 左边的方块格 -->
    <div class="metro_box_1">
        <c:forEach items="${systemResources}" var="resource">
            <c:if test="${resource.uniqueCode != 'netImage'}">
                <a href="<spring:url value="${resource.url}?uniqueCode=${resource.uniqueCode}"/>" target="_blank"
                   class="metro_block_1">
                    <div class="metro_content_1">
                        <img class="metro_image" src="${ctx}/images/logo/index/${resource.icon}">
                        <div class="metro_title_1">${resource.name}</div>
                    </div>
                </a>
                <%--<c:choose>
                    <c:when test="${userId eq 178}">
                        <c:choose>
                            <c:when test="${resource.uniqueCode eq 'analyze'}">
                                <a href="<spring:url value="${resource.url}"/>" target="_blank" class="metro_block_1">
                                    <div class="metro_content_1">
                                        <img class="metro_image" src="${ctx}/images/${resource.icon}">
                                        <div class="metro_title_1">${resource.name}</div>
                                    </div>
                                </a>
                            </c:when>
                            <c:otherwise>
                                <a href="javascript:void(0);" target="_blank" class="metro_block_1 noPermission">
                                    <div class="metro_content_1">
                                        <img class="metro_image" src="${ctx}/images/${resource.icon}">
                                        <div class="metro_title_1">${resource.name}</div>
                                    </div>
                                </a>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        <a href="<spring:url value="${resource.url}"/>" target="_blank" class="metro_block_1">
                            <div class="metro_content_1">
                                <img class="metro_image" src="${ctx}/images/${resource.icon}">
                                <div class="metro_title_1">${resource.name}</div>
                            </div>
                        </a>
                    </c:otherwise>
                </c:choose>--%>
            </c:if>
        </c:forEach>
        <%--<a href="#" class="metro_block_1">
            <div class="metro_content_1">
                <img class="metro_image" src="${ctx}/images/main_5.png">
                <div class="metro_title_1">主体画像</div>
            </div>
        </a>
        <a href="#" class="metro_block_1">
            <div class="metro_content_1">
                <img class="metro_image" src="${ctx}/images/main_6.png">
                <div class="metro_title_1">关联分析</div>
            </div>
        </a>
        <a href="#" class="metro_block_1">
            <div class="metro_content_1">
                <img class="metro_image" src="${ctx}/images/main_7.png">
                <div class="metro_title_1">企业画像</div>
            </div>
        </a>
        <a href="#" class="metro_block_1">
            <div class="metro_content_1">
                <img class="metro_image" src="${ctx}/images/main_8.png">
                <div class="metro_title_1">线索发现</div>
            </div>
        </a>
        <a href="#" class="metro_block_1">
            <div class="metro_content_1">
                <img class="metro_image" src="${ctx}/images/main_9.png">
                <div class="metro_title_1">系统管理</div>
            </div>
        </a>
        <a href="#" class="metro_block_1">
            <div class="metro_content_1">
                <img class="metro_image" src="${ctx}/images/main_10.png">
                <div class="metro_title_1">工具</div>
            </div>
        </a>
        <a href="#" class="metro_block_1 metro_add_block_1">
            <div class="metro_content_1">
                <img class="metro_image" src="${ctx}/images/main_add.png">
            </div>
        </a>--%>
        <div style="clear: both;"></div>
    </div>

    <!-- 右边的方块格 -->
    <div class="metro_box_2">
        <c:forEach items="${topResources}" var="resource" varStatus="index">
            <c:choose>
                <c:when test="${topResources.size()%2 == 1 && index.last}">
                    <a href="<spring:url value="${resource.url}?uniqueCode=${resource.uniqueCode}"/>" target="_blank"
                       class="metro_block_2 metro_add_block_2">
                        <div class="metro_content_2">
                            <img class="metro_image" src="${ctx}/images/logo/index/${resource.icon}">
                            <div class="metro_title_2">${resource.name}</div>
                        </div>
                    </a>
                </c:when>
                <c:otherwise>
                    <a href="<spring:url value="${resource.url}?uniqueCode=${resource.uniqueCode}"/>" target="_blank"
                       class="metro_block_2">
                        <div class="metro_content_2">
                            <img class="metro_image" src="${ctx}/images/logo/index/${resource.icon}">
                            <div class="metro_title_2">${resource.name}</div>
                        </div>
                    </a>
                </c:otherwise>
            </c:choose>
        </c:forEach>

        <%--<a href="#" class="metro_block_2">
            <div class="metro_content_2">
                <img class="metro_image" src="${ctx}/images/main_12.png">
                <div class="metro_title_2">FS-6000</div>
            </div>
        </a>
        <a href="#" class="metro_block_2">
            <div class="metro_content_2">
                <img class="metro_image" src="${ctx}/images/main_13.png">
                <div class="metro_title_2">执法办案系统</div>
            </div>
        </a>
        <a href="#" class="metro_block_2">
            <div class="metro_content_2">
                <img class="metro_image" src="${ctx}/images/main_14.png">
                <div class="metro_title_2">公安信息网</div>
            </div>
        </a>
        <a href="#" class="metro_block_2">
            <div class="metro_content_2">
                <img class="metro_image" src="${ctx}/images/main_15.png">
                <div class="metro_title_2">经侦信息网</div>
            </div>
        </a>
        <a href="#" class="metro_block_2">
            <div class="metro_content_2">
                <img class="metro_image" src="${ctx}/images/main_16.png">
                <div class="metro_title_2">手机画像</div>
            </div>
        </a>
        <a href="#" class="metro_block_2">
            <div class="metro_content_2">
                <img class="metro_image" src="${ctx}/images/main_17.png">
                <div class="metro_title_2">警综平台</div>
            </div>
        </a>
        <a href="#" class="metro_block_2 metro_add_block_2">
            <div class="metro_content_2">
                <img class="metro_image" src="${ctx}/images/main_add.png">
            </div>
        </a>--%>
        <div style="clear: both;"></div>
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
//        $("#peiXinArea").click(function () {
        <%--dialog.openIFrame("培训专区", '<spring:url value="/"/>' + 'training/list');--%>
//        });

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
        if ('onOpen' != openWriteUserInfo) {
            layer.open({
                id: "_openPageByDom",
                type: 1,
                title: "填写用户信息",
                content: $("#writeUserInfo"),
                maxmin: true,
                moveOut: true,
                closeBtn: 0,
                scrollbar: false,
                area: ['520px', '400px']
//                end: function () {  //窗口销毁后触发
//                    dom.hide();
//                }
            });
        }

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