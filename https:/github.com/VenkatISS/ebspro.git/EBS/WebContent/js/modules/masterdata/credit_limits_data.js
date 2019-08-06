if(!pinNum) {
	document.getElementById("contentDiv").style.display="none";
	document.getElementById("displayDiv").style.display="block";
}else {
	document.getElementById("displayDiv").style.display="none";
	document.getElementById("contentDiv").style.display="block";
}

//Construct Customer Type html
custdatahtml = "<OPTION VALUE='0'>SELECT</OPTION>";
if(cvo_data.length>0) {
	for(var z=0; z<cvo_data.length; z++){
		if((cvo_data[z].cvo_cat==1) && (cvo_data[z].deleted == 0) && (cvo_data[z].cvo_name != "UJWALA"))
			custdatahtml=custdatahtml+"<OPTION VALUE='"+cvo_data[z].id+"'>"+cvo_data[z].cvo_name+"</OPTION>";
	}
}

var checkNum=checkDisplay;
if(checkNum=="1"){
	document.getElementById("myModalcreditPin").style="none";
var tbody = document.getElementById('credit_limit_data_table_body');
for(var f=page_data.length-1; f>=0; f--) {
	var custName = getCustomerName(cvo_data,page_data[f].cust_id);
	if(page_data[f].disc_19kg_ndne == '') {
	var tblRow = tbody.insertRow(-1);
    tblRow.style.height="20px";
    tblRow.align="left";
    tblRow.innerHTML = '<td align="center">'+custName +'</td>'+
    	'<td align="center">'+ page_data[f].credit_limit +'</td>'+
       	'<td align="center">'+  page_data[f].credit_days +'</td>'+
       	
       	'<td align="center">'+ page_data[f].disc_19kg_ndne +'</td>'+
    	'<td align="center">'+ page_data[f].disc_19kg_cutting_gas +'</td>'+
    	'<td align="center">'+ page_data[f].disc_35kg_ndne +'</td>'+
    	'<td align="center">'+ page_data[f].disc_47_5kg_ndne +'</td>'+
    	'<td align="center">'+ page_data[f].disc_450kg_sumo +'</td>'+

       	'<td align="center">'+ ccontrols[page_data[f].cc_type] +'</td>'+
       	'<td valign="top" height="4" align="center"><img src="images/delete.png" class="fa fa-trash-o" onclick="deleteItem('+page_data[f].id+')"></td>'
	}else{
		var tblRow = tbody.insertRow(-1);
	    tblRow.style.height="20px";
	    tblRow.align="left";
	    tblRow.innerHTML = '<td align="center">'+custName +'</td>'+
	    	'<td align="center">'+ page_data[f].credit_limit +'</td>'+
	       	'<td align="center">'+  page_data[f].credit_days +'</td>'+
	       	
	       	'<td align="center">'+ page_data[f].disc_19kg_ndne +'</td>'+
	    	'<td align="center">'+ page_data[f].disc_19kg_cutting_gas +'</td>'+
	    	'<td align="center">'+ page_data[f].disc_35kg_ndne +'</td>'+
	    	'<td align="center">'+ page_data[f].disc_47_5kg_ndne +'</td>'+
	    	'<td align="center">'+ page_data[f].disc_450kg_sumo +'</td>'+

	       	'<td align="center">'+ ccontrols[page_data[f].cc_type] +'</td>'+
	       	'<td valign="top" height="4" align="center"><img src="images/delete.png" class="fa fa-trash-o" onclick="deleteItem('+page_data[f].id+')">'+
	       	'<input type=button name="edit_btn" id="edit_btn"  size="6" value="EDIT" class="fa fa-trash-o" onclick="editCreditlimitsData('+page_data[f].cust_id+','+page_data[f].disc_19kg_ndne+','+page_data[f].disc_19kg_cutting_gas+','+page_data[f].disc_35kg_ndne+','+page_data[f].disc_47_5kg_ndne+','+page_data[f].disc_450kg_sumo+')"></td>'

		
	}
};
}
else {
	var tbody = document.getElementById('credit_limit_data_table_body');
	for(var f=page_data.length-1; f>=0; f--) {
		var custName = getCustomerName(cvo_data,page_data[f].cust_id);
		if(page_data[f].disc_19kg_ndne == '')
		{
		var tblRow = tbody.insertRow(-1);
	    tblRow.style.height="20px";
	    tblRow.align="left";
	    tblRow.innerHTML = '<td align="center">'+custName +'</td>'+
	    	'<td align="center">'+ page_data[f].credit_limit +'</td>'+
	       	'<td align="center">'+  page_data[f].credit_days +'</td>'+
	       	
	       	'<td align="center">'+ page_data[f].disc_19kg_ndne +'</td>'+
	    	'<td align="center">'+ page_data[f].disc_19kg_cutting_gas +'</td>'+
	    	'<td align="center">'+ page_data[f].disc_35kg_ndne +'</td>'+
	    	'<td align="center">'+ page_data[f].disc_47_5kg_ndne +'</td>'+
	    	'<td align="center">'+ page_data[f].disc_450kg_sumo +'</td>'+

	       	'<td align="center">'+ ccontrols[page_data[f].cc_type] +'</td>'+
	       	'<td valign="top" height="4" align="center"><img src="images/delete.png" class="fa fa-trash-o" onclick="deleteItem('+page_data[f].id+')"></td>'

		}else{
			var tblRow = tbody.insertRow(-1);
		    tblRow.style.height="20px";
		    tblRow.align="left";
		    tblRow.innerHTML = '<td align="center">'+custName +'</td>'+
		    	'<td align="center">'+ page_data[f].credit_limit +'</td>'+
		       	'<td align="center">'+  page_data[f].credit_days +'</td>'+
		       	
		       	'<td align="center">'+ page_data[f].disc_19kg_ndne +'</td>'+
		    	'<td align="center">'+ page_data[f].disc_19kg_cutting_gas +'</td>'+
		    	'<td align="center">'+ page_data[f].disc_35kg_ndne +'</td>'+
		    	'<td align="center">'+ page_data[f].disc_47_5kg_ndne +'</td>'+
		    	'<td align="center">'+ page_data[f].disc_450kg_sumo +'</td>'+

		       	'<td align="center">'+ ccontrols[page_data[f].cc_type] +'</td>'+
		       	'<td valign="top" height="4" align="center"><img src="images/delete.png" class="fa fa-trash-o" onclick="deleteItem('+page_data[f].id+')">'+
		       	'<input type=button name="edit_btn" id="edit_btn"  size="6" value="EDIT" class="fa fa-trash-o" onclick="editCreditlimitsData('+page_data[f].cust_id+','+page_data[f].disc_19kg_ndne+','+page_data[f].disc_19kg_cutting_gas+','+page_data[f].disc_35kg_ndne+','+page_data[f].disc_47_5kg_ndne+','+page_data[f].disc_450kg_sumo+')"></td>'

		}
		

	};	
}

