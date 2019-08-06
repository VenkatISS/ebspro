/*function showNCDBCFormDialog() {
	var check = checkDayEndDateForAdd(dedate,a_created_date,effdate);
	if(check==true) {
		document.getElementById('myModal').style.display = "block";
		showBOMForm();
	}else if(check!=true) {
		document.getElementById("dialog-1").innerHTML = check;
		alertdialogue();
		return;
	}
}
*/

function showNCDBCFormDialog() {
	document.getElementById('myModal').style.display = "block";
	showBOMForm();
	pushMenu();
}


function showLoanAmountBox(){
	var formobj = document.getElementById('data_form');
	var ctype= formobj.ctype.value;
	//var ncuc= formobj.ncULtype.checked;
	//var spfrz= document.getElementById("bid").options;
	if((ctype == 3) || (ctype == 4)) {
		document.getElementById('aro').style.display="none";
		document.getElementById('bankdiv').style.display="none";
		document.getElementById('ujwalLoan').style.display="block";
		document.getElementById('uloanSpan').innerHTML = "<b>LOAN AMOUNT</b><br><input type='text' class='form-control input_field' id='ula' name='ula' readonly='readonly' placeholder='LOAN AMOUNT'>";
	} else {
		document.getElementById('bank_id').style.display="block";
		document.getElementById('ujwalLoan').style.display="none";
		document.getElementById('aro').style.display="block";
		document.getElementById('bankdiv').style.display="block";		
	}
	
}

function closeNCDBCFormDialog() {
	
	$(':radio:not(:checked)').attr('disabled', false);

	var efrz = document.getElementsByClassName("freez");
	remEleReadOnly(efrz,efrz.length);

	document.getElementById("data_form").reset();
	var tbody = document.getElementById('b_data_table_body');	
	var count = document.getElementById('b_data_table_body').getElementsByTagName('tr').length;
	for(var a=0; a<count; a++){
		tbody.deleteRow(-1);
	}			
	document.getElementById('myModal').style.display = "none";
    $("body").removeClass('sidebar-collapse').trigger('expanded.pushMenu');

}


//set bank account id
if(bank_data.length>0){
	for(var y=0; y<bank_data.length; y++) {
		if(bank_data[y].bank_code == "STAR ACCOUNT") {
			document.getElementById('data_form').bankId.value = bank_data[y].id;
			document.getElementById('m_data_form').bankId.value = bank_data[y].id;
			break;
		}
	}
}

function showBOMForm(){	
	var bids = document.getElementById("bom_id");
	var bidsv = bids.options[bids.selectedIndex].value;
	if(!(bidsv > 0)) {
		document.getElementById('myModal').style.display = "none";
		document.getElementById("dialog-1").innerHTML = "Please select a BOM";
		alertdialogueWithCollapse();
		//alert("Please select a BOM");
		
		$('#b_sb').click(function() {
			$('.sidebar-mini').css("overflow","auto");
		});
		return;
	}
	
	$('#b_sb').click(function() {
		$('.sidebar-mini').css("overflow","hidden");
	});	
	
	var tbody = document.getElementById('b_data_table_body');

	for(var a=0; a<bom_data.length; a++){	
		if(bom_data[a].id == bidsv){
			var bomItems = bom_data[a].bomItemsSet;
			
			if(bom_data[a].bom_type == 1) {
				document.getElementById('nctype').checked=true;
			}else if(bom_data[a].bom_type == 2) {
				document.getElementById('dbctype').checked=true;
			}else if(bom_data[a].bom_type == 3) {
				document.getElementById('ncULtype').checked=true;
			}else if(bom_data[a].bom_type == 4) {
				document.getElementById('ncUCtype').checked=true;
			}
					
			$(':radio:not(:checked)').attr('disabled', true);

			for(var b=0; b<bomItems.length; b++) {
				var prodId = bomItems[b].prod_code;		
				var spd = "";
				if(prodId < 100) {
					spd = fetchProductDetails(prod_types, prodId);
				} else {
					spd = fetchARBProductDetails(cat_arb_types_data, prodId);
				}
				var tblRow = tbody.insertRow(-1);
				tblRow.align="left";
				tblRow.innerHTML = "<tr><td><input type='text' class='form-control input_field' id='pd' name='pd' title='" + spd + "' value='"+spd+"'> <input type='hidden' class='form-control input_field' id='pid' name='pid' title='" + bomItems[b].prod_code + "' value='"+bomItems[b].prod_code+"'> </td>" 
					+ "<td> <input type='text' class='form-control input_field' id='qty' name='qty' size='8' title='" + bomItems[b].qty + "' value='"+bomItems[b].qty+"' readonly='readonly' style='background-color:#F3F3F3;'> </td>"
					+ "<td> <input type='text' class='form-control input_field upc' id='up' name='up' size='8' readonly='readonly' style='background-color:#FAFAC2;' placeholder ='UNIT RATE'> </td>"
					+ "<td> <input type='text' class='form-control input_field' id='gstp' name='gstp' size='8' readonly='readonly' style='background-color:#FAFAC2;' placeholder ='GST %'> </td>"
					+ "<td> <input type='text' class='form-control input_field' id='da' name='da' size='8' readonly='readonly' style='background-color:#FAFAC2;' placeholder ='DEPOSIT'> <input type='hidden' id='dr' name='dr' value='"+bomItems[b].deposit_req+"'> </td>"
					+ "<td> <input type='text' class='form-control input_field freez' id='dup' name='dup' maxlength='6' size='8' value='0.00' placeholder='Discount On UnitPrice'> </td>"
					+ "<td> <input type='text' class='form-control input_field' id='bp' name='bp' size='8' value='' readonly='readonly' style='background-color:#F3F3F3;' placeholder ='BASIC PRICE'> </td>"
					+ "<td> <input type='text' class='form-control input_field' id='cgsta' name='cgsta' size='8' value='' readonly='readonly' style='background-color:#F3F3F3;' placeholder ='CGST AMOUNT'> </td>"
					+ "<td> <input type='text' class='form-control input_field' id='sgsta' name='sgsta' size='8' value='' readonly='readonly' style='background-color:#F3F3F3;' placeholder ='SGST AMOUNT'> </td>"
					+ "<td> <input type='text' class='form-control input_field' id='pa' name='pa' size='8' value='' readonly='readonly' style='background-color:#F3F3F3;' placeholder ='PRODUCT'> </td></tr>";
			}
		}
	}
	
	document.getElementById("modal-content").style.display = "block";
}

