//Construct Category Type html
ctdatahtml = "<OPTION VALUE='-1' selected='selected'>SELECT</OPTION>";
if(cat_types_data.length>0) {
	for(var z=0; z<cat_types_data.length; z++){
		ctdatahtml=ctdatahtml+"<OPTION VALUE='"+cat_types_data[z].id+"'>"+cat_types_data[z].cat_name+"-"+cat_types_data[z].cat_desc+"</OPTION>";
	}
}

if(arb_types_data.length>0) {
	for(var z=0; z<arb_types_data.length; z++){
		//ctdatahtml=ctdatahtml+"<OPTION VALUE='"+arb_types_data[z].id+"'>"+arb_types_data[z].cat_code+"-"+arb_types_data[z].cat_name+"-"+arb_types_data[z].cat_desc+"</OPTION>";
		ctdatahtml=ctdatahtml+"<OPTION VALUE='"+arb_types_data[z].id+"'>"
		+getARBType(arb_types_data[z].prod_code)+" - "+arb_types_data[z].prod_brand+" - "+arb_types_data[z].prod_name
		+"</OPTION>";

	}
}

//Construct Vendor Type html
vendatahtml = "<OPTION VALUE='-1' selected='selected'>SELECT</OPTION>";
if(cvo_data.length>0) {
	for(var z=0; z<cvo_data.length; z++){
//		if(!(cvo_data[z].cvo_cat==2) && cvo_data[z].deleted == 0)  {
//		if(!(cvo_data[z].cvo_cat==2) && (cvo_data[z].deleted == 0) && (cvo_data[z].cvo_name != "UJWALA")) {
		if(!(cvo_data[z].cvo_cat==2) && (cvo_data[z].cvo_name != "UJWALA")) {
			vendatahtml=vendatahtml+"<OPTION VALUE='"+cvo_data[z].id+"'>"+cvo_data[z].cvo_name+"</OPTION>";
		}
	}
}

function showSelLedgerDivs() {
	var selval = document.getElementById("typeofled").value;
	if(selval == "0") {
		document.getElementById("prodwiseLedger").style.display = "none";
		document.getElementById("partywiseLedger").style.display = "none";
		document.getElementById("taxwiseLedger").style.display = "none";
	}else if(selval == "1") {
		document.getElementById("partywiseLedger").style.display = "none";
		document.getElementById("taxwiseLedger").style.display = "none";
		document.getElementById("prodwiseLedger").style.display = "block";
	}else if(selval == "2") {
		document.getElementById("prodwiseLedger").style.display = "none";
		document.getElementById("taxwiseLedger").style.display = "none";
		document.getElementById("partywiseLedger").style.display = "block";
	}else if(selval == "3") {
		document.getElementById("partywiseLedger").style.display = "none";
		document.getElementById("prodwiseLedger").style.display = "none";
		document.getElementById("taxwiseLedger").style.display = "none";
	}else if(selval == "4") {
		document.getElementById("prodwiseLedger").style.display = "none";
		document.getElementById("partywiseLedger").style.display = "none";
		document.getElementById("taxwiseLedger").style.display = "block";
	}
}

document.getElementById("pitems").innerHTML="<select name='pid' class='form-control input_field select_dropdown' id='pid'>"+ctdatahtml+"</select></td>";
document.getElementById("pitemsv").innerHTML="<select name='pv' class='form-control input_field select_dropdown' id='pv'>"+vendatahtml+"</select></td>";

//To get Product name
if( !((pcode == -1)||(pcode == null)) ){
    if(cat_types_data.length>0) {
    	for(var z=0; z<cat_types_data.length; z++){
    		if(cat_types_data[z].id == pcode){
    			prodcodeval = cat_types_data[z].cat_name+"-"+cat_types_data[z].cat_desc;
    			break;
    		}
    	}
    }
    if(arb_types_data.length>0) {
    	for(var z=0; z<arb_types_data.length; z++){
    		if(arb_types_data[z].id == pcode){
    			prodcodeval = getARBType(arb_types_data[z].prod_code)+" - "+arb_types_data[z].prod_brand+" - "+arb_types_data[z].prod_name;
    			break;
    		}
    	}
    }
    document.getElementById("prod_selected").innerHTML = prodcodeval;
 }

