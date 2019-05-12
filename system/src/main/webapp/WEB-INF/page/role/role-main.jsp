<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>角色管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <%@ include file="../common/common-css.jsp" %>
    <%@ include file="../common/common-baseJs.jsp" %>
</head>
<body>
<jsp:include page="../common/head.jsp"/>
<div class="public_mat" id="roleList">
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
                        <img src="${ctx}/images/system_ico4.png"/>角色管理
                    <%--<jsp:include page="../system-right-menu.jsp"/>--%>
                </div>
                <div class="system_info">
                    <input type="hidden" id="searchActionPrefix" value="<spring:url value="/sysRole/"/>"/>

                    <form id="searchForm" action="<spring:url value="/sysRole/main?_secondMenu=${systemMenu}"/>" method="post">
                        <input type="hidden" id="pageNo" name="pageNo" value="${page.pageNo}"/>
                        <input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}"/>
                        <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td><label for="name">角色名称：</label>
                                    <input name="name" id="name" value="${name}" type="text"
                                           class="public_input" placeholder="角色名称"/>
                                </td>
                                <td><input type="button" id="searchRoleBtn" class="public_but" value="查询"/></td>
                            </tr>
                        </table>
                    </form>
                </div>
                <div class="manage_tag">
                    <div class="manage_tag_left">
                        <w:hasPermission roles="sysRole_add">
                            <a href="javascript:void(0);" id="addRoleBtn"><img src="${ctx}/images/system_ico8.png"/></a>
                        </w:hasPermission>
                        <w:hasPermission roles="sysRole_del">
                            <a href="javascript:common.del();">
                                <img src="${ctx}/images/system_ico9.png"/></a>
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
                            <th>角色名称</th>
                            <th>创建用户</th>
                            <th>备注</th>
                            <th>角色编码</th>
                            <th>操作</th>
                        </tr>
                        <c:choose>
                            <c:when test="${page.totalCount > 0}">
                                <c:forEach items="${page.contents}" var="sysRole" varStatus="vs">
                                    <tr>
                                        <td onclick="common.checkOne(${sysRole.id});">
                                            <b class="public_checkbox" id="check_${sysRole.id}"></b>
                                        </td>
                                        <%--<c:choose>
                                            <c:when test="${sysRole.createOperator == userSession.id || wf:hasAnyPermission('superadmin,admin')}">
                                                <td onclick="common.checkOne(${sysRole.id});">
                                                    <b class="public_checkbox" id="check_${sysRole.id}"></b>
                                                </td>
                                            </c:when>
                                            <c:otherwise>
                                                <td>
                                                    <b class="public_checkbox"></b>
                                                </td>
                                            </c:otherwise>
                                        </c:choose>--%>
                                        <td>${page.pageSize*(page.pageNo-1) + vs.count}</td>
                                        <td>
                                            <span title="${sysRole.name}">
                                                <w:getValidString string="${sysRole.name}" length="30"/></span>
                                        </td>
                                        <td>
                                            <span title="${sysRole.username}">
                                                <w:getValidString string="${sysRole.username}" length="30"/></span>
                                        </td>
                                        <td>
                                            <span title="${sysRole.summary}">
                                                <w:getValidString string="${sysRole.summary}" length="40"/></span>
                                        </td>
                                        <td>
                                                ${sysRole.code}
                                        </td>
                                        <td>
                                            <%--<c:choose>
                                                <c:when test="${sysRole.createOperator == userSession.id || wf:hasAnyPermission('superadmin,admin')}">--%>
                                                    <w:hasPermission roles="sysRole_auth">
                                                        <a href="javascript:void(0);" class="authorizationRoleBtn"
                                                           roleId="${sysRole.id}" roleName="${sysRole.name}">
                                                            <img src="${ctx}/images/system_ico25.png" title="授权"/></a>
                                                    </w:hasPermission>
                                                    <w:hasPermission roles="sysRole_edit">
                                                        <a class="editRoleBtn" href="javascript:void(0);"
                                                           roleId="${sysRole.id}">
                                                            <img src="${ctx}/images/system_ico24.png" title="修改"/></a>
                                                    </w:hasPermission>
                                                    <w:hasPermission roles="sysRole_del">
                                                        <a href="javascript:common.del(${sysRole.id});">
                                                            <img src="${ctx}/images/system_ico13.png" title="删除"/></a>
                                                    </w:hasPermission>
                                                <%--</c:when>
                                                <c:otherwise>
                                                    <w:hasPermission roles="sysRole_auth">
                                                        <a href="javascript:void(0);" style="cursor: default;">
                                                            <img src="${ctx}/images/system_ico26.png" title="授权"/></a>
                                                    </w:hasPermission>
                                                    <w:hasPermission roles="sysRole_edit">
                                                        <a href="javascript:void(0);" style="cursor: default;">
                                                            <img src="${ctx}/images/system_ico23.png" title="修改"/></a>
                                                    </w:hasPermission>
                                                    <w:hasPermission roles="sysRole_del">
                                                        <a href="javascript:void(0);" style="cursor: default;">
                                                            <img src="${ctx}/images/system_ico22.png" title="删除"/></a>
                                                    </w:hasPermission>
                                                </c:otherwise>
                                            </c:choose>--%>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan="7">
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
<script src="${ctx}/js/modules/system/role-main.js" type="text/javascript"></script>
</body>
</html>
