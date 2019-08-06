document.getElementById("tbankSpan").innerHTML = "<select id='bid' name='bid'>"+bankdatahtml+"</select>";
//Construct bank html
tbankdatahtml = "<OPTION VALUE='0'>SELECT</OPTION>";
var starbid = 0;
var tarbid=0;
if(bank_data.length>0) {
	var bc;
	for(var z=0; z<bank_data.length; z++){
		bc = bank_data[z].bank_code;
		if((bc != "LOAN")&&(bank_data[z].deleted == 0)){
			if(bc=="TAR ACCOUNT") {
				bc = "LOAD ACCOUNT";
				tarbid = bank_data[z].id;
			}
			if(bc=="STAR ACCOUNT") {
				bc = "SV/TV ACCOUNT";
				starbid = bank_data[z].id;
			}
			tbankdatahtml=tbankdatahtml+"<OPTION VALUE='"+bank_data[z].id+"'>"+bc+" - "+bank_data[z].bank_acc_no+"</OPTION>";
		}
	}
}

/*function showBTRNXSFormDialog() {
	var check = checkDayEndDateForAdd(dedate,a_created_date,effdate);
	if(check == true) {
		document.getElementById('myModal').style.display = "block";
	}else{
		document.getElementById("dialog-1").innerHTML = "Please Submit Previous DayEnd inorder to add todays data.";
		alertdialogue();
	}
}
*/

function showBTRNXSFormDialog() {
	document.getElementById('myModal').style.display = "block";
	pushMenu();
}


function closeFormDialog() {
	var formobj = document.getElementById('page_data_form');
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

	document.getElementById("page_data_form").reset();
/*	var tbody = document.getElementById('data_table_body');
	var count = document.getElementById('data_table_body').getElementsByTagName('tr').length;
	for(var a=0; a<count; a++){
		if(a>0)
			tbody.deleteRow(-1);
	}
*/	document.getElementById('myModal').style.display = "none";
$("body").removeClass('sidebar-collapse').trigger('expanded.pushMenu');

}


//Set Page Data
var tbody = document.getElementById('page_data_table_body');
for(var f=page_data.length-1; f>=0; f--) {
	var ed = new Date(page_data[f].bt_date);
	var bankName = getBankName(bank_data,page_data[f].bank_id);
	var bid = page_data[f].bank_id;
	
	if(bid==parseInt(tarbid)) {
		bankName = "LOAD ACCOUNT";
	}
	if(bid==parseInt(starbid)) {
		bankName = "SV/TV ACCOUNT";
	}
	var trmode=mops[page_data[f].trans_mode];

/*  var trmode=page_data[f].trans_mode;
	if(trmode === 3) {
		trmode= "ONLINE TRANSFER";
	}else {
		trmode=mops[trmode];
	}
*/
//	var del = "<td><img src='images/delete.png' onclick='deleteItem("+page_data[f].id+","+page_data[f].bt_date+")'></td>";
	var del = "<td align='center'><img src='images/delete.png' onclick='deleteItem("+page_data[f].sr_no+","+page_data[f].bt_date+")'></td>";
	if(page_data[f].deleted==2 || page_data[f].trans_type == 6 || page_data[f].trans_type == 7 
			|| page_data[f].trans_type == 8 || page_data[f].trans_type == 11 || page_data[f].trans_type == 12
			|| page_data[f].trans_type == 13 || page_data[f].trans_type == 14 ) {
		del = "<td>TRANSACTION CANCELED</td>";
	}
	
//	if(page_data[f].btflag != 1 && ((page_data[f].deleted != 2) && (page_data[f].trans_type == 1 || page_data[f].trans_type == 2 || page_data[f].trans_type == 9 || page_data[f].trans_type == 10))){
	if(page_data[f].btflag != 1 && (page_data[f].trans_type == 1 || page_data[f].trans_type == 2 || page_data[f].trans_type == 9 || page_data[f].trans_type == 10)){	
        del = "<td align='center'><img src='images/delete.png'></td>";
	}
	var tblRow = tbody.insertRow(-1);
	tblRow.style.height="20px";
	tblRow.align="left";
	tblRow.innerHTML = "<tr>"+
		"<td>"+ page_data[f].sr_no+"</td>"+
		"<td>"+ ed.getDate()+"-"+months[ed.getMonth()]+"-"+ed.getFullYear() +"</td>"+
		"<td>"+ bankName +"</td>"+
		"<td>"+ round(page_data[f].trans_amount,0) +"</td>"+
		"<td>"+ page_data[f].trans_charges +"</td>"+
		"<td>"+ tts[page_data[f].trans_type] +"</td>"+
		"<td>"+ trmode +"</td>"+
		"<td>"+ page_data[f].instr_details +"</td>"+
		"<td>"+ page_data[f].narration +"</td>"+
		del+
		"</tr>";

};