//To get Party name
if(!( (ptycode == -1) || (ptycode == null) ) ){
	if(ptycode==0){
		prodcodeval = "CASH";
	}else {
		if(cvo_data.length>0) {
			for(var z=0; z<cvo_data.length; z++){
				if((cvo_data[z].id == ptycode)) {
					prodcodeval = cvo_data[z].cvo_name;
					break;
				}
			}
		}
	}
	document.getElementById("customer_selected").innerHTML = prodcodeval;
}

var prodcode = pwreport_data.productCode;
var pname = "";
if(prodcode < 100) {
	pname=fetchProductDetails(cat_types_data,prodcode);
} else if (prodcode > 100){
	pname=fetchARBProductDetails(arb_types_data,prodcode);
}
//document.getElementById("prod_selected").innerHTML = "<b>"+pname+"</b>";


var partycode = preport_data.partyId;
var party = "";
if(partycode > 0) {
	party=fetchProductDetails(cvo_data,partycode);
}
//document.getElementById("prod_selected").innerHTML = "<b>"+party+"</b>";


if(rrtype == 1) {
	if(preport_data.length>0) {
		document.getElementById("p_data_table").style.display="block";
		var tbody = document.getElementById('p_data_table_body');
		var count = 0;
		for(var f=0; f<preport_data.length; f++){
			var invdate = new Date(preport_data[f].trans_date);
			var ttypev = preport_data[f].trans_type;

			var pid = preport_data[f].cvo_id;
			var ptyname = "";
			var pty_cat = -1;
			if(pid==0){
				if(preport_data[f].inv_no != "NA") {
					ptyname = "HOUSEHOLDS";
				}
			}else if (pid>0) {
				for(var z=0; z<cvo_data.length; z++){
					if(pid == cvo_data[z].id) {
						pty_cat = cvo_data[z].cvo_cat;
						if(pty_cat != 2) {
							ptyname = cvo_data[z].cvo_name;				
						}else if(pty_cat == 2) {
							ptyname = omc_name +"-"+ cvo_data[z].cvo_name;
						}

						break;
					}
				}
			}

			var tblRow = tbody.insertRow(-1);
			tblRow.style.height="30px";
			tblRow.align="left";
			tblRow.innerHTML = "</td>" + "<td>" + invdate.getDate()+"-"+months[invdate.getMonth()]+"-"+invdate.getFullYear() + "</td>" + 
				"<td>" + preport_data[f].inv_no + "</td>" + 
				"<td>" + ptranxtype[preport_data[f].trans_type] +  "</td>" + 
				"<td>" + preport_data[f].fulls + "</td>" + 
				"<td>" + ptyname + "</td>" +
				"<td>" + preport_data[f].cs_fulls + "</td>"+
				"<td>" + preport_data[f].cs_emptys + "</td>"+
				"<td>" + preport_data[f].discount + "</td>";
		}
		var sdate = new Date(sfd);
		prodetails = prodetails+sdate.getDate()+"-"+months[sdate.getMonth()]+"-"+sdate.getFullYear()+" : "+ openingBal;
		document.getElementById("obSpan").innerHTML = prodetails;
	}else {
		document.getElementById("pw_data_table").style.display="none";
		document.getElementById("p_data_table").style.display="none";
		document.getElementById("tax_data_table").style.display="none";
		
		var tblRow = document.getElementById('errmsg');
		tblRow.style.width="80%";
		tblRow.align="center";
		tblRow.style.color="red";
		tblRow.innerHTML = "NO RECORDS FOUND";
	}
}

