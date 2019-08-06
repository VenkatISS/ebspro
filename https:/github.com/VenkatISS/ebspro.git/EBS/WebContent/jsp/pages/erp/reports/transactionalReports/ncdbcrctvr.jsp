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
<!-- 	<link href="https://cdn.datatables.net/1.10.16/css/jquery.dataTables.min.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/buttons/1.5.1/css/buttons.dataTables.min.css">
-->
		<script type="text/javascript" src="https://cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js"></script>
		<link href="https://cdn.datatables.net/1.10.15/css/jquery.dataTables.min.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/buttons/1.5.1/css/buttons.dataTables.min.css">
	
	 	<title>MyLPGBooks DEALER APPLICATION - NC / DBC / RC / TV REPORT<%  out.println("-- FROM DATE:"+ request.getAttribute("fdate") +" & TO DATE :"+ request.getAttribute("tdate")); %>&nbsp &nbsp&nbsp :: PRODUCT/CONNECTION TYPE SELECTED : &nbsp:&nbsp</title>
	
		<jsp:useBean id="agencyVO" scope="session" class="com.it.erpapp.framework.model.vos.AgencyVO"></jsp:useBean>
		<jsp:useBean id="cylinder_types_list" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="equipment_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="report_data" scope="request" class="java.lang.String"></jsp:useBean>
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
			var equipment_data = <%= equipment_data %>;
			var report_data = <%= report_data.length()>0? report_data : "[]" %>;
			var rrtype = <%= rrt %>;
			var ctdatahtml = "";
			var conntypes = ["","NC","DBC","UJWALA LOAN NC","UJWALA CASH NC","RC","TV"];
			var months = ["JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"];
			var eus = ["NONE","NOS","KGS","SET"];
			var concodenc ="";
			var pcodenc ="";
			var nctypsec ="";
		</script>
		<script type="text/javascript">
			//Construct Category Type html
			ctdatahtml = "<OPTION VALUE='0'>SELECT</OPTION>";
			if(cat_types_data.length>0) {
				for(var z=0; z<cat_types_data.length; z++){
					if(cat_types_data[z].id<10) {
						ctdatahtml=ctdatahtml+"<OPTION VALUE='"+cat_types_data[z].id+"'>"+cat_types_data[z].cat_name+"-"+cat_types_data[z].cat_desc+"</OPTION>";
					}
				}
			}

			function getARBType(prod_code){
				if(prod_code==13)
					return "STOVE";
				if(prod_code==14)
					return "SURAKSHA";
				if(prod_code==15)
					return "LIGHTER";
				if(prod_code==16)
					return "KITCHENWARE";
				if(prod_code==17)
					return "OTHERS";
			}
		</script>
<%-- 	<script src="js/commons/checkBrowser.js?<%=randomUUIDString%>"></script> --%>

		<!-- Sidenav -->
		<jsp:include page="/jsp/pages/commons/sidenav.jsp"/>
		<!-- Sidenav End-->
			
	<script type="text/javascript"> 
		window.onload = setWidthHightNav("100%");
	</script>		
 	</head>
  	<body class="sidebar-mini fixed">
    	<div class="wrapper">
			<!-- Header-->
			<jsp:include page="/jsp/pages/commons/header.jsp"/>      
			<!--Header End--->

      		<div class="content-wrapper">
      			<div class="page-title">
          			<div>
            		<h1>NC/DBC/RC/TV REPORT </h1>
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
		  				<form id="purchase_search_form" name="" method="post" action="ReportsDataControlServlet">
							<input type="hidden" id="agencyId" name="agencyId" value="<%= agencyVO.getAgency_code() %>">
							<input type="hidden" id="page" name="page" value="jsp/pages/erp/reports/transactionalReports/ncdbcrctvr.jsp">
							<input type="hidden" id="actionId" name="actionId" value="7052">
							<input type="hidden" id="dep" name="dep" value="1">
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
  								<select class="form-control input_field select_dropdown" name="pid" id="pid">
								<%  String prod="<script>document.writeln(ctdatahtml)</script>";
									out.println("value: "+prod); %>
  								</select> 
 							</div>
 							<div class="col-md-3 reprt">
 								<label>CONNECTION TYPE</label>
  								<select class="form-control input_field select_dropdown" name="ct" id="ct">
									<option value="0">SELECT</option>
									<option value="1">NC</option>
									<option value="3">UJWALA LOAN NC</option>
									<option value="4">UJWALA CASH NC</option>
									<option value="2">DBC</option>
									<option value="5">RC</option>
									<option value="6">TV</option>
  								</select>
 							</div>
