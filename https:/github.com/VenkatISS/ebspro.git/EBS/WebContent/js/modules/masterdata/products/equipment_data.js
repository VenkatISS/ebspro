//Construct Category Type html
ctdatahtml = "<OPTION VALUE='0'>SELECT</OPTION>";
if(cat_types_data.length>0) {
	for(var z=0; z<cat_types_data.length; z++){
		ctdatahtml=ctdatahtml+"<OPTION VALUE='"+cat_types_data[z].id+"'>"+cat_types_data[z].cat_name+"-"+cat_types_data[z].cat_desc+"</OPTION>";
	}
}

var tbody = document.getElementById('page_data_table_body');
for(var f=page_data.length-1; f>=0; f--){
	if(page_data[f].deleted==0) {
		var tblRow = tbody.insertRow(-1);
		tblRow.style.height="20px";
   		tblRow.align="left";
   		var spd = fetchProductDetails(cat_types_data, page_data[f].prod_code);
	   	var ed = new Date(page_data[f].effective_date);
   		tblRow.innerHTML = "<td>" + spd +  "</td>" + "<td>" + eus[page_data[f].units] + "</td>" + 
   			"<td>" + page_data[f].gstp +  "</td>" + "<td>" + page_data[f].csteh_no +  "</td>" + "<td>" + page_data[f].security_deposit +  "</td>" + 
   			"<td>" + page_data[f].os_fulls +  "</td>" + "<td>" + page_data[f].os_emptys +  "</td>" + 
   			"<td>" + ed.getDate()+"-"+months[ed.getMonth()]+"-"+ed.getFullYear() + "</td>" + 
   			"<td align='center'><img src='images/delete.png' onclick='deleteItem("+page_data[f].id+")'></td>";
	}
};

