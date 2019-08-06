
//Construct Customer Type html
custdatahtml = "<OPTION VALUE='-1'>SELECT</OPTION>";
custdatahtml = custdatahtml+"<OPTION VALUE='0'>CASH SALES / HOUSEHOLDS</OPTION>";
if(cvo_data.length>0) {
	for(var z=0; z<cvo_data.length; z++){
		if(cvo_data[z].cvo_cat==1 && cvo_data[z].deleted==0 && cvo_data[z].cvo_name != "UJWALA")
			custdatahtml=custdatahtml+"<OPTION VALUE='"+cvo_data[z].id+"'>"+cvo_data[z].cvo_name+"</OPTION>";
	}
}


//---------------------------------PURCHASES-----------------------------------------


//Fetch Cylinder Purchase
/*
function showCPFormDialog() {
	document.getElementById('cppopupModal').style.display = "block";
 $("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');
}

function closeCPFormDialog() {
//	document.getElementById("data_form").reset();
	document.getElementById('cppopupModal').style.display = "none";
$("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');
}

 var rowpdhtml = "";
 if(purchases_data.length>0) {
 for(var f=purchases_data.length-1; f>=0; f--){
 	var omcName = getCustomerName(cvo_data,purchases_data[f].omc_id);
 	var invdate = new Date(purchases_data[f].inv_date);
 	var srdate = new Date(purchases_data[f].stk_recvd_date);
 	var spd = fetchProductDetails(cat_cyl_types_data, purchases_data[f].prod_code);
 	var eord=purchases_data[f].emr_or_delv;
 	if(eord==1) {
 		eord = "ONE WAY LOAD";
 	}else if(eord==2){
 		eord = "TWO WAY LOAD";
 	} 
 	rowpdhtml = rowpdhtml + '<tr><td>'+ omc_name +"-"+ omcName +'</td>';
 	rowpdhtml = rowpdhtml + '<td>'+ purchases_data[f].inv_ref_no +'</td>';
 	rowpdhtml = rowpdhtml + '<td>'+ invdate.getDate()+"-"+months[invdate.getMonth()]+"-"+invdate.getFullYear() +'</td>';
 	rowpdhtml = rowpdhtml + '<td>'+ srdate.getDate()+"-"+months[srdate.getMonth()]+"-"+srdate.getFullYear() +'</td>';
 	rowpdhtml = rowpdhtml + '<td>'+ spd +'</td>'+
     		'<td>'+ eord +'</td>'+
     		'<td>'+ purchases_data[f].unit_price +'</td>'+
     		'<td>'+ purchases_data[f].quantity +'</td>'+
     		'<td>'+ purchases_data[f].gstp +'</td>'+
     		'<td>'+ purchases_data[f].basic_amount +'</td>'+
 		    '<td>'+ purchases_data[f].igst_amount + '</td>'+
 			'<td>'+ purchases_data[f].sgst_amount +'</td>'+
 			'<td>'+ purchases_data[f].cgst_amount +'</td>'+
 			'<td>'+ purchases_data[f].other_charges + '</td>'+
 			'<td>'+ purchases_data[f].c_amount +'</td></tr>';
  }
 }
 document.getElementById('cp_data_table_body').innerHTML = rowpdhtml;





//Fetch ARB Purchase page data


function showARBPFormDialog() {
	document.getElementById('arbppopupModal').style.display = "block";
 $("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');
}

function closeARBPFormDialog() {
//	document.getElementById("data_form").reset();
	document.getElementById('arbppopupModal').style.display = "none";
$("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');
}

 var  rowpdhtml = "";
 if(arbPurchaseData.length>0) {
 for(var f=arbPurchaseData.length-1; f>=0; f--) {
 	var invdate = new Date(arbPurchaseData[f].inv_date);
 	var srdate = new Date(arbPurchaseData[f].stk_recvd_date);
 	var spd = fetchARBProductDetails(cat_arb_types_data, arbPurchaseData[f].arb_code);
 	var vn = fetchVendorName(cvo_data, arbPurchaseData[f].vendor_id);
 	rowpdhtml = rowpdhtml + '<tr><td>'+ arbPurchaseData[f].inv_ref_no + '</td>';
 	rowpdhtml = rowpdhtml + '<td>'+ invdate.getDate()+"-"+months[invdate.getMonth()]+"-"+invdate.getFullYear() +'</td>';
 	rowpdhtml = rowpdhtml +	'<td>' + srdate.getDate()+"-"+months[srdate.getMonth()]+"-"+srdate.getFullYear() +'</td>';
 	rowpdhtml = rowpdhtml + '<td>'+ spd +'</td>'+
    		'<td>'+ vn +'</td>'+
    		'<td>'+ rcs[arbPurchaseData[f].reverse_charge] +'</td>'+
    		'<td>'+ arbPurchaseData[f].unit_price + '</td>'+
    		'<td>'+ arbPurchaseData[f].quantity + '</td>'+
    		'<td>'+ arbPurchaseData[f].gstp + '</td>'+
    		'<td>'+ arbPurchaseData[f].basic_amount +'</td>'+
    		'<td>'+ arbPurchaseData[f].igst_amount +'</td>'+
    		'<td>'+ arbPurchaseData[f].sgst_amount + '</td>'+
    		'<td>'+ arbPurchaseData[f].cgst_amount +'</td>'+
    		'<td>'+ arbPurchaseData[f].other_charges +'</td>'+
    		'<td>'+ arbPurchaseData[f].c_amount + '</td>'+'</tr>'
 	   
 }
}
 document.getElementById('arbp_data_table_body').innerHTML = rowpdhtml;




*/

