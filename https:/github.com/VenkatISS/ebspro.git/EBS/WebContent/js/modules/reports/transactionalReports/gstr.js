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
				if (gst_data[z].rate == 5) {
					pigst[0] = pigst[0] + (+gst_data[z].igst);
					pcgst[0] = pcgst[0] + (+gst_data[z].cgst);
					psgst[0] = psgst[0] + (+gst_data[z].sgst);
					ptaxable[0] = ptaxable[0] + (+gst_data[z].taxable_value);
					pcigst[0] = pcigst[0] + (+gst_data[z].igst) + (+gst_data[z].cgst);
				} else if (gst_data[z].rate == 12) {
					pigst[1] = pigst[1] + (+gst_data[z].igst);
					pcgst[1] = pcgst[1] + (+gst_data[z].cgst);
					psgst[1] = psgst[1] + (+gst_data[z].sgst);
					ptaxable[1] = ptaxable[1] + (+gst_data[z].taxable_value);
					pcigst[1] = pcigst[1] + (+gst_data[z].igst) + (+gst_data[z].cgst);
				} else if (gst_data[z].rate == 18) {
					pigst[2] = pigst[2] + (+gst_data[z].igst);
					pcgst[2] = pcgst[2] + (+gst_data[z].cgst);
					psgst[2] = psgst[2] + (+gst_data[z].sgst);
					ptaxable[2] = ptaxable[2] + (+gst_data[z].taxable_value);
					pcigst[2] = pcigst[2] + (+gst_data[z].igst) + (+gst_data[z].cgst);
				} else if (gst_data[z].rate == 28) {
					pigst[3] = pigst[3] + (+gst_data[z].igst);
					pcgst[3] = pcgst[3] + (+gst_data[z].cgst);
					psgst[3] = psgst[3] + (+gst_data[z].sgst);
					ptaxable[3] = ptaxable[3] + (+gst_data[z].taxable_value);
					pcigst[3] = pcigst[3] + (+gst_data[z].igst) + (+gst_data[z].cgst);
				}
			} else if (gst_data[z].ttype == 2) {
				if (gst_data[z].rate == 5) {
					sigst[0] = sigst[0] + (+gst_data[z].igst);
					scgst[0] = scgst[0] + (+gst_data[z].cgst);
					ssgst[0] = ssgst[0] + (+gst_data[z].sgst);
					staxable[0] = staxable[0] + (+gst_data[z].taxable_value);
				} else if (gst_data[z].rate == 12) {
					sigst[1] = sigst[1] + (+gst_data[z].igst);
					scgst[1] = scgst[1] + (+gst_data[z].cgst);
					ssgst[1] = ssgst[1] + (+gst_data[z].sgst);
					staxable[1] = staxable[1] + (+gst_data[z].taxable_value);
				} else if (gst_data[z].rate == 18) {
					sigst[2] = sigst[2] + (+gst_data[z].igst);
					scgst[2] = scgst[2] + (+gst_data[z].cgst);
					ssgst[2] = ssgst[2] + (+gst_data[z].sgst);
					staxable[2] = staxable[2] + (+gst_data[z].taxable_value);
				} else if (gst_data[z].rate == 28) {
					sigst[3] = sigst[3] + (+gst_data[z].igst);
					scgst[3] = scgst[3] + (+gst_data[z].cgst);
					ssgst[3] = ssgst[3] + (+gst_data[z].sgst);
					staxable[3] = staxable[3] + (+gst_data[z].taxable_value);
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
	}

	document.getElementById("data_div").style.display = "block";
	var tbody = document.getElementById('report_table_body');
	for (var k = 0; k < 4; k++) {
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
	tblRow.innerHTML = "<td><b>TOTAL</b></td>" + "<td>" + tptaxable + "</td>"
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
	var sigst = formobj.sigst.value;
	var scgst = formobj.scgst.value;
	var ssgst = formobj.ssgst.value;
	
	var lastDay = new Date(yr, mon, 0);
	formobj.fd.value = yr + "-" + mon + "-01";
	formobj.td.value = yr + "-" + mon + "-" + lastDay.getDate();
	ems = validateEntries(monn, yrn);
	//document.getElementById("span").innerHTML = mon;

	if (ems.length > 0) {
		alert(ems);
		document.getElementById("data_div").style.display = "none";
		return false;
	}
	var cdate=new Date();
	var cmonth=cdate.getMonth()+1;
	var cyear = cdate.getFullYear();
	
	if((monn > cmonth && (parseInt(yr)) > cyear) || (monn > cmonth && (parseInt(yr)) === cyear) ||
			(parseInt(yr)) > cyear) {
		alert("GST REPORT can't be future");
		document.getElementById("data_div").style.display="none";
		return false;
	}else{
		formobj.submit();
		document.getElementById("agency_payments_div").style.display = "none";
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
		alert(ems);
		document.getElementById("agency_payments_div").style.display = "none";
		return false;
	}else{
		formobj.sm.value = "0"+m;
		formobj.sy.value = y;
		var monn = formobj.sm.selectedIndex;
		var mon = formobj.sm.options[monn].value;
		var yrn = formobj.sy.selectedIndex;
		var yr = formobj.sy.options[yrn].value;
		
		
		var lastDay = new Date(yr, mon, 0);
		formobj.fd.value = yr + "-" + mon + "-01";
		formobj.td.value = yr + "-" + mon + "-" + lastDay.getDate();
		
		formobj.actionId.value = 7034;
		/*formobj.sigst.value = parseFloat(ligst)+xltsigst;
		formobj.scgst.value = parseFloat(lcgst)+xltscgst;
		formobj.ssgst.value = parseFloat(lsgst)+xltssgst;*/
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
	if(gst_payments_data[0].status==1){
		document.getElementById("calulate_gst_amounts").disabled = true;
	}
}

function calculateGSTR3(y,m) {
	
	tsigst = tsigst+parseFloat(gst_payments_data[0].liability_igst);
	tscgst = tscgst+parseFloat(gst_payments_data[0].liability_cgst);
	tssgst = tssgst+parseFloat(gst_payments_data[0].liability_sgst);
	tpigst = tpigst+parseFloat(gst_payments_data[0].creditors_igst);
	tpcgst = tpcgst+parseFloat(gst_payments_data[0].creditors_cgst);
	tpsgst = tpsgst+parseFloat(gst_payments_data[0].creditors_sgst);
	
	document.getElementById("agency_payments_div").style.display = "block";
	var tbody = document.getElementById('report2_table_body');
	var oRows = document.getElementById('report2_table_body').getElementsByTagName('tr');
	var iRowCount = oRows.length;
	if(iRowCount<4){
	var tblRow = tbody.insertRow(-1);
	var tblRow2 = tbody.insertRow(-1);
	var tblRow3 = tbody.insertRow(-1);
	var tblRow4 = tbody.insertRow(-1);
	tblRow.style.height = "30px";
	tblRow.align = "center";
	tblRow2.align = "center";
	tblRow3.align = "center";
	tblRow4.align = "center";

	if (tsigst > tpigst) {
		taisigst = tpigst;
		taasigst = tsigst - tpigst;
		tpigst = tpigst - taisigst;
		tsigst = taasigst;
		if (tpcgst > taasigst) {
			tasigst = taasigst;
			tpcgst = tpcgst - taasigst;
			tsigst = tsigst - tasigst;
		} else if (tpcgst <= taasigst) {
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
			} else if (tpsgst <= taaasigst) {
				tassigst = tpsgst;
				tpsgst = 0;
				tsigst = tsigst - tassigst;
				ttcsigst = tsigst;
				tsigst=0;
				ttpsigst = tsigst;
			}
		}
		ttpsigst = tsigst;

	} else if (tsigst <= tpigst) {
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
		} else if (tpigst <= taascgst) {
			taiscgst = tpigst;
			tpigst = tpigst - taiscgst;
			ttcscgst = tscgst - taiscgst;
			tscgst = 0;
			ttpscgst = tscgst;
		}
		ttpscgst = tscgst;
	} else if (tscgst <= tpcgst) {
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
		} else if (tpigst <= taassgst) {
			taissgst = tpigst;
			tpigst = tpigst - taissgst;
			ttcssgst = tssgst-taissgst;
			tssgst = 0;
			ttpssgst = tssgst;
		}
		ttpssgst = tssgst;
	} else if (tssgst <= tpsgst) {
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
			alert("GST REPORT SET-OFF HAS BEEN ALREADY DONE FOR THIS MONTH");
		}else {
			var r = confirm("ARE YOU SURE WANT TO SET-OFF DATA!");
		    if (r == true) {
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
		    }
			
		}
	}
}

