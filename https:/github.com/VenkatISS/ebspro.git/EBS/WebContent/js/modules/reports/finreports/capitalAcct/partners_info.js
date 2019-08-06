var tbody = document.getElementById('data_table_body');
	for(var f=0; f<page_data.length; f++){
       var tblRow = tbody.insertRow(-1);
       //tblRow.style.height="30px";
       tblRow.align="left";
       tblRow.innerHTML ="<tr><td>" + page_data[f].partner_name +  "</td>" +
       					 "<td>" + page_data[f].pan + "</td>" + 
       					 "<td>" + page_data[f].share_percent +  "</td>" +
       					 "<td>" + page_data[f].opening_balance + "</td>" +
       					 "<td>" + pstatus[page_data[f].status] +  "</td>" +
       					 "<td align='center'><img src='images/delete.png' onclick='deleteItem("+page_data[f].id+")'></td></tr>"; 
    };

    function addRow() {
    	var Ppship=document.getElementById("Ppship").value;
        if(Ppship=="1"){
        	document.getElementById("dialog-1").innerHTML = "You can add partners only when you choose PARTNERSHIP while registration.";
        	alertdialogue(); 
        //alert("You can add partners only when you choose PARTNERSHIP while registration.");
        }else {    
            $("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');
        	document.getElementById('page_add_table_div').style.display="block";
        	document.getElementById('savediv').style.display="inline";
    	
        	var trcount = document.getElementById('page_add_table_body').getElementsByTagName('tr').length;
        	if(trcount>0){
        		var trv=document.getElementById('page_add_table_body').getElementsByTagName('tr')[trcount-1];
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
    	
        	var ele = document.getElementsByClassName("pn");
        	if(ele.length < 4){
            	var tbody = document.getElementById('page_add_table_body');
            	var newRow = tbody.insertRow(-1);

        		var a = newRow.insertCell(0);
        		var b = newRow.insertCell(1);
        		var c = newRow.insertCell(2);
        		var d = newRow.insertCell(3);
        		var e = newRow.insertCell(4);
        		var f = newRow.insertCell(5);

        		a.innerHTML = "<tr class='title_head'><td valign='top' height='4' align='center'><INPUT TYPE='text' class='form-control input_field pn eadd' NAME='pname' ID='pname' maxlength ='25' placeholder='Partner Name'></td>";
        		b.innerHTML = "<td valign='top' height='4' align='center'><INPUT TYPE='text' class='form-control input_field eadd' NAME='pan' ID='pan' maxlength ='10' value='' placeholder='Pan'></td>";
        		c.innerHTML = "<td valign='top' height='4' align='center'><INPUT TYPE='text' class='form-control input_field eadd' NAME='sp' ID='sp' maxlength ='8' placeholder='% of Share'></td>";
        		d.innerHTML = "<td valign='top' height='4' align='center'><INPUT TYPE='text' class='form-control input_field eadd' NAME='ob' ID='ob' maxlength ='8' placeholder='Opening blnc'></td>";
        		e.innerHTML = "<td valign='top' height='4' align='center'></td>";
        		f.innerHTML = "<td valign='top' height='4' align='center'><img src='images/delete.png' onclick='doRowDelete(this)' style='margin-left:35px;'></td></tr>";
        	}else {
        		document.getElementById("dialog-1").innerHTML = "Please Save the Records and ADD Again";
            	alertdialogue();
        		//alert("Please Save the Records and ADD Again");
        	}
        }	
    }

    function saveData(){
    	var formobj = document.getElementById('data_add_form');
    	var ems = "",tpshare = 0.0;
    	var epana =new Array();
    	var ptpanflag = 0;

    	if (document.getElementById("pname") != null) {

    		var elements = document.getElementsByClassName("form-control input_field pn eadd");
    		if (elements.length == 1) {
    			
    			var epname = formobj.pname.value.trim();
    			var epan = formobj.pan.value.trim();
    			var esharep = formobj.sp.value.trim();
    			var eob = formobj.ob.value.trim();
    			tpshare = esharep;
    			ems = validateEntries(epname, epan, esharep, eob, ptpanflag, tpshare);
    		} else if (elements.length > 1) {
    			for (var i = 0; i < elements.length; i++) {
    				var epname = formobj.pname[i].value.trim();
        			var epan = formobj.pan[i].value.trim();
        			var esharep = formobj.sp[i].value.trim();
        			var eob = formobj.ob[i].value.trim();
        			tpshare = parseFloat(tpshare) + parseFloat(esharep);
        			if(epana.includes(epan)){
        				ptpanflag = 1;
        			}
        			 epana.push(epan); 
        			ems = validateEntries(epname, epan, esharep, eob, ptpanflag, tpshare);
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
    	formobj.submit();
    }

/*    function deleteItem(did){
    	if (confirm("Are you sure you want to delete?") == true) {
    		var formobj = document.getElementById('data_add_form');
    		formobj.actionId.value = "8503";
    		formobj.itemId.value = did;
    		formobj.submit();
    	}
    }*/
    
    function deleteItem(did){
   	 $("#myDialogText").text("Are You Sure You Want To Delete?");
   	 var formobj = document.getElementById('data_add_form');
   	 confirmDialogue(formobj,8503,did);
   }
    
   function validateEntries(pname, pan, sharep, ob, ptpanflag, tpshare) {
    	var formobj = document.getElementById('data_add_form');
    	var errmsg = "";
    	var flag =0;
    	var tsharep = 0;
    	/*if(pana.length>1 && (!(pana.indexOf(pan))))
    		 flag =1; */
    	for(var f=0; f<page_data.length; f++)
    	{
    		tsharep = tsharep + parseFloat(page_data[f].share_percent);     		
    	     if((page_data[f].pan == pan))
            	  flag =1; 	 
        }
    	
    	if (!(pname.length > 0))
    		errmsg = errmsg + "Please Enter PARTNER NAME<br>";
    	else if (!(IsNameSpaceDot(pname))) {
    		errmsg = errmsg + "Please Enter Valid PARTNER NAME.<br>";
    	}
    	
    	if (!(pan.length > 0))
    		errmsg = errmsg + "Please Enter PAN<br>";
    	else if (!(checkPAN(pan)))
    		errmsg = errmsg + "Please Enter valid PAN<br>";
    	else if((flag == 1) || (ptpanflag == 1))
            errmsg = errmsg + "PAN already exist.Please enter different PAN<br>";
    	
    	if(!(sharep.length > 0))
    		errmsg = errmsg + "Please Enter % OF SHARE <br>";
    	else if (validateDot(sharep))
    		errmsg = errmsg + "% OF SHARE Must Contain Atleast One Number<br>";
    	else if (!(validateDecimalNumberMinMax(sharep, -1, 100)))
    		errmsg = errmsg + "Please Enter Valid  % OF SHARE<br>";
    	else if (!(checkInteger(sharep) && checkInteger(tpshare)))
    		errmsg = errmsg + "% OF SHARE Must Contain Only Numerics<br>";
    	else if (!(parseFloat(sharep) <= 100))
    		errmsg = errmsg + "SHARE Must Be in between 0 to 100 %<br>";
    	else if(!((parseFloat(sharep)+tsharep) <= 100))
    		errmsg = errmsg + " Total SHARE Of The Partners Cannot be More Than 100 %<br>";
    	else if(!(parseFloat(tpshare)+tsharep <= 100))
    		errmsg = errmsg + " Total SHARE Of The Partners Cannot be More Than 100 %<br>";
    	
    	if (!(ob.length > 0))
    		errmsg = errmsg + "Please Enter OPENING BALANCE<br>";
    	else if (validateDot(ob))
    		errmsg = errmsg + "OPENING BALANCE Must Contain Atleast One Number<br>";
    	else if (!checkInteger(ob))
    		errmsg = errmsg + "OPENING BALANCE Must Contain Only Numerics<br>";
    	else if (!(parseFloat(ob) < 100000000))
    		errmsg = errmsg + "OPENING BALANCE Must Be Less Than 10 Crores<br>";
    	else if ((parseFloat(ob) < 0))
    		errmsg = errmsg + "OPENING BALANCE Must Be GreatherThan 0 ";
    	else
    		formobj.ob.value = round(parseFloat(ob), 2);
   
    	return errmsg;
    }

    	