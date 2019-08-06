var dq = '"';
var tbody = document.getElementById('bank_data_table_body');
for(var f=bank_data.length-1; f>=0; f--){
	if(bank_data[f].deleted == 0) {
		var bc = bank_data[f].bank_code;
		var ihtml = "<td></td>";
		var bname = banknames[bank_data[f].bank_name];
		var bbranch = bank_data[f].bank_branch;
		var bifsccode = bank_data[f].bank_ifsc_code;
		var baddr = bank_data[f].bank_addr;
		var bacno = bank_data[f].bank_acc_no; 
	
		
			
		if(bank_data[f].acc_ob==0.00){
			ihtml = "<td align='center'><button name='update_balance_popup' class='updatebutton' id='update_balance_popup' onclick='showUpdateBalanceFormDialog("+bank_data[f].id+","+dq+bank_data[f].bank_code+dq+")'>UPDATE</button></td>";
		}else{
			ihtml = "<td align='center'><button name='update_balance_popup'  id='update_balance_popup'  onclick='showUpdateBalanceFormDialog()' disabled>UPDATE</button></td>";
		}
		
		// balance popup div end 04052019
		
		if (bc == 'TAR ACCOUNT') {
			bc = "OMC";
			bname="LOAD ACCOUNT";
			bbranch="NA";
			bifsccode="NA";
			baddr="NA";
			bacno = "NA";  
		}
		if (bc == 'STAR ACCOUNT') {
			bc = "OMC";
			bname="SV/TV ACCOUNT";
			bbranch="NA";
			bifsccode="NA";
			baddr="NA";
			bacno = "NA";
		}
		var tblRow = tbody.insertRow(-1);
		tblRow.style.height="20px";
		tblRow.align="left";
		tblRow.innerHTML = '<td>'+ bname +'</td>'+
   			'<td>'+ bc +'</td>'+
   			'<td>'+ bacno +'</td>'+
   			'<td>'+ bbranch +'</td>'+
   			'<td>'+ bifsccode +'</td>'+
   			'<td>'+ baddr +'</td>'+
   			'<td>'+ bank_data[f].acc_ob +'</td>'+
   			'<td>'+ bank_data[f].acc_cb +'</td>'+ihtml
	}		
};

//----------------------------------

  var cashOpBal=0;
  var cashClBal=0;
  
  var cCount=0;
for(var f=cash_data.length-1; f>=0; f--){
	
	if(cCount == 0){
		cashOpBal=cash_data[f].cash_balance;
		cashClBal=cash_data[f].cash_balance;
	cCount=1;
	}else{
		cashClBal=cash_data[f].cash_balance;
	}	
}	

var cRowCount=0

for(var f=cash_data.length-1; f>=0; f--){

	if(cRowCount==0){

	cRowCount=1;
	if(cash_data[f].deleted == 0) {
		var bc = cash_data[f].id;
		var ihtml = "<td></td>";
		var bname = "CASH ACCOUNT";
		var bbranch = "NA";
		var bifsccode = "NA";
		var baddr = "NA";

		if(cashOpBal==0.00 && cash_data[f].inv_no=="NA"){
	//	if(cashOpBal==0.00 ){
			ihtml = "<td align='center'><button name='update_balance_popup' class='updatebutton' id='update_balance_popup'  onclick='showUpdateBalanceFormDialog("+cash_data[f].id+","+dq+cash_data[f].inv_no+dq+")'>UPDATE</button></td>";
		}else{
			ihtml = "<td align='center'><button name='update_balance_popup' id='update_balance_popup'  onclick='showUpdateBalanceFormDialog()' disabled>UPDATE</button></td>";	
		}
		
		// balance popup div end 04052019
		
		if(bc != null){
			bc = "CASH";
		}
		
		var tblRow = tbody.insertRow(-1);
		tblRow.style.height="20px";
		tblRow.align="left";
		tblRow.innerHTML = '<td>'+ bname +'</td>'+
   			'<td>'+ bc +'</td>'+
   			'<td>'+ bacno +'</td>'+
   			'<td>'+ bbranch +'</td>'+
   			'<td>'+ bifsccode +'</td>'+
   			'<td>'+ baddr +'</td>'+
   			'<td>'+ cashOpBal+'</td>'+
   			'<td>'+ cashClBal +'</td>'+ihtml
	}
	}
};


// fetch Ujjwala opening balance

for(var f=ujwala_data.length-1; f>=0; f--){
	if(ujwala_data[f].deleted == 0) {
		var bc = ujwala_data[f].id;
		var ihtml = "<td></td>";
		var bname = "UJWALA ACCOUNT";
		var bbranch = "NA";
		var bifsccode = "NA";
		var baddr = "NA";

		if(ujwala_data[f].obal==0){
			ihtml = "<td align='center'><button name='update_balance_popup' class='updatebutton' id='update_balance_popup'  onclick='showUpdateBalanceFormDialog("+ujwala_data[f].id+","+dq+ujwala_data[f].cvo_name+dq+")'>UPDATE</button></td>";
		}else{
			ihtml = "<td align='center'><button name='update_balance_popup' id='update_balance_popup'  onclick='showUpdateBalanceFormDialog()' disabled>UPDATE</button></td>";	
		}
		
		if (ujwala_data[f].cvo_name == "UJWALA") {
			bc = "UJWALA";
		}
		var tblRow = tbody.insertRow(-1);
		tblRow.style.height="20px";
		tblRow.align="left";
		tblRow.innerHTML = '<td>'+ bname +'</td>'+
   			'<td>'+ bc +'</td>'+
   			'<td>'+ bacno +'</td>'+
   			'<td>'+ bbranch +'</td>'+
   			'<td>'+ bifsccode +'</td>'+
   			'<td>'+ baddr +'</td>'+
   			'<td>'+ ujwala_data[f].obal +'</td>'+
   			'<td>'+ ujwala_data[f].cbal +'</td>'+ihtml
	}		
};

//--------------------------------------


// balance popup div start 04052019

function showUpdateBalanceFormDialog(id,name){
	
	document.getElementById('bankBalanceUpdatePopup').style.display = 'block';
	document.getElementById('bId').value=id;
	document.getElementById('bname').value=name;

}

function closeBankBalanceUpdatePopup(){
	
	document.getElementById('bankBalanceUpdatePopup').style.display = 'none';
	document.getElementById("update_balance_form").reset();

}

//balance popup div end 04052019


//set balance validations
function submitSetBalanceDetails(formobj) {
	var balAmount = formobj.bankbalamt.value.trim();

	var ems="";
	if(!(balAmount.length>0))
		ems= ems+"Please Enter Balance Amont<br>";
	else if(!validateAmount(balAmount))
		ems = ems+"Balance Amont Must Be A Number <br>";
	if (ems.length > 0) {
		document.getElementById("dialog-1").innerHTML = ems;
		alertdialogue();
		return false;
	}
}