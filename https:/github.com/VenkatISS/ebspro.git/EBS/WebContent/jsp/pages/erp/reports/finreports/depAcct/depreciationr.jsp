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

    	<title>MyLPGBooks DEALER WEB APPLICATION - DEPRECIATION REPORT</title>
		<jsp:useBean id="agencyVO" scope="session" class="com.it.erpapp.framework.model.vos.AgencyVO"></jsp:useBean>
		<jsp:useBean id="depr_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="depr_details_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="sfy" scope="request" class="java.lang.String"></jsp:useBean>
		<script type="text/javascript">
			var months = ["JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"];
			var years = ["2018","2019","2020"];
			var depr_data = <%= depr_data.length()>0? depr_data : "[]" %>;
			var depr_details_data = <%= depr_details_data.length()>0? depr_details_data : "[]" %>;
			var drstatus = ["","NEW","IN-PROGRESS","READY","COMPLETED"];
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
            		<h1>AGENCY DEPRECIATION REPORT </h1>
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
                	<form id="dr_form" name="" method="post" action="ReportsDataControlServlet">
						<input type="hidden" id="agencyId" name="agencyId" value="<%= agencyVO.getAgency_code() %>">
						<input type="hidden" id="page" name="page" value="jsp/pages/erp/reports/finreports/depAcct/depreciationr.jsp">
						<input type="hidden" id="actionId" name="actionId" value="5567">
						<input type="hidden" id="recId" name="recId" value="">
						<input type="hidden" id="sfyId" name="sfyId" value="">
						<input type="hidden" id="statusId" name="statusId" value="">
						
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
  						<button onclick="fetchDepreciationReport()" id="searchb" name="searchb" class="btn btn-info color_btn" style="margin-top:20px;">FETCH</button>
 					</div>
              	</div>
            </div>
			<br><br><br>
			<div class="row">
	     		<div class="clearfix"></div>
				<div id="dr_data_div" style="display: none;">
	      			<div class="col-md-12">
	      				<div id="dr_data_table">
	       					<div class="main_table">
	         					<div class="table-responsive">
	            					<table class="table table-striped" id="dr_data_table">
        	      						<thead>
            	    						<tr class="title_head">
            	    							<th><font size=1 color="white" face="tahoma"><b>FINANCIAL YEAR</b></font></th>
												<th><font size=1 color="white" face="tahoma"><b>CREATED ON</b></font></th>
												<th><font size=1 color="white" face="tahoma"><b>LAST UPDATED ON</b></font></th>
												<th><font size=1 color="white" face="tahoma"><b>OPENING BALANCE</b></font></th>
												<th><font size=1 color="white" face="tahoma"><b>NET ASSET VALUE</b></font></th>
												<th><font size=1 color="white" face="tahoma"><b>DEPRECIATION AMOUNT</b></font></th>
												<th><font size=1 color="white" face="tahoma"><b>CLOSING BALANCE</b></font></th>
												<th><font size=1 color="white" face="tahoma"><b>STATUS</b></font></th>
												<th><font size=1 color="white" face="tahoma"><b>ACTIONS</b></font></th>
											</tr>
										</thead>
										<tbody id="dr_data_table_body"></tbody>
									</table>
			  					</div>
            				</div>
          				</div>
        			</div>
       			</div>
      		</div>
			<br><br>
			<div class="row">
	     		<div class="clearfix"></div>
				<div id="dr_details_div" style="display: none;">
	      			<div class="col-md-12">
	      				<div id="dr_details_table">
	       					<div class="main_table">
	         					<div class="table-responsive">
	            					<table class="table" id="dr_details_table">
        	      						<thead>
            	    						<tr class="title_head">     
            	    							<th><font size=1 color="white" face="tahoma"><b>DATE</b></font></th>
												<th><font size=1 color="white" face="tahoma"><b>ITEM</b></font></th>
												<th><font size=1 color="white" face="tahoma"><b>DR</b></font></th>
												<th><font size=1 color="white" face="tahoma"><b>CR</b></font></th>
												<th><font size=1 color="white" face="tahoma"><b>BALANCE</b></font></th>
											</tr>
										</thead>
										<tbody id="dr_details_table_body">
											<tr>
												<td></td>
												<td><b>FIXED ASSETS</b></td>
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
	<script type="text/javascript" src="js/modules/reports/finreports/depAcct/depreciationr.js?<%=randomUUIDString%>"></script>
	<script type="text/javascript" src="js/modal.js?<%=randomUUIDString%>"></script>
    <script src="js/commons/main.js?<%=randomUUIDString%>"></script>

  	<script type="text/javascript">
 		document.getElementById("nameSpan").innerHTML = <%= agencyVO.getAgency_code() %>; 
 		document.getElementById('dr_form').agencyId = <%= agencyVO.getAgency_code() %>;
 		
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