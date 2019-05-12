<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>资源管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <%@ include file="../common/common-css.jsp" %>
    <%@ include file="../common/common-baseJs.jsp" %>
    <link href="${ctx}/js/tools/jquery/ztree3/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="../common/head.jsp"/>
<div class="public_mat" id="resourceList">
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
                        <img src="${ctx}/images/system_ico4.png"/>资源管理
                    <%--<jsp:include page="../system-right-menu.jsp"/>--%>
                </div>
                <div class="system_info">
                    <input type="hidden" id="searchActionPrefix" value="<spring:url value="/sysResource/"/>"/>

                    <form id="searchForm" action="<spring:url value="/sysResource/main?_secondMenu=${systemMenu}"/>" method="post">
                        <%--<input id="secondMenu" name="secondMenu" value="${systemMenu}" type="hidden"/>--%>
                        <input type="hidden" id="pageNo" name="pageNo" value="${page.pageNo}"/>
                        <input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}"/>
                        <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td><label for="name">资源名称：</label>
                                    <input name="name" id="name" value="${sysResourceRequest.name}" type="text"
                                           class="public_input" placeholder="资源名称"/></td>
                                <td><label for="uniqueCode">唯一编码：</label>
                                    <input name="uniqueCode" id="uniqueCode" value="${sysResourceRequest.uniqueCode}"
                                           type="text" class="public_input" placeholder="唯一编码"/></td>
                                <td><label for="fatherName">上级资源名称：</label>
                                    <input type="hidden" name="fatherId" id="fatherId"
                                           value="${sysResourceRequest.fatherId}"/>
                                    <input type="text" name="fatherName" id="fatherName"
                                           value="${sysResourceRequest.fatherName}"
                                           class="public_input" placeholder="请选择上级资源" readonly/>

                                    <div id="resourceTree" class="ztree" style="width: 200px;height: 200px;">
                                        <div style="margin:100px 0px 0px 100px;">
                                            <img src="${ctx}/images/task/running.gif"/>
                                        </div>
                                    </div>
                                </td>
                                <td>是否可用：
                                    <select name="valid">
                                        <option value="">请选择</option>
                                        <option value="1" <c:if test="${sysResourceRequest.valid == 1}">selected</c:if>>
                                            启用
                                        </option>
                                        <option value="0" <c:if test="${sysResourceRequest.valid == 0}">selected</c:if>>
                                            禁用
                                        </option>
                                    </select>
                                </td>
                                <td><input type="button" id="searchResourceBtn" class="public_but" value="查询"/></td>
                            </tr>
                        </table>
                    </form>
                </div>
                <div class="manage_tag">
                    <div class="manage_tag_left">
                        <w:hasPermission roles="sysResource_add">
                            <a href="javascript:void(0);" id="addResourceBtn"><img src="${ctx}/images/system_ico8.png"/></a>
                        </w:hasPermission>
                        <w:hasPermission roles="sysResource_del">
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
                            <th>资源名称</th>
                            <th>唯一编码</th>
                            <th>URL</th>
                            <th>上级资源</th>
                            <th>排序</th>
                            <th>是否可用</th>
                            <th>操作</th>
                        </tr>
                        <c:choose>
                            <c:when test="${page.totalCount > 0}">
                                <c:forEach items="${page.contents}" var="sysResource" varStatus="vs">
                                    <tr>
                                        <td onclick="common.checkOne(${sysResource.id});">
                                            <b class="public_checkbox" id="check_${sysResource.id}"></b>
                                        </td>
                                        <td>${page.pageSize*(page.pageNo-1) + vs.count}</td>
                                        <td>
                                            <span title="${sysResource.name}">
                                                <w:getValidString string="${sysResource.name}" length="20"/></span>
                                        </td>
                                        <td>
                                            <span title="${sysResource.uniqueCode}">
                                                <w:getValidString string="${sysResource.uniqueCode}"
                                                                  length="20"/></span>
                                        </td>
                                        <td>
                                                ${sysResource.url}
                                        </td>
                                        <td>
                                            <span title="${sysResource.fatherName}">
                                                <w:getValidString string="${sysResource.fatherName}"
                                                                  length="20"/></span>
                                        </td>
                                        <td>
                                            ${sysResource.weight}
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${sysResource.valid == 1}">
                                                    启用
                                                </c:when>
                                                <c:when test="${sysResource.valid == 0}">
                                                    禁用
                                                </c:when>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <w:hasPermission roles="sysResource_edit">
                                                <a class="editResourceBtn" href="javascript:void(0);"
                                                   resourceId="${sysResource.id}"><img
                                                        src="${ctx}/images/system_ico24.png" title="修改"/></a>
                                            </w:hasPermission>
                                            <w:hasPermission roles="sysResource_del">
                                                <a href="javascript:common.del(${sysResource.id});"><img
                                                        src="${ctx}/images/system_ico13.png" title="删除"/></a>
                                            </w:hasPermission>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan="13">
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
<script src="${ctx}/js/modules/system/resource-main.js" type="text/javascript"></script>
</body>
</html>