function payOffGSTAmt(y,m) {
	calculateGSTR3(y,m);
	var formobj = document.getElementById("gst_form");
	if(gst_payments_data.length>0){
		if(gst_payments_data[0].status==1) {
			var r = confirm("ARE YOU SURE WANT TO PAYOFF DATA!");
		    if (r == true) {
		    	formobj.gstAmt.value = document.getElementById("net_total_gst_span").innerHTML;
		    	formobj.sm.value = "0"+m;
		    	formobj.sy.value = y;
		    	formobj.actionId.value = 7036;
				formobj.submit();
				document.getElementById("agency_payments_div").style.display = "block";
				return true;
		    }else {
		    	return false;
		    }
		}else if(gst_payments_data[0].status==0){
			alert("PLEASE SET-OFF THE DATA AND THEN COMPLETE PAY-OFF");
			return false;
		}else if(gst_payments_data[0].status==2){
			alert("GST REPORT PAY-OFF HAS BEEN ALREADY DONE FOR THIS MONTH");
			return false;
		}
	}
}


function validateGSTEntries(crigst, crcgst, crsgst) {	
//	var formobj = document.getElementById('gst_form');
	var errmsg = "";
		
	if (!(sigst.length > 0))
		errmsg = errmsg + "Please Enter liability igst\n";
	
	if (!(scgst.length > 0))
		errmsg = errmsg + "Please Enter liability cgst\n";
	
	if (!(ssgst.length > 0))
		errmsg = errmsg + "Please Enter liability Sgst\n";
	
	if (!(crigst.length > 0))
		errmsg = errmsg + "Please Enter Creditors igst\n";
	
	if (!(crcgst.length > 0))
		errmsg = errmsg + "Please Enter Creditors cgst\n";
	
	if (!(crsgst.length > 0))
		errmsg = errmsg + "Please Enter Creditors Sgst\n";
	return errmsg;

}

