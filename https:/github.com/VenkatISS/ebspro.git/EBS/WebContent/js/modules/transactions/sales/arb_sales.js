/*function showARBSalesFormDialog() {
	var check = checkDayEndDateForAdd(dedate,a_created_date,effdate);
	if(check==true) {
		document.getElementById('myModal').style.display = "block";
	}else if(check!=true) {
		document.getElementById("dialog-1").innerHTML = check;
		alertdialogue();
		return;
	}
}*/
function showARBSalesFormDialog() {
	document.getElementById('myModal').style.display = "block";
	pushMenu();
}

function closeARBSalesFormDialog() {
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


//Construct Customer Type html
custdatahtml = "<OPTION VALUE='-1'>SELECT</OPTION>";
custdatahtml += "<OPTION VALUE='0'>CASH SALES </OPTION>";
if(cvo_data.length>0) {
	for(var z=0; z<cvo_data.length; z++){
		if(cvo_data[z].cvo_cat==1 && cvo_data[z].deleted==0 && cvo_data[z].cvo_name != "UJWALA")
			custdatahtml=custdatahtml+"<OPTION VALUE='"+cvo_data[z].id+"'>"+cvo_data[z].cvo_name+"</OPTION>";
	}
}
document.getElementById("cSpan").innerHTML = "<select id='cust_id' class='form-control input_field select_dropdown' name='cust_id' onchange='changePmntTerms()'>"+custdatahtml+"</select>";



/*new 23032018*/

function showCVOFormDialog() {
	document.getElementById('cvoModal').style.display = "block";
//	document.getElementById('myModalmarbpricePin').style.display = "none";
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

function showARBFormDialog() {
	document.getElementById('arbModal').style.display = "block";
//	document.getElementById('myModalmarbpricePin').style.display = "none";
	document.getElementById('marbSaveData').style.display="none";

    $("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');
}

function closeARBFormDialog() {
//	document.getElementById("data_form").reset();
	document.getElementById("marb_page_add_form").reset();
	document.getElementById('marb_page_add_table_body').innerHTML = "";
	document.getElementById('marb_page_add_table_div').style.display = 'none';
//	document.getElementById('saveEquipmentdiv').style.display='none';
	document.getElementById('arbModal').style.display = "none";
$("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');
}


function showPriceFormDialog() {
	document.getElementById('arbPriceModal').style.display = "block";
	document.getElementById('myModalmarbpricePin').style.display = "block";
	document.getElementById('saveARBpricepopupdiv').style.display = "none";
    $("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');
}

function closeRefillPriceFormDialog() {
//	document.getElementById("data_form").reset();
	document.getElementById("marbprice_page_data_form").reset();
	document.getElementById("pin_data_form").reset();
	document.getElementById('mARBpricepopup_page_add_table_body').innerHTML = "";
	document.getElementById('mymARBPriceDIV').style.display = 'none';
//	document.getElementById('saveRefillPricediv').style.display='none';
	document.getElementById('arbPriceModal').style.display = "none";
	$("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');
	document.getElementById('marbOfpNote').style.display="none";
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


 /*new end*/

function changePmntTerms() {
	var formobj = document.getElementById('data_form');
	var cvoid = formobj.cust_id.selectedIndex;
	var sptfrz= document.getElementById("pt").options;
	document.getElementById("pt").selectedIndex=0;
	if(cvoid == 1) {
		for (var i = 0; i < sptfrz.length; i++) {
			(sptfrz[i].value == 2) ? sptfrz[i].disabled = true : sptfrz[i].disabled = false ;
		}
	}else {
		enableSelect(sptfrz,sptfrz.length);		
	}
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


//Construct Staff html
staffdatahtml = "<OPTION VALUE='0'>SELECT</OPTION>";
if(staff_data.length>0) {
	for(var z=0; z<staff_data.length; z++){
		if(staff_data[z].deleted == 0){	
			staffdatahtml=staffdatahtml+"<OPTION VALUE='"+staff_data[z].id+"'>"+staff_data[z].emp_name+"</OPTION>";
		}
	}
}
document.getElementById("sSpan").innerHTML = "<select id='staff_id' class='form-control input_field select_dropdown' name='staff_id'>"+staffdatahtml+"</select>";

//Process page data
var rowpdhtml = "";
if(page_data.length>0) {
	for(var z=page_data.length-1 ; z>= 0; z--) {
		var ed = new Date(page_data[z].si_date);
		var custName = getCustomerName(cvo_data,page_data[z].customer_id);
		var staffName = getStaffName(staff_data,page_data[z].staff_id);
		for(var i=0; i<page_data[z].detailsList.length; i++)
		var bankName = getBankName(bank_data,page_data[z].detailsList[i].bank_id);
		var dlistLen = page_data[z].detailsList.length;
		var del = "<td align='center'><img src='images/delete.png' onclick='deleteItem("+page_data[z].id+","+page_data[z].si_date+","+dlistLen+")'></td>";
		if(page_data[z].deleted==2){
			del = "<td>TRANSACTION CANCELED</td>";
		}
		if(!staffName) {
			staffName = "NA";			
		}
		
		if(!bankName){				
			bankName = "NA";			
		}
		
		for(var i=page_data[z].detailsList.length-1 ; i>=0; i--){
			var spd = fetchARBProductDetails(cat_arb_types_data, page_data[z].detailsList[i].prod_code);
			//if(i==0) {

			if(page_data[z].deleted==2){
				rowpdhtml = rowpdhtml + "<tr><td>"+page_data[z].sr_no+"</td>";
			}else {
				rowpdhtml = rowpdhtml + "<tr><td><a href='javascript:showInvoice("+page_data[z].id+","+page_data[z].si_date+")'>"+page_data[z].sr_no+"</a></td>";
			}
			rowpdhtml = rowpdhtml + "<td>"+ed.getDate()+"-"+months[ed.getMonth()]+"-"+ed.getFullYear()+"</td>";
			rowpdhtml = rowpdhtml + "<td>"+custName+"</td>";
			rowpdhtml = rowpdhtml + "<td>"+staffName+"</td>";
			rowpdhtml = rowpdhtml + "<td>"+payment_terms_values[page_data[z].payment_terms]+"</td>";
			rowpdhtml = rowpdhtml + "<td>"+page_data[z].si_amount+"</td>";
				rowpdhtml = rowpdhtml + "<td>"+spd+"</td>"+
					"<td>"+page_data[z].detailsList[i].unit_rate+"</td>"+
					"<td>"+page_data[z].detailsList[i].disc_unit_rate+"</td>"+
					"<td>"+page_data[z].detailsList[i].quantity+"</td>"+
					"<td>"+bankName+"</td>"+
					"<td>"+page_data[z].detailsList[i].igst_amount+"</td>"+					
					"<td>"+page_data[z].detailsList[i].cgst_amount+"</td>"+
					"<td>"+page_data[z].detailsList[i].sgst_amount+"</td>"+
					"<td>"+page_data[z].detailsList[i].sale_amount+"</td>"+
					del+"</tr>";
			/*} else {
				rowpdhtml = rowpdhtml + "<tr><td><input type='text' class='form-control input_field' title='"+spd+"' value='" + spd +  "' readonly></td>"+
					"<td><input type='text' class='form-control input_field' title='"+page_data[z].detailsList[i].unit_rate+"' value='" + page_data[z].detailsList[i].unit_rate +  "' readonly></td>"+
					"<td><input type='text' class='form-control input_field' title='"+page_data[z].detailsList[i].disc_unit_rate+"' value='" + page_data[z].detailsList[i].disc_unit_rate +  "' readonly></td>"+
					"<td><input type='text' class='form-control input_field' title='"+page_data[z].detailsList[i].quantity+"' value='" + page_data[z].detailsList[i].quantity +  "' readonly></td>"+
					"<td><input type='text' class='form-control input_field' title='"+page_data[z].detailsList[i].sgst_amount+"' value='" + page_data[z].detailsList[i].sgst_amount +  "' readonly></td>"+
					"<td><input type='text' class='form-control input_field' title='"+page_data[z].detailsList[i].cgst_amount+"' value='" + page_data[z].detailsList[i].cgst_amount +  "' readonly></td>"+
					"<td><input type='text' class='form-control input_field' title='"+page_data[z].detailsList[i].sale_amount+"' value='" + page_data[z].detailsList[i].sale_amount +  "' readonly></td>"+
					"<td></td></tr>";
			}*/
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
			//<br>("Please enter all the values in current row,calculate and then add next row");
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
	
		a.innerHTML = "<td><select name='epid' id='epid' class='form-control input_field select_dropdown sadd pid blkcalc'>"+ctdatahtml+"</select></td>";
		b.innerHTML = "<td><input type=text class='form-control input_field eadd' name='vatp' id='vatp' size='6' readonly='readonly' style='background-color:#FAFAC2;' placeholder='Gst%'></td>";
		c.innerHTML = "<td><input type=text class='form-control input_field eadd' name='up' id='up' size='6' maxlength='7' readonly='readonly' style='background-color:#FAFAC2;' placeholder='Unit Price'></td>";
		d.innerHTML = "<td><input type=text class='form-control input_field freez eadd' name='upd' id='upd' size='6'  maxlength='7' placeholder='Discount On Unit Price' value='0.00' /></td>";
		e.innerHTML = "<td><input type=text class='form-control input_field qtyc freez eadd' name='qty' id='qty' maxlength='4' size='6' placeholder='Quantity'></td>";
		
		if(document.getElementById("pt").selectedIndex != 2){
			f.innerHTML = "<td><select name='bid' id='bid' class='form-control input_field select_dropdown freez'>"+bankdatahtml+"</select></td>";
			var formobj = document.getElementById('data_form');
			var sbfrz= formobj.bid[trcount].options;
			formobj.bid[trcount].selectedIndex = 0;
			disableSelect(sbfrz,sbfrz.length);
		}else
			f.innerHTML = "<td><select name='bid' id='bid' class='form-control input_field select_dropdown freez'>"+bankdatahtml+"</select></td>";
		
		g.innerHTML = "<td><input type=text class='form-control input_field eadd' name='siamt' id='siamt' size='6' readonly='readonly' style='background-color:#F3F3F3;' placeholder='Sale Amount'></td>";
		h.innerHTML = "<td><img src='images/delete.png' onclick='doRowDeleteInTranxs(this,siamt,si_amt,data_table_body)'></td>"+
							"<td>"+
							"<input type=hidden name='igsta' id='igsta'>"+	
							"<input type=hidden name='sgsta' id='sgsta'>"+
							"<input type=hidden name='cgsta' id='cgsta'>"+
							
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
			var samt = formobj.siamt.value.trim();
			var sgsta = formobj.sgsta.value.trim();
			var cgsta = formobj.cgsta.value.trim();
			var bidv = formobj.bid.value;
			
			if(checkDateFormate(sidate)){
				var ved = dateConvert(sidate);
				sidate=ved;
			}
			
			ems = validateEntries(sidate,cust,item,up,upd,qty,pt,samt,sgsta,cgsta,bidv);
		}else if (elements.length>1){
			for(var i=0; i<elements.length; i++){
				var item = formobj.epid[i].selectedIndex;
				var up = formobj.up[i].value.trim();
				var upd = formobj.upd[i].value.trim();
				var qty = formobj.qty[i].value.trim();
				var samt = formobj.siamt[i].value.trim();
				var sgsta = formobj.sgsta[i].value.trim();
				var cgsta = formobj.cgsta[i].value.trim();
				var bidv = formobj.bid[i].value;
				
				if(checkDateFormate(sidate)){
					var ved = dateConvert(sidate);
					sidate=ved;
				}
				ems = validateEntries(sidate,cust,item,up,upd,qty,pt,samt,sgsta,cgsta,bidv);
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
			$("#myDialogText").text("THIS CUSTOMER HAS EXCEEDED CREDIT LIMIT. DO YOU WANT TO CONTINUE?");
			confirmTxtDialogue(formobj);
			//if(confirm("THIS CUSTOMER HAS EXCEEDED CREDIT LIMIT. DO YOU WANT TO CONTINUE?") == false)
		}
	}else {
		if((cust==1) && (sinvamt>250000)){
			document.getElementById("dialog-1").innerHTML = "YOUR INVOICE AMOUNT EXCEEDS RS.2,50,000/- .IF YOU WANT TO ADD FURTHER,ADD IN NEXT INVOICE";
			alertdialogue();
			//alert("YOUR INVOICE AMOUNT EXCEEDS RS.2,50,000/- .IF YOU WANT TO ADD FURTHER,ADD IN NEXT INVOICE");
			return;
		}else if((cust!=1) && (sinvamt>250000)){
			$("#myDialogText").text("YOUR INVOICE AMOUNT EXCEEDS RS.2,50,000/-.DO YOU WANT TO CONTINUE?");
			confirmTxtDialogue(formobj);
			//if(confirm("YOUR INVOICE AMOUNT EXCEEDS RS.2,50,000/-.DO YOU WANT TO CONTINUE?") == false)
		}
	}
	
	var objId = obj.id;
	document.getElementById(objId).disabled = true;
	if(!((cctrl==2) || ((cust!=1) && (sinvamt>250000)))){
		formobj.submit();
	}
}


function checkForStock() {
	var formobj = document.getElementById('data_form');	
	if(document.getElementById("epid") != null) {
		var ele = document.getElementsByClassName("qtyc");
		if(ele.length==1) {
			var item = formobj.epid.value;
			var qty = formobj.qty.value.trim();
			for(var e=0;e<arb_data.length;e++) {
				if(arb_data[e].id == parseInt(item)) {
					var stock = arb_data[e].current_stock-qty;
					if(stock<0) {
						return false;
					} 
				}
			}
		}else if (ele.length>1) {
			for(var i=0; i<ele.length; i++) {
				var mitem = formobj.epid[i].value;
				var mqty = formobj.qty[i].value.trim();
				for(var e=0;e<arb_data.length;e++) {
					if(arb_data[e].id == parseInt(mitem)) {
						var mstock = arb_data[e].current_stock-mqty;
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


function validateEntries(sidate,cust,item,up,upd,qty,pt,samt,sgsta,cgsta,bidv){
	
	var formobj = document.getElementById('data_form');
	var errmsg = "";

	restrictChangingAllValues(".freez",".blkcalc");
	
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
	}else if(chkd != "true")
		errmsg = errmsg+chkd+"<br>";
	else if (vfd != "false")
		errmsg = errmsg +"INVOICE"+vfd+"<br>";
/*	else {
		var lastTrxnDateCheck = checkforLastTrxnDate(dedate,a_created_date,sidate,trdate);
		if(lastTrxnDateCheck != "false") {
			return lastTrxnDateCheck;
		}
	}*/

	if(!(cust>0))
		errmsg = errmsg+"Please select CUSTOMER NAME<br>";
		
	if(!(item>0))
		errmsg = errmsg+"Please select PRODUCT<br>";
	else if (!validateProduct())
		errmsg = errmsg + "Please Select Another PRODUCT Which Was not Selected.<br>";
	
	if(!up.length>0)
		  errmsg = errmsg + "Please Enter UNIT PRICE. <br>";
	else if(validateDot(up)) 
		errmsg = errmsg+"UNIT PRICE must contain atleast one number. <br>";
	else if(!(validateDecimalNumberMinMax(up,0,10000)))
		errmsg = errmsg + "Please enter valid UNIT PRICE and it must be in between 0 and 10,000 <br>";
	else
		formobj.up.value=round(parseFloat(up),2);

	if(!(upd.length>0))
		errmsg = errmsg+"Please enter UNIT PRICE DISCOUNT<br>";
	else if(validateDot(upd)) 
		errmsg = errmsg+"DISCOUNT ON UNIT PRICE must contain atleast one number. <br>";	
	else if(!(validateDecimalNumberMinMax(upd,-1,10000)))
		errmsg = errmsg + "Please enter Valid DISCOUNT ON UNIT PRICE and it must be >=0 and <10,000 .<br>";
	else if(parseFloat(upd)>parseFloat(up)) 
		errmsg = errmsg+"DISCOUNT ON UNIT PRICE must be less than UNIT PRICE.<br>";
	else
		formobj.upd.value=round(parseFloat(upd),2);
	
	if(!qty.length>0)
		  errmsg = errmsg + "Please Enter QUANTITY. <br>";
	else if(!(validateNumberMinMax(qty,0,10000)))
		errmsg = errmsg + "QUANTITY must be in Numerics and it must be in between 0 and 10,000 .<br>";

	if(!(pt>0))
		errmsg = errmsg+"Please select PAYMENT TERMS<br>";

	if( (pt==2) && (!(bidv>0)) )
		errmsg = errmsg+"Please select BANK ACCOUNT<br>";
	
	if(!(validateDecimalNumberMinMax(samt,0,10000000)))
		errmsg = errmsg + "Invalid SALE AMOUNT.<br>";

	if(!(validateDecimalNumberMinMax(sgsta,-1,10000000)))
		errmsg = errmsg + "Invalid GST..<br>";

	if(!(validateDecimalNumberMinMax(cgsta,-1,10000000)))
		errmsg = errmsg + "Invalid GST.. <br>";

	
	return errmsg;
}

/*function deleteItem(iid,invdate,detailsListlen) {
	var formobj = document.getElementById('m_data_form');
	var flag=validateDayEndForDelete(invdate,dedate);
	if(flag) {
		if(detailsListlen>1){
			if (confirm("THIS INVOICE CONTAINS MULTIPLE PRODUCTS.IF YOU DELETE THIS ONE TOTAL INVOICE WILL GET DELETED.ARE YOU SURE YOU WANT TO DELETE?") == true) {				
				formobj.actionId.value = "5133";
				formobj.dataId.value = iid;
				formobj.submit();
			}	
		}else if(detailsListlen==1){
			if (confirm("ARE YOU SURE YOU WANT TO DELETE?") == true) {	
				formobj.actionId.value = "5133";
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
			confirmDialogue(formobj,5133,iid);
		}else if(detailsListlen==1){
			$("#myDialogText").text("Are You Sure You Want To Delete?");
			confirmDialogue(formobj,5133,iid);	
		}
	}else {
		document.getElementById("dialog-1").innerHTML = "This record can't be deleted as you have submitted and verified your DAYEND calculations succuessfully";
		alertdialogue();
	}
}

function fetchPriceAndVAT(){
	var formobj = document.getElementById('data_form');
	if(document.getElementById("up") != null) {

		var sdate = new Date(formobj.si_date.value);
		var millisecs = sdate.getTime();
		
		var elements = document.getElementsByClassName("qtyc");
		if(elements.length==1) {
			var pide = formobj.epid;
			if(pide.selectedIndex > 0) {
				var pidv = pide.options[pide.selectedIndex].value;
				var upv = fetchARBUnitPrice(arb_prices_data,pidv);
				if(upv>0) {

					var prodDateInArbM = 0;
					for(var e=0 ; e < arb_data.length ; e++){
						if(arb_data[e].id == pidv){
							prodDateInArbM = arb_data[e].effective_date;
							break;
						}
					}
					if(millisecs < prodDateInArbM){
						document.getElementById("dialog-1").innerHTML = "The product you have selected is invalid for the given date. Plese check effective date of the added product in BLPG/ARB/NFR Master.";
						alertdialogue();
						//alert("please define the price of product for sale invoice month in price master and continue");
						return;
					}else {
						formobj.vatp.value = fetchARBGSTPercent(arb_data,pidv);
						formobj.up.value = upv;
						formobj.opbasicprice.value = fetchARBOfpBasicPrice(arb_prices_data,pidv);
					}
				}else {
					document.getElementById("dialog-1").innerHTML = "Please ADD PRODUCT price in BLPG/ARB/NFR PRICE MASTER";
					alertdialogue();
					//alert("Please ADD PRODUCT price in BLPG/ARB/NFR PRICE MASTER");
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
					var upv = fetchARBUnitPrice(arb_prices_data,pidv);
					if(upv>0) {
						
						var prodDateInArbM = 0;
						for(var e=0 ; e < arb_data.length ; e++){
							if(arb_data[e].id == pidv){
								prodDateInArbM = arb_data[e].effective_date;
								break;
							}
						}
						if(millisecs < prodDateInArbM) {
							document.getElementById("dialog-1").innerHTML = "The product you have selected is invalid for the given date. Plese check effective date of the added product in BLPG/ARB/NFR Master.";
							alertdialogue();
							//alert("please define the price of product for sale invoice month in price master and continue");
							return;
						}else {
							formobj.vatp[i].value = fetchARBGSTPercent(arb_data,pidv);
							formobj.up[i].value = upv;
							formobj.opbasicprice[i].value = fetchARBOfpBasicPrice(arb_prices_data,pidv);
						}
					}else {
						document.getElementById("dialog-1").innerHTML = "Please ADD PRODUCT price in BLPG/ARB/NFR PRICE MASTER";
						alertdialogue();
						//alert("Please ADD PRODUCT price in BLPG/ARB/NFR PRICE MASTER");
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
		document.getElementById("dialog-1").innerHTML = "Please select PRODUCT and then click on FETCH UNIT PRICE AND GST";
		alertdialogue();
		//alert("Please select PRODUCT and then click on FETCH UNIT PRICE AND GST");
		return;
	}
	restrictChangingAllValues(".freez",".blkcalc");
	document.getElementById("calc_data").disabled=false;
}

function calculateValues() {
	var cvogstin;
	var cvoreg;
	var stypecheck = "";
	var formobj = document.getElementById('data_form');
	if(document.getElementById("up") != null) {
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
			var ofpbpv = formobj.opbasicprice.value.trim();
			
			var ems = validateCalcValues(upv,updv,vpv,qty);
			if(ems.length>0){
				document.getElementById("dialog-1").innerHTML = ems;
				alertdialogue();
				//alert(ems);
				return;
			}
			formobj.up.value = parseFloat(upv).toFixed(2);
			formobj.upd.value = parseFloat(updv).toFixed(2);
			
			formobj.opbasicprice.value = parseFloat(ofpbpv).toFixed(2);
			
			var gsta = ((((upv-updv)*qty)*vpv)/100).toFixed(2);
			var opgsta = ((((ofpbpv-updv)*qty)*vpv)/100).toFixed(2);
			
			if(stypecheck=="ls"){
				formobj.sgsta.value = (gsta/2).toFixed(2);
				formobj.cgsta.value = (gsta/2).toFixed(2);
				formobj.igsta.value = "0.00";				
				formobj.siamt.value = (+((upv-updv) * qty) + +(formobj.sgsta.value) + +(formobj.cgsta.value)).toFixed(2);
				
				formobj.opcgsta.value = (opgsta/2).toFixed(2);
				formobj.opsgsta.value = (opgsta/2).toFixed(2);
				formobj.opigsta.value = "0.00";
				formobj.optota.value = (+((ofpbpv-updv) * qty) + +(formobj.opsgsta.value) + +(formobj.opcgsta.value)).toFixed(2);
				
			}else if(stypecheck == "iss") {
				formobj.igsta.value = (round(gsta,2));
				formobj.sgsta.value = "0.00";
				formobj.cgsta.value = "0.00";
				formobj.siamt.value = (+((upv-updv) * qty) + +(formobj.igsta.value)).toFixed(2);
				
				formobj.opcgsta.value = "0.00";
				formobj.opsgsta.value = "0.00";
				formobj.opigsta.value = (round(opgsta,2));
				formobj.optota.value = (+((ofpbpv-updv) * qty) + +(formobj.opigsta.value)).toFixed(2);
				
			}
			formobj.optaxablea.value = (qty * (parseFloat(ofpbpv) - parseFloat(updv))).toFixed(2);
			
			formobj.si_amt.value = Math.round(formobj.siamt.value);
		} else if (elements.length>1){
			var totalAmt = 0;
			for(var i=0; i<elements.length; i++){
				var upv = formobj.up[i].value.trim();
				var updv = formobj.upd[i].value.trim();
				var vpv = formobj.vatp[i].value.trim();
				var qty = formobj.qty[i].value.trim();
				var ofpbpv = formobj.opbasicprice[i].value.trim();
				
				var ems = validateCalcValues(upv,updv,vpv,qty);
				if(ems.length>0){
					document.getElementById("dialog-1").innerHTML = ems;
					alertdialogue();
					//alert(ems);
					return;
				}
				formobj.up[i].value = parseFloat(upv).toFixed(2);
				formobj.upd[i].value = round(parseFloat(updv),2);
				
				formobj.opbasicprice[i].value = parseFloat(ofpbpv).toFixed(2);
				
				var gsta = ((((upv-updv)*qty)*vpv)/100).toFixed(2);
				var opgsta = ((((ofpbpv-updv)*qty)*vpv)/100).toFixed(2);
				
				if(stypecheck == "ls"){
					formobj.sgsta[i].value = (gsta/2).toFixed(2);
					formobj.cgsta[i].value = (gsta/2).toFixed(2);
					formobj.igsta[i].value = "0.00";
					formobj.siamt[i].value = (+((upv-updv) * qty) + +(formobj.sgsta[i].value) + +(formobj.cgsta[i].value)).toFixed(2);
					
					formobj.opcgsta[i].value = (opgsta/2).toFixed(2);
					formobj.opsgsta[i].value = (opgsta/2).toFixed(2);
					formobj.opigsta[i].value = "0.00";
					formobj.optota[i].value = (+((ofpbpv-updv) * qty) + +(formobj.opsgsta[i].value) + +(formobj.opcgsta[i].value)).toFixed(2);
				}else if(stypecheck == "iss") {
					formobj.igsta[i].value = (round(gsta,2));
					formobj.sgsta[i].value = "0.00";
					formobj.cgsta[i].value = "0.00";
					formobj.siamt[i].value = (+((upv-updv) * qty) + +(formobj.igsta[i].value)).toFixed(2);
					
					formobj.opcgsta[i].value = "0.00";
					formobj.opsgsta[i].value = "0.00";
					formobj.opigsta[i].value = (round(opgsta,2));
					formobj.optota[i].value = (+((ofpbpv-updv) * qty) + +(formobj.opigsta[i].value)).toFixed(2);
					
				}
				formobj.optaxablea[i].value = (qty * (parseFloat(ofpbpv) - parseFloat(updv))).toFixed(2);
				totalAmt = +totalAmt + +(formobj.siamt[i].value);
				
			}
			formobj.si_amt.value=Math.round(totalAmt);
		}
		
	}else {
		document.getElementById("dialog-1").innerHTML = "Please ADD data and click CALCULATE";
		alertdialogue();
		//alert("Please ADD data and click CALCULATE");
		return;
	}
	document.getElementById("save_data").disabled=false;
	
/*	var efrz = document.getElementsByClassName("freez");
	makeEleReadOnly(efrz,efrz.length);
*/	
	restrictChangingAllValues(".freez",".blkcalc");
}

function validateCalcValues(upv,updv,vpv,qty) {
	
	var errmsg = "";
	var formobj = document.getElementById('data_form');
	
	if(!upv.length>0)
		  errmsg = errmsg + "Please Enter UNIT PRICE. <br>";
	else if(validateDot(upv)) 
		errmsg = errmsg+"UNIT PRICE must contain atleast one number. <br>";
	else if(!(validateDecimalNumberMinMax(upv,0,10000)))
		errmsg = errmsg + "Please enter valid UNIT PRICE and it must be in between 0 and 10,000 <br>";
	
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
	var w = window.open("PopupControlServlet?actionId=997&sitype=3&sid="+inv_id +"&si_date="+si_date,null,"height=400,width=1024,status=yes,toolbar=no,menubar=no,location=no");
	w.print();
	return false;
	
/*	window.open("PopupControlServlet?actionId=997&sitype=3&sid="+inv_id,null,"height=400,width=1024,status=yes,toolbar=no,menubar=no,location=no");*/
}