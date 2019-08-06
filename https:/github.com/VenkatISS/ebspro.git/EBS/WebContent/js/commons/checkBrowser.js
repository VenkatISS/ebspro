function checkBrowser() {

	navigator.sayswho= (function(){
		var ua= navigator.userAgent, tem,
		M= ua.match(/(opera|chrome|safari|firefox|msie|trident(?=\/))\/?\s*(\d+)/i) || [];
		if(/trident/i.test(M[1])){
			tem=  /\brv[ :]+(\d+)/g.exec(ua) || [];
			return 'IE '+(tem[1] || '');
		}
		if(M[1]=== 'Chrome'){
			tem= ua.match(/\b(OPR|Edge)\/(\d+)/);
			if(tem!= null) return tem.slice(1).join(' ').replace('OPR', 'Opera');
		}
		M= M[1]? [M[1]]: [navigator.appVersion, '-?'];
		if((tem= ua.match(/version\/(\d+)/i))!= null) M.splice(1, 1, tem[1]);
		return M.join(' ');
	})();
//  alert(navigator.sayswho);

	if((jQuery.browser.mobile == true) || (!(navigator.sayswho == "Chrome" ||  navigator.sayswho == "Firefox"))){
		window.location.assign("https://www.mylpgbooks.com/ebs/jsp/pages/bcompatibility.jsp");
	}

//  working
//  if(!(!!window.chrome && !!window.chrome.webstore)) {
//  	window.location.assign("https://www.mylpgbooks.com/ebs/jsp/pages/bcompatibility.jsp");
//	}

}


function setwidthHeight(w) {
	if((window.screen.width < 1600) && (window.screen.height < 900)) {
		$(document).ready(function() {
			$("body").css("width",w);
			$('.wrapper').css("overflow","hidden");
			$('.wraper').css("overflow","visible"); // In BankBook Report
			$(".navbar").css("margin-right","1%");
			$("html").css("margin-right","1%");
			$(".hdlogo").css("width","230px");

			if((window.screen.width < 1400) && (window.screen.width > 1024)) {
				$("aside").css("width","200px");
				$('.hdlogo').css("cssText", "width: 200px !important;");
				$(".headermenu").css("padding","5px 11px");
				$(".sidebar").css("overflow","hidden");
				$(".stoggle").css("margin-left","-9%");
				$(".mcls").css("font-size","12px");
				$(".mcls").css("font-weight","bold");
				$(".sidebar-menu .treeview-menu>li>a").css("font-size","11px");
				$('.bg_details').css("cssText", "margin-top:-5% !important;margin-bottom:-0.2%;");

				$(".alertmsg").css("font-size","13px");
				$(".dsply").css("font-size","13px");
			}else {
				$("aside").css("width","190px");
				$('.hdlogo').css("cssText", "width: 190px !important;");
				$(".headermenu").css("font-size","13px");
				$(".headermenu").css("font-weight","500");
				$(".headermenu").css("padding","5px 8px");
				$(".headermenu").css("margin-top","10px");
				$(".bg_details").css("padding","55px 15px 15px 250px");
				$(".dwnlds").css("margin-left","-7%");
				$(".stoggle").css("margin-left","-7%");
				$(".hdcpur").css("margin-left","-1%");
				$('.hmwbox').css("cssText", "margin-left: -5.5% !important;");
				$(".headerop").css("margin-left","-35px");
				$(".mcls").css("font-size","11px");
				$(".mcls").css("font-weight","bold");
				$(".sidebar-menu .treeview-menu>li>a").css("font-size","10px");
				$(".sidebar-menu .treeview-menu>li>a").css("font-weight","bold");
			}
		});
	}
}
function setWidthHightNav(w) {
	if((window.screen.width < 1600) && (window.screen.height < 900)) {
		$(document).ready(function() {
			$("body").css("width",w);
			$('.wrapper').css("overflow","hidden");
			$('.wraper').css("overflow","visible"); // In BankBook Report
			$(".hdlogo").css("width","230px");

			if((window.screen.width < 1400) && (window.screen.width > 1024)) {
				$("body").css("font-size","12.5px");
				$("aside").css("width","200px");
				$('.hdlogo').css("cssText", "width: 200px !important;");
				$(".headermenu").css("padding","5px 11px");
				$(".sidebar").css("overflow","hidden");
				$(".stoggle").css("margin-left","-9%");
				$(".mcls").css("font-size","12px");
				$(".mcls").css("font-weight","bold");
				$(".sidebar-menu .treeview-menu>li>a").css("font-size","11px");
				$(".sidebar-menu .treeview-menu>li>a").css("font-weight","bold");
				$(".content-wrapper").css("margin-left","200px");
			}else {
				$("body").css("font-size","11px");
/*				$("aside").css("width","183px");
				$('.hdlogo').css("cssText", "width: 183px !important;");*/
				$("aside").css("width","190px");
				$('.hdlogo').css("cssText", "width: 190px !important;");				
				$(".headermenu").css("font-size","13px");
				$(".headermenu").css("font-weight","500");
				$(".headermenu").css("padding","5px 8px");
				$(".headermenu").css("margin-top","10px");
				$(".stoggle").css("margin-left","-7%");
				$(".hdcpur").css("margin-left","-1%");
//				$(".headerds").css("margin-left","-35px");
				$(".headerop").css("margin-left","-35px");
/*				$(".content-wrapper").css("margin-left","183px");				*/
				$(".content-wrapper").css("margin-left","190px");
				$(".mcls").css("font-size","11px");
				$(".mcls").css("font-weight","bold");
				$(".sidebar-menu .treeview-menu>li>a").css("font-size","10px");
				$(".sidebar-menu .treeview-menu>li>a").css("font-weight","bold");
			}
		});
	}
}