//Process page data
var rowpdhtml = "";
if(page_data.length>0) {
	for(var z=0; z<page_data.length; z++){
		var ed = new Date(page_data[z].inv_date);
		var staffName = getStaffName(staff_data,page_data[z].staff_id);
		var bankName1 = getBankName(bank_data,page_data[z].bank_id);
		var amtRcdO1 = page_data[z].online_amount;
		var connType = page_data[z].conn_type;
		var bankName ="";
		var amtRcdO = "";
		var ujwlamt = "";
		var del = "<td align='center'><img src='images/delete.png' onclick='deleteItem("+page_data[z].id+","+page_data[z].inv_date+")'></td>";
		if(page_data[z].deleted==2){
			del = "<td>TRANSACTION CANCELED</td>";
		}
		if(bankName1 == undefined){
			bankName = "NA";
		}else{
			bankName = bankName1;
		}
		if(amtRcdO1 == ""){
			amtRcdO = "<b>-</b>";
		}else{
			amtRcdO = amtRcdO1;
		}
		if(connType  == 3){
			ujwlamt = page_data[z].inv_amount;
		}else {
			ujwlamt ="<b>-</b>";
		}

	/*	----------------------------------------------------------------------------------------------------------------------------------------------------------------/*/
		//rowpdhtml = rowpdhtml + "<tr valign='top'><td rowspan='"+page_data[z].detailsList.length+"'>"+page_data[z].id+"</td>";
		if(page_data[z].deleted==2){
			rowpdhtml = rowpdhtml + "<tr valign='top'><td rowspan='"+page_data[z].detailsList.length+"'>"+page_data[z].sr_no+"</td>";
		}else {
			rowpdhtml = rowpdhtml + "<tr valign='top'><td rowspan='"+page_data[z].detailsList.length+"'><a href='javascript:showInvoice("+page_data[z].id+","+page_data[z].inv_date+")'>"+page_data[z].sr_no+"</a></td>"			
		}
		
		rowpdhtml = rowpdhtml + "<td rowspan='"+page_data[z].detailsList.length+"'>"+ed.getDate()+"-"+months[ed.getMonth()]+"-"+ed.getFullYear()+"</td>";
		rowpdhtml = rowpdhtml + "<td rowspan='"+page_data[z].detailsList.length+"'>"+staffName+"</td>";
		rowpdhtml = rowpdhtml + "<td rowspan='"+page_data[z].detailsList.length+"'>"+page_data[z].customer_no+"</td>";
		rowpdhtml = rowpdhtml + "<td rowspan='"+page_data[z].detailsList.length+"'>"+page_data[z].inv_amount+"</td>";
		rowpdhtml = rowpdhtml + "<td rowspan='"+page_data[z].detailsList.length+"'>"+page_data[z].no_of_conns+"</td>";
		rowpdhtml = rowpdhtml + "<td rowspan='"+page_data[z].detailsList.length+"'>"+page_data[z].cash_amount+"</td>";
		rowpdhtml = rowpdhtml + "<td rowspan='"+page_data[z].detailsList.length+"'>"+amtRcdO+"</td>";
		rowpdhtml = rowpdhtml + "<td rowspan='"+page_data[z].detailsList.length+"'>"+ujwlamt+"</td>";
		rowpdhtml = rowpdhtml + "<td rowspan='"+page_data[z].detailsList.length+"'>"+bankName+"</td>";

		for(var i=0 ; i<page_data[z].detailsList.length; i++){
			var spd = "";
			var prodId = page_data[z].detailsList[i].prod_code;
			if(prodId < 100) {
				spd = fetchProductDetails(prod_types, page_data[z].detailsList[i].prod_code);
			} else {
				spd = fetchARBProductDetails(cat_arb_types_data, page_data[z].detailsList[i].prod_code);
			}
			if(i==0) {		
				rowpdhtml = rowpdhtml + "<td>"+spd+"</td>"+
				"<td>"+page_data[z].detailsList[i].quantity+"</td>"+
				"<td>"+page_data[z].detailsList[i].deposit_amount+"</td>"+
				"<td>"+page_data[z].detailsList[i].unit_rate+"</td>"+
				"<td>"+page_data[z].detailsList[i].disc_unit_rate+"</td>"+
				"<td>"+page_data[z].detailsList[i].cgst_amount+"</td>"+
				"<td>"+page_data[z].detailsList[i].sgst_amount+"</td>"+
				"<td>"+page_data[z].detailsList[i].product_amount+"</td>"+
				del+
		        "</tr>";
		
			} else {				
				rowpdhtml = rowpdhtml + "<tr><td>"+spd+"</td>"+
				"<td>"+page_data[z].detailsList[i].quantity+"</td>"+
				"<td>"+page_data[z].detailsList[i].deposit_amount+"</td>"+
				"<td>"+page_data[z].detailsList[i].unit_rate+"</td>"+
				"<td>"+page_data[z].detailsList[i].disc_unit_rate+"</td>"+
				"<td>"+page_data[z].detailsList[i].cgst_amount+"</td>"+
				"<td>"+page_data[z].detailsList[i].sgst_amount+"</td>"+
				"<td>"+page_data[z].detailsList[i].product_amount+"</td>"+
				"<td></td></tr>";
			}
		}
	}
}
document.getElementById('m_data_table_body').innerHTML = rowpdhtml;

