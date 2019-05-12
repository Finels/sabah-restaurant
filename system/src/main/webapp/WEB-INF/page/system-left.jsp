<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="common/taglib.jsp" %>
<%--左侧菜单--%>
<div class="public_left">
    <%--一级菜单--%>
    <div class="public_left_title">
        <h3>
            系统管理
        </h3>
    </div>
    <ul class="public_list attention_empty">
        <%--二级菜单--%>
        <input id="secondMenu" value="${systemMenu}" type="hidden"/>
        <c:forEach var="secItem" items="${systemMenus}">
            <li id="sys_secMenu_${secItem.uniqueCode}"  class="sys_secMenu_class">
                <c:choose>
                    <c:when test="${empty secItem.url}">
                        <a href="javascript:void(0)"><img
                                src="${ctx}/images/resourceIcon/${fn:length(secItem.icon) > 0 ? secItem.icon : 'default.png'}"/>
                                ${secItem.name}</a>
                    </c:when>
                    <c:otherwise>
                        <a href="javascript:gotoMenu('${secItem.url}', '', '${secItem.uniqueCode}', '${secItem.target}')">
                            <img src="${ctx}/images/resourceIcon/${fn:length(secItem.icon) > 0 ? secItem.icon : 'default.png'}"/>
                                ${secItem.name}
                        </a>
                    </c:otherwise>
                </c:choose>
                    <%--三级菜单--%>
                <c:set var="thirdItems" value="${thirdMenus[secItem.uniqueCode]}"/>
                <c:if test="${fn:length(thirdItems) > 0}">
                    <ul class="attention_second_empty">
                        <c:forEach var="thirdItem" items="${thirdItems}">
                            <li>
                                <a href="javascript:gotoThirdMenu('${thirdItem.url}', '', '${secItem.uniqueCode}', '${thirdItem.uniqueCode}', '${thirdItem.target}')"
                                   <c:if test="${thirdItem.uniqueCode eq _thirdMenu}">class="press"</c:if>>
                                    <span>${thirdItem.name}</span>
                                </a>
                            </li>
                        </c:forEach>
                    </ul>
                </c:if>
            </li>
        </c:forEach>
    </ul>
</div>
<script type="text/javascript">
    $(function () {
        $(".sys_secMenu_class").click(function () {
            $(".sys_secMenu_class").removeClass("press");
            $(this).addClass("press");
        });


        var secondMenu = $("#secondMenu").val();
        $("#sys_secMenu_" + secondMenu).addClass("press");

        /*$("#sys_secMenu_" + secondMenu).addClass("press").find(".attention_second_empty").slideDown();

        $(".attention_empty > li").click(function () {
            if ($(this).find(".attention_second_empty").length > 0) {
                if ($(this).find(".attention_second_empty").css("display") == "none") {
                    $(this).addClass("press");
                    $(this).find(".attention_second_empty").slideDown();
                } else {
                    $(this).removeClass("press");
                    $(this).find(".attention_second_empty").slideUp();
                }
            }
        });*/
    });
</script>
