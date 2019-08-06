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

    	<title>MyLPGBooks DEALER WEB APPLICATION - BALANCE SHEET</title>
		<jsp:useBean id="agencyVO" scope="session" class="com.it.erpapp.framework.model.vos.AgencyVO"></jsp:useBean>
		<jsp:useBean id="bs_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="bs_details" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="sfy" scope="request" class="java.lang.String"></jsp:useBean>
		<script type="text/javascript">
			var months = ["JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"];
			var years = ["2018","2019","2020"];
			var bs_data = <%= bs_data.length()>0? bs_data : "[]" %>;
			var bs_details = <%= bs_details.length()>0? bs_details : "[]" %>;
			var bsstatus = ["","NEW","IN-PROGRESS","READY","COMPLETED","UN-PROCESSED"];
			var sfy = <%= sfy %>;
		</script>
		<script src="js/commons/jquery-2.1.4.min.js?<%=randomUUIDString%>"></script>
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
            		<h1>AGENCY BALANCE SHEET</h1>
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
        	<div class="row">
          		<div class="clearfix"></div>
          		<div class="col-md-15">
                	<form id="bs_form" name="bs_form" method="post" action="ReportsDataControlServlet">
						<input type="hidden" id="agencyId" name="agencyId" value="<%= agencyVO.getAgency_code() %>">
						<input type="hidden" id="page" name="page" value="jsp/pages/erp/reports/finreports/depAcct/bs.jsp">
						<input type="hidden" id="actionId" name="actionId" value="8552">
						<input type="hidden" id="recId" name="recId" value="">
						<input type="hidden" id="sfyId" name="sfyId" value="">
						
                		<div class="col-md-3 reprt">
  							<label>FINANCIAL YEAR</label>
							<select id="sfy" name="sfy" class="form-control input_field select_dropdown">
								<option value="00">SELECT</option>
								<option value="2018">2018-19</option>
								<option value="2019">2019-20</option>
								<option value="2020">2020-21</option>								
							</select>
 						</div>
 					</form>
 				 <div class="col-md-2 reprt">
  						<button onclick="fetchBalanceSheet()" id="searchb" name="searchb" class="btn btn-info color_btn" style="margin-top:25px;">FETCH</button>
 					</div> 
              	</div>
            </div>
			<br>
			<div class="row">
	     		<div class="clearfix"></div>
				<div id="bs_display_div" style="display: none;">
					<br><br>
					<div class="col-md-12">
						<b>FINANCIAL YEAR : <span id="fySpan"></span></b>
						<br><br>
						<b>STATUS : <span id="bssSpan"></span>&nbsp;<span id="bsProcessSpan"></span>
							&nbsp;<span id="bsSaveSpan"></span>&nbsp;<span id="bsSubmitSpan"></span>
							&nbsp;<span id="infoSpan"></span></b>
						<br><br>
       				</div>
       			</div>
      		</div>
			<div class="row">
	     		<div class="clearfix"></div>
				<div id="bs_data_div" style="display: none;">
	      			<div class="col-md-12">
	      				<div id="bs_data_table_div">
	       					<div class="main_table">
	         					<div class="table-responsive">
				                	<form id="bs_data_form" name="bs_form" method="post" action="ReportsDataControlServlet">
										<input type="hidden" id="agencyId" name="agencyId" value="<%= agencyVO.getAgency_code() %>">
										<input type="hidden" id="page" name="page" value="jsp/pages/erp/reports/finreports/depAcct/bs.jsp">
										<input type="hidden" id="actionId" name="actionId" value="8552">
										<input type="hidden" id="recId" name="recId" value="">
										<input type="hidden" id="sfyId" name="sfyId" value="">
										
		            					<table class="table" id="bs_data_table">
    	    	      						<thead>
        	    	    						<tr class="title_head">
            		    							<th><font size=1 color="white" face="tahoma"><b>LIABILITIES</b></font></th>
            		    							<th><font size=1 color="white" face="tahoma"></font></th>
													<th><font size=1 color="white" face="tahoma"><b>AMOUNT</b></font></th>
													<th><font size=1 color="white" face="tahoma"></font></th>
													<th><font size=1 color="white" face="tahoma"><b>ASSETS</b></font></th>
													<th><font size=1 color="white" face="tahoma"></font></th>													
													<th><font size=1 color="white" face="tahoma"><b>AMOUNT</b></font></th>
												</tr>
											</thead>
											<tbody id="data_table_body">
												<tr height="30px">
													<td><font size="2px"><b>CAPITAL ACCOUNT</b></font></td>
													<td></td>
													<td></td>
													<td></td>
													<td><font size="2px"><b>FIXED ASSETS</b></font></td>
													<td></td>
													<td></td>
												</tr>
												<tr height="30px">
													<td>OPENING BALANCE</td>
													<td><input type="text" id="caob" name="caob" size="12" class="form-control input_field" readonly="readonly" style="background-color:#F3F3F3;"></td>
													<td></td>
													<td></td>
													<td>OPENING BALANCE</td>
													<td><input type="text" id="faob" name="faob" size="12" class="form-control input_field" readonly="readonly" style="background-color:#F3F3F3;"></td>
													<td></td>
												</tr>
												<tr height="30px">
													<td>NET INFUSION</td>
													<td><input type="text" id="cani" name="cani" size="12" class="form-control input_field" readonly="readonly" style="background-color:#F3F3F3;"></td>
													<td></td>
													<td></td>
													<td>NET ASSET VALUE</td>
													<td><input type="text" id="fanp" name="fanp" size="12" class="form-control input_field" readonly="readonly" style="background-color:#F3F3F3;"></td>
													<td></td>
												</tr>
												<tr height="30px">
													<td>NET WITHDRAWLS</td>
													<td><input type="text" id="canw" name="canw" size="12" class="form-control input_field" readonly="readonly" style="background-color:#F3F3F3;"></td>
													<td></td>
													<td></td>
													<td>NET DEPRECIATION</td>
													<td><input type="text" id="fand" name="fand" size="12" class="form-control input_field" readonly="readonly" style="background-color:#F3F3F3;"></td>
													<td></td>
												</tr>
												<tr height="30px">
													<td>NET PROFIT</td>
													<td><input type="text" id="plnp" name="plnp" size="12" class="form-control input_field" readonly="readonly" style="background-color:#F3F3F3;"></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
												</tr>
												<tr height="30px">
													<td>CLOSING BALANCE</td>
													<td></td>
													<td><input type="text" id="cacb" name="cacb" size="12" class="form-control input_field" readonly="readonly" style="background-color:#FAFAC2;"></td>
													<td></td>
													<td>CLOSING BALANCE</td>
													<td></td>
													<td><input type="text" id="facb" name="facb" size="12" class="form-control input_field" readonly="readonly" style="background-color:#FAFAC2;"></td>
												</tr>
												<tr height="30px">
													<td><font size="2px"><b>CURRENT LIABILITIES</b></font></td>
													<td></td>
													<td></td>
													<td></td>
													<td><font size="2px"><b>CURRENT ASSETS</b></font></td>
													<td></td>
													<td></td>
												</tr>
												<tr height="30px">
													<td>SUNDRY CREDITORS</td>
													<td></td>
													<td><input type="text" id="clsc" name="clsc" class="form-control input_field" size="12"></td>
													<td></td>
													<td>SUNDRY DEBTORS</td>
													<td></td>
													<td><input type="text" id="casd" name="casd" class="form-control input_field" size="12"></td>
												</tr>
												<tr height="30px">
													<td><font size="2px"><b>SECURED LOANS</b></font></td>
													<td></td>
													<td></td>
													<td></td>
													<td>LOANS &amp; ADVANCES</td>
													<td></td>
													<td><input type="text" id="cala" name="cala" size="12" class="form-control input_field"></td>
												</tr>
												<tr height="30px">
													<td>TOTAL LOAN BALANCE</td>
													<td></td>
													<td><input type="text" id="sllb" name="sllb" size="12" class="form-control input_field" readonly="readonly" style="background-color:#F3F3F3;"></td>
													<td></td>
													<td>CASH IN HAND</td>
													<td></td>
													<td><input type="text" id="cach" name="cach" size="12" class="form-control input_field"></td>
												</tr>
												<tr height="30px">
													<td><font size="2px"><b>UNSECURED LOANS</b></font></td>
													<td></td>
													<td></td>
													<td></td>
													<td>DEPOSITS</td>
													<td></td>
													<td><input type="text" id="cadp" name="cadp" size="12" class="form-control input_field"></td>
												</tr>
												<tr height="30px">
													<td>LOANS FROM FRIENDS<br>AND RELATIVES</td>
													<td></td>
													<td><input type="text" id="ulfr" name="ulfr" size="12" class="form-control input_field"></td>
													<td></td>
													<td>RESERVES &amp; SURPLUS</td>
													<td></td>
													<td><input type="text" id="cars" name="cars" size="12" class="form-control input_field"></td>
												</tr>
												<tr height="30px">
													<td>PROVISIONS</td>
													<td></td>
													<td><input type="text" id="prov" name="prov" size="12" class="form-control input_field"></td>
													<td></td>
													<td>CASH AT BANK</td>
													<td></td>
													<td><input type="text" id="cabc" name="cabc" size="12" class="form-control input_field" readonly="readonly" style="background-color:#F3F3F3;"></td>
												</tr>
												<tr height="30px">
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td><font size="2px"><b>CLOSING STOCK VALUE</b></font></td>
													<td></td>
													<td><input type="text" id="cacs" name="cacs" size="12" class="form-control input_field" readonly="readonly" style="background-color:#F3F3F3;"></td>
												</tr>
												<tr><td colspan="7">&nbsp;</td></tr>
												<tr height="30px">
													<td><font size="2px"><b>TOTAL LIABILITIES</b></font></td>
													<td></td>
													<td><input type="text" id="fytl" name="fytl" size="12" class="form-control input_field" readonly="readonly" style="background-color:#FAFAC2;"></td>
													<td></td>
													<td><font size="2px"><b>TOTAL ASSETS</b></font></td>
													<td></td>
													<td><input type="text" id="fyta" name="fyta" size="12" class="form-control input_field" readonly="readonly" style="background-color:#FAFAC2;"></td>
												</tr>
											</tbody>
										</table>
									</form>
			  					</div>
            				</div>
          				</div>
        			</div>
       			</div>
      		</div>
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
	<script type="text/javascript" src="js/modules/reports/finreports/depAcct/bs.js?<%=randomUUIDString%>"></script>
	<script type="text/javascript" src="js/modal.js?<%=randomUUIDString%>"></script>
    <script src="js/commons/main.js?<%=randomUUIDString%>"></script>

  	<script type="text/javascript">
 		document.getElementById("nameSpan").innerHTML = <%= agencyVO.getAgency_code() %>; 
 		document.getElementById('bs_form').agencyId = <%= agencyVO.getAgency_code() %>;

 		$(document).ready(function() {
 			/* tooltip for select */
 			$('select').each(function(){
 				var tooltip = $(this).tooltip({
 		    		selector: 'data-toggle="tooltip"',
 		        	trigger: 'manual'
 		    	});
 		    	var selection = $(this).find('option:selected').text();
 		    	tooltip.attr('title', selection)
 			
 		    	$('select').change(function() {
 		    		var selection = $(this).find('option:selected').text();
 		    	    tooltip.attr('title', selection)
 		    	});
 			});
 		});
 		
	</script>
</html>