//Construct Category Type html
ctdatahtml = "<OPTION VALUE='0'>SELECT</OPTION>";
ctdatahtml = ctdatahtml + "<OPTION VALUE='-1' disabled>---CYLINDER LIST---</OPTION>";
rtdatahtml = "<OPTION VALUE='0'>SELECT</OPTION>";
rtdatahtml = rtdatahtml + "<OPTION VALUE='-1' disabled>---REGULATOR LIST---</OPTION>";
if(cat_cyl_types_data.length>0) {
	for(var z=0; z<cat_cyl_types_data.length; z++){
		if(cat_cyl_types_data[z].cat_type!=3) {
			ctdatahtml=ctdatahtml+"<OPTION VALUE='"+cat_cyl_types_data[z].id+"'>"
			+cat_cyl_types_data[z].cat_name+"-"+cat_cyl_types_data[z].cat_desc+"</OPTION>";
		} else {
			rtdatahtml=rtdatahtml+"<OPTION VALUE='"+cat_cyl_types_data[z].id+"'>"
			+cat_cyl_types_data[z].cat_name+"-"+cat_cyl_types_data[z].cat_desc+"</OPTION>";
		}
	}
}

//Construct Staff html
staffdatahtml = "<OPTION VALUE='0'>SELECT</OPTION>";
if(staff_data.length>0) {
	for(var z=0; z<staff_data.length; z++){
		if(staff_data[z].deleted == 0){
			staffdatahtml=staffdatahtml+"<OPTION VALUE='"+staff_data[z].id+"'>"+staff_data[z].emp_name+"</OPTION>";
		}
	}
}
document.getElementById("sSpan").innerHTML = "<select id='staff_id' class='form-control input_field select_dropdown' name='staff_id'>"+staffdatahtml+"</select>";

