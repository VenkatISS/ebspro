//Process page data
var tbody = document.getElementById('page_data_table_body');
var rowpdhtml = "";
if(page_data.length>0) {
	for(var f=page_data.length-1; f>=0; f--){
		var ed = new Date(page_data[f].asset_tdate);
		var staffName = getStaffName(staff_data,page_data[f].staff_id);
		var bankName = page_data[f].bank_id == 0 ? "CASH" : getBankName(bank_data,page_data[f].bank_id);	
		var mop = mops[page_data[f].asset_mop];
	
		if(!staffName)
			staffName = "NA";
		if(!bankName)
			bankName = "NA";
		if((!mop) || mop == "NONE")
			mop = "NA";

		var tblRow = tbody.insertRow(-1);
		tblRow.style.height="20px";
		tblRow.align="left";
		tblRow.innerHTML = "<td align='center'>" + ed.getDate()+"-"+months[ed.getMonth()]+"-"+ed.getFullYear() + "</td>" + 
				"<td align='center'>" + tts[page_data[f].asset_ttype] +  "</td>" + "<td align='center'>" + page_data[f].asset_desc +  "</td>" + "<td align='center'>" + amahs[page_data[f].asset_ah-140] +  "</td>" + 
				"<td align='center'>" + page_data[f].asset_value +  "</td>" + "<td align='center'>" + mop +  "</td>" + "<td align='center'>" + bankName +  "</td>" + 
				"</td>" + "<td align='center'>" + staffName +  "</td>"  + "<td><img src='images/delete.png' onclick='deleteItem("+page_data[f].id+")' style='margin-left:35px;'></td>"; 
		};
}


//Construct Staff html
staffdatahtml = "<OPTION VALUE='0'>SELECT</OPTION>";
if(staff_data.length>0) {
	for(var z=0; z<staff_data.length; z++){
		staffdatahtml=staffdatahtml+"<OPTION VALUE='"+staff_data[z].id+"'>"+staff_data[z].emp_name+"</OPTION>";
	}
}

//Construct bank html
bankdatahtml = "<OPTION VALUE='-1'>SELECT</OPTION>";
bankdatahtml = bankdatahtml + "<OPTION VALUE='0'>CASH</OPTION>";
if(bank_data.length>0) {
	for(var z=0; z<bank_data.length; z++){
		var bc = bank_data[z].bank_code;
		if(!(bc=="TAR ACCOUNT" || bc=="STAR ACCOUNT")) {
			bankdatahtml=bankdatahtml+"<OPTION VALUE='"+bank_data[z].id+"'>"+bank_data[z].bank_code+" - "+bank_data[z].bank_acc_no+"</OPTION>";
		}
	}
}


