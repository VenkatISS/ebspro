//Construct Category Type html
arbdatahtml = "<OPTION VALUE='0'>SELECT</OPTION>";
if(arb_types_list.length>0) {
	for(var z=0; z<arb_types_list.length; z++){
		arbdatahtml=arbdatahtml+"<OPTION VALUE='"+arb_types_list[z].id+"'>"+arb_types_list[z].cat_desc+"</OPTION>";
	}
}

var tbody = document.getElementById('marb_page_data_table_body');
for(var f=arb_data.length-1; f>=0; f--){
	if(arb_data[f].deleted == 0) {
		var tblRow = tbody.insertRow(-1);
		tblRow.style.height="20px";
		tblRow.align="left";
		var spd = fetchProductName(arb_types_list, arb_data[f].prod_code);
		var ed = new Date(arb_data[f].effective_date);
		tblRow.innerHTML = "<td>" + spd +  "</td>" + "<td>" + arb_data[f].prod_brand + "</td>" + "<td>" + arb_data[f].prod_name + "</td>" + 
			"<td>" + arb_data[f].csteh_no + "<td>" + eus[arb_data[f].units] + "</td>" + "<td>" + arb_data[f].gstp +  "</td>" +   
			"</td>" + "<td>" + arb_data[f].prod_mrp +  "</td>" + "<td>" + arb_data[f].opening_stock +  "</td>" + 
			"<td>" + ed.getDate()+"-"+months[ed.getMonth()]+"-"+ed.getFullYear() + "</td>" /*+ 
			"<td align='center'><img src='images/delete.png' onclick='deletemARBItem("+arb_data[f].id+")'></td>"*/; 
	}
};