function saveData(obj) {
	var formobj = document.getElementById('data_form');
	var bids = document.getElementById("bom_id");
	var bidsv = bids.options[bids.selectedIndex].value;
	var ctype = document.getElementById('data_form').ctype.value;
	formobj.bomId.value = bidsv;
	var ems = "";
	var ula  = 0;
	if(document.getElementById("pid") != null){
		var inv_date = formobj.inv_date.value.trim();
		var staff_id = formobj.staff_id.value.trim();
		var bank_id = formobj.bank_id.value.trim();
		var custn = formobj.custn.value.trim();
		var invamt = formobj.invamt.value.trim();
		var nos = formobj.nos.value.trim();
		var cashr = formobj.cashr.value.trim();
		var aro = formobj.aro.value.trim();
		if(ctype == 3){
			ula = formobj.ula.value.trim();
		}
		if(checkDateFormate(inv_date)) {
			var invdate = dateConvert(inv_date);
			formobj.ed.value = invdate;
			inv_date=invdate;
		}
		ems = validateEntries(inv_date,staff_id,bank_id,custn,invamt,nos,cashr,aro,ula);
	} else {
		document.getElementById("dialog-1").innerHTML = "Please Add Data";
		alertdialogue();
		return;
	}
	
	if(ems.length>0) {
		document.getElementById("dialog-1").innerHTML = ems;
		alertdialogue();
		return;
	}else {
		var stockCheck = checkForStock();
		if(stockCheck==false) {
			document.getElementById("dialog-1").innerHTML = "Your BLPG/ARB/NFR items are out of stock.Please Check and add again";
			alertdialogue();
			return;
		}
	}

	var negbalmsg = checkForNegativeBalances(formobj);
	if(negbalmsg != true) {
		document.getElementById("dialog-1").innerHTML = negbalmsg;
		alertdialogue();
		return;
	}
	
	var objId = obj.id;
	document.getElementById(objId).disabled = true;	
	formobj.submit();
}

function checkForStock() {
	var formobj = document.getElementById('data_form');	
	if(document.getElementById("pid") != null) {
		var nos = formobj.nos.value.trim();
		var ele = document.getElementsByClassName("upc");
		if(ele.length==1) {
			var item = formobj.pid.value;
			var qty = formobj.qty.value.trim();
			var tqty = qty*nos;
			for(var e=0;e<arb_data.length;e++) {
				if(arb_data[e].id == parseInt(item)) {
					var stock = arb_data[e].current_stock-tqty;
					if(stock<0) {
						return false;
					} 
				}
			}
		}else if (ele.length>1) {
			for(var i=0; i<ele.length; i++) {
				var mitem = formobj.pid[i].value;
				var mqty = formobj.qty[i].value.trim();
				var mtqty = mqty*nos;
				for(var e=0;e<arb_data.length;e++) {
					if(arb_data[e].id == parseInt(mitem)) {
						var mstock = arb_data[e].current_stock-mtqty;
						if(mstock<0) {
							return false;
						}
					}
				}
			}
		}
	}
}

function checkForNegativeBalances(formobj) {
	var nos = formobj.nos.value.trim();
	var ele = document.getElementsByClassName("upc");
	var tdepamt = 0;
	if(ele.length==1) {
		var da = formobj.da.value;
		var damt = da*nos;
		tdepamt = damt ;
	}else if (ele.length>1) {
		for(var i=0; i<ele.length; i++) {
			var mda = formobj.da[i].value;
			var mdamt = mda*nos;
			tdepamt = +(tdepamt) + +(mdamt);
		}
	}
	
	var starcb = 0;
	if(bank_data.length>0) {
		for(var y=0; y<bank_data.length; y++) {
			if(bank_data[y].bank_code == "STAR ACCOUNT"){
				starcb = bank_data[y].acc_cb;
				break;
	    	}
	    }
	}
	
	if(parseFloat(tdepamt) > parseFloat(starcb)) {
		return "Your SV/TV ACCOUNT have no sufficient balance to complete this transaction. Please Check and add again";
	}else return true;
}

