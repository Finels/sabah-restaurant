$(function () {
    var container = $("#logList");

    $("#searchLogBtn", container).on("click", function () {
        $("#pageNo", container).val(1);
        if(checkEndTime($("#sysTimeBegin").val(), $("#sysTimeEnd").val())) {
            $("#searchForm", container).submit();
        } else {
            common.showMsg("结束时间不能小于开始时间！");
        }
    });

    $("#resetBtn").click(function () {
        $("#remark").val("");
        $("#type").val("");
        $("#username").val("");
        $("#sysTimeBegin").val("");
        $("#sysTimeEnd").val("");
    });
});