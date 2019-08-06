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
    	<title>RECONNECTIONS</title>
     	<jsp:useBean id="agencyVO" scope="session" class="com.it.erpapp.framework.model.vos.AgencyVO"></jsp:useBean>
		<jsp:useBean id="cylinder_types_list" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="arb_types_list" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="rcs_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="staff_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="equipment_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="services_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="refill_prices_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="prod_types_list" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="bank_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="dequipment_data" scope="request" class="java.lang.String"></jsp:useBean>
  		<script type="text/javascript">
	  		var cat_cyl_types_data = <%= cylinder_types_list %>;
  			var page_data = <%= rcs_data.length()>0? rcs_data : "[]" %>;
  			var staff_data = <%= staff_data.length()>0? staff_data : "[]" %>;
  			var equipment_data = <%= equipment_data.length()>0? equipment_data : "[]" %>;
  			var services_data = <%= services_data.length()>0? services_data : "[]" %>;
  			var refill_prices_data = <%= refill_prices_data.length()>0? refill_prices_data : "[]" %>;
  			var prod_types = <%= prod_types_list %>;
  			var bank_data = <%= bank_data %>;
  			var ctdatahtml = "";
  			var rtdatahtml = "";
  			var vendordatahtml = "";
  			var staffdatahtml = "";
  			var rcservicesdatahtml = "";
  			var months = ["JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"];

			var rcServicesId = [19,29];
			rcservicesdatahtml = "<OPTION VALUE='0'>SELECT</OPTION>";
			
			//-------------------------mstart---------------------
	        var cat_types_data = <%= cylinder_types_list %>;
			var del_equipment_data = <%= dequipment_data.length()>0? dequipment_data : "[]" %>;
			var eus = ["NONE","NOS","KGS"];
			var years = ["2018","2019","2020"];
			var roles = ["DELIVERY STAFF","SHOWROOM STAFF","GODOWN STAFF","INSPECTOR","MECHANIC","OTHERS"];			
			//-------------------------mend---------------------
			
   			if(prod_types.length>0) {
   				for(var z=0; z<prod_types.length; z++){
   					if((prod_types[z].cat_type==5) && (services_data.length>0)) {
						for(var s=0;s<services_data.length;s++){
   							if((services_data[s].prod_code == prod_types[z].id) && (rcServicesId.includes(services_data[s].prod_code))) {
   								rcservicesdatahtml = rcservicesdatahtml + "<OPTION VALUE='"+services_data[s].prod_code+"'>"
									+prod_types[z].cat_desc+" ( Rs. " + services_data[s].prod_charges+"/-)" + "</OPTION>";
								break;
							}
						}
					}
   				}
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
          			<div><h1>ITV / RC </h1></div>
					<ul id="nav" class="nav nav-pills clearfix right" role="tablist">
          				<li class="dropdown"><span class="dropdown-toggle  btn-info" data-toggle="dropdown" id="ahelp">Help</span>
							<ul id="products-menu" class="dropdown-menu clearfix" role="menu">
								<li><a style="font-size: 14px;" title="ITV or RC" href="https://www.youtube.com/watch?v=K_pwQf2adv4" target="_blank">English</a></li>
								<!-- <li><a style="font-size: 14px;" href="https://www.youtube.com/watch?v=K_pwQf2adv4" target="_blank">Hindi</a></li> -->
							</ul>
						</li>
					</ul>
        		</div>
				
				<button name="b_sb" id="b_sb" class="btn btn-info color_btn bg_color4" onclick="showRCFormDialog()">ADD</button>
				<button name="cancel_data" id="cancel_data"	class="btn btn-info color_btn bg_color2" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/home.jsp','2001')">BACK</button>
				
				<!-- Modal -->
				<div class="modal_popup_add fade in" id="myModal" style="display: none;height: 100%">					
					<div class="modal-dialog modal-lg">
			  			<!-- Modal content-->
			  			<div class="modal-content" id="modal-content">
							<div class="modal-header" id="modal-header">
					  			<span class="close" id="close" onclick="closeRCFormDialog()">&times;</span>
<!-- 					  			<h4 class="modal-title">ITV / RC  </h4>-->		
 									
 									<!-- -------------------------mstart--------------------- -->
									<b>	ITV / RC</b>
   									<button name="equipment_pop1" id="equipment_pop1" class=" btn-info color_popup_btn bg_color4 btnr" onclick="showEquipmentFormDialog()">EQUIPMENT</button>
						   			<button name="price_pop1" id="price_pop1" class=" btn-info color_popup_btn bg_color4 btnr" onclick="showPriceFormDialog()">PRICE</button>
						   			<button name="staff_pop1" id="staff_pop1" class=" btn-info color_popup_btn bg_color4 btnr" onclick="showStaffFormDialog()">STAFF</button>
 									<!-- -------------------------mend--------------------- -->

							</div>
							<div class="modal-body">
								<form id="data_form" name="" method="post" action="TransactionsDataControlServlet">
									<input type="hidden" id="agencyId" name="agencyId" value="<%= agencyVO.getAgency_code() %>">
									<input type="hidden" id="page" name="page" value="jsp/pages/erp/transactions/ncordbc/rc.jsp">
									<input type="hidden" id="actionId" name="actionId" value="5712">

									<input type="hidden" id="cgsta" name="cgsta" value="">
									<input type="hidden" id="sgsta" name="sgsta" value="">
									<input type="hidden" id="scgsta" name="scgsta" value="">
									<input type="hidden" id="ssgsta" name="ssgsta" value="">
									<input type="hidden" id="bankId" name="bankId" value="">
									<input type="hidden" id="depamt" name="depamt" value="">
									
									<input type=hidden name='optaxablea' id='optaxablea'>
									<input type=hidden name='optota' id='optota'>
									<input type=hidden name='opcgsta' id='opcgsta'>
					  				<input type=hidden name='opsgsta' id='opsgsta'>
									<input type=hidden name='opigsta' id='opigsta'>
									<input type=hidden name='opbasicprice' id='opbasicprice'>
					  												
									<div class="row">
										<div class="col-adj-res-sub">										
											<label>INVOICE DATE </label>
											<input type="date" id="rc_date" name="rc_date" class="form-control input_field" placeholder="DD-MM-YY">
										</div>
										<div class="col-adj-res-sub">
											<label>STAFF NAME <span id="sSpan"></span> </label>
										</div>
										<div class="col-adj-res-sub">
											<label>CUST NO./NAME</label>
											<input type="text" class="form-control input_field" id="custn" name="custn" maxlength="15" placeholder="CUST NO./NAME">
										</div>	
										<div class="col-adj-res-sub">
											<label>INVOICE AMOUNT </label>
											<input type="text"  name="invamt" id="invamt"  value=""  class="form-control input_field" readonly="readonly" style="background-color:#F3F3F3;"placeholder="INV AMOUNT">
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
					  											<th width="10%" class="text-center sml_input">NUMBER OF CYLINDERS	</th>
					  											<th width="10%" class="text-center sml_input">NUMBER OF REGULATORS</th>
                      											<th width="10%" class="text-center sml_input">CYLINDER DEPOSIT</th>
					  											<th width="10%" class="text-center sml_input">REGULATOR DEPOSIT</th>
					  											<th width="10%" class="text-center sml_input">ADMIN CHARGES</th>
					  											<th width="10%" class="text-center sml_input">DGCC AMOUNT</th>
					  											<th width="10%" class="text-center sml_input">GST AMOUNT</th>
					  											<th width="10%" class="text-center sml_input">PAYMENT TERMS</th>  
					  											<th width="10%" class="text-center sml_input">Actions</th>				
                    										</tr>
                  										</thead>
                  										<tbody  id="data_table_body">
                    										<tr height="30px" valign="top">
																<td valign='top' height='4' align='center'>
																	<span id="epidSpan"></span>
																	<span id="rpidSpan"></span>
																</td>
																<td height='4' align='center'><input type=text name='nocyl' id='nocyl' maxlength="2" class="form-control input_field freez"  size='2' placeholder="No Of Cylinders"></td>
																<td height='4' align='center'><input type=text name='noreg' id='noreg' maxlength="1" class="form-control input_field freez"  size='2' placeholder="No Of Regulators"></td>
																<td valign='top' height='4' align='center'>
																	<input type=text name='cyld' id='cyld' maxlength="9" class="form-control input_field freez" maxlength='8' size='4' placeholder="Cylinder Deposit">
																	<input type="hidden" id="sprp" name="spup" value="">
																	<input type="hidden" id="sprp" name="sptp" value="">
																	<input type="hidden" id="sprp" name="spa" value="">
																	<input type="hidden" id="sprp" name="spgsta" value="">
																</td>
																<td valign='top' height='4' align='center'><input type=text name='regd' id='regd' maxlength="6" class="form-control input_field freez" maxlength='6' size='4' placeholder="Regulator Deposit"></td>
																<td valign='top' height='4' align='center'>
																	<select name="acs" id="acs" class="form-control input_field acs sadd freez" onchange="fetchValueForAdminCharges()" >
    	        														<%String services = "<script>document.writeln(rcservicesdatahtml)</script>";
					   													out.println("value: " + services);%>
											   						</select>
																	<input type="hidden" name='ac' id='ac'>
																</td>
																<td valign='top' height='4' align='center'><input type=text name='dgcc' id='dgcc' class="form-control input_field qtyc" size='4' readonly='readonly' style='background-color:#F3F3F3;' placeholder="DGCC Amount"></td>
																<td valign='top' height='4' align='center'>
																	<input type=hidden name='gstsa' id='gstsa' value="0">
																	<input type=text name='gsta' id='gsta' class="form-control input_field" size='4' readonly='readonly' style='background-color:#F3F3F3;' placeholder="Gst Amount">
																</td>
																<td valign='top' height='4' align='center'><input type=text name='pt' id='pt' value="cash" maxlength="6" class="form-control input_field"  size='18' placeholder="Payment Terms" readonly></td>
																<td valign='top' height='4' align='center'><img src='images/delete.png' onclick='doRowDelete(this)'></td>
							    							</tr>
            											</tbody>
                									</table>
              									</div>
            								</div>
            							</div>
           							</div>
            					</form>	
            				</div>
            				<div class="row">	
								<div class="clearfix"></div>
								<br/>
								<div class="col-md-3" style="margin-right:10%">
									<button name="fetch_data" id="fetch_data" class="btn btn-info color_btn"  onclick="fetchReconnectionCharges()" style="margin-left: 15px;">FETCH DEPOSITS AND CHARGES</button>
								</div>
								<div class="col-md-2" >
									<button  name="calc_data" id="calc_data" class="btn btn-info color_btn" onclick="calculateValues()" disabled="disabled" style=" margin-left:-25px;">CALCULATE</button>
								</div>
								<div class="col-md-2">
									<button  name="save_data" id="save_data" class="btn btn-info color_btn bg_color2" onclick="saveData(this)" disabled="disabled" style=" margin-left: -35px;">SAVE</button>
								</div>
							</div>
        				</div>
					</div>
				</div>
				<div class="row">
          			<div class="clearfix"></div>
          			<div class="col-md-12">
            			<form id="m_data_form" name="" method="post" action="MasterDataControlServlet">
							<input type="hidden" id="agencyId" name="agencyId" value="<%= agencyVO.getAgency_code() %>">
							<input type="hidden" id="page" name="page" value="jsp/pages/erp/transactions/ncordbc/rc.jsp">
							<input type="hidden" id="actionId" name="actionId" value="">
							<input type="hidden" id="dataId" name="dataId" value="">
							<input type="hidden" id="bankId" name="bankId" value="">
                			<table class="table table-striped" id="m_data_table">
                  				<thead>
                    				<tr class="title_head">
					  					<th width="10%" class="text-center sml_input"> INV NO</th>
                      					<th width="10%" class="text-center sml_input">DATE</th>
					  					<th width="10%" class="text-center sml_input">STAFF NAME</th>
                      					<th width="10%" class="text-center sml_input">CUST NO./NAME</th>
                      					<th width="10%" class="text-center sml_input">PRODUCT</th>
					  					<th width="10%" class="text-center sml_input">NUMBER OF CYLINDERS	</th>
					  					<th width="10%" class="text-center sml_input">NUMBER OF REGULATORS</th>
                      					<th width="10%" class="text-center sml_input">CYLINDER DEPOSIT</th>
										<th width="10%" class="text-center sml_input">REGULATOR DEPOSIT</th>
					 	 				<th width="10%" class="text-center sml_input">DGCC AMOUNT</th>
					  					<th width="10%" class="text-center sml_input">ADMIN CHARGES</th>
					  					<th width="10%" class="text-center sml_input">CGST AMOUNT</th>
					 	 				<th width="10%" class="text-center sml_input">SGST AMOUNT</th>
					  					<th width="10%" class="text-center sml_input">PAYMENT TERMS</th>
					  					<th width="10%" class="text-center sml_input">INV AMOUNT</th>
					  					<th width="10%" class="text-center sml_input">Actions</th>
									</tr>
                  				</thead>
                  				<tbody id= "m_data_table_body"></tbody>
                			</table>
                		</form>
              		</div>
        		</div>
        			
        			
<!------------------------------------------------------------------------- NEW POPUP 2202018 --------------------------------------------------------------------------->
			
				<!----------------------------------------------------- EQUIPMENT --------------------------------------------------------------------->		

				<div class="modal_popup_newadd fade in" id="equipmentModal" style="display: none;height: 100%">
					<div class="modal-dialog modal-lg">
			  			<!-- Modal content-->
<!-- 			  			<div class="modal-mulpopup_content" id="modal-content" style="top: -50px;">
 -->						<div class="modal-content" id="modal-content" style="top: -50px;">
                            	<div class="modal-header" id="modal-header" style="padding:5px;">
					  				<span class="close" id="closeEquipmentpopup" onclick="closeEquipmentFormDialog()">&times;</span>
					  				<h4 class="modal-title">EQUIPMENT MASTER</h4>
								</div>
			
           						<div class="row">
          							<div class="clearfix"></div>
          							<div class="col-md-15" id="mequipopup_page_add_table_div" style="display:none;">
            							<div class="main_table" style="margin-left: 37px;margin-right:37px;">
              								<div class="table-responsive">
              									<form id="mequipopup_page_add_form" name="mequipopup_page_add_form" method="post" action="PPMasterDataControlServlet">
													<input type="hidden" id="agencyId" name="agencyId" value="<%= agencyVO.getAgency_code() %>">
													<input type="hidden" id="page" name="page" value="jsp/pages/erp/transactions/ncordbc/rc.jsp">
													<input type="hidden" id="actionId" name="actionId" value="9999">
													<input type="hidden" id="itemId" name="itemId" value="">
													<input type="hidden" id="popupPageId" name="popupPageId" value="11">
						       					 	<input type="hidden" id="popupId" name="popupId" value="2">
						
                									<table class="table table-striped"  id="mequipmentpopup_add_table">
                  										<thead>
                    										<tr class="title_head_mpopup">
                      											<th width="10%" class="text-center"> PRODUCT</th>
                      											<th width="10%" class="text-center">Units</th>
                      											<th width="10%" class="text-center">Gst %</th>
                      											<th width="10%" class="text-center">Hsn Code</th>
                      											<th width="10%" class="text-center">SECURITY DEPOSIT</th>
                      											<th width="10%" class="text-center">FULLS</th>
					  											<th width="10%" class="text-center">EMPTIES</th>
					  											<th width="10%" class="text-center">EFFECTIVE DATE</th>
					  											<th width="10%" class="text-center">ACTIONS</th>
                    										</tr>
                  										</thead>
                  										<tbody id="mequipopup_page_add_table_body"></tbody>
                									</table>
                								</form>
              								</div>
            							</div>
										<div class="clearfix"></div>
          							</div>
        						</div>
								<button name="mequipopup_add_data" id="mequipopup_add_data"  class="btnr btn-info color_popup_btn bg_color2" onclick="addmEquipopupRow()">ADD</button>
								<span id="mequipopupsavediv" style="display:inline;"><button class="btnr btn-info color_popup_btn bg_color2" name="mequipopup_save_data" id="mequipopup_save_data" onclick="mequipoupsaveData(this)">SAVE</button></span>
<!-- 							<button name="mequipopup_cancel_data" id="mequipopup_cancel_data"	class="btnr btn-info color_popup_btn bg_color2" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/transactions/sales/dom_refill_sales.jsp','5121')">BACK</button>
 -->							<div class="row">
          							<div class="clearfix"></div>
          							<div class="col-md-15">
            							<div class="main_table" style="margin-left: 37px;margin-right:37px;">
              								<div class="table-mpopup_responsive">
                								<table class="table  table-striped" id="mequipment_popup_data_table">
                  									<thead>
                    									<tr class="title_head_mpopup">
                      										<th width="30%" class="text-center sml_equippopup_input">PRODUCT</th>
                      										<th width="20%" class="text-center sml_equippopup_input">UNITS</th>
                      										<th width="30%" class="text-center sml_equippopup_input">GST %</th>
                      										<th width="30%" class="text-center sml_equippopup_input">HSN CODE</th>
                      										<th width="30%" class="text-center sml_equippopup_input">SECURITY DEPOSIT</th>
					  										<th width="30%" class="text-center sml_equippopup_input">FULLS</th>
					  										<th width="30%" class="text-center sml_equippopup_input">EMPTIES</th>
					  										<th width="30%" class="text-center sml_equippopup_input">EFFECTIVE DATE</th>
					  								<!-- 	<th width="30%" class="text-center sml_input">Actions</th> -->
                    									</tr>
                  									</thead>
                  									<tbody id="mequipopup_page_data_table_body"></tbody>
                								</table>
              								</div>
           								</div>
          							</div>
        						</div>
							</div>
						</div>
					</div>		

                    <!----------------------------------------------------- PRICE MASTER ----------------------------------------------------------->
                                    

	       			<div class="modal_popup_newadd fade in" id="refillPriceModal" style="display: none;height: 100%">
						<div class="modal-dialog modal-lg">
			  				<!-- Modal content-->
<!-- 			  			<div class="modal-mulpopup_content" id="modal-content" style="top: -50px;">
 -->						<div class="modal-content" id="modal-content" style="top: -50px;">
                            	<div class="modal-header" id="modal-header" style="padding:5px;">
					  				<span class="close" id="closeRefillPricepopup" onclick="closeRefillPriceFormDialog()">&times;</span>
					  				<h4 class="modal-title">REFILL PRICE MASTER</h4>
								</div>
			
								<div class="row">
        							<div class="clearfix"></div>
         							<div class="col-md-15" id="mymRefillPricePopupDIV" style="display:none;">
            							<div class="main_table" style="margin-left: 37px;margin-right:37px;">
            								<div class="table-responsive">
												<form id="mrefiilpricepopup_page_data_form" name="" method="post" action="PPMasterDataControlServlet">
													<input type="hidden" id="agencyId" name="agencyId" value="<%= agencyVO.getAgency_code() %>">
													<input type="hidden" id="page" name="page" value="jsp/pages/erp/transactions/ncordbc/rc.jsp">
													<input type="hidden" id="actionId" name="actionId" value="9999">
													<input type="hidden" id="itemId" name="itemId" value="">
													<input type="hidden" id="popupPageId" name="popupPageId" value="11">
													<input type="hidden" id="popupId" name="popupId" value="3">
													
													<table id="mrefillpricepopup_page_add_table" class="table table-striped">
														<thead>
                    										<tr class="title_head_mpopup">
                      											<th width="10%" class="text-center sml_input"> PRODUCT</th>
                      											<th width="10%" class="text-center sml_input">RSP</th>
                			   									<th width="10%" class="text-center sml_input">BASIC PRICE</th>
                      											<th width="10%" class="text-center sml_input">SGST AMOUNT</th>
                      											<th width="10%" class="text-center sml_input">CGST AMOUNT</th>
                      											<th width="10%" class="text-center sml_input">OFFER PRICE</th>
                      											<th width="10%" class="text-center sml_input">MONTH</th>
					    										<th width="10%" class="text-center sml_input">YEAR</th>
						 										<th width="10%" class="text-center sml_input">ACTIONS</th>
                    										</tr>
                  										</thead>
                  										<tbody id="mrefillpricepopup_page_add_table_body"></tbody>
                									</table>
                								</form>	
             								</div>
           								</div>
            							<div class="clearfix"></div>
         							</div>
       							</div>
       							<span id="mrefillOfpNote" style="display:none;color:red;"> * Please &nbspenter &nbspOFFER PRICE &nbspalong &nbspwith &nbspit's &nbspGST &nbspamount.</span>
								<button class="btnr btn-info color_popup_btn bg_color2" onclick="mrefillPricePopupaddRow()">ADD</button>
								<span id="savemrefillpricepopupdiv" style="display:inline;">
									<button class="btnr btn-info color_popup_btn bg_color2" id="mrefillpricepopup_calculate_data" onclick="calculatemrefillpriceValues()" >CALCULATE</button>
									<button class="btnr btn-info color_popup_btn bg_color2" id="save_mrefillpricepopup_data" onclick="savemRefillpricepopupData(this)" disabled="disabled">SAVE</button>
								</span>
<!-- 							<button name="cancel_refillpricedata" id="cancel_refillpricedata"	class="btnr btn-info color_popup_btn bg_color2" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/transactions/sales/dom_refill_sales.jsp','5121')">BACK</button>
 -->		
								<div class="row">
        							<div class="clearfix"></div>
          							<div class="col-md-15">
           								<div class="main_table"  style="margin-left: 37px;margin-right:37px;">
             								<div class="table-mpopup_responsive">
                								<table class="table  table-striped" id="mpricepopup_page_data_table">
                 									<thead>
                   										<tr class="title_head_mpopup">
                       										<th width="20%" class="text-center sml_refillpricepopup_input">PRODUCT</th>
                      										<th width="20%" class="text-center sml_refillpricepopup_input">RSP</th>
                      										<th width="20%" class="text-center sml_refillpricepopup_input">BASIC PRICE</th>
                       										<th width="20%" class="text-center sml_refillpricepopup_input">SGST AMOUNT</th>
                       										<th width="20%" class="text-center sml_refillpricepopup_input">CGST AMOUNT</th>
                       										<th width="20%" class="text-center sml_refillpricepopup_input">OFFER PRICE</th>
                       										<th width="20%" class="text-center sml_refillpricepopup_input">MONTH</th>
					   										<th width="20%" class="text-center sml_refillpricepopup_input">YEAR</th>
				   <!--     								<th width="20%" class="text-center sml_input">ACTIONS</th> -->
                   										</tr>
                  									</thead>
                  									<tbody id="mrefillpricepopup_page_data_table_body"></tbody>
               									</table>
             								</div>
            							</div>			
          							</div>
       							</div>
      						</div>
      					</div>
					
					   <!---------------------------------- security popup pin div ------------------------------------------------------>
					   
          				<div class="modal_popup_add fade in" id="myModalmRefilpricepopupPin" style="display:block;padding-left: 14px; height: 100%">
      						<div class="modal-dialog modal-lg">
        						<!-- Modal content-->
            					<div class="modal-content prof_modalContent" style="height:200px;width:60%; margin:auto;">
									<div id="mRefilpricepopupcontentDiv">
										<div class="modal-header" style="text-align:center;">
											<span class="close" id="closeRefillPricepopup" onclick="closeRefillPriceFormDialog()">&times;</span>
											<h4 class="modal-title">ENTER SECURITY PIN DETAILS</h4>
                  						</div>
                    					<div class="modal-body">
                    						<form id="pin_data_form">
												<input type="hidden" id="pinNO" name="pinNO" value="<%= agencyVO.getPinNumber() %>">
                        						<table id="mRefilpricepopup_t_data_table">
													<tr class="spaceUnder">
														<td>Pin Number:&nbsp;&nbsp;&nbsp;&nbsp;</td>
														<td><input class="form-control" type="password" name="enteredPin" id="enteredPin" placeholder="Enter pin here"></td>
														<td colspan="6">
															<div class="submit_mRefilpricepopup_div" style="text-align: center; width: 100px; margin-left: 40px; margin-top: -3px;">
																<input type="button" name="prof_submit_mRefilpricepopup_btn" id="prof_submit_mRefilpricepopup_btn" value="SUBMIT" class="btn btn-success m-r-15" onclick="submitmRefillPricePopupPinNumber()">
															</div>
														</td>
													</tr>
												</table>
												<div class="submit_mRefilpricepopup_div" style="text-align: center; width: 100px; margin-left: 2px; margin-top: 10px;">
<!--                   								<button name="prof_submit_mRefilpricepopup_btn" id="prof_submit_mRefilpricepopup_btn" class="btn btn-success m-r-15" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/transactions/sales/dom_refill_sales.jsp','5121')">BACK</button> -->
                  								</div>
                  							</form>
                						</div>
               						</div>
                					<div id="displaymRefillPricePopupDiv">
        								<div class="modal-header" style="text-align:center;">
    	            						<h4 class="modal-title">SECURITY PIN DETAILS</h4>
    	            					</div>
    	            					<div class="modal-body"><br>
        									YOU  HAVEN'T  SET  ANY  PROFILE  PIN.
        									<a href="#prof" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/profile.jsp','9001')">CLICK HERE</a>
        									 TO  SET  PIN  OR  SET  PIN  IN  YOUR  PROFILE  DETAILS  AND  THEN  PROCEED  <br><br>
        									<div class="submit_mRefilpricepopup_div" style="text-align: center; width: 100px; margin-left: 200px;">
<!--                  			 				<button name="prof_submit_mRefilpricepopup_btn" id="prof_submit_mRefilpricepopup_btn" class="btn btn-success m-r-15" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/transactions/sales/dom_refill_sales.jsp','5121')">BACK</button> -->
		              						</div>
        								</div>	
        							</div>
								</div>
       						</div>
 						</div>
					</div>
		        
                                   
				<!-------------------------------------------------- STAFF ------------------------------------------------------------>		

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
												<input type="hidden" id="page" name="page" value="jsp/pages/erp/transactions/ncordbc/rc.jsp">
												<input type="hidden" id="actionId" name="actionId" value="9999">
												<input type="hidden" id="dataId" name="dataId" value="">
												<input type="hidden" id="popupPageId" name="popupPageId" value="11">
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
                  									<tbody id="mpopup_staff_add_table_body"></tbody>
                								</table>
                							</form>
              							</div>
            						</div>
									<div class="clearfix"></div>
				 				</div>
        					</div>
							<button name="add_mstaff_popup_data" id="add_mstaff_popup_data"  class="btnr btn-info color_popup_btn bg_color2" onclick="addmStaffPopupRow()">ADD</button>
							<span id="mstaff_popupsavediv" style="display:inline;"><button class="btnr btn-info color_popup_btn bg_color2" name="save_mstaffpopup_data" id="save_mstaffpopup_data" onclick="savemStaffpopupData(this)">SAVE</button></span>
<!-- 						<button name="cancel_mstaffpopup_data" id="cancel_mstaffpopup_data"	class="btnr btn-info color_popup_btn bg_color2" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/transactions/sales/dom_refill_sales.jsp','5121')">BACK</button>
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
					   				<!-- 				<th class="text-center sml_mpopupinput">ACTIONS</th> -->
                    								</tr>
                  								</thead>
												<tbody id="staff_mStaffpopup_data_table_body"></tbody>
                							</table>
              							</div>
            						</div>
          						</div>
        					</div>
      					</div>
    				</div>
  				</div>
<!----------------------------------------------------	NEW POPUP end  ---------------------------------------------------->
      			
      		</div>
      	</div>
	</body>
		
	<div id = "dialog-1" title="Alert(s)"></div>
 	<div id="dialog-confirm"><div id="myDialogText" style="color:black;"></div></div>
    	
    <script type="text/javascript" src="js/modal.js?<%=randomUUIDString%>"></script>
	<script type="text/javascript" src="js/commons/general_validations.js?<%=randomUUIDString%>"></script>
	<script type="text/javascript" src="js/modules/transactions/ncordbc/rcs.js?<%=randomUUIDString%>"></script>
	<script src="js/commons/bootstrap.min.js?<%=randomUUIDString%>"></script>
	<script src="js/commons/plugins/pace.min.js?<%=randomUUIDString%>"></script>
    <script src="js/commons/main.js?<%=randomUUIDString%>"></script>
    	 
    <!--------  for popup start 15052019  ---------------- --> 
    	    
	<script type="text/javascript">
		var checkDisplay = <%=request.getAttribute("checkDisplay") %>;
		if(!checkDisplay)
			checkDisplay="0";
		var pinNum = <%= agencyVO.getPinNumber() %>;
	</script>
  	<script type="text/javascript" src="js/modules/multipopup/mequipment_data.js?<%=randomUUIDString%>"></script>
  	<script type="text/javascript" src="js/modules/multipopup/mrefill_prices_data.js?<%=randomUUIDString%>"></script>
  	<script type="text/javascript" src="js/modules/multipopup/mstaff_data.js?<%=randomUUIDString%>"></script>  		
  		
  	<!--------   popup end 15052019  ---------------- --> 
		
		
	<script type="text/javascript">
 		document.getElementById("nameSpan").innerHTML = <%= agencyVO.getAgency_code() %>;
 		var dedate = <%=agencyVO.getDayend_date()%>;
 	     var effdate = <%=agencyVO.getEffective_date()%>;
 		var a_created_date = <%=agencyVO.getCreated_date()%>;
 			
	    $(document).ready(function() {
			$('#m_data_table').DataTable( {
	//	   	    "bPaginate" : $('#m_data_table tbody tr').length>5,
	//		   	 "iDisplayLength": 5,
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
		   	    
		   	    
		   	$('#mequipment_popup_data_table').DataTable( {
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
	       	    

			$('#mpricepopup_page_data_table').DataTable( {
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
	       	    
	    
			$('#staff_data_table').DataTable( {
				"lengthMenu": [[5,10, 25, 50, -1], [5,10, 25, 50, "All"]],
				"bFilter": false,
				"ordering": false,
				"scrollY":'50vh',
				"scrollCollapse": true,
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
		} );
	</script>
</html>