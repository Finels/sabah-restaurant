<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>用户组管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <%@ include file="../common/common-css.jsp" %>
    <%@ include file="../common/common-baseJs.jsp" %>
    <link href="${ctx}/js/tools/jquery/ztree3/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css"/>
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
                <div class="public_nav">
                    <%--右侧顶部导航菜单--%>
                    <img src="${ctx}/images/system_ico4.png"/>用户组管理
                    <%--<jsp:include page="../system-right-menu.jsp"/>--%>
                </div>
                <div class="system_info">
                    <form id="searchForm" action="<spring:url value="/sysGroup/main?_secondMenu=${systemMenu}"/>" method="post">
                        <input type="hidden" id="pageNo" name="pageNo" value="${page.pageNo}"/>
                        <input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}"/>
                        <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td><label for="groupName">用户组名称：</label>
                                    <input name="groupName" id="groupName" value="${groupName}" type="text"
                                           class="public_input1" placeholder="用户组名称"/>
                                </td>
                                <td><input type="submit" class="public_but" value="查询"/></td>
                            </tr>
                        </table>
                    </form>
                </div>
                <div class="manage_user">
                    <div class="manage_user_left">
                        <%--<input type="hidden" id="userSessionId" value='${userSession.id}'/>--%>
                        <input type="hidden" id="sysGroupNodes" value='${sysGroupNodes}'/>
                        <%--<input type="hidden" id="isAdmin" value='${wf:hasAnyPermission('superadmin,admin')}'/>--%>
                        <c:choose>
                            <c:when test="${sysGroupNodes != '[]'}">
                                <div id="groupTree" class="ztree"
                                     style="width: 200px; height:480px; overflow:auto "></div>
                            </c:when>
                            <c:otherwise>
                                暂无数据！
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="manage_user_right">
                        <input type="hidden" id="groupCode" value="${groupCode}"/>
                        <iframe id="userListIFrame" width="100%" height="550px;"
                                src="<spring:url value="/sysUser/list?selectGroupCode=${groupCode}"/>"
                                frameborder="0" scrolling="auto" style='height:550px;'></iframe>
                    </div>
                </div>
            </td>
        </tr>
    </table>
</div>
<jsp:include page="../common/bottom.jsp"/>
<%@ include file="../common/common-js.jsp" %>
<script src="${ctx}/js/tools/jquery/jquery.bgiframe.js" type="text/javascript"></script>
<script src="${ctx}/js/tools/jquery/ztree3/js/jquery.ztree.core-3.5.min.js" type="text/javascript"></script>
<script src="${ctx}/js/tools/jquery/ztree3/js/jquery.ztree.exedit-3.5.min.js" type="text/javascript"></script>
<script src="${ctx}/js/modules/system/group-main.js" type="text/javascript"></script>
</body>
</html>
