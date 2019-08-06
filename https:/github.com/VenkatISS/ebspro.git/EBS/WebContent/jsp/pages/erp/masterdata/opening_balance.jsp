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
    <!-- CSS-->
    <link rel="stylesheet" type="text/css" href="css/main.css?<%=randomUUIDString%>">
    <title>MyLPGBooks DEALER WEB APPLICATION - BANK MASTER</title>
    <jsp:useBean id="agencyVO" scope="session" class="com.it.erpapp.framework.model.vos.AgencyVO"></jsp:useBean>
	<jsp:useBean id="bank_data" scope="request" class="java.lang.String"></jsp:useBean>
	<jsp:useBean id="cash_data" scope="request" class="java.lang.String"></jsp:useBean>
	<jsp:useBean id="ujwala_data" scope="request" class="java.lang.String"></jsp:useBean>
	<script src="js/commons/jquery-2.1.4.min.js?<%=randomUUIDString%>"></script>


        			   <!-- Balance Update popup div start 04052019 -->

<style>
.updatebutton:hover {
  background-color: #2196F3;
  color: white;
  border-radius:8px;
  font-weight:600px;
}
</style>
		<script type="text/javascript">
		var bank_data = <%= bank_data.length()>0? bank_data : "[]" %>;
		var cash_data = <%= cash_data.length()>0? cash_data : "[]" %>;
		var ujwala_data = <%= ujwala_data.length()>0? ujwala_data : "[]" %>;

	
</script>

        			   <!-- Balance Update popup div end 04052019  -->

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
            		<h1>OPENING BALANCE MASTER</h1>
          		</div>
					<ul id="nav" class="nav nav-pills clearfix right" role="tablist">
          				<li class="dropdown"><span class="dropdown-toggle  btn-info" data-toggle="dropdown" id="ahelp">Help</span>
							<ul id="products-menu" class="dropdown-menu clearfix" role="menu">
								<li><a style="font-size: 14px;"title="bank-master" href="https://www.youtube.com/watch?v=3DdxnoQNQK0" target="_blank">English</a></li>
								<li><a style="font-size: 14px;" href="https://www.youtube.com/watch?v=3DdxnoQNQK0" target="_blank">Hindi</a></li>
							</ul>
						</li>
					</ul>
        	</div>
       
	   
			<div class="row">
          		<div class="clearfix"></div>
          		<div class="col-md-12">
            		<div class="main_table">
              			<div class="table-responsive">
                			<table class="table table-striped">
                  				<thead>
                    				<tr class="title_head">
                      					<th width="10%" class="text-center sml_input"> BANK NAME</th>
                      					<th width="10%" class="text-center sml_input">ACCOUNT TYPE</th>
                      					<th width="10%" class="text-center sml_input">ACCOUNT NO</th>
                      					<th width="10%" class="text-center sml_input">BRANCH</th>
                      					<th width="10%" class="text-center sml_input">IFSC CODE</th>
                      					<th width="10%" class="text-center sml_input">ADDRESS</th>
					   					<th width="10%" class="text-center sml_input">OPENING BALANCE</th>
					   					<th width="10%" class="text-center sml_input">CLOSING BALANCE</th>
									    <th width="10%" class="text-center sml_input">ACTIONS</th>
                    				</tr>
                  				</thead>
                  				<tbody id="bank_data_table_body">
				                </tbody>
                			</table>
              			</div>
            		</div>
          		</div>
        	</div>
        	
        	
        			   <!-- Balance Update popup div 04052019 -->
        			   
          <div class="modal_popup_add fade in" id="bankBalanceUpdatePopup" style="display:none;padding-left: 14px; height: 100%">
      	<div class="modal-dialog modal-lg">
        	<!-- Modal content-->
            <div class="modal-content prof_modalContent" style="height:150px;width:40%; margin:auto;">
			<div id="mRefilpricepopupcontentDiv"> 
					<div class="modal-header" style="text-align:center;">
						<span class="close" id="closeBankBalancepopup" onclick="closeBankBalanceUpdatePopup()">&times;</span>
						<h4 class="modal-title">ENTER BANK BALANCE</h4>
                    </div>
                    <div class="modal-body">
			         
                    <form method="Post" id="update_balance_form" action="MasterDataControlServlet" onsubmit="return submitSetBalanceDetails(this)"> 
            		<input type="hidden" id="agencyId" name="agencyId" value="<%= agencyVO.getAgency_code() %>">
					<input type="hidden" id="page" name="page" value="jsp/pages/erp/masterdata/opening_balance.jsp">
					<input type="hidden" id="actionId" name="actionId" value="3557">
				    <input type="hidden" id="bId" name="bId" value="">
				    <input type="hidden" id="bname" name="bname" value="">
				     
                        <table id="bankbalance_popup_table">
							<tr class="spaceUnder">
								<td>Enter Account Balance:&nbsp;&nbsp;&nbsp;&nbsp;</td>
								<td><input class="form-control" type="text" name="bankbalamt" id="bankbalamt" placeholder="Enter Balance Amount"></td>
								<td colspan="6">
									<div class="submit_mRefilpricepopup_div" style="text-align: center; width: 100px; margin-left: 40px; margin-top: -3px;">
						 				<input type="submit" name="balanceamt_submit_btn" id="balanceamt_submit_btn" value="SUBMIT" class="btn btn-success m-r-15">
								</div>
								</td>
							</tr>
						</table>
						
                  		</form>
                	</div>
                	                 </div> 
          </div>
        </div>
 	</div>
        	<!-- end popup div 04052019-->
        	
      	</div>
    </div>
	
	<div id = "dialog-1" title="Alert(s)"></div>
	<div id="dialog-confirm"><div id="myDialogText" style="color:black;"></div></div>

	<div id="dialog-form" style="display:none;">
    	<form>
			<label for="odv" style="margin-top:8px;">Please enter Debit Balance of OverDraft account upto which we can allow your transactions: </label><br>
			<input type="text" name="odv" id="odval" class="text ui-widget-content ui-corner-all" style="margin-top:8px;" />
		</form>
	</div>
		
    <!-- Javascripts-->
	<script type="text/javascript" src="js/local.js?<%=randomUUIDString%>"></script>
	<script type="text/javascript">
	var bank_data =  <%= bank_data.length()>0? bank_data : "[]" %>;
	</script>
	<script type="text/javascript" src="js/commons/general_validations.js?<%=randomUUIDString%>"></script>
	<script type="text/javascript" src="js/modules/masterdata/opening_balance.js?<%=randomUUIDString%>"></script>
	<script src="js/commons/bootstrap.min.js?<%=randomUUIDString%>"></script>
	<script src="js/commons/plugins/pace.min.js?<%=randomUUIDString%>"></script>
    <script src="js/commons/main.js?<%=randomUUIDString%>"></script>
	<script type="text/javascript">
		document.getElementById("nameSpan").innerHTML = <%= agencyVO.getAgency_code() %>
		
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
	       	    
		} );
	</script>	
  </body>
</html>
