<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>用户管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <%@ include file="../common/common-css.jsp" %>
    <%@ include file="../common/common-baseJs.jsp" %>
    <link href="${ctx}/js/tools/jquery/ztree3/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="../common/head.jsp"/>
<div class="public_mat" id="userList">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td width="157px" valign="top">
                <%--左侧菜单--%>
                <jsp:include page="../system-left.jsp"/>
            </td>
            <td width="10">&nbsp;</td>
            <td valign="top">
                <div class="public_nav">
                    <%--右侧顶部菜单--%>
                    <img src="${ctx}/images/system_ico4.png"/>用户管理
                    <%--<jsp:include page="../system-right-menu.jsp"/>--%>
                </div>
                <div class="system_info">
                    <input type="hidden" id="searchActionPrefix" value="<spring:url value="/sysUser/"/>"/>

                    <form id="searchForm" action="<spring:url value="/sysUser/main?_secondMenu=${systemMenu}"/>" method="post">
                        <input type="hidden" id="pageNo" name="pageNo" value="${page.pageNo}"/>
                        <input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}"/>
                        <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td><label for="username">账号：</label></td>
                                <td>
                                    <input name="username" id="username" value="${sysUserQuery.username}" type="text"
                                           class="public_input" placeholder="请输入账号"/></td>
                                <%--<td><label for="name">姓名：</label></td>
                                <td>
                                    <input name="name" id="name" value="${sysUserRequest.name}" type="text"
                                           class="public_input" placeholder="请输入姓名"/></td>--%>
                                <td><label for="groupName">用户组：</label></td>
                                <td>
                                    <input type="hidden" name="selectGroupCode" id="selectGroupCode"
                                           value="${sysUserQuery.selectGroupCode}"/>
                                    <input type="text" name="groupName" id="groupName" placeholder="请选择用户组"
                                           readonly="true" value="${sysUserQuery.groupName}"
                                           class="public_input"/>

                                    <div id="groupTree" class="ztree" style="width: 200px;height: 300px;">
                                        <div style="margin:100px 0px 0px 100px;">
                                            <img src="${ctx}/images/task/running.gif"/>
                                        </div>
                                    </div>
                                </td>
                                <td><label for="roleId">角色：</label></td>
                                <td>
                                    <select name="roleId" id="roleId">
                                        <option value="">请选择角色</option>
                                        <c:forEach items="${roleList}" var="role">
                                            <option value="${role.id }"
                                                    <c:if test="${role.id == sysUserQuery.roleId}">selected</c:if>
                                                    title="${role.name}">
                                                <c:choose>
                                                    <c:when test="${fn:length(role.name) > 12}">${fn:substring(role.name, 0, 11)}...</c:when>
                                                    <c:otherwise>${role.name}</c:otherwise>
                                                </c:choose>
                                            </option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td><label for="valid">状态：</label></td>
                                <td>
                                    <select name="valid" id="valid">
                                        <option value="" selected>请选择</option>
                                        <option value="1" <c:if test='${sysUserQuery.valid == 1}'>selected </c:if>>
                                            有效
                                        </option>
                                        <option value="0" <c:if test='${sysUserQuery.valid == 0}'>selected </c:if>>
                                            无效
                                        </option>
                                    </select></td>
                                <%--<td><label for="valid">审核状态：</label></td>
                                <td>
                                    <select name="auditing" id="auditing">
                                        <option value="" selected>请选择</option>
                                        <option value="0" <c:if test='${sysUserRequest.auditing == 0}'>selected </c:if>>
                                            未注册
                                        </option>
                                        <option value="1" <c:if test='${sysUserRequest.auditing == 1}'>selected </c:if>>
                                            待审核
                                        </option>
                                        <option value="3" <c:if test='${sysUserRequest.auditing == 3}'>selected </c:if>>
                                            审核通过
                                        </option>
                                        <option value="-1"
                                                <c:if test='${sysUserRequest.auditing == -1}'>selected </c:if>>
                                            审核不通过
                                        </option>
                                    </select>
                                </td>--%>
                                <td>
                                    <input type="button" id="searchUserBtn" class="public_but" value="查询"/>
                                    <input type="button" id="resetBtn" class="public_but1" value="重置"/>
                                </td>
                            </tr>
                        </table>
                    </form>
                </div>
                <div class="manage_tag">
                    <div class="manage_tag_left">
                        <w:hasPermission roles="sysUser_add">
                            <a href="<spring:url value='/sysUser/preEdit'/>"><img
                                    src="${ctx}/images/system_ico8.png"/></a>
                        </w:hasPermission>
                        <w:hasPermission roles="sysUser_del">
                            <a href="javascript:common.del();"><img src="${ctx}/images/system_ico9.png"/></a>
                        </w:hasPermission>
                    </div>
                    <div class="manage_tag_right">
                        <jsp:include page="../../page/common/pageShow.jsp">
                            <jsp:param name="formId" value="#searchForm"/>
                        </jsp:include>
                    </div>
                </div>
                <div class="manage_table">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <th width="45px;" onclick="common.checkAll();">
                                <b class="public_checkbox checkAll"></b>
                            </th>
                            <th>序号</th>
                            <th>账号</th>
                            <th>姓名</th>
                            <th>用户组</th>
                            <th>角色</th>
                            <%--<th>用户类型</th>--%>
                            <th>状态</th>
                            <%--<th>审核状态</th>--%>
                            <th>操作</th>
                        </tr>
                        <c:choose>
                            <c:when test="${page.totalCount >0}">
                                <c:forEach items="${page.contents}" var="sysUser" varStatus="vs">
                                    <tr>
                                        <c:choose>
                                            <%--<c:when test="${sysUser.createOperator == userSession.id || (userSession.id != sysUser.id && wf:hasAnyPermission('superadmin,admin'))}">--%>
                                            <c:when test="${userSession.id != sysUser.id}">
                                                <td onclick="common.checkOne(${sysUser.id});">
                                                    <b class="public_checkbox" id="check_${sysUser.id}"></b>
                                                </td>
                                            </c:when>
                                            <c:otherwise>
                                                <td>
                                                    <b class="public_checkbox"></b>
                                                </td>
                                            </c:otherwise>
                                        </c:choose>
                                        <td>
                                                ${page.pageSize*(page.pageNo-1) + vs.count}
                                        </td>
                                        <td>
                                            <span title="${sysUser.username}"><w:getValidString
                                                    string="${sysUser.username}" length="10"/></span>
                                        </td>
                                        <td>
                                            <span title="${sysUser.name}"><w:getValidString string="${sysUser.name}"
                                                                                            length="8"/></span>
                                        <td>
                                            <span title="${sysUser.groupName}"><w:getValidString
                                                    string="${sysUser.groupName}" length="8"/></span>
                                        </td>
                                        <td>
                                            <span title="${sysUser.roleNames}"><w:getValidString
                                                    string="${sysUser.roleNames}" length="8"/></span>
                                        <%--<td>
                                            <c:choose>
                                                <c:when test="${sysUser.userFrom == 1}">
                                                    平台
                                                </c:when>
                                                <c:when test="${sysUser.userFrom == 2}">
                                                    工具箱
                                                </c:when>
                                            </c:choose>
                                        </td>--%>
                                        <td>
                                            <w:getUserStatus userFrom="${sysUser.userFrom}"
                                                             userStatus="${sysUser.userStatus}"
                                                             validTimeBegin="${sysUser.validTimeBegin}"
                                                             validTimeEnd="${sysUser.validTimeEnd}"
                                                             auditing="${sysUser.auditing}"/>
                                        </td>
                                        <%--<td>
                                            <c:choose>
                                                <c:when test="${sysUser.auditing == 0}">
                                                    未注册
                                                </c:when>
                                                <c:when test="${sysUser.auditing == 1}">
                                                    <span style="color:red;">待审核</span>
                                                </c:when>
                                                <c:when test="${sysUser.auditing == 3}">
                                                    审核通过
                                                </c:when>
                                                <c:when test="${sysUser.auditing == -1}">
                                                    审核不通过
                                                </c:when>
                                            </c:choose>
                                        </td>--%>
                                        <td>
                                            <w:hasPermission roles="sysUser_password_reset">
                                                <a href="javascript:void(0);" id="${sysUser.id}" class="passwordReset">
                                                    <img src="${ctx}/images/system_ico27.png" title="重置密码"/></a>
                                            </w:hasPermission>
                                                <%--不能修改自己--%>
                                            <c:choose>
                                                <c:when test="${userSession.id != sysUser.id}">
                                                    <w:hasPermission roles="sysUser_edit">
                                                        <a href="<spring:url value='/sysUser/preEdit?id=${sysUser.id}'/>">
                                                            <img src="${ctx}/images/system_ico24.png" title="修改"/></a>
                                                    </w:hasPermission>
                                                    <w:hasPermission roles="sysUser_del">
                                                        <a href="javascript:common.del(${sysUser.id});">
                                                            <img src="${ctx}/images/system_ico13.png" title="删除"/></a>
                                                    </w:hasPermission>
                                                </c:when>
                                                <c:otherwise>
                                                    <w:hasPermission roles="sysUser_edit">
                                                        <a href="javascript:void(0);" style="cursor:default;">
                                                            <img src="${ctx}/images/system_ico23.png" title="修改"/></a>
                                                    </w:hasPermission>
                                                    <w:hasPermission roles="sysUser_del">
                                                        <a href="javascript:void(0);" style="cursor:default;">
                                                            <img src="${ctx}/images/system_ico22.png" title="删除"/></a>
                                                    </w:hasPermission>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan="12">
                                        暂无数据记录!
                                    </td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                    </table>
                </div>

                <jsp:include page="../common/pagination.jsp">
                    <jsp:param name="formId" value="#searchForm"/>
                </jsp:include>

            </td>
        </tr>
    </table>
</div>
<jsp:include page="../common/bottom.jsp"/>
<%@ include file="../common/common-js.jsp" %>
<script src="${ctx}/js/tools/jquery/jquery.bgiframe.js" type="text/javascript"></script>
<script src="${ctx}/js/tools/jquery/ztree3/js/jquery.ztree.core-3.5.min.js" type="text/javascript"></script>
<script src="${ctx}/js/tools/pico-popup.js" type="text/javascript"></script>
<script src="${ctx}/js/modules/system/user-main.js" type="text/javascript"></script>
</body>
</html>
