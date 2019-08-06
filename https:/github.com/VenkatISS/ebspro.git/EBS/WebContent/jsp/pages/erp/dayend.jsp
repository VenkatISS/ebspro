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
	    <title>MyLPGBooks APPLICATION - DAY END REPORTS</title>
		<jsp:useBean id="agencyVO" scope="session" class="com.it.erpapp.framework.model.vos.AgencyVO"></jsp:useBean>
		<jsp:useBean id="rder" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="sded" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="cylinder_types_list" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="arb_types_list" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="arb_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="bank_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="prod_de_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="bbr_data" scope="request" class="java.lang.String"></jsp:useBean>
	    <script src="js/commons/jquery-2.1.4.min.js?<%=randomUUIDString%>"></script>
		
		<script type="text/javascript">
			var rder = <%= rder %>;
			var sded = <%= sded.length()>0? sded : "[]" %>;
			var cylinder_types_list = <%= cylinder_types_list.length()>0? cylinder_types_list : "[]" %>;
			var arb_types_list = <%= arb_types_list.length()>0? arb_types_list : "[]" %>;
			var arb_data = <%= arb_data.length()>0? arb_data : "[]" %>;
			var bank_data =  <%= bank_data.length()>0? bank_data : "[]" %>;
			var prod_de_data = <%= prod_de_data.length()>0? prod_de_data : "[]" %>;
			var bbr_data = <%= bbr_data.length()>0? bbr_data : "[]" %>;
			var mops = ["NONE","CASH","CHEQUE","ONLINE TRANSFER"];
//			var tts = ["NONE","PAYMENT","RECEIPT","CASH DEPOSIT","CASH WITHDRAWL"];
			var tts = ["NONE","PAYMENT","RECEIPT","CASH DEPOSIT","CASH WITHDRAWL","TRANSFER","RECEIPT REVERSAL","PAYMENT REVERSAL","TRANSACTION CANCELLED","GST PAYMENT","INCOME TAX PAYMENT","GST PAYMENT REVERSAL","INCOME TAX PAYMENT REVERSAL","CASH DEPOSIT CANCELLED","CASH WITHDRAWL CANCELLED"];
			var ahs = ["NONE","CAPITAL ACCOUNT","EXPENDITURE","BANK CHARGES","SALARIES/WAGES","LOANS/ADVANCES","STAR","TAR"];
			var months = ["JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"];
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
          			<div><h1>DAY END REPORT </h1></div>
<!-- 					<a href="https://youtu.be/bgQnKRrz2X8" target="_blank" style="text-decoration: underline; float:right;margin-right: 5px;">help?</a> -->

					<ul id="nav" class="nav nav-pills clearfix right" role="tablist">
          				<li class="dropdown"><span class="dropdown-toggle  btn-info" data-toggle="dropdown" id="ahelp">Help</span>
							<ul id="products-menu" class="dropdown-menu clearfix" role="menu">
								<li><a href="files/MyLPGBooks Promo.mp4" target="_blank">English</a></li>
								<!-- <li><a style="font-size: 14px;" href="https://youtu.be/bgQnKRrz2X8" target="_blank">Hindi</a></li> -->
							</ul>
						</li>
					</ul>

        		</div>
				<br>
				<div class="row">
					<div class="clearfix"></div>
					<div class="col-md-10">
  						<label>LAST DAY END REPORT SUBMITTED DATE :</label>
  						<span id="ldedspan"></span> 
 					</div>
 				</div>
 				<br>
        		<div class="row">
		        	<div class="clearfix"></div>
		          	<div class="col-md-10">
		  				<form id="dayend_search_form" name="" method="post" action="ReportsDataControlServlet">
							<input type="hidden" id="agencyId" name="agencyId" value="<%= agencyVO.getAgency_code() %>">
							<input type="hidden" id="page" name="page" value="jsp/pages/erp/dayend.jsp"">
							<input type="hidden" id="actionId" name="actionId" value="8002">
   							<div class="col-md-2 reprt">
  								<label></label>
   								<input type="text" class="form-control input_field" id="fd" name="fd" readonly>
  								<!-- <input type="date" class="form-control input_field" id="fd" name="fd"> -->
 							</div>
  						</form>
