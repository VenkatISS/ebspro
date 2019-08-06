var tbody = document.getElementById('staff_data_table_body');
for(var f=staff_data.length-1; f>=0; f--){
   var tblRow = tbody.insertRow(-1);
   var dob = new Date(staff_data[f].dob);
   tblRow.style.height="20px";
   tblRow.align="left";
   tblRow.innerHTML = '<td>' + staff_data[f].emp_code +'</td>'+
   		'<td>'+ staff_data[f].emp_name +'</td>'+
 		'<td>'+ dob.toLocaleDateString() + '</td>'+
   		/*'<td>'+ staff_data[f].dob + '</td>'+*/
   		'<td>'+ staff_data[f].designation + '</td>'+
   		'<td>'+ roles[staff_data[f].role] +'</td>'+
   		'<td valign="top" height="4" align="center"><img src="images/delete.png" class="fa fa-trash-o" onclick="deleteItem('+staff_data[f].id+')"></td>'	   
};
 
function addRow() {
	document.getElementById('myDIV').style.display = 'block';
	document.getElementById('savediv').style.display="inline";
    $("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');

    var trcount = document.getElementById('staff_add_table_body').getElementsByTagName('tr').length;
    if(trcount>0){
    	var trv=document.getElementById('staff_add_table_body').getElementsByTagName('tr')[trcount-1];
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

	var ele = document.getElementsByClassName("sdc");
	if(ele.length < 4){
		var tbody = document.getElementById('staff_add_table_body');
		var newRow = tbody.insertRow(-1);

		var a = newRow.insertCell(0);
		var b = newRow.insertCell(1);
		var c = newRow.insertCell(2);
		var d = newRow.insertCell(3);
		var e = newRow.insertCell(4);
		var f = newRow.insertCell(5);

		a.innerHTML = "<td><INPUT TYPE=text NAME='s_code' ID='s_code' class='form-control input_field sdc eadd' size='8' maxlength='6' placeholder='Emp Code'></td>";
		b.innerHTML = "<td><INPUT TYPE=text NAME='s_name' ID='s_name' class='form-control input_field eadd' size='8' maxlength='30' placeholder='Emp Name'></td>";
		c.innerHTML = "<td><INPUT TYPE=date NAME='s_dob' ID='s_dob' class='form-control input_field eadd' size='8' placeholder='yyyy/mm/dd' ></td>";
		d.innerHTML = "<td><INPUT TYPE=text NAME='s_designation' ID='s_designation' class='form-control input_field eadd' size='8' maxlength='15' placeholder='Designation'></td>";
		e.innerHTML = "<td><SELECT NAME='s_role' ID='s_role'  class='form-control input_field select_dropdown sadd'>"
				+ "<OPTION VALUE='-1'>SELECT</OPTION>"
				+ "<OPTION VALUE='0'>DELIVERY STAFF</OPTION>"
				+ "<OPTION VALUE='1'>SHOWROOM STAFF</OPTION>"
				+ "<OPTION VALUE='2'>GODOWN STAFF</OPTION>"
				+ "<OPTION VALUE='3'>INSPECTOR</OPTION>"
				+ "<OPTION VALUE='4'>MECHANIC</OPTION>"
				+ "<OPTION VALUE='5'>OTHERS</OPTION>" + "</SELECT></td>";
		f.innerHTML = "<td><img src='images/delete.png' onclick='doRowDelete(this)'></td>";
	}else{
		document.getElementById("dialog-1").innerHTML = "Please Save the Records and ADD Again";
		alertdialogue();
		//alert("Please Save the Records and ADD Again");
	}
}

function saveData(obj) {
	var formobj = document.getElementById('staff_data_form');
	var ems = "";

	if (document.getElementById("s_code") != null) {

		var elements = document.getElementsByClassName("sdc");
		if (elements.length == 1) {
			var escode = formobj.s_code.value.trim();
			var esname = formobj.s_name.value.trim();
			var esdob = formobj.s_dob.value.trim();
			var esdesig = formobj.s_designation.value.trim();
			var esrole = formobj.s_role.selectedIndex;

			ems = validateSDEntries(escode, esname, esdob, esdesig, esrole);
		} else if (elements.length > 1) {
			for (var i = 0; i < elements.length; i++) {
				var escode = formobj.s_code[i].value.trim();
				var esname = formobj.s_name[i].value.trim();
				var esdob = formobj.s_dob[i].value.trim();
				var esdesig = formobj.s_designation[i].value.trim();
				var esrole = formobj.s_role[i].selectedIndex;

				ems = validateSDEntries(escode, esname, esdob, esdesig, esrole);
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

/*function deleteItem(staffDataId) {

	if (confirm("Are you sure you want to delete?") == true) {
		var formobj = document.getElementById('staff_data_form');
		formobj.actionId.value = "3543";
		formobj.staffDataId.value = staffDataId;
		formobj.submit();
	}
}*/
function deleteItem(id){
	 $("#myDialogText").text("Are You Sure You Want To Delete?");
	 var formobj = document.getElementById('staff_data_form');
	 confirmDialogue(formobj,3543,id);
}

function validateSDEntries(code, name, dob, designation, role) {
	var errmsg = "";

	for(var f=0; f<staff_data.length; f++){
		   var scode =staff_data[f].emp_code;
		   if(scode==code)
				errmsg = "EMPLOYEE CODE already exist.Please enter different EMPLOYEE CODE<br>";
			   }
	
	if (!(code.length > 0))
		errmsg = "Please enter EMPLOYEE CODE<br>";
	else if (validateDot(code))
		errmsg = errmsg + "Please Enter Valid EMPLOYEE CODE.<br>";

	if (!(name.length > 0))
		errmsg = errmsg + "Please Enter NAME.<br>";
	else if (!(IsNameSpaceDot(name)))
		errmsg = errmsg + "Please Enter Valid NAME.<br>";

	var vfd = ValidateFutureDate(dob);
	if (!(dob.length > 0))
		errmsg = errmsg + "Please Enter DATE OF BIRTH.<br>";
	else if (vfd != "false")
		errmsg = errmsg +vfd+"<br>";
		
	if (!(designation.length > 0))
		errmsg = errmsg + "Please Enter Designation. <br>";

	if (!(role > 0))
		errmsg = errmsg + "Please Select Role <br>";

	return errmsg;
}