function mARBaddRow() {
	document.getElementById('marb_page_add_table_div').style.display="block";
	document.getElementById('marbSaveData').style.display="inline";
    $("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');

    var trcount = document.getElementById('marb_page_add_table_body').getElementsByTagName('tr').length;
    if(trcount>0){
    	var trv=document.getElementById('marb_page_add_table_body').getElementsByTagName('tr')[trcount-1];
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
	if(ele.length < 4) {
		var tbody = document.getElementById('marb_page_add_table_body');
		var newRow = tbody.insertRow(-1);

		var a = newRow.insertCell(0); 
		var b = newRow.insertCell(1); 
		var c = newRow.insertCell(2);
		var d = newRow.insertCell(3);   // from 2015-dec 3rd till 2016-oct last  
		var e = newRow.insertCell(4);
		var f = newRow.insertCell(5);
		var g = newRow.insertCell(6);
		var h = newRow.insertCell(7);
		var i = newRow.insertCell(8);
		var j = newRow.insertCell(9);
		
		a.innerHTML = "<td><select name='pid' id='pid' onchange='checkFormARBDeletedItems()' class='form-control input_field select_dropdown ic sadd'>"+arbdatahtml+"</select></td>";
		b.innerHTML = "<td><input type=text name='pbrand' id='pbrand' maxlength='20' onchange='checkFormARBDeletedItems()' class='form-control input_field eadd brand' placeholder='PRODUCT BRAND'></td>";
		c.innerHTML = "<td><input type=text name='pname' id='pname' maxlength='20' onchange='checkFormARBDeletedItems()' class='form-control input_field eadd pname' placeholder='PRODUCT NAME'></td>";
		d.innerHTML = "<td><input type=text name='cstehv' id='cstehv' maxlength='8' onchange='checkFormARBDeletedItems()' class='form-control input_field eadd' placeholder='HSN CODE'></td>";
		e.innerHTML = "<td><SELECT NAME='pus' ID='pus' class='form-control input_field select_dropdown sadd'>"
			+ "<OPTION VALUE='0'>SELECT</OPTION>"
			+ "<OPTION VALUE='1'>NOS</OPTION>"
			+ "<OPTION VALUE='2'>KGS</OPTION>"
			+ "<OPTION VALUE='3'>SET</OPTION>"
			+ "</SELECT></td>";
		f.innerHTML = "<td><SELECT NAME='gstp' ID='gstp' class='form-control input_field select_dropdown sadd'>"
			+ "<OPTION VALUE='-1'>SELECT</OPTION>"
			+ "<OPTION VALUE='0'>0</OPTION>"
			+ "<OPTION VALUE='5'>5</OPTION>"
			+ "<OPTION VALUE='12'>12</OPTION>"
			+ "<OPTION VALUE='18'>18</OPTION>"
			+ "<OPTION VALUE='28'>28</OPTION>"
			+ "</SELECT></td>";
		g.innerHTML = "<td><input type=text name='mrp' id='mrp' value='0.00' maxlength='7' class='form-control input_field eadd'></td>";
		h.innerHTML = "<td><input type=text name='os' id='os' maxlength='4' class='form-control input_field eadd' placeholder='OPENING STOCK'></td>";
		i.innerHTML = "<td><input type=date name='ed' id='ed' class='form-control input_field eadd' placeholder='DD-MM-YYYY' style='width:160px;'></td>";
		j.innerHTML = "<td><img src='images/delete.png' onclick='doRowDelete(this)'></td>";

	}else {
		document.getElementById("dialog-1").innerHTML = "Please Save the Records and ADD Again";
		alertdialogue();
		//alert("Please Save the Records and ADD Again");
	}
}

function checkFormARBDeletedItems() {
	var formobj = document.getElementById('marb_page_add_form');
	var ele = document.getElementsByClassName("ic");
	if(ele.length==1){
		var prod = formobj.pid.value.trim();
		var sprod = document.getElementById("pid").options;
		var spus = document.getElementById("pus").options;
		var sgstp = document.getElementById("gstp").options;

		for(var d=0;d<arb_data.length;d++){
			if(arb_data[d].deleted != 0) {
				if(((arb_data[d].csteh_no).trim() == formobj.cstehv.value.trim()) && (arb_data[d].prod_code == parseInt(prod)) 
						&& ((arb_data[d].prod_brand).trim() == formobj.pbrand.value.trim()) && ((arb_data[d].prod_name).trim() == formobj.pname.value.trim())) {

					// FOR ARB PRODUCT
					var item = arb_data[d].prod_code;
					enableSelect(sprod,sprod.length);
					switch(item) {
					case 13 :
						formobj.pid.selectedIndex=1;
						disableSelect(sprod,sprod.length);
						break;
					case 14 :
						formobj.pid.selectedIndex=2;
						disableSelect(sprod,sprod.length);
						break;
					case 15 :
						formobj.pid.selectedIndex=3;
						disableSelect(sprod,sprod.length);
						break;
					case 16 :
						formobj.pid.selectedIndex=4;
						disableSelect(sprod,sprod.length);
						break;
					case 17 :
						formobj.pid.selectedIndex=5;
						disableSelect(sprod,sprod.length);
						break;
					default :
						formobj.pid.selectedIndex=0;
						disableSelect(sprod,sprod.length);
						break;
					}
					// For PRODUCT BRAND
					var prodbrand = (arb_data[d].prod_brand).trim();
					formobj.pbrand.value = prodbrand;
					document.getElementById("pbrand").setAttribute("readOnly","true");
					// For PRODUCT NAME
					var prodname = (arb_data[d].prod_name).trim();
					formobj.pname.value = prodname;
					document.getElementById("pname").setAttribute("readOnly","true");
					
					//FOR HSN CODE
					document.getElementById("cstehv").setAttribute("readOnly","true");
					
					// FOR UNITS
					var units = arb_data[d].units;
					enableSelect(spus,spus.length);
					formobj.pus.selectedIndex = units;
					disableSelect(spus,spus.length);
					
					//FOR GST PERCENT
					var gstp = arb_data[d].gstp;
					enableSelect(sgstp,sgstp.length);
					switch(gstp){
						case "0" :
							formobj.gstp.selectedIndex=1;
							disableSelect(sgstp,sgstp.length);
							break;
						case "5" :
							formobj.gstp.selectedIndex=2;
							disableSelect(sgstp,sgstp.length);
							break;
						case "12" :
							formobj.gstp.selectedIndex=3;
							disableSelect(sgstp,sgstp.length);
							break;
						case "18" :
							formobj.gstp.selectedIndex=4;
							disableSelect(sgstp,sgstp.length);
							break;
						case "28" :
							formobj.gstp.selectedIndex=5;
							disableSelect(sgstp,sgstp.length);
							break;
						default :
							formobj.gstp.selectedIndex=0;
							enableSelect(sgstp,sgstp.length);
							break;
					}

					// For PURCHASE PRICE
					var pprice = arb_data[d].prod_mrp;
					formobj.mrp.value = pprice.trim();
					document.getElementById("mrp").setAttribute("readOnly","false");
						
					// For OPENING STOCK
					var cs = arb_data[d].current_stock;
					formobj.os.value = cs;
					document.getElementById("os").setAttribute("readOnly","true");
					
					break;
					
				}
			}
		}
	}else if(ele.length>1){
		for(var e=0;e<ele.length;e++){
			var prod = formobj.pid[e].value;
			var sprod = formobj.pid[e].options;
			var spus = formobj.pus[e].options;
			var sgstp = formobj.gstp[e].options;			
			
			for(var d=0;d<arb_data.length;d++){
				if(arb_data[d].deleted != 0) {	
					if(((arb_data[d].csteh_no).trim() == formobj.cstehv[e].value.trim()) && (arb_data[d].prod_code == parseInt(prod)) 
							&& ((arb_data[d].prod_brand).trim() == formobj.pbrand[e].value.trim()) && ((arb_data[d].prod_name).trim() == formobj.pname[e].value.trim())) {

						// FOR ARB PRODUCT
						var item = arb_data[d].prod_code;
						enableSelect(sprod,sprod.length);						
						switch(item) {
							case 13 :
								formobj.pid[e].selectedIndex=1;
								disableSelect(sprod,sprod.length);
								break;
							case 14 :
								formobj.pid[e].selectedIndex=2;
								disableSelect(sprod,sprod.length);
								break;
							case 15 :
								formobj.pid[e].selectedIndex=3;
								disableSelect(sprod,sprod.length);
								break;
							case 16 :
								formobj.pid[e].selectedIndex=4;
								disableSelect(sprod,sprod.length);
								break;
							case 17 :
								formobj.pid[e].selectedIndex=5;
								disableSelect(sprod,sprod.length);
								break;
							default :
								formobj.pid[e].selectedIndex=0;
								disableSelect(sprod,sprod.length);
								break;
						}
						
						// For PRODUCT BRAND
						var prodbrand = (arb_data[d].prod_brand).trim();
						formobj.pbrand[e].value = prodbrand;
						formobj.pbrand[e].setAttribute("readOnly","true");
						// For PRODUCT NAME
						var prodname = (arb_data[d].prod_name).trim();
						formobj.pname[e].value = prodname;
						formobj.pname[e].setAttribute("readOnly","true");
						//FOR HSN CODE
						formobj.cstehv[e].setAttribute("readOnly","true");
						// FOR UNITS
						var units = arb_data[d].units;
						enableSelect(spus,spus.length);
						formobj.pus[e].selectedIndex = units;
						disableSelect(spus,spus.length);
						//FOR GST PERCENT
						var gstp = arb_data[d].gstp;
						enableSelect(sgstp,sgstp.length);
						switch(gstp){
							case "0" :
								formobj.gstp[e].selectedIndex=1;
								disableSelect(sgstp,sgstp.length);
								break;
							case "5" :
								formobj.gstp[e].selectedIndex=2;
								disableSelect(sgstp,sgstp.length);
								break;
							case "12" :
								formobj.gstp[e].selectedIndex=3;
								disableSelect(sgstp,sgstp.length);
								break;
							case "18" :
								formobj.gstp[e].selectedIndex=4;
								disableSelect(sgstp,sgstp.length);
								break;
							case "28" :
								formobj.gstp[e].selectedIndex=5;
								disableSelect(sgstp,sgstp.length);
								break;
							default :
								formobj.gstp[e].selectedIndex=0;
								enableSelect(sgstp,sgstp.length);
								break;
						}

						// For PURCHASE PRICE
						var pprice = arb_data[d].prod_mrp;
						formobj.mrp[e].value = pprice.trim();
//						formobj.mrp[e].setAttribute("readOnly","false");
							
						// For OPENING STOCK
						var cs = arb_data[d].current_stock;
						formobj.os[e].value = cs;
						formobj.os[e].setAttribute("readOnly","true");
						break;
					}
				}
			}
		}		
	}
}

function mARBsaveData(obj){
	var formobj = document.getElementById('marb_page_add_form');
	var ems = "";

	if(document.getElementById("pid") != null) {
		var elements = document.getElementsByClassName("ic");
		if(elements.length==1) {
			var eitem = formobj.pid.selectedIndex;
			var epb = formobj.pbrand.value.trim();
			var epn = formobj.pname.value.trim();
			var eusitem = formobj.pus.selectedIndex;
			var egst = formobj.gstp.value.trim();
			var ecsteh = formobj.cstehv.value.trim();
			var esd = formobj.mrp.value.trim();
			var ee = formobj.os.value.trim();
			var ed = formobj.ed.value.trim();
			if (checkDateFormate(ed)) {
				var ved = dateConvert(ed);
				formobj.ed.value = ved;
				ed = ved;
			}
			ems = validatemARBEntries(eitem,epb,epn,eusitem,egst,ecsteh,esd,ee,ed);
			if(!(ems.length>0)) {
				formobj.pbrand.value = formobj.pbrand.value.trim();
				formobj.pname.value = formobj.pname.value.trim();
				
				formobj.gstp.value = formobj.gstp.value.trim();
				formobj.cstehv.value = formobj.cstehv.value.trim();
				formobj.mrp.value = formobj.mrp.value.trim();
				formobj.os.value = formobj.os.value.trim();
				formobj.ed.value = formobj.ed.value.trim();				
			}
		}else if (elements.length>1){
			for(var i=0; i<elements.length; i++) {
				var eitem = formobj.pid[i].selectedIndex;
				var epb = formobj.pbrand[i].value.trim();
				var epn = formobj.pname[i].value.trim();
				var eusitem = formobj.pus[i].selectedIndex;
				var egst = formobj.gstp[i].value.trim();
				var ecsteh = formobj.cstehv[i].value.trim();
				var esd = formobj.mrp[i].value;
				var ee = formobj.os[i].value.trim();
				var ed = formobj.ed[i].value.trim();
				if (checkDateFormate(ed)) {
					var ved = dateConvert(ed);
					formobj.ed[i].value = ved;
					ed = ved;
				}
				ems = validatemARBEntries(eitem,epb,epn,eusitem,egst,ecsteh,esd,ee,ed);
				if(ems.length>0)
					break;
				else {
					formobj.pbrand[i].value = formobj.pbrand[i].value.trim();
					formobj.pname[i].value = formobj.pname[i].value.trim();
					
					formobj.gstp[i].value = formobj.gstp[i].value.trim();
					formobj.cstehv[i].value = formobj.cstehv[i].value.trim();
					formobj.mrp[i].value = formobj.mrp[i].value.trim();
					formobj.os[i].value = formobj.os[i].value.trim();
					formobj.ed[i].value = formobj.ed[i].value.trim();
				}
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
/*	$(objId).click(function (e) {
		$(objId).css('cursor', 'wait');
    });
*/
	formobj.submit();
}

/*function deletemARBItem(id){
	if (confirm("Are you sure you want to delete?") == true) {
		var formobj = document.getElementById('marb_page_add_form');
		formobj.actionId.value = "3113";
		formobj.itemId.value = id;
		formobj.submit();
	}
}*/

function deletemARBItem(id){
	 $("#myDialogText").text("Are You Sure You Want To Delete?");
	 var formobj = document.getElementById('marb_page_add_form');
	 confirmDialogue(formobj,3113,id);
}

function fetchmPopupARBProductDetails(types, pc,pb,pn) {
	for(var i=0; i< types.length; i++){
		if((types[i].prod_code == pc) && (types[i].prod_brand == pb) && (types[i].prod_name== pn)) {
			return getARBType(types[i].prod_code)+"-"+types[i].prod_brand+"-"+types[i].prod_name;
		}
    }
}
function validatemARBProduct2(text) {
    if (arb_data.length != 0) {
    	for (var i = 0; i < arb_data.length; i++) {
    		if(arb_data[i].deleted ==0){
    			var spd = fetchmPopupARBProductDetails(arb_data,arb_data[i].prod_code,arb_data[i].prod_brand,arb_data[i].prod_name);
    			if (spd.localeCompare(text) == 0) {
    				return false;
    			}
    		}
    	}
    }
    return true;
}

function validatemARBProduct() {
	var flag;
	var eletype = document.getElementsByClassName("ic");
	var elebrand = document.getElementsByClassName("brand");
	var elename = document.getElementsByClassName("pname");

	if (eletype.length == 1) {
		var e = document.getElementById("pid");
		var b = document.getElementById("pbrand");
		var n = document.getElementById("pname");
		
		var text = e.options[e.selectedIndex].text;
		var btext = b.value;
		var ntext = n.value;
		
		flag = validatemARBProduct2(text+"-"+btext+"-"+ntext);
		return flag;
	} else if (eletype.length > 1) {
		flag = false;
		var h = 1;
		for (i = 0; i < eletype.length - 1; i++) {
			var e3 = eletype[i].options[eletype[i].selectedIndex].text;
			var b3 = elebrand[i].value;
			var n3 = elename[i].value;
			flag = validatemARBProduct2(e3+"-"+b3+"-"+n3);
			if (flag) {
				for (j = 0; j < eletype.length - 1; j++) {
					var e4 = eletype[j + 1].options[eletype[j + 1].selectedIndex].text;
					var b4 = elebrand[j + 1].value;
					var n4 = elename[j + 1].value;
					flag = validatemARBProduct2(e4+"-"+b4+"-"+n4);
					var k = 0;
					if (flag) {
						for (k; k < h && k < eletype.length - 1; k++) {
							var e5 = eletype[k].options[eletype[k].selectedIndex].text;
							var b5 = elebrand[k].value;
							var n5 = elename[k].value;
							flag = validatemARBProduct2(e5+"-"+b5+"-"+n5);
							if ((e5+"-"+b5+"-"+n5).localeCompare(e4+"-"+b4+"-"+n4) == 0) {
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

function validatemARBEntries(item,pbrand,pname,units,gstp,cstehno,mrp, os, ed){
	var formobj = document.getElementById('marb_page_add_form');
	var errmsg = "";

	if (!(item > 0))
		errmsg = errmsg + "Please Select PRODUCT CATEGORY<br>";
	
	if (((item > 0) && (pbrand.length > 0) && (pname.length > 0)) && (!validatemARBProduct()))
		errmsg = errmsg + "Please Select Another PRODUCT Which Was not Selected.<br>";
	
	if (!(pbrand.length > 0))
		errmsg = errmsg + "Please Enter PRODUCT BRAND<br>";

	if (!(pname.length > 0))
		errmsg = errmsg + "Please Enter PRODUCT NAME<br>";

	if (!(units > 0))
		errmsg = errmsg + "Please Select UNITS<br>";

	var vd = isValidDate(ed);
	var vfd = ValidateFutureDate(ed);
	if (!(ed.length > 0))
		errmsg = errmsg + "Please Enter EFFECTIVE DATE<br>";
	else if (vd != "false")
		errmsg = errmsg +vd+"<br>";
	else if (vfd != "false")
		errmsg = errmsg +"EFFECTIVE"+vfd+"<br>";
	else if(validateDayEndAdd(ed,dedate)){
        errmsg = "EFFECTIVE DATE should be after DayEndDate<br>";
        return errmsg;
	}
	
	if (!(gstp >= 0))
		errmsg = errmsg + "Please Select GST%<br>";

	if (!(cstehno.length > 0))
		errmsg = errmsg + "Please Enter HSN CODE<br>";
	else if (!checkNumber(cstehno)) 
		errmsg = errmsg + "HSN CODE Must Be Numeric<br>";
	else if(parseInt(cstehno)==0)
		errmsg = errmsg + "HSN CODE Cannot Be Zero<br>";
	else if(!(cstehno.length>=3))
		errmsg = errmsg + "HSN CODE must contain atleast Three digits <br>";
	
	if (!(mrp.length > 0))
		errmsg = errmsg + "Please Enter PURCHASE PRICE. <br>";
	else if (validateDot(mrp))
		errmsg = errmsg + "PURCHASE PRICE Must Contain Atleast One Number.<br>";
	else if (validateNegatives(mrp))
		errmsg = errmsg + "PURCHASE PRICE Must be a positive number.<br>";
	else if (!isDecimalNumber(mrp))
		errmsg = errmsg + "PURCHASE PRICE Must Be Numeric. <br>";
	else if ((!validateDecimalNumberMinMax(mrp, 0, 10000)))
		errmsg = errmsg+ "Please Enter Valid PURCHASE PRICE. It Must Be Numeric And less than 10,000 And greater than 0<br>";
	else if(!(parseInt(mrp)>0))
		errmsg = errmsg+ "PURCHASE PRICE must be greater than 0<br>";
	

	if (!(os.length > 0))
		errmsg = errmsg + "Please Enter OPENING STOCK.If not Enter 0.<br>";
	else if (validateNegatives(os))
		errmsg = errmsg + "OPENING STOCK Must be a positive number.<br>";
	else if ((!validateNumberMinMax(os, -1, 10000)))
		errmsg = errmsg + "OPENING STOCK Must Be Numeric And less than 10,000 And greater than or equal to 0<br>";

	return errmsg;
}