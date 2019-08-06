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
    <title>MyLPGBooks DEALER WEB APPLICATION - CREDIT LIMIT MASTER PAGE</title>
    <jsp:useBean id="agencyVO" scope="session" class="com.it.erpapp.framework.model.vos.AgencyVO"></jsp:useBean>
	<jsp:useBean id="cvo_data" scope="request" class="java.lang.String"></jsp:useBean>
	<jsp:useBean id="cl_data" scope="session" class="java.lang.String"></jsp:useBean>
    <script src="js/commons/jquery-2.1.4.min.js?<%=randomUUIDString%>"></script>
<%-- 	<script src="js/commons/checkBrowser.js?<%=randomUUIDString%>"></script>     --%>

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
<!-- 			<div id = "dialog-1" title="Alert(s)"></div>
 			<div id="dialog-confirm"><div id="myDialogText" style="color:black;"></div></div> -->
        	<div class="page-title">
          		<div>
            		<h1>CREDIT LIMIT MASTER</h1>
          		</div>
					<ul id="nav" class="nav nav-pills clearfix right" role="tablist">
          				<li class="dropdown"><span class="dropdown-toggle  btn-info" data-toggle="dropdown" id="ahelp">Help</span>
							<ul id="products-menu" class="dropdown-menu clearfix" role="menu">
								<li><a style="font-size: 14px;" title="Credit and Dicscount Limit Setting" href="https://www.youtube.com/watch?v=Yo9jZmdpzrQ" target="_blank">English</a></li>
								<li><a style="font-size: 14px;" href="https://youtu.be/XtiejBL-VTs" target="_blank">Hindi</a></li>
							</ul>
						</li>
					</ul>
        	</div>
        		<div class="row">
          			<div class="clearfix"></div>
          			<div class="col-md-12" id="myDIV" style="display:none;">
            			<div class="main_table">
              				<div class="table-responsive">
                				<form id="credit_limit_form" name="" method="post" action="MasterDataControlServlet">
									<input type="hidden" id="agencyId" name="agencyId" value="<%= agencyVO.getAgency_code() %>">
									<input type="hidden" id="page" name="page" value="jsp/pages/erp/masterdata/credit_limits_data.jsp">
									<input type="hidden" id="actionId" name="actionId" value="3552">
									<input type="hidden" id="itemId" name="itemId" value="">
                					<table class="table" id="credit_limit_add_table">
                  						<thead>
                    						<tr class="title_head">
                      							<th width="10%" class="text-center">CUSTOMER</th>
                      							<th width="10%" class="text-center">CREDIT LIMIT</th>
                      							<th width="10%" class="text-center">CREDIT DAYS</th>
                      							
                    	                    	<th width="10%" class="text-center">DISCOUNT ON COM 19KG NDNE CYL</th>
                      							<th width="10%" class="text-center">DISCOUNT ON COM 19KG CUTTING GAS CYL</th>
                      							<th width="10%" class="text-center">DISCOUNT ON COM 35KG NDNE CYL</th>
                      							<th width="10%" class="text-center">DISCOUNT ON COM 47.5KG NDNE CYL</th>
                      							<th width="10%" class="text-center">DISCOUNT ON COM 450KG SUMO CYL</th>
                      							
                      							<th width="10%" class="text-center">CONTROL</th>
                      							<th width="10%" class="text-center">ACIONS</th>
                    		
                    						</tr>
                  						</thead>
                  						<tbody id="credit_limit_add_table_body" >
                  						</tbody>
                					</table>
                				</form>
              				</div>
            			</div>
						<div class="clearfix"></div>
          			</div>
        		</div>
				<button name="add_data" id="add_data"  class="btn btn-info color_btn" onclick="addRow()">ADD</button>
				<span id="savediv" style="display:none;"><button class="btn btn-info color_btn bg_color2" name="save_data" id="save_data" onclick="saveData(this)">SAVE</button></span>
				<button name="cancel_data" id="cancel_data"	class="btn btn-info color_btn bg_color2" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/home.jsp','2001')">BACK</button>
				<div class="row">
          			<div class="clearfix"></div>
          			<div class="col-md-12">
			            <div class="main_table">
              				<div class="table-responsive">
                				<table class="table  table-striped">
                  					<thead>
                    					<tr class="title_head">
                       						<th width="10%" class="text-center sml_input">CUSTOMER</th>
                      						<th width="10%" class="text-center sml_input">CREDIT LIMIT</th>
                      						<th width="10%" class="text-center sml_input">CREDIT DAYS</th>
                      					
                    			            <th width="10%" class="text-center sml_input">DISCOUNT ON COM 19KG NDNE CYL</th>
                      						<th width="10%" class="text-center sml_input">DISCOUNT ON COM 19KG CUTTING GAS CYL</th>
                      						<th width="10%" class="text-center sml_input">DISCOUNT ON COM 35KG NDNE CYL</th>
                      						<th width="10%" class="text-center sml_input">DISCOUNT ON COM 47.5KG NDNE CYL</th>
                      						<th width="10%" class="text-center sml_input">DISCOUNT ON COM 450KG SUMO CYL</th>
                    			
                    			        	<th width="10%" class="text-center sml_input">CONTROL</th>
	 					                    <th width="10%" class="text-center sml_input">Actions</th>
                    			
                    					</tr>
                  					</thead>
                  					<tbody id="credit_limit_data_table_body">
                  					</tbody>
                				</table>
              				</div>
            			</div>
          			</div>
        		</div>
      		</div>
      		<!-- ASK FOR PIN popup div -->
      		<div class="modal_popup_add fade in" id="myModalcreditPin" style="display:block;" padding-left: 14px; height: 100%">
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
											<div class="submit_div" style="text-align: center; width: 100px;  margin-left: 40px; margin-top: -3px;">
												<input type="button" name="prof_submit_btn" id="prof_submit_btn" value="SUBMIT" class="btn btn-success m-r-15" onclick="submitPinNumber()">
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
			
			<div class="modal_popup_add fade in" id="updatecrdismodel" style="display: none;padding-left: 14px; height: 100%">
				<div class="modal-dialog modal-lg">
					<!-- Modal content-->
					<div class="modal-content prof_modalContent">
						<div class="modal-header" style="text-align:center;">
							<span class="close" id="close" onclick="closeChangeCreditlimitsData()">&times;</span>
							<h4 class="modal-title">UPDATE CREDIT LIMIT DISCOUNT</h4>
						</div>
						<div class="modal-body">
							<form id="discount_form" name="" method="post" action="MasterDataControlServlet">
								<input type="hidden" id="agencyId" name="agencyId" value="<%= agencyVO.getAgency_code() %>">