/*function addRow(){
	var check = checkDayEndDateForAdd(dedate,a_created_date,effdate);
	if(check == true) {
		document.getElementById('page_add_table_div').style.display="block";
		document.getElementById('savediv').style.display="inline";
	
		var trcount = document.getElementById('page_add_table_body').getElementsByTagName('tr').length;
		if(trcount>0){
			var trv=document.getElementById('page_add_table_body').getElementsByTagName('tr')[trcount-1];
			var saddv=trv.getElementsByClassName('sadd');
			var eaddv=trv.getElementsByClassName('eadd');
    
			var res=checkRowData(saddv,eaddv);
			if(res == false){
				document.getElementById("dialog-1").innerHTML = "Please enter all the values in current row and then add next row";
				alertdialogue();
				//alert("Please enter all the values in current row and then add next row");
				return;
			}		
		}
    	
		var ele = document.getElementsByClassName("qtyc");
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
			
			a.innerHTML = "<td valign='top' height='4' align='center'><input type=date name='atdate' id='atdate' class='form-control input_field eadd' style='width:160px;'></td>";
			b.innerHTML = "<td valign='top' height='4' align='center'><select name='att' id='att'  class='form-control input_field select_dropdown sadd' style='width:100px;'>"
				+"<option value='0'>SELECT</option>"
				+"<option value='2'>SALE</option>"
				+"<option value='3'>TRANSFER</option>"
				+"</select></td>";
			c.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='idesc' id='idesc'  class='form-control input_field eadd' placeholder='ITEM DESCRIPTION' style='width:150px;'></td>";
			d.innerHTML = "<td valign='top' height='4' align='center'><select name='amah' id='amah' class='form-control input_field select_dropdown sadd' style='width:110px;'>"
				+"<option value='0'>SELECT</option>"
				+"<option value='141'>LAND</option>"
				+"<option value='142'>BUILDING</option>"
				+"<option value='143'>PLANT AND MACHINARY</option>"
				+"<option value='144'>FURNITURE AND FIXTURES</option>"
				+"<option value='145'>MOTAR VEHICLES</option>"
				+"<option value='146'>COMPUTERS AND PRINTERS</option>"
				+"<option value='147'>OTHER MOVABLE ASSETS</option>"
				+"</select></td>";
			e.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='av' id='av' class='form-control input_field eadd qtyc' style='width:100px;' placeholder='ASSET VALUE'></td>";
			f.innerHTML = "<td valign='top' height='4' align='center'><select name='mop' id='mop' style='width:105px;' class='form-control input_field select_dropdown'>"
				+"<option value='0'>SELECT</option>"
				+"<option value='1'>CASH</option>"
				+"<option value='2'>CHEQUE</option>"
				+"<option value='3'>ONLINE TRANSFER</option>"
				+"</select></td>";
			g.innerHTML = "<td valign='top' height='4' align='center'><select id='bid' name='bid' style='width:120px;' class='form-control input_field select_dropdown'>"+bankdatahtml+"</select></td>";
			h.innerHTML = "<td valign='top' height='4' align='center'><select id='sid' name='sid'  style='width:100px;' class='form-control input_field select_dropdown sadd'>"+staffdatahtml+"</select></td>";
			i.innerHTML = "<td valign='top' height='4' align='center'><img src='images/delete.png' onclick='doRowDelete(this)' style='margin-left:35px;'></td>";
		}else {
			document.getElementById("dialog-1").innerHTML = "Please Save the Records and ADD Again";
			alertdialogue();
			//alert("Please Save the Records and ADD Again");
		}
	}else if(check!=true) {
		document.getElementById("dialog-1").innerHTML = check;
		alertdialogue();
		return;
	}
}*/

function addRow(){
	document.getElementById('page_add_table_div').style.display="block";
	document.getElementById('savediv').style.display="inline";
    $("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');

	var trcount = document.getElementById('page_add_table_body').getElementsByTagName('tr').length;
	if(trcount>0){
		var trv=document.getElementById('page_add_table_body').getElementsByTagName('tr')[trcount-1];
		var saddv=trv.getElementsByClassName('sadd');
		var eaddv=trv.getElementsByClassName('eadd');
    
		var res=checkRowData(saddv,eaddv);
		if(res == false){
			document.getElementById("dialog-1").innerHTML = "Please enter all the values in current row and then add next row";
			alertdialogue();
			//alert("Please enter all the values in current row and then add next row");
			return;
		}		
	}
    	
	var ele = document.getElementsByClassName("qtyc");
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
		
		a.innerHTML = "<td valign='top' height='4' align='center'><input type=date name='atdate' id='atdate' class='form-control input_field eadd' style='width:160px;'></td>";
		b.innerHTML = "<td valign='top' height='4' align='center'><select name='att' id='att'  class='form-control input_field select_dropdown sadd' style='width:140px;'>"
			+"<option value='0'>SELECT</option>"
			+"<option value='2'>SALE</option>"
			+"<option value='3'>TRANSFER</option>"
			+"</select></td>";
		c.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='idesc' id='idesc'  class='form-control input_field eadd' placeholder='ITEM DESCRIPTION' style='width:150px;'></td>";
		d.innerHTML = "<td valign='top' height='4' align='center'><select name='amah' id='amah' class='form-control input_field select_dropdown sadd' style='width:210px;'>"
			+"<option value='0'>SELECT</option>"
			+"<option value='141'>LAND</option>"
			+"<option value='142'>BUILDING</option>"
			+"<option value='143'>PLANT AND MACHINARY</option>"
			+"<option value='144'>FURNITURE AND FIXTURES</option>"
			+"<option value='145'>MOTAR VEHICLES</option>"
			+"<option value='146'>COMPUTERS AND PRINTERS</option>"
			+"<option value='147'>OTHER MOVABLE ASSETS</option>"
			+"</select></td>";
		e.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='av' id='av' class='form-control input_field eadd qtyc' style='width:125px;' placeholder='ASSET VALUE'></td>";
		f.innerHTML = "<td valign='top' height='4' align='center'><select name='mop' id='mop' style='width:160px;' class='form-control input_field select_dropdown'>"
			+"<option value='0'>SELECT</option>"
			+"<option value='1'>CASH</option>"
			+"<option value='2'>CHEQUE</option>"
			+"<option value='3'>ONLINE TRANSFER</option>"
			+"</select></td>";
		g.innerHTML = "<td valign='top' height='4' align='center'><select id='bid' name='bid' style='width:160px;' class='form-control input_field select_dropdown'>"+bankdatahtml+"</select></td>";
		h.innerHTML = "<td valign='top' height='4' align='center'><select id='sid' name='sid'  style='width:135px;' class='form-control input_field select_dropdown sadd'>"+staffdatahtml+"</select></td>";
		i.innerHTML = "<td valign='top' height='4' align='center'><img src='images/delete.png' onclick='doRowDelete(this)'></td>";
	}else {
		document.getElementById("dialog-1").innerHTML = "Please Save the Records and ADD Again";
		alertdialogue();
		//alert("Please Save the Records and ADD Again");
	}
}


