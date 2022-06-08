var isMobile = {
    Android: function() {
        return navigator.userAgent.match(/Android/i);
    },
    BlackBerry: function() {
        return navigator.userAgent.match(/BlackBerry/i);
    },
    iOS: function() {
        return navigator.userAgent.match(/iPhone|iPad|iPod/i);
    },
    Opera: function() {
        return navigator.userAgent.match(/Opera Mini/i);
    },
    Windows: function() {
        return navigator.userAgent.match(/IEMobile/i);
    },
    any: function() {
        return (isMobile.Android() || isMobile.BlackBerry() || isMobile.iOS() || isMobile.Opera() || isMobile.Windows());
    }
};
/*导航下拉条*/
$(document).ready(function(){
	$("#navmenu ul li:has(ul)").hover(function() {
		$(this).addClass("active");
		if($(this).find("li").length > 0){
			$(this).children("ul").stop(true, true).slideDown(200)} 
			},
			function() {
				$(this).removeClass("active");;
				$(this).children("ul").stop(true, true).slideUp(0)
				});
			})

