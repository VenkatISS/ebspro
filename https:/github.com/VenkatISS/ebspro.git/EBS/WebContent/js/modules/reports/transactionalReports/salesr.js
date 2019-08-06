//To get Product name
if( !((pcodes == 0)||(pcodes == null)) ){
    if(cat_types_data.length>0) {
    	for(var z=0; z<cat_types_data.length; z++){
    		if(cat_types_data[z].id == pcodes){
    			stypsel = cat_types_data[z].cat_name+"-"+cat_types_data[z].cat_desc;
    			break;
    		}
    	}
    }
    if(arb_types_data.length>0) {
    	for(var z=0; z<arb_types_data.length; z++){
    		if(arb_types_data[z].id == pcodes){
    			stypsel = getARBType(arb_types_data[z].prod_code)+" - "+arb_types_data[z].prod_brand+" - "+arb_types_data[z].prod_name;
    			break;
    		}
        }
    }
    document.getElementById("type_selected").innerHTML = stypsel;
 }

//To get Party name
if(!( (scodes == 0) || (scodes == null) ) ){
	if(staff_data.length>0) {
		for(var z=0; z<staff_data.length; z++){
			if((staff_data[z].id == scodes)) {
				stypsel = staff_data[z].emp_name;
				break;
			}
		}
	}
	document.getElementById("type_selected").innerHTML = stypsel;
}


var rowpdhtml = "";
var tqty=0;
var tQtyPrice=0;
var tDisPrice=0;
var tigstamt=0;
var tsgstamt=0;
var tcgstamt=0;
var ttaxamt=0;
var tinvamt=0;

