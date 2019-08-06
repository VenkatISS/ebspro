
/*document.getElementById("vid").innerHTML = "<input list='vid' id='answerInput' class='form-control input_field' placeholder='SELECT' style='width: 110%;'>"+
												"<datalist id='vid'>"+vendordatahtml+"</datalist>"+
												"<input type='hidden' name='vid' id='answerInputHidden'>";*/


/*function changeVendor() {
	var formobj = document.getElementById('data_form');
	var cvoid = formobj.vid.selectedIndex;
	var cvoidv = formobj.vid.options[cvoid].value;
	var spfrz= document.getElementById("rcyn").options;
	
	document.getElementById("save_data").disabled=true;
	
	for(var i=0; i< cvo_data.length; i++){
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
		enableSelect(spfrz,spfrz.length);
		formobj.rcyn.selectedIndex=0;
		enableSelect(spfrz,spfrz.length);
	}
	$(':radio:not(:checked)').attr('disabled', false);	

}*/
function changeVendor() {
	var formobj = document.getElementById('data_form');
	var cvoid = document.getElementById("answerInputHidden").value.trim();
	var spfrz= document.getElementById("rcyn").options;
	
	document.getElementById("save_data").disabled=true;

	if(cvoid.length >0) {
		if(venarr.indexOf(parseInt(cvoid)) == -1){
			formobj.rcyn.selectedIndex=0;
			enableSelect(spfrz,spfrz.length);
			document.getElementById("dialog-1").innerHTML = "The vendor you have entered is not valid. Please enter valid vendor";
			alertdialogue();
			//alert("The vendor you have entered is not valid. Please enter valid vendor");
			return;
		}else {
			for(var i=0; i< cvo_data.length; i++){
				if(cvo_data[i].id == cvoid)
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
				enableSelect(spfrz,spfrz.length);
				formobj.rcyn.selectedIndex=0;
				enableSelect(spfrz,spfrz.length);
			}
			$(':radio:not(:checked)').attr('disabled', false);
		}
	}
}

