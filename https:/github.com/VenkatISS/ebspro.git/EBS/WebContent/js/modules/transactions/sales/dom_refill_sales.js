/*function showDOMRSalesFormDialog() {
	var check = checkDayEndDateForAdd(dedate,a_created_date);
	if(check==true) {
		document.getElementById('myModal').style.display = "block";
	}else {
		document.getElementById("dialog-1").innerHTML = "Please Submit Previous DayEnd inorder to add todays data";
		alertdialogue();
		//alert("Please Submit Previous DayEnd inorder to add todays data.");
		return;
	}
}*/


function showDOMRSalesFormDialog() {
	document.getElementById('myModal').style.display = "block";
	pushMenu();
}

function closeDOMRSalesFormDialog() {
	document.getElementById("calc_data").disabled = true;
	document.getElementById("save_data").disabled = true;
	var formobj = document.getElementById('data_form');	
	var elements = document.getElementsByClassName("qtyc");
	var efrz = document.getElementsByClassName("freez");
	remEleReadOnly(efrz,efrz.length);
	if (elements.length == 1) {
		var spfrz= document.getElementById("epid").options;
		enableSelect(spfrz,spfrz.length);

	} else if (elements.length > 1) {
		for (var i = 0; i < elements.length; i++) {
			var pfrz=formobj.epid[i].options;
			enableSelect(pfrz,pfrz.length);			
		}		
	}

	document.getElementById("data_form").reset();
	var tbody = document.getElementById('data_table_body');	
	var count = document.getElementById('data_table_body').getElementsByTagName('tr').length;
	for(var a=0; a<count; a++){
		if(a>0)
			tbody.deleteRow(-1);
	}			
	document.getElementById('myModal').style.display = "none";
    $("body").removeClass('sidebar-collapse').trigger('expanded.pushMenu');

}


/*new 23032018*/

