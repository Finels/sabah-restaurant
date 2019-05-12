<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>编辑资源</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <%@ include file="../common/common-css.jsp" %>
    <link href="${ctx}/js/tools/jquery/validate/jquery.validate.css?v=123456" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/js/tools/jquery/ztree3/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css"/>
    <%@ include file="../common/common-baseJs.jsp" %>
</head>
<body class="public_bg" id="editResource">
<div class="window">
    <form id="editForm" action="<spring:url value='/sysResource/save'/>" method="post">
        <input type="hidden" id="id" name="id" value="${sysResource.id}">

        <div class="window_table">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr class="allShow">
                    <th>
                        资源类型：
                    </th>
                    <td>
                        <div class="add_table_mat">
                            <div class="add_table_con4">
                                <input name="type" type="radio" value="0" id="type0"
                                       <c:if test="${sysResource.type == 0 || sysResource.type == null || sysResource.type == ''}">checked="true"</c:if>/>
                                <label for="type0">系统</label>
                            </div>
                            <div class="add_table_con4">
                                <input name="type" type="radio" value="1" id="type1"
                                       <c:if test="${sysResource.type == 1}">checked="true"</c:if>/>
                                <label for="type1">系统菜单</label>
                            </div>
                            <div class="add_table_con4">
                                <input name="type" type="radio" value="2" id="type2"
                                       <c:if test="${sysResource.type == 2}">checked="true"</c:if>/>
                                <label for="type2">功能点</label>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr class="allShow">
                    <th><label for="name">资源名称：</label></th>
                    <td>
                        <input type="text" name="name" id="name" value="${sysResource.name}" class="public_input1"
                               placeholder="请输入资源名称" maxlength="200"/>
                        <span class="red"> &nbsp;* </span>
                    </td>
                </tr>
                <tr class="allShow">
                    <th><label for="uniqueCode">唯一编码：</label></th>
                    <td>
                        <input type="text" name="uniqueCode" id="uniqueCode" value="${sysResource.uniqueCode}"
                               class="public_input1" placeholder="请输入唯一编码" maxlength="100"/>
                        <span class="red"> &nbsp;* </span>
                        <span class="red">
                            <br/>命名格式：系统名称、菜单名称、模块名称_功能点名称
                            <br/>例如：netImage、sysUser、sysUser_add
                        </span>
                    </td>
                </tr>
                <tr class="featureShow">
                    <th><label for="authCode">权限编码：</label></th>
                    <td>
                        <input type="text" name="authCode" id="authCode" value="${sysResource.authCode}"
                               class="public_input1" placeholder="请输入权限编码" maxlength="100"/>
                        <span class="red"> &nbsp;* </span>
                        <span class="red">
                            <br/>命名格式：模块名称_功能点名称_操作名称
                            <br/>例如：（system_sysUser_add）
                        </span>
                    </td>
                </tr>
                <tr class="menuAndFeatureShow">
                    <th><label for="fatherName">上级资源：</label></th>
                    <td>
                        <input type="hidden" name="fatherId" id="fatherId" value="${sysResource.fatherId}"
                               class="public_input1"/>
                        <input type="text" name="fatherName" id="fatherName" value="${sysResource.fatherName}"
                               class="public_input1" placeholder="请选择上级资源" readonly/>

                        <div id="resourceTree" class="ztree" style="width: 200px;height: 200px;">
                            <div style="margin:100px 0px 0px 100px;">
                                <img src="${ctx}/images/task/running.gif"/>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr class="systemAndMenuShow">
                    <th><label for="url">资源路径：</label></th>
                    <td><input type="text" name="url" id="url" value="${sysResource.url}" class="public_input1"
                               placeholder="请输入资源路径" maxlength="200"/>
                        <span class="red"> &nbsp;* </span>
                    </td>
                </tr>
                <tr class="systemAndMenuShow">
                    <th>跳转标识：</th>
                    <td>
                        <select id="router" name="router">
                            <option value=""
                                    <c:if test="${sysResource.router == null || sysResource.router == ''}">selected="selected"</c:if>>
                                请选择
                            </option>
                            <option value="1"
                                    <c:if test="${sysResource.router == '1'}">selected="selected"</c:if>>
                                路由跳转
                            </option>
                            <option value="2"
                                    <c:if test="${sysResource.router == '2'}">selected="selected"</c:if>>
                                直接跳转
                            </option>
                        </select>
                    </td>
                </tr>
                <tr class="systemAndMenuShow">
                    <th><label for="icon">资源图标：</label></th>
                    <td>
                        <c:if test="${sysResource.icon != '' && sysResource.icon != null}">
                            <c:choose>
                                <c:when test="${sysResource.level == 1}">
                                    <img src="${ctx}/images/resourceIcon/${sysResource.icon}_on.png"/>&nbsp;
                                </c:when>
                                <c:otherwise>
                                    <img src="${ctx}/images/resourceIcon/${sysResource.icon}"/>&nbsp;
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                        <input type="text" name="icon" id="icon" value="${sysResource.icon}" class="public_input1"
                               placeholder="请输入资源图标" maxlength="100"/>
                    </td>
                </tr>
                <tr class="systemAndMenuShow">
                    <th><label for="icon">资源头部图标：</label></th>
                    <td>
                        <c:if test="${sysResource.headIcon != '' && sysResource.headIcon != null}">
                            <img src="${ctx}/images/logo/index/${sysResource.headIcon}"/>&nbsp;
                        </c:if>
                        <input type="text" name="headIcon" id="headIcon" value="${sysResource.headIcon}"
                               class="public_input1"
                               placeholder="请输入资源头部图标" maxlength="100"/>
                    </td>
                </tr>
                <tr class="systemAndMenuShow">
                    <th>桌面区块：</th>
                    <td>
                        <select id="block" name="block">
                            <option value=""
                                    <c:if test="${sysResource.block == null || sysResource.block == ''}">selected="selected"</c:if>>
                                请选择
                            </option>
                            <option value="1"
                                    <c:if test="${sysResource.block == '1'}">selected="selected"</c:if>>
                                1
                            </option>
                            <option value="2"
                                    <c:if test="${sysResource.block == '2'}">selected="selected"</c:if>>2
                            </option>
                            <option value="3"
                                    <c:if test="${sysResource.block == '3'}">selected="selected"</c:if>>3
                            </option>
                        </select>
                    </td>
                </tr>
                <tr class="systemAndMenuShow">
                    <th><label for="styleClass">桌面样式：</label></th>
                    <td><input type="text" name="styleClass" id="styleClass" value="${sysResource.styleClass}"
                               class="public_input1"
                               placeholder="请输入桌面样式" maxlength="200"/>
                    </td>
                </tr>
                <tr class="allShow">
                    <th><label for="weight">排序值：</label></th>
                    <td><input type="text" name="weight" id="weight" value="${sysResource.weight}"
                               class="public_input1" placeholder="请输入排序值" maxlength="5"/>
                        <span class="red"> &nbsp;* </span>
                    </td>
                </tr>
                <tr class="systemAndMenuShow">
                    <th>打开方式：</th>
                    <td>
                        <select id="target" name="target">
                            <option value=""
                                    <c:if test="${sysResource.target == null || sysResource.target == ''}">selected="selected"</c:if>>
                                请选择
                            </option>
                            <option value="_blank"
                                    <c:if test="${sysResource.target == '_blank'}">selected="selected"</c:if>>
                                新页面
                            </option>
                            <option value="_parent"
                                    <c:if test="${sysResource.target == '_parent'}">selected="selected"</c:if>>当前页
                            </option>
                            <option value="popup"
                                    <c:if test="${sysResource.target == 'popup'}">selected="selected"</c:if>>弹框
                            </option>
                        </select>
                    </td>
                </tr>
                <tr class="allShow">
                    <th>是否启用：</th>
                    <td>
                        <div class="add_table_mat">
                            <div class="add_table_con4">
                                <input name="valid" type="radio" value="1" id="valid1"
                                       <c:if test="${sysResource.valid == 1 || sysResource.valid == null || sysResource.valid == ''}">checked="true"</c:if>/>
                                <label for="valid1">启用</label>
                            </div>
                            <div class="add_table_con4">
                                <input name="valid" type="radio" value="0" id="valid0"
                                       <c:if test="${sysResource.valid == 0}">checked="true"</c:if>/>
                                <label for="valid0">禁用</label>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr class="menuShow">
                    <th>菜单类别：</th>
                    <td>
                        <select id="level" name="level">
                            <option value="1"
                                    <c:if test="${sysResource.level == 1 || sysResource.level == null || sysResource.level == ''}">selected="selected"</c:if>>
                                一级菜单
                            </option>
                            <option value="2" <c:if test="${sysResource.level == 2}">selected="selected"</c:if>>二级菜单
                            </option>
                            <option value="3" <c:if test="${sysResource.level == 3}">selected="selected"</c:if>>三级菜单
                            </option>
                        </select>
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
<script src="${ctx}/js/tools/jquery/jquery.bgiframe.js" type="text/javascript"></script>
<script src="${ctx}/js/tools/jquery/ztree3/js/jquery.ztree.core-3.5.min.js" type="text/javascript"></script>
<script src="${ctx}/js/tools/pico-popup.js" type="text/javascript"></script>
<script src="${ctx}/js/tools/jquery/validate/jquery.validate.min.js" type="text/javascript"></script>
<script src="${ctx}/js/tools/jquery/jquery.form.js" type="text/javascript"></script>
<script src="${ctx}/js/common-form.js" type="text/javascript"></script>
<script src="${ctx}/js/modules/system/resource-edit.js" type="text/javascript"></script>
</body>
</html>
