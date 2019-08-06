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
    <title>MyLPGBooks DEALER WEB APPLICATION - ASSETS MANAGEMENT PAGE</title>
	<jsp:useBean id="agencyVO" scope="session" class="com.it.erpapp.framework.model.vos.AgencyVO"></jsp:useBean>
	<jsp:useBean id="bank_data" scope="request" class="java.lang.String"></jsp:useBean>
	<jsp:useBean id="staff_data" scope="request" class="java.lang.String"></jsp:useBean>
	<jsp:useBean id="am_data" scope="request" class="java.lang.String"></jsp:useBean>
	<script type="text/javascript">
		var page_data = <%= am_data.length()>0? am_data : "[]" %>;
		var staff_data = <%= staff_data.length()>0? staff_data : "[]" %>;
		var bank_data = <%= bank_data.length()>0? bank_data : "[]" %>;
		var ctdatahtml = "";
		var vendordatahtml = "";
		var staffdatahtml = "";
		var months = ["JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"];
		var tts = ["","PURCHASE","SALE","TRANSFER"];
		var amahs =["","LAND","BUILDING","PLANT AND MACHINARY","FURNITURE AND FIXTURES","MOTAR VEHICLES","COMPUTERS AND PRINTERS","OTHER MOVABLE ASSETS"];
		var mops = ["NONE","CASH","CHEQUE","ONLINE TRANSFER"];
	</script>
	<script src="js/commons/jquery-2.1.4.min.js?<%=randomUUIDString%>"></script>
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
            			<h1>AGENCY ASSETS</h1>
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
          			<div class="col-md-12" id="page_add_table_div" style="display:none;">
            			<div class="main_table">
              				<div class="table-responsive">
              					<form id="page_add_form" name="page_add_form" method="post" action="MasterDataControlServlet">
									<input type="hidden" id="agencyId" name="agencyId" value="<%= agencyVO.getAgency_code() %>">
									<input type="hidden" id="page" name="page" value="jsp/pages/erp/reports/finreports/depAcct/assets_management.jsp">
									<input type="hidden" id="actionId" name="actionId" value="5562">
									<input type="hidden" id="dataId" name="dataId" value="">
                					<table class="table" id="page_add_table">
                  						<thead>
                    						<tr class="title_head">
                      							<th width="10%" class="text-center">TRANSACTION DATE</th>
                      							<th width="10%" class="text-center">TRANSACTION TYPE</th>
                      							<th width="10%" class="text-center">ITEM DESC</th>
                      							<th width="10%" class="text-center">ACCOUNT HEAD</th>
                      							<th width="10%" class="text-center">VALUE</th>
                      							<th width="10%" class="text-center">MODE OF PAYMENT</th>
					  							<th width="10%" class="text-center">BANK</th>
					  							<th width="10%" class="text-center">STAFF</th>
					  							<th width="10%" class="text-center">ACTIONS</th>
                    						</tr>
                  						</thead>
                  						<tbody id="page_add_table_body">
                   						</tbody>
                					</table>
                				</form>
              				</div>
            			</div>
						<div class="clearfix"></div>
          			</div>
        		</div>
				<button name="add_data" id="add_data"  class="btn btn-info color_btn" onclick="addRow()">ADD</button>
				<span id="savediv" style="display:none;"><button class="btn btn-info color_btn bg_color2" name="save_data" id="save_data" onclick="saveData()">SAVE</button></span>
				<button name="cancel_data" id="cancel_data"	class="btn btn-info color_btn bg_color2" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/home.jsp','2001')">BACK</button>
				<div class="row">
          			<div class="clearfix"></div>
          			<div class="col-md-12">
            			<div class="main_table">
              				<div class="table-responsive">
                				<table class="table table-striped">
                  					<thead>
                    					<tr class="title_head">
                      						<th width="30%" class="text-center sml_input">TRANSACTION DATE</th>
                      						<th width="20%" class="text-center sml_input">TRANSACTION TYPE</th>
                      						<th width="30%" class="text-center sml_input">ITEM DESC</th>
                      						<th width="30%" class="text-center sml_input">ACCOUNT HEAD</th>
                      						<th width="30%" class="text-center sml_input">VALUE</th>
					  						<th width="30%" class="text-center sml_input">MODE OF PAYMENT</th>
					  						<th width="30%" class="text-center sml_input">BANK</th>
					  						<th width="30%" class="text-center sml_input">STAFF</th>
					  						<th width="30%" class="text-center sml_input">ACTIONS</th>
                    					</tr>
                  					</thead>
                  					<tbody id="page_data_table_body">                    
                  					</tbody>
                				</table>
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
	<script type="text/javascript" src="js/modules/reports/finreports/depAcct/assets_management.js?<%=randomUUIDString%>"></script>
	<script src="js/commons/bootstrap.min.js?<%=randomUUIDString%>"></script>
	<script src="js/commons/plugins/pace.min.js?<%=randomUUIDString%>"></script>
	<script src="js/commons/main.js?<%=randomUUIDString%>"></script>

	<script type="text/javascript">
		document.getElementById("nameSpan").innerHTML = <%= agencyVO.getAgency_code() %>;
		document.getElementById('page_add_form').agencyId = <%= agencyVO.getAgency_code() %>;
     	var dedate = <%=agencyVO.getDayend_date()%>;
     	var effdate = <%=agencyVO.getEffective_date()%>;
		var a_created_date = <%=agencyVO.getCreated_date()%>;
		
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