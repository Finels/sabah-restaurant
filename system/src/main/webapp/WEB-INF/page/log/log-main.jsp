<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglib.jsp"  %>
<!DOCTYPE html>
<html>
<head>
    <title>系统日志</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <%@ include file="../common/common-css.jsp" %>
    <%@ include file="../common/common-baseJs.jsp" %>
    <link href="${ctx}/js/tools/My97DatePicker/skin/WdatePicker.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="../common/head.jsp"/>
<div class="public_mat" id="logList">
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
                        <img src="${ctx}/images/system_ico4.png"/>系统日志
                    <%--<jsp:include page="../system-right-menu.jsp"/>--%>
                </div>
                <div class="system_info">
                    <input type="hidden" id="searchActionPrefix" value="<spring:url value="/sysLog/"/>"/>

                    <form id="searchForm" action="<spring:url value="/sysLog/main?_secondMenu=${systemMenu}"/>" method="post">
                        <input type="hidden" id="pageNo" name="pageNo" value="${page.pageNo}"/>
                        <input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}"/>
                        <fmt:formatDate value="${sysLogQuery.sysTimeBegin}" pattern="yyyy-MM-dd" var="timeStart"/>
                        <fmt:formatDate value="${sysLogQuery.sysTimeEnd}" pattern="yyyy-MM-dd" var="timeEnd"/>
                        <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td><label for="remark">日志内容：</label>
                                    <input name="remark" id="remark" value="${sysLogQuery.remark}" type="text"
                                           class="public_input" placeholder="日志内容"/></td>
                                <td>日志类型：
                                    <select name="type" id="type">
                                        <option value="">请选择</option>
                                        <option value="1" <c:if test="${sysLogQuery.type==1}">selected</c:if>>
                                            登录
                                        </option>
                                        <option value="2" <c:if test="${sysLogQuery.type==2}">selected</c:if>>
                                            任务
                                        </option>
                                        <option value="3" <c:if test="${sysLogQuery.type==3}">selected</c:if>>
                                            管理
                                        </option>
                                        <option value="4" <c:if test="${sysLogQuery.type==4}">selected</c:if>>
                                            积分
                                        </option>
                                    </select>
                                </td>
                                <td><label for="username">操作用户：</label>
                                    <input name="username" id="username" value="${sysLogQuery.username}"
                                           type="text" class="public_input" placeholder="操作用户"/></td>
                                <td>
                                    <div class="public_inputs">
                                        <input name="sysTimeBegin" id="sysTimeBegin" type="text" readonly="true"
                                               value="${timeStart}" placeholder="开始时间"
                                               class="public_input Wdate" onfocus="WdatePicker({skin:'whyGreen'})"
                                               onkeydown="return false;"/>
                                    </div>
                                </td>
                                <td>
                                    <div class="public_inputs">
                                        <input name="sysTimeEnd" id="sysTimeEnd" type="text" readonly="true"
                                               value="${timeEnd}" placeholder="结束时间"
                                               class="public_input Wdate" onfocus="WdatePicker({skin:'whyGreen'})"
                                               onkeydown="return false;"/>
                                    </div>
                                </td>
                                <td>
                                    <input type="button" id="searchLogBtn" class="public_but" value="查询"/>
                                    <input type="button" id="resetBtn" class="public_but1" value="重置"/>
                                </td>
                            </tr>
                        </table>
                    </form>
                </div>
                <div class="manage_tag">
                    <c:if test="${wf:hasAnyPermission('superadmin,admin')}">
                        <div class="manage_tag_left">
                            <a href="javascript:common.del();">
                                <img src="${ctx}/images/system_ico9.png"/></a>
                        </div>
                    </c:if>
                    <div class="manage_tag_right">
                        <jsp:include page="../../page/common/pageShow.jsp">
                            <jsp:param name="formId" value="#searchForm"/>
                        </jsp:include>
                    </div>
                </div>
                <div class="manage_table">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <c:if test="${wf:hasAnyPermission('superadmin,admin')}">
                                <th width="45px;" onclick="common.checkAll();">
                                    <b class="public_checkbox checkAll"></b>
                                </th>
                            </c:if>
                            <th>日志类别</th>
                            <th>操作用户</th>
                            <th>IP地址</th>
                            <th>日志内容</th>
                            <th>操作时间</th>
                            <c:if test="${wf:hasAnyPermission('superadmin,admin')}">
                                <th>操作</th>
                            </c:if>
                        </tr>
                        <c:choose>
                            <c:when test="${page.totalCount > 0}">
                                <c:forEach items="${page.contents}" var="sysLog">
                                    <tr>
                                        <c:if test="${wf:hasAnyPermission('superadmin,admin')}">
                                            <td onclick="common.checkOne(${sysLog.id});">
                                                <b class="public_checkbox" id="check_${sysLog.id}"></b>
                                            </td>
                                        </c:if>
                                        <td>
                                            <c:choose>
                                                <c:when test="${sysLog.type == 1}">
                                                    登录
                                                </c:when>
                                                <c:when test="${sysLog.type == 2}">
                                                    任务
                                                </c:when>
                                                <c:when test="${sysLog.type == 3}">
                                                    管理
                                                </c:when>
                                                <c:when test="${sysLog.type == 4}">
                                                    积分
                                                </c:when>
                                            </c:choose>
                                        </td>
                                        <td>
                                                ${sysLog.username}
                                        </td>
                                        <td>
                                                ${sysLog.ip}
                                        </td>
                                        <td>
                                            <span title="${sysLog.remark}">
                                                <w:getValidString string="${sysLog.remark}"
                                                                  length="40"/></span>
                                        </td>
                                        <td>
                                            <fmt:formatDate value="${sysLog.sysTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                        </td>
                                        <c:if test="${wf:hasAnyPermission('superadmin,admin')}">
                                            <td>
                                                <a href="javascript:common.del(${sysLog.id});"><img
                                                        src="${ctx}/images/system_ico13.png" title="删除"/></a>
                                            </td>
                                        </c:if>
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
            </td>
        </tr>
    </table>
</div>
<jsp:include page="../common/bottom.jsp"/>
<%@ include file="../common/common-js.jsp" %>
<script src="${ctx}/js/tools/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="${ctx}/js/modules/system/sysLog-main.js" type="text/javascript"></script>
</body>
</html>
