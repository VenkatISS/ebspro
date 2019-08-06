var tbody = document.getElementById('mpopup_bank_data_table_body');
for(var f=bank_data.length-1; f>=0; f--){
	if(bank_data[f].deleted == 0) {
		var bc = bank_data[f].bank_code;
		var ihtml = "<td></td>";
		var bname = banknames[bank_data[f].bank_name];
		var bbranch = bank_data[f].bank_branch;
		var bifsccode = bank_data[f].bank_ifsc_code;
		var baddr = bank_data[f].bank_addr;
		var bacno = bank_data[f].bank_acc_no; 
	
		/*if (!(bc == 'TAR ACCOUNT' || bc == 'STAR ACCOUNT')) {
			ihtml = "<td align='center'><img src='images/delete.png' class='fa fa-trash-o' onclick='mBankdeleteItem("+bank_data[f].id+")'></td>";
		}*/
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
   			'<td>'+ bank_data[f].acc_cb +'</td>'//+ihtml
	}		
};

function addmBankpopupRow() {
	document.getElementById('mymBankPopAddDIV').style.display = 'block';
	document.getElementById('savembankpopupdiv').style.display="inline";
    $("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');

    var trcount = document.getElementById('mpopup_bank_add_table_body').getElementsByTagName('tr').length;
    if(trcount>0){
    	var trv=document.getElementById('mpopup_bank_add_table_body').getElementsByTagName('tr')[trcount-1];
    	var saddv=trv.getElementsByClassName('sadd');
    	var eaddv=trv.getElementsByClassName('eadd');
    
    	var res=checkRowData(saddv,eaddv);
    	if(res == false){
    		document.getElementById("dialog-1").innerHTML = "Please enter all the values in current row and then add next row";
    		alertdialogue();
    		//alert("Please enter all the values in current row and then add next row");
    		return;
    	}		
    }

	var ele = document.getElementsByClassName("bdc");
	if(ele.length < 4){
		var tbody = document.getElementById('mpopup_bank_add_table_body');
		var newRow = tbody.insertRow(-1);

		var a = newRow.insertCell(0);
		var b = newRow.insertCell(1);
		var c = newRow.insertCell(2);
		var d = newRow.insertCell(3);
		var e = newRow.insertCell(4);
		var f = newRow.insertCell(5);
		var g = newRow.insertCell(6);
		var h = newRow.insertCell(7);

		a.innerHTML = "<td><select name='bank_name' id='bank_name' class='form-control input_field bdc sadd' style='width:160px;'>"
							+ bndatahtml + "</SELECT></td>";
		b.innerHTML = "<td><select name='bank_code' id='bank_code' class='form-control input_field eadd' style='width:160px;' onchange='setmBankPopupType()'></td>"+
		 "<OPTION VALUE='0'>SELECT</OPTION>"+
		 "<OPTION VALUE='SAVINGS'>SAVINGS</OPTION>"+
		 "<OPTION VALUE='CURRENT'>CURRENT</OPTION>"+
		 "<OPTION VALUE='LOAN'>LOAN</OPTION>"+
		 "<OPTION VALUE='OVER DRAFT'>OVER DRAFT</OPTION>"+
		 "</SELECT></td>";
		c.innerHTML = "<td><input type=text name='bank_accno' id='bank_accno' onchange='checkForDeletedmBankAccounts(this)' class='form-control input_field eadd' size='8' maxlength='20' placeholder='Account No'></td>";
		d.innerHTML = "<td><input type=text name='bank_branch' id='bank_branch' class='form-control input_field eadd' size='8' maxlength='20' placeholder='Branch'></td>";
		e.innerHTML = "<td><input type=text name='bank_ifsc' id='bank_ifsc' class='form-control input_field eadd' size='8' maxlength='11' placeholder='Ifsc Code'></td>";
		f.innerHTML = "<td><input type=text name='bank_addr' id='bank_addr' class='form-control input_field eadd' size='8' maxlength='25' placeholder='Address'></td>";
		g.innerHTML = "<td>"+
						"<input type=text name='bank_ob' id='bank_ob' class='form-control input_field eadd' value='0.00' size='8' maxlength='11' placeholder='Opening balance'>"+
						"<input type='hidden' id='DLbal' name='DLbal' value=''>"+
						"</td>";
		h.innerHTML = "<td><img src='images/delete.png' onclick='doRowDelete(this)'></td>";

		document.getElementById("odinstrn").style.display = "none";
	}else{
		document.getElementById("dialog-1").innerHTML = "Please Save the Records and ADD Again";
		alertdialogue();
		//alert("Please Save the Records and ADD Again");
	}
}


/*function setmBankPopupType() {
    var trcount = document.getElementById('mpopup_bank_add_table_body').getElementsByTagName('tr').length;
    var trv=document.getElementById('mpopup_bank_add_table_body').getElementsByTagName('tr')[trcount-1];
	
	var formobj = document.getElementById('mpopup_bank_data_form');
	var ele = document.getElementsByClassName("bdc");
	if(ele.length==1) {
		var bal = document.getElementById("bank_code");
		if(bal.selectedIndex>0) {
			var balv = bal.options[bal.selectedIndex].value;
			if(balv=="LOAN" || balv=="OVER DRAFT"){
				var inputBal = prompt("Please enter Balance here","");
				formobj.DLbal.value = inputBal;
			}else {
				formobj.DLbal.value = "NA";
				document.getElementById("DLbal").setAttribute("readOnly","true");
			}
		}
	}else if(ele.length>1) {
		for(var i=0;i<ele.length;i++){
			var bal = formobj.bank_code[i];
			if(bal.selectedIndex>0) {
				var balv = bal.options[bal.selectedIndex].value;
				if(balv=="LOAN" || balv=="OVER DRAFT") {
					var inputBal = prompt("Please enter Balance here","");
					formobj.DLbal[i].value = inputBal;
				}else {
					formobj.DLbal[i].value = "NA";
					document.getElementById("DLbal").setAttribute("readOnly","true");
				}
			}
		}		
	}
}*/

/*function setmBankPopupType() {
    var trcount = document.getElementById('mpopup_bank_add_table_body').getElementsByTagName('tr').length;
	var curRow = trcount-1 ;
	var formobj = document.getElementById('mpopup_bank_data_form');
	var ele = document.getElementsByClassName("bdc");
	if(ele.length==1) {
		var bal = document.getElementById("bank_code");
		if(bal.selectedIndex>0) {
			var balv = bal.options[bal.selectedIndex].value;
			if(balv=="LOAN" || balv=="OVER DRAFT"){
				var inputBal = prompt("Please enter Balance here","");
				formobj.DLbal.value = inputBal;
			}else {
				formobj.DLbal.value = "NA";
				document.getElementById("DLbal").setAttribute("readOnly","true");
			}
		}
	}else if(ele.length>1) {
		var bal = formobj.bank_code[curRow];
		if(bal.selectedIndex>0) {
			var balv = bal.options[bal.selectedIndex].value;
			if(balv=="LOAN" || balv=="OVER DRAFT") {
				var inputBal = prompt("Please enter Balance here","");
				formobj.DLbal[curRow].value = inputBal;
			}else {
				formobj.DLbal[curRow].value = "NA";
				document.getElementById("DLbal").setAttribute("readOnly","true");
			}
		}
	}
}*/

function setmBankPopupType() {
    var trcount = document.getElementById('mpopup_bank_add_table_body').getElementsByTagName('tr').length;
	var curRow = trcount-1 ;
	var formobj = document.getElementById('mpopup_bank_data_form');
	var ele = document.getElementsByClassName("bdc");
	if(ele.length==1) {
		var bal = document.getElementById("bank_code");
		if(bal.selectedIndex>0) {
			var balv = bal.options[bal.selectedIndex].value;
			if(balv=="OVER DRAFT"){
				document.getElementById("odinstrn").style.display = "block";
				var dlbalEle = formobj.DLbal;
				document.getElementById("odval").value = "";
				mbankpopuppromptDialog(formobj,dlbalEle);
			}else {
				document.getElementById("odinstrn").style.display = "none";
				formobj.DLbal.value = "NA";
				document.getElementById("DLbal").setAttribute("readOnly","true");
			}
		}
	}else if(ele.length>1) {
		var bal = formobj.bank_code[curRow];
		if(bal.selectedIndex>0) {
			var balv = bal.options[bal.selectedIndex].value;
			if(balv=="OVER DRAFT") {
				document.getElementById("odinstrn").style.display = "block";
				var dlbalEle = formobj.DLbal[curRow];
				document.getElementById("odval").value = "";
				mbankpopuppromptDialog(formobj,dlbalEle);
			}else {
				document.getElementById("odinstrn").style.display = "none";
				formobj.DLbal[curRow].value = "NA";
				document.getElementById("DLbal").setAttribute("readOnly","true");
			}
		}
	}
}


function mbankpopuppromptDialog(formobj,dlbalEle){
	$("#dialog-form").dialog({
        resizable: false,
        modal: true,
        title: "iLPG",
        height: 220,
        width: 400,
		buttons: {
			"Ok": function() {
				dlbalEle.value = $("#odval").val();
				var value = $("#odval").val();
				if(!(value.length>0)) {
					document.getElementById("dialog-1").innerHTML = "Please enter debit balance. ";
		    		alertdialogue();
		    		return;
				}else if(!checkInteger(value)){
					document.getElementById("dialog-1").innerHTML = "Please enter valid debit balance. ";
		    		alertdialogue();
		    		return;
				}else if(value){
					if(parseFloat(value)>0)
						dlbalEle.value = "-"+value;
					$(this).dialog("close");
				}	
			},
			"Close": function() {
			    var trcount = document.getElementById('mpopup_bank_add_table_body').getElementsByTagName('tr').length;
				var curRow = trcount-1 ;
				var ele = document.getElementsByClassName("bdc");
				if(ele.length==1) {
					if(formobj.bank_code.value == "OVER DRAFT"){
						formobj.bank_code.value = 0;
						document.getElementById("odinstrn").style.display = "none";
					}
				}else if(ele.length>1) {
					if(formobj.bank_code[curRow].value == "OVER DRAFT"){
						formobj.bank_code[curRow].value = 0;
						document.getElementById("odinstrn").style.display = "none";
					}
				}
				$(this).dialog("close");
			}
		}
	});
}

function checkForDeletedmBankAccounts(bobj){
	var formobj = document.getElementById('mpopup_bank_data_form');
//	var clsname = bobj.className;
//	var ele = document.getElementsByClassName(clsname);
	var ele = document.getElementsByClassName("bdc");
	if(ele.length==1) {
		var baccno = formobj.bank_accno.value.trim();
		var sbname = document.getElementById("bank_name").options;
		var sbtype = document.getElementById("bank_code").options;
		for(var d=0;d<bank_data.length;d++){
			if((bank_data[d].deleted != 0) && (bank_data[d].bank_acc_no === baccno)) {
				// FOR BANK NAME
				var bname = bank_data[d].bank_name;
				enableSelect(sbname,sbname.length);
				formobj.bank_name.selectedIndex= parseInt(bname);
				disableSelect(sbname,sbname.length);
					
				//FOR BANK TYPE
				var btype = bank_data[d].bank_code;
				enableSelect(sbtype,sbtype.length);
				switch(btype){
					case "SAVINGS" :
						formobj.bank_code.selectedIndex=1;
						disableSelect(sbtype,sbtype.length);
						break;
					case "CURRENT" :
						formobj.bank_code.selectedIndex=2;
						disableSelect(sbtype,sbtype.length);
						break;
					case "LOAN" :
						formobj.bank_code.selectedIndex=3;
						disableSelect(sbtype,sbtype.length);
						break;
					case "OVER DRAFT" :
						formobj.bank_code.selectedIndex=4;
						disableSelect(sbtype,sbtype.length);
						break;
					default: 
						formobj.bank_code.selectedIndex=0;
						enableSelect(sbtype,sbtype.length);
						break;
				}
					
				// For BANK BRANCH
				var branch = bank_data[d].bank_branch;
				formobj.bank_branch.value = branch;
				document.getElementById("bank_branch").setAttribute("readOnly","true");
				
				// For BANK IFSC
				var bifsc = bank_data[d].bank_ifsc_code;
				formobj.bank_ifsc.value = bifsc;
				document.getElementById("bank_ifsc").setAttribute("readOnly","true");
				
				// For BANK ADDRESS
				var baddr = bank_data[d].bank_addr;
				formobj.bank_addr.value = baddr;
				document.getElementById("bank_addr").setAttribute("readOnly","true");
					
				// For BANK OPENING BALANCE
				var bankob = bank_data[d].acc_cb;
				formobj.bank_ob.value = bankob;
				document.getElementById("bank_ob").setAttribute("readOnly","true");	
				
				break;
			}else if((bank_data[d].deleted != 0) && (bank_data[d].bank_acc_no != baccno)) {
				// FOR BANK NAME
//				formobj.bank_name.selectedIndex=0;
				enableSelect(sbname,sbname.length);
				
				//FOR BANK TYPE
//				formobj.bank_code.selectedIndex=0;
				enableSelect(sbtype,sbtype.length);
					
				// For BANK BRANCH
//				formobj.bank_branch.value = "";
				document.getElementById("bank_branch").readOnly = false;
 					
				// For BANK IFSC
//				formobj.bank_ifsc.value = "";
				document.getElementById("bank_ifsc").readOnly = false;
					
				// For BANK ADDRESS
//				formobj.bank_addr.value = "";
				document.getElementById("bank_addr").readOnly = false;
				
				// For BANK OPENING BALANCE
//				formobj.bank_ob.value = "";
				document.getElementById("bank_ob").readOnly = false;
			}
		}
	}else if(ele.length>1) {
		for(var e=0;e<ele.length;e++) {
			var baccno = formobj.bank_accno[e].value.trim();
			var sbname = formobj.bank_name[e].options;
			var sbtype = formobj.bank_code[e].options;
			for(var d=0;d<bank_data.length;d++) {
				if((bank_data[d].deleted != 0) && (bank_data[d].bank_acc_no === baccno)) {
					// FOR BANK NAME
					var bname = bank_data[d].bank_name;
					enableSelect(sbname,sbname.length);
					formobj.bank_name[e].selectedIndex= parseInt(bname);
					disableSelect(sbname,sbname.length);
					
					//FOR BANK TYPE
					var btype = bank_data[d].bank_code;
					enableSelect(sbtype,sbtype.length);
					switch(btype){
						case "SAVINGS" :
							formobj.bank_code[e].selectedIndex=1;
							disableSelect(sbtype,sbtype.length);
							break;
						case "CURRENT" :
							formobj.bank_code[e].selectedIndex=2;
							disableSelect(sbtype,sbtype.length);
							break;
						case "LOAN" :
							formobj.bank_code[e].selectedIndex=3;
							disableSelect(sbtype,sbtype.length);
							break;
						case "OVER DRAFT" :
							formobj.bank_code[e].selectedIndex=4;
							disableSelect(sbtype,sbtype.length);
							break;
						default: 
							formobj.bank_code[e].selectedIndex=0;
							enableSelect(sbtype,sbtype.length);
							break;
					}
						
					// For BANK BRANCH
					var branch = bank_data[d].bank_branch;
					formobj.bank_branch[e].value = branch;
					formobj.bank_branch[e].setAttribute("readOnly","true");
					
					// For BANK IFSC
					var bifsc = bank_data[d].bank_ifsc_code;
					formobj.bank_ifsc[e].value = bifsc;
					formobj.bank_ifsc[e].setAttribute("readOnly","true");
						
					// For BANK ADDRESS
					var baddr = bank_data[d].bank_addr;
					formobj.bank_addr[e].value = baddr;
					formobj.bank_addr[e].setAttribute("readOnly","true");
					
					// For BANK OPENING BALANCE
					var bankob = bank_data[d].acc_cb;
					formobj.bank_ob[e].value = bankob;
					formobj.bank_ob[e].setAttribute("readOnly","true");
					
					break;
					
				}else if(((bank_data[d].deleted != 0)) && (bank_data[d].bank_acc_no != baccno)) {
					// FOR BANK NAME
//					formobj.bank_name[e].selectedIndex=0;
					enableSelect(sbname,sbname.length);
					
					//FOR BANK TYPE
//					formobj.bank_code[e].selectedIndex=0;
					enableSelect(sbtype,sbtype.length);
						
					// For BANK BRANCH
//					formobj.bank_branch[e].value = "";
					formobj.bank_branch[e].readOnly = false;
 						
					// For BANK IFSC
//					formobj.bank_ifsc[e].value = "";
					formobj.bank_ifsc[e].readOnly = false;
					
					// For BANK ADDRESS
//					formobj.bank_addr[e].value = "";
					formobj.bank_addr[e].readOnly = false;
						
					// For BANK OPENING BALANCE
//					formobj.bank_ob[e].value = "";
					formobj.bank_ob[e].readOnly = false;
				}
			}
		}		
	}	
}

function savemBankPopupData(obj) {
	var formobj = document.getElementById('mpopup_bank_data_form');
	var ems = "";
	var eaccta =new Array();
	var accflag = 0;

	if (document.getElementById("bank_name") != null) {

		var elements = document.getElementsByClassName("bdc");
		if (elements.length == 1) {
			var ebname = formobj.bank_name.selectedIndex;
			var ebcode = formobj.bank_code.selectedIndex;
			var ebaccno = formobj.bank_accno.value.trim();
			var ebbranch = formobj.bank_branch.value.trim();
			var ebifsc = formobj.bank_ifsc.value.trim();
			var ebaddr = formobj.bank_addr.value.trim();
			var ebob = formobj.bank_ob.value.trim();

			ems = validatemBDpopupEntries(ebname, ebcode, ebaccno, ebbranch, ebifsc,ebaddr, ebob,accflag);
			
			formobj.bank_accno.value = formobj.bank_accno.value.trim();
			formobj.bank_branch.value = formobj.bank_branch.value.trim();
			formobj.bank_ifsc.value = formobj.bank_ifsc.value.trim();
			formobj.bank_addr.value = formobj.bank_addr.value.trim();
			formobj.bank_ob.value = formobj.bank_ob.value.trim();
		} else if (elements.length > 1) {
			for (var i = 0; i < elements.length; i++) {
				var ebname = formobj.bank_name[i].selectedIndex;
				var ebcode = formobj.bank_code[i].selectedIndex;
				var ebaccno = formobj.bank_accno[i].value.trim();
				var ebbranch = formobj.bank_branch[i].value.trim();
				var ebifsc = formobj.bank_ifsc[i].value.trim();
				var ebaddr = formobj.bank_addr[i].value.trim();
				var ebob = formobj.bank_ob[i].value.trim();
				if(eaccta.includes(ebaccno)){
					accflag = 1;
				}
				eaccta.push(ebaccno);
				ems = validatemBDpopupEntries(ebname, ebcode, ebaccno, ebbranch,ebifsc, ebaddr, ebob,accflag);

				if (ems.length > 0)
					break;
				
				formobj.bank_accno[i].value = formobj.bank_accno[i].value.trim();
				formobj.bank_branch[i].value = formobj.bank_branch[i].value.trim();
				formobj.bank_ifsc[i].value = formobj.bank_ifsc[i].value.trim();
				formobj.bank_addr[i].value = formobj.bank_addr[i].value.trim();
				formobj.bank_ob[i].value = formobj.bank_ob[i].value.trim();
			}
		}
	} else {
		document.getElementById("dialog-1").innerHTML = "PLEASE ADD DATA";
		alertdialogue();
		//alert("PLEASE ADD DATA");
		return;
	}

	if (ems.length > 0) {
		document.getElementById("dialog-1").innerHTML = ems;
		alertdialogue();
		//alert(ems);
		return;
	}
	
	var objId = obj.id;
	document.getElementById(objId).disabled = true;
	formobj.submit();
}

/*function mBankdeleteItem(did) {

	if (confirm("Are you sure you want to delete?") == true) {
		var formobj = document.getElementById('mpopup_bank_data_form');
		formobj.actionId.value = "3513";
		formobj.bankDataId.value = did;
		formobj.submit();
	}
}*/

function mBankdeleteItem(id){
	 $("#myDialogText").text("Are You Sure You Want To Delete?");
	 var formobj = document.getElementById('mpopup_bank_data_form');
	 confirmDialogue(formobj,3513,id);
}

function validatemBDpopupEntries(bname, code, accno, branch, ifsc, addr, ob,accflag) {
	var errmsg = "";
	var flag  = 0;
	var formobj = document.getElementById('mpopup_bank_data_form');

	/*if(eaccta.length>1 && (!(eaccta.indexOf(accno))))
		 flag =1;*/
	for(var f=0; f<bank_data.length; f++){
		if((bank_data[f].bank_acc_no == accno) && (bank_data[f].deleted==0))
			flag =1;
	}
	
	if (!(bname > 0))
		errmsg = "Please Select BANK NAME.<br>";

	if (!(code > 0))
		errmsg = errmsg + "Please select ACCOUNT TYPE<br>";

	if (!(accno.length > 0))
		errmsg = errmsg + "Please Enter ACCOUNT NUMBER<br>";
	else if (!(checkNumber(accno)))
		errmsg = errmsg + "Invalid ACCOUNT NUMBER<br>";
	else if (accno.length<10)
		errmsg = errmsg + "ACCOUNT NUMBER Must Contain Atleast Ten Digits<br>";
	else if(flag == 1 || accflag == 1)
		errmsg =errmsg + "ACCOUNT NUMBER already exist.Please enter different ACCOUNT NUMBER<br>";
	
	if (!(branch.length > 0))
		errmsg = errmsg	+ "Please Enter BANK BRANCH.Enter NA If Not Available.<br>";
/*	else if (!(IsText(branch)))
		errmsg = errmsg + " BRANCH Must Contain Only Alphabets<br>";
*/	else if (branch.length<2)
		errmsg = errmsg + "BRANCH Must Contain Atleast Two Characters<br>";

	if (!(ifsc.length > 0))
		errmsg = errmsg + "Please Enter IFSC Code<br>";
	else if (!(ifsc.length == 11))
		errmsg = errmsg + " IFSC CODE Must Contain 11 Characters<br>";
	else if (!(checkIFSC(ifsc)))
		errmsg = errmsg + "Please Enter Valid IFSC CODE.<br>";

	if (!(addr.length > 0))
		errmsg = errmsg + "Please Enter BANK ADDRESS.Enter NA If Not Available.<br>";

	if (!(ob.length > 0))
		errmsg = errmsg + "Please Enter OPENING BALANCE.<br>";
	else if (validateDot(ob))
		errmsg = errmsg + "OPENING BALANCE Must Contain Atleast One Number. <br>";
	else if (!checkInteger(ob))
		errmsg = errmsg + "OPENING BALANCE Must Contain Only Numerics. <br>";
	else if (!(parseFloat(ob) < 100000000))
		errmsg = errmsg + "OPENING BALANCE Must Be Less Than 10 Crores.<br>";
	else
		formobj.bank_ob.value = round(parseFloat(ob), 2);

	return errmsg;
}
