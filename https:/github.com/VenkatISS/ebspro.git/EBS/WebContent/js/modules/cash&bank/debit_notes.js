//Construct OMC & Vendor Type html
vendordatahtml = "<OPTION VALUE='0'>SELECT</OPTION>";
if(cvo_data.length>0) {
	for(var z=0; z<cvo_data.length; z++){
		if((cvo_data[z].cvo_cat!=1) && (cvo_data[z].cvo_cat!=3) && cvo_data[z].deleted==0) {
			if(cvo_data[z].cvo_cat == 0)
				vendordatahtml=vendordatahtml+"<OPTION VALUE='"+cvo_data[z].id+"'>"+cvo_data[z].cvo_name+"</OPTION>";
			else if(cvo_data[z].cvo_cat == 2)
				vendordatahtml=vendordatahtml+"<OPTION VALUE='"+cvo_data[z].id+"'>"+omc_name+"-"+cvo_data[z].cvo_name+"</OPTION>";
		}
	}
}

//Construct Customer Type html
var custdatahtml = "<OPTION VALUE='0'>SELECT</OPTION>";
if(cvo_data.length>0) {
	for(var z=0; z<cvo_data.length; z++){
		if(cvo_data[z].cvo_cat==1 && cvo_data[z].deleted==0 && cvo_data[z].cvo_name != "UJWALA")
			custdatahtml=custdatahtml+"<OPTION VALUE='"+cvo_data[z].id+"'>"+cvo_data[z].cvo_name+"</OPTION>";
	}
}

//Construct bank html
var tarbid=0;
var starbid=0;
var bankdatahtml = "<OPTION VALUE='0'>SELECT</OPTION>";
if(bank_data.length>0) {
	var bc;
	for(var b=0; b<bank_data.length; b++){
		bc = bank_data[b].bank_code;
		if((bc != "LOAN") && (bank_data[b].deleted == 0)){
			if(bc=="TAR ACCOUNT") {
				bc = "LOAD ACCOUNT";
				tarbid = bank_data[b].id;				
			}
			if(bc=="STAR ACCOUNT") {
				bc = "SV/TV ACCOUNT";
				starbid = bank_data[b].id;				
			}
			bankdatahtml=bankdatahtml+"<OPTION VALUE='"+bank_data[b].id+"'>"+bc+" - "+bank_data[b].bank_acc_no+"</OPTION>";
		}
	}
}
document.getElementById("bankSpan").innerHTML = "<select name='nbid' id='nbid' style='width:100px;' class='form-control input_field select_dropdown sadd freez'>"+bankdatahtml+"</select>";


/*function showDNFormDialog() {
	var check = checkDayEndDateForAdd(dedate,a_created_date,effdate);
	if(check==true) {
		document.getElementById('myModal').style.display = "block";
		document.getElementById("save_data").disabled=true;
	}else{
		document.getElementById("dialog-1").innerHTML = "Please Submit Previous DayEnd inorder to add todays data.";
		alertdialogue();
		//alert("Please Submit Previous DayEnd inorder to add todays data.")
	}
}
*/

function showDNFormDialog() {
	document.getElementById('myModal').style.display = "block";
	document.getElementById("save_data").disabled=true;
	pushMenu();
}


