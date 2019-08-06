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
    <title>LPG DEALER ERP WEB APPLICATION - SERVICES MASTER</title>
	<jsp:useBean id="agencyVO" scope="session" class="com.it.erpapp.framework.model.vos.AgencyVO"></jsp:useBean>
	<jsp:useBean id="service_types_list" scope="request" class="java.lang.String"></jsp:useBean>
	<jsp:useBean id="services_data" scope="request" class="java.lang.String"></jsp:useBean>
	<jsp:useBean id="services_gst" scope="request" class="java.lang.String"></jsp:useBean>
	
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
		var cat_types_data = <%= service_types_list %>;
		var ctdatahtml = "";
		var page_data =  <%= services_data.length()>0? services_data : "[]" %>;
		var services_gst =  <%= services_gst.length()>0? services_gst : "[]" %>;
    	var gstappPer = ["0%","25%","50%","75%","100%"];
	</script>
  	</head>
  	<body class="sidebar-mini fixed">
    	<div class="wrapper">
    		<!-- Header-->
			<jsp:include page="/jsp/pages/commons/header.jsp"/>
  			<!--header End--->
			
			<div class="content-wrapper">
        		<div class="page-title">
          			<!-- <div>
            			<h1>SERVICES MASTER </h1>
          			</div> -->
          			
          			<div>

	          			<!--  ----------------------- new requirement 25042019 --------------------------------- -->
						<lable style="font-weight:400;font-size:18px;padding-right:100px;">	SERVICES MASTER </lable>
						<lable style="font-weight:100;font-size:12px;padding-right:150px;">	CURRENT GST APPLICABLE :<b><span  id="gstpapplicable" style="padding-left:10px;"></span></b></lable>				
							
            			<form method="Post" action="PPMasterDataControlServlet">
            				<input type="hidden" id="agencyId" name="agencyId" value="<%= agencyVO.getAgency_code() %>">
							<input type="hidden" id="page" name="page" value="jsp/pages/erp/masterdata/products/services_data.jsp">
							<input type="hidden" id="actionId" name="actionId" value="3124">
									
							<span style="font-weight:100;font-size:12px;padding-right:130px;float:right;margin-right: -350px;margin-top:-20px" >	<!-- <label style="font-weight:100;font-size:12px;padding-right:130px;float:right;margin-right: -250px;margin-top:-20px" > -->GST APPLICABLE PER :
								<b>	<select  name='gstp' id='gstp' style="margin-left: 8px;">
										<option value="0">100%</option>
						 				<option value="1">75%</option>
						   				<option value="2">50%</option>	
						   				<option value="3">25%</option>
						   				<option value="4">0%</option>						   						
									</select>
									<!-- 	</label> -->
									<input type="submit"   name="gstappper_submit_btn" id="gstappper_submit_btn" value="SUBMIT"  style="margin-left: 8px;">
								</b>
							</span>	
						</form>
						<!-- ------------------------ new requirement end --------------------------------- -->
						
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
          			<div class="col-md-12">
           				<div class="main_table">
							<div class="table2-responsive">
              					<form id="page_data_form" name="" method="post" action="PPMasterDataControlServlet">
									<input type="hidden" id="agencyId" name="agencyId" value="<%= agencyVO.getAgency_code() %>">
									<input type="hidden" id="page" name="page" value="jsp/pages/erp/masterdata/products/services_data.jsp">
									<input type="hidden" id="actionId" name="actionId" value="3122">
									<input type="hidden" id="itemId" name="itemId" value="">
                  					<table class="table table-striped">
                	  					<thead>
                	    					<tr class="title_head">
                	      						<th width="30%" class="text-center sml_input">SERVICE NAME</th>
                	      						<th width="20%" class="text-center sml_input">SAC CODE</th>
                	      						<th width="20%" class="text-center sml_input">UOM</th>
               		      						<th width="18%" class="text-center sml_input">CHARGES</th>
												<th width="15%" class="text-center sml_input">GST AMOUNT<!-- <br>(18% GST) --></th>
               	       	  						<th width="40%" class="text-center sml_input">EFFECTIVE DATE</th>
                    						</tr>
                  						</thead>
                  						<tbody id="page_data_table_body"></tbody>
                					</table>
								</form>
              				</div>
            			</div>
						<button class="btn btn-info color_btn bg_color2" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/home.jsp','2001')">BACK</button>	
          			</div>
        		</div>
      		</div>
    	</div>
        <script src="js/commons/bootstrap.min.js?<%=randomUUIDString%>"></script>
		<script src="js/commons/plugins/pace.min.js?<%=randomUUIDString%>"></script>
        <script src="js/commons/main.js?<%=randomUUIDString%>"></script>
    	<script>
			document.getElementById("nameSpan").innerHTML = <%= agencyVO.getAgency_code() %>
			document.getElementById('page_data_form').agencyId = <%= agencyVO.getAgency_code() %>;
			document.getElementById("gstpapplicable").innerHTML =((services_gst[0].gst_percent_applicable)*100)+'%';
			//Construct Category Type html
			ctdatahtml = "<OPTION VALUE='0'>SELECT</OPTION>";
			if(cat_types_data.length>0) {
				for(var z=0; z<cat_types_data.length; z++){
					ctdatahtml=ctdatahtml+"<OPTION VALUE='"+cat_types_data[z].id+"'>"+cat_types_data[z].cat_desc+"</OPTION>";
				}
			}

			var tbody = document.getElementById('page_data_table_body');
			for(var f=0; f<page_data.length; f++) {
				 var tblRow = tbody.insertRow(-1);
				 tblRow.style.height="20px";
				 tblRow.align="left";
	 			var spd = fetchProductName(cat_types_data, page_data[f].prod_code);
	 			var ed = new Date(page_data[f].effective_date);
	 			tblRow.innerHTML = "<tr>"+
    				"<td>"+ spd +"</td>"+
     				"<td>"+ page_data[f].sac_code +"</td>"+
     				"<td>"+ "NOS"+"</td>"+
     				"<td>"+ page_data[f].prod_charges +"</td>"+
     				"<td>"+page_data[f].gst_amt +"</td>"+
     				"<td>"+ ed.getDate()+"-"+months[ed.getMonth()]+"-"+ed.getFullYear() +"</td>"+
   	 				"</tr>";
			};
			
/* 			$(document).ready(function() {
				//tooltip for select
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
		       	    
		       } ); */
		</script>
 	</body>
</html>