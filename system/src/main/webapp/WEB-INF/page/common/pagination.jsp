<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- 分页--%>
<c:if test="${page.totalCount > 0}">
    <div class="page">
        <div class="page_left"> 共<b class="public_color5" id="totalCount">${page.totalCount}</b>条记录
            每页
            <select name="bottomChangePageSize" id="bottomChangePageSize" style="width: 42px;">
                <option value="5" <c:if test="${page.pageSize == 5}">selected="selected"</c:if>>5</option>
                <option value="10" <c:if test="${page.pageSize == 10}">selected="selected"</c:if>>10</option>
                <option value="20" <c:if test="${page.pageSize == 20}">selected="selected"</c:if>>20</option>
                <option value="30" <c:if test="${page.pageSize == 30}">selected="selected"</c:if>>30</option>
                <option value="50" <c:if test="${page.pageSize == 50}">selected="selected"</c:if>>50</option>
            </select>
            条
        </div>
        <div class="page_right" id="pageRight">
        </div>
    </div>
</c:if>

<script type="text/javascript">
    $(function () {
        var defaultTotal = 0, defaultPageNo = 1, defaultPageSize = 10,
                maxSteps = 6;

        var form = $('<%=request.getParameter("formId") %>');

        var currentClass = "press";

        var total = ${page.totalCount};
        if (total == "") {
            total = defaultTotal;
        }

        var pageNo = ${page.pageNo};
        if (pageNo == "") {
            pageNo = defaultPageNo;
        }

        var pageSize = ${page.pageSize};
        if (pageSize == "") {
            pageSize = defaultPageSize;
        }

        var steps = maxSteps > 0;
        var firstPage = 1;
        var lastPage = Math.round(Math.ceil(total / pageSize));

        var html = "";
        if (pageNo > 1) {
            html += "<a href='javascript:void(0);' class='goToPage' pageNo='${page.pageNo - 1}'><<</a>";
        }

        if (steps && lastPage >= firstPage) {
            var beginPageNo = pageNo - Math.round(maxSteps / 2.0) + (maxSteps % 2);
            var endPageNo = pageNo + Math.round(maxSteps / 2.0) - 1;

            if (beginPageNo <= firstPage) {
                beginPageNo = firstPage;
                endPageNo = maxSteps;
            }
            if (endPageNo > lastPage) {
                beginPageNo = lastPage - maxSteps + 1;
                if (beginPageNo < firstPage) {
                    beginPageNo = firstPage;
                }
                endPageNo = lastPage;
            }
            if (beginPageNo >= firstPage + 1) {
                html += "<a href='javascript:void(0);' class='goToPage' pageNo='${page.pageNo - 4}'>...</a>";
            }
            // display paginate steps
            for (var i = beginPageNo; i <= endPageNo; i++) {
                html += "<a ";
                if (pageNo == i) {
                    html += " class=\"" + currentClass + "\" ";
                }
                if (pageNo != i) {
                    html += " href='javascript:void(0);' class='goToPage' pageNo='";
                    html += i;
                    html += "'";
                }
                html += " >";
                html += i;
                html += "</a>";
            }
            if (endPageNo < lastPage) {
                html += "<a href='javascript:void(0);' class='goToPage' pageNo='${page.pageNo + 3}'>...</a>";
            }
        }

        if (pageNo < lastPage) {
            html += "<a href='javascript:void(0);' class='goToPage' pageNo='${page.pageNo + 1}'>>></a>";
        }

        $("#pageRight").html(html);

        $(".goToPage").on("click", function () {
            $("#pageNo", form).val($(this).attr("pageNo"));
            form.submit();
        });

        //改变每页显示的记录条数
        $("#bottomChangePageSize").on("change", function () {
            $("#pageSize", form).val($(this).find("option:selected").text());
            $("#pageNo", form).val(1);
            form.submit();
        });
    });
</script>