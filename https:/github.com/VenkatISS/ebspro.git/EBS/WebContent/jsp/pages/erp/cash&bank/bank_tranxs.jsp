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
    	<script src="js/commons/jquery-2.1.4.min.js?<%=randomUUIDString%>"></script>
	    <script type="text/javascript" src="https://cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js"></script>
		<link href="https://cdn.datatables.net/1.10.15/css/jquery.dataTables.min.css" rel="stylesheet" type="text/css">
		<title>MyLPGBooks DEALER WEB APPLICATION - BANK TRANSACTIONS</title>
		<jsp:include page="/jsp/pages/commons/sidenav.jsp"/>
		<jsp:useBean id="agencyVO" scope="session" class="com.it.erpapp.framework.model.vos.AgencyVO"></jsp:useBean>
        <jsp:useBean id="bt_data" scope="request" class="java.lang.String"></jsp:useBean>
        <jsp:useBean id="bank_data" scope="request" class="java.lang.String"></jsp:useBean>
 		<script type="text/javascript" src="js/modal.js?<%=randomUUIDString%>"></script>

<%-- 		<script src="js/commons/checkBrowser.js?<%=randomUUIDString%>"></script> --%>
	 	<script type="text/javascript"> 
			window.onload = setWidthHightNav("100%");
		</script>
	 	
		<script type="text/javascript">
			var months = ["JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"];
			var years = ["2018","2019","2020"];
			var mops = ["NONE","","CHEQUE","ONLINE TRANSFER"];
			var tts = ["NONE","PAYMENT","RECEIPT","CASH DEPOSIT","CASH WITHDRAWL","TRANSFER","RECEIPT REVERSAL","PAYMENT REVERSAL","TRANSACTION CANCELLED","GST PAYMENT","INCOME TAX PAYMENT","GST PAYMENT REVERSAL","INCOME TAX PAYMENT REVERSAL","CASH DEPOSIT CANCELLED","CASH WITHDRAWL CANCELLED"];
			var bankdatahtml, tbankdatahtml = "";
			var page_data =  <%= bt_data.length()>0? bt_data : "[]" %>;
			var bank_data =  <%= bank_data.length()>0? bank_data : "[]" %>;

			//Construct bank html
			bankdatahtml = "<OPTION VALUE='0'>SELECT</OPTION>"
			if(bank_data.length>0) {
				for(var z=0; z<bank_data.length; z++) {
					var bc = bank_data[z].bank_code;
					if(!(bc=="TAR ACCOUNT" || bc=="STAR ACCOUNT")) {
						bankdatahtml=bankdatahtml+"<OPTION VALUE='"+bank_data[z].id+"'>"+bank_data[z].bank_code+" - "+bank_data[z].bank_acc_no+"</OPTION>";
					}
				}
			}

			//Construct bank html
			nlbankdatahtml = "<OPTION VALUE='0'>SELECT</OPTION>"
			if(bank_data.length>0) {
				for(var z=0; z<bank_data.length; z++) {
					if(bank_data[z].deleted==0) {
						var bc = bank_data[z].bank_code;
						if(!(bc=="TAR ACCOUNT" || bc=="STAR ACCOUNT" || bc=="LOAN")) {
							nlbankdatahtml = nlbankdatahtml+"<OPTION VALUE='"+bank_data[z].id+"'>"+bank_data[z].bank_code+" - "+bank_data[z].bank_acc_no+"</OPTION>";
						}
					}
				}
			}

		</script>
		<style>
			.table>tbody>tr>td, .table>tbody>tr>th, .table>tfoot>tr>td, .table>tfoot>tr>th, .table>thead>tr>td, .table>thead>tr>th {
    			padding: 12px;
    			line-height: 1.42857143;
    			vertical-align: top;
    			border-top: 0px solid #ddd;
    		}
    		#transferdropdown {
