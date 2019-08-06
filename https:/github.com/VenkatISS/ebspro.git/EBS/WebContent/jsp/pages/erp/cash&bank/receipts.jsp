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
    	<script src="js/commons/jquery-2.1.4.min.js?<%=randomUUIDString%>"></script>
	   	<script type="text/javascript" src="https://cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js"></script>
		<link href="https://cdn.datatables.net/1.10.15/css/jquery.dataTables.min.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" language="javascript" src="https://code.jquery.com/jquery-1.12.4.js"></script>
		<script type="text/javascript" language="javascript" src="https://cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js"></script>
		<title>RECEIPTS</title>
    	<jsp:useBean id="agencyVO" scope="session" class="com.it.erpapp.framework.model.vos.AgencyVO"></jsp:useBean>
		<jsp:useBean id="receipts_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="cvo_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="bank_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="staff_data" scope="request" class="java.lang.String"></jsp:useBean>
<%-- 		<script src="js/commons/checkBrowser.js?<%=randomUUIDString%>"></script> --%>

		<!-- Sidenav -->
		<jsp:include page="/jsp/pages/commons/sidenav.jsp"/>    
		<!---END Sidenav--->
		
	 	<script type="text/javascript">
			window.onload = setWidthHightNav("100%");
	 	</script>
		<script type="text/javascript">
		var months = ["JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"];
		var years = ["2017","2018","2019","2020"];
		var mops = ["NONE","CASH","CHEQUE","ONLINE TRANSFER"];
		var staffdatahtml, bankdatahtml = "";
		var page_data =  <%= receipts_data.length()>0? receipts_data : "[]" %>;
		var staff_data =  <%= staff_data.length()>0? staff_data : "[]" %>;
		var bank_data =  <%= bank_data.length()>0? bank_data : "[]" %>;
		var cvo_data = <%= cvo_data.length()>0 ? cvo_data : "[]"%>;
			
		
		/*--------  for popup start 15052019  ----------------*/ 
		var dcvo_data = cvo_data;
		var agency_oc = <%= agencyVO.getAgency_oc() %>;
		var omc_name;
		
     	if(agency_oc==1)
			omc_name = "IOCL";
		else if(agency_oc==2)
			omc_name = "HPCL";
		else if(agency_oc==3)
			omc_name = "BPCL";
		var categories = ["VENDOR","CUSTOMER","OMC-"+omc_name,"OTHERS"];
     	var gstyn = ["SELECT","YES","NO"];

		var roles = ["DELIVERY STAFF","SHOWROOM STAFF","GODOWN STAFF","INSPECTOR","MECHANIC","OTHERS"];

		/*--------   popup end 15052019  ----------------*/ 

		
		//Construct Staff html
		staffdatahtml = "<OPTION VALUE='0'>SELECT</OPTION>";
		if(staff_data.length>0) {
			for(var z=0; z<staff_data.length; z++) {
				if(staff_data[z].deleted == 0) {
					staffdatahtml=staffdatahtml+"<OPTION VALUE='"+staff_data[z].id+"'>"+staff_data[z].emp_name+"</OPTION>";
				}
			}
		}
