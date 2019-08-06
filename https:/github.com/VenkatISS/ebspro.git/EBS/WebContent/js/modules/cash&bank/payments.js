vendordatahtml = "";
if(cvo_data.length>0) {
	for(var z=0; z<cvo_data.length; z++){
		if((cvo_data[z].cvo_cat == 0 || cvo_data[z].cvo_cat == 3) && cvo_data[z].deleted==0)
			vendordatahtml=vendordatahtml+"<OPTION DATA-VALUE='"+cvo_data[z].id+"'>"+cvo_data[z].cvo_name+"</OPTION>";
	}
}

//Construct Customer Type html
custdatahtml = "";
if(cvo_data.length>0) {
	for(var z=0; z<cvo_data.length; z++){
		if(cvo_data[z].cvo_cat==1 && cvo_data[z].deleted==0 && cvo_data[z].cvo_name != "UJWALA")
			custdatahtml=custdatahtml+"<OPTION DATA-VALUE='"+cvo_data[z].id+"'>"+cvo_data[z].cvo_name+"</OPTION>";
	}
}


/*function showPMNTSFormDialog() {
	var check = checkDayEndDateForAdd(dedate,a_created_date,effdate);
	if(check == true) {
		document.getElementById('myModal').style.display = "block";
	}else{
		document.getElementById("dialog-1").innerHTML = "Please Submit Previous DayEnd inorder to add todays data.";
		alertdialogue();
		//alert("Please Submit Previous DayEnd inorder to add todays data.");
	}
}
*/

function showPMNTSFormDialog() {
	document.getElementById('myModal').style.display = "block";
	pushMenu();
}

function closePMNTSFormDialog() {
	document.getElementById("page_data_form").reset();
	var tbody = document.getElementById('page_add_table_body');	
	var count = document.getElementById('page_add_table_body').getElementsByTagName('tr').length;
	for(var a=0; a<count; a++){
		if(a>0)
			tbody.deleteRow(-1);
	}
	document.getElementById('myModal').style.display = "none";
    $("body").removeClass('sidebar-collapse').trigger('expanded.pushMenu');

}

function showInvoice(inv_id,pymt_date){
	popitup(inv_id,pymt_date);
}

function popitup(inv_id,pymt_date) { 
	
//	window.open("PopupControlServlet?actionId=999&sitype=1&sid="+inv_id,null,"height=400,width=1024,status=yes,toolbar=no,menubar=no,location=no");
	
	//newwindow=window.open("http://localhost:8080/ERPAPP/ControlServlet");
	//window.open('page2.jsp','popuppage','width=850,toolbar=1,resizable=1,scrollbars=yes,height=700,top=100,left=100');
	var w=window.open("PopupControlServlet?actionId=987&sitype=13&sid="+inv_id +"&si_date="+pymt_date,null,"height=400,width=1024,status=yes,toolbar=no,menubar=no,location=no");
	/*if (window.focus) {
		newwindow.focus();
	}  */ 	
    w.print();
	return false; 
}

