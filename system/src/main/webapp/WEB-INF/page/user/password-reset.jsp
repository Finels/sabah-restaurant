<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>重置密码</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <%@ include file="../common/common-css.jsp" %>
    <link href="${ctx}/css/system.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/js/tools/jquery/validate/jquery.validate.css" rel="stylesheet" type="text/css"/>
    <%@ include file="../common/common-baseJs.jsp" %>
</head>
<body class="public_bg">
<div class="window">
    <form id="editForm" action="<spring:url value='/sysUser/passwordEdit'/>" method="post">
        <input type="hidden" id="id" name="id" value="${id}">

        <div class="window_table">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <th>
                        <label for="password">输入新密码：</label>
                    </th>
                    <td>
                        <input name="password" id="password" type="password"
                               class="public_input1" maxlength="50"/>
                        <span class="red"> &nbsp;* </span>
                    </td>
                </tr>
                <tr>
                    <th>&nbsp;</th>
                    <td>
                        <div class="add_table_con_table window_passplan">
                            <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td id="weakPassword">弱</td>
                                    <td id="strongPassword">较强</td>
                                    <td id="veryStrongPassword">强</td>
                                </tr>
                            </table>
                        </div>
                    </td>
                </tr>
                <tr>
                    <th><label for="passwordConfirm">请确认密码：</label></th>
                    <td>
                        <input name="passwordConfirm" id="passwordConfirm" type="password"
                               class="public_input1"/>
                    </td>
                </tr>
            </table>
        </div>
    </form>
</div>
<div class="window_bar">
    <input type="button" class="window_but" id="saveBtn" value="保存"/>
    <input type="button" class="window_but1" id="cancelBtn" value="取消"/>
</div>
<%@ include file="../common/common-js.jsp" %>
<script src="${ctx}/js/tools/jquery/validate/jquery.validate.min.js" type="text/javascript"></script>
<script src="${ctx}/js/tools/jquery/validate/jquery.validate.extend.js" type="text/javascript"></script>
<script src="${ctx}/js/tools/jquery/jquery.form.js" type="text/javascript"></script>
<script src="${ctx}/js/common-form.js" type="text/javascript"></script>
<script src="${ctx}/js/modules/system/password-reset.js" type="text/javascript"></script>
</body>
</html>
