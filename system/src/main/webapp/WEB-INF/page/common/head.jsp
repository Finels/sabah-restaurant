<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="../common/taglib.jsp" %>
<!DOCTYPE html>
<style>
    .header_3 {width: 100%;height: 66px;line-height: 66px;position: relative;background-color: #2870EF;background-image: linear-gradient(to right, #0c77fb 0%, #5764da 100%);z-index: 10;}

    .header_3 .a_1 {margin-left: 25px;float: left;}
    .header_3 .a_1 img {display: inline-block;margin-top: 10px;}
</style>
<%--头部--%>
<div class="header_3">
    <a class="a_1" href="javascript:void(0);"><img src="${ctx}/images/win/logo/system_logo.png"></a>
</div>
<%--
<div class="head_wrap fixedNav">
    <div class="head">
        <div class="head_left">
            <img src="${ctx}/images/system_logo.png"/>
        </div>
        <ul class="head_mid">
            <c:forEach var="topMenu" items="${topMenus}" varStatus="stat">
                &lt;%&ndash;一级菜单&ndash;%&gt;
                <li class="topMenu ${stat.last ? 'noline':''}" id="topMenu_${topMenu.uniqueCode}">
                    <a href="javascript:gotoTopMenu('${topMenu.url}', '${topMenu.uniqueCode}', '${topMenu.target}')">
                        <div class="head_mid_top">
                            <img src="${ctx}/images/resourceIcon/${fn:length(topMenu.icon) > 0 ? topMenu.icon : 'tm_default'}_off.png"
                                 class="head_mid_top_img1"/>
                            <img src="${ctx}/images/resourceIcon/${fn:length(topMenu.icon) > 0 ? topMenu.icon : 'tm_default'}_on.png"
                                 class="head_mid_top_img2"/>
                        </div>
                        <div class="head_mid_bot">${topMenu.name}</div>
                    </a>

                        &lt;%&ndash;二级菜单&ndash;%&gt;
                    <c:set var="topMenuCode" value="${topMenu.uniqueCode}"/>
                    <c:set var="secItems" value="${secondMenus[topMenuCode]}"/>
                    <c:if test="${fn:length(secItems) > 0}">
                        <div class="head_submenu">

                            <dl class="head_submenu_list">
                                <c:forEach var="secItem" items="${secItems}">
                                    <dd>
                                        <c:choose>
                                            <c:when test="${secItem.target == 'popup'}">
                                                <a href="javascript:void(0);"
                                                   id="${secItem.uniqueCode}">${secItem.name}</a>
                                            </c:when>
                                            <c:otherwise>
                                                <a class="secItem <c:if test="${secItem.uniqueCode == _secondMenu}">press</c:if>"
                                                   href="javascript:gotoSecMenu('${secItem.url}', '${topMenu.uniqueCode}', '${secItem.uniqueCode}', '${secItem.target}')">${secItem.name}</a>
                                            </c:otherwise>
                                        </c:choose>

                                            &lt;%&ndash;三级菜单&ndash;%&gt;
                                        <c:set var="thirdItems" value="${thirdMenus[secItem.uniqueCode]}"/>
                                        <c:if test="${fn:length(thirdItems) > 0}">
                                            <ul class="head_submenu_second">
                                                <c:forEach var="thirdItem" items="${thirdItems}">
                                                    <li>
                                                        <a class="thirdItem <c:if test="${thirdItem.uniqueCode == _thirdMenu}">press</c:if>"
                                                           href="javascript:gotoThirdMenu('${thirdItem.url}', '${topMenu.uniqueCode}', '${secItem.uniqueCode}', '${thirdItem.uniqueCode}', '${thirdItem.target}')">${thirdItem.name}</a>
                                                    </li>
                                                </c:forEach>
                                            </ul>
                                        </c:if>
                                    </dd>
                                </c:forEach>
                            </dl>
                        </div>
                    </c:if>
                </li>
            </c:forEach>
        </ul>
    </div>
</div>--%>
