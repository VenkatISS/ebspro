<%@ page import="java.util.UUID" %>
<!DOCTYPE html>
<html>
	<%
		UUID uuid = UUID.randomUUID();
	    String randomUUIDString = uuid.toString();
    %>
  	<head>
    	<meta charset="utf-8">
    	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    	<meta name="viewport" content="width=device-width, initial-scale=1">
    	<link rel="stylesheet" type="text/css" href="css/main.css?<%=randomUUIDString%>">
    	<title>GST REPORT</title>
	    <script src="js/commons/jquery-2.1.4.min.js?<%=randomUUIDString%>"></script>
		<jsp:useBean id="agencyVO" scope="session" class="com.it.erpapp.framework.model.vos.AgencyVO"></jsp:useBean>
		<jsp:useBean id="gst_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="gstdata_size" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="staff_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="arbp_reverse_charge" scope="request" class="java.lang.String"></jsp:useBean>

		<jsp:useBean id="cvo_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="arb_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="rder" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="gstr1b2b_data" scope="request" class="java.lang.String"></jsp:useBean>	
		<jsp:useBean id="cylinder_types_list" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="service_types_list" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="gstr1b2cl_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="gstr1b2cs_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="gstr1hsn_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="gstr1docs_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="gstr1cdnr_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="gstr1cdnur_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="gst_payments_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="gst_payment_details" scope="request" class="java.lang.String"></jsp:useBean>
		
		<script type="text/javascript">
			var rder = <%= rder.length()>0? rder : "[]" %>;
			var staff_data =  <%= staff_data.length()>0? staff_data : "[]" %>;
			var arbp_reverse_charge =  <%= arbp_reverse_charge.length()>0? arbp_reverse_charge : "[]" %>;
			
			var gst_data =  <%= gst_data.length()>0? gst_data : "[]" %>;
			var gstdata_size = <%= gstdata_size.length()>0? gstdata_size : "[]" %>;			
			
			var cvo_data =  <%= cvo_data.length()>0? cvo_data : "[]" %>;
			var cat_types_data = <%= cylinder_types_list.length()>0? cylinder_types_list : "[]" %>;
			var services_types_data = <%= service_types_list.length()>0? service_types_list : "[]" %>;
			var arb_types_data = <%= arb_data.length()>0? arb_data : "[]" %>;
			var gstr1b2b_data =  <%= gstr1b2b_data.length()>0? gstr1b2b_data : "[]" %>;
			var gstr1b2cl_data =  <%= gstr1b2cl_data.length()>0? gstr1b2cl_data : "[]" %>;
			var gstr1b2cs_data =  <%= gstr1b2cs_data.length()>0? gstr1b2cs_data : "[]" %>;
			var gstr1hsn_data =  <%= gstr1hsn_data.length()>0? gstr1hsn_data : "[]" %>;
			var gstr1docs_data =  <%= gstr1docs_data.length()>0? gstr1docs_data : "[]" %>;
			var gstr1cdnr_data =  <%= gstr1cdnr_data.length()>0? gstr1cdnr_data : "[]" %>;
			var gstr1cdnur_data =  <%= gstr1cdnur_data.length()>0? gstr1cdnur_data : "[]" %>;
			var gst_payments_data =  <%= gst_payments_data.length()>0? gst_payments_data : "[]" %>;
			var gst_payment_details =  <%= gst_payment_details.length()>0? gst_payment_details : "[]" %>;
			
			var gstpc = [0,5,12,18,28];
			var pigst = [0.00,0.00,0.00,0.00,0.00];
			var pcgst = [0.00,0.00,0.00,0.00,0.00];
			var pcigst = [0.00,0.00,0.00,0.00,0.00];
			var psgst = [0.00,0.00,0.00,0.00,0.00];
			var sigst = [0.00,0.00,0.00,0.00,0.00];
			var scgst = [0.00,0.00,0.00,0.00,0.00];
			var ssgst = [0.00,0.00,0.00,0.00,0.00];
			var tpgst = [0.00,0.00,0.00,0.00,0.00];
			var tsgst = [0.00,0.00,0.00,0.00,0.00];
			var ptaxable = [0.00,0.00,0.00,0.00,0.00];
			var staxable = [0.00,0.00,0.00,0.00,0.00];

			var ngst = 0.00;
			var tpigst = 0.00;
			var tpcgst = 0.00;
			var tpsgst = 0.00;
			var tsigst = 0.00;
			var tscgst = 0.00;
			var tssgst = 0.00;
			var tptaxable = 0.00;
			var tstaxable = 0.00;

			var tasigst = 0.00;
			var tascgst = 0.00;
			var tassgst = 0.00;
			var ttpsigst = 0.00;
			var ttpscgst = 0.00;
			var ttpssgst = 0.00;
			var ttcsigst = 0.00;
			var ttcscgst = 0.00;
			var ttcssgst = 0.00;
			var taisigst = 0.00;
			var taiscgst = 0.00;
			var taissgst = 0.00;
			var taasigst = 0.00;
			var taascgst = 0.00;
			var taassgst = 0.00;
			var tassigst = 0.00;

			var xltsigst = 0.00;
			var xltscgst = 0.00;
			var xltssgst = 0.00;

			var xltpigst = 0.00;
			var xltpcgst = 0.00;
			var xltpsgst = 0.00;

			var months = ["jan","feb","mar","apr","may","jun","jul","aug","sep","oct","nov","dec"];
			var eus = ["NONE","NOS","KGS","SET"];

			var gstpc2 = ["Integrated Tax","Central Tax","State/UT Tax","Cess"];
			var regstate = <%= agencyVO.getAgency_st_or_ut()%> ;
	</script>
