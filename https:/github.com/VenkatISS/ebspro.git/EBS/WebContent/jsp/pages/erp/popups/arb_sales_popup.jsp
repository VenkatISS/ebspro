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
<title><%=agencyVO.getGstin_no()+"-"+"TAX INVOICE"+"-"+request.getAttribute("dateyear")%></title>
<jsp:useBean id="arb_types_list" scope="request" class="java.lang.String"></jsp:useBean>
<jsp:useBean id="arb_data" scope="request" class="java.lang.String"></jsp:useBean>
<jsp:useBean id="bank_data" scope="request" class="java.lang.String"></jsp:useBean>
<jsp:useBean id="cvo_data" scope="request" class="java.lang.String"></jsp:useBean>
<jsp:useBean id="area_codes_data" scope="request" class="java.lang.String"></jsp:useBean>
<jsp:useBean id="staff_data" scope="request" class="java.lang.String"></jsp:useBean>
<jsp:useBean id="equipment_data" scope="request" class="java.lang.String"></jsp:useBean>
<jsp:useBean id="refill_prices_data" scope="request" class="java.lang.String"></jsp:useBean>
<jsp:useBean id="arbs_data" scope="request" class="java.lang.String"></jsp:useBean>
<script type="text/javascript" src="js/modal.js?<%=randomUUIDString%>"></script>
<script type="text/javascript" src="js/commons/general_validations.js?<%=randomUUIDString%>"></script>
<script type="text/javascript" src="js/commons/rupeeConvStValidation.js?<%=randomUUIDString%>"></script>
<script type="text/javascript">
var cat_arb_types_data = <%= arb_types_list %>;
var page_data = <%= arbs_data.length()>0? arbs_data : "" %>;
var arb_data = <%= arb_data.length()>0? arb_data : "[]" %>;
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
var eus = ["NONE","NOS","KGS","SET"];
</script>
<style>
.table {
	border-collapse: collapse;
}
.table, .td {
   	border: 1px solid black;
    border-color: #808B96;
    font-size: 14px;
    padding: 10px;
} 
.footer {
  position: absolute;
  right: 0;
  bottom: 0;
  left: 0;
  padding: 1rem;
  background-color: #efefef;
  text-align: center;
}

