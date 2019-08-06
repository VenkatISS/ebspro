var tbody = document.getElementById('partners_data_table_body');
for(var f=page_data.length-1; f>=0; f--){
      var tblRow = tbody.insertRow(-1);
      var trdate=page_data[f].tranx_date;
      var txndate = new Date(trdate)
      var bankName = getBankName(bank_data,page_data[f].bank_id);
      var partnerName = getPartnerName(partners_data,page_data[f].partner_id);
      tblRow.style.height="20px";
      tblRow.align="left";
      tblRow.innerHTML = "<tr><td>" + txndate.getDate()+"-"+months[txndate.getMonth()]+"-"+txndate.getFullYear() + "</td>" +
      					 "<td>" + partnerName + "</td>" + 
      					 "<td>" + pttype[page_data[f].tranx_type] +  "</td>" +
      					 "<td>" + page_data[f].tranx_amount + "</td>" +
      					 "<td>" + bankName + "</td>" + 
      					 "<td>" + page_data[f].remarks + "</td>" +
      					 "<td valign='top' height='4' align='center'><img src='images/delete.png' class='fa fa-trash-o' onclick='deleteItem("+page_data[f].id+")'></td></tr>"; 
};

function addRow() {
	var Ppship=document.getElementById("Ppship").value;
    if(Ppship=="1"){
    	document.getElementById("dialog-1").innerHTML = "You can add partners transactions only when you choose PARTNERSHIP while registration.";
		alertdialogue();
    	//alert("You can add partners transactions only when you choose PARTNERSHIP while registration.");
    }else {
        $("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');
    	var data = document.getElementById('myDIV').style.display = 'block';
		document.getElementById('savediv').style.display = "inline";
		var trcount = document.getElementById('partners_add_table_body').getElementsByTagName('tr').length;
		if (trcount > 0) {
			var trv = document.getElementById('partners_add_table_body').getElementsByTagName('tr')[trcount - 1];
			var saddv = trv.getElementsByClassName('sadd');
			var eaddv = trv.getElementsByClassName('eadd');
			var res = checkRowData(saddv, eaddv);
			if (res == false) {
				document.getElementById("dialog-1").innerHTML = "Please enter all the values in current row and then add next row";
				alertdialogue();
				//alert("Please enter all the values in current row and then add next row");
				return;
			}
		}

    	var ele = document.getElementsByClassName("tdate");
    	if(ele.length < 4){
    		var tbody = document.getElementById('partners_add_table_body');
    		var newRow = tbody.insertRow(-1);

    		var a = newRow.insertCell(0);
			var b = newRow.insertCell(1);
			var c = newRow.insertCell(2);
			var d = newRow.insertCell(3);
			var e = newRow.insertCell(4);
			var f = newRow.insertCell(5);
			var g = newRow.insertCell(6);

			a.innerHTML = "<td valign='top' height='4' align='center'><INPUT TYPE='date' NAME='td' ID='td' class='form-control input_field tdate eadd' size='8' placeholder='yyyy/mm/dd'></td>";
			b.innerHTML = "<td valign='top' height='4' align='center'><SELECT NAME='pn' ID='pn' class='form-control input_field select_dropdown sadd'>"
					+ partnerdatahtml + "</select></td>";
			c.innerHTML = "<td valign='top' height='4' align='center'><SELECT NAME='tt' ID='tt' class='form-control input_field select_dropdown sadd'>"
					+ "<option value='0'>SELECT</option>"
					+ "<option value='1'>INFUSION</option>"
					+ "<option value='2'>WITHDRAWAL</option>"
					+ "<option value='3'>WRITE-OFF</option>" + "</select></td>";
			d.innerHTML = "<td valign='top' height='4' align='center'><INPUT TYPE='text' NAME='ta' ID='ta' class='form-control input_field eadd' size='8'></td>";
			e.innerHTML = "<td valign='top' height='4' align='center'><SELECT NAME='bid' ID='bid' class='form-control input_field select_dropdown sadd'>"
					+ bankdatahtml + "</select></td>";
			f.innerHTML = "<td valign='top' height='4' align='center'><INPUT TYPE='text' NAME='rm' ID='rm' class='form-control input_field eadd' size='20'></td>";
			g.innerHTML = "<td valign='top' height='4' align='center'><img src='images/delete.png' onclick='doRowDelete(this)' style='margin-left:35px;'></td>";
		} else {
			document.getElementById("dialog-1").innerHTML = "Please Save the Records and ADD Again";
			alertdialogue();
			//alert("Please Save the Records and ADD Again");
		}
    }
}

   function saveData(){
   	var formobj = document.getElementById('partners_data_form');
   	var ems = "";

	if (document.getElementById("td") != null) {

		var elements = document.getElementsByClassName("tdate");
		if (elements.length == 1) {
			var eptrdate = formobj.td.value.trim();
			var epname = formobj.pn.selectedIndex;
			var eptraty = formobj.tt.selectedIndex;
			var eptamt = formobj.ta.value.trim();
			var epbank = formobj.bid.selectedIndex;
			var epnarr = formobj.rm.value.trim();
			ems = validatePTEntries(eptrdate, epname, eptraty, eptamt, epbank, epnarr);
		} else if (elements.length > 1) {
			for (var i = 0; i < elements.length; i++) {
				var eptrdate = formobj.td[i].value.trim();
				var epname = formobj.pn[i].selectedIndex;
				var eptraty = formobj.tt[i].selectedIndex;
				var eptamt = formobj.ta[i].value.trim();
				var epbank = formobj.bid[i].selectedIndex;
				var epnarr = formobj.rm[i].value.trim();

				ems = validatePTEntries(eptrdate, epname, eptraty, eptamt, epbank, epnarr);
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
   	
   	formobj.submit();
   }

  /* function deleteItem(did){
	if (confirm("Are you sure you want to delete?") == true) {
		var formobj = document.getElementById('partners_data_form');
		formobj.actionId.value = "8513";
		formobj.itemId.value = did;
		formobj.submit();
		}
   }*/
   
   function deleteItem(did){
		 $("#myDialogText").text("Are You Sure You Want To Delete?");
		 var formobj = document.getElementById('partners_data_form');
		 confirmDialogue(formobj,8513,did);
	}
   
   function validatePTEntries(trdate, pname, tratyp, tramt, bank, narr) {
		var errmsg = "";
		var formobj = document.getElementById('partners_data_form');
		
		var vd = isValidDate(trdate);
		var vfd = ValidateFutureDate(trdate);

		if (!(trdate.length > 0))
			errmsg = errmsg + "Please Enter TRANSACTION DATE.<br>";
		else if(vd != "false")
			errmsg = errmsg+vd+"<br>";
		else if (vfd != "false")
			errmsg = errmsg +vfd+"<br>";
		
		if (!(pname > 0))
			errmsg = errmsg + "Please Select PARTNER <br>";
		
		if (!(tratyp > 0))
			errmsg = errmsg + "Please Select TRANSACTION TYPE <br>";
		

		if (!tramt.length > 0)
			errmsg = errmsg + "Please Enter TRANSACTION AMOUNT <br>";
		else if (validateDot(tramt))
			errmsg = errmsg + "TRANSACTION AMOUNT must contain atleast one number<br>";
		else if (!(validateDecimalNumberMinMax(tramt, 0, 10000000)))
			errmsg = errmsg + "Please Enter valid TRANSACTION AMOUNT And It Must Be In Between 0 And 1,00,00,000<br>";
		else
			formobj.ta.value = round(parseFloat(tramt), 2);

		if (!(bank > 0))
			errmsg = errmsg + "Please Select BANK <br>";
		
		if (!(narr.length > 0))
			errmsg = errmsg + "Please Enter NARRATION.<br>";
	
		return errmsg;
	}