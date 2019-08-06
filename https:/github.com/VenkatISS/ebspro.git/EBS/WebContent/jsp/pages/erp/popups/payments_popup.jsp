<%@ page import="java.util.UUID" %>
<!DOCTYPE html>
<html>
<%
	UUID uuid = UUID.randomUUID();
    String randomUUIDString = uuid.toString();
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:useBean id="agencyVO" scope="session" class="com.it.erpapp.framework.model.vos.AgencyVO"></jsp:useBean>
<title><%=agencyVO.getGstin_no()+"-"+"TAX INVOICE"+"-"+request.getAttribute("dateyear")%></title>
<link rel="stylesheet" href="css/modal.css?<%=randomUUIDString%>" type="text/css">
<jsp:useBean id="payments_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="cvo_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="bank_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="staff_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="gst_payments_data" scope="request" class="java.lang.String"></jsp:useBean>
<script type="text/javascript" src="js/modal.js?<%=randomUUIDString%>"></script>
<script type="text/javascript" src="js/commons/general_validations.js?<%=randomUUIDString%>"></script>
<script type="text/javascript" src="js/commons/rupeeConvStValidation.js?<%=randomUUIDString%>"></script>
<script type="text/javascript">
var custdatahtml, staffdatahtml, bankdatahtml = "";
var page_data =  <%= payments_data.length()>0? payments_data : "[]" %>;
var bank_data =  <%= bank_data.length()>0? bank_data : "[]" %>;
var cvo_data = <%= cvo_data.length()>0 ? cvo_data : "[]"%>;
var staff_data =  <%= staff_data.length()>0? staff_data : "[]" %>;
var gst_payments_data = <%= gst_payments_data.length()>0? gst_payments_data : "[]" %>;
var ctdatahtml = "";
var vendordatahtml = "";
var staffdatahtml = "";
var areacodeshtml = "";
var months = ["JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"];
var payment_terms_values = ["NONE","CASH","CREDIT"];
var eus = ["NONE","NOS","KGS"];
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
<body style="width: 91%;padding: -1px;margin-left:25px;">
	<table style="width: 90%; margin-left:70px;margin-top:-42px">
		<tr valign="top">
			<td><br><br><br>
			<center><span style="font-size: 17px;">PAYMENT VOUCHER</span></center>
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
								AGENCY ADDRESS: <%= agencyVO.getAddress() %>
							<% }else {%>
									AGENCY ADDRESS: NOT DEFINED
							<% } %>
							</span><br><br>
							<span style="float:left;width:50%;">
							<b>GSTIN : </b><span id="dgstin"></span>
							</span>
							
							<span style="float:right;margin-top:-15px;width:50%;">
							MOB NO: <span id="llno"></span>
							<br>
							 <%= agencyVO.getEmail_id() %></span>
							<br>
							
							
							<!--  DISPLAY STARTS HERE -->
							<table style="width: 100%;border: 1px solid;border-color: #808B96;font-size: 9px;padding: 10px;" class="table">
								<tr valign="top">
									<td width="50%" style="border: 1px solid;border-color: #808B96;font-size: 9px;" class="td">
										VOUCHER NUMBER : <span  id="invid" style="font-size:14px;"></span> <br>
										VOUCHER DATE : <span id="invdate"></span> <br>
										<label style = "margin-right:240px">PO NO :</label><br>
										PO DATE :  <br>
									</td>
									<td width="50%" style="border: 1px solid;border-color: #808B96;font-size: 9px;" class="td">
										<label style = "margin-right:190px">MODE OF TPT : BY ROAD</label><br>
										<label>VEHICLE NO : </label><br>
										<label style = "margin-right:120px">DATE&TIME OF SUPPLY:</label><br> 
										PLACE OF SUPPLY : <span id="dpos"></span><br>
										STATE NAME : <span id="dstname"></span>
									</td>
								</tr>
							</table>
							<br>
							<table style="width: 100%;border: 1px solid;border-color: #808B96;font-size: 8px;">
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
							<div id="data_table" class="row"><br>
															<div align="left" class="row" style="font-size:6px;">								
							
										<table style="width: 100%;border: 1px solid;border-color: #808B96;font-size: 8px;">
										<tr>
										<td style="width:50%;font-size:10px;">
											Mode of Payment:<b><span id="mpmt" style="font-size: 12px;"></span></b>
											</td>
											<td style="width:50%;font-size:10px;">
											Instrument&nbsp;Details:<b><span id="insd" style="font-size: 12px;padding-right:80px;"></span></b>	
										</td>
										</tr>
								<tr>
								<td style="font-size:10px;">
									Narration:<b><span id="narr" style="font-size: 12px;"></span></b>	
									</td>
									</tr>
									<tr>
									<td style="font-size:10px;">		
									AMOUNT :<b><span id="invamnt" style="font-size: 14px;"></span></b><br>
									</td>
									</tr>
									<tr>
									<td style="width:100%;font-size:10px;">
											TOTAL INVOICE AMOUNT (In Words) : <b><span id="invamtwords"></span></b><br>
											DECLARATION :<span style="font-size:10px;">This is system generated.Hence no stamp and signature is required.</span>
											</td>
											</tr>
											</table>
											</div><br>
							<div align="right">STAMP AND SIGNATURE </div>
								</div>
							
						</td>
				</table>
			</td>
		</tr>
	</table>
	<table style="width: 90%; margin-left:70px">
		<tr valign="top">
			<td><br>
			<center><span style="font-size: 17px;">PAYMENT VOUCHER</span></center>
			<span style="float:right;margin-top:-10px;padding-right:10px;">Original/Duplicate/Triplicate</span>
				<table style="width: 100%; border: 1px solid block; border-spacing: 0; border-collapse: 0;padding: -1px;margin: -1px; font-size: 14px;" class="table">
					<tr valign="top">
						<td width="80%" style="padding: 10px;">
							<b style="float:left;font-size:18px;"><span id="cname"></span></b>
							<%
							if(agencyVO.getAgency_oc()==1){%>
							<b style="float:right;font-size:18px;"><img id="cname" style="width:120px;height:60px" src="images/Indane.png"></img></b>
							<%}
							else if(agencyVO.getAgency_oc()==2){%>
							<b style="float:right;font-size:18px;"><img id="cname" style="width:120px;height:60px" src="images/Hp.png"></img></b>
							<%}
							else{%>
							<b style="float:right;font-size:18px;"><img id="cname" style="width:120px;height:60px" src="images/Bharatgas.png"></img></b>
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
							<b>GSTIN : </b><span id="cgstin"></span>
							</span>
							
							<span style="float:right;margin-top:-15px;width:50%;">
							MOB NO: <span id="cllno"></span>
							<br>
							 <%= agencyVO.getEmail_id() %></span>
							
							<!--  DISPLAY STARTS HERE -->
							<table style="width: 100%;border: 1px solid;border-color: #808B96;font-size: 9px;padding: 10px;" class="table">
								<tr valign="top">
									<td width="50%" style="border: 1px solid;border-color: #808B96;font-size: 9px;" class="td">
										VOUCHER NUMBER : <span style="font-size:14px;" id="cinvid"></span> <br>
										INVOICE DATE : <span id="cinvdate"></span> <br>
										<label style = "margin-right:240px">PO NO :</label><br>
										PO DATE :  <br>
									</td>
									<td width="50%" style="border: 1px solid;border-color: #808B96;font-size: 9px;" class="td">
										<label style = "margin-right:190px">MODE OF TPT : BY ROAD</label><br>
										<label>VEHICLE NO : </label><br>
										<label style = "margin-right:120px">DATE&TIME OF SUPPLY:</label><br> 
										PLACE OF SUPPLY : <span id="cdpos"></span><br>
										STATE NAME : <span id="cdstname"></span>
									</td>
								</tr>
							</table>
							<br>
							<table style="width: 100%;border: 1px solid;border-color: #808B96;font-size: 8px;">
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
										Party Name : <span id="ccnamespan1" class="cnamespan"></span><br>
										Address : <span id="ccaddrspan1" class="caddrspan"></span><br>
										State Code : <span id="ccstatespan1" class="cstatespan"></span><br>
										<b>GSTIN : </b><span id="ccgstinspan1" class="cgstinspan"></span><br>
									</td>
									<td>
										Party Name : <span id="ccnamespan2" class="cnamespan"></span><br>
										Address : <span id="ccaddrspan2" class="caddrspan"></span><br>
										State Code : <span id="ccstatespan2" class="cstatespan"></span><br>
										<b>GSTIN : </b><span id="ccgstinspan2" class="cgstinspan"></span><br>
									</td>
								</tr>
							</table>
							<div id="data_table" class="row"><br>
															<div align="left" class="row">								
							
										<table style="width: 100%;border: 1px solid;border-color: #808B96;font-size: 8px;">
										<tr>
										<td style="width:60%;font-size:10px;">
											Mode of Payment:<b><span id="cmpmt" style="font-size: 12px;"></span></b>
											</td>
											<td style="width:30%;">
											Instrument&nbsp;Details:<b><span id="cinsd" style="font-size: 12px;padding-right:80px"></span></b>	
										</td>
										</tr>
								<tr>
								<td style="font-size:10px;">
									Narration:<b><span id="cnarr" style="font-size: 12px;"></span></b>	
									</td>
									</tr>
									<tr>
									<td style="font-size:10px;">		
									AMOUNT :<b><span id="cinvamnt" style="font-size: 14px;"></span></b><br>
									</td>
									</tr>
									<tr>
									<td style="width:100%;font-size:10px;">
											TOTAL INVOICE AMOUNT (In Words) : <b><span id="cinvamtwords"></span></b><br>
											DECLARATION :<span style="font-size: 12px;">This is system generated.Hence no stamp and signature is required.</span>
											</td>
											</tr>
											</table>
											</div><br>
							<div align="right">STAMP AND SIGNATURE </div>
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
	document.getElementById("cname").innerHTML = agencyName.toUpperCase();


	var dealergstin = "<%= agencyVO.getGstin_no() %>";
	document.getElementById("dgstin").innerHTML	= dealergstin;
	document.getElementById("cgstin").innerHTML	= dealergstin;

	var landlineNo = "<%= agencyVO.getOffice_landline()%>";
	var mobileNo = <%= agencyVO.getAgency_mobile()%>;
	if(landlineNo != "null") {
		document.getElementById("llno").innerHTML = landlineNo;
		document.getElementById("cllno").innerHTML = landlineNo;

	}else {
		document.getElementById("llno").innerHTML = mobileNo;
		document.getElementById("cllno").innerHTML = mobileNo;

	}	
	
/* 	var pos = dealergstin.substr(0, 2);
	document.getElementById("dpos").innerHTML = pos;
	document.getElementById("dstname").innerHTML = fetchState(pos); */
</script>
<script type="text/javascript" src="js/modules/popups/payments_popup.js?<%=randomUUIDString%>"></script>
</html>