function closeDNFormDialog() {
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

function selectSaleType() {
	$(':radio:not(:checked)').attr('disabled', false);
	document.getElementById("save_data").disabled=true;
}
	
//Process page data
var rowpdhtml = "";
if(page_data.length>0) {
	var bname="";
	for(var z=page_data.length-1; z>=0; z--){
		var ed = new Date(page_data[z].note_date);

		var cvid = page_data[z].cvo_id;
		var vendorName;
		var cvo_cat = -1;
		if(cvid>0) {
			for(var c=0; c<cvo_data.length; c++){
				if(cvid == cvo_data[c].id) {
					cvo_cat = cvo_data[c].cvo_cat;
				}
			}			
			if((cvo_cat == 0) || (cvo_cat == 1))
				vendorName = getCustomerName(cvo_data,page_data[z].cvo_id);
			else if(cvo_cat == 2)
				vendorName = omc_name + "-" + getCustomerName(cvo_data,page_data[z].cvo_id);
		}
				
		var bname = getBankName(bank_data,page_data[z].bank_id);
		
		var del = "<td align='center'><img src='images/delete.png' onclick='deleteItem("+page_data[z].id+","+page_data[z].note_date+")'></td>";
		if(page_data[z].deleted!=0){
			del="<td>Transaction cancelled</td>"
		}
		if(bname == undefined)
			bname="NA";
		
		var bid = page_data[z].bank_id;
		if(bid == parseInt(tarbid)) {
			bname = "LOAD ACCOUNT";
		}
		if(bid == parseInt(starbid)) {
			bname = "SV/TV ACCOUNT";
		}		
		var spd = "";
		var prodId = page_data[z].prod_code;
		if(prodId < 100) {
			spd = fetchProductDetails(cat_cyl_types_data, prodId);
		} else {
			spd = fetchARBProductDetails(cat_arb_types_data, prodId);
		}
		if((page_data[z].debit_note_type!=1)  && (page_data[z].deleted == 0))
			rowpdhtml = rowpdhtml + "<tr valign='top'><td><a href='javascript:showInvoice("+page_data[z].id+","+page_data[z].note_date+")'>"+page_data[z].sid+"</td>";
		else
			rowpdhtml = rowpdhtml + "<td>"+page_data[z].sid+"</td>";

		rowpdhtml = rowpdhtml + "<td>"+ed.getDate()+"-"+months[ed.getMonth()]+"-"+ed.getFullYear()+"</td>";
		rowpdhtml = rowpdhtml + "<td>"+page_data[z].ref_no+"</td>";
		rowpdhtml = rowpdhtml + "<td>"+vendorName+"</td>";
		rowpdhtml = rowpdhtml + "<td>"+spd+"</td>";
		rowpdhtml = rowpdhtml + "<td>"+page_data[z].gstp+"</td>";
		rowpdhtml = rowpdhtml + "<td>"+page_data[z].taxable_amount+"</td>";
		rowpdhtml = rowpdhtml + "<td>"+page_data[z].igst_amount+"</td>";
		rowpdhtml = rowpdhtml + "<td>"+page_data[z].cgst_amount+"</td>";
		rowpdhtml = rowpdhtml + "<td>"+page_data[z].sgst_amount+"</td>";
		rowpdhtml = rowpdhtml + "<td>"+page_data[z].debit_amount+"</td>";
		rowpdhtml = rowpdhtml + "<td>"+bname+"</td>";
		rowpdhtml = rowpdhtml + "<td>"+nreasons[page_data[z].nreasons]+"</td>";
		rowpdhtml = rowpdhtml + del;
		rowpdhtml = rowpdhtml + "</tr>";
	}
}
document.getElementById('m_data_table_body').innerHTML = rowpdhtml;

function saveData(obj){
	var formobj = document.getElementById('data_form');

	var ems = "";
	if(document.getElementById("epid") != null){
		var nidate = formobj.ndate.value.trim();
		var crefno = formobj.cinvno.value.trim();
		var cvtype = formobj.crtype.selectedIndex;
		var cvoid = formobj.cvo_id.selectedIndex;

		if(checkDateFormate(nidate)) {
			var ved = dateConvert(nidate);
			formobj.ndate.value = ved;
			nidate=ved;
		}
		
		var item = formobj.epid.selectedIndex;
		var egst = formobj.egst.selectedIndex;
		var tamt = formobj.tamt.value.trim();
		var igst = formobj.igsta.value;
		var cgst = formobj.cgsta.value;
		var sgst = formobj.sgsta.value;
		var camt = formobj.camt.value;

		var nbid = formobj.nbid.selectedIndex;
		var nreason = formobj.nreason.selectedIndex;
		
		ems = validateEntries(nidate,cvtype,crefno,cvoid,item,egst,tamt,igst,cgst,sgst,camt,nbid,nreason);
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
	}

	var bc;
	var tflag = 0;
	if(formobj.crtype.selectedIndex == 1) {
		var invamt = formobj.camt.value;
		var bid = formobj.nbid.value;
		var bankcb = 0;
		var bname = "BANK ACCOUNT";
		var odbflag = 0;
		if(bank_data.length>0) {
			for(var y=0; y<bank_data.length; y++) {
				if((bank_data[y].id == bid) && (bank_data[y].deleted == 0)){
					bname = bank_data[y].bank_code+" - "+ bank_data[y].bank_acc_no;
					bc = bank_data[y].bank_code;

					if(bc != "OVER DRAFT"){
						bankcb = bank_data[y].acc_cb;
						odbflag = 1;
					}else{
						if(bank_data[y].od_and_loan_acceptable_bal == "")
							odbflag = 2;
						else {
							var val = parseFloat(bank_data[y].acc_cb) - parseFloat(invamt);
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
			if(parseFloat(invamt) > parseFloat(bankcb)) {
				tflag = 1;
				if((bc == "TAR ACCOUNT") || (bc == "STAR ACCOUNT")){
					if(bc == "TAR ACCOUNT"){
						$("#myDialogText").text("YOUR LOAD ACCOUNT HAVE NO SUFFICIENT BALANCE TO COMPLETE THIS TRANSACTION. DO YOU WANT TO CONTINUE?");
						confirmTxtDialogue(formobj);
					}else if(bc == "STAR ACCOUNT"){
						$("#myDialogText").text("YOUR SV/TV ACCOUNT HAVE NO SUFFICIENT BALANCE TO COMPLETE THIS TRANSACTION. DO YOU WANT TO CONTINUE?");
						confirmTxtDialogue(formobj);
					}
				}else {
					document.getElementById("dialog-1").innerHTML = "YOUR SELECTED "+bname+" HAVE NO SUFFICIENT BALANCE TO COMPLETE THIS TRANSACTION. PLEASE CHECK AND ADD AGAIN";
					alertdialogue();
					return;
				}	
			}
		}else if(odbflag == 3){
			document.getElementById("dialog-1").innerHTML = "YOUR SELECTED "+bname+" HAVE NO SUFFICIENT BALANCE TO COMPLETE THIS TRANSACTION. PLEASE CHECK AND ADD AGAIN";
			alertdialogue();
			return;
		}
	}

//	if(!((bc == "TAR ACCOUNT") || (bc == "STAR ACCOUNT"))){
	if(tflag == 0){
		var selval = document.getElementById("cvo_id").value;
		if(cvo_data.length>0) {
			for(var z=0; z<cvo_data.length; z++){
				if((cvo_data[z].deleted == 0) && (cvo_data[z].id == selval)) {
					formobj.cvo_cat.value = cvo_data[z].cvo_cat;
				}
			}
		}
		
		var objId = obj.id;
		document.getElementById(objId).disabled = true;	
		formobj.submit();
	}
}

/*
function checkForNegativeBalances(formobj) {
	var invamt = formobj.camt.value;
	var bid = formobj.nbid.value;
	var bankcb = 0;
	var bname = "BANK ACCOUNT";
	
	if(bank_data.length>0) {
		for(var y=0; y<bank_data.length; y++) {
			if((bank_data[y].id == bid) && (bank_data[y].deleted == 0)){
				bankcb = bank_data[y].acc_cb;
				bname = bank_data[y].bank_code+" - "+ bank_data[y].bank_acc_no;
				break;
	    	}
	    }
	}
	
	if(parseFloat(invamt) > parseFloat(bankcb)) {
		return "Your selected "+bname+" have no sufficient balance to complete this transaction. Please Check and add again";
	}else return true;
	
}
*/
function validateEntries(nidate,cvtype,crefno,cvoid,item,egst,tamt,igst,cgst,sgst,camt,nbid,nreason) {
	
	var formobj = document.getElementById('data_form');
	var errmsg = "";
	
	var vd = isValidDate(nidate);
	var vfd = ValidateFutureDate(nidate);	
	var chkd = checkdate(nidate);
	
	if(!(nidate.length>0))
		errmsg = errmsg+"Please enter DEBIT NOTE DATE<br>";
	else if (vd != "false")
		errmsg = errmsg +vd+"<br>";
	else if(validateDayEndAdd(nidate,dedate)){
        errmsg = "DEBIT NOTE DATE should be after DayEndDate<br>";
        return errmsg;
	}else if(validateEffectiveDateForCVO(nidate,effdate)){
        errmsg = "DEBIT NOTE DATE should be after Effective Date that you have entered While First Time Login<br>";
        return errmsg;
	}else if(chkd != "true")
		errmsg = errmsg+chkd+"<br>";
	else if (vfd != "false")
		errmsg = errmsg +"DEBIT NOTE "+vfd+"<br>";
/*	else {
		var lastTrxnDateCheck = checkforLastTrxnDate(dedate,a_created_date,nidate,trdate);
		if(lastTrxnDateCheck != "false") {
			return lastTrxnDateCheck;
		}
	}*/
	
	if(!(cvtype>0))
		errmsg = errmsg+"Please select DEBIT NOTE TYPE. <br>";

	if(!(crefno.length>0))
		errmsg = errmsg+"Please enter REFERENCE NUMBER <br>";

	if(!(cvoid>0))
		errmsg = errmsg+"Please select VENDOR/CUSTOMER NUMBER<br>";

	if($("input[type=radio][name=stype]:checked").length <= 0) {
		errmsg = errmsg + "Please select SALE TYPE<br>";			
	}  

	if(!(item>0))
		errmsg = errmsg+"Please select PRODUCT NAME<br>";
	
	if(!(egst>=0))
		errmsg = errmsg+"Please select GST% <br>";

	if(!(tamt.length>0))
		errmsg = errmsg+"Please enter TAXABLE AMOUNT<br>";
	else if(validateDot(tamt))
		errmsg = errmsg+"TAXABLE AMOUNT must contain atleast one number. <br>";
	else if(!(validateDecimalNumberMinMax(tamt,0,100000)))
		errmsg = errmsg + "Please enter valid TAXABLE AMOUNT and it must be in between 0 and 1,00,000 <br>";
	else
		formobj.tamt.value=round(parseFloat(tamt),2);
		
	if(!(validateDecimalNumberMinMax(igst,-1,10000000)))
		errmsg = errmsg + "Invalid TRANSACTION AMOUNT.<br>";
	if(!(validateDecimalNumberMinMax(cgst,-1,10000000)))
		errmsg = errmsg + "Invalid SALE AMOUNT.<br>";
	if(!(validateDecimalNumberMinMax(sgst,-1,10000000)))
		errmsg = errmsg + "Invalid GST..<br>";
	if(!(validateDecimalNumberMinMax(camt,0,10000000)))
		errmsg = errmsg + "Invalid GST.. <br>";
	
	if(cvtype!=2){
		 if(!(nbid>0))
			errmsg = errmsg+"Please select CREDITED/DEBITED TO BANK<br>";
	}
	if(!(nreason>0))
		errmsg = errmsg+"Please select debit note REASON <br>";
	
	return errmsg;
}


/*function deleteItem(iid,invdate) {
	var flag=validateDayEndForDelete(invdate,dedate);
	if(flag) {
		if (confirm("Are you sure you want to delete?") == true) {
			var formobj = document.getElementById('m_data_form');
			formobj.actionId.value = "5543";
			formobj.itemId.value = iid;
			formobj.submit();
		}
	}else {
		document.getElementById("dialog-1").innerHTML = "This record can't be deleted as you have submitted and verified your DAYEND calculations succuessfully... ";
		alertdialogue();
		//alert("This record can't be deleted as you have submitted and verified your DAYEND calculations succuessfully... ");
	}
}*/

function deleteItem(iid,invdate){
	var flag=validateDayEndForDelete(invdate,dedate);
	if(flag){
	 $("#myDialogText").text("Are You Sure You Want To Delete?");
	 var formobj = document.getElementById('m_data_form');
	 confirmDialogue(formobj,5543,iid);
	}else {
		document.getElementById("dialog-1").innerHTML = "This record can't be deleted as you have submitted and verified your DAYEND calculations succuessfully... ";
		alertdialogue();
	}
}

function setVC(){
	var formobj = document.getElementById('data_form');
	
	var si = formobj.crtype.selectedIndex;
	if(si==0) {
		document.getElementById("vSpan").innerHTML = "<select id='cvo_id' name='cvo_id' class='form-control input_field select_dropdown sadd' onchange='selectSaleType()'><OPTION VALUE='0'>SELECT</OPTION></select>";
		 var nbidv=document.getElementById("nbid").options;
			enableSelect(nbidv,nbidv.length);
	} else if (si==1) {
		document.getElementById("vSpan").innerHTML = "<select id='cvo_id' name='cvo_id' class='form-control input_field select_dropdown sadd' onchange='selectSaleType()'>"+vendordatahtml+"</select>";
		 var nbidv=document.getElementById("nbid").options;
			enableSelect(nbidv,nbidv.length);
	} else if (si==2) {
		document.getElementById("vSpan").innerHTML = "<select id='cvo_id' name='cvo_id' class='form-control input_field select_dropdown sadd' onchange='selectSaleType()'>"+custdatahtml+"</select>";
		 formobj.nbid.selectedIndex=0;
		 var nbidv=document.getElementById("nbid").options;
		disableSelect(nbidv,nbidv.length);
	}
	else{
		 var nbidv=document.getElementById("nbid").options;
		enableSelectSelect(nbidv,nbidv.length);
	}
	document.getElementById("cvname").style.display="block";
}

var cgstv="";
var igstv="";
function calculateAmounts(){
	var cvogstin;	
	var cvoreg;var res;
	var formobj = document.getElementById('data_form');
	if(formobj.epid.selectedIndex > 0){
		if(formobj.egst.selectedIndex > 0){
			if(formobj.tamt.value.trim() != ""){
				
    			var cvtype = formobj.crtype.selectedIndex;    			
    			if(cvtype > 0) {
    				if(formobj.cvo_id.selectedIndex >0) {
    					for(var d=0;d< cvo_data.length;d++){
    						if(parseInt(formobj.cvo_id.value) == cvo_data[d].id) {
    							cvogstin = cvo_data[d].cvo_tin;
    							cvoreg = cvo_data[d].is_gst_reg;
    						}
    					}
    				}else {
    					document.getElementById("dialog-1").innerHTML = "please select CUSTOMER / VENDOR NAME";
    					alertdialogue();
    					//alert("please select CUSTOMER / VENDOR NAME");
    					return;
    				}
    			}else {
    				document.getElementById("dialog-1").innerHTML = "please select DEBIT NOTE TYPE";
    				alertdialogue();
    				//alert("please select DEBIT NOTE TYPE");
    				return;
    			}
    			var selectedomcName= document.getElementById("cvo_id").value;
    			var parsedOmcVal=parseInt(selectedomcName);
    			var crefno = formobj.cinvno.value.trim();
    			var dinv_date = formobj.ndate.value.trim();
    			var ptypev = formobj.stype.value;

    			res=checkRefNoAndProduct(formobj,cvtype,crefno,dinv_date,parsedOmcVal);
    			if(res==false)
    				return;
	
    			if(cvoreg==1) {
    				var dstcode = dealergstin.substr(0, 2);
    				var cvstcode = cvogstin.substr(0, 2);
    				var ptypecheck = "";
    				if(cvstcode == dstcode)
    					ptypecheck = "lp"; 
    				else 
    					ptypecheck = "isp";
    			}else if(cvoreg==2){
    				if(cgstv!=0)
    					ptypecheck = "lp"; 
    				else
    					ptypecheck = "isp"; 			
    			}

    			var pid = formobj.epid.selectedIndex;
    			var tamtv = formobj.tamt.value;
    			var gstpsi = formobj.egst.selectedIndex;
    			var gstpv = formobj.egst.options[gstpsi].value;
//    			var nbid = formobj.nbid.selectedIndex;
//    			var nreason = formobj.nreason.selectedIndex;
    			
    			var ems = validateCalcValues(pid,cvtype,tamtv,gstpsi,ptypecheck,ptypev);
    			if(ems.length>0) {
    				document.getElementById("dialog-1").innerHTML = ems;
    				alertdialogue();
    				//alert(ems);
    				return;
    			}			
    			var gstav = ((tamtv * gstpv)/100).toFixed(2);
    			if(ptypev=='lp') {
    				formobj.igsta.value=0.00;
    				formobj.sgsta.value=((gstav)/2).toFixed(2);
    				formobj.cgsta.value=((gstav)/2).toFixed(2);
    				formobj.camt.value= (+(tamtv) + +(formobj.cgsta.value) + +(formobj.sgsta.value)).toFixed(2);
    			} else if (ptypev=='isp') {
    				formobj.igsta.value=(gstav);
    				formobj.sgsta.value=0.00;
    				formobj.cgsta.value=0.00;
    				formobj.camt.value= (+(tamtv) + +(formobj.igsta.value)).toFixed(2);
    			}
	
    			$(':radio:not(:checked)').attr('disabled', true);
    			document.getElementById("save_data").disabled=false;
    			restrictChangingAllValues(".freez");
    		}else{
    			document.getElementById("dialog-1").innerHTML = "Please enter Taxable Amount";
    			alertdialogue();
    		}
		}else{
			document.getElementById("dialog-1").innerHTML = "Please Select GST %";
			alertdialogue();
  			}
    	}else {
    		document.getElementById("dialog-1").innerHTML = "Please Select product";
    		alertdialogue();	
    		}	
		}

function validateCalcValues(pid,cvtype,tamtv,gstpsi,ptypecheck,ptypev) {
	var errmsg = "";
	var formobj = document.getElementById('data_form');

	if(!pid>0)
		errmsg = errmsg+"Please select PRODUCT<br>";
	
	if(cvtype==1) {
		if($("input[type=radio][name=stype]:checked").length <= 0) {
			errmsg = errmsg + "Please select PURCHASE TYPE<br>";
		}
		if(($("input[type=radio][name=stype]:checked").length > 0)&&(ptypecheck != ptypev))
			errmsg = errmsg + "The PURCHASE TYPE you have selected might be wrong.Please check it again<br>";
	}else if(cvtype==2){
		if($("input[type=radio][name=stype]:checked").length <= 0) {
			errmsg = errmsg + "Please select SALE TYPE<br>";
		}
		if(($("input[type=radio][name=stype]:checked").length > 0)&&(ptypecheck != ptypev))
			errmsg = errmsg + "The SALE TYPE you have selected might be wrong.Please check it again<br>";
	}

	if(!(tamtv.length>0))
		errmsg = errmsg+"Please enter TAXABLE AMOUNT<br>";
	else if(validateDot(tamtv))
		errmsg = errmsg+"TAXABLE AMOUNT must contain atleast one number. <br>";
	else if(!(validateDecimalNumberMinMax(tamtv,0,100000)))
		errmsg = errmsg + "Please enter valid TAXABLE AMOUNT and it must be in between 0 and 1,00,000 <br>";
	else
		formobj.tamt.value=round(parseFloat(tamtv),2);

	if (!(gstpsi > 0))
		errmsg = errmsg + "Please Select GST % <br>";
	
	return errmsg;	
}

//For Checking Reference Number
function checkRefNoAndProduct(formobj,cvtype,crefno,dinv_date,parsedOmcVal) {
	var cprval="";var arbprval="";var dsval="";
	var csval="";var arbsval=""; var puRvalue="";var salRval="";var typeflag;var res;
	var parsedOmcValue=parsedOmcVal;
	
	
	if(cvtype > 0) {
		if(crefno.length >0) {
			if(cvtype==1) {   //receive
				for(var l=0;l<pi_data.length;l++) {
					if(pi_data[l].inv_ref_no==crefno){
						cprval=crefno;
					}
				}
				for(var m=0;m<arb_pi_data.length;m++) {
					if(arb_pi_data[m].inv_ref_no==crefno){
						arbprval=crefno;
					}
				}
				for(var n=0;n<purchase_return.length;n++) {
					if(purchase_return[n].sid==crefno){
						puRvalue=crefno;
					}
				}
				
				/*if((cprval=="") && (arbprval=="") && (puRvalue=="")) {
					document.getElementById("dialog-1").innerHTML = "Invalid Reference Number.Please Check it again";
					alertdialogue();
					//alert("Invalid Reference Number.Please Check it again");
					return false;
				}else*/ if(cprval != "") {
					typeflag = "cp";
					res=checkProductInPurInv(formobj,pi_data,cprval,typeflag,parsedOmcValue,dinv_date);
					return res;
				}else if(puRvalue != "") {
					typeflag = "cpR";
					res=checkProductInPurInv(formobj,purchase_return,puRvalue,typeflag,parsedOmcValue,dinv_date);
					return res;
				}				
				else if(arbprval !="") {
					typeflag = "arbp";
					res=checkProductInPurInv(formobj,arb_pi_data,arbprval,typeflag,parsedOmcValue,dinv_date);
					return res;
				}else{ return true; }
			}else if(cvtype==2)	{ //issue
				var invno="";
				for(var i=0;i<drs_data.length;i++) {
					if(drs_data[i].customer_id!=0){
					invno=drs_data[i].sr_no;
					if(invno==crefno){
						dsval=crefno;
						}
					}
				}
				for(var j=0;j<crs_data.length;j++) {
					if(crs_data[j].customer_id!=0){
					invno=crs_data[j].sr_no;
					if(invno==crefno){
						csval=crefno;
						}
					}
				}
				for(var k=0;k<arbs_data.length;k++) {
					if(arbs_data[k].customer_id!=0){
					invno=arbs_data[k].sr_no;
					if(invno==crefno) {
						arbsval=crefno;
						}
					}
				}
				for(var l=0;l<sr_data.length;l++) {
					invno=sr_data[l].sr_no;
					if(invno==crefno) {
						salRval=crefno;
						}
				}
				
				if((dsval=="") && (csval=="") && (arbsval=="")&& (salRval=="")) {
					document.getElementById("dialog-1").innerHTML = "Invalid Reference Number.Please Check it again";
					alertdialogue();
					//alert("Invalid Reference Number.Please Check it again");
					return false;
				}else if(dsval != "") {
					typeflag = "ds";
					res=checkProductInSaleInv(formobj,drs_data,dsval,typeflag,parsedOmcValue,dinv_date);
					return res;
				}else if(csval != "") {
					typeflag = "cs";
					res=checkProductInSaleInv(formobj,crs_data,csval,typeflag,parsedOmcValue,dinv_date);
					return res;
				}else if(arbsval != "") {
					typeflag = "arbs";
					res=checkProductInSaleInv(formobj,arbs_data,arbsval,typeflag,parsedOmcValue,dinv_date);
					return res;
				}
				else if(salRval != "") {
					typeflag = "sr";
					res=checkProductInSaleInv(formobj,sr_data,salRval,typeflag,parsedOmcValue,dinv_date);
					return res;
				}else {return true;}
			}	
		}else {
			document.getElementById("dialog-1").innerHTML = "Please enter Reference Number. <br>";
			alertdialogue();
			//alert("Please enter Reference Number. <br>");
			return false;
		}
	}else {
		document.getElementById("dialog-1").innerHTML = "Please select CREDIT NOTE TYPE. <br>";
		alertdialogue();
		//alert("Please select CREDIT NOTE TYPE. <br>");
		return false;
	}
}

//For Checking PURCHASES(refill and ARB) product present for a Reference Number
function checkProductInPurInv(formobj,pur_data,prval,typeflag,parsedOmcValue,dinv_date) {
	var flag="NO";
	var pide = formobj.epid;
	var iprodar = new Array();	
	var omcId,invDate;
	var dnInvDate = (new Date(dinv_date)).getTime();

	for (var i=0;i<pur_data.length;i++) {
		if(((pur_data[i].inv_ref_no == prval) || (pur_data[i].sid == prval)) && (pur_data[i].deleted == 0)) {
			flag="YES";
			if(typeflag=="cp") {
				iprodar.push(pur_data[i].prod_code);
				omcId = pur_data[i].omc_id;
				invDate = pur_data[i].inv_date;
			}else if(typeflag=="cpR"){
				for (var d=0;d<pur_data[i].detailsList.length;d++) {				
					iprodar.push(pur_data[i].detailsList[d].prod_code);
				}
				omcId=pur_data[i].cvo_id;
				invDate = pur_data[i].inv_date;
			}else if(typeflag=="arbp") {
				iprodar.push(pur_data[i].arb_code);
				omcId=pur_data[i].vendor_id;
				invDate = pur_data[i].inv_date;
				igstv=pur_data[i].igst_amount;
				cgstv=pur_data[i].cgst_amount;
			}
		}
	}
		
	if(flag=="YES") {
		if (pide.selectedIndex > 0) {
			if(omcId!=parsedOmcValue){
				document.getElementById("dialog-1").innerHTML = "Please SELECT Valid VENDOR";
				alertdialogue();
				return false;
			}else if(!iprodar.includes(parseInt(pide.value))) {
				document.getElementById("dialog-1").innerHTML = "The PRODUCT you have selected is not under the provided  INVOICE NUMBER ";
				alertdialogue();
				return false;
			}else if(dnInvDate < invDate) {
				document.getElementById("dialog-1").innerHTML = "Please SELECT Valid DEBIT NOTE DATE ";
				alertdialogue();
				return false;
			}else {
				formobj.ref_date.value = invDate;
				return typeflag;
				//return true;
			}
		}else {
			document.getElementById("dialog-1").innerHTML = "Please Select PRODUCT and Click CALCULATE";
			alertdialogue();
			return false;
		}
	}else if(flag == "NO") {
		document.getElementById("dialog-1").innerHTML = "Please enter valid PURCHASE INVOICE NUMBER ";
		alertdialogue();
		return false;
	}
}

//For Checking SALES(refill and ARB) product present for a Reference Number
function checkProductInSaleInv(formobj,Sale_data,sval,typeflag,custVal,dinv_date) {
	var flag="NO";
	var pide = formobj.epid;
	var iprodar = new Array();	
	var custId, invDate;
	var dnInvDate = (new Date(dinv_date)).getTime();
	
	for (var i=0;i<Sale_data.length;i++) {
		if(((Sale_data[i].sr_no) == sval) && (Sale_data[i].deleted == 0)) {
			flag="YES";
/*			for (var j=0;j<Sale_data[i].detailsList.length;j++) {
				if(typeflag=="ds") {
					iprodar.push(Sale_data[i].detailsList[j].prod_code);
					custId = Sale_data[i].customer_id ;
				}else if(typeflag=="cs") {
					iprodar.push(Sale_data[i].detailsList[j].prod_code);
					custId = Sale_data[i].customer_id ;
				}else if(typeflag=="arbs") {
					iprodar.push(Sale_data[i].detailsList[j].prod_code);
					custId = Sale_data[i].customer_id ;
				}
			}*/
			
			for (var j=0;j<Sale_data[i].detailsList.length;j++) {
				if((typeflag=="ds") || (typeflag=="cs") || (typeflag=="arbs")) {
					iprodar.push(Sale_data[i].detailsList[j].prod_code);
					custId = Sale_data[i].customer_id ;
					invDate = Sale_data[i].si_date;
					igstv=Sale_data[i].detailsList[j].igst_amount;
					cgstv=Sale_data[i].detailsList[j].cgst_amount;
				}
				else if((typeflag=="sr")){
					iprodar.push(Sale_data[i].detailsList[j].prod_code);
					custId = Sale_data[i].cvo_id ;
					invDate = Sale_data[i].inv_date;
					igstv=Sale_data[i].detailsList[j].igst_amount;
					cgstv=Sale_data[i].detailsList[j].cgst_amount;
				}
			}
		}
	}
	if(flag=="YES") {
		if (pide.selectedIndex > 0) {
			if(custId != custVal) {
				document.getElementById("dialog-1").innerHTML = "Please SELECT Valid CUSTOMER";
				alertdialogue();
				return false;
			}else if(!iprodar.includes(parseInt(pide.value))) {
				document.getElementById("dialog-1").innerHTML = "The PRODUCT you have selected is not under the provided SALE INVOICE NUMBER ";
				alertdialogue();
				return false;
			}else if(dnInvDate < invDate) {
				document.getElementById("dialog-1").innerHTML = "Please SELECT Valid DEBIT NOTE DATE ";
				alertdialogue();
				return false;
			}else {
				formobj.ref_date.value = invDate;
				return typeflag;
				//return true;
			}	
		}else {
			document.getElementById("dialog-1").innerHTML = "Please Select PRODUCT and Click CALCULATE ";
			alertdialogue();
			return false;
		}
	}else if(flag == "NO") {
		document.getElementById("dialog-1").innerHTML = "Please enter valid SALE INVOICE NUMBER ";
		alertdialogue();
		return false;							
	}
}



function showInvoice(inv_id,si_date){
	popitup(inv_id,si_date)
}

function popitup(inv_id,si_date) { 
	var w=window.open("PopupControlServlet?actionId=989&sitype=11&sid="+inv_id +"&si_date="+si_date,null,"height=400,width=1024,status=yes,toolbar=no,menubar=no,location=no");
	w.print();
	return false; 
}