if(rrtype != 0) {
	if(dcs_report_data.length === 0 && ccs_report_data.length === 0 && arb_report_data.length === 0 && ncdbc_arb_report_data.length === 0 ) {
		 var tblRow = document.getElementById('errmsg');
		 tblRow.style.width="80%";
		 tblRow.align="center";
		 tblRow.style.color="red";
		 tblRow.innerHTML = "NO RECORDS FOUND";
	}else {
		document.getElementById("p_data_table").style.display="block";
		var tbody = document.getElementById('p_data_table_body');
		if(rrtype==1 || rrtype==9) {
			if(dcs_report_data.length>0) {
				for(var f=0; f<dcs_report_data.length; f++){
					var sid = new Date(dcs_report_data[f].si_date);
					var spd = fetchProductDetails(cat_types_data, dcs_report_data[f].prod_code);

					for(var i=0; i<dcs_report_data[f].detailsList.length; i++){
						var pidv = dcs_report_data[f].detailsList[i].prod_code;
						var qty = dcs_report_data[f].detailsList[i].quantity - dcs_report_data[f].detailsList[i].psv_cyls;
						var pur = dcs_report_data[f].detailsList[i].unit_rate; 
						var spd = fetchProductDetails(cat_types_data, pidv);
						var staffName1 = getStaffName(staff_data,dcs_report_data[f].detailsList[i].staff_id);
						var staffName = "";
						if(staffName1 === undefined){
							staffName = "NA";
						}else{ 
							staffName = staffName1;
						}
						var custName = "HOUSEHOLDS";
						var custGSTIN = "-";
						if(dcs_report_data[f].customer_id>0){
							custName = getCustomerName(cvo_data,dcs_report_data[f].customer_id);
							custGSTIN = getCustomerGSTIN(cvo_data,dcs_report_data[f].customer_id);
						}
						var igsta = dcs_report_data[f].detailsList[i].igst_amount;
						var cgsta = dcs_report_data[f].detailsList[i].cgst_amount;
						var sgsta = dcs_report_data[f].detailsList[i].sgst_amount;
						var tgsta = +(cgsta) + +(sgsta);
						var taxableAmount = (dcs_report_data[f].detailsList[i].sale_amount - cgsta - sgsta - igsta).toFixed(2);

						tDisPrice=(+(tDisPrice)+ +(qty*dcs_report_data[f].detailsList[i].disc_unit_rate).toFixed(2));
						tQtyPrice=(+(tQtyPrice)+ +(qty*pur)).toFixed(2);
						tigstamt=(+(tigstamt)+ +(igsta)).toFixed(2);
						tsgstamt=(+(tsgstamt)+ +(sgsta)).toFixed(2);
						tcgstamt=(+(tcgstamt)+ +(cgsta)).toFixed(2);
						tqty=+(tqty)+ +(qty);
						ttaxamt=(+(ttaxamt)+ +(taxableAmount));
						tinvamt=(+(tinvamt)+ +(dcs_report_data[f].detailsList[i].sale_amount)).toFixed(2);
	   				
						rowpdhtml = rowpdhtml + "<tr valign='top'><td>"+dcs_report_data[f].sr_no+"</td>"+ 
	   								"<td>"+sid.getDate()+"-"+months[sid.getMonth()]+"-"+sid.getFullYear()+"</td>" + 
	   								"<td>"+spd+"</td>"+
	   								"<td>"+fetchHSNCode(equipment_data,pidv)+"</td>"+
	   								"<td>"+fetchGSTPercent(equipment_data,pidv)+"</td>"+
	   								"<td>"+custName+"</td>"+
	   								"<td>"+custGSTIN+"</td>"+
	   								"<td>"+qty+"</td>"+
	   								"<td>"+eus[fetchUnitCode(equipment_data,pidv)]+"</td>"+
	   								"<td>"+pur+"</td>"+
	   								"<td>"+dcs_report_data[f].detailsList[i].disc_unit_rate+"</td>"+
	   								"<td>"+ taxableAmount +"</td>"+
	   								"<td>"+dcs_report_data[f].detailsList[i].igst_amount+"</td>"+

	   								"<td>"+dcs_report_data[f].detailsList[i].cgst_amount+"</td>"+
	   								"<td>"+dcs_report_data[f].detailsList[i].sgst_amount+"</td>"+
	   								"<td>"+dcs_report_data[f].detailsList[i].sale_amount+"</td>"+
	   								"<td>"+staffName+"</td>"+
	   								"</tr>";
        				
					}
        		};
        	}
    	}

		if(rrtype==2 || rrtype==9) {
			if(ccs_report_data.length>0) {
				for(var f=0; f<ccs_report_data.length; f++){
					var sid = new Date(ccs_report_data[f].si_date);
					var spd = fetchProductDetails(cat_types_data, ccs_report_data[f].prod_code);
				
					for(var i=0; i<ccs_report_data[f].detailsList.length; i++) {
						var spd = fetchProductDetails(cat_types_data, ccs_report_data[f].detailsList[i].prod_code);
						var pidv = ccs_report_data[f].detailsList[i].prod_code;
						var qty = ccs_report_data[f].detailsList[i].quantity - ccs_report_data[f].detailsList[i].pre_cyls;
						var pur = ccs_report_data[f].detailsList[i].unit_rate; 
						var staffName1 = getStaffName(staff_data,ccs_report_data[f].detailsList[i].staff_id);
						var staffName = "";
						if(staffName1 === undefined){
							staffName = "NA";
						}else{ 
							staffName = staffName1;
						}
						var igsta = ccs_report_data[f].detailsList[i].igst_amount;
						var cgsta = ccs_report_data[f].detailsList[i].cgst_amount;
						var sgsta = ccs_report_data[f].detailsList[i].sgst_amount;
						var tgsta = +(cgsta) + +(sgsta);
						var custName = "HOUSEHOLDS";
						var custGSTIN = "-";
						if(ccs_report_data[f].customer_id>0){
							custName = getCustomerName(cvo_data,ccs_report_data[f].customer_id);
							custGSTIN = getCustomerGSTIN(cvo_data,ccs_report_data[f].customer_id);
						}	

						var taxableAmount = (ccs_report_data[f].detailsList[i].sale_amount - cgsta - sgsta - igsta).toFixed(2);

						tDisPrice=(+(tDisPrice)+ +(qty*ccs_report_data[f].detailsList[i].disc_unit_rate).toFixed(2));
						tQtyPrice=(+(tQtyPrice)+ +(qty*pur)).toFixed(2);
						tigstamt=(+(tigstamt)+ +(igsta)).toFixed(2);
						tsgstamt=(+(tsgstamt)+ +(sgsta)).toFixed(2);
						tcgstamt=(+(tcgstamt)+ +(cgsta)).toFixed(2);
						tqty=+(tqty)+ +(qty);
						ttaxamt=(+(ttaxamt)+ +(taxableAmount));
						tinvamt=(+(tinvamt)+ +(ccs_report_data[f].detailsList[i].sale_amount)).toFixed(2);

						rowpdhtml = rowpdhtml + "<tr valign='top'>"+
    						"<td>"+ccs_report_data[f].sr_no+"</td>"+
    						"<td>"+sid.getDate()+"-"+months[sid.getMonth()]+"-"+sid.getFullYear()+"</td>" +
    						"<td>"+spd+"</td>"+
    						"<td>"+fetchHSNCode(equipment_data,pidv)+"</td>"+
    						"<td>"+fetchGSTPercent(equipment_data,pidv)+"</td>"+
    						"<td>"+custName+"</td>"+
    						"<td>"+custGSTIN+"</td>"+
    						"<td>"+qty+"</td>"+
    						"<td>"+eus[fetchUnitCode(equipment_data,pidv)]+"</td>"+
    						"<td>"+pur+"</td>"+
    						"<td>"+ccs_report_data[f].detailsList[i].disc_unit_rate+"</td>"+
    						"<td>"+taxableAmount+"</td>"+
    						"<td>"+ ccs_report_data[f].detailsList[i].igst_amount +"</td>"+
    						"<td>"+ ccs_report_data[f].detailsList[i].cgst_amount +"</td>"+
    						"<td>"+ ccs_report_data[f].detailsList[i].sgst_amount +"</td>"+
    						"<td>"+ ccs_report_data[f].detailsList[i].sale_amount +"</td>"+
    						"<td>"+ staffName+"</td>"+
    						"</tr>";
					}	
				};
			}
		}
    if(rrtype==3 || rrtype==9) {
		if(arb_report_data.length>0) {
			for(var f=0; f<arb_report_data.length; f++){
				var sid = new Date(arb_report_data[f].si_date);
				var spd = fetchProductDetails(cat_types_data, arb_report_data[f].prod_code);

				for(var i=0; i<arb_report_data[f].detailsList.length; i++){
					var spd = fetchARBProductDetails(arb_types_data, arb_report_data[f].detailsList[i].prod_code);
					var pidv = arb_report_data[f].detailsList[i].prod_code;
					var qty = arb_report_data[f].detailsList[i].quantity;
					var pur = arb_report_data[f].detailsList[i].unit_rate; 
					var pdur = arb_report_data[f].detailsList[i].disc_unit_rate; 
					var staffName1 = getStaffName(staff_data,arb_report_data[f].staff_id);
					var staffName = "";
    				if(staffName1 === undefined){
    					staffName = "NA";
    				}else{ 
    					staffName = staffName1;
    				}
    				var igsta = arb_report_data[f].detailsList[i].igst_amount;
					var cgsta = arb_report_data[f].detailsList[i].cgst_amount;
					var sgsta = arb_report_data[f].detailsList[i].sgst_amount;
					var tgsta = +(cgsta) + +(sgsta);
					var custName = "HOUSEHOLDS";
					var custGSTIN = "-";
					if(arb_report_data[f].customer_id>0){
						custName = getCustomerName(cvo_data,arb_report_data[f].customer_id);
						custGSTIN = getCustomerGSTIN(cvo_data,arb_report_data[f].customer_id);
					}
					var taxableAmount = (arb_report_data[f].detailsList[i].sale_amount - cgsta - sgsta - igsta).toFixed(2);
					
					tigstamt=(+(tigstamt)+ +(igsta)).toFixed(2);
					tsgstamt=(+(tsgstamt)+ +(sgsta)).toFixed(2);
					tcgstamt=(+(tcgstamt)+ +(cgsta)).toFixed(2);
					tqty=+(tqty)+ +(qty);
					tDisPrice=(+(tDisPrice)+ +(qty*pdur).toFixed(2));
					tQtyPrice=(+(tQtyPrice)+ +(qty*pur)).toFixed(2);
					ttaxamt=(+(ttaxamt)+ +(taxableAmount));
					tinvamt=(+(tinvamt)+ +(arb_report_data[f].detailsList[i].sale_amount)).toFixed(2);

    				rowpdhtml = rowpdhtml + "<tr valign='top'>"+
    					"<td>"+arb_report_data[f].sr_no+"</td>"+
 		       			"<td>"+sid.getDate()+"-"+months[sid.getMonth()]+"-"+sid.getFullYear()+"</td>"+ 
 		       			"<td>"+ spd +"</td>"+
 		       			"<td>"+ fetchARBHSNCode(arb_types_data,pidv)+"</td>"+
 		       			"<td>"+ fetchARBGSTPercent(arb_types_data,pidv) +"</td>"+
 		       			"<td>"+ custName +"</td>"+
 		       			"<td>"+ custGSTIN +"</td>"+
 		       			"<td>"+ qty +"</td>"+
 		       			"<td>"+ eus[fetchARBUnitCode(arb_types_data,pidv)] +"</td>"+
 		       			"<td>"+ pur +"</td>"+
 		       			"<td>"+ pdur+"</td>"+
 		       			"<td>"+ taxableAmount +"</td>"+
 		       			"<td>"+ arb_report_data[f].detailsList[i].igst_amount +"</td>"+
 		       			"<td>"+ arb_report_data[f].detailsList[i].cgst_amount +"</td>"+
 		       			"<td>"+ arb_report_data[f].detailsList[i].sgst_amount +"</td>"+
 		       			"<td>"+arb_report_data[f].detailsList[i].sale_amount+"</td>"+
 		       			"<td>"+staffName+"</td>"+
 		       			"</tr>";
    			}
    		};
    	}
		if(ncdbc_arb_report_data.length>0) {
            for(var f=0; f<ncdbc_arb_report_data.length; f++){
            	var sid = new Date(ncdbc_arb_report_data[f].inv_date);
            	for(var i=0; i<ncdbc_arb_report_data[f].detailsList.length; i++) {
            		
            		var spd = fetchARBProductDetails(arb_types_data, ncdbc_arb_report_data[f].detailsList[i].prod_code);
            		var pidv = ncdbc_arb_report_data[f].detailsList[i].prod_code;
            		var qty = ncdbc_arb_report_data[f].detailsList[i].quantity;
            		var pur = ncdbc_arb_report_data[f].detailsList[i].unit_rate;
            		var pdur = ncdbc_arb_report_data[f].detailsList[i].disc_unit_rate;
            		var staffName1 = getStaffName(staff_data,ncdbc_arb_report_data[f].staff_id);
            		var staffName = "";
            		
            		if(staffName1 === undefined){
            			staffName = "NA";
            		}else{
            			staffName = staffName1;
            		}
            		var igsta = 0;
            		var cgsta = ncdbc_arb_report_data[f].detailsList[i].cgst_amount;
            		var sgsta = ncdbc_arb_report_data[f].detailsList[i].sgst_amount;
            		var tgsta = +(cgsta) + +(sgsta);
            		var custName = "NCDBC SALES";
            		var custGSTIN = "-";
            		var taxableAmount = (ncdbc_arb_report_data[f].detailsList[i].product_amount - cgsta - sgsta - igsta).toFixed(2);
            		
            		tsgstamt=(+(tsgstamt)+ +(sgsta)).toFixed(2);
            		tcgstamt=(+(tcgstamt)+ +(cgsta)).toFixed(2);
            		tqty=+(tqty)+ +(qty);
            		tDisPrice=(+(tDisPrice)+ +(qty*pdur).toFixed(2));
            		tQtyPrice=(+(tQtyPrice)+ +(qty*pur)).toFixed(2);
            		ttaxamt=(+(ttaxamt)+ +(taxableAmount));
            		tinvamt=(+(tinvamt)+ +(ncdbc_arb_report_data[f].detailsList[i].product_amount)).toFixed(2);
            		
            		rowpdhtml = rowpdhtml + "<tr valign='top'><td>"+ncdbc_arb_report_data[f].sr_no+"</td>"+
            			"<td>"+sid.getDate()+"-"+months[sid.getMonth()]+"-"+sid.getFullYear()+"</td>"+
            			"<td>"+spd+"</td>"+
            			"<td>"+ fetchARBHSNCode(arb_types_data,pidv) +"</td>"+
            			"<td>"+ fetchARBGSTPercent(arb_types_data,pidv) +"</td>"+
            			"<td>"+custName+"</td>"+
            			"<td>"+custGSTIN+"</td>"+
            			"<td>"+qty+"</td>"+
            			"<td>"+eus[fetchARBUnitCode(arb_types_data,pidv)]+"</td>"+
            			"<td>"+pur+"</td>"+
            			"<td>"+pdur+"</td>"+
            			"<td>" + taxableAmount + "</td>" +
            			"<td>"+igsta+"</td>"+
            			"<td>"+ncdbc_arb_report_data[f].detailsList[i].cgst_amount+"</td>"+
            			"<td>"+ncdbc_arb_report_data[f].detailsList[i].sgst_amount+"</td>"+
            			"<td>"+ncdbc_arb_report_data[f].detailsList[i].product_amount+"</td>"+
            			"<td>"+staffName+"</td>"+
            			"</tr>";
            	}
            };
		}
    }
    tQtyPrice=(tQtyPrice/tqty).toFixed(2);
    tDisPrice=tDisPrice.toFixed(2);
    ttaxamt=ttaxamt.toFixed(2);

    document.getElementById('p_data_table_body').innerHTML = rowpdhtml;
	}
}