function validateEntries(inv_date,staff_id,bank_id,custn,invamt,nos,cashr,aro,ula){
	var errmsg = "";
	var vd = isValidDate(inv_date);
	var vfd = ValidateFutureDate(inv_date);
	var ctype =document.getElementById('data_form').ctype.value;
	if(!(inv_date.length>0))
		errmsg = "Please Enter INVOICE DATE<br>";
	else if (vd != "false")
		errmsg = errmsg +vd+"<br>";
	else if(validateDayEndAdd(inv_date,dedate)){
        errmsg = "INVOICE DATE should be after DayEndDate<br>";
        return errmsg;
	}else if (vfd != "false")
		errmsg = errmsg +vfd+"<br>";
/*	else {
		var lastTrxnDateCheck = checkforLastTrxnDate(dedate,a_created_date,inv_date,trdate);
		if(lastTrxnDateCheck != "false"){
			return lastTrxnDateCheck;
		}
	
//      var dayenddate=new Date(dedate);
//    	var agcrdate = new Date(a_created_date);
//    	var ddate = new Date(dayenddate.getFullYear(),dayenddate.getMonth(),dayenddate.getDate(),0,0,0);
//    	var acdate = new Date(agcrdate.getFullYear(),agcrdate.getMonth(),agcrdate.getDate(),0,0,0);

//    	if(!(acdate <= ddate)) {
//			var months = ["JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"];
//   		var sinvd = new Date(sidate);
//    		var sdate = new Date(sinvd.getFullYear(),sinvd.getMonth(),sinvd.getDate(),0,0,0);
//    		var txndate = new Date(new Date(trdate).getFullYear(),new Date(trdate).getMonth(),new Date(trdate).getDate(),0,0,0);
//    		var td;
//    		if(txndate.getDate()<10)
//    			td = "0"+txndate.getDate();
//    		else
//    			td = txndate.getDate();
	
//  	  	var tdate = td +"-"+ months[txndate.getMonth()] +"-"+ txndate.getFullYear();
//    		if(sdate < txndate) {
//    			errmsg = "Back dates are not allaowed. Your last transaction date is "+tdate+ "<br>Please check it and add again." ;
//    			return errmsg;
//    		}
//    	}
	}
*/
	
	if(!(staff_id>0))
		errmsg = errmsg+"Please Select STAFF<br>";
	if((!(bank_id>0)) && (aro != 0) )
		errmsg = errmsg+"Please Select BANK ACCOUNT<br>";
	else if((aro == 0) && (bank_id > 0) && (ula == 0) )
		errmsg = errmsg +"You Cannot Have Bank For AMOUNT RECEIVED ONLINE Zero<br>";
	else if((ula !=0) && (bank_id >0))
		errmsg = errmsg +"You Cannot Have Bank For Ujwala<br>";
	if(!(custn.length>0))
		errmsg = errmsg+"Please Enter CUST NO / CUST NAME.If Not Applicable Enter NA<br>";
	else if((custn.toUpperCase() != "NA") && (custn.length < 6))
		errmsg = errmsg+"CUST NO / CUST NAME Should Have Atleast 6 characters.<br>";
		
	if(!(nos.length>0))
		errmsg = errmsg+"Please Enter NO OF CONNECTIONS<br>";
	else if(!checkNumber(nos)) 
		errmsg = errmsg+"Please Enter Valid NO OF CONNECTIONS<br>";
	else if(nos == 0)
		errmsg = errmsg+"NO OF CONNECTIONS Can't Be Zero<br>";
	else if(ctype == 4){
		document.getElementById('cashr').value = invamt;
		cashr = invamt;
		document.getElementById('cashr').style.readonly = true; 
	    document.getElementById('ula').value = 0;
	    ula = 0;
	    aro = 0;
	}
    else if(ctype == 3){
		document.getElementById('ula').value = invamt;
		aro = 0;
	}

	
	if(!(cashr.length>0))
		errmsg = errmsg+"Please enter CASH RECEIVED<br>";
	else if(validateDot(cashr)) 
		errmsg = errmsg+"CASH RECEIVED Must Contain Atleast One Number. <br>";
	else if(!isDecimalNumber(cashr)) 
		errmsg = errmsg+"CASH RECEIVED Must Contain Only Numerics and can't be less than 0. <br>";
	
	
	if(!(aro.length>0) && (ctype != 3 && ctype != 4))
		errmsg = errmsg+"Please enter AMOUNT RECEIVED ONLINE<br>";
	else if(validateDot(aro) && (ctype != 3 && ctype != 4)) 
		errmsg = errmsg+"AMOUNT RECEIVED ONLINE Must Contain Atleast One Number <br>";
	else if(!isDecimalNumber(aro)) 
		errmsg = errmsg+"AMOUNT RECEIVED ONLINE Must Contain Only Numerics and can't be less than 0<br>";
	else if(cashr == 0 && ctype == 4 )
	     errmsg = errmsg+"Please Enter Cash RECEIVED.It Cannot be 0<br>";
	var ttlamt= Math.round(parseFloat(cashr)+parseFloat(aro)+parseFloat(ula));
	if((parseFloat(ttlamt) != parseFloat(invamt)) && ((cashr !=0 || aro != 0 ) && ula == 0))
		errmsg = errmsg+"SUM of CASH RECEIVED/AMOUNT RECEIVED ONLINE/LOAN AMOUNT must be equal to INVOICE AMOUNT<br>";
           			
	return errmsg;
}

/*function deleteItem(iid,ncdbcdate) {
	var flag=validateDayEndForDelete(ncdbcdate,dedate);
	if(flag) {
		if (confirm("Are you sure you want to delete?") == true) {
			var formobj = document.getElementById('m_data_form');
			formobj.actionId.value = "5723";
			formobj.dataId.value = iid;
			formobj.submit(); 
		}
	}else 
		alert("This record can't be deleted as you have submitted and verified your DAYEND calculations succuessfully... ");
	
}*/


