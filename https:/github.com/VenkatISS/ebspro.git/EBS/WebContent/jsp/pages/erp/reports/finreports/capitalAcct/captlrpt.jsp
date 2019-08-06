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
    	<title>CAPITAL REPORT</title>
		<script src="js/commons/jquery-2.1.4.min.js?<%=randomUUIDString%>"></script>
    	<jsp:useBean id="agencyVO" scope="session" class="com.it.erpapp.framework.model.vos.AgencyVO"></jsp:useBean>
		<jsp:useBean id="capar_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="sfy" scope="request" class="java.lang.String"></jsp:useBean>
		<script src="https://cdn.jsdelivr.net/alasql/0.3/alasql.min.js"></script>
		<script type="text/javascript">	
			var months = ["JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"];
			var years = ["2018","2019","2020"];
			var capar_data = <%= capar_data.length()>0? capar_data : "[]" %>;
			var drstatus = ["","NEW","IN-PROGRESS","READY","COMPLETED"];
			var sfy = <%= sfy.length()>0 ? sfy : "[]" %>;
		</script>
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
            			<h1>CAPITAL REPORT  </h1>
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
          			<div class="clearfix">
          			<div class="col-md-8" > 
              			<form id="cr_form" name="cr_form" method="post" action="ReportsDataControlServlet">
							<input type="hidden" id="agencyId" name="agencyId" value="<%= agencyVO.getAgency_code() %>">
							<input type="hidden" id="page" name="page" value="jsp/pages/erp/reports/finreports/capitalAcct/captlrpt.jsp">
							<input type="hidden" id="actionId" name="actionId" value="8532">
							<input type="hidden" id="recId" name="recId" value="">
							<input type="hidden" id="sfyId" name="sfyId" value="">
							<input type="hidden" id="statusId" name="statusId" value="">
 							<div class="col-md-2 reprt" style="width:30%">
  								<label>FINANCIAL YEAR </label>
  								<select class="form-control input_field select_dropdown"   id="sfy" name="sfy">
						  			<option value = "00">Select</option>
						  			<option value = "2018">2018-19</option>
						  			<option value = "2019">2019-20</option>
						  			<option value = "2020">2020-21</option>						  			
								</select>
 							</div>
 						</form>
 						
 						<button onclick="fetchCapitalAccountReport()" id="searchb" name="searchb" value="FETCH"  style="margin-top:25px" class="btn btn-info color_btn" style="margin-top:20px;">SEARCH</button>
 						</div>
                	</div>
                	<br> 
                	<div class="row">
	     			<div class="clearfix"></div>             		
                	<div class="col-md-12"  style = "padding-left : 13px; padding-right : 13px;" >
                		<div id="data_div" style="display: none;">
                		<br>
                			<%-- <p><span id="fdate" >YEAR:</span>&nbsp&nbsp<%  out.println( request.getAttribute("year")); %><p> --%>
								<table id="report_table" class="table table-striped">
									<thead>
										<tr class="title_head" height="30px">
											<th align="center"><font color="white" face="tahoma">FINANCIAL YEAR</font></th>
											<th align="center"><font color="white" face="tahoma">CREATED ON</font></th>
											<th align="center"><font color="white" face="tahoma">LAST UPDATED ON</font></th>
											<th align="center"><font color="white" face="tahoma">OPENING BALANCE</font></th>
											<th align="center"><font color="white" face="tahoma">NET INFUSION</font></th>
											<th align="center"><font color="white" face="tahoma">NET WITHDRAWAL / WRITE-OFF</font></th>
											<th align="center"><font color="white" face="tahoma">CLOSING BALANCE</font></th>
											<th align="center"><font color="white" face="tahoma">STATUS</font></th>
											<th align="center"><font color="white" face="tahoma">ACTIONS</font></th>
										</tr>
									</thead>
										<tbody id="data_table_body"></tbody>
									</table>
								</div>
							</div>
						</div>
							<br><br>
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
	<script type="text/javascript" src="js/modules/reports/finreports/capitalAcct/captrpt.js?<%=randomUUIDString%>"></script>
    <script src="js/commons/main.js?<%=randomUUIDString%>"></script> 
    <script type="text/javascript">
    document.getElementById("nameSpan").innerHTML = <%= agencyVO.getAgency_code() %>;
    document.getElementById('cr_form').agencyId = <%= agencyVO.getAgency_code() %>;
    
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