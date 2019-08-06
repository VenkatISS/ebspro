
/*function showRCPTSFormDialog() {
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

function showRCPTSFormDialog() {
	document.getElementById('myModal').style.display = "block";
	pushMenu();
}


function closeRCPTSFormDialog() {
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

//Set Page Data
var tbody = document.getElementById('page_data_table_body');
for(var f=page_data.length-1; f>=0; f--){
	var ed = new Date(page_data[f].rcp_date);
	var custName = getCustomerName(cvo_data,page_data[f].rcvd_from);
	var staffName = getStaffName(staff_data,page_data[f].staff_id);
	var bankName;
	var del = "<td align='center'><img src='images/delete.png' onclick='deleteItem("+page_data[f].id+","+page_data[f].rcp_date+")'></td>";
	if(page_data[f].deleted==2){
		del = "<td>TRANSACTION CANCELED</td>";
	}
	if(!staffName){		
		staffName = "NA";
	}
	if(page_data[f].credited_bank == 1){
			bankName = "CASH";
	}else if(page_data[f].credited_bank == 0){
			bankName = "NA";
	}else if(getBankName(bank_data,page_data[f].credited_bank) == ("TAR ACCOUNT-"+page_data[f].created_by)){
		bankName = "LOAD ACCOUNT";
	}else{
		bankName = getBankName(bank_data,page_data[f].credited_bank);
	}
	var tblRow = tbody.insertRow(-1);
	if(page_data[f].deleted==2){
		invno = "<td>"+page_data[f].sr_no+"</td>";
	}else {
		invno = "<td><a href='javascript:showInvoice("+page_data[f].id+","+page_data[f].rcp_date+")'>"+page_data[f].sr_no+"</a></td>";
	}
	
	tblRow.style.height="20px";
	tblRow.align="left";
	tblRow.innerHTML = invno+
			//"<td>"+ page_data[f].sr_no+"</td>"+
			"<td>"+ ed.getDate()+"-"+months[ed.getMonth()]+"-"+ed.getFullYear() +"</td>"+
   	 		"<td>"+ page_data[f].rcp_amount +"</td>"+
   	 		"<td>"+ custName +"</td>"+
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
	if(trcount>0) {
		var trv=document.getElementById('page_add_table_body').getElementsByTagName('tr')[trcount-1];
		var saddv=trv.getElementsByClassName('sadd');
		var eaddv=trv.getElementsByClassName('eadd');
		
		var res=checkRowData(saddv,eaddv);
		if(res == false){
			alert("Please enter all the values in current row and  then add next row");
			return;
		}		
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
	b.innerHTML = "<td valign='top' height='4' align='center'><input type=date name='rdate' id='rdate' class='form-control input_field bpc eadd'></td>";
	c.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='ramt' id='ramt' class='form-control input_field eadd' maxlength='9' placeholder='AMOUNT'></td>";
	d.innerHTML = "<td valign='top' height='4' align='center'><select name='rfrom' id='rfrom' class='form-control input_field select_dropdown sadd'>"
		+ custdatahtml + "</select></td>";
	e.innerHTML = "<td valign='top' height='4' align='center'><select name='mop' id='mop' onchange='changeBank()' class='form-control input_field select_dropdown sadd'>"
		+ "<option value='0'>SELECT</option>"
		+ "<option value='1'>CASH</option>"
		+ "<option value='2'>CHEQUE</option>"
		+ "<option value='3'>ONLINE TRANSFER</option>" + "</select></td>";
	f.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='instrd' id='instrd' class='form-control input_field eadd' value='NA' maxlength='30' placeholder='INSTRUMENT DETAILS'></td>";
	g.innerHTML = "<td valign='top' height='4' align='center'><select name='bid' id='bid' class='form-control input_field select_dropdown sadd'>"
		+ bankdatahtml + "</select></td>";
	h.innerHTML = "<td valign='top' height='4' align='center'><select name='sid' id='sid'class='form-control input_field select_dropdown sadd' >"
		+ staffdatahtml + "</select></td>";
	i.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='nar' id='nar' class='form-control input_field eadd' maxlength='200' placeholder='NARRATION'></td>";
	j.innerHTML = "<td valign='top' height='4' align='center'><img src='images/delete.png' onclick='doRowDelete(this)'></td>";
}

*/

