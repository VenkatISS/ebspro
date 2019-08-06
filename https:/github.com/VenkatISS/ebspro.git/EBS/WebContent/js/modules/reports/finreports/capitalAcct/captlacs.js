//Set Page Data
if(page_data.length>0) {
	document.getElementById("ca_data_div").style.display="block";
	var partnerName = getPartnerName(partners_data,spid);
	var partnerOB1 = getPartnerOB(partners_data,spid);
	var partnerOB = round(parseFloat(partnerOB1), 2)
	document.getElementById("spnSpan").innerHTML = partnerName;
	
	var tbody = document.getElementById('data_table_body');
	var tblRow = tbody.insertRow(-1);
	tblRow.innerHTML = "<td>01-APR-"+"2018"+"</td>"+"<td>OPENING BALANCE</td><td></td><td></td><td>"+partnerOB+"</td>";
	var bal = partnerOB;
	for(var f=0; f<page_data.length; f++){
		var ed = new Date(page_data[f].tranx_date);
		var da = "-";
		var ca = "-";
		var tt = page_data[f].tranx_type;
		if(tt==1){
			ca = page_data[f].tranx_amount;
			bal = (+bal) + (+ca);
			bal=round(parseFloat(bal), 2);
			
		} else if (tt==2){
			da = page_data[f].tranx_amount;
			bal = (+bal) - (+da);
			bal=round(parseFloat(bal), 2);
		} else if (tt==3){
			da = page_data[f].tranx_amount;
			bal = 0.00;
		}
		tblRow = tbody.insertRow(-1);
		tblRow.style.height="20px";
		tblRow.align="left";
		tblRow.innerHTML = "<td>" + ed.getDate()+"-"+months[ed.getMonth()]+"-"+ed.getFullYear() + "</td>" + "<td>" + pttype[tt] + "</td>" +  
				"<td>" + da +  "</td>" + "<td>" + ca +  "</td>" + "<td>" + bal +  "</td>"; 
	};
	var tblRow2 = tbody.insertRow(-1);
	tblRow2.innerHTML = "<td>31-MAR-"+"2019"+"</td>"+"<td>TOTAL CAPITAL</td><td></td><td></td><td>"+bal+"</td>";
	
}else if(yearsrch){
	document.getElementById("ca_data_div").style.display="none";
	  var tblRow1= document.getElementById('errmsg');
	   tblRow1.style.width="80%";
	   tblRow1.align="center";
	   tblRow1.style.color="red";
	   tblRow1.innerHTML = "NO RECORDS FOUND";

}

function fetchCapitalAccount(){
   	var formobj = document.getElementById('ca_form');
	var ems = "";
	var sfy = formobj.sfy.selectedIndex;
	var pid = formobj.pid.selectedIndex;
	
	ems = validateEntries(sfy,pid);
	if(ems.length>0) {
		document.getElementById("dialog-1").innerHTML = ems;
		alertdialogue();
		//alert(ems);
		document.getElementById("ca_data_div").style.display="none";
		return;
	} 	
	formobj.submit();
}

function validateEntries(sfy,pid){
	var formobj = document.getElementById('ca_form');
	var errmsg = "";
	var today = new Date();
	var pyear = today.getFullYear();
	var sfy1=formobj.sfy.selectedIndex;
	var sfy1v = formobj.sfy.options[sfy1].value;
	var sfvalue=parseInt(sfy1v.split('-'));
	
	if(!(sfy>0))
		errmsg = errmsg + "Please select FINANCIAL YEAR\n";
	else if(!(sfvalue <= pyear))
		errmsg = errmsg + " FINANCIAL YEAR Can't Be Future Year\n";
	if(!(pid>0))
		errmsg = errmsg + "Please select PARTNER NAME\n";
	return errmsg;
}
