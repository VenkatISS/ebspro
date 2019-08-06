
//Construct Statutory Item html
sidatahtml = "<OPTION VALUE='0'>SELECT</OPTION>";
if(window.statutory_items.length>0) {
	for(var z=0; z<statutory_items.length; z++){
		sidatahtml=sidatahtml+"<OPTION VALUE='"+statutory_items[z].id+"'>"+statutory_items[z].item+"</OPTION>";
	}
}

var tbody = document.getElementById('statutory_data_table_body');
for(var f=statutory_data.length-1; f>=0; f--){
	var vupto = new Date(statutory_data[f].valid_upto);	
	var tblRow = tbody.insertRow(-1);
    tblRow.align="left";
    tblRow.innerHTML = "<tr><td>" + statutory_items[statutory_data[f].item_type-1].item + "</td>" + 
    	"<td>" + statutory_data[f].item_ref_no + "</td>" + 
    	"<td>" + vupto.getDate()+"-"+months[(vupto.getMonth())]+"-"+vupto.getFullYear()+ "</td>" +
    	"<td>" + reminderDays[statutory_data[f].remind_before] +" Days"+"</td>" + 
    	"<td>" + statutory_data[f].remarks +  "</td>" + 
    	"<td align='center'><img src='images/delete.png' onclick='deleteItem("+statutory_data[f].id+")'></td></tr>"; 
};

