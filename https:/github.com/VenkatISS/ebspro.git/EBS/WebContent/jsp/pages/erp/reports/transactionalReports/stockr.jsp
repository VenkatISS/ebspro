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
		<script src="js/commons/jquery-2.1.4.min.js?<%=randomUUIDString%>"></script>
		 <link href="https://cdn.datatables.net/1.10.16/css/jquery.dataTables.min.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/buttons/1.5.1/css/buttons.dataTables.min.css">
		<title>MyLPGBooks DEALER APPLICATION - STOCK REPORT<%  out.println("-- FROM DATE:"+ request.getAttribute("fdate") +" & TO DATE :"+ request.getAttribute("tdate")); %>&nbsp &nbsp&nbsp :: PRODUCT SELECTED : &nbsp:&nbsp</title>
	    
		<jsp:useBean id="agencyVO" scope="session" class="com.it.erpapp.framework.model.vos.AgencyVO"></jsp:useBean>
		<jsp:useBean id="cylinder_types_list" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="arb_types_list" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="arb_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="sreport_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="rrt" scope="request" class="java.lang.String"></jsp:useBean>
	    <script type="text/javascript"  src="https://code.jquery.com/jquery-1.12.4.js"></script>
		<script type="text/javascript"  src="https://cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js"></script>
		<script type="text/javascript"  src="https://cdn.datatables.net/buttons/1.5.1/js/dataTables.buttons.min.js"></script>
		<script type="text/javascript"  src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.1.3/jszip.min.js"></script>
		<script type="text/javascript"  src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.32/pdfmake.min.js"></script>
		<script type="text/javascript"  src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.32/vfs_fonts.js"></script>
		<script type="text/javascript"  src="https://cdn.datatables.net/buttons/1.5.1/js/buttons.html5.min.js"></script>
		<script type="text/javascript"  src="https://cdn.datatables.net/buttons/1.5.1/js/buttons.print.min.js"></script>
			
		<script type="text/javascript">
			var cat_types_data = <%= cylinder_types_list %>;
			var arb_types_data = <%= arb_data %>;
			var showr = <%= rrt %>;
			var sreport_data = <%= sreport_data %>;
			var ctdatahtml = "";
			var vendatahtml = "";
			var cusdatahtml = "";
		</script>