/*     			margin-left: 30px; */
    			margin-left: 3px;
    		}
		</style>
  	</head>
  	<body class="sidebar-mini fixed">
  		<div class="wrapper">
	<!-- Header-->
		<jsp:include page="/jsp/pages/commons/header.jsp"/>
	  	<!--header End--->
	      	<div class="content-wrapper">
        	<div class="page-title">
          		<div>
	            	<h1>BANK TRANSACTIONS </h1>
          		</div>
					<ul id="nav" class="nav nav-pills clearfix right" role="tablist">
          				<li class="dropdown"><span class="dropdown-toggle btn-info" data-toggle="dropdown" id="ahelp">Help</span>
							<ul id="products-menu" class="dropdown-menu clearfix" role="menu">
								<li><a style="font-size: 14px;" href="https://www.youtube.com/watch?v=LkC7pdh2D0U" target="_blank">English</a></li>
                                <!-- <li><a style="font-size: 14px;" href="https://www.youtube.com/watch?v=LkC7pdh2D0U" target="_blank">Hindi</a></li> -->
							</ul>
						</li>
					</ul>
        	</div>
			<button name="b_sb" id="b_sb" class="btn btn-info color_btn bg_color4" onclick="showBTRNXSFormDialog()">ADD</button>
			<button name="cancel_data" id="cancel_data" class="btn btn-info color_btn bg_color2" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/home.jsp','2001')">BACK</button>
			<!-- Modal -->
<!-- 				<div class="modal_popup_add fade in" id="myModal" style="display: none; padding-left: 14px; height: 100%" > -->
				<div class="modal_popup_add fade in" id="myModal" style="display: none; height: 100%" >
					<div class="modal-dialog modal-lg">
			  			<!-- Modal content-->
			  			<div class="modal-content" id="modal-content">
							<div class="modal-header" id="modal-header">
					  			<span class="close" id="close" onclick="closeFormDialog()">&times;</span>
<!-- 					  			<h4 class="modal-title">BANK TRANSACTIONS</h4> -->
<!-- -------------------------mstart--------------------- -->
   <b>	BANK TRANSACTIONS</b>	
							<button name="bank_pop1" id="bank_pop1" class=" btn-info color_popup_btn bg_color4 btnr" onclick="showBankFormDialog()">BANK</button>
						    					
 	<!-- -------------------------mend--------------------- -->
							</div>
							<div class="modal-body">
					  			<form id="page_data_form" name="" method="post" action="TransactionsDataControlServlet">
					  				<input type="hidden" id="agencyId" name="agencyId" value="<%= agencyVO.getAgency_code() %>">
									<input type="hidden" id="page" name="page" value="jsp/pages/erp/cash&bank/bank_tranxs.jsp">
									<input type="hidden" id="actionId" name="actionId" value="5522">
									<input type="hidden" id="itemId" name="itemId" value="">

									<div class="row">
										<div class="col-adj-res-bt">
											<label>TRANSACTION DATE</label>
							   			    <input type=date name="btdate" id="btdate" class="form-control input_field bpc eadd" placeholder="DD-MM-YY">
										</div>
										<div class="col-adj-res-bt">
											<label>MODE OF TRANSACTION</label>
				   								<select name='mop' id='mop'  class='form-control input_field select_dropdown sadd'>
													<OPTION VALUE='0'>SELECT</OPTION>
													<OPTION VALUE='2'>CHEQUE</OPTION>
													<option value='3'>ONLINE TRANSFER</option>
												</select>
										</div>

									<div class="col-adj-res-bt">
											<label>TRANSACTION AMOUNT</label>
                   				<input type=text name='btamt' id='btamt' maxlength='9' class='form-control input_field eadd' placeholder='TRANSACTION AMOUNT'>
										</div>
										</div>
										<br>
			                     	<div class="row">
										<div class="col-adj-res-bt">
											<label>TRANSACTION TYPE</label>
											<select name='tt' id='tt'  onchange="showToBankAccount()" class='form-control input_field select_dropdown sadd'>
												<OPTION VALUE='0'>SELECT</OPTION>
												<OPTION VALUE='1'>PAYMENT</OPTION>
												<OPTION VALUE='2'>RECEIPT</OPTION>
												<option value='3'>CASH DEPOSIT</option>
												<OPTION VALUE='4'>CASH WITHDRAWL</OPTION>
												<option value='5'>TRANSFER</option>