td{
	font-size: 14px;
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
			<td><br><br><br>
			<center><span style="font-size:17px;">TAX INVOICE</span></center>
			<span style="float:right;margin-top:-10px;padding-right:10px;">Original/Duplicate/Triplicate</span>
				<table style="width: 100%; border: 1px solid block; border-spacing: 0; border-collapse: 0;padding: -1px;margin: -1px; font-size: 14px;" class="table">
					<tr valign="top">
						<td width="80%" style="padding: 10px;">
							<b style="float:left;font-size:18px;"><span id="Aname"></span></b>
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
								AGENCY ADDRESS: <%= agencyVO.getAddress() %> <br>
							<% }else {%>
									AGENCY ADDRESS: NOT DEFINED <br>
							<% } %>
							</span><br>
							<br>
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
							<table style="width: 100%;border: 1px solid;border-color: #808B96;font-size: 12px;padding: 10px;" class="table">
								<tr valign="top">
									<td width="50%" style="border: 1px solid;border-color: #808B96;font-size: 12px;" class="td">
										INVOICE NUMBER : <span id="invid"></span> <br>
										INVOICE DATE : <span id="invdate"></span> <br>
										<label style = "margin-right:240px">PO NO :</label><br>
										PO DATE :  <br>
									</td>
									<td width="50%" style="border: 1px solid;border-color: #808B96;font-size: 12px;" class="td">
										<label style = "margin-right:180px">MODE OF TPT : BY ROAD </label><br>
										<label>VEHICLE NO : </label><br>
										<label style = "margin-right:120px">DATE&TIME OF SUPPLY:</label><br>
										PLACE OF SUPPLY : <span id="dpos"></span><br>
										STATE NAME : <span id="dstname"></span>
									</td>
								</tr>
							</table>
							<br>
							<table style="width: 100%;border: 1px solid;border-color: #808B96;font-size: 14px;">
								<tr bgcolor="#808B96" height="30px">
									<td width="50%" align="center">
										Details of the Receiver (Billed To)
									</td>
									<td width="50%" align="center">
										Details of the Receiver (Shipped To)
									</td>
								</tr>
								<tr>
									<td>
										Party Name : <span id="cnamespan1" class="cnamespan"></span><br>
										Address : <span id="caddrspan1" class="caddrspan"></span><br>
										State Code : <span id="cstatespan1" class="cstatespan"></span><br>
										<b>GSTIN : </b><span id="cgstinspan1" class="cgstinspan"></span><br>
									</td>
									<td>
										Party Name : <span id="cnamespan2" class="cnamespan"></span><br>
										Address : <span id="caddrspan2" class="caddrspan"></span><br>
										State Code : <span id="cstatespan2" class="cstatespan"></span><br>
										<b>GSTIN : </b><span id="cgstinspan2" class="cgstinspan"></span><br>
									</td>
								</tr>
							</table>
							<div id="data_table" class="row">
								<br>
								<table id="m_data_table" style="width: 100%;border: 1px solid;border-color: #808B96;font-size: 14px;">
									<thead>
										<tr bgcolor="#808B96" height="30px">
											<th><font size=1 color="white" face="tahoma">S.NO</font></th>
											<th><font size=1 color="white" face="tahoma">PRODUCT</font></th>
											<th><font size=1 color="white" face="tahoma">HSN/SAC</font></th>
											<th><font size=1 color="white" face="tahoma">QTY</font></th>
											<th><font size=1 color="white" face="tahoma">UOM</font></th>
											<th><font size=1 color="white" face="tahoma">RATE</font></th>
											<th><font size=1 color="white" face="tahoma">DISCOUNT</font></th>
											<th><font size=1 color="white" face="tahoma">TAXABLE</font></th>
											<th><font size=1 color="white" face="tahoma"> GST % </font></th>
											<th><font size=1 color="white" face="tahoma"> GST </font></th>
											<th><font size=1 color="white" face="tahoma"> TOTAL </font></th>
										</tr>
									</thead>
									<tbody id="m_data_table_body">
									</tbody>
								</table>
								<br>
								<br>
								<div align="left" class="row">
									<table id="data_table2" class="table" style="width: 100%;border: 1px solid;border-color: #808B96;font-size: 14px;padding: 10px;">
										<tr>
										 <td>
										 	<table>
												<tr valign="top">
													<td colspan="3" style="font-size: 14px;">TOTAL INVOICE AMOUNT (In Words) : <b><span id="invamtwords"></span></b> <br><br></td>
												</tr>
												<tr valign="top">
													<td colspan="3" style="font-size: 14px;">DECLARATION : This is system generated.Hence no stamp and signature is required.<br><br> </td>
												</tr>
											 	<tr><td>&nbsp</td></tr>
												<tr><td>&nbsp</td></tr>
												<tr><td>&nbsp</td></tr>
											</table>
										</td>
										<td>
											<table id="data_table3" class="table" style="width: 100%;margin-left: -1%;border: 1px solid;border-color: #808B96;font-size: 15px;padding: 10px;border-collapse:collapse;">
										 		<tr>
													<td class="td" colspan="2" width="24.5%" style="border: 1px solid;border-color: #808B96;font-size: 14px;">TAXABLE <b>:</b> 
													<b><span id="taxableVal" style="font-size: 14px;"></span></b></td>
												</tr>
												<tr>
													<td class="td" colspan="2" width="24.5%" style="border: 1px solid;border-color: #808B96;font-size: 14px;">IGST <b>:</b>
													<b><span id="igstVal" style="font-size: 14px;"></span></b></td>
												</tr>
												<tr>
													<td class="td" colspan="2" width="24.5%" style="border: 1px solid;border-color: #808B96;font-size: 14px;">CGST <b>:</b>
													<b><span id="cgstVal" style="font-size: 14px;"></span></b></td>
												</tr>
												<tr>
													<td class="td" colspan="2" width="24.5%" style="border: 1px solid;border-color: #808B96;font-size: 14px;">SGST <b>:</b>
													<b><span id="sgstVal" style="font-size: 14px;"></span></b></td>
												</tr>
												<tr>
													<td class="td" colspan="2" width="24.5%" style="border: 1px solid;border-color: #808B96;font-size: 15px;"><b>TOTAL :</b>
													<b><span id="tinvamt" style="font-size: 15px;"></span></b></td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
								<br><br>
								<div align="right">STAMP AND SIGNATURE </div>
								</div>
							</div>
							<br><br>
						</td>
				</table>
			</td>
		</tr>
	</table>
</body>
<script type="text/javascript">
	var agencyName = "<%=agencyVO.getAgency_name()%>";
	document.getElementById("Aname").innerHTML = agencyName.toUpperCase();
	
	var dgstin = "<%= agencyVO.getGstin_no() %>";
	document.getElementById("dgstin").innerHTML	= dgstin;
	
	var landlineNo = "<%= agencyVO.getOffice_landline()%>";
	var mobileNo = <%= agencyVO.getAgency_mobile()%>;
	if(landlineNo != "null") {
		document.getElementById("llno").innerHTML = landlineNo;
	}else {
		document.getElementById("llno").innerHTML = mobileNo;
	}
</script>
<script type="text/javascript" src="js/modules/popups/arb_sales_popup.js?<%=randomUUIDString%>"></script> 
</html>