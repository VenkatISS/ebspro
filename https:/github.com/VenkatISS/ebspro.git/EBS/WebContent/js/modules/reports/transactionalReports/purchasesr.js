//To get Product name
if( !((pcodep == 0)||(pcodep == null)) ){
    if(cat_types_data.length>0) {
    	for(var z=0; z<cat_types_data.length; z++){
    		if(cat_types_data[z].id == pcodep){
    			ptypsel = cat_types_data[z].cat_name+"-"+cat_types_data[z].cat_desc;
    			break;
    		}
    	}
    }
    if(arb_types_data.length>0) {
    	for(var z=0; z<arb_types_data.length; z++){
    		if(arb_types_data[z].id == pcodep){
    			ptypsel = getARBType(arb_types_data[z].prod_code)+" - "+arb_types_data[z].prod_brand+" - "+arb_types_data[z].prod_name;
    			break;
    		}
    	}
    }
    document.getElementById("type_selected").innerHTML = ptypsel;
 }

//To get Party name
var cvo_cat =- 1;
if(!( (vcodep == 0) || (vcodep == null) ) ){
	if(cvo_data.length>0) {
		for(var z=0; z<cvo_data.length; z++){
			if((cvo_data[z].id == vcodep)) {
				ptypsel = cvo_data[z].cvo_name;
				cvo_cat = cvo_data[z].cvo_cat;
				break;
			}
		}
	}
	if(cvo_cat != 2)
		document.getElementById("type_selected").innerHTML = ptypsel;
	else if(cvo_cat == 2)
		document.getElementById("type_selected").innerHTML = omc_name +"-"+ ptypsel;		
}


var tqty=0;
var tQtyPrice=0;
var tigstamt=0;
var tsgstamt=0;
var tcgstamt=0;
var ttaxamt=0;
var tOtherCharges=0;

var tinvamt=0;


