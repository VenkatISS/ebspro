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
		<title>MyLPGBooks DEALER APPLICATION - LEDGER<%  out.println("-- FROM DATE:"+ request.getAttribute("fdate") +" & TO DATE :"+ request.getAttribute("tdate")); %>&nbsp &nbsp&nbsp :: PRODUCT/PARTY TYPE SELECTED : &nbsp:&nbsp</title>
	    
		<jsp:useBean id="agencyVO" scope="session" class="com.it.erpapp.framework.model.vos.AgencyVO"></jsp:useBean>
		<jsp:useBean id="cylinder_types_list" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="arb_types_list" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="arb_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="cvo_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="pwreport_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="preport_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="taxreport_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="openingBalance" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="rrt" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="req_type" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="sfd" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="cbr_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="cash_trans_enum_data" scope="session" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="noOfRecsBeforeFdate" scope="request" class="java.lang.String"></jsp:useBean>

		<script type="text/javascript"  src="https://code.jquery.com/jquery-1.12.4.js"></script>
		<script type="text/javascript"  src="https://cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js"></script>
		<script type="text/javascript"  src="https://cdn.datatables.net/buttons/1.5.1/js/dataTables.buttons.min.js"></script>
		<script type="text/javascript"  src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.1.3/jszip.min.js"></script>
		<script type="text/javascript"  src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.32/pdfmake.min.js"></script>
		<script type="text/javascript"  src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.32/vfs_fonts.js"></script>
		<script type="text/javascript"  src="https://cdn.datatables.net/buttons/1.5.1/js/buttons.html5.min.js"></script>
		<script type="text/javascript"  src="https://cdn.datatables.net/buttons/1.5.1/js/buttons.print.min.js"></script>
			
		<script type="text/javascript">
			var cat_types_data = <%= cylinder_types_list.length()>0? cylinder_types_list : "[]" %>;
			var arb_types_data = <%= arb_data.length()>0? arb_data : "[]"%>;
			var cvo_data = <%= cvo_data.length()>0? cvo_data : "[]"%>;
			var pwreport_data = <%= pwreport_data.length()>0? pwreport_data : "[]"%>;
			var preport_data = <%= preport_data.length()>0? preport_data : "[]"%>;
			var taxreport_data = <%= taxreport_data.length()>0? taxreport_data : "[]"%>;
			var openingBal = <%= openingBalance != "" ? openingBalance : "0" %>;
			<%-- 		var partywise_obal = <%= openingBalanceAmount != "" ? openingBalanceAmount : "0" %>; --%>
			var rrtype = <%= rrt.length()>0? rrt : "[]"%>;
			var sfd = <%= sfd.length()>0? sfd : "[]"%>;
			var noOfRecsBeforeFdate = <%= noOfRecsBeforeFdate.length()>0? noOfRecsBeforeFdate : "[]"%>;
			var cbr_data = <%= cbr_data.length()>0? cbr_data : "[]" %>;
			var cash_trans_enum_data =  <%= cash_trans_enum_data.length()>0? cash_trans_enum_data : "[]" %>;
			var bv = 0;
			var pcode="";
			var ptycode ="";
			var prodcodeval="";
			var obdetails = "Opening Balance As On : ";
			var prodetails = "Opening Stock As On  : ";
			var ctdatahtml = "";
			var vendatahtml = "";
		 	var months = ["JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"];
			var tranxtype = ["NONE","PURCHASE","SALE","PURCHASE RETURN","SALE RETURN","CANCELLED SALE","CANCELLED PUR","CANCELLED SR","CANCELLED PR"];
			var ptranxtype = ["NONE","PURCHASE","SALE","PURCHASE RETURN","SALE RETURN","NCDBC SALE","RC","TV","CANCELLED SALE","CANCELLED PUR","CANCELLED SR","CANCELLED PR"];
			//	var pwtranxtype = ["NONE","PURCHASE","SALE","PAYMENT","RECEIPT","SALES RETURN","CREDIT NOTE","DEBIT NOTE","PURCHASE RETURN","RECEIPT REVERSAL","PAYMENT REVERSAL","PUR CANCELLED","PR CANCELLED","SALE CANCELLED","SR CANCELLED","RECEIPT CANCELLED","PAYMENT CANCELLED","CN CANCELLED","DN CANCELLED","PAYMENT"];
			var pwtranxtype = ["NONE","PURCHASE","SALE","RECEIPT","PAYMENT","CREDIT NOTE","DEBIT NOTE","PURCHASE RETURN","SALES RETURN"];
			var taxtranxtype = ["NONE","PAYMENT TO GST ACCOUNT","REVERSAL OF PAYMENT TO GST ACCOUNT","GST AMOUNT PAID"];
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
			<!--Header End--->
      		
      		<div class="content-wrapper">
        		<div class="page-title">
          			<div>
            			<h1>LEDGER REPORT</h1>
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
		  				<form id="ledger_search_form" name="" method="post" action="ReportsDataControlServlet">
							<input type="hidden" id="agencyId" name="agencyId" value="<%= agencyVO.getAgency_code() %>">
							<input type="hidden" id="page" name="page" value="jsp/pages/erp/reports/transactionalReports/ledger.jsp">
							<input type="hidden" id="actionId" name="actionId" value="7202">
							<input type="hidden" id="stype" name="stype" value="">
							<input type="hidden" id="cvotype" name="cvotype" value="">
   							<div class="col-md-2 reprt rep_div">
  								<label>FROM DATE </label>
  								<input type="date" class="form-control input_field rep_input_field" id="fd" name="fd">
 							</div>
 							<div class="col-md-2 reprt rep_div">
  								<label>TO DATE </label>
  								<input type="date" class="form-control input_field rep_input_field" id="td" name="td">
 							</div>
 							<div class="col-md-2 reprt rep_div">
  								<label>SELECT TYPE OF LEDGER </label>
  								<select id="typeofled" onchange="showSelLedgerDivs()" class='form-control input_field select_dropdown rep_input_field'>
  									<option value="0">SELECT</option>
  									<option value="1">PRODUCT-WISE LEDGER</option>
  									<option value="2">PARTY-WISE LEDGER</option>
  									<option value="3">CASH LEDGER</option>
 									<option value="4">TAX LEDGER</option>
  								</select>
 							</div>

 							<div class="col-md-2 reprt" id="prodwiseLedger" style="display:none;">
  								<label>PRODUCT </label>
  								<span id="pitems" name="pitems"></span>
 							</div>
 							<div class="col-md-2 reprt" id="partywiseLedger" style="display:none;">
 								<label>PARTY NAME </label>
  								<span id="pitemsv" name="pitemsv"></span>
 							</div>
 							 <div class="col-md-2 reprt" id="taxwiseLedger" style="display:none;">
 								<label>SELECT TAX TYPE </label>
								<select name='taxtype' class='form-control input_field select_dropdown' id='taxtype'>
									<option value="0">SELECT</option>
									<option value="1">GST</option>
								</select>
 							</div>
 						</form>	
  						<button onclick="fetchLedgerReport(ledger_search_form)" id="searchb" name="searchb" class="btn btn-info color_btn" style="margin-top:20px;">SEARCH</button>
		  			</div>
				</div>
				<br>
				<div class="row">
	     			<div class="clearfix"></div>
	     		 	<div class="col-md-10">

	      				<div id="p_data_table" style="display: none;">
	      					<span id="obSpan"></span>
							<br><br>
							<div>
								<p><span id="fromd" >&nbsp <b>FROM DATE:</b></span>&nbsp&nbsp&nbsp&nbsp&nbsp<%  out.println( request.getAttribute("fdate")); %>
        	      				&nbsp&nbsp&nbsp&nbsp&nbsp <span id="tdate"><b>TO DATE:</b></span>&nbsp&nbsp&nbsp&nbsp&nbsp<% out.println( request.getAttribute("tdate")); %>
        	      				&nbsp&nbsp<b>PRODUCT SELECTED :</b> <span id="prod_selected"></span>
							</div>
	       					<table class="table table-striped ledgrtable" id="m_data_table">
        	      				<thead>
        	      					<tr class="title_head">
										<th width="10%" class="text-center sml_input">INVOICE <br> DATE</th>
										<th width="10%" class="text-center sml_input">INV NO</th>
										<th width="10%" class="text-center sml_input">TRANSACTION TYPE</th>
										<th width="10%" class="text-center sml_input">QUANTITY</th>
										<th width="10%" class="text-center sml_input">PARTY NAME</th>
										<th width="10%" class="text-center sml_input">BALANCE<br>(FULLS)</th>
										<th width="10%" class="text-center sml_input">BALANCE<br>(EMPTYS)</th>
 								 		<th width="10%" class="text-center sml_input">DISCOUNT</th>
 									</tr>
								</thead>
								<tbody id="p_data_table_body"></tbody>
							</table>
			  			</div>

			  			<div id="pw_data_table" style="display: none;">
							<br>
							<span id="obcvSpan"></span>
							<br><br>
							<div>
								<p><span id="fromd" >&nbsp <b>FROM DATE:</b></span>&nbsp&nbsp&nbsp&nbsp&nbsp<%  out.println( request.getAttribute("fdate")); %>
        	      				&nbsp&nbsp&nbsp&nbsp&nbsp <span id="tdate"><b>TO DATE:</b></span>&nbsp&nbsp&nbsp&nbsp&nbsp<% out.println( request.getAttribute("tdate")); %>
        	      				&nbsp&nbsp<b>PARTY  SELECTED :</b> <span id="customer_selected"></span>
							</div>
							<table id="pw_data_table" class="table table-striped ledgrtable">
								<thead>
									<tr class="title_head">
										<th width="10%" class="text-center sml_input">INVOICE <br> DATE</th>
										<th width="10%" class="text-center sml_input">INV NO</th>
										<th width="10%" class="text-center sml_input">TRANSACTION TYPE</th>
										<th width="10%" class="text-center sml_input">DEBIT AMOUNT</th>
										<th width="10%" class="text-center sml_input">CREDIT AMOUNT</th>
										<th width="10%" class="text-center sml_input">CLOSING BALANCE</th>
										<th width="10%" class="text-center sml_input">DISCOUNT</th>	
									</tr>
								</thead>
								<tbody id="pw_data_table_body"></tbody>
							</table>
							<br>
						</div>
						
						<div id="tax_data_table" style="display: none;">
	      					<span id="obtSpan"></span>
							<br><br>
							<div>
								<p><span id="fromd" >&nbsp <b>FROM DATE:</b></span>&nbsp&nbsp&nbsp&nbsp&nbsp<%  out.println( request.getAttribute("fdate")); %>
        	      				&nbsp&nbsp&nbsp&nbsp&nbsp <span id="tdate"><b>TO DATE:</b></span>&nbsp&nbsp&nbsp&nbsp&nbsp<% out.println( request.getAttribute("tdate")); %>
        	      				&nbsp&nbsp<b>TAX TYPE SELECTED :</b> <span id="tax_selected"></span>
							</div>
	       					<table class="table table-striped ledgrtable" id="tax_data_table">
        	      				<thead>
        	      					<tr class="title_head">
										<th width="10%" class="text-center sml_input">TRANSACTION <br> DATE</th>
										<th width="10%" class="text-center sml_input">TRANSACTION TYPE</th>
										<th width="10%" class="text-center sml_input">CREDIT AMOUNT</th>
										<th width="10%" class="text-center sml_input">DEBIT AMOUNT</th>
										<th width="10%" class="text-center sml_input">BALANCE</th>
 									</tr>
								</thead>
								<tbody id="tax_data_table_body"></tbody>
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
    <script type="text/javascript">
    	if((pwreport_data.length>0) || (preport_data.length>0)){
    		pcode = <%= request.getAttribute("pcodetype")%>;
   	    	ptycode = <%= request.getAttribute("ptycodetype")%>;
   		}
    
 		var agency_oc = <%= agencyVO.getAgency_oc() %>;
 		var omc_name;
 		if(agency_oc==1)
			omc_name = "IOCL";
		else if(agency_oc==2)
			omc_name = "HPCL";
		else if(agency_oc==3)
			omc_name = "BPCL";
    </script>
	<script type="text/javascript" src="js/modules/reports/transactionalReports/ledger.js?<%=randomUUIDString%>"></script>
	
	
<script type="text/javascript">
	document.getElementById("nameSpan").innerHTML = <%= agencyVO.getAgency_code() %>;
	domain=document.getElementById("prod_selected").innerHTML;
	domain1=document.getElementById("customer_selected").innerHTML;
	document.title += domain;
	document.title += domain1;

	$(document).ready(function() {
	$('.ledgrtable').DataTable( {
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
            } ,*/
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
<%--   var nors =<%= noOfRecsBeforeFdate %>; --%>

 </script>
</html>
