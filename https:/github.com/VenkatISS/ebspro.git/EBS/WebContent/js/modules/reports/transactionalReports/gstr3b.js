//Construct Staff html
staffdatahtml = "<OPTION VALUE='0'>SELECT</OPTION>";
if (staff_data.length > 0) {
	for (var z = 0; z < staff_data.length; z++) {
		staffdatahtml = staffdatahtml + "<OPTION VALUE='" + staff_data[z].id
				+ "'>" + staff_data[z].emp_name + "</OPTION>";
	}
}
document.getElementById("staff_span").innerHTML = "<select id='staff_id' name='staff_id'>"
		+ staffdatahtml + "</select>";

if (rder > 0) {
	if (gst_data.length > 0) {
//		var sDate = new Date(sd);
/*		document.getElementById("span").innerHTML = " "
				+ months[sDate.getMonth()] + "  " + sDate.getFullYear();
*/
		// Process list
		for (var z = 0; z < gst_data.length; z++) {
			if (gst_data[z].ttype == 1 || gst_data[z].ttype == 3) {
				if (gst_data[z].rate == 0) {
					pigst[0] = pigst[0] + (+gst_data[z].igst);
					pcgst[0] = pcgst[0] + (+gst_data[z].cgst);
					psgst[0] = psgst[0] + (+gst_data[z].sgst);
					ptaxable[0] = ptaxable[0] + (+gst_data[z].taxable_value);
					pcigst[0] = pcigst[0] + (+gst_data[z].igst) + (+gst_data[z].cgst);
				}else if (gst_data[z].rate == 5) {
					pigst[1] = pigst[1] + (+gst_data[z].igst);
					pcgst[1] = pcgst[1] + (+gst_data[z].cgst);
					psgst[1] = psgst[1] + (+gst_data[z].sgst);
					ptaxable[1] = ptaxable[1] + (+gst_data[z].taxable_value);
					pcigst[1] = pcigst[1] + (+gst_data[z].igst) + (+gst_data[z].cgst);
				} else if (gst_data[z].rate == 12) {
					pigst[2] = pigst[2] + (+gst_data[z].igst);
					pcgst[2] = pcgst[2] + (+gst_data[z].cgst);
					psgst[2] = psgst[2] + (+gst_data[z].sgst);
					ptaxable[2] = ptaxable[2] + (+gst_data[z].taxable_value);
					pcigst[2] = pcigst[2] + (+gst_data[z].igst) + (+gst_data[z].cgst);
				} else if (gst_data[z].rate == 18) {
					pigst[3] = pigst[3] + (+gst_data[z].igst);
					pcgst[3] = pcgst[3] + (+gst_data[z].cgst);
					psgst[3] = psgst[3] + (+gst_data[z].sgst);
					ptaxable[3] = ptaxable[3] + (+gst_data[z].taxable_value);
					pcigst[3] = pcigst[3] + (+gst_data[z].igst) + (+gst_data[z].cgst);
				} else if (gst_data[z].rate == 28) {

					pigst[4] = pigst[4] + (+gst_data[z].igst);
					pcgst[4] = pcgst[4] + (+gst_data[z].cgst);
					psgst[4] = psgst[4] + (+gst_data[z].sgst);
					ptaxable[4] = ptaxable[4] + (+gst_data[z].taxable_value);
					pcigst[4] = pcigst[4] + (+gst_data[z].igst) + (+gst_data[z].cgst);
				}
			} else if (gst_data[z].ttype == 2) {
				if (gst_data[z].rate == 0) {
					sigst[0] = sigst[0] + (+gst_data[z].igst);
					scgst[0] = scgst[0] + (+gst_data[z].cgst);
					ssgst[0] = ssgst[0] + (+gst_data[z].sgst);
					staxable[0] = staxable[0] + (+gst_data[z].taxable_value);
				} else if (gst_data[z].rate == 5) {

					sigst[1] = sigst[1] + (+gst_data[z].igst);
					scgst[1] = scgst[1] + (+gst_data[z].cgst);
					ssgst[1] = ssgst[1] + (+gst_data[z].sgst);
					staxable[1] = staxable[1] + (+gst_data[z].taxable_value);
				} else if (gst_data[z].rate == 12) {

					sigst[2] = sigst[2] + (+gst_data[z].igst);
					scgst[2] = scgst[2] + (+gst_data[z].cgst);
					ssgst[2] = ssgst[2] + (+gst_data[z].sgst);
					staxable[2] = staxable[2] + (+gst_data[z].taxable_value);
				} else if (gst_data[z].rate == 18) {

					sigst[3] = sigst[3] + (+gst_data[z].igst);
					scgst[3] = scgst[3] + (+gst_data[z].cgst);
					ssgst[3] = ssgst[3] + (+gst_data[z].sgst);
					staxable[3] = staxable[3] + (+gst_data[z].taxable_value);
				} else if (gst_data[z].rate == 28) {

					sigst[4] = sigst[4] + (+gst_data[z].igst);
					scgst[4] = scgst[4] + (+gst_data[z].cgst);
					ssgst[4] = ssgst[4] + (+gst_data[z].sgst);
					staxable[4] = staxable[4] + (+gst_data[z].taxable_value);
				}
			}
		}
		tpgst[0] = (pigst[0]) + (+pcgst[0]) + (+psgst[0]);
		tsgst[0] = (sigst[0]) + (+scgst[0]) + (+ssgst[0]);

		tpgst[1] = (pigst[1]) + (+pcgst[1]) + (+psgst[1]);
		tsgst[1] = (sigst[1]) + (+scgst[1]) + (+ssgst[1]);

		tpgst[2] = (pigst[2]) + (+pcgst[2]) + (+psgst[2]);
		tsgst[2] = (sigst[2]) + (+scgst[2]) + (+ssgst[2]);

		tpgst[3] = (pigst[3]) + (+pcgst[3]) + (+psgst[3]);
		tsgst[3] = (sigst[3]) + (+scgst[3]) + (+ssgst[3]);
		
		tpgst[4] = (pigst[4]) + (+pcgst[4]) + (+psgst[4]);
		tsgst[4] = (sigst[4]) + (+scgst[4]) + (+ssgst[4]);
	}

	document.getElementById("data_div").style.display = "block";
	var tbody = document.getElementById('report_table_body');
	for (var k = 0; k < 5; k++) {
		var indvNGST = (tsgst[k] - tpgst[k]).toFixed(2);
		var tblRow = tbody.insertRow(-1);
		var pigstv = (pigst[k]).toFixed(2);
		var pcgstv = (pcgst[k]).toFixed(2);
		var psgstv = (psgst[k]).toFixed(2);
		var ptaxablev = (ptaxable[k]).toFixed(2);

		var tpgstv = (tpgst[k]).toFixed(2);
		var sigstv = (sigst[k]).toFixed(2);
		var scgstv = (scgst[k]).toFixed(2);
		var ssgstv = (ssgst[k]).toFixed(2);
		var staxablev = (staxable[k]).toFixed(2);

		var tsgstv = (tsgst[k]).toFixed(2);
		tblRow.style.height = "30px";
		tblRow.align = "center";
		tblRow.innerHTML = "<td>" + gstpc[k] + "%</td>" + "<td>" + ptaxablev
				+ "</td>" + "<td>" + pigstv + "</td>" + "<td>" + pcgstv
				+ "</td>" + "<td>" + psgstv + "</td>" + "<td>" + tpgstv
				+ "</td>" + "<td>" + staxablev + "</td>" + "<td>" + sigstv
				+ "</td>" + "<td>" + scgstv + "</td>" + "<td>" + ssgstv
				+ "</td>" + "<td>" + tsgstv + "</td>" + "<td>" + indvNGST
				+ "</td>";

		// Calculate Net GST
		ngst = ngst + (+indvNGST);

		tpigst = (tpigst + (+pigstv));
		tpcgst = (tpcgst + (+pcgstv));
		tpsgst = (tpsgst + (+psgstv));
		tptaxable = (tptaxable + (+ptaxablev));

		tsigst = (tsigst + (+sigstv));
		tscgst = (tscgst + (+scgstv));
		tssgst = (tssgst + (+ssgstv));
		tstaxable = (tstaxable + (+staxablev));

		xltpigst = (tpigst);
		xltpcgst = (tpcgst);
		xltpsgst = (tpsgst);

		xltsigst = (tsigst);
		xltscgst = (tscgst);
		xltssgst = (tssgst);

	}

	// Set Table Bottom
	var tbody = document.getElementById('report_table_body');
	var tblRow = tbody.insertRow(-1);
	tblRow.style.height = "30px";
	tblRow.align = "center";
	tblRow.background = "#808B96";
	tblRow.innerHTML = "<td><b>TOTAL</b></td>" + "<td>" + tptaxable.toFixed(2) + "</td>"
			+ "<td><b>" + tpigst.toFixed(2) + "</b></td>" + "<td><b>" + tpcgst.toFixed(2)
			+ "</b></td>" + "<td><b>" + tpsgst.toFixed(2) + "</b></td>" + "<td><b>"
			+ (tpigst + (+(tpcgst + (+tpsgst)))).toFixed(2)  + "</td>" + "<td>"
			+ tstaxable.toFixed(2) + "</td>" + "<td><b>" + tsigst.toFixed(2) + "</b></td>"
			+ "<td><b>" + tscgst.toFixed(2) + "</b></td>" + "<td><b>" + tssgst.toFixed(2)
			+ "</b></td>" + "<td><b>"
			+ (tsigst + (+(tscgst + (+tssgst)))).toFixed(2) + "</b></td>"
			+ "<td><b>" + ngst.toFixed(2) + "</b></td>";

}
function fetchGSTR3() {
	var formobj = document.getElementById("gst_form");
	var monn = formobj.sm.selectedIndex;
	var mon = formobj.sm.options[monn].value;
	var yrn = formobj.sy.selectedIndex;
	var yr = formobj.sy.options[yrn].value;
	var lastDay = new Date(yr, mon, 0);
	formobj.fd.value = yr + "-" + mon + "-01";
	formobj.td.value = yr + "-" + mon + "-" + lastDay.getDate();
	ems = validateGSTR3BEntries(monn, yrn);
	//document.getElementById("span").innerHTML = mon;

	if (ems.length > 0) {
		document.getElementById("dialog-1").innerHTML = ems;
		alertdialogue();
		//alert(ems);
		document.getElementById("data_div").style.display = "none";
		return false;
	}
	var cdate=new Date();
	var cmonth=cdate.getMonth()+1;
	var cyear = cdate.getFullYear();
	
	if(((monn > cmonth) && (parseInt(yr) > cyear)) || (monn > cmonth && (parseInt(yr)) === cyear) || (parseInt(yr)) > cyear) {
		document.getElementById("dialog-1").innerHTML = "Sorry, GST REPORT cannot be generated or fetched for future date";
		alertdialogue();
		//alert("Sorry, GST REPORT cannot be generated or fetched for future date");
		document.getElementById("data_div").style.display="none";
		return false;
	}else{
		formobj.submit();
		return true;
	}
}
function saveGSTAmounts(y,m) {

	var formobj = document.getElementById("gst_form");
	
	/*var ligst = formobj.sigst.value;
	var lcgst = formobj.scgst.value;
	var lsgst = formobj.ssgst.value;*/
	var crigst = formobj.pigst.value;
	var crcgst = formobj.pcgst.value;
	var crsgst = formobj.psgst.value;
	
	ems = validateGSTEntries(crigst, crcgst, crsgst);

	if (ems.length > 0) {
		document.getElementById("dialog-1").innerHTML = ems;
		alertdialogue();
		//alert(ems);
		document.getElementById("agency_payments_div").style.display = "none";
		return false;
	}else{
//		formobj.sm.value = "0"+m;
		(m<10) ? (m = "0"+m) : m = m ;
		formobj.sm.value = m;
		formobj.sy.value = y;
		var monn = formobj.sm.selectedIndex;
		var mon = formobj.sm.options[monn].value;
		var yrn = formobj.sy.selectedIndex;
		var yr = formobj.sy.options[yrn].value;
				
		var lastDay = new Date(yr, mon, 0);
		formobj.fd.value = yr + "-" + mon + "-01";
		formobj.td.value = yr + "-" + mon + "-" + lastDay.getDate();
		
		formobj.actionId.value = 7034;

/*		formobj.sigst.value = parseFloat(ligst)+xltsigst;
		formobj.scgst.value = parseFloat(lcgst)+xltscgst;
		formobj.ssgst.value = parseFloat(lsgst)+xltssgst;*/
/*		formobj.sigst.value = xltsigst.toFixed(2);
		formobj.scgst.value = xltscgst.toFixed(2);
		formobj.ssgst.value = xltssgst.toFixed(2);
		formobj.pigst.value = (parseFloat(crigst)+xltpigst).toFixed(2);
		formobj.pcgst.value = (parseFloat(crcgst)+xltpcgst).toFixed(2);
		formobj.psgst.value = (parseFloat(crsgst)+xltpsgst).toFixed(2);
*/
		formobj.sigst.value = 0;
		formobj.scgst.value = 0;
		formobj.ssgst.value = 0;
		formobj.pigst.value = parseFloat(crigst);
		formobj.pcgst.value = parseFloat(crcgst);
		formobj.psgst.value = parseFloat(crsgst);
		
		formobj.submit();
		return true;
	}
}

