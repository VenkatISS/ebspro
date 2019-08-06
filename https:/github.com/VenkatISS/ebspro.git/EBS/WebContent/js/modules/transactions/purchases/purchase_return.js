/*function showPRFormDialog() {
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

function showPRFormDialog() {
	document.getElementById('myModal').style.display = "block";
	pushMenu();
}


function closePRFormDialog() {	
	
	document.getElementById("calc_data").disabled = true;
	document.getElementById("save_data").disabled = true;
	
	$(':radio:not(:checked)').attr('disabled', false);

	var formobj = document.getElementById('data_form');	
	var elements = document.getElementsByClassName("qtyc");
	var efrz = document.getElementsByClassName("freez");
	remEleReadOnly(efrz,efrz.length);
	if (elements.length == 1) {
		var spfrz= document.getElementById("pid").options;
		enableSelect(spfrz,spfrz.length);

	} else if (elements.length > 1) {
		for (var i = 0; i < elements.length; i++) {
			var pfrz=formobj.pid[i].options;
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


//Process page data
var rowpdhtml = "";
if(page_data.length>0) {
	for(var z=page_data.length-1;z>=0; z--){
		var ed = new Date(page_data[z].inv_date);
		var cvid = page_data[z].cvo_id;
		var dlistLen=page_data[z].detailsList.length;
		var vendorName = "HPCL";
		if(cvid>0) {
			vendorName = fetchVendorName(cvo_data,page_data[z].cvo_id);
		}
		var del = "<td align='center'><img src='images/delete.png' onclick='deleteItem("+page_data[z].id+","+page_data[z].inv_date+","+dlistLen+")'></td>";
		if(page_data[z].deleted == 2){
			del = "<td>TRANSACTION CANCELED</td>"
		}
		var spd;
		for(var i=0; i<page_data[z].detailsList.length; i++){
			var x=page_data[z].detailsList[i].prod_code;
			if(x<100){
				spd = fetchProductDetails(cat_cyl_types_data, page_data[z].detailsList[i].prod_code);
            }else {
            	spd = fetchARBProductDetails(cat_arb_types_data, page_data[z].detailsList[i].prod_code);
            }
			var vehicleNo = page_data[z].detailsList[i].vehicle_no;
			if(!vehicleNo){
				vehicleNo = "NA";			
			}
			//var bankName = getBankName(bank_data,page_data[z].detailsList[i].bank_id);
			//if(i==0) {
			if(page_data[z].deleted==2){
				rowpdhtml = rowpdhtml + "<tr><td>"+page_data[z].sid+"</td>";
			}else {
				rowpdhtml = rowpdhtml + "<tr><td><a href='javascript:showInvoice("+page_data[z].id+","+page_data[z].inv_date+")'>"+page_data[z].sid+"</a></td>";
			}
			
			
			rowpdhtml = rowpdhtml + 
				//"<tr>"+"<td><a href='javascript:showInvoice("+page_data[z].id+")'>"+page_data[z].sid+"</a></td>"+
				"<td>"+page_data[z].inv_ref_no+"</td>"+
        		"<td>"+ed.getDate()+"-"+months[ed.getMonth()]+"-"+ed.getFullYear()+"</td>"+
        		"<td>"+vendorName+"</td>"+
        		"<td>"+page_data[z].inv_amt+"</td>"+
        		"<td>"+page_data[z].narration+"</td>";

			rowpdhtml = rowpdhtml + "<td>"+spd+"</td>"+
				/*"<td>"+page_data[z].detailsList[i].csteh_no+"</td>"+*/
				"<td>"+page_data[z].detailsList[i].gstp+"</td>"+
				"<td>"+page_data[z].detailsList[i].rtn_qty+"</td>"+
				"<td>"+page_data[z].detailsList[i].net_weight+"</td>"+
				"<td>"+page_data[z].detailsList[i].unit_rate+"</td>"+
				"<td>"+page_data[z].detailsList[i].aamount+"</td>"+
				/*"<td>"+page_data[z].detailsList[i].aamount+"</td>"+*/
				"<td>"+page_data[z].detailsList[i].igst_amount+"</td>"+
				"<td>"+page_data[z].detailsList[i].cgst_amount+"</td>"+
				"<td>"+page_data[z].detailsList[i].sgst_amount+"</td>"+
				"<td>"+page_data[z].detailsList[i].amount+"</td>"+
				/*"<td>"+bank_data[z].bank_code+"-"+bank_data[z].bank_acc_no+"</td>"+*/
				"<td>"+vehicleNo+"</td>"+
				del+
				"</tr>";
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
	document.getElementById("save_data").disabled = true;

	var ele = document.getElementsByClassName("qtyc");
	if(ele.length < 4) {
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
		
	
		a.innerHTML = "<td valign='top' height='4' align='center'><select name='pid' id='pid' class='form-control input_field select_dropdown sadd tp' onchange='changeNetWeight()'>"
			+ ctdatahtml + "</select></td>";
		/*b.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='hsncode' id='hsncode' class='form-control input_field eadd' placeholder='HSN CODE' readonly='readonly'></td>";*/
		b.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='ur' id='ur' class='form-control input_field eadd' placeholder='UNIT PRICE' readonly='readonly'></td>";
		c.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='gstp' id='gstp' class='form-control input_field eadd' placeholder='GST %' readonly='readonly'></td>";
		d.innerHTML = "<td valign='top' height='4' align='center'>" +
			"<input type=text name='rqty' id='rqty' class='form-control input_field qtyc freez eadd' placeholder='RETURN QUANTITY' maxlength='4'>" +
			"<input type='hidden' name='cpqty' id='cpqty' value=''><input type='hidden' name='apqty' id='apqty' value=''>" +
			"</td>";
		e.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='nw' id='nw' class='form-control input_field freez eadd' placeholder='NET WEIGHT' maxlength='8' /></td>";
		f.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='namt' id='ramt' maxlength='10' class='form-control input_field eadd' placeholder='TAXABLE AMOUNT' readonly='readonly' ></td>";
		/*	h.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='aamt' id='aamt' maxlength='6' class='form-control input_field eadd' placeholder='REALISED AMOUNT'></td>";*/
		g.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='igsta' id='igst' class='form-control input_field eadd' placeholder='IGST AMOUNT' readonly='readonly' ></td>";
		h.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='cgsta' id='cgst' class='form-control input_field eadd' placeholder='CGST AMOUNT' readonly='readonly' ></td>";
		i.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='sgsta' id='sgst' class='form-control input_field eadd' placeholder='SGST AMOUNT' readonly='readonly' ></td>";	
		j.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='tamt' id='tamt' class='form-control input_field eadd' placeholder='TOTAL AMOUNT' readonly='readonly' ></td>";
		/*m.innerHTML = "<td valign='top' height='4' align='center'><select name='bid' id='bid' class='form-control input_field select_dropdown sadd'>"
			+ bankdatahtml + "</select></td>";*/
		k.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='vno' id='vno' class='form-control input_field eadd' placeholder='VEHICLE NUMBER' maxlength='10'></td>";
		l.innerHTML = "<td valign='top' height='4' align='center'><img src='images/delete.png' onclick='doRowDeleteInTranxs(this,ramt,pr_t_amt,data_table_body)'></td>";

		document.getElementById("fetch_data").disabled=false;
	}else {
		document.getElementById("dialog-1").innerHTML = "Please save the data and ADD again";
		alertdialogue();
		//alert("Please save the data and ADD again");
		return;
	}
}

