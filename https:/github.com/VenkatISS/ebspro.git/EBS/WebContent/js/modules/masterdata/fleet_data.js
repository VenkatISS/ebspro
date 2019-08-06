var tbody = document.getElementById('fleet_data_table_body');
for(var f=fleet_data.length-1; f>=0; f--){
	var tblRow = tbody.insertRow(-1);
    tblRow.style.height="20px";
    tblRow.align="left";
    tblRow.innerHTML = '<td>'+ fleet_data[f].vehicle_no +'</td>'+
    	'<td>'+ fleet_data[f].vehicle_make +'</td>'+
       	'<td>'+ vehicleType[fleet_data[f].vehicle_type] +'</td>'+
       	'<td>'+ vechicleUsage[fleet_data[f].vehicle_usuage] +'</td>'+
       	'<td valign="top" height="4" align="center"><img src="images/delete.png" class="fa fa-trash-o" onclick="deleteItem('+fleet_data[f].id+')"></td>'
};

function addRow() {
	document.getElementById('myDIV').style.display = 'block';
	document.getElementById('savediv').style.display="inline";
    $("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');

    var trcount = document.getElementById('fleet_add_table_body').getElementsByTagName('tr').length;
    if(trcount>0){
    	var trv=document.getElementById('fleet_add_table_body').getElementsByTagName('tr')[trcount-1];
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

	var ele = document.getElementsByClassName("vc");
	if(ele.length < 4){
		var tbody = document.getElementById('fleet_add_table_body');
		var newRow = tbody.insertRow(-1);

		var a = newRow.insertCell(0);
		var b = newRow.insertCell(1);
		var c = newRow.insertCell(2);
		var d = newRow.insertCell(3);
		var e = newRow.insertCell(4);

		a.innerHTML = "<td><INPUT TYPE=text NAME='vh_no' ID='vh_no' class='form-control input_field vc eadd' size='8' maxlength='10' placeholder='Vehicle No'></td>";
		b.innerHTML = "<td><INPUT TYPE=text NAME='vh_make' ID='vh_make' class='form-control input_field eadd' size='8' maxlength='20' placeholder='Vehicle Make'></td>";
		c.innerHTML = "<td><SELECT NAME='vh_type' ID='vh_type'  class='form-control input_field select_dropdown sadd'>"
			+ "<OPTION VALUE='-1'>SELECT</OPTION>"
			+ "<OPTION VALUE='0'>TWO WHEELER</OPTION>"
			+ "<OPTION VALUE='1'>THREE WHEELER</OPTION>"
			+ "<OPTION VALUE='2'>FOUR WHEELER</OPTION>"
			+ "<OPTION VALUE='3'>TRUCK</OPTION>"
			+ "<OPTION VALUE='4'>OTHERS</OPTION>" + "</SELECT></td>";
		d.innerHTML = "<td><SELECT NAME='vh_usage' ID='vh_usage'  class='form-control input_field select_dropdown sadd'>"
			+ "<OPTION VALUE='-1'>SELECT</OPTION>"
			+ "<OPTION VALUE='0'>INWARD DELIVERY</OPTION>"
			+ "<OPTION VALUE='1'>OUTWARD DELIVERY</OPTION>"
			+ "<OPTION VALUE='2'>CONVEYANCE</OPTION>"
			+ "<OPTION VALUE='3'>OTHERS</OPTION>" + "</SELECT></td>";
		e.innerHTML = "<td><img src='images/delete.png' onclick='doRowDelete(this)'></td>";
	}else{
		document.getElementById("dialog-1").innerHTML = "Please Save the Records and ADD Again";
		alertdialogue();
		//alert("Please Save the Records and ADD Again");
	}
}

function saveData(obj) {
	var formobj = document.getElementById('fleet_data_form');
	var ems = "";

	if (document.getElementById("vh_no") != null) {

		var elements = document.getElementsByClassName("vc");
		if (elements.length == 1) {
			var evhno = formobj.vh_no.value.trim();
			var evhmake = formobj.vh_make.value.trim();
			var evhtype = formobj.vh_type.selectedIndex;
			var evhusage = formobj.vh_usage.selectedIndex;

			ems = validateVHEntries(evhno, evhmake, evhtype, evhusage);
		} else if (elements.length > 1) {
			for (var i = 0; i < elements.length; i++) {
				var evhno = formobj.vh_no[i].value.trim();
				var evhmake = formobj.vh_make[i].value.trim();
				var evhtype = formobj.vh_type[i].selectedIndex;
				var evhusage = formobj.vh_usage[i].selectedIndex;

				ems = validateVHEntries(evhno, evhmake, evhtype, evhusage);
				if (ems.length > 0)
					break;
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
		var formobj = document.getElementById('fleet_data_form');
		formobj.actionId.value = "3533";
		formobj.fleetDataId.value = id;
		formobj.submit();
	}
}*/

function deleteItem(id){
	 $("#myDialogText").text("Are You Sure You Want To Delete?");
	 var formobj = document.getElementById('fleet_data_form');
	 confirmDialogue(formobj,3533,id);
}

function validateVHEntries(vhno, vhmake, vhtype, vhusage) {
	var errmsg = "";

	for(var f=0; f<fleet_data.length; f++){
		   var vno =fleet_data[f].vehicle_no;
		   if(vno==vhno)
				errmsg = "VEHICLE NUMBER already exist.Please enter different VEHICLE NUMBER<br>";
			   }
	if (!(vhno.length > 0))
		errmsg = "Please Enter VEHICLE NUMBER.<br>";
	else if (!(vhno.length == 10 || vhno.length == 9))
		errmsg = errmsg + "VEHICLE NUMBER Must Contain 9 or 10 Chars Only.<br>";
	else if (!(validateBothAlphaNumeric(vhno)))
		errmsg = errmsg + "VEHICLE NUMBER Must Contain Combination of Alphabets and Numerics.<br>";
	
	if (!(vhmake.length > 0))
		errmsg = errmsg + "Please Enter VEHICLE MAKE.<br>";
	else if (!(IsText(vhmake)))
		errmsg = errmsg + "VEHICLE MAKE Must Contain Only Alphabets.<br>";

	if (!(vhtype > 0))
		errmsg = errmsg + "Please Select VEHICLE TYPE.<br>";

	if (!(vhusage > 0))
		errmsg = errmsg + "Please Select VEHICLE USAGE.<br>";
	return errmsg;
}
