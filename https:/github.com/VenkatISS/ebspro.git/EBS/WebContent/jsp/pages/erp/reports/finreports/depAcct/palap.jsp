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

    	<title>MyLPGBooks DEALER WEB APPLICATION - PROFIT AND LOSS ACCOUNT</title>
		<jsp:useBean id="agencyVO" scope="session" class="com.it.erpapp.framework.model.vos.AgencyVO"></jsp:useBean>
		<jsp:useBean id="pal_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="pal_details" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="sfy" scope="request" class="java.lang.String"></jsp:useBean>
		<script type="text/javascript">
			var months = ["JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"];
			var years = ["2018","2019","2020"];
			var pal_data = <%= pal_data.length()>0? pal_data : "[]" %>;
			var pal_details = <%= pal_details.length()>0? pal_details : "[]" %>;
			var plstatus = ["","NEW","IN-PROGRESS","READY","COMPLETED"];
			var sfy = <%= sfy %>;
		</script>
 	    <script src="js/commons/jquery-2.1.4.min.js?<%=randomUUIDString%>"></script>
		<script src="js/commons/bootstrap.min.js?<%=randomUUIDString%>"></script>
<%-- 		<script src="js/commons/checkBrowser.js?<%=randomUUIDString%>"></script> --%>

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
            		<h1>PROFIT AND LOSS ACCOUNT</h1>
          		</div>
					<!-- <ul id="nav" class="nav nav-pills clearfix right" role="tablist">
          				<li class="dropdown"><span class="dropdown-toggle  btn-info" data-toggle="dropdown" id="ahelp">Help</span>
							<ul id="products-menu" class="dropdown-menu clearfix" role="menu">
								<li><a style="font-size: 14px;" href="https://youtu.be/bgQnKRrz2X8" target="_blank">English</a></li>
								<li><a style="font-size: 14px;" href="https://youtu.be/bgQnKRrz2X8" target="_blank">Hindi</a></li>
							</ul>
						</li>
					</ul> -->
        	</div>

			<form id="pl_form" name="" method="post" action="ReportsDataControlServlet">
				<input type="hidden" id="agencyId" name="agencyId" value="<%= agencyVO.getAgency_code() %>">
				<input type="hidden" id="page" name="page" value="jsp/pages/erp/reports/finreports/depAcct/palap.jsp">
				<input type="hidden" id="actionId" name="actionId" value="8542">
				<input type="hidden" id="recId" name="recId" value="">
				<input type="hidden" id="sfyId" name="sfyId" value="">
	
	        	<div class="row">
    	     		<div class="clearfix"></div>
        		  	<div class="col-md-15">	
                		<div class="col-md-3 reprt">
  							<label>FINANCIAL YEAR</label>
							<select id="sfy" name="sfy" class="form-control input_field select_dropdown">
								<option value="00">SELECT</option>
								<option value="2018">2018-19</option>
								<option value="2019">2019-20</option>
								<option value="2020">2020-21</option>								
							</select>
 						</div>
	 					<div class="col-md-2 reprt">
  							<button onclick="fetchPandLAccount()" id="searchb" name="searchb" class="btn btn-info color_btn" style="margin-top:25px;">FETCH</button>
 						</div>
            	  	</div>
           		</div>
				<br>
				<div class="row" id="display_div">
	     			<div class="clearfix"></div>
					<div id="pl_display_div" style="display: none;">
						<br><br>
						<div class="col-md-12">
							<b>FINANCIAL YEAR : <span id="fySpan"></span></b>
							<br><br>
							<b>STATUS : <span id="plsSpan"></span>&nbsp;<span id="plProcessSpan"></span>
							&nbsp;<span id="plSaveSpan"></span>&nbsp;<span id="plSubmitSpan"></span></b>
							<br><br>
       					</div>
       				</div>
 	     		</div>
				<div class="row">
	    	 		<div class="clearfix"></div>
					<div id="pl_data_div" style="display: none;">
	      				<div class="col-md-12">
	      					<div id="pl_data_table_div">
	       						<div class="main_table">
	         						<div class="table-responsive">
		            					<table class="table" id="pl_data_table">
    	    	      						<thead>
        	    	    						<tr class="title_head">     
            		    							<th><font size=1 color="white" face="tahoma"></font></th>            	    						
            		    							<th><font size=1 color="white" face="tahoma"><b>PARTICULARS</b></font></th>
													<th><font size=1 color="white" face="tahoma"><b>AMOUNT</b></font></th>
													<th><font size=1 color="white" face="tahoma"></font></th>
													<th><font size=1 color="white" face="tahoma"><b>PARTICULARS</b></font></th>
													<th><font size=1 color="white" face="tahoma"><b>AMOUNT</b></font></th>
												</tr>
											</thead>
											<tbody id="data_table_body">
												<tr>
													<td>TO</td>
													<td>OPENING STOCK</td>
													<td><input type='text' id='os' name='os' class="form-control input_field"></td>
													<td>BY</td>
													<td>CLOSING STOCK</td>
													<td><input type='text' id='cs' name='cs' class="form-control input_field"></td>
												</tr>
												<tr>
													<td>TO</td>
													<td>PURCHASES</td>
													<td><input type='text' id='ps' name='ps' class="form-control input_field"></td>
													<td>BY</td>
													<td>SALES</td>
													<td><input type='text' id='ss' name='ss' class="form-control input_field"></td>
												</tr>
												<tr>
													<td></td>
													<td></td>
													<td></td>
													<td>BY</td>
													<td>OTHER INCOME</td>
													<td><input type='text' id='ots' name='ots' class="form-control input_field"></td>
												</tr>
												<tr>
													<td colspan="6"></td>
												</tr>
												<tr>
													<td>TO</td>
													<td><b>GROSS PROFIT</b></td>
													<td><input type='text' id='gp' name='gp' class="form-control input_field" readonly="readonly" style="background-color:#FAFAC2;"></td>
													<td></td>
													<td></td>
													<td></td>
												</tr>
												<tr>
													<td colspan="6"></td>
												</tr>
												<tr>
													<td></td>
													<td><b>DIRECT EXPENSES</b></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
												</tr>
												<tr>
													<td>TO</td>
													<td>FREIGHT OUTWARDS</td>
													<td><input type='text' id='fo' name='fo' class="form-control input_field" readonly="readonly" style="background-color:#F3F3F3;"></td>
													<td></td>
													<td></td>
													<td></td>
												</tr>
												<tr>
													<td>TO</td>
													<td>FREIGHT INWARDS</td>
													<td><input type='text' id='fi' name='fi' class="form-control input_field" readonly="readonly" style="background-color:#F3F3F3;"></td>
													<td></td>
													<td></td>
													<td></td>
												</tr>
												<tr>
													<td>TO</td>
													<td>UTILITY CHARGES</td>
													<td><input type='text' id='uc' name='uc' class="form-control input_field" readonly="readonly" style="background-color:#F3F3F3;"></td>
													<td></td>
													<td></td>
													<td></td>
												</tr>
												<tr>
													<td></td>
													<td><b>INDIRECT EXPENSES</b></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
												</tr>
												<tr>
													<td>TO</td>
													<td>INSURANCE &amp; LICENSES</td>
													<td><input type='text' id='ial' name='ial' class="form-control input_field" readonly="readonly" style="background-color:#F3F3F3;"></td>
													<td></td>
													<td></td>
													<td></td>
												</tr>
												<tr>
													<td>TO</td>
													<td>STATIONERY</td>
													<td><input type='text' id='stry' name='stry' class="form-control input_field" readonly="readonly" style="background-color:#F3F3F3;"></td>
													<td></td>
													<td></td>
													<td></td>
												</tr>
												<tr>
													<td>TO</td>
													<td>TRAVELLING</td>
													<td><input type='text' id='te' name='te' class="form-control input_field" readonly="readonly" style="background-color:#F3F3F3;"></td>
													<td></td>
													<td></td>
													<td></td>
												</tr>
												<tr>
													<td>TO</td>
													<td>RENT</td>
													<td><input type='text' id='rent' name='rent' class="form-control input_field" readonly="readonly" style="background-color:#F3F3F3;"></td>
													<td></td>
													<td></td>
													<td></td>
												</tr>
												<tr>
													<td>TO</td>
													<td>BUSINESS PROMOTION</td>
													<td><input type='text' id='bp' name='bp' class="form-control input_field" readonly="readonly" style="background-color:#F3F3F3;"></td>
													<td></td>
													<td></td>
													<td></td>
												</tr>
												<tr>
													<td>TO</td>
													<td>STAFF WELFARE</td>
													<td><input type='text' id='sw' name='sw' class="form-control input_field" readonly="readonly" style="background-color:#F3F3F3;"></td>
													<td></td>
													<td></td>
													<td></td>
												</tr>
												<tr>
													<td>TO</td>
													<td>ALL OTHER EXPENDITURE</td>
													<td><input type='text' id='ms' name='ms' class="form-control input_field" readonly="readonly" style="background-color:#F3F3F3;"></td>
													<td></td>
													<td></td>
													<td></td>
												</tr>
												<tr>
													<td>TO</td>
													<td>BANK CHARGES</td>
													<td><input type='text' id='bc' name='bc' class="form-control input_field" readonly="readonly" style="background-color:#F3F3F3;"></td>
													<td></td>
													<td></td>
													<td></td>
												</tr>
												<tr>
													<td></td>
													<td><b>DEPRECIATION</b></td>
													<td><input type='text' id='dep' name='dep' class="form-control input_field" readonly="readonly" style="background-color:#F3F3F3;"></td>
													<td></td>
													<td></td>
													<td></td>
												</tr>
												<tr>
													<td colspan="6"></td>
												</tr>
												<tr>
													<td>TO</td>
													<td><b>NET PROFIT</b></td>
													<td><input type='text' id='np' name='np' class="form-control input_field" readonly="readonly" style="background-color:#FAFAC2;"></td>
													<td></td>
													<td></td>
													<td></td>
												</tr>
											</tbody>
										</table>
			  						</div>
            					</div>
          					</div>
        				</div>
       				</div>
      			</div>
				</form>
			<br>
			<div class="clearfix"></div>
			<div class="col-md-12">
				<button class="btn btn-info color_btn bg_color3 srchbtn" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/home.jsp','2001')" style="margin-left:-15px;">BACK</button>
			</div>	
        </div>
        </div>
        <div id = "dialog-1" title="Alert(s)"></div>
 		<div id="dialog-confirm"><div id="myDialogText" style="color:black;"></div></div>
  	</body>
  	<script type="text/javascript" src="js/commons/general_validations.js?<%=randomUUIDString%>"></script>
	<script type="text/javascript" src="js/modules/reports/finreports/depAcct/palap.js?<%=randomUUIDString%>"></script>
	<script type="text/javascript" src="js/modal.js?<%=randomUUIDString%>"></script>
    <script src="js/commons/main.js?<%=randomUUIDString%>"></script>

  	<script type="text/javascript">
 		document.getElementById("nameSpan").innerHTML = <%= agencyVO.getAgency_code() %>; 
 		document.getElementById('pl_form').agencyId = <%= agencyVO.getAgency_code() %>;
 		
	</script>
</html>