function showTVFormDialog() {
	document.getElementById('myModal').style.display = "block";
	pushMenu();
}

function closeTVFormDialog() {
	
	var efrz = document.getElementsByClassName("freez");
	remEleReadOnly(efrz,efrz.length);

	var spfrz= document.getElementById("epid").options;
	enableSelect(spfrz,spfrz.length);

	document.getElementById("data_form").reset();
	document.getElementById('myModal').style.display = "none";
    $("body").removeClass('sidebar-collapse').trigger('expanded.pushMenu');

}


/*new 23032018*/

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
//Process page data
var rowpdhtml = "";
if(page_data.length>0) {
	for(var z=page_data.length-1; z>=0; z--){
		var ed = new Date(page_data[z].tv_date);
		var spd = fetchProductDetails(cat_cyl_types_data, page_data[z].prod_code);
		var staffName = getStaffName(staff_data,page_data[z].staff_id);
		var del = "<td align='center'><img src='images/delete.png' onclick='deleteItem("+page_data[z].id+","+page_data[z].tv_date+")'></td>";
		if(page_data[z].deleted==2){
			del = "<td>TRANSACTION CANCELED</td>";
		}

		
		if(page_data[z].deleted==2){
			rowpdhtml = rowpdhtml + "<tr><td>"+page_data[z].sr_no+"</td>";
		}else {
			rowpdhtml = rowpdhtml + "<tr><td><a href='javascript:showInvoice("+page_data[z].id+","+page_data[z].tv_date+")'>"+page_data[z].sr_no+"</a></td>";
		}
		rowpdhtml = rowpdhtml + "<td>"+ed.getDate()+"-"+months[ed.getMonth()]+"-"+ed.getFullYear()+"</td>";
		rowpdhtml = rowpdhtml + "<td>"+staffName+"</td>";
		rowpdhtml = rowpdhtml + "<td>"+page_data[z].customer_no+"</td>";
		rowpdhtml = rowpdhtml + "<td>"+tvcat[page_data[z].tv_cat]+"</td>";
		rowpdhtml = rowpdhtml + "<td>"+spd+"</td>";
		rowpdhtml = rowpdhtml + "<td>"+page_data[z].no_of_cyl+"</td>";
		rowpdhtml = rowpdhtml + "<td>"+page_data[z].no_of_reg+"</td>";
		rowpdhtml = rowpdhtml + "<td>"+page_data[z].cyl_deposit+"</td>";
		rowpdhtml = rowpdhtml + "<td>"+page_data[z].reg_deposit+"</td>";
		rowpdhtml = rowpdhtml + "<td>"+page_data[z].imp_amount+"</td>";
		rowpdhtml = rowpdhtml + "<td>"+page_data[z].admin_charges+"</td>";
		rowpdhtml = rowpdhtml + "<td>"+page_data[z].payment_terms+"</td>";
		rowpdhtml = rowpdhtml + "<td>"+page_data[z].sgst_amount+"</td>";
		rowpdhtml = rowpdhtml + "<td>"+page_data[z].cgst_amount+"</td>";
		rowpdhtml = rowpdhtml + "<td>"+page_data[z].tv_amount+"</td>";
		rowpdhtml = rowpdhtml + del;
		rowpdhtml = rowpdhtml + "</tr>";
	}
}
document.getElementById('m_data_table_body').innerHTML = rowpdhtml;

