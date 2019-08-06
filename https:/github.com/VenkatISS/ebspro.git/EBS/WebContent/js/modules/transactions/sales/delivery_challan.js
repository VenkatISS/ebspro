function showDCFormDialog() {
	document.getElementById('myModal').style.display = "block";
	pushMenu();
}


function closeDCFormDialog() {
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


for(var i=0;i<prod_types.length;i++) {
	if(prod_types[i].id < 10) {
		var obj = {};
		obj["cat_code"] = prod_types[i].cat_code;
		obj["cat_desc"] = prod_types[i].cat_desc;
		obj["cat_name"] = prod_types[i].cat_name;
		obj["cat_type"] = prod_types[i].cat_type;
		obj["deleted"] = prod_types[i].deleted;
		obj["id"] = (prod_types[i].id) + "e" ;
		prod_types.push(obj);
	}
}


//Process page data
 var rowpdhtml = "";
 if(page_data.length>0) {
 	for(var z=page_data.length-1; z>=0; z--){
 		var ed = new Date(page_data[z].dc_date);
		var custName = getCustomerName(cvo_data,page_data[z].customer_id);
		var staffName = getStaffName(staff_data,page_data[z].staff_id);
		var fleetName = getFleetDetails(fleet_data,page_data[z].fleet_id);
		var DeliverMode = page_data[z].delivery_mode;
		if(!staffName) {
			staffName = "NA";		
		}		
		if(!fleetName) {
			fleetName = "NA";		
		}
		if(!DeliverMode) {
			DeliverMode = "NA";		
		}
 		
 		for(var i=0; i<page_data[z].dcItemsList.length; i++){
 			var spd = "";
			var prodId = page_data[z].dcItemsList[i].prod_code;
/*			if(prodId < 100) {*/
			if((prodId < 100) || (prodId.includes("e"))) {
				spd = fetchProductDetails(prod_types, page_data[z].dcItemsList[i].prod_code);
			}else {
				spd = fetchARBProductDetails(cat_arb_types_data, page_data[z].dcItemsList[i].prod_code);
			}
 			//if(i==0) {
			rowpdhtml = rowpdhtml + "<tr><td><a href='javascript:showInvoice("+page_data[z].id+","+page_data[z].dc_date+")'>"+page_data[z].sr_no+"</a></td>"+
	 		"<td>"+ed.getDate()+"-"+months[ed.getMonth()]+"-"+ed.getFullYear()+"</td>"+
	 		"<td>"+page_data[z].inv_no+"</td>"+
	 		"<td>"+custName+"</td>"+
	 		"<td>"+staffName+"</td>"+
	 		"<td>"+fleetName+"</td>"+
	 		"<td>"+page_data[z].dc_amount+"</td>"+
	 	    "<td>"+page_data[z].delivery_instructions+"</td>"+
	 		"<td>"+DeliverMode+"</td>"+
	 		"<td>"+spd+"</td>"+
 			"<td>"+page_data[z].dcItemsList[i].gstp+"</td>"+
 			"<td>"+page_data[z].dcItemsList[i].unit_rate+"</td>"+
 			"<td>"+page_data[z].dcItemsList[i].basic_amount+"</td>"+
 			"<td>"+page_data[z].dcItemsList[i].quantity+"</td>"+
 			"<td>"+page_data[z].dcItemsList[i].igst_amount+"</td>"+
 			"<td>"+page_data[z].dcItemsList[i].cgst_amount+"</td>"+
 			"<td>"+page_data[z].dcItemsList[i].sgst_amount+"</td>"+
 			"<td>"+page_data[z].dcItemsList[i].prod_amount+"</td>"+
 			"<td align='center'><img src='images/delete.png' onclick='deleteItem("+page_data[z].id+","+page_data[z].dc_date+")'></td>"+"</tr>";
 			/*} else {
 				rowpdhtml = rowpdhtml + "<tr><td><input type='text' class='form-control input_field' title='"+spd+"' value='" + spd +  "' readonly></td>"+
 				"<td><input type='text' class='form-control input_field' title='"+page_data[z].dcItemsList[i].gstp+"' value='" + page_data[z].dcItemsList[i].gstp +  "' readonly></td>"+
 				"<td><input type='text' class='form-control input_field' title='"+page_data[z].dcItemsList[i].unit_rate+"' value='" + page_data[z].dcItemsList[i].unit_rate +  "' readonly></td>"+
 				"<td><input type='text' class='form-control input_field' title='"+page_data[z].dcItemsList[i].basic_amount+"' value='" + page_data[z].dcItemsList[i].basic_amount +  "' readonly></td>"+
 				"<td><input type='text' class='form-control input_field' title='"+page_data[z].dcItemsList[i].quantity+"' value='" + page_data[z].dcItemsList[i].quantity +  "' readonly></td>"+
 				"<td><input type='text' class='form-control input_field' title='"+page_data[z].dcItemsList[i].cgst_amount+"' value='" + page_data[z].dcItemsList[i].cgst_amount +  "' readonly></td>"+
 				"<td><input type='text' class='form-control input_field' title='"+page_data[z].dcItemsList[i].sgst_amount+"' value='" + page_data[z].dcItemsList[i].sgst_amount +  "' readonly></td>"+
 				"<td><input type='text' class='form-control input_field' title='"+page_data[z].dcItemsList[i].prod_amount+"' value='" + page_data[z].dcItemsList[i].prod_amount +  "' readonly></td>"+
 				"<td></td></tr>";
 			}*/
 		}
 	}
 }
 document.getElementById('m_data_table_body').innerHTML = rowpdhtml;


 function fetchProductDetails(types, pc) {
	 for(var i=0; i< types.length; i++){
		 if(types[i].id == pc) {
			 if(types[i].cat_type=="1" || types[i].cat_type=="2" || types[i].cat_type=="3") {
				 if(pc.includes("e"))
					 return types[i].cat_name+"-"+types[i].cat_desc+" (Empties)";
				 else
					 return types[i].cat_name+"-"+types[i].cat_desc;
			 }else {
				 return types[i].cat_desc;
			 }
		 }
	 }
 }
 

 function addRow(){
	var trcount = document.getElementById('data_table_body').getElementsByTagName('tr').length;
	if(trcount >0){
		var trv=document.getElementById('data_table_body').getElementsByTagName('tr')[trcount-1];
		var saddv=trv.getElementsByClassName('sadd');
		var eaddv=trv.getElementsByClassName('eadd');
		var res=checkRowData(saddv,eaddv);
		if(res == false) {
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
		
		a.innerHTML = "<td><select name='epid' id='epid' class='form-control input_field select_dropdown sadd tp epid'>"+ctdatahtml+"</select></td>";
		b.innerHTML = "<td><input type=text class='form-control input_field eadd' name='vp' id='vp' size='8' readonly='readonly' style='background-color:#FAFAC2;' placeholder='Gst%'></td>";
		c.innerHTML = "<td><input type=text class='form-control input_field eadd' name='up' id='up' size='8' maxlength='7' style='background-color:#FAFAC2;' placeholder='Unit Price'><input type=hidden  name='cupv' id='cupv'></td>";
		d.innerHTML = "<td>" +
						"<input type='text' class='form-control input_field qtyc freez eadd' name='qty' id='qty' size='8' maxlength='4' placeholder='Quantity'>" +
						"<input type='hidden' name='cqty' id='cqty' value=''>" +
						"</td>";
		e.innerHTML = "<td><input type=text class='form-control input_field eadd' name='bp' id='bp' size='8' readonly='readonly' style='background-color:#F3F3F3;' placeholder='TAXABLE AMOUNT'></td>";
		f.innerHTML = "<td><input type=text class='form-control input_field eadd' name='igsta' id='igsta' size='8' value='0.00' readonly='readonly' style='background-color:#F3F3F3;' placeholder='IGST Amount'></td>";		
		g.innerHTML = "<td><input type=text class='form-control input_field eadd' name='sgsta' id='sgsta' size='8'  value='0.00'readonly='readonly' style='background-color:#F3F3F3;' placeholder='SGST Amount'></td>";
		h.innerHTML = "<td><input type=text class='form-control input_field eadd' name='cgsta' id='cgsta' size='8' value='0.00' readonly='readonly' style='background-color:#F3F3F3;' placeholder='CGST Amount'></td>";
		i.innerHTML = "<td><input type=text class='form-control input_field eadd' name='amt' id='amt' size='8' readonly='readonly' style='background-color:#F3F3F3;' placeholder='Total Amount'></td>";
		j.innerHTML = "<td><img src='images/delete.png' onclick='doRowDeleteInTranxs(this,amt,dc_amt,data_table_body)'></td>";
		
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
	
	if(document.getElementById("epid") != null) {
		var elements = document.getElementsByClassName("qtyc");
		var dcdate = formobj.dc_date.value.trim();
		var cust = formobj.cust_id.selectedIndex;
		var invno = formobj.inv_no.value.trim();
		var dinstr = formobj.dinstr.value.trim();
		
		if(elements.length==1) {
			var item = formobj.epid;
			var up = formobj.up.value.trim();
			var bp = formobj.bp.value.trim();
			var qty = formobj.qty.value.trim();
			var vp = formobj.vp.value.trim();
			var sgsta = formobj.sgsta.value;
			var cgsta = formobj.cgsta.value;
			var samt = formobj.amt.value;
			var refQty = formobj.cqty.value;
			
			if(checkDateFormate(dcdate)){
				var ved = dateConvert(dcdate);
				formobj.dc_date.value = ved;
				dcdate=ved;
			}
			ems = validateEntries(dcdate,cust,invno,dinstr,item,up,bp,qty,vp,sgsta,cgsta,samt,refQty);
		}else if (elements.length>1){
			for(var i=0; i<elements.length; i++){
				var item = formobj.epid[i];
				var up = formobj.up[i].value.trim();
				var bp = formobj.bp[i].value.trim();
				var qty = formobj.qty[i].value.trim();
				var vp = formobj.vp[i].value.trim();
				var sgsta = formobj.sgsta[i].value;
				var cgsta = formobj.cgsta[i].value;
				var samt = formobj.amt[i].value;
				var refQty = formobj.cqty[i].value;
				
				if(checkDateFormate(dcdate)){
					var ved = dateConvert(dcdate);
					formobj.dc_date[i].value = ved;
					dcdate=ved;
				}
				ems = validateEntries(dcdate,cust,invno,dinstr,item,up,bp,qty,vp,sgsta,cgsta,samt,refQty);
				if(ems.length>0)
					break;
			}			
		}
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
	
	var objId = obj.id;
	document.getElementById(objId).disabled = true;
	formobj.submit();
}

function validateProduct() {
	var flag=true;
	var prodar= new Array();
	var ele = document.getElementsByClassName("tp");
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

function validateEntries(dcdate,cust,invno,dinstr,item,up,bp,qty,vp,sgsta,cgsta,samt,qntty){
	
	var formobj = document.getElementById('data_form');
	var custval = formobj.cust_id.value;
	var itemin = item.value;
	var dcdateval=dcdate;
	var errmsg = "";
	var irdate = "";
	
	var vd = isValidDate(dcdate);
	var vfd = ValidateFutureDate(dcdate);	
	
	var dt1 = parseInt(dcdateval.substring(8, 10), 10);
	var mon1 = parseInt(dcdateval.substring(5, 7), 10);
	var yr1 = parseInt(dcdateval.substring(0, 4), 10);
	var inputdate = new Date(yr1, mon1-1, dt1);
	irdate = new Date(irefdate.getFullYear(),irefdate.getMonth(),irefdate.getDate(),0,0,0);
	
/*	var comSalInvNO="", irefdate ="",irefdateval ="",custname="",qntty="";
	if(com_salesInv.length>0) {
		for(var c=0; c<com_salesInv.length; c++){
			for(var l=0; l<com_salesInv[c].detailsList.length;l++){
				if(com_salesInv[c].sr_no == invno) {
					comSalInvNO = com_salesInv[c].sr_no;
					irefdateval = com_salesInv[c].si_date;
					irefdate = new Date(com_salesInv[c].si_date);
					irdate = new Date(irefdate.getFullYear(),irefdate.getMonth(),irefdate.getDate(),0,0,0);
					custname =com_salesInv[c].customer_id;
					if(com_salesInv[c].detailsList[l].prod_code == parseInt(itemin)){
						qntty =com_salesInv[c].detailsList[l].quantity;
					}
				}
			}
		}
	}*/

	if(!(dcdate.length>0))
		errmsg = errmsg+"Please Enter DELIVERY CHALLAN DATE<br>";
	else if (vd != "false")
		errmsg = errmsg +vd+"<br>";
	else if(validateDayEndAdd(dcdate,dedate)){
        errmsg = "DELIVERY CHALLAN DATE should be after DayEndDate<br>";
        return errmsg;
	}else if (vfd != "false")
		errmsg = errmsg +vfd+"<br>";
	else if(inputdate < irdate)
		errmsg = errmsg+"Please Enter valid DC DATE. It should be after its corresponding sale/purchase date .<br>";	
/*	else {
		var lastTrxnDateCheck = checkforLastTrxnDate(dedate,a_created_date,dcdate,trdate);
		if(lastTrxnDateCheck != "false") {
			return lastTrxnDateCheck;
		}
	}*/

	if(!(cust>0))
		errmsg = errmsg+"Please Select CUSTOMER NAME.<br>";
	else if(custname == 0)
		errmsg = errmsg+"You Cannot have DC For Cash Sale .Once please Verify the  Given Invoice .<br>";
	else if(custname != custval)
		errmsg = errmsg+"Please Select Valid CUSTOMER NAME For The Given Invoice .<br>";
	
	if(!(invno.length>0))
		errmsg = errmsg+"Please  Enter INVOICE NUMBER.<br>";
	//else if(!((domSalInvNO!=invno) || (comSalInvNO!=invno) || (arbSalInvNO!=invno)))
//	else if(comSalInvNO!=invno)
//		errmsg = errmsg+"Please Enter valid INVOICE NUMBER For COM SALE .<br>";
//	else 
//		formobj.dcrefdate.value = irefdateval;
	
	if(!(dinstr.length>0))
		errmsg = errmsg+"Please Enter DELIVERY INSTRUCTIONS.<br>";
	if($("input[type=radio][name=stype]:checked").length <= 0) {
		errmsg = errmsg + "Please select SALE TYPE<br>";			
	}
	if(!(item.selectedIndex >0))
		errmsg = errmsg+"Please Select PRODUCT<br>";
	else if (!validateProduct())
		errmsg = errmsg + "Please Select Another PRODUCT Which Was not Selected.<br>";

	if(!up.length>0)
		  errmsg = errmsg + "Please Enter UNIT PRICE. <br>";
	else if(validateDot(up)) 
		errmsg = errmsg+"UNIT PRICE must contain atleast one number. <br>";
	else if((!(validateDecimalNumberMinMax(up,0,100000))) && (!(itemin.includes("e"))))
		errmsg = errmsg + "Please Enter valid UNIT PRICE And It Must Be In Between 0 And 1,00,000<br>";
	else
		formobj.up.value=round(parseFloat(up),2);

	if(!qty.length>0)
		  errmsg = errmsg + "Please Enter QUANTITY. <br>";
	else if(!(validateNumberMinMax(qty,0,10000)))
		errmsg = errmsg + "Please Enter Valid QUANTITY And It Must Be In Between 0 And 10,000 .<br>";
	else if((parseInt(qty) > qntty) && (!(itemin.includes("e"))))
		errmsg = errmsg + "Please Enter Valid QUANTITY For The Given Invoice Number.<br>";
	
	if(!vp.length>0)
		  errmsg = errmsg + "Please Enter GST%. <br>";
	else if(!(checkNumber(vp)))
		errmsg = errmsg + "Please Enter Valid GST% .<br>";
	
	
	if((!(validateDecimalNumberMinMax(bp,0,10000000))) && (!(itemin.includes("e"))))
		errmsg = errmsg + "Invalid BASIC AMOUNT.<br>";

	if((!(validateDecimalNumberMinMax(samt,0,10000000))) && (!(itemin.includes("e"))))
		errmsg = errmsg + "Invalid TOTAL AMOUNT.<br>";

	if(!(validateDecimalNumberMinMax(sgsta,-1,10000000)))
		errmsg = errmsg + "Invalid GST..<br>";

	if(!(validateDecimalNumberMinMax(cgsta,-1,10000000)))
		errmsg = errmsg + "Invalid GST.. <br>";

	return errmsg;
}

/*function deleteItem(iid,invdate) {
	var flag=validateDayEndForDelete(invdate,dedate);
	if(flag) {
		if (confirm("Are you sure you want to delete?") == true) {
			var formobj = document.getElementById('m_data_form');
			formobj.actionId.value = "5153";
			formobj.dataId.value = iid;
			formobj.submit();
		}	
	}else 
		document.getElementById("dialog-1").innerHTML = "This record can't be deleted as you have submitted and verified your DAYEND calculations succuessfully...";
		alertdialogue();
		//alert("This record can't be deleted as you have submitted and verified your DAYEND calculations succuessfully... ");
	
}*/

function deleteItem(iid,invdate){
	var flag=validateDayEndForDelete(invdate,dedate);
	if(flag){
	 $("#myDialogText").text("Are You Sure You Want To Delete?");
	 var formobj = document.getElementById('m_data_form');
	 confirmDialogue(formobj,5153,iid);
	}else {
		document.getElementById("dialog-1").innerHTML = "This record can't be deleted as you have submitted and verified your DAYEND calculations succuessfully";
		alertdialogue();
	}
}

var cgstv = "";
var igstv = "";
var custname = "";
var irefdate = "";
function fetchPriceAndVAT(){
	var formobj = document.getElementById('data_form');
	if(document.getElementById("bp") != null){
		var dsdate = formobj.dc_date.value;
		var sdate = new Date(dsdate);
		var mon = sdate.getMonth();
		var yr = sdate.getFullYear();
		var pyear = ["2018","2019","2020"];
		var comSalInvNO="",irefdateval ="",qntty="",invdel = "";
		
		var elements = document.getElementsByClassName("qtyc");
		if(elements.length==1) {
			var pide = formobj.epid;
			var item = formobj.epid;
			var invno = formobj.inv_no.value;
			if(pide.selectedIndex > 0) {
				var pidv = pide.options[pide.selectedIndex].value;
				var epidv = pide.options[pide.selectedIndex].value;
				if(pidv.includes("e"))
					pidv = pidv.replace('e','');
				
				var gstp=0;var upv=0;
				var prdct;
				var flag = "NO",prodflag = "NO";
				
				var qnttya = new Array();
				var prdcta = new Array();
				if(!(invno.length>0)) {
					document.getElementById("dialog-1").innerHTML = "Please  Enter INVOICE NUMBER";
					alertdialogue();
					return;
				}else {

					if(cyld_purInv.length > 0) {
						for (var cp = 0 ; cp < cyld_purInv.length ; cp++) {
							if((cyld_purInv[cp].inv_ref_no == invno) && (cyld_purInv[cp].deleted == 0)) {
								flag = "CP_YES";
								prdcta.push(cyld_purInv[cp].prod_code);
								cgstv = cyld_purInv[cp].cgst_amount;
								igstv = cyld_purInv[cp].igst_amount;
								irefdate = new Date(cyld_purInv[cp].inv_date);
								formobj.dcrefdate.value = cyld_purInv[cp].inv_date;
								custname = cyld_purInv[cp].omc_id;
								if(cyld_purInv[cp].prod_code == pidv) {
									prodflag = "CP_YES";
									gstp = cyld_purInv[cp].gstp;
									upv = cyld_purInv[cp].unit_price;
									formobj.cqty.value = cyld_purInv[cp].quantity;
									break;
								}
							}
						}
					}
					if(flag == "CP_YES" && prodflag == "NO"){
						document.getElementById("dialog-1").innerHTML = "The PRODUCT you have selected is not under the provided INVOICE NUMBER ";
						alertdialogue();
						return;
					}
					
					if((dom_salesInv.length > 0) && (flag != "CP_YES")) {
						for (var ds=0 ; ds < dom_salesInv.length ; ds++) {
							if((dom_salesInv[ds].sr_no == invno) && (dom_salesInv[ds].deleted == 0)) {
								flag = "DS_YES";
								irefdate = new Date(dom_salesInv[ds].si_date);
								formobj.dcrefdate.value = dom_salesInv[ds].si_date;
								custname = dom_salesInv[ds].customer_id;
								for(var d=0; d < dom_salesInv[ds].detailsList.length ; d++) {
									if(pidv == dom_salesInv[ds].detailsList[d].prod_code) {
										prodflag = "DS_YES";
										prdcta.push(dom_salesInv[ds].detailsList[d].prod_code);
										cgstv = dom_salesInv[ds].detailsList[d].cgst_amount;
										igstv = dom_salesInv[ds].detailsList[d].igst_amount;									
										gstp = dom_salesInv[ds].detailsList[d].gstp;
										upv = dom_salesInv[ds].detailsList[d].unit_rate;
										formobj.cqty.value = dom_salesInv[ds].detailsList[d].quantity;
									}
								}
								break;
							}
						}
					}
						
					if(flag == "DS_YES" && prodflag == "NO"){
						document.getElementById("dialog-1").innerHTML = "The PRODUCT you have selected is not under the provided INVOICE NUMBER ";
						alertdialogue();
						return;
					}
						
					if((com_salesInv.length>0) && (flag == "NO")) {
						for(var c=0; c<com_salesInv.length; c++){
							if(com_salesInv[c].sr_no == invno) {
								flag = "CS_YES";
								for(var l=0; l<com_salesInv[c].detailsList.length;l++){
									prdct =com_salesInv[c].detailsList[l].prod_code;
									if(prdct == parseInt(pide.value)) {
										prodflag = "CS_YES";
										comSalInvNO = com_salesInv[c].sr_no;
										invdel = com_salesInv[c].deleted;
										irefdateval = com_salesInv[c].si_date;
										irefdate = new Date(com_salesInv[c].si_date);
										custname =com_salesInv[c].customer_id;
										cgstv = com_salesInv[c].detailsList[l].cgst_amount;
										igstv = com_salesInv[c].detailsList[l].igst_amount;
										//qnttya = com_salesInv[c].detailsList[l].quantity;
										prdcta.push(com_salesInv[c].detailsList[l].prod_code);
										formobj.dcrefdate.value = irefdateval;
										gstp = com_salesInv[c].detailsList[l].gstp;
										upv = com_salesInv[c].detailsList[l].unit_rate;
										formobj.cqty.value = com_salesInv[c].detailsList[l].quantity;
										break;
									}
								}
							}
						}
					}
					if(flag == "CS_YES" && prodflag != "CS_YES"){
						document.getElementById("dialog-1").innerHTML = "The PRODUCT you have selected is not under the provided INVOICE NUMBER ";
						alertdialogue();
						return;
					}
					
						
					if(flag == "NO"){
						document.getElementById("dialog-1").innerHTML = "Please Enter valid INVOICE NUMBER. There is no data with that invoice number.";
						alertdialogue();
						return;
					}else if((prdcta.indexOf(item.value) != -1)) {
						document.getElementById("dialog-1").innerHTML = "The PRODUCT you have selected is not under the provided INVOICE NUMBER ";
						alertdialogue();
						return;
					}else {
						if(epidv.includes("e")){
							formobj.vp.value =  0;
							formobj.up.value = 0;
						}else {
							formobj.vp.value =  gstp;
							formobj.up.value = upv;
						}
					}
					
				}
					
			}else{
				document.getElementById("dialog-1").innerHTML = "Please Select PRODUCT And Then Click On FETCH UNIT PRICE AND GST";
				alertdialogue();
				return;
			}
	
		}else if (elements.length>1){
			for(var i=0; i<elements.length; i++){
				var pide = formobj.epid[i];
				var item = pide;
				var invno = formobj.inv_no.value;

				var flag = "NO",prodflag = "NO";
				
				if(pide.selectedIndex > 0) {
					var pidv = pide.options[pide.selectedIndex].value;
					var epidv = pide.options[pide.selectedIndex].value;
					if(pidv.includes("e"))
						pidv = pidv.replace('e','');
					
					var gstp = 0; var upv = 0;
					var qnttya =new Array();
					var prdcta =new Array();

					if(!(invno.length>0)){
 						document.getElementById("dialog-1").innerHTML = "Please  Enter INVOICE NUMBER";
 						alertdialogue();
 						return;
					}else {
						if(cyld_purInv.length>0) {
							for (var cp = 0 ; cp < cyld_purInv.length ; cp++) {
								if((cyld_purInv[cp].inv_ref_no == invno) && (cyld_purInv[cp].deleted == 0)) {
									flag = "CP_YES";
									prdcta.push(cyld_purInv[cp].prod_code);
									cgstv = cyld_purInv[cp].cgst_amount;
									igstv = cyld_purInv[cp].igst_amount;
									irefdate = new Date(cyld_purInv[cp].inv_date);
									formobj.dcrefdate.value = cyld_purInv[cp].inv_date;
									custname = cyld_purInv[cp].omc_id;
									if(cyld_purInv[cp].prod_code == pidv) {
										prodflag = "CP_YES";
										gstp = cyld_purInv[cp].gstp;
										upv = cyld_purInv[cp].unit_price;
										formobj.cqty[i].value = cyld_purInv[cp].quantity;
										break;
									}
								}
							}
						}
						if(flag == "CP_YES" && prodflag == "NO"){
							document.getElementById("dialog-1").innerHTML = "The PRODUCT you have selected is not under the provided INVOICE NUMBER ";
							alertdialogue();
							return;
						}
								
						if((dom_salesInv.length > 0) && (flag != "CP_YES")) {
							for (var ds=0 ; ds<dom_salesInv.length ; ds++) {
								if((dom_salesInv[ds].sr_no == invno) && (dom_salesInv[ds].deleted == 0)) {
									flag = "DS_YES";
									irefdate = new Date(dom_salesInv[ds].si_date);
									formobj.dcrefdate.value = dom_salesInv[ds].si_date;
									custname = dom_salesInv[ds].customer_id;
									for(var d=0; d < dom_salesInv[ds].detailsList.length ; d++) {
										if(pidv == dom_salesInv[ds].detailsList[d].prod_code) {
											prodflag = "DS_YES";
											prdcta.push(dom_salesInv[ds].detailsList[d].prod_code);
											cgstv = dom_salesInv[ds].detailsList[d].cgst_amount;
											igstv = dom_salesInv[ds].detailsList[d].igst_amount;									
											gstp = dom_salesInv[ds].detailsList[d].gstp;
											upv = dom_salesInv[ds].detailsList[d].unit_rate;
											formobj.cqty[i].value = dom_salesInv[ds].detailsList[d].quantity;
										}
									}
									break;
								}
							}
						}
								
						if(flag == "DS_YES" && prodflag == "NO"){
							document.getElementById("dialog-1").innerHTML = "The PRODUCT you have selected is not under the provided INVOICE NUMBER ";
							alertdialogue();
							return;
						}
								
						if((com_salesInv.length>0) && (flag == "NO")) {
							for(var c=0; c<com_salesInv.length; c++){
								if(com_salesInv[c].sr_no == invno) {
									flag = "CS_YES";
									for(var l=0; l<com_salesInv[c].detailsList.length;l++){
										prdct =com_salesInv[c].detailsList[l].prod_code;
										if(prdct == parseInt(pide.value)) {
											prodflag = "CS_YES";
											comSalInvNO = com_salesInv[c].sr_no;
											invdel = com_salesInv[c].deleted;
											irefdateval = com_salesInv[c].si_date;
											irefdate =new  Date(com_salesInv[c].si_date);
											custname =com_salesInv[c].customer_id;
											cgstv = com_salesInv[c].detailsList[l].cgst_amount;
											igstv = com_salesInv[c].detailsList[l].igst_amount;
											//qnttya = com_salesInv[c].detailsList[l].quantity;
											prdcta.push(com_salesInv[c].detailsList[l].prod_code);
											formobj.dcrefdate.value = irefdateval;
											gstp = com_salesInv[c].detailsList[l].gstp;
											upv = com_salesInv[c].detailsList[l].unit_rate;
											formobj.cqty[i].value = com_salesInv[c].detailsList[l].quantity;
											break;
										}
									}
								}
							}
						}
						if(flag == "CS_YES" && prodflag != "CS_YES"){
							document.getElementById("dialog-1").innerHTML = "The PRODUCT you have selected is not under the provided INVOICE NUMBER ";
							alertdialogue();
							return;
						}
						
									
						if(flag == "NO"){
							document.getElementById("dialog-1").innerHTML = "Please Enter valid INVOICE NUMBER. There is no data with that invoice number.";
							alertdialogue();
							return;
						}else if((prdcta.indexOf(item.value) != -1)) {
							document.getElementById("dialog-1").innerHTML = "The PRODUCT you have selected is not under the provided INVOICE NUMBER ";
							alertdialogue();
							return;
						}else {								
							if(epidv.includes("e")){
								formobj.vp[i].value =  0;
								formobj.up[i].value = 0;
							}else {
								formobj.vp[i].value = gstp;
								formobj.up[i].value = upv;
							}
						}
					}
				}else{
					document.getElementById("dialog-1").innerHTML = "Please Select PRODUCT And Then Click On FETCH UNIT PRICE AND GST";
					alertdialogue();
					return;
				}
			}
		}
		
	}else {
		document.getElementById("dialog-1").innerHTML = "Please ADD Data And Then Click On FETCH UNIT PRICE AND GST";
		alertdialogue();
		return;
	}
	restrictChangingAllValues(".freez",".tp");
	document.getElementById("calc_data").disabled=false;

}

function calculateValues(){
	var cvogstin;
	var cvoreg;
	var formobj = document.getElementById('data_form');
	var stypev = formobj.stype.value;
	if(formobj.cust_id.selectedIndex >0) {
		for(var d=0;d< cvo_data.length;d++){
			if(parseInt(formobj.cust_id.value) == cvo_data[d].id) {
				cvogstin = cvo_data[d].cvo_tin;
				cvoreg = cvo_data[d].is_gst_reg;
			}
		}
	}else {
		document.getElementById("dialog-1").innerHTML = "please select CUSTOMER  NAME";
		alertdialogue();
		//alert("please select CUSTOMER  NAME");
		return;
	}
	if(cvoreg==1) {
		var dstcode = dealergstin.substr(0, 2);
		var cvstcode = cvogstin.substr(0, 2);
		var stypecheck = "";
		if(cvstcode == dstcode)
			stypecheck = "ls"; 
		else 
			stypecheck = "iss"
	}else if(cvoreg==2) {
		if(cgstv!=0)
			stypecheck = "ls";
		else
			stypecheck = "iss";
		 //stypecheck = stypev;
	}
	
	if(document.getElementById("bp") != null){		
		var elements = document.getElementsByClassName("qtyc");
		var stypev = formobj.stype.value;
		if(elements.length==1) {
			var pidv = formobj.epid.value;
			var vpv = formobj.vp.value.trim();
			var upv = formobj.up.value.trim();
			var qty = formobj.qty.value.trim();
			var prdcta;
			var qntty,comupv;
/*			for(var c=0; c<com_salesInv.length; c++) {
				for(var l=0; l<com_salesInv[c].detailsList.length;l++){
					if(com_salesInv[c].sr_no == formobj.inv_no.value) {
				    	prdcta=com_salesInv[c].detailsList[l].prod_code;
				    	if(prdcta == formobj.epid.value){
	                		qntty = com_salesInv[c].detailsList[l].quantity;
//	                		comupv = com_salesInv[c].detailsList[l].unit_rate;
			                break;						                		
				    	}
					}
				}
			}*/
			qntty = formobj.cqty.value;
			var ems = validateCalcValues(pidv,upv,qty,vpv,stypev,stypecheck,qntty);
			if(ems.length>0) {
				document.getElementById("dialog-1").innerHTML = ems;
				alertdialogue();
				//alert(ems);
				return;
			}
			formobj.up.value = parseFloat(upv).toFixed(2);
			formobj.bp.value = round(((formobj.up.value)*qty),2);
			//formobj.bp.value = (upv);
			var gsta = ((((upv)*qty)*vpv)/100).toFixed(2);
			if(stypev=='ls') {
				formobj.sgsta.value=(gsta/2).toFixed(2);
				formobj.cgsta.value=(gsta/2).toFixed(2);
				formobj.igsta.value=0;
				formobj.amt.value = (+((formobj.bp.value)) + +(formobj.sgsta.value) + +(formobj.cgsta.value)).toFixed(2);
			} else if (stypev=='iss') {
				formobj.igsta.value=gsta;
				formobj.sgsta.value=0;
				formobj.cgsta.value=0;
				formobj.amt.value = (+((formobj.bp.value)) + +(formobj.igsta.value)).toFixed(2);
			}
			formobj.dc_amt.value = Math.round(formobj.amt.value);
				
		} else if (elements.length>1){
			var totalAmt = 0;
			for(var i=0; i<elements.length; i++){
				var pidv = formobj.epid[i].value;
				var vpv = formobj.vp[i].value.trim();
				var upv = formobj.up[i].value.trim();
				var qty = formobj.qty[i].value.trim();
				var prdct;
				
/*				for(var c=0; c<com_salesInv.length; c++){ 
					if(com_salesInv[c].sr_no == formobj.inv_no.value) {
						for(var l=0; l<com_salesInv[c].detailsList.length;l++){
							prdct =com_salesInv[c].detailsList[l].prod_code;
							if(prdct == parseInt(formobj.epid[i].value)){
								formobj.cqty[i].value = com_salesInv[c].detailsList[l].quantity;
//								formobj.cupv[i].value = com_salesInv[c].detailsList[l].unit_rate;
							}
						}
					}
				}*/
				var cqty = formobj.cqty[i].value;
				var ems = validateCalcValues(pidv,upv,qty,vpv,stypev,stypecheck,cqty);
				if(ems.length>0){
					document.getElementById("dialog-1").innerHTML = ems;
					alertdialogue();
					//alert(ems);
					return;
				}
		        formobj.bp[i].value = round(((formobj.up[i].value)*qty),2);
				//formobj.bp[i].value = (upv);
				var gsta = ((((upv)*qty)*vpv)/100).toFixed(2);
				if(stypev=='ls') {
					formobj.sgsta[i].value=(gsta/2).toFixed(2);
					formobj.cgsta[i].value=(gsta/2).toFixed(2);
					formobj.igsta[i].value=0;

					formobj.amt[i].value = (+((formobj.bp[i].value)) + +(formobj.sgsta[i].value) + +(formobj.cgsta[i].value)).toFixed(2);
				} else if (stypev=='iss'){
					formobj.igsta[i].value=gsta;
					formobj.sgsta[i].value=0;
					formobj.cgsta[i].value=0;
					formobj.amt[i].value = (+((formobj.bp[i].value)) + +(formobj.igsta[i].value)).toFixed(2);
				}
				totalAmt = +totalAmt + +(formobj.amt[i].value);
			}
			formobj.dc_amt.value=Math.round(totalAmt);
		}
		
	}else {
		document.getElementById("dialog-1").innerHTML = "Please ADD DELIVERY CHALLAN Details and Click CALCULATE";
		alertdialogue();
		return;
	}
	document.getElementById("save_data").disabled=false;
	$(':radio:not(:checked)').attr('disabled', true);
	restrictChangingAllValues(".freez",".tp");
	
	//var efrz = document.getElementsByClassName("freez");
	//makeEleReadOnly(efrz,efrz.length);

}

function validateCalcValues(pidv,upv,qty,vpv,stypev,stypecheck,cqty) {
	
	var errmsg = "";
	var formobj = document.getElementById('data_form');
	if($("input[type=radio][name=stype]:checked").length <= 0) {
		errmsg = errmsg + "Please select LOCAL/INTER STATE SUPPLY<br>";			
	}
	if(($("input[type=radio][name=stype]:checked").length > 0)&&(stypecheck != stypev))
		errmsg = errmsg + "The LOCAL/INTER STATE TYPE you have selected might be wrong.Please check it again<br>";

	
	if(!upv.length>0)
		  errmsg = errmsg + "Please Enter UNIT PRICE. <br>";
	else if(validateDot(upv)) 
		errmsg = errmsg+"UNIT PRICE must contain atleast one number. <br>";
	else if(!(validateDecimalNumberMinMax(upv,0,100000))) {
		if(!(pidv.includes("e")))
			errmsg = errmsg + "Please Enter valid UNIT PRICE And It Must Be In Between 0 And 1,00,000<br>";
	}
	
/*	else if(!(parseInt(upv) == parseInt(cupv)))
		errmsg = errmsg + "Please Enter valid UNIT PRICE as defined for the given invoice.<br>";*/

	
	if(!qty.length>0)
		  errmsg = errmsg + "Please Enter QUANTITY. <br>";
	else if(!(validateNumberMinMax(qty,0,10000)))
		errmsg = errmsg + "Please Enter Valid QUANTITY And It Must Be In Between 0 And 10,000 .<br>";
	else if((parseInt(qty) > parseInt(cqty)) && (!pidv.includes("e")) )
		errmsg = errmsg + "QUANTITY Must Not Exceed Sold Quantity.<br>";

	if(!vpv.length>0)
		  errmsg = errmsg + "Please Enter GST%. <br>";
	else if(!(checkNumber(vpv)))
		errmsg = errmsg + "Please Enter Valid GST% .<br>";
	
	return errmsg;
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
	var w = window.open("PopupControlServlet?actionId=995&sitype=5&sid="+inv_id +"&si_date="+si_date,null,"height=400,width=1024,status=yes,toolbar=no,menubar=no,location=no");
	w.print();
	return false; 
}