<%-- 	<script src="js/commons/checkBrowser.js?<%=randomUUIDString%>"></script> --%>

	<!-- Sidenav -->
	<jsp:include page="/jsp/pages/commons/sidenav.jsp"/>    
	<!---END Sidenav--->
		
	<script type="text/javascript"> 
		window.onload = setWidthHightNav("100%");
	</script>
  	</head>
  	<body class="sidebar-mini fixed">
    	<div class="wrapper">
		<!-- Header-->
		<jsp:include page="/jsp/pages/commons/header.jsp"/>
	  	<!--header End--->

      		<div class="content-wrapper">
        		<div class="page-title">
          			<div>
            			<h1>GST REPORT  </h1>
          			</div>
					<ul id="nav" class="nav nav-pills clearfix right" role="tablist">
          				<li class="dropdown"><span class="dropdown-toggle  btn-info" data-toggle="dropdown" id="ahelp">Help</span>
							<ul id="products-menu" class="dropdown-menu clearfix" role="menu">
								<li><a style="font-size: 14px;" href="files/MyLPGBooks Promo.mp4" target="_blank">English</a></li>
								<!-- <li><a style="font-size: 14px;" href="https://youtu.be/bgQnKRrz2X8" target="_blank">Hindi</a></li> -->
							</ul>
						</li>
					</ul>
					
        		</div>
        		<div class="row">
          			<div class="clearfix"></div>
          			<div class="col-md-8"> 
              			<form id="gst_form" name="" method="post" action="ReportsDataControlServlet">
							<input type="hidden" id="agencyId" name="agencyId" value="<%= agencyVO.getAgency_code() %>">
							<input type="hidden" id="page" name="page" value="jsp/pages/erp/reports/transactionalReports/gstr.jsp">
							<input type="hidden" id="actionId" name="actionId" value="7032">
							<input type="hidden" id="fd" name="fd" value="">
							<input type="hidden" id="td" name="td" value="">
 							<div class="col-md-2 reprt" style="width:25%">
  								<label>MONTH </label>
  								<select class="form-control input_field select_dropdown"  id="sm" name="sm">
									<option value="00">SELECT</option>
									<option value="01">JAN</option>
									<option value="02">FEB</option>
									<option value="03">MAR</option>
									<option value="04">APR</option>
									<option value="05">MAY</option>
									<option value="06">JUN</option>
									<option value="07">JUL</option>
									<option value="08">AUG</option>
									<option value="09">SEP</option>
									<option value="10">OCT</option>
									<option value="11">NOV</option>
									<option value="12">DEC</option>
								</select>	
 							</div>
 							<div class="col-md-2 reprt" style="width:25%">
  								<label>YEAR </label>
  								<select class="form-control input_field select_dropdown" id="sy" name="sy">
						  			<option value="0">SELECT</option>
						  			<option value="2018">2018</option>
						  			<option value="2019">2019</option>
						  			<option value="2020">2020</option>
								</select>
 							</div>
 							<br><br><br><br><br><br>