function addRow() {
	document.getElementById('page_add_table_div').style.display="block";
	document.getElementById('savediv').style.display="inline";
    $("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');

    var trcount = document.getElementById('page_add_table_body').getElementsByTagName('tr').length;
    if(trcount>0) {
    	var trv=document.getElementById('page_add_table_body').getElementsByTagName('tr')[trcount-1];
    	var saddv=trv.getElementsByClassName('sadd');
    	var eaddv=trv.getElementsByClassName('eadd');
    
    	var res=checkRowData(saddv,eaddv);
    	if(res == false){
    		//alert("Please enter all the values in current row and then add next row");
    		document.getElementById("dialog-1").innerHTML = "Please enter all the values in current row and then add next row";
    		alertdialogue();
    		return;
    	}
    }
    
	var ele = document.getElementsByClassName("ic");
	if(ele.length < 4){
		var tbody = document.getElementById('page_add_table_body');
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
	
		a.innerHTML = "<td valign='top' height='4' align='center'><select name='eqepid' id='eqepid' onchange='checkForDeletedProducts(this)' class='form-control input_field select_dropdown ic sadd'>"+ctdatahtml+"</select></td>";
		b.innerHTML = "<td valign='top' height='4' align='center'><SELECT NAME='eus' ID='eus' class='form-control input_field select_dropdown sadd'>"
			+ "<OPTION VALUE='0'>SELECT</OPTION>"
			+ "<OPTION VALUE='1'>NOS</OPTION>"
			+ "<OPTION VALUE='2'>KGS</OPTION>"
			+ "</SELECT></td>";
		c.innerHTML = "<td valign='top' height='4' align='center'><SELECT NAME='egst' ID='egst' class='form-control input_field select_dropdown sadd'>"
			+ "<OPTION VALUE='-1'>SELECT</OPTION>"
			+ "<OPTION VALUE='0'>0</OPTION>"
			+ "<OPTION VALUE='5'>5</OPTION>"
			+ "<OPTION VALUE='12'>12</OPTION>"
			+ "<OPTION VALUE='18'>18</OPTION>"
			+ "<OPTION VALUE='28'>28</OPTION>"
			+ "<OPTION VALUE='NA'>NA</OPTION>"
			+ "</SELECT></td>";
		d.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='ecsteh' id='ecsteh' class='form-control input_field eadd' maxlength='8' placeholder='HSN CODE'></td>";
		e.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='esd' id='esd' class='form-control input_field eadd' value='0.00' maxlength='8'></td>";
		f.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='ef' id='ef' class='form-control input_field eadd' maxlength='4' placeholder='FULLS'></td>";
		g.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='ee' id='ee' class='form-control input_field eadd' maxlength='4' placeholder='EMPTYS'></td>";
		h.innerHTML = "<td valign='top' height='4' align='center'><input type=date name='ed' id='ed' class='form-control input_field eadd' placeholder='DD-MM-YYYY'></td>";
		i.innerHTML = "<td valign='top' height='4' align='center'><img src='images/delete.png' onclick='doRowDelete(this)'></td>";
	}else {
		//alert("Please Save the Records and ADD Again");
		document.getElementById("dialog-1").innerHTML = "Please Save the Records and ADD Again";
		alertdialogue();
	}
}

function checkForDeletedProducts(eqpobj) {
	var formobj = document.getElementById('page_add_form');
	var clsname = eqpobj.className;
	var ele = document.getElementsByClassName(clsname);
	
	if(ele.length==1){
		var invNo="";var editYrN = 1;
		var prod = formobj.eqepid.value.trim();
		if(agency_stock_data) {
			for(var s=0;s<agency_stock_data.length;s++){
				if(agency_stock_data[s].prod_code == prod){
					invNo = agency_stock_data[s].inv_no;
				}
				if(invNo != "NA" && invNo != "") {
					editYrN = 0;
					break;
				}
			}
		}
		
		var prod = formobj.eqepid.value.trim();
		var sprod = document.getElementById("eqepid").options;
		var sunits = document.getElementById("eus").options;
		var sgstp = document.getElementById("egst").options;
		for(var d=0;d<del_equipment_data.length;d++) {
			if(del_equipment_data[d].prod_code == parseInt(prod)) {

				// FOR PRODUCT
				var prodval = del_equipment_data[d].prod_code;
				enableSelect(sprod,sprod.length);
//				formobj.epid.selectedIndex=prodval;
				formobj.eqepid.value=prodval;
				disableSelect(sprod,sprod.length);
				
				// FOR UNITS
				var units = del_equipment_data[d].units;
				enableSelect(sunits,sunits.length);
				if(units==1){
					formobj.eus.selectedIndex=1;
					disableSelect(sunits,sunits.length);
				}else if(units==2){
					formobj.eus.selectedIndex=2;
					disableSelect(sunits,sunits.length);
				}
					
				//FOR GST PERCENT
				var gstp = del_equipment_data[d].gstp;
				enableSelect(sgstp,sgstp.length);
				switch(gstp){
					case "0" :
						formobj.egst.selectedIndex=1;
						disableSelect(sgstp,sgstp.length);
						break;
					case "5" :
						formobj.egst.selectedIndex=2;
						disableSelect(sgstp,sgstp.length);
						break;
					case "12" :
						formobj.egst.selectedIndex=3;
						disableSelect(sgstp,sgstp.length);
						break;
					case "18" :
						formobj.egst.selectedIndex=4;
						disableSelect(sgstp,sgstp.length);
						break;
					case "28" :
						formobj.egst.selectedIndex=5;
						disableSelect(sgstp,sgstp.length);
						break;
					case "NA" :
						formobj.egst.selectedIndex=6;
						disableSelect(sgstp,sgstp.length);
						break;
				}

				// For HSN CODE 					
				var hsn = del_equipment_data[d].csteh_no;
				formobj.ecsteh.value = hsn;
				document.getElementById("ecsteh").setAttribute("readOnly","true");
				
				// For SECURITY DEPOSIT
				var secdep = del_equipment_data[d].security_deposit;
				formobj.esd.value = secdep;

				if(editYrN == 1){
					// For FULLS
					var fulls = del_equipment_data[d].cs_fulls;
					formobj.ef.value = fulls;
					document.getElementById("ef").readOnly = false;
//					document.getElementById("ef").setAttribute("readOnly","true");

					// For EMPTIES
					var empties = del_equipment_data[d].cs_emptys;
					formobj.ee.value = empties;
					document.getElementById("ee").readOnly = false;
//					document.getElementById("ee").setAttribute("readOnly","true");
					
					document.getElementById("ed").readOnly = false;
					
				}else if(editYrN == 0){
					// For FULLS
					var fulls = del_equipment_data[d].cs_fulls;
					formobj.ef.value = fulls;
					document.getElementById("ef").setAttribute("readOnly","true");

					// For EMPTIES
					var empties = del_equipment_data[d].cs_emptys;
					formobj.ee.value = empties;
					document.getElementById("ee").setAttribute("readOnly","true");
					
					var effdate = page_data[d].effective_date;						
					var m = (new Date(effdate).getMonth())+1;
					var y = new Date(effdate).getFullYear();
					var d = new Date(effdate).getDate();

					if(d<10){
						d="0"+d;
					}
					if(m<10){
						m="0"+m;
					}
					
					formobj.ed.value = y+"-"+m+"-"+d;
					document.getElementById("ed").setAttribute("readOnly","true");
					
				}

				break;
			}else {
				// FOR UNITS
//				formobj.eus.selectedIndex=0;
				enableSelect(sunits,sunits.length);
					
				//FOR GST PERCENT
//				formobj.egst.selectedIndex=0;
				//if(!(prod==10 || prod==11 || prod==12))
				enableSelect(sgstp,sgstp.length);
				
				// For HSN CODE
//				formobj.ecsteh.value = "";
				document.getElementById("ecsteh").readOnly = false;
					
				// For FULLS
//				formobj.ef.value = "";
				document.getElementById("ef").readOnly = false;
				
				// For EMPTIES
//				formobj.ee.value = "";
				document.getElementById("ee").readOnly = false;
				
				// For SECURITY DEPOSIT
//				formobj.esd.value = "";
				
				// For EFFECTIVE DATE
//				formobj.ed.value = "";
				
				document.getElementById("ed").readOnly = false;
					
			}
		}
	}else if(ele.length>1){
		for(var e=0;e<ele.length;e++){
			var prod = formobj.eqepid[e].value.trim();
			var sprod = formobj.eqepid[e].options;
			var sunits = formobj.eus[e].options;
			var sgstp = formobj.egst[e].options;
			for(var d=0;d<del_equipment_data.length;d++){
				if(del_equipment_data[d].prod_code == parseInt(prod)) { 
					// FOR PRODUCT
					var prdval = del_equipment_data[d].prod_code;
					enableSelect(sprod,sprod.length);
//					formobj.epid[e].selectedIndex=prdval;
					formobj.eqepid[e].value = prdval;
					disableSelect(sprod,sprod.length);
					
					// FOR UNITS
					var units = del_equipment_data[d].units;
					enableSelect(sunits,sunits.length);					
					if(units==1){
						formobj.eus[e].selectedIndex=1;
						disableSelect(sunits,sunits.length);
					}else if(units==2){
						formobj.eus[e].selectedIndex=2;
						disableSelect(sunits,sunits.length);
					}
					
					//FOR GST PERCENT
					var gstp = del_equipment_data[d].gstp;
					enableSelect(sgstp,sgstp.length);
					switch(gstp){
						case "0" :
							formobj.egst[e].selectedIndex=1;
							disableSelect(sgstp,sgstp.length);
							break;
						case "5" :
							formobj.egst[e].selectedIndex=2;
							disableSelect(sgstp,sgstp.length);
							break;
						case "12" :
							formobj.egst[e].selectedIndex=3;
							disableSelect(sgstp,sgstp.length);
							break;
						case "18" :
							formobj.egst[e].selectedIndex=4;
							disableSelect(sgstp,sgstp.length);
							break;
						case "28" :
							formobj.egst[e].selectedIndex=5;
							disableSelect(sgstp,sgstp.length);
							break;
						case "NA" :
							formobj.egst[e].selectedIndex=6;
							disableSelect(sgstp,sgstp.length);
							break;
					}
					
					// For HSN CODE 
					var hsn = del_equipment_data[d].csteh_no;
					formobj.ecsteh[e].value = hsn;
					formobj.ecsteh[e].setAttribute("readOnly","true");

					
					if(editYrN == 1){
						// For FULLS
						var fulls = del_equipment_data[d].cs_fulls;
						formobj.ef[e].value = fulls;
						formobj.ef[e].readOnly = false;
						
						// For EMPTIES
						var empties = del_equipment_data[d].cs_emptys;
						formobj.ee[e].value = empties;
						formobj.ee[e].readOnly = false;
						
						formobj.ed[e].readOnly = false;
						
					}else if(editYrN == 0){
						// For FULLS
						var fulls = del_equipment_data[d].cs_fulls;
						formobj.ef[e].value = fulls;
						formobj.ef[e].setAttribute("readOnly","true");
						
						// For EMPTIES
						var empties = del_equipment_data[d].cs_emptys;
						formobj.ee[e].value = empties;
						formobj.ee[e].setAttribute("readOnly","true");
						
						var effdate = page_data[d].effective_date;						
						var m = (new Date(effdate).getMonth())+1;
						var y = new Date(effdate).getFullYear();
						var d = new Date(effdate).getDate();

						if(d<10){
							d="0"+d;
						}
						if(m<10){
							m="0"+m;
						}
						
						formobj.ed[e].value = y+"-"+m+"-"+d;
						formobj.ed[e].setAttribute("readOnly","true");
						
					}
					
					break;
				}else {
					// FOR UNITS
//					formobj.eus[e].selectedIndex=0;
					enableSelect(sunits,sunits.length);
					//FOR GST PERCENT
//					formobj.egst[e].selectedIndex=0;
				//	if(!(prod==10 || prod==11 || prod==12))
					enableSelect(sgstp,sgstp.length);
					// For HSN CODE 					
//					formobj.ecsteh[e].value = "";
					formobj.ecsteh[e].readOnly = false;
					// For FULLS
//					formobj.ef[e].value = "";
					formobj.ef[e].readOnly = false;
					// For EMPTIES
//					formobj.ee[e].value = "";
					formobj.ee[e].readOnly = false;
					
					formobj.ed[e].readOnly = false;
					
				}
			}
		}		
	}
}

function saveData(obj){
	var formobj = document.getElementById('page_add_form');
	var ems = "";
	if(document.getElementById("eqepid") != null){
		
		var elements = document.getElementsByClassName("ic");
		if(elements.length==1) {
			var eitem = formobj.eqepid.value;
			var eusitem = formobj.eus.selectedIndex;
			var egst = formobj.egst.selectedIndex;
			var ecsteh = formobj.ecsteh.value.trim();
			var esd = formobj.esd.value;
			var ef = formobj.ef.value.trim();
			var ee = formobj.ee.value.trim();
			var ed = formobj.ed.value.trim();

			formobj.ecsteh.value = formobj.ecsteh.value.trim();
			formobj.esd.value = formobj.esd.value.trim();
			formobj.ef.value = formobj.ef.value.trim();
			formobj.ee.value = formobj.ee.value.trim();
			formobj.ed.value = formobj.ed.value.trim();

			if (checkDateFormate(ed)) {
				var ved = dateConvert(ed);
				formobj.ed.value = ved;
				ed = ved;
			}
			
			ems = validateEntries(eitem,eusitem,egst,ecsteh,esd,ef,ee,ed);
		}else if (elements.length>1){
			for(var i=0; i<elements.length; i++){
				var eitem = formobj.eqepid[i].value;
				var eusitem = formobj.eus[i].selectedIndex;
				var egst = formobj.egst[i].selectedIndex;
				var ecsteh = formobj.ecsteh[i].value.trim();
				var esd = formobj.esd[i].value.trim();
				var ef = formobj.ef[i].value.trim();
				var ee = formobj.ee[i].value.trim();
				var ed = formobj.ed[i].value.trim();

				if (checkDateFormate(ed)) {
					var ved = dateConvert(ed);
					formobj.ed[i].value = ved;
					ed = ved;
				}
				
				formobj.ecsteh[i].value = formobj.ecsteh[i].value.trim();
				formobj.esd[i].value = formobj.esd[i].value.trim();
				formobj.ef[i].value = formobj.ef[i].value.trim();
				formobj.ee[i].value = formobj.ee[i].value.trim();
				formobj.ed[i].value = formobj.ed[i].value.trim();
				
				ems = validateEntries(eitem,eusitem,egst,ecsteh,esd,ef,ee,ed);
				if(ems.length>0)
					break;
			}			
		}
	}else {
		//alert("Please Add Data");
		document.getElementById("dialog-1").innerHTML = "Please Add Data";
		alertdialogue();
		return;
	}
	
	if(ems.length>0) {
		//alert(ems);
		document.getElementById("dialog-1").innerHTML = ems;
		alertdialogue();
		return;
	}

	var objId = obj.id;
	document.getElementById(objId).disabled = true;
	formobj.submit();
}

/*function deleteItem(id) {
	if (confirm("ARE YOU SURE YOU WANT TO DELETE?") == true) {
		var formobj = document.getElementById('page_add_form');
		formobj.actionId.value = "3103";
		formobj.itemId.value = id;
		formobj.submit();
	}
}
*/

function deleteItem(id){
	 $("#myDialogText").text("Are You Sure You Want To Delete?");
	 var formobj = document.getElementById('page_add_form');
	 confirmDialogue(formobj,3103,id);
}

function validateProduct2(text) {
	if (page_data.length != 0) {
		for (var i = 0; i < page_data.length; i++) {
			var spd = fetchProductDetails(cat_types_data,page_data[i].prod_code);
			if (spd.localeCompare(text) == 0) {
				return false;
			}
		}
	}
	return true;
}

function validateProduct() {

	var e2 = document.getElementsByClassName("form-control input_field select_dropdown ic");
	if (e2.length == 1) {
		var e = document.getElementById("eqepid");
		var text = e.options[e.selectedIndex].text;
		var flag = validateProduct2(text);
		return flag;
	} else if (e2.length > 1) {
		var flag = false;
		var h = 1;
		for (i = 0; i < e2.length - 1; i++) {
			var e3 = e2[i].options[e2[i].selectedIndex].text;
			var flag = validateProduct2(e3);
			if (flag) {
				for (j = 0; j < e2.length - 1; j++) {
					var e4 = e2[j + 1].options[e2[j + 1].selectedIndex].text;
					flag = validateProduct2(e4);
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


function validateEntries(item,units,gstp,cstehno,sd,fs,es,ed){
	var formobj = document.getElementById('page_add_form');
	var errmsg = "";

	if (!validateProduct())
		errmsg = errmsg + "The Product has been already selected Please select another Product<br>";
	else {
		if (!(item > 0))
			errmsg = errmsg + "Please Select PRODUCT<br>";

		if (!(units > 0))
			errmsg = errmsg + "Please Select UNITS<br>";

		if (!(gstp > 0))
			errmsg = errmsg + "Please Select GST%<br>";
		else if((item>9 && item!=12) && (gstp!=6)){
			errmsg = errmsg + "Please Select GST% AS NA<br>";
		}else if(item<10) {
			if(gstp==6)
				errmsg = errmsg + "GST% cannot be NA for an EQUIPMENT<br>";
			else if(gstp==1)
				errmsg = errmsg + "GST% cannot be 0% for an EQUIPMENT<br>";
		}
		else if(item==12) {
			if(gstp!=4)
				errmsg = errmsg + "GST% must be 18% for REGULATOR-FTL<br>";
		}
		if (!(cstehno.length > 0))
			errmsg = errmsg + "Please Enter HSN CODE<br>";
/*		else if((item >9 && cstehno != "NA") && (item >9 && cstehno != "na")) {
			errmsg = errmsg + "Please Enter HSN CODE NA For Regulators<br>";
		}*/
		
		else if(!(cstehno == "NA" || cstehno == "na")) {
			if ((!checkNumber(cstehno) ))
				errmsg = errmsg + "HSN CODE Must Be Numeric<br>";
			else if(parseInt(cstehno)==0)
				errmsg = errmsg + "HSN CODE Cannot Be Zero<br>";
			else if(!(item >9) && (cstehno.length != 8)) {
				errmsg = errmsg + "Please Enter valid 8 digits HSNCode<br>";
			}
		}else if(!(item >9) && ((cstehno === "NA") || cstehno == "na")) {
			errmsg = errmsg + "Please Enter valid 8 digits HSNCode<br>";
		}


		if (!(sd.length > 0))
			errmsg = errmsg + "Please Enter SECURITY DEPOSIT<br>";
		else if (validateDot(sd))
			errmsg = errmsg + "SECURITY DEPOSIT Must Contain Atleast One Number. <br>";
		else if (!isDecimalNumber(sd))
			errmsg = errmsg + "SECURITY DEPOSIT Must Contain Only Numerics. <br>";
		else if ((!validateDecimalNumberMinMax(sd, -1, 1000000)))
			errmsg = errmsg
				+ "SECURITY DEPOSIT It Must Be Numeric And <999999.99 And >=0.00<br>";
		else
			formobj.esd.value = round(parseFloat(sd), 2);

		if (!(fs.length > 0))
			errmsg = errmsg + "Please Enter FULLS if not Enter 0.<br>";
		else if ((!validateNumberMinMax(fs, -1, 10000)))
			errmsg = errmsg + "FULLS Must Be Numeric And <=9999 And >=0<br>";


		if (!(es.length > 0))
			errmsg = errmsg + "Please Enter EMPTIES if not Enter 0.<br>";
		else if ((!validateNumberMinMax(es, -1, 10000)))
			errmsg = errmsg + "EMPTIES Must Be Numeric And <=9999 And >=0<br>";

		var vd = isValidDate(ed);
		var vfd = ValidateFutureDate(ed);
		if (!(ed.length > 0))
			errmsg = errmsg + "Please Enter EFFECTIVE DATE<br>";
		else if (vd != "false")
			errmsg = errmsg +"EFFECTIVE"+vd+"<br>";
		else if (vfd != "false") 
			errmsg = errmsg +"EFFECTIVE"+vfd+"<br>";
		else if(validateDayEndAdd(ed,dedate)){
	        errmsg = "EFFECTIVE DATE should be after DayEndDate<br>";
	        return errmsg;
		}
	 }
	 return errmsg;
}
