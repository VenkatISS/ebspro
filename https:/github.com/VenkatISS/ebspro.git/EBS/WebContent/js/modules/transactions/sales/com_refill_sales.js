function showCOMRSalesFormDialog() {
	document.getElementById('myModal').style.display = "block";
	pushMenu();
}

function closeCOMRSalesFormDialog() {
	document.getElementById("calc_data").disabled = true;
	document.getElementById("save_data").disabled = true;
	
	$(':radio:not(:checked)').attr('disabled', false);
	
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
//	document.getElementById('myModalmRefilpricepopupPin').style.display = "none";
//	document.getElementById('mcvodata_save_data').style.display = "none";
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
//	document.getElementById('myModalmRefilpricepopupPin').style.display = "none";
	document.getElementById('mequipopupsavediv').style.display="none";

    $("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');
}

function closeEquipmentFormDialog() {
//	document.getElementById("data_form").reset();
	document.getElementById("mequipopup_page_add_form").reset();
	document.getElementById('mequipopup_page_add_table_div').style.display = 'none';
	document.getElementById('mequipopup_page_add_table_body').innerHTML = "";
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
	document.getElementById('mymRefillPricePopupDIV').style.display = 'none';
	document.getElementById('mrefillpricepopup_page_add_table_body').innerHTML = "";
	document.getElementById('refillPriceModal').style.display = "none";
	$("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');
	document.getElementById('mrefillOfpNote').style.display="none";
}



function showBankFormDialog() {
	document.getElementById('bankModal').style.display = "block";
	document.getElementById('savembankpopupdiv').style.display = "none";
    $("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');}

function closeBankFormDialog() {
//	document.getElementById("data_form").reset();
	document.getElementById("mpopup_bank_data_form").reset();
	document.getElementById('bankModal').style.display = "none";
	document.getElementById('mpopup_bank_add_table_body').innerHTML = "";
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
	document.getElementById('staffModal').style.display = "none";
	document.getElementById('mpopup_staff_add_table_body').innerHTML = "";
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

/*//Construct Customer Type html
custdatahtml = "<OPTION VALUE='-1'>SELECT</OPTION>";
custdatahtml += "<OPTION VALUE='0'>CASH SALES / HOUSEHOLDS</OPTION>";
if(cvo_data.length>0) {
	for(var z=0; z<cvo_data.length; z++){
		if(cvo_data[z].cvo_cat==1 && cvo_data[z].deleted==0)
			custdatahtml=custdatahtml+"<OPTION VALUE='"+cvo_data[z].id+"'>"+cvo_data[z].cvo_name+"</OPTION>";
	}
}*/
/*document.getElementById("cSpan").innerHTML = "<select id='cust_id' class='form-control input_field' name='cust_id' onchange='changePmntTermsAndSaleType()'>"+custdatahtml+"</select>";*/

/*
//set bank account id
if(bank_data.length>0){
	for(var y=0; y<bank_data.length; y++){
		if(bank_data[y].bank_code == "TAR ACCOUNT"){
			document.getElementById('data_form').bankId.value = bank_data[y].id;
			break;
		}
	}
}*/

//Process page data
var rowpdhtml = "";
if(page_data.length>0) {
	for(var z=page_data.length-1; z>=0; z--){
		var ed = new Date(page_data[z].si_date);
		var custName = getCustomerName(cvo_data,page_data[z].customer_id);
		var dlistLen = page_data[z].detailsList.length;
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
				rowpdhtml = rowpdhtml + "<tr valign='top'><td>"+page_data[z].sr_no+"</td>";
			}else {
				rowpdhtml = rowpdhtml + "<tr valign='top'><td>"+"<a href='javascript:showInvoice("+page_data[z].id+","+page_data[z].si_date+")'>"+page_data[z].sr_no+"</a></td>";
			}
			rowpdhtml = rowpdhtml + "<td>"+ed.getDate()+"-"+months[ed.getMonth()]+"-"+ed.getFullYear()+"</td>";
			rowpdhtml = rowpdhtml + "<td>"+custName+"</td>";
			rowpdhtml = rowpdhtml + "<td>"+payment_terms_values[page_data[z].payment_terms]+"</td>";
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
				"<td>"+bankName+"</td>"+
				"<td>"+staffName+"</td>"+
				"<td>"+areaName+"</td>"+
				del+"</tr>";
		}
	}
}
document.getElementById('m_data_table_body').innerHTML = rowpdhtml;


function addRow(){
	var trcount = document.getElementById('data_table_body').getElementsByTagName('tr').length;
	if(trcount >0){
		var trv=document.getElementById('data_table_body').getElementsByTagName('tr')[trcount-1];
		var saddv=trv.getElementsByClassName('sadd');
		var eaddv=trv.getElementsByClassName('eadd');
		var res=checkRowData(saddv,eaddv);
		if(res == false){
			document.getElementById("dialog-1").innerHTML = "Please enter all the values in current row,calculate and then add next row";
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

		a.innerHTML = "<td><select name='epid' class='form-control input_field select_dropdown sadd pid' id='epid'>"+ctdatahtml+"</select></td>";
		b.innerHTML = "<td><input type=text name='vatp' class='form-control input_field eadd' id='vatp' size='6' readonly='readonly' style='background-color:#FAFAC2;' placeholder='Gst%'></td>";
		c.innerHTML = "<td><input type=text name='up' class='form-control input_field eadd' id='up' maxlength='7' value='0' size='6' readonly='readonly' style='background-color:#FAFAC2;'></td>";
		d.innerHTML = "<td><input type=text name='upd' class='form-control input_field freez eadd' id='upd' maxlength='7' size='6' placeholder='Discount On Unit Price' value='0.00'></td>";
		e.innerHTML = "<td><input type=text name='qty' class='form-control input_field qtyc freez eadd' id='qty' maxlength='4' size='6' placeholder='Quantity'></td>";
		f.innerHTML = "<td><input type=text name='prec' class='form-control input_field freez eadd' id='pre' maxlength='3' size='6' placeholder='PSV Cylinders'/></td>";
		g.innerHTML = "<td><input type=text name='psvc' class='form-control input_field freez eadd' id='psvc' maxlength='3' size='6' placeholder='Empties Received'/></td>";
		h.innerHTML = "<td><select name='sid' class='form-control input_field select_dropdown' id='sid' style='width:100px;'>"+staffdatahtml+"</select></td>";
		if(document.getElementById("pt").selectedIndex != 2){
			i.innerHTML = "<td><select name='bid' id='bid' class='form-control input_field select_dropdown freez'>"+bankdatahtml+"</select></td>";
			var formobj = document.getElementById('data_form');
			var sbfrz= formobj.bid[trcount].options;
			formobj.bid[trcount].selectedIndex = 0;
			disableSelect(sbfrz,sbfrz.length);
		}else
			i.innerHTML = "<td><select name='bid' id='bid' class='form-control input_field select_dropdown freez'>"+bankdatahtml+"</select></td>";

		j.innerHTML = "<td><select name='acid' class='form-control input_field select_dropdown' id='acid' style='width:100px;'>"+areacodeshtml+"</select></td>";
		k.innerHTML = "<td><input type=text name='siamt' class='form-control input_field eadd' id='siamt' size='6' readonly='readonly' style='background-color:#F3F3F3;' placeholder='Sale Amount'></td>";
		l.innerHTML = "<td><img src='images/delete.png' onclick='doRowDeleteInTranxs(this,siamt,si_amt,data_table_body)'></td>"+
							"<td>"+
							"<input type=hidden name='igsta' id='igsta'>"+
							"<input type=hidden name='sgsta' id='sgsta'>"+
							"<input type=hidden name='cgsta' id='cgsta'>"+
							"<input type=hidden name='ppsiamt' id='ppsimat'>"+
							
							"<input type=hidden name='optaxablea' id='optaxablea'>" +
							"<input type=hidden name='optota' id='optota'>" +
							"<input type=hidden name='opcgsta' id='opcgsta'>" +
							"<input type=hidden name='opsgsta' id='opsgsta'>" +
							"<input type=hidden name='opigsta' id='opigsta'>" +
							"<input type=hidden name='opbasicprice' id='opbasicprice'>" +
							"</td>";
		
		document.getElementById("fetch_data").disabled=false;
	}else {
		document.getElementById("dialog-1").innerHTML = "Please save the data and ADD again";
		alertdialogue();
		//alert("Please save the data and ADD again");
		return;
	}
}

function changePmntTermsAndSaleType() {
	var formobj = document.getElementById('data_form');
	var cvoid = document.getElementById('answerInputHidden').value.trim();
	var sptfrz = document.getElementById("pt").options;
	document.getElementById("pt").selectedIndex=0;
	if(parseInt(cvoid) == 0) {
		for (var i = 0; i < sptfrz.length; i++) {
			(sptfrz[i].value == 2) ? sptfrz[i].disabled = true : sptfrz[i].disabled = false ;
		}
	}else {
		enableSelect(sptfrz,sptfrz.length);	
	}

	$(':radio:not(:checked)').attr('disabled', false);
	document.getElementById("save_data").disabled = true;
}

function validateBankAccountOnChangeInPT() {
	var formobj = document.getElementById('data_form');
	var ptv = document.getElementById("pt").value;
	var elements = document.getElementsByClassName("qtyc");
	
	if(elements.length==1) {
		var sbfrz= document.getElementById("bid").options;
		if(ptv != 3){
			formobj.bid.selectedIndex = 0;
			disableSelect(sbfrz,sbfrz.length);
		}else {
			enableSelect(sbfrz,sbfrz.length);
		}
	}else if (elements.length>1){
		for(var i=0; i<elements.length; i++){
			var sbfrz= formobj.bid[i].options;
			if(ptv != 3){
				formobj.bid[i].selectedIndex = 0;
				disableSelect(sbfrz,sbfrz.length);
			}else {
				enableSelect(sbfrz,sbfrz.length);
			}
		}
	}
}

/*function changePmntTermsAndSaleType() {
	var formobj = document.getElementById('data_form');
	var cvoid = document.getElementById('answerInputHidden').value.trim();
	var sptfrz= document.getElementById("pt").options;
	if(parseInt(cvoid) == 0) {
		formobj.pt.selectedIndex = 1;
		disableSelect(sptfrz,sptfrz.length);
	}else {
		formobj.pt.selectedIndex = 0;
		enableSelect(sptfrz,sptfrz.length);	
	}

	$(':radio:not(:checked)').attr('disabled', false);
	document.getElementById("save_data").disabled = true;
}
*/

function setPPCValue(){
	var formobj = document.getElementById('data_form');
	var pt = formobj.pt.selectedIndex;
	if(pt==2) {
		formobj.prec.value = 0;
	}else {
		formobj.prec.value = "";
	}
}


function saveData(obj){
	var formobj = document.getElementById('data_form');
	
	var ems = "";
	var sinvamt = 0;
	
	if(document.getElementById("epid") != null) {
		var elements = document.getElementsByClassName("qtyc");
		var sidate = formobj.si_date.value.trim();

/*		var cust = formobj.cust_id.selectedIndex;*/
		var cust = document.getElementById('answerInputHidden').value.trim();
		sinvamt = formobj.si_amt.value.trim();
		var pt = formobj.pt.selectedIndex;
		
		if(elements.length==1) {
			var e = document.getElementById("epid");
			var productId = e.options[e.selectedIndex].value;

			var item = formobj.epid.selectedIndex;
			var up = formobj.up.value.trim();
			var upd = formobj.upd.value.trim();
			var qty = formobj.qty.value.trim();
			var prec = formobj.prec.value.trim();
			var psvc = formobj.psvc.value.trim();
			var samt = formobj.siamt.value.trim();
			var sgsta = formobj.sgsta.value.trim();
			var cgsta = formobj.cgsta.value.trim();
			var bidv = formobj.bid.value;
			
			if(checkDateFormate(sidate)){
				var ved = dateConvert(sidate);
				formobj.si_date.value = ved;
				sidate=ved;
			}
			
			ems = validateEntries(sidate,cust,item,up,upd,qty,prec,psvc,pt,samt,sgsta,cgsta,productId,bidv);
		}else if (elements.length>1){
			for(var i=0; i<elements.length; i++){				
				var e = formobj.epid[i];
				var productId = e.options[e.selectedIndex].value;

				var item = formobj.epid[i].selectedIndex;
				var up = formobj.up[i].value.trim();
				var upd = formobj.upd[i].value.trim();
				var qty = formobj.qty[i].value.trim();
				var prec = formobj.prec[i].value.trim();
				var psvc = formobj.psvc[i].value.trim();
				var samt = formobj.siamt[i].value.trim();
				var sgsta = formobj.sgsta[i].value.trim();
				var cgsta = formobj.cgsta[i].value.trim();
				var bidv = formobj.bid[i].value;
				
				if(checkDateFormate(sidate)){
					var ved = dateConvert(sidate);
					formobj.si_date[i].value = ved;
					sidate=ved;
				}
				ems = validateEntries(sidate,cust,item,up,upd,qty,prec,psvc,pt,samt,sgsta,cgsta,productId,bidv);
				if(ems.length>0)
					break;
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
		
	var cctrl = 0;
	if(parseInt(cust) != 0 && pt != 1){
		var cust_cb;
		for(var k=0;k<cust_cl_data.length;k++){
			for(var l=0;l<cvo_data.length;l++){
				if(cust == cvo_data[l].id)
					cust_cb = +(cvo_data[l].cbal)+ +(sinvamt);				
			}
			if(cust_cb > cust_cl_data[k].credit_limit){
				if(cust == cust_cl_data[k].cust_id)
					cctrl = cust_cl_data[k].cc_type;
			}	
		}
	}
	if(cctrl != 0){
		if(cctrl==1){
			document.getElementById("dialog-1").innerHTML = "THIS TRANSACTION CANNOT BE PROCEEDED FURTHER AS THIS CUSTOMER HAS EXCEEDED CREDIT LIMIT";
			alertdialogue();
			//alert("THIS TRANSACTION CANNOT BE PROCEEDED FURTHER AS THIS CUSTOMER HAS EXCEEDED CREDIT LIMIT.");
			return;
		}else if(cctrl==2){
			$("#myDialogText").text("THIS CUSTOMER HAS EXCEEDED CREDIT LIMIT. DO YOU WANT TO CONTINUE?");
			confirmTxtDialogue(formobj);
			/*if(confirm("THIS CUSTOMER HAS EXCEEDED CREDIT LIMIT. DO YOU WANT TO CONTINUE?") == false)
				return;*/
		}
	}else {
		if((parseInt(cust)==0) && (sinvamt>250000)) {
			document.getElementById("dialog-1").innerHTML = "YOUR INVOICE AMOUNT EXCEEDS RS.2,50,000/- .IF YOU WANT TO ADD FURTHER,ADD IN NEXT INVOICE";
			alertdialogue();
			//alert("YOUR INVOICE AMOUNT EXCEEDS RS.2,50,000/- .IF YOU WANT TO ADD FURTHER,ADD IN NEXT INVOICE");
			return;
		}else if((parseInt(cust) != 0) && (sinvamt>250000)){
			$("#myDialogText").text("YOUR INVOICE AMOUNT EXCEEDS RS.2,50,000/-.DO YOU WANT TO CONTINUE?");
			confirmTxtDialogue(formobj);
			/*if(confirm("YOUR INVOICE AMOUNT EXCEEDS RS.2,50,000/-.DO YOU WANT TO CONTINUE?") == false)
				return;*/
		}
	}
	
	var objId = obj.id;
	document.getElementById(objId).disabled = true;
	if(!((cctrl==2) || ((parseInt(cust) != 0) && (sinvamt>250000)))){
	formobj.submit();
	}
}

function checkForStock(){
	var formobj = document.getElementById('data_form');	
	if(document.getElementById("epid") != null) {
		var ele = document.getElementsByClassName("qtyc");
		if(ele.length==1) {
			var item = formobj.epid.value;
			var qty = formobj.qty.value.trim(); // for fulls
//			var psv = formobj.psvc.value.trim(); // for empties
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
//				var mpsv = formobj.psvc[i].value.trim();
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

function checkInvDate(sidate){
	var errmsg = "";
	
	var vd = isValidDate(sidate);
	var vfd = ValidateFutureDate(sidate);	
	var chkd = checkdate(sidate);

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
	}
	else if(chkd != "true")
		errmsg = errmsg+chkd+"<br>";
	else if (vfd != "false")
		errmsg = errmsg +"INVOICE"+vfd+"<br>";
/*	else {
		var lastTrxnDateCheck = checkforLastTrxnDate(dedate,a_created_date,sidate,trdate);
		if(lastTrxnDateCheck != "false") {
			return lastTrxnDateCheck;
		}
	}*/
	return errmsg;
}


function validateDisount(upd,productId){

	var formobj = document.getElementById('data_form');
	var cust = document.getElementById('answerInputHidden').value.trim();

	for(var c=0;c<cust_cl_data.length;c++) {
		var cust_id = cust_cl_data[c].cust_id;
		var disc_19kg_ndne=cust_cl_data[c].disc_19kg_ndne;
		var disc_19kg_cutting_gas=cust_cl_data[c].disc_19kg_cutting_gas;
		var disc_35kg_ndne=cust_cl_data[c].disc_35kg_ndne;
		var disc_47_5kg_ndne=cust_cl_data[c].disc_47_5kg_ndne;
		var disc_450kg_sumo=cust_cl_data[c].disc_450kg_sumo;


	if(cust_id == cust)
		{
		 if((productId === '5') && (upd > parseFloat(disc_19kg_ndne)))
			{
			return 'Discount of 19kg NDNE cylinder is not valid. It exceeds specified discount limit of customer. Please check it in credit limit master';
		}else if((productId === '6') && (upd > parseFloat(disc_19kg_cutting_gas)))
		{
			return 'Discount of 19kg CUTTING GAS cylinder is not valid. It exceeds specified discount limit of customer. Please check it in credit limit master';
		}else if((productId === '7') && (upd > parseFloat(disc_35kg_ndne)))
		{
			return 'Discount of 35kg NDNE cylinder is not valid. It exceeds specified discount limit of customer. Please check it in credit limit master';
		}else if((productId === '8') && (upd > parseFloat(disc_47_5kg_ndne)))
		{
			return 'Discount of 47.5kg NDNE cylinder is not valid. It exceeds specified discount limit of customer. Please check it in credit limit master';
		}else if((productId === '9') && (upd > parseFloat(disc_450kg_sumo)))
				{
			return 'Discount of 450kg SUMO cylinder is not valid. It exceeds specified discount limit of customer. Please check it in credit limit master';
				}
		}
	}
	return false;


}


function validateEntries(sidate,cust,item,up,upd,qty,prec,psvc,pt,samt,sgsta,cgsta,productId,bidv){
	
	var formobj = document.getElementById('data_form');
	var errmsg = "";

	restrictChangingAllValues(".freez",".sinvd");
	
	errmsg = checkInvDate(sidate);
	
/*	// kept in checkInvDate(sidate):
	var vd = isValidDate(sidate);
	var vfd = ValidateFutureDate(sidate);	
	var chkd = checkdate(sidate);
		
	if(!(sidate.length>0))
		errmsg = errmsg+"Please enter INVOICE DATE<br>";
	else if (vd != "false")
		errmsg = errmsg +vd+"<br>";
	else if(validateDayEndAdd(sidate,dedate)){
        errmsg = "INVOICE DATE should be after DayEndDate<br>";
        return errmsg;
	}else if(chkd != "true")
		errmsg = errmsg+chkd+"<br>";
	else if (vfd != "false")
		errmsg = errmsg +"INVOICE"+vfd+"<br>";
	else {
		var lastTrxnDateCheck = checkforLastTrxnDate(dedate,a_created_date,sidate,trdate);
		if(lastTrxnDateCheck != "false") {
			return lastTrxnDateCheck;
		}
	}*/
	
	if(!(cust.length>0))
		errmsg = errmsg+"Please enter CUSTOMER NAME.<br>";
	else {
		if((parseInt(cust)!=0) && (custarr.indexOf(parseInt(cust))) == -1)
			errmsg = errmsg + "The CUSTOMER you have entered is not valid. Please enter valid CUSTOMER <br>";
	}
	
	
	if($("input[type=radio][name=stype]:checked").length <= 0) {
		errmsg = errmsg + "Please select SALE TYPE<br>";			
	}  
	if(!(item>0))
		errmsg = errmsg+"Please select Product<br>";
	else if (!validateProduct())
		errmsg = errmsg + "Please Select Another PRODUCT Which Was not Selected.<br>";
	
	if(!up.length>0)
		  errmsg = errmsg + "Please Enter UNIT PRICE. <br>";
	else if(validateDot(up)) 
		errmsg = errmsg+"UNIT PRICE must contain atleast one number. <br>";
	else if(!(validateDecimalNumberMinMax(up,0,100000)))
		errmsg = errmsg + "Please enter valid UNIT PRICE and it must be in between 0 and 10,0000 <br>";
	else
		formobj.up.value=round(parseFloat(up),2);
	
	
	var diserr=validateDisount(upd,productId);

	if(!(upd.length>0))
		errmsg = errmsg+"Please enter Unit Price Disscount<br>";
	else if(validateDot(upd)) 
		errmsg = errmsg+"DISCOUNT ON UNIT PRICE must contain atleast one number. <br>";	
	else if(!(validateDecimalNumberMinMax(upd,-1,10000)))
		errmsg = errmsg + "Please enter Valid DISCOUNT ON UNIT PRICE and it must be >=0 and <10,000 .<br>";
	else if(parseFloat(upd)>parseFloat(up)) 
		errmsg = errmsg+"DISCOUNT ON UNIT PRICE must be less than UNIT PRICE.<br>";
	else if(diserr != false )
		errmsg = errmsg+diserr+'\n';
	else
		formobj.upd.value=round(parseFloat(upd),2);

	if(!qty.length>0)
		  errmsg = errmsg + "Please Enter QUANTITY. <br>";
	else if(!(validateNumberMinMax(qty,0,10000)))
		errmsg = errmsg + "QUANTITY must be in Numerics and it must be in between 0 and 10,000 .<br>";
	
	if(!(prec.length>0))
		errmsg = errmsg+"Please enter PSV CYLINDERS.If not available, enter 0.<br>";
	else if(!(checkNumber(prec)))
		errmsg = errmsg + "PSV CYLINDERS must contain only numerics and can't be less than 0 .<br>";
	else if(!(validateNumberMinMax(prec,-1,1000)))
		errmsg = errmsg + "Please Enter Valid PSV CYLINDERS and it must be in between 0 and 1000 .<br>";

	if(!(psvc.length>0))
		errmsg = errmsg+"Please enter EMPTIES RECEIVED.If not available, enter 0.<br>";
	else if(!(checkNumber(psvc)))
		errmsg = errmsg + "EMPTIES RECEIVED must contain only Numerics and can't be less than 0 .<br>";
	else if(!(validateNumberMinMax(psvc,-1,1000)))
		errmsg = errmsg + "Please Enter Valid EMPTIES RECEIVED and it must be in between 0 and 1000 .<br>";

	if(!(pt>0))
		errmsg = errmsg+"Please select PAYMENT TERMS<br>";
	
	if((pt==2) && (!(bidv>0)))
		errmsg = errmsg+"Please select BANK ACCOUNT<br>";		
	
	if(!(validateDecimalNumberMinMax(samt,-1,10000000)))
		errmsg = errmsg + "Invalid SALE AMOUNT.<br>";

	if(!(validateDecimalNumberMinMax(sgsta,-1,10000000)))
		errmsg = errmsg + "Invalid GST..<br>";

	if(!(validateDecimalNumberMinMax(cgsta,-1,10000000)))
		errmsg = errmsg + "Invalid GST.. <br>";
	
	return errmsg;
}

/*function deleteItem(iid,invdate,detailsListlen) {
	var flag=validateDayEndForDelete(invdate,dedate);
	var formobj = document.getElementById('m_data_form');
	if(flag) {
		if(detailsListlen>1){
			if (confirm("THIS INVOICE CONTAINS MULTIPLE PRODUCTS.IF YOU DELETE THIS ONE TOTAL INVOICE WILL GET DELETED.ARE YOU SURE YOU WANT TO DELETE?") == true) {
				formobj.actionId.value = "5128";
				formobj.dataId.value = iid;
				formobj.submit();
			}	
		}else if(detailsListlen==1){
			if (confirm("ARE YOU SURE YOU WANT TO DELETE?") == true) {
				formobj.actionId.value = "5128";
				formobj.dataId.value = iid;
				formobj.submit();
			}	
		}	
	}else 
		document.getElementById("dialog-1").innerHTML = "THIS RECORD CAN'T BE DELETED AS YOU HAVE SUBMITTED AND VERIFIED YOUR DAYEND CALCULATIONS SUCCUESSFULLY... ";
		alertdialogue();
		//alert("THIS RECORD CAN'T BE DELETED AS YOU HAVE SUBMITTED AND VERIFIED YOUR DAYEND CALCULATIONS SUCCUESSFULLY... ");
	
}*/


function deleteItem(iid,invdate,detailsListlen){
	var flag=validateDayEndForDelete(invdate,dedate);
	if(flag){
		var formobj = document.getElementById('m_data_form');
		if(detailsListlen>1){
			$("#myDialogText").text("THIS INVOICE CONTAINS MULTIPLE PRODUCTS.IF YOU DELETE THIS ONE TOTAL INVOICE WILL GET DELETED.ARE YOU SURE YOU WANT TO DELETE?");
			confirmDialogue(formobj,5128,iid);
		}else if(detailsListlen==1){
			$("#myDialogText").text("Are You Sure You Want To Delete?");
			confirmDialogue(formobj,5128,iid);	
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
		var err = checkInvDate(dsdate);
		
		if(err == ""){
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
						
						for(var p=0; p<cat_cyl_types_data.length; p++){
							var cproduct= cat_cyl_types_data[p].cat_name+"-"+cat_cyl_types_data[p].cat_desc;
							var prodid = cat_cyl_types_data[p].id;
							
							for(var i=refill_prices_data.length-1;i>=0;i--){
								if(cproduct == pide.options[pide.selectedIndex].innerHTML) {
									if(prodid == refill_prices_data[i].prod_code) {
										
									    if(dsdate != ""){
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
									    		formobj.opbasicprice.value = fetchRefillOfferPriceBp(refill_prices_data,pidv);
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

			} else if (elements.length>1){
				for(var i=0; i<elements.length; i++){
					var pide = formobj.epid[i];
					if(pide.selectedIndex > 0) {
						var pidv = pide.options[pide.selectedIndex].value;
						fetchGSTPercent(equipment_data,pidv);
						var gstp = fetchGSTPercent(equipment_data,pidv);
						if(gstp>0) {
							
							var prodDateInEqpm = 0;
							for(var e=0 ; e < equipment_data.length ; e++){
								if(equipment_data[e].prod_code == pidv){
									prodDateInEqpm = equipment_data[e].effective_date;
									break;
								}
							}
							
							for(var q=0; q<cat_cyl_types_data.length; q++){
								var cproduct= cat_cyl_types_data[q].cat_name+"-"+cat_cyl_types_data[q].cat_desc;
								var prodid = cat_cyl_types_data[q].id;
								for(var k=refill_prices_data.length-1;k>=0;k--){
									if(cproduct == pide.options[pide.selectedIndex].innerHTML) {
										if(prodid == refill_prices_data[k].prod_code) {
											
											if(dsdate != ""){	
												if(!(refill_prices_data[k].montha == mon && pyear[refill_prices_data[k].yeara] == yr)){
													document.getElementById("dialog-1").innerHTML = "please define the price of product for sale invoice month in price master and continue";
													alertdialogue();
													//alert("please define the price of product for sale invoice month in price master and continue");
													return;
												}else if(millisecs < prodDateInEqpm){
													document.getElementById("dialog-1").innerHTML = "The product you have selected is invalid for the given date. Plese check effective date of the added product in Equipment Master.";
													alertdialogue();
													//alert("please define the price of product for sale invoice month in price master and continue");
													return;													
												}else{
													formobj.vatp[i].value = gstp;
													formobj.up[i].value = fetchRefillUnitPrice(refill_prices_data,pidv);
													formobj.opbasicprice[i].value = fetchRefillOfferPriceBp(refill_prices_data,pidv);
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
					}else {
						document.getElementById("dialog-1").innerHTML = "Please select PRODUCT and then click on FETCH UNIT PRICE AND GST";
						alertdialogue();
						//alert("Please select PRODUCT and then click on FETCH UNIT PRICE AND GST");
						return;
					}					
				}
			}
		}else{
			document.getElementById("dialog-1").innerHTML = err;
			alertdialogue();
			//alert(err);
			return;
		}
	}else {
		document.getElementById("dialog-1").innerHTML = "Please ADD COM REFILL SALES data and then click on FETCH UNIT PRICE AND GST";
		alertdialogue();
		//alert("Please ADD COM REFILL SALES data and then click on FETCH UNIT PRICE AND GST");
		return;
	}
	restrictChangingAllValues(".freez",".pid");
	document.getElementById("calc_data").disabled=false;	
}


function calculateValues() {
	var cvogstin;
	var cvoreg;
	var formobj = document.getElementById('data_form');
	
	if(document.getElementById("up") != null) {
		var stypev = formobj.stype.value;
		var cust = document.getElementById('answerInputHidden').value.trim();
		if(cust.length >0) {
			if((parseInt(cust)!=0) && (custarr.indexOf(parseInt(cust)) == -1)) {
				document.getElementById("dialog-1").innerHTML = "The customer you have entered is not valid. Please enter valid customer";
				alertdialogue();
				//alert("The customer you have entered is not valid. Please enter valid customer ");
				return;
			}else {
				for(var d=0;d< cvo_data.length;d++){
					if(parseInt(cust) == cvo_data[d].id) {
						cvogstin = cvo_data[d].cvo_tin;
						cvoreg = cvo_data[d].is_gst_reg;
					}
				}
			}	
		}else {
			document.getElementById("dialog-1").innerHTML = "please enter CUSTOMER";
			alertdialogue();
			//alert("please enter CUSTOMER");
			return;
		}
		if((parseInt(cust) != 0) && (cvoreg==1)) {
			var dstcode = dealergstin.substr(0, 2);
			var cvstcode = cvogstin.substr(0, 2);
			var stypecheck = "";
			if(cvstcode == dstcode)
				stypecheck = "ls"; 
			else 
				stypecheck = "iss";
		}else if((parseInt(cust) != 0) && (cvoreg==2)){
			stypecheck = stypev;
		}else if(parseInt(cust) == 0){
			stypecheck = "ls";
		}

		var elements = document.getElementsByClassName("qtyc");
		if(elements.length==1) {
			var upv = formobj.up.value.trim();
			var updv = formobj.upd.value.trim();
			var vpv = formobj.vatp.value.trim();
			var qty = formobj.qty.value.trim();
			var ppc = formobj.prec.value.trim();
			var psvc = formobj.psvc.value.trim();
			var ofpbpv = formobj.opbasicprice.value.trim();
			
			var ems = validateCalcValues(upv,updv,vpv,qty,ppc,psvc,stypev,stypecheck);
			if(ems.length>0){
				document.getElementById("dialog-1").innerHTML = ems;
				alertdialogue();
				//alert(ems);
				return;
			}	
			
			formobj.up.value = parseFloat(upv).toFixed(2);
			formobj.upd.value = round(parseFloat(updv),2);
			
			formobj.opbasicprice.value = parseFloat(ofpbpv).toFixed(2);
			
			var gsta = ((((upv-updv)*(qty-ppc))*vpv)/100).toFixed(2);
			var ppgsta = ((((upv-updv)*(qty-ppc))*vpv)/100).toFixed(2);
			
			var opgsta = ((((ofpbpv-updv)*(qty-ppc))*vpv)/100).toFixed(2);
			if(stypev=='ls') {
				formobj.sgsta.value = (gsta/2).toFixed(2);
				formobj.cgsta.value = (gsta/2).toFixed(2);
				formobj.igsta.value=0.00;
				formobj.siamt.value = (+(((upv-updv) * (qty-ppc))) + +(formobj.sgsta.value) + +(formobj.cgsta.value)).toFixed(2);
				
				formobj.opcgsta.value = (opgsta/2).toFixed(2);
				formobj.opsgsta.value = (opgsta/2).toFixed(2);
				formobj.opigsta.value = "0.00";
				formobj.optota.value = (+(((ofpbpv-updv) * (qty-ppc))) + +(formobj.opsgsta.value) + +(formobj.opcgsta.value)).toFixed(2);
			}
			else if (stypev=='iss'){
				formobj.igsta.value=gsta;
				formobj.sgsta.value=0.00;
				formobj.cgsta.value=0.00;
				formobj.siamt.value = (+(((upv-updv) * (qty-ppc))) + +(formobj.igsta.value)).toFixed(2);
				
				formobj.opcgsta.value = "0.00";
				formobj.opsgsta.value = "0.00";
				formobj.opigsta.value = (round(opgsta,2));
				formobj.optota.value = (+(((ofpbpv-updv) * (qty-ppc))) + +(formobj.opigsta.value)).toFixed(2);
				
			}
			formobj.optaxablea.value = ((qty - ppc) * (parseFloat(ofpbpv) - parseFloat(updv))).toFixed(2);
			
			formobj.si_amt.value = Math.round(formobj.siamt.value);
			formobj.ppsiamt.value = (+(((upv-updv) * (qty-ppc))) + +(ppgsta)).toFixed(2);
			formobj.c_amt.value = Math.round(formobj.si_amt.value - +(formobj.ppsiamt.value));
		} else if (elements.length>1){
			var totalAmt = 0;
			var totalPPAmt = 0;
			for(var i=0; i<elements.length; i++){
				var upv = formobj.up[i].value.trim();
				var updv = formobj.upd[i].value.trim();
				var vpv = formobj.vatp[i].value.trim();
				var qty = formobj.qty[i].value.trim();
				var ppc = formobj.prec[i].value.trim();
				var psvc = formobj.psvc[i].value.trim();
				var ofpbpv = formobj.opbasicprice[i].value.trim();
				
				var ems = validateCalcValues(upv,updv,vpv,qty,ppc,psvc,stypev,stypecheck);
				if(ems.length>0){
					document.getElementById("dialog-1").innerHTML = ems;
					alertdialogue();
					//alert(ems);
					return;
				}
				formobj.up[i].value = parseFloat(upv).toFixed(2);
				formobj.upd[i].value = round(parseFloat(updv),2);
				
				formobj.opbasicprice[i].value = parseFloat(ofpbpv).toFixed(2);
				
				var gsta = ((((upv-updv)*(qty-ppc))*vpv)/100).toFixed(2);
				var ppgsta = ((((upv-updv)*(qty-ppc))*vpv)/100).toFixed(2);
				
				var opgsta = ((((ofpbpv-updv)*(qty-ppc))*vpv)/100).toFixed(2);
				if(stypev=='ls') {
					formobj.sgsta[i].value = (gsta/2).toFixed(2);
					formobj.cgsta[i].value = (gsta/2).toFixed(2);
					formobj.igsta[i].value=0.00;
					formobj.siamt[i].value = (+(((upv-updv) * (qty-ppc))) + +(formobj.sgsta[i].value) + +(formobj.cgsta[i].value)).toFixed(2);
					
					formobj.opcgsta[i].value = (opgsta/2).toFixed(2);
					formobj.opsgsta[i].value = (opgsta/2).toFixed(2);
					formobj.opigsta[i].value = "0.00";
					formobj.optota[i].value = (+(((ofpbpv-updv) * (qty-ppc))) + +(formobj.opsgsta[i].value) + +(formobj.opcgsta[i].value)).toFixed(2);
					
				}
				else if (stypev=='iss'){
					formobj.igsta[i].value=gsta;
					formobj.sgsta[i].value=0.00;
					formobj.cgsta[i].value=0.00;
					formobj.siamt[i].value = (+(((upv-updv) * (qty-ppc))) + +(formobj.igsta[i].value)).toFixed(2);
					
					formobj.opcgsta[i].value = "0.00";
					formobj.opsgsta[i].value = "0.00";
					formobj.opigsta[i].value = (round(opgsta,2));
					formobj.optota[i].value = (+(((ofpbpv-updv) * (qty-ppc))) + +(formobj.opigsta[i].value)).toFixed(2);
					
				}
				formobj.optaxablea[i].value = ((qty - ppc) * (parseFloat(ofpbpv) - parseFloat(updv))).toFixed(2);
				
				formobj.ppsiamt[i].value = (+(((upv-updv) * (qty-ppc))) + +(ppgsta)).toFixed(2);

				totalAmt = +totalAmt + +(formobj.siamt[i].value);
				totalPPAmt = +totalPPAmt + +(formobj.ppsiamt[i].value);
			}
			formobj.si_amt.value=Math.round(totalAmt);
			formobj.c_amt.value = Math.round(formobj.si_amt.value - +(totalPPAmt));
		}
		
	}else {
		document.getElementById("dialog-1").innerHTML = "Please ADD COM REFILL SALES data and Click CALCULATE";
		alertdialogue();
		//alert("Please ADD COM REFILL SALES data and Click CALCULATE");
		return;
	}
	document.getElementById("save_data").disabled=false;
	$(':radio:not(:checked)').attr('disabled', true);	

/*	var efrz = document.getElementsByClassName("freez");
	makeEleReadOnly(efrz,efrz.length);
*/
	restrictChangingAllValues(".freez",".pid");
}



function validateCalcValues(upv,updv,vpv,qty,ppc,psvc,stype,stypecheck) {
	
	var errmsg = "";
	var formobj = document.getElementById('data_form');
	
	if($("input[type=radio][name=stype]:checked").length <= 0) {
		errmsg = errmsg + "Please select SALE TYPE<br>";			
	}
	if(($("input[type=radio][name=stype]:checked").length > 0)&&(stypecheck != stype))
		errmsg = errmsg + "The SALE TYPE you have selected might be wrong.Please check it again<br>";
	
	if(!upv.length>0)
		  errmsg = errmsg + "Please Enter UNIT PRICE. <br>";
	else if(validateDot(upv)) 
		errmsg = errmsg+"UNIT PRICE must contain atleast one number. <br>";
	else if(!(validateDecimalNumberMinMax(upv,0,100000)))
		errmsg = errmsg + "Please enter valid UNIT PRICE and it must be in between 0 and 1,00,000 <br>";

	if(!qty.length>0)
		  errmsg = errmsg + "Please Enter QUANTITY. <br>";
	else if(!(validateNumberMinMax(qty,0,10000)))
		errmsg = errmsg + "QUANTITY must be in Numerics and it must be in between 0 and 10,000 .<br>";
	
	if(!vpv.length>0)
		  errmsg = errmsg + "Please enter GST%. <br>";
	else if(!(checkNumber(vpv)))
		errmsg = errmsg + "Please enter valid GST% .<br>";
	
	if(!updv.length>0)
	  errmsg = errmsg + "Please enter DISCOUNT ON UNIT PRICE.<br>";
	else if(validateDot(updv)) 
		errmsg = errmsg+"DISCOUNT ON UNIT PRICE must contain atleast one number. <br>";
	else if(!(validateDecimalNumberMinMax(updv,-1,10000)))
		errmsg = errmsg + "Please enter Valid DISCOUNT ON UNIT PRICE and it must be >=0 and <10,000 .<br>";
	else if(!(parseFloat(updv)<parseFloat(upv)))
		errmsg = errmsg + "DISCOUNT ON UNIT PRICE must be less than UNIT PRICE.<br>";

	if(!(ppc.length>0))
		errmsg = errmsg+"Please enter PSV CYLINDERS.If not available, enter 0.<br>";
	else if(!(checkNumber(ppc)))
		errmsg = errmsg + "PSV CYLINDERS  must contain only Numerics and can't be less than 0.<br>";
	else if(!(validateNumberMinMax(ppc,-1,1000)))
		errmsg = errmsg + "Please Enter Valid PSV CYLINDERS and it must be in between 0 and 1000 .<br>";

	if(!(psvc.length>0))
		errmsg = errmsg+"Please enter EMPTIES RECEIVED.If not available, enter 0.<br>";
	else if(!(checkNumber(psvc)))
		errmsg = errmsg + "EMPTIES RECEIVED must contain only Numerics and can't be less than 0 .<br>";
	else if(!(validateNumberMinMax(psvc,-1,1000)))
		errmsg = errmsg + "Please Enter Valid EMPTIES RECEIVED and it must be in between 0 and 1000 .<br>";

	return errmsg;

}

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
	popitup(inv_id,si_date)
}

function popitup(inv_id,si_date) {
//	window.open("PopupControlServlet?actionId=998&sitype=2&sid="+inv_id,null,"height=400,width=1024,status=yes,toolbar=no,menubar=no,location=no");

	var w=window.open("PopupControlServlet?actionId=998&sitype=2&sid="+inv_id +"&si_date="+si_date,null,"height=400,width=1024,status=yes,toolbar=no,menubar=no,location=no");
	w.print();
	return false;
}