function saveData(obj){
	var formobj = document.getElementById('data_form');	
	var ems = "";
	//.............................
	if(document.getElementById("custn") != null){
		//if(!(isEmpty(document.getElementById("rc_date")))){		
		var elements = document.getElementById('epid');
		
		if(elements.length>0) {
			var etvDate = formobj.tv_date.value;
			var estaffName = formobj.staff_id.selectedIndex;
			var ecusNumb = formobj.custn.value;
			var etvcat = formobj.tvcategory.selectedIndex;			
			
			var eproduct = formobj.epid.selectedIndex;
			var eNoCyl = formobj.nocyl.value;
			var eNoReg = formobj.noreg.value;
			var eCylDep = formobj.cyld.value;
			var erd = formobj.regd.value;
			var eiamt = formobj.iamt.value;
			var eac = formobj.ac.value;
			var egsta = formobj.gsta.value;
			var ept = formobj.pt.value;
			
			ems = validateTVEntries(etvDate,estaffName,ecusNumb,etvcat,eproduct,eNoCyl,eNoReg,eCylDep,erd,eiamt,eac,egsta,ept);
		}
		
	}
	if(ems.length>0) {
		document.getElementById("dialog-1").innerHTML = ems;
		alertdialogue();
		//alert(ems);
		return;
	}
	
	var negbalmsg = checkForNegativeBalances(formobj);
	if(negbalmsg == false) {
		document.getElementById("dialog-1").innerHTML = "Your CASH ACCOUNT have no sufficient balance to complete this transaction. Please Check and add again";
		alertdialogue();
		return;
	}
		
	var objId = obj.id;
	document.getElementById(objId).disabled = true;
	formobj.submit();
}

function checkForNegativeBalances(formobj) {
	var invamt = formobj.invamt.value.trim();
	if(parseFloat(invamt) > parseFloat(cashcb)) {
		return false;
	}else return true;
}


