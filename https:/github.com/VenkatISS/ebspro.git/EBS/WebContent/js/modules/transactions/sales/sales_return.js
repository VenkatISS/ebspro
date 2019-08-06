function showSRFormDialog() {
	document.getElementById('myModal').style.display = "block";
	pushMenu();
}

function closeSRFormDialog() {
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



/*new 23032018*/
/*
function showDSFormDialog() {
	document.getElementById('dspopupModal').style.display = "block";
    $("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');
}

function closeDSFormDialog() {
	document.getElementById("data_form").reset();
	document.getElementById('dspopupModal').style.display = "none";
$("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');
}

function showCSFormDialog() {
	document.getElementById('cspopupModal').style.display = "block";
    $("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');
}

function closeCSFormDialog() {
	document.getElementById("data_form").reset();
	document.getElementById('cspopupModal').style.display = "none";
$("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');
}

function showARBFormDialog() {
	document.getElementById('arbpopupModal').style.display = "block";
    $("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');
}

function closeARBFormDialog() {
	document.getElementById("data_form").reset();
	document.getElementById('arbpopupModal').style.display = "none";
$("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');
}
*/
/* --------------   new end 23032018 ------------------------------------------*/

function selectSaleType() {
	$(':radio:not(:checked)').attr('disabled', false);
	document.getElementById("save_data").disabled=true;
}

//Process page data
var rowpdhtml = "";
if(page_data.length>0) {
	for(var z=page_data.length-1; z>=0; z--){
		var ed = new Date(page_data[z].inv_date);
		var cvid = page_data[z].cvo_id;
		var vendorName = "HPCL";
		if(cvid>0) {
			vendorName = fetchVendorName(cvo_data,page_data[z].cvo_id);
		}else 
			vendorName = "CASH SALES / HOUSEHOLDS";
		
		var dlistLen = page_data[z].detailsList.length;
		var del = "<td align='center'><img src='images/delete.png' onclick='deleteItem("+page_data[z].id+","+page_data[z].inv_date+","+dlistLen+")'></td>";
//		var del = "<td><img src='images/delete.png' onclick='deleteItem("+page_data[z].id+","+page_data[z].inv_date+")'></td>";
		if(page_data[z].deleted == 2){
			del = "<td>TRANSACTION CANCELED</td>"
		}
		for(var i=0 ; i<page_data[z].detailsList.length ; i++){
			var spd = "";
			var prodId = page_data[z].detailsList[i].prod_code;
			var bankName = getBankName(bank_data,page_data[z].detailsList[i].bank_id);

			if(!bankName){				
				bankName = "NA";			
			}
			
			if(prodId < 100) {
				spd = fetchProductDetails(cat_cyl_types_data, page_data[z].detailsList[i].prod_code);
			} else {
				spd = fetchARBProductDetails(cat_arb_types_data, page_data[z].detailsList[i].prod_code);
			}
			//var bankName = getBankName(bank_data,page_data[z].detailsList[i].bank_id);
			
			rowpdhtml = rowpdhtml + "<tr valign='top'><td>"+page_data[z].sr_no+"</td>";
			rowpdhtml = rowpdhtml + "<td>"+page_data[z].inv_ref_no+"</td>";
			rowpdhtml = rowpdhtml + "<td>"+ed.getDate()+"-"+months[ed.getMonth()]+"-"+ed.getFullYear()+"</td>";
			rowpdhtml = rowpdhtml + "<td>"+vendorName+"</td>";
			rowpdhtml = rowpdhtml + "<td>"+page_data[z].inv_amt+"</td>";
			rowpdhtml = rowpdhtml + "<td>"+page_data[z].narration+"</td>";

			rowpdhtml = rowpdhtml + "<td>"+spd+"</td>" + "<td>"+page_data[z].detailsList[i].gstp+"</td>"
				+"<td>"+page_data[z].detailsList[i].rtn_qty+"</td><td>"+page_data[z].detailsList[i].net_weight+"</td>"
				+"<td>"+page_data[z].detailsList[i].unit_rate+"</td><td>"+page_data[z].detailsList[i].amount+"</td>"
				+"<td>"+page_data[z].detailsList[i].igst_amount+"</td>" +"<td>"+page_data[z].detailsList[i].cgst_amount+"</td>"
				+"<td>"+page_data[z].detailsList[i].sgst_amount+"</td>" +"<td>"+page_data[z].detailsList[i].aamount+"</td>"
				+"<td>"+bankName+"</td>"
				+del+"</tr>";
		}
	}
}
document.getElementById('m_data_table_body').innerHTML = rowpdhtml;


/*//Construct bank html
bankdatahtml = "<OPTION VALUE='0'>SELECT</OPTION>";
if(bank_data.length>0) {
	for(var z=0; z<bank_data.length; z++){
		if((bank_data[z].bank_code).startsWith("TAR")) {
			bankdatahtml=bankdatahtml+"<OPTION VALUE='"+bank_data[z].id+"'>"+bank_data[z].bank_code+" - "+bank_data[z].bank_acc_no+"</OPTION>";
		}
	}
}
*/

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
		var l = newRow.insertCell(11);
		
		a.innerHTML = "<td valign='top' height='4' align='center'><select name='pid' id='pid' class='form-control input_field tp freez sadd csfrz' onchange='changeNetWeight()' style='width:100px;'>"+ctdatahtml+"</select></td>";
		b.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='gstp' id='gstp' size='8' class='form-control input_field eadd' readonly='readonly' style='background-color:#F3F3F3;' placeholder='GST %'></td>";
		c.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='rqty' id='rqty' size='8' class='form-control input_field qtyc freez eadd' maxlength='4' placeholder='RETURN QUANTITY'></td>";
		d.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='nw' id='nw' size='8' class='form-control input_field freez eadd' maxlength='6' placeholder='NET WEIGHT'></td>";
		e.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='ur' id='ur' size='8' class='form-control input_field freez eadd' maxlength='7' placeholder='UNIT PRICE'></td>";
		f.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='tamt' id='tamt' class='form-control input_field  eadd' maxlength='8' size='8' placeholder='TAXABLE AMOUNT' readonly='readonly' style='background-color:#F3F3F3;' ></td>";
		g.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='igsta' id='igsta' value='0.00' class='form-control input_field eadd' size='8' maxlength='7' placeholder='IGST AMOUNT' readonly='readonly' style='background-color:#F3F3F3;'></td>";
		h.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='cgsta' id='cgsta' value='0.00' class='form-control input_field  eadd' size='8' maxlength='7' placeholder='CGST AMOUNT' readonly='readonly' style='background-color:#F3F3F3;'></td>";
		i.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='sgsta' id='sgsta' value='0.00' class='form-control input_field  eadd' size='8' maxlength='7' placeholder='SGST AMOUNT' readonly='readonly' style='background-color:#F3F3F3;'></td>";
		j.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='namt' id='namt' class='form-control input_field  eadd' size='8' maxlength='7' placeholder='AMOUNT' readonly='readonly' style='background-color:#F3F3F3;'></td>";
		
		if(document.getElementById("pt").selectedIndex == 1){
			k.innerHTML = "<td><select name='bid' id='bid' class='form-control input_field select_dropdown freez'>"+bankdatahtml+"</select></td>";
			var formobj = document.getElementById('data_form');
			var sbfrz= formobj.bid[trcount].options;
			formobj.bid[trcount].selectedIndex = 0;
			disableSelect(sbfrz,sbfrz.length);
		}else
			k.innerHTML = "<td><select name='bid' id='bid' class='form-control input_field select_dropdown freez'>"+bankdatahtml+"</select></td>";
		
		l.innerHTML = "<td valign='top' height='4' align='center'><img src='images/delete.png' onclick='doRowDeleteInTranxs(this,namt,sr_t_amt,data_table_body)'></td>";
		
		document.getElementById("fetch_data").disabled=false;
	}else {
		document.getElementById("dialog-1").innerHTML = "Please save the data and ADD again";
		alertdialogue();
		//alert("Please save the data and ADD again");
		return;
	}	
}