<!-- 											<option value='6'>CHEQUE RETURN</option>
 -->
											</select>
										</div>
										<div class="col-adj-res-bt">
											<label>BANK ACCOUNT</label>
<%-- 											<select name="bid" id="bid" class="form-control input_field select_dropdown sadd">
                  								<%String str1 = "<script>document.writeln(bankdatahtml)</script>";
				   									out.println("value: " + str1);%>
				   							</select> --%>
											<span id="fbankSpan"></span>				   							
											<%-- <select name="bid" id="bid" class="form-control input_field select_dropdown sadd">
                  								<%String str1 = "<script>document.writeln(bankdatahtml)</script>";
				   									out.println("value: " + str1);%>
				   							</select> --%>
				   				    	</div>
										<div class="col-adj-res-bt" id="transferdropdown" style="display:none;">
							    			<span id="tbankSpan"></span>
										</div>
										<div class="clearfix"></div>
									</div>
									<br>
									<div class="row">
					    				<div class="col-adj-res-bt">
											<label>BANK CHARGES</label>
                   							<input type=text name='btcharges' id='btcharges'  value ='0.00' class='form-control input_field eadd' maxlength='6'  placeholder='BANK CHARGES'>
										</div>

											<div class="col-adj-res-bt">
											<label>INSTRUMENT DETAILS / TXR NO</label>
                   						<input type=text name='instrd' id='instrd'  class='form-control input_field eadd' value="NA" maxlength='30' placeholder='INSTRUMENT DETAILS'>
										</div>
										<div class="col-adj-res-bt">
											<label>NARRATION</label>
				   					<input type=text id='nar' name='nar' class='form-control input_field eadd' maxlength='200' placeholder='NARRATION'>
										</div>
    								</div>
								</form>
								</div>
								<div class="row">
			 						<div class="clearfix"></div>
									<div class="col-md-2" style="margin-left:60px;">
										<button name="save_data" id="save_data"  style="border-radius:3px;"class="btn btn-info color_btn bg_color2"  onclick="saveData(this)">SAVE</button>
									</div>
								</div>
							</div>
			  			</div>
					</div>
				 	<div class="row">
          				<div class="clearfix"></div>
          				<div class="col-md-12">
                			<table class="table table-striped" id="m_data_table">
                  				<thead>
                    				<tr class="title_head">
										<th width="10%" class="text-center sml_input"> TRANSACTION NUMBER</th>
                      					<th width="10%" class="text-center sml_input">TRANSACTION DATE</th>
                      					<th width="10%" class="text-center sml_input">BANK ACCOUNT</th>
                      					<th width="10%" class="text-center sml_input">TRANSACTION AMOUNT</th>
                      					<th width="10%" class="text-center sml_input">BANK CHARGES</th>
                      					<th width="10%" class="text-center sml_input">TRANSACTION TYPE</th>
					  					<th width="10%" class="text-center sml_input">MODE OF TRANSACTION</th>
					  					<th width="10%" class="text-center sml_input">INSTRUMENT DETAILS / TXR NO</th>
					  					<th width="10%" class="text-center sml_input">NARRATION</th>
						  				<th width="10%" class="text-center sml_input">ACTIONS</th>
                    				</tr>
                  				</thead>
                  				<tbody id="page_data_table_body"></tbody>
                			</table>
		              	</div>
        			</div>	
        			
        			<!-- new popup 2202018 -->
			
			
	                        <!-- BANK -->		

	<div class="modal_popup_newadd fade in" id="bankModal" style="display: none;height: 100%">
					<div class="modal-dialog modal-lg">
			  			<!-- Modal content-->
