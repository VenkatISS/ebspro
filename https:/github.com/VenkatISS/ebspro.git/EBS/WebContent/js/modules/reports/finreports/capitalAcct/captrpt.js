function fetchCapitalAccountReport(){
		   	var formobj = document.getElementById('cr_form');
		   	var today = new Date();
		   	var pyear = today.getFullYear();
			var sfy1=formobj.sfy.selectedIndex;
			var sfy1v = formobj.sfy.options[sfy1].value;
			var sfvalue=parseInt(sfy1v.split('-'));
			var spid = formobj.sfy.selectedIndex;
			if(!spid>0) {
				document.getElementById("dialog-1").innerHTML = "PLEASE SELECT FINANCIAL YEAR";
				alertdialogue();
				//alert("PLEASE SELECT FINANCIAL YEAR");
				return;
			}
			else if(!(sfvalue <= pyear)){
				document.getElementById("dialog-1").innerHTML = "FINANCIAL YEAR Can't Be Future";
				alertdialogue();
				//alert(" FINANCIAL YEAR Can't Be Future \n");
				document.getElementById("data_div").style.display="none";
				return;
			}
			formobj.submit();
		}

function takeAction(rid,aid){
		   	var formobj = document.getElementById('cr_form');
		   	if(aid==1)
		   		formobj.actionId.value="8533";
		   	else if (aid==2){
		   		/*if (confirm("Are you sure you want to SUBMIT?") == false) {	
		   			formobj.actionId.value="8533";
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
			                formobj.actionId.value="8533";
			                return;
			            }
			        }
			  });
		   		formobj.actionId.value="8534";
		   	}
		   	formobj.recId.value=rid;
		   	formobj.statusId.value=aid;
		   	if (!(aid==2)){
		   	formobj.submit();
		   	}
		}
if(sfy > 0) {
	document.getElementById("data_div").style.display="block";
	var cdate = new Date(capar_data.created_date);
	var ludate = new Date(capar_data.modified_date);
	var tbody = document.getElementById('data_table_body');
	var tblRow = tbody.insertRow(-1);
	var actionshtml = "";
	var drs = capar_data.status;
	var finy = ((sfy+1).toString()).substring(2, 4);
	if(drs==1) {
		actionshtml = "<input type='button' value='PROCESS' onclick=takeAction("+capar_data.id+",1)>";
	} else if (drs==3) {
		actionshtml = "<input type='button' value='RE-PROCESS' onclick=takeAction("+capar_data.id+",1)>"
			+ "<input type='button' value='SUBMIT'  style='margin-left:20px;margin-top:5px; ' onclick=takeAction("+capar_data.id+",2)>";
	} 
	tblRow.innerHTML = "<td>"+sfy+"-"+finy+"</td>"
		+"<td>"+cdate.getDate()+"-"+months[cdate.getMonth()]+"-"+cdate.getFullYear()+"</td>"
		+"<td>"+ludate.getDate()+"-"+months[ludate.getMonth()]+"-"+ludate.getFullYear()+"</td>"
		+"<td>"+capar_data.o_amt+"</td>"
		+"<td>"+capar_data.i_amt+"</td>"
		+"<td>"+capar_data.w_amt+"</td>"
		+"<td>"+capar_data.c_amt+"</td>"
		+"<td>"+drstatus[capar_data.status]+"</td>"
		+"<td>"+actionshtml+"</td>";
}