//for checking omc and vendor state.
function changeOMC() {
	$(':radio:not(:checked)').attr('disabled', false);
	document.getElementById("save_data").disabled=true;
}

/*
function showCPFormDialog() {
	var check = checkDayEndDateForAdd(dedate,a_created_date,effdate);
	if(check == true) {
		document.getElementById('myModal').style.display = "block";
	}else if(check!=true) {
		document.getElementById("dialog-1").innerHTML = check;
		alertdialogue();
		return;
	}
}
*/

function showCPFormDialog() {
	document.getElementById('myModal').style.display = "block";
	pushMenu();
}


function closeCPFormDialog() {
	document.getElementById("calc_data").disabled = true;
	document.getElementById("save_data").disabled = true;
	$(':radio:not(:checked)').attr('disabled', false);

	document.getElementById("data_form").reset();
	var tbody = document.getElementById('data_table_body');
	var count = document.getElementById('data_table_body').getElementsByTagName('tr').length;
	for(var a=0; a<count; a++){
		if(a>0)
			tbody.deleteRow(-1);
	}			
	document.getElementById('myModal').style.display = "none";
    $("body").removeClass('sidebar-collapse').trigger('expanded.pushMenu');

}


//set bank account id
if(bank_data.length>0){
	for(var y=0; y<bank_data.length; y++){
		if(bank_data[y].bank_code == "TAR ACCOUNT"){
			document.getElementById('data_form').bankId.value = bank_data[y].id;
			document.getElementById('m_data_form').bankId.value = bank_data[y].id;
			break;
    	}
    }
}


/*new 23032018*/