// -------------------------DOM SALES ---------------------------------------------------
document.getElementById("vSpan").innerHTML = "<select id='cust_id' name='cust_id' class='form-control input_field' onchange='changePmntTerms()'>"+custdatahtml+"</select>";


//Fetch DOM Sales page data
/*new 23032018*/

function showDSFormDialog() {
	document.getElementById('dspopupModal').style.display = "block";
	pushMenu();
}

function closeDSFormDialog() {
//	document.getElementById("data_form").reset();
	document.getElementById('dspopupModal').style.display = "none";
    $("body").removeClass('sidebar-collapse').trigger('expanded.pushMenu');
}



//var tbody = document.getElementById('m_data_table_body');
var rowpdhtml = "";
for(var z = dom_salesInv.length-1 ; z >= 0 ; z--) {
	var ed = new Date(dom_salesInv[z].si_date);
	var custName = getCustomerName(cvo_data,dom_salesInv[z].customer_id);
	var dlistLen=dom_salesInv[z].detailsList.length;
	for(var i=0; i<dom_salesInv[z].detailsList.length; i++){
		var spd = fetchProductDetails(cat_cyl_types_data, dom_salesInv[z].detailsList[i].prod_code);
		var staffName = getStaffName(staff_data,dom_salesInv[z].detailsList[i].staff_id);
		var areaName = getAreaCodeName(area_codes_data,dom_salesInv[z].detailsList[i].ac_id);
		var bankName = getBankName(bank_data,dom_salesInv[z].detailsList[i].bank_id);
	
		if(!staffName){				
			staffName = "NA";			
		}			
		if(!areaName){				
			areaName = "NA";			
		}
		if(!bankName){				
			bankName = "NA";			
		}
		if(dom_salesInv[z].deleted==2){
			rowpdhtml = rowpdhtml + "<tr><td>"+dom_salesInv[z].sr_no+"</td>";
		}else {
			rowpdhtml = rowpdhtml + "<tr><td>"+dom_salesInv[z].sr_no+"</td>";
		}

		rowpdhtml = rowpdhtml + "<td>"+ed.getDate()+"-"+months[ed.getMonth()]+"-"+ed.getFullYear()+"</td>";
		rowpdhtml = rowpdhtml + "<td>"+custName+"</td>";
		rowpdhtml = rowpdhtml + "<td>"+payment_terms_types[dom_salesInv[z].payment_terms]+"</td>";	
		rowpdhtml = rowpdhtml + "<td>"+dom_salesInv[z].cash_amount+"</td>";
		rowpdhtml = rowpdhtml + "<td>"+dom_salesInv[z].si_amount+"</td>";
		rowpdhtml = rowpdhtml + "<td>"+spd+"</td>"+
			"<td>"+dom_salesInv[z].detailsList[i].unit_rate+"</td>"+
			"<td>"+dom_salesInv[z].detailsList[i].disc_unit_rate+"</td>"+
			"<td>"+dom_salesInv[z].detailsList[i].quantity+"</td>"+
			"<td>"+dom_salesInv[z].detailsList[i].igst_amount+"</td>"+				
			"<td>"+dom_salesInv[z].detailsList[i].cgst_amount+"</td>"+
			"<td>"+dom_salesInv[z].detailsList[i].sgst_amount+"</td>"+
			"<td>"+dom_salesInv[z].detailsList[i].sale_amount+"</td>"+
			"<td>"+dom_salesInv[z].detailsList[i].pre_cyls+"</td>"+
			"<td>"+dom_salesInv[z].detailsList[i].psv_cyls+"</td>"+
			"<td>"+staffName+"</td>"+
			"<td>"+areaName+"</td>"+
			"<td>"+bankName+"</td>"+
			"<td>"+dom_salesInv[z].detailsList[i].amount_rcvd_online+"</td></tr>";
	}
}

