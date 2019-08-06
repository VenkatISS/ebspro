<!DOCTYPE html>
<%@ page import = "java.util.*,java.text.*" %>
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
	    <!-- <script type="text/javascript" src="https://cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js"></script>
	    <script src="js/commons/jquery.min.1.11.1.js"></script>
    	<script src="js/commons/jquery.table2excel.min.js"></script>
	    
		<link href="https://cdn.datatables.net/1.10.15/css/jquery.dataTables.min.css" rel="stylesheet" type="text/css">
		 -->
		
		<link href="https://cdn.datatables.net/1.10.16/css/jquery.dataTables.min.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/buttons/1.5.1/css/buttons.dataTables.min.css">
		
		 <title>MyLPGBooks DEALER APPLICATION - RECEIVABLES REPORTS</title>
		<jsp:useBean id="agencyVO" scope="session" class="com.it.erpapp.framework.model.vos.AgencyVO"></jsp:useBean>
		<jsp:useBean id="cvo_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="page_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="rrt" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="scustid" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="req_type" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="sad" scope="request" class="java.lang.String"></jsp:useBean>
		<script type="text/javascript">
			var cvo_data = <%= cvo_data %>;
			var page_data = <%= page_data %>;
			var rrtype = <%= rrt %>;
			var custid = <%= scustid %>;
			var sad = <%= sad %>;
			var bv = 0;
			var obdetails = "Receivables As On ";
			var ctdatahtml = "";
			var vendatahtml = "";
			var months = ["JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"];
		</script>
		
		<script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.js"></script>
		<script type="text/javascript" src="https://cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js"></script>
		<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.5.1/js/dataTables.buttons.min.js"></script>
		<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.1.3/jszip.min.js"></script>
		<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.32/pdfmake.min.js"></script>
		<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.32/vfs_fonts.js"></script>
		<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.5.1/js/buttons.html5.min.js"></script>
		<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.5.1/js/buttons.print.min.js"></script>
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
            		<h1>AGENCY RECEIVABLES REPORT </h1>
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
		  				<form id="receivables_search_form" name="" method="post" action="ReportsDataControlServlet">
							<input type="hidden" id="agencyId" name="agencyId" value="<%= agencyVO.getAgency_code() %>">
							<input type="hidden" id="page" name="page" value="jsp/pages/erp/reports/transactionalReports/receivablesr.jsp">
							<input type="hidden" id="actionId" name="actionId" value="7352">
   							<div class="col-md-2 reprt">
  								<label>CUSTOMER NAME </label>
  								<span id="pitemsv" name="pitemsv"></span>
 							</div>
 							<div class="col-md-2 reprt">
  								<label>AS ON </label>
<!--   								<input type="date" class="form-control input_field" id="ad" name="ad">
 -->
                                        <%Date date = new Date();
                                        SimpleDateFormat df = new SimpleDateFormat();
                                        df.applyPattern("MM/dd/yyyy"); %>
                                    <input type="text" id="ad" name="ad" class="form-control input_field"  value="<%=df.format(agencyVO.getDayend_date()) %>" readonly></span>
                           </div>
 						</form>
  						<button onclick="fetchReceivablesReport(receivables_search_form)" id="searchb" name="searchb" class="btn btn-info color_btn" style="margin-top:20px;">SEARCH</button>
		  			</div>
				</div>
				<br/>
				<div class="row">
	     			<div class="clearfix"></div>
	     		 	 <div class="col-md-12"> 
	      				<div id="data_div" style="display: none;">
								<b>DATE:</b>&nbsp<span id="rbSpan"></span>
	         					<table class="table table-striped" id="m_data_table">
        	      					<thead>

        	      				<%-- 				<tr>
        	      				<th width="10%" style="text-align:center;display:none;">AGENCY RECEIVABLES REPORT DATE:</th><td width="10%" style="text-align:center;display:none;"><span id="rbSpan">&nbsp&nbsp<%=df.format(agencyVO.getDayend_date()) %></span></td>
        	      				<td></td><td></td><td></td><td></td><td width="10%" style="text-align:right;"><button onclick="exportexcel()"> Excel </button></td>
        	      				</tr> --%>
				<tr class="title_head">
												<th width="10%" class="text-center sml_input">CUSTOMER NAME</th>
												<th width="10%" class="text-center sml_input">&lt; 30 DAYS <br> DATE</th>
												<th width="10%" class="text-center sml_input">31-60 DAYS</th>
												<th width="10%" class="text-center sml_input">&gt; 60 DAYS</th>
												<th width="10%" class="text-center sml_input">BALANCE</th>
					 						</tr>
										</thead>
										<tbody id="p_data_table_body">
											<tr>
												<td><span id="cnSpan"></span></td>
												<td><span id="l30Span"></span></td>
												<td><span id="l60g30Span"></span></td>
												<td><span id="g60Span"></span></td>
												<td><span id="bSpan"></span></td>										
											</tr>
										</tbody>
									</table>
			  				 	</div>
            				<!--</div> 
          				</div>-->
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

    <script type="text/javascript" src="js/commons/bootstrap.min.js?<%=randomUUIDString%>"></script>    
    <script src="js/commons/main.js?<%=randomUUIDString%>"></script>
    <script type="text/javascript" src="js/commons/general_validations.js?<%=randomUUIDString%>"></script>
	<script type="text/javascript" src="js/modules/reports/transactionalReports/receivablesr.js?<%=randomUUIDString%>"></script>
	
	
	<script type="text/javascript">
		document.getElementById("nameSpan").innerHTML = <%= agencyVO.getAgency_code() %>


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
			'excel','pdf',
			/* {
                extend: 'pdfHtml5',
                orientation: 'landscape',
                pageSize: 'LEGAL'
            }, */
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
	
    <%-- <script type="text/javascript">
        document.getElementById("nameSpan").innerHTML = <%= agencyVO.getAgency_code() %>
        
        function exportexcel() {
            $("#m_data_table").table2excel({
                name: "Table2Excel",
                filename: "receivablereports",
                fileext: ".xls"
            });
        }
	
      
	</script> --%>
</html>