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
    <!-- CSS-->
    <link rel="stylesheet" type="text/css" href="css/main.css?<%=randomUUIDString%>">
    <title>MyLPGBooks DEALER  WEB APPLICATION - PARTNERS TRANSACTION DATA PAGE</title>
	<script src="js/commons/jquery-2.1.4.min.js?<%=randomUUIDString%>"></script>
    <jsp:useBean id="agencyVO" scope="session" class="com.it.erpapp.framework.model.vos.AgencyVO"></jsp:useBean>
	<jsp:useBean id="partners_data" scope="request" class="java.lang.String"></jsp:useBean>
	<jsp:useBean id="pt_data" scope="request" class="java.lang.String"></jsp:useBean>
	<jsp:useBean id="bank_data" scope="request" class="java.lang.String"></jsp:useBean>
	<script type="text/javascript" src="js/local.js?<%=randomUUIDString%>"></script>
<%-- 	<script type="text/javascript" src="js/commons/sidenav.js?<%=randomUUIDString%>"></script> --%>
	<script type="text/javascript">
		var partners_data =  <%= partners_data.length()>0? partners_data : "[]" %>;
		var bank_data =  <%= bank_data.length()>0? bank_data : "[]" %>;
		var page_data =  <%= pt_data.length()>0? pt_data : "[]" %>;
		var pttype = ["","INFUSION","WITHDRAWAL","WRITE-OFF"];
		var bankdatahtml = "";
		var partnerdatahtml = "";
		var months = ["JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"];
		
		//Construct bank html
		bankdatahtml = "<OPTION VALUE='0'>SELECT</OPTION>";
		if(bank_data.length>0) {
			for(var z=0; z<bank_data.length; z++){
				var bc = bank_data[z].bank_code;
				if(!(bc=="TAR ACCOUNT" || bc=="STAR ACCOUNT")) {
					bankdatahtml=bankdatahtml+"<OPTION VALUE='"+bank_data[z].id+"'>"+bank_data[z].bank_code+" - "+bank_data[z].bank_acc_no+"</OPTION>";
				}
			}
		}

		//Construct partner html
		partnerdatahtml = "<OPTION VALUE='0'>SELECT</OPTION>";
		if(partners_data.length>0) {
			for(var z=0; z<partners_data.length; z++){
				if(!(partners_data[z].status==2)) {
					partnerdatahtml=partnerdatahtml+"<OPTION VALUE='"+partners_data[z].id+"'>"+partners_data[z].partner_name+"</OPTION>";
				}
			}
		}		
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
            			<h1>PARTNERS TRANSACTIONS</h1>
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
          			<div class="col-md-12" id="myDIV" style="display:none;">
            			<div class="main_table">
              				<div class="table-responsive">
              					<form id="partners_data_form" name="" method="post" action="MasterDataControlServlet">
									<input type="hidden" id="agencyId" name="agencyId" value="<%= agencyVO.getAgency_code() %>">
									<input type="hidden" id="page" name="page" value="jsp/pages/erp/reports/finreports/capitalAcct/partners_trans.jsp">
									<input type="hidden" id="actionId" name="actionId" value="8512">
									<input type="hidden" id="itemId" name="itemId" value="">
									<input type="hidden" id="Ppship" name="Ppship" value="<%= agencyVO.getPpship() %>">
               						<table class="table" id="partners_add_table">
                  						<thead>
                    						<tr class="title_head">
                      							<th width="10%" class="text-center sml_input">TRANSACTION DATE</th>
                      							<th width="10%" class="text-center sml_input">PARTNER NAME</th>
                      							<th width="1%" class="text-center sml_input">TRANSACTION TYPE</th>
                      							<th width="10%" class="text-center sml_input">TRANSACTION AMOUNT</th>
					  							<th width="10%" class="text-center sml_input">BANK</th>
					  							<th width="10%" class="text-center sml_input">NARRATION</th>
								                <th width="10%" class="text-center sml_input">ACTIONS</th>
                    						</tr>
                  						</thead>
                  						<tbody id="partners_add_table_body">
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
                				<table class="table  table-striped">
                  					<thead>
                    					<tr class="title_head">
                      						<th width="10%" class="text-center sml_input">TRANSACTION DATE</th>
                      						<th width="10%" class="text-center sml_input">PARTNER NAME</th>
                      						<th width="10%" class="text-center sml_input">TRANSACTION TYPE</th>
                      						<th width="10%" class="text-center sml_input">TRANSACTION AMOUNT</th>
					  						<th width="10%" class="text-center sml_input">BANK</th>
					  						<th width="10%" class="text-center sml_input">NARRATION</th>
					   						<th width="10%" class="text-center sml_input">ACTIONS</th>
                    					</tr>
                  					</thead>
                  					<tbody id="partners_data_table_body">  
                  					</tbody>
                				</table>
              				</div>
            			</div>
		         	</div>
        		</div>
      		</div>
      		<div id = "dialog-1" title="Alert(s)"></div>
 			<div id="dialog-confirm"><div id="myDialogText" style="color:black;"></div></div>
    	</div>
	<script type="text/javascript" src="js/commons/general_validations.js?<%=randomUUIDString%>"></script>
	<script type="text/javascript" src="js/modules/reports/finreports/capitalAcct/partners_trans.js?<%=randomUUIDString%>"></script>
	<script src="js/commons/bootstrap.min.js?<%=randomUUIDString%>"></script>
	<script src="js/commons/plugins/pace.min.js?<%=randomUUIDString%>"></script>
    <script src="js/commons/main.js?<%=randomUUIDString%>"></script>
	<script type="text/javascript">
	document.getElementById("nameSpan").innerHTML = <%= agencyVO.getAgency_code() %>
	document.getElementById('partners_data_form').agencyId = <%= agencyVO.getAgency_code() %>;
	
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
  </body>
</html>