document.getElementById('ds_data_table_body').innerHTML = rowpdhtml;

//Fetch COM Sales page data

function showCSFormDialog() {
	document.getElementById('cspopupModal').style.display = "block";
    $("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');
}

function closeCSFormDialog() {
//	document.getElementById("data_form").reset();
	document.getElementById('cspopupModal').style.display = "none";
$("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');
}

var rowpdhtml = "";
if(com_salesInv.length>0) {
	for(var z=com_salesInv.length-1; z>=0; z--){
		var ed = new Date(com_salesInv[z].si_date);
		var custName = getCustomerName(cvo_data,com_salesInv[z].customer_id);
		var dlistLen = com_salesInv[z].detailsList.length;
		for(var i=0; i<com_salesInv[z].detailsList.length; i++){
			var spd = fetchProductDetails(cat_cyl_types_data, com_salesInv[z].detailsList[i].prod_code);
			var staffName = getStaffName(staff_data,com_salesInv[z].detailsList[i].staff_id);
			var areaName = getAreaCodeName(area_codes_data,com_salesInv[z].detailsList[i].ac_id);
			var bankName = getBankName(bank_data,com_salesInv[z].detailsList[i].bank_id);
			/*var del = "<td align='center'><img src='images/delete.png' onclick='deleteItem("+com_salesInv[z].id+","+com_salesInv[z].si_date+","+dlistLen+")'></td>";
			if(com_salesInv[z].deleted==2){
				del = "<td>TRANSACTION CANCELED</td>";
			}*/
			if(!staffName){				
				staffName = "NA";			
			}
			if(!areaName){
				areaName = "NA";			
			}
			
			if(!bankName){				
				bankName = "NA";			
			}
			
			if(com_salesInv[z].deleted==2){
				rowpdhtml = rowpdhtml + "<tr valign='top'><td>"+com_salesInv[z].sr_no+"</td>";
			}else {
				rowpdhtml = rowpdhtml + "<tr valign='top'><td>"+com_salesInv[z].sr_no+"</td>";
			}
			rowpdhtml = rowpdhtml + "<td>"+ed.getDate()+"-"+months[ed.getMonth()]+"-"+ed.getFullYear()+"</td>";
			rowpdhtml = rowpdhtml + "<td>"+custName+"</td>";
			rowpdhtml = rowpdhtml + "<td>"+payment_terms_types[com_salesInv[z].payment_terms]+"</td>";
			rowpdhtml = rowpdhtml + "<td>"+com_salesInv[z].si_amount+"</td>";

			rowpdhtml = rowpdhtml + "<td>"+spd+"</td>"+
				"<td>"+com_salesInv[z].detailsList[i].unit_rate+"</td>"+
				"<td>"+com_salesInv[z].detailsList[i].disc_unit_rate+"</td>"+
				"<td>"+com_salesInv[z].detailsList[i].quantity+"</td>"+
				"<td>"+com_salesInv[z].detailsList[i].igst_amount+"</td>"+
				"<td>"+com_salesInv[z].detailsList[i].cgst_amount+"</td>"+
				"<td>"+com_salesInv[z].detailsList[i].sgst_amount+"</td>"+
				"<td>"+com_salesInv[z].detailsList[i].sale_amount+"</td>"+
				"<td>"+com_salesInv[z].detailsList[i].pre_cyls+"</td>"+
				"<td>"+com_salesInv[z].detailsList[i].psv_cyls+"</td>"+
				"<td>"+bankName+"</td>"+
				"<td>"+staffName+"</td>"+
				"<td>"+areaName+"</td>"+
				/*del+*/"</tr>";
		}
	}
}
document.getElementById('cs_data_table_body').innerHTML = rowpdhtml;




//Fetch ARB page data

function showARBFormDialog() {
	document.getElementById('arbpopupModal').style.display = "block";
    $("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');

}

function closeARBFormDialog() {
//	document.getElementById("data_form").reset();
	document.getElementById('arbpopupModal').style.display = "none";
$("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');
}


var rowpdhtml = "";
if(arb_salesInv.length>0) {
	for(var z=arb_salesInv.length-1 ; z>= 0; z--) {
		var ed = new Date(arb_salesInv[z].si_date);
		var custName = getCustomerName(cvo_data,arb_salesInv[z].customer_id);
		var staffName = getStaffName(staff_data,arb_salesInv[z].staff_id);

		for(var i=arb_salesInv[z].detailsList.length-1 ; i>=0; i--){
		var bankName = getBankName(bank_data,arb_salesInv[z].detailsList[i].bank_id);
		var dlistLen = arb_salesInv[z].detailsList.length;
		
		if(!staffName) {
			staffName = "NA";			
		}
		
		if(!bankName){				
			bankName = "NA";			
		}
		
			var spd = fetchARBProductDetails(cat_arb_types_data, arb_salesInv[z].detailsList[i].prod_code);
			//if(i==0) {

			if(arb_salesInv[z].deleted==2){
				rowpdhtml = rowpdhtml + "<tr><td>"+arb_salesInv[z].sr_no+"</td>";
			}else {
				rowpdhtml = rowpdhtml + "<tr><td>"+arb_salesInv[z].sr_no+"</td>";
			}
			rowpdhtml = rowpdhtml + "<td>"+ed.getDate()+"-"+months[ed.getMonth()]+"-"+ed.getFullYear()+"</td>";
			rowpdhtml = rowpdhtml + "<td>"+custName+"</td>";
			rowpdhtml = rowpdhtml + "<td>"+staffName+"</td>";
			rowpdhtml = rowpdhtml + "<td>"+payment_terms_types[arb_salesInv[z].payment_terms]+"</td>";
			rowpdhtml = rowpdhtml + "<td>"+arb_salesInv[z].si_amount+"</td>";
				rowpdhtml = rowpdhtml + "<td>"+spd+"</td>"+
					"<td>"+arb_salesInv[z].detailsList[i].unit_rate+"</td>"+
					"<td>"+arb_salesInv[z].detailsList[i].disc_unit_rate+"</td>"+
					"<td>"+arb_salesInv[z].detailsList[i].quantity+"</td>"+
					"<td>"+bankName+"</td>"+
					"<td>"+arb_salesInv[z].detailsList[i].igst_amount+"</td>"+					
					"<td>"+arb_salesInv[z].detailsList[i].cgst_amount+"</td>"+
					"<td>"+arb_salesInv[z].detailsList[i].sgst_amount+"</td>"+
					"<td>"+arb_salesInv[z].detailsList[i].sale_amount+"</td>"+
					/*del+*/ "</tr>";
			
		}
	}
}
document.getElementById('arb_data_table_body').innerHTML = rowpdhtml;