/* 
 		//Construct Customer Type html
		custdatahtml = "<OPTION VALUE='0'>SELECT</OPTION>";
		if(cvo_data.length>0) {
			for(var z=0; z<cvo_data.length; z++){
				if(cvo_data[z].cvo_cat==1 && cvo_data[z].deleted==0)
					custdatahtml=custdatahtml+"<OPTION VALUE='"+cvo_data[z].id+"'>"+cvo_data[z].cvo_name+"</OPTION>";
			}
		}
 */		
 		//Construct Customer Type html
		custdatahtml = "";
		if(cvo_data.length>0) {
			for(var z=0; z<cvo_data.length; z++){
				if(cvo_data[z].cvo_cat == 1 && cvo_data[z].deleted == 0)
					custdatahtml=custdatahtml+"<OPTION DATA-VALUE='"+cvo_data[z].id+"'>"+cvo_data[z].cvo_name+"</OPTION>";
			}
		}

		//Construct bank html
		bankdatahtml = "<OPTION VALUE='0'>SELECT</OPTION>";
		bankdatahtml =bankdatahtml+"<OPTION VALUE='1'>CASH</OPTION>";
		if(bank_data.length>0) {
			for(var z=0; z<bank_data.length; z++){
				var bc = bank_data[z].bank_code;
				if(!(bc=="STAR ACCOUNT") && bank_data[z].deleted == 0) {
					if(bc == "TAR ACCOUNT")
						bc = "LOAD ACCOUNT";
					bankdatahtml=bankdatahtml+"<OPTION VALUE='"+bank_data[z].id+"'>"+bc+" - "+bank_data[z].bank_acc_no+"</OPTION>";
				}
			}
		}

		</script>
		<script type="text/javascript" src="js/commons/general_validations.js?<%=randomUUIDString%>"></script>
  	</head>
  	<body class="sidebar-mini fixed">
    	<div class="wrapper">
       		<!-- Header-->
		<jsp:include page="/jsp/pages/commons/header.jsp"/>
	  	<!--header End--->
      	<div class="content-wrapper">
        	<div class="page-title">
          		<div>
            		<h1>RECEIPTS</h1>
          		</div>
<!-- 				<a href="https://youtu.be/bgQnKRrz2X8" target="_blank" style="text-decoration: underline; float:right;margin-right: 5px;">help?</a> -->
					<ul id="nav" class="nav nav-pills clearfix right" role="tablist">
          				<li class="dropdown"><span class="dropdown-toggle  btn-info" data-toggle="dropdown" id="ahelp">Help</span>
							<ul id="products-menu" class="dropdown-menu clearfix" role="menu">
								<li><a style="font-size: 14px;" href="https://www.youtube.com/watch?v=JkfNnEwc7Zs" target="_blank">English</a></li>
								<!-- <li><a style="font-size: 14px;" href="https://www.youtube.com/watch?v=JkfNnEwc7Zs" target="_blank">Hindi</a></li> -->
							</ul>
						</li>
					</ul>
        	</div>

			<button name="b_sb" id="b_sb" class="btn btn-info color_btn bg_color4" onclick="showRCPTSFormDialog()">ADD</button>
			<button name="cancel_data" id="cancel_data" class="btn btn-info color_btn bg_color2" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/home.jsp','2001')">BACK</button>
			<!-- Modal -->
				<div class="modal_popup_add fade in modal_popup" id="myModal">
					<div class="modal-dialog modal-lg">
			  			<!-- Modal content-->
			  			<div class="modal-content" id="modal-content">
							<div class="modal-header" id="modal-header">
					  			<span class="close" id="close" onclick="closeRCPTSFormDialog()">&times;</span>
<!-- 					  			<h4 class="modal-title">RECEIPTS</h4> -->
<!-- -------------------------mstart--------------------- -->
   <b>	RECEIPTS</b>	<button name="cvo_pop1" id="cvo_pop1" class=" btn-info color_popup_btn bg_color4 btnr" onclick="showCVOFormDialog()">CVO</button>
							<button name="bank_pop1" id="bank_pop1" class=" btn-info color_popup_btn bg_color4 btnr" onclick="showBankFormDialog()">BANK</button>
						    <button name="staff_pop1" id="staff_pop1" class=" btn-info color_popup_btn bg_color4 btnr" onclick="showStaffFormDialog()">STAFF</button>
												
 	<!-- -------------------------mend--------------------- -->
							</div>
							<div class="modal-body-rp">
	              				<form id="page_data_form" name="" method="post" action="PPMasterDataControlServlet">
									<input type="hidden" id="agencyId" name="agencyId" value="<%= agencyVO.getAgency_code() %>">
									<input type="hidden" id="page" name="page" value="jsp/pages/erp/cash&bank/receipts.jsp">
									<input type="hidden" id="actionId" name="actionId" value="5502">
									<input type="hidden" id="itemId" name="itemId" value="">
									<div class="row">
          								<div class="clearfix"></div>
          								<div class="col-md-12">
            								<div class="main_table">
              									<div class="table-responsive">									
               	 									<table class="table" id="b_data_table">
                  										<thead>
                     										<tr class="title_head">
