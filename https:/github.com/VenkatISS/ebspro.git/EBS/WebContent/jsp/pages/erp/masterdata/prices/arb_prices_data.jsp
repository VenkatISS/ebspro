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
    <title>BLPG/ARB/NFR ITEMS PRICE MASTER</title>
        
    <jsp:useBean id="agencyVO" scope="session" class="com.it.erpapp.framework.model.vos.AgencyVO"></jsp:useBean>
	<jsp:useBean id="arb_types_list" scope="request" class="java.lang.String"></jsp:useBean>
	<jsp:useBean id="arb_data" scope="request" class="java.lang.String"></jsp:useBean>
	<jsp:useBean id="arb_prices_data" scope="request" class="java.lang.String"></jsp:useBean>
    <script src="js/commons/jquery-2.1.4.min.js?<%=randomUUIDString%>"></script>
<%-- 	<script src="js/commons/checkBrowser.js?<%=randomUUIDString%>"></script>     --%>

	<!-- Sidenav -->
	<jsp:include page="/jsp/pages/commons/sidenav.jsp"/>    
	<!---END Sidenav--->
	
	<script type="text/javascript"> 
		window.onload = setWidthHightNav("100%");
	</script>
	<script type="text/javascript">
		var months = ["JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"];
		var years = ["2018","2019","2020"];
		//var cat_types_data = <%= arb_types_list %>;
		var cat_types_data = <%= arb_data %>;
		var ctdatahtml, mvdatahtml, yvdatahtml = "";
		var arb_data = <%= arb_data.length()>0? arb_data : "[]" %>;
		var page_data =  <%= arb_prices_data.length()>0? arb_prices_data : "[]" %>;
	</script>
	<script type="text/javascript" src="js/commons/general_validations.js?<%=randomUUIDString%>"></script>
  </head>
  <body class="sidebar-mini fixed">
    <div class="wrapper">
      	<!-- Header-->
		<jsp:include page="/jsp/pages/commons/header.jsp"/>      
	  	<!--header End--->
		
	<div class="content-wrapper">