<%-- 		<script src="js/commons/checkBrowser.js?<%=randomUUIDString%>"></script> --%>

       	<!-- sidenav start-->
       		<jsp:include page="/jsp/pages/commons/sidenav.jsp"/>
       	<!-- sidenav end-->

		<script type="text/javascript"> 
			window.onload = setWidthHightNav("100%");
		</script>
	</head>
  	<body class="sidebar-mini fixed">
    	<div class="wrapper">
       	<!-- HEADER START-->
			<jsp:include page="/jsp/pages/commons/header.jsp"/> 
      	<!---HEADER END---->

      	<div class="content-wrapper">
        	<div class="page-title">
          		<div>
            		<h1>STOCK REPORT </h1>
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
                	<form id="stock_search_form" name="" method="post" action="ReportsDataControlServlet">
						<input type="hidden" id="agencyId" name="agencyId" value="<%= agencyVO.getAgency_code() %>">
						<input type="hidden" id="page" name="page" value="jsp/pages/erp/reports/transactionalReports/stockr.jsp">
						<input type="hidden" id="actionId" name="actionId" value="7022">
                		<div class="col-md-2 reprt rep_div">
  							<label>FROM DATE </label>
  							<input type="date" class="form-control input_field rep_input_field" id="fd" name="fd">
 						</div>
 						<div class="col-md-2 reprt rep_div">
  							<label>TO DATE </label>
  							<input type="date" class="form-control input_field rep_input_field" id="td" name="td">
 						</div>
 						<div class="col-md-2 reprt">
  							<label>PRODUCT </label>
  							<span id="pitems"></span>
 						</div>
 					</form>
 					<div class="col-md-2 reprt">
						<button onclick="fetchStockReport()" id="searchb" name="searchb" class="btn btn-info color_btn" style="margin-top:20px;">SEARCH</button>
 					</div>
              	</div>
            </div>
			<br>
			<br>
			<div class="row">
	     		<div class="clearfix"></div>
	     		<br><br>
				<div id="p_data_table_div" style="display: none;">
					<p><span id="fromd" >&nbsp&nbsp&nbsp&nbsp<b>FROM DATE:</b></span>&nbsp&nbsp<%  out.println( request.getAttribute("fdate")); %>
        	      		&nbsp&nbsp <span id="tdate"><b>TO DATE:</b></span>&nbsp&nbsp<% out.println( request.getAttribute("tdate")); %>
						&nbsp&nbsp&nbsp&nbsp <b>PRODUCT SELECTED :</b> <span id="prod_selected"></span></p>
					
	      			<div class="col-md-12">
	      				<div id="p_data_table">
							<div class="table-responsive">
								<table class="table table-striped" id="m_data_table">
									<thead>
										<tr class="title_head">
											<th width="10%" class="text-center sml_input">OPENING STOCK</th>
											<th width="10%" class="text-center sml_input">TOTAL PURCHASES</th>
											<th width="10%" class="text-center sml_input">TOTAL PURCHASE RETURNS</th>
											<th width="10%" class="text-center sml_input">TOTAL SALES</th>
											<th width="10%" class="text-center sml_input">TOTAL SALES RETURNS</th>
											<th width="10%" class="text-center sml_input">CLOSING STOCK </th>
											<th width="10%" class="text-center sml_input">CLOSING STOCK EMPTIES</th>
											<th width="10%" class="text-center sml_input">TOTAL STOCK</th>
										</tr>
									</thead>
									<tbody id="p_data_table_body"></tbody>
								</table>
							</div>
          				</div>
        			</div>
  				  </div>
       			</div>
				<div class="clearfix"></div>
				<div class="col-md-12">
					<button name="cancel_data" id="cancel_data" class="btn btn-info color_btn bg_color3 srchbtn" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/home.jsp','2001')" style="margin-left:-15px;">BACK</button>
				</div>
      		</div>
		</div>
      	<div id = "dialog-1" title="Alert(s)"></div>
 		<div id="dialog-confirm"><div id="myDialogText" style="color:black;"></div></div>        
  	</body>
  	    <script type="text/javascript" src="js/commons/bootstrap.min.js?<%=randomUUIDString%>"></script>    
    <script src="js/commons/main.js?<%=randomUUIDString%>"></script>
    <script type="text/javascript" src="js/modal.js?<%=randomUUIDString%>"></script>
    
    <script type="text/javascript" src="js/commons/general_validations.js?<%=randomUUIDString%>"></script>
	<script type="text/javascript" src="js/modules/reports/transactionalReports/stockr.js?<%=randomUUIDString%>"></script>


	<script type="text/javascript">

		document.getElementById("nameSpan").innerHTML = <%= agencyVO.getAgency_code() %>;
		domain=document.getElementById("prod_selected").innerHTML;
	    document.title += domain;

		$(document).ready(function() {

			$('#m_data_table').DataTable( {
				//aLengthMenu: [[10, 25, 50,100], [10, 25, 50,100]], aaSorting: [[0, 'asc']], iDisplayLength: 10,
				scrollY:'50vh',
				scrollCollapse: true,
   				scrollX: true,
   	   			ordering:false,
				dom: 'Bfrtip',
        		lengthMenu: [
            		[ 5,10, 25, 50,100, -1 ],
	                ['5 rows', '10 rows', '25 rows', '50 rows','100 rows', 'Show all' ]
	            ],
				buttons: [
					'excel',
					{
            	    extend: 'pdfHtml5',
	                orientation: 'landscape',
	                pageSize: 'LEGAL'
		            },
					'pageLength',
				]
			} );
	
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