<!-- 					                	  						<th width="2%" class="text-center sml_input-rp">VOUCHER<br>NO</th> -->
                					  	    					<th width="10%" class="text-center sml_input">VOUCHER DATE</th>
                  	    										<th width="10%" class="text-center sml_input">AMOUNT</th>
					                 	    					<th width="30%" class="text-center sml_input">RECEIVED FROM</th>
    					             	    					<th width="10%" class="text-center sml_input">MODE OF RECEIPT</th>
    					            	    					<th width="10%" class="text-center sml_input">INSTRUMENT DETAILS/TXR NO</th>
																<th width="10%" class="text-center sml_input">CREDITED (BANK)</th>
																<th width="10%" class="text-center sml_input">COLLECTED BY</th>
																<th width="10%" class="text-center sml_input">NARRATION</th>
																<!-- <th width="10%" class="text-center sml_input">ACTIONS</th> -->
                    										</tr> 
                  										</thead>
                  										<tbody id="page_add_table_body">	
                  											<tr>
<!--                   												<td valign="top" height="4" align="center" style="padding-top: 20px;"></td> -->
				   												<td valign='top' height='4' align='center' style="padding-top: 20px;"><input type=date name="rdate" id="rdate" class="form-control input_field bpc eadd" placeholder="DD-MM-YY"></td>
                   												<td valign='top' height='4' align='center' style="padding-top: 20px;"><input type=text name='ramt' id='ramt'  class='form-control input_field eadd' maxlength='9' size='8' placeholder='AMOUNT'></td>
				   												<td valign="top" height="4" align="center" style="padding-top: 20px;">
<%--                   													<select name="rfrom" id="rfrom" class="form-control input_field select_dropdown sadd">
                  														<%String str1 = "<script>document.writeln(custdatahtml)</script>";
				   															out.println("value: " + str1);%>
				   													</select> --%>

																	<input list="rfrom" id="answerInput" class="form-control input_field sadd" placeholder="Received From">
																	<datalist id="rfrom">
																		<%String str1 = "<script>document.writeln(custdatahtml)</script>";
																			out.println("value: " + str1);%>
																	</datalist>
																	<input type="hidden" name="rfrom" id="answerInputHidden">
				   												</td>
				   												<td valign='top' height='4' align='center' style="padding-top: 20px;">
				   													<select name='mop' id='mop'  class='form-control input_field select_dropdown sadd' onchange='changeBank()'>
																		<OPTION VALUE='0'>SELECT</OPTION>
																		<OPTION VALUE='1'>CASH</OPTION>
																		<OPTION VALUE='2'>CHEQUE</OPTION>
																		<option value='3'>ONLINE TRANSFER</option>
																	</select>
																</td>
                   												<td valign='top' height='4' align='center' style="padding-top: 20px;"><input type=text name='instrd' id='instrd'  class='form-control input_field eadd' value="NA" maxlength='30' placeholder='INSTRUMENT DETAILS'></td>
				   												<td valign="top" height="4" align="center" style="padding-top: 20px;">
                  													<select name="bid" id="bid" class="form-control input_field select_dropdown sadd">
                  														<%String str2 = "<script>document.writeln(bankdatahtml)</script>";
				   															out.println("value: " + str2);%>
				   													</select>
				   												</td>
				   												<td valign="top" height="4" align="center" style="padding-top: 20px;">
                  													<select name="sid" id="sid" class="form-control input_field select_dropdown sadd">
                  														<%String str3 = "<script>document.writeln(staffdatahtml)</script>";
				   															out.println("value: " + str3);%>
				   													</select>
				   												</td>
                   												<td valign='top' height='4' align='center' style="padding-top: 20px;"><input type=text name='nar' id='nar' class='form-control input_field eadd' maxlength='200' placeholder='NARRATION'></td>
                   												<!-- <td valign='top' height='4'><img src='images/delete.png' onclick='doRowDelete(this)'></td> -->
                  											</tr>
                  										</tbody>
                									</table>
              									</div>
            								</div>
										</div>
									</div>
								</form>
								<div class="row">
			 						<div class="clearfix"></div>
			 						<br/> 
									<!-- <div class="col-md-2">
										<button   name="add_data" id="add_data"  class="btn btn-info color_btn"  onclick="addRow()">ADD</button>
									</div> -->
									<div class="col-md-2">
										<button name="save_data" id="save_data" class="btn btn-info color_btn bg_color2"  onclick="saveData(this)">SAVE</button>
									</div>
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
								<th width="10%" class="text-center sml_input"> VOUCHER NO</th>
                      			<th width="10%" class="text-center sml_input">VOUCHER DATE</th>
                      			<th width="10%" class="text-center sml_input">AMOUNT</th>
                      			<th width="10%" class="text-center sml_input">RECEIVED FROM</th>
                      			<th width="10%" class="text-center sml_input">MODE OF RECEIPT</th>
                      			<th width="10%" class="text-center sml_input">INSTRUMENT DETAILS/TXR NO</th>
					  			<th width="10%" class="text-center sml_input">CREDITED TO BANK</th>
					  			<th width="10%" class="text-center sml_input">COLLECTED BY</th>
					  			<th width="10%" class="text-center sml_input">NARRATION</th>
					  			<th width="10%" class="text-center sml_input">ACTIONS</th>
                    		</tr>
                  		</thead>
                  		<tbody id="page_data_table_body"></tbody>
                	</table>
              	</div>
        	</div>
        	
        	<!-- new popup 2202018 -->
			
			
			
				<div class="modal_popup_newadd fade in" id="cvoModal" style="display: none;height: 100%">
					<div class="modal-dialog modal-lg">
			  			<!-- Modal content-->
