function fetchGSTR1() {
	var formobj = document.getElementById("gst_form");
	var monn = formobj.sm.selectedIndex;
	var mon = formobj.sm.options[monn].value;
	var yrn = formobj.sy.selectedIndex;
	var yr = formobj.sy.options[yrn].value;
	var lastDay = new Date(yr, mon, 0);
	formobj.actionId.value = 7033;
	formobj.fd.value = yr + "-" + mon + "-01";
	formobj.td.value = yr + "-" + mon + "-" + lastDay.getDate();
	var ems = validateGSTR1Entries(monn, yrn);

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
	
	if((monn > cmonth && (parseInt(yr)) > cyear) || (monn > cmonth && (parseInt(yr)) === cyear) ||
			(parseInt(yr)) > cyear) {
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

function gstr1() {
	var flag = fetchGSTR1();
	if (flag) {
		document.getElementById("ggstr1_span").style.display = "block";
	}
}

function validateGSTR1Entries(monn, yrn) {
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

	return errmsg;
}

function generateGSTR1() {
	if (cvo_data != "" || cat_types_data != "" || arb_types_data != ""
			|| gstr1b2b_data != "" || gstr1b2cl_data != ""
			|| gstr1b2cs_data != "" || gstr1hsn_data != ""
			|| gstr1docs_data != "" || gstr1cdnr_data != ""
			|| gstr1cdnur_data != "") {
		
		var workbook = new $.ig.excel.Workbook();

		workbook.setCurrentFormat($.ig.excel.WorkbookFormat.excel2007);
		var sheet = workbook.worksheets().add('b2b');
		var sheet1 = workbook.worksheets().add('b2ba');
		var sheet2 = workbook.worksheets().add('b2cl');
		var sheet6 = workbook.worksheets().add('b2cla');

		var sheet3 = workbook.worksheets().add('b2cs');
		var sheet7 = workbook.worksheets().add('b2csa');

		var sheet4 = workbook.worksheets().add('cdnr');
		var sheet8 = workbook.worksheets().add('cdnra');

		var sheet5 = workbook.worksheets().add('cdnur');
		var sheet9 = workbook.worksheets().add('cdnura');

		var sheet12= workbook.worksheets().add('exp');
		var sheet13= workbook.worksheets().add('expa');
		
		var sheet14= workbook.worksheets().add('at');
		var sheet15= workbook.worksheets().add('ata');
		
		
		var sheet16 = workbook.worksheets().add('atadj');
		var sheet17 = workbook.worksheets().add('atadja');
		var sheet18 = workbook.worksheets().add('exemp');
		var sheet10 = workbook.worksheets().add('hsn');
		var sheet11 = workbook.worksheets().add('docs');
		var sheet19 = workbook.worksheets().add('master');
		
		//------------------------------------------------------------------------------------------------------------

		sheet.columns(0).setWidth(180,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet.columns(1).setWidth(150,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet.columns(2).setWidth(120,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet.columns(3).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet.columns(4).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet.columns(5).setWidth(150,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet.columns(6).setWidth(110,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet.columns(7).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet.columns(8).setWidth(150,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet.columns(9).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet.columns(10).setWidth(70,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet.columns(11).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet.columns(12).setWidth(100,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		
		sheet.getCell('A1').value('Summary For B2B(4)');
		sheet.getCell('A1').cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		sheet.getCell('A1').cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#0070C0'));
		sheet.getCell('B1').cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#0070C0'));
		sheet.getCell('A1').cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
		sheet.getCell('A1').cellFormat().font().colorInfo(
				new $.ig.excel.WorkbookColorInfo(
						$.ig.excel.WorkbookThemeColorType.light1));
		sheet.rows(0).cellFormat().font().bold(true);

		sheet.getCell('A1').cellFormat().font().height(14 * 14);

		for (i = 0; i <= 12; i++) {
			sheet.rows(1).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#0070C0'));
			sheet.rows(1).cells(i).cellFormat().font().height(14 * 14);
			sheet.rows(2).cells(i).cellFormat().font().height(14 * 14);
			sheet.rows(3).cells(i).cellFormat().font().height(14 * 15);

            sheet.rows(1).cells(i).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet.rows(1).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(1).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(1).cells(i).cellFormat().rightBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(1).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(1).cells(i).cellFormat().font().colorInfo(
					new $.ig.excel.WorkbookColorInfo(
							$.ig.excel.WorkbookThemeColorType.light1));
			sheet.rows(1).height(300);
			sheet.rows(1).cellFormat().font().bold(true);

		}

		for (i = 0; i <= 12; i++) {
			sheet.rows(2).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(2).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(2).cells(i).cellFormat().rightBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(2).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(2).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
		}

		for (i = 0; i <= 12; i++) {
			sheet.rows(3).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#F8CBAD'));
			sheet.rows(3).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(3).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet.rows(3).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet.rows(3).height(600);
		}
		
		sheet.getCell('A2').value('No. of Recipients');
		sheet.getCell('C2').value('No. of Invoices');
		sheet.getCell('E2').value('Total Invoice Value');
		sheet.getCell('L2').value('Total Taxable Value');
		sheet.getCell('M2').value('Total Cess');
		sheet
		.getCell('A3')
		.applyFormula(
				'=SUMPRODUCT((A5:A100 <> "")/COUNTIF(A5:A100,A5:A100 & ""))');
		/*sheet
				.getCell('A3')
				.applyFormula(
						'=SUMPRODUCT(--(FREQUENCY(IF(A5:A100<>"", MATCH(A5:A100,A5:A100,0)),ROW(A5:A100)-ROW(A5)+1)>0))');*/
		var len = gstr1b2b_data.length;
		len = len+4;
		/*sheet.getCell('A3').applyFormula(
				'=SUM(IF(FREQUENCY(MATCH(A5:A'+len+',A5:A'+len+',0),MATCH(A5:A'+len+',A5:A'+len+',0))>0,1))');*/
		sheet.getCell('A3').cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		/*sheet
				.getCell('C3')
				.applyFormula(
						'=SUMPRODUCT(--(FREQUENCY(IF(C5:C100<>"", MATCH(C5:C100,C5:C100,0)),ROW(C5:C100)-ROW(C5)+1)>0))');*/
		sheet
			.getCell('C3')
			.applyFormula(
			'=SUMPRODUCT((C5:C100 <> "")/COUNTIF(C5:C100,C5:C100 & ""))');
		sheet.getCell('C3').cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		sheet.getCell('E3').applyFormula('=SUM(E5:E100)');
		sheet.getCell('L3').applyFormula('=SUM(L5:L100)');
		sheet.getCell('M3').applyFormula('=SUM(M5:M100)');
		
		
		sheet.getCell('A4').value('GSTIN/UIN of Recipient');
		sheet.getCell('B4').value('Receiver Name');
		sheet.getCell('C4').value('Invoice Number');
		sheet.getCell('D4').value('Invoice date');
		sheet.getCell('E4').value('Invoice Value');
		sheet.getCell('F4').value('Place Of Supply');
		sheet.getCell('G4').value('Reverse Charge');
		sheet.getCell('H4').value('Applicable % of Tax Rate');
		sheet.getCell('I4').value('Invoice Type');
		sheet.getCell('J4').value('E-Commerce GSTIN');
		sheet.getCell('K4').value('Rate');
		sheet.getCell('L4').value('Taxable Value');
		sheet.getCell('M4').value('Cess Amount');

		for (i = 0; i < gstr1b2b_data.length; i++) {
			var j = i + 5;
			
			sheet.rows(j-1).cellFormat().font().height(14 * 14);

			/*sheet
			.getCell('A3')
			.applyFormula(
					'=SUM(IF(FREQUENCY(MATCH(A5:A10,A5:A10,0),MATCH(A5:A10,A5:A10,0))>0,1))');*/
			var custName = "";
			var custGSTIN = "";
			var state = "";
			if (gstr1b2b_data[i].customer_id > 0) {
				custName = getCustomerName(cvo_data,
						gstr1b2b_data[i].customer_id);
				custGSTIN = getCustomerGSTIN(cvo_data,
						gstr1b2b_data[i].customer_id);
				var custState = custGSTIN.substring(0, 2);
				switch (parseFloat(custState)) {
				case 1:
					state = "01-JAMMU & KASHMIR";
					break;
				case 2:
					state = "02-HIMACHAL PRADESH";
					break;
				case 3:
					state = "03-PUNJAB";
					break;
				case 4:
					state = "04-CHANDIGARH";
					break;
				case 5:
					state = "05-UTTARAKHAND";
					break;
				case 6:
					state = "06-HARYANA";
					break;
				case 7:
					state = "07-DELHI";
					break;
				case 8:
					state = "08-RAJASTHAN";
					break;
				case 9:
					state = "09-UTTAR PRADESH";
					break;
				case 10:
					state = "10-BIHAR";
					break;
				case 11:
					state = "11-SIKKIM";
					break;
				case 12:
					state = "12-ARUNACHAL PRADESH";
					break;
				case 13:
					state = "13-NAGALAND";
					break;
				case 14:
					state = "14-MANIPUR";
					break;
				case 15:
					state = "15-MIZORAM";
					break;
				case 16:
					state = "16-TRIPURA";
					break;
				case 17:
					state = "17-MEGHALAYA";
					break;
				case 18:
					state = "18-ASSAM";
					break;
				case 19:
					state = "19-WEST BENGAL";
					break;
				case 20:
					state = "20-JHARKHAND";
					break;
				case 21:
					state = "21-ODISHA";
					break;
				case 22:
					state = "22-CHHATTISGARH";
					break;
				case 23:
					state = "23-MADHYA PRADESH";
					break;
				case 24:
					state = "24-GUJARAT";
					break;
				case 25:
					state = "25-DAMAN & DIU";
					break;
				case 26:
					state = "26-DADRA & NAGAR HAVELI";
					break;
				case 27:
					state = "27-MAHARASHTRA";
					break;
				case 29:
					state = "29-KARNATAKA";
					break;
				case 30:
					state = "30-GOA";
					break;
				case 31:
					state = "31-LAKSHDWEEP";
					break;
				case 32:
					state = "32-KERALA";
					break;
				case 33:
					state = "33-TAMIL NADU";
					break;
				case 34:
					state = "34-PONDICHERRY";
					break;
				case 35:
					state = "35-ANDAMAN & NICOBAR ISLANDS";
					break;
				case 36:
					state = "36-TELANGANA";
					break;
				case 37:
					state = "37-ANDHRA PRADESH";
					break;
				case 97:
					state = "97-OTHER TERRITORY";
					break;

				}
			}
			var invDate = new Date(gstr1b2b_data[i].inv_date);
			var invAmt = gstr1b2b_data[i].inv_amount;
			var invno =  gstr1b2b_data[i].inv_no;
			
			
			
			sheet.getCell('A' + j).value(custGSTIN);
			sheet.getCell('B' + j).value(custName);
			sheet.getCell('C' + j).value(invno);
			sheet.getCell('D' + j).value(
					invDate.getDate() + "-" + months[invDate.getMonth()] + "-"
							+ invDate.getFullYear());
			sheet.getCell('E' + j).value(parseFloat(invAmt));
			sheet.getCell('F' + j).value(state);
			sheet.getCell('G' + j).value("N");
			sheet.getCell('I' + j).value("REGULAR");
			sheet.getCell('K' + j).value(gstr1b2b_data[i].gstp);
			sheet.getCell('L' + j).value(
					parseFloat(gstr1b2b_data[i].taxable_value));
			sheet.getCell('M' + j).value(0);
		}
		
//    B2CL		

		sheet2.columns(0).setWidth(180,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet2.columns(1).setWidth(100,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet2.columns(2).setWidth(150,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet2.columns(3).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet2.columns(4).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet2.columns(5).setWidth(70,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet2.columns(6).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet2.columns(7).setWidth(100,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet2.columns(8).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet2.columns(9).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		
		sheet2.getCell('A1').value('Summary For B2CL(5)');
		sheet2.getCell('A1').cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		sheet2.getCell('A1').cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#0070C0'));
		sheet2.getCell('A1').cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
		sheet2.getCell('A1').cellFormat().font().colorInfo(
				new $.ig.excel.WorkbookColorInfo(
						$.ig.excel.WorkbookThemeColorType.light1));
		sheet2.getCell('A1').cellFormat().font().Name = "Times New Roman";
		sheet2.rows(0).cellFormat().font().bold(true);
		sheet2.getCell('A1').cellFormat().font().height(14 * 14);
		
		for (i = 0; i <= 8; i++) {
			sheet2.rows(1).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#0070C0'));
			sheet2.rows(1).cells(i).cellFormat().font().height(14 * 14);
			sheet2.rows(2).cells(i).cellFormat().font().height(14 * 14);
			sheet2.rows(3).cells(i).cellFormat().font().height(14 * 15);

			sheet2.rows(1).cells(i).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet2.rows(1).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet2.rows(1).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet2.rows(1).cells(i).cellFormat().rightBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet2.rows(1).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet2.rows(1).cells(i).cellFormat().font().colorInfo(
					new $.ig.excel.WorkbookColorInfo(
							$.ig.excel.WorkbookThemeColorType.light1));
			sheet2.rows(1).height(300);
			sheet2.rows(1).cellFormat().font().bold(true);

		}

		for (i = 0; i <= 8; i++) {
			sheet2.rows(2).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet2.rows(2).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet2.rows(2).cells(i).cellFormat().rightBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet2.rows(2).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet2.rows(2).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
		}

		for (i = 0; i <= 9; i++) {
			sheet2.rows(3).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#F8CBAD'));
			sheet2.rows(3).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet2.rows(3).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet2.rows(3).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet2.rows(3).height(600);
		}

		sheet2.getCell('A2').value('No. of Invoices');
		sheet2.getCell('C2').value('Total Invoice Value');
		sheet2.getCell('G2').value('Total Taxable Value');
		sheet2.getCell('H2').value('Total Cess');
		/*sheet2
				.getCell('A3')
				.applyFormula(
						'=SUMPRODUCT(--(FREQUENCY(IF(A5:A100<>"", MATCH(A5:A100,A5:A100,0)),ROW(A5:A100)-ROW(A5)+1)>0))');*/
		sheet2
		.getCell('A3')
		.applyFormula(
				'=SUMPRODUCT((A5:A100 <> "")/COUNTIF(A5:A100,A5:A100 & ""))');
		sheet2.getCell('A3').cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		sheet2.getCell('C3').applyFormula('=SUM(C5:C100)');
		sheet2.getCell('G3').applyFormula('=SUM(G5:G100)');
		sheet2.getCell('H3').applyFormula('=SUM(H5:H100)');
		sheet2.getCell('A4').value('Invoice Number');
		sheet2.getCell('B4').value('Invoice date');
		sheet2.getCell('C4').value('Invoice Value');
		sheet2.getCell('D4').value('Place Of Supply');
		sheet2.getCell('E4').value('Applicable % of Tax Rate');
		sheet2.getCell('F4').value('Rate');
		sheet2.getCell('G4').value('Taxable Value');
		sheet2.getCell('H4').value('Cess Amount');
		sheet2.getCell('I4').value('E-Commerce GSTIN');
		sheet2.getCell('J4').value('Sale from Bonded WH');
		for (i = 0; i < gstr1b2cl_data.length; i++) {
			var j = i + 5;
			var invDate = new Date(gstr1b2cl_data[i].inv_date);
			var invAmt = gstr1b2cl_data[i].inv_amount;
			var invno = gstr1b2cl_data[i].inv_no;
			
			var state = "";
			switch (parseFloat(regstate)) {
			case 1:
				state = "01-JAMMU & KASHMIR";
				break;
			case 2:
				state = "02-HIMACHAL PRADESH";
				break;
			case 3:
				state = "03-PUNJAB";
				break;
			case 4:
				state = "04-CHANDIGARH";
				break;
			case 5:
				state = "05-UTTARAKHAND";
				break;
			case 6:
				state = "06-HARYANA";
				break;
			case 7:
				state = "07-DELHI";
				break;
			case 8:
				state = "08-RAJASTHAN";
				break;
			case 9:
				state = "09-UTTAR PRADESH";
				break;
			case 10:
				state = "10-BIHAR";
				break;
			case 11:
				state = "11-SIKKIM";
				break;
			case 12:
				state = "12-ARUNACHAL PRADESH";
				break;
			case 13:
				state = "13-NAGALAND";
				break;
			case 14:
				state = "14-MANIPUR";
				break;
			case 15:
				state = "15-MIZORAM";
				break;
			case 16:
				state = "16-TRIPURA";
				break;
			case 17:
				state = "17-MEGHALAYA";
				break;
			case 18:
				state = "18-ASSAM";
				break;
			case 19:
				state = "19-WEST BENGAL";
				break;
			case 20:
				state = "20-JHARKHAND";
				break;
			case 21:
				state = "21-ODISHA";
				break;
			case 22:
				state = "22-CHHATTISGARH";
				break;
			case 23:
				state = "23-MADHYA PRADESH";
				break;
			case 24:
				state = "24-GUJARAT";
				break;
			case 25:
				state = "25-DAMAN & DIU";
				break;
			case 26:
				state = "26-DADRA & NAGAR HAVELI";
				break;
			case 27:
				state = "27-MAHARASHTRA";
				break;
			case 29:
				state = "29-KARNATAKA";
				break;
			case 30:
				state = "30-GOA";
				break;
			case 31:
				state = "31-LAKSHDWEEP";
				break;
			case 32:
				state = "32-KERALA";
				break;
			case 33:
				state = "33-TAMIL NADU";
				break;
			case 34:
				state = "34-PONDICHERRY";
				break;
			case 35:
				state = "35-ANDAMAN & NICOBAR ISLANDS";
				break;
			case 36:
				state = "36-TELANGANA";
				break;
			case 37:
				state = "37-ANDHRA PRADESH";
				break;
			case 97:
				state = "97-OTHER TERRITORY";
				break;

			}
			sheet2.rows(j-1).cellFormat().font().height(14 * 14);
			sheet2.getCell('A' + j).value(invno);
			sheet2.getCell('B' + j).value(
					invDate.getDate() + "-" + months[invDate.getMonth()] + "-"
							+ invDate.getFullYear());
			sheet2.getCell('C' + j).value(parseFloat(invAmt));
			sheet2.getCell('D' + j).value(state);
			sheet2.getCell('F' + j).value(gstr1b2cl_data[i].gstp);
			sheet2.getCell('G' + j).value(
					parseFloat(gstr1b2cl_data[i].taxable_value));
			sheet2.getCell('H' + j).value(0);
		}

//  B2CS
		
		sheet3.columns(0).setWidth(180,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet3.columns(1).setWidth(150,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet3.columns(2).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet3.columns(3).setWidth(70,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet3.columns(4).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet3.columns(5).setWidth(150,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet3.columns(6).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet3.columns(7).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet3.columns(8).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet3.getCell('A1').value('Summary For B2CS(7)');
		sheet3.getCell('A1').cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		sheet3.getCell('A1').cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#0070C0'));
		sheet3.getCell('A1').cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
		sheet3.getCell('A1').cellFormat().font().colorInfo(
				new $.ig.excel.WorkbookColorInfo(
						$.ig.excel.WorkbookThemeColorType.light1));
		sheet3.getCell('A1').cellFormat().font().Name = "Times New Roman";
		sheet3.rows(0).cellFormat().font().bold(true);
		sheet3.getCell('A1').cellFormat().font().height(14 * 14);

		for (i = 0; i <= 6; i++) {
			sheet3.rows(1).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#0070C0'));
			sheet3.rows(1).cells(i).cellFormat().font().height(14 * 14);
			sheet3.rows(2).cells(i).cellFormat().font().height(14 * 14);
			sheet3.rows(3).cells(i).cellFormat().font().height(14 * 15);

			sheet3.rows(1).cells(i).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet3.rows(1).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet3.rows(1).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet3.rows(1).cells(i).cellFormat().rightBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet3.rows(1).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet3.rows(1).cells(i).cellFormat().font().colorInfo(
					new $.ig.excel.WorkbookColorInfo(
							$.ig.excel.WorkbookThemeColorType.light1));
			sheet3.rows(1).height(300);
			sheet3.rows(1).cellFormat().font().bold(true);

		}
		for (i = 0; i <= 5; i++) {
			sheet3.rows(2).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet3.rows(2).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet3.rows(2).cells(i).cellFormat().rightBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet3.rows(2).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet3.rows(2).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
		}
		for (i = 0; i <= 6; i++) {
			sheet3.rows(3).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#F8CBAD'));
			sheet3.rows(3).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet3.rows(3).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet3.rows(3).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet3.rows(3).height(600);
		}
		
		sheet3.getCell('E2').value('Total Taxable Value');
		sheet3.getCell('F2').value('Total Cess');
		sheet3.getCell('E3').applyFormula('=SUM(E5:E100)');
		sheet3.getCell('F3').applyFormula('=SUM(F5:F100)');
		sheet3.getCell('A4').value('Type');
		sheet3.getCell('B4').value('Place Of Supply');
		sheet3.getCell('C4').value('Applicable % of Tax Rate');
		sheet3.getCell('D4').value('Rate');
		sheet3.getCell('E4').value('Taxable Value');
		sheet3.getCell('F4').value('Cess Amount');
		sheet3.getCell('G4').value('E-Commerce GSTIN');
		var taxable = 0;

		for (i = 0; i < gstr1b2cs_data.length; i++) {
			j = i + 5;
			
			sheet3.rows(j-1).cellFormat().font().height(14 * 14);
			
			var gstper = gstr1b2cs_data[i].gstp;
			var state = "";
			switch (parseFloat(regstate)) {
			case 1:
				state = "01-JAMMU & KASHMIR";
				break;
			case 2:
				state = "02-HIMACHAL PRADESH";
				break;
			case 3:
				state = "03-PUNJAB";
				break;
			case 4:
				state = "04-CHANDIGARH";
				break;
			case 5:
				state = "05-UTTARAKHAND";
				break;
			case 6:
				state = "06-HARYANA";
				break;
			case 7:
				state = "07-DELHI";
				break;
			case 8:
				state = "08-RAJASTHAN";
				break;
			case 9:
				state = "09-UTTAR PRADESH";
				break;
			case 10:
				state = "10-BIHAR";
				break;
			case 11:
				state = "11-SIKKIM";
				break;
			case 12:
				state = "12-ARUNACHAL PRADESH";
				break;
			case 13:
				state = "13-NAGALAND";
				break;
			case 14:
				state = "14-MANIPUR";
				break;
			case 15:
				state = "15-MIZORAM";
				break;
			case 16:
				state = "16-TRIPURA";
				break;
			case 17:
				state = "17-MEGHALAYA";
				break;
			case 18:
				state = "18-ASSAM";
				break;
			case 19:
				state = "19-WEST BENGAL";
				break;
			case 20:
				state = "20-JHARKHAND";
				break;
			case 21:
				state = "21-ODISHA";
				break;
			case 22:
				state = "22-CHHATTISGARH";
				break;
			case 23:
				state = "23-MADHYA PRADESH";
				break;
			case 24:
				state = "24-GUJARAT";
				break;
			case 25:
				state = "25-DAMAN & DIU";
				break;
			case 26:
				state = "26-DADRA & NAGAR HAVELI";
				break;
			case 27:
				state = "27-MAHARASHTRA";
				break;
			case 29:
				state = "29-KARNATAKA";
				break;
			case 30:
				state = "30-GOA";
				break;
			case 31:
				state = "31-LAKSHDWEEP";
				break;
			case 32:
				state = "32-KERALA";
				break;
			case 33:
				state = "33-TAMIL NADU";
				break;
			case 34:
				state = "34-PONDICHERRY";
				break;
			case 35:
				state = "35-ANDAMAN & NICOBAR ISLANDS";
				break;
			case 36:
				state = "36-TELANGANA";
				break;
			case 37:
				state = "37-ANDHRA PRADESH";
				break;
			case 97:
				state = "97-OTHER TERRITORY";
				break;

			}
			
			if (parseFloat(gstper) == 0) {

				taxable = parseFloat(gstr1b2cs_data[i].taxable_value);
				sheet3.getCell('A' + j).value("OE");
				sheet3.getCell('B' + j).value(state);
				sheet3.getCell('D' + j).value(gstper);
				sheet3.getCell('E' + j).value(taxable);
				sheet3.getCell('F' + j).value(0);
			}
			
			if (parseFloat(gstper) == 5) {

				taxable = parseFloat(gstr1b2cs_data[i].taxable_value);
				sheet3.getCell('A' + j).value("OE");
				sheet3.getCell('B' + j).value(state);
				sheet3.getCell('D' + j).value(gstper);
				sheet3.getCell('E' + j).value(taxable);
				sheet3.getCell('F' + j).value(0);
			}
			if (parseFloat(gstper) == 12) {

				taxable = parseFloat(gstr1b2cs_data[i].taxable_value);
				sheet3.getCell('A' + j).value("OE");
				sheet3.getCell('B' + j).value(state);
				sheet3.getCell('D' + j).value(gstper);
				sheet3.getCell('E' + j).value(taxable);
				sheet3.getCell('F' + j).value(0);
			}
			if (parseFloat(gstper) == 18) {

				taxable = parseFloat(gstr1b2cs_data[i].taxable_value);
				sheet3.getCell('A' + j).value("OE");
				sheet3.getCell('B' + j).value(state);
				sheet3.getCell('D' + j).value(gstper);
				sheet3.getCell('E' + j).value(taxable);
				sheet3.getCell('F' + j).value(0);

			}
			if (parseFloat(gstper) == 28) {
				taxable = parseFloat(gstr1b2cs_data[i].taxable_value);
				sheet3.getCell('A' + j).value("OE");
				sheet3.getCell('B' + j).value(state);
				sheet3.getCell('D' + j).value(gstper);
				sheet3.getCell('E' + j).value(taxable);
				sheet3.getCell('F' + j).value(0);

			}

		}

//  CDNR
		
		sheet4.columns(0).setWidth(180,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet4.columns(1).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet4.columns(2).setWidth(200,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet4.columns(3).setWidth(200,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet4.columns(4).setWidth(200,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet4.columns(5).setWidth(200,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet4.columns(6).setWidth(150,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet4.columns(7).setWidth(150,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet4.columns(8).setWidth(230,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet4.columns(9).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet4.columns(10).setWidth(70,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet4.columns(11).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet4.columns(12).setWidth(100,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet4.columns(13).setWidth(80,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);

		sheet4.getCell('A1').value('Summary For CDNR(9B)');
		sheet4.getCell('A1').cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		sheet4.getCell('A1').cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#0070C0'));
		sheet4.getCell('A1').cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
		sheet4.getCell('A1').cellFormat().font().colorInfo(
				new $.ig.excel.WorkbookColorInfo(
						$.ig.excel.WorkbookThemeColorType.light1));
		sheet4.getCell('A1').cellFormat().font().Name = "Times New Roman";
		sheet4.rows(0).cellFormat().font().bold(true);
		sheet4.getCell('A1').cellFormat().font().height(14 * 14);
		
		for (i = 0; i <= 13; i++) {
			sheet4.rows(1).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#0070C0'));
			sheet4.rows(1).cells(i).cellFormat().font().height(14 * 14);
			sheet4.rows(2).cells(i).cellFormat().font().height(14 * 14);
			sheet4.rows(3).cells(i).cellFormat().font().height(14 * 15);

			sheet4.rows(1).cells(i).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet4.rows(1).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet4.rows(1).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet4.rows(1).cells(i).cellFormat().rightBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet4.rows(1).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet4.rows(1).cells(i).cellFormat().font().colorInfo(
					new $.ig.excel.WorkbookColorInfo(
							$.ig.excel.WorkbookThemeColorType.light1));
			sheet4.rows(1).height(300);
			sheet4.rows(1).cellFormat().font().bold(true);

		}
		for (i = 0; i <= 13; i++) {
			sheet4.rows(2).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet4.rows(2).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet4.rows(2).cells(i).cellFormat().rightBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet4.rows(2).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet4.rows(2).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
		}
		for (i = 0; i <= 13; i++) {
			sheet4.rows(3).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#F8CBAD'));
			sheet4.rows(3).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet4.rows(3).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet4.rows(3).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet4.rows(3).height(600);
		}
		
		sheet4.getCell('A2').value('No. of Recipients');
		sheet4.getCell('C2').value('No. of Invoices');
		sheet4.getCell('E2').value('No. of Notes/Vouchers');
		sheet4.getCell('I2').value('Total Note/Refund Voucher Value');
		sheet4.getCell('L2').value('Total Taxable Value');
		sheet4.getCell('M2').value('Total Cess');
		sheet4
				.getCell('A3')
				.applyFormula(
						'=SUMPRODUCT((A5:A100 <> "")/COUNTIF(A5:A100,A5:A100 & ""))');
		sheet4.getCell('A3').cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		sheet4
				.getCell('C3')
				.applyFormula(
						'=SUMPRODUCT((C5:C100 <> "")/COUNTIF(C5:C100,C5:C100 & ""))');
		sheet4.getCell('C3').cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		sheet4
				.getCell('E3')
				.applyFormula(
						'=SUMPRODUCT((E5:E100 <> "")/COUNTIF(E5:E100,E5:E100 & ""))');
		sheet4.getCell('E3').cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);

		sheet4.getCell('I3').applyFormula('=SUM(I5:I100)');
		sheet4.getCell('L3').applyFormula('=SUM(L5:L100)');
		sheet4.getCell('M3').applyFormula('=SUM(M5:M100)');
		sheet4.getCell('A4').value('GSTIN/UIN of Recipient');
		sheet4.getCell('B4').value('Receiver Name');
		sheet4.getCell('C4').value('Invoice/Advance Receipt Number');
		sheet4.getCell('D4').value('Invoice/Advance Receipt date');
		sheet4.getCell('E4').value('Note/Refund Voucher Number');
		sheet4.getCell('F4').value('Note/Refund Voucher date');
		sheet4.getCell('G4').value('Document Type');
	//	sheet4.getCell('H4').value('Reason For Issuing document');
		sheet4.getCell('H4').value('Place Of Supply');
		sheet4.getCell('I4').value('Note/Refund Voucher Value');
		sheet4.getCell('J4').value('Applicable % of Tax Rate');
		sheet4.getCell('K4').value('Rate');
		sheet4.getCell('L4').value('Taxable Value');
		sheet4.getCell('M4').value('Cess Amount');
		sheet4.getCell('N4').value('Pre GST');

		for (i = 0; i < gstr1cdnr_data.length; i++) {
			var j = i + 5;
			sheet4.rows(j-1).cellFormat().font().height(14 * 14);
			var custName = "";
			var custGSTIN = "";
			var state = "";
			if (gstr1cdnr_data[i].cvo_id > 0) {
				custName = getCustomerName(cvo_data, gstr1cdnr_data[i].cvo_id);
				custGSTIN = getCustomerGSTIN(cvo_data, gstr1cdnr_data[i].cvo_id);
				var str = "Hello world!";
				var custState = custGSTIN.substring(0, 2);
				switch (parseFloat(custState)) {
				case 1:
					state = "01-JAMMU & KASHMIR";
					break;
				case 2:
					state = "02-HIMACHAL PRADESH";
					break;
				case 3:
					state = "03-PUNJAB";
					break;
				case 4:
					state = "04-CHANDIGARH";
					break;
				case 5:
					state = "05-UTTARAKHAND";
					break;
				case 6:
					state = "06-HARYANA";
					break;
				case 7:
					state = "07-DELHI";
					break;
				case 8:
					state = "08-RAJASTHAN";
					break;
				case 9:
					state = "09-UTTAR PRADESH";
					break;
				case 10:
					state = "10-BIHAR";
					break;
				case 11:
					state = "11-SIKKIM";
					break;
				case 12:
					state = "12-ARUNACHAL PRADESH";
					break;
				case 13:
					state = "13-NAGALAND";
					break;
				case 14:
					state = "14-MANIPUR";
					break;
				case 15:
					state = "15-MIZORAM";
					break;
				case 16:
					state = "16-TRIPURA";
					break;
				case 17:
					state = "17-MEGHALAYA";
					break;
				case 18:
					state = "18-ASSAM";
					break;
				case 19:
					state = "19-WEST BENGAL";
					break;
				case 20:
					state = "20-JHARKHAND";
					break;
				case 21:
					state = "21-ODISHA";
					break;
				case 22:
					state = "22-CHHATTISGARH";
					break;
				case 23:
					state = "23-MADHYA PRADESH";
					break;
				case 24:
					state = "24-GUJARAT";
					break;
				case 25:
					state = "25-DAMAN & DIU";
					break;
				case 26:
					state = "26-DADRA & NAGAR HAVELI";
					break;
				case 27:
					state = "27-MAHARASHTRA";
					break;
				case 29:
					state = "29-KARNATAKA";
					break;
				case 30:
					state = "30-GOA";
					break;
				case 31:
					state = "31-LAKSHDWEEP";
					break;
				case 32:
					state = "32-KERALA";
					break;
				case 33:
					state = "33-TAMIL NADU";
					break;
				case 34:
					state = "34-PONDICHERRY";
					break;
				case 35:
					state = "35-ANDAMAN & NICOBAR ISLANDS";
					break;
				case 36:
					state = "36-TELANGANA";
					break;
				case 37:
					state = "37-ANDHRA PRADESH";
					break;
				case 97:
					state = "97-OTHER TERRITORY";
					break;

				}
			}
			var cdrDate = new Date(gstr1cdnr_data[i].note_date);
			var receiptDate = new Date(gstr1cdnr_data[i].receipt_date);
			var invAmt = gstr1cdnr_data[i].inv_amount;
			var cdrid = gstr1cdnr_data[i].id;
			var nreason = gstr1cdnr_data[i].nreasons;
			var reason = "";
			if (nreason == 1) {
				var reason = "01-Sales Return";
			} else if (nreason == 2) {
				var reason = "02-Post Sale Discount";
			} else if (nreason == 3) {
				var reason = "03-Deficiency in services";
			} else if (nreason == 4) {
				var reason = "04-Change in POS";
			} else if (nreason == 5) {
				var reason = "05-Correction in Invoice";
			} else if (nreason == 6) {
				var reason = "06-Finalization of Provisional assessment";
			} else if (nreason == 7) {
				var reason = "07-Others";
			}
			
			sheet4.getCell('A' + j).value(custGSTIN);
			sheet4.getCell('B' + j).value(custName);
			sheet4.getCell('C' + j).value(gstr1cdnr_data[i].ref_no);
			sheet4.getCell('D' + j).value(
					receiptDate.getDate() + "-"
							+ months[receiptDate.getMonth()] + "-"
							+ receiptDate.getFullYear());

			sheet4.getCell('E' + j).value(cdrid);

			sheet4.getCell('F' + j).value(
					cdrDate.getDate() + "-" + months[cdrDate.getMonth()] + "-"
							+ cdrDate.getFullYear());
			sheet4.getCell('G' + j).value(gstr1cdnr_data[i].doc_type);
		//	sheet4.getCell('H' + j).value(reason);
			sheet4.getCell('H' + j).value(state);
			sheet4.getCell('I' + j).value(
					parseFloat(gstr1cdnr_data[i].credit_or_debit_amount));
			sheet4.getCell('K' + j).value(gstr1cdnr_data[i].gstp);
			sheet4.getCell('L' + j).value(
					parseFloat(gstr1cdnr_data[i].taxable_amount));
			sheet4.getCell('M' + j).value(0);
			sheet4.getCell('N' + j).value("");
		}

//  CDNUR		
		sheet5.columns(0).setWidth(200,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet5.columns(1).setWidth(200,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet5.columns(2).setWidth(180,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet5.columns(3).setWidth(100,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet5.columns(4).setWidth(200,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet5.columns(5).setWidth(200,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet5.columns(6).setWidth(200,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet5.columns(7).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet5.columns(8).setWidth(180,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet5.columns(9).setWidth(70,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet5.columns(10).setWidth(150,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet5.columns(11).setWidth(150,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet5.columns(12).setWidth(100,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);

		sheet5.getCell('A1').value('Summary For CDNUR(9B)');
		sheet5.getCell('A1').cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		sheet5.getCell('A1').cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#0070C0'));
		sheet5.getCell('A1').cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
		sheet5.getCell('A1').cellFormat().font().colorInfo(
				new $.ig.excel.WorkbookColorInfo(
						$.ig.excel.WorkbookThemeColorType.light1));
		sheet5.getCell('A1').cellFormat().font().Name = "Times New Roman";
		sheet5.rows(0).cellFormat().font().bold(true);
		sheet5.getCell('A1').cellFormat().font().height(14 * 14);

		for (i = 0; i <= 12; i++) {
			sheet5.rows(1).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#0070C0'));
			sheet5.rows(1).cells(i).cellFormat().font().height(14 * 14);
			sheet5.rows(2).cells(i).cellFormat().font().height(14 * 14);
			sheet5.rows(3).cells(i).cellFormat().font().height(14 * 15);

			sheet5.rows(1).cells(i).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet5.rows(1).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet5.rows(1).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet5.rows(1).cells(i).cellFormat().rightBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet5.rows(1).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet5.rows(1).cells(i).cellFormat().font().colorInfo(
					new $.ig.excel.WorkbookColorInfo(
							$.ig.excel.WorkbookThemeColorType.light1));
			sheet5.rows(1).height(300);
			sheet5.rows(1).cellFormat().font().bold(true);
		}

		for (i = 0; i <= 12; i++) {
			sheet5.rows(2).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet5.rows(2).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet5.rows(2).cells(i).cellFormat().rightBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet5.rows(2).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet5.rows(2).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
		}
		for (i = 0; i <= 12; i++) {
			sheet5.rows(3).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#F8CBAD'));
		    sheet5.rows(3).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet5.rows(3).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet5.rows(3).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet5.rows(3).height(600);
		}
		
		sheet5.getCell('B2').value('No. of Notes/Vouchers');
		sheet5.getCell('E2').value('No. of Invoices');
		sheet5.getCell('H2').value('Total Note Value');
		sheet5.getCell('K2').value('Total Taxable Value');
		sheet5.getCell('L2').value('Total Cess');
		sheet5
				.getCell('B3')
				.applyFormula(
						'=SUMPRODUCT((B5:B100 <> "")/COUNTIF(B5:B100,B5:B100 & ""))');
		sheet5.getCell('B3').cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		sheet5
				.getCell('E3')
				.applyFormula(
						'=SUMPRODUCT((E5:E100 <> "")/COUNTIF(E5:E100,E5:E100 & ""))');
		sheet5.getCell('E3').cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);

		sheet5.getCell('H3').applyFormula('=SUM(H5:H100)');
		sheet5.getCell('K3').applyFormula('=SUM(K5:K100)');
		sheet5.getCell('L3').applyFormula('=SUM(L5:L100)');
		sheet5.getCell('A4').value('UR Type');
		sheet5.getCell('B4').value('Note/Refund Voucher Number');
		sheet5.getCell('C4').value('Note/Refund Voucher date');
		sheet5.getCell('D4').value('Document Type');
		sheet5.getCell('E4').value('Invoice/Advance Receipt Number');
		sheet5.getCell('F4').value('Invoice/Advance Receipt date');
	//	sheet5.getCell('G4').value('Reason For Issuing document');
		sheet5.getCell('G4').value('Place Of Supply');
		sheet5.getCell('H4').value('Note/Refund Voucher Value');
		sheet5.getCell('I4').value('Applicable % of Tax Rate');
		sheet5.getCell('J4').value('Rate');
		sheet5.getCell('K4').value('Taxable Value');
		sheet5.getCell('L4').value('Cess Amount');
		sheet5.getCell('M4').value('Pre GST');

		for (i = 0; i < gstr1cdnur_data.length; i++) {
			var j = i + 5;
			sheet5.rows(j-1).cellFormat().font().height(14 * 14);
			var state = "";
			if (gstr1cdnur_data[i].cvo_id > 0) {

				var str = "Hello world!";
				var custState = custGSTIN.substring(0, 2);
				switch (parseFloat(custState)) {
				case 1:
					state = "01-JAMMU & KASHMIR";
					break;
				case 2:
					state = "02-HIMACHAL PRADESH";
					break;
				case 3:
					state = "03-PUNJAB";
					break;
				case 4:
					state = "04-CHANDIGARH";
					break;
				case 5:
					state = "05-UTTARAKHAND";
					break;
				case 6:
					state = "06-HARYANA";
					break;
				case 7:
					state = "07-DELHI";
					break;
				case 8:
					state = "08-RAJASTHAN";
					break;
				case 9:
					state = "09-UTTAR PRADESH";
					break;
				case 10:
					state = "10-BIHAR";
					break;
				case 11:
					state = "11-SIKKIM";
					break;
				case 12:
					state = "12-ARUNACHAL PRADESH";
					break;
				case 13:
					state = "13-NAGALAND";
					break;
				case 14:
					state = "14-MANIPUR";
					break;
				case 15:
					state = "15-MIZORAM";
					break;
				case 16:
					state = "16-TRIPURA";
					break;
				case 17:
					state = "17-MEGHALAYA";
					break;
				case 18:
					state = "18-ASSAM";
					break;
				case 19:
					state = "19-WEST BENGAL";
					break;
				case 20:
					state = "20-JHARKHAND";
					break;
				case 21:
					state = "21-ODISHA";
					break;
				case 22:
					state = "22-CHHATTISGARH";
					break;
				case 23:
					state = "23-MADHYA PRADESH";
					break;
				case 24:
					state = "24-GUJARAT";
					break;
				case 25:
					state = "25-DAMAN & DIU";
					break;
				case 26:
					state = "26-DADRA & NAGAR HAVELI";
					break;
				case 27:
					state = "27-MAHARASHTRA";
					break;
				case 29:
					state = "29-KARNATAKA";
					break;
				case 30:
					state = "30-GOA";
					break;
				case 31:
					state = "31-LAKSHDWEEP";
					break;
				case 32:
					state = "32-KERALA";
					break;
				case 33:
					state = "33-TAMIL NADU";
					break;
				case 34:
					state = "34-PONDICHERRY";
					break;
				case 35:
					state = "35-ANDAMAN & NICOBAR ISLANDS";
					break;
				case 36:
					state = "36-TELANGANA";
					break;
				case 37:
					state = "37-ANDHRA PRADESH";
					break;
				case 97:
					state = "97-OTHER TERRITORY";
					break;

				}
			}
			var cdrDate = new Date(gstr1cdnur_data[i].note_date);
			var receiptDate = new Date(gstr1cdnur_data[i].receipt_date);
			var invAmt = gstr1cdnur_data[i].inv_amount;
			
			var cdrid = gstr1cdnur_data[i].id;
			var nreason = gstr1cdnur_data[i].nreasons;
			var reason = "";
			if (nreason == 1) {
				var reason = "01-Sales Return";
			} else if (nreason == 2) {
				var reason = "02-Post Sale Discount";
			} else if (nreason == 3) {
				var reason = "03-Deficiency in services";
			} else if (nreason == 4) {
				var reason = "04-Correction in Invoice";
			} else if (nreason == 5) {
				var reason = "05-Change in POS";
			} else if (nreason == 6) {
				var reason = "06-Finalization of Provisional assessment";
			} else if (nreason == 7) {
				var reason = "07-Others";
			}
			
			sheet5.getCell('A' + j).value();
			sheet5.getCell('B' + j).value(cdrid);
			sheet5.getCell('C' + j).value(
					cdrDate.getDate() + "-" + months[cdrDate.getMonth()] + "-"
							+ cdrDate.getFullYear());
			sheet5.getCell('D' + j).value(gstr1cdnur_data[i].doc_type);
			sheet5.getCell('E' + j).value(gstr1cdnur_data[i].ref_no);
			sheet5.getCell('F' + j).value(
					receiptDate.getDate() + "-"
							+ months[receiptDate.getMonth()] + "-"
							+ receiptDate.getFullYear());

		//	sheet5.getCell('G' + j).value(reason);
			sheet5.getCell('G' + j).value(state);
		//	sheet5.getCell('H' + j).value(state);
			sheet5.getCell('H' + j).value(
					parseFloat(gstr1cdnur_data[i].credit_or_debit_amount));
			sheet5.getCell('J' + j).value(gstr1cdnur_data[i].gstp);
			sheet5.getCell('K' + j).value(
					parseFloat(gstr1cdnur_data[i].taxable_amount));
			sheet5.getCell('L' + j).value(0);
			sheet5.getCell('M' + j).value("");
		}

//  B2CLA
		
		sheet6.columns(0).setWidth(180,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet6.columns(1).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet6.columns(2).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet6.columns(3).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet6.columns(4).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet6.columns(5).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		
		sheet6.columns(6).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet6.columns(7).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet6.columns(8).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet6.columns(9).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet6.columns(10).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet6.columns(11).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		
		sheet6.getCell('A1').value('Summary For B2CLA');
		sheet6.getCell('A1').cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		sheet6.getCell('A1').cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#0070C0'));
		sheet6.getCell('A1').cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
		sheet6.getCell('A1').cellFormat().font().colorInfo(
				new $.ig.excel.WorkbookColorInfo(
						$.ig.excel.WorkbookThemeColorType.light1));
		sheet6.getCell('A1').cellFormat().font().Name = "Times New Roman";
		sheet6.rows(0).cells(0).cellFormat().font().bold(true);
		sheet6.rows(0).cellFormat().font().height(14 * 14);
		
		var mergedRegion1 = sheet6.mergedCellsRegions().add(0, 1, 0, 2);
		mergedRegion1.value('Original details');

		sheet6.getCell('B1').cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		sheet6.getCell('B1').cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#F8CBAD'));
		sheet6.getCell('B1').cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
		sheet6.getCell('B1').cellFormat().font().Name = "Times New Roman";

		var mergedRegion1 = sheet6.mergedCellsRegions().add(0, 3, 0, 10);
		mergedRegion1.value('Revised Details');
		sheet6.getCell('E1').cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		sheet6.getCell('E1').cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#B4C7E7'));
		sheet6.getCell('E1').cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
		sheet6.getCell('E1').cellFormat().font().Name = "Times New Roman";
		sheet6.getCell('J1').cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#B4C7E7'));
		sheet6.rows(0).cells(4).cellFormat().font().bold(true);
		
		for (i = 0; i <=10; i++) {
			sheet6.rows(1).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#0070C0'));
			sheet6.rows(1).cells(i).cellFormat().font().height(14 * 14);
			sheet6.rows(2).cells(i).cellFormat().font().height(14 * 14);
			sheet6.rows(3).cells(i).cellFormat().font().height(14 * 15);

			sheet6.rows(1).cells(i).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet6.rows(1).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet6.rows(1).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet6.rows(1).cells(i).cellFormat().rightBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet6.rows(1).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet6.rows(1).cells(i).cellFormat().font().colorInfo(
					new $.ig.excel.WorkbookColorInfo(
							$.ig.excel.WorkbookThemeColorType.light1));
			sheet6.rows(1).height(300);
			sheet6.rows(1).cellFormat().font().bold(true);
		}

		for (i = 0; i <= 9; i++) {
			sheet6.rows(2).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet6.rows(2).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet6.rows(2).cells(i).cellFormat().rightBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet6.rows(2).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet6.rows(2).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
		}

		for (i = 0; i <= 2; i++) {
			sheet6.rows(3).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#F8CBAD'));
			sheet6.rows(3).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet6.rows(3).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet6.rows(3).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet6.rows(3).height(600);
		}
		
		for (i = 3; i <= 11; i++) {
			sheet6.rows(3).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#B4C7E7'));
			sheet6.rows(3).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet6.rows(3).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet6.rows(3).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet6.rows(3).height(600);
		}

		sheet6.getCell('A2').value('No. of Invoices');
		sheet6.getCell('F2').value('Total Inv Value');
		sheet6.getCell('I2').value('Total Taxable Value');
		sheet6.getCell('J2').value('Total Cess');

		sheet6
		.getCell('A3')
		.applyFormula(
				'=SUMPRODUCT((A5:A100 <> "")/COUNTIF(A5:A100,A5:A100 & ""))');
		sheet6.getCell('A3').cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		sheet6.getCell('F3').applyFormula('=SUM(F5:F100)');
		sheet6.getCell('I3').applyFormula('=SUM(I5:I100)');
		sheet6.getCell('J3').applyFormula('=SUM(J5:J100)');
		sheet6.getCell('A4').value('Original Invoice Number');
		sheet6.getCell('B4').value('Original Invoice date');
		sheet6.getCell('C4').value('Original Place Of Supply');
		sheet6.getCell('D4').value('Revised Invoice Number');
		sheet6.getCell('E4').value('Revised Invoice date');
		sheet6.getCell('F4').value('Invoice Value');
		sheet6.getCell('G4').value('Applicable % of Tax Rate');
		sheet6.getCell('H4').value('Rate');
		sheet6.getCell('I4').value('Taxable Value');
		sheet6.getCell('J4').value('Cess Amount');
		sheet6.getCell('K4').value('E-Commerce GSTIN');
		sheet6.getCell('L4').value('Sale from Bonded WH');

//  B2CSA
		
		sheet7.columns(0).setWidth(180,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet7.columns(1).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet7.columns(2).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet7.columns(3).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet7.columns(4).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet7.columns(5).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		
		sheet7.columns(6).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet7.columns(7).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet7.columns(8).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet7.columns(9).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		
		sheet7.getCell('A1').value('Summary For B2CSA');
		sheet7.getCell('A1').cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		sheet7.getCell('A1').cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#0070C0'));
		sheet7.getCell('A1').cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
		sheet7.getCell('A1').cellFormat().font().colorInfo(
				new $.ig.excel.WorkbookColorInfo(
						$.ig.excel.WorkbookThemeColorType.light1));
		sheet7.getCell('A1').cellFormat().font().Name = "Times New Roman";
		sheet7.rows(0).cellFormat().font().bold(true);
		sheet7.rows(0).cellFormat().font().height(14 * 14);
		
		var mergedRegion1 = sheet7.mergedCellsRegions().add(0, 1, 0, 1);
		mergedRegion1.value('Original details');

		sheet7.getCell('B1').cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		sheet7.getCell('B1').cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#F8CBAD'));
		sheet7.getCell('B1').cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
		sheet7.getCell('B1').cellFormat().font().Name = "Times New Roman";
		sheet7.rows(0).cellFormat().font().bold(true);
		sheet7.rows(1).cellFormat().font().height(14 * 14);
		sheet7.rows(2).cellFormat().font().height(14 * 14);
		sheet7.rows(3).cellFormat().font().height(14 * 15);

		var mergedRegion1 = sheet7.mergedCellsRegions().add(0, 2, 0, 7);
		mergedRegion1.value('Revised Details');
		sheet7.getCell('D1').cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		sheet7.getCell('D1').cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#B4C7E7'));
		sheet7.getCell('D1').cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
		sheet7.getCell('D1').cellFormat().font().Name = "Times New Roman";
		sheet7.rows(0).cellFormat().font().bold(true);

		sheet7.getCell('I1').cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#B4C7E7'));
		
		for (i = 0; i <=8; i++) {
			sheet7.rows(1).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#0070C0'));
			
			sheet7.rows(1).cells(i).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet7.rows(1).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet7.rows(1).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet7.rows(1).cells(i).cellFormat().rightBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet7.rows(1).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet7.rows(1).cells(i).cellFormat().font().colorInfo(
					new $.ig.excel.WorkbookColorInfo(
							$.ig.excel.WorkbookThemeColorType.light1));
			sheet7.rows(1).height(300);
			sheet7.rows(1).cellFormat().font().bold(true);
		}

		for (i = 0; i <= 8; i++) {
			sheet7.rows(2).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet7.rows(2).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet7.rows(2).cells(i).cellFormat().rightBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet7.rows(2).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet7.rows(2).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
		}

		for (i = 0; i <= 2; i++) {
			sheet7.rows(3).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#F8CBAD'));
			sheet7.rows(3).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet7.rows(3).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet7.rows(3).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet7.rows(3).height(600);
		}
		
		for (i = 2; i <= 8; i++) {
			sheet7.rows(3).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#B4C7E7'));
			sheet7.rows(3).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet7.rows(3).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet7.rows(3).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet7.rows(3).height(600);
		}
		
		sheet7.getCell('G2').value('Total Taxable Value');
		sheet7.getCell('H2').value('Total Cess');

		sheet7.getCell('G3').applyFormula('=SUM(G5:G100)');
		sheet7.getCell('H3').applyFormula('=SUM(H5:H100)');
		
		sheet7.getCell('A4').value('Financial Year');
		sheet7.getCell('B4').value('Original Month');
		sheet7.getCell('C4').value('Place Of Supply');
//		sheet7.getCell('C4').value('Original Place Of Supply');
//		sheet7.getCell('D4').value('Revised Place Of Supply');
		sheet7.getCell('D4').value('Type');
		sheet7.getCell('E4').value('Applicable % of Tax Rate');
		sheet7.getCell('F4').value('Rate');
		sheet7.getCell('G4').value('Taxable Value');
		sheet7.getCell('H4').value('Cess Amount');
		sheet7.getCell('I4').value('E-Commerce GSTIN');

//  CDNRA
		
		sheet8.columns(0).setWidth(180,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet8.columns(1).setWidth(180,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet8.columns(2).setWidth(250,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet8.columns(3).setWidth(240,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet8.columns(4).setWidth(250,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet8.columns(5).setWidth(250,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		
		sheet8.columns(6).setWidth(250,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet8.columns(7).setWidth(240,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet8.columns(8).setWidth(120,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet8.columns(9).setWidth(100,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		
		sheet8.columns(10).setWidth(250,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet8.columns(11).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet8.columns(12).setWidth(70,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet8.columns(13).setWidth(150,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		
		sheet8.columns(14).setWidth(100,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet8.columns(15).setWidth(80,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet8.columns(16).setWidth(300,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		
		sheet8.getCell('A1').value('Summary For CDNRA');
		sheet8.getCell('A1').cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		sheet8.getCell('A1').cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#0070C0'));
		sheet8.getCell('A1').cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
		sheet8.getCell('A1').cellFormat().font().colorInfo(
				new $.ig.excel.WorkbookColorInfo(
						$.ig.excel.WorkbookThemeColorType.light1));
		sheet8.getCell('A1').cellFormat().font().Name = "Times New Roman";
		sheet8.rows(0).cellFormat().font().bold(true);
		sheet8.rows(0).cellFormat().font().height(14 * 14);
		
		var mergedRegion1 = sheet8.mergedCellsRegions().add(0, 1, 0, 5);
		mergedRegion1.value('Original details');

		//sheet1.getCell('B1').value('Original details');
		sheet8.getCell('B1').cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		sheet8.getCell('B1').cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#F8CBAD'));
		sheet8.getCell('B1').cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
		sheet8.getCell('B1').cellFormat().font().Name = "Times New Roman";
		sheet8.rows(0).cellFormat().font().bold(true);

		
		var mergedRegion1 = sheet8.mergedCellsRegions().add(0, 6, 0, 15);
		mergedRegion1.value('Revised Details');
		sheet8.getCell('G1').cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		sheet8.getCell('G1').cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#B4C7E7'));
		sheet8.getCell('G1').cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
		sheet8.getCell('G1').cellFormat().font().Name = "Times New Roman";
		sheet8.rows(0).cellFormat().font().bold(true);

		sheet8.getCell('P1').cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#B4C7E7'));
		
		for (i = 0; i <=15; i++) {
			sheet8.rows(1).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#0070C0'));
			sheet8.rows(1).cells(i).cellFormat().font().height(14 * 14);
			sheet8.rows(2).cells(i).cellFormat().font().height(14 * 14);
			sheet8.rows(3).cells(i).cellFormat().font().height(14 * 15);

			sheet8.rows(1).cells(i).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet8.rows(1).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet8.rows(1).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet8.rows(1).cells(i).cellFormat().rightBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet8.rows(1).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet8.rows(1).cells(i).cellFormat().font().colorInfo(
					new $.ig.excel.WorkbookColorInfo(
							$.ig.excel.WorkbookThemeColorType.light1));
			sheet8.rows(1).height(300);
			sheet8.rows(1).cellFormat().font().bold(true);
		}

		for (i = 0; i <= 15; i++) {
			sheet8.rows(2).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet8.rows(2).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet8.rows(2).cells(i).cellFormat().rightBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet8.rows(2).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet8.rows(2).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
		}

		for (i = 0; i <= 5; i++) {
			sheet8.rows(3).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#F8CBAD'));
			sheet8.rows(3).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet8.rows(3).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet8.rows(3).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet8.rows(3).height(600);
		}
		
		for (i = 6; i <= 15; i++) {
			sheet8.rows(3).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#B4C7E7'));
			sheet8.rows(3).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet8.rows(3).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet8.rows(3).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet8.rows(3).height(600);
		}

		sheet8.getCell('A2').value('No. of Recipients');
		sheet8.getCell('C2').value('No. of Notes/Vouchers');
		sheet8.getCell('E2').value('No. of Invoices');
		sheet8.getCell('K2').value('Total Note/Refund Voucher Value');
		sheet8.getCell('N2').value('Total Taxable Value');
		sheet8.getCell('O2').value('Total Cess');
		
		sheet8.getCell('A3').applyFormula('=SUMPRODUCT((A5:A100 <> "")/COUNTIF(A5:A100,A5:A100 & ""))');
		sheet8.getCell('C3').applyFormula('=SUM(C5:C100)');
		sheet8.getCell('E3').applyFormula('=SUM(E5:E100)');
		sheet8.getCell('K3').applyFormula('=SUM(K5:K100)');
		sheet8.getCell('N3').applyFormula('=SUM(N5:N100)');
		sheet8.getCell('O3').applyFormula('=SUM(O5:O100)');
		
		sheet8.getCell('A4').value('GSTIN/UIN of Recipient');
		sheet8.getCell('B4').value('Receiver Name');
		sheet8.getCell('C4').value('Original Note/Refund Voucher Number');
		sheet8.getCell('D4').value('Original Note/Refund Voucher date');
		sheet8.getCell('E4').value('Original Invoice/Advance Receipt Number');
		sheet8.getCell('F4').value('Original Invoice/Advance Receipt date');
		sheet8.getCell('G4').value('Revised Note/Refund Voucher Number');
		sheet8.getCell('H4').value('Revised Note/Refund Voucher date');
		sheet8.getCell('I4').value('Document Type');
	//	sheet8.getCell('J4').value('Reason For Issuing document');
		sheet8.getCell('J4').value('Supply Type');
		sheet8.getCell('K4').value('Note/Refund Voucher Value');
		sheet8.getCell('L4').value('Applicable % of Tax Rate');
		sheet8.getCell('M4').value('Rate');
		sheet8.getCell('N4').value('Taxable Value');
		sheet8.getCell('O4').value('Cess Amount');
		sheet8.getCell('P4').value('Pre GST');

	
//   CDNURA
		
		sheet9.columns(0).setWidth(150,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet9.columns(1).setWidth(250,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet9.columns(2).setWidth(240,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet9.columns(3).setWidth(250,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet9.columns(4).setWidth(240,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet9.columns(5).setWidth(250,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet9.columns(6).setWidth(240,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet9.columns(7).setWidth(150,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet9.columns(8).setWidth(140,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet9.columns(9).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		
		sheet9.columns(10).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet9.columns(11).setWidth(70,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet9.columns(12).setWidth(150,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet9.columns(13).setWidth(120,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		
		sheet9.columns(14).setWidth(70,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet9.columns(15).setWidth(70,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		
		sheet9.getCell('A1').value('Summary For CDNURA');
		sheet9.getCell('A1').cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		sheet9.getCell('A1').cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#0070C0'));
		sheet9.getCell('A1').cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
		sheet9.getCell('A1').cellFormat().font().colorInfo(
				new $.ig.excel.WorkbookColorInfo(
						$.ig.excel.WorkbookThemeColorType.light1));
		sheet9.getCell('A1').cellFormat().font().Name = "Times New Roman";
		sheet9.rows(0).cellFormat().font().bold(true);
		sheet9.rows(0).cellFormat().font().height(14 * 14);
		
		var mergedRegion1 = sheet9.mergedCellsRegions().add(0, 1, 0, 4);
		mergedRegion1.value('Original details');

		//sheet1.getCell('B1').value('Original details');
		sheet9.getCell('B1').cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		sheet9.getCell('B1').cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#F8CBAD'));
		sheet9.getCell('B1').cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
		sheet9.getCell('B1').cellFormat().font().Name = "Times New Roman";
		sheet9.rows(0).cellFormat().font().bold(true);

		var mergedRegion1 = sheet9.mergedCellsRegions().add(0, 5, 0, 14);
		mergedRegion1.value('Revised Details');
		sheet9.getCell('F1').cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		sheet9.getCell('F1').cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#B4C7E7'));
		sheet9.getCell('F1').cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
		sheet9.getCell('F1').cellFormat().font().Name = "Times New Roman";
		sheet9.rows(0).cellFormat().font().bold(true);

		sheet9.getCell('O1').cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#B4C7E7'));
		
		for (i = 0; i <=14; i++) {
			sheet9.rows(1).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#0070C0'));
			sheet9.rows(1).cells(i).cellFormat().font().height(14 * 14);
			sheet9.rows(2).cells(i).cellFormat().font().height(14 * 14);
			sheet9.rows(3).cells(i).cellFormat().font().height(14 * 15);

			sheet9.rows(1).cells(i).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet9.rows(1).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet9.rows(1).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet9.rows(1).cells(i).cellFormat().rightBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet9.rows(1).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet9.rows(1).cells(i).cellFormat().font().colorInfo(
					new $.ig.excel.WorkbookColorInfo(
							$.ig.excel.WorkbookThemeColorType.light1));
			sheet9.rows(1).height(300);
			sheet9.rows(1).cellFormat().font().bold(true);
		}

		for (i = 0; i <= 14; i++) {
			sheet9.rows(2).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet9.rows(2).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet9.rows(2).cells(i).cellFormat().rightBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet9.rows(2).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet9.rows(2).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
		}

		for (i = 0; i <= 4; i++) {
			sheet9.rows(3).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#F8CBAD'));
			sheet9.rows(3).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet9.rows(3).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet9.rows(3).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet9.rows(3).height(600);
		}

		for (i = 5; i <= 14; i++) {
			sheet9.rows(3).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#B4C7E7'));
			sheet9.rows(3).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet9.rows(3).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet9.rows(3).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet9.rows(3).height(600);
		}

		sheet9.getCell('B2').value('No. of Notes/Vouchers');
		sheet9.getCell('D2').value('No. of Invoices');
		sheet9.getCell('J2').value('Total Note Value');
		sheet9.getCell('M2').value('Total Taxable Value');
		sheet9.getCell('N2').value('Total Cess');
		
		sheet9.getCell('B3').applyFormula('=SUMPRODUCT((B5:A100 <> "")/COUNTIF(B5:A100,B5:A100 & ""))');
		sheet9.getCell('D3').applyFormula('=SUM(D5:D100)');
		sheet9.getCell('J3').applyFormula('=SUM(J5:J100)');
		sheet9.getCell('M3').applyFormula('=SUM(M5:M100)');
		sheet9.getCell('N3').applyFormula('=SUM(N5:N100)');
		
		sheet9.getCell('A4').value('UR Type');
		sheet9.getCell('B4').value('Original Note/Refund Voucher Number');
		sheet9.getCell('C4').value('Original Note/Refund Voucher date');
		sheet9.getCell('D4').value('Original Invoice/Advance Receipt Number');
		sheet9.getCell('E4').value('Original Invoice/Advance Receipt date');
		sheet9.getCell('F4').value('Revised Note/Refund Voucher Number');
		sheet9.getCell('G4').value('Revised Note/Refund Voucher date');
		sheet9.getCell('H4').value('Document Type');
//		sheet9.getCell('I4').value('Reason For Issuing document');
		sheet9.getCell('I4').value('Supply Type');
		sheet9.getCell('J4').value('Note/Refund Voucher Value');
		sheet9.getCell('K4').value('Applicable % of Tax Rate');
		sheet9.getCell('L4').value('Rate');
		sheet9.getCell('M4').value('Taxable Value');
		sheet9.getCell('N4').value('Cess Amount');
		sheet9.getCell('O4').value('Pre GST');

//  B2BA 	
		
		sheet1.columns(0).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet1.columns(1).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet1.columns(2).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet1.columns(3).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet1.columns(4).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet1.columns(5).setWidth(150,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet1.columns(6).setWidth(150,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet1.columns(7).setWidth(150,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet1.columns(8).setWidth(120,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet1.columns(9).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet1.columns(10).setWidth(150,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet1.columns(11).setWidth(150,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet1.columns(12).setWidth(70,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet1.columns(13).setWidth(150,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet1.columns(14).setWidth(120,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		
		sheet1.getCell('A1').value('Summary For B2BA');
		sheet1.getCell('A1').cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		sheet1.getCell('A1').cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#0070C0'));
		sheet1.getCell('A1').cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
		sheet1.getCell('A1').cellFormat().font().colorInfo(
				new $.ig.excel.WorkbookColorInfo(
						$.ig.excel.WorkbookThemeColorType.light1));
		sheet1.getCell('A1').cellFormat().font().Name = "Times New Roman";
		sheet1.rows(0).cellFormat().font().bold(true);
		sheet1.rows(0).cellFormat().font().height(14 * 14);

		var mergedRegion0 = sheet1.mergedCellsRegions().add(0, 1, 0, 3);
		mergedRegion0.value('Original details');

		sheet1.getCell('B1').cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		sheet1.getCell('B1').cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#F8CBAD'));
		sheet1.getCell('B1').cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
		sheet1.getCell('B1').cellFormat().font().Name = "Times New Roman";
		sheet1.rows(0).cellFormat().font().bold(true);

		var mergedRegion1 = sheet1.mergedCellsRegions().add(0, 4, 0, 13);
		mergedRegion1.value('Revised Details');
		sheet1.getCell('E1').cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		sheet1.getCell('E1').cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#B4C7E7'));
		sheet1.getCell('E1').cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
		sheet1.getCell('E1').cellFormat().font().Name = "Times New Roman";
		sheet1.rows(0).cellFormat().font().bold(true);

		sheet1.getCell('O1').cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#B4C7E7'));
		
		for (i = 0; i <=14; i++) {
			sheet1.rows(1).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#0070C0'));
			sheet1.rows(1).cells(i).cellFormat().font().height(14 * 14);
			sheet1.rows(2).cells(i).cellFormat().font().height(14 * 14);
			sheet1.rows(3).cells(i).cellFormat().font().height(14 * 15);

			sheet1.rows(1).cells(i).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet1.rows(1).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet1.rows(1).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet1.rows(1).cells(i).cellFormat().rightBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet1.rows(1).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet1.rows(1).cells(i).cellFormat().font().colorInfo(
					new $.ig.excel.WorkbookColorInfo(
							$.ig.excel.WorkbookThemeColorType.light1));
			sheet1.rows(1).height(300);
			sheet1.rows(1).cellFormat().font().bold(true);

		}

		for (i = 0; i <= 14; i++) {
			sheet1.rows(2).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet1.rows(2).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet1.rows(2).cells(i).cellFormat().rightBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet1.rows(2).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet1.rows(2).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
		}

		for (i = 0; i <= 3; i++) {
			sheet1.rows(3).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#F8CBAD'));
			sheet1.rows(3).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet1.rows(3).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet1.rows(3).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet1.rows(3).height(600);
		}
		
		for (i = 4; i <= 14; i++) {
			sheet1.rows(3).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#B4C7E7'));
			sheet1.rows(3).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet1.rows(3).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet1.rows(3).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet1.rows(3).height(600);
		}
		
		sheet1.getCell('A2').value('No. of Recipients');
		sheet1.getCell('C2').value('No. of Invoices');
		sheet1.getCell('G2').value('Total Invoice Value');
		sheet1.getCell('N2').value('Total Taxable Value');
		sheet1.getCell('O2').value('Total Cess');

		sheet1
		.getCell('A3')
		.applyFormula(
				'=SUMPRODUCT((A5:A100 <> "")/COUNTIF(A5:A100,A5:A100 & ""))');
		sheet1.getCell('A3').cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		sheet1.getCell('C3').applyFormula('=SUM(C5:C100)');
		sheet1.getCell('G3').applyFormula('=SUM(G5:G100)');
		sheet1.getCell('M3').applyFormula('=SUM(M5:M100)');
		sheet1.getCell('N3').applyFormula('=SUM(N5:N100)');
		sheet1.getCell('O3').applyFormula('=SUM(O5:O100)');

		sheet1.getCell('A4').value('GSTIN/UIN of Recipient');
		sheet1.getCell('B4').value('Receiver Name');
		sheet1.getCell('C4').value('Original Invoice Number');
		sheet1.getCell('D4').value('Original Invoice date');
		sheet1.getCell('E4').value('Revised Invoice Number');
		sheet1.getCell('F4').value('Revised Invoice date');
		sheet1.getCell('G4').value('Invoice Value');
		sheet1.getCell('H4').value('Place Of Supply');
		sheet1.getCell('I4').value('Reverse Charge');
		sheet1.getCell('J4').value('Applicable % of Tax Rate');
		sheet1.getCell('K4').value('Invoice Type');
		sheet1.getCell('L4').value('E-Commerce GSTIN');
		sheet1.getCell('M4').value('Rate');
		sheet1.getCell('N4').value('Taxable Value');
		sheet1.getCell('O4').value('Cess Amount');

//  EXP
		
		sheet12.columns(0).setWidth(150,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet12.columns(1).setWidth(150,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet12.columns(2).setWidth(140,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet12.columns(3).setWidth(150,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet12.columns(4).setWidth(130,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet12.columns(5).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet12.columns(6).setWidth(160,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet12.columns(7).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet12.columns(8).setWidth(70,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet12.columns(9).setWidth(120,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet12.columns(10).setWidth(150,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		
		sheet12.getCell('A1').value('Summary For EXP(6)');
		sheet12.getCell('A1').cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		sheet12.getCell('A1').cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#0070C0'));
		sheet12.getCell('A1').cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
		sheet12.getCell('A1').cellFormat().font().colorInfo(
				new $.ig.excel.WorkbookColorInfo(
						$.ig.excel.WorkbookThemeColorType.light1));
		sheet12.getCell('A1').cellFormat().font().Name = "Times New Roman";
		sheet12.rows(0).cellFormat().font().bold(true);
		sheet12.rows(0).cellFormat().font().height(14 * 14);
		
		for (i = 0; i <=10; i++) {
			sheet12.rows(1).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#0070C0'));
			sheet12.rows(1).cells(i).cellFormat().font().height(14 * 14);
			sheet12.rows(2).cells(i).cellFormat().font().height(14 * 14);
			sheet12.rows(3).cells(i).cellFormat().font().height(14 * 15);

			sheet12.rows(1).cells(i).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet12.rows(1).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet12.rows(1).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet12.rows(1).cells(i).cellFormat().rightBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet12.rows(1).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet12.rows(1).cells(i).cellFormat().font().colorInfo(
					new $.ig.excel.WorkbookColorInfo(
							$.ig.excel.WorkbookThemeColorType.light1));
			sheet12.rows(1).height(300);
			sheet12.rows(1).cellFormat().font().bold(true);
		}

		for (i = 0; i <= 10; i++) {
			sheet12.rows(2).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet12.rows(2).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet12.rows(2).cells(i).cellFormat().rightBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet12.rows(2).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet12.rows(2).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
		}

		for (i = 0; i <= 10; i++) {
			sheet12.rows(3).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#F8CBAD'));
			sheet12.rows(3).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet12.rows(3).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet12.rows(3).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet12.rows(3).height(600);
		}
		
		sheet12.getCell('B2').value('No. of Invoices');
		sheet12.getCell('D2').value('Total Invoice Value');
		sheet12.getCell('F2').value('No. of Shipping Bill');
		sheet12.getCell('J2').value('Total Taxable Value');
		
		sheet12.getCell('B3').applyFormula('=SUMPRODUCT((B5:A100 <> "")/COUNTIF(B5:A100,B5:A100 & ""))');
		sheet12.getCell('D3').applyFormula('=SUM(D5:D100)');
		sheet12.getCell('F3').applyFormula('=SUM(F5:F100)');
		sheet12.getCell('I3').applyFormula('=SUM(I5:I100)');
		sheet12.getCell('J3').applyFormula('=SUM(J5:J100)');

		sheet12.getCell('A4').value('Export Type');
		sheet12.getCell('B4').value('Invoice Number');
		sheet12.getCell('C4').value('Invoice date');
		sheet12.getCell('D4').value('Invoice Value');
		sheet12.getCell('E4').value('Port Code');
		sheet12.getCell('F4').value('Shipping Bill Number');
		sheet12.getCell('G4').value('Shipping Bill Date');
		sheet12.getCell('H4').value('Applicable % of Tax Rate');
		sheet12.getCell('I4').value('Rate');
		sheet12.getCell('J4').value('Taxable Value');
		sheet12.getCell('K4').value('Cess Amount');

//  EXPA
		
		sheet13.columns(0).setWidth(150,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet13.columns(1).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet13.columns(2).setWidth(160,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet13.columns(3).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet13.columns(4).setWidth(160,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet13.columns(5).setWidth(150,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet13.columns(6).setWidth(120,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet13.columns(7).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet13.columns(8).setWidth(160,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet13.columns(9).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet13.columns(10).setWidth(70,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet13.columns(11).setWidth(140,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet13.columns(12).setWidth(150,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		
		sheet13.getCell('A1').value('Summary For EXPA');
		sheet13.getCell('A1').cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		sheet13.getCell('A1').cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#0070C0'));
		sheet13.getCell('A1').cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
		sheet13.getCell('A1').cellFormat().font().colorInfo(
				new $.ig.excel.WorkbookColorInfo(
						$.ig.excel.WorkbookThemeColorType.light1));
		sheet13.getCell('A1').cellFormat().font().Name = "Times New Roman";
		sheet13.rows(0).cellFormat().font().bold(true);
		sheet13.rows(0).cellFormat().font().height(14 * 14);
		
		var mergedRegion1 = sheet13.mergedCellsRegions().add(0, 1, 0, 2);
		mergedRegion1.value('Original details');

		//sheet1.getCell('B1').value('Original details');
		sheet13.getCell('B1').cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		sheet13.getCell('B1').cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#F8CBAD'));
		sheet13.getCell('B1').cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
		sheet13.getCell('B1').cellFormat().font().Name = "Times New Roman";
		sheet13.rows(0).cellFormat().font().bold(true);

		var mergedRegion1 = sheet13.mergedCellsRegions().add(0, 3, 0, 12);
		mergedRegion1.value('Revised Details');

		sheet13.getCell('D1').cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		sheet13.getCell('D1').cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#B4C7E7'));
		sheet13.getCell('D1').cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
		sheet13.getCell('D1').cellFormat().font().Name = "Times New Roman";
		sheet13.rows(0).cellFormat().font().bold(true);
		sheet13.getCell('K1').cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#B4C7E7'));
		
		for (i = 0; i <=12; i++) {
			sheet13.rows(1).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#0070C0'));
			sheet13.rows(1).cells(i).cellFormat().font().height(14 * 14);
			sheet13.rows(2).cells(i).cellFormat().font().height(14 * 14);
			sheet13.rows(3).cells(i).cellFormat().font().height(14 * 15);

			sheet13.rows(1).cells(i).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet13.rows(1).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet13.rows(1).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet13.rows(1).cells(i).cellFormat().rightBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet13.rows(1).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet13.rows(1).cells(i).cellFormat().font().colorInfo(
					new $.ig.excel.WorkbookColorInfo(
							$.ig.excel.WorkbookThemeColorType.light1));
			sheet13.rows(1).height(300);
			sheet13.rows(1).cellFormat().font().bold(true);
		}

		for (i = 0; i <= 12; i++) {
			sheet13.rows(2).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet13.rows(2).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet13.rows(2).cells(i).cellFormat().rightBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet13.rows(2).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet13.rows(2).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
		}

		for (i = 0; i <= 2; i++) {
			sheet13.rows(3).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#F8CBAD'));
			sheet13.rows(3).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet13.rows(3).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet13.rows(3).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet13.rows(3).height(600);
		}
		
		for (i = 3; i <= 12; i++) {
			sheet13.rows(3).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#B4C7E7'));
			sheet13.rows(3).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet13.rows(3).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet13.rows(3).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet13.rows(3).height(600);
		}

		sheet13.getCell('B2').value('No. of Invoices');
		sheet13.getCell('F2').value('Total Invoice Value');
		sheet13.getCell('H2').value('No. of Shipping Bill');
		sheet13.getCell('L2').value('Total Taxable Value');
		sheet13.getCell('M2').value('Total Cess');

		
		sheet13.getCell('B3').applyFormula('=SUMPRODUCT((B5:A100 <> "")/COUNTIF(B5:A100,B5:A100 & ""))');
		sheet13.getCell('F3').applyFormula('=SUM(F5:F100)');
		sheet13.getCell('H3').applyFormula('=SUM(H5:H100)');
		sheet13.getCell('K3').applyFormula('=SUM(K5:K100)');
		sheet13.getCell('L3').applyFormula('=SUM(L5:L100)');
		sheet13.getCell('M3').applyFormula('=SUM(M5:M100)');

		
		sheet13.getCell('A4').value('Export Type');
		sheet13.getCell('B4').value('Original Invoice Number');
		sheet13.getCell('C4').value('Original Invoice date');
		sheet13.getCell('D4').value('Revised Invoice Number');
		sheet13.getCell('E4').value('Revised Invoice date');
		sheet13.getCell('F4').value('Invoice Value');
		sheet13.getCell('G4').value('Port Code');
		sheet13.getCell('H4').value('Shipping Bill Number');
		sheet13.getCell('I4').value('Shipping Bill Date');
		sheet13.getCell('J4').value('Applicable % of Tax Rate');
		sheet13.getCell('K4').value('Rate');
		sheet13.getCell('L4').value('Taxable Value');
		sheet13.getCell('M4').value('Cess Amount');
		
//  at
		
		sheet14.columns(0).setWidth(270,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet14.columns(1).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet14.columns(2).setWidth(70,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet14.columns(3).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet14.columns(4).setWidth(140,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		
		sheet14.getCell('A1').value('Summary For Advance Received (11B) ');
		sheet14.getCell('A1').cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		sheet14.getCell('A1').cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#0070C0'));
		sheet14.getCell('A1').cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
		sheet14.getCell('A1').cellFormat().font().colorInfo(
				new $.ig.excel.WorkbookColorInfo(
						$.ig.excel.WorkbookThemeColorType.light1));
		sheet14.getCell('A1').cellFormat().font().Name = "Times New Roman";
		sheet14.rows(0).cellFormat().font().bold(true);
		sheet14.rows(0).height(600);
		sheet14.rows(0).cellFormat().font().height(14 * 14);

		for (i = 0; i <=4; i++) {
			sheet14.rows(1).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#0070C0'));
			sheet14.rows(1).cells(i).cellFormat().font().height(14 * 14);
			sheet14.rows(2).cells(i).cellFormat().font().height(14 * 14);
			sheet14.rows(3).cells(i).cellFormat().font().height(14 * 15);

			sheet14.rows(1).cells(i).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet14.rows(1).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet14.rows(1).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet14.rows(1).cells(i).cellFormat().rightBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet14.rows(1).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet14.rows(1).cells(i).cellFormat().font().colorInfo(
					new $.ig.excel.WorkbookColorInfo(
							$.ig.excel.WorkbookThemeColorType.light1));
			sheet14.rows(1).height(300);
			sheet14.rows(1).cellFormat().font().bold(true);
		}

		for (i = 0; i <= 4; i++) {
			sheet14.rows(2).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet14.rows(2).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet14.rows(2).cells(i).cellFormat().rightBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet14.rows(2).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet14.rows(2).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
		}

		for (i = 0; i <= 4; i++) {
			sheet14.rows(3).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#F8CBAD'));
			sheet14.rows(3).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet14.rows(3).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet14.rows(3).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet14.rows(3).height(600);
		}
		
		sheet14.getCell('D2').value('Total Advance Received');
		sheet14.getCell('E2').value('Total Cess');
		
		sheet14.getCell('D3').applyFormula('=SUM(D5:D100)');
		sheet14.getCell('E3').applyFormula('=SUM(E5:E100)');
		
		sheet14.getCell('A4').value('Place Of Supply');
		sheet14.getCell('B4').value('Applicable % of Tax Rate');
		sheet14.getCell('C4').value('Rate');
		sheet14.getCell('D4').value('Gross Advance Received');
		sheet14.getCell('E4').value('Cess Amount');
		
//  ata		
		
		sheet15.columns(0).setWidth(200,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet15.columns(1).setWidth(150,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet15.columns(2).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet15.columns(3).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet15.columns(4).setWidth(70,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet15.columns(5).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet15.columns(6).setWidth(140,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		
		sheet15.getCell('A1').value('Summary For Amended Tax Liability(Advance Received)');
		sheet15.getCell('A1').cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		sheet15.getCell('A1').cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#0070C0'));
		sheet15.getCell('A1').cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
		sheet15.getCell('A1').cellFormat().font().colorInfo(
				new $.ig.excel.WorkbookColorInfo(
						$.ig.excel.WorkbookThemeColorType.light1));
		sheet15.getCell('A1').cellFormat().font().Name = "Times New Roman";
		sheet15.rows(0).cellFormat().font().bold(true);
		sheet15.rows(0).height(600);
		sheet15.rows(0).cellFormat().font().height(14 * 14);
		sheet15.rows(1).cellFormat().font().height(14 * 14);
		sheet15.rows(2).cellFormat().font().height(14 * 14);
		sheet15.rows(3).cellFormat().font().height(14 * 15);

		var mergedb7 = sheet15.mergedCellsRegions().add(0, 1, 0, 2);
		mergedb7.value('Original details');

		sheet15.getCell('B1').cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
			sheet15.getCell('B1').cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#F8CBAD'));
		sheet15.getCell('B1').cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
		sheet15.getCell('B1').cellFormat().font().Name = "Times New Roman";
		sheet15.rows(0).cellFormat().font().bold(true);
		
		var mergedb8 = sheet15.mergedCellsRegions().add(0, 3, 0, 5);
		mergedb8.value('Revised details');
		
		for(i=3;i<=5;i++){
		sheet15.rows(0).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#B4C7E7'));
		sheet15.rows(0).cells(i).cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		sheet15.rows(0).cells(i).cellFormat().bottomBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
		sheet15.rows(0).cells(i).cellFormat().leftBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
		sheet15.rows(0).cells(i).cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
		sheet15.rows(0).cells(i).cellFormat().font().Name = "Times New Roman";	
		
	}
		sheet15.rows(0).cellFormat().font().bold(true);
		sheet15.getCell('G1').cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#B4C7E7'));
		
		for (i = 0; i <= 6; i++) {
			sheet15.rows(1).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#0070C0'));
			sheet15.rows(1).cells(i).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet15.rows(1).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet15.rows(1).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet15.rows(1).cells(i).cellFormat().rightBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet15.rows(1).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet15.rows(1).cells(i).cellFormat().font().colorInfo(
					new $.ig.excel.WorkbookColorInfo(
							$.ig.excel.WorkbookThemeColorType.light1));
			sheet15.rows(1).cellFormat().font().bold(true);
			sheet15.rows(1).height(300);
		}
		for (i = 0; i <= 6; i++) {
			sheet15.rows(2).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet15.rows(2).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet15.rows(2).cells(i).cellFormat().rightBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet15.rows(2).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet15.rows(2).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
		}
		for (i = 0; i <= 3; i++) {
			sheet15.rows(3).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#F8CBAD'));
			sheet15.rows(3).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet15.rows(3).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet15.rows(3).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet15.rows(3).height(600);
		}
		for (i = 3; i <= 6; i++) {
			sheet15.rows(3).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#B4C7E7'));
			sheet15.rows(3).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet15.rows(3).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet15.rows(3).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet15.rows(3).height(600);

		}
		
		sheet15.getCell('F2').value('Total Advance Received');
		sheet15.getCell('G2').value('Total Cess');
		
		sheet15.getCell('F3').applyFormula('=SUM(F5:F100)');
		sheet15.getCell('G3').applyFormula('=SUM(G5:G100)');
		
		sheet15.getCell('A4').value('Financial Year');
		sheet15.getCell('B4').value('Original Month');
		sheet15.getCell('C4').value('Original Place Of Supply');
		sheet15.getCell('D4').value('Applicable % of Tax Rate');
		sheet15.getCell('E4').value('Rate');
		sheet15.getCell('F4').value('Gross Advance Received');
		sheet15.getCell('G4').value('Cess Amount');

//	atadj
		
		sheet16.columns(0).setWidth(200,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet16.columns(1).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet16.columns(2).setWidth(70,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet16.columns(3).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet16.columns(4).setWidth(140,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		
		sheet16.getCell('A1').value('Summary For Advance Adjusted (11B)');
		sheet16.getCell('A1').cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		sheet16.getCell('A1').cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#0070C0'));
		sheet16.getCell('A1').cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
		sheet16.getCell('A1').cellFormat().font().colorInfo(
				new $.ig.excel.WorkbookColorInfo(
						$.ig.excel.WorkbookThemeColorType.light1));
		sheet16.getCell('A1').cellFormat().font().Name = "Times New Roman";
		sheet16.rows(0).cellFormat().font().bold(true);
		sheet16.rows(0).height(600);
		
		sheet16.rows(0).cellFormat().font().height(14 * 14);
		sheet16.rows(1).cellFormat().font().height(14 * 14);
		sheet16.rows(2).cellFormat().font().height(14 * 14);
		sheet16.rows(3).cellFormat().font().height(14 * 15);

		for (i = 0; i <= 4; i++) {
			sheet16.rows(1).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#0070C0'));
			sheet16.rows(1).cells(i).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet16.rows(1).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet16.rows(1).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet16.rows(1).cells(i).cellFormat().rightBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet16.rows(1).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet16.rows(1).cells(i).cellFormat().font().colorInfo(
					new $.ig.excel.WorkbookColorInfo(
							$.ig.excel.WorkbookThemeColorType.light1));
			sheet16.rows(1).cellFormat().font().bold(true);
			sheet16.rows(1).height(300);
		}
		for (i = 0; i <= 4; i++) {
			sheet16.rows(2).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet16.rows(2).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet16.rows(2).cells(i).cellFormat().rightBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet16.rows(2).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet16.rows(2).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
		}
		for (i = 0; i <= 4; i++) {
			sheet16.rows(3).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#F8CBAD'));
			sheet16.rows(3).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet16.rows(3).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet16.rows(3).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet16.rows(3).height(600);

		}

		sheet16.getCell('D2').value('Total Advance Adjusted');
		sheet16.getCell('E2').value('Total Cess');

		sheet16.getCell('D3').applyFormula('=SUM(D5:D100)');
		sheet16.getCell('E3').applyFormula('=SUM(E5:E100)');
		
		sheet16.getCell('A4').value('Place Of Supply');
		sheet16.getCell('B4').value('Applicable % of Tax Rate');
		sheet16.getCell('C4').value('Rate');
		sheet16.getCell('D4').value('Gross Advance Adjusted');
		sheet16.getCell('E4').value('Cess Amount');

//  atadja
		
		sheet17.columns(0).setWidth(250,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet17.columns(1).setWidth(150,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet17.columns(2).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet17.columns(3).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet17.columns(4).setWidth(70,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet17.columns(5).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet17.columns(6).setWidth(140,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		
		sheet17.getCell('A1').value('Summary For Amendement Of Adjustment Advances');
		sheet17.getCell('A1').cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		sheet17.getCell('A1').cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#0070C0'));
		sheet17.getCell('A1').cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
		sheet17.getCell('A1').cellFormat().font().colorInfo(
				new $.ig.excel.WorkbookColorInfo(
						$.ig.excel.WorkbookThemeColorType.light1));
		sheet17.getCell('A1').cellFormat().font().Name = "Times New Roman";
		sheet17.rows(0).cellFormat().font().bold(true);
		sheet17.rows(0).height(600);
		sheet17.rows(0).cellFormat().font().height(14 * 14);
		sheet17.rows(1).cellFormat().font().height(14 * 14);
		sheet17.rows(2).cellFormat().font().height(14 * 14);
		sheet17.rows(3).cellFormat().font().height(14 * 15);
		
		var mergedb7 = sheet17.mergedCellsRegions().add(0, 1, 0, 2);
		mergedb7.value('Original details');
		sheet17.getCell('B1').cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
			sheet17.getCell('B1').cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#F8CBAD'));
		sheet17.getCell('B1').cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
		sheet17.getCell('B1').cellFormat().font().Name = "Times New Roman";
		sheet17.rows(0).cellFormat().font().bold(true);
		
		var mergedb8 = sheet17.mergedCellsRegions().add(0, 3, 0, 5);
		mergedb8.value('Revised details');
		sheet17.getCell('D1').cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		sheet17.getCell('D1').cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#B4C7E7'));
		sheet17.getCell('D1').cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
		sheet17.getCell('D1').cellFormat().font().Name = "Times New Roman";
		sheet17.rows(0).cellFormat().font().bold(true);
		
		sheet17.getCell('G1').cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#B4C7E7'));
		
		for (i = 0; i <= 6; i++) {
			sheet17.rows(1).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#0070C0'));
			sheet17.rows(1).cells(i).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet17.rows(1).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet17.rows(1).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet17.rows(1).cells(i).cellFormat().rightBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet17.rows(1).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet17.rows(1).cells(i).cellFormat().font().colorInfo(
					new $.ig.excel.WorkbookColorInfo(
							$.ig.excel.WorkbookThemeColorType.light1));
			sheet17.rows(1).cellFormat().font().bold(true);
			sheet17.rows(1).height(300);
		}
		for (i = 0; i <= 6; i++) {
			sheet17.rows(2).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet17.rows(2).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet17.rows(2).cells(i).cellFormat().rightBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet17.rows(2).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet17.rows(2).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
		}
		for (i = 0; i <= 3; i++) {
			sheet17.rows(3).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#F8CBAD'));
			sheet17.rows(3).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet17.rows(3).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet17.rows(3).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet17.rows(3).height(600);

		}
		for (i = 3; i <= 6; i++) {
			sheet17.rows(3).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#B4C7E7'));
			sheet17.rows(3).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
        	sheet17.rows(3).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet17.rows(3).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet17.rows(3).height(600);

		}

		sheet17.getCell('F2').value('Total Advance Adjusted');
		sheet17.getCell('G2').value('Total Cess');
		
		sheet17.getCell('F3').applyFormula('=SUM(F5:F100)');
		sheet17.getCell('G3').applyFormula('=SUM(G5:G100)');
		
		sheet17.getCell('A4').value('Financial Year');
		sheet17.getCell('B4').value('Original Month');
		sheet17.getCell('C4').value('Original Place Of Supply');
		sheet17.getCell('D4').value('Applicable % of Tax Rate');
		sheet17.getCell('E4').value('Rate');
		sheet17.getCell('F4').value('Gross Advance Adjusted');
		sheet17.getCell('G4').value('Cess Amount');

//  exemp	
		
		sheet18.columns(0).setWidth(250,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet18.columns(1).setWidth(200,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet18.columns(2).setWidth(300,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet18.columns(3).setWidth(200,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		
		sheet18.getCell('A1').value('Summary For Nil rated, exempted and non GST outward supplies (8)');
		sheet18.getCell('A1').cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		sheet18.getCell('A1').cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#0070C0'));
		sheet18.getCell('A1').cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
		sheet18.getCell('A1').cellFormat().font().colorInfo(
				new $.ig.excel.WorkbookColorInfo(
						$.ig.excel.WorkbookThemeColorType.light1));
		sheet18.getCell('A1').cellFormat().font().Name = "Times New Roman";
		sheet18.rows(0).cellFormat().font().bold(true);
		sheet18.rows(0).height(600);
		
		sheet18.rows(0).cellFormat().font().height(14 * 14);
		sheet18.rows(1).cellFormat().font().height(14 * 14);
		sheet18.rows(2).cellFormat().font().height(14 * 14);
		sheet18.rows(3).cellFormat().font().height(14 * 15);

		for (i = 0; i <= 3; i++) {
			sheet18.rows(1).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#0070C0'));
			sheet18.rows(1).cells(i).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet18.rows(1).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet18.rows(1).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet18.rows(1).cells(i).cellFormat().rightBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet18.rows(1).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet18.rows(1).cells(i).cellFormat().font().colorInfo(
					new $.ig.excel.WorkbookColorInfo(
							$.ig.excel.WorkbookThemeColorType.light1));
			sheet18.rows(1).height(300);
			sheet18.rows(1).cellFormat().font().bold(true);

		}
		for (i = 0; i <= 3; i++) {
			sheet18.rows(2).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet18.rows(2).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet18.rows(2).cells(i).cellFormat().rightBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet18.rows(2).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet18.rows(2).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
		}
		for (i = 0; i <= 3; i++) {
			sheet18.rows(3).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#F8CBAD'));
			sheet18.rows(3).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet18.rows(3).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet18.rows(3).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet18.rows(3).height(600);

		}
		sheet18.getCell('B2').value('Total Nil Rated Supplies');
		sheet18.getCell('C2').value('Total Exempted Supplies');
		sheet18.getCell('D2').value('Total Non-GST Supplies');
		sheet18.getCell('B3').applyFormula('=SUM(B5:B100)');
		sheet18.getCell('C3').applyFormula('=SUM(C5:C100)');
		sheet18.getCell('D3').applyFormula('=SUM(D5:D100)');
		
		sheet18.getCell('A4').value('Description');
		sheet18.getCell('B4').value('Nil Rated Supplies');
		sheet18.getCell('C4').value('Exempted(other than nil rated/non GST supply)');
		sheet18.getCell('D4').value('Non-GST Supplies');

//  hsn
		
		sheet10.columns(0).setWidth(180,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet10.columns(1).setWidth(150,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet10.columns(2).setWidth(100,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet10.columns(3).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet10.columns(4).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet10.columns(5).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet10.columns(6).setWidth(150,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet10.columns(7).setWidth(180,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet10.columns(8).setWidth(180,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet10.columns(9).setWidth(150,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet10.columns(10).setWidth(100,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet10.getCell('A1').value('Summary For HSN(12)');
		sheet10.getCell('A1').cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		sheet10.getCell('A1').cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#0070C0'));
		sheet10.getCell('A1').cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
		sheet10.getCell('A1').cellFormat().font().colorInfo(
				new $.ig.excel.WorkbookColorInfo(
						$.ig.excel.WorkbookThemeColorType.light1));
		sheet10.getCell('A1').cellFormat().font().Name = "Times New Roman";
		sheet10.rows(0).cellFormat().font().bold(true);
		
		sheet10.rows(0).cellFormat().font().height(14 * 14);
		sheet10.rows(1).cellFormat().font().height(14 * 14);
		sheet10.rows(2).cellFormat().font().height(14 * 14);
		sheet10.rows(3).cellFormat().font().height(14 * 15);

		for (i = 0; i <= 9; i++) {
			sheet10.rows(1).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#0070C0'));
			sheet10.rows(1).cells(i).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet10.rows(1).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet10.rows(1).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet10.rows(1).cells(i).cellFormat().rightBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet10.rows(1).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet10.rows(1).cells(i).cellFormat().font().colorInfo(
					new $.ig.excel.WorkbookColorInfo(
							$.ig.excel.WorkbookThemeColorType.light1));
			sheet10.rows(1).height(300);
			sheet10.rows(1).cellFormat().font().bold(true);
		}
		for (i = 0; i <= 9; i++) {
			sheet10.rows(2).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet10.rows(2).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet10.rows(2).cells(i).cellFormat().rightBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet10.rows(2).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet10.rows(2).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
		}
		for (i = 0; i <= 9; i++) {
			sheet10.rows(3).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#F8CBAD'));
			sheet10.rows(3).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet10.rows(3).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet10.rows(3).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
		}
		sheet10.getCell('A2').value('No. of HSN');
		sheet10.getCell('E2').value('Total Value');
		sheet10.getCell('F2').value('Total Taxable Value');
		sheet10.getCell('G2').value('Total Integrated Tax');
		sheet10.getCell('H2').value('Total Central Tax');
		sheet10.getCell('I2').value('Total State/UT Tax');
		sheet10.getCell('J2').value('Total Cess');
		sheet10
				.getCell('A3')
				.applyFormula(
						'=SUMPRODUCT((A5:A100 <> "")/COUNTIF(A5:A100,A5:A100 & ""))');
		sheet10.getCell('A3').cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		sheet10.getCell('E3').applyFormula('=SUM(E5:E100)');
		sheet10.getCell('F3').applyFormula('=SUM(F5:F100)');
		sheet10.getCell('G3').applyFormula('=SUM(G5:G100)');
		sheet10.getCell('H3').applyFormula('=SUM(H5:H100)');
		sheet10.getCell('I3').applyFormula('=SUM(I5:I100)');
		sheet10.getCell('J3').applyFormula('=SUM(J5:J100)');
		sheet10.getCell('A4').value('HSN');
		sheet10.getCell('B4').value('Description');
		sheet10.getCell('C4').value('UQC');
		sheet10.getCell('D4').value('Total Quantity');
		sheet10.getCell('E4').value('Total Value');
		sheet10.getCell('F4').value('Taxable Value');
		sheet10.getCell('G4').value('Integrated Tax Amount');
		sheet10.getCell('H4').value('Central Tax Amount');
		sheet10.getCell('I4').value('State/UT Tax Amount');
		sheet10.getCell('J4').value('Cess Amount');


		var qty = 0;
		var taxable = 0;
		var cgst = 0;
		var sgst = 0;
		var arr = [];
		var arr2 = [];
		var x = [];
		for (i = 0; i < gstr1hsn_data.length; i++) {
			var j = i + 5;
			sheet10.rows(j-1).cellFormat().font().height(14 * 14);
			
			arr[i] = gstr1hsn_data[i].prod_code;
			var pidv = gstr1hsn_data[i].prod_code;
			if (gstr1hsn_data[i].prod_type == 1) {
				var spd = fetchProductDetails(cat_types_data, pidv);
				var hsncode = gstr1hsn_data[i].hsncode;
				var uqc = eus[gstr1hsn_data[i].uqc];
			}
			if (gstr1hsn_data[i].prod_type == 2) {
				var spd = fetchARBProductDetails(arb_types_data, pidv);
				var hsncode = fetchARBHSNCode(arb_types_data, pidv);
				var uqc = eus[fetchARBUnitCode(arb_types_data, pidv)];
			}
			if (gstr1hsn_data[i].prod_type == 3) {
				var spd = fetchProductDetails(cat_types_data, pidv);
				var hsncode = gstr1hsn_data[i].hsncode;
				var uqc = eus[gstr1hsn_data[i].uqc];
			}
			if (gstr1hsn_data[i].prod_type == 4) {
				var spd = fetchProductDetails(services_types_data, pidv);
				var hsncode = gstr1hsn_data[i].hsncode;
				var uqc = eus[gstr1hsn_data[i].uqc];
			}
			qty = gstr1hsn_data[i].total_quantity;
			taxable = parseFloat(gstr1hsn_data[i].taxable_value);
			cgst = gstr1hsn_data[i].cgst_amount;
			sgst = gstr1hsn_data[i].sgst_amount;
			sheet10.getCell('A' + j).value(hsncode);
			sheet10.getCell('B' + j).value(spd);
			sheet10.getCell('C' + j).value(uqc);
			sheet10.getCell('D' + j).value(gstr1hsn_data[i].total_quantity);
			sheet10.getCell('E' + j).value(
					parseFloat(gstr1hsn_data[i].total_value));
			sheet10.getCell('F' + j).value(taxable);
			sheet10.getCell('G' + j).value(
					parseFloat(gstr1hsn_data[i].igst_amount));
			sheet10.getCell('H' + j).value(
					parseFloat(gstr1hsn_data[i].cgst_amount));
			sheet10.getCell('I' + j).value(
					parseFloat(gstr1hsn_data[i].sgst_amount));
			sheet10.getCell('J' + j).value(0);
		}

//  docs
		
		sheet11.columns(0).setWidth(350,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet11.columns(1).setWidth(100,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet11.columns(2).setWidth(100,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet11.columns(3).setWidth(150,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet11.columns(4).setWidth(150,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);

		sheet11.getCell('A1').value(
				'Summary of documents issued during the tax period (13)');
		sheet11.getCell('A1').cellFormat().alignment(
				$.ig.excel.HorizontalCellAlignment.center);
		sheet11.getCell('A1').cellFormat().fill(
				$.ig.excel.CellFill.createSolidFill('#0070C0'));
		sheet11.getCell('A1').cellFormat().rightBorderStyle(
				$.ig.excel.CellBorderLineStyle.thin);
		sheet11.getCell('A1').cellFormat().font().colorInfo(
				new $.ig.excel.WorkbookColorInfo(
						$.ig.excel.WorkbookThemeColorType.light1));
		sheet11.getCell('A1').cellFormat().font().Name = "Times New Roman";
		sheet11.rows(0).cellFormat().font().bold(true);
		
		sheet11.rows(0).cellFormat().font().height(14 * 14);
		sheet11.rows(1).cellFormat().font().height(14 * 14);
		sheet11.rows(2).cellFormat().font().height(14 * 14);
		sheet11.rows(3).cellFormat().font().height(14 * 15);


		for (i = 0; i <= 4; i++) {
			sheet11.rows(1).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#0070C0'));
			sheet11.rows(1).cells(i).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet11.rows(1).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet11.rows(1).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet11.rows(1).cells(i).cellFormat().rightBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet11.rows(1).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet11.rows(1).cells(i).cellFormat().font().colorInfo(
					new $.ig.excel.WorkbookColorInfo(
							$.ig.excel.WorkbookThemeColorType.light1));
			sheet11.rows(1).height(300);
		}

		for (i = 0; i <= 4; i++) {
			sheet11.rows(2).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet11.rows(2).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet11.rows(2).cells(i).cellFormat().rightBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet11.rows(2).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet11.rows(2).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
		}
		for (i = 0; i <= 4; i++) {
			sheet11.rows(3).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#F8CBAD'));
			sheet11.rows(3).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet11.rows(3).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet11.rows(3).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
		}

		sheet11.getCell('D2').value('Total Number');
		sheet11.getCell('E2').value('Total Cancelled');
		sheet11.getCell('D3').applyFormula('=SUM(D5:D10)');
		sheet11.getCell('E3').applyFormula('=SUM(E5:E10)');
		sheet11.getCell('A4').value('Nature of Document');
		sheet11.getCell('B4').value('Sr. No. From');
		sheet11.getCell('C4').value('Sr. No. To');
		sheet11.getCell('D4').value('Total Number');
		sheet11.getCell('E4').value('Cancelled');

		for (i = 0; i < gstr1docs_data.length; i++) {
			j = i + 5;
			sheet11.rows(j-1).cellFormat().font().height(14 * 14);
			
			if (gstr1docs_data[i].doc_type == 1) {
				sheet11.getCell('A' + j).value("Invoices for outward supply");
				sheet11.getCell('B' + j).value(gstr1docs_data[i].sr_no_from);
				sheet11.getCell('C' + j).value(gstr1docs_data[i].sr_no_to);
				sheet11.getCell('D' + j).value(gstr1docs_data[i].total_no);
				sheet11.getCell('E' + j).value(gstr1docs_data[i].cancelled);
			}
			if (gstr1docs_data[i].doc_type == 2) {
				sheet11.getCell('A' + j).value("Invoices for outward supply");
				sheet11.getCell('B' + j).value(gstr1docs_data[i].sr_no_from);
				sheet11.getCell('C' + j).value(gstr1docs_data[i].sr_no_to);
				sheet11.getCell('D' + j).value(gstr1docs_data[i].total_no);
				sheet11.getCell('E' + j).value(gstr1docs_data[i].cancelled);
			}
			if (gstr1docs_data[i].doc_type == 3) {
				sheet11.getCell('A' + j).value("Reviced Invoice");
				sheet11.getCell('B' + j).value(gstr1docs_data[i].sr_no_from);
				sheet11.getCell('C' + j).value(gstr1docs_data[i].sr_no_to);
				sheet11.getCell('D' + j).value(gstr1docs_data[i].total_no);
				sheet11.getCell('E' + j).value(gstr1docs_data[i].cancelled);
			}
			if (gstr1docs_data[i].doc_type == 5) {
				sheet11.getCell('A' + j).value("Debit Note");
				sheet11.getCell('B' + j).value(gstr1docs_data[i].sr_no_from);
				sheet11.getCell('C' + j).value(gstr1docs_data[i].sr_no_to);
				sheet11.getCell('D' + j).value(gstr1docs_data[i].total_no);
				sheet11.getCell('E' + j).value(gstr1docs_data[i].cancelled);
			}
			if (gstr1docs_data[i].doc_type == 4) {
				sheet11.getCell('A' + j).value("Credit Note");
				sheet11.getCell('B' + j).value(gstr1docs_data[i].sr_no_from);
				sheet11.getCell('C' + j).value(gstr1docs_data[i].sr_no_to);
				sheet11.getCell('D' + j).value(gstr1docs_data[i].total_no);
				sheet11.getCell('E' + j).value(gstr1docs_data[i].cancelled);
			}
		}
		
// master
		
		sheet19.columns(0).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet19.columns(1).setWidth(150,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet19.columns(2).setWidth(150,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet19.columns(3).setWidth(150,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet19.columns(4).setWidth(70,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet19.columns(5).setWidth(100,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet19.columns(6).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet19.columns(7).setWidth(150,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet19.columns(8).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet19.columns(9).setWidth(300,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet19.columns(10).setWidth(100,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet19.columns(11).setWidth(120,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet19.columns(12).setWidth(140,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet19.columns(13).setWidth(150,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		sheet19.columns(14).setWidth(170,
				$.ig.excel.WorksheetColumnWidthUnit.pixel);
		
		
		for (i = 0; i <= 14; i++) {
			sheet19.rows(0).cells(i).cellFormat().fill(
					$.ig.excel.CellFill.createSolidFill('#BDD7EE'));
			sheet19.rows(0).cells(i).cellFormat().font().height(14 * 15);
			sheet19.rows(0).cells(i).cellFormat().alignment(
					$.ig.excel.HorizontalCellAlignment.center);
			sheet19.rows(0).cells(i).cellFormat().bottomBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet19.rows(0).cells(i).cellFormat().leftBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet19.rows(0).cells(i).cellFormat().rightBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet19.rows(0).cells(i).cellFormat().topBorderStyle(
					$.ig.excel.CellBorderLineStyle.thin);
			sheet19.rows(0).height(1500);
			sheet19.rows(0).cellFormat().font().bold(true);
			sheet19.rows(0).cellFormat().font().Name = "Times New Roman";
		}
		
		sheet19.getCell('A1').value('UQC');
		sheet19.getCell('B1').value('Export Type');
		sheet19.getCell('C1').value('Reverse Charge/Provisional Assessment');
		sheet19.getCell('D1').value('Note Type');
		sheet19.getCell('E1').value('Type');
		sheet19.getCell('F1').value('Tax Rate');
		sheet19.getCell('G1').value('POS');
		sheet19.getCell('H1').value('Invoice Type');
		sheet19.getCell('I1').value('Reason For Issuing Note');
		sheet19.getCell('J1').value('Nature  of Document');
		sheet19.getCell('K1').value('UR Type');
		sheet19.getCell('L1').value('Supply Type');
		sheet19.getCell('M1').value('Month');
		sheet19.getCell('N1').value('Financial Year');
		sheet19.getCell('O1').value('Differential Percentage');
		
		// Save the workbook
		saveWorkbook(workbook, "GSTR1.xlsx");

	} else {
		document.getElementById("dialog-1").innerHTML = "Please,Select Date Range and click FetchGSTR1";
		alertdialogue();
		//alert("Please,Select Date Range and click FetchGSTR1");
	}
}

function saveWorkbook(workbook, name) {
	workbook.save({
		type : 'blob'
	}, function(data) {
		saveAs(data, name);
	}, function(error) {
		document.getElementById("dialog-1").innerHTML = "Error exporting: : "+ error;
		alertdialogue();
		//alert('Error exporting: : ' + error);
	});
}