if(gst_payments_data.length>0){
	document.getElementById("save_gst_amounts").disabled = true;
	document.getElementById("calulate_gst_amounts").disabled = false;
	document.getElementById("setoff_gst_amounts_btn").disabled = false;
}
if(gst_payments_data[0].status==1){
	document.getElementById("calulate_gst_amounts").disabled = true;
	document.getElementById("setoff_gst_amounts_btn").disabled = true;
	document.getElementById("payoff_gst_amounts_btn").disabled = false;
//	document.getElementById("save_gst_amounts").disabled = true;
}else if(gst_payments_data[0].status==2){
	document.getElementById("calulate_gst_amounts").disabled = true;
	document.getElementById("setoff_gst_amounts_btn").disabled = true;
	document.getElementById("payoff_gst_amounts_btn").disabled = true;
}

function calculateGSTR3(y,m) {
	
	tsigst = parseFloat(tsigst) + parseFloat(gst_payments_data[0].liability_igst);
	tscgst = parseFloat(tscgst) + parseFloat(gst_payments_data[0].liability_cgst);
	tssgst = parseFloat(tssgst) + parseFloat(gst_payments_data[0].liability_sgst);
	tpigst = parseFloat(tpigst) + parseFloat(gst_payments_data[0].creditors_igst);
	tpcgst = parseFloat(tpcgst) + parseFloat(gst_payments_data[0].creditors_cgst);
	tpsgst = parseFloat(tpsgst) + parseFloat(gst_payments_data[0].creditors_sgst);
	
	document.getElementById("agency_payments_div").style.display = "block";
	var tbody = document.getElementById('report2_table_body');
	var oRows = document.getElementById('report2_table_body').getElementsByTagName('tr');
	var iRowCount = oRows.length;
	if(iRowCount < 5){
		var tblRow = tbody.insertRow(-1);
		var tblRow2 = tbody.insertRow(-1);
		var tblRow3 = tbody.insertRow(-1);
		var tblRow4 = tbody.insertRow(-1);
		var tblRow5 = tbody.insertRow(-1);
		tblRow.style.height = "30px";
		tblRow.align = "center";
		tblRow2.align = "center";
		tblRow3.align = "center";
		tblRow4.align = "center";
		tblRow5.align = "center";
		
		if (tsigst > tpigst) {
			taisigst = tpigst;
			taasigst = tsigst - tpigst;
			tpigst = tpigst - taisigst;
			tsigst = taasigst;
			
			if (tpcgst > taasigst) {
				tasigst = taasigst;
				tpcgst = tpcgst - taasigst;
				tsigst = tsigst - tasigst;
			}else if (tpcgst <= taasigst) {
				tasigst = tpcgst;
				tpcgst = tpcgst - tasigst;
				tsigst = tsigst - tasigst;
				taaasigst = taasigst - tasigst;
				
				if (tpsgst > taaasigst) {
					tassigst = taaasigst;
					tpsgst = tpsgst - tassigst;
					tsigst = tsigst - tassigst;
					ttcsigst = tsigst;
					tsigst=0;
					ttpsigst = tsigst;
				}else if (tpsgst <= taaasigst) {
					tassigst = tpsgst;
					tpsgst = 0;
					tsigst = tsigst - tassigst;
 					ttcsigst = tsigst;
					tsigst=0;
					ttpsigst = tsigst;
				}
			}
			ttpsigst = tsigst;

		}else if (tsigst <= tpigst) {
			taisigst = tsigst;
			tpigst = tpigst - taisigst;
			tsigst = 0;
			ttcsigst = tsigst;
			ttpsigst = tsigst;
		}
		if (tscgst > tpcgst) {
			tascgst = tpcgst;
			taascgst = tscgst - tpcgst;
			tpcgst = tpcgst - tascgst;
			tscgst = taascgst;
		
			if (tpigst > taascgst) {
				taiscgst = tscgst;
				tscgst = 0;
				ttcscgst = tscgst;
				tpigst = tpigst - taiscgst;
				ttpscgst = tscgst;
			}else if (tpigst <= taascgst) {
				taiscgst = tpigst;
				tpigst = tpigst - taiscgst;
				ttcscgst = tscgst - taiscgst;
				tscgst = 0;
				ttpscgst = tscgst;
			}
			ttpscgst = tscgst;
		}else if (tscgst <= tpcgst) {
			tascgst = tscgst;
			tscgst = 0;
			tpcgst = tpcgst - tascgst;
			taiscgst = 0;
			ttcscgst = tscgst;
			ttpscgst = tscgst;
		}

		if (tssgst > tpsgst) {
			tassgst = tpsgst;
			taassgst = tssgst - tpsgst;
			tpsgst = tpsgst - tassgst;
			tssgst = taassgst;
			
			if (tpigst > taassgst) {
				taissgst = tssgst;
				tssgst = 0;
				ttcssgst = tssgst;
				tpigst = tpigst - taissgst;
				ttpssgst = tssgst;
			}else if (tpigst <= taassgst) {
				taissgst = tpigst;
				tpigst = tpigst - taissgst;
				ttcssgst = tssgst-taissgst;
				tssgst = 0;
				ttpssgst = tssgst;
			}
			ttpssgst = tssgst;
		}else if (tssgst <= tpsgst) {
			tassgst = tssgst;
			tssgst = 0;
			tpsgst = tpsgst - tassgst;
			taissgst = 0;
			ttcssgst = tssgst;
			ttpssgst = tssgst;
		}

		tblRow.innerHTML = "<td>" + gstpc2[0] + "</td>" + "<td>" + ttpsigst.toFixed(2)
			+ "</td>" + "<td>" + taisigst.toFixed(2) + "</td>" + "<td>"
			+ tasigst.toFixed(2) + "</td>" + "<td>" + tassigst.toFixed(2)
			+ "</td>" + "<td>" + "0.00" + "</td>" + "<td>" + ttcsigst.toFixed(2) + "</td>"
			+ "<td>" + "0.00" + "</td>" + "<td>" + "0.00" + "</td>";
		tblRow2.innerHTML = "<td>" + gstpc2[1] + "</td>" + "<td>" + ttpscgst.toFixed(2)
			+ "</td>" + "<td>" + taiscgst.toFixed(2) + "</td>" + "<td>" + tascgst.toFixed(2)
			+ "</td>" + "<td></td>" + "<td>" + "0.00" + "</td>" + "<td>"
			+ ttcscgst.toFixed(2) + "</td>" + "<td>" + "0.00" + "</td>" + "<td>" + "0.00"
			+ "</td>";
		tblRow3.innerHTML = "<td>" + gstpc2[2] + "</td>" + "<td>" + ttpssgst.toFixed(2)
			+ "</td>" + "<td>" + taissgst.toFixed(2) + "</td>" + "<td></td>" + "<td>"
			+ tassgst.toFixed(2) + "</td>" + "<td>" + "0.00" + "</td>" + "<td>" + ttcssgst.toFixed(2)
			+ "</td>" + "<td>" + "0.00" + "</td>" + "<td>" + "0.00" + "</td>";
		tblRow4.innerHTML = "<td>" + gstpc2[3] + "</td>" + "<td>" + "0.00"
			+ "</td>" + "<td></td>" + "<td></td>" + "<td></td>" + "<td>"
			+ "0.00" + "</td>" + "<td>" + "0.00" + "</td>" + "<td>" + "0.00"
			+ "</td>" + "<td>" + "0.00" + "</td>";
		tblRow5.innerHTML = "<td>" + gstpc2[4] + "</td>" + "<td>" + "0.00"
		+ "</td>" + "<td></td>" + "<td></td>" + "<td></td>" + "<td>"
		+ "0.00" + "</td>" + "<td>" + "0.00" + "</td>" + "<td>" + "0.00"
		+ "</td>" + "<td>" + "0.00" + "</td>";

		document.getElementById("net_gst_span").innerHTML = ngst.toFixed(2);
		document.getElementById("net_igst_span").innerHTML = ttcsigst.toFixed(2);
		document.getElementById("net_cgst_span").innerHTML = ttcscgst.toFixed(2);
		document.getElementById("net_sgst_span").innerHTML = ttcssgst.toFixed(2);
		document.getElementById("net_total_gst_span").innerHTML = +(ttcsigst.toFixed(2)) + +(ttcscgst.toFixed(2)) + +(ttcssgst.toFixed(2));
	}
	
}

