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
		window.location.assign("https://www.ilpg.in/ebs/jsp/pages/bcompatibility.jsp");
	}

//  working
//  if(!(!!window.chrome && !!window.chrome.webstore)) {
//  	window.location.assign("https://www.ilpg.in/ebs/jsp/pages/bcompatibility.jsp");
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
//				$('.hmwbox').css("cssText", "margin-left: -2.9% !important;");
//				$('.hmwbox').css("cssText", "margin-left: 1% !important;");
				$('.bg_details').css("cssText", "margin-top:-5% !important;margin-bottom:-0.2%;");
				$(".hmalerts").css("margin-top","-70px");
				$(".hmalerts").css("margin-left","-30%");
				$(".hmnews").css("margin-left","-30%");
//              $(".hmalerts").css("width","130%");
				$(".hmalerts").css("width","122%");
				$(".hmalmrq").css("width","90%");
//              $(".hmnews").css("width","130%");
				$(".hmnews").css("width","122%");
				if ($('.hmalerts').css('height') !== '100px') {
					$(".hmalerts").css("height","380px");
					$(".hmalmrq").css("height","220px");
					$("#news").css("height","240px");
				}
				$(".alertmsg").css("font-size","13px");
				$(".hmalrtsspn").css("font-size","12px");
				$(".hmnewsspn").css("font-size","12px");
				$(".hmalrtsspn").css("font-weight","bold");
				$(".hmnewsspn").css("font-weight","bold");
				$(".dsply").css("font-size","13px");
				$('.b-r-none').css("cssText", "float:right; margin-right:-0%;");
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
				$('.b-r-none').css("cssText", "float:right; margin-top:-5% ; margin-right:-6.5%;");
				$(".hmalrtsspn").css("font-size","11px");
				$(".hmnewsspn").css("font-size","11px");
				$(".hmalrtsspn").css("font-weight","bold");
				$(".hmnewsspn").css("font-weight","bold");
//				$(".hmnews").css("margin-left","-30%");
				$(".hmnews").css("margin-left","-79px");
				$(".dsply").css("font-size","13px");
				if ($('.hmalerts').css('height') !== '100px') {
					$(".hmalmrq").css("height","210px");
					$('.hmalerts').css("cssText", "margin-top:0px; margin-left:-79px ; height:325px;padding: 35px 5px 35px 20px !important;");
				}
				$(".alertmsg").css("font-size","12px");
//				$(".headerds").css("margin-left","-35px");
				$(".headerop").css("margin-left","-35px");
				$(".mcls").css("font-size","11px");
				$(".mcls").css("font-weight","bold");
				$(".sidebar-menu .treeview-menu>li>a").css("font-size","10px");
				$(".sidebar-menu .treeview-menu>li>a").css("font-weight","bold");
/*				$(".hmcontent").css("margin-left","8%");*/
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


/*function checkBrowser() {
	
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
//	alert(navigator.sayswho);

	if((jQuery.browser.mobile == true) || (!(navigator.sayswho == "Chrome" ||  navigator.sayswho == "Firefox"))){
		window.location.assign("https://www.ilpg.in/ebs/jsp/pages/bcompatibility.jsp");
	}

//	working
//	if(!(!!window.chrome && !!window.chrome.webstore)) {
//		window.location.assign("https://www.ilpg.in/ebs/jsp/pages/bcompatibility.jsp");
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
					$('.hmwbox').css("cssText", "margin-left: -2.9% !important;");
					$('.bg_details').css("cssText", "margin-top:-5% !important;margin-bottom:-0.2%;");
					$(".hmalerts").css("margin-top","-110px");
					$(".hmalerts").css("margin-left","-30%");
					$(".hmnews").css("margin-left","-30%");
//					$(".hmalerts").css("width","130%");
					$(".hmalerts").css("width","122%");
					$(".hmalmrq").css("width","90%");
//					$(".hmnews").css("width","130%");
					$(".hmnews").css("width","122%");
					if ($('.hmalerts').css('height') !== '100px') {
						$(".hmalerts").css("height","380px");
						$(".hmalmrq").css("height","220px");
						$("#news").css("height","240px");
					}
					$(".alertmsg").css("font-size","13px");
					$(".hmalrtsspn").css("font-size","12px");
					$(".hmnewsspn").css("font-size","12px");
					$(".hmalrtsspn").css("font-weight","bold");
					$(".hmnewsspn").css("font-weight","bold");	
					$(".dsply").css("font-size","13px");
					$('.b-r-none').css("cssText", "float:right; margin-right:-0%;");
				}else {
					$("aside").css("width","183px");
					$('.hdlogo').css("cssText", "width: 183px !important;");
					$(".headermenu").css("font-size","13px");
					$(".headermenu").css("font-weight","500");
					$(".headermenu").css("padding","5px 8px");
					$(".headermenu").css("margin-top","10px");
					$(".bg_details").css("padding","55px 15px 15px 250px");
					$(".dwnlds").css("margin-left","-7%");
					$(".stoggle").css("margin-left","-7%");
					$(".hdcpur").css("margin-left","-1%");
					$('.hmwbox').css("cssText", "margin-left: -5.5% !important;");
					$('.b-r-none').css("cssText", "float:right; margin-top:-5% ; margin-right:-6.5%;");
					$(".hmalrtsspn").css("font-size","11px");
					$(".hmnewsspn").css("font-size","11px");
					$(".hmalrtsspn").css("font-weight","bold");
					$(".hmnewsspn").css("font-weight","bold");
					$(".hmnews").css("margin-left","-30%");
					$(".dsply").css("font-size","13px");
					if ($('.hmalerts').css('height') !== '100px') {
						$(".hmalmrq").css("height","210px");
						$('.hmalerts').css("cssText", "margin-top:-70px; margin-left:-30% ; height:325px;padding: 35px 5px 35px 20px !important;");
					}
					$(".alertmsg").css("font-size","12px");
//					$(".headerds").css("margin-left","-35px");
					$(".headerop").css("margin-left","-35px");					
					$(".mcls").css("font-size","11px");
					$(".mcls").css("font-weight","bold");
					$(".sidebar-menu .treeview-menu>li>a").css("font-size","10px");
					$(".sidebar-menu .treeview-menu>li>a").css("font-weight","bold");					
//					$(".hmcontent").css("margin-left","8%");
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
				$("aside").css("width","183px");
				$('.hdlogo').css("cssText", "width: 183px !important;");
				$(".headermenu").css("font-size","13px");
				$(".headermenu").css("font-weight","500");
				$(".headermenu").css("padding","5px 8px");
				$(".headermenu").css("margin-top","10px");
				$(".stoggle").css("margin-left","-7%");
				$(".hdcpur").css("margin-left","-1%");
//				$(".headerds").css("margin-left","-35px");
				$(".headerop").css("margin-left","-35px");
				$(".content-wrapper").css("margin-left","183px");
				$(".mcls").css("font-size","11px");
				$(".mcls").css("font-weight","bold");
				$(".sidebar-menu .treeview-menu>li>a").css("font-size","10px");
				$(".sidebar-menu .treeview-menu>li>a").css("font-weight","bold");
			}
		}); 	
	}
}*/