/*new 23032018*/

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

 /*new end*/
/*function addRow() {
	var trcount = document.getElementById('page_add_table_body').getElementsByTagName('tr').length;
	if(trcount>0){
		var trv=document.getElementById('page_add_table_body').getElementsByTagName('tr')[trcount-1];
		var saddv=trv.getElementsByClassName('sadd');
		var eaddv=trv.getElementsByClassName('eadd');

		var res=checkRowData(saddv,eaddv);
		if(res == false){
			alert("Please enter all the values in current row,calculate and then add next row");
			return;
		}
	}

	var tbody = document.getElementById('page_add_table_body');

	var newRow = tbody.insertRow(-1);
	newRow.style.height = "30px";

	var a = newRow.insertCell(0);
	var b = newRow.insertCell(1);
	var c = newRow.insertCell(2);
	var d = newRow.insertCell(3);
	var e = newRow.insertCell(4);
	var f = newRow.insertCell(5);
	var g = newRow.insertCell(6);
	var h = newRow.insertCell(7);
	var i = newRow.insertCell(8);

	a.innerHTML = "<td valign='top' height='4' align='center'></td>";
	b.innerHTML = "<td valign='top' height='4' align='center'><input type=date name='btdate' id='btdate' class='form-control input_field bpc eadd'></td>";
	c.innerHTML = "<td valign='top' height='4' align='center'><select name='bid' id='bid' class='form-control input_field select_dropdown sadd'>"
			+ bankdatahtml + "</select></td>";
	d.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='btamt' id='btamt' maxlength='9' class='form-control input_field eadd' placeholder='TRANSACTION AMOUNT'></td>";
	e.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='btcharges'  value='0.00' id='btcharges' class='form-control input_field eadd' maxlength='6'  placeholder='BANK CHARGES'/></td>";
	f.innerHTML = "<td valign='top' height='4' align='center'><select name='tt' id='tt' class='form-control input_field select_dropdown sadd'>"
			+ "<option value='0'>SELECT</option>"
			+ "<option value='1'>PAYMENT</option>"
			+ "<option value='2'>RECEIPT</option>"
			+ "<option value='3'>CASH /CHQ DEPOSIT</option>"
			+ "<option value='4'>CASH WITHDRAWL</option>"
			+ "<option value='5'>TRANSFER</option>" + "</select></td>";
	g.innerHTML = "<td valign='top' height='4' align='center'><select name='mop' id='mop' class='form-control input_field select_dropdown sadd'>"
			+ "<option value='0'>SELECT</option>"
			+ "<option value='1'>CASH</option>"
			+ "<option value='2'>CHEQUE</option>"
			+ "<option value='3'>ONLINE TRANSFER</option>" + "</select></td>";
	h.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='instrd' id='instrd' class='form-control input_field eadd' value='NA' maxlength='30'></td>";
	i.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='nar' id='nar' size='8' class='form-control input_field eadd' maxlength='200'></td>";

}*/

function saveData(obj) {
	var formobj = document.getElementById('page_data_form');
	var ems = "";

	if (document.getElementById("btdate") != null) {
		var elements = document.getElementsByClassName("bpc");
		if (elements.length == 1) {
			var ebtdate = formobj.btdate.value;
			var ebid = formobj.bid.selectedIndex;
			var ebtamt = formobj.btamt.value.trim();
			var ebtcharges = formobj.btcharges.value.trim();
			var ett = formobj.tt.selectedIndex;
			var emop = formobj.mop.selectedIndex;
			var einstrd = formobj.instrd.value.trim();
			var enar = formobj.nar.value.trim();
			ems = validateBTEntries(ebtdate, ebid, ebtamt, ebtcharges, ett, emop, einstrd,enar);
		} /*else if (elements.length > 1) {
			for (var i = 0; i < elements.length; i++) {
				var ebtdate = formobj.btdate[i].value;
				var ebid = formobj.bid[i].selectedIndex;
				var ebtamt = formobj.btamt[i].value.trim();
				var ebtcharges = formobj.btcharges[i].value.trim();
				var ett = formobj.tt[i].selectedIndex;
				var emop = formobj.mop[i].selectedIndex;
				var einstrd = formobj.instrd[i].value.trim();
				ems = validateBTEntries(ebtdate, ebid, ebtamt, ebtcharges, ett, emop, einstrd);

				if (ems.length > 0)
					break;
			}
		}*/
	} else {
		document.getElementById("dialog-1").innerHTML = "PLEASE ADD DATA";
		alertdialogue();
		return;
	}

	if (ems.length > 0) {
		document.getElementById("dialog-1").innerHTML = ems;
		alertdialogue();
		return;
	}

	if(formobj.tt.selectedIndex != 2) {
		var negbalmsg = checkForNegativeBalances(formobj);
		if(negbalmsg != true) {
			document.getElementById("dialog-1").innerHTML = negbalmsg;
			alertdialogue();
			return;
		}
	}

	var objId = obj.id;
	document.getElementById(objId).disabled = true;
	
	formobj.submit();
}