if(rrtype == 2){
	if(pwreport_data.length>0){
		document.getElementById("pw_data_table").style.display="block";
		var tbody = document.getElementById('pw_data_table_body');
		
		for(var f=0; f<pwreport_data.length; f++){
			var amtt = pwreport_data[f].amtType;
		//	var tranxt = pwreport_data[f].tranxType;
			var invdate = new Date(pwreport_data[f].tranxDate);
			
			/*if(f==0){
				var paytype = pwreport_data[f].paymentTerms;
				if(!(paytype==1)) {
					if(amtt==1){
						bv = ((+pwreport_data[f].balAmount) - (+pwreport_data[f].amount)).toFixed(2);
					}else if (amtt==2){
						bv = ((+pwreport_data[f].balAmount) + (+pwreport_data[f].amount)).toFixed(2);
					}
				} else {
					bv = pwreport_data[f].balAmount;
				}
			}
			var invdate = new Date(pwreport_data[f].tranxDate);*/
			var ca = "-";
			var da = "-";
			var discount;
			var ttype=pwreport_data[f].tranxType;
/*
			if(ttype==2 || ttype==13) {
				discount=parseFloat(pwreport_data[f].discount).toFixed(2);
			}else if(ttype==1 || ttype==3 || ttype==4 || ttype==5 || ttype==6 || ttype==7 || ttype==8) {
				discount="NA";
			}
			
			if(amtt==1 || (amtt == 2 && tranxt == 3)){
				da = pwreport_data[f].amount;
			}else if (amtt==2 || (amtt==2 && tranxt == 4)){
				ca = pwreport_data[f].amount;
			}
*/

			if(ttype==2) {
				discount=parseFloat(pwreport_data[f].discount).toFixed(2);
			}else if(ttype==1 || ttype==3 || ttype==4 || ttype==5 || ttype==6 || ttype==7 || ttype==8) {
				discount="NA";
			}
			
			if(amtt==1){
			if(ttype == 2  || ttype == 4 || ttype == 6){
				da = pwreport_data[f].amount;
			}else if(ttype == 3  || ttype == 5 || ttype == 8){
				ca = pwreport_data[f].amount;
			}
			}else if(amtt==0 || amtt==3){
				if(ttype == 1  || ttype == 3 || ttype == 6){
					da = pwreport_data[f].amount;
				}else if(ttype == 4  || ttype == 5 || ttype == 7){
					ca = pwreport_data[f].amount;
				}
			}
         var tblRow = tbody.insertRow(-1);
			tblRow.style.height="30px";
			tblRow.align="left";
			tblRow.innerHTML = "</td>" + "<td>" + invdate.getDate()+"-"+months[invdate.getMonth()]+"-"+invdate.getFullYear() + "</td>" + 
				"<td>" + pwreport_data[f].displayId + "</td>" +
				"<td>" + pwtranxtype[pwreport_data[f].tranxType] +  "</td>" + 
				"<td>" + da + "</td>" + 
				"<td>" + ca + "</td>" +
				"<td>" + parseFloat(pwreport_data[f].balAmount).toFixed(2) + "</td>" +
				"<td>" + discount + "</td>";

		}
		var sdate = new Date(sfd);
		obdetails = obdetails+sdate.getDate()+"-"+months[sdate.getMonth()]+"-"+sdate.getFullYear()+" : "+openingBal;		
		document.getElementById("obcvSpan").innerHTML = obdetails;
	}else{
		document.getElementById("pw_data_table").style.display="none";
		document.getElementById("p_data_table").style.display="none";
		document.getElementById("tax_data_table").style.display="none";
		
		var tblRow = document.getElementById('errmsg');
		tblRow.style.width="80%";
		tblRow.align="center";
		tblRow.style.color="red";
		tblRow.innerHTML = "NO RECORDS FOUND";
	}
}

