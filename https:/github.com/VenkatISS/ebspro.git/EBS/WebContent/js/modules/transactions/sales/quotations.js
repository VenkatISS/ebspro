function showQuoFormDialog() {
	document.getElementById('myModal').style.display = "block";
	pushMenu();
}


function closeQuoFormDialog() {
	document.getElementById("calc_data").disabled = true;
	document.getElementById("save_data").disabled = true;
	var formobj = document.getElementById('data_form');	
	var elements = document.getElementsByClassName("qtyc");
	var efrz = document.getElementsByClassName("freez");
	remEleReadOnly(efrz,efrz.length);
	if (elements.length == 1) {
		var spfrz= document.getElementById("epid").options;
		enableSelect(spfrz,spfrz.length);

	} else if (elements.length > 1) {
		for (var i = 0; i < elements.length; i++) {
			var pfrz=formobj.epid[i].options;
			enableSelect(pfrz,pfrz.length);			
		}		
	}

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

function changeSaleTypeBasedOnCust() {
	$(':radio:not(:checked)').attr('disabled', false);
	document.getElementById("save_data").disabled=true;
}

/*//Construct Customer Type html
custdatahtml = "<OPTION VALUE='0'>SELECT</OPTION>";
if(cvo_data.length>0) {
	for(var z=0; z<cvo_data.length; z++){
		if(cvo_data[z].cvo_cat==1){
			if((cvo_data[z].deleted == 0) && (cvo_data[z].cvo_name != "UJWALA")){
				custdatahtml=custdatahtml+"<OPTION VALUE='"+cvo_data[z].id+"'>"+cvo_data[z].cvo_name+"</OPTION>";
			}
		}
	}
}
document.getElementById("cSpan").innerHTML = "<select id='cust_id' class='form-control input_field select_dropdown' name='cust_id' onchange='changeSaleTypeBasedOnCust()'>"+custdatahtml+"</select>";
*/

//Construct Staff html
staffdatahtml = "<OPTION VALUE='0'>SELECT</OPTION>";
if(staff_data.length>0) {
	for(var z=0; z<staff_data.length; z++) {
		if(staff_data[z].deleted == 0){
		staffdatahtml=staffdatahtml+"<OPTION VALUE='"+staff_data[z].id+"'>"+staff_data[z].emp_name+"</OPTION>";
		}	
	}
}
document.getElementById("sSpan").innerHTML = "<select id='staff_id' class='form-control input_field select_dropdown' name='staff_id'>"+staffdatahtml+"</select>";


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


function showPriceFormDialog() {
	document.getElementById('refillPriceModal').style.display = "block";
	document.getElementById('myModalmRefilpricepopupPin').style.display = "block";
	document.getElementById('savemrefillpricepopupdiv').style.display = "none";
    $("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');
}

function closeRefillPriceFormDialog() {
//	document.getElementById("data_form").reset();
	document.getElementById("mrefiilpricepopup_page_data_form").reset();
	document.getElementById("pin_data_form").reset();
	document.getElementById('mrefillpricepopup_page_add_table_body').innerHTML = "";
	document.getElementById('mymRefillPricePopupDIV').style.display = 'none';
	document.getElementById('refillPriceModal').style.display = "none";
$("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');
}

function showStaffFormDialog() {
	document.getElementById('staffModal').style.display = "block";
	document.getElementById('mstaff_popupsavediv').style.display = "none";
    $("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');
}

function closeStaffFormDialog() {
//	document.getElementById("data_form").reset();
	document.getElementById("mpopupstaff_data_form").reset();
	document.getElementById('mpopup_staff_add_table_body').innerHTML = "";
	document.getElementById('staffModal').style.display = "none";
	document.getElementById('myStaffDataDIV').style.display = 'none';
$("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');
}



 /*new end*/
//Process page data
var rowpdhtml = "";
if(page_data.length>0) {
	for(var z=page_data.length-1; z>=0; z--){
		var ed = new Date(page_data[z].qtn_date);
		var custName = "";
		if(page_data[z].customer_id == 0 )
			custName = page_data[z].customer_name;
		else
			custName = getCustomerName(cvo_data,page_data[z].customer_id);
			
		var staffName = getStaffName(staff_data,page_data[z].staff_id);
		if(!staffName){			
			staffName = "NA";		
		}
		var qlistLen = page_data[z].quotationItemsList.length;
		for(var i=page_data[z].quotationItemsList.length-1; i>=0; i--){
			var spd = "";
			var prodId = page_data[z].quotationItemsList[i].prod_code;
			if(prodId < 100) {
				spd = fetchProductDetails(prod_types, page_data[z].quotationItemsList[i].prod_code);
			} else {
				spd = fetchARBProductDetails(cat_arb_types_data, page_data[z].quotationItemsList[i].prod_code);
			}
			//if(i==0) {
			rowpdhtml = rowpdhtml + "<tr><td><a href='javascript:showInvoice("+page_data[z].id+","+page_data[z].qtn_date+")'>"+page_data[z].sr_no+"</a></td>";
			rowpdhtml = rowpdhtml + "<td>"+ed.getDate()+"-"+months[ed.getMonth()]+"-"+ed.getFullYear()+"</td>";
			rowpdhtml = rowpdhtml + "<td>"+custName+"</td>";
			rowpdhtml = rowpdhtml + "<td>"+staffName+"</td>";
			rowpdhtml = rowpdhtml + "<td>"+page_data[z].qtn_amount+"</td>";

			rowpdhtml = rowpdhtml + "<td>"+spd+"</td>"+
						"<td>"+page_data[z].quotationItemsList[i].vatp+"</td>"+
						"<td>"+page_data[z].quotationItemsList[i].unit_rate+"</td>"+
						"<td>"+page_data[z].quotationItemsList[i].disc_unit_rate+"</td>"+
						"<td>"+page_data[z].quotationItemsList[i].basic_amount+"</td>"+
						"<td>"+page_data[z].quotationItemsList[i].quantity+"</td>"+
						"<td>"+page_data[z].quotationItemsList[i].igst_amount+"</td>"+						
						"<td>"+page_data[z].quotationItemsList[i].cgst_amount+"</td>"+
						"<td>"+page_data[z].quotationItemsList[i].sgst_amount+"</td>"+
						"<td>"+page_data[z].quotationItemsList[i].prod_amount+"</td>"+
						"<td>"+page_data[z].foot_notes+"</td>"+
						"<td align='center'><img src='images/delete.png' onclick='deleteItem("+page_data[z].id+","+page_data[z].qtn_date+","+qlistLen+")'></td>"+"</tr>";

		}
	}
}
document.getElementById('m_data_table_body').innerHTML = rowpdhtml;

function addRow() {
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
	document.getElementById("save_data").disabled=true;
	
	var ele = document.getElementsByClassName("qtyc");
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
//		var k = newRow.insertCell(11);	

		a.innerHTML = "<td><select name='epid' class='form-control input_field select_dropdown sadd tp epid' id='epid'>"+ctdatahtml+"</select></td>";
		b.innerHTML = "<td><input type=text name='vp' class='form-control input_field eadd' id='vp' size='8' readonly='readonly' placeholder='Gst%' style='background-color:#FAFAC2;'></td>";
		c.innerHTML = "<td><input type=text name='up' class='form-control input_field eadd' id='up' maxlength='7' size='8' readonly='readonly' placeholder='Unit Price' style='background-color:#FAFAC2;'></td>";
		d.innerHTML = "<td><input type=text name='upd' class='form-control input_field freez eadd' id='upd' size='8' maxlength='7' size='8' placeholder='Discount On Unit Price' ></td>";
		e.innerHTML = "<td><input type=text name='qty' class='form-control input_field qtyc freez eadd' id='qty' size='8' maxlength='4' placeholder='Quantity'></td>";
		f.innerHTML = "<td><input type=text name='bp' class='form-control input_field eadd' id='bp' size='8' size='8' value='0.00' readonly='readonly' placeholder='TAXABLE AMOUNT' style='background-color:#F3F3F3;'></td>";
		g.innerHTML = "<td><input type=text name='igsta' class='form-control input_field eadd' id='igsta' size='8' readonly='readonly' placeholder='IGST' style='background-color:#F3F3F3;'></td>";
		h.innerHTML = "<td><input type=text name='cgsta' class='form-control input_field eadd' id='cgsta' size='8' readonly='readonly' placeholder='CGST' style='background-color:#F3F3F3;'></td>";
		i.innerHTML = "<td><input type=text name='sgsta' class='form-control input_field eadd' id='sgsta' size='8' readonly='readonly' placeholder='SGST' style='background-color:#F3F3F3;'></td>";
		j.innerHTML = "<td><input type=text name='amt' class='form-control input_field eadd' id='amt' size='8' readonly='readonly' placeholder='Total Amount' style='background-color:#F3F3F3;'></td>";
//		k.innerHTML = "<td><input type=text name='fn' class='form-control input_field eadd' id='fn' maxlength='100' size='8' placeholder='Foot Notes'></td>";
		k.innerHTML = "<td><img src='images/delete.png' onclick='doRowDeleteInTranxs(this,amt,qtn_c_amt,data_table_body)'></td>";
		
		document.getElementById("fetch_data").disabled=false;
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
	
	if(document.getElementById("epid") != null){
		var elements = document.getElementsByClassName("qtyc");
		
		var fn = formobj.fn.value.trim();
//		var cust = formobj.cust_id.selectedIndex;
		var cust = document.getElementById('answerInputHidden').value.trim(); // here we will get CUST ID
		var custname = document.getElementById('answerInput').value.trim(); // here we will get CUST NAME
		
		if(elements.length==1) {
			var qtndate = formobj.qtn_date.value.trim();
			var e = document.getElementById("epid");
	        var productId = e.options[e.selectedIndex].value;
			var item = formobj.epid.selectedIndex;
			var up = formobj.up.value.trim();
			var bp = formobj.bp.value.trim();
			var upd = formobj.upd.value.trim();
			var qty = formobj.qty.value;
			var igsta = formobj.igsta.value;
			var sgsta = formobj.sgsta.value;
			var cgsta = formobj.cgsta.value;
			var samt = formobj.amt.value;	
			
			if(checkDateFormate(qtndate)){
				var ved = dateConvert(qtndate);
				formobj.qtn_date.value = ved;
				qtndate=ved;
			}			
			 ems = validateEntries(qtndate,cust,item,up,bp,upd,qty,fn,igsta,sgsta,cgsta,samt,productId);
		}else if (elements.length>1){
			for(var i=0; i<elements.length; i++) {
				var qtndate = formobj.qtn_date.value.trim();
//				var cust = formobj.cust_id.selectedIndex;
				var e = formobj.epid[i];
		        var productId = e.options[e.selectedIndex].value;
				var item = formobj.epid[i].selectedIndex;
				var up = formobj.up[i].value.trim();
				var bp = formobj.bp[i].value.trim();				
				var upd = formobj.upd[i].value.trim();
				var qty = formobj.qty[i].value;
				var igsta = formobj.igsta[i].value;
				var sgsta = formobj.sgsta[i].value;
				var cgsta = formobj.cgsta[i].value;
				var samt = formobj.amt[i].value;
				
				if(checkDateFormate(qtndate)){
					var ved = dateConvert(qtndate);
					formobj.qtn_date[i].value = ved;
					qtndate=ved;
				}
				ems = validateEntries(qtndate,cust,item,up,bp,upd,qty,fn,igsta,sgsta,cgsta,samt,productId);
				if(ems.length>0)
					break;
			}
		}
		
		/*document.getElementById('cust_name').value = custname;
		if((parseInt(cust)!=0) && (custarr.indexOf(parseInt(cust))) == -1)
			document.getElementById('answerInputHidden').value = 0;*/
		
	}else {
		document.getElementById("dialog-1").innerHTML = "Please Add Data";
		alertdialogue();
		//alert("Please Add Data");
		return;
	}
	
	if(ems.length>0) {
		document.getElementById("dialog-1").innerHTML = ems;
		alertdialogue();
		//alert(ems);
		return;
	}
	
	document.getElementById('cust_name').value = custname;
	if((parseInt(cust)!=0) && (custarr.indexOf(parseInt(cust))) == -1)
		document.getElementById('answerInputHidden').value = 0;
	
	var objId = obj.id;
	document.getElementById(objId).disabled = true;
	formobj.submit();
}

function validateProduct() {
	var flag=true;
	var prodar= new Array();
	var ele = document.getElementsByClassName("epid");
	for (var i=0;i<ele.length; i++) {
		var e = ele[i].options[ele[i].selectedIndex].text;
		prodar.push(e);
	}		
    
    for(var j = 0; j <= prodar.length; j++) {
        for(var k = j; k <= prodar.length; k++) {
            if(j != k && prodar[j] == prodar[k]) {
                flag=false;
            }
        }
    }
    return flag;
}

function validateDisount(upd,productId){

    var formobj = document.getElementById('data_form');
//    var cust = formobj.cust_id.value;
    var cust = document.getElementById('answerInputHidden').value.trim();

    var returnArray = new Array();
    
    for(var c=0;c<cust_cl_data.length;c++) {
    	var cust_id = cust_cl_data[c].cust_id;
    	var disc_19kg_ndne=cust_cl_data[c].disc_19kg_ndne;
    	var disc_19kg_cutting_gas=cust_cl_data[c].disc_19kg_cutting_gas;
    	var disc_35kg_ndne=cust_cl_data[c].disc_35kg_ndne;
    	var disc_47_5kg_ndne=cust_cl_data[c].disc_47_5kg_ndne;
    	var disc_450kg_sumo=cust_cl_data[c].disc_450kg_sumo;

    	var cctype = cust_cl_data[c].cc_type;

    	if(cust_id == cust) {
//        	var cctype = cust_cl_data[c].cc_type;
        	returnArray.push(cctype);
        	
        	if((productId === '5') && (upd > parseFloat(disc_19kg_ndne))) {
    			returnArray.push('Discount of 19kg NDNE cylinder is not valid. It exceeds specified discount limit of customer. Please check it in credit limit master.');
    			return returnArray;
            }else if((productId === '6') && (upd > parseFloat(disc_19kg_cutting_gas))) {
            	returnArray.push('Discount of 19kg CUTTING GAS cylinder is not valid. It exceeds specified discount limit of customer. Please check it in credit limit master.');
            	return returnArray;
            }else if((productId === '7') && (upd > parseFloat(disc_35kg_ndne))) {
            	returnArray.push('Discount of 35kg NDNE cylinder is not valid. It exceeds specified discount limit of customer. Please check it in credit limit master.');
            	return returnArray;
            }else if((productId === '8') && (upd > parseFloat(disc_47_5kg_ndne))) {
            	returnArray.push('Discount of 47.5kg NDNE cylinder is not valid. It exceeds specified discount limit of customer. Please check it in credit limit master.');
            	return returnArray;
            }else if((productId === '9') && (upd > parseFloat(disc_450kg_sumo))) {
            	returnArray.push('Discount of 450kg SUMO cylinder is not valid. It exceeds specified discount limit of customer. Please check it in credit limit master.');
            	return returnArray;
            }
    		break;
    	}
    }
    return false;
}

function validateEntries(qtndate,cust,item,up,bp,upd,qty,fn,igsta,sgsta,cgsta,samt,productId) {
	
	var formobj = document.getElementById('data_form');
	var errmsg = "";

	var vd = isValidDate(qtndate);
	var vfd = ValidateFutureDate(qtndate);
	if(!(qtndate.length>0))
		errmsg = errmsg+"Please Enter QUOTATION DATE<br>";
	else if (vd != "false")
		errmsg = errmsg +vd+"<br>";
	else if(validateDayEndAdd(qtndate,dedate)){
        errmsg = "QUOTATION DATE should be after DayEndDate<br>";
        return errmsg;
	}else if (vfd != "false")
		errmsg = errmsg +vfd+"<br>";
/*	else {
		var lastTrxnDateCheck = checkforLastTrxnDate(dedate,a_created_date,qtndate,trdate);
		if(lastTrxnDateCheck != "false") {
			return lastTrxnDateCheck;
		}
	}*/
	
	if(!(cust.length>0))
		errmsg = errmsg+"Please enter CUSTOMER NAME.<br>";
	/*else {
		if((parseInt(cust)!=0) && (custarr.indexOf(parseInt(cust))) == -1)
			errmsg = errmsg + "The CUSTOMER you have entered is not valid. Please enter valid CUSTOMER <br>";
	}*/
	
	if($("input[type=radio][name=stype]:checked").length <= 0) {
		errmsg = errmsg + "Please select SALE TYPE<br>";			
	}  
	
	if(!(item>0))
		errmsg = errmsg+"Please Select PRODUCT<br>";
	else if (!validateProduct()) 
		errmsg = errmsg + "Please Select Another PRODUCT Which Was not Selected.<br>";

	if(!up.length>0)
		  errmsg = errmsg + "Please Enter UNIT PRICE. <br>";
	else if(validateDot(up)) 
		errmsg = errmsg+"UNIT PRICE must contain atleast one number. <br>";
	else if(!(validateDecimalNumberMinMax(up,0,100000)))
		errmsg = errmsg + "Please Enter valid UNIT PRICE And It Must Be In Between 0 And 1,00,000<br>";
	else
		formobj.up.value=round(parseFloat(up),2);

	var diserr=validateDisount(upd,productId);
	
	if(!upd.length>0)
		  errmsg = errmsg + "Please Enter DISCOUNT ON UNIT PRICE.Enter 0 if not avaliable.<br>";
	else if(validateDot(upd)) 
		errmsg = errmsg+"DISCOUNT ON UNIT PRICE. It must contain atleast one number. <br>";
	else if(!(validateDecimalNumberMinMax(upd,-1,10000)))
		errmsg = errmsg + "Please Enter Valid DISCOUNT ON UNIT PRICE.It Must Be >=0 and <10000. <br>";
	else if((parseFloat(upd)>parseFloat(up)) && (parseFloat(up)!=0))
		errmsg = errmsg + "DISCOUNT ON UNIT PRICE Must Be Less Than UNIT PRICE.<br>";
	else if(diserr != false ) {
		if(diserr[0]==1) {// block
			document.getElementById("dialog-1").innerHTML = diserr[1];
			alertdialogue();
			return;
		}else if(diserr[0]==2) { // warn
			$("#myDialogText").text(diserr[1] +" Do You Want To Save ?");
			confirmTxtDialogue(formobj);
			return;
		}
	}else
        formobj.upd.value=round(parseFloat(upd),2);
	
	
	if(!qty.length>0)
		  errmsg = errmsg + "Please Enter PRODUCT QUANTITY. <br>";
	else if(!(validateNumberMinMax(qty,0,10000)))
		errmsg = errmsg + "Please Enter Valid QUANTITY And It Must Be In Between 0 And 10,000 .<br>";

	
	if(!(fn.length>0))
		errmsg = errmsg+"Please enter Footnotes.Enter NA if Not Applicable.<br>";
	
	
	if(!(validateDecimalNumberMinMax(bp,0,10000000)))
		errmsg = errmsg + "Invalid BASIC AMOUNT.<br>";
	if(!(validateDecimalNumberMinMax(igsta,-1, 10000000)))
		errmsg = errmsg + "Invalid GST.. <br>";	
	if(!(validateDecimalNumberMinMax(sgsta,-1,10000000)))
		errmsg = errmsg + "Invalid GST..<br>";
	if(!(validateDecimalNumberMinMax(cgsta,-1,10000000)))
		errmsg = errmsg + "Invalid GST.. <br>";
	if(!(validateDecimalNumberMinMax(samt,-1,10000000)))
		errmsg = errmsg + "Invalid TOTAL AMOUNT.<br>";

	return errmsg;
}

/*function deleteItem(iid,invdate,detailsListlen) {
	var flag=validateDayEndForDelete(invdate,dedate);
	var formobj = document.getElementById('m_data_form');
	if(flag) {
		if(detailsListlen>1){
			if (confirm("THIS INVOICE CONTAINS MULTIPLE PRODUCTS.IF YOU DELETE THIS ONE TOTAL INVOICE WILL GET DELETED.ARE YOU SURE YOU WANT TO DELETE?") == true) {
				formobj.actionId.value = "5143";
				formobj.dataId.value = iid;
				formobj.submit();
			}
		}else if(detailsListlen==1){
			if (confirm("ARE YOU SURE YOU WANT TO DELETE?") == true) {
				formobj.actionId.value = "5143";
				formobj.dataId.value = iid;
				formobj.submit();
			}
		}	
	}else{ 
		document.getElementById("dialog-1").innerHTML = "Please safsdsadasd Data";
		alertdialogue();	
		//alert("THIS RECORD CAN'T BE DELETED AS YOU HAVE SUBMITTED AND VERIFIED YOUR DAYEND CALCULATIONS SUCCUESSFULLY... ");
	
}}*/

function deleteItem(iid,invdate,detailsListlen){
	var flag=validateDayEndForDelete(invdate,dedate);
	if(flag){
		var formobj = document.getElementById('m_data_form');
		if(detailsListlen>1){
			$("#myDialogText").text("THIS INVOICE CONTAINS MULTIPLE PRODUCTS.IF YOU DELETE THIS ONE TOTAL INVOICE WILL GET DELETED.ARE YOU SURE YOU WANT TO DELETE?");
			
			confirmDialogue(formobj,5143,iid);
		}else if(detailsListlen==1){
			$("#myDialogText").text("Are You Sure You Want To Delete?");
			confirmDialogue(formobj,5143,iid);	
		}
	}else {
		document.getElementById("dialog-1").innerHTML = "This record can't be deleted as you have submitted and verified your DAYEND calculations succuessfully";
		alertdialogue();
	}
}

function fetchPriceAndVAT(){
	var formobj = document.getElementById('data_form');
	if(document.getElementById("bp") != null) {	
		var dsdate = formobj.qtn_date.value;
		var sdate = new Date(dsdate);
		var mon = sdate.getMonth();
		var yr = sdate.getFullYear();
		var pyear = ["2018","2019","2020"];
		var elements = document.getElementsByClassName("qtyc");
		if(elements.length==1) {
			var pide = formobj.epid;
			if(pide.selectedIndex > 0) {
				var pidv = pide.options[pide.selectedIndex].value;
				var gstp = fetchGSTPercent(equipment_data,pidv) != 0 ? fetchGSTPercent(equipment_data,pidv) : fetchARBGSTPercent(arb_data,pidv);
				var upv = fetchRefillUnitPrice(refill_prices_data,pidv) != 0 ? fetchRefillUnitPrice(refill_prices_data,pidv) : fetchARBUnitPrice(arb_prices_data,pidv);	
				if(gstp>0 && upv>0) {
					for(var p=0; p<cat_cyl_types_data.length; p++){
						var cproduct = cat_cyl_types_data[p].cat_name+"-"+cat_cyl_types_data[p].cat_desc;
						var prodid = cat_cyl_types_data[p].id;
						
						for(var i=0;i<refill_prices_data.length;i++){
							if(cproduct == pide.options[pide.selectedIndex].innerHTML) {
								if(dsdate != ""){
								if(prodid == refill_prices_data[i].prod_code) {
									if(!(refill_prices_data[i].montha == mon && pyear[refill_prices_data[i].yeara] == yr)){
										document.getElementById("dialog-1").innerHTML = "please define the price of product for sale invoice month in price master and continue";
										alertdialogue();
										//alert("please define the price of product for sale invoice month in price master and continue");
										return;
									}else {
										formobj.vp.value = gstp;
										formobj.up.value = upv;
										break;
									}
								}	
							
								}else  if(dsdate == ""){
									document.getElementById("dialog-1").innerHTML = "Please Enter INVOICE DATE";
									alertdialogue();
									//alert("Please Enter INVOICE DATE");
									return;
								}
							}
							
						}
					}
				}else if(gstp<=0){
					document.getElementById("dialog-1").innerHTML = "Please ADD the PRODUCT in EQUIPMENT MASTER.";
					alertdialogue();
					//alert("Please ADD the PRODUCT in EQUIPMENT MASTER.");
					return;
				}else if(upv<=0){
					document.getElementById("dialog-1").innerHTML = "Please ADD PRODUCT'S price in REFILL PRICE MASTER.";
					alertdialogue();
					//alert("Please ADD PRODUCT'S price in REFILL PRICE MASTER.");
					return;	
				}
			}else{
				document.getElementById("dialog-1").innerHTML = "Please Select PRODUCT And Then Click On FETCH UNIT PRICE AND GST";
				alertdialogue();
				//alert("Please Select PRODUCT And Then Click On FETCH UNIT PRICE AND GST");
				return;
			}
		} else if (elements.length>1){
			for(var i=0; i<elements.length; i++){
				var pide = formobj.epid[i];
				if(pide.selectedIndex > 0) {
					var pidv = pide.options[pide.selectedIndex].value;
					var gstp = fetchGSTPercent(equipment_data,pidv) != 0 ? fetchGSTPercent(equipment_data,pidv) : fetchARBGSTPercent(arb_data,pidv);
					var upv = fetchRefillUnitPrice(refill_prices_data,pidv) != 0 ? fetchRefillUnitPrice(refill_prices_data,pidv) : fetchARBUnitPrice(arb_prices_data,pidv);
					if(gstp>0 && upv>0) {	
						for(var p=0; p<cat_cyl_types_data.length; p++){
							var cproduct = cat_cyl_types_data[p].cat_name+"-"+cat_cyl_types_data[p].cat_desc;
							var prodid = cat_cyl_types_data[p].id;
							
							for(var j=0;j<refill_prices_data.length;j++) {
								if(cproduct == pide.options[pide.selectedIndex].innerHTML) {
									if(prodid == refill_prices_data[j].prod_code) {
										if(!(refill_prices_data[j].montha == mon && pyear[refill_prices_data[j].yeara] == yr)){
											document.getElementById("dialog-1").innerHTML = "please define the price of product for sale invoice month in price master and continue";
											alertdialogue();
											//alert("please define the price of product for sale invoice month in price master and continue");
											return;
										}else {
											formobj.vp[i].value = gstp;
											formobj.up[i].value = upv;
											break;
										}
									}	
								}
							}
						}
					}else if(gstp<=0){
						document.getElementById("dialog-1").innerHTML = "Please ADD the PRODUCT in EQUIPMENT MASTER.";
						alertdialogue();
						//alert("Please ADD the PRODUCT in EQUIPMENT MASTER.");
						return;
					}else if(upv<=0){
						document.getElementById("dialog-1").innerHTML = "Please ADD PRODUCT'S price in REFILL PRICE MASTER.";
						alertdialogue();
						//alert("Please ADD PRODUCT'S price in REFILL PRICE MASTER.");
						return;
					}
				}else{
					document.getElementById("dialog-1").innerHTML = "Please Select PRODUCT And Then Click On FETCH UNIT PRICE AND GST";
					alertdialogue();
					//alert("Please Select PRODUCT And Then Click On FETCH UNIT PRICE AND GST");
					return;
				}	
			}
		}
		
	}else {
		document.getElementById("dialog-1").innerHTML = "Please ADD data and Click FETCH UNIT PRICE AND GST";
		alertdialogue();
		//alert("Please ADD data and Click FETCH UNIT PRICE AND GST");
		return;
	}
	restrictChangingAllValues(".freez",".epid");
	document.getElementById("calc_data").disabled=false;
}


function calculateValues(){
	var cvogstin;
	var cvoreg;
	var custcheck = 0;
	var formobj = document.getElementById('data_form');
	if(document.getElementById("bp") != null){
		var stypev = formobj.stype.value;
		var cust = document.getElementById('answerInputHidden').value.trim();
		
		if(cust.length > 0) {
			if((parseInt(cust)!=0) && (custarr.indexOf(parseInt(cust)) == -1)) {
				custcheck = 2; // if the cust entered by user is not present in cvodata then 2
			}else {
				custcheck = 1;// if the cust entered by user is present in cvodata then 1
				for(var d=0;d< cvo_data.length;d++){
					if(parseInt(cust) == cvo_data[d].id) {
						cvogstin = cvo_data[d].cvo_tin;
						cvoreg = cvo_data[d].is_gst_reg;
					}
				}
			}
		}else {
			document.getElementById("dialog-1").innerHTML = "please select CUSTOMER";
			alertdialogue();
			//alert("please select CUSTOMER");
			return;
		}
		if((custcheck == 1) && (cvoreg != 2)) {
			var dstcode = dealergstin.substr(0, 2);
			var cvstcode = cvogstin.substr(0, 2);
			var stypecheck = "";
			if(cvstcode == dstcode)
				stypecheck = "ls";
			else 
				stypecheck = "iss";
		}else if((custcheck == 2) || (cvoreg == 2)){
			stypecheck = stypev;
		}
				
		var elements = document.getElementsByClassName("qtyc");		
		if(elements.length==1) {
			var vpv = formobj.vp.value.trim();
			var upv = formobj.up.value.trim();
			var updv = formobj.upd.value.trim();
			var qty = formobj.qty.value.trim();
			var ems = validateCalcValues(upv,qty,updv,vpv,stypev,stypecheck);
			if(ems.length>0) {
				document.getElementById("dialog-1").innerHTML = ems;
				alertdialogue();
				//alert(ems);
				return;
			}
			 formobj.up.value = parseFloat(upv).toFixed(2);
			 formobj.upd.value = parseFloat(updv).toFixed(2);
			 formobj.bp.value = (((formobj.up.value)-(formobj.upd.value))*qty).toFixed(2);
			//formobj.bp.value = (upv-updv).toFixed(2);
			var gsta = ((((upv-updv)*qty)*vpv)/100).toFixed(2);
			if(stypev=='ls') {
				formobj.sgsta.value=(gsta/2).toFixed(2);
				formobj.cgsta.value=(gsta/2).toFixed(2);
				formobj.igsta.value=0;
			} else if (stypev=='iss') {
				formobj.igsta.value=gsta;
				formobj.sgsta.value=0;
				formobj.cgsta.value=0;				
			}
			formobj.amt.value = (+((formobj.bp.value)) + +(gsta)).toFixed(2);
			formobj.qtn_c_amt.value = Math.round(formobj.amt.value);
		} else if (elements.length>1){			
			var totalAmt = 0;
			for(var i=0; i<elements.length; i++){
				var vpv = formobj.vp[i].value.trim();
				var upv = formobj.up[i].value.trim();
				var updv = formobj.upd[i].value.trim();
				var qty = formobj.qty[i].value.trim();

				var ems = validateCalcValues(upv,qty,updv,vpv,stypev,stypecheck);
				if(ems.length>0){
					document.getElementById("dialog-1").innerHTML = ems;
					alertdialogue();
					//alert(ems);
					return;
				}
				formobj.up[i].value = parseFloat(upv).toFixed(2);
				formobj.upd[i].value = parseFloat(updv).toFixed(2);
		        formobj.bp[i].value = (((formobj.up[i].value)-(formobj.upd[i].value))*qty).toFixed(2);
				//formobj.bp[i].value = (upv-updv).toFixed(2);
				var gsta = ((((upv-updv)*qty)*vpv)/100).toFixed(2);
				if(stypev=='ls') {
					formobj.sgsta[i].value=(gsta/2).toFixed(2);
					formobj.cgsta[i].value=(gsta/2).toFixed(2);
					formobj.igsta[i].value=0;
				} else if (stypev=='iss'){
					formobj.igsta[i].value=gsta;
					formobj.sgsta[i].value=0;
					formobj.cgsta[i].value=0;
				}
				formobj.amt[i].value = (+((formobj.bp[i].value)) + +(gsta)).toFixed(2);
				totalAmt = +totalAmt + +(formobj.amt[i].value);
	
			}
			formobj.qtn_c_amt.value=Math.round(totalAmt);
		}
		
	}else {
		document.getElementById("dialog-1").innerHTML = "Please Add Quotation Details and Click Calculate";
		alertdialogue();
		//alert("Please Add Quotation Details and Click Calculate");
		return;
	}
	document.getElementById("save_data").disabled=false;
	$(':radio:not(:checked)').attr('disabled', true);
	restrictChangingAllValues(".freez",".tp");
	
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

function validateCalcValues(upv,qty,updv,vpv,stype,stypecheck) {
	
	var errmsg = "";
	var formobj = document.getElementById('data_form');

	if($("input[type=radio][name=stype]:checked").length <= 0) {
		errmsg = errmsg + "Please select SALE TYPE<br>";			
		return errmsg;
	}  
	if(($("input[type=radio][name=stype]:checked").length > 0)&&(stypecheck != stype)) {
		errmsg = errmsg + "The SALE TYPE you have selected might be wrong.Please check it again<br>";
		return errmsg;
	}
	
	if(!upv.length>0)
		errmsg = errmsg + "Please Enter UNIT PRICE. <br>";
	else if(validateDot(upv)) 
		errmsg = errmsg+"UNIT PRICE must contain atleast one number. <br>";
	else if(!(validateDecimalNumberMinMax(upv,0,100000)))
		errmsg = errmsg + "Please Enter valid UNIT PRICE And It Must Be In Between 0 And 1,00,000<br>";
	
	if(!qty.length>0)
		  errmsg = "Please Enter QUANTITY. <br>";
	else if(!(validateNumberMinMax(qty,0,10000)))
		errmsg = errmsg + "Please Enter Valid QUANTITY And It Must Be In Between 0 And 10,000 .<br>";
	
	if(!vpv.length>0)
		  errmsg = "Please Enter GST%. <br>";
	else if(!(checkNumber(vpv)))
		errmsg = errmsg + "Please Enter Valid GST% .<br>";
	
	if(!updv.length>0)
	  errmsg = "Please Enter DISCOUNT ON UNIT PRICE.Enter 0 if not available.<br>";
	else if(validateDot(updv)) 
		errmsg = errmsg+"DISCOUNT ON UNIT PRICE. must contain atleast one number. <br>";
	else if(!(validateDecimalNumberMinMax(updv,-1,10000)))
		errmsg = errmsg + "Please Enter Valid DISCOUNT ON UNIT PRICE.It Must Be >=0 and <10000. <br>";
	else if(!(parseFloat(updv)<parseFloat(upv)))
		errmsg = errmsg + "DISCOUNT ON UNIT PRICE Must Be Less Than UNIT PRICE.<br>";
	
	return errmsg;

}

function showInvoice(inv_id,si_date){
	popitup(inv_id,si_date)
}

function popitup(inv_id,si_date) {
	var w = window.open("PopupControlServlet?actionId=996&sitype=4&sid="+inv_id +"&si_date="+si_date,null,"height=400,width=1024,status=yes,toolbar=no,menubar=no,location=no");
	w.print();
	return false;
}