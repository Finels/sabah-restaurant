(function ($) {
    $._popup = [];
    $._popupFnc = function () {
        for (var i in $._popup) {
            $._popup[i].hide(true);
        }
    };

    $.fn.popup = function (divId, width, height) {
        this.p_divId = divId;
        this.p_width = width || $(this).width() + 200;
        this.p_height = height || 200;
        this._init = function () {
            var str = [
                "<div style='position:absolute;overflow:hidden;border:1px solid #C2D4E8;background-color:#FFFFFF;display:none;width:", this.p_width, ";height:", this.p_height, "'></div>"
            ];

            var _popupPanel = this._popupPanel = $(str.join(''));
            $(document.body).append(_popupPanel);
            _popupPanel.bgiframe();
            var item = $('#' + this.p_divId);
            item.css("overflow-x", "auto");
            item.css("overflow-y", "auto");
            item.css("background-color", "#FFFFFF");
            item.css("border-width", "0px");
            _popupPanel.append(item);

            this.data("_popup", this);
            $._popup.push(this);

            $(this).bind("focus", function () {
                $(this).popup().show();
            });

            //	this.show();
            return this;
        };
        this.show = function () {
            this.hasShow = true;
            var offset = $(this).offset();
            var top = offset.top + $(this).height() + 7;
            this._popupPanel.css('top', offset.top + $(this).height() + 7);
            this._popupPanel.css('left', offset.left);
            this._popupPanel.show();
            $(document.body).bind("click", $._popupFnc);
        };
        this.hide = function (auto) {
            if (!this.hasShow)return;
            if (auto) {
                var offset = $(this).offset();
                if (!offset)
                    return;
                var p_offset = $(this._popupPanel).offset();
                var x = event.x + document.body.scrollLeft;
                if (x < offset.left || x > offset.left + $(this._popupPanel).width())
                    this._popupPanel.hide();
                var y = event.y + document.body.scrollTop;
                //		alert(y)
                if (y < offset.top || y > offset.top + $(this).height() + $(this._popupPanel).height() + 7) {
                    this._popupPanel.hide();
                    $(document.body).unbind("click", $._popupFnc);
                }
            } else {
                this._popupPanel.hide();
                $(document.body).unbind("click", $._popupFnc);
            }
        };

        var popup = $(this).data("_popup");
        if (popup) {
            return popup;
        } else {
            popup = this._init();
        }
        return popup;
    };
})(jQuery);	