function showCVOFormDialog() {
	document.getElementById('cvoModal').style.display = "block";
	document.getElementById('savemCVOPopdiv').style.display="none";
    $("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');
}

function closeCVOFormDialog() {
	document.getElementById("mcvopopup_data_form").reset();
	document.getElementById('mymCVOPopupDIV').style.display = 'none';
	document.getElementById('mcvopopup_add_table_body').innerHTML = "";
	document.getElementById('cvoModal').style.display = "none";
$("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');
}

function showEquipmentFormDialog() {
	document.getElementById('equipmentModal').style.display = "block";
	document.getElementById('mequipopupsavediv').style.display="none";
    $("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');
}

function closeEquipmentFormDialog() {
//	document.getElementById("data_form").reset();
	document.getElementById("mequipopup_page_add_form").reset();
	document.getElementById('mequipopup_page_add_table_body').innerHTML = "";
	document.getElementById('mequipopup_page_add_table_div').style.display = 'none';
	document.getElementById('equipmentModal').style.display = "none";
$("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');
}

  /* new popup ends*/

var tbody = document.getElementById('m_data_table_body');
for(var f=page_data.length-1; f>=0; f--){
	var omcName = getCustomerName(cvo_data,page_data[f].omc_id);
	var invdate = new Date(page_data[f].inv_date);
	var srdate = new Date(page_data[f].stk_recvd_date);
	var spd = fetchProductDetails(cat_types_data, page_data[f].prod_code);
//	var eord=emrordelv[page_data[f].emr_or_delv];
	var eord=page_data[f].emr_or_delv;
	if(eord==1) {
		eord = "ONE WAY LOAD";
	}else if(eord==2){
		eord = "TWO WAY LOAD";
	} 
		
	var tblRow = tbody.insertRow(-1);
	tblRow.style.height="30px";
	tblRow.align="left";
	tblRow.innerHTML ='<td>'+ omc_name +"-"+ omcName +'</td>'+
			'<td>'+ page_data[f].inv_ref_no +'</td>'+
    		'<td>'+ invdate.getDate()+"-"+months[invdate.getMonth()]+"-"+invdate.getFullYear() +'</td>'+
    		'<td>'+ srdate.getDate()+"-"+months[srdate.getMonth()]+"-"+srdate.getFullYear() +'</td>'+
    		'<td>'+ spd +'</td>'+
    		'<td>'+ eord +'</td>'+
    		'<td>'+ page_data[f].unit_price +'</td>'+
    		'<td>'+ page_data[f].quantity +'</td>'+
    		'<td>'+ page_data[f].gstp +'</td>'+
    		'<td>'+ page_data[f].basic_amount +'</td>'+
		    '<td>'+ page_data[f].igst_amount + '</td>'+
			'<td>'+ page_data[f].sgst_amount +'</td>'+
			'<td>'+ page_data[f].cgst_amount +'</td>'+
			'<td>'+ page_data[f].other_charges + '</td>'+
			'<td>'+ page_data[f].c_amount +'</td>'+
			'<td valign="top" height="4" align="center"><img src="images/delete.png" class="fa fa-trash-o" onclick="deleteItem('+page_data[f].id+','+page_data[f].stk_recvd_date+')"></td>'
};

function addRow(){
	var trcount = document.getElementById('data_table_body').getElementsByTagName('tr').length;
	if(trcount >0){
		var trv=document.getElementById('data_table_body').getElementsByTagName('tr')[trcount-1];	
		var saddv=trv.getElementsByClassName('sadd');
		var eaddv=trv.getElementsByClassName('eadd');
		var res=checkRowData(saddv,eaddv);
		if(res == false){
			document.getElementById("dialog-1").innerHTML = "Please enter all the values in current row,calculate and then add next row";
			alertdialogue();
			//alert("Please enter all the values in current row,calculate and then add next row");
			return;
		}		
	}	
	document.getElementById("calc_data").disabled=true;
	document.getElementById("save_data").disabled=true;

	var ele = document.getElementsByClassName("tp");
	if(ele.length<4){
		var tbody = document.getElementById('data_table_body');
		var newRow = tbody.insertRow(-1);
		
		var a = newRow.insertCell(0);
		var b = newRow.insertCell(1);
		var c = newRow.insertCell(2);
		var d = newRow.insertCell(3);
		var e = newRow.insertCell(4);
		var f = newRow.insertCell(5);
		var g = newRow.insertCell(6);
		var h = newRow.insertCell(7);
		var i = newRow.insertCell(8);
		var j = newRow.insertCell(9);
		
		a.innerHTML = "<td valign='top' height='4' align='center'><select name='epid'  class='form-control input_field tp freez sadd blkcalc' id='epid' style='width:100px;'>"
			+ ctdatahtml + "</select></td>";
		b.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='vp' id='vp'  class='form-control input_field eadd'  size='8' readonly='readonly' style='background-color:#FAFAC2;' placeholder='Gst%'></td>";
		c.innerHTML = "<td valign='top' height='4' align='center'><select name='eord' id='eord'  class='form-control input_field freez sadd' style='width:100px;' disabled='disabled'>"
						+ "<OPTION VALUE='0'>SELECT</OPTION>"
						+ "<OPTION VALUE='2'>TWO WAY LOAD</OPTION>" 
						+ "<OPTION VALUE='1'>ONE WAY LOAD</OPTION>"						
						+ "</SELECT></td>";
		d.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='up' id='up'  class='form-control input_field freez eadd' maxlength='8' size='8' placeholder='Unit Price' readonly='readonly'></td>";
		e.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='qty' id='qty' size='8' class=' orm-control input_field qtyc freez eadd' maxlength='4'  placeholder='Quantity' readonly='readonly'></td>";
		f.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='oc' id='oc'  class='form-control input_field freez eadd' size='8' maxlength='7' value='0.00' placeholder='Other Charges' readonly='readonly'></td>";
		g.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='bp' id='bp' class='form-control input_field eadd'   size='8' readonly='readonly' style='background-color:#F3F3F3;'  placeholder='Taxable Amount' ></td>";
		h.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='gsta'  id='gsta' class='form-control input_field eadd' size='8' readonly='readonly' style='background-color:#F3F3F3;' placeholder='Gst Amount' ></td>";
		i.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='amt' id='amt'  class='form-control input_field eadd'  size='8' readonly='readonly' style='background-color:#F3F3F3;'  placeholder='Amount'></td>";
		j.innerHTML = "<td valign='top' height='4' align='center'><img src='images/delete.png' onclick='doRowDeleteInTranxs(this,amt,inv_amt,data_table_body)'>"
						+ "<input type=hidden name='igsta' id='igsta'>"
						+ "<input type=hidden name='sgsta' id='sgsta'>"
						+ "<input type=hidden name='cgsta' id='cgsta'>" + "</td>";
 	
		document.getElementById("fetch_data").disabled = false;
	}else {
		document.getElementById("dialog-1").innerHTML = "Please save the data and ADD again";
		alertdialogue();
		//alert("Please save the data and ADD again");
		return;		
	}	
}

function saveData(obj) {
	var formobj = document.getElementById('data_form');
	var ems = "";

	if (document.getElementById("qty") != null) {
		var eomc = formobj.omc_id.selectedIndex;
		var elements = document.getElementsByClassName("tp");
		var einvReNo = formobj.inv_ref_no.value.trim();
		var einvDate = formobj.inv_date.value;
		var estockRcdDate = formobj.s_r_date.value;
		
		if (elements.length == 1) {
			var eproduct = formobj.epid.value;
			var eemrORdeliv = formobj.eord.selectedIndex;
			var eunitPrice = formobj.up.value.trim();
			var ebp = formobj.bp.value;			
			var eqnty = formobj.qty.value.trim();
			var eotherchrgs = formobj.oc.value.trim();
			var evp = formobj.vp.value.trim();
			var egsta = formobj.gsta.value;
			var eamt = formobj.amt.value;
			var eigsta = formobj.igsta.value;
			var ecgsta = formobj.cgsta.value;
			var esgsta = formobj.sgsta.value;

			ems = validateEntries(einvReNo, einvDate, estockRcdDate, eomc,eproduct,
					eemrORdeliv, eunitPrice,ebp, eqnty, eotherchrgs, evp,egsta,eamt,eigsta,ecgsta,esgsta);
		} else if (elements.length > 1) {
			for (var i = 0; i < elements.length; i++) {
				var eproduct = formobj.epid[i].value;
				var eemrORdeliv = formobj.eord[i].selectedIndex;
				var eunitPrice = formobj.up[i].value.trim();
				var ebp = formobj.bp[i].value;
				var eqnty = formobj.qty[i].value.trim();
				var eotherchrgs = formobj.oc[i].value.trim();
				var evp = formobj.vp[i].value.trim();
				var egsta = formobj.gsta[i].value;
				var eamt = formobj.amt[i].value;
				var eigsta = formobj.igsta[i].value;
				var ecgsta = formobj.cgsta[i].value;
				var esgsta = formobj.sgsta[i].value;

				ems = validateEntries(einvReNo, einvDate, estockRcdDate, eomc,
						eproduct, eemrORdeliv, eunitPrice,ebp,eqnty, eotherchrgs,
						evp,egsta,eamt,eigsta,ecgsta,esgsta);
				if (ems.length > 0)
					break;
				
			}
		}
	} else {
		document.getElementById("dialog-1").innerHTML = "Please Add Data";
		alertdialogue();
		//alert("Please Add Data");
		return;
	}
	if (ems.length > 0) {
		document.getElementById("dialog-1").innerHTML = ems;
		alertdialogue();
		//alert(ems);
		return;
	}

	var negbalmsg = checkForNegativeBalances(formobj);
	if(negbalmsg == false) {
		$("#myDialogText").text("YOUR LOAD ACCOUNT HAVE NO SUFFICIENT BALANCE TO COMPLETE THIS TRANSACTION. DO YOU WANT TO CONTINUE?");
		confirmTxtDialogue(formobj);

	}
	
	var stockemptiesmsg = checkForTwoWayStockEmpties();
	if(stockemptiesmsg == false) {
		$("#myDialogText").text("YOU HAVE NO SUFFICIENT EMPTIES FOR THE EQUIPMENT SELECTED. DO YOU WANT TO CONTINUE?");
		confirmTxtDialogue(formobj);
	}
		
	if((negbalmsg != false) && (stockemptiesmsg != false)) {
		var objId = obj.id;
		document.getElementById(objId).disabled = true;
		formobj.submit();
	}		
}

function checkForTwoWayStockEmpties() {
	var formobj = document.getElementById('data_form');	
	if (document.getElementById("qty") != null) {
		var ele = document.getElementsByClassName("tp");
		if (ele.length == 1) {
			var item = formobj.epid.value;
			var emrORdeliv = formobj.eord.value;
			var qty = formobj.qty.value.trim();
			if(parseInt(emrORdeliv)==2) {
				for(var e=0;e<equipment_data.length;e++) {
					if(equipment_data[e].prod_code == parseInt(item)) {
						var stock = equipment_data[e].cs_emptys-qty;
						if(stock<0) {
							return false;
						}
					}
				}
			}
		}else if (ele.length>1) {
			for(var i=0; i<ele.length; i++){
				var mitem = formobj.epid[i].value;
				var memrORdeliv = formobj.eord[i].value;
				var mqty = formobj.qty[i].value.trim();
				if(parseInt(memrORdeliv)==2) {
					for(var e=0;e<equipment_data.length;e++) {
						if(equipment_data[e].prod_code == parseInt(mitem)) {
							var mstock = equipment_data[e].cs_emptys-mqty;
							if(mstock<0) {
								return false;
							} 
						}
					}
				}	
			}
		}
	}
}

function checkForNegativeBalances(formobj) {
	var invamt = formobj.inv_amt.value;
	var tarcb = 0;
	if(bank_data.length>0) {
		for(var y=0; y<bank_data.length; y++) {
			if(bank_data[y].bank_code == "TAR ACCOUNT"){
				tarcb = bank_data[y].acc_cb;
				break;
	    	}
	    }
	}
	
	if(parseInt(invamt) > parseFloat(tarcb)) {
		return false;
	}else return true;
	
}


/*function deleteItem(iid,srdate) {
	var flag=validateDayEndForDelete(srdate,dedate);
	if(flag) {
		if (confirm("Are you sure you want to delete?") == true) {
			var formobj = document.getElementById('m_data_form');
			formobj.actionId.value = "5103";
			formobj.dataId.value = iid;
			formobj.submit();
		}
	}else 
		document.getElementById("dialog-1").innerHTML = "This record can't be deleted as you have submitted and verified your DAYEND calculations succuessfully... ";
		alertdialogue();
		//alert("This record can't be deleted as you have submitted and verified your DAYEND calculations succuessfully... ");
	
}
*/

function deleteItem(iid,srdate){
	var flag=validateDayEndForDelete(srdate,dedate);
	if(flag){
	 $("#myDialogText").text("Are You Sure You Want To Delete?");
	 var formobj = document.getElementById('m_data_form');
	 confirmDialogue(formobj,5103,iid);
	}else {
		document.getElementById("dialog-1").innerHTML = "This record can't be deleted as you have submitted and verified your DAYEND calculations succuessfully";
		alertdialogue();
	}
}

function calculateValues() {
	var formobj = document.getElementById('data_form');
	if (document.getElementById("bp") != null) {
		if(formobj.omc_id.selectedIndex >0) {
			for(var d=0;d< cvo_data.length;d++){
				if(parseInt(formobj.omc_id.value) == cvo_data[d].id)
					cvogstin = cvo_data[d].cvo_tin;
			}
		}else {
			document.getElementById("dialog-1").innerHTML = "please select OMC";
			alertdialogue();
			//alert("please select OMC");
			return;
		}
		var dstcode = dealergstin.substr(0, 2);
		var cvstcode = cvogstin.substr(0, 2);
		var ptypecheck = "";
		if(cvstcode == dstcode)
			ptypecheck = "lp"; 
		else 
			ptypecheck = "isp";

		var elements = document.getElementsByClassName("qtyc");
		var ptypev = formobj.ptype.value;	
		if (elements.length == 1) {
			var upv = formobj.up.value.trim();
			var vpv = formobj.vp.value.trim();
			var qty = formobj.qty.value.trim();
			var ocv = formobj.oc.value.trim();
			var epid =formobj.epid.value.trim();
			var cemrORdeliv = formobj.eord.selectedIndex;
			var cproduct = formobj.epid.selectedIndex;
			var ems = validateEntries2(cproduct,cemrORdeliv,upv, qty, ocv, vpv,ptypecheck,ptypev);

			if (ems.length > 0) {
				document.getElementById("dialog-1").innerHTML = ems;
				alertdialogue();
				//alert(ems);
					return;
			}
			
			formobj.up.value = parseFloat(upv).toFixed(2);
			formobj.oc.value = parseFloat(ocv).toFixed(2);
			ocv = round((formobj.oc.value), 2);
			formobj.bp.value = (upv * qty).toFixed(2);
			if(epid==10 || epid==11){
//				var cgstv = ((upv * qty) / 100);
//				var agstv = cgstv.toFixed(2);
//				formobj.gsta.value = agstv;
//				formobj.amt.value = (+(formobj.bp.value) + (+agstv) + +ocv).toFixed(2);
//				formobj.inv_amt.value = Math.round(formobj.amt.value);
				formobj.gsta.value = 0;
				formobj.amt.value = (+(formobj.bp.value) + +ocv).toFixed(2);
				formobj.inv_amt.value = Math.round(formobj.amt.value);
			}else{
				var cgstv = (((upv * qty) * vpv) / 100);
				var agstv = cgstv.toFixed(2);
				formobj.gsta.value = agstv;
				formobj.amt.value = (+(formobj.bp.value) + (+agstv) + +ocv).toFixed(2);
				formobj.inv_amt.value = Math.round(formobj.amt.value);
			}
			
			if (ptypecheck == 'lp') {
				if(epid==10 || epid==11){
					formobj.sgsta.value = 0;
					formobj.cgsta.value = 0;
				}else {
					formobj.sgsta.value = (formobj.gsta.value) / 2;
					formobj.cgsta.value = (formobj.gsta.value) / 2;
				}
				formobj.igsta.value = 0;
			}else if (ptypecheck == 'isp') {
				if(epid==10 || epid==11){
					formobj.igsta.value = 0;
				}else {
					formobj.igsta.value = (formobj.gsta.value);
				}
				formobj.sgsta.value = 0;
				formobj.cgsta.value = 0;
			}
		}else if (elements.length > 1) {
			var totalAmt = 0;
			for (var i = 0; i < elements.length; i++) {
				var upv = formobj.up[i].value.trim();
				var vpv = formobj.vp[i].value.trim();
				var qty = formobj.qty[i].value.trim();
				var ocv = formobj.oc[i].value;
				var epid =formobj.epid[i].value.trim();

				var cemrORdeliv = formobj.eord[i].selectedIndex;
				var cproduct = formobj.epid[i].selectedIndex;
				var ems = validateEntries2(cproduct,cemrORdeliv,upv, qty, ocv, vpv,ptypecheck,ptypev);
				if (ems.length > 0) {
					document.getElementById("dialog-1").innerHTML = ems;
					alertdialogue();
					//alert(ems);
					return;
				}
				formobj.up[i].value = parseFloat(upv).toFixed(2);
				formobj.oc[i].value = parseFloat(ocv).toFixed(2);
				ocv = round((formobj.oc[i].value), 2);
				formobj.bp[i].value = (upv * qty).toFixed(2);
				if(epid==10 || epid==11){
//					var cgstv = ((upv * qty) / 100);
//					formobj.gsta[i].value = +cgstv.toFixed(2);
//					formobj.amt[i].value = (+(formobj.bp[i].value)+ +(formobj.gsta[i].value) + +ocv).toFixed(2);
//					totalAmt = +totalAmt + +(formobj.amt[i].value);
					formobj.gsta[i].value = "NA";
					formobj.amt[i].value = (+(formobj.bp[i].value) + +ocv).toFixed(2);
					totalAmt = +totalAmt + +(formobj.amt[i].value);					
				}else {
					var cgstv = (((upv * qty) * vpv) / 100);
					formobj.gsta[i].value = +cgstv.toFixed(2);
					formobj.amt[i].value = (+(formobj.bp[i].value)+ +(formobj.gsta[i].value) + +ocv).toFixed(2);
					totalAmt = +totalAmt + +(formobj.amt[i].value);
				}
				
				if (ptypecheck == 'lp') {
					if(epid==10 || epid==11){
						formobj.sgsta[i].value = 0;
						formobj.cgsta[i].value = 0;
					}else {
						formobj.sgsta[i].value = (formobj.gsta[i].value) / 2;
						formobj.cgsta[i].value = (formobj.gsta[i].value) / 2;						
					}
					formobj.igsta[i].value = 0;
				} else if (ptypecheck == 'isp') {
					if(epid==10 || epid==11){
						formobj.igsta[i].value = 0;
					}else {
						formobj.igsta[i].value = (formobj.gsta[i].value);						
					}
					formobj.sgsta[i].value = 0;
					formobj.cgsta[i].value = 0;
				}
			}
			formobj.inv_amt.value = Math.round(totalAmt);
		}

	} else {
		document.getElementById("dialog-1").innerHTML = "Please Add Cylinder Purchases Data and Click Calculate";
		alertdialogue();
		//alert("Please Add Cylinder Purchases Data and Click Calculate");
		return;
	}
	document.getElementById("save_data").disabled = false;
	$(':radio:not(:checked)').attr('disabled', true);
	restrictChangingAllValues(".freez",".blkcalc");
	
}

function calculateVATPrice(ebp, spc) {
	var cv = 0;
	for (var z = 0; z < equipment_data.length; z++) {
		if (equipment_data[z].prod_code == spc) {
			var vatv = equipment_data[z].vatp;
			cv = ((ebp * vatv) / 100);
		}
	}
	return cv;
}

function validateEntries(invReNo, invDate, stockRcdDate, omc,product, emrORdeliv,
		unitPrice,bp, qnty, otherchrgs, vp,egsta,eamt,eigsta,ecgsta,esgsta) {
	var errmsg = "";

	if (!(invReNo.length > 0))
		errmsg = "Please enter INVOICE REFERENCE NO.<br>";
	else if(invReNo.length<8)
		errmsg = "INVOICE REFERENCE Number must contain atleast 8 characters. <br>";
	else if(page_data){
		for(var i=0 ;i<page_data.length;i++) {
			var inv_ref_no =page_data[i].inv_ref_no;
			if(inv_ref_no == invReNo){
				errmsg = errmsg + "You have already added products under the entered INVOICE REFERENCE NO.Please Enter Valid INVOICE REFERENCE NO. <br>";
				break;
			}
		}
	}
	
	if(arb_pi_data){
		for(var ap=0 ; ap < arb_pi_data.length ; ap++) {
			var ap_inv_ref_no = arb_pi_data[ap].inv_ref_no;
			if(ap_inv_ref_no == invReNo){
				errmsg = errmsg + "You have already added products in BLPG/ARB/NFR PURCHASES under the entered INVOICE REFERENCE NUMBER .Please Enter Valid INVOICE REFERENCE NO. <br>";
				break;
			}
		}
	}
	
	var chkd = checkdate(invDate);
	var vd = isValidDate(invDate);
	
	var vfd = ValidateFutureDate(stockRcdDate);
	var ctd = comparisionofTwoDates(invDate, stockRcdDate);	
	
	if (!(omc > 0))
		errmsg = errmsg + "Please Select VENDOR.<br>";
	
	if (!(invDate.length > 0))
		errmsg = errmsg + "Please Enter INVOICE DATE.<br>";
	else if(chkd != "true")
		errmsg = errmsg+chkd+"<br>";
	else if(vd != "false")
		errmsg = errmsg+"INVOICE "+vd+".<br>";
	else if (!(stockRcdDate.length > 0))
		errmsg = errmsg + "Please Enter STOCK RECEIVED DATE.<br>";
	else if(ctd != "false")
		errmsg = errmsg + "INVOICE DATE must be earlier than STOCK RECEIVED DATE. <br>";
	else if(validateEffectiveDateForCVO(invDate,effdate)){
        errmsg = "INVOICE DATE should be after Effective Date that you have entered While First Time Login<br>";
        return errmsg;
	}else if(validateDayEndAdd(stockRcdDate,dedate)){
        errmsg = "STOCK RECEIVED DATE should be after DayEndDate<br>";
        return errmsg;
	}else if (vfd != "false")
		errmsg = errmsg +"STOCK RECEIVED"+vfd+"<br>";
/*	else {
		var lastTrxnDateCheck = checkforLastTrxnDate(dedate,a_created_date,invDate,trdate);
		if(lastTrxnDateCheck != "false") {
			return lastTrxnDateCheck;
		}
	}*/
	
	
	if($("input[type=radio][name=ptype]:checked").length <= 0) {
		errmsg = errmsg + "Please select PURCHASE TYPE<br>";
	}

	if (!(parseInt(product) > 0))
		errmsg = errmsg + "Please Select PRODUCT<br>";

	if (!(emrORdeliv > 0))
		errmsg = errmsg + "Please Select ONE WAY or TWO WAY LOAD <br>";

	if (!unitPrice.length > 0)
		errmsg = errmsg + "Please Enter UNIT PRICE <br>";
	else if (validateDot(unitPrice))
		errmsg = errmsg + "UNIT PRICE must contain atleast one number<br>";
	else if (!(validateDecimalNumberMinMax(unitPrice, 0, 100000)))
		errmsg = errmsg + "Please Enter valid UNIT PRICE And It Must Be In Between 0 And 1,00,000<br>";
	
	if (!qnty.length > 0)
		errmsg = errmsg + "Please Enter QUANTITY. <br>";
	else if (!(validateNumberMinMax(qnty, 0, 10000)))
		errmsg = errmsg
				+ "Please Enter Valid QUANTITY And It Must Be In Between 0 And 10,000 .<br>";

	if (!otherchrgs.length > 0)
		errmsg = errmsg + "Please Enter OTHER CHARGES<br>";
	else if (validateDot(otherchrgs))
		errmsg = errmsg + "OTHER CHARGES must contain atleast one number. <br>";
	else if (!(validateDecimalNumberMinMax(otherchrgs, -1, 10000)))
		errmsg = errmsg + "Please Enter Valid  CHARGES<br>";
	
	if (!(validateDecimalNumberMinMax(bp, 0, 10000000)))
		errmsg = errmsg + "Invalid TAXABLE AMOUNT.. <br>";
	if (!(validateDecimalNumberMinMax(egsta,0, 10000000)))
		if((parseInt(vp)!=0)&& (parseFloat(egsta)==0))
			errmsg = errmsg + "Invalid GST AMOUNT.. <br>";
	if (!(validateDecimalNumberMinMax(eamt, 0, 10000000)))
		errmsg = errmsg + "Invalid AMOUNT.. <br>";
	
//	if( !( ((parseInt(product)==10) || (parseInt(product)==11) || (parseInt(product)==12)) && (egsta.equalsIgnoreCase("NA")) ) ) {
	if( !( ((parseInt(product)==10) || (parseInt(product)==11) || (parseInt(product)==12)) && (egsta==0) ) ) {
		if (!(validateDecimalNumberMinMax(eigsta,-1, 10000000)))
			errmsg = errmsg + "Invalid GST.. <br>";
		if (!(validateDecimalNumberMinMax(ecgsta,-1, 10000000)))
			errmsg = errmsg + "Invalid GST.. <br>";
		if (!(validateDecimalNumberMinMax(esgsta,-1, 10000000)))
			errmsg = errmsg + "Invalid GST.. <br>";
	}
	return errmsg;
}

function validateEntries2(product,emrORdeliv,upv, qty, ocv, vpv, ptypecheck, ptypev) {
	var errmsg = "";
	var formobj = document.getElementById('data_form');
   
	if($("input[type=radio][name=ptype]:checked").length <= 0) {
		errmsg = errmsg + "Please select PURCHASE TYPE<br>";			
	}
	if(($("input[type=radio][name=ptype]:checked").length > 0)&&(ptypecheck != ptypev))
		errmsg = errmsg + "The PURCHASE TYPE you have selected might be wrong.Please check it again<br>";

	if (!(product > 0))
		errmsg = errmsg + "Please Select PRODUCT<br>";
	
	if (!(emrORdeliv > 0))
		errmsg = errmsg + "Please Select ONE WAY or TWO WAY LOAD <br>";

	
	if (!upv.length > 0)
		errmsg = errmsg + "Please Enter UNIT PRICE. <br>";
	else if (validateDot(upv))
		errmsg = errmsg + "UNIT PRICE must contain atleast one number. <br>";
	else if (!(validateDecimalNumberMinMax(upv, 0, 100000)))
		errmsg = errmsg + "Please Enter valid UNIT PRICE and it must be In Between 0 And 1,00,000 .<br>";
	else
		formobj.up.value = round(parseFloat(upv), 2);

	if (!qty.length > 0)
		errmsg = errmsg + "Please Enter QUANTITY. <br>";
	else if (!(validateNumberMinMax(qty, 0, 10000)))
		errmsg = errmsg
				+ "Please Enter Valid QUANTITY And It Must Be In Between 0 And 10,000 .<br>";

	if (!ocv.length > 0)
		errmsg = errmsg + "Please Enter OTHER CHARGES, If not Enter 0 <br>";
	else if (validateDot(ocv))
		errmsg = errmsg + "OTHER CHARGES must contain atleast one number. <br>";
	else if (!(validateDecimalNumberMinMax(ocv, -1, 10000)))
		errmsg = errmsg + "Please Enter Valid  CHARGES<br>";
	else
		formobj.oc.value = round(parseFloat(ocv), 2);

	return errmsg;
}

function calculateVATPrice(ebp, spc) {
	var cv = 0;
	for (var z = 0; z < equipment_data.length; z++) {
		if (equipment_data[z].prod_code == spc) {
			var vatv = equipment_data[z].vatp;
			cv = ((ebp * vatv) / 100);
		}
	}
	return cv;
}

function fetchGSTValue() {
	var formobj = document.getElementById('data_form');
	if (document.getElementById("up") != null) {

		var cpdate = new Date(formobj.s_r_date.value);
		var millisecs = cpdate.getTime();
		
		var elements = document.getElementsByClassName("qtyc");
		if (elements.length == 1) {
			var pide = formobj.epid;
			if (pide.selectedIndex > 0) {
				var pidv = pide.options[pide.selectedIndex].value;
				
				var prodDateInEqpm = 0;
				for(var e=0 ; e < equipment_data.length ; e++){
					if(equipment_data[e].prod_code == pidv){
						prodDateInEqpm = equipment_data[e].effective_date;
						break;
					}
				}
				if(millisecs < prodDateInEqpm){
					document.getElementById("dialog-1").innerHTML = "The product you have selected is invalid for the given Stock Received Date. Plese check effective date of the added product in Equipment Master.";
					alertdialogue();
					//alert("please define the price of product for sale invoice month in price master and continue");
					return;
				}else {
					formobj.vp.value = fetchGSTPercent(equipment_data, pidv);
				}
			}else {
				document.getElementById("dialog-1").innerHTML = "Please Select PRODUCT and Click FETCH GST";
				alertdialogue();
				//alert("Please Select PRODUCT and Click FETCH GST");
				return;
			}
		} else if (elements.length > 1) {
			for (var i = 0; i < elements.length; i++) {
				var pide = formobj.epid[i];
				if (pide.selectedIndex > 0) {
					var pidv = pide.options[pide.selectedIndex].value;
					
					var prodDateInEqpm = 0;
					for(var e=0 ; e < equipment_data.length ; e++){
						if(equipment_data[e].prod_code == pidv){
							prodDateInEqpm = equipment_data[e].effective_date;
							break;
						}
					}
					if(millisecs < prodDateInEqpm){
						document.getElementById("dialog-1").innerHTML = "The product you have selected is invalid for the given Stock Received Date. Plese check effective date of the added product in Equipment Master.";
						alertdialogue();
						//alert("please define the price of product for sale invoice month in price master and continue");
						return;
					}else {
						formobj.vp[i].value = fetchGSTPercent(equipment_data, pidv);
					}
				}else {
					document.getElementById("dialog-1").innerHTML = "Please Select PRODUCT and Click FETCH GST";
					alertdialogue();
					//alert("Please Select PRODUCT and Click FETCH GST");
					return;
				}
			}
		}

	} else {
		document.getElementById("dialog-1").innerHTML = "Please Add Data and Click FETCH GST";
		alertdialogue();
		//alert("Please Add Data and Click FETCH GST");
		return;
	}
	restrictChangingAllValues(".freez",".blkcalc");
	document.getElementById("calc_data").disabled = false;

	if (elements.length == 1) {
		document.getElementById("eord").disabled = false;
	} else if (elements.length > 1) {
		for (var i = 0; i < elements.length; i++) {
			formobj.eord[i].disabled = false;
		}		
	}	
	var efrz = document.getElementsByClassName("freez");
	remEleReadOnly(efrz,efrz.length);

/*	if (elements.length == 1) {
		var spfrz= document.getElementById("epid").options;
		disableSelect(spfrz,spfrz.length);
	} else if (elements.length > 1) {
		for (var i = 0; i < elements.length; i++) {
			var pfrz=formobj.epid[i].options;
			disableSelect(pfrz,pfrz.length);			
		}		
	}
*/
}