function validateBankAccountOnChangeInPT() {
	var formobj = document.getElementById('data_form');
	var ptv = document.getElementById("pt").value;
	var elements = document.getElementsByClassName("qtyc");
	
	if(elements.length==1) {
		var sbfrz= document.getElementById("bid").options;
		if(ptv == 1){
			formobj.bid.selectedIndex = 0;
			disableSelect(sbfrz,sbfrz.length);
		}else {
			enableSelect(sbfrz,sbfrz.length);
		}
	}else if (elements.length>1){
		for(var i=0; i<elements.length; i++){
			var sbfrz= formobj.bid[i].options;
			if(ptv == 1){
				formobj.bid[i].selectedIndex = 0;
				disableSelect(sbfrz,sbfrz.length);
			}else {
				enableSelect(sbfrz,sbfrz.length);
			}
		}
	}
}

function changeNetWeight() {
	var formobj = document.getElementById('data_form');	
	var elements = document.getElementsByClassName("qtyc");	
	if (elements.length == 1) {
		var pide = formobj.pid;
		var pidv = pide.options[pide.selectedIndex].value;
		var efrz = document.getElementById("nw");
		if(pidv>100) {
			formobj.nw.value = "NA";
			efrz.setAttribute("readOnly","true");
		}else {
			formobj.nw.value = "";
			efrz.readOnly = false;
		}
	} else if (elements.length > 1) {
		for (var i = 0; i < elements.length; i++) {
			//var pide = formobj.pid[i];
			var pidv = formobj.pid[i].options[formobj.pid[i].selectedIndex].value;
			var enwfrz = formobj.nw[i];
			if(pidv>100) {
				formobj.nw[i].value = "NA";
				enwfrz.setAttribute("readOnly","true");
			}else {
				//formobj.nw[i].value = "";
				enwfrz.readOnly = false;
			}		

		}
	}
}

