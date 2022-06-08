var pnObj = {
    pushNewsPosInterval: null,
    showPushNews: function() {
        //调用
        // $.getScript("https://noti.sporttery.cn/realtime_news/news.js", null);
    },
    closePushNews: function() {
        clearInterval(pnObj.pushNewsPosInterval);
        $("#pushNews").hide();
        pnObj.setCookie("pushNews", pnObj.getCookie("pushNews").split("_")[0] + "_1");
    },
    pushNewsPos: function() {
        var tLeft = ($(document).width() - $("#pushNews").width()) / 2, tTop = $(document).scrollTop() + $(window).height() - $("#pushNews").height(), oLeft = $("#pushNews").offset().left, oTop = $("#pushNews").offset().top;
        if (oLeft > tLeft + 5 || oLeft < tLeft - 5 || oTop > tTop + 5 || oTop < tTop - 5) {
            clearInterval(pnObj.pushNewsPosInterval);
            $("#pushNews").animate({ top: $(document).scrollTop() + $(window).height() - ($("#pushNews").height() + 1), left: ($(document).width() - $("#pushNews").width()) / 2 }, "fast", function() {
                pnObj.pushNewsPosInterval = setInterval(pnObj.pushNewsPos, 50);
            });
        }
    },
    UrlDecode: function(zipStr) {
        var uzipStr = "";
        for (var i = 0; i < zipStr.length; i++) {
            var chr = zipStr.charAt(i);
            if (chr == "+") {
                uzipStr += " ";
            } else if (chr == "%") {
                var asc = zipStr.substring(i + 1, i + 3);
                if (parseInt("0x" + asc) > 0x7f) {
                    uzipStr += decodeURI("%" + asc.toString() + zipStr.substring(i + 3, i + 9).toString());
                    i += 8;
                } else {
                    uzipStr += pnObj.AsciiToString(parseInt("0x" + asc));
                    i += 2;
                }
            } else {
                uzipStr += chr;
            }
        }
        return uzipStr;
    },
    AsciiToString: function(asccode) {
        return String.fromCharCode(asccode);
    },
    setCookie: function(name, value) {
        var Days = 30; //此 cookie 将被保存 30 天
        var exp = new Date();    //new Date("December 31, 9998");
        exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
        document.cookie = name + "=" + escape(value) + ";path=/;expires=" + exp.toGMTString();
    },
    getCookie: function(name)//取cookies函数        
    {
        var arr = document.cookie.match(new RegExp("(^| )" + name + "=([^;]*)(;|$)"));
        if (arr != null) return unescape(arr[2]); return null;
    },
    delCookie: function(name)//删除cookie
    {
        var exp = new Date();
        exp.setTime(exp.getTime() - 1);
        var cval = getCookie(name);
        if (cval != null) document.cookie = name + "=" + cval + ";path=/;expires=" + exp.toGMTString();
    }
};
function push_news(obj) {
    if ($("#pushNews").length == 0) {
        $("body").append("<table cellspacing='0' id='pushNews' style='position:absolute;top:0px;display:none;zindex:101;vertical-align:middle;font-size:16px;font-weight:bold;padding:0px;'><tr><td style='width:106px;line-height:82px; background-image:url(//static.sporttery.cn/images/main/push_news2.png); background-repeat:no-repeat; background-position:right top;'></td><td style='background-image:url(//static.sporttery.cn/images/main/push_news4.png); background-repeat:repeat-x;'><a style=' margin-top:30px; font-family:微软雅黑; padding-left:8px;padding-right:8px; display:inline-block; color:#FFF; text-decoration:none;' target='_blank' onclick=pnObj.closePushNews()>中国竞彩网</a></td><td align='center' valign='top' style='width:38px; height:82px; background-image:url(//static.sporttery.cn/images/main/push_news3.png); background-repeat:no-repeat;'><span onclick=pnObj.closePushNews() onmouseover=this.style.color='#000000' onmouseout=this.style.color='#FF0000' style='cursor:pointer; display:inline-block; margin-top:29px; color:Red;'>×</span></td></tr></table>");
        setInterval(function() {
            if ($("#pushNews:visible").length > 0) {
                $("#pushNews label:eq(0)").fadeOut(600).fadeIn(600);
            }
        }, 1200);
    }
    if (obj.title == undefined) {
        $("#pushNews").hide();
        $("#pushNews a").text("");
    } else {
        var cookieValue = null;
        if (pnObj.getCookie("pushNews") != null) {
            cookieValue = pnObj.getCookie("pushNews").split("_");
        }
        if (cookieValue != null && obj.title == cookieValue[0] && cookieValue[1] == "1") {
        } else if (obj.title != $("#pushNews a").text()) {
            $("#pushNews a").text(obj.title);
            //位置
            $("#pushNews").offset({ top: $(document).scrollTop() + $(window).height(), left: ($(document).width() - $("#pushNews").width()) / 2 });
            //
            $("#pushNews").show();
            $("#pushNews").animate({ "top": "-=" + ($("#pushNews").height() + 1) + "px" }, "slow", function() {
                pnObj.pushNewsPosInterval = setInterval(pnObj.pushNewsPos, 50);
            });
            $("#pushNews a").attr("href", pnObj.UrlDecode(obj.url));
            //
            pnObj.setCookie("pushNews", obj.title + "_0");
        }
    }
    setTimeout(pnObj.showPushNews, 180000);
}
pnObj.showPushNews();