function SetOffGSTs(y,m) {
	calculateGSTR3(y,m);
	var formobj = document.getElementById("gst_form");
	//var monn = y;
	var ligst = formobj.sigst.value;
	var lcgst = formobj.scgst.value;
	var lsgst = formobj.ssgst.value;
	if(gst_payments_data.length>0) {
		if(gst_payments_data[0].status==1){
			document.getElementById("calulate_gst_amounts").disabled = true;
			document.getElementById("agency_payments_div").style.display = "block";
			document.getElementById("dialog-1").innerHTML = "GST REPORT SET-OFF HAS BEEN ALREADY DONE FOR THIS MONTH";
			alertdialogue();
//			alert("GST REPORT SET-OFF HAS BEEN ALREADY DONE FOR THIS MONTH");
		}else {
			$("#myDialogText").text("ARE YOU SURE WANT TO SET-OFF DATA!");
			$("#dialog-confirm").dialog({
		        resizable: false,
		        modal: true,
		        title: "Confirm",
		        height: 150,
		        width: 400,
		           buttons: {
		            "Yes": function () {
		                $(this).dialog('close');
		                formobj.sm.value = "0"+m;
				    	formobj.sy.value = y;
				    	formobj.actionId.value = 7035;
				    	formobj.sigst.value = tsigst;
						formobj.scgst.value = tscgst;
						formobj.ssgst.value = tssgst;
						formobj.pigst.value = tpigst;
						formobj.pcgst.value = tpcgst;
						formobj.psgst.value = tpsgst;
						formobj.ccgst.value = ttcscgst.toFixed(2);
						formobj.csgst.value = ttcssgst.toFixed(2);
						formobj.submit();
						document.getElementById("agency_payments_div").style.display = "block";
						return true;
		            },
		                "No": function () {
		                $(this).dialog('close');
		                return false;
		            }
		        }
		  });
			//var r = confirm("ARE YOU SURE WANT TO SET-OFF DATA!");
		 /*   if (r == true) {
		    	formobj.sm.value = "0"+m;
		    	formobj.sy.value = y;
		    	formobj.actionId.value = 7035;
		    	formobj.sigst.value = tsigst;
				formobj.scgst.value = tscgst;
				formobj.ssgst.value = tssgst;
				formobj.pigst.value = tpigst;
				formobj.pcgst.value = tpcgst;
				formobj.psgst.value = tpsgst;
				formobj.ccgst.value = ttcscgst.toFixed(2);
				formobj.csgst.value = ttcssgst.toFixed(2);
				formobj.submit();
				document.getElementById("agency_payments_div").style.display = "block";
				return true;
		    }else {
		    	return false;
		    }*/
			
		}
	}
}