<!--     <div id = "dialog-1" title="Alert(s)"></div>
	<div id="dialog-confirm"><div id="myDialogText" style="color:black;"></div></div> -->
          <div>
    	    <div class="page-title">
	            <h1>BLPG/ARB/NFR ITEMS PRICE MASTER  </h1>
					<ul id="nav" class="nav nav-pills clearfix right" role="tablist">
          				<li class="dropdown"><span class="dropdown-toggle  btn-info" data-toggle="dropdown" id="ahelp">Help</span>
							<ul id="products-menu" class="dropdown-menu clearfix" role="menu">
								<li><a style="font-size: 14px;" title="BLPG NFR & ARB Price Setting" href="https://www.youtube.com/watch?v=9HMb1z22Tjw" target="_blank">English</a></li>
								<li><a style="font-size: 14px;" href="https://youtu.be/E-PJXC7eN7M" target="_blank">Hindi</a></li>
							</ul>
						</li>
					</ul>
          	</div>
        </div>
		<div class="row">
          <div class="clearfix"></div>
          <div class="col-md-12" id="myDIV" style="display:none;">
		    
            <div class="main_table">
              <div class="table-responsive">
					<form id="page_data_form" name="" method="post" action="PPMasterDataControlServlet">
						<input type="hidden" id="agencyId" name="agencyId" value="<%= agencyVO.getAgency_code() %>">
						<input type="hidden" id="page" name="page" value="jsp/pages/erp/masterdata/prices/arb_prices_data.jsp">
						<input type="hidden" id="actionId" name="actionId" value="3212">
						<input type="hidden" id="itemId" name="itemId" value="">

                		<table class="table">
                  		<thead>
                    		<tr class="title_head">
                    		  <th width="10%" class="text-center sml_input"> PRODUCT</th>
                    		  <th width="10%" class="text-center sml_input">MRP</th>
					   		  <th width="10%" class="text-center sml_input">BASIC PRICE</th>
                    		  <th width="10%" class="text-center sml_input">CGST AMOUNT</th>
					   		  <th width="10%" class="text-center sml_input">SGST AMOUNT</th>
					   		  <th width="10%" class="text-center sml_input">OFFER PRICE</th>
                    		  <th width="10%" class="text-center sml_input">MONTH</th>
                    		  <th width="10%" class="text-center sml_input">YEAR</th>
							  <th width="10%" class="text-center sml_input">ACTIONS</th>
                    		</tr>
                  		</thead>
                  		<tbody id="page_add_table_body"></tbody>
                	</table>
				</form>
              </div>
            </div>
				
			<div class="clearfix"></div>
          </div>
        </div>
        <span id="arbOfpNote" style="display:none;color:red;"> * Please &nbspenter &nbspOFFER PRICE &nbspalong &nbspwith &nbspit's &nbspGST &nbspamount.</span>
		<button class="btn btn-info color_btn btnadd" onclick="addRow()">ADD</button>
		<span id="savediv" style="display:none;">
			<button class="btn btn-info color_btn bg_color2" id="calc_data" onclick="calculateValues()" >CALCULATE</button>
			<button class="btn btn-info color_btn bg_color2" id="save_data" onclick="saveData(this)" disabled="disabled">SAVE</button>
		</span>
			
		<button class="btn btn-info color_btn bg_color2" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/home.jsp','2001')">BACK</button>	
		<br>
		<br>	
		
		<div class="row">
          <div class="clearfix"></div>
          <div class="col-md-12">
		    
            <div class="main_table">
              <div class="table-responsive">
                <table class="table  table-striped">
                  <thead>
                    <tr class="title_head">
                      <th width="10%" class="text-center sml_input"> PRODUCT</th>
                      <th width="10%" class="text-center sml_input">MRP</th>
					  <th width="10%" class="text-center sml_input">BASIC PRICE</th>
                      <th width="10%" class="text-center sml_input">CGST AMOUNT</th>
					  <th width="10%" class="text-center sml_input">SGST AMOUNT</th>
					  <th width="10%" class="text-center sml_input">OFFER PRICE</th>
					  <th width="10%" class="text-center sml_input">MONTH</th>
                      <th width="10%" class="text-center sml_input">YEAR</th>
					  <th width="10%" class="text-center sml_input">ACTIONS</th>
                    </tr>
                  </thead>
                  <tbody id="page_data_table_body"></tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!-- popup div -->
      <div class="modal_popup_add fade in" id="myModalarbPin" style="display:block; padding-left: 14px; height: 100%">
      	<div class="modal-dialog modal-lg">
        	<!-- Modal content-->
            	<div class="modal-content prof_modalContent" style="height:200px;width:60%; margin:auto;">
            		<div id="contentDiv">
	                    <div class="modal-header" style="text-align:center;">
    	                	<h4 class="modal-title">ENTER SECURITY PIN DETAILS</h4>
    	                </div>
        	            <div class="modal-body">
							<input type="hidden" id="pinNO" name="pinNO" value="<%= agencyVO.getPinNumber() %>">

							<table id="t_data_table">
                            	<tr class="spaceUnder">
									<td>Pin Number:&nbsp;&nbsp;&nbsp;&nbsp;</td>
									<td><input class="form-control" type="password" name="enteredPin" id="enteredPin" placeholder="Enter pin here"></td>
									<td colspan="6">
										<div class="submit_div" style="text-align: center; width: 100px; margin-left: 40px; margin-top: -3px;">
											<input type="button" name="prof_submit_btn" id="pin_submit_btn" value="SUBMIT" class="btn btn-success m-r-15" onclick="submitPinNumber()">
										</div>
									</td>
								</tr>
                        	</table>
                        	
                			<div class="submit_div" style="text-align: center; width: 100px; margin-left: 2px; margin-top: 10px;">
                  				<button name="prof_submit_btn" id="prof_submit_btn" class="btn btn-success m-r-15" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/home.jsp','2001')">BACK</button>
		                	</div>
    	            	</div>
        			</div>
        			<div id="displayDiv">
        				<div class="modal-header" style="text-align:center;">
    	            		<h4 class="modal-title">SECURITY PIN DETAILS</h4>
    	            	</div>
    	            	<div class="modal-body"><br>
        					YOU  HAVEN'T  SET  ANY  PROFILE  PIN.
        					<a href="#prof" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/profile.jsp','9001')">CLICK HERE</a>
        				 	TO  SET  PIN  OR  SET  PIN  IN  YOUR  PROFILE  DETAILS  AND  THEN  PROCEED  <br><br>
        					<div class="submit_div" style="text-align: center; width: 100px; margin-left: 200px;">
                  				<button name="prof_submit_btn" id="prof_submit_btn" class="btn btn-success m-r-15" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/home.jsp','2001')">BACK</button>
		                	</div>
        				</div>	
        			</div>       
                </div>
			</div>
        </div>
    </div>
    
	<div id = "dialog-1" title="Alert(s)"></div>
	<div id="dialog-confirm"><div id="myDialogText" style="color:black;display:block;"></div></div>
    
	<script type="text/javascript">
  		var checkDisplay = <%=request.getAttribute("checkDisplay") %>;
  		if(!checkDisplay)
        	checkDisplay="0";
  		var pinNum = <%= agencyVO.getPinNumber() %>;
	</script>
    <!-- Javascripts-->
	<script src="js/commons/bootstrap.min.js?<%=randomUUIDString%>"></script>
	<script src="js/commons/plugins/pace.min.js?<%=randomUUIDString%>"></script>
	<script src="js/commons/main.js?<%=randomUUIDString%>"></script>
	<script type="text/javascript" src="js/modules/masterdata/prices/arb_prices_data.js?<%=randomUUIDString%>"></script>	
	<script>
		document.getElementById("nameSpan").innerHTML = <%= agencyVO.getAgency_code() %>
		document.getElementById('page_data_form').agencyId = <%= agencyVO.getAgency_code() %>;

		var dedate = new Date(<%=agencyVO.getDayend_date()%>);
		var effdate = new Date(<%=agencyVO.getEffective_date()%>);

/* 		var input = document.getElementById("enteredPin");
		input.addEventListener("keyup", function(event) {
		  event.preventDefault();
		  if (event.keyCode === 13) { // 13 = enter button
		    document.getElementById("pin_submit_btn").click();
		  }
		}); */
				
		$(document).ready(function() {
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
	 <%-- <script>
    var pinNum=<%=agencyVO.getPinNumber() %>;
    if(!pinNum)
    	alert("Please set your pin in MY PROFILE");
    </script>
 --%>
  </body>
</html>