/*function showOTHRPFormDialog() {
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

function showOTHRPFormDialog() {
	document.getElementById('myModal').style.display = "block";
	pushMenu();
}


function closeOTHRPFormDialog() {
//	document.getElementById("calc_data").disabled = true;
	document.getElementById("save_data").disabled = true;
	
	$(':radio:not(:checked)').attr('disabled', false);
    /*var spfrz= document.getElementById("egst").options;
	enableSelect(spfrz,spfrz.length);*/
	var spfrz1= document.getElementById("rcyn").options;
	enableSelect(spfrz1,spfrz1.length);
	document.getElementById("rcyn").selectedIndex =0;
	document.getElementById("data_form").reset();
	var tbody = document.getElementById('data_table_body');	
	var count = document.getElementById('data_table_body').getElementsByTagName('tr').length;
	for(var a=0; a<count; a++){
		if(a>0) tbody.deleteRow(-1);
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

//---------------------------------------

/*
//Construct Vendor Type html
vendordatahtml = "<OPTION VALUE='0'>SELECT</OPTION>";
if(cvo_data.length>0) {
	for(var z= 0; z<cvo_data.length; z++){
		if(cvo_data[z].cvo_cat==3 && cvo_data[z].deleted==0)
		vendordatahtml=vendordatahtml+"<OPTION VALUE='"+cvo_data[z].id+"'>"+cvo_data[z].cvo_name+"</OPTION>";
		vndryn=cvo_data[z].is_gst_reg;
	}
}
document.getElementById("vid").innerHTML = "<select id='vid' name='vid' class='form-control input_field'>"+vendordatahtml+"</select>";
*/

var tbody = document.getElementById('m_data_table_body');
for(var f=page_data.length-1; f>=0; f--){
	var invdate = new Date(page_data[f].inv_date);
	var vn = fetchVendorName(cvo_data, page_data[f].vendor_id);
	var gstp  = page_data[f].gstp;
	if(gstp == -1){
		gstp ="NA";
	}
	var minhin = page_data[f].mh;
    var minhva ;
    if(minhin == 0){
            minhva = "NA"
    }else {
    	minhva = getExpenditureSubHead(page_data[f].mh);
    }
   var tblRow = tbody.insertRow(-1);
   tblRow.align="left";
   tblRow.innerHTML = "<td>" + page_data[f].inv_ref_no +  "</td>" +
   					  "<td>" + invdate.getDate()+"-"+months[invdate.getMonth()]+"-"+invdate.getFullYear() + "</td>" + 
   					  "<td>" + vn +  "</td>" +
   					  "<td>" + rcs[page_data[f].reverse_charge] +  "</td>" +
   					  "<td>" +  page_data[f].pname +  "</td>" + 
   					  "<td>" + taxbls[page_data[f].taxable] +  "</td>" + 
   					  "<td>" + page_data[f].hsn_code +  "</td>" + 
   					  "<td>" + gstp +  "</td>" + 
   					  "<td>" + minhva +  "</td>" + 
   					  "<td>" + opahs[page_data[f].ah] +  "</td>" +
   					  "<td>" + page_data[f].quantity +  "</td>" + 
   					  "<td>" + uoms[page_data[f].uom] +  "</td>" +
   					  "<td>" + page_data[f].unit_rate +  "</td>" +    
   					  "<td>" + page_data[f].basic_amount +  "</td>" +
   					  "<td>" + page_data[f].igst_amount +  "</td>" + 
   					  "<td>" + page_data[f].sgst_amount +  "</td>" +
   					  "<td>" + page_data[f].cgst_amount +  "</td>" + 
   					  "<td>" + page_data[f].p_amount +  "</td>" +
   					  "<td align='center'><img src='images/delete.png' onclick='deleteItem("+page_data[f].id+","+page_data[f].inv_date+")'></td>"; 
};

function addRow(){
	document.getElementById("save_data").disabled = true;
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
    /*var spfrz= document.getElementById("egst").options;
	enableSelect(spfrz,spfrz.length);*/
	
	var ele = document.getElementsByClassName("pn");
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
		var k = newRow.insertCell(10);
		var l = newRow.insertCell(11);
		var m = newRow.insertCell(12);
		var n = newRow.insertCell(13);
		var o = newRow.insertCell(14);
		
		a.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='pname' id='pname' class='form-control input_field pn freez eadd' size='6' maxlength='15' placeholder='NAME'></td>";
		b.innerHTML = "<td valign='top' height='4' align='center'><SELECT NAME='tbl' ID='tbl'  class='form-control input_field freez sadd'  onchange='settaxv()' style='width:100px;'>"
			+ "<OPTION VALUE='0'>SELECT</OPTION>"
			+ "<OPTION VALUE='1'>YES</OPTION>"
			+ "<OPTION VALUE='2'>NO</OPTION>"
			+ "</SELECT></td>";
		c.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='hsnc' id='hsnc' class='form-control input_field freez hsnr eadd' size='6' maxlength='10' placeholder='HSN/SAC CODE'></td>";
		d.innerHTML = "<td valign='top' height='4' align='center'><SELECT NAME='egst' ID='egst'  class='form-control input_field egstd freez' style='width:100px;'>"
			+ "<OPTION VALUE='-1'>SELECT</OPTION>"
			+ "<OPTION VALUE='0'>0</OPTION>"
			+ "<OPTION VALUE='5'>5</OPTION>"
			+ "<OPTION VALUE='12'>12</OPTION>"
			+ "<OPTION VALUE='18'>18</OPTION>"
			+ "<OPTION VALUE='28'>28</OPTION>"
			+ "</SELECT></td>";
		e.innerHTML = "<td valign='top' height='4' align='center'><SELECT NAME='minh' ID='minh' class='form-control input_field freez' onchange='setCH()'  style='width:100px;'>"+eshdatahtml+ "</SELECT></td>";
		f.innerHTML = "<td valign='top' height='4' align='center'><SELECT NAME='ah' ID='ah' class='form-control input_field freez sadd'  style='width:100px;'>"+opahsdatahtml+ "</SELECT></td>";
		g.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='qty' id='qty' class='form-control input_field freez eadd qtyc' size='4' placeholder='Quantity'></td>";
		h.innerHTML = "<td valign='top' height='4' align='center'><SELECT NAME='uom' ID='uom' class='form-control input_field freez sadd'  style='width:100px;'>"+uomdatahtml+"</SELECT></td>";
		i.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='ur' id='ur' class='form-control input_field freez eadd' size='5' placeholder='UNIT RATE'><input type='hidden' id='ch' name='ch'></td>";
		j.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='tval' id='tval'  class='form-control input_field eadd' size='5' readonly='readonly' style='background-color:#F3F3F3;' placeholder='TAXABLE VALUE'></td>";
		k.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='igsta' id='igsta'  class='form-control input_field eadd' size='5' readonly='readonly' style='background-color:#F3F3F3;' placeholder='IGST AMOUNT'></td>";
		l.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='cgsta' id='cgsta' class='form-control input_field eadd'  size='5' readonly='readonly' style='background-color:#F3F3F3;' placeholder='CGST AMOUNT'></td>";
		m.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='sgsta' id='sgsta' size='5'  class='form-control input_field eadd' readonly='readonly' style='background-color:#F3F3F3;' placeholder='SGST AMOUNT'></td>";
		n.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='ta' id='ta' size='6' class='form-control input_field eadd'  readonly='readonly' style='background-color:#F3F3F3;' placeholder='TOTAL AMOUNT'></td>";
		o.innerHTML = "<td valign='top' height='4' align='center'><img src='images/delete.png' onclick='doRowDeleteInTranxs(this,ta,inv_amt,data_table_body)'></td>";
	}else {
		document.getElementById("dialog-1").innerHTML = "Please save the data and ADD again";
		alertdialogue();
		//alert("Please save the data and ADD again");
		return;
	}
}