<!-- 			  			<div class="modal-mulpopup_content" id="modal-content" style="top: -50px;">
 -->						<div class="modal-content" id="modal-content" style="top: -50px;">
                            	<div class="modal-header" id="modal-header" style="padding:5px;">
					  			<span class="close" id="closeCVOpopup" onclick="closeCVOFormDialog()">&times;</span>
					  			<h4 class="modal-title">CUSTOMER / VENDOR MASTER </h4>
							</div>
			
			 <div class="row">
        	<div class="clearfix"></div>
          	<div class="col-md-15" id="mymCVOPopupDIV" style="display:none;">
            	<div class="main_table"  style="margin-left: 37px;margin-right:37px;">
              		<div class="table-responsive">
             	 		<form id="mcvopopup_data_form" name="" method="post" action="MasterDataControlServlet">
			  				<input type="hidden" id="agencyId" name="agencyId" value="<%= agencyVO.getAgency_code() %>">
							<input type="hidden" id="page" name="page" value="jsp/pages/erp/cash&bank/receipts.jsp">
							<input type="hidden" id="actionId" name="actionId" value="9999">
							<input type="hidden" id="dataId" name="dataId" value="">
							<input type="hidden" id="popupPageId" name="popupPageId" value="7">
							<input type="hidden" id="popupId" name="popupId" value="1">
							<input type="hidden" id="effDateInFTL" name="effDateInFTL" value="<%= agencyVO.getEffective_date() %>">
              				
              				<table class="table  table-striped" id="mcvopopup_add_table">
                  				<thead>
                    				<tr class="title_head_mpopup">
                      					<th width="10%" class="text-center sml_input">PARTY TYPE</th>
                      					<th width="10%" class="text-center sml_input">PARTY/PLANT NAME</th>
                      					<th width="10%" class="text-center sml_input">GST REG</th>
                      					<th width="10%" class="text-center sml_input">GSTIN</th>
					  					<th width="10%" class="text-center sml_input">PAN</th>
                      					<th width="10%" class="text-center sml_input">ADDRESS</th>
                      					<th width="10%" class="text-center sml_input">CONTACT NO</th>
                      					<th width="10%" class="text-center sml_input">EMAIL</th>
					  					<th width="10%" class="text-center sml_input">OPENING BALANCE</th>
						 				<th width="10%" class="text-center sml_input">ACTIONS</th>
						 				
                    				</tr>
                  				</thead>
                  				<tbody id='mcvopopup_add_table_body'>
                  				</tbody>
                			</table>
                		</form>
              		</div>
            	</div>
				<div class="clearfix"></div>
          	</div>
        </div>
		<button class="btnr btn-info color_popup_btn bg_color2" onclick="addmCVOPopupRow()" href="#">ADD</button>
		<span id="savemCVOPopdiv" style="display:inline;"><button class="btnr btn-info color_popup_btn bg_color2" name="mcvodata_save_data" id="mcvodata_save_data" onclick="mcvosaveData(this)">SAVE</button></span>
		<!-- <button name="mcvo_cancel_data" id="mcvo_cancel_data"	class="btnr btn-info color_popup_btn bg_color2" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/transactions/sales/dom_refill_sales.jsp','5121')">BACK</button> -->
		 	
				
	<div class="row">
        	<div class="clearfix"></div>
          	<div class="col-md-15">
	            <div class="main_table"  style="margin-left: 37px;margin-right:37px;">
              		<div class="table-mpopup_responsive">
                		<table class="table  table-striped"  id="mcvo_popup_data_table">
                  			<thead>
                    			<tr class="title_head_mpopup">
                      				<th width="10%" class="text-center sml_input">PARTY/PLANT NAME</th>
                      				<th width="10%" class="text-center sml_input">PARTY TYPE</th>
                      				<th width="10%" class="text-center sml_input">GST REG</th>
                      				<th width="10%" class="text-center sml_input">GSTIN</th>
					   				<th width="10%" class="text-center sml_input">PAN</th>
                      				<th width="10%" class="text-center sml_addressinput">ADDRESS</th>
                      				<th width="10%" class="text-center sml_input">CONTACT NO</th>
                      				<th width="10%" class="text-center sml_input">EMAIL</th>
				  					<th width="10%" class="text-center sml_input">OPENING BALANCE</th>
				  					<th width="10%" class="text-center sml_input">CLOSING BALANCE</th>
						 			<!-- <th width="10%" class="text-center sml_input">ACTIONS</th> -->
                    			</tr>
                  			</thead>
                  			<tbody id='mcvo_cvo_data_table_body'>                    
                  			</tbody>
                		</table>
              		</div>
            	</div>
          	</div>
        </div>
      </div>
    </div>
