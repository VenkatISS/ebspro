/*function showRCFormDialog() {
	var check = checkDayEndDateForAdd(dedate,a_created_date,effdate);
	if(check==true) {
		document.getElementById('myModal').style.display = "block";
	}else if(check!=true) {
		document.getElementById("dialog-1").innerHTML = check;
		alertdialogue();
		return;
	}
}
*/

function showRCFormDialog() {
	document.getElementById('myModal').style.display = "block";
	pushMenu();
}


function closeRCFormDialog() {
	var efrz = document.getElementsByClassName("freez");
	remEleReadOnly(efrz,efrz.length);

	var spfrz= document.getElementById("epid").options;
	enableSelect(spfrz,spfrz.length);

	document.getElementById("data_form").reset();
	document.getElementById('myModal').style.display = "none";
    $("body").removeClass('sidebar-collapse').trigger('expanded.pushMenu');

}


// set bank account id
if(bank_data.length>0){
	for(var y=0; y<bank_data.length; y++) {
		if(bank_data[y].bank_code == "STAR ACCOUNT") {
			document.getElementById('data_form').bankId.value = bank_data[y].id;
			document.getElementById('m_data_form').bankId.value = bank_data[y].id;
			break;
		}
	}
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
// Process page data
var rowpdhtml = "";
var tbody = document.getElementById('m_data_table_body');
if(page_data.length>0) {
	for(var z=page_data.length-1; z>=0; z--){
		var ed = new Date(page_data[z].rc_date);
		var spd = fetchProductDetails(cat_cyl_types_data, page_data[z].prod_code);
		var staffName = getStaffName(staff_data,page_data[z].staff_id);
		var tblRow = tbody.insertRow(-1);
	    tblRow.style.height="30px";
	    tblRow.align="left";
	    var del = "<td align='center'><img src='images/delete.png' onclick='deleteItem("+page_data[z].id+','+page_data[z].rc_date+")'></td>";
		if(page_data[z].deleted==2){
			del = "<td>TRANSACTION CANCELED</td>";
		}
		var invno;
		if(page_data[z].deleted==2){
			invno = '<td>'+page_data[z].sr_no+'</td>'
 		}else {
			invno = '<td><a href="javascript:showInvoice('+page_data[z].id+','+page_data[z].rc_date+')">'+page_data[z].sr_no+'</a></td>'
		} 
		
	    tblRow.innerHTML = invno +
	    		'<td>'+ed.getDate()+"-"+months[ed.getMonth()]+"-"+ed.getFullYear()+'</td>'+
 				'<td>'+staffName+'</td>'+
				'<td>'+page_data[z].customer_no+'</td>'+
		  		'<td>'+spd+'</td>'+
        		'<td>'+page_data[z].no_of_cyl+'</td>'+
        		'<td>'+page_data[z].no_of_reg+'</td>'+
        		'<td>'+page_data[z].cyl_deposit+'</td>'+
				'<td>'+page_data[z].reg_deposit+'</td>'+
		   		'<td>'+page_data[z].dgcc_amount+'</td>'+
				'<td>'+page_data[z].admin_charges+'</td>'+
				'<td>'+page_data[z].cgst_amount+'</td>'+
				'<td>'+page_data[z].sgst_amount+'</td>'+
				'<td>'+page_data[z].payment_terms+'</td>'+
			  	'<td>'+page_data[z].rc_amount+'</td>'+ del
	};
}


// Construct Category Type html
var ctdatahtml = "<OPTION VALUE='0'>SELECT</OPTION>";
ctdatahtml = ctdatahtml + "<OPTION VALUE='-1' disabled>---CYLINDER LIST---</OPTION>";
var rtdatahtml = "<OPTION VALUE='0'>SELECT</OPTION>";
rtdatahtml = rtdatahtml + "<OPTION VALUE='-1' disabled>---REGULATOR LIST---</OPTION>";
if(cat_cyl_types_data.length>0) {
	for(var z=0; z<cat_cyl_types_data.length; z++){
		if(cat_cyl_types_data[z].cat_type==1) {
			for(var y=0; y<equipment_data.length; y++){
				if(equipment_data[y].prod_code == cat_cyl_types_data[z].id) {
					ctdatahtml=ctdatahtml+"<OPTION VALUE='"+cat_cyl_types_data[z].id+"'>"
					+cat_cyl_types_data[z].cat_name+"-"+cat_cyl_types_data[z].cat_desc+"</OPTION>";
					break;
				}
			}
		} else {
			rtdatahtml=rtdatahtml+"<OPTION VALUE='"+cat_cyl_types_data[z].id+"'>"
			+cat_cyl_types_data[z].cat_name+"-"+cat_cyl_types_data[z].cat_desc+"</OPTION>";
		}
	}
}
document.getElementById("epidSpan").innerHTML = "<select id='epid' name='epid' class='form-control input_field tp'>"+ctdatahtml+"</select>";

// Construct Staff html
staffdatahtml = "<OPTION VALUE='0'>SELECT</OPTION>";
if(staff_data.length>0) {
	for(var z=0; z<staff_data.length; z++){
		if(staff_data[z].deleted == 0){
			staffdatahtml=staffdatahtml+"<OPTION VALUE='"+staff_data[z].id+"'>"+staff_data[z].emp_name+"</OPTION>";
		}
	}
}
document.getElementById("sSpan").innerHTML = "<select id='staff_id' name='staff_id' class='form-control input_field' style='width:205px;margin-top:6px'>"+staffdatahtml+"</select>";




function saveData(obj){
	var formobj = document.getElementById('data_form');
	var ems = "";
	// .............................
	if(document.getElementById("custn") != null){
		// if(!(isEmpty(document.getElementById("rc_date")))){
		
		var elements = document.getElementById('epid');
		if(elements.length>0) {
			var ercDate = formobj.rc_date.value;
			var estaffName = formobj.staff_id.selectedIndex;
			var ecusNumb = formobj.custn.value;
			var eproduct = formobj.epid.selectedIndex;
			var eNoCyl = formobj.nocyl.value;
			var eNoReg = formobj.noreg.value;			
			var eCylDep = formobj.cyld.value;
			var erd = formobj.regd.value;
			var edgcc = formobj.dgcc.value;
			var eac = formobj.ac.value;
			var egsta = formobj.gsta.value;
			var ept = formobj.pt.value;
			
			ems = validateRCEntries(ercDate,estaffName,ecusNumb,eproduct,eNoCyl,eNoReg,eCylDep,erd,edgcc,eac,egsta,ept);
		}
	}
	if(ems.length>0) {
		document.getElementById("dialog-1").innerHTML = ems;
		alertdialogue();
		// alert(ems);
		return;
	}
	
	var negbalmsg = checkForNegativeBalances(formobj);
	if(negbalmsg == false) {		
		$("#myDialogText").text("YOUR SV/TV ACCOUNT HAVE NO SUFFICIENT BALANCE TO COMPLETE THIS TRANSACTION. DO YOU WANT TO CONTINUE?");
		confirmTxtDialogue(formobj);
	}else {
		// For restricting save to single click
		var objId = obj.id;
		document.getElementById(objId).disabled = true;
		formobj.submit();
	}	
}

function checkForNegativeBalances(formobj) {
	var cdep = formobj.cyld.value;
	var rdep = formobj.regd.value;
	var deptotal = +(cdep) + +(rdep);
	var starcb = 0;
	if(bank_data.length>0) {
		for(var y=0; y<bank_data.length; y++) {
			if(bank_data[y].bank_code == "STAR ACCOUNT"){
				starcb = bank_data[y].acc_cb;
				break;
	    	}
	    }
	}
	
	if(parseFloat(deptotal) > parseFloat(starcb)) {
		return false;
	}else return true;
}

/*
 * function deleteItem(iid,rcdate) { var
 * flag=validateDayEndForDelete(rcdate,dedate); if(flag) { if (confirm("Are you
 * sure you want to delete?") == true) { var formobj =
 * document.getElementById('m_data_form'); formobj.actionId.value = "5713";
 * formobj.dataId.value = iid; formobj.submit(); } }else alert("This record
 * can't be deleted as you have submitted and verified your DAYEND calculations
 * succuessfully... ");
 *  }
 */

function deleteItem(iid,rcdate){
	var flag=validateDayEndForDelete(rcdate,dedate);
	if(flag){
	 $("#myDialogText").text("Are You Sure You Want To Delete?");
	 var formobj = document.getElementById('m_data_form');
	 confirmDialogue(formobj,5713,iid);
	}else {
		document.getElementById("dialog-1").innerHTML = "This record can't be deleted as you have submitted and verified your DAYEND calculations succuessfully";
		alertdialogue();
	}
}

function calculateValues(){
	var formobj = document.getElementById('data_form');
	var eproduct = formobj.epid.selectedIndex;
	var ncs = formobj.nocyl.value.trim();
	var nreg = formobj.noreg.value;			
	var spupv = formobj.spup.value.trim();  // unit price
	var sptpv = formobj.sptp.value.trim();  // gst%
	var tspv = (ncs*spupv).toFixed(2); // basic price
	var tspgstv = ((tspv*sptpv)/100).toFixed(2); // gst value
	
	var ofpbpv = formobj.opbasicprice.value.trim();
	var ofptspv = (ncs*ofpbpv).toFixed(2); // basic price
	var ofptspgstv = ((ofptspv*sptpv)/100).toFixed(2);
	
	formobj.spa.value = tspv; 
	formobj.spgsta.value = tspgstv;

	var cdv = formobj.cyld.value.trim();
	var rdv = formobj.regd.value.trim();
//	var tdepamt = cdv + rdv;
	formobj.depamt.value=((+cdv)+(+rdv)).toFixed(2);
	var dgccv = formobj.dgcc.value.trim();
	var acs = parseInt(formobj.acs.value.trim());
	
	if(eproduct>0){
		var ems = validateRCEntries2(cdv,rdv,ncs,nreg,acs);
		if (ems.length > 0) {
			document.getElementById("dialog-1").innerHTML = ems;
			alertdialogue();
			formobj.gsta.value = "";
			formobj.invamt.value = "";
			return;
		}

		var acv = formobj.ac.value.trim();
		var acgsta = fetchRCAdminCharges(acs,acv); // here we will get tax for rc admin charges.
		var dgccgsta = fetchDGCCCharges(); // here we will get dgcc tax
		formobj.gstsa.value = ( (+acgsta) + (+dgccgsta));
		
		formobj.opbasicprice.value = parseFloat(ofpbpv).toFixed(2);
		
		var gsta = formobj.gstsa.value.trim(); // gstsa: services gst
		acv = formobj.ac.value.trim();
		
		var ta = +(tspv) + +(tspgstv) + +(cdv) + (+(rdv) + +((+dgccv) + ((+acv) + (+gsta))));
		formobj.gsta.value = (+(tspgstv) + +(gsta));
		formobj.invamt.value = round(ta,0);
		formobj.cgsta.value = ((formobj.gsta.value)/2).toFixed(2);
		formobj.sgsta.value = ((formobj.gsta.value)/2).toFixed(2);
		formobj.scgsta.value = ((gsta)/2).toFixed(2);
		formobj.ssgsta.value = ((gsta)/2).toFixed(2);
		
		var opgsta = (+(ofptspgstv) + +(gsta));
		formobj.opcgsta.value = (opgsta/2).toFixed(2);
		formobj.opsgsta.value = (opgsta/2).toFixed(2);
		formobj.opigsta.value = "0.00";
		formobj.optota.value = round((+(ofptspv) + +(ofptspgstv) + +(cdv) + (+(rdv) + +((+dgccv) + ((+acv) + (+gsta))))),0);
		formobj.optaxablea.value = round((+(formobj.optota.value) - +(cdv) - +(rdv) - +(formobj.opigsta.value) - +(formobj.opcgsta.value) - +(formobj.opsgsta.value) - +((+dgccv)) - +((+acv))),2);
		
		document.getElementById("save_data").disabled=false;
	}else{
		document.getElementById("dialog-1").innerHTML = "please select the Product";
		alertdialogue();
		// alert("please select the Product");
	}
	document.getElementById("save_data").disabled=false;
	restrictChangingAllValues(".freez",".tp");

	// var efrz = document.getElementsByClassName("freez");
	// makeEleReadOnly(efrz,efrz.length);
}

/*
 * function fetchReconnectionCharges(){ var formobj =
 * document.getElementById('data_form'); var eproduct =
 * formobj.epid.selectedIndex; var pval=new Array();
 * 
 * for(var i=0; i<equipment_data.length; i++) {
 * pval.push(equipment_data[i].prod_code); } if(eproduct>0) {
 * if(!pval.includes(10)) { document.getElementById("dialog-1").innerHTML =
 * "Please Add REGULATOR-SC TYPE in Equipment Master."; alertdialogue();
 * //alert("Please Add REGULATOR-SC TYPE in Equipment Master."); return; }
 * fetchPriceAndGST(); var acgsta = fetchRCAdminCharges(); var dgccgsta =
 * fetchDGCCCharges(); formobj.gstsa.value = ( (+acgsta) + (+dgccgsta));
 * document.getElementById("calc_data").disabled=false; }else {
 * document.getElementById("dialog-1").innerHTML = "Please Select PRODUCT and
 * then FETCH."; alertdialogue(); //alert("Please Select PRODUCT and then
 * FETCH."); return; }
 * 
 * document.getElementById("calc_data").disabled = false;
 * 
 * //var spfrz= document.getElementById("epid").options;
 * //disableSelect(spfrz,spfrz.length); }
 */

/*
 * function fetchReconnectionCharges() {
 * document.getElementById("calc_data").disabled = true; var formobj =
 * document.getElementById('data_form'); var eproduct =
 * formobj.epid.selectedIndex; var pidv = formobj.epid.value;
 * 
 * var rcdate = new Date(formobj.rc_date.value); var millisecs =
 * rcdate.getTime();
 * 
 * var pval=new Array();
 * 
 * var cyldDateInEqpm = 0; var regDateInEqpm = 0; for(var i=0; i<equipment_data.length;
 * i++) { pval.push(equipment_data[i].prod_code);
 * 
 * if(equipment_data[i].prod_code == pidv) { cyldDateInEqpm =
 * equipment_data[i].effective_date; }else if(equipment_data[i].prod_code == 10) {
 * regDateInEqpm = equipment_data[i].effective_date; } } if(eproduct>0) {
 * if(!pval.includes(10)) { document.getElementById("dialog-1").innerHTML =
 * "Please Add REGULATOR-SC TYPE in Equipment Master."; alertdialogue();
 * //alert("Please Add REGULATOR-SC TYPE in Equipment Master."); return; }
 * 
 * if((millisecs < cyldDateInEqpm) && (millisecs < regDateInEqpm)){
 * document.getElementById("dialog-1").innerHTML = "The product you have
 * selected and SC Type Regulator is invalid for the given date. Plese check
 * effective date of the two in Equipment Master."; alertdialogue();
 * //alert("please define the price of product for sale invoice month in price
 * master and continue"); return; }else if(millisecs < cyldDateInEqpm) {
 * document.getElementById("dialog-1").innerHTML = "The product you have
 * selected is invalid for the given date. Plese check effective date of the
 * added product in Equipment Master."; alertdialogue(); //alert("please define
 * the price of product for sale invoice month in price master and continue");
 * return; }else if(millisecs < regDateInEqpm) {
 * document.getElementById("dialog-1").innerHTML = "The REGULATOR SC TYPE is
 * invalid for the given date. Plese check effective date of the regulator in
 * Equipment Master."; alertdialogue(); //alert("please define the price of
 * product for sale invoice month in price master and continue"); return; }
 * 
 * fetchPriceAndGST(); var acgsta = fetchRCAdminCharges(); var dgccgsta =
 * fetchDGCCCharges(); formobj.gstsa.value = ( (+acgsta) + (+dgccgsta));
 * document.getElementById("calc_data").disabled=false; }else {
 * document.getElementById("dialog-1").innerHTML = "Please Select PRODUCT and
 * then FETCH."; alertdialogue(); //alert("Please Select PRODUCT and then
 * FETCH."); return; }
 * 
 * document.getElementById("calc_data").disabled = false;
 * 
 * //var spfrz= document.getElementById("epid").options;
 * //disableSelect(spfrz,spfrz.length); }
 */

function fetchReconnectionCharges() {
	document.getElementById("calc_data").disabled = true;
	var formobj = document.getElementById('data_form');
	var eproduct = formobj.epid.selectedIndex;
	var pidv = formobj.epid.value;
	
	var rcdate = new Date(formobj.rc_date.value);
	var millisecs = rcdate.getTime();

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
	}
	if(eproduct>0) {
		if(!pval.includes(10)) {
			document.getElementById("dialog-1").innerHTML = "Please Add REGULATOR-SC TYPE in Equipment Master.";
			alertdialogue();
			// alert("Please Add REGULATOR-SC TYPE in Equipment Master.");
			return;
		}
		
		if((millisecs < cyldDateInEqpm) && (millisecs < regDateInEqpm)){
			document.getElementById("dialog-1").innerHTML = "The product you have selected and SC Type Regulator is invalid for the given date. Please check effective date of the two in Equipment Master.";
			alertdialogue();
			// alert("please define the price of product for sale invoice month
			// in price master and continue");
			return;
		}else if(millisecs < cyldDateInEqpm) {
			document.getElementById("dialog-1").innerHTML = "The product you have selected is invalid for the given date. Please check effective date of the added product in Equipment Master.";
			alertdialogue();
			// alert("please define the price of product for sale invoice month
			// in price master and continue");
			return;
		}else if(millisecs < regDateInEqpm) {
			document.getElementById("dialog-1").innerHTML = "The REGULATOR SC TYPE is invalid for the given date. Please check effective date of the regulator in Equipment Master.";
			alertdialogue();
			// alert("please define the price of product for sale invoice month
			// in price master and continue");
			return;
		}

		fetchPriceAndGST();
		// var acgsta = fetchRCAdminCharges();
		var dgccgsta = fetchDGCCCharges();
		//		formobj.gstsa.value = ( (+acgsta) + (+dgccgsta));
		// here setting value of dgcc tax amount. While performing Caculate, we will add this dgcc tax with admin charges tax and set this total amount to gstsa field.
		formobj.gstsa.value = dgccgsta;
	}else {
		document.getElementById("dialog-1").innerHTML = "Please Select PRODUCT and then FETCH.";
		alertdialogue();
		// alert("Please Select PRODUCT and then FETCH.");
		return;
	}
	
  	document.getElementById("calc_data").disabled = false;

	// var spfrz= document.getElementById("epid").options;
	// disableSelect(spfrz,spfrz.length);
}