function saveData(obj){
	var formobj = document.getElementById('data_form');
	var ems = "";
	if (document.getElementById("qty") != null) {
		var evendor = document.getElementById("answerInputHidden").value.trim();
		var elements = document.getElementsByClassName("pn");
		var einvReNo = formobj.inv_ref_no.value.trim();
		var einvDate = formobj.inv_date.value;
		var ercyn = formobj.rcyn.selectedIndex;
		if (elements.length == 1) {
			var eproduct = formobj.pname.value.trim();
			var etaxableyn = formobj.tbl.selectedIndex;
			var ehsnorsac = formobj.hsnc.value.trim();
			var egstp = formobj.egst.selectedIndex;
			var eminorh = formobj.minh.selectedIndex;
			var eacch = formobj.ah.selectedIndex;
			var eqnty = formobj.qty.value.trim();
			var euom = formobj.uom.selectedIndex;
			var eunitrate = formobj.ur.value.trim();
			var tval = formobj.tval.value;
			var eamt = formobj.ta.value;
			var eigsta = formobj.igsta.value;
			var ecgsta = formobj.cgsta.value;
			var esgsta = formobj.sgsta.value;
			
            if(formobj.ch.value == ""){
            	formobj.ch.value=0;
            }
			ems = validateEntries(ercyn,einvReNo, einvDate, evendor, eproduct,
					etaxableyn,ehsnorsac,egstp, eminorh, eacch, eqnty, euom, eunitrate,tval,eamt ,eigsta, ecgsta,esgsta);
		} else if (elements.length > 1) {
			for (var i = 0; i < elements.length; i++) {
				var eproduct = formobj.pname[i].value.trim();
				var etaxableyn = formobj.tbl[i].selectedIndex;
				var ehsnorsac = formobj.hsnc[i].value.trim();
				var egstp = formobj.egst[i].selectedIndex;
				var eminorh = formobj.minh[i].selectedIndex;
				var eacch = formobj.ah[i].selectedIndex;
				var eqnty = formobj.qty[i].value.trim();
				var euom = formobj.uom[i].selectedIndex;
				var eunitrate = formobj.ur[i].value.trim();
				var tval = formobj.tval[i].value;
				var eamt = formobj.ta[i].value;
				var eigsta = formobj.igsta[i].value;
				var ecgsta = formobj.cgsta[i].value;
				var esgsta = formobj.sgsta[i].value;
				if(formobj.ch[i].value == ""){
	            	formobj.ch[i].value=0;
	            }
				ems = validateEntries(ercyn,einvReNo, einvDate, evendor, eproduct,
					etaxableyn,ehsnorsac,egstp, eminorh, eacch, eqnty, euom, eunitrate,tval,eamt ,eigsta, ecgsta,esgsta);
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

	formobj.tval.value = round((formobj.tval.value) ,2);
	
	var objId = obj.id;
	document.getElementById(objId).disabled = true;
	formobj.submit();
}

function validateEntries(ercyn,invReNo,invDate, vendor, product, txblyn,
		hsnorsac,gstp,minorhd, acch, qnty,uom, unitPrice,txblv,eamt,eigsta,ecgsta,esgsta) {
	var errmsg = "";
	var formobj = document.getElementById('data_form');
	var etaxableyn = formobj.tbl.selectedIndex;

	if (!(invReNo.length > 0))
		errmsg = "Please enter INVOICE REFERENCE NO<br>";
	else if(invReNo.length < 3)
		errmsg = " INVOICE REFERENCE NO Must Contains Atleast 3 Characters<br>";
	
	var chkd = checkdate(invDate);
	var vd = isValidDate(invDate);
	var vfd = ValidateFutureDate(invDate);
	
	if (!(invDate.length > 0))
		errmsg = errmsg + "Please Enter INVOICE DATE<br>";
	else if(chkd != "true")
		errmsg = errmsg+chkd+"<br>";
	else if(vd != "false")
		errmsg = errmsg+"INVOICE "+vd+"<br>";
	else if(validateDayEndAdd(invDate,dedate)){
        errmsg = "INVOICE DATE should be after DayEndDate<br>";
        return errmsg;
	}else if(validateEffectiveDateForCVO(invDate,effdate)){
        errmsg = "INVOICE DATE should be after Effective Date that you have entered While First Time Login<br>";
        return errmsg;
	}else if (vfd != "false")
		errmsg = errmsg +"INVOICE RECEIVED"+vfd+"<br>";
/*	else {
		var lastTrxnDateCheck = checkforLastTrxnDate(dedate,a_created_date,invDate,trdate);
		if(lastTrxnDateCheck != "false") {
			return lastTrxnDateCheck;
		}
	}	*/
	
	if (!(vendor.length > 0))
		errmsg = errmsg + "Please enter VENDOR<br>";
	else {
		if(venarr.indexOf(parseInt(vendor)) == -1)
			errmsg = errmsg + "The vendor you have entered is not valid. Please enter valid vendor <br>";
	}
	
	if($("input[type=radio][name=ptype]:checked").length <= 0) {
		errmsg = errmsg + "Please select PURCHASE TYPE<br>";
	}
	
	if (!(ercyn > 0))
		errmsg = errmsg + "Please Select whether REVERSE CHARGE yes or no <br>";
	//else if(!(changeVendor(cvo_data,vendor) == 2))
		//errmsg = errmsg + "Please select the valid REVERSE CHARGE For The Selected VENDOR  <br>";


	if (!(product.length > 0))
		errmsg = errmsg + "Please enter PRODUCT name<br>";
	else if(!(product.length >= 5))
		errmsg = errmsg + "PRODUCT name must contain atleast 5 chars<br>";
	else if(!validateAlphaNumeric(product))
		errmsg = errmsg + "PRODUCT name must contain only alphanumerics<br>";
	
	if (!(txblyn > 0))
		errmsg = errmsg + "Please Select TAXABLE YES or NO <br>";
	/*else if(txblyn==2 && ercyn == 1)
		errmsg = errmsg + "Please Select valid TAXABLE YES or NO <br>";
*/	
	if (parseInt(etaxableyn) == 1){		
		if (!(gstp > 0))
			errmsg = errmsg + "Please Select GST %<br>";
	
		if (!(validateDecimalNumberMinMax(eigsta,-1, 10000000)))
			errmsg = errmsg + "Invalid GST.. <br>";
		if (!(validateDecimalNumberMinMax(ecgsta,-1, 10000000)))
			errmsg = errmsg + "Invalid GST.. <br>";
		if (!(validateDecimalNumberMinMax(esgsta,-1, 10000000)))
			errmsg = errmsg + "Invalid GST.. <br>";
		if(!(txblv > 0))
			errmsg = errmsg + "TAXABLE VALUE Should Be >0 %<br>";
		
	}	
	
	if (!hsnorsac.length > 0)
		errmsg = errmsg + "Please Enter HSN/SAC CODE <br>";
	/*else if(!checkNumber(hsnorsac))
		errmsg = errmsg + "Please Enter valid HSN/SAC CODE <br>";
	else if(!(txblyn==2)) {
		if(!(hsnorsac.length>=3))
			errmsg = errmsg + " HSN/SAC CODE must be in between 3 to 10 <br>";
	}	*/


	else if(!(txblyn==2)) {
		if(!checkNumber(hsnorsac))
			errmsg = errmsg + "Please Enter valid HSN/SAC CODE <br>";
		else if(!(hsnorsac.length>=3 && hsnorsac !="NA"))
			errmsg = errmsg + " HSN/SAC CODE must be in between 3 to 10 <br>";
	}
	
	if (!(acch > 0))
		errmsg = errmsg + "Please Select ACCOUNT HEAD <br>";
	else if((acch == 3 || acch == 6 || acch == 5) && (!(minorhd >0)))
        errmsg = errmsg + "Please Select Minor Head <br>";


	if (!qnty.length > 0)
		errmsg = errmsg + "Please Enter QUANTITY. <br>";
	else if (!(validateNumberMinMax(qnty, 0, 10000)))
		errmsg = errmsg
				+ "Please Enter Valid QUANTITY And It Must Be In Between 0 And 10,000 .<br>";
	
	if (!(uom > 0))
		errmsg = errmsg + "Please Select UOM <br>";
	
	if (!unitPrice.length > 0)
		errmsg = errmsg + "Please Enter UNIT PRICE <br>";
	else if (validateDot(unitPrice))
		errmsg = errmsg + "UNIT PRICE must contain atleast one number<br>";
	else if (!(validateDecimalNumberMinMax(unitPrice, 0, 10000000)))
		errmsg = errmsg + "Please Enter valid UNIT PRICE And It Must Be In Between 0 And 1,00,00,000<br>";
	else
		formobj.ur.value = round(parseFloat(unitPrice), 2);
	
	if (!(validateDecimalNumberMinMax(txblv, 0, 10000000)))
		errmsg = errmsg + "Invalid AMOUNT.. <br>";
	else if (!(validateDecimalNumberMinMax(eamt, 0, 10000000)))
		errmsg = errmsg + "Invalid AMOUNT.. <br>";
	
	return errmsg;
}

function validateEntries2(urv,qty, gstpsi, vpv,ptypecheck,ptypev,vryn,taxableyn,rcyn) {
	var errmsg = "";
	var formobj = document.getElementById('data_form');
	var vfryn = formobj.rcyn.selectedIndex;
   
	if($("input[type=radio][name=ptype]:checked").length <= 0) {
		errmsg = errmsg + "Please select PURCHASE TYPE<br>";			
	}
	
	if (!(taxableyn > 0))
        errmsg = errmsg + "Please Select TAXABLE YES or NO <br>";
/*	else if(taxableyn == 2 && rcyn == 1)
        errmsg = errmsg + "Please Select Valid TAXABLE YES or NO <br>";*/
	
	if(vfryn == 2){
	if(($("input[type=radio][name=ptype]:checked").length > 0)&&(ptypecheck != ptypev))
		errmsg = errmsg + "The PURCHASE TYPE you have selected might be wrong.Please check it again<br>";
	}
	if (!urv.length > 0)
		errmsg = errmsg + "Please Enter UNIT PRICE. <br>";
	else if (validateDot(urv))
		errmsg = errmsg + "UNIT PRICE must contain atleast one number. <br>";
	else if (!(validateDecimalNumberMinMax(urv, 0, 10000000)))
		errmsg = errmsg + "Please Enter valid UNIT PRICE and it must be In Between 0 And 1,00,00,000 .<br>";
	
	if (!qty.length > 0)
		errmsg = errmsg + "Please Enter QUANTITY. <br>";
	else if (!(validateNumberMinMax(qty, 0, 10000)))
		errmsg = errmsg + "Please Enter Valid QUANTITY And It Must Be In Between 0 And 10,000 .<br>";
	
	/*if((vryn == 2)|| (vryn == 1))
		errmsg = errmsg + "Please select valid REVERSE CHARGE For choosen VENDOR.<br>";
*/
	/*if (!vpv.length > 0)
		errmsg = errmsg + "Please Enter GST%. <br>";
	else if (!(checkNumber(vpv)))
		errmsg = errmsg + "Please Enter Valid GST% .<br>";
		*/
	if(!(gstpsi > 0))
		errmsg = errmsg + "Please Selcet GST%. <br>";

	return errmsg;
}

function validateEntries3(urv,qty,ptypecheck,ptypev,vryn,taxableyn,rcyn){

    var errmsg = "";
    var formobj = document.getElementById('data_form');
    var vfryn = formobj.rcyn.selectedIndex;

    if($("input[type=radio][name=ptype]:checked").length <= 0) {
            errmsg = errmsg + "Please select PURCHASE TYPE<br>";
    }

    if (!(taxableyn > 0))
            errmsg = errmsg + "Please Select TAXABLE YES or NO <br>";
    /*else if(taxableyn == 2 && rcyn == 1)
            errmsg = errmsg + "Please Select Valid REVERSE CHARGE <br>";*/


    if(vfryn == 2){
    if(($("input[type=radio][name=ptype]:checked").length > 0)&&(ptypecheck != ptypev))
            errmsg = errmsg + "The PURCHASE TYPE you have selected might be wrong.Please check it again<br>";
    }
    if (!urv.length > 0)
    	errmsg = errmsg + "Please Enter UNIT PRICE. <br>";
    else if (validateDot(urv))
    	errmsg = errmsg + "UNIT PRICE must contain atleast one number. <br>";
    else if (!(validateDecimalNumberMinMax(urv, 0, 10000000)))
        errmsg = errmsg + "Please Enter valid UNIT PRICE and it must be In Between 0 And 1,00,00,000 .<br>";
  

    if (!qty.length > 0)
            errmsg = errmsg + "Please Enter QUANTITY. <br>";
    else if (!(validateNumberMinMax(qty, 0, 10000)))
            errmsg = errmsg + "Please Enter Valid QUANTITY And It Must Be In Between 0 And 10,000 .<br>";

    return errmsg;
}

/*function deleteItem(iid,invdate) {
	var flag=validateDayEndForDelete(invdate,dedate);
	if(flag) {
		if (confirm("ARE YOU SURE YOU WANT TO DELETE?") == true) {
			var formobj = document.getElementById('m_data_form');
			formobj.actionId.value = "5118";
			formobj.dataId.value = iid;
			formobj.submit();
		}	
	}else{ 
		document.getElementById("dialog-1").innerHTML = "THIS RECORD CAN'T BE DELETED AS YOU HAVE SUBMITTED AND VERIFIED YOUR DAYEND CALCULATIONS SUCCUESSFULLY... ";
		alertdialogue();
		//alert("THIS RECORD CAN'T BE DELETED AS YOU HAVE SUBMITTED AND VERIFIED YOUR DAYEND CALCULATIONS SUCCUESSFULLY... ");	
}
}*/

function deleteItem(iid,invdate){
	var flag=validateDayEndForDelete(invdate,dedate);
	if(flag){
	 $("#myDialogText").text("Are You Sure You Want To Delete?");
	 var formobj = document.getElementById('m_data_form');
	 confirmDialogue(formobj,5118,iid);
	}else {
		document.getElementById("dialog-1").innerHTML = "This record can't be deleted as you have submitted and verified your DAYEND calculations succuessfully ";
		alertdialogue();
	}
}

function calculateValues() {
	var formobj = document.getElementById('data_form');
	var rcyn = formobj.rcyn.selectedIndex;
	var cvryn;var vryn;
	
	if(document.getElementById("ur") != null){
		/*if(formobj.vid.selectedIndex >0) {
			for(var d=0;d< cvo_data.length;d++){
				if(parseInt(formobj.vid.value) == cvo_data[d].id){
					var cvogstin = cvo_data[d].cvo_tin;
				     cvryn = cvo_data[d].is_gst_reg;
				}
			}
		}else {
			alert("please select VENDOR");
			return;
		}*/
		var cvogstin = "";
		var vendor = document.getElementById("answerInputHidden").value.trim();
		if(vendor.length >0) {
			if(venarr.indexOf(parseInt(vendor)) == -1) {
				document.getElementById("dialog-1").innerHTML = "The vendor you have entered is not valid. Please enter valid vendor";
				alertdialogue();
				//alert("The vendor you have entered is not valid. Please enter valid vendor ");
				return;
			}else {
				for(var d=0;d< cvo_data.length;d++){
					if(parseInt(vendor) == cvo_data[d].id){
						cvogstin = cvo_data[d].cvo_tin;
						cvryn = cvo_data[d].is_gst_reg;
					}
				}
			}
		}else {
			document.getElementById("dialog-1").innerHTML = "please enter VENDOR";
			alertdialogue();
			//alert("please enter VENDOR");
			return;
		}
		vryn =cvryn;
		var dstcode = dealergstin.substr(0, 2);
		var cvstcode = cvogstin.substr(0, 2);
		var ptypecheck = "";
		if(cvstcode == dstcode)
			ptypecheck = "lp"; 
		else 
			ptypecheck = "isp";
	
		var elements = document.getElementsByClassName("qtyc");
		var ptypev = formobj.ptype.value;
		if(elements.length==1) {
			var urv = formobj.ur.value.trim();
            var qty = formobj.qty.value.trim();
            var gstpsi = formobj.egst.selectedIndex;
            var vpv = formobj.egst.options[gstpsi].value;
            var taxableyn = formobj.tbl.selectedIndex;
            var spfrz= document.getElementById("egst").options;
            var efrz = document.getElementById("hsnc");
            if(taxableyn == 2){
            	formobj.hsnc.value = "NA";
            	formobj.igsta.value = "NA";
            	formobj.cgsta.value = "NA";
            	formobj.sgsta.value = "NA";
            	efrz.setAttribute("readOnly","true");
            	//makeEleReadOnly(efrz,efrz.length);
            	formobj.egst.selectedIndex=0;
            	disableSelect(spfrz,spfrz.length);
            	
            	var ems = validateEntries3(urv,qty,ptypecheck,ptypev,vryn,taxableyn,rcyn);
            	if (ems.length > 0) {
            		document.getElementById("dialog-1").innerHTML = ems;
        			alertdialogue();
            		//alert(ems);
                    return;
            	}
            	
            	formobj.ur.value = parseFloat(urv).toFixed(2);
            	formobj.tval.value = parseFloat(urv * qty).toFixed(2);
            	formobj.ta.value = (+(formobj.tval.value) + +(0)).toFixed(2);
            	formobj.inv_amt.value = Math.round(formobj.ta.value);
            }else {   
            	//efrz.readOnly=false;
            	//remEleReadOnly(efrz,efrz.length);
            	enableSelect(spfrz,spfrz.length);
            	var ems = validateEntries2(urv,qty, gstpsi, vpv,ptypecheck,ptypev,vryn,taxableyn,rcyn);
            	if (ems.length > 0) {
            		document.getElementById("dialog-1").innerHTML = ems;
        			alertdialogue();
            		//alert(ems);
                    return;
            	}
            	
            	formobj.ur.value = parseFloat(urv).toFixed(2);
            	formobj.tval.value = parseFloat(urv * qty).toFixed(2);
            	var gstav = (((urv*qty)*vpv)/100).toFixed(2);
            	formobj.ta.value = (+(formobj.tval.value) + +(gstav)).toFixed(2);
            	formobj.inv_amt.value = Math.round(formobj.ta.value);
            	var ptypev = formobj.ptype.value;
            	if(ptypev=='lp') {
            		formobj.igsta.value=0.00;
            		formobj.sgsta.value=((gstav)/2).toFixed(2);
            		formobj.cgsta.value=((gstav)/2).toFixed(2);
            	} else if (ptypev=='isp') {
            		formobj.igsta.value=(gstav);
            		formobj.sgsta.value=0.00;
            		formobj.cgsta.value=0.00;
            	}
            }
    	} else if (elements.length>1){
            var totalAmt = 0;
            var efrz = document.getElementsByClassName("hsnr");
            for(var i=0; i<elements.length; i++){
            	var urv = formobj.ur[i].value.trim();var vryn;
            	var qty = formobj.qty[i].value.trim();
            	var gstpsi = formobj.egst[i].selectedIndex;
            	var vpv = formobj.egst[i].options[gstpsi].value;
            	var pfrz=formobj.egst[i].options;
            	var taxableyn = formobj.tbl[i].selectedIndex;  
            	
            	if(taxableyn == 2){
            		formobj.hsnc[i].value = "NA";
            		formobj.igsta[i].value = "NA";
            		formobj.cgsta[i].value = "NA";
            		formobj.sgsta[i].value = "NA";
                
            		//makeEleReadOnly(efrz,efrz.length);
            		formobj.egst.selectedIndex= 0;
                    disableSelect(pfrz,pfrz.length);
                    
                    var ems = validateEntries3(urv,qty,ptypecheck,ptypev,vryn,taxableyn,rcyn);
                	if (ems.length > 0) {
                		document.getElementById("dialog-1").innerHTML = ems;
            			alertdialogue();
                		//alert(ems);
                		return;
                	}
                	
                    formobj.ur[i].value = parseFloat(urv).toFixed(2);
                    formobj.tval[i].value = parseFloat(urv * qty).toFixed(2);
                    formobj.ta[i].value = (+(formobj.tval[i].value) + +(0)).toFixed(2);
                    totalAmt = +totalAmt + +(formobj.ta[i].value);
                    formobj.inv_amt.value = Math.round(formobj.ta.value);
            	}else{
            		remEleReadOnly(efrz,efrz.length);
            		enableSelect(pfrz,pfrz.length);
            		
            		var ems = validateEntries2(urv,qty, gstpsi, vpv,ptypecheck,ptypev,vryn,taxableyn,rcyn);
                	if (ems.length > 0) {
                		document.getElementById("dialog-1").innerHTML = ems;
            			alertdialogue();
                		//alert(ems);
                		return;
                	}
                	
            		formobj.ur[i].value = parseFloat(urv).toFixed(2);
            		formobj.tval[i].value = parseFloat(urv * qty).toFixed(2);
            		var gstav = (((urv*qty)*vpv)/100).toFixed(2);
            		formobj.ta[i].value = (+(formobj.tval[i].value) + +(gstav)).toFixed(2);
            		totalAmt = +totalAmt + +(formobj.ta[i].value);
            		var ptypev = formobj.ptype.value;
            		if(ptypev=='lp') {
            			formobj.igsta[i].value=0.00;
            			formobj.sgsta[i].value=((gstav)/2).toFixed(2);
            			formobj.cgsta[i].value=((gstav)/2).toFixed(2);
            		} else if (ptypev=='isp') {
            			formobj.igsta[i].value=(gstav);
            			formobj.sgsta[i].value=0.00;
            			formobj.cgsta[i].value=0.00;
            		}
            	}
            }
            formobj.inv_amt.value=Math.round(totalAmt);
    	}
	}else {
		document.getElementById("dialog-1").innerHTML = "Please Add Data and Click Calculate";
		alertdialogue();
		//alert("Please Add Data and Click Calculate");
		return;
	}
	document.getElementById("save_data").disabled=false;
	$(':radio:not(:checked)').attr('disabled', true);
	restrictChangingAllValues(".freez");
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

function fetchGSTValues() {
	var formobj = document.getElementById('data_form');
	if(document.getElementById("up") != null){
		
		var elements = document.getElementsByClassName("qtyc");
		if(elements.length==1) {
			var pide = formobj.epid;
			var pidv = pide.options[pide.selectedIndex].value;
			formobj.vp.value = fetchARBGSTPercent(cat_types_data,pidv);
		} else if (elements.length>1){
			for(var i=0; i<elements.length; i++){
				var pide = formobj.epid[i];
				var pidv = pide.options[pide.selectedIndex].value;
				formobj.vp[i].value = fetchARBGSTPercent(cat_types_data,pidv);
			}
		}
		
	}else {
		document.getElementById("dialog-1").innerHTML = "Please Select Product and Click Fetch Unit Price and VAT";
		alertdialogue();
		//alert("Please Select Product and Click Fetch Unit Price and VAT");
		return;
	}
	document.getElementById("calc_data").disabled=false;
}

function setCH(){
    var formobj = document.getElementById('data_form');
    var elements = document.getElementsByClassName("qtyc");
    var sminho;
    var sminhv;
    if(elements.length==1) {
    	var spfrz= document.getElementById("ah").options;
    	sminho = formobj.minh;
    	sminhv = sminho.options[sminho.selectedIndex].value;
    	var minhtolab = findCH(sminhv);
    	formobj.ch.value = minhtolab;
    	if(minhtolab == 1 || minhtolab == 2 || minhtolab == 3 || minhtolab == 9){
    		enableSelect(spfrz,spfrz.length); 
    		formobj.ah.selectedIndex = 3;
    		disableSelect(spfrz,spfrz.length);
    	}else if(minhtolab == 4 || minhtolab == 5 || minhtolab == 6 || minhtolab == 7 || minhtolab == 8 || minhtolab == 10 || minhtolab == 11 || minhtolab == 12 || minhtolab == 13){
    		enableSelect(spfrz,spfrz.length);
    		formobj.ah.selectedIndex = 6;
    		disableSelect(spfrz,spfrz.length);
    	}else if(minhtolab == 14){
    		enableSelect(spfrz,spfrz.length);
    		formobj.ah.selectedIndex = 5;
    		disableSelect(spfrz,spfrz.length);
    	}else{
    		// formobj.ah.selectedIndex = 0;    
    		enableSelect(spfrz,spfrz.length);
    	}
    } else if (elements.length>1){
    	for(var i=0; i<elements.length; i++){
    		var spfrz=formobj.ah[i].options;
    		sminho = formobj.minh[i];
    		sminhv = sminho.options[sminho.selectedIndex].value;
    		var minhtolab = findCH(sminhv);
    		formobj.ch[i].value = minhtolab;
    		if(minhtolab == 1 || minhtolab == 2 || minhtolab == 3 || minhtolab == 9){
    			enableSelect(spfrz,spfrz.length); 
    			formobj.ah[i].selectedIndex = 3;
    			disableSelect(spfrz,spfrz.length);
    		}else if(minhtolab == 4 || minhtolab == 5 || minhtolab == 6 || minhtolab == 7 || minhtolab == 8 || minhtolab == 10 || minhtolab == 11 || minhtolab == 12 || minhtolab == 13){
    			enableSelect(spfrz,spfrz.length);    
    			formobj.ah[i].selectedIndex = 6;
    			disableSelect(spfrz,spfrz.length);
    		}else if(minhtolab == 14){
    			enableSelect(spfrz,spfrz.length);  
    			formobj.ah[i].selectedIndex = 5;
    			disableSelect(spfrz,spfrz.length);
    		}else{
    			//	formobj.ah[i].selectedIndex = 0;   
    			enableSelect(spfrz,spfrz.length);
    		}
    	}
    }
}

function settaxv(){
	var formobj = document.getElementById('data_form');
	var elements = document.getElementsByClassName("qtyc");
	var txbl;var gst;
	
	if(elements.length==1) {
		var spfrz= document.getElementById("egst").options;
        var efrz = document.getElementById("hsnc");
		 txbl = formobj.tbl.selectedIndex;
		 if(txbl == 1){
			 efrz.readOnly= false;
			 enableSelect(spfrz,spfrz.length);
			// formobj.hsnc.value = "";
			// txbl = 0;
		 }else{
			 formobj.hsnc.value = "NA";
			 txbl = 0;
			 efrz.setAttribute("readOnly","true"); 
			 enableSelect(spfrz,spfrz.length);
			 formobj.egst.selectedIndex = 0;
			 disableSelect(spfrz,spfrz.length);
		 }
	} else if (elements.length>1){
		var efrz1 = document.getElementsByClassName("hsnr");
		for(var i=0; i<elements.length; i++){
			 txbl = formobj.tbl[i].selectedIndex;
			 var pfrz=formobj.egst[i].options;
			 if(txbl == 1){
				// makeEleReadOnly(efrz1,efrz1.length);
				 enableSelect(pfrz,pfrz.length);
				// formobj.hsnc[i].value = "";
				// txbl = 0;
				
			 }else{
				 formobj.hsnc[i].value = "NA";
				 txbl = 0;
				 remEleReadOnly(efrz1,efrz1.length);
				 enableSelect(pfrz,pfrz.length);
				 formobj.egst[i].selectedIndex = 0;
				 disableSelect(pfrz,pfrz.length);
			 }
				
		}
	}
}

function findCH(sminhv){
	
	if( (sminhv == 141) || (sminhv == 142) || (sminhv == 143) || (sminhv == 144) 
			|| (sminhv == 145) || (sminhv == 146) || (sminhv == 147) ) {
		return 14;
	}else if ( sminhv >10 && sminhv <20 ) {
		return 1;
	} else if ( sminhv >20 && sminhv <30 ) {
		return 2;
	} else if ( sminhv >30 && sminhv <40 ) {
		return 3;
	} else if ( sminhv >40 && sminhv <50 ) {
		return 4;
	} else if ( sminhv >50 && sminhv <60 ) {
		return 13;
	} else if ( sminhv >60 && sminhv <70 ) {
		return 6;
	} else if ( sminhv >70 && sminhv <80 ) {
		return 7;
	} else if ( sminhv >80 && sminhv <90 ) {
		return 8;
	} else if ( sminhv >90 && sminhv <100 ) {
		return 9;
	} else if ( sminhv >100 && sminhv <110 ) {
		return 10;
	} else if ( sminhv >110 && sminhv <120 ) {
		return 13;
	} else if ( sminhv >120 && sminhv <130 ) {
		return 4;
	} else if ( sminhv >130 && sminhv <140 ) {
		return 13;
	}
	
	return 0;
}
