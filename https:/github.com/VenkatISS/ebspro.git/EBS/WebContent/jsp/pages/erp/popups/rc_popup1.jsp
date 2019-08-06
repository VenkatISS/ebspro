<%@ page import="java.util.UUID" %>
<!DOCTYPE html>
<html>
<%
	UUID uuid = UUID.randomUUID();
    String randomUUIDString = uuid.toString();
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="css/modal.css?<%=randomUUIDString%>" type="text/css">
<jsp:useBean id="agencyVO" scope="session" class="com.it.erpapp.framework.model.vos.AgencyVO"></jsp:useBean>
<title><%=agencyVO.getGstin_no()+"-"+"ITV/RC"+"-"+request.getAttribute("dateyear")%></title>
<jsp:useBean id="cylinder_types_list" scope="request" class="java.lang.String"></jsp:useBean>
<jsp:useBean id="bank_data" scope="request" class="java.lang.String"></jsp:useBean>
<jsp:useBean id="cvo_data" scope="request" class="java.lang.String"></jsp:useBean>
<jsp:useBean id="area_codes_data" scope="request" class="java.lang.String"></jsp:useBean>
<jsp:useBean id="staff_data" scope="request" class="java.lang.String"></jsp:useBean>
<jsp:useBean id="equipment_data" scope="request" class="java.lang.String"></jsp:useBean>
<jsp:useBean id="refill_prices_data" scope="request" class="java.lang.String"></jsp:useBean>
<jsp:useBean id="rcs_data" scope="request" class="java.lang.String"></jsp:useBean>
<script type="text/javascript" src="js/modal.js?<%=randomUUIDString%>"></script>
<script type="text/javascript" src="js/commons/rupeeConvStValidation.js?<%=randomUUIDString%>"></script>
<script type="text/javascript" src="js/commons/general_validations.js?<%=randomUUIDString%>"></script>

<script type="text/javascript">
var cat_cyl_types_data = <%= cylinder_types_list %>;
var page_data = <%= rcs_data.length()>0? rcs_data : "" %>;
var bank_data = <%= bank_data %>;
var cvo_data = <%= cvo_data.length()>0? cvo_data : "[]" %>;
var staff_data = <%= staff_data.length()>0? staff_data : "[]" %>;
var area_codes_data = <%= area_codes_data.length()>0? area_codes_data : "[]" %>;
var equipment_data = <%= equipment_data.length()>0? equipment_data : "[]" %>;
var refill_prices_data = <%= refill_prices_data.length()>0? refill_prices_data : "[]" %>;
var ctdatahtml = "";
var vendordatahtml = "";
var staffdatahtml = "";
var areacodeshtml = "";
var months = ["JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"];
var payment_terms_values = ["NONE","CASH","CREDIT"];
var numString = "";
var eus = ["NONE","NOS","KGS","SET"];
</script>
<style>
.table {
	border-collapse: collapse;
}
.table, .td {
   	border: 1px solid black;
    border-color: #808B96;
    font-size: 12px;
    padding: 8px;
} 

td{
font-size: 12px;
}
</style>