function validateTVEntries(tvDate,staffName,cusNumb,tvcat,product,NoCyl,NoReg,CylDep,rd,iamt,ac,gsta,pt){
	var errmsg = "";
	var formobj = document.getElementById('data_form');
	
	var vd = isValidDate(tvDate);
	var vfd = ValidateFutureDate(tvDate);	
	if (!(tvDate.length > 0))
		errmsg = errmsg + "Please Enter INVOICE DATE<br>";
	else if (vd != "false")
		errmsg = errmsg +vd+"<br>";
	else if(validateDayEndAdd(tvDate,dedate)){
        errmsg = "INVOICE DATE should be after DayEndDate<br>";
        return errmsg;
	}else if (vfd != "false")
		errmsg = errmsg +vfd+"<br>";
/*	else {
		var lastTrxnDateCheck = checkforLastTrxnDate(dedate,a_created_date,tvDate,trdate);
		if(lastTrxnDateCheck != "false"){
			return lastTrxnDateCheck;
		}
	}*/
	
	if(!(staffName>0))
		errmsg = errmsg + "Please Select STAFF<br>";
	
	if(!(cusNumb.length>0))
		errmsg = errmsg + "Please Enter  CUST NO./NAME<br>";
	else if(!validateAlphaNumeric(cusNumb))
			errmsg = errmsg + "Please Enter valid CUST NO./NAME<br>";
	else if((cusNumb.length<5 ||cusNumb.length>15))
		errmsg = errmsg + "Please Enter valid CUST NO./NAME<br>";

	
	if(!(tvcat>0))
		errmsg = errmsg + "Please Select CATEGORY<br>";
	
	if(!(product>0))
		errmsg = errmsg + "Please Select PRODUCT<br>";
	
	if(!(NoCyl.length>0))
		errmsg = errmsg + "Please Enter NO. OF CYL<br>";
	else if (!(checkNumber(NoCyl)))
		errmsg = errmsg + "Please enter valid NO. OF CYL<br>";
	else if(!(NoCyl==1 || NoCyl==2))
		errmsg = errmsg+ "NO. OF CYL must be 1 or 2 <br>";

	if(!(NoReg.length>0))
		errmsg = errmsg + "Please Enter NO. OF REGULATORS.Enter 0 if not available. <br>";
	else if (!(checkNumber(NoReg)))
		errmsg = errmsg + "Please enter valid NO. OF REGULATORS<br>";
	else if(!(NoReg==0 || NoReg==1))
		errmsg = errmsg+ "NO. OF REGULATORS must be 0 or 1 <br>";
		
	
	if (!(CylDep.length > 0))
		errmsg = errmsg + "Please enter CYLINDER DEPOSIT and then Calculate <br>";
	else if (validateDot(CylDep))
		errmsg = errmsg + "CYLINDER DEPOSIT must contain atleast one number <br>";
	else if (!(isDecimalNumber(CylDep)))
		errmsg = errmsg + "CYLINDER DEPOSIT must contain only numerics<br>";
	else if (!validateDecimalNumberMinMax(CylDep,0,1000000))
		errmsg = errmsg+ "CYLINDER DEPOSIT must be less than 10,00,000 and greater than 0<br>";
	else
		formobj.cyld.value = round(parseFloat(CylDep), 2);	
	
	if (!(rd.length > 0))
		errmsg = errmsg + "Please enter REGULATOR DEPOSIT.Enter 0 if not available.<br>";
	else if (validateDot(rd))
		errmsg = errmsg + "REGULATOR DEPOSIT must contain atleast one number<br>";
	else if (!(isDecimalNumber(rd)))
		errmsg = errmsg + "REGULATOR DEPOSIT must contain only numerics<br>";
	else if (!validateDecimalNumberMinMax(rd,-1,10000))
		errmsg = errmsg+ "REGULATOR DEPOSIT must be less than 10,000 and greater than 0<br>";
	else if ((NoReg.length>0)  && (NoReg == 0) && (!(rd == 0)))
		errmsg = errmsg+ "REGULATOR DEPOSIT must be 0 for Zero NO OF REGULATORS.<br>";
	else
		formobj.regd.value = round(parseFloat(rd), 2);
	
	
	if (validateDot(iamt))
		errmsg = errmsg + "IMPREST AMOUNT must contain atleast one number<br>";
	else if (!(isDecimalNumber(iamt)))
		errmsg = errmsg + "IMPREST AMOUNT must contain only numerics<br>";
	else if (!validateDecimalNumberMinMax(iamt,-1,10000))
		errmsg = errmsg+ "IMPREST AMOUNT must be less than 10 thousand and greater than 0<br>";
	else
		formobj.iamt.value = round(parseFloat(iamt), 2);

	if(!(ac.length>0))
		errmsg = errmsg + "Please Enter ADMIN CHARGES<br>";
	
	if(!(gsta.length>0))
		errmsg = errmsg + "Please Enter GST AMOUNT<br>";
	else if (!(isDecimalNumber(gsta)))
		errmsg = errmsg + "Please enter valid GST AMOUNT<br>";
	
	if(!(pt.length>0))
		errmsg = errmsg + "Please Enter PAYMENT TERMS<br>";
	else if(!checkAlphabets(pt))
		errmsg = errmsg + "PAYMENT TERMS must contain only alphabets<br>";
	
	return errmsg;
}


/*function deleteItem(iid,tvdate) {
	var flag=validateDayEndForDelete(tvdate,dedate);
	if(flag) {
		if (confirm("Are you sure you want to delete?") == true) {
			var formobj = document.getElementById('m_data_form');
			formobj.actionId.value = "5703";
			formobj.dataId.value = iid;
			formobj.submit();
		}
	}else 
		alert("This record can't be deleted as you have submitted and verified your DAYEND calculations succuessfully... ");
	
}*/

function deleteItem(iid,tvdate){
	var flag=validateDayEndForDelete(tvdate,dedate);
	if(flag){
	 $("#myDialogText").text("Are You Sure You Want To Delete?");
	 var formobj = document.getElementById('m_data_form');
	 confirmDialogue(formobj,5703,iid);
	}else {
		document.getElementById("dialog-1").innerHTML = "This record can't be deleted as you have submitted and verified your DAYEND calculations succuessfully ";
		alertdialogue();
	}
}