function deleteItem(iid,ncdbcdate){
	var flag=validateDayEndForDelete(ncdbcdate,dedate);
	if(flag){
	 $("#myDialogText").text("Are You Sure You Want To Delete?");
	 var formobj = document.getElementById('m_data_form');
	 confirmDialogue(formobj,5723,iid);
	}else {
		document.getElementById("dialog-1").innerHTML = "This record can't be deleted as you have submitted and verified your DAYEND calculations succuessfully ";
		alertdialogue();
	}
}

function calculateValues() {
	var formobj = document.getElementById('data_form');
	var elements = document.getElementsByClassName("upc");
	var nocs = document.getElementById('nos').value;
	var ctype = document.getElementById('data_form').ctype.value;
	var totalAmt = 0;
	var totalDAmt = 0;
	if(elements.length==1) {
		var vpv = formobj.gstp.value.trim();
		var upv = formobj.up.value.trim();
		var updv = formobj.dup.value.trim();
		var qty = formobj.qty.value.trim();
		var depa = formobj.da.value.trim();
		var pid = formobj.pid.value.trim();
		
		var erms = validateCalcValues(nocs,updv);
		if(erms.length>0) {
			document.getElementById("dialog-1").innerHTML = erms;
			alertdialogue();
			//alert(erms);
			return;
		}else {
			formobj.bp.value = round(((upv-updv)*qty*nocs),2);
			if(pid==10 ||pid==11 || pid==12){
				formobj.sgsta.value="NA";
				formobj.cgsta.value="NA";
				formobj.pa.value = ((nocs * (upv-updv) * qty)).toFixed(2);
				totalDAmt = +totalDAmt + +(depa);
			}else {
				var gsta = ((((upv-updv)*qty)*vpv)/100).toFixed(2);
				formobj.sgsta.value=(nocs * (gsta/2)).toFixed(2);
				formobj.cgsta.value=(nocs * (gsta/2)).toFixed(2);
				formobj.pa.value = (+(nocs * (upv-updv) * qty) + +(formobj.sgsta.value) + +(formobj.cgsta.value)).toFixed(2);
				totalAmt = +totalAmt + +(formobj.pa.value);
				totalDAmt = +totalDAmt + +(depa);
			}			
			formobj.tdup.value = updv;
		}
	}else if(elements.length>1){
		var totdis = 0;
		for(var i=0; i<elements.length; i++){
			var vpv = formobj.gstp[i].value.trim();
			var upv = formobj.up[i].value.trim();
			var updv = formobj.dup[i].value.trim();
			var qty = formobj.qty[i].value.trim();
			var depa = formobj.da[i].value.trim();
			var pid = formobj.pid[i].value.trim();

			var erms = validateCalcValues(nocs,updv,upv);
			if(erms.length>0) {
				document.getElementById("dialog-1").innerHTML = erms;
				alertdialogue();
				//alert(erms);
				return;
			}else {
				formobj.bp[i].value = round(((upv-updv)*qty*nocs),2);
				if(pid==10 ||pid==11 || pid==12){
					formobj.sgsta[i].value="NA";
					formobj.cgsta[i].value="NA";
					formobj.pa[i].value = ((nocs * (upv-updv) * qty)).toFixed(2);
					totalDAmt = +totalDAmt + +(depa);
				}else {
					var gsta = ((((upv-updv)*qty)*vpv)/100).toFixed(2);
					formobj.sgsta[i].value=(nocs * (gsta/2)).toFixed(2);
					formobj.cgsta[i].value=(nocs * (gsta/2)).toFixed(2);
					formobj.pa[i].value = (+(nocs * (upv-updv) * qty) + +(formobj.sgsta[i].value) + +(formobj.cgsta[i].value)).toFixed(2);
					totalAmt = +totalAmt + +(formobj.pa[i].value);
					totalDAmt = +totalDAmt + +(depa);
				}

				totdis = +totdis + +(updv);				
			}
		}
		formobj.tdup.value = totdis;		
	}
	
	
	formobj.tdamt.value = nocs * totalDAmt;
	formobj.invamt.value=Math.round(+totalAmt + +(formobj.tdamt.value));
	if(ctype == 3){
		document.getElementById('ula').value = formobj.invamt.value;
		document.getElementById('cashr').value = 0;
		document.getElementById('cashr').setAttribute("readonly", true);
	}else if(ctype == 4){
		document.getElementById('cashr').value = formobj.invamt.value;
		document.getElementById('ula').value = 0;
		//document.getElementById('cashr').readOnly = true;
		document.getElementById('cashr').setAttribute("readonly", true);
	}
	document.getElementById("save_data").disabled=false;
	
	//var efrz = document.getElementsByClassName("freez");
	//makeEleReadOnly(efrz,efrz.length);
	restrictChangingAllValues(".freez");
}

