//To get Product name
if( !((pcodenc == 0)||(pcodenc == null)) ){
    if(cat_types_data.length>0) {
           for(var z=0; z<cat_types_data.length; z++){
                    if(cat_types_data[z].id == pcodenc){
                    nctypsec = cat_types_data[z].cat_name+"-"+cat_types_data[z].cat_desc;
                    break;
                    }
           }
    }
    document.getElementById("ptype_selected").innerHTML = nctypsec;
 }

//To get Connection Type
if(!( (concodenc == 0) || (concodenc == null) ) ){
	if(concodenc == 1)
		 nctypsec  = "NC";	
	 else if(concodenc == 2) 
	   	 nctypsec  = "DBC";
	 else if(concodenc == 3) 
		 nctypsec  = "UJWALA LOAN NC";
	 else if(concodenc == 4) 
	   	 nctypsec  = "UJWALA CASH NC";
	 else if(concodenc == 5) 
	   	 nctypsec  = "RC";
	 else if(concodenc == 6) 
	   	 nctypsec  = "TV";
      document.getElementById("ctype_selected").innerHTML = nctypsec;
}



var tNumoOfConnections=0;
var tNumOfCylinders=0;
var tNumOfRegulators=0;
var tSecurityDeposite=0;


if(rrtype!=0){
	if(report_data.length === 0) {
		 var tblRow = document.getElementById('errmsg');
		   tblRow.style.width="80%";
		   tblRow.align="center";
		   tblRow.style.color="red";
		   tblRow.innerHTML = "NO RECORDS FOUND";
	}else{
	document.getElementById("p_data_table").style.display="block";
	var tbody = document.getElementById('p_data_table_body');
	for(var f=0; f < report_data.length; f++) {
		var invdate = new Date(report_data[f].tranxDate);
		var pidv = report_data[f].prodCode;
		 var spd = fetchProductDetails(cat_types_data, report_data[f].prodCode);
		 
		 
		 tNumoOfConnections=(+(tNumoOfConnections)+ +(report_data[f].nocs));
		 tNumOfCylinders=(+(tNumOfCylinders)+ +(report_data[f].ncs));
		 tNumOfRegulators=(+(tNumOfRegulators)+ +(report_data[f].nrs));
		 tSecurityDeposite=(+(tSecurityDeposite)+ +(report_data[f].totalSecurityDeposit)).toFixed(2);

		   var tblRow = tbody.insertRow(-1);
		   tblRow.style.height="30px";
		   tblRow.align="left";
		   tblRow.innerHTML = "<td>" + invdate.getDate()+"-"+months[invdate.getMonth()]+"-"+invdate.getFullYear() + "</td>" + 
		   		"<td>" + conntypes[report_data[f].connectionType] + "</td>" + 
		   		"<td>" + spd + "</td>" + 
		   		"<td>" + report_data[f].nocs + "</td>" + "<td>" + report_data[f].ncs +  "</td>" + "<td>" + report_data[f].nrs +  "</td>" +
		   		"<td>" + report_data[f].totalSecurityDeposit +  "</td>"; 
	}
	}
}

function fetchNCDBCRCTVReport() {
	var formobj = document.getElementById('purchase_search_form');
	var ems = "";
	var fd = formobj.fd.value.trim();
	var td = formobj.td.value.trim();	
	var spid = formobj.pid.selectedIndex;
	var sct = formobj.ct.selectedIndex;

	if(checkDateFormate(fd)){
		var ved = dateConvert(fd);
		fd=ved;
	}
	if(checkDateFormate(td)){
		var ved = dateConvert(td);
		td=ved;
	}
	
	ems = validateEntries(fd,td,spid,sct);
	if(ems.length>0) {
		document.getElementById("dialog-1").innerHTML = ems;
		alertdialogue();
		//alert(ems);
		document.getElementById("p_data_table").style.display="none";
		return;
	}
	
	formobj.submit();
}



function validateEntries(fd,td,spid,sct) {
	var formobj = document.getElementById('purchase_search_form');
	var errmsg = "";

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
	else if(checkd != "true") {
		errmsg = errmsg+checkd+"<br>";
		formobj.fd.value="";
		formobj.td.value="";
	}else if(ctd != "false"  && td.length>0) {
		errmsg = errmsg + ctd +"<br>";
		formobj.fd.value="";
		formobj.td.value="";
	}

	if(!spid>0 && !sct>0){
		errmsg = errmsg + "Please Select Either PRODUCT / CONNECTION TYPE or Both <br>";
	}

	return errmsg;
}