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
    <title>LPG DEALER ERP WEB APPLICATION - EQUIPMENT MASTER PAGE</title>
	<jsp:useBean id="agencyVO" scope="session" class="com.it.erpapp.framework.model.vos.AgencyVO"></jsp:useBean>
	<jsp:useBean id="cylinder_types_list" scope="request" class="java.lang.String"></jsp:useBean>
	<jsp:useBean id="equipment_data" scope="request" class="java.lang.String"></jsp:useBean>
	<jsp:useBean id="dequipment_data" scope="request" class="java.lang.String"></jsp:useBean>
	<jsp:useBean id="astock_data" scope="request" class="java.lang.String"></jsp:useBean>
	<script src="js/commons/jquery-2.1.4.min.js?<%=randomUUIDString%>"></script>
<%-- 	<script src="js/commons/checkBrowser.js?<%=randomUUIDString%>"></script> --%>

	<!-- Sidenav -->
	<jsp:include page="/jsp/pages/commons/sidenav.jsp"/>    
	<!---END Sidenav--->
		
	<script type="text/javascript">
		window.onload = setWidthHightNav("100%");
	</script>
	<script type="text/javascript">
		var months = ["JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"];
		var cat_types_data = <%= cylinder_types_list.length()>0? cylinder_types_list : "[]" %>;
		var ctdatahtml = "";
		var page_data =  <%= equipment_data.length()>0? equipment_data : "[]" %>;
		var del_equipment_data = <%= dequipment_data.length()>0? dequipment_data : "[]" %>;
		var agency_stock_data = <%= astock_data.length()>0? astock_data : "[]" %>;
		var eus = ["NONE","NOS","KGS"];
	</script>
  </head>
  <body class="sidebar-mini fixed">
    	<div class="wrapper">
		<!-- Header-->
		<jsp:include page="/jsp/pages/commons/header.jsp"/>      
	  	<!--header End--->
      		<div class="content-wrapper">
<!--       		<div id = "dialog-1" title="Alert(s)"></div>
 			<div id="dialog-confirm"><div id="myDialogText" style="color:black;"></div></div> -->
        		<div class="page-title">
          			<div>
            			<h1>EQUIPMENT MASTER</h1>
          			</div>
					<ul id="nav" class="nav nav-pills clearfix right" role="tablist">
          				<li class="dropdown"><span class="dropdown-toggle  btn-info" data-toggle="dropdown" id="ahelp">Help</span>
							<ul id="products-menu" class="dropdown-menu clearfix" role="menu">
								<li><a style="font-size: 14px;" title="equipment-master" href="https://www.youtube.com/watch?v=ekVmPL7P_vY" target="_blank">English</a></li>
								<li><a style="font-size: 14px;" href="https://youtu.be/_iTv42U-_sE" target="_blank">Hindi</a></li>
							</ul>
						</li>
					</ul>
        		</div>
        		<div class="row">
          			<div class="clearfix"></div>
          			<div class="col-md-12" id="page_add_table_div" style="display:none;">
            			<div class="main_table">
              				<div class="table-responsive">
              					<form id="page_add_form" name="page_add_form" method="post" action="PPMasterDataControlServlet">
									<input type="hidden" id="agencyId" name="agencyId" value="<%= agencyVO.getAgency_code() %>">
									<input type="hidden" id="page" name="page" value="jsp/pages/erp/masterdata/products/equipment_data.jsp">
									<input type="hidden" id="actionId" name="actionId" value="3102">
									<input type="hidden" id="itemId" name="itemId" value="">
                					<table class="table" id="page_add_table">
                  						<thead>
                    						<tr class="title_head">
                      							<th width="10%" class="text-center"> PRODUCT</th>
                      							<th width="10%" class="text-center">Units</th>
                      							<th width="10%" class="text-center">Gst %</th>
                      							<th width="10%" class="text-center">Hsn Code</th>
                      							<th width="10%" class="text-center">SECURITY DEPOSIT</th>
                      							<th width="10%" class="text-center">FULLS</th>
					  							<th width="10%" class="text-center">EMPTIES</th>
					  							<th width="10%" class="text-center">EFFECTIVE DATE</th>
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
				<span id="savediv" style="display:none;"><button class="btn btn-info color_btn bg_color2" name="save_data" id="save_data" onclick="saveData(this)">SAVE</button></span>
				<button name="cancel_data" id="cancel_data"	class="btn btn-info color_btn bg_color2" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/home.jsp','2001')">BACK</button>
				<div class="row">
          			<div class="clearfix"></div>
          			<div class="col-md-12">
            			<div class="main_table">
              				<div class="table-responsive">
                				<table class="table  table-striped">
                  					<thead>
                    					<tr class="title_head">
                      						<th width="30%" class="text-center sml_input">PRODUCT</th>
                      						<th width="20%" class="text-center sml_input">UNITS</th>
                      						<th width="30%" class="text-center sml_input">GST %</th>
                      						<th width="30%" class="text-center sml_input">HSN CODE</th>
                      						<th width="30%" class="text-center sml_input">SECURITY DEPOSIT</th>
					  						<th width="30%" class="text-center sml_input">FULLS</th>
					  						<th width="30%" class="text-center sml_input">EMPTIES</th>
					  						<th width="30%" class="text-center sml_input">EFFECTIVE DATE</th>
					  						<th width="30%" class="text-center sml_input">Actions</th>
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
	<script type="text/javascript" src="js/modules/masterdata/products/equipment_data.js?<%=randomUUIDString%>"></script>
	<script src="js/commons/bootstrap.min.js?<%=randomUUIDString%>"></script>
	<script src="js/commons/plugins/pace.min.js?<%=randomUUIDString%>"></script>
	<script src="js/commons/main.js?<%=randomUUIDString%>"></script>

	<script type="text/javascript">
		document.getElementById("nameSpan").innerHTML = <%= agencyVO.getAgency_code() %>
		document.getElementById('page_add_form').agencyId = <%= agencyVO.getAgency_code() %>;
		var dedate = <%=agencyVO.getDayend_date()%>;
		
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
	       	    
	    } );
	</script>		
</html>