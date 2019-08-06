function fetchBalanceSheet(){
   	var formobj = document.getElementById('bs_form');
	var spid = formobj.sfy.selectedIndex;
	var year = formobj.sfy.options[spid].value;

	if(!spid>0) {
		document.getElementById("dialog-1").innerHTML = "PLEASE SELECT FINANCIAL YEAR";
		alertdialogue();
		//alert("PLEASE SELECT FINANCIAL YEAR");
		return;
	}
	var cdate=new Date();
	var cmonth=cdate.getMonth()+1;
	var cyear = cdate.getFullYear();
	
	if((parseInt(year)) > cyear) {
		document.getElementById("dialog-1").innerHTML = "AGENCY BALANCE SHEET can't be future";
		alertdialogue();
		//alert("AGENCY BALANCE SHEET can't be future");
		document.getElementById("bs_display_div").style.display = "none";
		return false;
	}else{
		formobj.submit();
		return true;
	}
}

function takeAction(aid,rid){
   	var formobj = document.getElementById('bs_form');
   	if(aid==2)
   		formobj.actionId.value="8553";
   	else if (aid==3)
   		formobj.actionId.value="8554";
   	else if (aid==4)
   		formobj.actionId.value="8555";
   	formobj.recId.value=rid;
	formobj.submit();
}

if(sfy > 0) {

	var rstatus = bs_data.status;
	var formobj = document.getElementById("bs_data_form");
	document.getElementById("bs_display_div").style.display="block";
	document.getElementById("fySpan").innerHTML = bs_data.fyv;
	document.getElementById("bssSpan").innerHTML = bsstatus[rstatus];
	
	if(rstatus==1) {
		document.getElementById("bsProcessSpan").innerHTML = "<input type='button' value='PROCESS' onclick='takeAction(2,"+bs_data.id+")'>";
	} else if (rstatus==5){
		document.getElementById("bsProcessSpan").innerHTML = "<input type='button' value='PROCESS' onclick='takeAction(2,"+bs_data.id+")'>";
		document.getElementById("infoSpan").innerHTML = bs_data.remarks;
	} else if(rstatus==3){
		
		document.getElementById("bs_data_div").style.display="block";
		//show save & submit buttons
		document.getElementById("bsSaveSpan").innerHTML = "<input type='button' value='RE-PROCESS' onclick='takeAction(3,"+bs_data.id+")'>";
		document.getElementById("bsSubmitSpan").innerHTML = "<input type='button' value='SUBMIT' onclick='takeAction(4,"+bs_data.id+")'>";
		setAllValues(formobj);
		formobj.cacb.value=calculateCAB(formobj);
		var fytla = calculateFYTL(formobj);
		var fytaa = calculateFYTA(formobj);
		formobj.fytl.value= Math.round(fytla);
		formobj.fyta.value= Math.round(fytaa);
		
	} else if(rstatus==4){
		document.getElementById("bs_data_div").style.display="block";
		setAllValues(formobj);
		formobj.cacb.value=bs_details.cacb_amt;
		formobj.fytl.value=bs_details.fytl_amt;
		formobj.fyta.value=bs_details.fyta_amt
	} 
}

function setAllValues(formobj){
	formobj.caob.value=bs_details.caob_amt;
	formobj.cani.value=bs_details.cani_amt;
	formobj.canw.value=bs_details.canw_amt;
	formobj.plnp.value=bs_details.plnp_amt;
	formobj.faob.value=bs_details.faob_amt;
	formobj.fanp.value=bs_details.fanp_amt;
	formobj.fand.value=bs_details.fadp_amt;
	formobj.facb.value=bs_details.facb_amt;
	formobj.clsc.value=bs_details.clsc_amt;
	formobj.casd.value=bs_details.casd_amt;
	formobj.sllb.value=bs_details.sltb_amt;
	formobj.ulfr.value=bs_details.ulfr_amt;
	formobj.prov.value=bs_details.prov_amt;
	formobj.cala.value=bs_details.cala_amt;
	formobj.cach.value=bs_details.cach_amt;
	formobj.cadp.value=bs_details.cadp_amt;
	formobj.cars.value=bs_details.cars_amt;
	formobj.cabc.value=bs_details.cabc_amt;
	formobj.cacs.value=bs_details.cacs_amt;
}

function calculateCAB(fo){
	var cabv = "0.00";
	cabv = (+bs_details.cacb_amt) + (+bs_details.plnp_amt);
	return cabv;
}

function calculateFYTL(fo){
	var tlv = "0.00";
	tlv = (+fo.cacb.value) + (+fo.clsc.value) + (+fo.sllb.value) + (+fo.ulfr.value) + (+fo.prov.value);
	return tlv;
}

function calculateFYTA(fo){
	var tav = "0.00";
	tav = (+fo.facb.value) + (+fo.casd.value) + (+fo.cala.value) + (+fo.cach.value) + (+fo.cadp.value) + (+fo.cars.value) + (+fo.cabc.value) + (+fo.cacs.value);  
	return tav;
}