<!-- 			  			<div class="modal-mulpopup_content" id="modal-content" style="top: -50px;">
 -->						<div class="modal-content" id="modal-content" style="top: -50px;">
                            	<div class="modal-header" id="modal-header" style="padding:5px;">
					  			<span class="close" id="closeBankpopup" onclick="closeBankFormDialog()">&times;</span>
					  			<h4 class="modal-title">BANK MASTER</h4>
							</div>
			
	                    					
        <div class="row">
          <div class="clearfix"></div>
          <div class="col-md-15" id="mymBankPopAddDIV" style="display:none;">
            <div class="main_table" style="margin-left: 37px;margin-right:37px;">
              <div class="table-responsive">
				<form id="mpopup_bank_data_form" name="" method="post" action="MasterDataControlServlet">
					<input type="hidden" id="agencyId" name="agencyId" value="<%= agencyVO.getAgency_code() %>">
					<input type="hidden" id="page" name="page" value="jsp/pages/erp/cash&bank/bank_tranxs.jsp">
					<input type="hidden" id="actionId" name="actionId" value="9999">
					<input type="hidden" id="dataId" name="dataId" value="">
	                <input type="hidden" id="popupPageId" name="popupPageId" value="9">
					<input type="hidden" id="popupId" name="popupId" value="4">
						
                	<table id="mpopup_bank_add_table" class="table table-striped">
                  		<thead>
                    		<tr class="title_head_mpopup">
        	            					
						  						<th width="15%" class="text-center sml_input"> BANK NAME</th>
                	      						<th width="15%" class="text-center sml_input">ACCOUNT TYPE</th>
                    	  						<th width="15%" class="text-center sml_input">ACCOUNT NO</th>
                      							<th width="15%" class="text-center sml_input">BRANCH</th>
                      							<th width="15%" class="text-center sml_input">IFSC CODE</th>
                      							<th width="15%" class="text-center sml_input">ADDRESS</th>
					   							<th width="35%" class="text-center sml_input">OPENING BALANCE</th>
						 						<th width="5%" style="padding: 18px 20px !important;">ACTIONS</th>
	                    					</tr>
	                  					</thead>
    	              					<tbody  id="mpopup_bank_add_table_body"></tbody>
			                		</table>
        		        		</form>
            		  		</div>
            			</div>
						<div class="clearfix"></div>
    	    	</div>
    	   	</div>
        	<span id="odinstrn" style="display:none;color:red;"> * Please &nbspenter &nbsppositive &nbspvalue &nbspif &nbspyou &nbsphave &nbspcredit  &nbspbalance, &nbspenter &nbspnegative &nbspvalue &nbspif &nbspyou &nbsphave &nbspdebit &nbspbalance &nbspin &nbspOpening &nbspbalance &nbspof &nbspOverdraft &nbspaccount</span>
			<button class="btnr btn-info color_popup_btn bg_color2" onclick="addmBankpopupRow()">ADD</button>
			<span id="savembankpopupdiv" style="display:inline;"><button class="btnr btn-info color_popup_btn bg_color2" name="save_mbankpopup_data" id="save_mbankpopup_data" onclick="savemBankPopupData(this)">SAVE</button></span>
		<!-- 	<button name="mbbankpopup_cancel_data" id="mbbankpopup_cancel_data" class="btnr btn-info color_popup_btn bg_color2" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/transactions/sales/dom_refill_sales.jsp','5121')">BACK</button>
 -->
			<div class="row">
          		<div class="clearfix"></div>
          		<div class="col-md-15">
            		<div class="main_table" style="margin-left: 37px;margin-right:37px;">
              			<div class="table-mpopup_responsive">
                			<table class="table table-striped" id="bank_data_table">
                  				<thead>
                    				<tr class="title_head_mpopup">
                      					<th width="10%" class="text-center sml_bankpopup_input"> BANK NAME</th>
                      					<th width="10%" class="text-center sml_bankpopup_input">ACCOUNT TYPE</th>
                      					<th width="10%" class="text-center sml_bankpopup_input">ACCOUNT NO</th>
                      					<th width="10%" class="text-center sml_bankpopup_input">BRANCH</th>
                      					<th width="10%" class="text-center sml_bankpopup_input">IFSC CODE</th>
                      					<th width="10%" class="text-center sml_bankpopup_input">ADDRESS</th>
					   					<th width="10%" class="text-center sml_bankpopup_input">OPENING BALANCE</th>
					   					<th width="10%" class="text-center sml_bankpopup_input">CLOSING BALANCE</th>
								<!-- 	    <th width="10%" class="text-center sml_input">ACTIONS</th> -->
                    				</tr>
                  				</thead>
                  				<tbody id="mpopup_bank_data_table_body">
				                </tbody>
                			</table>
              			</div>
            		</div>
          		</div>
        	</div>
    </div>
  </div>   
  </div>                  
  
  			<!--  	new popup end  -->				
				</div>
      	</div>
	<script src="js/commons/bootstrap.min.js?<%=randomUUIDString%>"></script>
	<script src="js/commons/plugins/pace.min.js?<%=randomUUIDString%>"></script>
    <script src="js/commons/main.js?<%=randomUUIDString%>"></script>
    <script type="text/javascript" src="js/modal.js?<%=randomUUIDString%>"></script>
    <!--------  for popup start 15052019  ---------------- --> 
