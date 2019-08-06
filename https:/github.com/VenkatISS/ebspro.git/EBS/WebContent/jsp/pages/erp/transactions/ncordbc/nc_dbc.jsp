<%@ page import="java.util.Map, java.util.UUID" %>
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
    	<!-- CSS-->
    	<link rel="stylesheet" type="text/css" href="css/main.css?<%=randomUUIDString%>">
    	<title>NC / DBC DATA  </title>
		<jsp:useBean id="agencyVO" scope="session" class="com.it.erpapp.framework.model.vos.AgencyVO"></jsp:useBean>
		<jsp:useBean id="cylinder_types_list" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="arb_types_list" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="rcs_data" scope="request" class="java.lang.String"></jsp:useBean>	
		<jsp:useBean id="staff_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="equipment_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="services_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="arb_data" scope="request" class="java.lang.String"></jsp:useBean>	
		<jsp:useBean id="refill_prices_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="arb_prices_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="prod_types_list" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="bom_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="page_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="bank_data" scope="request" class="java.lang.String"></jsp:useBean>
		<script type="text/javascript" src="js/modal.js?<%=randomUUIDString%>"></script>
		<script type="text/javascript" src="js/commons/general_validations.js?<%=randomUUIDString%>"></script>
		<script src="js/commons/jquery-2.1.4.min.js?<%=randomUUIDString%>"></script>
		<script type="text/javascript">
			var cat_cyl_types_data = <%= cylinder_types_list %>;
			var cat_arb_types_data = <%= arb_data %>;
			var arb_data = <%= arb_data.length()>0? arb_data : "[]" %>;
			var page_data = <%= page_data.length()>0? page_data : "[]" %>;
			var staff_data = <%= staff_data.length()>0? staff_data : "[]" %>;
			var equipment_data = <%= equipment_data.length()>0? equipment_data : "[]" %>;
			var services_data = <%= services_data.length()>0? services_data : "[]" %>;
			var refill_prices_data = <%= refill_prices_data.length()>0? refill_prices_data : "[]" %>;
			var arb_prices_data = <%= arb_prices_data.length()>0? arb_prices_data : "[]" %>;
			var prod_types = <%= prod_types_list %>;
			var bom_data = <%= bom_data %>;
			var bank_data = <%= bank_data.length()>0? bank_data: "[]"%>;
			var ctdatahtml = "";
			var rtdatahtml = "";
			var vendordatahtml = "";
			var staffdatahtml = "";
			var bomdatahtml = "";
			var bankdatahtml = "";
			var months = ["JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"];
			//Construct BOM Data
			bomdatahtml = "<OPTION VALUE='0'>SELECT</OPTION>";
				if(bom_data.length>0) {
					for(var z=0; z<bom_data.length; z++){
						bomdatahtml=bomdatahtml+"<OPTION VALUE='"+bom_data[z].id+"'>"+bom_data[z].bom_name+"</OPTION>";
				}
			}
				
			//Construct bank html
			bankdatahtml = "<OPTION VALUE='0'>SELECT</OPTION>";
			if(bank_data.length>0) {
				for(var z=0; z<bank_data.length; z++) {
					var bc = bank_data[z].bank_code;
					if(!(bc=="TAR ACCOUNT" || bc=="STAR ACCOUNT" || bc=="LOAN") && bank_data[z].deleted == 0) {
						bankdatahtml=bankdatahtml+"<OPTION VALUE='"+bank_data[z].id+"'>"+bank_data[z].bank_code+" - "+bank_data[z].bank_acc_no+"</OPTION>";
					}
				}
			}
			
			function tableAlternateRow() {
	    		if(document.getElementsByTagName){
					var table = document.getElementById("m_data_table");
		    		var rows = table.getElementsByTagName("tr");
		    		var rl=rows.length;
		    		var rowno=rl-rl+1;
		    		for(j=0;j<page_data.length;j++){
		    			var length=page_data[j].detailsList.length;
		        		if(length>1){
		        			if(j % 2 == 0){
		            			for(k=0;k<length;k++){
		                			rows[rowno].className = "even";
		                    		rowno++;
		                 		}
		            		}else{
		            			for(h=0;h<length;h++){
		                			rows[rowno].className = "odd";
		                    		rowno++;
		                		}
		            		}
		        		}else{
		        			if(j % 2 == 0){
		            			rows[rowno].className = "even";
		            			rowno++;
		            		}else{
		            			rows[rowno].className = "odd";
		                		rowno++;
		            		}
		        		}
		    		}
				}
			}
			if (window.addEventListener) {
				window.addEventListener("load", tableAlternateRow, false);
			} else if (window.attachEvent) {
				window.attachEvent("onload", tableAlternateRow);
			} else {
	    		window.onload = tableAlternateRow;
			}
		</script>