if(rrtype != 0) {
	if(report_data.length === 0 && arb_report_data.length === 0) {
		 var tblRow = document.getElementById('errmsg');
		   tblRow.style.width="80%";
		   tblRow.align="center";
		   tblRow.style.color="red";
		   tblRow.innerHTML = "NO RECORDS FOUND";
	}else{
		document.getElementById("p_data_table").style.display="block";
		var tbody = document.getElementById('p_data_table_body');
		if(rrtype==1 || rrtype==3) {
			if(report_data.length>0) {
				for(var f=0; f<report_data.length; f++) {
					var invdate = new Date(report_data[f].inv_date);
					var pidv = report_data[f].prod_code;
					var spd = fetchProductDetails(cat_types_data, report_data[f].prod_code);
//					var vd = fetchVendorName(cvo_data,report_data[f].omc_id);
					
					var vendorName = omc_name;
					for(var c=0;c<cvo_data.length; c++){
						if((cvo_data[c].id == report_data[f].omc_id)) {
							cvo_cat = cvo_data[c].cvo_cat;
						}
					}
					if(cvo_cat != 2)
						vendorName = fetchVendorName(cvo_data,report_data[f].omc_id);
					else if(cvo_cat == 2)
						vendorName = omc_name + "-" + fetchVendorName(cvo_data,report_data[f].omc_id);
					
					var vdgstn = fetchVendorGSTN(cvo_data,report_data[f].omc_id);        		
					var igsta = report_data[f].igst_amount;
					var cgsta = report_data[f].cgst_amount;
					var sgsta = report_data[f].sgst_amount;
					var tgsta = (+igsta) + (+cgsta) + (+sgsta);
					
					var taxableAmount = (report_data[f].c_amount - cgsta - sgsta - igsta-report_data[f].other_charges).toFixed(2);
					tQtyPrice=(+(tQtyPrice)+ +((report_data[f].quantity)*(report_data[f].unit_price))).toFixed(2);
					tigstamt=(+(tigstamt)+ +(igsta)).toFixed(2);
					tsgstamt=(+(tsgstamt)+ +(sgsta)).toFixed(2);
					tcgstamt=(+(tcgstamt)+ +(cgsta)).toFixed(2);
					tqty=+(tqty)+ +(report_data[f].quantity);
					ttaxamt=(+(ttaxamt)+ +(taxableAmount)); 	
					tOtherCharges=(+(tOtherCharges)+ +(report_data[f].other_charges));
					tinvamt=(+(tinvamt)+ +(report_data[f].c_amount)).toFixed(2);  
					
					var tblRow = tbody.insertRow(-1);
					tblRow.align="left";
					tblRow.innerHTML = "<tr>"+
				   		"<td>"+ report_data[f].inv_ref_no +"</td>"+
				   		"<td>"+ invdate.getDate()+"-"+months[invdate.getMonth()]+"-"+invdate.getFullYear() +"</td>"+
				   		"<td>"+ spd +"</td>"+
				   		"<td>"+ fetchHSNCode(equipment_data,pidv) +"</td>"+
				   		"<td>"+ fetchGSTPercent(equipment_data,pidv) +"</td>"+
				   		"<td>"+ vendorName +"</td>"+
				   		"<td>"+ vdgstn +"</td>"+
				   		"<td>"+ report_data[f].quantity +"</td>"+
				   		"<td>"+ eus[fetchUnitCode(equipment_data,pidv)] +"</td>"+
				   	 	"<td>"+ report_data[f].unit_price +"</td>"+
				   	 	"<td>"+ report_data[f].basic_amount +"</td>"+
				   	  	"<td>"+ report_data[f].igst_amount +"</td>"+
				   	  	"<td>"+ report_data[f].cgst_amount +"</td>"+
				   	  	"<td>"+ report_data[f].sgst_amount +"</td>"+
				   	  	"<td>"+ report_data[f].other_charges +"</td>"+
				   		"<td>"+ report_data[f].c_amount +"</td>"+
				   		"</tr>";
         	};
    	}
    }
    if(rrtype==2 || rrtype==3) {
    	if(arb_report_data.length>0) {
        	for(var f=0; f<arb_report_data.length; f++){
        		var pidv = arb_report_data[f].arb_code;
        		var invdate = new Date(arb_report_data[f].inv_date);
        		var srdate = new Date(arb_report_data[f].stk_recvd_date);
        		var spd = fetchARBProductDetails(arb_types_data, arb_report_data[f].arb_code);
//        		var vd = fetchVendorName(cvo_data,arb_report_data[f].vendor_id);

        		var vname = omc_name;
				if(cvo_cat != 2)
					vname = fetchVendorName(cvo_data,arb_report_data[f].vendor_id);
				else if(cvo_cat == 2)
					vname = omc_name + "-" + fetchVendorName(cvo_data,arb_report_data[f].vendor_id);
				
        		var vdgstn = fetchVendorGSTN(cvo_data,arb_report_data[f].vendor_id);
        		var igsta = arb_report_data[f].igst_amount;
        		var cgsta = arb_report_data[f].cgst_amount;
        		var sgsta = arb_report_data[f].sgst_amount;
        		var tgsta = (+igsta) + (+cgsta) + (+sgsta);
        		
        		
        		var taxableAmount = (arb_report_data[f].c_amount - cgsta - sgsta - igsta-arb_report_data[f].other_charges).toFixed(2);
				tQtyPrice=(+(tQtyPrice)+ +((arb_report_data[f].quantity)*(arb_report_data[f].unit_price))).toFixed(2);
				tigstamt=(+(tigstamt)+ +(igsta)).toFixed(2);
				tsgstamt=(+(tsgstamt)+ +(sgsta)).toFixed(2);
				tcgstamt=(+(tcgstamt)+ +(cgsta)).toFixed(2);
				tqty=+(tqty)+ +(arb_report_data[f].quantity);
				ttaxamt=(+(ttaxamt)+ +(taxableAmount)); 
				tOtherCharges=(+(tOtherCharges)+ +(arb_report_data[f].other_charges));
				tinvamt=(+(tinvamt)+ +(arb_report_data[f].c_amount)).toFixed(2);
   		
        		
        		var tblRow = tbody.insertRow(-1);
			   	tblRow.style.height="30px";
			   	tblRow.align="left";
			   	tblRow.innerHTML = "<tr>"+
			   		"<td>"+ arb_report_data[f].inv_ref_no +"</td>"+
			   		"<td>"+ invdate.getDate()+"-"+months[invdate.getMonth()]+"-"+invdate.getFullYear() +"</td>"+
			   		"<td>"+ spd +"</td>"+
			   		"<td>"+ fetchARBHSNCode(arb_types_data,pidv) +"</td>"+
			   		"<td>"+ fetchARBGSTPercent(arb_types_data,pidv) +"</td>"+
			   		"<td>"+ vname +"</td>"+
			   		"<td>"+ vdgstn +"</td>"+
			   		"<td>"+ arb_report_data[f].quantity +"</td>"+
			   		"<td>"+ eus[fetchARBUnitCode(arb_types_data,pidv)] +"</td>"+
			   	 	"<td>"+ arb_report_data[f].unit_price +"</td>"+
			   	 	"<td>"+ arb_report_data[f].basic_amount +"</td>"+
			   	  	"<td>"+ arb_report_data[f].igst_amount +"</td>"+
			   	  	"<td>"+ arb_report_data[f].cgst_amount +"</td>"+
			   	  	"<td>"+ arb_report_data[f].sgst_amount +"</td>"+
			   	  	"<td>"+ arb_report_data[f].other_charges +"</td>"+
			   		"<td>"+ arb_report_data[f].c_amount +"</td>"+
				   	"</tr>";
        	};	
    	}
    }
    
    tQtyPrice=(tQtyPrice/tqty).toFixed(2);
    tOtherCharges=tOtherCharges.toFixed(2);
    ttaxamt=ttaxamt.toFixed(2);
  
	}
}