function validateCalcValues(nos,updv,upv){
	var errmsg = "";

	if(!(nos.length>0)) 
		errmsg = "Please Enter NO. OF CONNECTIONS. <br>";
	else if(!checkNumber(nos))  
		errmsg = errmsg + "Please Enter Valid NO. OF CONNECTIONS. .<br>";
	
	if(!(updv.length>0)) 
		errmsg = errmsg + "Please Enter DISCOUNT ON UNIT PRICE.<br>";
	else if(parseFloat(upv)==0 &&(parseFloat(updv)!=0))
		errmsg = errmsg + "DISCOUNT ON UNITPRICE Must be 0 With Unit Price(0).<br>";
	
	else if(parseFloat(upv)!=0 &&(parseFloat(updv)>=parseFloat(upv)))
		errmsg = errmsg + "DISCOUNT ON UNITPRICE Must be Less than Unit Price.<br>";

	else if(validateDot(updv)) 
		errmsg = errmsg + "DISCOUNT ON UNITPRICE Must Contain Atleast One Number.<br>";
	else if(!isDecimalNumber(updv)) 
		errmsg = errmsg + "DISCOUNT ON UNITPRICE Must Contain Only Numerics. <br>";

	return errmsg;
}

function fetchURGSTDepositDetails(){
	/*
	var formobj = document.getElementById('data_form');
	var acgsta = fetchRCAdminCharges();
	var dgccgsta = fetchDGCCCharges();
	formobj.gsta.value = ( (+acgsta) + (+dgccgsta));
	*/
	fetchPriceAndVAT();
}


function fetchGSTPercentForNCDBC(pc_data, pcid) {
	for(var i=0; i< pc_data.length; i++){
		if(pc_data[i].prod_code == pcid)
			return pc_data[i].gstp;
	}
	return "false";
	
}

function fetchRefillUnitPriceForNCDBC(pc_data, pcid) {
	for(var i=0; i< pc_data.length; i++){
		if(pc_data[i].prod_code == pcid)
			return pc_data[i].base_price;
		
	}
	return "false";
}

function setUnitPriceValue(pidv) {
	var upv;
	if(pidv < 10) {
		upv = fetchRefillUnitPriceForNCDBC(refill_prices_data,pidv);
		return upv;
	}else if(pidv <= 12) {
		return 0;		
	}else if(pidv <= 100) {
		upv = fetchServiceAmount(services_data,pidv);
		return upv;
	}else { 
		upv = fetchARBUnitPrice(arb_prices_data,pidv);
		return upv;
	}		
}

function fetchServiceAmount(s_data,pid) {
	for(var i=0; i< s_data.length; i++){
		if(s_data[i].prod_code == pid)
			return s_data[i].prod_charges;
	}
	return "false";
}

function fetchARBUnitPrice(pc_data, pcid) {
	for(var i=0; i< pc_data.length; i++){
		if(pc_data[i].arb_code == pcid)
			return pc_data[i].base_price;
		
	}
	return "false";
}


/*function fetchPriceAndVAT() {
	var formobj = document.getElementById('data_form');
	var elements = document.getElementsByClassName("upc");
	for(var i=0; i<elements.length; i++){
		var pidv = formobj.pid[i].value;
		var gstv = pidv <= 12 ? fetchGSTPercentForNCDBC(equipment_data,pidv) : ( pidv <= 100 ? 18 : fetchARBGSTPercent(arb_data,pidv));
		var upv = setUnitPriceValue(pidv);
		if((gstv !="false")) {				
			if((upv != "false")) {
				formobj.gstp[i].value = gstv;
				formobj.up[i].value = upv;
				var yorn = formobj.dr[i].value;
				if(yorn == "NO") {
					formobj.da[i].value="0";
				} else {
					formobj.da[i].value = pidv <= 12 ? fetchRefillDeposit(equipment_data,pidv) : 0;
				}
				document.getElementById("calc_data").disabled=false;
			}else {
				alert("Please add the product's price in PRICE MASTER and FETCH again..");
				document.getElementById("calc_data").disabled=true;
				return;
			}
		}else {
			alert("Please add the Product in it's MASTER and FETCH again..");
			document.getElementById("calc_data").disabled=true;
			return;
		}	
	}		
}
*/

function fetchARBGSTPercent(pc_data, pcid) {
	for(var i=0; i< pc_data.length; i++){
		if((pc_data[i].id == pcid) && (pc_data[i].deleted ==0))
			return pc_data[i].gstp;
	}
	return "false";
}

