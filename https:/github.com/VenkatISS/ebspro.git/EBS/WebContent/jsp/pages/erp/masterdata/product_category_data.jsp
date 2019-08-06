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
    <title>MyLPGBooks DEALER WEB APPLICATION - PRODUCT CATEGORY MASTER PAGE</title>
    
    <!-- CSS-->
    <link rel="stylesheet" type="text/css" href="css/main.css?<%=randomUUIDString%>">
	
	<jsp:useBean id="agencyVO" scope="session" class="com.it.erpapp.framework.model.vos.AgencyVO"></jsp:useBean>
	<jsp:useBean id="prod_cat_data" scope="session" class="java.lang.String"></jsp:useBean>
	<script type="text/javascript">
		prodCatData = <%= prod_cat_data %>;
		var prod_cat_types = ["NONE","EQUIPMENT","EQUIPMENT","EQUIPMENT","ARB","SERVICE"]
	</script>
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
        <div class="page-title">
          <div>
            <h1>PRODUCT CATEGORY MASTER</h1>
          </div>
					<ul id="nav" class="nav nav-pills clearfix right" role="tablist">
          				<li class="dropdown"><span class="dropdown-toggle  btn-info" data-toggle="dropdown" id="ahelp">Help</span>
							<ul id="products-menu" class="dropdown-menu clearfix" role="menu">
								<li><a style="font-size: 14px;" href="https://youtu.be/bgQnKRrz2X8" target="_blank">English</a></li>
								<li><a style="font-size: 14px;" href="https://youtu.be/bgQnKRrz2X8" target="_blank">Hindi</a></li>
							</ul>
						</li>
					</ul>
        </div>		
        <div class="row">
          <div class="clearfix"></div>
          <div class="col-md-10">
            <div class="main_table">
              <div class="table2-responsive">
                <table class="table table-striped">
                  <thead>
                    <tr class="title_head">
                      <th width="25%" style="text-align:center;">CATEGORY NAME</th>
                      <th width="35%" style="text-align:center;">DESCRIPTION</th>
                      <th width="30%" style="text-align:center;">CATEGORY TYPE</th>
                    </tr>
                  </thead>
                  <tbody id="prod_cat_data_table_body">                    
                  </tbody>
                </table>
              </div>
            </div>
			<div class="clearfix"></div>
          </div>
         </div>
         <button name="cancel_data" id="cancel_data" class="btn btn-info color_btn bg_color2" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/home.jsp','2001')">BACK</button>
      </div>
    </div>
    <script src="js/commons/bootstrap.min.js?<%=randomUUIDString%>"></script>
	<script src="js/commons/plugins/pace.min.js?<%=randomUUIDString%>"></script>
    <script src="js/commons/main.js?<%=randomUUIDString%>"></script>
    <script type="text/javascript">
    document.getElementById("nameSpan").innerHTML = <%= agencyVO.getAgency_code() %>
		var tbody = document.getElementById('prod_cat_data_table_body');
		for(var f=0; f<prodCatData.length; f++){
       		var tblRow = tbody.insertRow(-1);
       		tblRow.style.height="20px";
       		tblRow.align="left";
       		tblRow.innerHTML = "<td>"+prodCatData[f].cat_name+"</td>"+
       		"<td>"+prodCatData[f].cat_desc+"</td>"+
       		"<td>"+prod_cat_types[prodCatData[f].cat_type]+"</td>"; 
    	};
    	
    	
/* 		$(document).ready(function() {
	    	// tooltip for select 
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