/*if(rrtype == 3){
	if(cbr_data.length>0) {
		document.getElementById("pw_data_table").style.display="block";
		var tbody = document.getElementById('pw_data_table_body');
		var x=0;
		for(var f=0; f<cbr_data.length; f++){
			var tt = cbr_data[f].trans_type;
			if(tt==0){
				x=1;
			}else if(tt != 11) {
				if(f==x){
					if(tt==2||tt==4||tt==6||tt==7||tt==8) {
						ocb = ((+cbr_data[f].cash_balance) - (+cbr_data[f].cash_amount)).toFixed(2);
					}else if (tt==1||tt==3||tt==5) {
						ocb = ((+cbr_data[f].cash_balance) + (+cbr_data[f].cash_amount)).toFixed(2);
					}
				}
				var invdate = new Date(cbr_data[f].t_date);
				var ca = "-";
				var da = "-";
				var discount="-";
				if(tt==2|| tt==4|| tt==6|| tt==7|| tt==8 || tt==10){
					ca = cbr_data[f].cash_amount;
				}else if (tt==1|| tt==3|| tt==5 || tt==9){
					da = cbr_data[f].cash_amount;
				}
				discount="NA";
				if(tt==7 || tt==8 || tt==9){
					discount=parseFloat(cbr_data[f].discount).toFixed(2);
				}
				if(discount=="NaN") {
					discount="NA";
				}
				if(tt==5 || tt==6) {
					discount="NA";
				}
				var tblRow = tbody.insertRow(-1);
				tblRow.style.height="30px";
				tblRow.align="left";
				tblRow.innerHTML = "</td>" + "<td>" + invdate.getDate()+"-"+months[invdate.getMonth()]+"-"+invdate.getFullYear() + "</td>" + 
					"<td>" + cbr_data[f].inv_no + "</td>" + 
					"<td>" + cash_trans_enum_data[cbr_data[f].trans_type-1].trans_module +  "</td>" + 
					"<td>" + da + "</td>" + 
					"<td>" + ca + "</td>" +
					"<td>" +  parseFloat(cbr_data[f].cash_balance).toFixed(2)  + "</td>" +
					"<td>" + discount + "</td>";

			}
		}
		var sdate = new Date(sfd);
		obdetails = obdetails+sdate.getDate()+"-"+months[sdate.getMonth()]+"-"+sdate.getFullYear()+" : "+ocb;		
		document.getElementById("obcvSpan").innerHTML = obdetails;
		document.getElementById("customer_selected").innerHTML = "CASH";
	}else {
		document.getElementById("pw_data_table").style.display="none";
		document.getElementById("p_data_table").style.display="none";
		document.getElementById("tax_data_table").style.display="none";
		
		var tblRow = document.getElementById('errmsg');
		tblRow.style.width="80%";
		tblRow.align="center";
		tblRow.style.color="red";
		tblRow.innerHTML = "NO RECORDS FOUND";
	}
}*/

if(rrtype == 3){
	if(cbr_data.length>0) {
		document.getElementById("pw_data_table").style.display="block";
		var tbody = document.getElementById('pw_data_table_body');
		var x=0;
		var ocb;
		for(var f=0; f<cbr_data.length; f++){
			var tt = cbr_data[f].trans_type;
			if((tt != 11) && (ocb == undefined)){
				x=f;
			}
			if(tt==0){
				ocb=cbr_data[f].cash_amount.toFixed(2);
				x=1;
			}else if(tt != 11) {
				if(f==x){
					if(tt==2||tt==4||tt==6||tt==7||tt==8) {
						ocb = ((+cbr_data[f].cash_balance) - (+cbr_data[f].cash_amount)).toFixed(2);
					}else if (tt==1||tt==3||tt==5) {
						ocb = ((+cbr_data[f].cash_balance) + (+cbr_data[f].cash_amount)).toFixed(2);
					}
				}
				var invdate = new Date(cbr_data[f].t_date);
				var ca = "-";
				var da = "-";
				var discount="-";
				if(tt==2|| tt==4|| tt==6|| tt==7|| tt==8 || tt==10){
					ca = cbr_data[f].cash_amount;
				}else if (tt==1|| tt==3|| tt==5 || tt==9){
					da = cbr_data[f].cash_amount;
				}
				discount="NA";
				if(tt==7 || tt==8 || tt==9){
					discount=parseFloat(cbr_data[f].discount).toFixed(2);
				}
				if(discount=="NaN") {
					discount="NA";
				}
				/*if(tt==5 || tt==6) {
					discount="NA";
				}*/
				var tblRow = tbody.insertRow(-1);
				tblRow.style.height="30px";
				tblRow.align="left";
				tblRow.innerHTML = "</td>" + "<td>" + invdate.getDate()+"-"+months[invdate.getMonth()]+"-"+invdate.getFullYear() + "</td>" + 
					"<td>" + cbr_data[f].inv_no + "</td>" + 
					"<td>" + cash_trans_enum_data[cbr_data[f].trans_type-1].trans_module +  "</td>" + 
					"<td>" + da + "</td>" + 
					"<td>" + ca + "</td>" +
					"<td>" +  parseFloat(cbr_data[f].cash_balance).toFixed(2)  + "</td>" +
					"<td>" + discount + "</td>";

			}
		}
		var sdate = new Date(sfd);
		obdetails = obdetails+sdate.getDate()+"-"+months[sdate.getMonth()]+"-"+sdate.getFullYear()+" : "+ocb;		
		document.getElementById("obcvSpan").innerHTML = obdetails;
		document.getElementById("customer_selected").innerHTML = "CASH";
	}else {
		document.getElementById("pw_data_table").style.display="none";
		document.getElementById("p_data_table").style.display="none";
		document.getElementById("tax_data_table").style.display="none";
		
		var tblRow = document.getElementById('errmsg');
		tblRow.style.width="80%";
		tblRow.align="center";
		tblRow.style.color="red";
		tblRow.innerHTML = "NO RECORDS FOUND";
	}
}


