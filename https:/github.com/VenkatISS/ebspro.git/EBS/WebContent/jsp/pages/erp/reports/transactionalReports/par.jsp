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
<!--  	<link href="https://cdn.datatables.net/1.10.16/css/jquery.dataTables.min.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/buttons/1.5.1/css/buttons.dataTables.min.css">
-->
		<script type="text/javascript" src="https://cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js"></script>
		<link href="https://cdn.datatables.net/1.10.15/css/jquery.dataTables.min.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/buttons/1.5.1/css/buttons.dataTables.min.css">

		<title>MyLPGBooks DEALER APPLICATION - PROFITABILITY ANALYSIS<%  out.println("-- YEAR:"+ request.getAttribute("year") +" & MONTH :"+ request.getAttribute("month")); %></title>
	
		<jsp:useBean id="agencyVO" scope="session" class="com.it.erpapp.framework.model.vos.AgencyVO"></jsp:useBean>
		<jsp:useBean id="cylinder_types_list" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="arb_types_list" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="arb_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="rrt" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="pa_data" scope="request" class="java.lang.String"></jsp:useBean>
		<script type="text/javascript">
			var rrt = <%= rrt %>;
			var tgp = "0.00";
			var cylinder_types_list = <%= cylinder_types_list %>;
			var arb_types_list = <%= arb_types_list %>;
			var arb_data = <%= arb_data %>;
			var pa_data = <%= pa_data.length()>0?pa_data : "[]" %>;
			var months = ["JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"];
		</script>
		<script type="text/javascript"  src="https://code.jquery.com/jquery-1.12.4.js"></script>
		<script type="text/javascript"  src="https://cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js"></script>
		<script type="text/javascript"  src="https://cdn.datatables.net/buttons/1.5.1/js/dataTables.buttons.min.js"></script>
		<script type="text/javascript"  src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.1.3/jszip.min.js"></script>
		<script type="text/javascript"  src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.32/pdfmake.min.js"></script>
		<script type="text/javascript"  src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.32/vfs_fonts.js"></script>
		<script type="text/javascript"  src="https://cdn.datatables.net/buttons/1.5.1/js/buttons.html5.min.js"></script>
		<script type="text/javascript"  src="https://cdn.datatables.net/buttons/1.5.1/js/buttons.print.min.js"></script>
