if(!pinNum) {
	document.getElementById("mRefilpricepopupcontentDiv").style.display="none";
	document.getElementById("displaymRefillPricePopupDiv").style.display="block";
}else {
	document.getElementById("displaymRefillPricePopupDiv").style.display="none";
	document.getElementById("mRefilpricepopupcontentDiv").style.display="block";
}

//Construct Category Type html
ppctdatahtml = "<OPTION VALUE='0'>SELECT</OPTION>";
if(cat_types_data.length>0) {
	for(var z=0; z<cat_types_data.length; z++){
		for(var y=0; y<equipment_data.length; y++){
			if(equipment_data[y].prod_code == cat_types_data[z].id) {
				if(cat_types_data[z].id<10) {
					ppctdatahtml = ppctdatahtml+"<OPTION VALUE='"+cat_types_data[z].id+"'>"
										+cat_types_data[z].cat_name+"-"+cat_types_data[z].cat_desc+"</OPTION>";
					break;
				}
			}
		}
	}
}

//Construct Month html
mvdatahtml = "<OPTION VALUE='-1'>SELECT</OPTION>";
for(var z=0; z<months.length; z++){
	mvdatahtml=mvdatahtml+"<OPTION VALUE='"+z+"'>"+months[z]+"</OPTION>";
}
yvdatahtml = "<OPTION VALUE='-1'>SELECT</OPTION>";
for(var z=0; z<years.length; z++){
	yvdatahtml=yvdatahtml+"<OPTION VALUE='"+z+"'>"+years[z]+"</OPTION>";
}

var checkNum=checkDisplay;
if(checkNum=="1"){
	document.getElementById("myModalmRefilpricepopupPin").style="none";
	var tbody = document.getElementById('mrefillpricepopup_page_data_table_body');
	for(var f=refill_prices_data.length-1; f>=0; f--){
		var tblRow = tbody.insertRow(-1);
		tblRow.style.height="20px";
		tblRow.align="left";
		tblRow.id="or";
		var spd = fetchProductDetails(cat_types_data, refill_prices_data[f].prod_code);
		tblRow.innerHTML = "<tr>"+
			"<td>"+ spd +"</td>"+
			"<td>"+ refill_prices_data[f].rsp +"</td>"+
			"<td>"+ refill_prices_data[f].base_price +"</td>"+
			"<td>"+ refill_prices_data[f].sgst_price +"</td>"+
			"<td>"+ refill_prices_data[f].cgst_price +"</td>"+
			"<td>"+ refill_prices_data[f].offer_price +"</td>"+
			"<td>"+ months[refill_prices_data[f].montha] +"</td>"+
			"<td>"+ years[refill_prices_data[f].yeara] +"</td>"+
		//	"<td valign='top' height='4' align='center'><img src='images/delete.png' onclick='deletemRefillPricepopupItem("+refill_prices_data[f].id+")'></td>"+
			"</tr>";
	} 
}else {
	var tbody = document.getElementById('mrefillpricepopup_page_data_table_body');
	for(var f=refill_prices_data.length-1; f>=0; f--){
		var tblRow = tbody.insertRow(-1);
		tblRow.style.height="20px";
		tblRow.align="left";
		tblRow.id="or";
		var spd = fetchProductDetails(cat_types_data, refill_prices_data[f].prod_code);
		tblRow.innerHTML = "<tr>"+
			"<td>"+ spd +"</td>"+
			"<td>"+ refill_prices_data[f].rsp +"</td>"+
			"<td>"+ refill_prices_data[f].base_price +"</td>"+
			"<td>"+ refill_prices_data[f].sgst_price +"</td>"+
			"<td>"+ refill_prices_data[f].cgst_price +"</td>"+
			"<td>"+ refill_prices_data[f].offer_price +"</td>"+
			"<td>"+ months[refill_prices_data[f].montha] +"</td>"+
			"<td>"+ years[refill_prices_data[f].yeara] +"</td>"+
		//	"<td valign='top' height='4' align='center'><img src='images/delete.png' onclick='deletemRefillPricepopupItem("+refill_prices_data[f].id+")'></td>"+
			"</tr>";
	}
}