function calculateValues(){
	
	var formobj = document.getElementById('data_form');

	var eproduct = formobj.epid.selectedIndex;
	var eNoCyl = formobj.nocyl.value;
	var eNoReg = formobj.noreg.value;

	var cd = formobj.cyld.value.trim();
	var rd = formobj.regd.value.trim();
	var tdepamt = ((+cd)+(+rd)).toFixed(2);
	var ima = formobj.iamt.value.trim();
	var acs = formobj.acs.value;
	var gsta = formobj.gsta.value.trim();
	
	var pidv = formobj.epid.value;
	var tv_date = formobj.tv_date.value;
	if(tv_date == ""){
		document.getElementById("dialog-1").innerHTML = "Please enter TV Date.";
		alertdialogue();
		return;
	}
	var tvdate = new Date(tv_date);
	var millisecs = tvdate.getTime();

	if(eproduct>0){
		var pval=new Array();
		var cyldDateInEqpm = 0;
		var regDateInEqpm = 0;
		for(var i=0; i<equipment_data.length; i++) {
			pval.push(equipment_data[i].prod_code);

			if(equipment_data[i].prod_code == pidv) {
				cyldDateInEqpm = equipment_data[i].effective_date;
			}else if(equipment_data[i].prod_code == 10) {
				regDateInEqpm = equipment_data[i].effective_date;
			}
			if(cyldDateInEqpm !=0 && regDateInEqpm != 0 )
				break;
		}
		
		if(!pval.includes(10)) {
			document.getElementById("dialog-1").innerHTML = "Please Add REGULATOR-SC TYPE in Equipment Master.";
			alertdialogue();
			return;
		}
		if((millisecs < cyldDateInEqpm) && (millisecs < regDateInEqpm)){
			document.getElementById("dialog-1").innerHTML = "The product you have selected and SC Type Regulator is invalid for the given date. Please check effective date of the two in Equipment Master.";
			alertdialogue();
			return;
		}else if(millisecs < cyldDateInEqpm) {
			document.getElementById("dialog-1").innerHTML = "The product you have selected is invalid for the given date. Please check effective date of the added product in Equipment Master.";
			alertdialogue();
			return;
		}else if(millisecs < regDateInEqpm) {
			document.getElementById("dialog-1").innerHTML = "The REGULATOR SC TYPE is invalid for the given date. Please check effective date of the regulator in Equipment Master.";
			alertdialogue();
			return;
		}

		var ems = validateCalcTVEntries(eNoCyl,eNoReg,cd,rd,ima,acs,gsta);
		if (ems.length > 0) {
			document.getElementById("dialog-1").innerHTML = ems;
			alertdialogue();
			//alert(ems);
			return;
		}
		var ac = formobj.ac.value.trim();
		var ta = ((+cd) + (+rd));
		formobj.invamt.value = round((ta-ima-ac-gsta),0);
		formobj.depamt.value = tdepamt;
		formobj.cgsta.value = gsta/2;
		formobj.sgsta.value = gsta/2;
		document.getElementById("save_data").disabled=false;
	}else{
		document.getElementById("dialog-1").innerHTML = "please select the product";
		alertdialogue();
		//alert("please select the product");
	}
	document.getElementById("save_data").disabled=false;
	restrictChangingValues(".freez");
}