<!-- 	 						<div class="col-md-2 reprt" style="width:100%"> -->
	 						<div class="col-md-2 reprt" style="width:640px">
		 						<label>Please &nbspEnter &nbspYour &nbspMonthly &nbspCarry &nbspforwards &nbspof &nbspGST &nbspin &nbspCreditors &nbspIGST, &nbspCGST &nbspand &nbspSGST :</label>
								<br><br>
		 						<label>Creditors IGST </label>
			  					<input type="text" id="pigst" name = "pigst" class='form-control input_field' maxlength="6" placeholder='IGST' style="width:25%;"></input>
	  			
	  							<label style="float: left;margin-left: 210px;margin-top: -63px;">Creditors CGST </label>
	  							<input type="text" id="pcgst" name = "pcgst" class='form-control input_field' maxlength="6" placeholder='CGST' style="width:25%;float:left;margin-left: 210px;margin-top: -38px;"></input>
	  						
	  							<label style="float: left;margin-left: 420px;margin-top: -65px;">Creditors SGST </label>
	  							<input type="text" id="psgst" name = "psgst" class='form-control input_field' maxlength="6" placeholder='SGST' style="width:25%;float:left;margin-left: 420px;margin-top: -40px;"></input>
<br>
	 						</div>
	 						 	
 						<!-- <div class="col-md-2 reprt" style="width:25%">
 						<label>liability IGST </label>
 						<input type="text" id="sigst" name = "sigst" class='form-control input_field' maxlength="6" placeholder='IGST'></input>
 						<label>liability CGST </label>
	  					<input type="text" id="scgst" name = "scgst" class='form-control input_field' maxlength="6" placeholder='CGST'></input>
	  					<label>liability SGST </label>
	  					<input type="text" id="ssgst" name = "ssgst" class='form-control input_field' maxlength="6" placeholder='SGST'></input>
	  						  					<input type="hidden" id="ccgst" name = "ccgst"></input>
	  					<input type="hidden" id="csgst" name = "csgst"></input>
 						</div> -->

 						<div class="col-md-2 reprt" style="width:25%">
	 						<input type="hidden" id="sigst" name = "sigst" class='form-control input_field' maxlength="6" placeholder='IGST'></input>
		  					<input type="hidden" id="scgst" name = "scgst" class='form-control input_field' maxlength="6" placeholder='CGST'></input>
		  					<input type="hidden" id="ssgst" name = "ssgst" class='form-control input_field' maxlength="6" placeholder='SGST'></input>
	  						<input type="hidden" id="ccgst" name = "ccgst"></input>
	  						<input type="hidden" id="csgst" name = "csgst"></input>
	  						<input type="hidden" id="gstAmt" name = "gstAmt"></input>
 						</div>
 						
 						</form>
 					</div>
	               	<br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
                	<button onclick="gstr1()" class="btn btn-info color_btn gst_fetch_btn">Fetch GSTR1</button>
	  				<button onclick="generateGSTR1()" class="btn btn-info color_btn">GENERATE GSTR1</button>
	  				<button onclick="fetchGSTR3()" class="btn btn-info color_btn">Fetch GSTR3</button>
	  				<button onclick="generateGSTR3()" class="btn btn-info color_btn">GENERATE GSTR3</button>

                	<div class="col-md-12" >
                		<div id="data_div" style="display: none;">
                			<div class="main_table1">
	                			<p><span id="fdate" >YEAR:</span>&nbsp&nbsp<%  out.println( request.getAttribute("year")); %>
                                   &nbsp&nbsp <span id="tdate">MONTH:</span>&nbsp&nbsp<% out.println( request.getAttribute("month")); %>
                                    <input type="text" id="gstinNum" value="<%= agencyVO.getGstin_no()  %>" hidden/>
                                    <input type="text" id="contactPerson" value="<%= agencyVO.getAgency_name() %>" hidden/>
                                    </p>
        	      				
								<table id="report_table" class="table table-striped"  style="width: 90%;border: 1px solid;border-color: #ffffff;font-size: 10px;">
									<thead>
										<tr class="title_head" height="30px">
											<th rowspan="2" style="text-align: center;"><font color="white" face="tahoma">GST RATE</font></th>
									        <th style="text-align: center;" rowspan="2"><font color="white" face="tahoma">TAXABLE</font></th>
											<th colspan="4" style="text-align: center;"><font color="white" face="tahoma">PURCHASE GST</font></th>
									        <th style="text-align: center;" rowspan="2"><font color="white" face="tahoma">TAXABLE</font></th>											
											<th colspan="4" style="text-align: center;"><font color="white" face="tahoma">SALE GST</font></th>
											<th rowspan="2" style="text-align: center;"><font color="white" face="tahoma">NET GST</font></th>
										</tr>
										<tr class="title_head"  height="30px"  style="width: 90%;border: 1px solid;border-color: #ffffff;">
											<th style="text-align: center;"><font color="white" face="tahoma">IGST</font></th>
											<th style="text-align: center;"><font color="white" face="tahoma">CGST</font></th>
											<th style="text-align: center;"><font color="white" face="tahoma">SGST</font></th>
											<th style="text-align: center;"><font color="white" face="tahoma">TOTAL</font></th>
											<th style="text-align: center;"><font color="white" face="tahoma">IGST</font></th>
											<th style="text-align: center;"><font color="white" face="tahoma">CGST</font></th>
											<th style="text-align: center;"><font color="white" face="tahoma">SGST</font></th>
											<th style="text-align: center;"><font color="white" face="tahoma">TOTAL</font></th>
										</tr>
									</thead>
									<tbody id="report_table_body" class="main_table">
									</tbody>
									</table><br>
									<button onclick="saveGSTAmounts(<%=request.getAttribute("year") %>,<%=request.getAttribute("month") %>)" class="btn btn-info color_btn" id="save_gst_amounts">Save GST Amounts</button>
									<button onclick="calculateGSTR3(<%=request.getAttribute("year") %>,<%=request.getAttribute("month") %>)" class="btn btn-info color_btn" id="calulate_gst_amounts" disabled="disabled">CALCULATE</button>
									<button onclick="SetOffGSTs(<%=request.getAttribute("year") %>,<%=request.getAttribute("month") %>)" class="btn btn-info color_btn" id="setoff_gst_amounts_btn" disabled="disabled">SET OFF</button> 
									<button onclick="payOffGSTAmt(<%=request.getAttribute("year") %>,<%=request.getAttribute("month") %>)" class="btn btn-info color_btn" id="payoff_gst_amounts_btn" disabled="disabled">PAY OFF GST AMOUNT</button> 
								<div style="display: none;" id="agency_payments_div">
								<table id="report2_table" class="table table-striped" style="width: 90%;border: 1px solid;border-color: #808B96;font-size: 12px;">
									<thead>
										<tr class="title_head"  height="30px"  style="width: 90%;border: 1px solid;border-color: #ffffff;">
											<th style="text-align: center;" rowspan="2"><font color="white" face="tahoma">Description</font></th>
											<th style="text-align: center;" rowspan="2"><font color="white" face="tahoma">Tax payable</font></th>
											<th style="text-align: center;" colspan="4"><font color="white" face="tahoma">Paid through ITC</font></th>
											<th style="text-align: center;" rowspan="2"><font color="white" face="tahoma">Tax/Cess Paid in cash</font></th>
											<th style="text-align: center;" rowspan="2"><font color="white" face="tahoma">Interest Paid in cash</font></th>
											<th style="text-align: center;" rowspan="2"><font color="white" face="tahoma">Late fee Paid in cash</font></th>
										</tr>
										<tr class="title_head"  height="30px"  style="width: 90%;border: 1px solid;border-color: #ffffff;">
											<th style="text-align: center;"><font color="white" face="tahoma">Integrated <br>Tax</font></th>
											<th style="text-align: center;"><font color="white" face="tahoma">Central <br>Tax</font></th>
											<th style="text-align: center;"><font color="white" face="tahoma">State/UT <br>Tax</font></th>
											<th style="text-align: center;"><font color="white" face="tahoma">Cess</font></th>
										</tr>
									</thead>
									<tbody id="report2_table_body" class="main_table">
									</tbody>
								</table>
								<br><br>
								GST AMOUNTS PAYABLES:
									<table id="net_gst3b_table" class="table table-striped" style="width: 50%;border: 1px solid;border-color: #ffffff; font-size: 12px;">
											<tr height="30px">
												<td><b>IGST AMOUNT</b></td>
												<td><b>:</b></td>
												<td><b><span id="net_igst_span"></span></b></td>
											</tr>
											<tr height="30px">
												<td><b>CGST AMOUNT</b></td>
												<td><b>:</b></td>
												<td><b><span id="net_cgst_span"></span></b></td>
											</tr>
											
											<tr height="30px">
												<td><b>SGST AMOUNT</b></td>
												<td><b>:</b></td>
												<td><b><span id="net_sgst_span"></span></b></td>
											</tr>
											
											<tr height="30px">
												<td><b>TOTAL PAYABLE GST AMOUNT</b></td>
												<td><b>:</b></td>
												<td><b><span id="net_total_gst_span"></span></b></td>
											</tr>
										</table>
										<br>
								<div>
									<form action="">
									<table id="net_gst_table" class="table table-striped" style="width: 50%;border: 1px solid;border-color: #ffffff;font-size: 10px;">
										<tr height="30px">
											<td> &nbsp&nbsp&nbsp NET GST AMOUNT</td>
											<td>: &nbsp&nbsp&nbsp</td>
											<td><span id="net_gst_span"></span></td>
										</tr>
										<tr height="30px">
											<td> &nbsp&nbsp&nbsp VERIFIED BY</td>
											<td>: &nbsp&nbsp&nbsp</td>
											<td><span  id="staff_span"></span></td>
										</tr>
									</table>
								</form>
							</div>
							</div>
						</div>
						<div class="clearfix"></div>
						<div class="col-md-12">
							<button class="btn btn-info color_btn bg_color3 srchbtn" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/home.jsp','2001')" style="margin-left:-15px;">BACK</button>
						</div>	
          			</div>
        		</div>
      		</div>
    	</div>
    	</div>
    	<div id = "dialog-1" title="Alert(s)"></div>
 		<div id="dialog-confirm"><div id="myDialogText" style="color:black;"></div></div>
  	</body>
  	<script type="text/javascript" src="js/commons/general_validations.js?<%=randomUUIDString%>"></script>