<%--                                                                 <input type="hidden" id="pin" name="pin" value="<%= agencyVO.getPinNumber() %>">
 --%>
								<input type="text" style="display:none" name="cid" id="cid">
								<input type="hidden" id="page" name="page" value="jsp/pages/erp/masterdata/credit_limits_data.jsp">
								<input type="hidden" id="actionId" name="actionId" value="3554">
								<br>
								<table id="t_data_table">
									<tr class="spaceUnder">
<!-- 										<input type="text" style="display:none" name="cid" id="cid"  maxlength="12"> -->
										<td>CUSTOMER:</td>
										<td> <input class="form-control" type="text" style="" name="cpname" id="cpname"  maxlength="35" placeholder="PERSON NAME" autofocus readonly></td>
										<td>DISCOUNT ON COM 19KG NDNE CYL:&nbsp</td>
										<td><input class="form-control" type="text" name="dis19kgndne" id="dis19kgndne" maxlength="8" placeholder="19KG NDNE DISCOUNT"></td>
										<td>DISCOUNT ON COM 19KG CUTTING GAS CYL:&nbsp</td>
										<td><input class="form-control" type="text" name="dis19kgcgas" id="dis19kgcgas" maxlength="8" placeholder="19KG CUTTING GAS CYL DISCOUNT"></td>
									</tr>
									<tr class="spaceUnder">
										<td>DISCOUNT ON COM 35KG NDNE CYL:&nbsp</td>
										<td><input class="form-control" type="text" name="dis35kgndne" id="dis35kgndne" maxlength="8" placeholder="35KG NDNE DISCOUNT"></td>
										<td>DISCOUNT ON COM 47.5KG NDNE CYL:&nbsp</td>
										<td><input class="form-control" type="text" name="dis47_5kgndne" id="dis47_5kgndne" maxlength="8" placeholder="47.5KG NDNE DISCOUNT"></td>
										<td>DISCOUNT ON COM 450KG SUMO CYL:&nbsp</td>
										<td><input class="form-control" type="text" name="dis450kgsumo" id="dis450kgsumo" maxlength="8" placeholder="450KG SUMO DISCOUNT"></td>
									</tr>
									<tr class="spaceUnder">
										<td colspan="6">
											<!-- <div class="submit_div" style="text-align:center;padding-top:20px;margin-bottom:-25px;">
												<button name="prof_submit_btn" id="prof_submit_btn" class="prof_submit_btn" onclick="submitUpdatedProfileDetails()">SUBMIT</button>
											</div> -->

											<div class="submit_div" style="text-align:center;width:100px;margin-left: 350px;margin-top: 10px;">
												<input type="button" name="creditlimit_submit_btn"  id="creditlimit_submit_btn" value="SUBMIT" class="btn btn-success m-r-15" onclick="submitUpdatedCreditlimitsData(this.form)">
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
    	
		<div id = "dialog-1" title="Alert(s)"></div>
 		<div id="dialog-confirm"><div id="myDialogText" style="color:black;"></div></div>
<script type="text/javascript">
    var custdatahtml = "";
    var cvo_data =  <%= cvo_data.length()>0? cvo_data : "[]" %>;
    var page_data =  <%= cl_data.length()>0? cl_data : "[]" %>;
    var ccontrols = ["SELECT","BLOCK","WARN"];
</script>
<script type="text/javascript">
	var checkDisplay = <%=request.getAttribute("checkDisplay") %>;
  	if(!checkDisplay)
    	checkDisplay="0";
	var pinNum = <%= agencyVO.getPinNumber() %>;  	
</script>
	<script type="text/javascript" src="js/commons/general_validations.js?<%=randomUUIDString%>"></script>
	<script type="text/javascript" src="js/modules/masterdata/credit_limits_data.js?<%=randomUUIDString%>"></script>
	<script src="js/commons/bootstrap.min.js?<%=randomUUIDString%>"></script>
	<script src="js/commons/plugins/pace.min.js?<%=randomUUIDString%>"></script>
    <script src="js/commons/main.js?<%=randomUUIDString%>"></script>

    <script  type="text/javascript">
    document.getElementById("nameSpan").innerHTML = <%= agencyVO.getAgency_code() %>
    document.getElementById('credit_limit_form').agencyId = <%= agencyVO.getAgency_code() %>;
    
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