function showCVOFormDialog() {
	document.getElementById('cvoModal').style.display = "block";
	document.getElementById('savemCVOPopdiv').style.display="none";
    $("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');
}

function closeCVOFormDialog() {
//	document.getElementById("data_form").reset();
	document.getElementById("mcvopopup_data_form").reset();
	document.getElementById('mymCVOPopupDIV').style.display = 'none';
	document.getElementById('mcvopopup_add_table_body').innerHTML = "";
	document.getElementById('cvoModal').style.display = "none";
	$("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');
}

function showEquipmentFormDialog() {
	document.getElementById('equipmentModal').style.display = "block";
	document.getElementById('mequipopupsavediv').style.display="none";
    $("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');
}

function closeEquipmentFormDialog() {
//	document.getElementById("data_form").reset();
	document.getElementById("mequipopup_page_add_form").reset();
	document.getElementById('mequipopup_page_add_table_body').innerHTML = "";
	document.getElementById('mequipopup_page_add_table_div').style.display = 'none';
	document.getElementById('equipmentModal').style.display = "none";
	$("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');
}


function showPriceFormDialog() {
	document.getElementById('refillPriceModal').style.display = "block";
	document.getElementById('myModalmRefilpricepopupPin').style.display = "block";
	document.getElementById('savemrefillpricepopupdiv').style.display = "none";
    $("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');
}

function closeRefillPriceFormDialog() {
//	document.getElementById("data_form").reset();
	document.getElementById("mrefiilpricepopup_page_data_form").reset();
	document.getElementById("pin_data_form").reset();
	document.getElementById('mrefillpricepopup_page_add_table_body').innerHTML = "";
	document.getElementById('mymRefillPricePopupDIV').style.display = 'none';
	document.getElementById('refillPriceModal').style.display = "none";
	$("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');
	document.getElementById('mrefillOfpNote').style.display="none";
}



function showBankFormDialog() {
	document.getElementById('bankModal').style.display = "block";
	document.getElementById('savembankpopupdiv').style.display = "none";
    $("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');
}

function closeBankFormDialog() {
//	document.getElementById("data_form").reset();
	document.getElementById("mpopup_bank_data_form").reset();
	document.getElementById('mpopup_bank_add_table_body').innerHTML = "";
	document.getElementById('bankModal').style.display = "none";
	document.getElementById('mymBankPopAddDIV').style.display = 'none';
	$("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');
}

function showStaffFormDialog() {
	document.getElementById('staffModal').style.display = "block";
	document.getElementById('mstaff_popupsavediv').style.display = "none";
    $("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');
}

function closeStaffFormDialog() {
//	document.getElementById("data_form").reset();
	document.getElementById("mpopupstaff_data_form").reset();
	document.getElementById('mpopup_staff_add_table_body').innerHTML = "";
	document.getElementById('staffModal').style.display = "none";
	document.getElementById('myStaffDataDIV').style.display = 'none';
	$("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');
}

function showAreaCodeFormDialog() {
	document.getElementById('areacodeModal').style.display = "block";
	document.getElementById('mareacode_popupsavediv').style.display = "none";
    $("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');
}

function closeAreaCodeFormDialog() {
//	document.getElementById("data_form").reset();
	document.getElementById("mareacode_page_data_form").reset();
	document.getElementById('mareacode_page_add_table_body').innerHTML = "";
	document.getElementById('areacodeModal').style.display = "none";
	document.getElementById('mareacode_page_add_table_div').style.display = 'none';
	$("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');
}

 /*new end*/

//Construct Customer Type html
custdatahtml = "<OPTION VALUE='-1'>SELECT</OPTION>";
custdatahtml = custdatahtml+"<OPTION VALUE='0'>CASH SALES / HOUSEHOLDS</OPTION>";
if(cvo_data.length>0) {
	for(var z=0; z<cvo_data.length; z++){
		if(cvo_data[z].cvo_cat==1 && cvo_data[z].deleted==0 && cvo_data[z].cvo_name != "UJWALA")
			custdatahtml=custdatahtml+"<OPTION VALUE='"+cvo_data[z].id+"'>"+cvo_data[z].cvo_name+"</OPTION>";
	}
}
document.getElementById("cSpan").innerHTML = "<select id='cust_id' name='cust_id' class='form-control input_field' onchange='changePmntTerms()'>"+custdatahtml+"</select>";

//set bank account id
if(bank_data.length>0){
	for(var y=0; y<bank_data.length; y++){
		if(bank_data[y].bank_code == "TAR ACCOUNT"){
			document.getElementById('data_form').bankId.value = bank_data[y].id;
			break;
		}
	}
}

//Process page data
var rowpdhtml = "";
if(page_data.length>0) {
	for(var z=page_data.length-1; z>=0; z--){
		var ed = new Date(page_data[z].si_date);
		var custName = getCustomerName(cvo_data,page_data[z].customer_id);
		var dlistLen=page_data[z].detailsList.length;
		for(var i=0; i<page_data[z].detailsList.length; i++){
			var spd = fetchProductDetails(cat_cyl_types_data, page_data[z].detailsList[i].prod_code);
			var staffName = getStaffName(staff_data,page_data[z].detailsList[i].staff_id);
			var areaName = getAreaCodeName(area_codes_data,page_data[z].detailsList[i].ac_id);
			var bankName = getBankName(bank_data,page_data[z].detailsList[i].bank_id);
			var del = "<td align='center'><img src='images/delete.png' onclick='deleteItem("+page_data[z].id+","+page_data[z].si_date+","+dlistLen+")'></td>";
			if(page_data[z].deleted==2){
				del = "<td>TRANSACTION CANCELED</td>";
			}
			if(!staffName){				
				staffName = "NA";			
			}			
			if(!areaName){				
				areaName = "NA";			
			}
			if(!bankName){				
				bankName = "NA";			
			}
			if(page_data[z].deleted==2){
				rowpdhtml = rowpdhtml + "<tr><td>"+page_data[z].sr_no+"</td>";
			}else {
				rowpdhtml = rowpdhtml + "<tr><td><a href='javascript:showInvoice("+page_data[z].id+","+page_data[z].si_date+")'>"+page_data[z].sr_no+"</a></td>";
			}

			rowpdhtml = rowpdhtml + "<td>"+ed.getDate()+"-"+months[ed.getMonth()]+"-"+ed.getFullYear()+"</td>";
			rowpdhtml = rowpdhtml + "<td>"+custName+"</td>";
			rowpdhtml = rowpdhtml + "<td>"+payment_terms_values[page_data[z].payment_terms]+"</td>";	
			rowpdhtml = rowpdhtml + "<td>"+page_data[z].cash_amount+"</td>";
			rowpdhtml = rowpdhtml + "<td>"+page_data[z].si_amount+"</td>";
			rowpdhtml = rowpdhtml + "<td>"+spd+"</td>"+
				"<td>"+page_data[z].detailsList[i].unit_rate+"</td>"+
				"<td>"+page_data[z].detailsList[i].disc_unit_rate+"</td>"+
				"<td>"+page_data[z].detailsList[i].quantity+"</td>"+
				"<td>"+page_data[z].detailsList[i].igst_amount+"</td>"+				
				"<td>"+page_data[z].detailsList[i].cgst_amount+"</td>"+
				"<td>"+page_data[z].detailsList[i].sgst_amount+"</td>"+
				"<td>"+page_data[z].detailsList[i].sale_amount+"</td>"+
				"<td>"+page_data[z].detailsList[i].pre_cyls+"</td>"+
				"<td>"+page_data[z].detailsList[i].psv_cyls+"</td>"+
				"<td>"+staffName+"</td>"+
				"<td>"+areaName+"</td>"+
				"<td>"+bankName+"</td>"+
				"<td>"+page_data[z].detailsList[i].amount_rcvd_online+"</td>"+

				del+"</tr>";
		}
	}
}
document.getElementById('m_data_table_body').innerHTML = rowpdhtml;

function calculateVATPrice(ebp, spc){
	var cv = 0;
	for(var z=0; z<equipment_data.length; z++){
		if(equipment_data[z].prod_code == spc) {
			var vatv = equipment_data[z].vatp;
			cv = ((ebp*vatv)/100);
		}
	}
	return cv;
}

function showInvoice(inv_id,si_date){
	popitup(inv_id,si_date);
}

function popitup(inv_id,si_date) { 
	
//	window.open("PopupControlServlet?actionId=999&sitype=1&sid="+inv_id,null,"height=400,width=1024,status=yes,toolbar=no,menubar=no,location=no");
	
	//newwindow=window.open("http://localhost:8080/ERPAPP/ControlServlet");
	//window.open('page2.jsp','popuppage','width=850,toolbar=1,resizable=1,scrollbars=yes,height=700,top=100,left=100');
	var w=window.open("PopupControlServlet?actionId=999&sitype=1&sid="+inv_id +"&si_date="+si_date,null,"height=400,width=1024,status=yes,toolbar=no,menubar=no,location=no");
	/*if (window.focus) {
		newwindow.focus();
	}  */ 	
    w.print();
	return false; 
}


function addRow() {
	var trcount = document.getElementById('data_table_body').getElementsByTagName('tr').length;
	if(trcount >0) {
		var trv=document.getElementById('data_table_body').getElementsByTagName('tr')[trcount-1];
		var saddv=trv.getElementsByClassName('sadd');
		var eaddv=trv.getElementsByClassName('eadd');
		var res=checkRowData(saddv,eaddv);
		if(res == false) {
			document.getElementById("dialog-1").innerHTML = "Please enter all the values in current row,calculate and then add next row ";
			alertdialogue();
			//alert("Please enter all the values in current row,calculate and then add next row");
			return;
		}
	}		
	document.getElementById("save_data").disabled=true;
	
	var ele = document.getElementsByClassName("qtyc");
	if(ele.length<4){
		var tbody = document.getElementById('data_table_body');
		var newRow = tbody.insertRow(-1);

		var a = newRow.insertCell(0);
		var b = newRow.insertCell(1);
		var c = newRow.insertCell(2);
		var d = newRow.insertCell(3);
		var e = newRow.insertCell(4);
		var f = newRow.insertCell(5);
		var g = newRow.insertCell(6);
		var h = newRow.insertCell(7);
		var i = newRow.insertCell(8);
		var j = newRow.insertCell(9);
		var k = newRow.insertCell(10);
		var l = newRow.insertCell(11);
		var m = newRow.insertCell(12);
		var n = newRow.insertCell(13);
//		var o = newRow.insertCell(14);
		
		a.innerHTML = "<td><select name='epid' id='epid' class='form-control input_field select_dropdown sadd pid freez'>"+ctdatahtml+"</select></td>";
		b.innerHTML = "<td><input type=text name='vatp' class='form-control input_field eadd' id='vatp' size='6' readonly='readonly' style='background-color:#FAFAC2;'placeholder='Gst%'></td>";
		c.innerHTML = "<td><input type=text name='up'  class='form-control input_field eadd' id='up' size='6' maxlength='7' value='0' readonly='readonly' style='background-color:#FAFAC2;' placeholder='Unit Price'></td>";
		d.innerHTML = "<td><input type=text name='upd' class='form-control input_field freez eadd' id='upd' maxlength='7' size='6' value='0' placeholder='Discount On Unit Price'/></td>";
		e.innerHTML = "<td><input type=text name='qty' class='form-control input_field qtyc freez eadd' id='qty' maxlength='4' size='6' placeholder='Quantity'></td>";
		
		if(document.getElementById("pt").selectedIndex == 2)
			f.innerHTML = "<td><input type=text name='prec' class='form-control input_field freez eadd pre' id='pre' value='0' maxlength='3' size='6' placeholder='Prepaid Cylinders'></td>";
		else 
			f.innerHTML = "<td><input type=text name='prec' class='form-control input_field freez eadd pre' id='pre' maxlength='3' size='6' placeholder='Prepaid Cylinders'></td>";
		
		g.innerHTML = "<td><input type=text name='psvc' class='form-control input_field freez eadd' id='psvc' maxlength='3' size='6' placeholder='PSV Quantity'></td>";
		h.innerHTML = "<td><select name='sid' id='sid' class='form-control input_field select_dropdown'>"+staffdatahtml+"</select></td>";
		
		if(document.getElementById("pt").selectedIndex == 2){
			i.innerHTML = "<td><select name='bid' id='bid' class='form-control input_field select_dropdown freez'>"+bankdatahtml+"</select></td>";
			var formobj = document.getElementById('data_form');	
			var sbfrz= formobj.bid[trcount].options;
			formobj.bid[trcount].selectedIndex = 0;
			disableSelect(sbfrz,sbfrz.length);
		}else
			i.innerHTML = "<td><select name='bid' id='bid' class='form-control input_field select_dropdown freez'>"+bankdatahtml+"</select></td>";
		
		j.innerHTML = "<td><select name='acid' id='acid' class='form-control input_field select_dropdown'>"+areacodeshtml+"</select></td>";
		k.innerHTML = "<td><input type=text name='txnamt' class='form-control input_field eadd' id='txnamt' size='6' readonly='readonly' style='background-color:#F3F3F3;' placeholder='Transaction Amount'></td>";
		l.innerHTML = "<td><input type=text name='siamt' class='form-control input_field eadd' id='siamt' size='6' readonly='readonly' style='background-color:#F3F3F3;' placeholder='Sale Amount'></td>";
		
		m.innerHTML = "<td><input type=text name='onlinercvdamt' class='form-control input_field eadd' id='onlinercvdamt' size='6' readonly='readonly' style='background-color:#F3F3F3;' placeholder='Online Rcvd Amount'></td>";
		n.innerHTML = "<td><img src='images/delete.png' class='freez' onclick='doRowDeleteInTranxs(this,siamt,si_amt,data_table_body,c_amt,txnamt)'></td>"+
							"<td>"+
							"<input type=hidden name='igsta' id='igsta'>" +	
							"<input type=hidden name='sgsta' id='sgsta'>" +
							"<input type=hidden name='cgsta' id='cgsta'>" +
							"<input type=hidden name='ppsiamt' id='ppsimat'>" +
							
							"<input type=hidden name='optaxablea' id='optaxablea'>" +
							"<input type=hidden name='optota' id='optota'>" +
							"<input type=hidden name='opcgsta' id='opcgsta'>" +
							"<input type=hidden name='opsgsta' id='opsgsta'>" +
							"<input type=hidden name='opigsta' id='opigsta'>" +
							"<input type=hidden name='opbasicprice' id='opbasicprice'>" +

							"</td>";
		document.getElementById("fetch_data").disabled = false;
	}else {
		document.getElementById("dialog-1").innerHTML = "Please save the data and ADD again";
		alertdialogue();
		//alert("Please save the data and ADD again");
		return;
	}

}

function changePmntTerms() {
	var formobj = document.getElementById('data_form');
	var cvoid = formobj.cust_id.selectedIndex;
	var sptfrz= document.getElementById("pt").options;

	var ele = document.getElementsByClassName("qtyc");
	if(ele.length==1){
		var sbfrz= document.getElementById("bid").options;
		if(cvoid == 1) {
			formobj.pt.selectedIndex = 1;
			disableSelect(sptfrz,sptfrz.length);

			formobj.prec.value = "";
			formobj.bid.selectedIndex = 0;
			enableSelect(sbfrz,sbfrz.length);			
		}else {
			formobj.pt.selectedIndex = 0;
			enableSelect(sptfrz,sptfrz.length);
			enableSelect(sbfrz,sbfrz.length);
		}		
	}else if(ele.length>1){
		for(var i=0;i<ele.length;i++) {
			var sbfrz= formobj.bid[i].options;
			if(cvoid == 1) {
				formobj.pt.selectedIndex = 1;
				disableSelect(sptfrz,sptfrz.length);
			
				formobj.prec[i].value = "";
				formobj.bid[i].selectedIndex = 0;
				enableSelect(sbfrz,sbfrz.length);
			}else {
				formobj.pt.selectedIndex = 0;
				enableSelect(sptfrz,sptfrz.length);
				enableSelect(sbfrz,sbfrz.length);
			}
		}
	}
}

function setPPCValueAndBankAcct(){
	var formobj = document.getElementById('data_form');
	var pt = formobj.pt.selectedIndex;
	var ele = document.getElementsByClassName("qtyc");
	if(ele.length==1){
		var sbfrz= document.getElementById("bid").options;	
		if(pt==2) {
			formobj.prec.value = 0;
			enableSelect(sbfrz,sbfrz.length);
			formobj.bid.selectedIndex = 0;
			disableSelect(sbfrz,sbfrz.length);		
		}else {
			formobj.prec.value = "";
			formobj.bid.selectedIndex = 0;
			enableSelect(sbfrz,sbfrz.length);
		}
	}else if(ele.length>1){
		for(var j=0;j<ele.length;j++){
			var sbfrz= formobj.bid[j].options;	
			if(pt==2) {
				formobj.prec[j].value = 0;
				enableSelect(sbfrz,sbfrz.length);
				formobj.bid[j].selectedIndex = 0;
				disableSelect(sbfrz,sbfrz.length);		
			}else {
				formobj.prec[j].value = "";
				formobj.bid[j].selectedIndex = 0;
				enableSelect(sbfrz,sbfrz.length);
			}
		}	
	}	
}


function saveData(obj){
	var formobj = document.getElementById('data_form');	
	var ems = "";
	var sinvamt = 0;
	if(document.getElementById("epid") != null){
		var elements = document.getElementsByClassName("qtyc");
		var sidate = formobj.si_date.value.trim();
		var cust = formobj.cust_id.selectedIndex;
		sinvamt = formobj.si_amt.value.trim();
		var pt = formobj.pt.selectedIndex;

		if(elements.length==1) {
			var item = formobj.epid.selectedIndex;
			var up = formobj.up.value.trim();
			var upd = formobj.upd.value.trim();
			var qty = formobj.qty.value.trim();
			var prec = formobj.prec.value.trim();
			var psvc = formobj.psvc.value.trim();
			var txamt = formobj.txnamt.value.trim();
			var samt = formobj.siamt.value.trim();
			var sgsta = formobj.sgsta.value.trim();
			var cgsta = formobj.cgsta.value.trim();

			var bankId = formobj.bid.selectedIndex;
			var onlinercvdamt = formobj.onlinercvdamt.value.trim();

			if(checkDateFormate(sidate)){
				var ved = dateConvert(sidate);
				formobj.si_date.value = ved;
				sidate = ved;
			}
			ems = validateEntries(sidate,cust,item,up,upd,qty,prec,psvc,pt,txamt,samt,sgsta,cgsta,bankId,onlinercvdamt);
			if(!(ems.length>0)){
				formobj.up.value=round(parseFloat(up),2);
				formobj.upd.value=round(parseFloat(upd),2);
			}
			
		}else if (elements.length>1){
			for(var i=0; i<elements.length; i++){
				var item = formobj.epid[i].selectedIndex;
				var up = formobj.up[i].value.trim();
				var upd = formobj.upd[i].value.trim();
				var qty = formobj.qty[i].value.trim();
				var prec = formobj.prec[i].value.trim();
				var psvc = formobj.psvc[i].value.trim();
				var txamt = formobj.txnamt[i].value.trim();
				var samt = formobj.siamt[i].value.trim();
				var sgsta = formobj.sgsta[i].value.trim();
				var cgsta = formobj.cgsta[i].value.trim();
				
				var bankId = formobj.bid[i].selectedIndex;
				var onlinercvdamt = formobj.onlinercvdamt[i].value.trim();

				if(checkDateFormate(sidate)) {
					var ved = dateConvert(sidate);
					formobj.si_date[i].value = ved;
					sidate=ved;
				}
				ems = validateEntries(sidate,cust,item,up,upd,qty,prec,psvc,pt,txamt,samt,sgsta,cgsta,bankId,onlinercvdamt);
				if(ems.length>0)
					break;
				else {
					formobj.up[i].value=round(parseFloat(up),2);
					formobj.upd[i].value=round(parseFloat(upd),2);
				}				
			}
		}
	}else {
		document.getElementById("dialog-1").innerHTML = "Please ADD data";
		alertdialogue();
		//alert("Please ADD data");
		return;
	}
	
	if(ems.length>0) {
		document.getElementById("dialog-1").innerHTML = ems;
		alertdialogue();
		//alert(ems);
		return;
	}else {
		var stockCheck = checkForStock();
		if(stockCheck==false) {
			document.getElementById("dialog-1").innerHTML = "You are out of stock.Please Check and add again";
			alertdialogue();
			//alert("You are out of stock.Please Check and add again");
			return;
		}
	}

	var custv = formobj.cust_id.options[cust].value;
	var cctrl = 0;
	if(cust != 1 && pt != 1){
		var cust_cb;
		for(var k=0;k<cust_cl_data.length;k++){
			for(var l=0;l<cvo_data.length;l++){
				if(custv == cvo_data[l].id)
					cust_cb = +(cvo_data[l].cbal)+ +(sinvamt);				
			}
			if(cust_cb > cust_cl_data[k].credit_limit){
				if(custv == cust_cl_data[k].cust_id)
					cctrl = cust_cl_data[k].cc_type;
			}	
		}
	}
	if(cctrl != 0){
		if(cctrl==1){
			document.getElementById("dialog-1").innerHTML = "THIS TRANSACTION CANNOT BE PROCEEDED FURTHER AS THIS CUSTOMER HAS EXCEEDED CREDIT LIMIT.";
			alertdialogue();
			//alert("THIS TRANSACTION CANNOT BE PROCEEDED FURTHER AS THIS CUSTOMER HAS EXCEEDED CREDIT LIMIT.");
			return;
		}else if(cctrl==2){
			var formobj = document.getElementById('data_form');
			$("#myDialogText").text("THIS CUSTOMER HAS EXCEEDED CREDIT LIMIT. DO YOU WANT TO CONTINUE?");
			confirmTxtDialogue(formobj);
			/*if(confirm("THIS CUSTOMER HAS EXCEEDED CREDIT LIMIT. DO YOU WANT TO CONTINUE?") == false)
				return;*/
		}
	}else {
		if((cust==1) && (sinvamt>250000)){
			document.getElementById("dialog-1").innerHTML = "YOUR INVOICE AMOUNT EXCEEDS RS.2,50,000/- .IF YOU WANT TO ADD FURTHER,ADD IN NEXT INVOICE";
			alertdialogue();
			//alert("YOUR INVOICE AMOUNT EXCEEDS RS.2,50,000/- .IF YOU WANT TO ADD FURTHER,ADD IN NEXT INVOICE");
			return;
		}else if((cust!=1) && (sinvamt>250000)){
			var formobj = document.getElementById('data_form');
			$("#myDialogText").text("YOUR INVOICE AMOUNT EXCEEDS RS.2,50,000/-.DO YOU WANT TO CONTINUE?");
			confirmTxtDialogue(formobj);
			/*if(confirm("YOUR INVOICE AMOUNT EXCEEDS RS.2,50,000/-.DO YOU WANT TO CONTINUE?") == false)
				return;*/
		}
	}
	
	var objId = obj.id;
	document.getElementById(objId).disabled = true;    	
	if(!((cctrl==2) || ((cust!=1) && (sinvamt>250000)))){
		formobj.submit();	
	}
	
}

function checkForStock(){
//	qty-psv
	var formobj = document.getElementById('data_form');	
	if(document.getElementById("epid") != null){
		var ele = document.getElementsByClassName("qtyc");
		if(ele.length==1) {
			var item = formobj.epid.value;
			var qty = formobj.qty.value.trim();
			var psv = formobj.psvc.value.trim();
			var tqty = (qty)-(psv);  // for empties
			
			for(var e=0;e<equipment_data.length;e++) {
				if(equipment_data[e].prod_code == parseInt(item)) {
					var stock = equipment_data[e].cs_fulls-qty;
					if(stock<0) {
						return false;
					} 
				}
			}			
		}else if (ele.length>1){
			for(var i=0; i<ele.length; i++){
				var mitem = formobj.epid[i].value;
				var mqty = formobj.qty[i].value.trim();
				var mpsv = formobj.psvc[i].value.trim();
				var mtqty = (mqty)-(mpsv);  // for empties
				
				for(var e=0;e<equipment_data.length;e++){
					if(equipment_data[e].prod_code == parseInt(mitem)) {
						var mstock = equipment_data[e].cs_fulls-mqty;
						if(mstock<0) {
							return false;
						} 
					}
				}
			}
		}
	}	
}

function validateProduct() {
	var flag=true;
	var prodar= new Array();
	var ele = document.getElementsByClassName("pid");
	for (var i=0;i<ele.length; i++) {
		var e = ele[i].options[ele[i].selectedIndex].text;
		prodar.push(e);
	}		
    
    for(var j = 0; j <= prodar.length; j++) {
        for(var k = j; k <= prodar.length; k++) {
            if(j != k && prodar[j] == prodar[k]) {
                flag=false;
            }
        }
    }
    return flag;
}

function validateEntries(sidate,cust,item,up,upd,qty,prec,psvc,pt,txamt,samt,sgsta,cgsta,bankId,onlinercvdamt){
	
	var formobj = document.getElementById('data_form');
	var errmsg = "";
	
	var vd = isValidDate(sidate);
	var vfd = ValidateFutureDate(sidate);	
	var chkd = checkdate(sidate);

	restrictChangingAllValues(".freez",".sinvd");
	if(!(sidate.length>0))
		errmsg = errmsg+"Please enter INVOICE DATE<br>";
	else if (vd != "false")
		errmsg = errmsg +vd+"<br>";
	else if(validateDayEndAdd(sidate,dedate)){
        errmsg = "INVOICE DATE should be after DayEndDate<br>";
        return errmsg;
	}else if(validateEffectiveDateForCVO(sidate,effdate)){
        errmsg = "INVOICE DATE should be after Effective Date that you have entered While First Time Login<br>";
        return errmsg;
	}else if(chkd != "true")
		errmsg = errmsg+chkd+"<br>";
	else if (vfd != "false")
		errmsg = errmsg +"INVOICE"+vfd+"<br>";
/*	else {
		var lastTrxnDateCheck = checkforLastTrxnDate(dedate,a_created_date,sidate,trdate);
		if(lastTrxnDateCheck != "false"){
			return lastTrxnDateCheck;
		}
	}*/
	/*else {
        var dayenddate=new Date(dedate);
        var agcrdate = new Date(a_created_date);
        var ddate = new Date(dayenddate.getFullYear(),dayenddate.getMonth(),dayenddate.getDate(),0,0,0);
        var acdate = new Date(agcrdate.getFullYear(),agcrdate.getMonth(),agcrdate.getDate(),0,0,0);

        if(!(acdate <= ddate)) {
        	var sinvd = new Date(sidate);
        	var sdate = new Date(sinvd.getFullYear(),sinvd.getMonth(),sinvd.getDate(),0,0,0);
        	var txndate = new Date(new Date(trdate).getFullYear(),new Date(trdate).getMonth(),new Date(trdate).getDate(),0,0,0);
        	var tdate = txndate.getDate() +"-"+ (txndate.getMonth()+1) +"-"+ txndate.getFullYear();
        	if(sdate < txndate) {
        		errmsg = "Back dates are not allaowed. Your last transaction date is "+tdate+ "<br>Please check it and add again." ;
        		return errmsg;
        	}
        }
    }*/
	
	if(!(cust>0))
		errmsg = errmsg+"Please select CUSTOMER NAME. <br>";
		
	if(!(pt>0))
		errmsg = errmsg+"Please select PAYMENT TERMS<br>";

	if(!(item>0))
		errmsg = errmsg+"Please select PRODUCT<br>";
	else if (!validateProduct())
		errmsg = errmsg + "Please Select Another PRODUCT Which Was not Selected.<br>";
	
	if(!(up.length>0))
		errmsg = errmsg+"Please enter UINT PRICE<br>";
	else if(validateDot(up)) 
		errmsg = errmsg+"UNIT PRICE must contain atleast one number. <br>";
	else if(!(validateDecimalNumberMinMax(up,0,100000)))
		errmsg = errmsg + "Please enter valid UNIT PRICE and it must be in between 0 and 10,0000 <br>";
		
	if(!(upd.length>0))
		errmsg = errmsg+"Please enter UNIT PRICE DISCOUNT<br>";
	else if(validateDot(upd)) 
		errmsg = errmsg+"DISCOUNT ON UNIT PRICE must contain atleast one number. <br>";	
	else if(!(validateDecimalNumberMinMax(upd,-1,10000)))
		errmsg = errmsg + "Please enter Valid DISCOUNT ON UNIT PRICE and it must be >=0 and <10,000 .<br>";
	else if(parseFloat(upd)>parseFloat(up)) 
		errmsg = errmsg+"DISCOUNT ON UNIT PRICE must be less than UNIT PRICE.<br>";
	
	var qtyflag =0;
	if(!qty.length>0)
		  errmsg = errmsg + "Please Enter QUANTITY. <br>";
	else if(!(validateNumberMinMax(qty,0,10000)))
		errmsg = errmsg + "QUANTITY must be in Numerics and it must be in between 0 and 10,000 .<br>";
	else
		qtyflag = 1;
			
	var precflag =0;
	if(!(prec.length>0))
		errmsg = errmsg+"Please enter PREPAID CYLINDERS.If not available, enter 0.<br>";
	else if(!(checkNumber(prec)))
		errmsg = errmsg + "PREPAID CYLINDERS must contain only Numerics and can't be less than 0 .<br>";
	else if(pt==2 && parseInt(prec)!=0)
		errmsg = errmsg + "PREPAID CYLINDERS must be 0 for CREDIT type payment.<br>";
	else if(!(validateNumberMinMax(prec,-1,1000)))
		errmsg = errmsg + "Please Enter Valid PREPAID CYLINDERS and it must be in between 0 and 1000 .<br>";
	else
		precflag = 1;

	var psvflag =0;
	if(!(psvc.length>0))
		errmsg = errmsg+"Please enter PSV CYLINDERS.If not available, enter 0.<br>";
	else if(!(checkNumber(psvc)))
		errmsg = errmsg + "PSV CYLINDERS Must Contain Only Numerics and can't be less than 0 .<br>";
	else if(!(validateNumberMinMax(psvc,-1,1000)))
		errmsg = errmsg + "Please Enter Valid PSV CYLINDERS and it must be in between 0 and 1000 .<br>";
	else
		psvflag = 1;

	if((qtyflag==1)&&(precflag == 1)&&(psvflag == 1)){
		if((parseInt(qty)) < (parseInt(prec) + parseInt(psvc))) {
			errmsg = errmsg + "Please Enter PSV & PREPAID CYLINDERS from QUANTITY .<br>";
		}
			
	}
		
	if(!(validateDecimalNumberMinMax(txamt,-1,1000000000)))
		errmsg = errmsg + "Invalid TRANSACTION AMOUNT.<br>";
	if(!(validateDecimalNumberMinMax(samt,-1,10000000)))
		errmsg = errmsg + "Invalid SALE AMOUNT.<br>";
	if(!((validateDecimalNumberMinMax(sgsta,-1,10000000)) || (validateDecimalNumberMinMax(cgsta,-1,10000000))))
		errmsg = errmsg + "Invalid GST Amount For The provided Values<br>";
	/*if(!(validateDecimalNumberMinMax(cgsta,-1,10000000)))
		errmsg = errmsg + "Invalid GST.. <br>";*/
	
	if((pt==1) && (parseInt(prec)>0) && (bankId==0))
		errmsg = errmsg + "Please select BANK ACCOUNT <br>";

	return errmsg;
}

/*function deleteItem(iid,invdate,detailsListlen) {
	var flag=validateDayEndForDelete(invdate,dedate);
	var formobj = document.getElementById('m_data_form');
	if(flag) {
		if(detailsListlen>1){
			if (confirm("THIS INVOICE CONTAINS MULTIPLE PRODUCTS.IF YOU DELETE THIS ONE TOTAL INVOICE WILL GET DELETED.ARE YOU SURE YOU WANT TO DELETE?") == true) {				
				formobj.actionId.value = "5123";
				formobj.dataId.value = iid;
				formobj.submit();
			}	
		}else if(detailsListlen==1){
			if (confirm("ARE YOU SURE YOU WANT TO DELETE?") == true) {
				formobj.actionId.value = "5123";
				formobj.dataId.value = iid;
				formobj.submit();
			}
		}
	}else 
		
		alert("THIS RECORD CAN'T BE DELETED AS YOU HAVE SUBMITTED AND VERIFIED YOUR DAYEND CALCULATIONS SUCCUESSFULLY... ");
	
}*/


function deleteItem(iid,invdate,detailsListlen){
	var flag=validateDayEndForDelete(invdate,dedate);
	if(flag){
		var formobj = document.getElementById('m_data_form');
		if(detailsListlen>1){
			$("#myDialogText").text("THIS INVOICE CONTAINS MULTIPLE PRODUCTS.IF YOU DELETE THIS ONE TOTAL INVOICE WILL GET DELETED.ARE YOU SURE YOU WANT TO DELETE?");
			confirmDialogue(formobj,5123,iid);
		}else if(detailsListlen==1){
			$("#myDialogText").text("Are You Sure You Want To Delete?");
			confirmDialogue(formobj,5123,iid);	
		}
	}else {
		document.getElementById("dialog-1").innerHTML = "This record can't be deleted as you have submitted and verified your DAYEND calculations succuessfully";
		alertdialogue();
	}
}



function fetchPriceAndVAT(){
	var formobj = document.getElementById('data_form');
	if(document.getElementById("up") != null) {
		var dsdate = formobj.si_date.value;
		var sdate = new Date(dsdate);
		var mon = sdate.getMonth();
		var yr = sdate.getFullYear();
		var pyear = ["2018","2019","2020"];
		var millisecs = sdate.getTime();
		
		var elements = document.getElementsByClassName("qtyc");
		if(elements.length==1) {
			var pide = formobj.epid;
			if(pide.selectedIndex > 0) {
				var pidv = pide.options[pide.selectedIndex].value;
				var gstp = fetchGSTPercent(equipment_data,pidv);
				
				if(gstp>0) {
					
					var prodDateInEqpm = 0;
					for(var e=0 ; e < equipment_data.length ; e++){
						if(equipment_data[e].prod_code == pidv){
							prodDateInEqpm = equipment_data[e].effective_date;
							break;
						}
					}
					for(var p=cat_cyl_types_data.length-1; p>=0; p--) {
						var cproduct= cat_cyl_types_data[p].cat_name + "-" + cat_cyl_types_data[p].cat_desc;
						var prodid = cat_cyl_types_data[p].id;
					
						for(var i=refill_prices_data.length-1 ; i>=0 ; i--) {
							if(cproduct == pide.options[pide.selectedIndex].innerHTML) {
								
							if(dsdate != ""){
								if(prodid == refill_prices_data[i].prod_code) {
									if(!(refill_prices_data[i].montha == mon && pyear[refill_prices_data[i].yeara] == yr)){
										document.getElementById("dialog-1").innerHTML = "please define the price of product for sale invoice month in price master and continue";
										alertdialogue();
										//alert("please define the price of product for sale invoice month in price master and continue");
										return;
									}else if(millisecs < prodDateInEqpm){
										document.getElementById("dialog-1").innerHTML = "The product you have selected is invalid for the given date. Plese check effective date of the added product in Equipment Master.";
										alertdialogue();
										//alert("please define the price of product for sale invoice month in price master and continue");
										return;
									}else {
										formobj.vatp.value = gstp;
										formobj.up.value = fetchRefillUnitPrice(refill_prices_data,pidv);

										/*// OfferPrice Taxing: Masked For Now. Can Be Used Later.
										formobj.opbasicprice.value = fetchRefillOfferPriceBp(refill_prices_data,pidv);
										 */
										formobj.opbasicprice.value = 0;
										
										break;
									}
								}
							}else if(dsdate == ""){
								document.getElementById("dialog-1").innerHTML = "Please Enter INVOICE DATE";
								alertdialogue();
								//alert("Please Enter INVOICE DATE");
								return;
							}
							}
						}
					}
				}else {
					document.getElementById("dialog-1").innerHTML = "Please ADD the PRODUCT in EQUIPMENT MASTER";
					alertdialogue();
					//alert("Please ADD the PRODUCT in EQUIPMENT MASTER");
					return;					
				}
			}else{
				document.getElementById("dialog-1").innerHTML = "Please select PRODUCT and then click on FETCH UNIT PRICE AND GST";
				alertdialogue();
				//alert("Please select PRODUCT and then click on FETCH UNIT PRICE AND GST");
				return;
			}
		}else if (elements.length>1) {
			for(var i=0; i<elements.length; i++){
				var pide = formobj.epid[i];
				if(pide.selectedIndex > 0) {
					var pidv = pide.options[pide.selectedIndex].value;
					var gstp = fetchGSTPercent(equipment_data,pidv);
					
					if(gstp>0) {
						var prodDateInEqpm = 0;
						for(var e=0 ; e < equipment_data.length ; e++){
							if(equipment_data[e].prod_code == pidv) {
								prodDateInEqpm = equipment_data[e].effective_date;
								break;
							}
						}
						
						for(var p=cat_cyl_types_data.length-1; p >= 0; p--) {
							var cproduct= cat_cyl_types_data[p].cat_name+"-"+cat_cyl_types_data[p].cat_desc;
							var prodid = cat_cyl_types_data[p].id;
						
							for(var j=refill_prices_data.length-1;j>=0;j--) {
								if(cproduct == pide.options[pide.selectedIndex].innerHTML) {
									if(prodid == refill_prices_data[j].prod_code) {
										
										if(dsdate != "") {
											if(!(refill_prices_data[j].montha == mon && pyear[refill_prices_data[j].yeara] == yr)){
												document.getElementById("dialog-1").innerHTML = "please define the price of product for sale invoice month in price master and continue";
												alertdialogue();
												//alert("please define the price of product for sale invoice month in price master and continue");
												return;
											}else if(millisecs < prodDateInEqpm) {
												document.getElementById("dialog-1").innerHTML = "The product you have selected is invalid for the given date. Plese check effective date of the added product in Equipment Master.";
												alertdialogue();
												//alert("please define the price of product for sale invoice month in price master and continue");
												return;
											}else {
												formobj.vatp[i].value = gstp;
												formobj.up[i].value = fetchRefillUnitPrice(refill_prices_data,pidv);
												
												/*// OfferPrice Taxing: Masked For Now. Can Be Used Later.
												formobj.opbasicprice[i].value = fetchRefillOfferPriceBp(refill_prices_data,pidv);*/
												formobj.opbasicprice[i].value = 0;
													
												break;
											}
										}else if(dsdate == ""){
											document.getElementById("dialog-1").innerHTML = "Please Enter INVOICE DATE";
											alertdialogue();
											//alert("Please Enter INVOICE DATE");
											return;
										}
									}
								}
							}
						}
					}else {
						document.getElementById("dialog-1").innerHTML = "Please ADD the PRODUCT in EQUIPMENT MASTER";
						alertdialogue();
						//alert("Please ADD the PRODUCT in EQUIPMENT MASTER");
						return;					
					}
				}else{
					document.getElementById("dialog-1").innerHTML = "Please select PRODUCT and then click on FETCH UNIT PRICE AND GST";
					alertdialogue();
					//alert("Please select PRODUCT and then click on FETCH UNIT PRICE AND GST");
					return;
				}
			}
		}
	}else {
		document.getElementById("dialog-1").innerHTML = "Please ADD data and then click on FETCH UNIT PRICE AND GST";
		alertdialogue();
		//alert("Please ADD data and then click on FETCH UNIT PRICE AND GST");
		return;
	}
	restrictChangingAllValues(".freez",".pid");
	document.getElementById("calc_data").disabled=false;
	
}

function calculateValues(){
	// siamt - total amt - gstr1 - invoice value
	
	var cvogstin;
	var cvoreg;
	var stypecheck = "";

	var formobj = document.getElementById('data_form');
	if(document.getElementById("up") != null){
		if(formobj.cust_id.selectedIndex >0) {
			for(var d=0;d< cvo_data.length;d++){
				if(parseInt(formobj.cust_id.value) == cvo_data[d].id) {
					cvogstin = cvo_data[d].cvo_tin;
					cvoreg = cvo_data[d].is_gst_reg;
				}
			}
		}else {
			document.getElementById("dialog-1").innerHTML = "please select CUSTOMER";
			alertdialogue();
			//alert("please select CUSTOMER");
			return;
		}
		if((cvoreg==1) && (formobj.cust_id.selectedIndex != 1)) {
			var dstcode = dealergstin.substr(0, 2);
			var cvstcode = cvogstin.substr(0, 2);
			if(cvstcode == dstcode)
				stypecheck = "ls";
			else 
				stypecheck = "iss";
		}else if((cvoreg==2) && (formobj.cust_id.selectedIndex != 1)){
			stypecheck = "ls";
		}else if(formobj.cust_id.selectedIndex == 1){
			stypecheck = "ls";
		}

		var elements = document.getElementsByClassName("qtyc");
		if(elements.length==1) {
			var upv = formobj.up.value.trim();
			var updv = formobj.upd.value.trim();
			var vpv = formobj.vatp.value.trim();
			var qty = formobj.qty.value.trim();
			var ppc = formobj.prec.value.trim();
			var psvn = formobj.psvc.value.trim();
			var sbfrz= document.getElementById("bid").options;
			
			/*// OfferPrice Taxing: Masked For Now. Can Be Used Later.
			var ofpbpv = formobj.opbasicprice.value.trim();*/
			
			if(ppc == 0){
				formobj.bid.selectedIndex = 0;
				disableSelect(sbfrz,sbfrz.length);
			}else {
				enableSelect(sbfrz,sbfrz.length);
			}
			var ems = validateCalcValues(upv,updv,vpv,qty,ppc,psvn);
			if(ems.length>0){
				document.getElementById("dialog-1").innerHTML = ems;
				alertdialogue();
				//alert(ems);
				return;
			}
			formobj.up.value = parseFloat(upv).toFixed(2);
			formobj.upd.value = parseFloat(updv).toFixed(2);
			
			/*// OfferPrice Taxing: Masked For Now. Can Be Used Later.
			formobj.opbasicprice.value = parseFloat(ofpbpv).toFixed(2);*/
			
			var gsta = ((((upv-updv)*(qty-psvn))*vpv)/100).toFixed(2);
			var ppgsta = ((((upv-updv)*(qty-ppc-psvn))*vpv)/100).toFixed(2);
			
			/*// OfferPrice Taxing: Masked For Now. Can Be Used Later.
			var opgsta = ((((ofpbpv-updv)*(qty-psvn))*vpv)/100).toFixed(2);*/
			if(stypecheck == "ls"){
				formobj.sgsta.value = (gsta/2).toFixed(2);
				formobj.cgsta.value = (gsta/2).toFixed(2);
				formobj.igsta.value = "0.00";
				formobj.siamt.value = (+((upv-updv) * (qty-psvn)) + +(formobj.sgsta.value) + +(formobj.cgsta.value)).toFixed(2);
				
				/*// OfferPrice Taxing: Masked For Now. Can Be Used Later.
				formobj.opcgsta.value = (opgsta/2).toFixed(2);
				formobj.opsgsta.value = (opgsta/2).toFixed(2);
				formobj.opigsta.value = "0.00";
				formobj.optota.value = (+((ofpbpv-updv) * (qty-psvn)) + +(formobj.opsgsta.value) + +(formobj.opcgsta.value)).toFixed(2);*/
								
			}else if(stypecheck == "iss"){
				formobj.igsta.value = (round(gsta,2));
				formobj.cgsta.value = "0.00";
				formobj.sgsta.value = "0.00";
				formobj.siamt.value = (+((upv-updv) * (qty-psvn)) + +(formobj.igsta.value)).toFixed(2);
				
				/*// OfferPrice Taxing: Masked For Now. Can Be Used Later.
				formobj.opcgsta.value = "0.00";
				formobj.opsgsta.value = "0.00";
				formobj.opigsta.value = (round(opgsta,2));
				formobj.optota.value = (+((ofpbpv-updv) * (qty-psvn)) + +(formobj.opigsta.value)).toFixed(2);*/
			}
			/*// OfferPrice Taxing: Masked For Now. Can Be Used Later.
			formobj.optaxablea.value = ((qty - psvn) * (parseFloat(ofpbpv) - parseFloat(updv))).toFixed(2);*/
			
			formobj.opcgsta.value = 0;
			formobj.opsgsta.value = 0;
			formobj.opigsta.value = 0;
			formobj.optota.value = 0;
			formobj.optaxablea.value = 0;
			
			formobj.ppsiamt.value = (+(((upv-updv) * (qty-ppc-psvn))) + +(ppgsta)).toFixed(2);
			formobj.txnamt.value = formobj.ppsiamt.value;
			formobj.si_amt.value = Math.round(formobj.siamt.value);
			formobj.c_amt.value = formobj.txnamt.value;
			var vatamount=((((upv-updv)*(ppc))*vpv)/100).toFixed(2);
			formobj.onlinercvdamt.value =(+(((upv-updv)*ppc)+ +vatamount)).toFixed(2);
			
		} else if (elements.length>1){
			var totalAmt = 0;
			var totalPPAmt = 0;
			for(var i=0; i<elements.length; i++){
				var upv = formobj.up[i].value.trim();
				var updv = formobj.upd[i].value.trim();
				var vpv = formobj.vatp[i].value.trim();
				var qty = formobj.qty[i].value.trim();
				var ppc = formobj.prec[i].value.trim();
				var psvn = formobj.psvc[i].value.trim();
				var bankId = formobj.bid[i].selectedIndex;
				var pfrz = formobj.bid[i].options;
				
				/*// OfferPrice Taxing: Masked For Now. Can Be Used Later.
				var ofpbpv = formobj.opbasicprice[i].value.trim();*/
				
				if(ppc == 0){
					formobj.bid[i].selectedIndex = 0;
					disableSelect(pfrz,pfrz.length);
				}else {
					enableSelect(pfrz,pfrz.length);
				}
				
				var ems = validateCalcValues(upv,updv,vpv,qty,ppc,psvn);
				if(ems.length>0){
					document.getElementById("dialog-1").innerHTML = ems;
					alertdialogue();
					//alert(ems);
					return;
				}
				
				formobj.up[i].value = parseFloat(upv).toFixed(2);
				formobj.upd[i].value = round(parseFloat(updv),2);
				
				/*// OfferPrice Taxing: Masked For Now. Can Be Used Later.
				formobj.opbasicprice[i].value = parseFloat(ofpbpv).toFixed(2);*/
				
				var gsta = ((((upv-updv)*(qty-psvn))*vpv)/100).toFixed(2);
				var ppgsta = ((((upv-updv)*(qty-ppc-psvn))*vpv)/100).toFixed(2);
				
				/*// OfferPrice Taxing: Masked For Now. Can Be Used Later.
				var opgsta = ((((ofpbpv-updv)*(qty-psvn))*vpv)/100).toFixed(2);*/
				
				if(stypecheck == "ls"){
					formobj.sgsta[i].value = (gsta/2).toFixed(2);
					formobj.cgsta[i].value = (gsta/2).toFixed(2);
					formobj.igsta[i].value = "0.00";
					formobj.siamt[i].value = (+((upv-updv) * (qty-psvn)) + +(formobj.sgsta[i].value) + +(formobj.cgsta[i].value)).toFixed(2);
					
					/*// OfferPrice Taxing: Masked For Now. Can Be Used Later.
					formobj.opcgsta[i].value = (opgsta/2).toFixed(2);
					formobj.opsgsta[i].value = (opgsta/2).toFixed(2);
					formobj.opigsta[i].value = "0.00";
					formobj.optota[i].value = (+((ofpbpv-updv) * (qty-psvn)) + +(formobj.opsgsta.value) + +(formobj.opcgsta.value)).toFixed(2);*/

				}else if(stypecheck == "iss"){
					formobj.igsta[i].value = (round(gsta,2));
					formobj.sgsta[i].value = "0.00";
					formobj.cgsta[i].value = "0.00";
					formobj.siamt[i].value = (+((upv-updv) * (qty-psvn)) + +(formobj.igsta[i].value)).toFixed(2);
					
					/*// OfferPrice Taxing: Masked For Now. Can Be Used Later.
					formobj.opcgsta[i].value = "0.00";
					formobj.opsgsta[i].value = "0.00";
					formobj.opigsta[i].value = (round(opgsta,2));
					formobj.optota[i].value = (+((ofpbpv-updv) * (qty-psvn)) + +(formobj.opigsta.value)).toFixed(2);*/

				}
				/*// OfferPrice Taxing: Masked For Now. Can Be Used Later.
				formobj.optaxablea[i].value = ((qty - psvn) * (parseFloat(ofpbpv) - parseFloat(updv))).toFixed(2);*/
				
				formobj.opcgsta[i].value = 0;
				formobj.opsgsta[i].value = 0;
				formobj.opigsta[i].value = 0;
				formobj.optota[i].value = 0;
				formobj.optaxablea[i].value = 0;
				
				formobj.ppsiamt[i].value = (+(((upv-updv) * (qty-ppc-psvn))) + +(ppgsta)).toFixed(2);
				formobj.txnamt[i].value = formobj.ppsiamt[i].value;
				totalAmt = +totalAmt + +(formobj.siamt[i].value);
				totalPPAmt = +totalPPAmt + +(formobj.txnamt[i].value);
				var vatamount=((((upv-updv)*(ppc))*vpv)/100).toFixed(2);
				formobj.onlinercvdamt[i].value =(+(((upv-updv)*ppc)+ +vatamount)).toFixed(2);
				
			}
			formobj.si_amt.value=Math.round(totalAmt);
			formobj.c_amt.value = (totalPPAmt).toFixed(2);
		}
		
	}else {
		document.getElementById("dialog-1").innerHTML = "Please ADD DOM REFILL SALES data and click then on CALCULATE";
		alertdialogue();
		//alert("Please ADD DOM REFILL SALES data and click then on CALCULATE");
		return;
	}
	document.getElementById("save_data").disabled=false;
	
/*	var efrz = document.getElementsByClassName("freez");
	makeEleReadOnly(efrz,efrz.length);
*/
	restrictChangingAllValues(".freez",".pid");
}

function validateCalcValues(upv,updv,vpv,qty,ppc,psvc) {
	
	var errmsg = "";
	var formobj = document.getElementById('data_form');
	
	if(!upv.length>0)
		  errmsg = "Please Enter UNIT PRICE. <br>";
	else if(validateDot(upv)) 
		errmsg = errmsg+"UNIT PRICE must contain atleast one number. <br>";
	else if(!(validateDecimalNumberMinMax(upv,0,100000)))
		errmsg = errmsg + "Please Enter valid UNIT PRICE and it must be in between 0 and 1,00,000 <br>";
	
	if(!qty.length>0)
		  errmsg = "Please Enter QUANTITY. <br>";
	else if(!(validateNumberMinMax(qty,0,10000)))
		errmsg = errmsg + "QUANTITY Must Be In Numerics And It Must Be In Between 0 And 10,000 .<br>";
	
	if(!vpv.length>0)
		  errmsg = "Please Enter GST%. <br>";
	else if(!(checkNumber(vpv)))
		errmsg = errmsg + "Please Enter Valid GST% .<br>";
	
	if(!updv.length>0)
	  errmsg = "Please Enter DISCOUNT ON UNIT PRICE.<br>";
	else if(validateDot(updv)) 
		errmsg = errmsg+"DISCOUNT ON UNIT PRICE. must contain atleast one number. <br>";
	else if(!(validateDecimalNumberMinMax(updv,-1,10000)))
		errmsg = errmsg + "Please enter Valid DISCOUNT ON UNIT PRICE and it must be >=0 and <10,000 .<br>";
	else if(!(parseFloat(updv)<parseFloat(upv)))
		errmsg = errmsg + "DISCOUNT ON UNIT PRICE Must Be Less Than UNIT PRICE.<br>";
	
	if(!(ppc.length>0))
		errmsg = "Please enter PREPAID CYLINDERS.If not available, enter 0.<br>";
	else if(!(checkNumber(ppc)))
		errmsg = errmsg + "PREPAID CYLINDERS Must Contain Only Numerics and can't be less than 0 .<br>";
	else if(!(validateNumberMinMax(ppc,-1,1000)))
		errmsg = errmsg + "Please Enter Valid PREPAID CYLINDERS And It Must Be In Between 0 And 1000 .<br>";

	if(!(psvc.length>0))
		errmsg = errmsg+"Please enter PSV CYLINDERS.If not available, enter 0.<br>";
	else if(!(checkNumber(psvc)))
		errmsg = errmsg + "PSV CYLINDERS Must Contain Only Numerics and can't be less than 0 .<br>";
	else if(!(validateNumberMinMax(psvc,-1,1000)))
		errmsg = errmsg + "Please Enter Valid PSV CYLINDERS and it must be in between 0 and 1000 .<br>";
	
	return errmsg;

}

function doRowDeleteInTranxs(tdobject,amt,inv_amt,tbody,c_amt,txnamt) {
	var count = tbody.getElementsByTagName('tr').length;
	var x = tdobject.parentNode.parentNode;

	if(count==1) {
		inv_amt.value = 0;
		c_amt.value = 0;
	}else if(count>1 && (amt[x.rowIndex-1].value)!= "") {
		inv_amt.value = parseInt(inv_amt.value)-parseInt(amt[x.rowIndex-1].value);
		c_amt.value = ((c_amt.value) - (txnamt[x.rowIndex-1].value)).toFixed(2);
	}
	tdobject.parentNode.parentNode.remove();
	
}