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
    <title>MyLPGBooks DEALER WEB APPLICATION - VEHICLES MASTER PAGE</title>
    <jsp:useBean id="agencyVO" scope="session" class="com.it.erpapp.framework.model.vos.AgencyVO"></jsp:useBean>
	<jsp:useBean id="fleet_data" scope="request" class="java.lang.String"></jsp:useBean>
    <script src="js/commons/jquery-2.1.4.min.js?<%=randomUUIDString%>"></script>
<%-- 	<script src="js/commons/checkBrowser.js?<%=randomUUIDString%>"></script>     --%>

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
<!-- 		<div id = "dialog-1" title="Alert(s)"></div>
 		<div id="dialog-confirm"><div id="myDialogText" style="color:black;"></div></div> -->
        <div class="page-title">
        	<div>
            	<h1>VEHICLES MASTER</h1>
          	</div>
					<ul id="nav" class="nav nav-pills clearfix right" role="tablist">
          				<li class="dropdown"><span class="dropdown-toggle  btn-info" data-toggle="dropdown" id="ahelp">Help</span>
							<ul id="products-menu" class="dropdown-menu clearfix" role="menu">
								<li><a style="font-size: 14px;" title="vehicle-master"href="https://www.youtube.com/watch?v=ny77GwYNFiw" target="_blank">English</a></li>
								<li><a style="font-size: 14px;" href="https://youtu.be/HuE5B-4-G9Q" target="_blank">Hindi</a></li>
							</ul>
						</li>
					</ul>
        </div>
        
		<div class="row">
        	<div class="clearfix"></div>
          	<div class="col-md-12" id="myDIV" style="display:none;">
            			<div class="main_table">
              				<div class="table-responsive">
                				<form id="fleet_data_form" name="" method="post" action="MasterDataControlServlet">
									<input type="hidden" id="agencyId" name="agencyId" value="<%= agencyVO.getAgency_code() %>">
									<input type="hidden" id="page" name="page" value="jsp/pages/erp/masterdata/fleet_data.jsp">
									<input type="hidden" id="actionId" name="actionId" value="3532">
									<input type="hidden" id="dataId" name="dataId" value="">
                					<table class="table" id="fleet_add_table">
                  						<thead>
                    						<tr class="title_head">
                      							<th width="10%" class="text-center">VEHICLE NUMBER</th>
                      							<th width="10%" class="text-center">VEHICLE MAKE</th>
                      							<th width="10%" class="text-center">VEHICLE TYPE</th>
                      							<th width="10%" class="text-center">VEHICLE USAGE</th>
                      							<th width="10%" class="text-center">ACTIONS</th>
                    						</tr>
                  						</thead>
                  						<tbody id="fleet_add_table_body" >
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
                       						<th width="10%" class="text-center sml_input">VEHICLE NUMBER</th>
                      						<th width="10%" class="text-center sml_input">VEHICLE MAKE</th>
                      						<th width="10%" class="text-center sml_input">VEHICLE TYPE</th>
                      						<th width="10%" class="text-center sml_input">VEHICLE USAGE</th>
	 					                    <th width="10%" class="text-center sml_input">Actions</th>
                    					</tr>
                  					</thead>
                  					<tbody id="fleet_data_table_body">
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
    <script type="text/javascript">
    var fleet_data =  <%= fleet_data.length()>0? fleet_data : "[]" %>;
    var vehicleType = ["TWO WHEELER","THREE WHEELER","FOUR WHEELER","TRUCK","OTHERS"];
    var vechicleUsage = ["INWARD DELIVERY","OUTWARD DELIVERY","CONVEYANCE","OTHERS"];
	</script>
	<script type="text/javascript" src="js/commons/general_validations.js?<%=randomUUIDString%>"></script>
	<script type="text/javascript" src="js/modules/masterdata/fleet_data.js?<%=randomUUIDString%>"></script>
	<script src="js/commons/bootstrap.min.js?<%=randomUUIDString%>"></script>
	<script src="js/commons/plugins/pace.min.js?<%=randomUUIDString%>"></script>
    <script src="js/commons/main.js?<%=randomUUIDString%>"></script>

    <script  type="text/javascript">
	    document.getElementById("nameSpan").innerHTML = <%= agencyVO.getAgency_code() %>
	    document.getElementById('fleet_data_form').agencyId = <%= agencyVO.getAgency_code() %>;
	    
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
  </body>
</html>