/*
function fetchPriceAndVAT() {
	var formobj = document.getElementById('data_form');
	var elements = document.getElementsByClassName("upc");
	for(var i=0; i<elements.length; i++){
		var pidv = formobj.pid[i].value;
		var gstv = pidv <= 12 ? fetchGSTPercentForNCDBC(equipment_data,pidv) : ( pidv <= 100 ? 18 : fetchARBGSTPercent(arb_data,pidv));
		var upv = setUnitPriceValue(pidv);
		if((gstv !="false")) {
			if((upv != "false")) {
				formobj.gstp[i].value = gstv;
				formobj.up[i].value = upv;
				var yorn = formobj.dr[i].value;
				if(yorn == "NO") {
					formobj.da[i].value="0";
				}else {
					formobj.da[i].value = pidv <= 12 ? fetchRefillDeposit(equipment_data,pidv) : 0 ;
				}
				document.getElementById("calc_data").disabled=false;
			}else {
				document.getElementById("dialog-1").innerHTML = "Please add the product's price in PRICE MASTER and FETCH again..";
				alertdialogue();
				//alert("Please add the product's price in PRICE MASTER and FETCH again..");
				document.getElementById("calc_data").disabled=true;
				return;
			}
		}else {
			document.getElementById("dialog-1").innerHTML = "Please add the Product in it's MASTER and FETCH again..";
			alertdialogue();
			//alert("Please add the Product in it's MASTER and FETCH again..");
			document.getElementById("calc_data").disabled=true;
			return;
		}
	}
}
*/

function fetchPriceAndVAT() {
	var formobj = document.getElementById('data_form');
	var elements = document.getElementsByClassName("upc");

	var ncdate = formobj.inv_date.value;
	var sdate = new Date(ncdate);
	var millisecs = sdate.getTime();
	
	for(var i=0; i<elements.length; i++){
		var pidv = formobj.pid[i].value;
		var gstv = pidv <= 12 ? fetchGSTPercentForNCDBC(equipment_data,pidv) : ( pidv <= 100 ? 18 : fetchARBGSTPercent(arb_data,pidv));
		var upv = setUnitPriceValue(pidv);
		if((gstv !="false")) {
			if((upv != "false")) {
				
				var prodDateInMaster = 0;

				if(pidv <= 12){
					for(var e=0 ; e < equipment_data.length ; e++){
						if(equipment_data[e].prod_code == pidv){
							prodDateInMaster = equipment_data[e].effective_date;
							break;
						}
					}
				}else if(pidv > 100){
					for(var a=0 ; a < arb_data.length ; a++){
						if(arb_data[a].id == pidv){
							prodDateInMaster = arb_data[a].effective_date;
							break;
						}
					}
				}
				
				if(millisecs < prodDateInMaster){
					document.getElementById("calc_data").disabled = true;
					document.getElementById("dialog-1").innerHTML = "The product you have selected is invalid for the given date. Plese check effective date of the added product in its Master.";
					alertdialogue();
					//alert("please define the price of product for sale invoice month in price master and continue");
					return;
				}else {
					formobj.gstp[i].value = gstv;
					formobj.up[i].value = upv;
					var yorn = formobj.dr[i].value;
					if(yorn == "NO") {
						formobj.da[i].value="0";
					}else {
						formobj.da[i].value = pidv <= 12 ? fetchRefillDeposit(equipment_data,pidv) : 0 ;
					}
					document.getElementById("calc_data").disabled=false;
				}
			}else {
				document.getElementById("dialog-1").innerHTML = "Please add the product's price in PRICE MASTER and FETCH again..";
				alertdialogue();
				//alert("Please add the product's price in PRICE MASTER and FETCH again..");
				document.getElementById("calc_data").disabled=true;
				return;
			}
		}else {
			document.getElementById("dialog-1").innerHTML = "Please add the Product in it's MASTER and FETCH again..";
			alertdialogue();
			//alert("Please add the Product in it's MASTER and FETCH again..");
			document.getElementById("calc_data").disabled=true;
			return;
		}
	}
}

function fetchDeposit(spc){
	for(var z=0; z<equipment_data.length; z++){
		if(equipment_data[z].prod_code == spc) {
			return equipment_data[z].vatp;
		}
	}
}


function calculateVATPrice(ebp, spc){
	var cv = 0;
	for(var z=0; z<equipment_data.length; z++){
		if(equipment_data[z].prod_code == spc) {
			var vatv = equipment_data[z].vatp;
			cv = ((ebp*vatv)/100);
		}
	}
	return cv;
}

function showInvoice(inv_id,si_date){
	popitup(inv_id,si_date)
}

function popitup(inv_id,si_date) { 
	var w = window.open("PopupControlServlet?actionId=993&sitype=7&sid="+inv_id +"&si_date="+si_date,null,"height=400,width=1024,status=yes,toolbar=no,menubar=no,location=no");
	w.print();
	return false; 
}
