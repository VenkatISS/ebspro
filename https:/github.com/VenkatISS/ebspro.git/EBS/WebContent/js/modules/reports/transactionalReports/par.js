var tOpeningStock=0;
var tPurchases=0;
var tPurchasesreturn=0;
var tSales=0;
var tSalesReturn=0;
var tClosingStock=0;
var tGrossprofit=0;

if(rrt>0) {

	document.getElementById("pa_data_div").style.display="block";
	var tbody = document.getElementById('pa_report_table_body');
	var pc = 0;
	var pname = "";
	for(var prop in pa_data){
		pc = 1;
		var propDetails = pa_data[prop];
		if(prop<100) {
			pname = fetchProductDetails(cylinder_types_list,prop);
		} else if(prop>100){
			pname = fetchARBProductDetails(arb_data,prop);
		}
		var gp = round(((+propDetails.totalSalesAmount) + (+propDetails.totalPurchaseReturnsAmount) - (+propDetails.totalPurchasesAmount) - (+propDetails.totalSalesReturnsAmount)),2);
	 
		tOpeningStock=(+(tOpeningStock)+ +(propDetails.openingStockF)).toFixed(2);
		tPurchases=(+(tPurchases)+ +(propDetails.totalPurchasesAmount)).toFixed(2);
		tPurchasesreturn=(+(tPurchasesreturn)+ +(propDetails.totalPurchaseReturnsAmount)).toFixed(2);
		tSales=(+(tSales)+ +(propDetails.totalSalesAmount)).toFixed(2);
		tSalesReturn=(+(tSalesReturn)+ +(propDetails.totalSalesReturnsAmount)).toFixed(2);
		tClosingStock=(+(tClosingStock)+ +(propDetails.closingStockF)).toFixed(2);
		tGrossprofit=(+(tGrossprofit)+ +(gp)).toFixed(2);

		var tblRow = tbody.insertRow(-1);
	   tblRow.style.height="30px";
	   tblRow.align="top";
	   tblRow.innerHTML = "<td>" + pname + "</td>" + "<td>" + propDetails.openingStockF + "</td>" +
	   "<td>" + propDetails.totalPurchasesAmount + "</td>" + "<td>" + propDetails.totalPurchaseReturnsAmount +  "</td>" + 
	   "<td>" + propDetails.totalSalesAmount +  "</td>" + "<td>" + propDetails.totalSalesReturnsAmount + "</td>" + "<td>" + propDetails.closingStockF +  "</td>" + 
	   "<td>" + gp +  "</td>"; 
	   tgp = (+(tgp)) + (+(gp));
	}
	if (pc==0) {
		document.getElementById("pa_data_div").style.display="none";

		   var tblRow = document.getElementById('errmsg');
		   tblRow.style.width="80%";
		   tblRow.align="top";
		   tblRow.style.color="red";
		   tblRow.innerHTML = "NO RECORDS FOUND";
	} else {
		document.getElementById("tgpSpan").innerHTML=Math.round(tgp);
	}
}

function fetchPAReport(){
	var formobj = document.getElementById("pa_form");
	var monn = formobj.sm.selectedIndex;
	var mon = formobj.sm.options[monn].value;
	var yrn = formobj.sy.selectedIndex;
	var yr = formobj.sy.options[yrn].value;
	var fDay = 0;
	var lDay = 0;
	var lastDay = new Date(yr,mon,0);
	formobj.fd.value = yr+"-"+mon+"-01";
	formobj.td.value = yr+"-"+mon+"-"+lastDay.getDate();

	ems = validateEntries(monn,yrn);
	document.getElementById("errmsg").style.display="none";

	if(ems.length>0) {
		document.getElementById("dialog-1").innerHTML = ems;
		alertdialogue();
		//alert(ems);
		document.getElementById("pa_data_div").style.display="none";
		return;
	}
		
	var cdate=new Date();
	var cmonth=cdate.getMonth()+1;
	var cyear = cdate.getFullYear();
	
	if((monn > cmonth && (parseInt(yr)) > cyear) || (monn > cmonth && (parseInt(yr)) === cyear) ||
			(parseInt(yr)) > cyear) {
		document.getElementById("dialog-1").innerHTML = "PROFITABILITY ANALYSIS can't be future";
		alertdialogue();
		//alert("PROFITABILITY ANALYSIS can't be future");
		document.getElementById("pa_data_div").style.display="none";
		return false;
	}else{

	formobj.submit();
	return true;

	}
}

function validateEntries(monn,yrn){
	var formobj = document.getElementById('pa_form');
	var errmsg = "";

	if(!(monn>0))
		errmsg = errmsg + "Please select Month<br>";
	
	if(!(yrn>0))
		errmsg = errmsg+"Please select  Year<br>";
	else if (parseInt(formobj.sy.value) == 2017 && parseInt(monn) < 7) {
		errmsg = errmsg+ "Select MONTH and YEAR from JULY,2017 onwards .<br>";
	}
	return errmsg;
}
