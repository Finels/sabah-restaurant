<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>编辑用户</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <%@ include file="../common/common-css.jsp" %>
    <%@ include file="../common/common-baseJs.jsp" %>
    <link href="${ctx}/js/tools/My97DatePicker/skin/WdatePicker.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/js/tools/jquery/ztree3/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/js/tools/jquery/validate/jquery.validate.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/js/tools/jquery/multiselect2/css/jquery-ui.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/js/tools/jquery/multiselect2/css/jquery.multiselect.filter.css" rel="stylesheet"
          type="text/css"/>
    <link href="${ctx}/js/tools/jquery/multiselect2/css/jquery.multiselect.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="../common/head.jsp"/>
<div class="public_mat">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td width="157px" valign="top">
                <%--左侧菜单--%>
                <jsp:include page="../system-left.jsp"/>
            </td>
            <td width="10">&nbsp;</td>
            <td valign="top">
                <div class="public_nav"><img src="${ctx}/images/system_ico4.png"/>系统管理 >用户管理</div>
                <div class="public_info">
                    <div class="public_infp_right">
                        <input type="button" class="public_but cacheAddUser" value="返回"/>
                    </div>
                </div>
                <form id="addUserForm" action="<spring:url value='/sysUser/save'/>" method="post">
                    <input type="hidden" id="id" name="id" value="${sysUser.id}"/>

                    <div class="add_title">
                        <h3><img src="${ctx}/images/system_ico5.png"/>账户信息</h3>
                    </div>
                    <div class="add_table">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <th><label for="username">账号：</label></th>
                                <td>
                                    <div class="add_table_mat">
                                        <div class="add_table_con_top">
                                            <c:choose>
                                                <c:when test="${sysUser.id != '' && sysUser.id != null}">
                                                    <input name="username" id="username" type="hidden" class="ignore"
                                                           value="${sysUser.username}"/>
                                                    ${sysUser.username}
                                                </c:when>
                                                <c:otherwise>
                                                    <input name="username" id="username" type="text" placeholder="请输入账号"
                                                           class="public_input1" value="${sysUser.username}"
                                                           maxlength="15"/>
                                                    <span class="red"> &nbsp;* </span>

                                                    <div class="add_table_con_bot">长度为4-15位，支持数字、大小写字母，两者的组合</div>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <c:if test="${sysUser.id == '' || sysUser.id == null}">
                                <tr>
                                    <th><label for="password">密码：</label></th>
                                    <td>
                                        <div class="add_table_mat">
                                            <div class="add_table_con_top">
                                                <div class="add_table_con">
                                                    <input name="password" id="password" type="password"
                                                           placeholder="请输入密码"
                                                           class="public_input1" maxlength="50"/>
                                                    <span class="red"> &nbsp;* </span>
                                                </div>
                                                <div class="add_table_con_table">
                                                    <table border="0" cellspacing="0" cellpadding="0">
                                                        <tr>
                                                            <td id="weakPassword">弱</td>
                                                            <td id="strongPassword">较强</td>
                                                            <td id="veryStrongPassword">强</td>
                                                        </tr>
                                                    </table>
                                                </div>
                                            </div>
                                            <div class="add_table_con_bot">为您的账户安全，建议使用字符+数字等多种不同了性的组合，长度大于6位</div>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <th><label for="passwordConfirm">确认密码：</label></th>
                                    <td>
                                        <div class="add_table_mat">
                                            <div class="add_table_con">
                                                <input name="passwordConfirm" id="passwordConfirm" type="password"
                                                       placeholder="请输入确认密码"
                                                       class="public_input1"/>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                            </c:if>
                        </table>
                    </div>
                    <div class="add_title">
                        <h3><img src="${ctx}/images/system_ico5.png"/>配置信息</h3>
                    </div>
                    <div class="add_table">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <th><label for="groupName">用户组：</label></th>
                                <td colspan="3">
                                    <input type="hidden" name="groupId" id="groupId" value="${sysUser.groupId}"/>
                                    <input type="text" name="groupName" id="groupName" placeholder="请选择用户组"
                                           readonly="true" value="${sysUser.groupName}"
                                           class="public_input1"/>
                                    <span class="red"> &nbsp;* </span>

                                    <div id="groupTree" class="ztree" style="width: 200px;height: 200px;">
                                        <div style="margin:100px 0px 0px 100px;">
                                            <img src="${ctx}/images/task/running.gif"/>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>用户角色：</th>
                                <td colspan="3">
                                    <select name="roles" id="roles" multiple="multiple" style="width: 300px;">
                                    </select>
                                    <input type="hidden" name="roleIds" id="roleIds" value="${sysUser.roleIds}"/>
                                    <input type="hidden" name="roleNames" id="roleNames" value="${sysUser.roleNames}"/>
                                    <input type="hidden" name="roleCodes" id="roleCodes" value="${sysUser.roleCodes}"/>
                                    <span class="red"> &nbsp;* </span>
                                </td>
                            </tr>
                            <%--<tr>
                                <th>基础库：</th>
                                <td colspan="3">
                                    <div class="add_table_mat">
                                        <div class="add_table_con4">
                                            <input name="baseData" id="baseData" type="radio" value="1"
                                                   <c:if test="${sysUser.baseData == 1}">checked="true"</c:if>/>
                                            <fmt:formatDate value="${sysUser.baseExp}" pattern="yyyy-MM-dd"
                                                            var="baseExp"/>
                                            &nbsp;&nbsp;有效期 <input name="baseExp" id="baseExp" type="text"
                                                                   readonly="true" value="${baseExp}"
                                                                   class="public_input Wdate"
                                                                   onfocus="WdatePicker({skin:'whyGreen'})"
                                                                   onkeydown="return false;"/>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>用户类型：</th>
                                <td>
                                    <div class="add_table_mat">
                                        <div class="add_table_con4">
                                            <input name="userFrom" type="radio" value="1" id="userFrom1"
                                                   <c:if test="${sysUser.userFrom == 1}">checked="true"</c:if>/><label
                                                for="userFrom1">平台</label>
                                        </div>
                                        <div class="add_table_con4">
                                            <input name="userFrom" type="radio" value="2" id="userFrom2"
                                                   <c:if test="${sysUser.userFrom == 2}">checked="true"</c:if>/><label
                                                for="userFrom2">工具箱</label>
                                            <span class="red"> &nbsp;* </span>
                                        </div>
                                    </div>
                                </td>
                                <th width="70px">用户状态：</th>
                                <td>
                                    <div class="add_table_mat">
                                        <div class="add_table_con4">
                                            <input name="userStatus" type="radio" value="1" id="userStatus1"
                                                   <c:if test="${sysUser.userStatus == 1}">checked="true"</c:if>/><label
                                                for="userStatus1">正常</label>
                                        </div>
                                        <div class="add_table_con4">
                                            <input name="userStatus" type="radio" value="2" id="userStatus2"
                                                   <c:if test="${sysUser.userStatus == 2}">checked="true"</c:if>/><label
                                                for="userStatus2">试用</label>
                                            <span class="red"> &nbsp;* </span>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th width="70px">审核状态：</th>
                                <td colspan="3">
                                    <div class="add_table_mat">
                                        <input name="preAuditing" value="${sysUser.auditing}" type="hidden"/>
                                        <div class="add_table_con4"><input name="auditing" type="radio" value="0"
                                                                           id="audit0"
                                                                           <c:if test="${sysUser.auditing == 0}">checked="true"</c:if>/>
                                            <label for="audit0">未注册</label>
                                        </div>
                                        <div class="add_table_con4"><input name="auditing" type="radio" value="1"
                                                                           id="audit1"
                                                                           <c:if test="${sysUser.auditing == 1}">checked="true"</c:if>/>
                                            <label for="audit1">待审核</label>
                                        </div>
                                        <div class="add_table_con4"><input name="auditing" type="radio" value="3"
                                                                           id="audit3"
                                                                           <c:if test="${sysUser.auditing == 3}">checked="true"</c:if>/>
                                            <label for="audit3">审核通过</label>
                                        </div>
                                        <div class="add_table_con4"><input name="auditing" type="radio" value="-1"
                                                                           id="audit_1"
                                                                           <c:if test="${sysUser.auditing == -1}">checked="true"</c:if>/>
                                            <label for="audit_1">审核不通过</label>
                                        </div>
                                        <span class="red"> &nbsp;* </span>
                                    </div>
                                </td>
                            </tr>--%>
                            <tr>
                                <th>有效期：</th>
                                <td colspan="3">
                                    <div class="add_table_mat">
                                        <div class="add_table_con">
                                            <fmt:formatDate value="${sysUser.validTimeBegin}" pattern="yyyy-MM-dd"
                                                            var="validTimeBegin"/>
                                            <input name="validTimeBegin" id="validTimeBegin" type="text" readonly="true"
                                                   value="${validTimeBegin}"
                                                   class="public_input Wdate" onfocus="WdatePicker({skin:'whyGreen'})"
                                                   onkeydown="return false;"/> -
                                            <fmt:formatDate value="${sysUser.validTimeEnd}" pattern="yyyy-MM-dd"
                                                            var="validTimeEnd"/>
                                            <input name="validTimeEnd" id="validTimeEnd" type="text" readonly="true"
                                                   value="${validTimeEnd}"
                                                   class="public_input Wdate" onfocus="WdatePicker({skin:'whyGreen'})"
                                                   onkeydown="return false;"/>
                                            &nbsp;&nbsp;不设置，为永不过期
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <%--<tr>
                                <th><label for="uKeySerialNo">UKey序列号：</label></th>
                                <td>
                                    <input name="uKeySerialNo" id="uKeySerialNo" type="text"
                                           value="${sysUser.uKeySerialNo}" class="public_input1"/>
                                </td>
                                <th><label for="maxTask">最大任务数：</label></th>
                                <td>
                                    <input name="maxTask" id="maxTask" type="text" value="${sysUser.maxTask}"
                                           class="public_input"/>
                                </td>
                            </tr>--%>
                        </table>
                    </div>
                    <div class="add_title">
                        <h3><img src="${ctx}/images/system_ico5.png"/>个人信息</h3>
                    </div>
                    <div class="add_table">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <th><label for="name">姓名：</label></th>
                                <td>
                                    <input name="name" id="name" type="text" placeholder="请输入姓名" value="${sysUser.name}"
                                           class="public_input1" maxlength="50"/>
                                    <span class="red"> &nbsp;* </span>
                                </td>
                            </tr>
                            <tr>
                                <th><label for="idCard">身份证：</label></th>
                                <td>
                                    <input name="idCard" id="idCard" type="text" placeholder="请输入身份证"
                                           class="public_input1" value="${sysUser.idCard}"/>
                                </td>
                            </tr>
                            <tr>
                                <th><label for="telephone">联系电话：</label></th>
                                <td>
                                    <input name="telephone" id="telephone" type="text" placeholder="请输入联系电话"
                                           class="public_input1" value="${sysUser.telephone}"/>
                                </td>
                            </tr>
                            <tr>
                                <th><label for="qq">QQ：</label></th>
                                <td>
                                    <input name="qq" id="qq" type="text" placeholder="请输入QQ" value="${sysUser.qq}"
                                           class="public_input1"/>
                                </td>
                            </tr>
                            <tr>
                                <th><label for="email">邮箱：</label></th>
                                <td>
                                    <input name="email" id="email" type="text" placeholder="请输入邮箱"
                                           class="public_input1" value="${sysUser.email}"/>
                                </td>
                            </tr>
                            <tr>
                                <th><label for="company">单位：</label></th>
                                <td>
                                    <input name="company" id="company" type="text" placeholder="请输入单位"
                                           class="public_input1" value="${sysUser.company}"/>
                                </td>
                            </tr>
                            <tr>
                                <th><label for="address">联系地址：</label></th>
                                <td>
                                    <input name="address" id="address" type="text" placeholder="请输入联系地址"
                                           class="public_input1" value="${sysUser.address}"/>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div class="add_but">
                        <input type="button" value="保存" id="submitAddUser" class="public_buts"/>
                        <input type="button" value="取消" class="public_buts1 cacheAddUser"/>
                    </div>
                </form>
            </td>
        </tr>
    </table>