function checkForNegativeBalances(formobj) {
	var btamt = formobj.btamt.value.trim();
	var charges = formobj.btcharges.value.trim();
	var tamt = +(parseFloat(btamt)) + +(parseFloat(charges));
	if(formobj.tt.selectedIndex == 3) {
		if(parseFloat(btamt) > parseFloat(cashcb)) {
			return "Your CASH ACCOUNT have no sufficient balance to complete this transaction. Please Check and add again";
		}else return true;
	}else {
		var bankactcb = 0;
		var bankid = formobj.bid.value.trim();
		var bname = "BANK ACCOUNT";
		var odbflag = 0;
		if(bank_data.length>0) {
			for(var y=0; y<bank_data.length; y++) {
				if((bank_data[y].id == bankid) && (bank_data[y].deleted == 0)) {
					bname = bank_data[y].bank_code+" - "+bank_data[y].bank_acc_no;
					
					if(bank_data[y].bank_code != "OVER DRAFT"){
						bankactcb = bank_data[y].acc_cb;
						odbflag = 1;
					}else{
						if(bank_data[y].od_and_loan_acceptable_bal == "")
							odbflag = 2;
						else {
							var val = parseFloat(bank_data[y].acc_cb) - parseFloat(tamt);
							var odab = bank_data[y].od_and_loan_acceptable_bal;
							if(val < parseFloat(odab))
								odbflag = 3;
						}
					}
					break;					
		    	}
			}
		}
		
		if(odbflag == 1){
			if(parseFloat(tamt) > parseFloat(bankactcb)) {
				return "Your selected "+bname+" have no sufficient balance to complete Online Transaction. Please Check and add again";
			}else return true;
		}else if(odbflag == 3){
			return "Your selected "+bname+" have no sufficient balance to complete Online Transaction. Please Check and add again";
		}else return true;
	}
}


/*function deleteItem(id,btdate) {
	var flag=validateDayEndForDelete(btdate,dedate);
	if(flag) {
		if (confirm("Are you sure you want to delete?") == true) {
			var formobj = document.getElementById('page_data_form');
			formobj.actionId.value = "5523";
			formobj.itemId.value = id;
			formobj.submit();
		}
	}else{
		document.getElementById("dialog-1").innerHTML = "This record can't be deleted as you have submitted and verified your DAYEND calculations succuessfully...";
		alertdialogue();
		//alert("This record can't be deleted as you have submitted and verified your DAYEND calculations succuessfully... ");
	}
}*/

function deleteItem(id,btdate){
	var flag=validateDayEndForDelete(btdate,dedate);
	if(flag){
		$("#myDialogText").text("Are You Sure You Want To Delete?");
		var formobj = document.getElementById('page_data_form');
		confirmDialogue(formobj,5523,id);
	}else {
		document.getElementById("dialog-1").innerHTML = "This record can't be deleted as you have submitted and verified your DAYEND calculations succuessfully... ";
		alertdialogue();
	}
}

function calculateVATPrice(ebp, spc) {
	var cv = 0;
	for (var z = 0; z < arb_data.length; z++) {
		if (arb_data[z].prod_code == spc) {
			var vatv = arb_data[z].vatp;
			cv = ((ebp * vatv) / 100);
		}
	}
	return cv;
}


function showToBankAccount(){
	var formobj = document.getElementById('page_data_form');
	var bidi= formobj.bid.selectedIndex;
	var si = formobj.tt.selectedIndex;
	var mopi = formobj.mop.selectedIndex;
	var spfrz= document.getElementById("bid").options;

	if(si==2){
		document.getElementById("fbankSpan").innerHTML = "<select id='bid' name='bid' class='form-control input_field select_dropdown sadd'>"+bankdatahtml+"</select>";
	}else 
		document.getElementById("fbankSpan").innerHTML = "<select id='bid' name='bid' class='form-control input_field select_dropdown sadd'>"+nlbankdatahtml+"</select>";
	
	if(si==5) {
		document.getElementById('transferdropdown').style="block";
		document.getElementById("tbankSpan").innerHTML = "<b>TO BANK ACCOUNT</b><br><select id='tbid' name='tbid' class='form-control input_field select_dropdown sadd' style='margin-top:5px;'>"+tbankdatahtml+"</select>";
	} else {
		document.getElementById("tbankSpan").innerHTML = "";
	}
	/*if(!(mopi>0)){
		alert("please select Mode Of Transaction");
		document.getElementById("bid").selectedIndex=0;
		return;
		}*/
	}