//Set Page Data
var tbody = document.getElementById('page_data_table_body');
for(var f=page_data.length-1; f>=0; f--){
	var ed = new Date(page_data[f].pymt_date);

	var venName;
	if(page_data[f].cust_ven != 3)
		venName = getCustomerName(cvo_data,page_data[f].paid_to);
	else
		venName = taxt[page_data[f].tax_type];
	
	var staffName = getStaffName(staff_data,page_data[f].staff_id);

	var bankName;
	if(!staffName){
		staffName = "NA";	
	}
	if(page_data[f].debited_bank == 1)
		bankName = "CASH";
	else
		bankName = getBankName(bank_data,page_data[f].debited_bank);

	var charges = page_data[f].bank_charges;
	
	if(charges == "")
		charges = "-";
	
	var del = "<td align='center'><img src='images/delete.png' onclick='deleteItem("+page_data[f].id+","+page_data[f].pymt_date+")'></td>";
	if(page_data[f].deleted==2){
		del = "<td>TRANSACTION CANCELED</td>";
	}
	
	var tblRow = tbody.insertRow(-1);

	if(page_data[f].deleted==2){
		invno = "<td>"+page_data[f].sr_no+"</td>";
	}else {
		invno = "<td><a href='javascript:showInvoice("+page_data[f].id+","+page_data[f].pymt_date+")'>"+page_data[f].sr_no+"</a></td>";
	}
	
	tblRow.style.height="20px";
	tblRow.align="left";
	tblRow.innerHTML = invno+
		//"<td>"+ page_data[f].sr_no +"</td>"+
		"<td>"+ ed.getDate()+"-"+months[ed.getMonth()]+"-"+ed.getFullYear() +"</td>"+
		"<td>"+ page_data[f].pymt_amount+"</td>"+
		"<td>"+ charges +"</td>"+		
		"<td>"+ venName +"</td>"+
		"<td>"+ mops[page_data[f].payment_mode] +"</td>"+
		"<td>"+ page_data[f].instr_details +"</td>"+
		"<td>"+ bankName +"</td>"+
		"<td>"+ staffName +"</td>"+
		"<td>"+ page_data[f].narration +"</td>"+
		del+
		"</tr>";
};



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

