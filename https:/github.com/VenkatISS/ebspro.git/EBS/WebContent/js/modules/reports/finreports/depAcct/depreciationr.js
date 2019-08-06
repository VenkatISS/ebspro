function fetchDepreciationReport(){
   	var formobj = document.getElementById('dr_form');
	var spid = formobj.sfy.selectedIndex;
	var spidv =formobj.sfy.options[spid].value;
	var sysdate= new Date();
	var sysyear=sysdate.getFullYear();
	if(!spid>0) {
		document.getElementById("dialog-1").innerHTML = "Please Select FINANCIAL YEAR";
		alertdialogue();
		//alert("Please Select FINANCIAL YEAR");
		return;
	}
	if(spidv > sysyear){
		document.getElementById("dialog-1").innerHTML = "FINANCIAL YEAR Cannot Be Futher Year";
		alertdialogue();
		//alert("FINANCIAL YEAR Cannot Be Futher Year");
		document.getElementById("dr_data_div").style.display="none";
		return;
	}
	formobj.submit();
}

function takeAction(rid,aid){
   	var formobj = document.getElementById('dr_form');
   	if(aid==1)
   		formobj.actionId.value="5568";
   	else if (aid==2){
   		/*if (confirm("Are you sure you want to SUBMIT?") == false) {	
   			formobj.actionId.value="5568";
   			return;
   		}*/
   	 $("#myDialogText").text("Are You Sure You Want To SUBMIT?");
   		$("#dialog-confirm").dialog({
	        resizable: false,
	        modal: true,
	        title: "Confirm",
	        height: 150,
	        width: 400,
	           buttons: {
	            "Yes": function () {
	                $(this).dialog('close');
	                formobj.submit();
	            },
	                "No": function () {
	                $(this).dialog('close');
	                formobj.actionId.value="5568";
	                return;
	            }
	        }
	  });
	
   		formobj.actionId.value="5569";
   	}	
   	formobj.recId.value=rid;
   	formobj.statusId.value=aid;
	if(!(aid==2)){
   	formobj.submit();
	}
}

if(sfy > 0) {
	document.getElementById("dr_data_div").style.display="block";
	var cdate = new Date(depr_data.created_date);
	var ludate = new Date(depr_data.modified_date);
	var tbody = document.getElementById('dr_data_table_body');
	var tblRow = tbody.insertRow(-1);
	var actionshtml = "";
	var drs = depr_data.status;
	if(drs==1) {
		actionshtml = "<input type='button' value='PROCESS' onclick=takeAction("+depr_data.id+",1)>";
	} else if (drs==3) {
		actionshtml = "<input type='button' value='RE-PROCESS' onclick=takeAction("+depr_data.id+",1)>&nbsp"
			+ "<input type='button' value='SUBMIT'  style='margin-left:20px;margin-top:5px;' onclick=takeAction("+depr_data.id+",2)>";
	} 
	tblRow.innerHTML = "<td>"+sfy+"</td>"
		+"<td>"+cdate.getDate()+"-"+months[cdate.getMonth()]+"-"+cdate.getFullYear()+"</td>"
		+"<td>"+ludate.getDate()+"-"+months[ludate.getMonth()]+"-"+ludate.getFullYear()+"</td>"
		+"<td>"+depr_data.o_amt+"</td>"
		+"<td>"+depr_data.p_amt+"</td>"
		+"<td>"+depr_data.d_amt+"</td>"
		+"<td>"+depr_data.c_amt+"</td>"
		+"<td>"+drstatus[depr_data.status]+"</td>"
		+"<td>"+actionshtml+"</td>";
		
	if(drs==3 || drs==4) {
		document.getElementById("dr_details_div").style.display="block";
		var tdetbody = document.getElementById('dr_details_table_body');

		var ac = 0;
		var cbv = 0.00;
		var tdetbody = document.getElementById('dr_details_table_body');
		//Process for Land Details...
		var tdetRow = tdetbody.insertRow(-1);
		tdetRow.innerHTML = "<td></td><td><b>LAND</b></td><td></td><td></td><td></td>";
		for(var i=0; i<depr_details_data.length;i++){
			var deprDetail = depr_details_data[i];
			var aid = depr_details_data[i].mh;
			if(aid==141) {
				if(ac==0) {
					
					var tr = tdetbody.insertRow(-1);
					tr.innerHTML = "<td>01-"+months[3]+"-"+sfy+"</td><td>OPENING BALANCE</td><td></td><td></td><td>0.00</td>"; 
				}
				ac = ac+1;
				var tt = depr_details_data[i].dtype;
				var tstr = "PURCHASE";
				var crv = "";
				var drv = "";
				if(tt==2) {
					tstr = "SALE";
					drv = depr_details_data[i].basic_amount;
					cbv = (+cbv) - (+drv);
				} else if (tt==1) {
					crv = depr_details_data[i].basic_amount;
					cbv = (+cbv) + (+crv);
				}
				var tr = tdetbody.insertRow(-1);
				var invdate = new Date(depr_details_data[i].inv_date);
				tr.innerHTML = "<td>" + invdate.getDate()+"-"+months[invdate.getMonth()]+"-"+invdate.getFullYear() + "</td>" + 
					"<td>"+ tstr +"</td><td>"+drv+"</td><td>"+crv+"</td><td></td>"; 
			}			
			if(i==(depr_details_data.length-1)){
				
				var tr = tdetbody.insertRow(-1);
				tr.innerHTML = "<td>01-"+months[3]+"-"+sfy+"</td><td>CLOSING BALANCE</td><td></td><td></td><td>"+cbv+"</td>"; 
			}
		}

		//Process for Building Details...
		ac = 0;
		var tdetRow = tdetbody.insertRow(-1);
		tdetRow.innerHTML = "<td></td><td><b>BUILDING</b></td><td></td><td></td><td></td>";
		for(var i=0; i<depr_details_data.length;i++){
			var deprDetail = depr_details_data[i];
			var aid = depr_details_data[i].mh;
			if(aid==142) {
				if(ac==0) {
					
					var tr = tdetbody.insertRow(-1);
					tr.innerHTML = "<td>01-"+months[3]+"-"+sfy+"</td><td>OPENING BALANCE</td><td></td><td></td><td>0.00</td>"; 
				}
				ac = ac+1;
				var tt = depr_details_data[i].dtype;
				var tstr = "PURCHASE";
				var crv = "";
				var drv = "";
				if(tt==2) {
					tstr = "SALE";
					drv = depr_details_data[i].basic_amount;
				} else if (tt==1) {
					crv = depr_details_data[i].basic_amount;
				}
				var tr = tdetbody.insertRow(-1);
				var invdate = new Date(depr_details_data[i].inv_date);
				tr.innerHTML = "<td>" + invdate.getDate()+"-"+months[invdate.getMonth()]+"-"+invdate.getFullYear() + "</td>" + 
					"<td>"+ tstr +"</td><td>"+drv+"</td><td>"+crv+"</td><td></td>"; 
			}			
		}
	}
}

if(document.getElementById("dr_details_div").style.display=="block") {
    var trcount = document.getElementById('dr_details_table_body').getElementsByTagName('tr').length;
    if(trcount>6){
    	$('#dr_details_table').css("max-height","45vh");
		$('#dr_details_table').css("overflow","auto");    	
    }	
}
