/*
var isCtrl = false;
var isShift = false;
$(document).ready(function() {
	var code = new Array();
	$(document).keydown(function(e) {
		code=e.keyCode;
		alert(code);
	});	
});	
*/

var code = new Array();
var href;
$(document).ready(function() {
	$(document).keydown(function(e) {
		code.push(e.keyCode);
		checkKey(code);
				
	});	
	function checkKey(code) {	
		if(code.includes(67) && code.includes(80) && code.includes(18)) {
			//alt(18)+c(67)+p(80) -->CYLLINDER PURCHASES
			href= "MasterDataControlServlet";	
			document.getElementById('forwardForm').action = href;
			document.getElementById('forwardForm').page.value = "jsp/pages/erp/transactions/purchases/c_purchases.jsp";   
			document.getElementById('forwardForm').actionId.value = "5101";		
			document.getElementById('forwardForm').submit();
		}
		if(code.includes(68) && code.includes(83) && code.includes(18)) {
			//alt(18)+s(83)+d(68)--> DOM REFILL SALES
			href= "MasterDataControlServlet";
			document.getElementById('forwardForm').action = href;
			document.getElementById('forwardForm').page.value = "jsp/pages/erp/transactions/sales/dom_refill_sales.jsp";   
			document.getElementById('forwardForm').actionId.value = "5121";		
			document.getElementById('forwardForm').submit();
		}
		if(code.includes(67) && code.includes(83) && code.includes(18)) {
			//alt+s(83)+c(67)-->COM REFILL SALES 
			href= "MasterDataControlServlet";	
			document.getElementById('forwardForm').action = href;
			document.getElementById('forwardForm').page.value = "jsp/pages/erp/transactions/sales/com_refill_sales.jsp";   
			document.getElementById('forwardForm').actionId.value = "5126";		
			document.getElementById('forwardForm').submit();
		}
		if(code.includes(78) && code.includes(67) && code.includes(18)) {
			//alt+n(78)+c(67)-->NC/DBC
			href= "MasterDataControlServlet";	
			document.getElementById('forwardForm').action = href;
			document.getElementById('forwardForm').page.value = "jsp/pages/erp/transactions/ncordbc/nc_dbc.jsp";   
			document.getElementById('forwardForm').actionId.value = "5721";		
			document.getElementById('forwardForm').submit();
		}
		if(code.includes(82) && code.includes(67) && code.includes(18)) {
			//alt+r(82)+c(67)-->RC
			href= "MasterDataControlServlet";	
			document.getElementById('forwardForm').action = href;
			document.getElementById('forwardForm').page.value = "jsp/pages/erp/transactions/ncordbc/rc.jsp";   
			document.getElementById('forwardForm').actionId.value = "5711";		
			document.getElementById('forwardForm').submit();
		}
		if(code.includes(82) && code.includes(80) && code.includes(18)) {
			//alt+r(82)+p(80)-->Receipts
			href= "MasterDataControlServlet";	
			document.getElementById('forwardForm').action = href;
			document.getElementById('forwardForm').page.value = "jsp/pages/erp/cash&bank/receipts.jsp";   
			document.getElementById('forwardForm').actionId.value = "5501";		
			document.getElementById('forwardForm').submit();
		}
		if(code.includes(66) && code.includes(84) && code.includes(18)) {
			//alt+b(66)+t(84)-->Bank Transactions
			href= "MasterDataControlServlet";	
			document.getElementById('forwardForm').action = href;
			document.getElementById('forwardForm').page.value = "jsp/pages/erp/cash&bank/bank_tranxs.jsp";   
			document.getElementById('forwardForm').actionId.value = "5521";		
			document.getElementById('forwardForm').submit();
		}
	}
	
	$(document).keyup(function (e) {
		//code = [];
		code.length = 0;
		
	});
	
});	
