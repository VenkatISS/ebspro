
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