function saveData(obj) {
	var formobj = document.getElementById('data_form');
	var ems = "";

	if (document.getElementById("rqty") != null) {
		var elements = document.getElementsByClassName("qtyc");

		var einvReNo = formobj.sinvno.value.trim();
		var einvDate = formobj.invdate.value;
		var ecust = formobj.cvo_id.selectedIndex;

		var einvamt = formobj.sr_t_amt.value;
		var enar = formobj.nar.value.trim();
		var ptv = formobj.pt.value.trim();
		
		if (elements.length == 1) {
			var eproduct = formobj.pid.selectedIndex;
			var egst = formobj.gstp.value.trim();
			var erqty = formobj.rqty.value.trim();
			var enw = formobj.nw.value.trim();			
			var eur = formobj.ur.value.trim();
			var etxblamt = formobj.tamt.value.trim();
			var eigsta = formobj.igsta.value.trim();
			var ecgsta = formobj.cgsta.value.trim();
			var esgsta = formobj.sgsta.value.trim();
			var enamt = formobj.namt.value.trim();
			var bidv = formobj.bid.value.trim();
			
//			ems = validateEntries(einvReNo,einvDate,ecust,einvamt,enar,
//					eproduct,egst,erqty,enw,eur,etxblamt,eigsta,ecgsta,esgsta,enamt,pname);
			ems = validateEntries(einvReNo,einvDate,ecust,einvamt,enar,
					eproduct,egst,erqty,enw,eur,etxblamt,eigsta,ecgsta,esgsta,enamt,ptv,bidv);
		} else if (elements.length > 1) {
			for (var i = 0; i < elements.length; i++) {
				var eproduct = formobj.pid[i].selectedIndex;
				var egst = formobj.gstp[i].value.trim();
				var erqty = formobj.rqty[i].value.trim();
				var enw = formobj.nw[i].value.trim();			
				var eur = formobj.ur[i].value.trim();
				var etxblamt = formobj.tamt[i].value.trim();
				var eigsta = formobj.igsta[i].value.trim();
				var ecgsta = formobj.cgsta[i].value.trim();
				var esgsta = formobj.sgsta[i].value.trim();
				var enamt = formobj.namt[i].value.trim();
				var bidv = formobj.bid[i].value.trim();
				
//				ems = validateEntries(einvReNo,einvDate,ecust,einvamt,enar,
//						eproduct,egst,erqty,enw,eur,etxblamt,eigsta,ecgsta,esgsta,enamt,pname);
				ems = validateEntries(einvReNo,einvDate,ecust,einvamt,enar,
						eproduct,egst,erqty,enw,eur,etxblamt,eigsta,ecgsta,esgsta,enamt,ptv,bidv);
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
	}else {
		var stockCheck = checkForStock();
		if(stockCheck != "false") {
			document.getElementById("dialog-1").innerHTML = stockCheck;
			alertdialogue();
			//alert(stockCheck);
			return;
		}
	}
	
	var objId = obj.id;
	document.getElementById(objId).disabled = true;
	formobj.submit();
}

function checkForStock() {
	var ret = "false";
	var formobj = document.getElementById('data_form');	
	if(document.getElementById("rqty") != null) {
		var sinvno = document.getElementById('sinvno').value;
		var ele = document.getElementsByClassName("qtyc");
		if(ele.length == 1) {
			var item = formobj.pid.value;
			var qty = formobj.rqty.value.trim();
			var rqty = 0;
			for(var z=0;z<page_data.length;z++) {
				if((page_data[z].inv_ref_no == sinvno) && (page_data[z].deleted == 0)) {
					for(var dl=0;dl<page_data[z].detailsList.length;dl++){
						if(page_data[z].detailsList[dl].prod_code==item) {
							rqty += page_data[z].detailsList[dl].rtn_qty;
						}
					}
				}
			}
			var tqty = +(qty)+ +(rqty);
			
			var sqty = 0;
			if(domprodt != "") {
				for(var d=0; d<dom_salesInv.length; d++){
					if(dom_salesInv[d].sr_no==sinvno) {
						for(var dd=0; dd < dom_salesInv[d].detailsList.length; dd++){
							if(dom_salesInv[d].detailsList[dd].prod_code==item) {
								sqty = dom_salesInv[d].detailsList[dd].quantity;
								break;
							}
						}
					}
				}				
			}else if(comprodt != "") {
				for(var c=0; c<com_salesInv.length; c++){
					if(com_salesInv[c].sr_no==sinvno) {
						for(var cd=0; cd < com_salesInv[c].detailsList.length; cd++){
							if(com_salesInv[c].detailsList[cd].prod_code==item) {
								sqty = com_salesInv[c].detailsList[cd].quantity;
								break;
							}	
						}
					}	
				}				
			}else if(arbprodt != "") {
				for(var a=0; a<arb_salesInv.length; a++){
					if(arb_salesInv[a].sr_no==sinvno){
						for(var ad=0; ad < arb_salesInv[a].detailsList.length; ad++){
							if(arb_salesInv[a].detailsList[ad].prod_code==item) {
								sqty = arb_salesInv[a].detailsList[ad].quantity;
								break;
							}
						}
					}	
				}
			}
		
			if(tqty > sqty) {
				if(rqty == sqty) {
//					return "You have already received "+pname+" all the sold products of this invoice. Please check again.<br>" +
//						" If you want to add again,please delete the previous ones regarding this invoice inorder to maintain the stock correctly.";
					return "You have already received all the sold products of this invoice. Please check again.<br>" +
					" If you want to add again,please delete the previous ones regarding this invoice inorder to maintain the stock correctly.";
				}else if(tqty > sqty) {
//					return "The quantity of "+pname+" you entered exceeds the sold quantity as you have already received some of this sold product ";
					return "The quantity you entered exceeds the sold quantity as you have already received some of this sold product ";
				}
			}else {
				var estock,astock;
				if((domprodt != "") || (comprodt != "")) {
					for(var e=0;e<equipment_data.length;e++) {
						if(equipment_data[e].prod_code == parseInt(item)) {
							estock = equipment_data[e].cs_emptys-qty;
							if(estock<0) {
//								return "The Equipment of "+pname+" you are trying to return is out of stock.Please Check and add again";
								return "The Equipment you are trying to return is out of stock.Please Check and add again";
							}
						}
					}
				}else if(arbprodt != "") { //cat_arb_types_data
					for(var e=0;e<cat_arb_types_data.length;e++) {
						if(cat_arb_types_data[e].id == parseInt(item)) {
							astock = cat_arb_types_data[e].current_stock-qty;
							if(astock<0) {
//								return "The BLPG/ARB/NFR of "+pname+" item you are trying to return is out of stock.Please Check and add again";
								return "The BLPG/ARB/NFR item you are trying to return is out of stock.Please Check and add again";
							}
						}
					}
				}
			}
		}else if (ele.length>1){
			for(var i=0; i<ele.length; i++){
				var mitem = formobj.pid[i].value;
				var mqty = formobj.rqty[i].value.trim();
				var mrqty = 0;

				for(var z=0;z<page_data.length;z++) {
					if((page_data[z].inv_ref_no == sinvno) && (page_data[z].deleted == 0)) {
						for(var dl=0;dl<page_data[z].detailsList.length;dl++){
							if(page_data[z].detailsList[dl].prod_code==mitem) {
								mrqty += page_data[z].detailsList[dl].rtn_qty;
							}
						}
					}	
				}
				var mtqty = +(mqty)+ +(mrqty);

				var msqty = 0;
				if(domprodt != "") {
					for(var d=0; d<dom_salesInv.length; d++){
						if(dom_salesInv[d].sr_no==sinvno) {
							for(var dd=0; dd < dom_salesInv[d].detailsList.length; dd++){
								if(dom_salesInv[d].detailsList[dd].prod_code==mitem) {
									msqty = dom_salesInv[d].detailsList[dd].quantity;
									break;
								}	
							}
						}	
					}				
				}else if(comprodt != "") {
					for(var c=0; c<com_salesInv.length; c++){
						if(com_salesInv[c].sr_no==sinvno) {
							for(var cd=0; cd < com_salesInv[c].detailsList.length; cd++){
								if(com_salesInv[c].detailsList[cd].prod_code==mitem) {
									msqty = com_salesInv[c].detailsList[cd].quantity;
									break;
								}	
							}
						}	
					}				
				}else if(arbprodt != "") {
					for(var a=0; a<arb_salesInv.length; a++){
						if(arb_salesInv[a].sr_no==sinvno){			
							for(var ad=0; ad < arb_salesInv[a].detailsList.length; ad++){
								if(arb_salesInv[a].detailsList[ad].prod_code==mitem) {
									msqty = arb_salesInv[a].detailsList[ad].quantity;
									break;
								}
							}
						}	
					}
				}
				
				if(mtqty > msqty) {
					if(mrqty == msqty) {
/*						return "You have already received "+pname+" all the sold products of this invoice. Please check again.<br>" +
						" If you want to add again,please delete the previous ones regarding this invoice inorder to maintain the stock correctly.";*/
						return "You have already received  all the sold products of this invoice. Please check again.<br>" +
						" If you want to add again,please delete the previous ones regarding this invoice inorder to maintain the stock correctly.";						
					}else if(mtqty > msqty) {
//						return "The quantity of "+pname+"you entered exceeds the sold quantity as you have already received some of this sold product ";
						return "The quantity you entered exceeds the sold quantity as you have already received some of this sold product ";
					}
				}else {
					var estock,astock;
					if((domprodt != "") || (comprodt != "")) {
						for(var e=0;e<equipment_data.length;e++) {
							if(equipment_data[e].prod_code == parseInt(mitem)) {
								estock = equipment_data[e].cs_emptys-mqty;
								if(estock<0) {
//									return "The Equipment of"+pname+"you are trying to return is out of stock.Please Check and add again";
									return "The Equipment you are trying to return is out of stock.Please Check and add again";
								}
							}
						}
					}else if(arbprodt != "") { //cat_arb_types_data
						for(var e=0;e<cat_arb_types_data.length;e++) {
							if(cat_arb_types_data[e].id == parseInt(mitem)) {
								astock = cat_arb_types_data[e].current_stock-mqty;
								if(astock<0) {
//									return "The BLPG/ARB/NFR of "+pname+"item you are trying to return is out of stock.Please Check and add again";
									return "The BLPG/ARB/NFR item you are trying to return is out of stock.Please Check and add again";
								}
							}
						}
					}
				}				
			}
		}
	}
	return ret;
}


function validateEntries(invReNo,invDate,cust,invamt,nar,product,gst,rqty,nw,ur,txblamt,igsta,cgsta,sgsta,enamt,ptv,bidv) {

	var errmsg = "";
	var formobj = document.getElementById('data_form');
	var custVal = formobj.cvo_id.options[cust].value;

	if (!(invReNo.length > 0))
		errmsg = "Please enter INVOICE REFERENCE NO<br>";
	
	var domSalInvD;
	var comSalInvD;
	var arbSalInvD;
	
	var chkd = checkdate(invDate);
	var vd = isValidDate(invDate);
	var vfd = ValidateFutureDate(invDate);
	
	var invcdate = new Date(invDate);
	invcdate = new Date(invcdate.getFullYear(),invcdate.getMonth(),invcdate.getDate(),0,0,0);
//	var lastTrxnDateCheck = checkforLastTrxnDate(dedate,a_created_date,invDate,trdate);
	if (!(invDate.length > 0))
		errmsg = errmsg + "Please Enter INVOICE DATE<br>";
	else if(vd != "false")
        errmsg = errmsg+"INVOICE "+vd+"<br>";
	else if(validateDayEndAdd(invDate,dedate)){
		errmsg = "INVOICE DATE should be after DayEndDate<br>";
		return errmsg;
    }else if(validateEffectiveDateForCVO(invDate,effdate)){
        errmsg = "INVOICE DATE should be after Effective Date that you have entered While First Time Login<br>";
        return errmsg;
	}else if (vfd != "false")
        errmsg = errmsg +"INVOICE"+vfd+"<br>";	
/*    else if(lastTrxnDateCheck != "false") {
    	return lastTrxnDateCheck;
	}*/
    else if(dom_salesInv.length>0 ||com_salesInv.length>0 || arb_salesInv.length>0 ) {
		for(var d=0; d<dom_salesInv.length; d++){
			if(dom_salesInv[d].sr_no==invReNo) {
				domSalInvD = new Date(dom_salesInv[d].si_date);
				domSalInvD = new Date(domSalInvD.getFullYear(),domSalInvD.getMonth(),domSalInvD.getDate(),0,0,0);
			}	
		}
		for(var c=0; c<com_salesInv.length; c++){
			if(com_salesInv[c].sr_no==invReNo) {
				comSalInvD = new Date(com_salesInv[c].si_date);
				comSalInvD = new Date(comSalInvD.getFullYear(),comSalInvD.getMonth(),comSalInvD.getDate(),0,0,0);
			} 
		}
		
		for(var a=0; a<arb_salesInv.length; a++){
			if(arb_salesInv[a].sr_no==invReNo) {
				arbSalInvD = new Date(arb_salesInv[a].si_date);
				arbSalInvD = new Date(arbSalInvD.getFullYear(),arbSalInvD.getMonth(),arbSalInvD.getDate(),0,0,0);
			}	
		}
	/*else if(chkd != "true")
		errmsg = errmsg+chkd+"<br>";*/
		
		if(domprodt!="") {
			if(invcdate < domSalInvD)
				errmsg = errmsg+"INVOICE DATE should be after STOCK SALE DATE<br>";
		}
		if(comprodt!="") {
			if(invcdate < comSalInvD)
				errmsg = errmsg+"INVOICE DATE should be after STOCK SALE DATE<br>";
	    }
		if(arbprodt!="") {
			if(invcdate < arbSalInvD)
				errmsg = errmsg+"INVOICE DATE should be after STOCK SALE DATE<br>";
		}
    }
	
	if (!(cust > 0))
		errmsg = errmsg + "Please Select CUSTOMER<br>";
	
	else if((domCust!="" && domCust!=custVal) || (comCust!="" && comCust!=custVal) || (arbCust!="" && arbCust!=custVal))
		errmsg = errmsg + "Please select Valid CUSTOMER<br>";			

	if($("input[type=radio][name=stype]:checked").length <= 0) {
		errmsg = errmsg + "Please select SALE TYPE<br>";			
	}  
	if (!invamt.length > 0)
		errmsg = errmsg + "INVOICE AMOUNT cannot be zero <br>";
	
	if (!nar.length > 0)
		errmsg = errmsg + "Please enter NARRATION.Enter NA if not available <br>";
	
	
	if (!(product > 0))
		errmsg = errmsg + "Please Select PRODUCT<br>";

	if (!gst.length > 0)
		errmsg = errmsg + "Please FETCH GST%. <br>";
	
	else if (!validateProduct()) 
		errmsg = errmsg + "Please Select Another PRODUCT Which Was not Selected.<br>";
	if (!rqty.length > 0)
		errmsg = errmsg + "Please Enter RETURN QUANTITY. <br>";
	else if (!(validateDecimalNumberMinMax(rqty, 0, 10000)))
		errmsg = errmsg + "Please Enter Valid RETURN QUANTITY And It Must Be In Between 0 And 10,000 .<br>";

	if (!nw.length > 0)
		errmsg = errmsg + "Please Enter NET WEIGHT. <br>";
	var pidd=formobj.pide;

	if(pidd<100){
	if (!(validateNumberMinMax(nw, -1, 1000000)))
		errmsg = errmsg + "Please Enter Valid NET WEIGHT And It Must Be In Between 0 And 10,00,000 .<br>";
	}
	if (!ur.length > 0)
		errmsg = errmsg + "Please Enter UNIT PRICE <br>";
	else if (validateDot(ur))
		errmsg = errmsg + "UNIT PRICE must contain atleast one number<br>";
	else if (!(validateDecimalNumberMinMax(ur, 0, 10000000)))
		errmsg = errmsg + "Please Enter valid UNIT PRICE And It Must Be In Between 0 And 1,00,00,000<br>";
	else
		formobj.ur.value = round(parseFloat(ur), 2);
	
	if(!(ptv>0)){
		errmsg = errmsg + "Please select PAYMENT TERMS<br>";
	}
	
	if((ptv == 2 || ptv == 3) && (!(bidv >0))) {
		errmsg = errmsg + "Please select BANK ACCOUNT<br>";
	}
		

	if (!(validateDecimalNumberMinMax(txblamt, 0, 100000000)))
		errmsg = errmsg + "Invalid TAXABLE AMOUNT.. <br>";
	if (!(validateDecimalNumberMinMax(igsta,-1, 10000000)))
		errmsg = errmsg + "Invalid GST.. <br>";
	if (!(validateDecimalNumberMinMax(cgsta,-1, 10000000)))
		errmsg = errmsg + "Invalid GST.. <br>";
	if (!(validateDecimalNumberMinMax(sgsta,-1, 10000000)))
		errmsg = errmsg + "Invalid GST.. <br>";
	if (!(validateDecimalNumberMinMax(enamt, 0, 10000000)))
		errmsg = errmsg + "Invalid AMOUNT.. <br>";
	
	return errmsg;
}

/*function deleteItem(iid,srdate) {
	var flag=validateDayEndForDelete(srdate,dedate);
	if(flag) {
		if (confirm("Are you sure you want to delete?") == true) {
			var formobj = document.getElementById('m_data_form');
			formobj.actionId.value = "5553";
			formobj.dataId.value = iid;
			formobj.submit();
		}
	}else 
		alert("This record can't be deleted as you have submitted and verified your DAYEND calculations succuessfully... ");
	
}*/

/*
function deleteItem(iid,srdate){
	var flag=validateDayEndForDelete(srdate,dedate);
	if(flag){
		$("#myDialogText").text("Are You Sure You Want To Delete?");
		var formobj = document.getElementById('m_data_form');
		confirmDialogue(formobj,5553,iid);
	}else {
		document.getElementById("dialog-1").innerHTML = "This record can't be deleted as you have submitted and verified your DAYEND calculations succuessfully";
		alertdialogue();
	}
}
*/

function deleteItem(iid, srdate, detailsListlen){
	var flag=validateDayEndForDelete(srdate,dedate);
	if(flag){
		var formobj = document.getElementById('m_data_form');
		if(detailsListlen > 1){
			$("#myDialogText").text("THIS INVOICE CONTAINS MULTIPLE PRODUCTS.IF YOU DELETE THIS ONE TOTAL INVOICE WILL GET DELETED.ARE YOU SURE YOU WANT TO DELETE?");
			confirmDialogue(formobj,5553,iid);
		}else if(detailsListlen == 1){
			$("#myDialogText").text("Are You Sure You Want To Delete?");
			confirmDialogue(formobj,5553,iid);
		}
	}else {
		document.getElementById("dialog-1").innerHTML = "This record can't be deleted as you have submitted and verified your DAYEND calculations succuessfully";
		alertdialogue();
	}
}


var comprodt = new Array();
var domprodt = new Array();
var arbprodt = new Array();
var domCust="", domUpv="";
var comCust ="", comUpv="";
var arbCust = "", arbUpv="";
var cgstv="";
var igstv="";
function fetchGST() {

	comprodt = [];
	domprodt = [];
	arbprodt = [];
	domCust="", domUpv="" , comCust ="";
	comUpv="", arbCust = "", arbUpv="";
	cgstv="", igstv="";
	
	
	var formobj = document.getElementById('data_form');
  	//var enteredInv=formobj.pinvno;
  	var sinvno = document.getElementById('sinvno').value;
  	//var sinvnoP=parseInt(sinvno);
  	if (document.getElementById("ur") != null) {
  		var elements = document.getElementsByClassName("qtyc");
  		var unitprice;var gstp;var flag="NO";
  		
        if(sinvno != "") {
  			if (elements.length == 1) {
  				var pide = formobj.pid;
  				for(var i=0;i<dom_salesInv.length;i++) {
//  					if(dom_salesInv[i].customer_id!=0){
  						if((dom_salesInv[i].sr_no == sinvno) && (dom_salesInv[i].deleted == 0)){
  							flag="YES";
  							for(var m=0;m<dom_salesInv[i].detailsList.length;m++){
  								domprodt.push(dom_salesInv[i].detailsList[m].prod_code);
  								domCust=dom_salesInv[i].customer_id;
  								//domUpv = dom_salesInv[i].detailsList[m].unit_rate;
  								cgstv=dom_salesInv[i].detailsList[m].sgst_amount;
  								igstv=dom_salesInv[i].detailsList[m].igst_amount;
  							}
  						}
//  					}
  				}
  					
  				for(var k=0;k<com_salesInv.length;k++) {
// 					if(com_salesInv[k].customer_id!=0){
  						if((com_salesInv[k].sr_no == sinvno) && (com_salesInv[k].deleted == 0)) {
  							flag="YES";
  							for(var h=0;h<com_salesInv[k].detailsList.length;h++){
  								comprodt.push(com_salesInv[k].detailsList[h].prod_code);
  								comCust=com_salesInv[k].customer_id;
  								//comUpv=com_salesInv[k].detailsList[h].unit_rate;
  								cgstv=com_salesInv[k].detailsList[h].sgst_amount;
  								igstv=com_salesInv[k].detailsList[h].igst_amount;
  							}
  						}
//					}
  				}
  					
  				for(var l=0;l<arb_salesInv.length;l++) {
//					if(arb_salesInv[l].customer_id!=0){
  						if((arb_salesInv[l].sr_no == sinvno) && (arb_salesInv[l].deleted == 0)) {
  							flag="YES";
  							for(var g=0;g<arb_salesInv[l].detailsList.length;g++){
  								arbprodt.push(arb_salesInv[l].detailsList[g].prod_code);
  								arbCust=arb_salesInv[l].customer_id;
  								//arbUpv=arb_salesInv[l].detailsList[g].unit_rate;
  								cgstv=arb_salesInv[l].detailsList[g].sgst_amount;
  								igstv=arb_salesInv[l].detailsList[g].igst_amount;
  							}
  						}
//					}
  				}
  					
  				if(flag=="YES") {
  					if(pide.selectedIndex > 0) {
  						if(typeof domprodt != undefined && domprodt.length > 0) {
  							for (var z=0;z<dom_salesInv.length;z++) {
  								for(var f=0;f<dom_salesInv[z].detailsList.length; f++){	
	  								if(dom_salesInv[z].detailsList[f].prod_code==pide.value && (dom_salesInv[z].sr_no == sinvno)) {
	  									//hsnval = fetchHSNCode(equipment_data,purchases_data[z].prod_code);							
	  									unitprice = dom_salesInv[z].detailsList[f].unit_rate;
	  									gstp = dom_salesInv[z].detailsList[f].gstp;
	  								}else if(!domprodt.includes(parseInt(pide.value))) {
	  									document.getElementById("dialog-1").innerHTML = "The PRODUCT you have selected is not under the provided SALES INVOICE NUMBER ";
	  									alertdialogue();
	  									//alert("The PRODUCT you have selected is not under the provided SALES INVOICE NUMBER ");
  										formobj.ur.value="";
  										formobj.gstp.value="";
  										return;
	  								}
  								}
  							}
  						}else if(typeof comprodt !== 'undefined' && comprodt.length > 0) {
  							for (var w=0;w<com_salesInv.length;w++) {
  								for(var e=0;e<com_salesInv[w].detailsList.length; e++){
  									if(com_salesInv[w].detailsList[e].prod_code==pide.value && (com_salesInv[w].sr_no == sinvno)) {
  										//hsnval = fetchHSNCode(equipment_data,purchases_data[z].prod_code);							
  										unitprice = com_salesInv[w].detailsList[e].unit_rate;
  										gstp = com_salesInv[w].detailsList[e].gstp;
  									}else if(!comprodt.includes(parseInt(pide.value))) {
  										document.getElementById("dialog-1").innerHTML = "The PRODUCT you have selected is not under the provided SALES INVOICE NUMBER ";
	  									alertdialogue();
  										//alert("The PRODUCT you have selected is not under the provided SALES INVOICE NUMBER ");
  										formobj.ur.value="";
  										formobj.gstp.value="";
  										return;
  									}
  								}
  							}
  						}else if(typeof arbprodt !== 'undefined' && arbprodt.length > 0) {
  							for (var x=0;x<arb_salesInv.length;x++) {
  								for(var t=0;t<arb_salesInv[x].detailsList.length; t++){
  									if(arb_salesInv[x].detailsList[t].prod_code==pide.value && (arb_salesInv[x].sr_no == sinvno)) {
  										//hsnval = fetchHSNCode(equipment_data,purchases_data[z].prod_code);							
  										unitprice = arb_salesInv[x].detailsList[t].unit_rate;
  										gstp = arb_salesInv[x].detailsList[t].gstp;
  									}else if(!arbprodt.includes(parseInt(pide.value))) {
  										document.getElementById("dialog-1").innerHTML = "The PRODUCT you have selected is not under the provided SALES INVOICE NUMBER ";
	  									alertdialogue();
  										//alert("The PRODUCT you have selected is not under the provided SALES INVOICE NUMBER ");
  										//formobj.hsncode.value="";
  										formobj.ur.value="";
  										formobj.gstp.value="";
  										return;
  									}
  								}
  							}
  						}
  					}else {
  						document.getElementById("dialog-1").innerHTML = "Please Select PRODUCT and Click FETCH UNIT PRICE AND GST";
						alertdialogue();
  						//alert("Please Select PRODUCT and Click FETCH UNIT PRICE AND GST");
  						return;
  					}
  				}else if(flag == "NO") {
  					document.getElementById("dialog-1").innerHTML = "Please enter valid SALES INVOICE NUMBER ";
					alertdialogue();
  					//alert("Please enter valid SALES INVOICE NUMBER ");
  					return;							
  				}
  				//formobj.hsncode.value = hsnval;
  				formobj.ur.value = unitprice;
  				formobj.gstp.value = gstp;
  			}else if (elements.length > 1) {
  				for(var k = 0; k < elements.length; k++) {
  					var pide = formobj.pid[k];
  					for(var i=0;i<dom_salesInv.length;i++) {
  						if(dom_salesInv[i].customer_id!=0){
  							if(dom_salesInv[i].sr_no == sinvno){
  								flag="YES";
  								for(var m=0;m<dom_salesInv[i].detailsList.length;m++){
  									domprodt.push(dom_salesInv[i].detailsList[m].prod_code);
  									domCust=dom_salesInv[i].customer_id;
  									//domUpv=dom_salesInv[i].detailsList[m].unit_rate;
  									cgstv=dom_salesInv[i].detailsList[m].sgst_amount;
  									igstv=dom_salesInv[i].detailsList[m].igst_amount;
  								}
  							}
  						}
  					}
  					
  					for(var c=0;c<com_salesInv.length;c++) {
  						if(com_salesInv[c].customer_id!=0){
  							if((com_salesInv[c].sr_no == sinvno)) {
  								flag="YES";
  								for(var h=0;h<com_salesInv[c].detailsList.length;h++){
  									comprodt.push(com_salesInv[c].detailsList[h].prod_code);
  									comCust=com_salesInv[c].customer_id;
  									//comUpv=com_salesInv[c].detailsList[h].unit_rate;
  									cgstv=com_salesInv[c].detailsList[h].sgst_amount;
  									igstv=com_salesInv[c].detailsList[h].igst_amount;
  								}
  							}
  						}
  					}
  					
  					for(var l=0;l<arb_salesInv.length;l++) {
  						if(arb_salesInv[l].customer_id!=0){
  							if(arb_salesInv[l].sr_no == sinvno) {
  								flag="YES";
  								for(var g=0;g<arb_salesInv[l].detailsList.length;g++){
  									arbprodt.push(arb_salesInv[l].detailsList[g].prod_code);
  									arbCust=arb_salesInv[l].customer_id;
  									//arbUpv=dom_salesInv[l].detailsList[g].unit_rate;
  									cgstv=arb_salesInv[l].detailsList[g].sgst_amount;
  									igstv=arb_salesInv[l].detailsList[g].igst_amount;
  								}
  							}
  						}
  					}
  					
  					if(flag=="YES") {
  						if (pide.selectedIndex > 0) {
  						  if(typeof domprodt != undefined && domprodt.length > 0) {
  							for (var z=0;z<dom_salesInv.length;z++) {
  								for(var f=0;f<dom_salesInv[z].detailsList.length; f++){	
	  								if(dom_salesInv[z].detailsList[f].prod_code==pide.value && (dom_salesInv[z].sr_no == sinvno)) {
	  									//hsnval = fetchHSNCode(equipment_data,purchases_data[z].prod_code);							
	  									unitprice = dom_salesInv[z].detailsList[f].unit_rate;
	  									gstp = dom_salesInv[z].detailsList[f].gstp;
	  								}else if(!domprodt.includes(parseInt(pide.value))) {
	  									document.getElementById("dialog-1").innerHTML = "The PRODUCT you have selected is not under the provided SALES INVOICE NUMBER ";
	  									alertdialogue();
	  									//alert("The PRODUCT you have selected is not under the provided SALES INVOICE NUMBER ");
  										formobj.ur[k].value="";
  										formobj.gstp[k].value="";
  										return;
	  								}
  								}
  							}
  						}else if(typeof comprodt !== 'undefined' && comprodt.length > 0) {
  							for (var w=0;w<com_salesInv.length;w++) {
  								for(var e=0;e<com_salesInv[w].detailsList.length; e++){
  									if(com_salesInv[w].detailsList[e].prod_code==pide.value && (com_salesInv[w].sr_no == sinvno)) {
  										//hsnval = fetchHSNCode(equipment_data,purchases_data[z].prod_code);							
  										unitprice = com_salesInv[w].detailsList[e].unit_rate;
  										gstp = com_salesInv[w].detailsList[e].gstp;
  									}else if(!comprodt.includes(parseInt(pide.value))) {
  										document.getElementById("dialog-1").innerHTML = "The PRODUCT you have selected is not under the provided SALES INVOICE NUMBER  ";
	  									alertdialogue();
  										//alert("The PRODUCT you have selected is not under the provided SALES INVOICE NUMBER ");
  										formobj.ur[k].value="";
  										formobj.gstp[k].value="";
  										return;
  									}
  								}
  							}
  						}else if(typeof arbprodt !== 'undefined' && arbprodt.length > 0) {
  							for (var x=0;x<arb_salesInv.length;x++) {
  								for(var t=0;t<arb_salesInv[x].detailsList.length; t++){
  									if(arb_salesInv[x].detailsList[t].prod_code==pide.value && (arb_salesInv[x].sr_no == sinvno)) {
  										//hsnval = fetchHSNCode(equipment_data,purchases_data[z].prod_code);							
  										unitprice = arb_salesInv[x].detailsList[t].unit_rate;
  										gstp = arb_salesInv[x].detailsList[t].gstp;
  									}else if(!arbprodt.includes(parseInt(pide.value))) {
  										document.getElementById("dialog-1").innerHTML = "The PRODUCT you have selected is not under the provided SALES INVOICE NUMBER ";
	  									alertdialogue();
  										//alert("The PRODUCT you have selected is not under the provided SALES INVOICE NUMBER ");
  										//formobj.hsncode.value="";
  										formobj.ur[k].value="";
  										formobj.gstp[k].value="";
  										return;
  									}
  								}
  								}
  							}	  								
  						}else {
  							document.getElementById("dialog-1").innerHTML = "Please Select PRODUCT and Click FETCH UNIT PRICE AND GST";
							alertdialogue();
  							//alert("Please Select PRODUCT and Click FETCH UNIT PRICE AND GST");
  							return;
  						}

  					}
  					else if(flag == "NO") {
  						document.getElementById("dialog-1").innerHTML = "Please enter valid SALES INVOICE NUMBER ";
						alertdialogue();
  						//alert("Please enter valid SALES INVOICE NUMBER ");
  						return;							
  					}
  					//formobj.hsncode[k].value = hsnval;
  					formobj.ur[k].value = unitprice;
  					formobj.gstp[k].value = gstp;
  				}
  			}
  		}else {
  			document.getElementById("dialog-1").innerHTML = "Please enter SALES INVOICE number";
			alertdialogue();
  			//alert("Please enter SALES INVOICE number");
  			return;
  		}
  	} else {
  		document.getElementById("dialog-1").innerHTML = "Please Add Data and Click FETCH GST";
		alertdialogue();
  		//alert("Please Add Data and Click FETCH GST");
  		return;
  	}
  	document.getElementById("calc_data").disabled = false;
  }

function calculateValues(){
	var cvogstin;
	var cvoreg;
	var formobj = document.getElementById('data_form');
	var cust = formobj.cvo_id.selectedIndex;
	var custVal = formobj.cvo_id.options[cust].value;
	if(document.getElementById("pid") != null){
		var ptypev = formobj.stype.value;
		if(formobj.cvo_id.selectedIndex >0) {
			if((domCust!="" && domCust!=custVal) || (comCust!="" && comCust!=custVal) || (arbCust!="" && arbCust!=custVal)) {
				document.getElementById("dialog-1").innerHTML = "Please select Valid CUSTOMER<br>";	
				alertdialogue();
				return;
			}else {
				for(var d=0;d< cvo_data.length;d++){
					if(parseInt(formobj.cvo_id.value) == cvo_data[d].id) {
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
/*		if(cvoreg==1) {
			var dstcode = dealergstin.substr(0, 2);
			var cvstcode = cvogstin.substr(0, 2);
			var ptypecheck = "";
			if(cvstcode == dstcode)
				ptypecheck = "lp"; 
			else 
				ptypecheck = "isp";
		}else if(cvoreg==2) {
			 if(cgstv!=0)
				 ptypecheck = "lp";
			 else
				 ptypecheck = "isp";
		}*/
		
		var ptypecheck = "";
		if(custVal == 0){
			ptypecheck = "lp";
		}else { 
			if(cvoreg==1) {
				var dstcode = dealergstin.substr(0, 2);
				var cvstcode = cvogstin.substr(0, 2);
			
				if(cvstcode == dstcode)
					ptypecheck = "lp"; 
				else 
					ptypecheck = "isp";
			}else if(cvoreg==2) {
				 if(cgstv!=0)
					 ptypecheck = "lp";
				 else
					 ptypecheck = "isp";
			}
		}
		
		var elements = document.getElementsByClassName("qtyc");
		if(elements.length==1) {
			var pide = formobj.pid;
			var pidv = pide.options[pide.selectedIndex].value;
			var vpv = formobj.gstp.value.trim();
			var nwv = formobj.nw.value.trim();
			var urv = formobj.ur.value.trim();
			var rq = formobj.rqty.value.trim();
			var taxamt = 0;
			var gsta = 0;

			var ems = validateCalcValues(pidv,vpv,nwv,urv,rq,ptypecheck,ptypev);
			if(ems.length>0){
				document.getElementById("dialog-1").innerHTML = ems;
				alertdialogue();
				//alert(ems);
				return;
			}			
			formobj.nw.value = parseFloat(nwv).toFixed(2);
			formobj.ur.value = parseFloat(urv).toFixed(2);
			/*if(pidv<10) {
				taxamt = (nwv*urv);
				gsta = (((nwv*urv)*vpv)/100).toFixed(2);
			} else {
				taxamt = (rq*urv);
				gsta = (((rq*urv)*vpv)/100).toFixed(2);
			}*/
			
			taxamt = (rq*urv);
			gsta = (((rq*urv)*vpv)/100).toFixed(2);
			
			formobj.tamt.value = (taxamt).toFixed(2);
			if(ptypev=='lp') {
				formobj.sgsta.value=(gsta/2).toFixed(2);
				formobj.cgsta.value=(gsta/2).toFixed(2);
				formobj.igsta.value="0.00";
				formobj.namt.value = (+(formobj.tamt.value) + +(formobj.sgsta.value) + +(formobj.cgsta.value)).toFixed(2);
			} else if (ptypev=='isp'){
				formobj.igsta.value=gsta;
				formobj.sgsta.value="0.00";
				formobj.cgsta.value="0.00";				
				formobj.namt.value = (+(formobj.tamt.value) + +(formobj.igsta.value)).toFixed(2);
			}
			formobj.sr_t_amt.value = Math.round(formobj.namt.value);
		} else if (elements.length>1){
			
			var totalAmt = 0;
			for(var i=0; i<elements.length; i++){
				var pide = formobj.pid[i];
				var pidv = pide.options[pide.selectedIndex].value;
				var vpv = formobj.gstp[i].value.trim();
				var nwv = formobj.nw[i].value.trim();
				var urv = formobj.ur[i].value.trim();
				var rq = formobj.rqty[i].value.trim();
				var taxamt = 0;
				var gsta = 0;

				var ems = validateCalcValues(pidv,vpv,nwv,urv,rq,ptypecheck,ptypev);
				if(ems.length>0){
					document.getElementById("dialog-1").innerHTML = ems;
					alertdialogue();
					//alert(ems);
					return;
				}								
				formobj.nw[i].value = parseFloat(nwv).toFixed(2);
				formobj.ur[i].value = parseFloat(urv).toFixed(2);
				/*if(pidv<10) {
					taxamt = (nwv*urv);
					gsta = (((nwv*urv)*vpv)/100).toFixed(2);
				} else {
					taxamt = (rq*urv);
					gsta = (((rq*urv)*vpv)/100).toFixed(2);
				}*/
				
				taxamt = (rq*urv);
				gsta = (((rq*urv)*vpv)/100).toFixed(2);
				
				formobj.tamt[i].value = (taxamt).toFixed(2);
				if(ptypev=='lp') {
					formobj.sgsta[i].value=(gsta/2).toFixed(2);
					formobj.cgsta[i].value=(gsta/2).toFixed(2);
					formobj.igsta[i].value="0.00";
					formobj.namt[i].value = (+(formobj.tamt[i].value) + +(formobj.sgsta[i].value) + +(formobj.cgsta[i].value)).toFixed(2);
				} else if (ptypev=='isp'){
					formobj.igsta[i].value=gsta;
					formobj.sgsta[i].value="0.00";
					formobj.cgsta[i].value="0.00";
					formobj.namt[i].value = (+(formobj.tamt[i].value) + +(formobj.igsta[i].value)).toFixed(2);
				}
				totalAmt = +totalAmt + +(formobj.namt[i].value);
			}
			formobj.sr_t_amt.value=Math.round(totalAmt);
		}
		
	}else {
		document.getElementById("dialog-1").innerHTML = "Please Add Sales Return details and Click Calculate";
		alertdialogue();
		//alert("Please Add Sales Return details and Click Calculate");
		return;
	}
	document.getElementById("save_data").disabled=false;

	$(':radio:not(:checked)').attr('disabled', true);
	restrictChangingAllValues(".freez",".csfrz");

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

//function validateCalcValues(pidv,vpv,nw,urv,rqty,ptypecheck, ptypev,pname) {
function validateCalcValues(pidv,vpv,nw,urv,rqty,ptypecheck, ptypev) {
	var errmsg = "";
	var formobj = document.getElementById('data_form');
   
	if($("input[type=radio][name=stype]:checked").length <= 0) {
		errmsg = errmsg + "Please select SALE TYPE<br>";			
	}
	if(($("input[type=radio][name=stype]:checked").length > 0)&&(ptypecheck != ptypev))
		errmsg = errmsg + "The SALE TYPE you have selected might be wrong.Please check it again<br>";

	if (!vpv.length > 0)
		errmsg = errmsg + "Please FETCH GST%. <br>";

	if (!(rqty.length > 0))
		errmsg = errmsg + "Please enter QUANTITY to be returned.<br>";
	else if (validateNegatives(rqty))
		errmsg = errmsg + "RETURN QUANTITY cant be negative <br>";
	else if (!(checkNumber(rqty)))
		errmsg = errmsg + " Please Enter Valid RETURN QUANTITY <br>";
	else if (!((parseFloat(rqty) > 0) && parseFloat(rqty) < 10000))
		errmsg = errmsg + "RETURN QUANTITY must lie in between 0 and 10000 <br>";
//	else if(!checkretQuantityWithSales(rqty,pidv,formobj))
//		errmsg = errmsg + pname+" RETURN QUANTITY must be less than or equal to sold quantity <br>";	
	else if(!checkretQuantityWithSales(rqty,pidv,formobj))
		errmsg = errmsg +" RETURN QUANTITY must be less than or equal to sold quantity <br>";

	if(pidv>100) {
		formobj.nw.value = "NA";
	}else{
		if (!(nw.length > 0))
			errmsg = errmsg + "Please enter NET WEIGHT.Atleast enter 0 if not available. <br>";	
		else if(validateDot(nw))
			errmsg = errmsg + "NET WEIGHT must contain atleast one number. <br>";
		else if (!(isDecimalNumber(nw)))
			errmsg = errmsg + "NET WEIGHT must contain only numerics >=0. <br>";		
	}
		
	if (!urv.length > 0)
		errmsg = errmsg + "Please Enter UNIT PRICE. <br>";
	else if (validateDot(urv))
		errmsg = errmsg + "UNIT PRICE must contain atleast one number. <br>";
	else if (!(validateDecimalNumberMinMax(urv, 0, 100000)))
		errmsg = errmsg + "Please Enter valid UNIT PRICE and it must be In Between 0 And 100000 .<br>";
	return errmsg;
}

function checkretQuantityWithSales(rqty,pide,formobj) {
	var invReNo = formobj.sinvno.value.trim();
	var dsqty=0; var csqty=0; var asqty=0;

	if(dom_salesInv.length>0 ||com_salesInv.length>0 || arb_salesInv.length>0 ) {
		for(var d=0; d<dom_salesInv.length; d++){
			if(dom_salesInv[d].sr_no==invReNo) {
				for(var dd=0; dd < dom_salesInv[d].detailsList.length; dd++){
					if(dom_salesInv[d].detailsList[dd].prod_code==pide) {
						dsqty = dom_salesInv[d].detailsList[dd].quantity;
						break;
					}	
				}
			}	
		}
		
		for(var c=0; c<com_salesInv.length; c++){
			if(com_salesInv[c].sr_no==invReNo) {
				for(var cd=0; cd < com_salesInv[c].detailsList.length; cd++){
					if(com_salesInv[c].detailsList[cd].prod_code==pide) {
						csqty = com_salesInv[c].detailsList[cd].quantity;
						break;
					}	
				}
			}	
		}
		
		for(var a=0; a<arb_salesInv.length; a++){
			if(arb_salesInv[a].sr_no==invReNo){			
				for(var ad=0; ad < arb_salesInv[a].detailsList.length; ad++){
					if(arb_salesInv[a].detailsList[ad].prod_code==pide) {
						asqty = arb_salesInv[a].detailsList[ad].quantity;
						break;
					}
				}
			}	
		}
		if(domprodt!="") {
			if(parseInt(rqty)>dsqty)
				return false;
		}else if(comprodt!="") {
			if(parseInt(rqty)>csqty)
				return false;
	    }else if(arbprodt!="") {
			if(parseInt(rqty)>asqty)
				return false;
		}else 
			return true;
    }
	return true;	
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