if(rrtype == 4) {	
	if(taxreport_data.length>0) {
		document.getElementById("tax_data_table").style.display="block";
		var tbody = document.getElementById('tax_data_table_body');
		var bv = 0;
		for(var f=0; f<taxreport_data.length; f++){
			var txndate = new Date(taxreport_data[f].tranxDate);
			var pstatus = taxreport_data[f].pStatus;

			var noOfRecs = parseInt(noOfRecsBeforeFdate);
//			if(f==0) {
/*				if((noOfRecs != -1) && (noOfRecs == 0))
					bv = 0;
				else if((noOfRecs != -1) && (noOfRecs > 0))
					bv = ((+taxreport_data[f].balGSTAmount) - (+taxreport_data[f].gstAmount));*/
//				if((noOfRecs != -1) && (noOfRecs > 0))
//					bv = round(((+taxreport_data[f].balGSTAmount) - (+taxreport_data[f].gstAmount)),2);
//			}

			if(f==0) {
				bv = round(((+taxreport_data[f].balGSTAmount) - (+taxreport_data[f].gstAmount)),2);
			}
			
			var ca = "-";
			var da = "-";

			if(taxreport_data[f].tranxType == 1) {
				ca = taxreport_data[f].gstAmount;
			}else if((taxreport_data[f].tranxType == 2) || (taxreport_data[f].tranxType == 3)) {
				da = taxreport_data[f].gstAmount;
			}
						
			var tblRow = tbody.insertRow(-1);
			tblRow.style.height="30px";
			tblRow.align="left";
			tblRow.innerHTML = "</td>" + "<td>" + txndate.getDate()+"-"+months[txndate.getMonth()]+"-"+txndate.getFullYear() + "</td>" + 
			"<td>" + taxtranxtype[taxreport_data[f].tranxType] +  "</td>" +
			"<td>" + ca + "</td>" + 
			"<td>" + da + "</td>" +
			"<td>" + parseFloat(taxreport_data[f].balGSTAmount).toFixed(2) + "</td>";
		}
		var cdate = new Date(sfd);
		obdetails = obdetails+cdate.getDate()+"-"+months[cdate.getMonth()]+"-"+cdate.getFullYear()+" : "+bv;		
		document.getElementById("obtSpan").innerHTML = obdetails;
		document.getElementById("tax_selected").innerHTML = "GST";
	}else {
		document.getElementById("pw_data_table").style.display="none";
		document.getElementById("p_data_table").style.display="none";
		document.getElementById("tax_data_table").style.display="none";
		
		var tblRow = document.getElementById('errmsg');
		tblRow.style.width="80%";
		tblRow.align="center";
		tblRow.style.color="red";
		tblRow.innerHTML = "NO RECORDS FOUND";
	}

}