<style type="text/css" media="print">
@page {
    size: auto;   /* auto is the initial value */
    margin: 0;  /* this affects the margin in the printer settings */
}
</style>
</head>
<body style="width: 93%;padding: -1px;margin-left:25px;">
	<table style="width: 90%; margin-left:70px">
		<tr valign="top">
			<td><br><br>
			<center><span style="font-size:15px;">ITV/RC</span></center>
			<span style="float:right;margin-top:-10px;padding-right:8px;">Original/Duplicate/Triplicate</span>
				<table style="width: 100%; border: 1px solid block; border-spacing: 0; border-collapse: 0;padding: -1px;margin: -1px; font-size: 12px;" class="table">
					<tr valign="top">
						<td width="80%" style="padding: 8px;">
							<b style="float:left;font-size:15px;"><span id="Aname"></span></b>
							<%
							if(agencyVO.getAgency_oc()==1){%>
							<b style="float:right;font-size:18px;"><img id="Aname" style="width:120px;height:60px" src="images/Indane.png"></img></b>
							<%} 
							else if(agencyVO.getAgency_oc()==2){%>
							<b style="float:right;font-size:18px;"><img id="Aname" style="width:120px;height:60px" src="images/Hp.png"></img></b>
							<%}
							else{%>
							<b style="float:right;font-size:18px;"><img id="Aname" style="width:120px;height:60px" src="images/Bharatgas.png"></img></b>
						<%} %>							
							<br><br>
							<span style="float:left;width:50%;">
							<% if(agencyVO.getAddress() != null) { %>
								AGENCY ADDRESS: <%= agencyVO.getAddress() %>
							<% }else {%>
									AGENCY ADDRESS: NOT DEFINED
							<% } %>
							</span><br><br>
							<span style="float:left;width:50%;">
							<b>GSTIN : </b><span id="dgstin"></span>
							</span>
							
							<span style="float:right;margin-top:-15px;width:50%;">
							MOB NO: <span id="llno"></span>,
							<br>
							<%= agencyVO.getEmail_id() %></span>
							<br>
							<br>

							<!--  DISPLAY STARTS HERE -->
							<table style="width: 100%;border: 1px solid;border-color: #808B96;font-size: 12px;padding: 8px;" class="table">
								<tr valign="top">
									<td width="50%" style="border: 1px solid;border-color: #808B96;font-size: 11px;" class="td">
										INVOICE NUMBER : <span id="invid"></span> <br>
										INVOICE DATE : <span id="invdate"></span> <br><br>
										CUSTOMER NO : <span id="cnospan" class="cnospan"></span>
									</td>
									<td width="50%" style="border: 1px solid;border-color: #808B96;font-size: 11px;" class="td">
										<label style = "margin-right:120px">DATE & TIME OF SUPPLY :</label><br> 
										PLACE OF SUPPLY : <span id="dpos"></span><br>
										STATE NAME : <span id="dstname"></span>
									</td>
								</tr>
							</table>
							<div id="data_table" class="row">
								<table id="m_data_table" style="width: 100%;border: 1px solid;border-color: #808B96;font-size: 13px;">
									<thead>
										<tr bgcolor="#808B96" height="30px">
											<th><font size=1 color="white" face="tahoma">S.NO</font></th>
											<th><font size=1 color="white" face="tahoma">PRODUCT</font></th>
											<th><font size=1 color="white" face="tahoma">HSN/SAC</font></th>
											<th><font size=1 color="white" face="tahoma">QTY</font></th>
											<th><font size=1 color="white" face="tahoma">UOM</font></th>
											<th><font size=1 color="white" face="tahoma">RATE</font></th>
											<th><font size=1 color="white" face="tahoma">DGCC AMOUNT</font></th>
											<th><font size=1 color="white" face="tahoma">ADMIN CHARGES</font></th>
											<th><font size=1 color="white" face="tahoma">TAXABLE</font></th>
											<th><font size=1 color="white" face="tahoma"> GST % </font></th>
											<th><font size=1 color="white" face="tahoma"> GST </font></th>
											<th><font size=1 color="white" face="tahoma"> TOTAL </font></th>
										</tr>
									</thead>
									<tbody id="m_data_table_body">
									</tbody>
								</table>
								<div align="left" class="row">
								<table id="data_table2" class="table" style="width: 100%;border: 1px solid;border-color: #808B96;font-size: 12px;padding: 8px;">
									<tr class="tr">
									 <td>
										<table>
											<tr>
												<td style="font-size: 12px;">TOTAL INVOICE AMOUNT :
												<span id="tinvamt1"></span></td>
											</tr>
											<tr>
												<td style="font-size: 12px;">TOTAL SECURITY DEPOSIT AMOUNT : <span id="tsda"></span></td>
 											</tr>
											<tr>
												<td style="font-size: 12px;">TOTAL AMOUNT : <span id="tamt"></span></td>
 											</tr>
											<tr valign="top">
												<td colspan="3" style="font-size: 12px;">TOTAL AMOUNT (In Words) : <b><span id="invamtwords"></span></b><br><br></td>
											</tr>
											<tr valign="top">
												<td colspan="3" style="font-size: 12px;">DECLARATION :  This is system generated.Hence no stamp and signature is required.<br></td>
											</tr>
											<tr><td>&nbsp</td></tr>
											<tr><td>&nbsp</td></tr>
											<tr><td>&nbsp</td></tr>
										</table>
									</td>
									<td>
										<table id="data_table3" class="table" style="width: 100%;margin-left: -1%;border: 1px solid;border-color: #808B96;font-size: 13px;padding: 8px;border-collapse:collapse;">
										 	<tr>
												<td class="td" colspan="2" width="24.5%" style="border: 1px solid;border-color: #808B96;font-size: 12px;">TAXABLE <b>: 
												<span id="taxableVal" style="font-size: 12px;"></span></b></td>
											</tr>
											<tr>
												<td class="td" colspan="2" width="24.5%" style="border: 1px solid;border-color: #808B96;font-size: 12px;">IGST <b>:
												<span id="igstVal" style="font-size: 12px;"></span></b></td>
											</tr>
											<tr>
												<td class="td" colspan="2" width="24.5%" style="border: 1px solid;border-color: #808B96;font-size: 12px;">CGST <b>:
												<span id="cgstVal" style="font-size: 12px;"></span></b></td>
											</tr>
											<tr>
												<td class="td" colspan="2" width="24.5%" style="border: 1px solid;border-color: #808B96;font-size: 12px;">SGST <b>:
												<span id="sgstVal" style="font-size: 12px;"></span></b></td>
											</tr>
											<tr>
												<td class="td" colspan="2" width="24.5%" style="border: 1px solid;border-color: #808B96;font-size: 13px;"><b>TOTAL :
												<span id="tinvamt" style="font-size: 13px;"></span></b></td>
											</tr>
										</table>
									</td>
									</tr>
								</table>
								<br><br>
							<div align="left" style="margin-top:-1px;">CUSTOMER SIGNATURE </div>
								
								<div align="right" style="margin-top:-16px;">STAMP AND SIGNATURE </div>
								</div>
							</div>
						</td>
				</table>
			</td>
		</tr>
	</table>
	<br>
	<!-- duplicate copy -->
	
	<table style="width: 90%; margin-left:70px">
		<tr valign="top">
			<td>
			<center><span style="font-size:15px;">ITV/RC</span></center>
			<span style="float:right;margin-top:-10px;padding-right:8px;">Original/Duplicate/Triplicate</span>
				<table style="width: 100%; border: 1px solid block; border-spacing: 0; border-collapse: 0;padding: -1px;margin: -1px; font-size: 12px;" class="table">
					<tr valign="top">
						<td width="80%" style="padding: 8px;">
							<b style="float:left;font-size:15px;"><span id="cAname"></span></b>
							<%
							if(agencyVO.getAgency_oc()==1){%>
							<b style="float:right;font-size:18px;"><img id="Aname" style="width:90px;height:60px" src="images/Indane.png"></img></b>
							<%} 
							else if(agencyVO.getAgency_oc()==2){%>
							<b style="float:right;font-size:18px;"><img id="Aname" style="width:90px;height:60px" src="images/Hp.png"></img></b>
							<%}
							else{%>
							<b style="float:right;font-size:18px;"><img id="Aname" style="width:90px;height:60px" src="images/Bharatgas.png"></img></b>
						<%} %>							
							<br><br>
							<span style="float:left;width:50%;">
							<% if(agencyVO.getAddress() != null) { %>
								AGENCY ADDRESS: <%= agencyVO.getAddress() %>
							<% }else {%>
									AGENCY ADDRESS: NOT DEFINED
							<% } %>
							</span><br><br>
							<span style="float:left;width:50%;">
							<b>GSTIN : </b><span id="cdgstin"></span>
							</span>
							
							<span style="float:right;margin-top:-15px;width:50%;">
							MOBILE NO: <span id="cllno"></span>,
							<br>
							EMAIL ID: <%= agencyVO.getEmail_id() %></span>
							<br>
							<br>

							<!--  DISPLAY STARTS HERE -->
							<table style="width: 100%;border: 1px solid;border-color: #808B96;font-size: 12px;padding: 8px;" class="table">
								<tr valign="top">
									<td width="50%" style="border: 1px solid;border-color: #808B96;font-size: 11px;" class="td">
										INVOICE NUMBER : <span id="cinvid"></span> <br>
										INVOICE DATE : <span id="cinvdate"></span> <br><br>
										CUSTOMER NO : <span id="ccnospan" class="cnospan"></span>
									</td>
									<td width="50%" style="border: 1px solid;border-color: #808B96;font-size: 11px;" class="td">
										<label style = "margin-right:120px">DATE & TIME OF SUPPLY :</label> <br>
										PLACE OF SUPPLY : <span id="cdpos"></span><br>
										STATE NAME : <span id="cdstname"></span>
									</td>
								</tr>
							</table>							
							<div id="data_table" class="row">
								<table id="cm_data_table" style="width: 100%;border: 1px solid;border-color: #808B96;font-size: 13px;">
									<thead>
										<tr bgcolor="#808B96" height="30px">
											<th><font size=1 color="white" face="tahoma">S.NO</font></th>
											<th><font size=1 color="white" face="tahoma">PRODUCT</font></th>
											<th><font size=1 color="white" face="tahoma">HSN/SAC</font></th>
											<th><font size=1 color="white" face="tahoma">QTY</font></th>
											<th><font size=1 color="white" face="tahoma">UOM</font></th>
											<th><font size=1 color="white" face="tahoma">RATE</font></th>
											<th><font size=1 color="white" face="tahoma">DGCC AMOUNT</font></th>
											<th><font size=1 color="white" face="tahoma">ADMIN CHARGES</font></th>
											<th><font size=1 color="white" face="tahoma">TAXABLE</font></th>
											<th><font size=1 color="white" face="tahoma"> GST % </font></th>
											<th><font size=1 color="white" face="tahoma"> GST </font></th>
											<th><font size=1 color="white" face="tahoma"> TOTAL </font></th>
										</tr>
									</thead>
									<tbody id="cm_data_table_body">
									</tbody>
								</table>
								<div align="left" class="row">
								<table id="data_table2" class="table" style="width: 100%;border: 1px solid;border-color: #808B96;font-size: 12px;padding: 8px;">
									<tr class="tr">
									 <td>
										<table>
											<tr>
												<td style="font-size: 12px;">TOTAL INVOICE AMOUNT :
												<span id="ctinvamt1"></span></td>
											</tr>
											<tr>
												<td style="font-size: 12px;">TOTAL SECURITY DEPOSIT AMOUNT : <span id="ctsda"></span></td>
 											</tr>
											<tr>
												<td style="font-size: 12px;">TOTAL AMOUNT : <span id="ctamt"></span></td>
 											</tr>
											<tr valign="top">
												<td colspan="3" style="font-size: 12px;">TOTAL AMOUNT (In Words) : <b><span id="cinvamtwords"></span></b><br><br></td>
											</tr>
											<tr valign="top">
												<td colspan="3" style="font-size: 12px;">DECLARATION :  This is system generated.Hence no stamp and signature is required.<br></td>
											</tr>
											<tr><td>&nbsp</td></tr>
											<tr><td>&nbsp</td></tr>
											<tr><td>&nbsp</td></tr>
										</table>
									</td>
									<td>
										<table id="data_table3" class="table" style="width: 100%;margin-left: -1%;border: 1px solid;border-color: #808B96;font-size: 13px;padding: 8px;border-collapse:collapse;">
										 	<tr>
												<td class="td" colspan="2" width="24.5%" style="border: 1px solid;border-color: #808B96;font-size: 12px;">TAXABLE <b>: 
												<span id="ctaxableVal" style="font-size: 12px;"></span></b></td>
											</tr>
											<tr>
												<td class="td" colspan="2" width="24.5%" style="border: 1px solid;border-color: #808B96;font-size: 12px;">IGST <b>:
												<span id="cigstVal" style="font-size: 12px;"></span></b></td>
											</tr>
											<tr>
												<td class="td" colspan="2" width="24.5%" style="border: 1px solid;border-color: #808B96;font-size: 12px;">CGST <b>:
												<span id="ccgstVal" style="font-size: 12px;"></span></b></td>
											</tr>
											<tr>
												<td class="td" colspan="2" width="24.5%" style="border: 1px solid;border-color: #808B96;font-size: 12px;">SGST <b>:
												<span id="csgstVal" style="font-size: 12px;"></span></b></td>
											</tr>
											<tr>
												<td class="td" colspan="2" width="24.5%" style="border: 1px solid;border-color: #808B96;font-size: 13px;"><b>TOTAL :
												<span id="ctinvamt" style="font-size: 13px;"></span></b></td>
											</tr>
										</table>
									</td>
									</tr>
								</table>
								<br><br>
								<div align="right">STAMP AND SIGNATURE </div>
								</div>
							</div>
						</td>
				</table>
			</td>
		</tr>
	</table>
</body>
<script>
	var agencyName = "<%=agencyVO.getAgency_name()%>";
	document.getElementById("Aname").innerHTML = agencyName.toUpperCase();
	document.getElementById("cAname").innerHTML = agencyName.toUpperCase();
	
	var dgstin = "<%= agencyVO.getGstin_no() %>";
	document.getElementById("dgstin").innerHTML	= dgstin;
	document.getElementById("cdgstin").innerHTML = dgstin;
	
	var landlineNo = "<%= agencyVO.getOffice_landline()%>";
	var mobileNo = <%= agencyVO.getAgency_mobile()%>;
	if(landlineNo != "null") {
		document.getElementById("llno").innerHTML = landlineNo;
		document.getElementById("cllno").innerHTML = landlineNo;
	}else {
		document.getElementById("llno").innerHTML = mobileNo;
		document.getElementById("cllno").innerHTML = mobileNo;
	}
		
</script>
<script type="text/javascript" src="js/modules/popups/rc_popup.js?<%=randomUUIDString%>"></script>
</html>