function showBankFormDialog() {
	document.getElementById('bankModal').style.display = "block";
	document.getElementById('savembankpopupdiv').style.display = "none";
    $("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');}

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
/*function addRow() {
	var trcount = document.getElementById('page_add_table_body').getElementsByTagName('tr').length;
	if(trcount>0){
		var trv=document.getElementById('page_add_table_body').getElementsByTagName('tr')[trcount-1];
		var saddv=trv.getElementsByClassName('sadd');
		var eaddv=trv.getElementsByClassName('eadd');
		
		var res=checkRowData(saddv,eaddv);
		if(res == false){
			alert("Please enter all the values in current row and then add next row");
			return;
		}		
	
	
	var tbody = document.getElementById('page_add_table_body');
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
	
	a.innerHTML = "<td valign='top' height='4' align='center'></td>";
	b.innerHTML = "<td valign='top' height='4' align='center'><input type=date name='pdate' id='pdate' class='form-control input_field bpc eadd'></td>";
	c.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='pamt' id='pamt' maxlength='9' class='form-control input_field eadd' placeholder='AMOUNT'></td>";
	d.innerHTML = "<td valign='top' height='4' align='center'><select name='pto' id='pto' class='form-control input_field select_dropdown sadd'>"
		+ custdatahtml + "</select></td>";
	e.innerHTML = "<td valign='top' height='4' align='center'><select name='mop' id='mop' onchange='changeBank()' class='form-control input_field select_dropdown sadd'>"
		+ "<option value='0'>SELECT</option>"
		+ "<option value='1'>CASH</option>"
		+ "<option value='2'>CHEQUE</option>"
		+ "<option value='3'>ONLINE TRANSFER</option>" + "</select></td>";
	f.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='instrd' id='instrd' value='NA' maxlength='30' class='form-control input_field eadd' placeholder='INSTRUMENT DETAILS'></td>";
	g.innerHTML = "<td valign='top' height='4' align='center'><select name='bid' id='bid' class='form-control input_field select_dropdown sadd'>"
		+ bankdatahtml + "</select></td>";
	h.innerHTML = "<td valign='top' height='4' align='center'><select name='sid' id='sid' class='form-control input_field select_dropdown sadd'>"
		+ staffdatahtml + "</select></td>";
	i.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='nar' id='nar' size='18' maxlength='200' class='form-control input_field eadd' placeholder='NARRATION'></td>";
	j.innerHTML = "<td valign='top' height='4' align='center'><img src='images/delete.png' onclick='doRowDelete(this)'></td>";
}*/	
	
function changeBank() {
	var formobj = document.getElementById('page_data_form');
	var emop = formobj.mop.selectedIndex;
	var charges = document.getElementById("charges");
	if(emop == 1) {
		formobj.bid.selectedIndex=1;
		charges.value = 0.00;
		charges.setAttribute("readOnly","true");
	}else {
		formobj.bid.selectedIndex=0;
		charges.readOnly = false;
	}
}

function setMOPandPto() {
	document.getElementById("answerInput").value="";
	
    if((document.getElementById("cv").value != 3) && (document.getElementById("cv").value != 0)) {
    	document.getElementById("taxseldiv").style.display = "none";
    	document.getElementById("cvseldiv").style.display = "block";
    }else if(document.getElementById("cv").value == 3) {
    	document.getElementById("cvseldiv").style.display = "none";
    	document.getElementById("taxseldiv").style.display = "block";
    }

    var smopfrz= document.getElementById("mop").options;
    enableSelect(smopfrz,smopfrz.length);
    var sbfrz= document.getElementById("bid").options;
	sbfrz[1].disabled = false; // For enabling CASH

	if(document.getElementById("cv").selectedIndex == 2){
    	document.getElementById("mop").selectedIndex = 2;
    	disableSelect(smopfrz,smopfrz.length);
    }else if(document.getElementById("cv").selectedIndex == 3) {
    	document.getElementById("mop").selectedIndex = 3;
    	disableSelect(smopfrz,smopfrz.length);
    	sbfrz[1].setAttribute("disabled","true"); // To disable CASH for Tax type.
    }else {
    	enableSelect(smopfrz,smopfrz.length);
    	document.getElementById("mop").selectedIndex = 0;
    }
}


//document.getElementById("cSpan").innerHTML = "<select id='cvo_id' name='cvo_id' class='form-control input_field select_dropdown sadd' ><OPTION VALUE='0'>SELECT</OPTION></select>";

/*function changecv(){
	var formobj = document.getElementById('page_data_form');

	document.getElementById('mop').selectedIndex=0;
	document.getElementById("mop").options[2].disabled = false;
    document.getElementById("mop").disabled=false;

    var si = formobj.cv.selectedIndex;
	var spfrz= document.getElementById("mop").options;	
	if(si==0) {
		enableSelect(spfrz,spfrz.length);
		document.getElementById("cSpan").innerHTML = "<input list='cvo_id' id='answerInput' class='form-control input_field sadd' placeholder='Paid To' style='width: 110%;' readonly>"+
													 "<datalist id='cvo_id'>"+
													 "</datalist>"+
													 "<input type='hidden' name='cvo_id' id='answerInputHidden'>";
	} else if (si==1) {
		document.getElementById("cSpan").innerHTML = "<select id='cvo_id' name='cvo_id' class='form-control input_field select_dropdown sadd' >"+vendordatahtml+"</select>";
		enableSelect(spfrz,spfrz.length);		
	} else if (si==2) {
		document.getElementById("cSpan").innerHTML = "<select id='cvo_id' name='cvo_id' class='form-control input_field select_dropdown sadd' >"+custdatahtml+"</select>";
		document.getElementById('mop').selectedIndex=2;
		enableSelect(spfrz,spfrz.length);
		disableSelect(spfrz,spfrz.length);		
	}
	document.getElementById("cvname").style.display="block";
}*/

function saveData(obj) {
	var formobj = document.getElementById('page_data_form');
	var ems = "";

	if (document.getElementById("pdate") != null) {
		var elements = document.getElementsByClassName("bpc");
		if (elements.length == 1) {
			var epdate = formobj.pdate.value.trim();
			var epamt = formobj.pamt.value.trim();
			var cv = formobj.cv.selectedIndex;
//			var cvname = formobj.cvo_id.selectedIndex;
			var cvname = formobj.answerInputHidden.value.trim();
			var ptax = formobj.ptax.value.trim();
			var emop = formobj.mop.selectedIndex;
			var einstrd = formobj.instrd.value.trim();
			var ebid = formobj.bid.selectedIndex;
			var esid = formobj.sid.selectedIndex;
			var enar = formobj.nar.value.trim();	
			var chrg = formobj.charges.value.trim();
			ems = validatePMTEntries(epdate, epamt, cv,cvname,ptax, emop, einstrd, ebid,esid, enar,chrg);

		} else if (elements.length > 1) {
			for (var i = 0; i < elements.length; i++) {
				var epdate = formobj.pdate[i].value.trim();
				var epamt = formobj.pamt[i].value.trim();
				var cv = formobj.cv.selectedIndex;
//				var cvname = formobj.cvo_id.selectedIndex;
				var cvname = formobj.answerInputHidden[i].value.trim();			
				var ptax = formobj.ptax[i].value.trim();
				var emop = formobj.mop[i].selectedIndex;
				var einstrd = formobj.instrd[i].value.trim();
				var ebid = formobj.bid[i].selectedIndex;
				var esid = formobj.sid[i].selectedIndex;
				var enar = formobj.nar[i].value.trim();				
				var chrg = formobj.charges[i].value.trim();
				ems = validatePMTEntries(epdate, epamt, cv,cvname,ptax, emop, einstrd,ebid, esid, enar,chrg);

				if (ems.length > 0)
					break;
			}
		}
	} else {
		document.getElementById("dialog-1").innerHTML = "Please Add Data";
		alertdialogue();
		return;
	}

	if (ems.length > 0) {
		document.getElementById("dialog-1").innerHTML = ems;
		alertdialogue();
		return;
	}

	var epamtround = formobj.pamt.value.trim();
	 epamt=parseFloat(epamtround).toFixed(2);


	if(cv == 3) {
		var pdate = new Date(epdate);
		var pmon = pdate.getMonth()+1;
		var pyr = pdate.getFullYear();
		
		document.getElementById("month").value = pmon;
		document.getElementById("year").value = pyr;

		if(ptax == 1){
			document.getElementById("gsta").value = epamt;
			document.getElementById("ita").value = "0.00";
		}else if(ptax == 2) {
			document.getElementById("gsta").value = "0.00";
			document.getElementById("ita").value = epamt;
		}		
	}else {
		document.getElementById("month").value = "0";
		document.getElementById("year").value = "0";
		
		document.getElementById("gsta").value = "0.00";
		document.getElementById("ita").value = "0.00";
	}
	
	var negbalmsg = checkForNegativeBalances(formobj);
	var name = "BANK";
	if(negbalmsg != false) {
		if(negbalmsg != "")
			name = negbalmsg;

		document.getElementById("dialog-1").innerHTML = "The "+ name +" Account you have selected has no sufficient balance to complete this transaction. Please Check and add again";
		alertdialogue();
		//alert("Your LOAD ACCOUNT have no sufficient balance to complete this transaction. Please Check and add again");
		return;
	}

	var selval = formobj.answerInputHidden.value.trim();
	if(cvo_data.length>0 && selval != "") {
		for(var z=0; z<cvo_data.length; z++){
			if(cvo_data[z].cvo_cat != 2 && cvo_data[z].deleted == 0){
				if(cvo_data[z].id==selval)  {
					formobj.cvo_cat.value = cvo_data[z].cvo_cat;
				}
			}
		}
	}else if(selval == ""){
		formobj.cvo_cat.value = "-1";
	}

	var objId = obj.id;
	document.getElementById(objId).disabled = true;
	formobj.submit();
}

/*function checkForNegativeBalances(formobj) {
	var sbid = formobj.bid.value.trim();
	var pamt = formobj.pamt.value.trim();
	var bankcb = 0;
	var bname = "";
	if(sbid != 1) {
		if(bank_data.length>0) {
			for(var z=0; z<bank_data.length; z++) {
				if(sbid == bank_data[z].id) {
					bankcb = bank_data[z].acc_cb;
					bname = bank_data[z].bank_code+" - "+bank_data[z].bank_acc_no;
					break;
				}
			}
		}
		if(parseFloat(pamt) > parseFloat(bankcb)) {
			return bname;
		}else return false;

	}else if(sbid == 1){
		if(parseFloat(pamt) > parseFloat(cashcb))
			return "CASH";
		else return false;
	}
}
*/

function checkForNegativeBalances(formobj) {
	var sbid = formobj.bid.value.trim();
	var pamt = formobj.pamt.value.trim();
	var charges = formobj.charges.value.trim();
	var amt = +(parseFloat(pamt)) + +(parseFloat(charges));
	var bankcb = 0;
	var bname = "";
	var odbflag = 0;
	if(sbid != 1) {
		if(bank_data.length>0) {
			for(var z=0; z<bank_data.length; z++) {
				if(sbid == bank_data[z].id) {
					bname = bank_data[z].bank_code+" - "+bank_data[z].bank_acc_no;
					
					if(bank_data[z].bank_code != "OVER DRAFT"){
						bankcb = bank_data[z].acc_cb;
						odbflag = 1;
					}else{
						if(bank_data[z].od_and_loan_acceptable_bal == "")
							odbflag = 2;
						else {
							var val = parseFloat(bank_data[z].acc_cb) - parseFloat(amt);
							var odab = bank_data[z].od_and_loan_acceptable_bal;
							if(val < parseFloat(odab))
								odbflag = 3;
						}
					}
					break;
				}
			}
		}
		if(odbflag == 1){
			if(parseFloat(amt) > parseFloat(bankcb)) {
				return bname;
			}else return false;
		}else if(odbflag == 3){
			return bname;
		}else return false;
	}else if(sbid == 1){
		if(parseFloat(pamt) > parseFloat(cashcb))
			return "CASH";
		else return false;
	}
}

function deleteItem(id,pdate) {
	var flag=validateDayEndForDelete(pdate,dedate);
	if(flag) {
		/*if (confirm("Are you sure you want to delete?") == true) {
			var formobj = document.getElementById('page_data_form');
			formobj.actionId.value = "5513";
			formobj.itemId.value = id;
			formobj.submit();
		}*/
		$("#myDialogText").text("Are You Sure You Want To Delete?");
		 var formobj = document.getElementById('page_data_form');
		 confirmDialogue(formobj,5513,id);
	}else {
		document.getElementById("dialog-1").innerHTML = "This record can't be deleted as you have submitted and verified your DAYEND calculations succuessfully... ";
		alertdialogue();
		//alert("This record can't be deleted as you have submitted and verified your DAYEND calculations succuessfully... ");
	}
		
	
}

function validatePMTEntries(pdate, pamt,cv,cvname,ptax, mop, instrd, bid, sid, nar, chrg) {
	var errmsg = "";
	var formobj = document.getElementById('page_data_form');
	var mopv = formobj.mop.value;
	
	var vd=isValidDate(pdate);
	var vfd = ValidateFutureDate(pdate);	
	if (!(pdate.length > 0))
		errmsg = "Please enter VOUCHER DATE. <br>";
	else if (vd != "false")
		errmsg = errmsg + vd +"<br>";
	else if(validateDayEndAdd(pdate,dedate)){
        errmsg = "VOUCHER DATE should be after DayEndDate. <br>";
        return errmsg;
	}else if(validateEffectiveDateForCVO(pdate,effdate)){
        errmsg = "VOUCHER DATE should be after Effective Date that you have entered While First Time Login<br>";
        return errmsg;
	}else if (vfd != "false")
		errmsg = errmsg +"VOUCHER"+vfd+"<br>";
/*	else {
		var lastTrxnDateCheck = checkforLastTrxnDate(dedate,a_created_date,pdate,trdate);
		if(lastTrxnDateCheck != "false") {
			return lastTrxnDateCheck;
		}
	}*/
	
	if (!(pamt > 0))
		errmsg = errmsg + "Please enter AMOUNT. <br>";
	else if (validateDot(pamt))
		errmsg = errmsg + "AMOUNT must contain atleast one number. <br>";
	else if (!validateDecimalNumberMinMax(pamt, 0, 1000000))
		errmsg = errmsg + "AMOUNT Must be Numeric and must be between 0 & 1000000 .<br>";
	else
		formobj.pamt.value = round(parseFloat(pamt), 2);

	var cptoarr = new Array();
	var vptoarr = new Array();
	if (!(cv > 0))
		errmsg = errmsg + "Please select CUSTOMER/VENDOR. <br>";
	else if((cv > 0) && (cv != 3)) {
		if(!(cvname.length > 0))
			errmsg = errmsg + "Please select PAID TO. <br>";
		else {
			for(var z=0; z<cvo_data.length; z++){
				if((cvo_data[z].cvo_cat == 0 || cvo_data[z].cvo_cat == 3) && (cvo_data[z].deleted==0))
					vptoarr.push(cvo_data[z].id);
				else if(cvo_data[z].cvo_cat==1 && cvo_data[z].deleted==0 && cvo_data[z].cvo_name != "UJWALA")
					cptoarr.push(cvo_data[z].id);
			}
			if(isNaN(parseInt(cvname))==false) {
				if((cv==1) && (vptoarr.indexOf(parseInt(cvname)) == -1))
					errmsg = errmsg + "The vendor you have entered in PAID TO is not valid. Please enter valid vendor. <br>";
				else if((cv==2) && (cptoarr.indexOf(parseInt(cvname)) == -1))
					errmsg = errmsg + "The customer you have entered in PAID TO is not valid. Please enter valid customer. <br>";
			}else 
				errmsg = errmsg + "The customer/vendor you have entered in PAID TO is not valid. Please enter valid customer/vendor. <br>";
		}
	}else if(cv == 3){
		if(!(ptax>0))
			errmsg = errmsg + "Please select the type of TAX. <br>";
	}

	if (!(instrd.length > 0))
		errmsg = errmsg + "Please enter INSTRUMENT DETAILS.Enter NA if NOT APPLICABLE. <br>";
	else if(mopv == "2" && (instrd == "NA" || instrd == "na")){
		errmsg = errmsg + "Please enter valid Cheque Number in INSTRUMENT DETAILS. <br> ";
	}else if((mopv == "3") && (instrd == "NA" || instrd == "na"))
		errmsg = errmsg + "INSTRUMENT DETAILS Is Mandatory For Online Transfer. <br> ";
		
	if (!(bid > 0))
		errmsg = errmsg + "Please select DEBITED TO(BANK).<br>";
	else if((mopv == "2" || mopv == "3")&& bid == "1")
		errmsg = errmsg + "Please select valid DEBITED TO(BANK).<br>";
	else if(mop !== 1 && bid == 1)
		errmsg = errmsg + "Please select Valid CREDITED TO(BANK).<br>";

	if (!(mop > 0))
		errmsg = errmsg + "Please select MODE OF PAYMENT. <br>";
	else if((mop == 1) && (bid > 1)){
		errmsg = errmsg + "Please select CASH in DEBITED TO(BANK). <br>";	
//	}else if((mop == 2)&&((cv == 1)||(cv == 2))){
	}else if((mop == 2)&&(cv == 1)){
		if((chrg.length>0)&&(parseFloat(chrg) != 0))
			errmsg = errmsg + "There cannot be any charges for Payment with the type Cheque. <br>";	
	}
	
	if (!(nar.length > 0))
		errmsg = errmsg + "Please enter NARRATION.Enter NA if NOT APPLICABLE. <br>";
	
	if (!(chrg.length>0))
		errmsg = errmsg + "Please enter CHARGES. Enter 0 if not applicable. <br>";
	
	if(mop != 1){
		if (!validateDecimalNumberMinMax(chrg, -1, 1000))
			errmsg = errmsg + "CHARGES Must be Numeric and must be greater than or equal to 0 & less than 1000. <br>";
	}
	return errmsg;
}