function fetchLedgerReport(formobj){
	var ems = "";
	var fd = formobj.fd.value.trim();
	var td = formobj.td.value.trim();
	if(checkDateFormate(fd)){
		var ved = dateConvert(fd);
		fd=ved;
	}
	if(checkDateFormate(td)){
		var ved = dateConvert(td);
		td=ved;
	}
	
	var typeofledger = formobj.typeofled.value;
	var spid = formobj.pid;
	var spidv = spid.options[spid.selectedIndex].value;
	var cvoid = formobj.pv;
	var cvoidv = cvoid.options[cvoid.selectedIndex].value;
	var typeoftax = formobj.taxtype.value;
	
	ems = validateEntries(fd,td,typeofledger,spidv,cvoidv,typeoftax);
	if(ems.length>0) {
		document.getElementById("dialog-1").innerHTML = ems;
		alertdialogue();
		//alert(ems);
		document.getElementById("p_data_table").style.display="none";
		document.getElementById("pw_data_table").style.display="none";
		document.getElementById("tax_data_table").style.display="none";
		return;
	}

	if((parseInt(typeofledger) == 1) && (spidv>-1)){
		formobj.stype.value="1";
	}else if((parseInt(typeofledger) == 2) && (cvoidv>-1)){
		formobj.stype.value="2";
		for(var i=0; i<cvo_data.length; i++){
			if(cvoidv==cvo_data[i].id){
				formobj.cvotype.value=cvo_data[i].cvo_cat;
			}
		}
	}else if(parseInt(typeofledger) == 3){
		formobj.stype.value="3";
	}else if((parseInt(typeofledger) == 4) && (typeoftax > 0)){
		formobj.stype.value="4";
	} 
	
/*	if(spidv>-1){
		formobj.stype.value="1";
	}
	if(cvoidv>-1){
		if(cvoidv==0){
			formobj.stype.value="3";
		}else {
			formobj.stype.value="2";
			for(var i=0; i<cvo_data.length; i++){
				if(cvoidv==cvo_data[i].id){
				formobj.cvotype.value=cvo_data[i].cvo_cat;
				}
			}
		}
	}*/

	formobj.submit();
}

function validateEntries(fd,td,typeofledger,spidv,cvoidv,typeoftax) {
	var formobj = document.getElementById('ledger_search_form');
	var errmsg = "";
	
	var chkd = checkdate(td);
	var vfd = ValidateFutureDate(td);	
	if(!(td.length>0))
		errmsg = errmsg+"Please Enter TO DATE<br>";	
	else if(chkd != "true"){
		errmsg = errmsg+chkd+"<br>";
		formobj.td.value="";
	}else if(vfd != "false") {
		errmsg = errmsg+"TO DATE Can't be Future Date<br>";
		formobj.td.value="";
	}	
		
	var checkd = checkdate(fd);
	var ctd = comparisionofTwoDates(fd,td);
	var fvfd = ValidateFutureDate(fd);
	
	if(!(fd.length>0))
		errmsg = errmsg+"Please Enter FROM DATE <br>";
	else if(checkd != "true") {
		errmsg = errmsg + checkd+"<br>";
		formobj.fd.value="";
		formobj.td.value="";
	}else if(fvfd != "false") {
		errmsg = errmsg+"FROM DATE Can't Be Future Date<br>";
		formobj.td.value="";
	}else if(ctd != "false" && (td.length>0)) {
		errmsg = errmsg + ctd +"<br>";
		formobj.fd.value="";
		formobj.td.value="";
	}

	
	if(!(parseInt(typeofledger) > 0)) {
		errmsg = errmsg + "Please Select TYPE OF LEDGER <br>";
	}else if(parseInt(typeofledger) == 1) {
		if(!(spidv > -1)){
			errmsg = errmsg + "Please Select PRODUCT and then click on FETCH ledger <br>";
		}
	}else if(parseInt(typeofledger) == 2) {
		if(!(cvoidv > -1)){
			errmsg = errmsg + "Please Select PARTY NAME and then click on FETCH ledger <br>";
		}
	}else if(parseInt(typeofledger) == 4) {
		if(!(typeoftax > 0)) {
			errmsg = errmsg + "Please Select TAX TYPE and then click on FETCH ledger <br>";
		}
	}
	
	/*if(spidv > -1 && cvoidv > -1){
		errmsg = errmsg + "Please Select Either PRODUCT OR VENDOR/CUSTOMER - NOT BOTH <br>";
	}
	if(!(spidv>-1) && !(cvoidv>-1)){
		formobj.stype.value="1";
		errmsg = errmsg + "Please Select Either PRODUCT OR VENDOR/CUSTOMER <br>";
	}*/
	
	return errmsg;
}