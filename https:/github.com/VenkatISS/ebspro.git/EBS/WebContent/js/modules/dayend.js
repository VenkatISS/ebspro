if(rder==1) {
	//Show Day End Selected Date
    document.getElementById("de_date_div").style.display="block";
    var sdeDate = new Date(sded);
    document.getElementById("sdedspan").innerHTML = "<label>SELECTED DAY END DATE : </label>"+sdeDate.getDate()+"-"+months[sdeDate.getMonth()]+"-"+sdeDate.getFullYear();
    document.getElementById("submit_de_form").deds.value=sded;
        	
	//Show REFILLS Data
	document.getElementById("cyld_data_div").style.display="block";
	var pc = 0;
	var tbody = document.getElementById('cyld_report_table_body');
	for(var prop in prod_de_data){
		var propDetails = prod_de_data[prop];
		var pname = fetchProductDetails(cylinder_types_list,prop);
		if(prop<10){
			pc = 1;
		   var tblRow = tbody.insertRow(-1);
		   tblRow.style.height="30px";
		   tblRow.align="center";
		   tblRow.innerHTML = "<td>" + pname + "</td>" + "<td>" + propDetails.openingStockF + "</td>" + 
		   "<td>" + propDetails.totalPurchases + "</td>" + "<td>" + propDetails.totalPurchaseReturns +  "</td>" +  
		   "<td>" + propDetails.totalSalesReturns +  "</td>" +  "<td>" + propDetails.totalNCSales + "</td>" +
		   "<td>" + propDetails.totalSales + "</td>" + "<td>" + propDetails.closingStockF +  "</td>"; 
		}
	};
	if(pc==0){
		var tbody = document.getElementById('cyld_report_table_body');
		var tblRow = tbody.insertRow(-1);
		   tblRow.style.height="30px";
		   tblRow.align="center";
		   tblRow.innerHTML = "<td colspan='6'>NO RECORDS FOUND</td>";
	}
	
	//Show ARB Data
	document.getElementById("arb_data_div").style.display="block";
	var arbc = 0;
	var tbody = document.getElementById('arb_report_table_body');
	for(var prop in prod_de_data){
		var propDetails = prod_de_data[prop];
		if(prop>100){
			var pname = fetchARBProductDetails(arb_data,prop);
			arbc = 1;
		   var tblRow = tbody.insertRow(-1);
		   tblRow.style.height="30px";
		   tblRow.align="center";
		   tblRow.innerHTML = "<td>" + pname + "</td>" + "<td>" + propDetails.openingStockF + "</td>" +
		   "<td>" + propDetails.totalPurchases + "</td>" + "<td>" + propDetails.totalPurchaseReturns +  "</td>" + 
		   "<td>" + propDetails.totalSalesReturns +  "</td>" + "<td>" + propDetails.totalSales + "</td>" + "<td>" + propDetails.closingStockF +  "</td>"; 
		}
	};
	if(arbc==0){
		var tbody = document.getElementById('arb_report_table_body');
		var tblRow = tbody.insertRow(-1);
		   tblRow.style.height="30px";
		   tblRow.align="center";
		   tblRow.innerHTML = "<td colspan='6'>NO RECORDS FOUND</td>";
	}
	
	//Show Bank Data
	document.getElementById("bb_data_div").style.display="block";
	if(bbr_data.length>0) {
		var tbody = document.getElementById('bb_report_table_body');
		for(var f=0; f<bbr_data.length; f++){
		   var invdate = new Date(bbr_data[f].bt_date);
		   var tblRow = tbody.insertRow(-1);
		   var ca = "-";
		   var da = "-";
		   var bankName = getBankName(bank_data,bbr_data[f].bank_id);

		   if(bbr_data[f].trans_type==3 || bbr_data[f].trans_type==2 || bbr_data[f].trans_type==7
					|| bbr_data[f].trans_type==11 || bbr_data[f].trans_type==12 || bbr_data[f].trans_type==14) {
//				ca = (+(bbr_data[f].trans_amount)+ +(bbr_data[f].trans_charges));
				ca = (+(bbr_data[f].trans_amount)- +(bbr_data[f].trans_charges));			   
			}
		   	if(bbr_data[f].trans_type==1 || bbr_data[f].trans_type==4 || bbr_data[f].trans_type==6
		   		 || bbr_data[f].trans_type==9 || bbr_data[f].trans_type==10 || bbr_data[f].trans_type==13) {
		   		da = (+(bbr_data[f].trans_amount)+ +(bbr_data[f].trans_charges));
		   	}
		   
		   tblRow.style.height="30px";
		   tblRow.align="left";
		   tblRow.innerHTML = "<td>" + invdate.getDate()+"-"+months[invdate.getMonth()]+"-"+invdate.getFullYear() + "</td>" + "<td>" + bankName + "</td>" +
		   "<td>" + tts[bbr_data[f].trans_type] + " - " + mops[bbr_data[f].trans_mode] + " - " + bbr_data[f].instr_details + "</td>" + 
		   		"<td>" + da +  "</td>" + "<td>" + ca +  "</td>" + "<td>" + bbr_data[f].bank_acb +  "</td>"; 
		};
	} else {
		var tbody = document.getElementById('bb_report_table_body');
		var tblRow = tbody.insertRow(-1);
		   tblRow.style.height="30px";
		   tblRow.align="center";
		   tblRow.innerHTML = "<td colspan='6'>NO RECORDS FOUND</td>";
	}
}

/*function confirmDER(formobj){
	if (confirm("PRESS OK TO CONFIRM DAY END REPORT SUBMISSION") == true) {
        formobj.submit();
    } else {
        return;
    }
}*/

function confirmDER(formobj){
//	$("#myDialogText").text("PRESS OK TO CONFIRM DAY END REPORT SUBMISSION");
	$("#myDialogText").text("RECORDS CANNOT BE MODIFIED/DELETED AFTER DAYEND SUBMISSION/n. ARE YOU SURE YOU WANT TO SUBMIT DAY END REPORT ??");
	$("#dialog-confirm").dialog({
	        resizable: false,
	        modal: true,
	        title: "Confirm",
	        height: 150,
	        width: 400,
	           buttons: {
	            "Yes": function () {
	                $(this).dialog('close');
	                formobj.submit();
	            },
	                "No": function () {
	                $(this).dialog('close');
	                return;
	            }
	        }
	  });
	}

function searchData() {
	var formobj = document.getElementById('dayend_search_form');
	var ems = "";
	var fd = formobj.fd.value;
	ems = validateEntries(fd);
	if(ems.length>0) {
		document.getElementById("dialog-1").innerHTML = ems;
		alertdialogue();
		//alert(ems);
		return;
	}
	formobj.submit();
}


function validateEntries(fd){
	var errmsg = "";
	var sddate = new Date(fd);
	var date = sddate.getDate(); 
	var mon = sddate.getMonth()+1;
	var yr = sddate.getFullYear();
  	if(date<10)
  		date="0"+date;
  	if(mon<10)                                     
  		mon="0"+mon;
	var sd = yr+"-"+mon+"-"+date;
	if(!(fd.length>0))
		errmsg = errmsg+"Please enter Select Date<br>";
	else if(ValidateFutureDate(sd) != "false"){
		errmsg = errmsg+"The Date you have provided cannot future Date<br>";
	}
	return errmsg;
}