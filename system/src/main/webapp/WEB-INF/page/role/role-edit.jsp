<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>编辑角色</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <%@ include file="../common/common-css.jsp" %>
    <link href="${ctx}/css/system.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/js/tools/jquery/validate/jquery.validate.css" rel="stylesheet" type="text/css"/>
    <%@ include file="../common/common-baseJs.jsp" %>
</head>
<body class="public_bg">
<div class="window">
    <form id="addRoleForm" action="<spring:url value='/sysRole/save'/>" method="post">
        <input type="hidden" id="id" name="id" value="${sysRole.id}">

        <div class="window_table">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <th>
                        <label for="name">角色名称：</label>
                    </th>
                    <td>
                        <input type="text" name="name" id="name" value="${sysRole.name}" class="public_input1"
                               placeholder="请输入角色名称" maxlength="100"/>
                        <span class="red"> &nbsp;* </span>
                    </td>
                </tr>
                <tr>
                    <th>角色编码：</th>
                    <td>
                        <select id="code" name="code">
                            <option value=""
                                    <c:if test="${sysRole.code == null || sysRole.code == ''}">selected="selected"</c:if>>
                                请选择角色编码
                            </option>
                            <c:forEach items="${roleMap}" var="role">
                                <option value="${role.key}"
                                        <c:if test="${sysRole.code == role.key}">selected="selected"</c:if>>
                                        ${role.value}
                                </option>
                            </c:forEach>
                        </select>
                        <span class="red"> &nbsp;* </span>
                    </td>
                </tr>
                <tr>
                    <th><label for="summary">备注：</label></th>
                    <td>
                        <textarea id="summary" name="summary" maxlength="500" class="system_textarea">${sysRole.summary}</textarea>
                    </td>
                </tr>
            </table>
        </div>
    </form>
</div>
<div class="window_bar">
    <input type="button" class="window_but" id="submitAddRole" value="保存"/>
    <input type="button" class="window_but1" id="cancelAddRole" value="取消"/>
</div>
<%@ include file="../common/common-js.jsp" %>
<script src="${ctx}/js/tools/jquery/validate/jquery.validate.min.js" type="text/javascript"></script>
<script src="${ctx}/js/tools/jquery/validate/messages_zh.js" type="text/javascript"></script>
<script src="${ctx}/js/tools/jquery/jquery.form.js" type="text/javascript"></script>
<script src="${ctx}/js/common-form.js" type="text/javascript"></script>
<script src="${ctx}/js/modules/system/role-edit.js" type="text/javascript"></script>
</body>
</html>