function validateBTEntries(btdate, bid, btamt, btcharges, tt, mop, instrd,nar) {
	var errmsg = "";
	var formobj = document.getElementById('page_data_form');
	var vd = isValidDate(btdate);
	var vfd = ValidateFutureDate(btdate);

	if (!(btdate.length > 0))
		errmsg = "Please enter Date. <br>";
	else if (vd != "false")
		errmsg = errmsg +vd+"<br>";
	else if(validateDayEndAdd(btdate,dedate)){
        errmsg = "TRANSACTION DATE should be after DayEndDate. <br>";
        return errmsg;
	}else if (vfd != "false")
		errmsg = errmsg +"TRANSACTION"+vfd+"<br>";
/*	else {
		var lastTrxnDateCheck = checkforLastTrxnDate(dedate,a_created_date,btdate,trdate);
		if(lastTrxnDateCheck != "false") {
			return lastTrxnDateCheck;
		}
	}*/
	
	if (!(tt > 0)){
		errmsg = errmsg + "Please select TRANSACTION TYPE. <br>";
	}else if(tt!=3 && tt!=4) {
		if (!(mop > 0))
			errmsg = errmsg + "Please select MODE OF TRANSACTION. <br>";
		else if( mop == 1 && tt == 5)
			errmsg = errmsg + "Please Select valid MODE OF TRANSACTION For the selected TRANSACTION TYPE. <br> ";
		else if( (mop == 2) && ((tt == 3) ||(tt == 4) ||(tt == 6)) )
			errmsg = errmsg + "Please Select valid MODE OF TRANSACTION For the selected TRANSACTION TYPE. <br> ";
	}else if((tt == 3 || tt == 4) && (mop > 0)){
		errmsg = errmsg + "MODE OF TRANSACTION cannot be cheque or online transfer for CASH type transactions. <br>";
	}

	if (!(btamt.length > 0))
		errmsg = errmsg + "Please enter TRANSACTION AMOUNT. <br>";
	else if (validateDot(btamt))
		errmsg = errmsg + "TRANSACTION AMOUNT Must contain atleast one number. <br>";
	else if(tt == 5) {
		if(document.getElementById("tbid").selectedIndex ==1) {
			if (!validateDecimalNumberMinMax(btamt, 0, 10000000))
				errmsg = errmsg	+ "AMOUNT Must be Numeric and must be between 0 & 10000000. <br>";
		}else if(document.getElementById("tbid").selectedIndex !=1){
			if (!validateDecimalNumberMinMax(btamt, 0, 1000000))
				errmsg = errmsg	+ "AMOUNT Must be Numeric and must be between 0 & 1000000. <br>";
		}		
	}else if (!validateDecimalNumberMinMax(btamt, 0, 1000000))
		errmsg = errmsg	+ "AMOUNT Must be Numeric and must be between 0 & 1000000. <br>"; 			
	else
		formobj.btamt.value = round(parseFloat(btamt), 2);
	

	if (validateDot(btcharges))
		errmsg = errmsg + "BANK CHARGES Must contain atleast one number. <br>";
	else if (!validateDecimalNumberMinMax(btcharges, -1, 1000))
		errmsg = errmsg + "BANK CHARGES Must be Numeric and must be between 0 & 1000. <br>";
	else
		formobj.btcharges.value = round(btcharges, 2);

	if (tt > 0) {
		if((tt == 5) && ((formobj.tbid.selectedIndex) == 0)){
			errmsg = errmsg + "Please select TO BANK For The Choosen TRANSACTION TYPE. <br>";
		}
		if((tt==1) && (mop == 1) && (parseFloat(btcharges) != 0)){
			errmsg = errmsg + "There cannot be any charges for the Transaction Type PAYMENT with Cheque. <br>";
		}
		if((tt==2) && (parseFloat(btcharges) != 0)){
			errmsg = errmsg + "There cannot be any charges for the Transaction Type RECEIPT. <br>";
		}
	}
	
	if (!(bid > 0))
		errmsg = errmsg + "Please select BANK ACCOUNT. <br>";

	if (!(instrd.length > 0))
		errmsg = errmsg + "Please enter INSTRUMENT DETAILS.Enter NA if Not Applicable. <br>";
	else if(  (mop == 1)&&(instrd == "NA" || instrd == "na"))
		errmsg = errmsg + " Please Enter Valid Cheque Number in INSTRUMENT DETAILS/TXR NO. <br>";

	if (!(nar.length > 0))
		errmsg = errmsg + "Please enter NARATION or NA if not applicable. <br>";

	return errmsg;
}