function validateCalcTVEntries(NoCyl,NoReg,cd,rd,ima,acs,gsta) {
	var errmsg = "";
	var formobj = document.getElementById('data_form');

	
	if(!(NoCyl.length>0))
		errmsg = errmsg + "Please Enter NO. OF CYL<br>";
	else if (!(checkNumber(NoCyl)))
		errmsg = errmsg + "Please enter valid NO. OF CYL<br>";
	else if(!(NoCyl==1 || NoCyl==2))
		errmsg = errmsg+ "NO. OF CYL must be 1 or 2 <br>";

	if(!(NoReg.length>0))
		errmsg = errmsg + "Please Enter NO. OF REGULATORS.Enter 0 if not available. <br>";
	else if (!(checkNumber(NoReg)))
		errmsg = errmsg + "Please enter valid NO. OF REGULATORS<br>";
	else if(!(NoReg==0 || NoReg==1))
		errmsg = errmsg+ "NO. OF REGULATORS must be 0 or 1 <br>";
	
	if (!(cd.length > 0))
		errmsg = errmsg + "Please enter CYLINDER DEPOSIT and then Calculate<br>"; 
	else if (validateDot(cd))
		errmsg = errmsg + "CYLINDER DEPOSIT must contain atleast one number<br>";
	else if (!(isDecimalNumber(cd)))
		errmsg = errmsg + "CYLINDER DEPOSIT must contain only numerics<br>";
	else if (parseFloat(cd)==0)
		errmsg = errmsg + "Please Enter Valid CYLINDER DEPOSIT and then CALCULATE<br>";
	else if (!validateDecimalNumberMinMax(cd,0,1000000))
		errmsg = errmsg+ "CYLINDER DEPOSIT must be less than 10,00,000 and greater than 0<br>";
	else
		formobj.cyld.value = round(parseFloat(cd), 2);

	if(!(rd.length >0))
		errmsg = errmsg + "Please Enter REGULATOR DEPOSIT. Enter 0 if not available <br>";
	else if (validateDot(rd))
		errmsg = errmsg + "REGULATOR DEPOSIT must contain atleast one number. <br>";
	else if (!(isDecimalNumber(rd)))
		errmsg = errmsg + "REGULATOR DEPOSIT must contain only numerics.<br>";
	else if (!validateDecimalNumberMinMax(rd,-1,10000))
		errmsg = errmsg+ "REGULATOR DEPOSIT must be less than 10,000 and greater than 0.<br>";
	else if ((NoReg.length>0) && (NoReg == 0) && (!(rd == 0)))
		errmsg = errmsg+ "REGULATOR DEPOSIT must be 0 for Zero NO OF REGULATORS.<br>";
	else if (NoReg == 1 && (rd == 0))
		errmsg = errmsg+ "REGULATOR DEPOSIT must be grater than 0.<br>";
	else
		formobj.regd.value = round(parseFloat(rd), 2);
	
	if(!(ima.length >0))
		errmsg = errmsg + "Please Enter IMPREST AMOUNT. Enter 0 if not available <br>";
	else if (validateDot(ima))
		errmsg = errmsg + "IMPREST AMOUNT must contain atleast one number <br>";
	else if (!(isDecimalNumber(ima)))
		errmsg = errmsg + "IMPREST AMOUNT must contain only numerics <br>";
	else if (!validateDecimalNumberMinMax(ima,-1,10000))
		errmsg = errmsg+ "IMPREST AMOUNT must be less than 10 thousand and greater than 0<br>";
	else
		formobj.iamt.value = round(parseFloat(ima), 2);

	if(!(acs>0))
		errmsg = errmsg + "Please Select ADMIN CHARGES<br>";
	
/*	if(!(gsta.length>0))
		errmsg = errmsg + "Please Enter GST AMOUNT<br>";
	else if (!(isDecimalNumber(gsta)))
		errmsg = errmsg + "Please enter valid GST AMOUNT<br>";*/
	
	return errmsg;
}

/*function fetchTVAdminCharges(){
	var formobj = document.getElementById('data_form');
	var eproduct = formobj.epid.selectedIndex;	
	if(eproduct>0) {
		for(var z=0; z<services_data.length; z++){
			if(services_data[z].prod_code == 20) {
				document.getElementById("data_form").ac.value = services_data[z].prod_charges;
				document.getElementById("data_form").gsta.value = services_data[z].gst_amt;
				break;
			}
		}
		document.getElementById("calc_data").disabled=false;
	}else {
		document.getElementById("dialog-1").innerHTML = "Please Select PRODUCT and the FETCH.";
		alertdialogue();
		//alert("Please Select PRODUCT and the FETCH.");
		return;
	}

  	document.getElementById("calc_data").disabled = false;

	//var spfrz= document.getElementById("epid").options;
	//disableSelect(spfrz,spfrz.length);	
}
*/


