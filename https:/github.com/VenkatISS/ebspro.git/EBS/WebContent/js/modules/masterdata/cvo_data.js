var tbody = document.getElementById('cvo_data_table_body');
var dq='"';
var count=-1;
for(var f = dcvo_data.length-1 ; f >= 0 ; f--) {
	if(dcvo_data[f].deleted == 0) {
		var uhtml;
		var ihtml ="<td></td>"
			  if(dcvo_data[f].obal=='0.00' && dcvo_data[f].cvo_name == 'UJWALA'){
                  ihtml = "<td align='center'><button name='update_balance_popup' class='updatebutton' id='update_balance_popup'  onclick='showUpdateBalanceFormDialog("+dcvo_data[f].id+","+dq+dcvo_data[f].cvo_name+dq+")'>UPDATE</button></td>";
          }
          else if(dcvo_data[f].obal!=0 && dcvo_data[f].cvo_name == 'UJWALA'){
                  ihtml = "<td align='center'><button name='update_balance_popup' id='update_balance_popup'  onclick='showUpdateBalanceFormDialog()' disabled>UPDATE</button></td>";
          }
          else if (!(dcvo_data[f].cvo_name == 'UJWALA' &&  dcvo_data[f].cvo_email == "NA" && dcvo_data[f].cvo_cat == 1)) {
          ihtml = "<td align='center'><img src='images/delete.png' class='fa fa-trash-o' onclick='deleteItem("+dcvo_data[f].id+")'></td>";
  }

		if(dcvo_data[f].cvo_name == 'UJWALA')
			var tblRow = tbody.insertRow(0);
		else
			var tblRow = tbody.insertRow(-1);
		tblRow.style.height="20px";
		tblRow.align="left";
		 if(dcvo_data[f].cvo_name == 'UJWALA')
             uhtml =dcvo_data[f].cvo_name;
             else
                     uhtml = "<a href='javascript:showInvoice("+dq+dcvo_data[f].cvo_name+dq+","+dcvo_data[f].cvo_cat+","+dcvo_data[f].is_gst_reg+","+dq+dcvo_data[f].cvo_tin+dq+","+dq+dcvo_data[f].cvo_pan+dq+","+dq+dcvo_data[f].cvo_address+dq+","+dq+dcvo_data[f].cvo_contact+dq+","+dq+dcvo_data[f].cvo_email+dq+","+dq+dcvo_data[f].obal+dq+","+dq+dcvo_data[f].cbal+dq+","+dq+dcvo_data[f].id+dq+")'>"+dcvo_data[f].cvo_name+'</a>';
		/*tblRow.innerHTML = '<td>'+dcvo_data[f].cvo_name +'</td>'+
			'<td>'+ categories[dcvo_data[f].cvo_cat]+'</td>'+
			'<td>'+ gstyn[dcvo_data[f].is_gst_reg] +'</td>'+
			'<td>'+ dcvo_data[f].cvo_tin +'</td>'+
			'<td>'+ dcvo_data[f].cvo_pan +'</td>'+
			'<td>'+ dcvo_data[f].cvo_address +'</td>'+
			'<td>'+ dcvo_data[f].cvo_contact +'</td>'+
			'<td>'+ dcvo_data[f].cvo_email +'</td>'+
			'<td>'+ dcvo_data[f].obal +'</td>'+
			'<td>'+ dcvo_data[f].cbal +'</td>'+ihtml*/
		
		tblRow.innerHTML = "<td>"+uhtml+"</td>"+
        //tblRow.innerHTML = "<td><a href='javascript:showInvoice("+dcvo_data[f]+")'>"+dcvo_data[f].cvo_name+'</a></td>'+
                "<td>"+ categories[dcvo_data[f].cvo_cat]+"</td>"+
                "<td>"+ gstyn[dcvo_data[f].is_gst_reg] +"</td>"+
                "<td>"+ dcvo_data[f].cvo_tin +"</td>"+
                "<td>"+ dcvo_data[f].cvo_pan +"</td>"+
                "<td>"+ dcvo_data[f].cvo_address +"</td>"+
                "<td>"+ dcvo_data[f].cvo_contact +"</td>"+
                '<td>'+ dcvo_data[f].cvo_email +'</td>'+
                "<td>"+ dcvo_data[f].obal +"</td>"+
                "<td>"+ dcvo_data[f].cbal +"</td>"+ihtml
			/*'<td valign="top" height="4" align="center"><img src="images/delete.png" class="fa fa-trash-o" onclick="deleteItem('+dcvo_data[f].id+')"></td>'*/
		
	}
};

function showUpdateBalanceFormDialog(id,name){

    document.getElementById('ujwalaBalanceUpdatePopup').style.display = 'block';
    document.getElementById('bId').value=id;
    document.getElementById('bname').value=name;

}

function closeUjwalaBalanceUpdatePopup(){

    document.getElementById('ujwalaBalanceUpdatePopup').style.display = 'none';
    document.getElementById("update_balance_form").reset();

}
//set ujwala balance validations
function submitSetBalanceDetails(formobj) {
        var balAmount = formobj.bankbalamt.value.trim();

        var ems="";
        if(!(balAmount.length>0))
                ems= ems+"Please Enter Balance Amont<br>";
        else if(!validateAmount(balAmount))
                ems = ems+"Please Enter valid Balance<br>";
        else if(!(parseFloat(balAmount)<10000000))
        	ems = ems + "Balance must be less than 10000000<br>";
        
        
        if (ems.length > 0) {
                document.getElementById("dialog-1").innerHTML = ems;
                alertdialogue();
                return false;
        }
}

