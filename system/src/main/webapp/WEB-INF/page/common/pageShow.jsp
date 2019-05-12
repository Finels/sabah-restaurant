<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- 分页--%>
<c:if test="${page.totalCount > 0}">
    每页显示：
    <select name="topChangePageSize" id="topChangePageSize">
        <option value="5" <c:if test="${page.pageSize == 5}">selected="selected"</c:if>>5</option>
        <option value="10" <c:if test="${page.pageSize == 10}">selected="selected"</c:if>>10</option>
        <option value="20" <c:if test="${page.pageSize == 20}">selected="selected"</c:if>>20</option>
        <option value="30" <c:if test="${page.pageSize == 30}">selected="selected"</c:if>>30</option>
        <option value="50" <c:if test="${page.pageSize == 50}">selected="selected"</c:if>>50</option>
    </select>
</c:if>

<script type="text/javascript">
    $(function () {
        var form = $('<%=request.getParameter("formId") %>');

        //改变每页显示的记录条数
        $("#topChangePageSize").on("change", function () {
            $("#pageSize", form).val($(this).find("option:selected").text());
            $("#pageNo", form).val(1);
            form.submit();
        });
    });
</script>