</div>
<jsp:include page="../common/bottom.jsp"/>
<%@ include file="../common/common-js.jsp" %>
<script src="${ctx}/js/tools/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="${ctx}/js/tools/jquery/jquery.bgiframe.js" type="text/javascript"></script>
<script src="${ctx}/js/tools/jquery/validate/jquery.validate.min.js" type="text/javascript"></script>
<script src="${ctx}/js/tools/jquery/validate/jquery.validate.extend.js" type="text/javascript"></script>
<script src="${ctx}/js/tools/jquery/validate/messages_zh.js" type="text/javascript"></script>
<script src="${ctx}/js/tools/jquery/ztree3/js/jquery.ztree.core-3.5.min.js" type="text/javascript"></script>
<script src="${ctx}/js/tools/jquery/ztree3/js/jquery.ztree.exedit-3.5.min.js" type="text/javascript"></script>
<script src="${ctx}/js/tools/jquery/multiselect2/jquery-ui.min.js" type="text/javascript"></script>
<script src="${ctx}/js/tools/jquery/multiselect2/jquery.multiselect.js" type="text/javascript"></script>
<script src="${ctx}/js/tools/jquery/multiselect2/jquery.multiselect.filter.js" type="text/javascript"></script>
<script src="${ctx}/js/tools/pico-popup.js" type="text/javascript"></script>
<script src="${ctx}/js/tools/jquery/jquery.form.js" type="text/javascript"></script>
<script src="${ctx}/js/common-form.js" type="text/javascript"></script>
<script src="${ctx}/js/modules/system/user-edit.js" type="text/javascript"></script>
</body>
</html>
