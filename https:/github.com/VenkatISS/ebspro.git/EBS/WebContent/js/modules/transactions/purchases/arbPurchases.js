//Construct Vendor Type html
vendordatahtml = "<OPTION VALUE='0'>SELECT</OPTION>";
if(cvo_data.length>0) {
	for(var z=0; z<cvo_data.length; z++){
		if(cvo_data[z].cvo_cat==0 && cvo_data[z].deleted==0)
			vendordatahtml=vendordatahtml+"<OPTION VALUE='"+cvo_data[z].id+"'>"+cvo_data[z].cvo_name+"</OPTION>";
	}
}

function changeVendor() {
	var formobj = document.getElementById('data_form');
	var cvoid = formobj.vid.selectedIndex;
	var cvoidv = formobj.vid.options[cvoid].value;
	var spfrz= document.getElementById("rcyn").options;
	
	document.getElementById("save_data").disabled=true;
	for(var i=0; i< cvo_data.length; i++) {
		if(cvo_data[i].id == cvoidv)
			var vreg= cvo_data[i].is_gst_reg;
	}
	if(vreg == 1) {
		enableSelect(spfrz,spfrz.length);
		formobj.rcyn.selectedIndex=2;
		disableSelect(spfrz,spfrz.length);
	}else if(vreg == 2) {
		enableSelect(spfrz,spfrz.length);
		formobj.rcyn.selectedIndex=1;
		disableSelect(spfrz,spfrz.length);
	}else {
		formobj.rcyn.selectedIndex="";
		enableSelect(spfrz,spfrz.length);
	}
	
	$(':radio:not(:checked)').attr('disabled', false);
}

/*function showARBPFormDialog() {

	var check = checkDayEndDateForAdd(dedate,a_created_date,effdate);
	if(check==true) {
		document.getElementById('myModal').style.display = "block";
	}else if(check!=true) {
		document.getElementById("dialog-1").innerHTML = check;
		alertdialogue();
		return;
	}
}
*/
function showARBPFormDialog() {
	document.getElementById('myModal').style.display = "block";
	pushMenu();
}

function closeARBPFormDialog() {

	$(':radio:not(:checked)').attr('disabled', false);
    document.getElementById("data_form").reset();
    var spfrz1= document.getElementById("rcyn").options;
    enableSelect(spfrz1,spfrz1.length);
    document.getElementById("rcyn").selectedIndex =0;
    var tbody = document.getElementById('data_table_body');	
	var count = document.getElementById('data_table_body').getElementsByTagName('tr').length;
	for(var a=0; a<count; a++){
		if(a>0)
			tbody.deleteRow(-1);
	}			
	document.getElementById('myModal').style.display = "none";	
    $("body").removeClass('sidebar-collapse').trigger('expanded.pushMenu');

}



/*new 23032018*/