function showInvoice(inv_id,rcp_date){
	popitup(inv_id,rcp_date);
}
function popitup(inv_id,rcp_date) { 
	
//	window.open("PopupControlServlet?actionId=999&sitype=1&sid="+inv_id,null,"height=400,width=1024,status=yes,toolbar=no,menubar=no,location=no");
	
	//newwindow=window.open("http://localhost:8080/ERPAPP/ControlServlet");
	//window.open('page2.jsp','popuppage','width=850,toolbar=1,resizable=1,scrollbars=yes,height=700,top=100,left=100');
	var w=window.open("PopupControlServlet?actionId=986&sitype=14&sid="+inv_id +"&si_date="+rcp_date,null,"height=400,width=1024,status=yes,toolbar=no,menubar=no,location=no");
	/*if (window.focus) {
		newwindow.focus();
	}  */ 	
    w.print();
	return false; 
}
function changeBank() {
	var formobj = document.getElementById('page_data_form');
	var emop = formobj.mop.selectedIndex;
	var spfrz= document.getElementById("bid").options;
	if(emop == 1) {
		formobj.bid.selectedIndex=1;
	}else {
		formobj.bid.selectedIndex=0;
	}
	/*if(emop == 2) {
		formobj.bid.selectedIndex=0;
		disableSelect(spfrz,spfrz.length);
	}else {
		formobj.bid.selectedIndex="";
		enableSelect(spfrz,spfrz.length);
	}*/
}

function saveData(obj) {
	var formobj = document.getElementById('page_data_form');
	var ems = "";
	var eramt1;
	if (document.getElementById("rdate") != null) {
		var elements = document.getElementsByClassName("bpc");
		if (elements.length == 1) {
			var erdate = formobj.rdate.value.trim();
			var eramt = formobj.ramt.value.trim();
//			var erfrom = formobj.rfrom.selectedIndex;
			var erfrom = formobj.answerInputHidden.value.trim();
			var emop = formobj.mop.selectedIndex;
			var einstrd = formobj.instrd.value.trim();
			var ebid = formobj.bid.selectedIndex;
			var esid = formobj.sid.selectedIndex;
			var enar = formobj.nar.value.trim();
			ems = validateRCPTEntries(erdate, eramt, erfrom, emop, einstrd,ebid, esid, enar);
			
		} else if (elements.length > 1) {
			for (var i = 0; i < elements.length; i++) {
				var erdate = formobj.rdate[i].value.trim();
				var eramt = formobj.ramt[i].value.trim();
//				var erfrom = formobj.rfrom[i].selectedIndex;
				var erfrom = formobj.answerInputHidden[i].value.trim();
				var emop = formobj.mop[i].selectedIndex;
				var einstrd = formobj.instrd[i].value.trim();
				var ebid = formobj.bid[i].selectedIndex;
				var esid = formobj.sid[i].selectedIndex;
				var enar = formobj.nar[i].value.trim();
				ems = validateRCPTEntries(erdate, eramt, erfrom, emop, einstrd,ebid, esid, enar);

				if (ems.length > 0)
					break;
			}
		}
	} else {
		document.getElementById("dialog-1").innerHTML = "Please Add Data";
		alertdialogue();
		//alert("Please Add Data");
		return;
	}

	if (ems.length > 0) {
		document.getElementById("dialog-1").innerHTML = ems;
		alertdialogue();
		//alert(ems);
		return;
	}
	
	var objId = obj.id;
	document.getElementById(objId).disabled = true;
	formobj.submit();
}

/*function deleteItem(id,rdate) {
	var flag=validateDayEndForDelete(rdate,dedate);
	if(flag) {
		if (confirm("Are you sure you want to delete?") == true) {
			var formobj = document.getElementById('page_data_form');
			formobj.actionId.value = "5503";
			formobj.itemId.value = id;
			formobj.submit();
		}
	}else {
		document.getElementById("dialog-1").innerHTML = "This record can't be deleted as you have submitted and verified your DAYEND calculations succuessfully... ";
		alertdialogue();
		//alert("This record can't be deleted as you have submitted and verified your DAYEND calculations succuessfully... ");
	}
}*/

function deleteItem(id,rdate){
	var flag=validateDayEndForDelete(rdate,dedate);
	if(flag){
	 $("#myDialogText").text("Are You Sure You Want To Delete?");
	 var formobj = document.getElementById('page_data_form');
	 confirmDialogue(formobj,5503,id);
	}else {
		document.getElementById("dialog-1").innerHTML = "This record can't be deleted as you have submitted and verified your DAYEND calculations succuessfully... ";
		alertdialogue();
	}
}