</div>
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
					<input type="hidden" id="page" name="page" value="jsp/pages/erp/cash&bank/receipts.jsp">
					<input type="hidden" id="actionId" name="actionId" value="9999">
					<input type="hidden" id="dataId" name="dataId" value="">
	                <input type="hidden" id="popupPageId" name="popupPageId" value="7">
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
                           
                                   <!-- STAFF -->		

							
				<div class="modal_popup_newadd fade in" id="staffModal" style="display: none;height: 100%">
					<div class="modal-dialog modal-lg">
			  			<!-- Modal content-->
						<div class="modal-content" id="modal-content" style="top: -50px;">
                            	<div class="modal-header" id="modal-header" style="padding:5px;">
					  			<span class="close" id="closeStaffpopup" onclick="closeStaffFormDialog()">&times;</span>
					  			<h4 class="modal-title">STAFF MASTER</h4>
							</div>
	
			
			       <div class="row">
          			<div class="clearfix"></div>
          			<div class="col-md-15" id="myStaffDataDIV" style="display:none;">
            			<div class="main_table"  style="margin-left: 37px;margin-right:37px;">
              				<div class="table-responsive">
              					<form id="mpopupstaff_data_form" name="" method="post" action="MasterDataControlServlet">
									<input type="hidden" id="agencyId" name="agencyId" value="<%= agencyVO.getAgency_code() %>">
									<input type="hidden" id="page" name="page" value="jsp/pages/erp/cash&bank/receipts.jsp">
									<input type="hidden" id="actionId" name="actionId" value="9999">
									<input type="hidden" id="dataId" name="dataId" value="">
									<input type="hidden" id="popupPageId" name="popupPageId" value="7">
							        <input type="hidden" id="popupId" name="popupId" value="5">
						
               						<table class="table" id="mpopup_staff_add_table">
                  						<thead>
                    						<tr class="title_head_mpopup">
                      							<th width="10%" class="text-center sml_input">EMPLOYEE CODE</th>
                      							<th width="10%" class="text-center sml_input">EMPLOYEE NAME</th>
                      							<th width="10%" class="text-center sml_input">DATE OF BIRTH</th>
                      							<th width="10%" class="text-center sml_input">DESIGNATION</th>
					  							<th width="10%" class="text-center sml_input">ROLE</th>
								                <th width="10%" class="text-center sml_input">ACTIONS</th>
                    						</tr>
                  						</thead>
                  						<tbody id="mpopup_staff_add_table_body">
                  						</tbody>
                					</table>
                				</form>
              				</div>
            			</div>
						<div class="clearfix"></div>
				 	</div>
        		</div>
				<button name="add_mstaff_popup_data" id="add_mstaff_popup_data"  class="btnr btn-info color_popup_btn bg_color2" onclick="addmStaffPopupRow()">ADD</button>
				<span id="mstaff_popupsavediv" style="display:inline;"><button class="btnr btn-info color_popup_btn bg_color2" name="save_mstaffpopup_data" id="save_mstaffpopup_data" onclick="savemStaffpopupData(this)">SAVE</button></span>
			<!-- 	<button name="cancel_mstaffpopup_data" id="cancel_mstaffpopup_data"	class="btnr btn-info color_popup_btn bg_color2" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/transactions/sales/dom_refill_sales.jsp','5121')">BACK</button>
			 -->	
				<div class="row">
          		<div class="clearfix"></div>
          		<div class="col-md-15">
            		<div class="main_table" style="margin-left: 37px;margin-right:37px;">
              			<div class="table-mpopup_responsive ">
                			<table class="table table-striped" id="staff_data_table">
                  				<thead>
                    				<tr class="title_head_mpopup">
            
                      						<th class="text-center sml_mpopupinput">EMPLOYEE CODE</th>
                      						<th class="text-center sml_mpopupinput">EMPLOYEE NAME</th>
                      						<th class="text-center sml_mpopupinput">DATE OF BIRTH</th>
                      						<th class="text-center sml_mpopupinput">DESIGNATION</th>
					  						<th class="text-center sml_mpopupinput">ROLE</th>
					   				<!-- 		<th class="text-center sml_mpopupinput">ACTIONS</th> -->
                    					</tr>
                  					</thead>
                  					<tbody id="staff_mStaffpopup_data_table_body">  
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
</body>
<div id = "dialog-1" title="Alert(s)"></div>
<div id="dialog-confirm"><div id="myDialogText" style="color:black;"></div></div>
<script src="js/commons/bootstrap.min.js?<%=randomUUIDString%>"></script>
<script src="js/commons/plugins/pace.min.js?<%=randomUUIDString%>"></script>
<script src="js/commons/main.js?<%=randomUUIDString%>"></script>
<!--------  for popup start 15052019  ---------------- --> 
<script type="text/javascript" src="js/local.js?<%=randomUUIDString%>"></script>
<script type="text/javascript" src="js/modules/multipopup/mcvo_data.js?<%=randomUUIDString%>"></script>
<script type="text/javascript" src="js/modules/multipopup/mbank_data.js?<%=randomUUIDString%>"></script>
<script type="text/javascript" src="js/modules/multipopup/mstaff_data.js?<%=randomUUIDString%>"></script>