function fetchPurchaseReport() {
	var formobj = document.getElementById('purchase_search_form');
	var ems = "";
	var fd = formobj.fd.value.trim();
	var td = formobj.td.value.trim();	
	var spid = formobj.pid.selectedIndex;
	var spv = formobj.pv.selectedIndex;

	if(checkDateFormate(fd)){
		var ved = dateConvert(fd);
		fd=ved;
	}
	if(checkDateFormate(td)){
		var ved = dateConvert(td);
		td=ved;
	}
	
	ems = validateEntries(fd,td,spid,spv);
	if(ems.length>0) {
		document.getElementById("dialog-1").innerHTML = ems;
		alertdialogue();
		//alert(ems);
		document.getElementById("p_data_table").style.display="none";
		return;
	}
	
	formobj.submit();
}



function validateEntries(fd,td,spid,spv){
	var formobj = document.getElementById('purchase_search_form');
	var errmsg = "";

	var chkd = checkdate(td);
	var vfd = ValidateFutureDate(td);		
	if(!(td.length>0))
		errmsg = errmsg+"Please Enter TO DATE<br>";
	else if(chkd != "true"){
		errmsg = errmsg+chkd+"<br>";
		formobj.td.value="";
	}else if(vfd != "false"){
		errmsg = errmsg+"TO DATE Can't Be Future Date<br>";
		formobj.td.value="";
	}	
	
	var checkd = checkdate(fd);
	var ctd = comparisionofTwoDates(fd,td);	
	var fvfd = ValidateFutureDate(fd);
	if(!(fd.length>0))
		errmsg = errmsg+"Please Enter From Date<br>";
	else if(checkd != "true") {
		errmsg = errmsg+checkd+"<br>";
		formobj.fd.value="";
		formobj.td.value="";
	}else if(fvfd != "false"){
		errmsg = errmsg+"FROM DATE Can't Be Future Date<br>";
		formobj.td.value="";
	}else if(ctd != "false"&& td.length>0) {
		errmsg = errmsg + ctd +"<br>";
		formobj.fd.value="";
		formobj.td.value="";
	}

	if(!spid>0 && !spv>0){
		errmsg = errmsg + "Please Select Either PRODUCT / VENDOR <br>";
	}
	if(spid>0 && spv>0){
		errmsg = errmsg + "Please Select Either PRODUCT / VENDOR <br>";
	}

	return errmsg;
}