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
	    <script type="text/javascript" src="https://cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js"></script>
		<link href="https://cdn.datatables.net/1.10.15/css/jquery.dataTables.min.css" rel="stylesheet" type="text/css">
		<title>MyLPGBooks DEALER APPLICATION - BANK BOOK</title>
		<jsp:useBean id="agencyVO" scope="session" class="com.it.erpapp.framework.model.vos.AgencyVO"></jsp:useBean>
		<jsp:useBean id="partners_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="bank_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="spid" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="car_data" scope="request" class="java.lang.String"></jsp:useBean>
		<script type="text/javascript">
			var months = ["JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"];
			var years = ["2018","2019","2020"];
			var partnr= "";
			var prtrsec = "";
			var bank_data = <%= bank_data.length()>0? bank_data : "[]" %>;
			var partners_data =  <%= partners_data.length()>0? partners_data : "[]" %>;
			var page_data = <%= car_data.length()>0? car_data : "[]" %>;
			var pttype = ["","INFUSION","WITHDRAWAL","WRITE-OFF"];
			var spid = <%= spid %>;
		</script>
<%-- 		<script src="js/commons/checkBrowser.js?<%=randomUUIDString%>"></script> --%>

		<!-- Sidenav -->
		<jsp:include page="/jsp/pages/commons/sidenav.jsp"/>    
	 	<!---END Sidenav--->
	 	
		<script type="text/javascript"> 
			window.onload = setWidthHightNav("100%");
		</script>
	<script>
	//Construct bank html
		bankdatahtml = "<OPTION VALUE='0'>SELECT</OPTION>";
		if(bank_data.length>0) {
			for(var z=0; z<bank_data.length; z++){
				var bc = bank_data[z].bank_code;
			if(!(bc=="TAR ACCOUNT" || bc=="STAR ACCOUNT")) {
			bankdatahtml=bankdatahtml+"<OPTION VALUE='"+bank_data[z].id+"'>"+bank_data[z].bank_code+" - "+bank_data[z].bank_acc_no+"</OPTION>";
			}
		}
	}

	//Construct partner html
		partnerdatahtml = "<OPTION VALUE='0'>SELECT</OPTION>";
		if(partners_data.length>0) {
			for(var z=0; z<partners_data.length; z++){
			partnerdatahtml=partnerdatahtml+"<OPTION VALUE='"+partners_data[z].id+"'>"+partners_data[z].partner_name+"</OPTION>";
			}
		}
		//document.getElementById("pnSpan").innerHTML = "<select id='pid' name='pid'>"+partnerdatahtml+"</select>";
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
            			<h1>CAPITAL ACCOUNT SEARCH  </h1>
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
							<form id="ca_form" name="ca_form" method="post" action="ReportsDataControlServlet">
								<input type="hidden" id="agencyId" name="agencyId" value="<%= agencyVO.getAgency_code() %>">
								<input type="hidden" id="page" name="page" value="jsp/pages/erp/reports/finreports/capitalAcct/captlacs.jsp">
								<input type="hidden" id="actionId" name="actionId" value="8522">
 								<div class="col-md-3 reprt">
  									<label>FINANCIAL YEAR </label>
  								<select class="form-control input_field select_dropdown"   id="sfy" name="sfy">
						  			<option value = "00">Select</option>
						  			<option value = "2018-2019">2018-19</option>
						  			<option value = "2019-2020">2019-20</option>
						  			<option value = "2020-2021">2020-21</option>						  			
								</select>
 								</div>
 								<div class="col-md-3 reprt">
  									<label>PARTNER NAME </label>
  									<select id='pid' name='pid' class="form-control input_field select_dropdown">
  									<%  String pid="<script>document.writeln(partnerdatahtml)</script>";
										out.println("value: "+pid); %>
  									</select>
 								</div>          
 							</form>	
  								<button onclick="fetchCapitalAccount()" id="searchb" name="searchb"  style="margin-top:20px;" class="btn btn-info color_btn" value="SEARCH" style="margin-top:20px;">SEARCH</button>
		  				</div>
					</div>
					<br>
					<div class="row">
	     				<div class="clearfix"></div>
	      				<div class="col-md-12"  style = "padding-left : 13px; padding-right : 13px;">
	      					<div id="ca_data_div" style="display: none;">
	      						<p><span id="fdate" >YEAR:</span>&nbsp&nbsp<%  out.println( request.getAttribute("year")); %>
        	      				<%-- &nbsp&nbsp <span id="type_selected">PARTNER SELECTED:</span>&nbsp&nbsp<% out.println( request.getAttribute("partnr")); %><p> --%>
        	      			
	      					<br>
	      					CAPITAL ACCOUNT OF : <span id="spnSpan"></span>
	            				<table class="table table-striped" id="bb_report_table">
        	      					<thead>
            	    					<tr class="title_head">
											<th width="10%" class="text-center sml_input">DATE</th>
											<th width="10%" class="text-center sml_input">PARTICULARS</th>
											<th width="10%" class="text-center sml_input">DEBIT</th>
											<th width="10%" class="text-center sml_input">CREDIT</th>
											<th width="10%" class="text-center sml_input">BALANCE</th>
					 					</tr>
									</thead>
									<tbody id="data_table_body"></tbody>
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
   	<script>
	   	var yearsrch = <%=request.getAttribute("year")%>;
   	</script>
	<script type="text/javascript" src="js/modules/reports/finreports/capitalAcct/captlacs.js?<%=randomUUIDString%>"></script>
    <script src="js/commons/main.js?<%=randomUUIDString%>"></script>
	<script type="text/javascript">
	document.getElementById("nameSpan").innerHTML = <%= agencyVO.getAgency_code() %>;
	document.getElementById('ca_form').agencyId = <%= agencyVO.getAgency_code() %>;  
	$(document).ready(function() {
   	    $('#bb_report_table').DataTable( { 
//   	    	"bPaginate" : $('#m_data_table tbody tr').length>5,
//	   	    "iDisplayLength": 5,
			"lengthMenu": [[5,10, 25, 50, -1], [5,10, 25, 50, "All"]],			
			"bFilter": false,
   	    	"ordering": false,
   	    	"scrollY":'50vh',
			"scrollCollapse": true,               
   	        "scrollX": true,
            language: {
                paginate: {
                	next: '&#x003E;&#x003E;',
                    previous: '&#x003C;&#x003C;'
                }
              }
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
	
	
	/* $(document).ready(function() {
    	    $('#bb_report_table').DataTable( {
    	    	"bFilter": false,
    	    	"ordering": false,
       	    	"scrollY":'50vh',
    			"scrollCollapse": true,
       	        "scrollX": true,
	            language: {
                    paginate: {
                    	next: '&#x003E;',
                        previous: '&#x003C;'
                    }
                  }
    	    } );
    	} );    */    
	</script>
</html>