/*function setBankAndMOP() {
	var formobj = document.getElementById('page_add_form');	
	var elements = document.getElementsByClassName("qtyc");
	var atti = formobj.att.selectedIndex;
	var attv = formobj.att.options[atti].value;

	if(attv == 3) {
		if (elements.length == 1) {
			var smpfrz= document.getElementById("mop").options;
			disableSelect(smpfrz,smpfrz.length);
			
			var sbpfrz= document.getElementById("bid").options;
			disableSelect(sbpfrz,sbpfrz.length);
		} else if (elements.length > 1) {
			for (var i = 0; i < elements.length; i++) {
				var pmfrz=formobj.mop[i].options;
				disableSelect(pmfrz,pmfrz.length);	
				
				var pbfrz=formobj.bid[i].options;
				disableSelect(pbfrz,pbfrz.length);
			}		
		}
	}else {
		if (elements.length == 1) {
			var spfrz= document.getElementById("mop").options;
			enableSelect(spfrz,spfrz.length);

			var sbidfrz= document.getElementById("bid").options;
			enableSelect(sbidfrz,sbidfrz.length);
		} else if (elements.length > 1) {
			for (var i = 0; i < elements.length; i++) {
				var pfrz=formobj.mop[i].options;
				enableSelect(pfrz,pfrz.length);			
				
				var bidfrz=formobj.bid[i].options;
				enableSelect(bidfrz,bidfrz.length);
			}		
		}
	}
}
*/