function addRow() {
	document.getElementById('myDIV').style.display = 'block';
	document.getElementById('savediv').style.display="inline";
    $("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');

    var trcount = document.getElementById('credit_limit_add_table_body').getElementsByTagName('tr').length;
    if(trcount>0){
    	var trv=document.getElementById('credit_limit_add_table_body').getElementsByTagName('tr')[trcount-1];
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

	var ele = document.getElementsByClassName("cl");
	if(ele.length < 4){
		var tbody = document.getElementById('credit_limit_add_table_body');
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
		var j = newRow.insertCell(9);

		a.innerHTML = "<td><SELECT NAME='cid' ID='cid'  class='form-control input_field select_dropdown sadd' style='width:150px;'>"+custdatahtml+"</select></td>";
		b.innerHTML = "<td><INPUT TYPE=text NAME='cl' ID='cl' class='form-control input_field eadd cl' style='width:100px;' placeholder='Credit Limit'></td>";
		c.innerHTML = "<td><INPUT TYPE=text NAME='cd' ID='cd' class='form-control input_field eadd' style='width:100px;' placeholder='Credit Days'></td>";
		d.innerHTML = "<td><INPUT TYPE=text NAME='ccyl1' ID='ccyl1' class='form-control input_field eadd' style='width:75px;' value='0.00'></td>";
		e.innerHTML = "<td><INPUT TYPE=text NAME='ccyl2' ID='ccyl2' class='form-control input_field eadd' style='width:75px;' value='0.00'></td>";
		f.innerHTML = "<td><INPUT TYPE=text NAME='ccyl3' ID='ccyl3' class='form-control input_field eadd' style='width:75px;' value='0.00'></td>";
		g.innerHTML = "<td><INPUT TYPE=text NAME='ccyl4' ID='cclyl4' class='form-control input_field eadd' style='width:75px;' value='0.00'></td>";
		h.innerHTML = "<td><INPUT TYPE=text NAME='ccyl5' ID='ccyl5' class='form-control input_field eadd' style='width:75px;' value='0.00'></td>";

		i.innerHTML = "<td><SELECT NAME='cc' ID='cc'  class='form-control input_field select_dropdown sadd' style='width:100px;'>"
			+ "<OPTION VALUE='0'>SELECT</OPTION>"
			+ "<OPTION VALUE='1'>BLOCK</OPTION>"
			+ "<OPTION VALUE='2'>WARN</OPTION>"+ "</SELECT></td>";
		j.innerHTML = "<td><img src='images/delete.png' onclick='doRowDelete(this)'></td>";
	}else{
		document.getElementById("dialog-1").innerHTML = "Please Save the Records and ADD Again";
		alertdialogue();
		//alert("Please Save the Records and ADD Again");
	}
}

function saveData(obj) {
	var formobj = document.getElementById('credit_limit_form');
	var ems = "";
	var  ecusta =new Array();

	if (document.getElementById("cid") != null) {

		var elements = document.getElementsByClassName("cl");
		if (elements.length == 1) {
			var eclname = formobj.cid.value;
			var eclcl = formobj.cl.value.trim();
			var eclcd = formobj.cd.value.trim();
		
			var eccyl1 = formobj.ccyl1.value.trim();
			var eccyl2 = formobj.ccyl2.value.trim();
			var eccyl3 = formobj.ccyl3.value.trim();
			var eccyl4 = formobj.ccyl4.value.trim();
			var eccyl5 = formobj.ccyl5.value.trim();
			var eclcontrol = formobj.cc.selectedIndex;

			formobj.cl.value = formobj.cl.value.trim();
			formobj.cd.value = formobj.cd.value.trim();		
			formobj.ccyl1.value = formobj.ccyl1.value.trim();
			formobj.ccyl2.value = formobj.ccyl2.value.trim();
			formobj.ccyl3.value = formobj.ccyl3.value.trim();
			formobj.ccyl4.value = formobj.ccyl4.value.trim();
			formobj.ccyl5.value = formobj.ccyl5.value.trim();
			
			ems = validateCLEntries(eclname, eclcl,eclcd,eccyl1,eccyl2,eccyl3,eccyl4,eccyl5, eclcontrol, ecusta);
		} else if (elements.length > 1) {
			for (var i = 0; i < elements.length; i++) {
				var eclname = formobj.cid[i].value;
				var eclcl = formobj.cl[i].value.trim();
				var eclcd = formobj.cd[i].value.trim()
				
				var eccyl1 = formobj.ccyl1[i].value.trim();
				var eccyl2 = formobj.ccyl2[i].value.trim();
				var eccyl3 = formobj.ccyl3[i].value.trim();
				var eccyl4 = formobj.ccyl4[i].value.trim();
				var eccyl5 = formobj.ccyl5[i].value.trim();
				var eclcontrol = formobj.cc[i].selectedIndex;

				var eclcontrol = formobj.cc[i].selectedIndex;
				ecusta.push(eclname);
				ems = validateCLEntries(eclname, eclcl,eclcd,eccyl1,eccyl2,eccyl3,eccyl4,eccyl5, eclcontrol, ecusta);
				if (ems.length > 0)
					break;
				
				formobj.cl[i].value = formobj.cl[i].value.trim();
				formobj.cd[i].value = formobj.cd[i].value.trim()
				
				formobj.ccyl1[i].value = formobj.ccyl1[i].value.trim();
				formobj.ccyl2[i].value = formobj.ccyl2[i].value.trim();
				formobj.ccyl3[i].value = formobj.ccyl3[i].value.trim();
				formobj.ccyl4[i].value = formobj.ccyl4[i].value.trim();
				formobj.ccyl5[i].value = formobj.ccyl5[i].value.trim();
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
		var formobj = document.getElementById('credit_limit_form');
		formobj.actionId.value = "3553";
		formobj.itemId.value = id;
		formobj.submit();
	}
}*/

function deleteItem(id){
	 $("#myDialogText").text("Are You Sure You Want To Delete?");
	 var formobj = document.getElementById('credit_limit_form');
	 confirmDialogue(formobj,3553,id);
}

function editCreditlimitsData(custid,dis19kgndne,dis19kgcutgas,dis35kgndne,dis47_5kgndne,dis450kgsumo) {
	//for(var f=page_data.length-1; f>=0; f--) {

	document.getElementById("cid").value =custid;

		var custName = getCustomerName(cvo_data,custid);

    document.getElementById("cpname").value =custName;
    document.getElementById("dis19kgndne").value = dis19kgndne;
     document.getElementById("dis19kgcgas").value = dis19kgcutgas;
    document.getElementById("dis35kgndne").value = dis35kgndne;
    document.getElementById("dis47_5kgndne").value = dis47_5kgndne;
    document.getElementById("dis450kgsumo").value = dis450kgsumo;

	//}
    document.getElementById("updatecrdismodel").style.display = "block";
}

function closeChangeCreditlimitsData() {
    document.getElementById('updatecrdismodel').style.display = "none";

}

function submitUpdatedCreditlimitsData(custid,dis19kgndne,dis19kgcutgas,dis35kgndne,dis47_5kgndne,dis450kgsumo) {

		var formobj = document.getElementById('discount_form');
	    var dis19kgndne = formobj.dis19kgndne.value.trim();
	    var dis19kgcutgas = formobj.dis19kgcgas.value.trim();
	    var dis35kgndne = formobj.dis35kgndne.value.trim();
	    var dis47_5kgndne = formobj.dis47_5kgndne.value.trim();
	    var dis450kgsumo = formobj.dis450kgsumo.value.trim();

		  var ems="";

		    if((dis19kgndne==""))
		            ems= ems+"Please Enter Valid Discount for 19kg NDNE Cylinder. If Not,Enter 0 \n";
		    else if (validateDot(dis19kgndne))
		    	ems = ems+"DISCOUNT ON UNIT PRICE Of 19kg NDNE Cylinder Must Contain Atleast One Number \n";
			else if(!(validateDecimalNumberMinMax(dis19kgndne,-1,10000)))
				ems = ems + "Please Enter Valid Disount for 19kg NDNE Cylinder\n";

		     if((dis19kgcutgas==""))
	            ems= ems+"Please Enter Valid Discount for 19kg Cutting Gas Cylinder. If Not,Enter 0 \n";
		     else if (validateDot(dis19kgcutgas))
		    	 ems = ems+"DISCOUNT ON UNIT PRICE Of 19kg CUTTING GAS Cylinder Must Contain Atleast One Number \n";
		 	else if(!(validateDecimalNumberMinMax(dis19kgcutgas,-1,10000)))
		 		ems = ems + "Please Enter Valid Disount for 19kg CUTTING GAS Cylinder\n";

		     if((dis35kgndne==""))
	            ems= ems+"Please Enter Valid Discount for 35kg NDNE Cylinder. If Not,Enter 0 \n";
		     else if (validateDot(dis35kgndne))
		    	 ems = ems+"DISCOUNT ON UNIT PRICE of 35kg NDNE Cylinder Must Contain Atleast One Number \n";
		 	else if(!(validateDecimalNumberMinMax(dis35kgndne,-1,10000)))
		 		ems = ems + "Please Enter Valid Disount for 35kg NDNE Cylinder\n";

		     if((dis47_5kgndne==""))
	            ems= ems+"Please Enter Valid Discount for 47.5kg NDNE Cylinder. If Not,Enter 0 \n";
		     else if (validateDot(dis47_5kgndne))
		    	 ems = ems+"DISCOUNT ON UNIT PRICE Of 47.5kg NDNE Cylinder Must Contain Atleast One Number \n";
		 	else if(!(validateDecimalNumberMinMax(dis47_5kgndne,-1,10000)))
		 		ems = ems + "Please Enter Valid Disount for 47.5kg NDNE Cylinder\n";

		     if((dis450kgsumo==""))
	            ems= ems+"Please Enter Valid Discount for 450kg NDNE Cylinder. If Not,Enter 0 \n";
		     else if (validateDot(dis450kgsumo))
		    	 ems = ems+"DISCOUNT ON UNIT PRICE Of 450kg SUMO Cylinder Must Contain Atleast One Number \n";
		 	else if(!(validateDecimalNumberMinMax(dis450kgsumo,-1,10000)))
		 		ems = ems + "Please Enter Valid Disount for 450kg SUMO Cylinder\n";


		    if (ems.length > 0) {
	            alert(ems);
	            return;
	    }else{

		formobj.submit();

	    }
}

function validateCLEntries(clname, cllimit, cldays,clcomcly1,clcomcly2,clcomcly3,clcomcly4,clcomcly5, clcontrol, clcusta) {
	var errmsg = "";
	var formobj = document.getElementById('credit_limit_form');

	var cflag =0;
    
   	if(clcusta.length>1 && (!(clcusta.indexOf(clname))))
   		 cflag =1; 
   	for(var f=0; f<page_data.length; f++)
   	{     		
   	   	     if((page_data[f].cust_id == clname))
           	  cflag =1; 	 
    }
	if (!(clname > 0))
		errmsg = "Please Select Customer .<br>";
	else if(cflag == 1)
            errmsg = errmsg + "CUSTOMER already exist.Please Select different CUSTOMER<br>"

	if (!(cllimit.length > 0))
		errmsg = errmsg + "Please Enter Credit Limit.<br>";
	else if (validateDot(cllimit))
		errmsg = errmsg + "cllimit must contain atleast one number<br>";
	else if (!(validateDecimalNumberMinMax(cllimit, 0, 10000000)))
		errmsg = errmsg + "Please Enter valid Credit Limit<br>";
	else
		formobj.cl.value = round(parseFloat(cllimit), 2);

	if (!(cldays.length > 0))
		errmsg = errmsg + "Please Enter Credit Days.<br>";
	else	if (cldays === "0")
		errmsg = errmsg + "Please Enter Valid Credit Days.<br>";
	else if (validateDot(cldays))
		errmsg = errmsg + "Credit Days must contain atleast one number OR Enter Zero<br>";
	else if(cldays.length>3)
		errmsg = errmsg + "Please enter valid Credit Days<br>";
	else if (!(validateDecimalNumberMinMax(cldays, 0, 1000)))
		errmsg = errmsg + "Please Enter valid Credit Days<br>";
	
	if (!(clcomcly1.length > 0))
		errmsg = errmsg + "Please Enter Disount for 19kg NDNE Cylinder.\n";
	else if (validateDot(clcomcly1))
		errmsg = errmsg+"Discount On Unit Price Of 19kg NDNE Cylinder Must Contain Atleast One Number \n";
	else if(!(validateDecimalNumberMinMax(clcomcly1,-1,10000)))
		errmsg = errmsg + "Please Enter Valid Disount for 19kg NDNE Cylinder\n";
	/*else if(parseFloat(clcomcly1)>parseFloat(up))
		errmsg = errmsg+"DISCOUNT ON UNIT PRICE must be less than UNIT PRICE.\n";
*/	/*else
		formobj.clcomcly1.value=round(parseFloat(clcomcly1),2);
*/

	if (!(clcomcly2.length > 0))
		errmsg = errmsg + "Please Enter Disount for 19kg Cutting Gas Cylinder.\n";
	else if (validateDot(clcomcly2))
		errmsg = errmsg+"Discount On Unit Price Of 19kg Cutting Gas Cylinder Must Contain Atleast One Number \n";
	else if(!(validateDecimalNumberMinMax(clcomcly2,-1,10000)))
				errmsg = errmsg + "Please Enter Valid Disount for 19kg Cutting Gas Cylinder\n";

	if (!(clcomcly3.length > 0))
		errmsg = errmsg + "Please Enter Disount for 35kg NDNE Cylinder.\n";
	else if (validateDot(clcomcly3))
		errmsg = errmsg+"Discount On Unit Price Of 35kg NDNE Cylinder Must Contain Atleast One Number \n";
	else if(!(validateDecimalNumberMinMax(clcomcly3,-1,10000)))
		errmsg = errmsg + "Please Enter Valid Disount for 35kg NDNE Cylinder\n";

	if (!(clcomcly4.length > 0))
		errmsg = errmsg + "Please Enter Disount for 47.5kg NDNE cylinder.\n";
	else if (validateDot(clcomcly4))
		errmsg = errmsg+"Discount On Unit Price Of 47.5kg NDNE Cylinder Must Contain Atleast One Number \n";
	else if(!(validateDecimalNumberMinMax(clcomcly4,-1,10000)))
				errmsg = errmsg + "Please Enter Valid Disount for 47.5kg NDNE Cylinder\n";

	if (!(clcomcly5.length > 0))
		errmsg = errmsg + "Please Enter Disount for 450kg SUMO Cylinder.\n";
	else if (validateDot(clcomcly5))
		errmsg = errmsg+"Discount On Unit Price Of 450kg SUMO Cylinder Must Contain Atleast One Number \n";
	else if(!(validateDecimalNumberMinMax(clcomcly5,-1,10000)))
				errmsg = errmsg + "Please Enter Valid Disount for 450kg SUMO Cylinder\n";

	if (!(clcontrol > 0))
		errmsg = errmsg + "Please Select Credit Control.<br>";

	return errmsg;
}

function submitPinNumber() {
	var pinNO = document.getElementById("pinNO").value.trim();
	var enteredPin = document.getElementById("enteredPin").value.trim();
	var ems="";
	if(!(enteredPin.length>0))
		ems= ems+"Please Enter PIN NUMBER<br>";
	else if(!validatePinNumber(enteredPin,4,4))
		ems = ems+"PIN NUMBER Must Be Valid  4 DIGIT Number <br>";
	else if(enteredPin!==pinNO)
		ems= ems+"Please Enter Valid PIN NUMBER<br>";
	else if(enteredPin != (document.getElementById("enteredPin").value))
		ems= ems+"No spaces are allowed in pin Number\n";
	else if(enteredPin==pinNO){
		document.getElementById('myModalcreditPin').style.display="none";
		document.getElementById('pageDiv').style.display="block";
	}
	if (ems.length > 0) {
		document.getElementById("dialog-1").innerHTML = ems;
		alertdialogue();
		//alert(ems);
		return;
	}
}