function mrefillPricePopupaddRow() {
	document.getElementById('mrefillOfpNote').style.display="block";
	
	var eqdataLen=equipment_data.length;
    $("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');

	if (eqdataLen<1) {
		document.getElementById("dialog-1").innerHTML ="Please enter PRODUCT in EQUIPMENT MASTER";
		alertdialogueWithCollapse();
		// alert("Please enter PRODUCT in EQUIPMENT MASTER");
	}
	else {
		document.getElementById("save_mrefillpricepopup_data").disabled = true;
		document.getElementById('savemrefillpricepopupdiv').style.display="inline";
	
		var x = document.getElementById('mymRefillPricePopupDIV');
		if (x.style.display === 'none')
			x.style.display = 'block';
    
		var trcount = document.getElementById('mrefillpricepopup_page_add_table_body').getElementsByTagName('tr').length;
		if(trcount>0){
			var trv=document.getElementById('mrefillpricepopup_page_add_table_body').getElementsByTagName('tr')[trcount-1];
			var saddv=trv.getElementsByClassName('sadd');
			var eaddv=trv.getElementsByClassName('eadd');
    
			var res=checkRowData(saddv,eaddv);
			if(res == false){
				//alert("Please enter all the values in current row,calculate and then add next row");
				document.getElementById("dialog-1").innerHTML ="Please enter all the values in current row,calculate and then add next row";
				alertdialogue();
				return;
			}		
		}
    
		var ele = document.getElementsByClassName("rspc");
		if(ele.length < 4){
			var tbody = document.getElementById('mrefillpricepopup_page_add_table_body');
			var newRow = tbody.insertRow(-1);
			newRow.id = "nr";

			var a = newRow.insertCell(0);
			var b = newRow.insertCell(1);
			var c = newRow.insertCell(2);
			var d = newRow.insertCell(3);
			var e = newRow.insertCell(4);
			var f = newRow.insertCell(5);
			var g = newRow.insertCell(6);
			var h = newRow.insertCell(7);
			var i = newRow.insertCell(8);
			
			a.innerHTML = "<td valign='top' height='4' align='center'><select name='pid' class='form-control input_field select_dropdown ic sadd freez' id='pid'>"
				+ ppctdatahtml + "</select></td>";
			b.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='rsp' id='rsp' placeholder='RSP' class='form-control input_field rspc freez eadd' maxlength='8'></td>";
			c.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='bp' id='bp' placeholder='BASIC PRICE' class='form-control input_field eadd' readonly='readonly' style='background-color:#F3F3F3;'></td>";
			d.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='sgst' id='sgst' placeholder='SGST' class='form-control input_field eadd' readonly='readonly' style='background-color:#F3F3F3;'></td>";
			e.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='cgst' id='cgst' placeholder='CGST' class='form-control input_field eadd' readonly='readonly' style='background-color:#F3F3F3;'></td>";
			f.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='ofp' id='ofp' placeholder='OFFER PRICE' class='form-control input_field ofpc freez eadd' maxlength='8'><input type=hidden name='ofpBp' id='ofpBp'></td>";			
			g.innerHTML = "<td valign='top' height='4' align='center'><select name='mon' id='mon' class='form-control input_field select_dropdown sadd'>"
				+ mvdatahtml + "</SELECT></td>";
			h.innerHTML = "<td valign='top' height='4' align='center'><select name='yr' id='yr' class='form-control input_field select_dropdown sadd'>"
				+ yvdatahtml + "</SELECT></td>";
			i.innerHTML = "<td valign='top' height='4' align='center'><img src='images/delete.png' onclick='doRowDelete(this)'></td>";
		}else{
			//alert("Please Save the Records and ADD Again");
			document.getElementById("dialog-1").innerHTML ="Please Save the Records and ADD Again";
			alertdialogue();
			document.getElementById("save_mrefillpricepopup_data").disabled = false;
		}	
	}
}
function savemRefillpricepopupData(obj) {
	var formobj = document.getElementById('mrefiilpricepopup_page_data_form');
	var ems = "";

	if (document.getElementById("pid") != null) {

		var elements = document.getElementsByClassName("rspc");
		if (elements.length == 1) {
			var epid = formobj.pid.selectedIndex;
			var ersp = formobj.rsp.value.trim();
			var esgst = formobj.sgst.value;
			var ecgst = formobj.cgst.value;
			var ebp = formobj.bp.value;
			var emon = formobj.mon.selectedIndex;
			var eyr = formobj.yr.selectedIndex;
			var eyrv = formobj.yr.options[eyr].value;
			var pidv = formobj.pid.value;
			var gstpercent=findmRefillPricePopupGSTPercent(ersp, pidv);
			var eofp = formobj.ofp.value.trim();
			
			ems = validatemRefillpricePopupEntries(epid, ersp, esgst, ecgst,gstpercent, ebp, emon,eyr,eyrv,eofp);
			
		} else if (elements.length > 1) {
			for (var i = 0; i < elements.length; i++) {
				var epid = formobj.pid[i].selectedIndex;
				var ersp = formobj.rsp[i].value.trim();
				var esgst = formobj.sgst[i].value;
				var ecgst = formobj.cgst[i].value;
				var ebp = formobj.bp[i].value;
				var emon = formobj.mon[i].selectedIndex;
				var eyr = formobj.yr[i].selectedIndex;
				var eyrv = formobj.yr[i].options[eyr].value;
				var pidv = formobj.pid[i].value;
				var gstpercent=findmRefillPricePopupGSTPercent(ersp, pidv);
				var eofp = formobj.ofp[i].value.trim();
				
				ems = validatemRefillpricePopupEntries(epid,ersp,esgst,ecgst,gstpercent,ebp,emon,eyr,eyrv,eofp);
				if (ems.length > 0)
					break;
			}
		}
	} else {
		document.getElementById("dialog-1").innerHTML ="PLEASE ADD DATA";
		alertdialogue();
		//alert("PLEASE ADD DATA");
		return;
	}

	if (ems.length > 0) {
		document.getElementById("dialog-1").innerHTML =ems;
		alertdialogue();
		//alert(ems);
		return;
	}
	
	var objId = obj.id;
	document.getElementById(objId).disabled = true;
	formobj.submit();
}

/*function deletemRefillPricepopupItem(id) {

	if (confirm("Are you sure you want to delete?") == true) {
		var formobj = document.getElementById('mrefiilpricepopup_page_data_form');
		formobj.actionId.value = "3203";
		formobj.itemId.value = id;
		formobj.submit();
	}
}*/
function deletemRefillPricepopupItem(id){
	 $("#myDialogText").text("Are You Sure You Want To Delete?");
	 var formobj = document.getElementById('mrefiilpricepopup_page_data_form');
	 confirmDialogue(formobj,3203,id);
}

function calculatemrefillpriceValues() {
	var formobj = document.getElementById('mrefiilpricepopup_page_data_form');
	if (document.getElementById("rsp") != null) {
		var elements = document.getElementsByClassName("rspc");
		if (elements.length == 1) {
			var rspv = formobj.rsp.value.trim();
			var ofpv = formobj.ofp.value.trim();
			var epid = formobj.pid.selectedIndex;
			
			var ems = validatemRefillPricePopupEntries2(epid,rspv,ofpv);
			if (ems.length > 0) {
				//alert(ems);
				document.getElementById("dialog-1").innerHTML = ems;
				alertdialogue();
				return;
			}
			var pid = formobj.pid.value;
			var gstp = calculatemRefillPricePopupGSTPrice(rspv, pid);
			var gstav = (Math.round(gstp * 100)) / 100;
			formobj.sgst.value = gstav;
			formobj.cgst.value = gstav;
			formobj.rsp.value = round(parseFloat(rspv), 2);
			formobj.bp.value = (+rspv - (+(2 * gstav))).toFixed(2);
			
			var ofpGstp = calculatemRefillPricePopupGSTPrice(ofpv, pid);
			var ofpGstav = (Math.round(ofpGstp * 100)) / 100;
			formobj.ofp.value = round(parseFloat(ofpv), 2);
			formobj.ofpBp.value = (+ofpv - (+(2 * ofpGstav))).toFixed(2);
	
		} else if (elements.length > 1) {
			for (var i = 0; i < elements.length; i++) {
				var rspv = formobj.rsp[i].value.trim();
				var ofpv = formobj.ofp[i].value.trim();
				var epid = formobj.pid[i].selectedIndex;
				
				var ems = validatemRefillPricePopupEntries2(epid,rspv,ofpv);
				if (ems.length > 0) {
					document.getElementById("dialog-1").innerHTML = ems;
					alertdialogue();
					//alert(ems);
					return;
				}

				var pid = formobj.pid[i].value;					
				var e = document.getElementById("pid");
				var text = e.options[e.selectedIndex].text;
				
				var gstp = calculatemRefillPricePopupGSTPrice(rspv, pid);
				var gstav = (Math.round(gstp * 100)) / 100;
				formobj.sgst[i].value = gstav;
				formobj.cgst[i].value = gstav;
				formobj.rsp[i].value = round(parseFloat(rspv), 2);
				formobj.bp[i].value = (+rspv - (+(2 * gstav))).toFixed(2);
				
				var ofpGstp = calculatemRefillPricePopupGSTPrice(ofpv, pid);
				var ofpGstav = (Math.round(ofpGstp * 100)) / 100;
				formobj.ofp[i].value = round(parseFloat(ofpv), 2);
				formobj.ofpBp[i].value = (+ofpv - (+(2 * ofpGstav))).toFixed(2);
				
			}
		}

	} else {
		document.getElementById("dialog-1").innerHTML = "PLEASE ADD REFILL PRICE DATA AND CLICK CALCULATE";
		alertdialogue();
		//alert("PLEASE ADD REFILL PRICE DATA AND CLICK CALCULATE");
		return;
	}
	document.getElementById("save_mrefillpricepopup_data").disabled = false;	
	restrictChangingAllValues(".freez");
}


function validatemRefillpricePopupEntries(prod, rsp, sgst, cgst,gstpercent, bp, mon, yr,yrv, ofp) {
	var errmsg = "";
	var formobj = document.getElementById('mrefiilpricepopup_page_data_form');

	if (!(prod > 0))
		errmsg = "Please Select The PRODUCT <br>";
	else if (!validateRefillPricePopupProduct())
		errmsg = errmsg +"The PRICE has already been fixed for the product you have selected." ;	

	if (!(rsp.length > 0))
		errmsg = errmsg + "Please enter RSP.<br>";
	else if (validateDot(rsp))
		errmsg = errmsg + "RSP must contain atleast one number. <br>";
	else if (!(isDecimalNumber(rsp)))
		errmsg = errmsg + "RSP must contain only numerics.<br>";
	else if (!((parseFloat(rsp) > 0) && (parseFloat(rsp) <= 99999.99))) {
		errmsg = errmsg+ "RSP must be less than 1 lakh and greater than 0.<br>";
		errmsg = errmsg+ "please enter RSP correctly and then CALCULATE again.<br>";
	} else
		formobj.rsp.value = round(parseFloat(rsp), 2);

	if (!(ofp.length > 0))
		errmsg = errmsg + "Please enter OFFER PRICE.<br>";
	else if (validateDot(ofp))
		errmsg = errmsg + "OFFER PRICE must contain atleast one number. <br>";
	else if (!(isDecimalNumber(ofp)))
		errmsg = errmsg + "OFFER PRICE must contain only numerics.<br>";
	else if (!(parseFloat(ofp) > 0)) {
		errmsg = errmsg+ "OFFER PRICE must be greater than 0.<br>";
	}else if ( (errmsg == "") && (!(parseFloat(ofp) <= parseFloat(rsp)))) {
		errmsg = errmsg+ "OFFER PRICE must be less than or equal to RSP.<br>";
	}else if( (errmsg == "") && (parseFloat(ofp) <= parseFloat(rsp)) ) {
		var eofp = parseFloat(ofp);
		var ersp = parseFloat(rsp);
		var maxcutOffVal = ( ersp * (75/100) ) ;
		if(eofp < maxcutOffVal) {
			errmsg = errmsg+ "OFFER PRICE must be atleast 75% of RSP.<br>";
		}
	}else
		formobj.ofp.value = round(parseFloat(ofp), 2);
	
	
	if (!(sgst.length > 0))
		errmsg = errmsg + "Please calculate SGST.<br>";
	else if (!(isDecimalNumber(sgst)))
		errmsg = errmsg + "SGST must not contain alphabets.<br>";
	else if(parseInt(gstpercent)!=0 && parseFloat(sgst)==0) 
		errmsg = errmsg + "Invalid SGST Amount For a non 0% GST.<br>";
	else if (!(validateDecimalNumberMinMax(cgst, -1, 100000))) 
		errmsg = errmsg + "SGST must be less than 1,00,000 and greater than 0.<br>";

	if (!(isDecimalNumber(cgst)))
		errmsg = errmsg + "CGST must not contain alphabets.<br>";
	else if(parseInt(gstpercent)!=0 && parseFloat(cgst)==0) 
		errmsg = errmsg + "Invalid CGST Amount For a non 0% GST.<br>";
	else if (!(validateDecimalNumberMinMax(cgst, -1, 100000))) 
		errmsg = errmsg + "CGST must be less than 1,00,000 and greater than 0.<br>";

	if (!(isDecimalNumber(bp)))
		errmsg = errmsg + "BASIC PRICE must not contain alphabets.<br>";
	else if (!(validateDecimalNumberMinMax(bp, 0, 100000))) 
		errmsg = errmsg + "BASIC PRICE must be less than 1,00,000 and greater than 0.<br>";
	
	var date= new Date;
	var curmonth=date.getMonth();
	var curyear=date.getFullYear();

	if (!(parseInt(mon) > 0))
		errmsg = errmsg + "Please select MONTH.<br>";
	else if((parseInt(mon)>curmonth+1) && (parseInt(years[yrv])>=curyear))
		errmsg = errmsg + "MONTH can't be Future month<br>";
	else if((parseInt(years[yrv])>curyear))
		errmsg = errmsg + "YEAR can't be Future year<br>";

	if (!(parseInt(yr) > 0))
		errmsg = errmsg + "Please select YEAR.<br>";
/*	else if (parseInt(yr) == 1 && parseInt(mon) < 7)
		errmsg = errmsg+ "Select MONTH and YEAR from JULY,2017 onwards .<br>";
*/
	else {
		var yrval = years[yr-1];
		var cdate = new Date(parseInt(yrval),parseInt(mon-1),01,0,0,0);
/*		var ddate = new Date(dedate.getFullYear(),dedate.getMonth(),01,0,0,0);
		if(cdate < ddate) {
			errmsg = errmsg+ "MONTH and YEAR should be after DayEnd Date .<br>";
		}
*/
		
/*		var edate = new Date(effdate.getFullYear(),effdate.getMonth(),01,0,0,0);
		if(cdate < edate) {
			errmsg = errmsg+ "MONTH and YEAR is acceptable from the EFFECTIVE Date provided.<br>";
		}
*/
		
		var pcode = document.getElementById("pid").value.trim();
		var plongdate = 0;
		for(var e=0;e<equipment_data.length;e++) {
			if(equipment_data[e].prod_code == pcode) {
				plongdate = parseFloat(equipment_data[e].effective_date);
			}
		}
		if(plongdate !=0 ) {
			var pdate = new Date((new Date(plongdate)).getFullYear(),(new Date(plongdate)).getMonth(),01,0,0,0);
			if(cdate < pdate) {
				errmsg = errmsg+ "MONTH and YEAR is acceptable from the Effective Date provided for this product in its Master.<br>";
			}
		}else {
			var edate = new Date(effdate.getFullYear(),effdate.getMonth(),01,0,0,0);
			if(cdate < edate) {
				errmsg = errmsg+ "MONTH and YEAR is acceptable from the EFFECTIVE Date provided.<br>";
			}
		}		
	}
	return errmsg;
}


function validatemRefillPricePopupEntries2(pid,rsp,ofp) {
	var errmsg = "";
	var formobj = document.getElementById('mrefiilpricepopup_page_data_form');

	if (!(pid > 0))
		errmsg = "Please Select The PRODUCT <br>";
	else if (!validateRefillPricePopupProduct()){
		errmsg = "The PRICE has already been fixed for the product you have selected.";
		return errmsg;
	}
		
	else if (!(rsp.length > 0))
		errmsg = errmsg + "Please enter RSP.<br>";
	else if (validateDot(rsp))
		errmsg = errmsg + "RSP must contain atleast one number. <br>";
	else if (!(isDecimalNumber(rsp)))
		errmsg = errmsg + "Please Enter Valid RSP.<br>";
	else if (!((parseFloat(rsp) > 0) && (parseFloat(rsp) <= 99999.99))) {
		errmsg = errmsg + "RSP must be less than 1 lakh and greater than 0.<br>";
		errmsg = errmsg + "please enter RSP correctly and then CALCULATE again.<br>";
	}
	
	else if (!(ofp.length > 0))
		errmsg = errmsg + "Please enter OFFER PRICE.<br>";
	else if (validateDot(ofp))
		errmsg = errmsg + "OFFER PRICE must contain atleast one number. <br>";
	else if (!(isDecimalNumber(ofp)))
		errmsg = errmsg + "OFFER PRICE must contain only numerics.<br>";
	else if (!(parseFloat(ofp) > 0)) {
		errmsg = errmsg+ "OFFER PRICE must be greater than 0.<br>";
	}else if ( (errmsg == "") && (!(parseFloat(ofp) <= parseFloat(rsp)))) {
		errmsg = errmsg+ "OFFER PRICE must be less than or equal to RSP.<br>";
	}else if( (errmsg == "") && (parseFloat(ofp) <= parseFloat(rsp)) ) {
		var eofp = parseFloat(ofp);
		var ersp = parseFloat(rsp);
		var maxcutOffVal = ( ersp * (75/100) ) ;
		if(eofp < maxcutOffVal) {
			errmsg = errmsg+ "OFFER PRICE must be atleast 75% of RSP.<br>";
		}
	}else {
		formobj.rsp.value = round(parseFloat(rsp), 2);
		formobj.ofp.value = round(parseFloat(ofp), 2);
	}

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

function calculatemRefillPricePopupGSTPrice(ebp, spc) {
	var cv = 0;
	for (var z = 0; z < equipment_data.length; z++) {
		if (equipment_data[z].prod_code == spc) {
			var vatv = equipment_data[z].gstp;
			cv = ((ebp * vatv) / (100 + +vatv));
			cv = cv / 2;
		}
	}
	return cv;
}

function findmRefillPricePopupGSTPercent(ebp, spc) {
	var cv = 0;
	for (var z = 0; z < equipment_data.length; z++) {
		if (equipment_data[z].prod_code == spc) {
			var cv = equipment_data[z].gstp;
		}
	}
	return cv;
}
function validateRefillPricePopupProduct2(text) {
	if (refill_prices_data.length != 0) {
		for (var i = 0; i < refill_prices_data.length; i++) {
			var spd = fetchProductDetails(cat_types_data,refill_prices_data[i].prod_code);
			if (spd.localeCompare(text) == 0) {
				return false;
			}
		}
	}
	return true;
}

function validateRefillPricePopupProduct() {

	var e2 = document.getElementsByClassName("form-control input_field select_dropdown ic");
	if (e2.length == 1) {
		var e = document.getElementById("pid");
		var text = e.options[e.selectedIndex].text;
		var flag = validateRefillPricePopupProduct2(text);
		return flag;
	} else if (e2.length > 1) {
		var flag = false;
		var h = 1;
		for (i = 0; i < e2.length - 1; i++) {
			var e3 = e2[i].options[e2[i].selectedIndex].text;
			var flag = validateRefillPricePopupProduct2(e3);
			if (flag) {
				for (j = 0; j < e2.length - 1; j++) {
					var e4 = e2[j + 1].options[e2[j + 1].selectedIndex].text;
					flag = validateRefillPricePopupProduct2(e4);
					var k = 0;
					if (flag) {
						for (k; k < h && k < e2.length - 1; k++) {
							var e5 = e2[k].options[e2[k].selectedIndex].text;
							if (e5.localeCompare(e4) == 0) {
								flag = false;
								return flag;
							}
						}
						h = h + 1;
					}
					if (!flag)
						return flag;
				}
			}
			return flag;
		}

	}
}

/*function submitmRefillPricePopupPinNumber(formobj) {
    var pinNO = formobj.pinNO.value.trim();
    var enteredPin = formobj.enteredPin.value.trim();
    var ems="";
    if(!(enteredPin.length>0))
            ems= ems+"Please Enter PIN NUMBER<br>";
    else if(!validatePinNumber(enteredPin,4,4))
            ems = ems+"PIN NUMBER Must Be Valid  4 DIGIT Number <br>";
    else if(enteredPin!==pinNO)
            ems= ems+"Please Enter Valid PIN NUMBER<br>";
    else if(enteredPin != formobj.enteredPin.value)
    		ems= ems+ "No spaces are allowed in pin Number\n";
    else if(enteredPin==pinNO){
            document.getElementById('myModalmRefilpricepopupPin').style="none";
            document.getElementById('pageDiv').style="block";
    }
    if (ems.length > 0) {
    	document.getElementById("dialog-1").innerHTML = ems;
		alertdialogue();    
		return;
    }
}*/

function submitmRefillPricePopupPinNumber() {
	var pinNO = document.getElementById("pinNO").value.trim();
	var enteredPin = document.getElementById("enteredPin").value.trim();
	var ems="";
	if(!(enteredPin.length>0))
		ems= ems+"Please Enter PIN NUMBER<br>";
	else if(!validatePinNumber(enteredPin,4,4))
		ems = ems+"PIN NUMBER Must Be Valid  4 DIGIT Number <br>";
	else if(enteredPin !== pinNO)
		ems= ems+"Please Enter Valid PIN NUMBER<br>";
	else if(enteredPin != document.getElementById("enteredPin").value)
		ems= ems+ "No spaces are allowed in pin Number\n";
	else if(enteredPin == pinNO){
		document.getElementById('myModalmRefilpricepopupPin').style.display="none";
	//	document.getElementById('pageDiv').style.display="block";
	}
	if (ems.length > 0) {
		document.getElementById("dialog-1").innerHTML = ems;
		alertdialogue();    
		return;
	}
}