<!--   						<button onclick="searchData()" id="searchb" name="searchb" class="btn btn-info color_btn" style="margin-top: 20px;">GENERATE</button> -->
  						<button onclick="searchData()" id="searchb" name="searchb" class="btn btn-info color_btn" style="margin-top: 16px;">GENERATE</button>
		  			</div>
				</div>
				<br>
				<div class="row">
		        	<div class="clearfix"></div>
		          	<div class="col-md-10" id="de_date_div" style="display: none;">
						<span id="sdedspan"></span>
						<form id="submit_de_form" name="" method="post" action="ReportsDataControlServlet">
							<input type="hidden" id="page" name="page" value="jsp/pages/erp/dayend.jsp">
							<input type="hidden" id="actionId" name="actionId" value="8003">
							<input type="hidden" id="deds" name="deds" value="">
						</form>
						<input type="button" value="SUBMIT" class="btn btn-info color_btn" onclick="confirmDER(submit_de_form)">
					</div>
				</div>
				<br>
				<div class="row">
	     			<div class="clearfix"></div>
	     		 	<div class="col-md-12">
	      				<div id="cyld_data_div" style="display: none;">
	      				<label>CYLINDER STOCK DETAILS</label>
	       					<div class="main_table">
	         					<div class="table-responsive">
	            					<table class="table table-striped" id="cyld_report_table">
        	      						<thead>
            	    						<tr class="title_head">
												<th width="10%" class="text-center sml_input">PRODUCT</th>
												<th width="10%" class="text-center sml_input">OPENING<br>FULL STOCK</th>
												<th width="10%" class="text-center sml_input">PURCHASE<br>QUANTITY</th>
												<th width="10%" class="text-center sml_input">PURCHASE<br>RETURNS</th>
												<th width="10%" class="text-center sml_input">SALES<br>RETURNS</th>												
												<th width="10%" class="text-center sml_input">NC/DBC/RC<br>QUANTITY</th>
												<th width="10%" class="text-center sml_input">SOLD <br>QUANTITY</th>
												<th width="10%" class="text-center sml_input">CLOSING <br>FULL STOCK</th>
					 						</tr>
										</thead>
										<tbody id="cyld_report_table_body"></tbody>
									</table>
			  					</div>
            				</div>
          				</div>
        			</div>
      			</div>
      			<br>
      			<div class="row">
	     			<div class="clearfix"></div>
	     		 	<div class="col-md-12">
	      				<div id="arb_data_div" style="display: none;">
	      				<label>ARB STOCK DETAILS</label>
	       					<div class="main_table">
	         					<div class="table-responsive">
	            					<table class="table table-striped" id="arb_report_table">
        	      						<thead>
            	    						<tr class="title_head">
												<th width="10%" class="text-center sml_input">ARB PRODUCT</th>
												<th width="10%" class="text-center sml_input">OPENING<br>FULL STOCK</th>
												<th width="10%" class="text-center sml_input">PURCHASE<br>QUANTITY</th>
												<th width="10%" class="text-center sml_input">PURCHASE<br>RETURNS</th>
												<th width="10%" class="text-center sml_input">SALES<br>RETURNS</th>												
												<th width="10%" class="text-center sml_input">SOLD <br>QUANTITY</th>
												<th width="10%" class="text-center sml_input">CLOSING <br>FULL STOCK</th>
					 						</tr>
										</thead>
										<tbody id="arb_report_table_body"></tbody>
									</table>
			  					</div>
            				</div>
          				</div>
        			</div>
      			</div>
      			<br>
      			<div class="row">
	     			<div class="clearfix"></div>
	     		 	<div class="col-md-12">
	      				<div id="bb_data_div" style="display: none;">
	      				<label>BANK TRANSACTIONS</label>
	       					<div class="main_table">
	         					<div class="table-responsive">
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
        			</div>
      			</div>
				<div class="clearfix"></div>
				<div class="col-md-12">
					<button class="btn btn-info color_btn bg_color3 srchbtn" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/home.jsp','2001')">BACK</button>	
				</div>
	  		</div>
    	</div>
    </body>
    <div id = "dialog-1" title="Alert(s)"></div>
 	<div id="dialog-confirm"><div id="myDialogText" style="color:black;height:900px;"></div></div>
	<script src="js/commons/bootstrap.min.js?<%=randomUUIDString%>"></script>
	<script src="js/commons/plugins/pace.min.js?<%=randomUUIDString%>"></script>
    <script src="js/commons/main.js?<%=randomUUIDString%>"></script>
	<script type="text/javascript" src="js/commons/general_validations.js?<%=randomUUIDString%>"></script>
	<script type="text/javascript" src="js/modules/dayend.js?<%=randomUUIDString%>"></script>

    <script type="text/javascript">
        document.getElementById("nameSpan").innerHTML = <%= agencyVO.getAgency_code() %>
        var ldeDate = new Date(<%= agencyVO.getDayend_date() %>);
//        var ldeDate = new Date("Sun May 13,2018");
        document.getElementById("ldedspan").innerHTML = "<b>"+ldeDate.getDate()+"-"+months[ldeDate.getMonth()]+"-"+ldeDate.getFullYear()+"</b>";
		
  		var date = new Date();
    	var checklpyr=checkleapyear(ldeDate.getFullYear());

		if((ldeDate.getMonth()+1)==2) {
			if((checklpyr==true) && (ldeDate.getDate()==29)) {
	    		document.getElementById("fd").value = ldeDate.getFullYear()+"-0"+3+"-0"+1;
			}else if((checklpyr==true) && (ldeDate.getDate()==28)) {
            	document.getElementById("fd").value = ldeDate.getFullYear()+"-0"+2+"-"+29;
			}else {
				date.setDate(ldeDate.getDate()+1);
				date.setMonth(ldeDate.getMonth());
			    date.setFullYear(ldeDate.getFullYear());
			}
		 }else {        	
			date.setFullYear(ldeDate.getFullYear());
			date.setMonth(ldeDate.getMonth());
	        date.setDate(ldeDate.getDate()+1);
		}	
  		function checkleapyear(year){
			return ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0);
		}
  		if(!(((ldeDate.getMonth()+1)==2) && (checklpyr==true) && (ldeDate.getDate()>=28))) {
  			var tdate = date.getDate();  		 
  			var tmonth = date.getMonth()+1;
  			var tyear = date.getFullYear();
  	    	if(tdate<10)
  	    		tdate="0"+tdate;
  	    	if(tmonth<10)                                     
  	    		tmonth="0"+tmonth;

  	    	document.getElementById("fd").value = tyear+"-"+tmonth+"-"+tdate; 
  	    }
  		
  		
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
  		});
    </script>
</html>