//Construct Category Type html
			ctdatahtml = "<OPTION VALUE='0'>SELECT</OPTION>";
			if(cat_types_data.length>0) {
				for(var z=0; z<cat_types_data.length; z++){
					if(cat_types_data[z].id<10) {
						ctdatahtml=ctdatahtml+"<OPTION VALUE='"+cat_types_data[z].id+"'>"+cat_types_data[z].cat_name+"-"+cat_types_data[z].cat_desc+"</OPTION>";
					}
				}
			}

			if(arb_types_data.length>0) {
				for(var z=0; z<arb_types_data.length; z++){
					//ctdatahtml=ctdatahtml+"<OPTION VALUE='"+arb_types_data[z].id+"'>"+arb_types_data[z].cat_code+"-"+arb_types_data[z].cat_name+"-"+arb_types_data[z].cat_desc+"</OPTION>";
					ctdatahtml=ctdatahtml+"<OPTION VALUE='"+arb_types_data[z].id+"'>"
					+getARBType(arb_types_data[z].prod_code)+" - "+arb_types_data[z].prod_brand+" - "+arb_types_data[z].prod_name
					+"</OPTION>";

				}
			}

var prodcode = sreport_data.productCode;
var pname = "";
if(prodcode < 100) {
	pname=fetchProductDetails(cat_types_data,prodcode);
} else if (prodcode > 100){
	pname=fetchARBProductDetails(arb_types_data,prodcode);
}
document.getElementById("prod_selected").innerHTML = pname;

document.getElementById("pitems").innerHTML="<select name='pid' class='form-control input_field select_dropdown' id='pid'>"+ctdatahtml+"</select></td>";
if(showr>0){
	document.getElementById("p_data_table_div").style.display="block";
	var tbody = document.getElementById('p_data_table_body');
   	var tblRow = tbody.insertRow(-1);
   	tblRow.style.height="30px";
   	tblRow.align="left";
   	tblRow.innerHTML =  "<td>"+ sreport_data.openingStockF +"</td>" 
   			+ "<td>" + sreport_data.totalPurchases + "</td><td>" + sreport_data.totalPurchaseReturns + "</td><td>" + sreport_data.totalSales + "</td>"
   			+ "<td>"+ sreport_data.totalSalesReturns + "</td><td>"+ sreport_data.closingStockF +"</td><td>"+ sreport_data.closingStockE +"</td>" 
   			+ "<td>"+ ((+sreport_data.closingStockF) + (+sreport_data.closingStockE)) +"</td>"; 
}
function fetchStockReport() {
	var formobj = document.getElementById('stock_search_form');
	var ems = "";
	var fd = formobj.fd.value.trim();
	var td = formobj.td.value.trim();
	var pid = formobj.pid.selectedIndex;
	if(checkDateFormate(fd)){
		var ved = dateConvert(fd);
		fd=ved;
	}
	if(checkDateFormate(td)){
		var ved = dateConvert(td);
		td=ved;
	}
	ems = validateEntries(fd,td,pid);
	if(ems.length>0) {
		document.getElementById("dialog-1").innerHTML = ems;
		alertdialogue();
		//alert(ems);
		document.getElementById("p_data_table_div").style.display="none";
		return;
	}
	formobj.submit();
}
function validateEntries(fd,td,pid){
	var formobj = document.getElementById('stock_search_form');
	var errmsg = "";
		
	var chkd = checkdate(td);
	var vfd = ValidateFutureDate(td);	
	if(!(td.length>0))
		errmsg = errmsg+"Please Enter TO DATE<br>";	
	else if(chkd != "true"){
		errmsg = errmsg+chkd+"<br>";
		formobj.td.value="";
	}else if(vfd != "false") {
		errmsg = errmsg+"TO DATE Can't Be Future Date<br>";
		formobj.td.value="";
	}	

		
	var checkd = checkdate(fd);
	var ctd = comparisionofTwoDates(fd,td);
	var fvfd = ValidateFutureDate(fd);
	if(!(fd.length>0))
		errmsg = errmsg+"Please Enter FROM DATE<br>";
	else if(checkd != "true") {
		errmsg = errmsg+checkd+"<br>";
		formobj.fd.value="";
		formobj.td.value="";
	}else if(fvfd != "false") {
		errmsg = errmsg+"FROM DATE Can't Be Future Date<br>";
		formobj.td.value="";
	}else if(ctd != "false" && td.length>0 ) {
		errmsg = errmsg + ctd +"<br>";
		formobj.fd.value="";
		formobj.td.value="";
	}

	if(!(pid>0))
		errmsg = errmsg + "Please Select PRODUCT<br>";
	return errmsg;
}