function payOffGSTAmt(y,m) {
	calculateGSTR3(y,m);
	var formobj = document.getElementById("gst_form");
	
	if(gst_payments_data.length>0){
		if(gst_payments_data[0].status==1) {
			if(gst_payment_details.length >0){
				var gstfv = gst_payment_details[0].final_gst_amount;
				var payablegstamt = document.getElementById("net_total_gst_span").innerHTML;
				if(parseFloat(gstfv) >= parseFloat(payablegstamt)) {
					//var r = confirm("ARE YOU SURE WANT TO PAYOFF DATA!");
					$("#myDialogText").text("ARE YOU SURE WANT TO PAYOFF DATA!");
					$("#dialog-confirm").dialog({
				        resizable: false,
				        modal: true,
				        title: "Confirm",
				        height: 150,
				        width: 400,
				           buttons: {
				            "Yes": function () {
				                $(this).dialog('close');
				                formobj.gstAmt.value = document.getElementById("net_total_gst_span").innerHTML;
								formobj.sm.value = "0"+m;
								formobj.sy.value = y;
								formobj.actionId.value = 7036;
								formobj.submit();
								document.getElementById("agency_payments_div").style.display = "block";
								return true;
				            },
				                "No": function () {
				                $(this).dialog('close');
				                return false;
				            }
				        }
				  });
					/*if (r == true) {
						formobj.gstAmt.value = document.getElementById("net_total_gst_span").innerHTML;
						formobj.sm.value = "0"+m;
						formobj.sy.value = y;
						formobj.actionId.value = 7036;
						formobj.submit();
						document.getElementById("agency_payments_div").style.display = "block";
						return true;
					}else {
						return false;
					}*/
				}else {
					document.getElementById("dialog-1").innerHTML = "PLEASE ADD SUFFICIENT MONEY TO YOUR GST ACCOUNT IN PAYMENTS MODULE OF CASH&BANK AND THEN COMPLETE PAY-OFF";
					alertdialogue();
					return false;					
				}	
			}else {
				document.getElementById("dialog-1").innerHTML = "PLEASE ADD MONEY TO YOUR GST ACCOUNT IN PAYMENTS MODULE OF CASH&BANK AND THEN COMPLETE PAY-OFF";
				alertdialogue();
				return false;
			}	
		}else if(gst_payments_data[0].status==0){
			document.getElementById("dialog-1").innerHTML = "PLEASE SET-OFF THE DATA AND THEN COMPLETE PAY-OFF";
			alertdialogue();
			return false;
		}else if(gst_payments_data[0].status==2){
			document.getElementById("dialog-1").innerHTML = "GST REPORT PAY-OFF HAS BEEN ALREADY DONE FOR THIS MONTH";
			alertdialogue();
			return false;
		}	
	}
}

function validateGSTEntries(crigst, crcgst, crsgst) {	
//	var formobj = document.getElementById('gst_form');
	var errmsg = "";
		
	if (!(sigst.length > 0))
		errmsg = errmsg + "Please Enter liability igst<br>";
	
	if (!(scgst.length > 0))
		errmsg = errmsg + "Please Enter liability cgst<br>";
	
	if (!(ssgst.length > 0))
		errmsg = errmsg + "Please Enter liability Sgst<br>";
	
	if((!(crigst.length > 0)) && (!(crcgst.length > 0)) && (!(crsgst.length > 0)))
		errmsg = errmsg + "Please Enter Creditors igst, cgst and sgst<br>";
	else if((!(crigst.length > 0)) && (!(crcgst.length > 0)))
		errmsg = errmsg + "Please Enter Creditors igst and cgst<br>";
	else if((!(crcgst.length > 0)) && (!(crsgst.length > 0)))
		errmsg = errmsg + "Please Enter Creditors cgst and sgst<br>";
	else if((!(crigst.length > 0)) && (!(crsgst.length > 0)))
		errmsg = errmsg + "Please Enter Creditors igst and sgst<br>";
	else if (!(crigst.length > 0))
		errmsg = errmsg + "Please Enter Creditors igst<br>";
	else if (!(crcgst.length > 0))
		errmsg = errmsg + "Please Enter Creditors cgst<br>";
	else if (!(crsgst.length > 0))
		errmsg = errmsg + "Please Enter Creditors Sgst";

	return errmsg;

}

function validateGSTR3BEntries(monn, yrn) {
	var formobj = document.getElementById('gst_form');
	var errmsg = "";
	var syv = parseInt(formobj.sy.value);
	var seldate = new Date(syv,monn-1,01);
	var neweffd = new Date(effDate.getFullYear(),effDate.getMonth(),01,0,0,0);
	
	if (!(monn > 0) && !(yrn > 0))
		errmsg = errmsg + "Please select Month and Year\n";
	else if (!(monn > 0))
		errmsg = errmsg + "Please select Month\n";
	else if (!(yrn > 0))
		errmsg = errmsg + "Please select  Year\n";
/*	else if (parseInt(formobj.sy.value) == 2017 && parseInt(monn) < 7)
		errmsg = errmsg + "Select MONTH and YEAR from JULY,2017 onwards .\n";*/
	else if (seldate<neweffd)
		errmsg = errmsg + "Invalid Date Selection.Please provide month and year after the EFFECTIVE DATE that you have provided while your registration \n";

	if (!(sigst.length > 0))
		errmsg = errmsg + "Please Enter igst\n";
	
	if (!(scgst.length > 0))
		errmsg = errmsg + "Please Enter cgst\n";
	
	if (!(ssgst.length > 0))
		errmsg = errmsg + "Please Enter Sgst\n";
	
	return errmsg;
}