function closeCVOPopupFormDialog(){
	document.getElementById('myModal').style.display = "none";
    $("body").removeClass('sidebar-collapse').trigger('expanded.pushMenu');
}
function addRow() {
	document.getElementById('myDIV').style.display = 'block';
	document.getElementById('savediv').style.display="inline";
    $("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');

    var trcount = document.getElementById('cvo_add_table_body').getElementsByTagName('tr').length;
    if(trcount>0){
    	var trv=document.getElementById('cvo_add_table_body').getElementsByTagName('tr')[trcount-1];
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

	var ele = document.getElementsByClassName("cvoc");
	if(ele.length < 4){
	    var tbody = document.getElementById('cvo_add_table_body');
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
    	
    	a.innerHTML = "<td valign='top' height='4' align='center'><select name='cvo_cat' id='cvo_cat' class='form-control input_field select_dropdown sadd cvocat ' onchange='changePartyType(this)' style='width:120px;' autofocus>"
    		+ "<OPTION VALUE='-1'>SELECT</OPTION>"
    		+ "<OPTION VALUE='0'>VENDOR</OPTION>"
    		+ "<OPTION VALUE='1'>CUSTOMER</OPTION>"
    		+ "<OPTION VALUE='2'>OMC-"+omc_name+"</OPTION>"
    		+ "<OPTION VALUE='3'>OTHERS</OPTION>" + "</SELECT></td>";
    	b.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='cvo_name' id='cvo_name' class='form-control input_field cvoc eadd' size='8' maxlength='35' placeholder='Party Name' autofocus></td>";
    	c.innerHTML = "<td valign='top' height='4' align='center'><select name='gstyn' id='gstyn' class='form-control input_field select_dropdown sadd'  onchange='changeRegYN()' style='width:100px;'autofocus>"
    		+"<OPTION VALUE='0'>SELECT</OPTION>"
    		+ "<OPTION VALUE='1'>YES</OPTION>"
    		+"<OPTION VALUE='2'>NO</OPTION>"+"</SELECT></td>";
    	d.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='cvo_tin' id='cvo_tin' onchange='checkForDeletedCVO(this)' class='form-control input_field eadd regyn' maxlength='15' size='8' placeholder='GSTIN' autofocus></td>";
    	e.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='cvo_pan' id='cvo_pan' class='form-control input_field regyn'  maxlength='10' size='8' placeholder='PAN' autofocus></td>";
    	f.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='cvo_addr' id='cvo_addr' class='form-control input_field eadd' size='8' maxlength='100' placeholder='Address' autofocus></td>";
    	g.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='cvo_contact' id='cvo_contact'  class='form-control input_field eadd' size='8' maxlength='10' placeholder='Contact' autofocus></td>";
    	h.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='cvo_email' id='cvo_email' class='form-control input_field eadd' size='8' maxlength='50' placeholder='Email' autofocus></td>";
    	i.innerHTML = "<td valign='top' height='4' align='center'><input type=text name='cvo_ob' id='cvo_ob' class='form-control input_field eadd'  maxlength='11' value='0.00' size='8' placeholder='OPENING BALANCE' autofocus></td>";
    	j.innerHTML = "<td valign='top' height='4' align='center'><img src='images/delete.png' onclick='doRowDelete(this)'></td>";
    }else {
    	document.getElementById("dialog-1").innerHTML = "Please Save the Records and ADD Again";
		alertdialogue();
    	//alert("Please Save the Records and ADD Again");
    }
}

function checkForDeletedCVO(cvoObj){
	var formobj = document.getElementById('cvo_data_form');
	var clsname = cvoObj.className;	
	var ele = document.getElementsByClassName(clsname);
	if(ele.length==1){
		var cvoName = formobj.cvo_name.value.trim();
		var cvotin = formobj.cvo_tin.value.trim();
		var cvocat = formobj.cvo_cat.value;
		var sgstyn = document.getElementById("gstyn").options;
		var scvocat = document.getElementById("cvo_cat").options;		
		for(var d=0;d<dcvo_data.length;d++) {
			if((dcvo_data[d].deleted !=0) && ((dcvo_data[d].cvo_tin).trim() === cvotin) && (cvotin.toUpperCase() != "NA") ) {
				if(parseInt(cvocat) != 2) {
					if(dcvo_data[d].cvo_cat == parseInt(cvocat)) {
						// FOR CVO CATEGORY
						var cat = dcvo_data[d].cvo_cat;
						enableSelect(scvocat,scvocat.length);
						switch(cat){
							case 0 :
								formobj.cvo_cat.selectedIndex = 1;
								disableSelect(scvocat,scvocat.length);
								break;
							case 1 :
								formobj.cvo_cat.selectedIndex= 2;
								disableSelect(scvocat,scvocat.length);
								break;
							case 2 :
								formobj.cvo_cat.selectedIndex= 3;
								disableSelect(scvocat,scvocat.length);
								break;
							case 3 :
								formobj.cvo_cat.selectedIndex= 4;
								disableSelect(scvocat,scvocat.length);
								break;
							default :
								formobj.cvo_cat.selectedIndex= 0;
								disableSelect(scvocat,scvocat.length);
								break;
						}

						//FOR CVO NAME
						if(cvoName == ""){
							var cvoname = dcvo_data[d].cvo_name;
							formobj.cvo_name.value = cvoname.trim();
							document.getElementById("cvo_name").setAttribute("readOnly","false");
//							document.getElementById("cvo_name").readOnly = false;
						}						
						
						// FOR GSTYN
						var gstyn = dcvo_data[d].is_gst_reg;
						enableSelect(sgstyn,sgstyn.length);					
						if(gstyn==1){
							formobj.gstyn.selectedIndex=1;
							disableSelect(sgstyn,sgstyn.length);
						}else if(gstyn==2){
							formobj.gstyn.selectedIndex=2;
							disableSelect(sgstyn,sgstyn.length);
						}
						//FOR PAN
						var pan = dcvo_data[d].cvo_pan;
						formobj.cvo_pan.value = pan;
						document.getElementById("cvo_pan").setAttribute("readOnly","true");

						// For OB
						var cvocbal = dcvo_data[d].cbal;
						formobj.cvo_ob.value = cvocbal;
						document.getElementById("cvo_ob").setAttribute("readOnly","true");
						
						break;
					}else {
						enableSelect(scvocat,scvocat.length);
						enableSelect(sgstyn,sgstyn.length);
						document.getElementById("cvo_pan").readOnly = false;
						document.getElementById("cvo_ob").readOnly = false;
					}
				}else {
					if(dcvo_data[d].cvo_cat == parseInt(cvocat)) {
						// FOR CVO CATEGORY
						var cat = dcvo_data[d].cvo_cat;
						enableSelect(scvocat,scvocat.length);
						
						formobj.cvo_cat.selectedIndex= 3;
						disableSelect(scvocat,scvocat.length);

						//FOR CVO NAME
						if(formobj.cvo_name.value == ""){
							var cvoname = dcvo_data[d].cvo_name;
							formobj.cvo_name.value = cvoname;
//							document.getElementById("cvo_name").setAttribute("readOnly","true");
							document.getElementById("cvo_name").readOnly = false;
						}
						
						// FOR GSTYN
						var gstyn = dcvo_data[d].is_gst_reg;
						enableSelect(sgstyn,sgstyn.length);					
						if(gstyn==1){
							formobj.gstyn.selectedIndex=1;
							disableSelect(sgstyn,sgstyn.length);
						}else if(gstyn==2){
							formobj.gstyn.selectedIndex=2;
							disableSelect(sgstyn,sgstyn.length);
						}
						//FOR PAN
						var pan = dcvo_data[d].cvo_pan;
						formobj.cvo_pan.value = pan;
						document.getElementById("cvo_pan").setAttribute("readOnly","true");
						// For OB
						formobj.cvo_ob.value = "0.00";
						document.getElementById("cvo_ob").setAttribute("readOnly","true");

						break;
					}else {
						enableSelect(scvocat,scvocat.length);
						enableSelect(sgstyn,sgstyn.length);
						document.getElementById("cvo_pan").readOnly = false;							
					}
				}
			}else {
				// FOR CVO CATEGORY
				enableSelect(scvocat,scvocat.length);
				//FOR PAN
				document.getElementById("cvo_pan").readOnly = false;
				document.getElementById("cvo_name").readOnly = false;
				if(parseInt(cvocat)!=2) {
					// FOR GSTYN
					enableSelect(sgstyn,sgstyn.length);
					// For OB
					document.getElementById("cvo_ob").readOnly = false;
				}else {
					formobj.cvo_ob.value = "0.00";
					document.getElementById("cvo_ob").setAttribute("readOnly","true");
				}
			}
		}	
	}else if(ele.length>1){
		for(var e=0;e<ele.length;e++){
			var cvoName = formobj.cvo_name[e].value.trim();
			var cvotin = formobj.cvo_tin[e].value.trim();
			var cvocat = formobj.cvo_cat[e].value;
			var sgstyn = formobj.gstyn[e].options;
			var scvocat = formobj.cvo_cat[e].options;			
			for(var d=0;d<dcvo_data.length;d++) {
				if((dcvo_data[d].deleted !=0) && ((dcvo_data[d].cvo_tin).trim() === cvotin) && (cvotin.toUpperCase() != "NA")) {
					if(parseInt(cvocat)!=2) {
						if(dcvo_data[d].cvo_cat == parseInt(cvocat)) {
							// FOR CVO CATEGORY
							var cat = dcvo_data[d].cvo_cat;
							enableSelect(scvocat,scvocat.length);
							switch(cat){
								case 0 :
									formobj.cvo_cat[e].selectedIndex = 1;
									disableSelect(scvocat,scvocat.length);
									break;
								case 1 :
									formobj.cvo_cat[e].selectedIndex= 2;
									disableSelect(scvocat,scvocat.length);
									break;
								case 2 :
									formobj.cvo_cat[e].selectedIndex= 3;
									disableSelect(scvocat,scvocat.length);
									break;
								case 3 :
									formobj.cvo_cat[e].selectedIndex= 4;
									disableSelect(scvocat,scvocat.length);
									break;
								default :
									formobj.cvo_cat[e].selectedIndex= 0;
									disableSelect(scvocat,scvocat.length);
									break;
							}
								
							// FOR GSTYN
							var gstyn = dcvo_data[d].is_gst_reg;
							enableSelect(sgstyn,sgstyn.length);					
							if(gstyn==1){
								formobj.gstyn[e].selectedIndex=1;
								disableSelect(sgstyn,sgstyn.length);
							}else if(gstyn==2){
								formobj.gstyn[e].selectedIndex=2;
								disableSelect(sgstyn,sgstyn.length);
							}
						
							//FOR PAN
							var pan = dcvo_data[d].cvo_pan;
							formobj.cvo_pan[e].value = pan;
							formobj.cvo_pan[e].setAttribute("readOnly","true");
							
							// For OB
							var cvocbal = dcvo_data[d].cbal;
							formobj.cvo_ob[e].value = cvocbal;
							formobj.cvo_ob[e].setAttribute("readOnly","true");
								
							break;
						}else {
							enableSelect(scvocat,scvocat.length);							
							enableSelect(sgstyn,sgstyn.length);
							formobj.cvo_pan[e].readOnly = false;
							formobj.cvo_ob[e].readOnly = false;
						}
					}else {
						if(dcvo_data[d].cvo_cat == parseInt(cvocat)) {
							// FOR GSTYN
							var gstyn = dcvo_data[d].is_gst_reg;
							enableSelect(sgstyn,sgstyn.length);					
							if(gstyn==1){
								formobj.gstyn[e].selectedIndex=1;
								disableSelect(sgstyn,sgstyn.length);
							}else if(gstyn==2){
								formobj.gstyn[e].selectedIndex=2;
								disableSelect(sgstyn,sgstyn.length);
							}	

							if(cvoName == ""){
								var cvoname = dcvo_data[d].cvo_name;
								formobj.cvo_name[e].value = cvoname.trim();
//								formobj.cvo_pan[e].setAttribute("readOnly","true");
								formobj.cvo_name[e].readOnly = false;
							}
							
							//FOR PAN
							var pan = dcvo_data[d].cvo_pan;
							formobj.cvo_pan[e].value = pan;
							formobj.cvo_pan[e].setAttribute("readOnly","true");
							
							// For OB
							var cvocbal = dcvo_data[d].cbal;
							formobj.cvo_ob[e].value = cvocbal;
							formobj.cvo_ob[e].setAttribute("readOnly","true");

							break;
						}else {
							enableSelect(scvocat,scvocat.length);
							enableSelect(sgstyn,sgstyn.length);
							formobj.cvo_pan[e].readOnly = false;
						}	
					}
				}else {				
					enableSelect(scvocat,scvocat.length);
					formobj.cvo_pan[e].readOnly = false;
					formobj.cvo_name[e].readOnly = false;
					if(parseInt(cvocat) != 2) {
						// FOR GSTYN
						enableSelect(sgstyn,sgstyn.length);
						// FOR OB
						formobj.cvo_ob[e].readOnly = false;
					}else {
						formobj.cvo_ob[e].value = "0.00";
						formobj.cvo_ob[e].setAttribute("readOnly","true");
					}
				}
			}
		}
	}
}

function changeRegYN() {
	var formobj = document.getElementById('cvo_data_form');
//	var pan=formobj.cvo_pan.value;
	var elements = document.getElementsByClassName("cvoc");
	
	if (elements.length == 1) {
		var rgstyn = formobj.gstyn;
		var rgstynv = rgstyn.options[rgstyn.selectedIndex].value;
//		var pan = document.getElementById("cvo_pan");
		var tin = document.getElementById("cvo_tin");
		if(rgstynv==2) {
			formobj.cvo_tin.value = "NA";
			tin.setAttribute("readOnly","true");
		}else if(rgstynv==1){
			formobj.cvo_tin.value = "";
			tin.readOnly=false;
		}
	}
	else if(elements.length>1){
		for (var i = 0; i < elements.length; i++) {
			var rgstyn = formobj.gstyn[i].options[formobj.gstyn[i].selectedIndex].value;
			if(rgstyn==2) {
				formobj.cvo_tin[i].value = "NA";
				formobj.cvo_tin[i].setAttribute("readOnly","true");
			}else  if(rgstyn==1){
				//formobj.cvo_tin[i].value = "";
				formobj.cvo_tin[i].readOnly=false;
			}
		}
	}
}

function changePartyType(obj) {
	var formobj = document.getElementById('cvo_data_form');
	var elements = document.getElementsByClassName("cvoc");
	
	if (elements.length == 1) {
		var cvparty = formobj.cvo_cat;
		var cvpartyv = cvparty.options[cvparty.selectedIndex].value;
		var regstn = document.getElementById("gstyn");
		if(cvpartyv==2) {
			regstn.options[1].selected="true";
			disableSelectGstn(regstn,regstn.length);
//			formobj.cvo_tin.value = "";
//			formobj.cvo_pan.value = "";
			document.getElementById('cvo_pan').readOnly = false;
			document.getElementById('cvo_tin').readOnly = false;
			formobj.cvo_ob.value="0.00";
			formobj.cvo_ob.setAttribute("readOnly","true");
		}else {
			formobj.cvo_ob.readOnly=false;
			regstn.selectedIndex=0;
			enableSelectGstn(regstn,regstn.length);
		}
	}
	else if(elements.length>1){
		for (var i = 0; i < elements.length; i++) {
			var cvpartyv = formobj.cvo_cat[i].options[formobj.cvo_cat[i].selectedIndex].value;
			var regstn = formobj.gstyn[i];
			if(cvpartyv==2) {
				regstn.options[1].selected="true";
				disableSelectGstn(regstn,regstn.length);
//				formobj.cvo_tin.value = "";
//				formobj.cvo_pan.value = "";
				formobj.cvo_pan[i].value.readOnly = false;
				formobj.cvo_tin[i].value.readOnly = false;
				formobj.cvo_ob[i].value="0.00";
				formobj.cvo_ob[i].setAttribute("readOnly","true");
			}else {
				formobj.cvo_ob[i].readOnly=false;
//				regstn[i].selectedIndex=0;
//				formobj.gstyn[i].selectedIndex=0;
				enableSelectGstn(regstn,regstn.length);
			}
		}
	}
	checkForDeletedCVO(obj);
}

function showInvoice(cvoname,cvotype,gstr,tin,pan,address,mobl,email,obl,cbal,id){
	if(cvoname!="UJWALA"){
    document.getElementById('myModal').style.display = 'block';
    popitup(cvoname,cvotype,gstr,tin,pan,address,mobl,email,obl,cbal,id);
	}
}

function popitup(cvoname,cvotype,gstr,tin,pan,address,mobl,email,obl,cbal,id) {
	count=0;
    var formobj = document.getElementById('data_form');
    formobj.partyType.value=cvotype;
    formobj.partyName.value=cvoname;
    formobj.mobl.value=mobl;
    formobj.email.value=email;
    formobj.gstin.value=tin;
    formobj.pan.value=pan;
    formobj.cvoId.value=id;



    if(cvotype=="0")
            formobj.partyType.value="VENDOR";
    else if(cvotype=="1")
            formobj.partyType.value="CUSTOMER";
    if(cvotype=="2")
            formobj.partyType.value="OMC-"+omc_name;
    if(cvotype=="3")
            formobj.partyType.value="OTHERS";

    if(gstr=="1"){
            //formobj.gstr.value="YES";
            formobj.dgstyn.value=1;
            formobj.dgstyn.selectedIndex=1;
            var dgstyn=document.getElementById('dgstyn');
            disableSelect(dgstyn,dgstyn.length);
            formobj.dgstyn.setAttribute("readOnly","true");
            formobj.gstin.setAttribute("readOnly","true");
            formobj.pan.setAttribute("readOnly","true");
    document.getElementById('gstin').style.backgroundColor ='#F3F3F3';
    document.getElementById('pan').style.backgroundColor ='#F3F3F3';
    document.getElementById('dgstyn').style.backgroundColor ='#F3F3F3';

    }
    else{
            formobj.dgstyn.selectedIndex=2;
            formobj.dgstyn.value=2;
            var dgstyn=document.getElementById('dgstyn');
            enableSelect(dgstyn,dgstyn.length);

            //formobj.gstr.value="NO";
            formobj.dgstyn.readOnly=false;
            formobj.dgstyn.readOnly=false;
            formobj.pan.readOnly=false;
            formobj.gstin.setAttribute("readOnly","true");

            document.getElementById('gstin').style.backgroundColor ='none';
    document.getElementById('pan').style.backgroundColor ='none';
    document.getElementById('dgstyn').style.backgroundColor ='none';

            formobj.dgstyn.style="none";
            formobj.gstin.style="none";
            formobj.pan.style="none";
    }
    formobj.addr.value=address;
    formobj.obal.value=obl;
    formobj.cvocbal.value=cbal;

}

//update saved data
function updateSavedData(obj) {
    var formobj = document.getElementById('data_form');
    var ems = "";
    var epana =new Array();
    var gstwithpanv = new Array();
    var panflag = 0;
var gstflag = 0;

    if (document.getElementById("partyName") != null) {

            //var formobj = document.getElementById('data_form');
            var cvotype=document.getElementById("partyType").value;
            var cvoname=document.getElementById("partyName").value;
            var mobile=document.getElementById("mobl").value;
            var email=document.getElementById("email").value;
            //var gstryn=document.getElementById("gstr").value;
            var gstin=document.getElementById("gstin").value;
            var pan=document.getElementById("pan").value;
            var addr=document.getElementById("addr").value;
          //  var obal=document.getElementById("obal").value;
            var dgstyn=document.getElementById("dgstyn").value;
            var cvo_id=document.getElementById("cvoId").value;


            formobj.partyType.value =document.getElementById("partyType").value.trim();
            formobj.partyName.value = document.getElementById("partyName").value.trim();
            formobj.mobl.value = document.getElementById("mobl").value.trim();
            formobj.email.value = document.getElementById("email").value.trim();
    //        formobj.gstr.value = document.getElementById("gstr").value.trim();
            formobj.gstin.value = document.getElementById("gstin").value.trim();
            formobj.pan.value = document.getElementById("pan").value.trim();
            formobj.addr.value = document.getElementById("addr").value.trim();
            formobj.obal.value = document.getElementById("obal").value.trim();
            formobj.cvoId.value = document.getElementById("cvoId").value.trim();
            formobj.dgstyn.value=document.getElementById("dgstyn").selectedIndex;
            formobj.cvoId.value=document.getElementById("cvoId").value.trim();





                    ems = validateCVOEntriesUpdate(cvoname, cvotype, addr, mobile, email,dgstyn, gstin,pan,panflag,gstflag,cvo_id,count);
    }
    if (ems.length > 0) {
            document.getElementById("dialog-1").innerHTML = ems;
            alertdialogue();
            //alert(ems);
            return;
    }
    formobj.submit();
}
function updateCount(){
    count=1;
            var formobj = document.getElementById('data_form');

                    var rgstyn = formobj.dgstyn;
                    var rgstynv = rgstyn.options[rgstyn.selectedIndex].value;
                    var tin = document.getElementById("gstin");
                    if(rgstynv==2) {
                            formobj.gstin.value = "NA";
                            tin.setAttribute("readOnly","true");
                    }else if(rgstynv==1){
                            formobj.gstin.value = "";
                            tin.readOnly=false;
                    }
}
function saveData(obj) {
	var formobj = document.getElementById('cvo_data_form');
	var ems = "";
	var epana =new Array();
	var gstwithpanv = new Array();
	var panflag = 0;
    var gstflag = 0;
	
	if (document.getElementById("cvo_name") != null) {
		var elements = document.getElementsByClassName("cvoc");
		if (elements.length == 1) {
			var ename = formobj.cvo_name.value.trim();
			formobj.cvo_name.value = ename.replace(/ +(?= )/g,'');
			var ername = formobj.cvo_name.value;
			
			var ecat = formobj.cvo_cat.selectedIndex;
			var eaddr = formobj.cvo_addr.value.trim();
			var econtact = formobj.cvo_contact.value;
			var email = formobj.cvo_email.value.trim();
			var egstyn = formobj.gstyn.selectedIndex;
			var etin = formobj.cvo_tin.value.trim();
			var epan = formobj.cvo_pan.value.trim();
			var ecvob = formobj.cvo_ob.value.trim();
			
			formobj.cvo_addr.value = formobj.cvo_addr.value.trim();
			formobj.cvo_contact.value = formobj.cvo_contact.value.trim();
			formobj.cvo_email.value = formobj.cvo_email.value.trim();
			formobj.cvo_tin.value = formobj.cvo_tin.value.trim();
			formobj.cvo_pan.value = formobj.cvo_pan.value.trim();
			formobj.cvo_ob.value = formobj.cvo_ob.value.trim();
			
			ems = validateCVOEntries(ername, ecat, eaddr, econtact, email, egstyn, etin,epan,ecvob,panflag,gstflag);
		} else if (elements.length > 1) {
			for (var i = 0; i < elements.length; i++) {
				var ename = formobj.cvo_name[i].value.trim();
				formobj.cvo_name[i].value = ename.replace(/ +(?= )/g,'');
				var ername = formobj.cvo_name[i].value;
				
				var ecat = formobj.cvo_cat[i].selectedIndex;
				var eaddr = formobj.cvo_addr[i].value.trim();
				var econtact = formobj.cvo_contact[i].value.trim();
				var email = formobj.cvo_email[i].value.trim();
				var egstyn = formobj.gstyn[i].selectedIndex;
				var etin = formobj.cvo_tin[i].value.trim();
				var epan = formobj.cvo_pan[i].value.trim();
				var ecvob = formobj.cvo_ob[i].value.trim();

				if(epana.includes(epan) && (epan != "") && (epan.toUpperCase() != "NA")){
                    panflag = 1;
				}
				if(gstwithpanv.includes(etin) && (etin != "") && (etin.toUpperCase() != "NA")){
                    gstflag = 1;
				}
				epana.push(epan);
				gstwithpanv.push(etin);
				
				ems = validateCVOEntries(ername, ecat, eaddr, econtact, email, egstyn, etin, epan,ecvob,panflag,gstflag);
				
				if (ems.length > 0)
					break;
				
				formobj.cvo_addr[i].value = formobj.cvo_addr[i].value.trim();
				formobj.cvo_contact[i].value = formobj.cvo_contact[i].value.trim();
				formobj.cvo_email[i].value = formobj.cvo_email[i].value.trim();
				formobj.cvo_tin[i].value = formobj.cvo_tin[i].value.trim();
				formobj.cvo_pan[i].value = formobj.cvo_pan[i].value.trim();
				formobj.cvo_ob[i].value = formobj.cvo_ob[i].value.trim();				
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


//update saved data
function updateSavedData(obj) {
        var formobj = document.getElementById('data_form');
        var ems = "";
        var epana =new Array();
        var gstwithpanv = new Array();
        var panflag = 0;
    var gstflag = 0;

        if (document.getElementById("partyName") != null) {

                //var formobj = document.getElementById('data_form');
                var cvotype=document.getElementById("partyType").value;
                var cvoname=document.getElementById("partyName").value;
                var mobile=document.getElementById("mobl").value;
                var email=document.getElementById("email").value;
                //var gstryn=document.getElementById("gstr").value;
                var gstin=document.getElementById("gstin").value;
                var pan=document.getElementById("pan").value;
                var addr=document.getElementById("addr").value;
                var obal=document.getElementById("obal").value;
                var dgstyn=document.getElementById("dgstyn").value;
                var cvo_id=document.getElementById("cvoId").value;


                formobj.partyType.value =document.getElementById("partyType").value.trim();
                formobj.partyName.value = document.getElementById("partyName").value.trim();
                formobj.mobl.value = document.getElementById("mobl").value.trim();
                formobj.email.value = document.getElementById("email").value.trim();
        //        formobj.gstr.value = document.getElementById("gstr").value.trim();
                formobj.gstin.value = document.getElementById("gstin").value.trim();
                formobj.pan.value = document.getElementById("pan").value.trim();
                formobj.addr.value = document.getElementById("addr").value.trim();
                formobj.obal.value = document.getElementById("obal").value.trim();
                formobj.cvoId.value = document.getElementById("cvoId").value.trim();
                formobj.dgstyn.value=document.getElementById("dgstyn").selectedIndex;
                formobj.cvoId.value=document.getElementById("cvoId").value.trim();





                        ems = validateCVOEntriesUpdate(cvoname, cvotype, addr, mobile, email,dgstyn, gstin,pan,panflag,gstflag,cvo_id,count);
        }
        if (ems.length > 0) {
                document.getElementById("dialog-1").innerHTML = ems;
                alertdialogue();
                //alert(ems);
                return;
        }
        formobj.submit();
}
/*function deleteItem(did) {
	if (confirm("Are you sure you want to delete?") == true) {
		var formobj = document.getElementById('cvo_data_form');
		formobj.actionId.value = "3503";
		formobj.dataId.value = did;
		formobj.submit();
	}
}*/

function deleteItem(id){
	 $("#myDialogText").text("Are You Sure You Want To Delete?");
	 var formobj = document.getElementById('cvo_data_form');
	 confirmDialogue(formobj,3503,id);
}

/*function validateCVOEntries(name, cat, addr, contact, mail, gstyn, tin, pan,vobal,panflag,gstflag) {
	var errmsg = "";

	if ((name == ""))
		errmsg = "Please Enter PARTY NAME<br>";	
	else if (!(IsNameSpaceDot(name))) {
		errmsg = errmsg + "Please Enter Valid PARTY NAME.<br>";
	}
	
	if (cat == "0")
		errmsg = errmsg + "Please Select PARTY TYPE.<br>";
	
	if ((addr == ""))
		errmsg = errmsg + "Please Enter ADDRESS<br>";
    else if(addr.length<25)
    	errmsg = errmsg + "ADDRESS Must Contain Atleast 25 Characters. <br>";
    //else if (!(checkTextLength(addr, 25, 100)))
    //errmsg = errmsg + "ADDRESS Must Contain 25-100 Characters Only. <br>";

	if ((contact != "")) {
		if (!(checkNumber(contact)))
			errmsg = errmsg + "CONTACT NUMBER Must Contain Only Digits. <br>";
		else if (!(contact.length == 10))
			errmsg = errmsg + "CONTACT NUMBER Must Contain 10 Digits. <br>";
	}
	
	if ((mail == ""))
		errmsg = errmsg + "Please Enter EMAIL ID<br>";
	else if (!checkEmail(mail))
		errmsg = errmsg + "Please Enter valid EMAIL ID<br>";
	
    
    if(!(vobal.length > 0)){
    	errmsg = errmsg + "Please Enter Opening Balance. Enter 0 if not applicable. <br>";
    }else if(!isDecimalNumber(vobal))
    	errmsg = errmsg + "Please Enter valid Opening Balance<br>";
    else if(!(parseFloat(vobal)<100000000))
    	errmsg = errmsg + "Opening Balance must be less than 100000000<br>";
    
	var flag =0;
	if((pan != "") && (pan.toUpperCase() != "NA")){
//		if(pana.length>1 && (!(pana.indexOf(pan))))
//			flag =1;
		for(var f=0; f<cvo_data.length; f++) {
			var panv =cvo_data[f].cvo_pan;
			if(panv==pan && (pan.toUpperCase() != "NA"))
				flag = 1;
		}
	}

    var flag =0;
    if((tin != "") && (tin.toUpperCase() != "NA")){
    	for(var f=0; f<cvo_data.length; f++) {
    		var tinv =cvo_data[f].cvo_tin;
    		if(tinv==tin && (tin.toUpperCase() != "NA"))
    			flag = 1;
    	}
    }
        
    if (gstyn == 0)
		errmsg = errmsg + "Please Select GST REGISTERED OR NOT.<br>";
    else if(gstyn==1) {
    	var stcode = tin.substr(0,2);    	
    	if (tin == "")
    		errmsg = errmsg + "Please Enter GSTIN <br>";
    	if(pan=="")
    		errmsg = errmsg + "Please Enter PAN<br>";
    	else if (tin.length != 15)
    		errmsg = errmsg + "GSTIN Must Contains 15 Characters <br>";
    	else if(stcode == "00")
			errmsg = errmsg + "Enter valid StateCode in GSTIN <br>";
    	else if (!(checkGSTIN(tin)))
    		errmsg = errmsg + "Enter Valid GSTIN <br>";
    	else if((flag == 1) || (gstflag == 1))
    			errmsg = errmsg + "GSTIN already exist.Please enter different GSTIN<br>";	   
    	else if(checkPAN(pan)) {
    		var tpan = validateCorrectGSTINFromPAN(tin, pan);
    		if(!tpan)
    			errmsg = errmsg + "Please Enter valid GSTIN with respect to PAN. <br>";
    	}else if(!(checkPAN(pan))) {
    		errmsg = errmsg + "Please Enter valid PAN. <br>";
    	}
    		//	}else if((!(checkPAN(pan))) && (checkGSTIN(tin)) ) {
    		//	errmsg = errmsg + "Please Enter valid PAN. <br>";
    		//	}
    }else if(gstyn==2){
        if(pan=="")
        	errmsg = errmsg + "Please Enter Pan.Enter NA if not available <br>";
        else if((pan.toUpperCase() != "NA") && (!(checkPAN(pan))))
        	errmsg = errmsg + "Please Enter valid PAN. <br>";
	    else if(flag == 1)
    		errmsg = errmsg + "PAN already exist.Please enter different PAN<br>";
    }
    
    return errmsg;
 }*/

function validateCVOEntries(name, cat, addr, contact, mail,dgstyn, tin, pan,vobal,panflag,gstflag) {
    var errmsg = "";

    if ((name == ""))
            errmsg = "Please Enter PARTY NAME<br>";
    else if(name=="UJWALA" || name=="ujwala")
		errmsg = "Please Enter Valid PARTY NAME<br>";
    else if (!(IsNameSpaceDot(name))) {
            errmsg = errmsg + "Please Enter Valid PARTY NAME.<br>";
    }

    if (cat == "0")
            errmsg = errmsg + "Please Select PARTY TYPE.<br>";

    if ((addr == ""))
            errmsg = errmsg + "Please Enter ADDRESS<br>";
    if (addr.indexOf("'")!=-1 || addr.indexOf('"')!=-1)
        errmsg = errmsg + "ADDRESS can not have Single or Double Quotes<br>";
else if(addr.length<25)
        errmsg = errmsg + "ADDRESS Must Contain Atleast 25 Characters. <br>";
//else if (!(checkTextLength(addr, 25, 100)))
//errmsg = errmsg + "ADDRESS Must Contain 25-100 Characters Only. <br>";


    if ((contact != ""  && contact!="NA" && contact!="na")) {
            if (!(checkNumber(contact)))
                    errmsg = errmsg + "CONTACT NUMBER Must Contain Only Digits. <br>";
            else if (!(contact.length == 10))
                    errmsg = errmsg + "CONTACT NUMBER Must Contain 10 Digits. <br>";
    }

    if ((mail == ""))
            errmsg = errmsg + "Please Enter EMAIL ID<br>";
    else if (!checkEmail(mail))
            errmsg = errmsg + "Please Enter valid EMAIL ID<br>";


if(!(vobal.length > 0)){
        errmsg = errmsg + "Please Enter Opening Balance. Enter 0 if not applicable. <br>";
}else if(!isDecimalNumber(vobal))
        errmsg = errmsg + "Please Enter valid Opening Balance<br>";
else if(!(parseFloat(vobal)<10000000))
        errmsg = errmsg + "Opening Balance must be less than 10000000<br>";

/*        var flag =0;
    if((pan != "") && (pan.toUpperCase() != "NA")){
//            if(pana.length>1 && (!(pana.indexOf(pan))))
//                    flag =1;
            for(var f=0; f<cvo_data.length; f++) {
                    var panv =cvo_data[f].cvo_pan;
                    if(panv==pan && (pan.toUpperCase() != "NA"))
                            flag = 1;
            }
    }*/

var flag =0;
if((tin != "") && (tin.toUpperCase() != "NA")){
        for(var f=0; f<cvo_data.length; f++) {
                var tinv =cvo_data[f].cvo_tin;
                if(tinv==tin && (tin.toUpperCase() != "NA"))
                        flag = 1;
        }
}

if (gstyn =="0" || dgstyn =="0")
            errmsg = errmsg + "Please Select GST REGISTERED OR NOT.<br>";
else if (gstyn =="")
            errmsg = errmsg + "Please Enter NO if GST not REGISTERED.<br>";
else if(gstyn==1 || dgstyn =="1") {
        var stcode = tin.substr(0,2);
        if (tin == "")
                errmsg = errmsg + "Please Enter GSTIN <br>";
        if(pan=="")
                errmsg = errmsg + "Please Enter PAN<br>";
        else if (tin.length != 15)
                errmsg = errmsg + "GSTIN Must Contains 15 Characters <br>";
        else if(stcode == "00")
                    errmsg = errmsg + "Enter valid StateCode in GSTIN <br>";
        else if (!(checkGSTIN(tin)))
                errmsg = errmsg + "Enter Valid GSTIN <br>";
        else if((flag == 1) || (gstflag == 1))
                        errmsg = errmsg + "GSTIN already exist.Please enter different GSTIN<br>";
        else if(checkPAN(pan)) {
                var tpan = validateCorrectGSTINFromPAN(tin, pan);
                if(!tpan)
                        errmsg = errmsg + "Please Enter valid GSTIN with respect to PAN. <br>";
        }
                else if(!(checkPAN(pan))) {
                        errmsg = errmsg + "Please Enter valid PAN. <br>";
                }

}/*else if(gstyn=="YES") {
        var stcode = tin.substr(0,2);
        if (tin == "")
                errmsg = errmsg + "Please Enter GSTIN <br>";
        if(pan=="")
                errmsg = errmsg + "Please Enter PAN<br>";
        else if (tin.length != 15)
                errmsg = errmsg + "GSTIN Must Contains 15 Characters <br>";
        else if(stcode == "00")
                    errmsg = errmsg + "Enter valid StateCode in GSTIN <br>";
        else if (!(checkGSTIN(tin)))
                errmsg = errmsg + "Enter Valid GSTIN <br>";
        else if(checkPAN(pan)) {
                var tpan = validateCorrectGSTINFromPAN(tin, pan);
                if(!tpan)
                        errmsg = errmsg + "Please Enter valid GSTIN with respect to PAN. <br>";
        }
                else if(!(checkPAN(pan))) {
                        errmsg = errmsg + "Please Enter valid PAN. <br>";
                }
        }*/

else if(gstyn==2 || dgstyn =="2"){
    if(pan=="")
            errmsg = errmsg + "Please Enter Pan.Enter NA if not available <br>";
    else if((pan.toUpperCase() != "NA") && (!(checkPAN(pan))))
            errmsg = errmsg + "Please Enter valid PAN. <br>";
        else if(flag == 1)
                errmsg = errmsg + "PAN already exist.Please enter different PAN<br>";
}
/* else if(gstyn=="NO"){
    if(pan=="")
            errmsg = errmsg + "Please Enter Pan.Enter NA if not available <br>";
    else if((pan.toUpperCase() != "NA") && (!(checkPAN(pan))))
            errmsg = errmsg + "Please Enter valid PAN. <br>";
        else if(flag == 1)
                errmsg = errmsg + "PAN already exist.Please enter different PAN<br>";
    if (tin == "")
                errmsg = errmsg + "Please Enter GSTIN.Enter NA if GST not REGISTERED <br>";
}*/

return errmsg;
}


//update validations

function validateCVOEntriesUpdate(name, cat, addr, contact, mail,dgstyn, tin, pan,panflag,gstflag,cvo_id,countV) {
        var errmsg = "";

        if ((name == ""))
                errmsg = "Please Enter PARTY NAME<br>";
        else if (!(IsNameSpaceDot(name))) {
                errmsg = errmsg + "Please Enter Valid PARTY NAME.<br>";
        }

        if (cat == "0")
                errmsg = errmsg + "Please Select PARTY TYPE.<br>";

        if ((addr == ""))
                errmsg = errmsg + "Please Enter ADDRESS<br>";
        if (addr.indexOf("'")!=-1 || addr.indexOf('"')!=-1)
                errmsg = errmsg + "ADDRESS can not have Single or Double Quotes<br>";
    else if(addr.length<25)
            errmsg = errmsg + "ADDRESS Must Contain Atleast 25 Characters. <br>";
    //else if (!(checkTextLength(addr, 25, 100)))
    //errmsg = errmsg + "ADDRESS Must Contain 25-100 Characters Only. <br>";


        if ((contact != "" && contact!="NA" && contact!="na")) {
                if (!(checkNumber(contact)))
                        errmsg = errmsg + "CONTACT NUMBER Must Contain Only Digits. <br>";
                else if (!(contact.length == 10))
                        errmsg = errmsg + "CONTACT NUMBER Must Contain 10 Digits. <br>";
        }

        if ((mail == ""))
                errmsg = errmsg + "Please Enter EMAIL ID<br>";
        else if (!checkEmail(mail))
                errmsg = errmsg + "Please Enter valid EMAIL ID<br>";


  /*  if(!(vobal.length > 0)){
            errmsg = errmsg + "Please Enter Opening Balance. Enter 0 if not applicable. <br>";
    }else if(!isDecimalNumber(vobal))
            errmsg = errmsg + "Please Enter valid Opening Balance<br>";
    else if(!(parseFloat(vobal)<100000000))
            errmsg = errmsg + "Opening Balance must be less than 100000000<br>";

*/
    var flag =0;
    if((tin != "") && (tin.toUpperCase() != "NA")){
            for(var f=0; f<cvo_data.length; f++) {
                    var tinv =cvo_data[f].cvo_tin;
                    var idv =cvo_data[f].id;
                    if(tinv==tin && (tin.toUpperCase() != "NA"))
                            flag = 1;
            }
    }

    if (dgstyn =="0")
                errmsg = errmsg + "Please Select GST REGISTERED OR NOT.<br>";
    else if (gstyn =="")
                errmsg = errmsg + "Please Enter NO if GST not REGISTERED.<br>";

    else if(dgstyn =="1" && countV<1) {
            var stcode = tin.substr(0,2);
            if (tin == "")
                    errmsg = errmsg + "Please Enter GSTIN <br>";
            if(pan=="")
                    errmsg = errmsg + "Please Enter PAN<br>";
            else if (tin.length != 15)
                    errmsg = errmsg + "GSTIN Must Contains 15 Characters <br>";
            else if(stcode == "00")
                        errmsg = errmsg + "Enter valid StateCode in GSTIN <br>";
            else if (!(checkGSTIN(tin)))
                    errmsg = errmsg + "Enter Valid GSTIN <br>";

            else if(checkPAN(pan)) {
                    var tpan = validateCorrectGSTINFromPAN(tin, pan);
                    if(!tpan)
                            errmsg = errmsg + "Please Enter valid GSTIN with respect to PAN. <br>";
            }
                    else if(!(checkPAN(pan))) {
                            errmsg = errmsg + "Please Enter valid PAN. <br>";
                    }

    }

    else if(dgstyn =="1" && countV>0) {
            var stcode = tin.substr(0,2);
            if (tin == "")
                    errmsg = errmsg + "Please Enter GSTIN <br>";
            if(pan=="")
                    errmsg = errmsg + "Please Enter PAN<br>";
            else if (tin.length != 15)
                    errmsg = errmsg + "GSTIN Must Contains 15 Characters <br>";
            else if(stcode == "00")
                        errmsg = errmsg + "Enter valid StateCode in GSTIN <br>";
            else if (!(checkGSTIN(tin)))
                    errmsg = errmsg + "Enter Valid GSTIN <br>";
            else if((flag == 1) || (gstflag == 1))
                        errmsg = errmsg + "GSTIN already exist.Please enter different GSTIN<br>";
            else if(checkPAN(pan)) {
                    var tpan = validateCorrectGSTINFromPAN(tin, pan);
                    if(!tpan)
                            errmsg = errmsg + "Please Enter valid GSTIN with respect to PAN. <br>";
            }
                    else if(!(checkPAN(pan))) {
                            errmsg = errmsg + "Please Enter valid PAN. <br>";
                    }

    }

    else if(dgstyn =="2"){
        if(pan=="")
                errmsg = errmsg + "Please Enter Pan.Enter NA if not available <br>";
        else if((pan.toUpperCase() != "NA") && (!(checkPAN(pan))))
                errmsg = errmsg + "Please Enter valid PAN. <br>";
            else if(flag == 1)
                    errmsg = errmsg + "PAN already exist.Please enter different PAN<br>";
    }


    return errmsg;
 }
