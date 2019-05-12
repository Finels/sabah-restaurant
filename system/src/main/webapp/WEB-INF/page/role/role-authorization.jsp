<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>分配权限</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <%@ include file="../common/common-css.jsp" %>
    <link href="${ctx}/css/system.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/js/tools/jquery/ztree3/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/js/tools/jquery/multiselect2/css/jquery-ui.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/js/tools/jquery/multiselect2/css/jquery.multiselect.filter.css" rel="stylesheet"
          type="text/css"/>
    <link href="${ctx}/js/tools/jquery/multiselect2/css/jquery.multiselect.css" rel="stylesheet" type="text/css"/>
    <%@ include file="../common/common-baseJs.jsp" %>
</head>
<body class="public_bg">
<div class="window">
    <input type="hidden" id="resourceNodes" value='${resourceNodes}'/>

    <form id="authorizationRoleForm" action="<spring:url value='/sysRole/authorization'/>" method="post">
        <input type="hidden" id="id" name="id" value="${sysRole.id}">
        <input type="hidden" id="name" name="name" value="${sysRole.name}">

        <div class="window_table">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <th>数据权限：</th>
                    <td>
                        <input name="dataArea" id="dataArea" value="${sysRole.dataArea}" type="hidden">
                        <span title="可查看本组内所有用户数据">
                            <input name="dataAreaTemp" id="dataAreaG" class="dataAreaTemp" type="radio" value="g"
                                   <c:if test="${fn:contains(sysRole.dataArea, 'g')}">checked</c:if>/>
                            <label for="dataAreaG">本组</label>&nbsp;&nbsp;
                        </span>
                        <span title="仅可查看个人数据">
                            <input name="dataAreaTemp" id="dataAreaP" class="dataAreaTemp" type="radio" value="p"
                                   <c:if test="${fn:contains(sysRole.dataArea, 'p')}">checked</c:if>/>
                            <label for="dataAreaP">个人</label>&nbsp;&nbsp;
                        </span>
                        <br/>
                        <span title="可查看本组以下各组的用户数据">
                            <input name="dataArea_l" id="dataArea_l" type="checkbox" value="l"
                                   <c:if test="${fn:contains(sysRole.dataArea, 'l')}">checked</c:if>/>
                            <label for="dataArea_l">下级组</label>&nbsp;&nbsp;
                        </span>
                        <span title="可发布共享数据供下级组查看">
                            <input name="share" id="share" type="checkbox" value="1"
                                   <c:if test="${sysRole.share == 1}">checked</c:if>/>
                            <label for="share">发布共享</label>&nbsp;&nbsp;
                        </span>
                        <span title="可查看上级组共享的数据">
                            <input name="dataArea_s" id="dataArea_s" type="checkbox" value="s"
                                   <c:if test="${fn:contains(sysRole.dataArea, 's')}">checked</c:if>/>
                            <label for="dataArea_s">查看上级共享数据</label>
                        </span>
                    </td>
                </tr>
                <tr>
                    <th>
                        功能权限：
                    </th>
                    <td>
                        <input type="hidden" name="resources" id="resources" value="${sysRole.resources}"/>

                        <div id="authorityTree" class="ztree"
                             style="height:220px; overflow:auto; border:solid;border-width: 1px;border-radius:5px;"></div>
                    </td>
                </tr>
            </table>
        </div>
    </form>
</div>
<div class="window_bar">
    <input type="button" class="window_but" id="submitAuthorizationRole" value="保存"/>
    <input type="button" class="window_but1" id="cancelAuthorizationRole" value="取消"/>
</div>
<%@ include file="../common/common-js.jsp" %>
<script src="${ctx}/js/tools/jquery/ztree3/js/jquery.ztree.core-3.5.min.js" type="text/javascript"></script>
<script src="${ctx}/js/tools/jquery/ztree3/js/jquery.ztree.excheck-3.5.min.js" type="text/javascript"></script>
<script src="${ctx}/js/tools/jquery/multiselect2/jquery-ui.min.js" type="text/javascript"></script>
<script src="${ctx}/js/tools/jquery/multiselect2/jquery.multiselect.js" type="text/javascript"></script>
<script src="${ctx}/js/tools/jquery/multiselect2/jquery.multiselect.filter.js" type="text/javascript"></script>
<script src="${ctx}/js/tools/jquery/jquery.form.js" type="text/javascript"></script>
<script src="${ctx}/js/common-form.js" type="text/javascript"></script>
<script src="${ctx}/js/modules/system/role-authorization.js" type="text/javascript"></script>
</body>
</html>
