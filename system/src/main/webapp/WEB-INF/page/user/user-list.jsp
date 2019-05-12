<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>用户列表</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <%@ include file="../common/common-css.jsp" %>
    <%@ include file="../common/common-baseJs.jsp" %>
</head>
<body>
<div class="manage_table">
    <form id="searchForm" action="<spring:url value="/sysUser/list"/>" method="POST">
        <input type="hidden" id="selectGroupCode" name="selectGroupCode" value="${sysUserRequest.selectGroupCode}"/>
        <input type="hidden" id="pageNo" name="pageNo" value="${page.pageNo}"/>
        <input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}"/>
    </form>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <th>序号</th>
            <th>账号</th>
            <th>用户</th>
            <th>用户组</th>
            <th>角色</th>
            <%--<th>用户类型</th>--%>
            <th>状态</th>
            <th>联系方式</th>
        </tr>
        <c:choose>
            <c:when test="${page.totalCount > 0}">
                <c:forEach items="${page.contents}" var="sysUser" varStatus="vs">
                    <tr>
                        <td>
                                ${page.pageSize*(page.pageNo-1) + vs.count}
                        </td>
                        <td>
                            <span title="${sysUser.username}"><w:getValidString string="${sysUser.username}"
                                                                               length="8"/></span>
                        </td>
                        <td>
                            <span title="${sysUser.name}"><w:getValidString string="${sysUser.name}" length="8"/></span>
                        <td>
                            <span title="${sysUser.groupName}"><w:getValidString string="${sysUser.groupName}"
                                                                                 length="8"/></span>
                        </td>
                        <td>
                            <span title="${sysUser.roleNames}"><w:getValidString string="${sysUser.roleNames}"
                                                                                 length="8"/></span>
                        </td>
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
                        <td>${sysUser.telephone}</td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr>
                    <td colspan="11">
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
<%@ include file="../common/common-js.jsp" %>
</body>
</html>
