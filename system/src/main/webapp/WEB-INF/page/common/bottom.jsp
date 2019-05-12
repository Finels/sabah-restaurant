<%--
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
&lt;%&ndash;底部&ndash;%&gt;
<div class="foot">
    Copyright @ <span id="thisYear"></span>
    &lt;%&ndash;版权所有 |
    <a href="javascript:void(0);">数据说明</a> |
    <a href="javascript:void(0);">意见反馈</a> |
    <a href="javascript:void(0);">帮助中心</a> |
    <a href="javascript:void(0);">联系我们</a>&ndash;%&gt;
</div>
<div class="public_up">
    &lt;%&ndash;<a href="javascript:void(0);" target="_blank" class="public_up2"></a>&ndash;%&gt;
    <a href="javascript:void(0);" class="public_up1"></a>
</div>
<script type="text/javascript">
    $(function () {
        $("#thisYear").text(new Date().getFullYear());

        if ($(window).width() > 1024) {
            if ($("#div1,.middle,.index_show,.public_mat").length > 0) {
                $(".public_up").css("right", (($("body").width() - $("#div1,.middle,.index_show,.public_mat").width()) / 2) - $(".public_up").width() - 10);
            }
            else {
                $(".public_up").css("right", (($("body").width() - $("#div1,.middle,.index_show,.public_mat").width()) / 2) - $(".public_up").width() - 10);
            }
        }
        else {
            if ($("#div1,.middle,.index_show").length > 0) {
                $(".public_up").css("right", (($("body").width() - $("#div1,.middle,.index_show,.public_mat").width()) / 2));
            }
            else {
                $(".public_up").css("right", (($("body").width() - $("#div1,.middle,.index_show,.public_mat").width()) / 2));
            }
        }

        $(window).resize(function () {
            if ($(window).width() > 1024) {
                if ($("#div1,.middle,.index_show").length > 0) {
                    $(".public_up").css("right", (($("body").width() - $("#div1,.middle,.index_show,.public_mat").width()) / 2) - $(".public_up").width() - 10);
                }
                else {
                    $(".public_up").css("right", (($("body").width() - $("#div1,.middle,.index_show,.public_mat").width()) / 2) - $(".public_up").width() - 10);
                }
            }
            else {
                if ($("#div1").length > 0) {
                    $(".public_up").css("right", (($("body").width() - $("#div1,.middle,.index_show,.public_mat").width()) / 2));
                }
                else {
                    $(".public_up").css("right", (($("body").width() - $("#div1,.middle,.index_show,.public_mat").width()) / 2));
                }
            }
        });

        $(window).scroll(function () {
            $(this).scrollTop() >= 70 ? $('.public_up1').css("display", "block") : $('.public_up1').hide();
        });
        $('.public_up1').click(function () {
            $('html, body').animate({scrollTop: 0}, $(this).offset().top / 7);
        });
    });

    if ($(window).height() >= $(document).height() - 4) {
        $(".foot").addClass("footFix");
    }
</script>--%>
