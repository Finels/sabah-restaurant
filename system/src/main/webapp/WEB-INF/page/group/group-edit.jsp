<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>编辑用户组</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <%@ include file="../common/common-css.jsp" %>
    <link href="${ctx}/js/tools/jquery/validate/jquery.validate.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/js/tools/jquery/ztree3/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css"/>
    <%@ include file="../common/common-baseJs.jsp" %>
</head>
<body class="public_bg">
<div class="window">
    <form id="editForm" action="<spring:url value='/sysGroup/save?ajaxUpload=true'/>" method="post"
          enctype="multipart/form-data">
        <input type="hidden" id="id" name="id" value="${sysGroup.id}">
        <input type="hidden" id="code" name="code" value="${sysGroup.code}">

        <div class="window_table">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <c:if test="${sysGroup.code != 'NI'}">
                    <tr>
                        <th><label for="fatherName">上级用户组：</label></th>
                        <td>
                            <input type="hidden" id="fatherCode" name="fatherCode" value="${sysGroup.fatherCode}">
                            <input type="hidden" name="fatherId" id="fatherId" value="${sysGroup.fatherId}"/>
                            <input type="text" name="fatherName" id="fatherName" placeholder="请选择用户组"
                                   readonly="true" value="${sysGroup.fatherName}"
                                   class="public_input1"/>

                            <div id="groupTree" class="ztree" style="width: 200px;height: 200px;">
                                <div style="margin:100px 0px 0px 100px;">
                                    <img src="${ctx}/images/task/running.gif"/>
                                </div>
                            </div>
                        </td>
                    </tr>
                </c:if>
                <tr>
                    <th><label for="name">名称：</label></th>
                    <td>
                        <input type="text" name="name" id="name" value="${sysGroup.name}" class="public_input1"
                               placeholder="请输入名称" maxlength="100"/>
                        <span class="red"> &nbsp;* </span>
                    </td>
                </tr>
                <tr>
                    <th><label for="cityName">所在城市：</label></th>
                    <td>
                        <input type="hidden" id="areaTreeNodes" name="areaTreeNodes" value='${areaTreeNodes}'/>
                        <input type="hidden" name="cid" id="cid" value="${sysGroup.cid}"/>
                        <input type="hidden" name="pid" id="pid" value="${sysGroup.pid}"/>
                        <input type="text" name="cityName" id="cityName" placeholder="请选择所在城市"
                               readonly="true" value="${sysGroup.cityName}"
                               class="public_input1"/>

                        <div id="cityTree" class="ztree" style="width: 200px;height: 200px;">
                            <div style="margin:100px 0px 0px 100px;">
                                <img src="${ctx}/images/task/running.gif"/>
                            </div>
                        </div>
                        <span class="red"> &nbsp;* </span>
                    </td>
                </tr>
                <tr>
                    <th><label for="contactPerson">联系人姓名：</label></th>
                    <td><input type="text" name="contactPerson" id="contactPerson" value="${sysGroup.contactPerson}"
                               class="public_input1" maxlength="50"/>
                    </td>
                </tr>
                <tr>
                    <th><label for="contactPhone">联系人电话：</label></th>
                    <td><input type="text" name="contactPhone" id="contactPhone" value="${sysGroup.contactPhone}"
                               class="public_input1" maxlength="50"/>
                    </td>
                </tr>
                <tr>
                    <th><label for="introduction">简介：</label></th>
                    <td><textarea name="introduction" id="introduction" maxlength="200"
                                  class="system_textarea">${sysGroup.introduction}</textarea>
                    </td>
                </tr>
                <%--<tr>
                    <th style="vertical-align: top;">顶部logo：</th>
                    <td>
                        ${sysGroup.topLogo}
                        <input type="file" name="topLogoFile" id="topLogoFile" value="${sysGroup.topLogo}" onchange="checkLogo();" class="public_input1"/>
                        <br/><div style="margin-top: 3px">请上传215*61的图片,支持图片格式有.jpg .bmp .gif .png .jpeg</div>
                    </td>
                </tr>--%>
            </table>
        </div>
    </form>
</div>
<div class="window_bar">
    <input type="button" class="window_but" id="saveBtn" value="保存"/>
    <input type="button" class="window_but1" id="cancelBtn" value="取消"/>
</div>
<%@ include file="../common/common-js.jsp" %>
<script src="${ctx}/js/tools/jquery/jquery.bgiframe.js" type="text/javascript"></script>
<script src="${ctx}/js/tools/jquery/ztree3/js/jquery.ztree.core-3.5.min.js" type="text/javascript"></script>
<script src="${ctx}/js/tools/jquery/ztree3/js/jquery.ztree.exedit-3.5.min.js" type="text/javascript"></script>
<script src="${ctx}/js/tools/pico-popup.js" type="text/javascript"></script>
<script src="${ctx}/js/tools/jquery/validate/jquery.validate.min.js" type="text/javascript"></script>
<script src="${ctx}/js/tools/jquery/validate/jquery.validate.extend.js" type="text/javascript"></script>
<script src="${ctx}/js/tools/jquery/validate/messages_zh.js" type="text/javascript"></script>
<script src="${ctx}/js/tools/jquery/jquery.form.js" type="text/javascript"></script>
<script src="${ctx}/js/common-form.js" type="text/javascript"></script>
<script src="${ctx}/js/modules/system/group-edit.js?t=1" type="text/javascript"></script>
</body>
</html>
