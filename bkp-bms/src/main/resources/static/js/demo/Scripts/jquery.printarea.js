(function($) {
    var printAreaCount = 0;
    $.fn.printArea = function() {
        var ele = $(this);
        var idPrefix = "printArea_";
        removePrintArea(idPrefix + printAreaCount);
        printAreaCount++;
        var iframeId = idPrefix + printAreaCount;
        var iframeStyle = 'position:absolute;width:0px;height:0px;left:-500px;top:-500px;font-size:12px;';
        iframe = document.createElement('IFRAME');
        $(iframe).attr({
            style: iframeStyle,
            id: iframeId
        });
        document.body.appendChild(iframe);
        var doc = iframe.contentWindow.document;
        doc.write("<style>td{ border-right:solid 1px #666666;border-bottom:solid 1px #666666; } table{ border-left:solid 1px #666666;border-top:solid 1px #666666; }</style>");
        var trObj = $(ele).find("tr");
        var htmStr = "<table width='100%' cellspacing=0 cellpadding=5><tr><td>注号</td><td>过关方式</td><td>注项内容</td><td>奖金</td></tr>";
        for (var i = 0; i < trObj.length; i++) {
            htmStr += "<tr>";
            var tdObj = trObj.eq(i).find("td");
            for (var j = 0; j < tdObj.length; j++) {
                htmStr += "<td>" + tdObj.eq(j).html() + "</td>";
            }
            htmStr == "</tr>";
        }
        htmStr += "</table>";
        doc.write(htmStr);
        doc.close();
        var frameWindow = iframe.contentWindow;
        frameWindow.close();
        frameWindow.focus();
        frameWindow.print();
    }
    var removePrintArea = function(id) {
        $("iframe#" + id).remove();
    };
})(jQuery);