function fetchPandLAccount(){	
   	var formobj = document.getElementById('pl_form');
	var spid = formobj.sfy.selectedIndex;
	var year = formobj.sfy.options[spid].value;

	if(!spid>0) {
		document.getElementById("dialog-1").innerHTML = "Please Select FINANCIAL YEAR";
		alertdialogue();
		//alert("Please Select FINANCIAL YEAR");
		return;
	}
	var cdate=new Date();
	var cmonth=cdate.getMonth()+1;
	var cyear = cdate.getFullYear();
	
	if((parseInt(year)) > cyear ) {
		document.getElementById("dialog-1").innerHTML = "FINANCIAL YEAR Cannot Be Future Year";
		alertdialogue();
		//alert("FINANCIAL YEAR Can't Be Future Year");
		document.getElementById("pl_display_div").style.display="none";
		return false;
	}else {
		formobj.submit();
		return true;
	}
}

function takeAction(aid,rid,flag) {
   	var formobj = document.getElementById('pl_form');
   	var osvalue=formobj.os.value;
   	var psvalue=formobj.ps.value;
   	var csvalue=formobj.cs.value;
   	var ssvalue=formobj.ss.value;
   	var otsvalue=formobj.ots.value;

   	if(aid==2)
   		formobj.actionId.value="8543";
   	else if (aid==3)
   		formobj.actionId.value="8544";
   	else if (aid==4)
   		formobj.actionId.value="8545";

   	var ValidationExpression="\d{10}";

   	if(!(validateDecimalNumberMinMax(osvalue,-1,10000000.00))){
   		document.getElementById("dialog-1").innerHTML = "Please Enter Valid OPENING STOCK";
   		alertdialogue();
   		//alert("Please Enter Valid OPENING STOCK")
   	}else if(!(validateDecimalNumberMinMax(psvalue,-1,10000000.00))){
   		document.getElementById("dialog-1").innerHTML = "Please Enter Valid PURCHASES";
   		alertdialogue();
   		//alert("Please Enter Valid PURCHASES")
   	}else if(!(validateDecimalNumberMinMax(csvalue,-1,10000000.00))){
   		document.getElementById("dialog-1").innerHTML = "Please Enter Valid CLOSING STOCK";
   		alertdialogue();
   		//alert("Please Enter Valid CLOSING STOCK")
   	}else if(!(validateDecimalNumberMinMax(ssvalue,-1,10000000.00))){
   		document.getElementById("dialog-1").innerHTML = "Please Enter Valid SALES";
   		alertdialogue();
   		//alert("Please Enter Valid SALES")
   	}else if(!(validateDecimalNumberMinMax(otsvalue,-1,10000000.00))){
   		document.getElementById("dialog-1").innerHTML = "Please Enter Valid OTHER INCOME";
   		alertdialogue();
   		//alert("Please Enter Valid OTHER INCOME")
   	}else if(flag==1 || flag==2) {
   		var retVal = true;
   	}else if(flag==3) {
   		/*var retVal = confirm("Are You  Confirmed To SUBMIT ?");*/
   		$("#myDialogText").text("Are You Sure You Want To SUBMIT?");
   		$("#dialog-confirm").dialog({
   			resizable: false,
   			modal: true,
   			title: "Confirm",
   			height: 150,
   			width: 400,
   			buttons: {
   				"Yes": function () {
   					$(this).dialog('close');
   			   		formobj.recId.value=rid;
   			   		formobj.submit();
   				},
   				"No": function () {
   					$(this).dialog('close');
   					return;
   				}
   			}
   		});
   	}
   		
   	if(retVal === true){
   		formobj.recId.value=rid;
   		formobj.submit();
   		return true;
   	}else{
   		return false;
   	}
}

if(sfy > 0) {
	var rstatus = pal_data.status;
	var formobj = document.getElementById("pl_form");
	document.getElementById("pl_display_div").style.display="block";
	document.getElementById("fySpan").innerHTML = pal_data.fyv;
	document.getElementById("plsSpan").innerHTML = plstatus[rstatus];
	
	if(rstatus==1) {
		document.getElementById("plProcessSpan").innerHTML = "<input type='button' value='PROCESS' onclick='takeAction(2,"+pal_data.id+",1)'>";
	}
	
	if(rstatus==3){
		document.getElementById("pl_data_div").style.display="block";
		//show save & submit buttons
		document.getElementById("plSaveSpan").innerHTML = "<input type='button' value='RE-PROCESS' onclick='takeAction(3,"+pal_data.id+",2)'>";
		document.getElementById("plSubmitSpan").innerHTML = "<input type='button' value='SUBMIT' onclick='takeAction(4,"+pal_data.id+",3)'>";
		//Set GP & NP - when in ready state
		
		formobj.gp.value=(+pal_details.sv_amt + +pal_details.oi_amt) - (+pal_details.osv_amt + +pal_details.pv_amt);
		setAllValues(formobj);
		var npv = calculateNetProfit(formobj);
		formobj.np.value=npv.toFixed(2);
	}
	if(rstatus==4){
		document.getElementById("pl_data_div").style.display="block";
		setAllValues(formobj);
		formobj.gp.value=pal_details.gp_amt;
		formobj.np.value=pal_details.np_amt;
	}
}

function setAllValues(formobj){
	formobj.os.value=pal_details.osv_amt;
	formobj.cs.value=pal_details.csv_amt;
	formobj.ps.value=pal_details.pv_amt;
	formobj.ss.value=pal_details.sv_amt;
	formobj.ots.value=pal_details.oi_amt;
	formobj.fo.value=pal_details.fo_amt;
	formobj.fi.value=pal_details.fi_amt;
	formobj.uc.value=pal_details.uc_amt;
	formobj.ial.value=pal_details.il_amt;
	formobj.stry.value=pal_details.sp_amt;
	formobj.te.value=pal_details.tc_amt;
	formobj.rent.value=pal_details.rn_amt;
	formobj.bp.value=pal_details.bp_amt;
	formobj.sw.value=pal_details.sw_amt;
	formobj.ms.value=pal_details.ms_amt;
	formobj.bc.value=pal_details.bc_amt;
	formobj.dep.value=pal_details.dp_amt;
}

function calculateNetProfit(fo) {
	var npv = "0.00";
	npv = (+formobj.gp.value) - (+pal_details.fo_amt + +pal_details.fi_amt + +pal_details.uc_amt + +pal_details.il_amt + +pal_details.sp_amt +
			 +pal_details.tc_amt + +pal_details.rn_amt + +pal_details.bp_amt + +pal_details.sw_amt + +pal_details.ms_amt + +pal_details.bc_amt + 
			 +pal_details.dp_amt);
	return npv;
}

/*function calculateNetProfit(fo) {
	return ((+formobj.gp.value) - (+pal_details.fo_amt + +pal_details.fi_amt + +pal_details.uc_amt + +pal_details.il_amt + +pal_details.sp_amt +
			 +pal_details.tc_amt + +pal_details.rn_amt + +pal_details.bp_amt + +pal_details.sw_amt + +pal_details.ms_amt + +pal_details.bc_amt + 
			 +pal_details.dp_amt));
}*/