<script type="text/javascript" src="js/local.js?<%=randomUUIDString%>"></script>
<script type="text/javascript" src="js/modules/multipopup/mbank_data.js?<%=randomUUIDString%>"></script>

<!--------   popup end 15052019  ---------------- --> 
    <script type="text/javascript" src="js/commons/general_validations.js?<%=randomUUIDString%>"></script>
	<script type="text/javascript" src="js/modules/cash&bank/bank_transactions.js?<%=randomUUIDString%>"></script>
	<script>
		document.getElementById("nameSpan").innerHTML = <%= agencyVO.getAgency_code() %>;
		var dedate = <%=agencyVO.getDayend_date()%>;
     	var effdate = <%=agencyVO.getEffective_date()%>;
		var a_created_date = <%=agencyVO.getCreated_date()%>;
    	var dealergstin =  "<%= agencyVO.getGstin_no() %>";
		document.getElementById('page_data_form').agencyId = <%= agencyVO.getAgency_code() %>;
		document.getElementById("fbankSpan").innerHTML = "<select id='bid' name='bid' class='form-control input_field select_dropdown sadd'>"+nlbankdatahtml+"</select>";
				
		<% Map<String,Object> details = (Map<String,Object>) session.getAttribute("details"); %>
		var cashcb = <%=details.get("cashBal") %>;
				
		$(document).ready(function() {
       	    $('#m_data_table').DataTable( {
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
       	    
       	    

      		/* --------  for popup start 15052019  ---------------- */ 
               	   	
         	$('#bank_data_table').DataTable( {
        		    "lengthMenu": [[5,10, 25, 50, -1], [5,10, 25, 50, "All"]],
        	    	"bFilter": false,
        	    	"ordering": false,
         	    "scrollY":'50vh',
        		        "scrollCollapse": true,
        	        "scrollX": false,
                 language: {
                     paginate: {
                     	next: '&#x003E;&#x003E;',
                         previous: '&#x003C;&#x003C;'
                     }
                   }
        	    } );
        		
        		
         	/* --------   popup end 15052019  ---------------- */ 
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
  </body>
  <div id = "dialog-1" title="Alert(s)"></div>
  <div id="dialog-confirm"><div id="myDialogText" style="color:black;"></div></div>
</html>