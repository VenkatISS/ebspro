//Construct Customer Type html
custdatahtml = "<OPTION VALUE='0'>SELECT</OPTION>";
if(cvo_data.length>0) {
	for(var z=0; z<cvo_data.length; z++){
		if(cvo_data[z].cvo_cat==1)
			custdatahtml=custdatahtml+"<OPTION VALUE='"+cvo_data[z].id+"'>"+cvo_data[z].cvo_name+"</OPTION>";
	}
}


var tbody = document.getElementById('credit_limit_data_table_body');
for(var f=0; f<page_data.length; f++){
	var custName = getCustomerName(cvo_data,page_data[f].cust_id);
	var tblRow = tbody.insertRow(-1);
    tblRow.style.height="20px";
    tblRow.align="left";
    tblRow.innerHTML = '<td>'+custName +'</td>'+
    	'<td>'+ page_data[f].credit_limit +'</td>'+
       	'<td>'+  page_data[f].credit_days +'</td>'+
       	'<td>'+ ccontrols[page_data[f].cc_type] +'</td>'+
       	'<td valign="top" height="4" align="center"><img src="images/delete.png" class="fa fa-trash-o" onclick="deleteItem('+page_data[f].id+')"></td>'
};

function addRow() {
	document.getElementById('myDIV').style.display = 'block';
	document.getElementById('savediv').style.display="inline";
	
    var trcount = document.getElementById('credit_limit_add_table_body').getElementsByTagName('tr').length;
    if(trcount>0){
    	var trv=document.getElementById('credit_limit_add_table_body').getElementsByTagName('tr')[trcount-1];
    	var saddv=trv.getElementsByClassName('sadd');
    	var eaddv=trv.getElementsByClassName('eadd');
    
    	var res=checkRowData(saddv,eaddv);
    	if(res == false){
    		alert("Please enter all the values in current row and then add next row");
    		return;
    	}		
    }

	var ele = document.getElementsByClassName("cl");
	if(ele.length < 4){
		var tbody = document.getElementById('credit_limit_add_table_body');
		var newRow = tbody.insertRow(-1);
		var a = newRow.insertCell(0);
		var b = newRow.insertCell(1);
		var c = newRow.insertCell(2);
		var d = newRow.insertCell(3);
		var e = newRow.insertCell(4);

		a.innerHTML = "<td><SELECT NAME='cid' ID='cid'  class='form-control input_field select_dropdown sadd' style='width:200px;'>"+custdatahtml+"</select></td>";
		b.innerHTML = "<td><INPUT TYPE=text NAME='cl' ID='cl' class='form-control input_field eadd cl' size='8' maxlength='20' placeholder='Credit Limit'></td>";
		c.innerHTML = "<td><INPUT TYPE=text NAME='cd' ID='cd' class='form-control input_field eadd' size='8' maxlength='20' placeholder='Credit Days'></td>";
		d.innerHTML = "<td><SELECT NAME='cc' ID='cc'  class='form-control input_field select_dropdown sadd' style='width:200px;'>"
			+ "<OPTION VALUE='0'>SELECT</OPTION>"
			+ "<OPTION VALUE='1'>BLOCK</OPTION>"
			+ "<OPTION VALUE='2'>WARN</OPTION>"+ "</SELECT></td>";
		e.innerHTML = "<td><img src='images/delete.png' onclick='doRowDelete(this)'></td>";
	}else{
		alert("Please Save the Records and ADD Again");
	}
}

function saveData() {
	var formobj = document.getElementById('credit_limit_form');
	var ems = "";

	if (document.getElementById("cid") != null) {

		var elements = document.getElementsByClassName("cl");
		if (elements.length == 1) {
			var eclname = formobj.cid.selectedIndex;
			var eclcl = formobj.cl.value.trim();
			var eclcd = formobj.cd.value.trim()
			var eclcontrol = formobj.cc.selectedIndex;

			ems = validateCLEntries(eclname, eclcl, eclcd, eclcontrol);
		} else if (elements.length > 1) {
			for (var i = 0; i < elements.length; i++) {
				var eclname = formobj.cid[i].selectedIndex;
				var eclcl = formobj.cl[i].value.trim();
				var eclcd = formobj.cd[i].value.trim()
				var eclcontrol = formobj.cc[i].selectedIndex;

				ems = validateCLEntries(eclname, eclcl, eclcd, eclcontrol);
				if (ems.length > 0)
					break;
			}
		}
	} else {
		alert("PLEASE ADD DATA");
		return;
	}

	if (ems.length > 0) {
		alert(ems);
		return;
	}
	formobj.submit();
}

function deleteItem(id) {

	if (confirm("Are you sure you want to delete?") == true) {
		var formobj = document.getElementById('credit_limit_form');
		formobj.actionId.value = "3553";
		formobj.itemId.value = id;
		formobj.submit();
	}
}

function validateCLEntries(clname, cllimit, cldays, clcontrol) {
	var errmsg = "";
	var formobj = document.getElementById('credit_limit_form');

	if (!(clname > 0))
		errmsg = "Please Select Customer .\n";

	if (!(cllimit.length > 0))
		errmsg = errmsg + "Please Enter Credit Limit.\n";
	else if (validateDot(cllimit))
		errmsg = errmsg + "cllimit must contain atleast one number\n";
	else if (!(validateDecimalNumberMinMax(cllimit, 0, 10000000)))
		errmsg = errmsg + "Please Enter valid Credit Limit\n";
	else
		formobj.cl.value = round(parseFloat(cllimit), 2);

	if (!(cldays.length > 0))
		errmsg = errmsg + "Please Enter Credit Days.\n";
	else	if (cldays === "0")
		errmsg = errmsg + "Please Enter Valid Credit Days.\n";
	else if (validateDot(cldays))
		errmsg = errmsg + "Credit Days must contain atleast one number OR Enter Zero\n";
	else if(cldays.length>3)
		errmsg = errmsg + "Please enter valid Credit Days\n";
	else if (!(validateDecimalNumberMinMax(cldays, 0, 1000)))
		errmsg = errmsg + "Please Enter valid Credit Days\n";

	if (!(clcontrol > 0))
		errmsg = errmsg + "Please Select Credit Control.\n";
	return errmsg;
}
