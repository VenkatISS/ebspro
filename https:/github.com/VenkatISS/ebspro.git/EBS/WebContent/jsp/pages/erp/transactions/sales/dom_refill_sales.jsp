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

    	<title>DOMESTIC CYLINDER SALES </title>
    	<jsp:useBean id="agencyVO" scope="session" class="com.it.erpapp.framework.model.vos.AgencyVO"></jsp:useBean>
    	<jsp:useBean id="cl_data" scope="request" class="java.lang.String"></jsp:useBean>
   		<jsp:useBean id="cylinder_types_list" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="bank_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="cvo_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="area_codes_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="staff_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="equipment_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="refill_prices_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="drs_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="dequipment_data" scope="request" class="java.lang.String"></jsp:useBean>

		<script type="text/javascript">
			var cat_cyl_types_data = <%= cylinder_types_list.length()>0? cylinder_types_list: "[]" %>;
			var page_data = <%= drs_data.length()>0? drs_data : "[]" %>;
			var bank_data = <%= bank_data.length()>0? bank_data: "[]"%>;
			var cvo_data = <%= cvo_data.length()>0? cvo_data : "[]" %>;
			var staff_data = <%= staff_data.length()>0? staff_data : "[]" %>;
			var area_codes_data = <%= area_codes_data.length()>0? area_codes_data : "[]" %>;
			var equipment_data = <%= equipment_data.length()>0? equipment_data : "[]" %>;
			var refill_prices_data = <%= refill_prices_data.length()>0? refill_prices_data : "[]" %>;
			var ctdatahtml = "";
			var vendordatahtml = "";
			var staffdatahtml = "";
			var areacodeshtml = "";
			var bankdatahtml = "";
			var cust_cl_data = <%= cl_data.length()>0? cl_data: "[]" %>;
			
			var months = ["JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"];
			var payment_terms_values = ["NONE","CASH","CREDIT"];
			
			
			//-------------------------mstart---------------------
	           var cat_types_data = <%= cylinder_types_list %>;
				var del_equipment_data = <%= dequipment_data.length()>0? dequipment_data : "[]" %>;

		    	var ctdatahtml, ppctdatahtml, mpopcdatahtml, mvdatahtml, yvdatahtml = "";
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
				var eus = ["NONE","NOS","KGS"];
				var years = ["2018","2019","2020"];
				var roles = ["DELIVERY STAFF","SHOWROOM STAFF","GODOWN STAFF","INSPECTOR","MECHANIC","OTHERS"];


	//-------------------------mend---------------------
			//Construct Cylinder list
			ctdatahtml = "<OPTION VALUE='0'>SELECT</OPTION>";
			if(cat_cyl_types_data.length>0) {
				for(var z=0; z<cat_cyl_types_data.length; z++){
					if(cat_cyl_types_data[z].cat_name=='DOMESTIC') {
						//ctdatahtml=ctdatahtml+"<OPTION VALUE='"+cat_cyl_types_data[z].id+"'>"+cat_cyl_types_data[z].cat_code+"-"
						//+cat_cyl_types_data[z].cat_name+"-"+cat_cyl_types_data[z].cat_desc+"</OPTION>";
						for(var y=0; y<refill_prices_data.length; y++){
							if(refill_prices_data[y].prod_code == cat_cyl_types_data[z].id) {
								ctdatahtml = ctdatahtml+"<OPTION VALUE='"+cat_cyl_types_data[z].id+"'>"
											+cat_cyl_types_data[z].cat_name+"-"+cat_cyl_types_data[z].cat_desc+"</OPTION>";
								break;
							}
						}
					}
				}
			}
			//Construct Staff html
			staffdatahtml = "<OPTION VALUE='0'>SELECT</OPTION>";
			if(staff_data.length>0) {
				for(var z=0; z<staff_data.length; z++){
					if(staff_data[z].deleted == 0){
						staffdatahtml=staffdatahtml+"<OPTION VALUE='"+staff_data[z].id+"'>"+staff_data[z].emp_name+"</OPTION>";
					}
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
		
			//Area codes html
			areacodeshtml = "<OPTION VALUE='0'>SELECT</OPTION>";
			if(area_codes_data.length>0) {
				for(var z=0; z<area_codes_data.length; z++){
					if(area_codes_data[z].deleted == 0){
						areacodeshtml=areacodeshtml+"<OPTION VALUE='"+area_codes_data[z].id+"'>"+area_codes_data[z].area_code+" - "+area_codes_data[z].area_name+"</OPTION>";
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
          			<div><h1>DOMESTIC CYLINDER SALES</h1></div>
					<ul id="nav" class="nav nav-pills clearfix right" role="tablist">
          				<li class="dropdown"><span class="dropdown-toggle  btn-info" data-toggle="dropdown" id="ahelp">Help</span>
							<ul id="products-menu" class="dropdown-menu clearfix" role="menu">
								<li><a style="font-size: 14px;" title="domestic-cylinder-sales" href="https://www.youtube.com/watch?v=Guuo9CVH3-o" target="_blank">English</a></li>
								<!-- <li><a style="font-size: 14px;" href="https://www.youtube.com/watch?v=Guuo9CVH3-o" target="_blank">Hindi</a></li> -->
							</ul>
						</li>
					</ul>
        		</div>
				<button name="b_sb" id="b_sb" class="btn btn-info color_btn bg_color4" onclick="showDOMRSalesFormDialog()">ADD</button>
				<button name="cancel_data" id="cancel_data"	class="btn btn-info color_btn bg_color3" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/home.jsp','2001')">BACK</button>
				<!-- Modal -->
				<div class="modal_popup_add fade in" id="myModal" style="display: none;height: 100%">
					<div class="modal-dialog modal-lg">
			  			<!-- Modal content-->
			  			<div class="modal-content" id="modal-content">
							<div class="modal-header" id="modal-header">
					  			<span class="close" id="close" onclick="closeDOMRSalesFormDialog()">&times;</span>
					<!--   			<h4 class="modal-title">DOMESTIC CYLINDER SALES </h4> -->
					
					<!-- -------------------------mstart--------------------- -->
							<b>DOMESTIC CYLINDER SALES</b>
							<button name="cvo_pop1" id="cvo_pop1" class=" btn-info color_popup_btn bg_color4 btnr" onclick="showCVOFormDialog()">CVO</button>
							<button name="equipment_pop1" id="equipment_pop1" class=" btn-info color_popup_btn bg_color4 btnr" onclick="showEquipmentFormDialog()">EQUIPMENT</button>
						    <button name="price_pop1" id="price_pop1" class=" btn-info color_popup_btn bg_color4 btnr" onclick="showPriceFormDialog()">PRICE</button>
						    <button name="bank_pop1" id="bank_pop1" class=" btn-info color_popup_btn bg_color4 btnr" onclick="showBankFormDialog()">BANK</button>
						    <button name="staff_pop1" id="staff_pop1" class=" btn-info color_popup_btn bg_color4 btnr" onclick="showStaffFormDialog()">STAFF</button>
							<button name="areacode_pop1" id="areacode_pop1" class=" btn-info color_popup_btn bg_color4 btnr" onclick="showAreaCodeFormDialog()">AREACODE</button>						

					<!-- -------------------------mend--------------------- -->
							</div>
							<div class="modal-body">
								<form id="data_form" name="" method="post" action="TransactionsDataControlServlet">
									<input type="hidden" id="agencyId" name="agencyId" value="<%= agencyVO.getAgency_code() %>">
									<input type="hidden" id="page" name="page" value="jsp/pages/erp/transactions/sales/dom_refill_sales.jsp">
									<input type="hidden" id="bankId" name="bankId" value="">
									<input type="hidden" id="actionId" name="actionId" value="5122">
									<div class="row">
										<div class="col-adj-res-sub">
											<label>SALES INVOICE DATE </label>
											<input type="date" class="form-control input_field sinvd freez"  id="si_date" name="si_date" placeholder="DD-MM-YY">
										</div>
										<div class="col-adj-res-sub">
											<label>CUSTOMER NAME  </label>
											<span id="cSpan"></span>
										</div>
										<div class="col-adj-res-sub">
											<label>PAYMENT TERMS </label>
											<select class="form-control input_field select_dropdown sadd freez" name='pt' id='pt' onchange="setPPCValueAndBankAcct()">
						  						<option value="0">SELECT</option>
						 						<option value="1">CASH</option>
						   						<option value="2">CREDIT</option>						   						
											</select>
										</div>
										<div class="col-adj-res-sub">
											<label>SALES INVOICE AMOUNT </label>
											<input type="text" class="form-control input_field" id="si_amt" name="si_amt" value="" readonly="readonly" style="background-color:#F3F3F3;" placeholder="INV AMOUNT">
						    				<input type="hidden" id="c_amt" name="c_amt" value="">
										</div>
										<div class="clearfix"></div>
									</div>
									<br/>
									<div class="row">
                    					<div class="clearfix"></div>
                    					<div class="col-md-12">
                    						<div class="main_table">
                    							<div class="table-responsive">
                									<table class="table">
                  										<thead>
                    										<tr class="title_head">
					   											<th width="10%" class="text-center sml_input">PRODUCT</th>
					   											<th width="10%" class="text-center sml_input">GST %</th>
					    										<th width="10%" class="text-center sml_input">UNIT PRICE</th>
																<th width="10%" class="text-center sml_input">DISCOUNT ON UNIT PRICE</th>
																<th width="10%" class="text-center sml_input">QUANTITY</th>
																<th width="10%" class="text-center sml_input">DIGI / PREPAID CYLINDERS</th>
																<th width="10%" class="text-center sml_input">PSV QUANTITY</th>
																<th width="10%" class="text-center sml_input">DELIVERED BY</th>
																<th width="10%" class="text-center sml_input">BANK ACCOUNT</th>
																<th width="10%" class="text-center sml_input">AREA CODE</th>
																<th width="10%" class="text-center sml_input">TRANSACTION<br>AMOUNT</th>
																<th width="10%" class="text-center sml_input">TOTAL AMOUNT</th>
																<th width="10%" class="text-center sml_input">ONLINE RCVD AMOUNT</th>
						 										<th width="10%" class="text-center sml_input">Actions</th>
                    										</tr>
                  										</thead>
                  										<tbody id="data_table_body">
                    										<tr>
                      											<td>
                      												<select class="form-control input_field select_dropdown sadd pid freez" name='epid' id='epid'>
						   												<%String str = "<script>document.writeln(ctdatahtml)</script>";
						   														out.println("value: " + str);%>
																	</select>
																</td>
                      											<td>
                      												<input type="text" name='vatp' class='form-control input_field eadd' id='vatp' size='6' readonly='readonly' style='background-color:#FAFAC2;'  placeholder="Gst%">
                      											</td>
					    										<td>
					    											<input type="text" name='up'  class='form-control input_field eadd' id='up' size='6' maxlength='7' value='0' readonly='readonly' style='background-color:#FAFAC2;' placeholder="Unit Price">
					    										</td>
																<td>
																	<input type="text" name='upd' class='form-control input_field freez eadd' id='upd' maxlength='7' size='6' value='0' placeholder=" Discount On Unit Price">
																</td>
						 										<td>
						 											<input type="text" name='qty' class='form-control input_field qtyc freez eadd' id='qty' maxlength='4' size='6' placeholder="Quantity">
						 										</td>
						 										<td>
						 											<input type="text" name='prec' class='form-control input_field freez eadd' id='pre' maxlength='3' size='6' placeholder="Prepaid Cylinders">
						 										</td>
						 										<td>
						 											<input type="text" name='psvc' class='form-control input_field freez eadd' id='psvc' maxlength='3' size='6' placeholder="PSV Quantity">
						 										</td>
						 										<td>
						 											<select class="form-control input_field select_dropdown" name='sid' id='sid'>
						   													<%String str2 = "<script>document.writeln(staffdatahtml)</script>";
						   															out.println("value: " + str2);%>
																	</select>
																</td>
						  										<td>
						 											<select name="bid" id="bid" class="form-control input_field select_dropdown freez">
                  														<%String str4 = "<script>document.writeln(bankdatahtml)</script>";
				   															out.println("value: " + str4);%>
				   													</select>
																</td>
							 									<td>
							 									<select class="form-control input_field select_dropdown" name='acid' id='acid'>
						   													<%String str3 = "<script>document.writeln(areacodeshtml)</script>";
						   															out.println("value: " + str3);%>
																	</select>
																</td>
																<td>
																<input type=text name='txnamt' class='form-control input_field eadd' id='txnamt' size='6' readonly='readonly' style='background-color:#F3F3F3;' placeholder='Transaction Amount'></td>
						  										<td>
						  											<input type="text" name='siamt' class='form-control input_field eadd' id='siamt' size='6' readonly='readonly' style='background-color:#F3F3F3;' placeholder="Sale Amount">
					  												<input type=hidden name='igsta' id='igsta'>
					  												<input type=hidden name='cgsta' id='cgsta'>					  												
						  											<input type=hidden name='sgsta' id='sgsta'>
					  												<input type=hidden name='ppsiamt' id='ppsimat'>
					  												
					  												<input type=hidden name='optaxablea' id='optaxablea'>
					  												<input type=hidden name='optota' id='optota'>
					  												<input type=hidden name='opcgsta' id='opcgsta'>
					  												<input type=hidden name='opsgsta' id='opsgsta'>
					  												<input type=hidden name='opigsta' id='opigsta'>
					  												<input type=hidden name='opbasicprice' id='opbasicprice'>
					  												
						  										</td>
																
																<td>
																	<input type=text name='onlinercvdamt' class='form-control input_field eadd' id='onlinercvdamt' size='6' readonly='readonly' style='background-color:#F3F3F3;' placeholder='Online rcvd Amount'>
																</td>
                      											<td><img src='images/delete.png' class="freez" onclick='doRowDeleteInTranxs(this,siamt,si_amt,data_table_body,c_amt,txnamt)'></td>
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
									<div class="col-md-2">
										<button name="add_data" id="add_data"  class="btn btn-info color_btn" onclick="addRow()">ADD</button>
									</div>
									<div class="col-md-2">
										<button name="fetch_data" class="btn btn-info color_btn fetch" id="fetch_data"  onclick="fetchPriceAndVAT()">FETCH PRICE</button>
									</div>
									<div class="col-md-2">
										<button name="calc_data" class="btn btn-info color_btn" id="calc_data" onclick="calculateValues()" disabled="disabled">CALCULATE</button>
									</div>
									<div class="col-md-2">
										<button name="save_data" class="btn btn-info color_btn bg_color2" id="save_data"  onclick="saveData(this)" disabled="disabled">SAVE</button>
									</div>
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
								<input type="hidden" id="page" name="page" value="jsp/pages/erp/transactions/sales/dom_refill_sales.jsp">
								<input type="hidden" id="actionId" name="actionId" value="">
								<input type="hidden" id="dataId" name="dataId" value="">
                				<table class="table table-striped" id="m_data_table">
                  					<thead>
                    					<tr class="title_head">
											<th width="10%" class="text-center sml_input"> INV NO</th>
                      						<th width="10%" class="text-center sml_input">INVOICE DATE</th>
                      						<th width="10%" class="text-center sml_input">CUSTOMER</th>
					  						<th width="10%" class="text-center sml_input">PAYMENT TERMS</th>                      						
                      						<th width="10%" class="text-center sml_input">TRANSACTION<br>AMOUNT</th>
					  						<th width="10%" class="text-center sml_input">INV AMOUNT</th>
					  						<th width="10%" class="text-center sml_input">PRODUCT</th>
					  						<th width="10%" class="text-center sml_input">UNIT PRICE</th>
					  						<th width="10%" class="text-center sml_input">DISCOUNT AMOUNT</th>
                      						<th width="10%" class="text-center sml_input">QUANTITY</th>
					  						<th width="10%" class="text-center sml_input">IGST AMOUNT</th>                      						
					  						<th width="10%" class="text-center sml_input">CGST AMOUNT</th>
					   						<th width="10%" class="text-center sml_input">SGST AMOUNT</th>
					   						<th width="10%" class="text-center sml_input">TOTAL AMOUNT</th>
					    					<th width="10%" class="text-center sml_input">DIGI / PRE PAID QUANTITY</th>
					  						<th width="10%" class="text-center sml_input">PSV QUANTITY</th>
					  						<th width="10%" class="text-center sml_input">DELIVERED BY</th>
											<th width="10%" class="text-center sml_input">AREA CODE</th>
											<th width="10%" class="text-center sml_input">BANK ACCOUNT</th>
											<th width="10%" class="text-center sml_input">ONLINE RCVD AMOUNT</th>
											
											<th width="10%" class="text-center sml_input">Actions</th>
                   						</tr>
                  					</thead>
                  					<tbody id="m_data_table_body"></tbody>
                				</table>
                			</form>
              			</div>
        			</div>
        			
        			<!-- new popup 2202018 -->
			
			
			<!-- <button name="cvo_pop1" id="cvo_pop1" class="btn btn-info color_btn bg_color4" onclick="showCVOFormDialog()">CVO</button>
				<button name="cancel_data" id="cancel_data"	class="btn btn-info color_btn bg_color3" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/home.jsp','2001')">BACK</button>
			 -->	<!-- Modal -->
				<div class="modal_popup_newadd fade in" id="cvoModal" style="display: none;height: 100%">
					<div class="modal-dialog modal-lg">
			  			<div class="modal-content" id="modal-content" style="top: -50px;font-size:10px;">
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
												<input type="hidden" id="page" name="page" value="jsp/pages/erp/transactions/sales/dom_refill_sales.jsp">
												<input type="hidden" id="actionId" name="actionId" value="9999">
												<input type="hidden" id="dataId" name="dataId" value="">
												<input type="hidden" id="popupPageId" name="popupPageId" value="1">
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
                  									<tbody id='mcvopopup_add_table_body'></tbody>
                								</table>
                							</form>
              							</div>
            						</div>
									<div class="clearfix"></div>
          						</div>
       						</div>
							
							<button class="btnr btn-info color_popup_btn bg_color2" onclick="addmCVOPopupRow()" href="#">ADD</button>
							<span id="savemCVOPopdiv" style="display:inline;"><button class="btnr btn-info color_popup_btn bg_color2" name="mcvodata_save_data" id="mcvodata_save_data" onclick="mcvosaveData(this)">SAVE</button></span>
							<!-- <button name="mcvo_cancel_data" id="mcvo_cancel_data" class="btnr btn-info color_popup_btn bg_color2" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/transactions/sales/dom_refill_sales.jsp','5121')">BACK PAGE</button> -->

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
                  									<tbody id='mcvo_cvo_data_table_body'></tbody>
                								</table>
              								</div>
            							</div>
          							</div>
        						</div>
      						</div>
   						</div>
					</div>
					
					<!-- EQUIPMENT -->		

					<div class="modal_popup_newadd fade in" id="equipmentModal" style="display: none;height: 100%">
						<div class="modal-dialog modal-lg">
			  				<!-- Modal content-->
							<div class="modal-content" id="modal-content" style="top: -50px;font-size:10px;">
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
													<input type="hidden" id="page" name="page" value="jsp/pages/erp/transactions/sales/dom_refill_sales.jsp">
													<input type="hidden" id="actionId" name="actionId" value="9999">
													<input type="hidden" id="itemId" name="itemId" value="">
													<input type="hidden" id="popupPageId" name="popupPageId" value="1">
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
<!-- 								<button name="mequipopup_cancel_data" id="mequipopup_cancel_data"	class="btnr btn-info color_popup_btn bg_color2" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/transactions/sales/dom_refill_sales.jsp','5121')">BACK PAGE</button> -->
							
								<div class="row">
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
					  					<!-- 				<th width="30%" class="text-center sml_input">Actions</th> -->
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

					<!-- PRICE MASTER -->
                                    
					<div class="modal_popup_newadd fade in" id="refillPriceModal" style="display: none;height: 100%">
						<div class="modal-dialog modal-lg">
			  				<!-- Modal content-->
							<div class="modal-content" id="modal-content" style="top: -50px;font-size:10px;">
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
													<input type="hidden" id="page" name="page" value="jsp/pages/erp/transactions/sales/dom_refill_sales.jsp">
													<input type="hidden" id="actionId" name="actionId" value="9999">
													<input type="hidden" id="itemId" name="itemId" value="">
													<input type="hidden" id="popupPageId" name="popupPageId" value="1">
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
<!-- 								<button name="cancel_refillpricedata" id="cancel_refillpricedata"	class="btnr btn-info color_popup_btn bg_color2" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/transactions/sales/dom_refill_sales.jsp','5121')">BACK PAGE</button> -->
		
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
				   									<!--    <th width="20%" class="text-center sml_input">ACTIONS</th> -->
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
					
						<!-- security popup pin div -->
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
<!--                   							<button name="prof_submit_mRefilpricepopup_btn" id="prof_submit_mRefilpricepopup_btn" class="btn btn-success m-r-15" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/transactions/sales/dom_refill_sales.jsp','5121')">BACK</button> -->
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
 -->						<div class="modal-content" id="modal-content" style="top: -50px;font-size:10px;">
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
													<input type="hidden" id="page" name="page" value="jsp/pages/erp/transactions/sales/dom_refill_sales.jsp">
													<input type="hidden" id="actionId" name="actionId" value="9999">
													<input type="hidden" id="dataId" name="dataId" value="">
	                								<input type="hidden" id="popupPageId" name="popupPageId" value="1">
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
<!-- 								<button name="mbbankpopup_cancel_data" id="mbbankpopup_cancel_data" class="btnr btn-info color_popup_btn bg_color2" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/transactions/sales/dom_refill_sales.jsp','5121')">BACK PAGE</button> -->

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
                  									<tbody id="mpopup_bank_data_table_body"></tbody>
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
						<div class="modal-content" id="modal-content" style="top: -50px;font-size:10px;">
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
												<input type="hidden" id="page" name="page" value="jsp/pages/erp/transactions/sales/dom_refill_sales.jsp">
												<input type="hidden" id="actionId" name="actionId" value="9999">
												<input type="hidden" id="dataId" name="dataId" value="">
												<input type="hidden" id="popupPageId" name="popupPageId" value="1">
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
<!-- 							<button name="cancel_mstaffpopup_data" id="cancel_mstaffpopup_data"	class="btnr btn-info color_popup_btn bg_color2" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/transactions/sales/dom_refill_sales.jsp','5121')">BACK PAGE</button> -->
				
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
                  								<tbody id="staff_mStaffpopup_data_table_body"></tbody>
                							</table>
              							</div>
            						</div>
          						</div>
        					</div>
      					</div>
   					</div>
				</div>  
				
				<!-- AREA CODE -->		

				<div class="modal_popup_newadd fade in" id="areacodeModal" style="display: none;height: 100%">
					<div class="modal-dialog modal-lg">
			  			<!-- Modal content-->
						<div class="modal-content" id="modal-content" style="top: -50px;font-size:10px;">
                            <div class="modal-header" id="modal-header" style="padding:5px;">
					  			<span class="close" id="closeAreaCodepopup" onclick="closeAreaCodeFormDialog()">&times;</span>
					  			<h4 class="modal-title">AREA CODE MASTER</h4>
							</div>
			
							<div class="row">
          						<div class="clearfix"></div>
          						<div class="col-md-15" id="mareacode_page_add_table_div" style="display:none;">
            						<div class="main_table" style="margin-left: 37px;margin-right:37px;">
              							<div class="table-responsive">
											<form id="mareacode_page_data_form" name="" method="post" action="PPMasterDataControlServlet">
												<input type="hidden" id="agencyId" name="agencyId" value="<%= agencyVO.getAgency_code() %>">
												<input type="hidden" id="page" name="page" value="jsp/pages/erp/transactions/sales/dom_refill_sales.jsp">
												<input type="hidden" id="actionId" name="actionId" value="9999">
												<input type="hidden" id="itemId" name="itemId" value="">
												<input type="hidden" id="popupPageId" name="popupPageId" value="1">
						        				<input type="hidden" id="popupId" name="popupId" value="6">
						
												<table class="table" id="page_add_table">
                  									<thead>
                    									<tr class="title_head">
                    		  								<th width="10%" class="text-center">AREA CODE</th>
                    		  								<th width="10%" class="text-center">AREA NAME</th>
                   		 	  								<th width="10%" class="text-center">ONEWAY DISTANCE (KM)</th>
                   			  								<th width="10%" class="text-center">TRANSPORT CHARGES</th>
                   		   	  								<th width="10%" class="text-center">EFFECTIVE DATE</th>
                   		   	  								<th width="10%" class="text-center">ACTIONS</th>
                   		 								</tr>
                  									</thead>
                  									<tbody id="mareacode_page_add_table_body"></tbody>
                   								</table>
                							</form>
              							</div>
            						</div>
									<div class="clearfix"></div>			
          						</div>
        					</div>
							
							<button name="mareacode_add_data" id="mareacode_add_data"  class="btnr btn-info color_popup_btn bg_color2" onclick="addmAreaCodeRow()">ADD</button>
							<span id="mareacode_popupsavediv" style="display:inline;"><button class="btnr btn-info color_popup_btn bg_color2" name="mareacode_save_data" id="mareacode_save_data" onclick="savemAreaCodeData(this)">SAVE</button></span>
<!-- 							<button name="mareacode_cancel_data" id="mareacode_cancel_data"	class="btnr btn-info color_popup_btn bg_color2" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/transactions/sales/dom_refill_sales.jsp','5121')">BACK PAGE</button> -->
							<br><br>	
				
							<div class="row">
          						<div class="clearfix"></div>
          						<div class="col-md-15">
            						<div class="main_table" style="margin-left: 37px;margin-right:37px;">
              							<div class="table-mpopup_responsive">
                							<table class="table  table-striped" id="mareacode_popup_data_table">
                  								<thead>
                    								<tr class="title_head_mpopup">
                      									<th class="text-center sml_mpopupinput">AREA CODE</th>
                      									<th class="text-center sml_mpopupinput">AREA NAME</th>
                      									<th class="text-center sml_mpopupinput">ONEWAY DISTANCE (KM)</th>
                      									<th class="text-center sml_mpopupinput">TRANSPORT CHARGES</th>
                      									<th class="text-center sml_mpopupinput">EFFECTIVE DATE</th>
                      						<!-- 		<th class="text-center sml_mpopupinput">ACTIONS</th> -->
                    								</tr>
                  								</thead>
                  								<tbody id="mareacode_page_data_table_body"></tbody>
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
		<div id = "dialog-1" title="Alert(s)"></div>
 		<div id="dialog-confirm"><div id="myDialogText" style="color:black;"></div></div>
 		
 		</body>
		<script type="text/javascript">
			var checkDisplay = <%=request.getAttribute("checkDisplay") %>;
			if(!checkDisplay)
        		checkDisplay="0";
  			var pinNum = <%= agencyVO.getPinNumber() %>;
	    </script>
  		
  		<script type="text/javascript" src="js/modal.js?<%=randomUUIDString%>"></script>
  		<script type="text/javascript" src="js/commons/general_validations.js?<%=randomUUIDString%>"></script>
  		<script type="text/javascript" src="js/modules/transactions/sales/dom_refill_sales.js?<%=randomUUIDString%>"></script>
  		<script src="js/commons/bootstrap.min.js?<%=randomUUIDString%>"></script>
		<script src="js/commons/plugins/pace.min.js?<%=randomUUIDString%>"></script>
  		<script src="js/commons/main.js?<%=randomUUIDString%>"></script>
  		<script type="text/javascript" src="js/local.js?<%=randomUUIDString%>"></script>
        <script type="text/javascript" src="js/modules/multipopup/mcvo_data.js?<%=randomUUIDString%>"></script>
  		<script type="text/javascript" src="js/modules/multipopup/mequipment_data.js?<%=randomUUIDString%>"></script>
  		<script type="text/javascript" src="js/modules/multipopup/mrefill_prices_data.js?<%=randomUUIDString%>"></script>
  		<script type="text/javascript" src="js/modules/multipopup/mbank_data.js?<%=randomUUIDString%>"></script>
  		<script type="text/javascript" src="js/modules/multipopup/mstaff_data.js?<%=randomUUIDString%>"></script>
  		<script type="text/javascript" src="js/modules/multipopup/marea_code_data.js?<%=randomUUIDString%>"></script>
  		<script type="text/javascript">
 			document.getElementById("nameSpan").innerHTML = <%= agencyVO.getAgency_code() %>
 			var dedate = <%=agencyVO.getDayend_date()%>;
 	     	var effdate = <%=agencyVO.getEffective_date()%>;
 			var a_created_date = <%=agencyVO.getCreated_date()%>;
	    	var dealergstin =  "<%= agencyVO.getGstin_no() %>"; 			
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
 		    	
 		    	
 		    	$('#mareacode_popup_data_table').DataTable( {
 	   			    "lengthMenu": [[5,10, 25, 50, -1], [5,10, 25, 50, "All"]],
 	       	    	"bFilter": false,
 	       	    	"ordering": false,
 		    	    "scrollY":'50vh',
 				    "scrollCollapse": true,
 	/*        	        "scrollX": true,
 	 */	            language: {
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
 			
  	        /* $(document).ready(function() {
 	        	$('#m_data_table').DataTable( {
 	        		"bFilter": false,
 	        		"ordering": false,
 	        	    "scrollY": 230,
 	        	    "scrollX": true
 	        	} );
 	         } ); */
  	</script>
</html>