function validateEntries(monn, yrn) {
	
	var formobj = document.getElementById('gst_form');
	var errmsg = "";

	if (!(monn > 0))
		errmsg = errmsg + "Please select Month\n";

	if (!(yrn > 0))
		errmsg = errmsg + "Please select  Year\n";
	else if (parseInt(formobj.sy.value) == 2017 && parseInt(monn) < 7) {
		errmsg = errmsg + "Select MONTH and YEAR from JULY,2017 onwards .\n";
	}
	
	if (!(sigst.length > 0))
		errmsg = errmsg + "Please Enter igst\n";
	
	if (!(scgst.length > 0))
		errmsg = errmsg + "Please Enter cgst\n";
	
	if (!(ssgst.length > 0))
		errmsg = errmsg + "Please Enter Sgst\n";
	return errmsg;

}

function generateGSTR3() {
	if (gst_data != "") {
		var workbook = new $.ig.excel.Workbook();

		workbook.setCurrentFormat($.ig.excel.WorkbookFormat.excel2007);
		var sheet = workbook.worksheets().add('GSTR-3B');

		sheet.columns(0).setWidth(260,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet.columns(1).setWidth(150,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet.columns(2).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet.columns(3).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet.columns(4).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet.columns(5).setWidth(100,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet.columns(6).setWidth(100,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet.columns(7).setWidth(180,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);

		var mergedRegion1 = sheet.mergedCellsRegions().add(0, 0, 0, 5);
		mergedRegion1
				.value('3.1 Details of Outward Supplies and inward supplies liable to reverse charge');

		sheet.rows(0).cells(5).cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		sheet.rows(0).cellFormat().font().bold(true);

		for (i = 0; i <= 5; i++) {
			sheet.rows(1).cellFormat().font().bold(true);

			sheet.rows(1).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#F3CCAF'));
			sheet.rows(1).cells(i).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet.rows(1).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(1).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(1).cells(i).cellFormat().rightBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(1).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);

			sheet.rows(1).height(300);
		}

		for (i = 0; i <= 5; i++) {
			sheet.rows(2).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#cc6600'));

			sheet.rows(2).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(2).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(2).cells(i).cellFormat().rightBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(2).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(2).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet.rows(2).cells(i).cellFormat().font().colorInfo(
					new $.ig.excel.WorkbookColorInfo(
							$.ig.excel.WorkbookThemeColorType.light1));
		}

		sheet.rows(1).cellFormat().font().bold(true);

		sheet.getCell('A2').value('Nature of Supplies');
		sheet.getCell('B2').value('Total Taxable value');
		sheet.getCell('C2').value('Integrated Tax');
		sheet.getCell('D2').value('Central Tax');
		sheet.getCell('E2').value('State/UT Tax');
		sheet.getCell('F2').value('Cess');

		sheet.getCell('A3').value('1');
		sheet.getCell('B3').value('2');
		sheet.getCell('C3').value('3');
		sheet.getCell('D3').value('4');
		sheet.getCell('E3').value('5');
		sheet.getCell('F3').value('6');

		var merged4 = sheet.mergedCellsRegions().add(3, 0, 4, 0);
		var mergedb4 = sheet.mergedCellsRegions().add(3, 1, 4, 1);
		var mergedc4 = sheet.mergedCellsRegions().add(3, 2, 4, 2);
		var mergedbd4 = sheet.mergedCellsRegions().add(3, 3, 4, 3);
		var mergedbe4 = sheet.mergedCellsRegions().add(3, 4, 4, 4);
		var mergedbf4 = sheet.mergedCellsRegions().add(3, 5, 4, 5);

		sheet.getCell('A4').value('(a) Outward Taxable  supplies  (other than zero rated, nil rated and exempted)');
		sheet.getCell('A6').value('(b) Outward Taxable  supplies  (zero rated )');

		var merged7 = sheet.mergedCellsRegions().add(6, 0, 7, 0);
		var mergedb7 = sheet.mergedCellsRegions().add(6, 1, 7, 1);
		var mergedc7 = sheet.mergedCellsRegions().add(6, 2, 7, 2);
		var mergedbd7 = sheet.mergedCellsRegions().add(6, 3, 7, 3);
		var mergedbe7 = sheet.mergedCellsRegions().add(6, 4, 7, 4);
		var mergedbf7 = sheet.mergedCellsRegions().add(6, 5, 7, 5);

		sheet.getCell('A7').value(
				'(c) Other Outward Taxable  supplies (Nil rated, exempted)');

		sheet.getCell('A9').value(
				'(d) Inward supplies (liable to reverse charge)');
		sheet.getCell('A10').value('(e) Non-GST Outward supplies');

		sheet.getCell('B4').value(tstaxable);
		sheet.getCell('C4').value(xltsigst);
		sheet.getCell('D4').value(xltscgst);
		sheet.getCell('E4').value(xltssgst);
		sheet.getCell('F4').value(0);

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
		sheet.getCell('B9').value(ttaxable);
		sheet.getCell('C9').value(rcpigst);
		sheet.getCell('D9').value(rcpcgst);
		sheet.getCell('E9').value(rcpsgst);
		sheet.getCell('F9').value(0);
		sheet.getCell('A12').value('Total');

		sheet.getCell('A12').cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		for (i = 0; i <= 5; i++) {

			sheet.rows(11).cellFormat().font().bold(true);
			sheet.rows(11).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);

		}

		sheet.getCell('B12').value(tstaxable + ttaxable);
		sheet.getCell('C12').value(xltsigst + rcpigst);
		sheet.getCell('D12').value(xltscgst + rcpcgst);
		sheet.getCell('E12').value(xltssgst + rcpsgst);
		sheet.getCell('F12').value(0);

		var mergedRegion2 = sheet.mergedCellsRegions().add(14, 0, 15, 4);
		mergedRegion2.value('4. Eligible ITC');
		sheet.rows(14).cells(4).cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		sheet.rows(14).cellFormat().font().bold(true);

		sheet.rows(15).cells(4).cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		for (i = 0; i <= 4; i++) {

			sheet.rows(14).cellFormat().font().bold(true);
			sheet.rows(15).cellFormat().font().bold(true);

			sheet.rows(14).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);

		}

		sheet.rows(16).cellFormat().font().bold(true);

		for (i = 0; i <= 4; i++) {
			sheet.rows(16).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#003366'));
			sheet.rows(17).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#003366'));

			sheet.rows(16).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(16).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(16).cells(i).cellFormat().rightBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(16).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(16).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet.rows(16).cells(i).cellFormat().font().colorInfo(
					new $.ig.excel.WorkbookColorInfo(
							$.ig.excel.WorkbookThemeColorType.light1));
		}

		var merged16 = sheet.mergedCellsRegions().add(16, 0, 17, 0);
		merged16.value('Details');

		var mergedb16 = sheet.mergedCellsRegions().add(16, 1, 17, 1);
		var mergedc16 = sheet.mergedCellsRegions().add(16, 2, 17, 2);
		var mergedbd16 = sheet.mergedCellsRegions().add(16, 3, 17, 3);
		var mergedbe16 = sheet.mergedCellsRegions().add(16, 4, 17, 4);

		sheet.getCell('B17').value('Integrated Tax');
		sheet.getCell('C17').value('Central Tax');
		sheet.getCell('D17').value('State/UT Tax');
		sheet.getCell('E17').value('Cess');

		for (i = 0; i <= 4; i++) {
			sheet.rows(18).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#527a7a'));

			sheet.rows(18).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(18).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(18).cells(i).cellFormat().rightBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(18).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(18).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet.rows(18).cells(i).cellFormat().font().colorInfo(
					new $.ig.excel.WorkbookColorInfo(
							$.ig.excel.WorkbookThemeColorType.light1));
		}

		sheet.getCell('A19').value('1');
		sheet.getCell('B19').value('2');
		sheet.getCell('C19').value('3');
		sheet.getCell('D19').value('4');
		sheet.getCell('E19').value('5');

		sheet.getCell('A20').cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);

		sheet.getCell('A20').cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
		sheet.rows(19).cellFormat().font().bold(true);

		var mergedba19 = sheet.mergedCellsRegions().add(19, 0, 20, 0);
		var mergedbb19 = sheet.mergedCellsRegions().add(19, 1, 20, 1);
		var mergedbc19 = sheet.mergedCellsRegions().add(19, 2, 20, 2);
		var mergedbd19 = sheet.mergedCellsRegions().add(19, 3, 20, 3);
		var mergedbe19 = sheet.mergedCellsRegions().add(19, 4, 20, 4);

		sheet.getCell('A20').value(
				'(A) ITC Available (Whether in full or part)');

		sheet.getCell('A23').value('(1)   Import of goods ');
		sheet.getCell('A24').value('(2)   Import of services');
		sheet
				.getCell('A25')
				.value(
						'(3)   Inward supplies liable to reverse charge (other than 1 &2 above)');
		sheet.getCell('A26').value('(4)   Inward supplies from ISD');
		sheet.getCell('A27').value('(5)   All other ITC');

		sheet.getCell('B25').value(rcpigst);
		sheet.getCell('C25').value(rcpcgst);
		sheet.getCell('D25').value(rcpsgst);
		sheet.getCell('E25').value(0);

		
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
		
		sheet.getCell('B27').value(itcpigst);
		sheet.getCell('C27').value(itcpcgst);
		sheet.getCell('D27').value(itcpsgst);
		sheet.getCell('E27').value(0);

		sheet.getCell('A28').cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);

		sheet.getCell('A28').cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
		sheet.rows(27).cellFormat().font().bold(true);

		sheet.getCell('A28').value('(B) ITC Reversed');

		sheet.getCell('A29').value(
				'(1)   As per Rule 42 & 43 of SGST/CGST rules ');
		sheet.getCell('A30').value('(2)   Others');

		sheet.getCell('A32').cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);

		sheet.getCell('A32').cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);

		sheet.rows(31).cellFormat().font().bold(true);

		sheet.getCell('A32').value(' (C) Net ITC Available (A)-(B)');

		sheet.getCell('A33').cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);

		sheet.getCell('A33').cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);

		sheet.rows(33).cellFormat().font().bold(true);

		sheet.getCell('A34').value(' (D) Ineligible ITC');
		sheet.getCell('A34').cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);

		sheet.getCell('A35').value(
				'(1)   As per section 17(5) of CGST//SGST Act');
		sheet.getCell('A36').value('(2)   Others');

		for (i = 0; i <= 4; i++) {

			sheet.rows(36).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);

		}

		sheet.rows(39).cellFormat().font().bold(true);

		for (i = 0; i <= 2; i++) {

			sheet.rows(39).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);

		}

		var mergedRegion39 = sheet.mergedCellsRegions().add(39, 0, 40, 2);
		mergedRegion39
				.value('5. Values of exempt, Nil-rated and non-GST inward supplies');
		sheet.rows(39).cells(2).cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);

		for (i = 0; i <= 2; i++) {
			sheet.rows(41).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#ffff99'));

			sheet.rows(41).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(41).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(41).cells(i).cellFormat().rightBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(41).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(41).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
		}
		sheet.rows(41).cellFormat().font().bold(true);

		sheet.getCell('A42').value('Nature of supplies');
		sheet.getCell('B42').value('Inter-State supplies');
		sheet.getCell('C42').value('Intra-state supplies');

		for (i = 0; i <= 2; i++) {
			sheet.rows(42).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#b3b300'));

			sheet.rows(42).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(42).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(42).cells(i).cellFormat().rightBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(42).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(42).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet.rows(42).cells(i).cellFormat().font().colorInfo(
					new $.ig.excel.WorkbookColorInfo(
							$.ig.excel.WorkbookThemeColorType.light1));
		}

		sheet.getCell('A43').value('1');
		sheet.getCell('B43').value('2');
		sheet.getCell('C43').value('3');

		sheet.getCell('A47').cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		sheet.rows(46).cellFormat().font().bold(true);
		for (i = 0; i <= 2; i++) {

			sheet.rows(46).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);

		}

		sheet.getCell('A47').value('Total');

		for (i = 0; i <= 4; i++) {
			sheet.rows(51).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#b3b3ff'));

			sheet.rows(51).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(51).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(51).cells(i).cellFormat().rightBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(51).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(51).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);

		}

		sheet.rows(49).cellFormat().font().bold(true);

		for (i = 0; i <= 4; i++) {

			sheet.rows(49).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(49).cells(i).cellFormat().rightBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);

		}

		var mergedRegion49 = sheet.mergedCellsRegions().add(49, 0, 50, 4);
		mergedRegion49.value('5.1 Interest & late fee payable');
		sheet.rows(49).cells(4).cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);

		sheet.rows(51).cellFormat().font().bold(true);

		sheet.getCell('A52').value('Description');
		sheet.getCell('B52').value('Integrated Tax');
		sheet.getCell('C52').value('Central Tax');
		sheet.getCell('D52').value('State/UT Tax');
		sheet.getCell('E52').value('Cess');

		for (i = 0; i <= 4; i++) {
			sheet.rows(52).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#9900cc'));

			sheet.rows(52).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(52).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(52).cells(i).cellFormat().rightBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(52).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(52).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet.rows(52).cells(i).cellFormat().font().colorInfo(
					new $.ig.excel.WorkbookColorInfo(
							$.ig.excel.WorkbookThemeColorType.light1));
		}

		sheet.getCell('A53').value('1');
		sheet.getCell('B53').value('2');
		sheet.getCell('C53').value('3');
		sheet.getCell('D53').value('4');
		sheet.getCell('E53').value('5');

		for (i = 0; i <= 4; i++) {

			sheet.rows(53).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);
			sheet.rows(53).cells(i).cellFormat().rightBorderStyle(
					$.ig.excel.CellBorderLineStyle.thick);

		}
		// Save the workbook
		saveWorkbook(workbook, "GSTR3.xlsx");

	} else {
		alert("There is no data present during the selected Date Range. Please check it and then Fetch GSTR3");
	}
}

function saveWorkbook(workbook, name) {
	workbook.save({
		type : 'blob'
	}, function(data) {
		saveAs(data, name);
	}, function(error) {
		alert('Error exporting: : ' + error);
	});
}