<!-- 	<script type="text/javascript" src="js/modules/reports/gstr.js"></script> -->
	<script type="text/javascript" src="js/modules/reports/transactionalReports/gstr1.js?<%=randomUUIDString%>"></script>
	<script type="text/javascript" src="js/modules/reports/transactionalReports/gstr3b.js?<%=randomUUIDString%>"></script>

<!-- 	<script type="text/javascript" src="js/modules/reports/transactionalReports/gstr1.js"></script> -->
<!-- <script type="text/javascript" src="js/modules/reports/transactionalReports/gstr3b.js"></script> -->

    <script src="js/commons/main.js?<%=randomUUIDString%>"></script>

	<script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.7.12/xlsx.core.min.js"></script>
	<script src="https://igniteui.com/js/external/FileSaver.js"></script>
	<script src="https://igniteui.com/js/external/Blob.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.7.12/xlsx.core.min.js"></script>
	<script src="https://cdn-na.infragistics.com/igniteui/latest/js/infragistics.core.js"></script>
	<script src="https://cdn-na.infragistics.com/igniteui/latest/js/modules/infragistics.ext_core.js"></script>
	<script src="https://cdn-na.infragistics.com/igniteui/latest/js/modules/infragistics.ext_collections.js"></script>	
	<script src="https://cdn-na.infragistics.com/igniteui/latest/js/modules/infragistics.ext_text.js"></script>
	<script src="https://cdn-na.infragistics.com/igniteui/latest/js/modules/infragistics.ext_io.js"></script>
	<script src="https://cdn-na.infragistics.com/igniteui/latest/js/modules/infragistics.ext_ui.js"></script>
	<script src="https://cdn-na.infragistics.com/igniteui/latest/js/modules/infragistics.documents.core_core.js"></script>
	<script src="https://cdn-na.infragistics.com/igniteui/latest/js/modules/infragistics.ext_collectionsextended.js"></script>
	<script src="https://cdn-na.infragistics.com/igniteui/latest/js/modules/infragistics.excel_core.js"></script>
	<script src="https://cdn-na.infragistics.com/igniteui/latest/js/modules/infragistics.ext_threading.js"></script>
	<script src="https://cdn-na.infragistics.com/igniteui/latest/js/modules/infragistics.ext_web.js"></script>
	<script src="https://cdn-na.infragistics.com/igniteui/latest/js/modules/infragistics.xml.js"></script>
	<script src="https://cdn-na.infragistics.com/igniteui/latest/js/modules/infragistics.documents.core_openxml.js"></script>	
	<script src="https://cdn-na.infragistics.com/igniteui/latest/js/modules/infragistics.excel_serialization_openxml.js"></script>
	
	<script type="text/javascript">
		document.getElementById("nameSpan").innerHTML = <%= agencyVO.getAgency_code() %>;
		var effDate = new Date(<%= agencyVO.getEffective_date()%>);
		var gstYear=<%=  request.getAttribute("year") %>;
		var gstMonth=<%= request.getAttribute("month") %>;
	</script>
</html>