function saveData(){
	var formobj = document.getElementById('page_add_form');
	var ems = "";
	
	if(document.getElementById("att") != null){
		
		var elements = document.getElementsByClassName("qtyc");
		if(elements.length==1) {
			var eatdate = formobj.atdate.value.trim();
			var eatt = formobj.att.selectedIndex;
			var eattv = formobj.att.options[eatt].value;
			var eidesc = formobj.idesc.value.trim();
			var eamah = formobj.amah.selectedIndex;
			var eav = formobj.av.value.trim();
			var emop = formobj.mop.selectedIndex;
			var ebid = formobj.bid.selectedIndex;
			
			if (checkDateFormate(eatdate)) {
				var ved = dateConvert(eatdate);
				formobj.atdate.value = ved;
				eatdate = ved;
			}
			
			ems = validateEntries(eatdate,eatt,eattv,eidesc,eamah,eav,emop,ebid);
		}else if (elements.length>1){
			for(var i=0; i<elements.length; i++){
				var eatdate = formobj.atdate[i].value.trim();
				var eatt = formobj.att[i].selectedIndex;
				var eattv = formobj.att[i].options[eatt].value;				
				var eidesc = formobj.idesc[i].value.trim();
				var eamah = formobj.amah[i].selectedIndex;
				var eav = formobj.av[i].value.trim();
				var emop = formobj.mop[i].selectedIndex;
				var ebid = formobj.bid[i].selectedIndex;			

				if (checkDateFormate(eatdate)) {
					var ved = dateConvert(eatdate);
					formobj.atdate[i].value = ved;
					eatdate = ved;
				}
				
				ems = validateEntries(eatdate,eatt,eattv,eidesc,eamah,eav,emop,ebid);
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
	
	formobj.submit();

}

/*function deleteItem(iid){
	if (confirm("Are you sure you want to delete?") == true) {
		var formobj = document.getElementById('page_add_form');
		formobj.actionId.value = "5563";
		formobj.dataId.value = iid;
		formobj.submit();
	}	
}*/

function deleteItem(iid){
	 $("#myDialogText").text("Are You Sure You Want To Delete?");
	 var formobj = document.getElementById('page_add_form');
	 confirmDialogue(formobj,5563,iid);
}

function validateEntries(eatdate,eatt,eattv,eidesc,eamah,eav,emop,ebid) {
	var formobj = document.getElementById('page_add_form');
	var vd = isValidDate(eatdate);
	var errmsg = "";	

	var vfd = ValidateFutureDate(eatdate);
	if (!(eatdate.length > 0))
		errmsg = errmsg + "Please Enter TRANSACTION DATE <br>";
	else if (vd != "false")
		errmsg = errmsg+"TRANSACTION "+vd+"<br>";
	else if (vfd != "false")
		errmsg = errmsg +"TRANSACTION "+vfd+"<br>";

	
	if (!(eatt > 0))
		errmsg = errmsg + "Please Select TRANSACTION TYPE <br>";

	if (!(eidesc.length > 0))
		errmsg = errmsg + "Please Enter ITEM DESCRIPTION <br>";
	else if(eidesc.length < 5)
		errmsg = errmsg + "ITEM DESCRIPTION Must Contains Atleast 5 Chars <br>";
	else if(!(IsNameSpaceDot(eidesc)))
		errmsg = errmsg + "Please Enter Valid ITEM DESCRIPTION<br>";
	
	if (!(eamah > 0))
		errmsg = errmsg + "Please Select ACCOUNT HEAD <br>";

	if (!(eav.length > 0))
		errmsg = errmsg + "Please Enter ASSET VALUE <br>";
	else if (validateDot(eav))
		errmsg = errmsg + "ASSET VALUE Must Contain Atleast One Number. <br>";
	else if (!isDecimalNumber(eav))
		errmsg = errmsg + "ASSET VALUE Must Contain Only Numerics. <br>";
	else if ((!validateDecimalNumberMinMax(eav, 0, 10000000)))
		errmsg = errmsg + "ASSET VALUE It Must Be Numeric And Less Than 10000000 And Greater Than 0 <br>";
	else
		formobj.av.value = round(parseFloat(eav), 2);
     
	if(eattv != 3) {
		if (!(emop > 0))
			errmsg = errmsg + "Please Select MODE OF PAYMENT <br>";
        
		if(ebid == 0)
        	errmsg = errmsg + "Please Select BANK <br>";
		else if(emop == 1 && ebid != 1)
				errmsg = errmsg + "Please Select Valid BANK For The Chosen MODE OF PAYMENT <br>";
		else if( emop != 1 && ebid == 1)	
				errmsg = errmsg + "Please Select Valid MODE OF PAYMENT For The Chosen BANK <br>";		
	
	}
	if(eattv == 3 && (ebid !=0 || emop != 0)) 
		errmsg = errmsg + "You Cannot Have BANK And MODE OF PAYMENT For TRASFER TYPE<br>";
	//if(emop == 1  && ebid != 1)
		//errmsg = errmsg + "Please Select Valid BANK For The Choosen Mode Of Payment<br>";
	return errmsg;
}
