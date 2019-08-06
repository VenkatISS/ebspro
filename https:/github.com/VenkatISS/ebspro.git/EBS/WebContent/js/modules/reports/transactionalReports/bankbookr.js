/*if(!( (bcodeb == 0) || (bcodeb == null) ) ){
	if(bank_data.length>0) {
		for(var z=0; z<bank_data.length; z++){
			if((bank_data[z].id == bcodeb)) {
				var bc = bank_data[z].bank_code;
				if(bc === "TAR ACCOUNT")
					bc = "LOAD ACCOUNT";
				if(bc === "STAR ACCOUNT")
					bc = "SV/TV ACCOUNT";
				btypsel = bc+" - "+bank_data[z].bank_acc_no;
				break;
			}
		}
	}
	document.getElementById("type_selected").innerHTML = btypsel;
}
*/

if(!( (bcodeb == 0) || (bcodeb == null) ) ){
	if(bank_data.length>0) {
		for(var z=0; z<bank_data.length; z++){
			if((bank_data[z].id == bcodeb)) {
				var bc = bank_data[z].bank_code;
				btypsel = bc+" - "+bank_data[z].bank_acc_no;

				if(bc === "TAR ACCOUNT") {
					btypsel = "LOAD ACCOUNT";
				}else if(bc === "STAR ACCOUNT") {
					btypsel = "SV/TV ACCOUNT";
				}
				break;
			}
		}
	}
	document.getElementById("type_selected").innerHTML = btypsel;
}


if(bbr_data.length>0) {
	document.getElementById("bb_data_table").style.display="block";
	var tbody = document.getElementById('bb_report_table_body');
	for(var f=0; f<bbr_data.length; f++){
		var invdate = new Date(bbr_data[f].bt_date);
		var tblRow = tbody.insertRow(-1);
		var ca = "-";
		var da = "-";
		var bankName = getBankName(bank_data,bbr_data[f].bank_id);
		if(bbr_data[f].bank_id == parseInt(tarbid)) {
			bankName = "LOAD ACCOUNT";
		}
		if(bbr_data[f].bank_id == parseInt(starbid)) {
			bankName = "SV/TV ACCOUNT";
		}
		if(bbr_data[f].trans_type==3 || bbr_data[f].trans_type==2 || bbr_data[f].trans_type==7
				|| bbr_data[f].trans_type==11 || bbr_data[f].trans_type==12 || bbr_data[f].trans_type==14) {
//			ca = (+(bbr_data[f].trans_amount)+ +(bbr_data[f].trans_charges));
			ca = (+(bbr_data[f].trans_amount)- +(bbr_data[f].trans_charges));			
		}
	   	if(bbr_data[f].trans_type==1 || bbr_data[f].trans_type==4 || bbr_data[f].trans_type==6
	   		 || bbr_data[f].trans_type==9 || bbr_data[f].trans_type==10 || bbr_data[f].trans_type==13) {
	   		da = (+(bbr_data[f].trans_amount)+ +(bbr_data[f].trans_charges));
	   	}

	   	tblRow.style.height="30px";
	   	tblRow.align="left";
	   	tblRow.innerHTML = "<tr>"+
		   		"<td>"+ invdate.getDate()+"-"+months[invdate.getMonth()]+"-"+invdate.getFullYear() +"</td>"+
		   		"<td>"+ bankName +"</td>"+
	   			"<td>"+ tts[bbr_data[f].trans_type] + " - " + mops[bbr_data[f].trans_mode] + " - " + bbr_data[f].instr_details +"</td>"+
	   			"<td>"+ da +"</td>"+
	   			"<td>"+ ca +"</td>"+
	   			"<td>"+ bbr_data[f].bank_acb +"</td>"+
	   			"</tr>";
	};
	var sdate = new Date(sfd);
	bankdetails = "<b>"+bankdetails+"</b>"+sdate.getDate()+"-"+months[sdate.getMonth()]+"-"+sdate.getFullYear()+" : "+ openingBal;
	document.getElementById("obSpan").innerHTML = bankdetails;

}else if(norecf != 1 && bbr_data.length == 0){
	var tblRow = document.getElementById('errmsg');
	tblRow.style.width="80%";
	tblRow.align="center";
	tblRow.style.color="red";
	tblRow.innerHTML = "NO RECORDS FOUND";
}
function searchData() {
	var formobj = document.getElementById('bank_book_search_form');
	var ems = "";
	var bid = formobj.bid.selectedIndex;
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
	ems = validateEntries(bid,fd,td);
	if(ems.length>0) {
		document.getElementById("dialog-1").innerHTML = ems;
		alertdialogue();
		//alert(ems);
		document.getElementById("bb_data_table").style.display="none";
		return;
	}
	formobj.submit();
}
function validateEntries(bid,fd,td){
	var formobj = document.getElementById('bank_book_search_form');
	var errmsg = "";
	if(!(bid>0))
		errmsg = errmsg + "Please Select BANK<br>";
	
	var chkd = checkdate(td);
	var vfd = ValidateFutureDate(td);	
	if(!(td.length>0))
		errmsg = errmsg+"Please Enter TO DATE<br>";
	else if(chkd != "true"){
		errmsg = errmsg+chkd+"<br>";
		formobj.td.value="";
	}else if(vfd != "false"){
		errmsg = errmsg+"TO DATE Can't Be Future Date<br>";
		formobj.td.value="";
	}	

	var checkd = checkdate(fd);
	var ctd = comparisionofTwoDates(fd,td);
	if(!(fd.length>0))
		errmsg = errmsg+"Please Enter FROM DATE<br>";
	else if(checkd != "true"){
		errmsg = errmsg+checkd+"<br>";
		formobj.fd.value="";
		formobj.td.value="";
	}else if(ctd != "false" && td.length>0){
		errmsg = errmsg + ctd +"<br>";
		formobj.fd.value="";
		formobj.td.value="";
	}
	return errmsg;
}