function addRow() {
	document.getElementById('statutory_add_table_div').style.display="block";
	document.getElementById('savediv').style.display="inline";
    $("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');

    var trcount = document.getElementById('statutory_add_table_body').getElementsByTagName('tr').length;
    if(trcount>0){
    	var trv=document.getElementById('statutory_add_table_body').getElementsByTagName('tr')[trcount-1];
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
		var tbody = document.getElementById('statutory_add_table_body');
		var newRow = tbody.insertRow(-1);

		var a = newRow.insertCell(0);
		var b = newRow.insertCell(1);
		var c = newRow.insertCell(2);
		var	d = newRow.insertCell(3);
		var e = newRow.insertCell(4);
		var f = newRow.insertCell(5);

		a.innerHTML = "<td><SELECT NAME='item' ID='item' class='form-control input_field select_dropdown ic sadd'>"
				+ sidatahtml + "</SELECT></td>";
		b.innerHTML = "<td><INPUT TYPE=text class='form-control input_field eadd' NAME='refno' ID='refno' size='8' value='NA' maxlength='20'></td>";
		c.innerHTML = "<td><INPUT TYPE=date class='form-control input_field eadd' NAME='validupto' ID='validupto' size='8' placeholder='DD-MM-YY'></td>";
		d.innerHTML = "<td><SELECT NAME='reminder' class='form-control input_field select_dropdown sadd' ID='reminder'>"
				+ "<OPTION VALUE='0'>SELECT</OPTION>"
				+ "<OPTION VALUE='1'>7 DAYS</OPTION>"
				+ "<OPTION VALUE='2'>15 DAYS</OPTION>"
				+ "<OPTION VALUE='3'>30 DAYS</OPTION>"
				+ "<OPTION VALUE='4'>60 DAYS</OPTION>" + "</SELECT></td>";
		e.innerHTML = "<td><INPUT TYPE=text class='form-control input_field eadd' NAME='remarks' ID='remarks' size='8' maxlength='40'></td>";
		f.innerHTML = "<td class='text-center'><img src='images/delete.png' onclick='doRowDelete(this)'></td>";
	}else{
	document.getElementById("dialog-1").innerHTML = "Please Save the Records and ADD Again";
	alertdialogue();
	}
}

function saveData(obj) {
	var formobj = document.getElementById('statutory_add_form');
	var ems = "";
	if (document.getElementById("refno") != null) {
		var elements = document.getElementsByClassName("ic");
		if (elements.length == 1) {
			var eitem = formobj.item.selectedIndex;
			var erefno = formobj.refno.value.trim();
			var evupto = formobj.validupto.value;
			var eremarks = formobj.remarks.value.trim();
			var reminder = formobj.reminder.selectedIndex;
			if (checkDateFormate(evupto)) {
				var ved = dateConvert(evupto);
				evupto = ved;
			}
			ems = validateEntries(eitem, erefno, evupto, reminder, eremarks);
		} else if (elements.length > 1) {
			for (var i = 0; i < elements.length; i++) {
				var eitem = formobj.item[i].selectedIndex;
				var erefno = formobj.refno[i].value.trim();
				var evupto = formobj.validupto[i].value;
				var eremarks = formobj.remarks[i].value.trim();
				var reminder = formobj.reminder[i].selectedIndex;
				if (checkDateFormate(evupto)) {
					var ved = dateConvert(evupto);
					evupto = ved;
				}
				ems = validateEntries(eitem, erefno, evupto, reminder, eremarks);
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

/*function deleteItem(statutoryItemId) {
	if (confirm("Are you sure you want to delete?") == true) {
		var formobj = document.getElementById('statutory_add_form');
		formobj.actionId.value = "3003";
		formobj.itemId.value = statutoryItemId;
		formobj.submit();
	}
}*/

function deleteItem(id){
	 $("#myDialogText").text("Are You Sure You Want To Delete?");
	 var formobj = document.getElementById('statutory_add_form');
	 var  delactionId =3003;
	 confirmDialogue(formobj,delactionId,id);
}

function validateItem() {
	var e2 = document.getElementsByClassName("ic");
	if (e2.length == 1) {
		var e = document.getElementById("item");
		var text = e.options[e.selectedIndex].text;
		var flag = validateItem2(text);
		return flag;
	}else if (e2.length > 1) {
		var flag = false;
		var h = 1;
		for (i = 0; i < e2.length - 1; i++) {
			var e3 = e2[i].options[e2[i].selectedIndex].text;
			var flag = validateItem2(e3);
			if (flag) {
				for (j = 0; j < e2.length - 1; j++) {
					var e4 = e2[j + 1].options[e2[j + 1].selectedIndex].text;

					flag = validateItem2(e4);
					var k = 0;
					if (flag) {
						for (k; k < h && k < e2.length - 1; k++) {
							var e5 = e2[k].options[e2[k].selectedIndex].text;

							if (e4.localeCompare("PLATFORM WEIGHING SCALE") == 0) {
								flag = true;
							} else if (e4.localeCompare("PORTABLE WEIGHING SCALE") == 0) {
								flag = true;
							} else if (e4.localeCompare("FIRE EXTINGHUISHER") == 0) {
								flag = true;
							}
							if (e5.localeCompare(e4) == 0) {
								if (e5.localeCompare("PLATFORM WEIGHING SCALE") == 0) {
									flag = true;
								} else if (e5.localeCompare("PORTABLE WEIGHING SCALE") == 0) {
									flag = true;
								} else if (e5.localeCompare("FIRE EXTINGHUISHER") == 0) {
									flag = true;
								} else {
									flag = false;
									return flag;
								}
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

function validateItem2(text) {
	if(statutory_data.length != 0){
		for (var i=0;i<statutory_data.length;i++){
			var statutory_item = statutory_items[statutory_data[i].item_type-1].item
            if (text.localeCompare("PLATFORM WEIGHING SCALE") == 0) {
            	return true;
            }
            if (text.localeCompare("PORTABLE WEIGHING SCALE") == 0) {
            	return true;
            }
            if (text.localeCompare("FIRE EXTINGHUISHER") == 0) {
            	return true;
            }
            if (statutory_item.localeCompare(text) == 0) {
            	return false;
            }
		}
    }
    return true;
}

function validateEntries(item, refno, vupto, reminder, remarks) {
	var errmsg = "";

	 if (!validateItem()) {
			errmsg = errmsg + "The ITEM has been already selected Please select another ITEM <br>";
		}
	 else{
	if (!(item > 0))
		errmsg = "Please Select Statutory ITEM<br>";

	if (!(refno.length > 0))
		errmsg = errmsg+ "Please Enter REFERENCE NUMBER. Enter NA If Not Applicable.<br>";

	var vd = isValidDate(vupto);
	var vppd = ValidatePresentAndPastDate(vupto)
	if (!(vupto.length > 0))
		errmsg = errmsg + "Please Enter VALID UPTO Date.<br>";
	else if (vd != "false")
		errmsg = errmsg + vd+"<br>";
	else if (vppd != "false")
		errmsg = errmsg +vppd+"<br>";

	if (!(reminder > 0))
		errmsg = errmsg + "Please Select REMINDER<br>";

	if (!(remarks.length > 0))
		errmsg = errmsg + "Please Enter REMARKS. Enter NA If Not Applicable<br>";
	 }
	return errmsg;
}


