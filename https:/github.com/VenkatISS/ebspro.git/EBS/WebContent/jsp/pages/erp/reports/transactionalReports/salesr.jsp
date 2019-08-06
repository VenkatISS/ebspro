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
	 
		<title>MyLPGBooks DEALER APPLICATION - SALES REPORTS <%  out.println("-- FROM DATE:"+ request.getAttribute("fdate") +" & TO DATE :"+ request.getAttribute("tdate")); %>&nbsp &nbsp&nbsp :: PRODUCT / DELIVERED BY SELECTED : &nbsp:&nbsp</title>
        <jsp:useBean id="agencyVO" scope="session" class="com.it.erpapp.framework.model.vos.AgencyVO"></jsp:useBean>
		<jsp:useBean id="cylinder_types_list" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="arb_types_list" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="equipment_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="arb_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="staff_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="cvo_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="dcsreport_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="ncdcsreport_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="ccsreport_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="ncccsreport_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="arbsreport_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="ncarbsreport_data" scope="request" class="java.lang.String"></jsp:useBean>
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
			var arb_types_data = <%= arb_data %>;
			var staff_data = <%= staff_data %>;
			var cvo_data = <%= cvo_data %>;
			var dcs_report_data = <%= dcsreport_data %>;
			var ncdbc_dcs_report_data = <%= ncdcsreport_data %>;
			var ccs_report_data = <%= ccsreport_data %>;
			var ncdbc_ccs_report_data = <%= ncccsreport_data %>;
			var arb_report_data = <%= arbsreport_data %>;
			var ncdbc_arb_report_data = <%= ncarbsreport_data %>;
			var rrtype = <%= rrt %>;
			var ctdatahtml = "";
			var staffdatahtml = "";
			var pcodes="";
			var scodes ="";
			var stypsel="";
			var months = ["JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"];
			var eus = ["NONE","NOS","KGS","SET"];
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
			if(arb_types_data.length>0) {
				for(var z=0; z<arb_types_data.length; z++){
// 					if(arb_types_data[z].deleted == 0){
					//ctdatahtml=ctdatahtml+"<OPTION VALUE='"+arb_types_data[z].id+"'>"+arb_types_data[z].cat_code+"-"+arb_types_data[z].cat_name+"-"+arb_types_data[z].cat_desc+"</OPTION>";
					ctdatahtml=ctdatahtml+"<OPTION VALUE='"+arb_types_data[z].id+"'>"
					+getARBType(arb_types_data[z].prod_code)+" - "+arb_types_data[z].prod_brand+" - "+arb_types_data[z].prod_name
					+"</OPTION>";
// 					}
				}
			}
	    	//Construct Staff html
			staffdatahtml = "<OPTION VALUE='0'>SELECT</OPTION>";
			if(staff_data.length>0) {
				for(var z=0; z<staff_data.length; z++){
					if(staff_data[z].deleted == 0){
						staffdatahtml=staffdatahtml+"<OPTION VALUE='"+staff_data[z].id+"'>"+staff_data[z].emp_name+"</OPTION>";
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
            			<h1>SALE REPORT  </h1>
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
		  				<form id="sales_search_form" name="" method="post" action="ReportsDataControlServlet">
							<input type="hidden" id="agencyId" name="agencyId" value="<%= agencyVO.getAgency_code() %>">
							<input type="hidden" id="page" name="page" value="jsp/pages/erp/reports/transactionalReports/salesr.jsp">
							<input type="hidden" id="actionId" name="actionId" value="7012">
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
 							<div class="col-md-2 reprt" style="width:160px;">
 								<label>DELIVERED BY/STAFF NAME</label>
  								<select class="form-control input_field select_dropdown" name="pv" id="pv">
									<%  String stfdata="<script>document.writeln(staffdatahtml)</script>";
										out.println("value: "+stfdata); %>
  								</select>
 							</div>
 						</form>	
  							<button onclick="fetchSaleReport()" id="searchb" name="searchb" class="btn btn-info color_btn" style="margin-top:20px;">SEARCH</button>
		  			</div>
				</div>
				<br>
				<div class="row">
	     			<div class="clearfix"></div>
	      			<div class="col-md-12">
	     		 		<div id="p_data_table" style="display: none;">	
	     		 		<div>
								<p><span id="fromd" ><b> FROM DATE:</b></span>&nbsp&nbsp&nbsp&nbsp&nbsp<%  out.println( request.getAttribute("fdate")); %>
        	      				&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp <span id="tdate"><b>TO DATE:</b></span>&nbsp&nbsp&nbsp&nbsp&nbsp<% out.println( request.getAttribute("tdate")); %>
        	      				&nbsp&nbsp&nbsp&nbsp <b>PRODUCT / DELIVERED BY SELECTED :</b> <span id="type_selected"></span>
        	      				</p>
						</div>
						      
							<table class="table table-striped" id="m_data_table">
        	      				<thead>
        	      					<tr class="title_head">
										<th width="10%" class="text-center sml_input">INV NO</th>
										<th width="10%" class="text-center sml_input">INVOICE <br> DATE</th>
										<th width="10%" class="text-center sml_input">PRODUCT</th>
										<th width="10%" class="text-center sml_input">HSN CODE</th>
										<th width="10%" class="text-center sml_input">GST RATE</th>
										<th width="10%" class="text-center sml_input">CUSTOMER</th>
										<th width="10%" class="text-center sml_input">CUSTOMER<br>GSTN</th>
										<th width="10%" class="text-center sml_input">QTY</th>
										<th width="10%" class="text-center sml_input">UNITS</th>
										<th width="10%" class="text-center sml_input">UNIT <br> PRICE</th>
										<th width="10%" class="text-center sml_input">DISC. ON UNIT PRICE</th>										
										<th width="10%" class="text-center sml_input">TAXABLE VALUE</th>
										<th width="10%" class="text-center sml_input">IGST <br> AMOUNT</th>
										<th width="10%" class="text-center sml_input">CGST <br> AMOUNT</th>
										<th width="10%" class="text-center sml_input">SGST <br> AMOUNT</th>
										<th width="10%" class="text-center sml_input">INVOICE <br> AMOUNT</th>
										<th width="25%" class="text-center sml_input">DELIVERED BY/STAFF NAME</th>
					 				</tr>
								</thead>
								
								 <tfoot>
            <tr>
                <th></th><th></th><th></th><th></th><th></th><th></th><th></th><th></th><th></th><th></th>
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
					<button name="cancel_data" id="cancel_data" class="btn btn-info color_btn bg_color3 srchbtn" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/home.jsp','2001')" style="margin-left:-15px;">BACK</button>	
	  			</div>
	  		</div>
    	</div>
		<div id = "dialog-1" title="Alert(s)"></div>
 		<div id="dialog-confirm"><div id="myDialogText" style="color:black;"></div></div>
  	</body>

  	<script type="text/javascript">
	  	if((dcs_report_data.length > 0) || (ccs_report_data.length > 0) || ( arb_report_data.length > 0)){           
    		pcodes = <%= request.getAttribute("pcodes")%>;
   	    	scodes = <%= request.getAttribute("scodes")%>;
	    }
  	</script>
  	
  	<script type="text/javascript" src="js/commons/general_validations.js?<%=randomUUIDString%>"></script>  	
	<script type="text/javascript" src="js/modules/reports/transactionalReports/salesr.js?<%=randomUUIDString%>"></script>	
    <script type="text/javascript" src="js/commons/bootstrap.min.js?<%=randomUUIDString%>"></script>
    <script src="js/commons/main.js?<%=randomUUIDString%>"></script>
   
   <script type="text/javascript">

		document.getElementById("nameSpan").innerHTML = <%= agencyVO.getAgency_code() %>
		domain=document.getElementById("type_selected").innerHTML;
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
					{ extend: 'excel', footer: true },
					{
	                extend: 'pdfHtml5',
    	            orientation: 'landscape',
    	            pageSize: 'LEGAL',
    	            footer: true
    		        },
	    		/* 	{
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

            		$( api.column( 7 ).footer() ).html('total : '+tqty);
       			 	$( api.column( 9 ).footer() ).html('avg : '+tQtyPrice);

        			$( api.column( 10 ).footer() ).html('total : '+tDisPrice);

        			$( api.column( 11 ).footer() ).html(ttaxamt);
        			$( api.column( 12 ).footer() ).html(tigstamt);
        			$( api.column( 13 ).footer() ).html(tsgstamt);
        			$( api.column( 14 ).footer() ).html(tcgstamt);
        			$( api.column( 15 ).footer() ).html(tinvamt);
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