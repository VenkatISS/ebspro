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
		<title>MyLPGBooks DEALER APPLICATION - BANK BOOK<%  out.println("-- FROM DATE:"+ request.getAttribute("fdate") +" & TO DATE :"+ request.getAttribute("tdate")); %>&nbsp &nbsp&nbsp :: BANK SELECTED : &nbsp:&nbsp</title>
	
		<jsp:useBean id="agencyVO" scope="session" class="com.it.erpapp.framework.model.vos.AgencyVO"></jsp:useBean>
		<jsp:useBean id="bank_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="bbr_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="sfd" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="openingBalance" scope="request" class="java.lang.String"></jsp:useBean>
			
		<script type="text/javascript">
			var bank_data =  <%= bank_data.length()>0? bank_data : "[]" %>;
			var bbr_data = <%= bbr_data.length()>0? bbr_data : "[]" %>;
			var bankdatahtml = "";
			var bcodeb ="";
			var norecf = "";
			var btypsel="";
			var mops = ["NONE","CASH","CHEQUE","ONLINE TRANSFER"];
			var tts = ["NONE","PAYMENT","RECEIPT","CASH DEPOSIT","CASH WITHDRAWL","TRANSFER","RECEIPT REVERSAL","PAYMENT REVERSAL","TRANSACTION CANCELLED","GST PAYMENT","INCOME TAX PAYMENT","GST PAYMENT REVERSAL","INCOME TAX PAYMENT REVERSAL","CASH DEPOSIT CANCELLED","CASH WITHDRAWL CANCELLED"];
			var ahs = ["NONE","CAPITAL ACCOUNT","EXPENDITURE","BANK CHARGES","SALARIES/WAGES","LOANS/ADVANCES","STAR","TAR"];
			var months = ["JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"];
			
			var sfd = <%= sfd.length()>0? sfd : "[]"%>;
			 var openingBal = <%= openingBalance != "" ? openingBalance : "0" %>;
			var bankdetails = "Opening Balance As On : ";
			 
			</script>
	
		<script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.js"></script>
		<script type="text/javascript" src="https://cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js"></script>
		<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.5.1/js/dataTables.buttons.min.js"></script>
		<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.1.3/jszip.min.js"></script>
		<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.32/pdfmake.min.js"></script>
		<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.32/vfs_fonts.js"></script>
		<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.5.1/js/buttons.html5.min.js"></script>
		<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.5.1/js/buttons.print.min.js"></script>
		    	
 		<script type="text/javascript">
			//Construct bank html
			var tarbid = 0;
			var starbid = 0;
			var baccno = 0;
			bankdatahtml = "<OPTION VALUE='0'>SELECT</OPTION>";
			if(bank_data.length>0) {
				for(var z=0; z<bank_data.length; z++){
					bc = bank_data[z].bank_code;
					if(bc=="TAR ACCOUNT") {
						bc = "LOAD ACCOUNT";
						tarbid = bank_data[z].id;
						baccno = bank_data[z].bank_acc_no;
					}
					if(bc=="STAR ACCOUNT") {
						bc = "SV/TV ACCOUNT";
						starbid = bank_data[z].id;
						baccno = bank_data[z].bank_acc_no;
					}
					bankdatahtml=bankdatahtml+"<OPTION VALUE='"+bank_data[z].id+"'>"+bc+" - "+bank_data[z].bank_acc_no+"</OPTION>";
				}
			}
   	 	</script>
<%-- 		<script src="js/commons/checkBrowser.js?<%=randomUUIDString%>"></script> --%>

		<!-- Sidenav-->
      		<jsp:include page="/jsp/pages/commons/sidenav.jsp"/>
		<!---Sidenav END---->
		<script type="text/javascript"> 
			window.onload = setWidthHightNav("100%");
		</script>
		
		<style>
		@media screen and (min-width: 1400px) {
			.sidebar-mini.sidebar-collapse .dataTable {
				width: 1418px !important;1258
			}
		}
		
		@media screen and (min-width: 1200px) and (max-width: 1399px) {
			.sidebar-mini.sidebar-collapse .dataTable {
				width: 1258px !important;
			}
		}
		
		@media screen and (min-width: 1200px) and (max-width: 1399px) {
			.sidebar-mini:not(.sidebar-collapse) .dataTable {
				width: 1087.5px !important;
			}
		}
		</style>
  	</head>
  	<body class="sidebar-mini fixed">
    	<div class="wrapper wraper">
			<!-- Header-->
			<jsp:include page="/jsp/pages/commons/header.jsp"/>
			<!--Header End--->
      		
      		<div class="content-wrapper">
        		<div class="page-title">
          			<div>
            			<h1>BANK BOOK  </h1>
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
							<form id="bank_book_search_form" name="" method="post" action="ReportsDataControlServlet">
								<input type="hidden" id="agencyId" name="agencyId" value="<%= agencyVO.getAgency_code() %>">
								<input type="hidden" id="page" name="page" value="jsp/pages/erp/reports/transactionalReports/bankbookr.jsp">
								<input type="hidden" id="actionId" name="actionId" value="7042">
 								<div class="col-md-2 reprt">
  									<label>BANK </label>
  									<select class="form-control input_field select_dropdown" name="bid" id="bid">
										<%  String bid="<script>document.writeln(bankdatahtml)</script>";
											out.println("value: "+bid); %>
  									</select>
 								</div>
								<div class="col-md-2 reprt rep_div ">
  									<label>FROM DATE </label>
  									<input type="date" class="form-control input_field rep_input_field" id="fd" name="fd">