function showCVOFormDialog() {
	document.getElementById('cvoModal').style.display = "block";
	document.getElementById('savemCVOPopdiv').style.display="none";
    $("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');
}

function closeCVOFormDialog() {
//	document.getElementById("data_form").reset();
	document.getElementById("mcvopopup_data_form").reset();
	document.getElementById('mymCVOPopupDIV').style.display = 'none';
	document.getElementById('mcvopopup_add_table_body').innerHTML = "";
	document.getElementById('cvoModal').style.display = "none";
$("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');
}

function showARBFormDialog() {
	document.getElementById('arbModal').style.display = "block";
	document.getElementById('marbSaveData').style.display="none";
    $("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');
}

function closeARBFormDialog() {
//	document.getElementById("data_form").reset();
	document.getElementById("marb_page_add_form").reset();
	document.getElementById('marb_page_add_table_body').innerHTML = "";
	document.getElementById('marb_page_add_table_div').style.display = 'none';
	document.getElementById('arbModal').style.display = "none";
$("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');
}


/*end 23032018*/

var tbody = document.getElementById('m_data_table_body');
for(var f=page_data.length-1; f>=0; f--) {
	var invdate = new Date(page_data[f].inv_date);
	var srdate = new Date(page_data[f].stk_recvd_date);
	var spd = fetchARBProductDetails(cat_types_data, page_data[f].arb_code);
	var vn = fetchVendorName(cvo_data, page_data[f].vendor_id);
	var tblRow = tbody.insertRow(-1);
	tblRow.style.height="30px";	
	tblRow.align="left";
	tblRow.innerHTML ='<td>'+ page_data[f].inv_ref_no + '</td>'+
   		'<td>'+ invdate.getDate()+"-"+months[invdate.getMonth()]+"-"+invdate.getFullYear() +'</td>'+
   		'<td>' + srdate.getDate()+"-"+months[srdate.getMonth()]+"-"+srdate.getFullYear() +'</td>'+
   		'<td>'+ spd +'</td>'+
   		'<td>'+ vn +'</td>'+
   		'<td>'+ rcs[page_data[f].reverse_charge] +'</td>'+
   		'<td>'+ page_data[f].unit_price + '</td>'+
   		'<td>'+ page_data[f].quantity + '</td>'+
   		'<td>'+ page_data[f].gstp + '</td>'+
   		'<td>'+ page_data[f].basic_amount +'</td>'+
   		'<td>'+ page_data[f].igst_amount +'</td>'+
   		'<td>'+ page_data[f].sgst_amount + '</td>'+
   		'<td>'+ page_data[f].cgst_amount +'</td>'+
   		'<td>'+ page_data[f].other_charges +'</td>'+
   		'<td>'+ page_data[f].c_amount + '</td>'+
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

	var tbody = document.getElementById('data_table_body');
	var newRow = tbody.insertRow(-1);
	//var invamt = document.getElementById('data_form').inv_amt;                                                              
	var a = newRow.insertCell(0);
	var b = newRow.insertCell(1);
	var c = newRow.insertCell(2);
	var d = newRow.insertCell(3);
	var e = newRow.insertCell(4);
	var f = newRow.insertCell(5);
	var g = newRow.insertCell(6);
	var h = newRow.insertCell(7);
	var i = newRow.insertCell(8);
//	var j = newRow.insertCell(9);

	a.innerHTML = "<td valign='top' height='4' align='center'><select name='epid' id='epid' class='form-control input_field tp sadd blkcalc' style='width:100px;' >"
			+ ctdatahtml + "</select></td>";
	b.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='vp' id='vp'  class='form-control input_field eadd' size='8' readonly='readonly' style='background-color:#FAFAC2;' placeholder='Gst%'></td>";
	c.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='up' id='up'  class='form-control input_field freez eadd' size='8' maxlength='7'  placeholder='Unit Price'></td>";
	d.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='qty' id='qty'  class='form-control input_field qtyc freez eadd'  size='8'  maxlength='4'  placeholder='Quantity'></td>";
	e.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='oc' id='oc' class='form-control input_field freez eadd' value='0.00'  size='8' maxlength='8' placeholder='Other Charges'></td>";
	f.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='bp' id='bp' class='form-control input_field eadd' size='8'  readonly='readonly' style='background-color:#F3F3F3;'  placeholder='Taxable Amount' ></td>";
	g.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='gsta' id='gsta'  class='form-control input_field eadd' size='8' readonly='readonly' style='background-color:#F3F3F3;'  placeholder='Gst Amount'></td>";
	h.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='amt' id='amt' class='form-control input_field eadd' size='8' readonly='readonly' style='background-color:#F3F3F3;'  placeholder='Amount'></td>";
	i.innerHTML = "<td valign='top' height='4' align='center'><img src='images/delete.png' onclick='doRowDeleteInTranxs(this,amt,inv_amt,data_table_body)'></td>"
						+ "<td>"
						+ "<input type=hidden name='igsta' id='igsta'>"
						+ "<input type=hidden name='sgsta' id='sgsta'>"
						+ "<input type=hidden name='cgsta' id='cgsta'>" + "</td>";
	document.getElementById("fetch_data").disabled = false;
}

function saveData(obj) {
	var formobj = document.getElementById('data_form');
	var ems = "";
	if (document.getElementById("qty") != null) {
		var elements = document.getElementsByClassName("tp");
		if (elements.length == 1) {
			var einvReNo = formobj.inv_ref_no.value.trim();
			var einvDate = formobj.inv_date.value;
			var estockRcdDate = formobj.s_r_date.value;
			var evendor = formobj.vid.selectedIndex;
			var ervschrg = formobj.rcyn.selectedIndex;
			var eproduct = formobj.epid.selectedIndex;
			var eunitPrice = formobj.up.value;
			var ebp = formobj.bp.value;
			var eqnty = formobj.qty.value;
			var evp = formobj.vp.value.trim();
			var eotherchrgs = formobj.oc.value;
			var egsta = formobj.gsta.value;
			var eamt = formobj.amt.value;
			var eigsta = formobj.igsta.value;
			var ecgsta = formobj.cgsta.value;
			var esgsta = formobj.sgsta.value;

			ems = validateEntries(einvReNo, einvDate, estockRcdDate, evendor,ervschrg,
					eproduct, eunitPrice,ebp,eqnty, evp, eotherchrgs,egsta,eamt,eigsta,ecgsta,esgsta);
			formobj.up.value = parseFloat(eunitPrice).toFixed(2)
		}else if (elements.length > 1) {
			for (var i = 0; i < elements.length; i++) {

				var einvReNo = formobj.inv_ref_no.value.trim();
				var einvDate = formobj.inv_date.value;
				var estockRcdDate = formobj.s_r_date.value;
				var evendor = formobj.vid.selectedIndex;
				var ervschrg = formobj.rcyn.selectedIndex;
				var eproduct = formobj.epid[i].selectedIndex;
				var eunitPrice = formobj.up[i].value.trim();
				var ebp = formobj.bp[i].value;
				var eqnty = formobj.qty[i].value.trim();
				var evp = formobj.vp[i].value.trim();
				var eotherchrgs = formobj.oc[i].value.trim();
				var egsta = formobj.gsta[i].value;
				var eamt = formobj.amt[i].value;
				var eigsta = formobj.igsta[i].value;
				var ecgsta = formobj.cgsta[i].value;
				var esgsta = formobj.sgsta[i].value;

				ems = validateEntries(einvReNo, einvDate, estockRcdDate,evendor,ervschrg,
						eproduct, eunitPrice,ebp, eqnty, evp, eotherchrgs,egsta,eamt,eigsta,ecgsta,esgsta);
				if (ems.length > 0)
					break;
				formobj.up[i].value = parseFloat(eunitPrice).toFixed(2)
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
	var objId = obj.id;
	document.getElementById(objId).disabled = true;
	formobj.submit();
}

/*function deleteItem(iid,srdate) {
	var flag=validateDayEndForDelete(srdate,dedate);
	if(flag) {
		if (confirm("Are you sure you want to delete?") == true) {
			var formobj = document.getElementById('m_data_form');
			formobj.actionId.value = "5113";
			formobj.dataId.value = iid;
			formobj.submit();
		}
	}else 
		alert("This record can't be deleted as you have submitted and verified your DAYEND calculations succuessfully... ");	
}*/

function deleteItem(iid,srdate){
	var flag=validateDayEndForDelete(srdate,dedate);
	if(flag){
	 $("#myDialogText").text("Are You Sure You Want To Delete?");
	 var formobj = document.getElementById('m_data_form');
	 confirmDialogue(formobj,5113,iid);
	}else {
		document.getElementById("dialog-1").innerHTML = "This record can't be deleted as you have submitted and verified your DAYEND calculations succuessfully";
		alertdialogue();
	}
}


function calculateValues() {
	var formobj = document.getElementById('data_form');
	var cvogstin = "";
	var cvoreg;
	var ptypev = formobj.ptype.value;
	if (document.getElementById("bp") != null) {		
		if(formobj.vid.selectedIndex >0) {
			for(var d=0;d< cvo_data.length;d++){
				if(parseInt(formobj.vid.value) == cvo_data[d].id) {
					cvogstin = cvo_data[d].cvo_tin;
					cvoreg = cvo_data[d].is_gst_reg;
				}	
			}
		}else {
			document.getElementById("dialog-1").innerHTML = "please select Vendor";
			alertdialogue();
			//alert("please select Vendor");
			return;
		}
		if(cvoreg==1) {	
			var dstcode = dealergstin.substr(0, 2);
			var cvstcode = cvogstin.substr(0, 2);
			var ptypecheck = "";
			if(cvstcode == dstcode)
				ptypecheck = "lp"; 
			else 
				ptypecheck = "isp";
		}else if(cvoreg==2){
			ptypecheck = ptypev;
		}
		var elements = document.getElementsByClassName("qtyc");

		if (elements.length == 1) {
			var upv = formobj.up.value.trim();
			var vpv = formobj.vp.value.trim();
			var qty = formobj.qty.value.trim();
			var ocv = formobj.oc.value.trim();
			var ems = validateEntries2(upv, qty, ocv, vpv,ptypecheck,ptypev);
			if (ems.length > 0) {
				document.getElementById("dialog-1").innerHTML = ems;
				alertdialogue();
				//alert(ems);
				return;
			}
			formobj.up.value = parseFloat(upv).toFixed(2);
			formobj.oc.value = parseFloat(ocv).toFixed(2);
			formobj.bp.value = round((upv * qty),2);
			formobj.gsta.value = (((upv * qty) * vpv) / 100).toFixed(2);
			formobj.amt.value = (+(formobj.bp.value)+ +(formobj.gsta.value) + +ocv).toFixed(2);
			formobj.inv_amt.value =round((formobj.amt.value),0);
			if (ptypecheck == 'lp') {
				formobj.igsta.value = 0;
				formobj.sgsta.value = (formobj.gsta.value) / 2;
				formobj.cgsta.value = (formobj.gsta.value) / 2;
			} else if (ptypecheck == 'isp') {
				formobj.igsta.value = (formobj.gsta.value);
				formobj.sgsta.value = 0;
				formobj.cgsta.value = 0;
			}
		} else if (elements.length > 1) {
			var totalAmt = 0;
			for (var i = 0; i < elements.length; i++) {
				var upv = formobj.up[i].value.trim();
				var vpv = formobj.vp[i].value.trim();
				var qty = formobj.qty[i].value.trim();
				var ocv = formobj.oc[i].value;
				var ems = validateEntries2(upv, qty, ocv, vpv,ptypecheck,ptypev);
				if (ems.length > 0) {
					document.getElementById("dialog-1").innerHTML = ems;
					alertdialogue();
					//alert(ems);
					return;
				}
				formobj.up[i].value = parseFloat(upv).toFixed(2);
				formobj.oc[i].value = parseFloat(ocv).toFixed(2);
				formobj.bp[i].value = round((upv * qty),2);
				formobj.gsta[i].value = (((upv * qty) * vpv) / 100).toFixed(2);
				formobj.amt[i].value = (+(formobj.bp[i].value)+ +(formobj.gsta[i].value) + +ocv).toFixed(2);
				totalAmt = +totalAmt + +(formobj.amt[i].value);
				if (ptypecheck == 'lp') {
					formobj.igsta[i].value = 0;
					formobj.sgsta[i].value = (formobj.gsta[i].value) / 2;
					formobj.cgsta[i].value = (formobj.gsta[i].value) / 2;
				} else if (ptypecheck == 'isp') {
					formobj.igsta[i].value = (formobj.gsta[i].value);
					formobj.sgsta[i].value = 0;
					formobj.cgsta[i].value = 0;
				}
			}
			formobj.inv_amt.value = round(totalAmt,0);
		}

	} else {
		document.getElementById("dialog-1").innerHTML = "Please Add ARB Purchases Data and Click Calculate";
		alertdialogue();
		//alert("Please Add ARB Purchases Data and Click Calculate");
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

function validateEntries(invReNo, invDate, stockRcdDate, vendor,rvrschrg, product,
		unitPrice,bp, qnty, vpv, otherchrgs,egsta,eamt,eigsta,ecgsta,esgsta) {
	var errmsg = "";
	var formobj = document.getElementById('data_form');

	if (!(invReNo.length > 0))
		errmsg = "Please enter INVOICE REFERENCE NO .<br>";
	else if(invReNo.length < 3)
		errmsg = errmsg + " INVOICE REFERENCENO Should Be Atleast Three Characters<br>"
	else if(page_data){
		for(var i=0 ;i<page_data.length;i++) {
			var inv_ref_no =page_data[i].inv_ref_no;
			if(inv_ref_no == invReNo){
				errmsg = errmsg + "Please Enter Valid INVOICE REFERENCE NO<br>";
				break;
			}
		}
	}
	
	if(pi_data){
		for(var cp=0 ;cp<pi_data.length;cp++) {
			var cp_inv_ref_no = pi_data[cp].inv_ref_no;
			if(cp_inv_ref_no == invReNo){
				errmsg = errmsg + "You have already added products in CYLINDER PURCHASES under the entered INVOICE REFERENCE NUMBER .Please Enter Valid INVOICE REFERENCE NO <br>";
				break;
			}
		}
	}

	var chkd = checkdate(invDate);
	var vd = isValidDate(invDate);
	
	var vfd = ValidateFutureDate(stockRcdDate);
	var ctd = comparisionofTwoDates(invDate, stockRcdDate);	

	if (!(invDate.length > 0))
		errmsg = errmsg + "Please enter INVOICE DATE<br>";
	else if(chkd != "true")
		errmsg = errmsg+chkd+"<br>";
	else if(vd != "false")
		errmsg = errmsg+"INVOICE "+vd+"<br>";
	else if (!(stockRcdDate.length > 0))
		errmsg = errmsg + "Please enter STOCK RECEIVED DATE<br>";
	else if(ctd != "false")
		errmsg = errmsg + " INVOICE DATE must be earlier than STOCK RECEIVED DATE. <br>";
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
	}
*/
	
	if (!(vendor > 0))
		errmsg = errmsg + "Please select VENDOR<br>";
	
	if (!(rvrschrg > 0))
		errmsg = errmsg + "Please select REVERSE CHARGE YES OR NO<br>";
	

	if($("input[type=radio][name=ptype]:checked").length <= 0) {
		errmsg = errmsg + "Please select PURCHASE TYPE<br>";			
	}  
	
	if (!(product > 0))
		errmsg = errmsg + "Please select PRODUCT<br>";

	if (!(unitPrice.length > 0))
		errmsg = errmsg + "Please enter UNIT PRICE<br>";
	else if (validateDot(unitPrice))
		errmsg = errmsg + "UNIT PRICE must contain atleast one number<br>";
	else if (!(validateDecimalNumberMinMax(unitPrice, 0, 10000)))
		errmsg = errmsg + "Please enter valid UNIT PRICE And it must be in between 0 and 10000<br>";
	
	if (!(validateDecimalNumberMinMax(bp, 0, 10000000)))
		errmsg = errmsg + "Invalid TAXABLE AMOUNT.. <br>";
	
	if ((vpv != 0) && (!(validateDecimalNumberMinMax(egsta, 0, 10000000))))
		errmsg = errmsg + "Invalid GST AMOUNT <br>";
	if (!(validateDecimalNumberMinMax(eamt, 0, 10000000)))
		errmsg = errmsg + "Invalid AMOUNT.. <br>";
	if (!(validateDecimalNumberMinMax(eigsta, -1, 10000000)))
		errmsg = errmsg + "Invalid GST <br>";
	if (!(validateDecimalNumberMinMax(ecgsta, -1, 10000000)))
		errmsg = errmsg + "Invalid GST <br>";
	if (!(validateDecimalNumberMinMax(esgsta, -1, 10000000)))
		errmsg = errmsg + "Invalid GST <br>";

	
	if (!(qnty.length > 0))
		errmsg = errmsg + "Please enter QUANTITY<br>"
	else if (!(checkNumber(qnty)))
		errmsg = errmsg + "Please enter valid QUANTITY.<br>";
	else if (!(validateNumberMinMax(qnty, 0, 10000)))
		errmsg = errmsg
				+ "Please Enter Valid QUANTITY And It Must Be In Between 0 And 10,000 .<br>";
	
/*	if (!(vpv.length > 0))
		errmsg = errmsg + "Please enter GST%<br>";
	else if (!(checkNumber(vpv)))
		errmsg = errmsg + "GST% must be a valid number.<br>";
*/
	if (!(otherchrgs.length > 0))
		errmsg = errmsg + "Please enter OTHER CHARGES<br>";
	else if (validateDot(otherchrgs))
		errmsg = errmsg + "OTHER CHARGES must contain atleast one number. <br>";
	else if (!(validateDecimalNumberMinMax(otherchrgs, -1, 10000)))
		errmsg = errmsg + "Please enter valid CHARGES And it must be  >=0 and <10000.<br>";
	
	return errmsg;
}

function validateEntries2(upv, qty, ocv, vpv,ptypecheck,ptypev) {
	var errmsg = "";
	var formobj = document.getElementById('data_form');
	var vryn = formobj.rcyn.selectedIndex;

	if($("input[type=radio][name=ptype]:checked").length <= 0) {
		errmsg = errmsg + "Please select PURCHASE TYPE<br>";			
	}
	if(vryn == 2){
	if(($("input[type=radio][name=ptype]:checked").length > 0)&&(ptypecheck != ptypev))
		errmsg = errmsg + "The PURCHASE TYPE you have selected might be wrong.Please check it again<br>";
	}
	if (!(upv.length > 0))
		errmsg = errmsg + "Please enter UNIT PRICE<br>";
	else if (validateDot(upv))
		errmsg = errmsg + "UNIT PRICE must contain atleast one number. <br>";
	else if (!(validateDecimalNumberMinMax(upv, 0, 10000)))
		errmsg = errmsg + "Please enter valid UNIT PRICE And it must be in between 0 and 10000 <br>";
	

	if (!(qty.length > 0))
		errmsg = errmsg + "Please enter QUANTITY<br>"
	else if (!(checkNumber(qty)))
		errmsg = errmsg + "Please enter valid QUANTITY<br>";
	else if (!(validateNumberMinMax(qty, 0, 10000)))
		errmsg = errmsg + "Please Enter Valid QUANTITY And It Must Be In Between 0 And 10,000 .<br>";
/*	if (!(vpv.length > 0))
		errmsg = errmsg + "Please enter GST%<br>";
	else if (!(checkNumber(vpv)))
		errmsg = errmsg + "GST% must be a valid number.<br>";*/

	if (!(ocv.length > 0))
		errmsg = errmsg + "Please enter OTHER CHARGES<br>";
	else if (validateDot(ocv))
		errmsg = errmsg + "OTHER CHARGES must contain atleast one number. <br>";
	else if (!(validateDecimalNumberMinMax(ocv, -1, 10000)))
		errmsg = errmsg + "Please enter valid CHARGES And it must be  >=0 and <10000.<br>";
	
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

function fetchGSTValues() {
	var formobj = document.getElementById('data_form');
	var elements = document.getElementsByClassName("qtyc");
	if (document.getElementById("up") != null) {
		
		var apdate = new Date(formobj.s_r_date.value);
		var millisecs = apdate.getTime();
		
		if (elements.length == 1) {
			var pide = formobj.epid;
			if (pide.selectedIndex > 0) {
				var pidv = pide.options[pide.selectedIndex].value;

				var prodDateInArbm = 0;
				for(var a=0 ; a < cat_types_data.length ; a++){
					if(cat_types_data[a].id == pidv){
						prodDateInArbm = cat_types_data[a].effective_date;
						break;
					}
				}
				if(millisecs < prodDateInArbm) {
					document.getElementById("dialog-1").innerHTML = "The product you have selected is invalid for the given Stock Received Date. Plese check effective date of the added product in BLPG/ARB/NFR Master.";
					alertdialogue();
					//alert("please define the price of product for sale invoice month in price master and continue");
					return;
				}else {
					formobj.vp.value = fetchARBGSTPercent(cat_types_data, pidv);
				}
			}else {
				document.getElementById("dialog-1").innerHTML = "Please Select PRODUCT and Click FETCH GST";
				alertdialogue();
				//alert("Please Select PRODUCT and Click FETCH GST");
				return;
			}
		}else if (elements.length > 1) {
			for (var i = 0; i < elements.length; i++) {
				var pide = formobj.epid[i];
				if (pide.selectedIndex > 0) {
					var pidv = pide.options[pide.selectedIndex].value;

					var prodDateInArbm = 0;
					for(var a=0 ; a < cat_types_data.length ; a++){
						if(cat_types_data[a].id == pidv){
							prodDateInArbm = cat_types_data[a].effective_date;
							break;
						}
					}
					if(millisecs < prodDateInArbm){
						document.getElementById("dialog-1").innerHTML = "The product you have selected is invalid for the given Stock Received Date. Plese check effective date of the added product in BLPG/ARB/NFR Master.";
						alertdialogue();
						//alert("please define the price of product for sale invoice month in price master and continue");
						return;
					}else {
						formobj.vp[i].value = fetchARBGSTPercent(cat_types_data,pidv);
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