<%-- 	   	<script src="js/commons/checkBrowser.js?<%=randomUUIDString%>"></script> --%>

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
            		<h1>PROFITABILITY ANALYSIS </h1>
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
				<br>
        		<div class="row">
		        	<div class="clearfix"></div>
		          	<div class="col-md-15">
		  				<form id="pa_form" name="" method="post" action="ReportsDataControlServlet">
							<input type="hidden" id="agencyId" name="agencyId" value="<%= agencyVO.getAgency_code() %>">
							<input type="hidden" id="page" name="page" value="jsp/pages/erp/reports/transactionalReports/par.jsp">
							<input type="hidden" id="actionId" name="actionId" value="7062">
							<input type="hidden" id="fd" name="fd" value="">
							<input type="hidden" id="td" name="td" value="">
   							<div class="col-md-2 reprt">
  								<label>YEAR </label>
								<select id="sy" name="sy" class="form-control input_field select_dropdown">
									<option value="0">SELECT</option>
									<option value="2018">2018</option>
									<option value="2019">2019</option>
									<option value="2020">2020</option>																		
								</select>
 							</div>
 							<div class="col-md-2 reprt">
  								<label>MONTH </label>
								<select id="sm" name="sm" class="form-control input_field select_dropdown">
									<option value="00">SELECT</option>
									<option value="01">JAN</option>
									<option value="02">FEB</option>
									<option value="03">MAR</option>
									<option value="04">APR</option>
									<option value="05">MAY</option>
									<option value="06">JUN</option>
									<option value="07">JUL</option>
									<option value="08">AUG</option>
									<option value="09">SEP</option>
									<option value="10">OCT</option>
									<option value="11">NOV</option>
									<option value="12">DEC</option>
								</select>
 							</div>
 						</form>	
  						<button onclick="fetchPAReport()" id="searchb" name="searchb" class="btn btn-info color_btn" style="margin-top:20px;">FETCH</button>
		  			</div>
				</div>
				<br/>
				<div class="row">
	     			<div class="clearfix"></div>
	     		 	 <div class="col-md-12"> 
	      				<div id="pa_data_div" style="display: none;">
	       					<!-- <div class="main_table"> 
	         					<div class="table-responsive"> -->
	         						
        	      				<p><span id="fdate" >YEAR:</span>&nbsp&nbsp<%  out.println( request.getAttribute("year")); %>
        	      				&nbsp&nbsp <span id="tdate">MONTH:</span>&nbsp&nbsp<% out.println( request.getAttribute("month")); %>
        	      				</p>
            		
	            					<table class="table table-striped" id="pa_report_table">
        	      						<thead>
	            						<tr class="title_head">	
											<th width="10%" class="text-center sml_input">PRODUCT</th>
											<th width="10%" class="text-center sml_input">OPENING STOCK</th>
											<th width="10%" class="text-center sml_input">PURCHASES</th>
											<th width="10%" class="text-center sml_input">PURCHASE RETURNS</th>
											<th width="10%" class="text-center sml_input">SALES</th>
											<th width="10%" class="text-center sml_input">SALES RETURNS</th>
											<th width="10%" class="text-center sml_input">CLOSING STOCK</th>
											<th width="10%" class="text-center sml_input">GROSS PROFIT</th>
					 					</tr>
										</thead>
										
										 <tfoot>
            <tr>
                <th></th><th></th><th></th><th></th><th></th><th></th><th></th><th></th>

            </tr>
        </tfoot>
										<tbody id="pa_report_table_body"></tbody>
									</table>
									
									<br><br>
									TOTAL GROSS PROFIT : <span id="tgpSpan"></span>
									
			  				 	</div>
            				<!--</div> 
          				</div>-->
        			</div>
      			</div>
      			<div id="errmsg" style="width:100%;"></div>
				<div class="clearfix"></div>
				<div class="col-md-12">
					<button class="btn btn-info color_btn bg_color3 srchbtn" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/home.jsp','2001')" style="margin-left:-15px;">BACK</button>	
				</div>
	  		</div>
    	</div>
		<div id = "dialog-1" title="Alert(s)"></div>
 		<div id="dialog-confirm"><div id="myDialogText" style="color:black;"></div></div>    	
    </body>

    <script type="text/javascript" src="js/commons/bootstrap.min.js?<%=randomUUIDString%>"></script>    
    <script src="js/commons/main.js?<%=randomUUIDString%>"></script>
    <script type="text/javascript" src="js/commons/general_validations.js?<%=randomUUIDString%>"></script>
	<script type="text/javascript" src="js/modules/reports/transactionalReports/par.js?<%=randomUUIDString%>"></script>
	
	<script type="text/javascript">

    document.getElementById("nameSpan").innerHTML = <%= agencyVO.getAgency_code() %>


$(document).ready(function() {
	$('#pa_report_table').DataTable( {

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
			{ extend: 'excel', footer: true },
			{ extend: 'pdf', footer: true },
			/* {
                extend: 'pdfHtml5',
                orientation: 'landscape',
                pageSize: 'LEGAL'
            }, */
			'pageLength',
		],
		footerCallback: function ( row, data, start, end, display ) {
            var api = this.api(), data;
 
            // Remove the formatting to get integer data for summation
            var intVal = function ( i ) {
                return typeof i === 'string' ?
                    i.replace(/[\$,]/g, '')*1 :
                    typeof i === 'number' ?
                        i : 0;
            };
 
            $( api.column( 0 ).footer() ).html('Total : ');
            $( api.column( 1 ).footer() ).html(tOpeningStock);
            $( api.column( 2 ).footer() ).html(tPurchases);     
            $( api.column( 3 ).footer() ).html(tPurchasesreturn);
            $( api.column( 4 ).footer() ).html(tSales);
            $( api.column( 5 ).footer() ).html(tSalesReturn);
            $( api.column( 6 ).footer() ).html(tClosingStock);
            $( api.column( 7 ).footer() ).html(tGrossprofit);
 
		},
    
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