function changeNetWeight() {
	var formobj = document.getElementById('data_form');	
	var elements = document.getElementsByClassName("qtyc");

	if (elements.length == 1) {
		var pide = formobj.pid;
		var efrz = document.getElementById("nw");
		if(pide.value > 100) {
			formobj.nw.value = "NA";
			efrz.setAttribute("readOnly","true");
		}else {
			formobj.nw.value = "";
			efrz.readOnly = false;
		}
	}
	else if(elements.length>1){
		for (var j = 0; j< elements.length; j++) {
			var pidv = formobj.pid[j].options[formobj.pid[j].selectedIndex].value;
			var efrz = formobj.nw[j];
			if(pidv>100) {
				formobj.nw[j].value = "NA";
				efrz.setAttribute("readOnly","true");
			}else {
				formobj.nw.value = "";
				efrz.readOnly = false;
			}
		}
	}
	restrictChangingAllValues(".freez",".tp");
}

function selectPurchaseType() {
	$(':radio:not(:checked)').attr('disabled', false);
	document.getElementById("save_data").disabled=true;
}

function saveData(obj) {
	var formobj = document.getElementById('data_form');
	var ems = "";

	if (document.getElementById("rqty") != null) {
		var esinvno = formobj.pinvno.value.trim();
		var einvdate = formobj.invdate.value;
		var ecvoId = formobj.cvo_id.value.trim();
		var eprtamt = formobj.pr_t_amt.value.trim();
		var enar = formobj.nar.value.trim();
		var elements = document.getElementsByClassName("qtyc");
		
		if (elements.length == 1) {
			var epid = formobj.pid.selectedIndex;
			var epidv = formobj.pid.options[epid].value;

			var erqty = formobj.rqty.value.trim();
			var eur = formobj.ur.value.trim();
			var enw = formobj.nw.value.trim();
			var eramt = formobj.ramt.value.trim();
			/*var eaamt = formobj.aamt.value.trim();
	*/		/*var ebid = formobj.bid.selectedIndex;*/
			var evno = formobj.vno.value.trim();

			/*switch (parseInt(epidv)) {
			case 1:
				pname = "DOM 5 KG SUB CYLINDER";
				break;
			case 2:
				pname = "DOM 14.2 KG SUB CYLINDER";
				break;
			case 3:
				pname = "DOM 14.2 KG NS CYLINDER";
				break;
			case 4:
				pname = "COM 5 KG FTL CYLINDER";
				break;
			case 5:
				pname = "COM 19 KG NDNE CYLINDER";
				break;
			case 6:
				pname = "COM 19 KG CUTTING GAS CYLINDER";
				break;
			case 7:
				pname = "COM 35 KG NDNE CYLINDER";
				break;
			case 8:
				pname = "COM 47.5 KG NDNE CYLINDER";
				break;
			case 9:
				pname = "COM 450 KG SUMO CYLINDERS";
				break;
			case 10:
				pname = "STOVE";
				break;
			case 11:
				pname = "SURAKSHA";
				break;
			case 12:
				pname = "LIGHTER";
				break;
			case 13:
				pname = "KITCHENWARE";
				break;
			}*/
			ems = validatePREntries(esinvno, einvdate, ecvoId, eprtamt, epid,epidv,
					erqty, eur, enw, eramt, evno, enar);
			if (ems.length > 0){
				document.getElementById("dialog-1").innerHTML = ems;
				alertdialogue();
				//alert(ems);
				return;
			}
				
			formobj.ur.value = parseFloat(eur).toFixed(2);
		} else if (elements.length > 1) {
			for (var i = 0; i < elements.length; i++) {
				var epid = formobj.pid[i].selectedIndex;
				var epidv = formobj.pid[i].options[epid].value;

				var erqty = formobj.rqty[i].value.trim();
				var eur = formobj.ur[i].value.trim();
				var enw = formobj.nw[i].value.trim();
				var eramt = formobj.ramt[i].value.trim();
				//var eaamt = formobj.aamt[i].value.trim();
				/*var ebid = formobj.bid[i].selectedIndex;*/
				var evno = formobj.vno[i].value.trim();

				/*switch (parseInt(epidv)) {
				case 1:
					pname = "DOM 5 KG SUB CYLINDER";
					break;
				case 2:
					pname = "DOM 14.2 KG SUB CYLINDER";
					break;
				case 3:
					pname = "DOM 14.2 KG NS CYLINDER";
					break;
				case 4:
					pname = "COM 5 KG FTL CYLINDER";
					break;
				case 5:
					pname = "COM 19 KG NDNE CYLINDER";
					break;
				case 6:
					pname = "COM 19 KG CUTTING GAS CYLINDER";
					break;
				case 7:
					pname = "COM 35 KG NDNE CYLINDER";
					break;
				case 8:
					pname = "COM 47.5 KG NDNE CYLINDER";
					break;
				case 9:
					pname = "COM 450 KG SUMO CYLINDERS";
					break;
				case 10:
					pname = "STOVE";
					break;
				case 11:
					pname = "SURAKSHA";
					break;
				case 12:
					pname = "LIGHTER";
					break;
				case 13:
					pname = "KITCHENWARE";
					break;
				}*/
				ems = validatePREntries(esinvno, einvdate, ecvoId, eprtamt, epid,epidv,
						erqty, eur, enw, eramt, evno, enar);
				if (ems.length > 0){
					document.getElementById("dialog-1").innerHTML = ems;
					alertdialogue();
					//alert(ems);
					return;
				}
				formobj.ur[i].value = parseFloat(eur).toFixed(2);
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
		alertdialogue()
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
	
	var selval = document.getElementById("cvo_id").value;
	if(cvo_data.length>0) {
		for(var z=0; z<cvo_data.length; z++){
			if((cvo_data[z].deleted == 0) && (cvo_data[z].id==selval))  {
				formobj.cvo_cat.value = cvo_data[z].cvo_cat;
			}
		}
	}

	var objId = obj.id;
	document.getElementById(objId).disabled = true;
	formobj.submit();
}

function checkForStock() {
	var ret = "false";
	var formobj = document.getElementById('data_form');	
	if(document.getElementById("pid") != null) {
		var pinvno = document.getElementById('pinvno').value;
		var ele = document.getElementsByClassName("qtyc");
		if(ele.length == 1) {
			var item = formobj.pid.value;
			var qty = formobj.rqty.value.trim();

			var rqty = 0;
			var pqty = 0;			
			var peqty = formobj.cpqty.value.trim();
			var paqty = formobj.apqty.value.trim();
			var estock,astock;
			
			/*switch (parseInt(item)) {
			case 1:
				pname = "DOM 5 KG SUB CYLINDER";
				break;
			case 2:
				pname = "DOM 14.2 KG SUB CYLINDER";
				break;
			case 3:
				pname = "DOM 14.2 KG NS CYLINDER";
				break;
			case 4:
				pname = "COM 5 KG FTL CYLINDER";
				break;
			case 5:
				pname = "COM 19 KG NDNE CYLINDER";
				break;
			case 6:
				pname = "COM 19 KG CUTTING GAS CYLINDER";
				break;
			case 7:
				pname = "COM 35 KG NDNE CYLINDER";
				break;
			case 8:
				pname = "COM 47.5 KG NDNE CYLINDER";
				break;
			case 9:
				pname = "COM 450 KG SUMO CYLINDERS";
				break;
			case 10:
				pname = "STOVE";
				break;
			case 11:
				pname = "SURAKSHA";
				break;
			case 12:
				pname = "LIGHTER";
				break;
			case 13:
				pname = "KITCHENWARE";
				break;
			}
*/
			if((peqty != "") && (paqty == "")){				
				for(var e=0;e<equipment_data.length;e++) {
					if(equipment_data[e].prod_code == parseInt(item)) {
						estock = equipment_data[e].cs_fulls-qty;
						if(estock<0) {
							return "The Equipment  you are trying to return is out of stock.Please Check and add again";
						} 
					}
				}
				pqty = parseInt(peqty);
			}else if((paqty != "") && (peqty == "")){
				for(var e=0; e<cat_arb_types_data.length ; e++) {
					if(cat_arb_types_data[e].id == parseInt(item)) {
						astock = cat_arb_types_data[e].current_stock-qty;
						if(astock<0) {
							return "The BLPG/ARB/NFR item you are trying to return is out of stock.Please Check and add again";
						} 
					}
				}
				pqty = parseInt(paqty);
			}

			for(var z=0;z<page_data.length;z++) {
				if((page_data[z].inv_ref_no == pinvno) && (page_data[z].deleted == 0)) {
					for(var dl=0;dl<page_data[z].detailsList.length;dl++){
						if(page_data[z].detailsList[dl].prod_code==item) {
							rqty += page_data[z].detailsList[dl].rtn_qty;
						}
					}
				}	
			}
			var tqty = +(qty)+ +(rqty);
			if(tqty > pqty) {
				if(rqty == pqty) {
					return pname+" You have already returned all the purchased products of this invoice. Please check again.<br>" +
						" If you want to return again,please delete the previous ones regarding this invoice inorder to maintain the stock correctly.";
				}else if(tqty > pqty) {
					return "Your return quantity exceeds the purchased quantity as you have already returned some of this purchased product ";
				}		
			}
		}else if (ele.length>1){
			ret = "false";
			for(var i=0; i<ele.length; i++){
				var mitem = formobj.pid[i].value;
				var mqty = formobj.rqty[i].value.trim();

				var mrqty = 0;
				var mpqty = 0;				
				var mpeqty = formobj.cpqty[i].value.trim();
				var mpaqty = formobj.apqty[i].value.trim();
				var mestock,mastock;

				if((mpeqty != "") && (mpaqty == "")){
					for(var e=0;e<equipment_data.length;e++) {
						if(equipment_data[e].prod_code == parseInt(mitem)) {
							mestock = equipment_data[e].cs_fulls-mqty;
							if(mestock<0) {
								ret = "The Equipment  you are trying to return is out of stock.Please Check and add again";
							} 
						}
					}
					mpqty = parseInt(mpeqty);
				}else if((mpaqty != "") && (mpeqty == "")){ 					
					for(var e=0;e<cat_arb_types_data.length;e++) {
						if(cat_arb_types_data[e].id == parseInt(mitem)) {
							mastock = cat_arb_types_data[e].current_stock-mqty;
							if(mastock<0) {
								ret = "The BLPG/ARB/NFR item you are trying to return is out of stock.Please Check and add again";
							}
						}
					}
					mpqty = parseInt(mpaqty);
				}
				
				for(var z=0;z<page_data.length;z++) {
					if((page_data[z].inv_ref_no == pinvno) && (page_data[z].deleted == 0)) {
						for(var dl=0;dl<page_data[z].detailsList.length;dl++){
							if(page_data[z].detailsList[dl].prod_code==mitem) {
								mrqty += page_data[z].detailsList[dl].rtn_qty;
							}
						}
					}	
				}
				var mtqty = +(mqty)+ +(mrqty);
				if(mtqty > mpqty) {
					if(mrqty == mpqty) {
						return "You have already returned all the purchased products of this invoice. Please check again.<br>" +
							" If you want to return again,please delete the previous ones regarding this invoice inorder to maintain the stock correctly.";
					}else if(mtqty > mpqty) {
						return "Your return quantity exceeds the purchased quantity as you have already returned some of this purchased product ";
					}		
				}
				
			}
		}
	}
	return ret;
}

/*function deleteItem(iid,prdate,detailsListlen) {
	var flag=validateDayEndForDelete(prdate,dedate);
	var formobj = document.getElementById('m_data_form');
	if(flag) {
		if(detailsListlen>1){
			if (confirm("THIS INVOICE CONTAINS MULTIPLE PRODUCTS.IF YOU DELETE THIS ONE TOTAL INVOICE WILL GET DELETED.ARE YOU SURE YOU WANT TO DELETE?") == true) {				
				formobj.actionId.value = "5573";
				formobj.dataId.value = iid;
				formobj.submit();
			}	
		}else if(detailsListlen==1){
		if (confirm("Are you sure you want to delete?") == true) {
			formobj.actionId.value = "5573";
			formobj.dataId.value = iid;
			formobj.submit();
		}
		}
	}else 
		alert("This record can't be deleted as you have submitted and verified your DAYEND calculations succuessfully... ");
	
}*/




function deleteItem(iid,prdate,detailsListlen){
	var flag=validateDayEndForDelete(prdate,dedate);
	if(flag){
		var formobj = document.getElementById('m_data_form');
		if(detailsListlen>1){
			$("#myDialogText").text("THIS INVOICE CONTAINS MULTIPLE PRODUCTS.IF YOU DELETE THIS ONE TOTAL INVOICE WILL GET DELETED.ARE YOU SURE YOU WANT TO DELETE?");
			confirmDialogue(formobj,5573,iid);
		}else if(detailsListlen==1){
			$("#myDialogText").text("Are You Sure You Want To Delete?");
			confirmDialogue(formobj,5573,iid);	
		}
	}else {
		document.getElementById("dialog-1").innerHTML = "This record can't be deleted as you have submitted and verified your DAYEND calculations succuessfully";
		alertdialogue();
	}
}

var cgstv="";
var igstv="";
function fetchValues() {
	//var hsnval;
	var formobj = document.getElementById('data_form');
	//var enteredInv=formobj.pinvno;
	var pinvno = document.getElementById('pinvno').value;
	if (document.getElementById("ur") != null) {
		var elements = document.getElementsByClassName("qtyc");
		var unitprice;var gstp;var flag="NO";
		var iprodar = new Array();
		var arbprodar = new Array();
      
		if(pinvno != "") {
			if (elements.length == 1) {
				var pide = formobj.pid;
					for (var i=0;i<purchases_data.length;i++) {
						if((purchases_data[i].inv_ref_no == pinvno) && (purchases_data[i].deleted == 0)) {
							flag="YES";
							iprodar.push(purchases_data[i].prod_code);
							cgstv = purchases_data[i].cgst_amount;
							igstv = purchases_data[i].igst_amount;
						}
					}
					
					for (var k=0;k<arbPurchaseData.length;k++) {
						if((arbPurchaseData[k].inv_ref_no == pinvno) && (arbPurchaseData[k].deleted == 0)) {
							flag="YES";
							arbprodar.push(arbPurchaseData[k].arb_code);
							cgstv = arbPurchaseData[k].cgst_amount;
							igstv = arbPurchaseData[k].igst_amount;
						}
					}
					
					if(flag=="YES") {
						if (pide.selectedIndex > 0) {
							if(typeof iprodar !== 'undefined' && pide.value<100 && iprodar.length>0) {
								for (var z=0;z<purchases_data.length;z++) {								
									if((purchases_data[z].prod_code==pide.value) && (purchases_data[z].inv_ref_no == pinvno)) {
										//hsnval = fetchHSNCode(equipment_data,purchases_data[z].prod_code);							
										unitprice = purchases_data[z].unit_price;
										gstp = purchases_data[z].gstp;
										formobj.cpqty.value = purchases_data[z].quantity;
										formobj.apqty.value = "";
									}else if(!iprodar.includes(parseInt(pide.value))) {
										document.getElementById("dialog-1").innerHTML = "The PRODUCT you have selected is not under the provided PURCHASE INVOICE NUMBER";
										alertdialogue();
										//alert("The PRODUCT you have selected is not under the provided PURCHASE INVOICE NUMBER ");
										formobj.ur.value="";
										formobj.gstp.value="";
										return;
									}
								}
							}
							if(typeof arbprodar !== 'undefined' && pide.value>100000 && arbprodar.length>0){
								for (var y=0;y<arbPurchaseData.length;y++) {	
									if(arbPurchaseData[y].arb_code==pide.value && (arbPurchaseData[y].inv_ref_no == pinvno)) {
										unitprice = arbPurchaseData[y].unit_price;
										gstp = arbPurchaseData[y].gstp;
										formobj.apqty.value = arbPurchaseData[y].quantity;
										formobj.cpqty.value = "";
									}else if(!arbprodar.includes(parseInt(pide.value))) {
										document.getElementById("dialog-1").innerHTML = "The PRODUCT you have selected is not under the provided PURCHASE INVOICE NUMBER";
										alertdialogue();
										//alert("The PRODUCT you have selected is not under the provided PURCHASE INVOICE NUMBER ");
										//formobj.hsncode[k].value="";
										formobj.ur.value="";
										formobj.gstp.value="";
										return;
									}
								}
							}else if((!arbprodar.includes(parseInt(pide.value))) && (!iprodar.includes(parseInt(pide.value)))){
								document.getElementById("dialog-1").innerHTML = "The PRODUCT you have selected is not under the provided PURCHASE INVOICE NUMBER";
								alertdialogue();
								//alert("The PRODUCT you have selected is not under the provided PURCHASE INVOICE NUMBER ");
								formobj.ur.value="";
								formobj.gstp.value="";
								formobj.apqty.value = "";
								formobj.cpqty.value = "";
								return;
							}
						}else {
							document.getElementById("dialog-1").innerHTML = "Please Select PRODUCT and Click FETCH UNIT PRICE AND GST";
							alertdialogue();
							//alert("Please Select PRODUCT and Click FETCH UNIT PRICE AND GST");
							return;
						
						}
						 
					}else if(flag == "NO") {
						document.getElementById("dialog-1").innerHTML = "Please enter valid PURCHASE INVOICE NUMBER";
						alertdialogue();
						//alert("Please enter valid PURCHASE INVOICE NUMBER ");
						return;							
					}
					//formobj.hsncode.value = hsnval;
					formobj.ur.value = unitprice;
					formobj.gstp.value = gstp;
			}else if (elements.length > 1) {
				for (var k = 0; k < elements.length; k++) {
					var pide = formobj.pid[k];
					for (var j=0;j<purchases_data.length;j++) {
						if((purchases_data[j].inv_ref_no == pinvno)) {
							flag="YES";
							iprodar.push(purchases_data[j].prod_code);
							cgstv = purchases_data[j].cgst_amount;
							igstv = purchases_data[j].igst_amount;
						}
					}
					for (var m=0;m<arbPurchaseData.length;m++) {
						if((arbPurchaseData[m].inv_ref_no == pinvno)) {
							flag="YES";
							arbprodar.push(arbPurchaseData[m].arb_code);
							cgstv = arbPurchaseData[m].cgst_amount;
							igstv = arbPurchaseData[m].igst_amount;
						}
					}
					
					if(flag=="YES") {
						if (pide.selectedIndex > 0) {
							for (var z=0;z<purchases_data.length;z++) {
								if((purchases_data[z].prod_code==pide.value) && (purchases_data[z].inv_ref_no == pinvno)) {
									//hsnval = fetchHSNCode(equipment_data,purchases_data[z].prod_code);																
									unitprice = purchases_data[z].unit_price;
									gstp = purchases_data[z].gstp;
									formobj.cpqty[k].value = purchases_data[z].quantity;
									formobj.apqty[k].value = "";
								}else if(pide.value<100) {
									if(!iprodar.includes(parseInt(pide.value))) {
										document.getElementById("dialog-1").innerHTML = "The PRODUCT you have selected is not under the provided PURCHASE INVOICE NUMBER ";
										alertdialogue();
										//alert("The PRODUCT you have selected is not under the provided PURCHASE INVOICE NUMBER");
										//formobj.hsncode[k].value="";
										formobj.ur[k].value="";
										formobj.gstp[k].value="";
										return;
									}
								}
							}
							
							if(arbprodar!=""){
								for (var y=0;y<arbPurchaseData.length;y++) {
									if(arbPurchaseData[y].arb_code==pide.value && (arbPurchaseData[y].inv_ref_no == pinvno)) {
										unitprice = arbPurchaseData[y].unit_price;
										gstp = arbPurchaseData[y].gstp;
										formobj.apqty[k].value = arbPurchaseData[y].quantity;
										formobj.cpqty[k].value = "";
									}else if(!arbprodar.includes(parseInt(pide.value))) {
										document.getElementById("dialog-1").innerHTML = "The PRODUCT you have selected is not under the provided PURCHASE INVOICE NUMBER ";
										alertdialogue();
										//alert("The PRODUCT you have selected is not under the provided PURCHASE INVOICE NUMBER ");
										//formobj.hsncode[k].value="";
										formobj.ur[k].value="";
										formobj.gstp[k].value="";
										return;
									}
								}
							}
							 else if((!arbprodar.includes(parseInt(pide.value))) && (!iprodar.includes(parseInt(pide.value)))){
								 	document.getElementById("dialog-1").innerHTML = "The PRODUCT you have selected is not under the provided PURCHASE INVOICE NUMBER ";
									alertdialogue();
								 	//alert("The PRODUCT you have selected is not under the provided PURCHASE INVOICE NUMBER ");
									formobj.ur[k].value="";
									formobj.gstp[k].value="";
									formobj.cpqty[k].value = "";
									formobj.apqty[k].value = "";									
									return;
						  }
						}else {
							document.getElementById("dialog-1").innerHTML = "Please Select PRODUCT and Click FETCH UNIT PRICE AND GST";
							alertdialogue();
							//alert("Please Select PRODUCT and Click FETCH UNIT PRICE AND GST");
							return;
						}

					}
					else if(flag == "NO") {
						document.getElementById("dialog-1").innerHTML = "Please enter valid PURCHASE INVOICE NUMBER ";
						alertdialogue();
						//alert("Please enter valid PURCHASE INVOICE NUMBER ");
						return;							
					}
					//formobj.hsncode[k].value = hsnval;
					formobj.ur[k].value = unitprice;
					formobj.gstp[k].value = gstp;

			}
			}		
		} else {
			document.getElementById("dialog-1").innerHTML = "Please enter valid PURCHASE INVOICE NUMBER ";
			alertdialogue();
			//alert("Please enter PURCHASE INVOICE number");
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

function calculateValues() {
	var cvogstin;
	var cvoreg;
	
	var formobj = document.getElementById('data_form');
	var pinvno = document.getElementById('pinvno').value;
	var ptypecheck = "";
	var pide = formobj.pid;
	var elements = document.getElementsByClassName("qtyc");
	var pidv;
    if(elements.length>1){
    	for(var s=0;s<pide.length;s++) {
    		pidv = pide[s].options[pide[s].selectedIndex].value;
    	}
    }
    else if(elements.length==1)
    	pidv = pide.options[pide.selectedIndex].value;
    	  
	var dstcode = dealergstin.substr(0, 2);
	var cvstcode = "";
	var cvoidval = parseInt(formobj.cvo_id.value);
	if (document.getElementById("pid") != null) {
		var ptypev = formobj.ptype.value;		
		var elements = document.getElementsByClassName("qtyc");
		if(formobj.cvo_id.selectedIndex >0) {
			for(var d=0;d< cvo_data.length;d++){
				if((cvoidval != 0) && (cvoidval == cvo_data[d].id)) {
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

		if((cvoidval != 0)&& (cvoreg==1)) {			
			cvstcode = cvogstin.substr(0, 2);
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
		
		if (elements.length == 1) {
			var pide = formobj.pid;
			if (pide.selectedIndex > 0) {
				var crqty = formobj.rqty.value.trim();
				var cur = formobj.ur.value.trim();
				var cnw = formobj.nw.value.trim();
				var cgstpv = formobj.gstp.value.trim();
				var ccpqty = formobj.cpqty.value.trim();
				var capqty = formobj.apqty.value.trim();
				
				/*switch (parseInt(pide.value)) {
				case 1:
					pname = "DOM 5 KG SUB CYLINDER";
					break;
				case 2:
					pname = "DOM 14.2 KG SUB CYLINDER";
					break;
				case 3:
					pname = "DOM 14.2 KG NS CYLINDER";
					break;
				case 4:
					pname = "COM 5 KG FTL CYLINDER";
					break;
				case 5:
					pname = "COM 19 KG NDNE CYLINDER";
					break;
				case 6:
					pname = "COM 19 KG CUTTING GAS CYLINDER";
					break;
				case 7:
					pname = "COM 35 KG NDNE CYLINDER";
					break;
				case 8:
					pname = "COM 47.5 KG NDNE CYLINDER";
					break;
				case 9:
					pname = "COM 450 KG SUMO CYLINDERS";
					break;
				case 10:
					pname = "STOVE";
					break;
				case 11:
					pname = "SURAKSHA";
					break;
				case 12:
					pname = "LIGHTER";
					break;
				case 13:
					pname = "KITCHENWARE";
					break;
				}*/
				var ems = validatePREntries2(crqty,ccpqty,capqty,cur,cnw,cgstpv,ptypecheck,ptypev,pidv);
				if (ems.length > 0) {
					document.getElementById("dialog-1").innerHTML = ems;
					alertdialogue();
					//alert(ems);
					return;
				}
				if(cnw !="NA"){
					formobj.nw.value = parseFloat(cnw).toFixed(2);
				}
/*				if(pide.value<10) {
					var rndval = round((cur * crqty), 2);
					var gstv = (((cur * crqty) * cgstpv) / 100);
					formobj.ramt.value = rndval;
					formobj.tamt.value = (rndval + +(gstv)).toFixed(2);
				}else{
					var rndval = round((cur * crqty), 2);
					var gstv = (((cur * crqty) * cgstpv) / 100);
					formobj.ramt.value = rndval;
					formobj.tamt.value = (rndval + +(gstv)).toFixed(2);
				}*/
				
				var rndval = round((cur * crqty), 2);
				var gstv = (((cur * crqty) * cgstpv) / 100);
				formobj.ramt.value = rndval;
				formobj.tamt.value = (rndval + +(gstv)).toFixed(2);
				
				if(pide.value<10) {
					if(cvoidval == 0) {
						for (var z=0;z<purchases_data.length;z++) {
							if((purchases_data[z].inv_ref_no == pinvno) && (purchases_data[z].prod_code==pide.value) ){
								if(purchases_data[z].igst_amount !=0)
									ptypecheck = "isp";
								else if(purchases_data[z].cgst_amount !=0)
									ptypecheck = "lp";
							}	
						}	
					}
					if (ptypev == 'lp') {
						formobj.igst.value = "0.00";
						formobj.sgst.value = ((gstv) / 2).toFixed(2);
						formobj.cgst.value = ((gstv) / 2).toFixed(2);
					} else if (ptypev == 'isp') {
						formobj.igst.value = (gstv).toFixed(2);
						formobj.sgst.value = "0.00";
						formobj.cgst.value = "0.00";
					}
				}else if(pide.value >= 18) {
					if (ptypev == 'lp') {
						formobj.igst.value = "0.00";
						formobj.sgst.value = ((gstv) / 2).toFixed(2);
						formobj.cgst.value = ((gstv) / 2).toFixed(2);
					} else if (ptypev == 'isp') {
						formobj.igst.value = (gstv).toFixed(2);
						formobj.sgst.value = "0.00";
						formobj.cgst.value = "0.00";
					}
				}else if(pide.value >= 10) {
					formobj.igst.value = 0;
					formobj.sgst.value = 0;
					formobj.cgst.value = 0;					
				}
				formobj.pr_t_amt.value = round(rndval + +(gstv), 0);
			
			} else {
				document.getElementById("dialog-1").innerHTML = "Please select PRODUCT and then click on CALCULATE.";
				alertdialogue();
				return;
			}
		} else if (elements.length > 1) {
			var totalAmt = 0;
			for (var i = 0; i < elements.length; i++) {
				var pide = formobj.pid[i];
				if (pide.selectedIndex > 0) {
					var crqty = formobj.rqty[i].value.trim();
					var cur = formobj.ur[i].value.trim();
					var cnw = formobj.nw[i].value;
					var cgstpv = formobj.gstp[i].value.trim();
					var ccpqty = formobj.cpqty[i].value.trim();
					var capqty = formobj.apqty[i].value.trim();	
					
					/*switch (parseInt(pide)) {
					case 1:
						pname = "DOM 5 KG SUB CYLINDER";
						break;
					case 2:
						pname = "DOM 14.2 KG SUB CYLINDER";
						break;
					case 3:
						pname = "DOM 14.2 KG NS CYLINDER";
						break;
					case 4:
						pname = "COM 5 KG FTL CYLINDER";
						break;
					case 5:
						pname = "COM 19 KG NDNE CYLINDER";
						break;
					case 6:
						pname = "COM 19 KG CUTTING GAS CYLINDER";
						break;
					case 7:
						pname = "COM 35 KG NDNE CYLINDER";
						break;
					case 8:
						pname = "COM 47.5 KG NDNE CYLINDER";
						break;
					case 9:
						pname = "COM 450 KG SUMO CYLINDERS";
						break;
					case 10:
						pname = "STOVE";
						break;
					case 11:
						pname = "SURAKSHA";
						break;
					case 12:
						pname = "LIGHTER";
						break;
					case 13:
						pname = "KITCHENWARE";
						break;
					}*/
					
					var ems = validatePREntries2(crqty,ccpqty,capqty, cur,cnw,cgstpv,ptypecheck,ptypev);
					if (ems.length > 0) {
						document.getElementById("dialog-1").innerHTML = ems;
						alertdialogue();
						return;
					}
					if(cnw != "NA"){
						formobj.nw[i].value = parseFloat(cnw).toFixed(2);
					}
					/*if(pide.value<10) {
						var rndval = round((cur * cnw), 2);
						var gstv = (((cur * crqty) * cgstpv) / 100);
						formobj.ramt[i].value = rndval;
						formobj.tamt[i].value = (rndval + +(gstv)).toFixed(2);	
					}else {
						var rndval = round((cur * crqty), 2);
						var gstv = (((cur * crqty) * cgstpv) / 100);
						formobj.ramt[i].value = rndval;
						formobj.tamt[i].value = (rndval + +(gstv)).toFixed(2);	
					}*/
					
					var rndval = round((cur * crqty), 2);
					var gstv = (((cur * crqty) * cgstpv) / 100);
					formobj.ramt[i].value = rndval;
					formobj.tamt[i].value = (rndval + +(gstv)).toFixed(2);	
					
					if(pide.value<10) {
						if(cvoidval == 0){
							for (var z=0;z<purchases_data.length;z++) {
								if((purchases_data[z].inv_ref_no == pinvno) && (purchases_data[z].prod_code==pide.value) ){
									if(purchases_data[z].igst_amount !=0)
										ptypev = "isp";
									else if(purchases_data[z].cgst_amount !=0)
										ptypev = "lp";
								}	
							}
						}	
						if (ptypev == 'lp') {
							formobj.igst[i].value = "0.00";
							formobj.sgst[i].value = ((gstv) / 2).toFixed(2);
							formobj.cgst[i].value = ((gstv) / 2).toFixed(2);
						} else if (ptypev == 'isp') {
							formobj.igst[i].value = (gstv).toFixed(2);
							formobj.sgst[i].value = "0.00";
							formobj.cgst[i].value = "0.00";
						}
					}else if(pide.value >= 18) {
						if (ptypev == 'lp') {
							formobj.igst[i].value = "0.00";
							formobj.sgst[i].value = ((gstv) / 2).toFixed(2);
							formobj.cgst[i].value = ((gstv) / 2).toFixed(2);
						} else if (ptypev == 'isp') {
							formobj.igst[i].value = (gstv).toFixed(2);
							formobj.sgst[i].value = "0.00";
							formobj.cgst[i].value = "0.00";
						}
					}else if(pide.value >= 10) {
						formobj.igst[i].value = "0.00";
						formobj.sgst[i].value = "0.00";
						formobj.cgst[i].value = "0.00";					
					}
					totalAmt = round((+totalAmt + +(rndval) + +(gstv)), 0);				
					
				} else {
					document.getElementById("dialog-1").innerHTML = "Please select PRODUCT and then click on CALCULATE.";
					alertdialogue();
					return;
				}
			}
			formobj.pr_t_amt.value = round(totalAmt, 0);
		}

	} else {
		document.getElementById("dialog-1").innerHTML = "Please ADD Purchase Return details and Click CALCULATE";
		alertdialogue();
		return;
	}
	document.getElementById("save_data").disabled = false;

	$(':radio:not(:checked)').attr('disabled', true);
	restrictChangingAllValues(".freez",".tp");
}

function validateProduct2(text) {
	if (page_data.length != 0) {
		for (var i = 0; i < page_data.length; i++) {
			for(var k=0;k<page_data[i].detailsList.length;k++){
				var y=page_data[i].detailsList[k].prod_code;
				if(y<100)
					var spd = fetchProductDetails(cat_cyl_types_data,page_data[i].detailsList[k].prod_code);
				else
					var spd = fetchARBProductDetails(cat_arb_types_data,page_data[i].detailsList[k].prod_code);
				if (spd.localeCompare(text) == 0) {
					return false;
				}
			}
		}
	}
	return true;
}

function validateProduct() {
    var flag=true;
    var prodar = new Array();
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

var irefdate;
function validatePREntries(sinvno, invdate, cvoid, prtamt, pid,pidv,rqty, ur, nw, ramt, vno, nar) {
	var errmsg = "";
	var formobj = document.getElementById('data_form');
	var stockReceiveDate;
	var arbStockReceiveDate;
	
	var invcdate = new Date(invdate);
	invcdate = new Date(invcdate.getFullYear(),invcdate.getMonth(),invcdate.getDate(),0,0,0);
	
	if (!(sinvno.length > 0))
		errmsg = "Please enter PURCHASE INVOICE. <br>";
	var vd = isValidDate(invdate);
	var vfd = ValidateFutureDate(invdate);
	
	if(purchases_data.length>0) {
		for(var z=0; z<purchases_data.length; z++){
			if(purchases_data[z].inv_ref_no==sinvno && purchases_data[z].prod_code==pidv) {
				var stredate = new Date(purchases_data[z].stk_recvd_date);
				stockReceiveDate = new Date(stredate.toDateString());
				stockReceiveDate = new Date(stockReceiveDate.getFullYear(),stockReceiveDate.getMonth(),stockReceiveDate.getDate(),0,0,0);
				irefdate = purchases_data[z].stk_recvd_date;
			}
		}
	}
	
	if(arbPurchaseData.length>0) {
		for(var p=0; p<arbPurchaseData.length; p++){
			if(arbPurchaseData[p].inv_ref_no==sinvno && arbPurchaseData[p].arb_code==pidv) {
				var arbstcdate = new Date(arbPurchaseData[p].stk_recvd_date);
				arbStockReceiveDate = new Date(arbstcdate.toDateString());
				arbStockReceiveDate = new Date(arbStockReceiveDate.getFullYear(),arbStockReceiveDate.getMonth(),arbStockReceiveDate.getDate(),0,0,0);
				irefdate = arbPurchaseData[p].stk_recvd_date;
			}
		}
	}
	
//	var lastTrxnDateCheck = checkforLastTrxnDate(dedate,a_created_date,invdate,trdate);
	if (!(invdate.length > 0))
		errmsg = errmsg + "Please enter INVOICE DATE<br>";
	else if(pidv<100) {
		if(invcdate >= stockReceiveDate) {
			if (vd != "false")
				errmsg = errmsg +vd+"<br>";
			else if(validateDayEndAdd(invdate,dedate))
				errmsg = errmsg+"INVOICE DATE should be after DayEndDate<br>";
			else if(validateEffectiveDateForCVO(invdate,effdate)){
		        errmsg = "INVOICE DATE should be after Effective Date that you have entered While First Time Login<br>";
		        return errmsg;
			}else if (vfd != "false")
				errmsg = errmsg +vfd+"<br>";
/*			else if(lastTrxnDateCheck != "false"){
				return lastTrxnDateCheck;
			}*/
			else
				formobj.refdate.value = irefdate;
		}else
			errmsg = errmsg+"INVOICE DATE should be after STOCK RECEIVED DATE<br>";
	}else if(pidv>100) {
		if(invcdate >= arbStockReceiveDate) {
			if (vd != "false")
				errmsg = errmsg +vd+"<br>";
			else if(validateDayEndAdd(invdate,dedate))
				errmsg = errmsg+"INVOICE DATE should be after DayEndDate<br>";
			else if(validateEffectiveDateForCVO(invdate,effdate)){
		        errmsg = "INVOICE DATE should be after Effective Date that you have entered While First Time Login<br>";
		        return errmsg;
			}else if (vfd != "false")
				errmsg = errmsg +vfd+"<br>";
			/*else if(lastTrxnDateCheck != "false"){
				return lastTrxnDateCheck;
			}*/
			else
				formobj.refdate.value = irefdate;			
		}else
			errmsg = errmsg+"INVOICE DATE should be after STOCK RECEIVED DATE<br>";
	}
	
	if (!(cvoid >= 0))
		errmsg = errmsg + "Please SELECT VENDOR.<br>";
	else {
		var omcId=0;
		var selectedomcName= document.getElementById("cvo_id").value;
		var parseVal=parseInt(selectedomcName);
		for(var d=0;d<purchases_data.length;d++) {
			if(purchases_data[d].inv_ref_no==sinvno &&  purchases_data[d].prod_code==pidv) {
				omcId=purchases_data[d].omc_id;
				break;
			}
		}
		
		for(var a=0;a<arbPurchaseData.length;a++){
			if(arbPurchaseData[a].inv_ref_no==sinvno &&  arbPurchaseData[a].arb_code==pidv) {
				omcId=arbPurchaseData[a].vendor_id;
				cgstv=arbPurchaseData[a].sgst_amount;
					igstv=arbPurchaseData[a].igst_amount;
				break;
			}
		}
		if(omcId!=parseVal)
			errmsg = errmsg + "Please SELECT Valid VENDOR<br>";
	}

	if($("input[type=radio][name=ptype]:checked").length <= 0) {
		errmsg = errmsg + "Please select PURCHASE TYPE<br>";			
	}

	if (!(pid > 0))
		errmsg = errmsg + "Please select any PRODUCT<br>";
	else if (!validateProduct()) 
		errmsg = errmsg + "Please Select Another PRODUCT Which Was not Selected.<br>";
	if (!(rqty.length > 0))
		errmsg = errmsg + "Please enter QUANTITY to be returned.<br>";
	else if (validateNegatives(rqty))
		errmsg = errmsg + "RETURN QUANTITY cant be negative <br>";
	else if (!(checkNumber(rqty)))
		errmsg = errmsg + "Please Enter valid RETURN QUANTITY<br>";
	else if (!((parseFloat(rqty) > 0) && parseFloat(rqty) < 10000))
		errmsg = errmsg + "RETURN QUANTITY must lie in between 0 and 10000 <br>";
	
	if (!(ur.length > 0))
		errmsg = errmsg + "Please enter UNIT PRICE<br>";
	else if(validateDot(ur))
		errmsg = errmsg + "UNIT PRICE must contain atleast one number <br>";
	else if (!validateDecimalNumberMinMax(ur, 0, 10000))
		errmsg = errmsg + "UNIT PRICE Must be Numeric and must lie in between 0 & 10000 <br>";
	

	if (!(nw.length > 0))
		errmsg = errmsg + "Please enter NET WEIGHT.Atleast enter 0 if not available. <br>";	
	else if(validateDot(nw))
		errmsg = errmsg + "NET WEIGHT must contain atleast one number. <br>";
	
	if(pidv<100){
	if (!(isDecimalNumber(nw)))
		errmsg = errmsg + "NET WEIGHT must contain only numerics >=0. <br>";
	else
		formobj.nw.value = round(parseFloat(nw), 2);
	}
	if (!(ramt.length > 0))
		errmsg = errmsg + "AMOUNT can't be empty. <br>";
	else if (!validateDecimalNumberMinMax(ramt, 0, 10000000))
		errmsg = errmsg + "AMOUNT must lie in between 0 & 10000000 .<br>";

	/*if (!(bid > 0))
		errmsg = errmsg + "Please select BANK ACCOUNT<br>";*/

	if (!(vno.length > 0))
		errmsg = errmsg + "Please Enter VEHICLE NUMBER.Enter NA if NOT AVAILABLE. <br>";
	else if(!(vno =="NA" || vno =="na")) 
		if((vno.length != 10))	
			errmsg = errmsg + "VEHICLE NUMBER Must Contain 10 Chars Only.<br>";
		else if (!(validateBothAlphaNumeric(vno)))
			errmsg = errmsg + "VEHICLE NUMBER Must Contain Combination of Alphabets and Numerics.<br>";

	if (!(nar.length > 0))
		errmsg = errmsg + "Please enter NARRATION.Enter NA if NOT APPLICABLE.<br>";

	return errmsg;
}

function validatePREntries2(crqty,ccpqty,capqty,cur,nw,gstp,ptypecheck,ptypev,pidv) {
	var errmsg = "";
	var formobj = document.getElementById('data_form');
	
	if($("input[type=radio][name=ptype]:checked").length <= 0) {
		errmsg = errmsg + "Please select PURCHASE TYPE<br>";			
	}
	if(($("input[type=radio][name=ptype]:checked").length > 0)&&(ptypecheck != ptypev))
		errmsg = errmsg + "The PURCHASE TYPE you have selected might be wrong.Please check it again<br>";
	
	if (!(crqty.length > 0))
		errmsg = errmsg + "Please enter QUANTITY to be returned.<br>";
	else if (validateNegatives(crqty))
		errmsg = errmsg + "RETURN QUANTITY cant be negative <br>";
	else if (!(checkNumber(crqty)))
		errmsg = errmsg + "RETURN QUANTITY must contain only numerics.<br>";
	else if (!((parseFloat(crqty) > 0) && parseFloat(crqty) < 10000))
		errmsg = errmsg + "RETURN QUANTITY must lie in between 0 and 10000.<br>";
	else if(ccpqty != ""){
		if(parseInt(ccpqty) < parseInt(crqty)){
			errmsg = errmsg + "RETURN QUANTITY  must not exceed purchased QUANTITY <br>";
		}
	}else if(capqty != "") {
		if(parseInt(capqty) < parseInt(crqty)) {
			errmsg = errmsg + "RETURN QUANTITY must not exceed purchased QUANTITY <br>";
		}
	}
	
	if (!cur.length > 0)
		errmsg = errmsg + "Please Enter UNIT PRICE. <br>";
	else if (validateDot(cur))
		errmsg = errmsg + "UNIT PRICE must contain atleast one number. <br>";
	else if (!(validateDecimalNumberMinMax(cur, 0, 10000)))
		errmsg = errmsg + "Please Enter valid UNIT PRICE and it must lie in between 0 and 10000<br>";
	if(pidv<100){
	if (!(nw.length > 0))
		errmsg = errmsg + "Please enter NET WEIGHT.Atleast enter 0 if not available. <br>";	
	else if(validateDot(nw))
		errmsg = errmsg + "NET WEIGHT must contain atleast one number. <br>";
	else if (!(isDecimalNumber(nw)))
		errmsg = errmsg + "NET WEIGHT must contain only numerics >=0. <br>";
	
	}
	
	if (!gstp.length > 0)
		errmsg = errmsg + "Please FETCH GST%. <br>";

	return errmsg;
}

function showInvoice(inv_id,si_date){
	popitup(inv_id,si_date)
}

function popitup(inv_id,si_date) { 
	var w = window.open("PopupControlServlet?actionId=994&sitype=6&sid="+inv_id +"&si_date="+si_date,null,"height=400,width=1024,status=yes,toolbar=no,menubar=no,location=no");
	w.print();
	return false;
}