<%-- 		<script src="js/commons/checkBrowser.js?<%=randomUUIDString%>"></script> --%>

		<!-- Sidenav -->
		<jsp:include page="/jsp/pages/commons/sidenav.jsp"/>    
		<!---END Sidenav--->

		<script type="text/javascript"> 
			window.onload = setWidthHightNav("100%");
		</script>		
		<style>
			table tr.even {
    			background: #fff;
			}
			table tr.odd {
    			background: #eeeeee;
			}
		</style>
		<style>
			.table>tbody>tr>td, .table>tbody>tr>th, .table>tfoot>tr>td, .table>tfoot>tr>th, .table>thead>tr>td, .table>thead>tr>th {
    			padding: 12px;
    			line-height: 1.42857143;
    			vertical-align: top;
    			border-top: 0px solid #ddd;
		</style>
  	</head>
  	<body class="sidebar-mini fixed">
    	<div class="wrapper">
		<!-- Header-->
		<jsp:include page="/jsp/pages/commons/header.jsp"/>      
	  	<!--header End--->

	  	<div class="content-wrapper">
        	<div class="page-title">
          		<div><h1>NC / DBC DATA </h1></div>
					<ul id="nav" class="nav nav-pills clearfix right" role="tablist">
          				<li class="dropdown"><span class="dropdown-toggle  btn-info" data-toggle="dropdown" id="ahelp">Help</span>
							<ul id="products-menu" class="dropdown-menu clearfix" role="menu">
								<li><a style="font-size: 14px;" title="NC and DBC" href="https://www.youtube.com/watch?v=2baAQjsO8xA" target="_blank">English</a></li>
								<!-- <li><a style="font-size: 14px;" href="https://www.youtube.com/watch?v=2baAQjsO8xA" target="_blank">Hindi</a></li> -->
							</ul>
						</li>
					</ul>
        	</div>
        	<div>
	        	<div class="col-sm-3">
					<select class="form-control input_field select_dropdown" id="bom_id" name="bom_id">		
						<%  String bomdata="<script>document.writeln(bomdatahtml)</script>";
							out.println("value: "+bomdata);
						%>
					</select>
				</div>
				<div class="clearfix"></div>
				<div>
 					<button name="b_sb" id="b_sb" class="btn btn-info color_btn bg_color4" onclick="showNCDBCFormDialog(),showLoanAmountBox()">ADD NC / DBC</button>
					<button name="cancel_data" id="cancel_data"	class="btn btn-info color_btn bg_color2" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/home.jsp','2001')">BACK</button>
					<button name="bom_data" id="bom_data"	class="btn btn-info color_btn bg_color2" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/masterdata/products/bom_data.jsp','3141')">NC/DBC PACKAGING</button>
				</div>
			</div>
			<div class="modal_popup_add fade in" id="myModal" style="display: none;height: 100%">					
				<div class="modal-dialog modal-lg">
			  		<!-- Modal content-->
			  			<div class="modal-content" id="modal-content">
							<div class="modal-header" id="modal-header">
				  			<span class="close" id="ncClose" onclick="closeNCDBCFormDialog()">&times;</span>
				  			<h4 class="modal-title">NC / DBC DATA  </h4>
						</div>
						<div class="modal-body">
							<form id="data_form" name="" method="post" action="TransactionsDataControlServlet">
								<input type="hidden" id="agencyId" name="agencyId" value="<%= agencyVO.getAgency_code() %>">
								<input type="hidden" id="page" name="page" value="jsp/pages/erp/transactions/ncordbc/nc_dbc.jsp">
								<input type="hidden" id="actionId" name="actionId" value="5722">
								<input type="hidden" id="bomId" name="bomId" value="">
								<input type="hidden" id="bankId" name="bankId" value="">
								<input type="hidden" id="tdup" name="tdup" value="">
								
								<div class="row">
									<div class="col-sm-8">
										<div class="animated-radio-button" style="margin-top:3%;">
											<label>CONNECTION TYPE</label>&nbsp;&nbsp;
											<label><input type="radio" id="nctype" name="ctype" value="1" readonly="readonly"><span class="label-text">GEN NC</span></label>&nbsp;&nbsp;
											<label><input type="radio" id="ncULtype" name="ctype" value="3" readonly="readonly"><span class="label-text">UJWAL LOAN NC</span></label>&nbsp;&nbsp;
											<label><input type="radio" id="ncUCtype" name="ctype" value="5" readonly="readonly"><span class="label-text">UJWAL CASH</span></label>&nbsp;&nbsp;
											
											<label><input type="radio" id="bpltype" name="ctype" value="4" readonly="readonly"><span class="label-text">BPL</span></label>&nbsp;&nbsp;
											<label><input type="radio" id="dbctype" name="ctype" value="2" readonly="readonly"><span class="label-text">DBC</span></label>
										</div>
									</div>
									<br/><br/><br/>
									<div class="col-adj-res-sub" style="margin-top:-2%;">
										<label>INVOICE DATE </label>
										<input type="date" class="form-control input_field"  id="inv_date" name="inv_date" placeholder="DD-MM-YY">
									</div>
									<div class="col-adj-res-sub" style="margin-top:-2%;">
										<label>STAFF NAME  </label>
										<span id="sSpan"></span>
									</div>
									<div class="col-adj-res-sub" style="margin-top:-2%;">
										<label>CUST NO / CUST NAME</label>
										<input type="text" class="form-control input_field" id="custn" name="custn"  maxlength="20" placeholder="CUST NO / CUST NAME">
									</div>	
									<div class="col-adj-res-sub" style="margin-top:-2%;">
										<label>INVOICE AMOUNT</label>
										<input type="text" class="form-control input_field" id="invamt" name="invamt" value="" readonly="readonly" style="background-color:#F3F3F3;" placeholder="INV AMOUNT">
										<input type="hidden" id="tdamt" name="tdamt" value="">
									</div>
									<div class="col-adj-res-sub" style="margin-top:2%;">
										<label>NO. OF CONNECTIONS  </label>
										<input type="text" class="form-control input_field freez" id="nos" name="nos"  maxlength="3" placeholder="NO. OF CONNECTIONS">
									</div>
									<div class="col-adj-res-sub" style="margin-top:2%;">
										<label>CASH RECEIVED </label>
										<input type="text" class="form-control input_field" id="cashr" name="cashr" placeholder="CASH RECEIVED">
									</div>
									<div class="col-adj-res-sub" id="aro" style="margin-top:2%;">
										<label style="min-width: 200px;">AMOUNT RECEIVED ONLINE </label>
										<input type="text" class="form-control input_field" id="aro" name="aro" style="min-width: 190px;" placeholder="AMOUNT RECEIVED ONLINE ">
									</div>
									<!-- ---------------------------------------------------------------------------------------------------------------/ -->
									 <div class="col-adj-res-sub" id="ujwalLoan" style="display:none;margin-top:2.5%">
							    			<span id="uloanSpan"></span>
									</div>
									<div class="col-adj-res-sub" id="bankdiv" style="margin-top:2%;">
										<label>BANK ACCOUNT  </label>
										<select name="bank_id" id="bank_id" class="form-control input_field select_dropdown sadd">
                  							<%String str4 = "<script>document.writeln(bankdatahtml)</script>";
				   							out.println("value: " + str4);%>
				   						</select>
									</div>														
									<div class="clearfix"></div>
								</div>
								<br/>
								<div class="row">
          							<div class="clearfix"></div>
          								<div class="col-md-12">
            								<div class="main_table">
              									<div class="table-responsive">
                									<table class="table" id="b_data_table">
                  										<thead>
                    										<tr class="title_head">
										       					<th width="10%" class="text-center sml_input">PRODUCT</th>
					  						   					<th width="10%" class="text-center sml_input">QUANTITY	</th>
                      						   					<th width="10%" class="text-center sml_input">UNIT RATE</th>
					  						   					<th width="10%" class="text-center sml_input">GST %	</th>
					   						   					<th width="10%" class="text-center sml_input">DEPOSIT</th>
					    					   					<th width="10%" class="text-center sml_input">DISCOUNT ON UNIT PRICE</th>
											   					<th width="10%" class="text-center sml_input">TAXABLE VALUE</th>
						 					   					<th width="10%" class="text-center sml_input">CGST AMOUNT</th>
						 					   					<th width="10%" class="text-center sml_input">SGST AMOUNT</th>
						    				   					<th width="10%" class="text-center sml_input">PRODUCT AMOUNT</th>
															</tr>
                  										</thead>
                  										<tbody id="b_data_table_body"></tbody>
                									</table>
             	 								</div>
            								</div>
            							</div>
            						</div>
            					</form>
									<div class="row">	
										<div class="clearfix"></div><br/>
											<div class="col-md-3 cmd3">
												<button class="btn btn-info color_btn" name="fetch_data" id="fetch_data" onclick="fetchURGSTDepositDetails()">FETCH DEPOSITS AND CHARGES</button>
											</div>
											<div class="col-md-2">
												<button class="btn btn-info color_btn" name="calc_data" id="calc_data"  onclick="calculateValues()" disabled="disabled">CALCULATE</button>
											</div>
											<div class="col-md-2">
												<button class="btn btn-info color_btn bg_color2" name="save_data" id="save_data"  onclick="saveData(this)" disabled="disabled">SAVE</button>
											</div>
										</div>
        	  						</div>					
		    					</div>	
	      					</div>
        				</div>
						<div class="row">
          					<div class="clearfix"></div>
         					<div class="col-md-12">
								<h1 class="table_title">NC / DBC DATA  </h1>
           	 					<div class="main_table">
              						<div class="table-responsive">
              							<form id="m_data_form" name="" method="post" action="MasterDataControlServlet">
											<input type="hidden" id="agencyId" name="agencyId" value="<%= agencyVO.getAgency_code() %>">
											<input type="hidden" id="page" name="page" value="jsp/pages/erp/transactions/ncordbc/nc_dbc.jsp">
											<input type="hidden" id="actionId" name="actionId" value="">
											<input type="hidden" id="dataId" name="dataId" value="">
											<input type="hidden" id="bankId" name="bankId" value="">
                							<table class="table" id="m_data_table">
                  								<thead>
                    								<tr class="title_head">
					  									<th width="10%" class="text-center sml_input">INV NO</th>
                      									<th width="10%" class="text-center sml_input">DATE</th>
					  									<th width="10%" class="text-center sml_input">STAFF NAME</th>
                      									<th width="10%" class="text-center sml_input">CUST NO / CUST NAME</th>
					  									<th width="10%" class="text-center sml_input">INV AMOUNT</th>
					  									<th width="10%" class="text-center sml_input">NO. OF CONNS</th>
					  									<th width="10%" class="text-center sml_input">CASH AMOUNT</th>
                      									<th width="10%" class="text-center sml_input">ONLINE AMOUNT</th>
                      									<th width="10%" class="text-center sml_input">LOAN AMOUNT</th>
					  									<th width="10%" class="text-center sml_input">BANK ACCOUNT</th>
					  									<th width="10%" class="text-center sml_input">PRODUCT / SERVICE</th>
                      									<th width="10%" class="text-center sml_input">TOTAL QTY</th>
					  									<th width="10%" class="text-center sml_input">DEPOSIT PER UNIT</th>
					  									<th width="10%" class="text-center sml_input">UNIT RATE</th>
					  									<th width="10%" class="text-center sml_input">DISCOUNT ON UNIT RATE</th>
					  									<th width="10%" class="text-center sml_input">TOTAL CGST AMOUNT</th>
				      									<th width="10%" class="text-center sml_input">TOTAL SGST AMOUNT</th>
					  									<th width="10%" class="text-center sml_input">TOTAL PRODUCT AMOUNT</th>  
					  									<th width="10%" class="text-center sml_input">Actions</th>					
                    								</tr>
                  								</thead>
                  								<tbody id="m_data_table_body"></tbody>
                							</table>
                						</form>
              						</div>
            					</div>
          					</div>
        				</div>
      				</div>
    			</div>
  			</body>
  			<div id = "dialog-1" title="Alert(s)"></div>
 			<div id="dialog-confirm"><div id="myDialogText" style="color:black;"></div></div>
     		<!-- Javascripts-->
			<script src="js/commons/bootstrap.min.js?<%=randomUUIDString%>"></script>
			<script src="js/commons/plugins/pace.min.js?<%=randomUUIDString%>"></script>
    		<script src="js/commons/main.js?<%=randomUUIDString%>"></script>
			<script type="text/javascript" src="js/modules/transactions/ncordbc/nc_dbc.js?<%=randomUUIDString%>"></script>
    		<script type="text/javascript">
				document.getElementById("nameSpan").innerHTML = <%= agencyVO.getAgency_code() %>;
				var dedate = <%=agencyVO.getDayend_date()%>;
		     	var effdate = <%=agencyVO.getEffective_date()%>;
				var a_created_date = <%=agencyVO.getCreated_date()%>;				

				<% Map<String,Object> details = (Map<String,Object>) session.getAttribute("details"); %>
				var cashcb = <%=details.get("cashBal") %>;
				
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