var tbody = document.getElementById('page_data_table_body');
for(var f=page_data.length-1; f>=0; f--){
   var tblRow = tbody.insertRow(-1);
   tblRow.style.height="20px";
   tblRow.align="left";
   var ed = new Date(page_data[f].effective_date);
   tblRow.innerHTML = "<tr>"+
	   "<td>"+ page_data[f].area_code +"</td>"+
	   "<td>"+ page_data[f].area_name +"</td>"+
	   "<td>"+ page_data[f].one_way_dist +"</td>"+
	   "<td>"+ page_data[f].transport_charges +"</td>"+	
	   "<td>"+ ed.getDate()+"-"+months[ed.getMonth()]+"-"+ed.getFullYear() +"</td>"+
	   "<td align='center'><img src='images/delete.png' onclick='deleteItem("+page_data[f].id+")'></td>"+
	   "</tr>";
};


function addRow(){
	var x = document.getElementById('page_add_table_div');
    $("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');

    if (x.style.display === 'none')
        x.style.display = 'block';
	document.getElementById('savediv').style.display="inline";

    var trcount = document.getElementById('page_add_table_body').getElementsByTagName('tr').length;
    if(trcount>0){
    	var trv=document.getElementById('page_add_table_body').getElementsByTagName('tr')[trcount-1];
    	var saddv=[]; //Since in this module no SELECT elements are there..
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
		
		a.innerHTML = "<td><input type=text name='ac' id='ac' maxlength='8' class='form-control input_field ic eadd' placeholder='AREA CODE'></td>";
		b.innerHTML = "<td><input type=text name='an' id='an' maxlength='25' class='form-control input_field eadd' placeholder='AREA NAME'></td>";
		c.innerHTML = "<td><input type=text name='owd' id='owt' maxlength='3' value='0' class='form-control input_field eadd' onfocus='javascript:checkOnFocus(this)' onblur='javascript:checkOnBlur(this)' defaultValue='0' placeholder='DISTANCE(KM)'></td>";
		d.innerHTML = "<td><input type=text name='tc' id='tc' maxlength='5' value='0.00' class='form-control input_field eadd' onfocus='javascript:checkOnFocus(this)' onblur='javascript:checkOnBlur(this)' defaultValue='0.00' placeholder='TRANSPORT CHARGES'></td>";
		e.innerHTML = "<td><input type=date name='ed' id='ed' class='form-control input_field eadd' placeholder='DD-MM-YY'></td>";
		f.innerHTML = "<td><img src='images/delete.png' onclick='doRowDelete(this)'></td>";
	}else{
		document.getElementById("dialog-1").innerHTML = "Please Save the Records and ADD Again";
		alertdialogue();
		//alert("Please Save the Records and ADD Again");
	}

}

function saveData(obj) {
	var formobj = document.getElementById('page_data_form');
	var ems = "";

	if (document.getElementById("ac") != null) {
		var elements = document.getElementsByClassName("ic");
		if (elements.length == 1) {
			var ac = formobj.ac.value.trim();
			var an = formobj.an.value.trim();
			var owt = formobj.owt.value.trim();
			var tc = formobj.tc.value.trim();
			var ed = formobj.ed.value.trim();

			if (checkDateFormate(ed)) {
				var ved = dateConvert(ed);
				formobj.ed.value = ved;
				ed = ved;
			}
			ems = validateEntries(ac, an, owt, tc, ed);
		} else if (elements.length > 1) {
			for (var i = 0; i < elements.length; i++) {
				var ac = formobj.ac[i].value.trim();
				var an = formobj.an[i].value.trim();
				var owt = formobj.owt[i].value.trim();
				var tc = formobj.tc[i].value.trim();
				var ed = formobj.ed[i].value.trim();

				if (checkDateFormate(ed)) {
					var ved = dateConvert(ed);
					formobj.ed[i].value = ved;
					ed = ved;
				}
				ems = validateEntries(ac, an, owt, tc, ed);

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
	
	var objId = obj.id;
	document.getElementById(objId).disabled = true;
	formobj.submit();
}

/*function deleteItem(id) {
	if (confirm("Are you sure you want to delete?") == true) {
		var formobj = document.getElementById('page_data_form');
		formobj.actionId.value = "3133";
		formobj.itemId.value = id;
		formobj.submit();
	}
}*/

function deleteItem(id){
	 $("#myDialogText").text("Are You Sure You Want To Delete?");
	 var formobj = document.getElementById('page_data_form');
	 confirmDialogue(formobj,3133,id);
}


function validateEntries(ac, an, owt, tc, ed) {
	var formobj = document.getElementById('page_data_form');
	var errmsg = "";

	if (!(ac.length > 0))
		errmsg = errmsg + "Please Enter AREA CODE<br>";

	if (!(an.length > 0))
		errmsg = errmsg + "Please Enter AREA NAME<br>";

	var vd = isValidDate(ed);
	var vfd = ValidateFutureDate(ed);
	if (!(ed.length > 0))
		errmsg = errmsg + "Please Enter EFFECTIVE DATE<br>";
	else if (vd != "false")
		errmsg = errmsg +vd+"<br>";
	else if (vfd != "false")
		errmsg = errmsg +vfd+"<br>";
	else if(validateDayEndAdd(ed,dedate)){
        errmsg = "EFFECTIVE DATE should be after DayEndDate<br>";
        return errmsg;
	}
	
/*	if (!(ed.length > 0))
		errmsg = errmsg + "Please Enter EFFECTIVE DATE<br>";
	else if (!isValidDate(ed))
		errmsg = errmsg + "Please Enter Valid EFFECTIVE DATE<br>";
	else if (!ValidateFutureDate(ed))
		errmsg = errmsg + "EFFECTIVE DATE Is Not Future Date<br>";
*/
	if (!(owt.length > 0))
		errmsg = errmsg + "Please Enter ONE WAY DISTANCE<br>";
	else if (!checkNumber(owt))
		errmsg = errmsg + "ONE WAY DISTANCE Must Contain Only Numerics. <br>";
	else if (!validateNumberMinMax(owt, -1, 100))
		errmsg = errmsg + "ONE WAY DISTANCE Must Be Numeric And <100 And >=0<br>";
	else
		formobj.owt.value = round(parseFloat(owt), 2);

	if (!(tc.length > 0))
		errmsg = errmsg + "Please Enter TRANSPORTATION CHARGES<br>";
	else if (validateDot(tc))
		errmsg = errmsg	+ "TRANSPORTATION CHARGES Must Contain Atleast One Number. <br>";
	else if (!isDecimalNumber(tc))
		errmsg = errmsg	+ "TRANSPORTATION CHARGES Must Contain Only Numerics. <br>";
	else if (!validateDecimalNumberMinMax(tc, -1, 100))
		errmsg = errmsg	+ "TRANSPORTATION CHARGES Must Be Numeric And less than 100 And greater than or equal to 0 <br>";
	else if ((parseFloat(owt) == 0.00) && (parseFloat(tc) != 0.00))
		errmsg = errmsg + "There Cant Be Any Charge For A Zero DISTANCE. <br>";
	else
		formobj.tc.value = round(parseFloat(tc), 2);

	return errmsg;
}