function generateGSTR3() {
	if((gst_data != "") && (parseInt(gstdata_size) > 0)) {
		var workbook = new $.ig.excel.Workbook();

		workbook.setCurrentFormat($.ig.excel.WorkbookFormat.excel2007);
		var sheet = workbook.worksheets().add('GSTR-3B');

		sheet.columns(0).setWidth(25,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet.columns(1).setWidth(280,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet.columns(2).setWidth(150,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet.columns(3).setWidth(150,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet.columns(4).setWidth(150,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet.columns(5).setWidth(100,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet.columns(6).setWidth(120,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet.columns(7).setWidth(140,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet.columns(8).setWidth(100,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet.columns(9).setWidth(100,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		
	//	var merged0 = sheet.mergedCellsRegions().add(0, 0, 0, 20);
		var light1Fill = $.ig.excel.CellFill.createSolidFill(new $.ig.excel.WorkbookColorInfo($.ig.excel.WorkbookThemeColorType.light1));
		var cells1 = sheet.getRegion('A1:AA1').getEnumerator();
		while (cells1.moveNext()) {
			cells1.current().cellFormat().fill(light1Fill);
		}
		var cells = sheet.getRegion('A1:A500').getEnumerator();
		while (cells.moveNext()) {
			cells.current().cellFormat().fill(light1Fill);
		}
		var cells1 = sheet.getRegion('A2:DA500').getEnumerator();
		while (cells1.moveNext()) {
			cells1.current().cellFormat().fill(light1Fill);
		}
	//	var merged0 = sheet.mergedCellsRegions().add(0, 0, 99, 0);
		var merged1 = sheet.mergedCellsRegions().add(1, 1, 2, 11);
		merged1.value('GSTR-3B  [See rule 61(5)]');
		merged1.cellFormat().font().height(20 * 24);
		merged1.cellFormat().alignment($.ig.excel.HorizontalCellAlignment.center);
		merged1.cellFormat().font().bold(true);

		sheet.rows(2).height(1500);
		sheet.rows(1).cells(1).cellFormat().leftBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(2).cells(1).cellFormat().leftBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		
		sheet.rows(1).cells(12).cellFormat().leftBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(2).cells(12).cellFormat().leftBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		
		for (i = 1; i <= 11; i++) {
			sheet.rows(1).cellFormat().font().bold(true);

			sheet.rows(1).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#DAE3F3'));
			sheet.rows(1).cells(i).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet.rows(2).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(1).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			/*sheet.rows(2).cells(i).cellFormat().rightBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			*/
			sheet.rows(1).height(500);
		}
		
		sheet.rows(4).cells(1).cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#FFE699'));
		sheet.rows(5).cells(1).cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#FFE699'));

		sheet.rows(4).height(400);
		sheet.rows(5).height(400);

		sheet.mergedCellsRegions().add(4, 2, 4, 3);
		sheet.mergedCellsRegions().add(5, 2, 5, 3);

		sheet.getCell('B5').value('GSTIN');
		sheet.getCell('B6').value('Legal name of the registered person');
		//alert(gstinNum);

		sheet.getCell('C5').value(document.getElementById("gstinNum").value);
	//	sheet.getCell('C5').value(gstinNum);
		
		sheet.getCell('C6').value(document.getElementById("contactPerson").value);
		
		sheet.rows(4).cells(1).cellFormat().leftBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(4).cells(1).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
		sheet.rows(5).cells(1).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
		sheet.rows(5).cells(1).cellFormat().leftBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(4).cells(3).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
		sheet.rows(5).cells(3).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
	
		sheet.mergedCellsRegions().add(4, 4, 4, 5);
		sheet.mergedCellsRegions().add(5, 4, 5, 5);

		sheet.getCell('E5').value('Year');
		sheet.getCell('E6').value('Month');



		var month=0;
		switch (parseInt(gstMonth)) {
		case 01:
			month = "JANUARY";
			break;
		case 02:
			month = "FEBRUARY";
			break;
		case 03:
			month = "MARCH";
			break;
		case 04:
			month = "APRIL";
			break;
		case 05:
			month = "MAY";
			break;
		case 06:
			month = "JUNE";
			break;
		case 07:
			month = "JULY";
			break;
		case 08:
			month = "AUGUST";
			break;
		case 09:
			month = "SEPTEMBER";
			break;
		case 10:
			month = "OCTOBER";
			break;
		case 11:
			month = "NOVEMBER";
			break;
		case 12:
			month = "DECEMBER";
			break;
		}
		
		sheet.getCell('G5').value(gstYear);
		sheet.getCell('G6').value(month);
		
		sheet.rows(4).cells(5).cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#B4C7E7'));
		sheet.rows(5).cells(5).cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#B4C7E7'));
	
		sheet.rows(4).cells(5).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
		sheet.rows(5).cells(5).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
	
		sheet.mergedCellsRegions().add(4, 7, 5, 7);
		sheet.mergedCellsRegions().add(4, 8, 5, 9);

		sheet.getCell('H5').value('Sheet Status:');
		sheet.getCell('H5').cellFormat().font().height(16 * 18);
		sheet.getCell('I5').value('Validation Successful');

		sheet.rows(4).cells(7).cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#FFE699'));
		
		sheet.rows(4).cells(1).cellFormat().font().bold(true);
		sheet.rows(5).cells(1).cellFormat().font().bold(true);
		sheet.rows(4).cells(4).cellFormat().font().bold(true);
		sheet.rows(5).cells(4).cellFormat().font().bold(true);
		sheet.rows(5).cells(7).cellFormat().font().bold(true);
		sheet.rows(4).cells(7).cellFormat().font().bold(true);
		
		sheet.getCell('C4').cellFormat().font().height(15 * 15);
		sheet.getCell('C5').cellFormat().font().height(15 * 15);
		sheet.getCell('G4').cellFormat().font().height(15 * 15);
		sheet.getCell('G5').cellFormat().font().height(15 * 15);
		sheet.getCell('I5').cellFormat().font().height(16 * 17);


		sheet.rows(4).cells(6).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
		sheet.rows(5).cells(6).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
		sheet.rows(4).cells(7).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
		sheet.rows(4).cells(9).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		
		for (i = 1; i <=9; i++) {
			sheet.rows(4).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(4).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(4).cells(i).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet.rows(5).cells(i).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			
			sheet.rows(5).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(5).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			
//			sheet.rows(5).cells(i).cellFormat().font().colorInfo(
//					new $.ig.excel.WorkbookColorInfo(
//							$.ig.excel.WorkbookThemeColorType.light1));
		}

//  --------------------

		sheet.rows(7).height(400);
		sheet.rows(8).height(600);
		
		var mergedRegion1 = sheet.mergedCellsRegions().add(7, 1, 7, 6);
		mergedRegion1
				.value('3.1 Details of Outward Supplies and inward supplies liable to reverse charge');

		sheet.rows(7).cells(5).cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		sheet.rows(7).cellFormat().font().bold(true);
		sheet.rows(7).cells(1).cellFormat().leftBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(7).cells(6).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		
		sheet.getCell('B9').value('Nature of Supplies');
		sheet.getCell('C9').value('Total Taxable value');
		sheet.getCell('D9').value('Integrated Tax');
		sheet.getCell('E9').value('Central Tax');
		sheet.getCell('F9').value('State/UT Tax');
		sheet.getCell('G9').value('Cess');
		
		sheet.getCell('B10').value('1');
		sheet.getCell('C10').value('2');
		sheet.getCell('D10').value('3');
		sheet.getCell('E10').value('4');
		sheet.getCell('F10').value('5');
		sheet.getCell('G10').value('6');

		for (i = 1; i <= 6; i++) {
			sheet.rows(8).cellFormat().font().bold(true);
			sheet.rows(8).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#F4B183'));
			sheet.rows(8).cells(i).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet.rows(8).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(8).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(8).cells(i).cellFormat().rightBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(8).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(7).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(8).height(500);
			
			sheet.rows(9).cells(i).cellFormat().font().height(14 * 14);
			sheet.rows(9).cellFormat().font().bold(true);
		}

		for (i = 1; i <=6; i++) {
			sheet.rows(9).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#C55A11'));
			sheet.rows(9).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(9).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(9).cells(i).cellFormat().rightBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(9).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet.rows(9).cells(i).cellFormat().font().colorInfo(
					new $.ig.excel.WorkbookColorInfo(
							$.ig.excel.WorkbookThemeColorType.light1));

			sheet.rows(10).cells(i).cellFormat().font().height(14 * 15);
			sheet.rows(11).cells(i).cellFormat().font().height(14 * 15);
			sheet.rows(12).cells(i).cellFormat().font().height(14 * 15);
			sheet.rows(13).cells(i).cellFormat().font().height(14 * 15);
			sheet.rows(14).cells(i).cellFormat().font().height(14 * 15);
			sheet.rows(15).cells(i).cellFormat().font().height(14 * 15);
			sheet.rows(16).cells(i).cellFormat().font().height(14 * 15);

			sheet.rows(10).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(11).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(12).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(13).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(14).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(15).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);

			sheet.rows(10).cells(i).cellFormat().rightBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(11).cells(i).cellFormat().rightBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(12).cells(i).cellFormat().rightBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(13).cells(i).cellFormat().rightBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(14).cells(i).cellFormat().rightBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(15).cells(i).cellFormat().rightBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
		}
		
		sheet.rows(10).cells(1).cellFormat().leftBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(11).cells(1).cellFormat().leftBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(12).cells(1).cellFormat().leftBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(13).cells(1).cellFormat().leftBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(14).cells(1).cellFormat().leftBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(15).cells(1).cellFormat().leftBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);

		sheet.rows(10).cells(7).cellFormat().leftBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(11).cells(7).cellFormat().leftBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(12).cells(7).cellFormat().leftBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(13).cells(7).cellFormat().leftBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(14).cells(7).cellFormat().leftBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(15).cells(7).cellFormat().leftBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);

		sheet.rows(8).cellFormat().font().bold(true);
		sheet.rows(10).height(600);
		sheet.rows(12).height(600);
		sheet.rows(15).cellFormat().font().bold(true);

		sheet.rows(16).cellFormat().font().bold(true);

		for(i=2;i<=6;i++)
		{
		sheet.rows(10).cells(i).cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#FBE5D6'));
		sheet.rows(11).cells(2).cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#FBE5D6'));
		sheet.rows(11).cells(3).cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#FBE5D6'));
		sheet.rows(11).cells(6).cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#FBE5D6'));
		sheet.rows(12).cells(2).cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#FBE5D6'));
		sheet.rows(13).cells(i).cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#FBE5D6'));
		sheet.rows(14).cells(2).cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#FBE5D6'));
		}
		
		sheet.getCell('B12').value('(b) Outward Taxable  supplies  (zero rated )');
		sheet.getCell('B13').value('(c) Other Outward Taxable  supplies (Nil rated, exempted)');
		sheet.getCell('B11').value('(a) Outward Taxable  supplies  (other than zero rated, nil rated and exempted)');
		sheet.getCell('B14').value('(d) Inward supplies (liable to reverse charge)');
		sheet.getCell('B15').value('(e) Non-GST Outward supplies');
		sheet.getCell('B16').value('Total');
		
		sheet.getCell('B16').cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		
		sheet.getCell('C11').value(tstaxable);
		sheet.getCell('D11').value(xltsigst);
		sheet.getCell('E11').value(xltscgst);
		sheet.getCell('F11').value(xltssgst);
		sheet.getCell('G11').value(0);

		sheet.mergedCellsRegions().add(11, 4, 11, 5);
		sheet.mergedCellsRegions().add(12, 3, 12, 6);
		sheet.mergedCellsRegions().add(14, 3, 14, 6);
		sheet.rows(11).cells(4).cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#C55A11'));
		sheet.rows(12).cells(3).cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#C55A11'));
		sheet.rows(14).cells(3).cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#C55A11'));
		
		var rcpigst = 0;
		var rcpcgst = 0;
		var rcpsgst = 0;
		var rcptgst = 0;
		var ttaxable = 0;
		for (var z = 0; z < gst_data.length; z++) {
			if (gst_data[z].ttype == 3) {
				ttaxable = ttaxable + (+gst_data[z].taxable_value);
				rcpigst = rcpigst + (+gst_data[z].igst);
				rcpcgst = rcpcgst + (+gst_data[z].cgst);
				rcpsgst = rcpsgst + (+gst_data[z].sgst);
			}
		}
		sheet.getCell('C14').value(ttaxable);
		sheet.getCell('D14').value(rcpigst);
		sheet.getCell('E14').value(rcpcgst);
		sheet.getCell('F14').value(rcpsgst);
		sheet.getCell('G14').value(0);
	
		sheet.getCell('C16').applyFormula('=SUM(C11:C15)');
		sheet.getCell('D16').applyFormula('=SUM(D11:D12) + D14');
		sheet.getCell('E16').applyFormula('=E11 + E14');
		sheet.getCell('F16').applyFormula('=F11 + F14');
		sheet.getCell('G16').applyFormula('=SUM(G11:G12) + G14');
	
		
//----------------------------------	

		var mergedRegion2 = sheet.mergedCellsRegions().add(17, 1, 17, 5);
		mergedRegion2.value('4. Eligible ITC');
		mergedRegion2.cellFormat().font().height(16 * 16);
		
		sheet.rows(17).height(400);
		sheet.rows(18).height(600);
		sheet.rows(20).height(600);
		sheet.rows(23).height(1000);
		sheet.rows(26).height(350);
		sheet.rows(27).height(800);
		sheet.rows(29).height(350);
		sheet.rows(30).height(350);
		sheet.rows(31).height(800);
		
		for(i=1;i<=5;i++){
			
			sheet.rows(18).cells(i).cellFormat().font().height(16 * 16);
			sheet.rows(19).cells(i).cellFormat().font().height(14 * 14);

			sheet.rows(17).cells(i).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet.rows(18).cells(i).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet.rows(19).cells(i).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			
			sheet.rows(17).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(17).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(18).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(19).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(20).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(21).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(22).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(23).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(24).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(25).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(26).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(27).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(28).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(29).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(30).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(31).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(32).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(18).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(19).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(20).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(21).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(22).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(23).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(24).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(25).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(26).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(27).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(28).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(29).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(30).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(31).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(32).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);

			sheet.rows(20).cells(i).cellFormat().font().height(15 * 15);
			sheet.rows(26).cells(i).cellFormat().font().height(15 * 15);
			sheet.rows(29).cells(i).cellFormat().font().height(15 * 15);
			sheet.rows(30).cells(i).cellFormat().font().height(15 * 15);

			sheet.rows(21).cells(i).cellFormat().font().height(14 * 15);
			sheet.rows(22).cells(i).cellFormat().font().height(14 * 15);
			sheet.rows(23).cells(i).cellFormat().font().height(14 * 15);
			sheet.rows(24).cells(i).cellFormat().font().height(14 * 15);
			sheet.rows(25).cells(i).cellFormat().font().height(14 * 15);
			sheet.rows(27).cells(i).cellFormat().font().height(14 * 15);
			sheet.rows(28).cells(i).cellFormat().font().height(14 * 15);
			sheet.rows(31).cells(i).cellFormat().font().height(14 * 15);
			sheet.rows(32).cells(i).cellFormat().font().height(14 * 15);
			
			sheet.rows(18).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#1F4E79'));
			sheet.rows(19).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#222A35'));
			
			sheet.rows(18).cells(i).cellFormat().font().colorInfo(
					new $.ig.excel.WorkbookColorInfo(
							$.ig.excel.WorkbookThemeColorType.light1));
			sheet.rows(19).cells(i).cellFormat().font().colorInfo(
					new $.ig.excel.WorkbookColorInfo(
							$.ig.excel.WorkbookThemeColorType.light1));
		}
		
		for(i=2;i<=5;i++){
			sheet.rows(23).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#C6D1FE'));
			sheet.rows(24).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#C6D1FE'));
			sheet.rows(25).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#C6D1FE'));
			sheet.rows(27).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#C6D1FE'));
			sheet.rows(28).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#C6D1FE'));
			sheet.rows(31).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#C6D1FE'));
			sheet.rows(32).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#C6D1FE'));
		}
		
		for(i=3;i<=4;i++){
			sheet.rows(21).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#222A35'));
			sheet.rows(22).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#222A35'));
		}
		
		sheet.rows(21).cells(5).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(26).cells(5).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(29).cells(5).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(30).cells(5).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		
		sheet.rows(21).cells(2).cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#C6D1FE'));
		sheet.rows(21).cells(5).cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#C6D1FE'));
		sheet.rows(22).cells(2).cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#C6D1FE'));

		sheet.rows(22).cells(5).cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#C6D1FE'));

		sheet.rows(17).cells(1).cellFormat().leftBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(17).cells(5).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(18).cells(5).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(19).cells(5).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		
		for(i=2;i<=5;i++){
		
		sheet.rows(20).cells(i).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
		sheet.rows(21).cells(i).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
		sheet.rows(22).cells(i).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
		sheet.rows(23).cells(i).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
		sheet.rows(24).cells(i).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
		sheet.rows(25).cells(i).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
		sheet.rows(26).cells(i).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
		sheet.rows(27).cells(i).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
		sheet.rows(28).cells(i).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
		sheet.rows(29).cells(i).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
		sheet.rows(30).cells(i).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
		sheet.rows(31).cells(i).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
		sheet.rows(32).cells(i).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
		}
		
		sheet.rows(17).cellFormat().font().bold(true);
		sheet.rows(18).cellFormat().font().bold(true);
		sheet.rows(20).cells(1).cellFormat().font().bold(true);
		sheet.rows(26).cells(1).cellFormat().font().bold(true);
		sheet.rows(29).cellFormat().font().bold(true);
		sheet.rows(30).cells(1).cellFormat().font().bold(true);

		sheet.rows(21).cells(1).cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		sheet.rows(22).cells(1).cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		sheet.rows(23).cells(1).cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		sheet.rows(24).cells(1).cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		sheet.rows(25).cells(1).cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		sheet.rows(27).cells(1).cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		sheet.rows(28).cells(1).cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		sheet.rows(31).cells(1).cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		sheet.rows(32).cells(1).cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);

		
		sheet.getCell('B19').value('Details');
		sheet.getCell('C19').value('Integrated Tax');
		sheet.getCell('D19').value('Central Tax');
		sheet.getCell('E19').value('State/UT Tax');
		sheet.getCell('F19').value('Cess');
		
		sheet.getCell('B20').value('1');
		sheet.getCell('C20').value('2');
		sheet.getCell('D20').value('3');
		sheet.getCell('E20').value('4');
		sheet.getCell('F20').value('5');

		sheet.getCell('B21').value('(A) ITC Available (Whether in full or part)');
		sheet.getCell('B22').value('(1) Import of goods ');
		sheet.getCell('B23').value('(2) Import of services');
		sheet.getCell('B24').value('(3) Inward supplies liable to reverse charge (other than 1 &2 above)');
		sheet.getCell('B25').value('(4) Inward supplies from ISD');
		sheet.getCell('B26').value('(5) All other ITC');

		
		sheet.getCell('C24').value(rcpigst);
		sheet.getCell('D24').value(rcpcgst);
		sheet.getCell('E24').value(rcpsgst);
		sheet.getCell('F24').value(0);
		
		var itcpigst = 0;
		var itcpcgst = 0;
		var itcpsgst = 0;
		for (var z = 0; z < gst_data.length; z++) {

			if (gst_data[z].ttype == 1) {

				itcpigst = itcpigst + (+gst_data[z].igst);
				itcpcgst = itcpcgst + (+gst_data[z].cgst);
				itcpsgst = itcpsgst + (+gst_data[z].sgst);
			}
		}

		

		sheet.getCell('C26').value(itcpigst);
		sheet.getCell('D26').value(itcpcgst);
		sheet.getCell('E26').value(itcpsgst);
		sheet.getCell('F26').value(0);

		
		sheet.getCell('B27').value('(B) ITC Reversed');
		sheet.getCell('B28').value('(1) As per Rule 42 & 43 of SGST/CGST rules ');
		sheet.getCell('B29').value('(2) Others');
		sheet.getCell('B30').value('(C) Net ITC Available (A)-(B)');
		sheet.getCell('B31').value('(D) Ineligible ITC');
		sheet.getCell('B32').value('(1) As per section 17(5) of CGST//SGST Act');
		sheet.getCell('B33').value('(2) Others');

		var netITCitax=itcpigst;
		var netITCctax=itcpcgst;
		var netSttax=itcpsgst;
		var netcess=0;
		
		sheet.getCell('C30').applyFormula('=SUM(ROUND(C22,2),ROUND(C23,2),ROUND(C24,2),ROUND(C25,2),ROUND(C26,2))-SUM(ROUND(C28,2),ROUND(C29,2))');
		sheet.getCell('D30').applyFormula('=SUM(ROUND(D22,2),ROUND(D23,2),ROUND(D24,2),ROUND(D25,2),ROUND(D26,2))-SUM(ROUND(D28,2),ROUND(D29,2))');
		sheet.getCell('E30').applyFormula('=SUM(ROUND(E22,2),ROUND(E23,2),ROUND(E24,2),ROUND(E25,2),ROUND(E26,2))-SUM(ROUND(E28,2),ROUND(E29,2))');
		sheet.getCell('F30').applyFormula('=SUM(ROUND(F22,2),ROUND(F23,2),ROUND(F24,2),ROUND(F25,2),ROUND(F26,2))-SUM(ROUND(F28,2),ROUND(F29,2))');

		
// ------------------------------------------
		
		var mergedRegion3 = sheet.mergedCellsRegions().add(35, 1, 35, 4);
		mergedRegion3.value('5. Values of exempt, Nil-rated and non-GST inward supplies');
		mergedRegion3.cellFormat().font().height(16 * 16);
		
		sheet.rows(35).height(400);
		sheet.rows(36).height(600);
		
		sheet.rows(35).cells(1).cellFormat().leftBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(35).cells(4).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(36).cells(4).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(37).cells(4).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(38).cells(1).cellFormat().leftBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(38).cells(4).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(39).cells(1).cellFormat().leftBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(40).cells(1).cellFormat().leftBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(39).cells(4).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(40).cells(4).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		
		sheet.rows(40).cells(1).cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		
		sheet.mergedCellsRegions().add(36, 1, 36, 2);
		sheet.mergedCellsRegions().add(37, 1, 37, 2);
		sheet.mergedCellsRegions().add(38, 1, 38, 2);
		sheet.mergedCellsRegions().add(39, 1, 39, 2);
		sheet.mergedCellsRegions().add(40, 1, 40, 2);

		sheet.rows(35).cellFormat().font().bold(true);
		sheet.rows(36).cellFormat().font().bold(true);
		sheet.rows(40).cellFormat().font().bold(true);

		for(i=1;i<=4;i++){
			
			sheet.rows(36).cells(i).cellFormat().font().height(14 * 16);
			sheet.rows(37).cells(i).cellFormat().font().height(14 * 14);

			sheet.rows(35).cells(i).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet.rows(36).cells(i).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet.rows(37).cells(i).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			
			sheet.rows(35).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(35).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(36).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(37).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(38).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(39).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(40).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			
			sheet.rows(36).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(37).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			
			sheet.rows(38).cells(i).cellFormat().font().height(14 * 15);
			sheet.rows(39).cells(i).cellFormat().font().height(14 * 15);
			sheet.rows(40).cells(i).cellFormat().font().height(14 * 15);
			
			sheet.rows(36).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#FFE699'));
			sheet.rows(37).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#BF9000'));
			sheet.rows(40).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#F2F2F2'));
	
			sheet.rows(37).cells(i).cellFormat().font().colorInfo(
					new $.ig.excel.WorkbookColorInfo(
							$.ig.excel.WorkbookThemeColorType.light1));
    	}
		
		for(i=3;i<=4;i++)
		{
			sheet.rows(38).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#FFF2CC'));
			sheet.rows(39).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#FFF2CC'));
			sheet.rows(38).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(39).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(40).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			}

		sheet.getCell('B37').value('Nature of supplies');
		sheet.getCell('D37').value('Inter-State supplies');
		sheet.getCell('E37').value('Intra-state supplies');
		
		sheet.getCell('B38').value('1');
		sheet.getCell('D38').value('2');
		sheet.getCell('E38').value('3');

		sheet.getCell('B39').value('From a supplier under composition scheme, Exempt  and Nil rated supply');
		sheet.getCell('B40').value('Non GST supply');
		sheet.getCell('B41').value('Total');
		sheet.getCell('D41').applyFormula('=SUM(D39:D40)');
		sheet.getCell('E41').applyFormula('=SUM(E39:E40)');

// ----------------------------------
		
		var mergedRegion5= sheet.mergedCellsRegions().add(43, 1, 43, 5);
		mergedRegion5.value('5.1 Interest & late fee payable');
		mergedRegion5.cellFormat().font().height(15 * 16);
		
		sheet.rows(43).height(400);
		
		sheet.rows(43).cells(1).cellFormat().leftBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(43).cells(5).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(44).cells(5).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(45).cells(1).cellFormat().leftBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(45).cells(5).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(46).cells(1).cellFormat().leftBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(46).cells(5).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		
		sheet.rows(43).cells(1).cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		
		sheet.rows(43).cellFormat().font().bold(true);
		sheet.rows(44).cellFormat().font().bold(true);

		for(i=1;i<=5;i++){
			
			sheet.rows(44).cells(i).cellFormat().font().height(14 * 14);
			sheet.rows(45).cells(i).cellFormat().font().height(14 * 14);

			sheet.rows(44).cells(i).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet.rows(45).cells(i).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			
			sheet.rows(43).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(43).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(44).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(45).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(46).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			
			sheet.rows(44).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(45).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			
			sheet.rows(44).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#B3A3E1'));
			sheet.rows(45).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#7030A0'));
			
			sheet.rows(45).cells(i).cellFormat().font().colorInfo(
					new $.ig.excel.WorkbookColorInfo(
							$.ig.excel.WorkbookThemeColorType.light1));
    	}
		
		for(i=2;i<=5;i++)
		{
			sheet.rows(46).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#B3A3E1'));
			sheet.rows(45).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(46).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			
			}
		
		sheet.rows(46).cells(1).cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		

    	sheet.getCell('B45').value('Description');
		sheet.getCell('C45').value('Integrated Tax');
		sheet.getCell('D45').value('Central Tax');
		sheet.getCell('E45').value('State/UT Tax');
		sheet.getCell('F45').value('Cess');
		
		sheet.getCell('B46').value('1');
		sheet.getCell('C46').value('2');
		sheet.getCell('D46').value('3');
		sheet.getCell('E46').value('4');
		sheet.getCell('F46').value('5');
		sheet.getCell('B47').value('Interest');

//  -----------------------		
		
		var mergedRegion6= sheet.mergedCellsRegions().add(50, 1, 50, 7);
		mergedRegion6.value('3.2  Of the supplies shown in 3.1 (a), details of inter-state supplies made to unregistered persons, composition taxable person and UIN holders');
		mergedRegion6.cellFormat().font().height(15 * 16);
		
		sheet.rows(50).height(500);
		
		sheet.rows(50).cells(1).cellFormat().leftBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(50).cells(7).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(51).cells(7).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(52).cells(1).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(52).cells(7).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(53).cells(7).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		
		sheet.rows(54).cells(7).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(55).cells(7).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(56).cells(7).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(57).cells(7).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(58).cells(7).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(59).cells(7).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(60).cells(7).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(61).cells(7).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(62).cells(7).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(63).cells(7).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(64).cells(7).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(65).cells(7).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(66).cells(7).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(67).cells(7).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(68).cells(7).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(69).cells(7).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(70).cells(7).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(71).cells(7).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(72).cells(7).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(73).cells(7).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(74).cells(7).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(75).cells(7).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(76).cells(7).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(77).cells(7).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(78).cells(7).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(79).cells(7).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(80).cells(7).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(81).cells(7).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(82).cells(7).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
		sheet.rows(83).cells(7).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thick);
			
		sheet.rows(50).cellFormat().font().bold(true);
		sheet.rows(51).cellFormat().font().bold(true);
		sheet.rows(51).height(750);
		sheet.rows(52).height(900);
		
		sheet.mergedCellsRegions().add(51, 1, 52, 1);
		sheet.mergedCellsRegions().add(51, 2, 51, 3);
		sheet.mergedCellsRegions().add(51, 4, 51, 5);
		sheet.mergedCellsRegions().add(51, 6, 51, 7);


		for(i=1;i<=7;i++){
			
			sheet.rows(51).cells(i).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet.rows(52).cells(i).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet.rows(53).cells(i).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			
			sheet.rows(50).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(50).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(51).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(52).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(53).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(54).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(55).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(56).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(57).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(58).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(59).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(60).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(61).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(62).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(63).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(64).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(65).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(66).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(67).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(68).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(69).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(70).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(71).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(72).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(73).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(74).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(75).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(76).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(77).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(78).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(79).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(80).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(81).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(82).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(83).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			
			sheet.rows(51).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(52).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(53).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(54).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(55).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(56).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(57).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(58).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(59).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(60).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(61).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(62).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(63).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(64).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(65).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(66).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(67).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(68).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(69).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(70).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(71).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(72).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(73).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(74).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(75).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(76).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(77).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(78).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(79).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(80).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(81).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(82).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(83).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			
			sheet.rows(53).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#A9D18E'));
			
			sheet.rows(53).cells(i).cellFormat().font().height(14 * 14);

		}
		sheet.rows(51).cells(1).cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#A9D18E'));
		sheet.rows(83).cells(1).cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		sheet.rows(51).cells(1).cellFormat().font().height(16 * 17);
	
		for(i=2;i<=7;i++)
		{
			sheet.rows(51).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#A9D18E'));
			sheet.rows(52).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#548235'));
			sheet.rows(52).cells(i).cellFormat().font().colorInfo(
					new $.ig.excel.WorkbookColorInfo(
							$.ig.excel.WorkbookThemeColorType.light1));
			sheet.rows(52).cellFormat().font().bold(true);
			sheet.rows(52).cells(i).cellFormat().font().height(14 * 14);

			
		}
		
		for(i=54;i<=82;i++)
		{
			sheet.rows(i).cells(1).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#E2F0D9'));
		}
		sheet.rows(83).cellFormat().font().bold(true);
		
		sheet.getCell('B52').value('Place of Supply(State/UT)');
		sheet.getCell('C52').value('Supplies made to Unregistered Persons');
		sheet.getCell('E52').value('Supplies made to Composition Taxable Persons');
		sheet.getCell('G52').value('Supplies made to UIN holders');
		
		sheet.getCell('C53').value('Total Taxable value');
		sheet.getCell('D53').value('Amount of Integrated Tax');
		sheet.getCell('E53').value('Total Taxable value');
		sheet.getCell('F53').value('Amount of Integrated Tax');
		sheet.getCell('G53').value('Total Taxable value');
		sheet.getCell('H53').value('Amount of Integrated Tax');

		sheet.getCell('B54').value('1');
		sheet.getCell('C54').value('2');
		sheet.getCell('D54').value('3');
		sheet.getCell('E54').value('4');
		sheet.getCell('F54').value('5');
		sheet.getCell('G54').value('6');
		sheet.getCell('H54').value('7');

		sheet.getCell('B84').value('Total');
		sheet.getCell('C84').applyFormula('=SUM(C55:C83)');
		sheet.getCell('D84').applyFormula('=SUM(D55:D83)');
		sheet.getCell('E84').applyFormula('=SUM(E55:E83)');
		sheet.getCell('F84').applyFormula('=SUM(F55:F83)');
		sheet.getCell('G84').applyFormula('=SUM(G55:G83)');
		sheet.getCell('H84').applyFormula('=SUM(H55:H83)');
		
	// Save the workbook
		saveWorkbook(workbook, "GSTR3.xlsx");

	}else if((gst_data == "") && (parseInt(gstdata_size) != -1)) {
		document.getElementById("dialog-1").innerHTML = "There is no data present during the selected Date Range. Please check it and then Fetch GSTR3";
		alertdialogue();
		//alert("There is no data present during the selected Date Range. Please check it and then Fetch GSTR3");
	}else if((gst_data == "") && (parseInt(gstdata_size) == -1)) {
		document.getElementById("dialog-1").innerHTML = "Please Fetch GSTR3 and then generate GSTR3";
		alertdialogue();
		//alert("There is no data present during the selected Date Range. Please check it and then Fetch GSTR3");
	}
}

function saveWorkbook(workbook, name) {
	workbook.save({
		type : 'blob'
	}, function(data) {
		saveAs(data, name);
	}, function(error) {
		document.getElementById("dialog-1").innerHTML = "Error exporting: : "+error;
		alertdialogue();
		//alert('Error exporting: : ' + error);
	});
}