function fetchDeposit(spc){
	for(var z=0; z<equipment_data.length; z++){
		if(equipment_data[z].prod_code == spc) {
			return equipment_data[z].vatp;
		}
	}
}

function fetchValueForAdminCharges() {
	var formobj = document.getElementById('data_form');
	var acsVal = formobj.acs.value;
	if(acsVal !=0 ){
		for(var z=0; z<services_data.length; z++) {
			if(acsVal == services_data[z].prod_code) {
				document.getElementById("data_form").ac.value = services_data[z].prod_charges;
				break;
			}
		}
	}else {
		document.getElementById("data_form").ac.value = "";
	}	
}

function fetchRCAdminCharges(rcServiceChargescode,acv){
	for(var z=0; z<services_data.length; z++){
		if(services_data[z].prod_code == rcServiceChargescode) {
			if(acv = "")
				document.getElementById("data_form").ac.value = (services_data[z].prod_charges).trim();
			return services_data[z].gst_amt;
		}
	}
	document.getElementById("calc_data").disabled=false;
}

function fetchDGCCCharges(){
	for(var z=0; z<services_data.length; z++){
		if(services_data[z].prod_code == 23) {
			document.getElementById("data_form").dgcc.value = services_data[z].prod_charges;
			return services_data[z].gst_amt;
		}
	}
	document.getElementById("calc_data").disabled=false;
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

function fetchPriceAndGST(){
	var formobj = document.getElementById('data_form');
	var pidv = formobj.epid.value;
	formobj.sptp.value = fetchGSTPercent(equipment_data,pidv);
	formobj.spup.value = fetchRefillUnitPrice(refill_prices_data,pidv);
	
	formobj.opbasicprice.value = fetchRefillOfferPriceBp(refill_prices_data,pidv);
}

function validateRCEntries(rcDate,staffName,cusNumb,product,NoCyl,NoReg,CylDep,rd,dgccv,ac,gsta,pt){
	var errmsg = "";
	var formobj = document.getElementById('data_form');

	var vd = isValidDate(rcDate);
	var vfd = ValidateFutureDate(rcDate);	
	if (!(rcDate.length > 0))
		errmsg = errmsg + "Please Enter INVOICE DATE<br>";
	else if (vd != "false")
		errmsg = errmsg +vd+"<br>";
	else if(validateDayEndAdd(rcDate,dedate)){
        errmsg = "INVOICE DATE should be after DayEndDate<br>";
        return errmsg;
	}else if (vfd != "false")
		errmsg = errmsg +"INVOICE"+vfd+"<br>";
/*
 * else { var lastTrxnDateCheck =
 * checkforLastTrxnDate(dedate,a_created_date,rcDate,trdate);
 * if(lastTrxnDateCheck != "false"){ return lastTrxnDateCheck; } }
 */
	

	if(!(staffName>0))
		errmsg = errmsg + "Please Select STAFF<br>";
	
	if(!(cusNumb.length>0))
		errmsg = errmsg + "Please Enter  CUST NO./NAME<br>";
	else if(!validateAlphaNumeric(cusNumb))
			errmsg = errmsg + "Please Enter  valid CUST NO./NAME<br>";
	else if((cusNumb.length<5 ||cusNumb.length>15))
		errmsg = errmsg + "Please Enter  valid CUST NO./NAME<br>";
	
	if(!(product>0))
		errmsg = errmsg + "Please Select PRODUCT<br>";
	
	if(!(NoCyl.length>0))
		errmsg = errmsg + "Please Enter NO. OF CYL<br>";
	else if (!(checkNumber(NoCyl)))
		errmsg = errmsg + "Please enter valid NO. OF CYL<br>";
	else if(!(NoCyl==1 || NoCyl==2))
		errmsg = errmsg+ "NO OF CYL must be 1 or 2<br>";
	
	if(!(NoReg.length>0))
		errmsg = errmsg + "Please Enter NO. OF REGULATORS.Enter 0 if not available<br>";
	else if (!(checkNumber(NoReg)))
		errmsg = errmsg + "Please enter valid NO. OF REGULATORS<br>";
	else if(!(NoReg==0 || NoReg==1))
		errmsg = errmsg+ "NO. OF REGULATORS must be 0 or 1<br>";
   
	if (!(CylDep.length > 0))
		errmsg = errmsg + "Please enter CYLINDER DEPOSIT and then Calculate<br>";
	else if (validateDot(CylDep))
		errmsg = errmsg + "CYLINDER DEPOSIT must contain atleast one number <br>";
	else if (!(isDecimalNumber(CylDep)))
		errmsg = errmsg + "CYLINDER DEPOSIT must contain only numerics<br>";
	else if (!validateDecimalNumberMinMax(CylDep,0,1000000))
		errmsg = errmsg+ "CYLINDER DEPOSIT must be less than 10,00,000 and greater than 0<br>";
	else
		formobj.cyld.value = round(parseFloat(CylDep), 2);	

	
	if (!(rd.length > 0))
		errmsg = errmsg + "Please enter REGULATOR DEPOSIT and then Calculate.Enter 0 if not available.<br>";
	else if (validateDot(rd))
		errmsg = errmsg + "REGULATOR DEPOSIT must contain atleast one number <br>";
	else if (!(isDecimalNumber(rd)))
		errmsg = errmsg + "REGULATOR DEPOSIT must contain only numerics<br>";
	else if (!validateDecimalNumberMinMax(rd,-1,10000))
		errmsg = errmsg+ "REGULATOR DEPOSIT must be <10000 and  >=0.<br>";
	else if ((NoReg.length>0) && (NoReg == 0) && (!(rd == 0)))
		errmsg = errmsg+ "REGULATOR DEPOSIT must be 0 for Zero NO OF REGULATORS.<br>"; 
	else if (NoReg == 1 && (rd == 0))
		errmsg = errmsg+ "REGULATOR DEPOSIT must grater than be 0.<br>"; 
	else
		formobj.regd.value = round(parseFloat(rd), 2);
	
	if(!(dgccv>0))
		errmsg = errmsg + "Please Enter  DGCC<br>";
	
//	if(ac == "")
//		errmsg = errmsg + "Please Select ADMIN CHARGES<br>";
	
	if(!(gsta>0))
		errmsg = errmsg + "Please Enter GST AMOUNT<br>";
	
	if(!(pt.length>0))
		errmsg = errmsg + "Please Enter PAYMENT TERMS<br>";
	else if(!checkAlphabets(pt))
		errmsg = errmsg + "PAYMENT TERMS must contain only alphabets<br>";

	return errmsg;
}

function validateRCEntries2(CylDepv,rdv,NoCylv,nreg,acs){
	var errmsg = "";
	var formobj = document.getElementById('data_form');
	
	if(!(NoCylv.length>0))
		errmsg = errmsg + "Please Enter NO. OF CYL<br>";
	else if (!(checkNumber(NoCylv)))
		errmsg = errmsg + "Please Enter valid NO. OF CYL<br>";
	else if(!(NoCylv==1 || NoCylv==2))
		errmsg = errmsg+ "NO. OF CYL Must be 1 or 2<br>";

	if(!(nreg.length>0))
		errmsg = errmsg + "Please Enter NO. OF REGULATORS.Enter 0 if not available.<br>";
	else if (!(checkNumber(nreg)))
		errmsg = errmsg + "Please Enter Valid NO OF REGULATORS<br>";
	else if(!(nreg==0 || nreg==1))
		errmsg = errmsg+ "NO. OF REGULATORS Must Be 0 or 1<br>";
	
	
	if (!(CylDepv.length > 0))
		errmsg = errmsg + "Please enter CYLINDER DEPOSIT and then Calculate<br>";
	else if (validateDot(CylDepv))
		errmsg = errmsg + "CYLINDER DEPOSIT must contain atleast one number <br>";
	else if (!(isDecimalNumber(CylDepv)))
		errmsg = errmsg + "CYLINDER DEPOSIT must contain only numerics<br>";
	else if (!validateDecimalNumberMinMax(CylDepv,0,1000000))
		errmsg = errmsg+ "CYLINDER DEPOSIT must be less than 10,00,000 and greater than 0<br>";
	else
		formobj.cyld.value = round(parseFloat(CylDepv), 2);	
	
	
	if (!(rdv.length > 0))
		errmsg = errmsg + "Please enter REGULATOR DEPOSIT.Enter 0 if not available.<br>";
	else if (validateDot(rdv))
		errmsg = errmsg + "REGULATOR DEPOSIT must contain atleast one number <br>";
	else if (!(isDecimalNumber(rdv)))
		errmsg = errmsg + "REGULATOR DEPOSIT must contain only numerics<br>";
	else if (!validateDecimalNumberMinMax(rdv,-1,10000))
		errmsg = errmsg+ "REGULATOR DEPOSIT must be <10000 and >=0<br>";
	else if ((nreg.length>0) && (nreg == 0) && (!(rdv == 0)))
		errmsg = errmsg+ "REGULATOR DEPOSIT must be 0 for Zero NO OF REGULATORS.<br>";
	else
		formobj.regd.value = round(parseFloat(rdv), 2);
	
	if(!acs>0){
		errmsg = errmsg+ "Please select Admin Charges";
	}
	
	return errmsg;
}
function showInvoice(inv_id,si_date){
	popitup(inv_id,si_date);
}

function popitup(inv_id,si_date) {
	var w = window.open("PopupControlServlet?actionId=992&sitype=8&sid="+inv_id +"&si_date="+si_date,null,"height=400,width=1024,status=yes,toolbar=no,menubar=no,location=no");
	w.print();
	return false;
}