<!--  							<div class="col-md-2 reprt">
 								<label>DEPOSIT</label>
  								<select class="form-control input_field select_dropdown" name="dep" id="dep">
									<option value="0">SELECT</option>
									<option value="1">YES</option>
									<option value="2">NO</option>
								</select>
 							</div>
 -->
 						</form>	
  						<button onclick="fetchNCDBCRCTVReport()" id="searchb" name="searchb" class="btn btn-info color_btn" style="margin-top:20px;">SEARCH</button>
		  			</div>
				</div>
				<br/>
				<div class="row">
	     			<div class="clearfix"></div>
	     		 	 <div class="col-md-12"> 
	      				<div id="p_data_table" style="display: none;">
							<div>
								<p><span id="fromd" ><b> FROM DATE:</b></span>&nbsp&nbsp&nbsp&nbsp&nbsp<%  out.println( request.getAttribute("fdate")); %>
        	      				&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp <span id="tdate"><b>TO DATE:</b></span>&nbsp&nbsp&nbsp&nbsp&nbsp<% out.println( request.getAttribute("tdate")); %>
        	      				&nbsp&nbsp&nbsp&nbsp<b>PRODUCT SELECTED :</b> <span id="ptype_selected"></span>
        	      				&nbsp&nbsp&nbsp&nbsp<b>CONNECTION TYPE SELECTED :</b> <span id="ctype_selected"></span>
							</div>
							<table class="table table-striped" id="m_data_table">
								<thead>
									<tr class="title_head">
										<th width="10%" class="text-center sml_input">TRANSACTION DATE</th>
										<th width="10%" class="text-center sml_input">CONNECTION TYPE</th>
										<th width="10%" class="text-center sml_input">PRODUCT</th>
										<th width="10%" class="text-center sml_input">NUMBER OF CONNECTIONS</th>
										<th width="10%" class="text-center sml_input">NUMBER OF CYLINDERS</th>
										<th width="10%" class="text-center sml_input">NUMBER OF REGULATORS</th>
										<th width="10%" class="text-center sml_input">TOTAL SECURITY DEPOSIT</th>
					 				</tr>
								</thead>
								<tfoot>
									<tr>
										<th></th><th></th><th></th><th></th><th></th><th></th><th></th>
									</tr>
								</tfoot>
								<tbody id="p_data_table_body"></tbody>
							</table>
						</div>
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
    <script>
    if(report_data.length >0){
    	pcodenc = <%= request.getAttribute("pcodenc")%>;
    	concodenc = <%= request.getAttribute("concodenc")%>;
    }
    </script>
	<script type="text/javascript" src="js/modules/reports/transactionalReports/ncdbcrctvr.js?<%=randomUUIDString%>"></script>
	
	<script type="text/javascript">

    document.getElementById("nameSpan").innerHTML = <%= agencyVO.getAgency_code() %>
    domain=document.getElementById("ptype_selected").innerHTML;
	domain1=document.getElementById("ctype_selected").innerHTML;
	document.title += domain;
	document.title +=domain1;


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
			{ extend: 'excel', footer: true },
			{
				extend: 'pdfHtml5',
				orientation: 'landscape',
				pageSize: 'LEGAL',
				footer: true
			},
			/* {
				extend: 'print',
				customize: function ( win ) {
					$(win.document.body)
						.css( 'font-size', '8pt' )
						.prepend(
							'<img src="" style="position:absolute; top:0; left:0;" />'
					);;
					$(win.document.body).find( 'table' )
						.addClass( 'compact' )
						.css( 'font-size', 'inherit' );
					}
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

			$( api.column( 2 ).footer() ).html('Total :');
			$( api.column( 3 ).footer() ).html(tNumoOfConnections);
			$( api.column( 4 ).footer() ).html(tNumOfCylinders);
			$( api.column( 5 ).footer() ).html(tNumOfRegulators);
			$( api.column( 6 ).footer() ).html(tSecurityDeposite);
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