function fetchGstValuesForServices(){
	var formobj = document.getElementById('data_form');
	var acsVal = formobj.acs.value;
	if(acsVal !=0 ){
		for(var z=0; z<services_data.length; z++) {
			if(acsVal == services_data[z].prod_code) {
				document.getElementById("data_form").ac.value = services_data[z].prod_charges;
				document.getElementById("data_form").gsta.value = services_data[z].gst_amt;
				break;
			}
		}
	}else {
		document.getElementById("data_form").gsta.value = "";
		document.getElementById("data_form").ac.value = "";
	}
}



/*function fetchTVAdminCharges(){
  	document.getElementById("calc_data").disabled = true;
	var formobj = document.getElementById('data_form');
	
	var pidv = formobj.epid.value;
	var tv_date = formobj.tv_date.value;
	if(tv_date == ""){
		document.getElementById("dialog-1").innerHTML = "Please enter TV Date.";
		alertdialogue();
		return;
	}
	var tvdate = new Date(tv_date);
	var millisecs = tvdate.getTime();
	
	var eproduct = formobj.epid.selectedIndex;
	if(eproduct > 0) {
		
		var pval=new Array();
		var cyldDateInEqpm = 0;
		var regDateInEqpm = 0;
		for(var i=0; i<equipment_data.length; i++) {
			pval.push(equipment_data[i].prod_code);

			if(equipment_data[i].prod_code == pidv) {
				cyldDateInEqpm = equipment_data[i].effective_date;
			}else if(equipment_data[i].prod_code == 10) {
				regDateInEqpm = equipment_data[i].effective_date;
			}
			if(cyldDateInEqpm !=0 && regDateInEqpm != 0 )
				break;
		}
		
		if(!pval.includes(10)) {
			document.getElementById("dialog-1").innerHTML = "Please Add REGULATOR-SC TYPE in Equipment Master.";
			alertdialogue();
			return;
		}
		if((millisecs < cyldDateInEqpm) && (millisecs < regDateInEqpm)){
			document.getElementById("dialog-1").innerHTML = "The product you have selected and SC Type Regulator is invalid for the given date. Plese check effective date of the two in Equipment Master.";
			alertdialogue();
			return;
		}else if(millisecs < cyldDateInEqpm) {
			document.getElementById("dialog-1").innerHTML = "The product you have selected is invalid for the given date. Plese check effective date of the added product in Equipment Master.";
			alertdialogue();
			return;
		}else if(millisecs < regDateInEqpm) {
			document.getElementById("dialog-1").innerHTML = "The REGULATOR SC TYPE is invalid for the given date. Plese check effective date of the regulator in Equipment Master.";
			alertdialogue();
			return;
		}

		for(var z=0; z<services_data.length; z++) {
			if(services_data[z].prod_code == 20) {
				document.getElementById("data_form").ac.value = services_data[z].prod_charges;
				document.getElementById("data_form").gsta.value = services_data[z].gst_amt;
				break;
			}
		}
		document.getElementById("calc_data").disabled = false;
	}else {
		document.getElementById("dialog-1").innerHTML = "Please Select PRODUCT and the FETCH.";
		alertdialogue();
		return;
	}

  	document.getElementById("calc_data").disabled = false;

	//var spfrz= document.getElementById("epid").options;
	//disableSelect(spfrz,spfrz.length);	
}*/

function showInvoice(inv_id,si_date){
	popitup(inv_id,si_date);
}

function popitup(inv_id,si_date) {
	var w=window.open("PopupControlServlet?actionId=988&sitype=12&sid="+inv_id +"&si_date="+si_date,null,"height=400,width=1024,status=yes,toolbar=no,menubar=no,location=no");
	w.print();
	return false; 
}