function fetchSaleReport() {
	var formobj = document.getElementById('sales_search_form');
	var ems = "";
	var fd = formobj.fd.value.trim();
	var td = formobj.td.value.trim();
	var spid = formobj.pid.selectedIndex;
	var spv = formobj.pv.selectedIndex;
	document.getElementById("errmsg").style.display="none";

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
	var formobj = document.getElementById('sales_search_form');
	var errmsg = "";
	
	var checkd = checkdate(fd);
	var vfd1 = ValidateFutureDate(fd);
	var ctd = comparisionofTwoDates(fd,td);
	if(!(fd.length>0))
		errmsg = errmsg+"Please enter From Date <br>";
	else if(checkd != "true") {
		errmsg = errmsg+checkd+"<br>";
		formobj.fd.value="";
		formobj.td.value="";
	}else if(ctd != "false" && td.length>0) {
		errmsg = errmsg + ctd +"<br>";
		formobj.fd.value="";
		formobj.td.value="";
	}else if(vfd1 != "false" ) {
		errmsg = errmsg+"From Date Can't Be future Date<br>";
		formobj.td.value="";
	}	
	
	
	var chkd = checkdate(td);
	var vfd = ValidateFutureDate(td);	
	if(!(td.length>0))
		errmsg = errmsg+"Please enter To Date<br>";	
	else if(chkd != "true"){
		errmsg = errmsg+chkd+"<br>";
		formobj.td.value="";
	}else if(vfd != "false") {
		errmsg = errmsg+"To Date Can't Be Future Date<br>";
		formobj.td.value="";
	}	
		
	
	if(!spid>0 && !spv>0){
		errmsg = errmsg + "Please select either Product / Delivered By <br>";
	}
	if(spid>0 && spv>0){
		errmsg = errmsg + "Please select either Product / Delivered By <br>";
	}
	return errmsg;
}