function validateRCPTEntries(rdate, ramt, rfrom, mop, instrd, bid, sid, nar) {
	var errmsg = "";
	var formobj = document.getElementById('page_data_form');
	var mopv = formobj.mop.value;
	
	var vd=isValidDate(rdate);
	var vfd = ValidateFutureDate(rdate);	
	if (!(rdate.length > 0))
		errmsg = "Please enter VOUCHER DATE<br>";
	else if (vd != "false")
		errmsg = errmsg + vd +"<br>";
	else if(validateDayEndAdd(rdate,dedate)){
        errmsg = "VOUCHER DATE should be after DayEndDate<br>";
        return errmsg;
	}else if(validateEffectiveDateForCVO(rdate,effdate)){
        errmsg = "VOUCHER DATE should be after Effective Date that you have entered While First Time Login<br>";
        return errmsg;
	}else if (vfd != "false")
		errmsg = errmsg + "VOUCHER" +vfd+"<br>";
/*	else {
		var lastTrxnDateCheck = checkforLastTrxnDate(dedate,a_created_date,rdate,trdate);
		if(lastTrxnDateCheck != "false") {
			return lastTrxnDateCheck;
		}
	}*/
	
	if (!(ramt > 0))
		errmsg = errmsg + "Please enter AMOUNT<br>";
	else if (validateDot(ramt))
		errmsg = errmsg + "AMOUNT must contain atleast one number<br>";
	else if (!validateDecimalNumberMinMax(ramt, 0, 1000000))
		errmsg = errmsg	+ "AMOUNT Must be Numeric and must be between 0 & 1000000<br>";
	else
		formobj.ramt.value = round(parseFloat(ramt), 2);

	var rfromarr = new Array();
	var bflag = 0; 
	if (!(rfrom.length > 0))
		errmsg = errmsg + "Please enter RECEIVED FROM <br>";
	else {
		for(var z=0; z<cvo_data.length; z++){
			if(cvo_data[z].cvo_cat==1)
				rfromarr.push(cvo_data[z].id);
			if((cvo_data[z].id == parseInt(rfrom)) && (cvo_data[z].cvo_name == "UJWALA")){
				if(document.getElementById("mop").selectedIndex != 3){
					errmsg = errmsg + "Please select MODE OF RECEIPT as ONLINE TRANSFER for UJWALA.  <br>";
				}
				if(document.getElementById("bid").selectedIndex != 2){
					errmsg = errmsg + "Please select LOAD ACCOUNT for UJWALA.  <br>";
					bflag = 1;
				}
			}else if((cvo_data[z].id == parseInt(rfrom)) && (cvo_data[z].cvo_name != "UJWALA")){
				if(document.getElementById("bid").selectedIndex == 2){
					errmsg = errmsg + "The bank account you have selected is not valid for the selected customer.  <br>";
					bflag = 1;
				}
			}
				
		}
		
		if(rfromarr.indexOf(parseInt(rfrom)) == -1)
			errmsg = errmsg + "The customer you have entered in RECEIVED FROM is not valid. Please enter valid customer <br>";
	} 

	if (!(instrd.length > 0))
		errmsg = errmsg + "Please enter INSTRUMENT DETAILS.Enter NA if NOT APPLICABLE<br>";
	else if(mopv == "2" && (instrd == "NA" || instrd == "na"))
		errmsg = errmsg + " Please Enter Valid Cheque Number in INSTRUMENT DETAILS/TXR NO <br>";
	else if((mopv == "3")&& (instrd == "NA" || instrd == "na"))
		errmsg = errmsg + "INSTRUMENT DETAILS Is Mandatory For Online Transfer <br>";

	if (!(mop > 0))
		errmsg = errmsg + "Please select MODE OF RECEIPT <br>";
	
	/*if(mop ==2 && bid != 0)
		errmsg = errmsg + "For CHEQUE You Cannot Select CREDITED(BANK) <br>";
	if(!(bid > 0)&& mop !=2) 
		errmsg = errmsg + "Please select CREDITED(BANK) <br>";
	else if((mop == 3) && bid == 1)
			errmsg = errmsg + "Please select Valid CREDITED TO(BANK)<br>";
	*/if(!(bid > 0)&& mop !=0)
		errmsg = errmsg + "Please select CREDITED(BANK) <br>";
	else if((bflag == 0)&&((mop == 3 || mop == 2) && bid == 1))
			errmsg = errmsg + "Please select Valid CREDITED TO(BANK)<br>";
	//else if()

	else if((bflag==0)&&(mop !== 1 && bid == 1))
		errmsg = errmsg + "Please select Valid CREDITED TO(BANK)<br>";
	else if((bflag==0)&&((mop == 1) && (bid > 1)))
		errmsg = errmsg + "Please select CASH in CREDITED TO(BANK) <br>";
	
	if (!(nar.length > 0))
		errmsg = errmsg + "Please enter NARATION or NA if not applicable <br>";
	
	return errmsg;
}