<!--  									<input type="date" class="form-control input_field" id="fd" name="fd"> -->
 								</div>
 								<div class="col-md-2 reprt rep_div ">
  									<label class="">TO DATE </label>
  									<input type="date" class="form-control input_field rep_input_field" id="td" name="td">
 <!--  									<input type="date" class="form-control input_field" id="td" name="td">-->
  								</div>
 							</form>	
  								<button onclick="searchData()" id="searchb" name="searchb" class="btn btn-info color_btn rep_button" style="margin-top:18px;">SEARCH</button>
		  				</div>
					</div>
					<br>
					<div class="row">
	     				<div class="clearfix"></div>
	      				<div class="col-md-12"  style = "padding-left : 13px; padding-right : 13px;">
	      					<div id="bb_data_table" style="display: none;" >
							<div>
								<p><span id="fromd" >&nbsp&nbsp&nbsp&nbsp<b>FROM DATE:</b></span>&nbsp&nbsp<%  out.println( request.getAttribute("fdate")); %>
									&nbsp&nbsp <span id="tdate"><b>TO DATE:</b></span>&nbsp&nbsp<% out.println( request.getAttribute("tdate")); %>
									<br>&nbsp&nbsp&nbsp&nbsp<b>BANK SELECTED :</b> <span id="type_selected"></span>&nbsp&nbsp&nbsp <span id="obSpan"></span>
								</p>
        	      			</div>	
	            				<table class="table table-striped" id="bb_report_table">
        	      					<thead>
            	    					<tr class="title_head">
											<th width="10%" class="text-center sml_input">DATE</th>
											<th width="10%" class="text-center sml_input">BANK DETAILS</th>
											<th width="10%" class="text-center sml_input">TRANSACTION DETAILS</th>
											<th width="10%" class="text-center sml_input">DEBIT</th>
											<th width="10%" class="text-center sml_input">CREDIT</th>
											<th width="10%" class="text-center sml_input">BALANCE</th>
					 					</tr>
									</thead>
									<tbody id="bb_report_table_body"></tbody>
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
   	<script type="text/javascript" src="js/commons/general_validations.js?<%=randomUUIDString%>"></script>
   	<script type="text/javascript">
   	if(bbr_data.length>0){
   		bcodeb = <%= request.getAttribute("bcodeb")%>;
	}
   	norecf = <%= request.getAttribute("norecf")%>;
   	</script>
	<script type="text/javascript" src="js/modules/reports/transactionalReports/bankbookr.js?<%=randomUUIDString%>"></script>
    <script src="js/commons/main.js?<%=randomUUIDString%>"></script>
	
	<script type="text/javascript">
		document.getElementById("nameSpan").innerHTML = <%= agencyVO.getAgency_code() %>
		domain=document.getElementById("type_selected").innerHTML;
		document.title += domain;

		$(document).ready(function() {
			$('#bb_report_table').DataTable( {
				//aLengthMenu: [[10, 25, 50,100], [10, 25, 50,100]], aaSorting: [[0, 'asc']], iDisplayLength: 10,
			 	"scrollY":'50vh',
    			"scrollCollapse": true,
       	        "scrollX": true,
       	    	"ordering": false,
                dom: 'Bfrtip',
                lengthMenu: [
                    [ 5,10, 25, 50,100, -1 ],
                    ['5 rows', '10 rows', '25 rows', '50 rows','100 rows', 'Show all' ]
                ],
				buttons: [
					'excel','pdf',
				/* 	{
            		    extend: 'pdfHtml5',
                		orientation: 'landscape',
                		pageSize: 'LEGAL'
           	 		},
		 		*/'pageLength',
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
		});
   </script> 
</html>