<!--------   popup end 15052019  ---------------- --> 

<script type="text/javascript" src="js/modules/cash&bank/receipts.js?<%=randomUUIDString%>"></script>
<script>
	document.getElementById("nameSpan").innerHTML = <%= agencyVO.getAgency_code() %>;
	var dedate = <%=agencyVO.getDayend_date()%>;
 	var effdate = <%=agencyVO.getEffective_date()%>;
	var a_created_date = <%=agencyVO.getCreated_date()%>;
	document.getElementById('page_data_form').agencyId = <%= agencyVO.getAgency_code() %>;	

	// for datalist
	document.querySelector('input[list]').addEventListener('input', function(e) {
	    var input = e.target,
		    list = input.getAttribute('list'),
	        options = document.querySelectorAll('#' + list + ' option'),
	        hiddenInput = document.getElementById(input.id + 'Hidden'),
	        inputValue = input.value.trim();

	    hiddenInput.value = inputValue;

	    for(var i = 0; i < options.length; i++) {
	        var option = options[i];

	        if(option.innerText.trim() === inputValue) {
	            hiddenInput.value = option.getAttribute('data-value');
	            break;
	        }
	    }
	});	
	
	$(document).ready(function() {
		$('#m_data_table').DataTable( {
			scrollY:'50vh',
			scrollCollapse: true,
			scrollX: true,
			ordering: false,
			aLengthMenu: [[5,10, 25, 50, -1], [5,10, 25, 50, "All"]],
	    } );
		
		
		/* --------  for popup start 15052019  ---------------- */ 

		
      	 $('#mcvo_popup_data_table').DataTable( {
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
	
	$('#staff_data_table').DataTable( {
		    "lengthMenu": [[5,10, 25, 50, -1], [5,10, 25, 50, "All"]],
	    	"bFilter": false,
	    	"ordering": false,
	    "scrollY":'50vh',
		        "scrollCollapse": true,
	       /*  "scrollX": true, */
        language: {
            paginate: {
            	next: '&#x003E;&#x003E;',
                previous: '&#x003C;&#x003C;'
            }
          }
	    } );
	
	/* --------   popup end 15052019  ---------------- */ 

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

	/* $(document).ready(function() {
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
	} ); */  
</script>
</html>





<%-- <!DOCTYPE html>
<html>
  	<head>
    	<meta charset="utf-8">
    	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    	<meta name="viewport" content="width=device-width, initial-scale=1">
    	<!-- CSS-->
    	<link rel="stylesheet" type="text/css" href="css/main.css">
    	<script src="js/commons/jquery-2.1.4.min.js"></script>
	    <script type="text/javascript" src="https://cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js"></script>
		<link href="https://cdn.datatables.net/1.10.15/css/jquery.dataTables.min.css" rel="stylesheet" type="text/css">
    	<title>RECEIPTS</title>
    	<jsp:useBean id="agencyVO" scope="session" class="com.it.erpapp.framework.model.vos.AgencyVO"></jsp:useBean>
		<jsp:useBean id="receipts_data" scope="request" class="java.lang.String"></jsp:useBean>	
		<jsp:useBean id="cvo_data" scope="request" class="java.lang.String"></jsp:useBean>	
		<jsp:useBean id="bank_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="staff_data" scope="request" class="java.lang.String"></jsp:useBean>
		<script type="text/javascript">
			var months = ["JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"];
			var years = ["2017","2018","2019","2020"];
			var mops = ["NONE","CASH","CHEQUE","ONLINE TRANSFER"];
			var staffdatahtml, bankdatahtml = "";
			var page_data =  <%= receipts_data.length()>0? receipts_data : "[]" %>;
			var staff_data =  <%= staff_data.length()>0? staff_data : "[]" %>;
			var bank_data =  <%= bank_data.length()>0? bank_data : "[]" %>;
			var cvo_data = <%= cvo_data.length()>0 ? cvo_data : "[]"%>;
		</script>
		<script type="text/javascript" src="js/commons/general_validations.js"></script>    
  	</head>
  	<body class="sidebar-mini fixed">
    	<div class="wrapper">
       	<!-- HEADER-->
       	<jsp:include page="/jsp/pages/commons/sidenav.jsp"/>
     	<!---HEADER END---->
      	<div class="content-wrapper">
        	<div class="page-title">
          		<div>
            		<h1>RECEIPTS</h1>
          		</div>
        	</div>
        	<div class="row">
          		<div class="clearfix"></div>
          		<div class="col-md-12" id="myDIV" style="display:none;">
            		<div class="main_table"> 
              			<div class="table-responsive">
              				<form id="page_data_form" name="" method="post" action="PPMasterDataControlServlet">
								<input type="hidden" id="agencyId" name="agencyId" value="<%= agencyVO.getAgency_code() %>">
								<input type="hidden" id="page" name="page" value="jsp/pages/erp/cash&bank/receipts.jsp">
								<input type="hidden" id="actionId" name="actionId" value="5502">
								<input type="hidden" id="itemId" name="itemId" value="">
                				<table class="table">
                  					<thead>
                  	  					<tr class="title_head">
                	  						<th width="10%" class="text-center sml_input">VOUCHER NO</th>
                  	    					<th width="10%" class="text-center sml_input">VOUCHER DATE</th>
                  	    					<th width="10%" class="text-center sml_input">AMOUNT</th>
                 	    					<th width="10%" class="text-center sml_input">RECEIVED FROM</th>
                 	    					<th width="10%" class="text-center sml_input">MODE OF RECEIPT</th>
                	    					<th width="10%" class="text-center sml_input">INSTRUMENT DETAILS</th>
											<th width="10%" class="text-center sml_input">CREDITED (BANK)</th>
											<th width="10%" class="text-center sml_input">COLLECTED BY</th>
											<th width="10%" class="text-center sml_input">NARRATION</th>
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
			<button class="btn btn-info color_btn btnadd" onclick="addRow()">ADD</button>
			<span id="savediv" style="display:none;">
				<button class="btn btn-info color_btn bg_color2" id="save_data" onclick="saveData()">SAVE</button>
			</span>
			<button class="btn btn-info color_btn bg_color2" onclick="showPage('jsp/pages/erp/home.jsp','2000')">BACK</button>	
			<br>
			<br>	
			<div class="row">
          		<div class="clearfix"></div>
          		<div class="col-md-12">
                	<table class="table  table-striped" id="m_data_table">
                  		<thead>
                    		<tr class="title_head">
								<th width="10%" class="text-center sml_input"> VOUCHER NO</th>
                      			<th width="10%" class="text-center sml_input">VOUCHER DATE</th>
                      			<th width="10%" class="text-center sml_input">AMOUNT</th>
                      			<th width="10%" class="text-center sml_input">RECEIVED FROM</th>
                      			<th width="10%" class="text-center sml_input">MODE OF RECEIPT</th>
                      			<th width="10%" class="text-center sml_input">INSTRUMENT DETAILS</th>
					  			<th width="10%" class="text-center sml_input">CREDITED TO BANK</th>
					  			<th width="10%" class="text-center sml_input">COLLECTED BY</th>
					  			<th width="10%" class="text-center sml_input">NARRATION</th>
					  			<th width="10%" class="text-center sml_input">ACTIONS</th>
                    		</tr>
                  		</thead>
                  		<tbody id="page_data_table_body"></tbody>
                	</table>
              	</div>
        	</div>
     	</div>
	</div>
</body>
	<script src="js/commons/bootstrap.min.js"></script>
	<script src="js/commons/plugins/pace.min.js"></script>
    <script src="js/commons/main.js"></script>
	<script type="text/javascript" src="js/modules/cash&bank/receipts.js"></script>
	<script>
		document.getElementById("nameSpan").innerHTML = <%= agencyVO.getAgency_code() %>;
		var dedate = <%=agencyVO.getDayend_date()%>;
		document.getElementById('page_data_form').agencyId = <%= agencyVO.getAgency_code() %>;
		$(document).ready(function() {
	       	    $('#m_data_table').DataTable( {
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
	       	} );  
	</script>
</html>



if(document.getElementById("answerInput-hidden").value=="")
 please enter received from
  	
 --%>