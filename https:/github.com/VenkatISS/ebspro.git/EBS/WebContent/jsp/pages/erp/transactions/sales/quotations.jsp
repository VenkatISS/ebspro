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
    	<title>MyLPGBooks DEALER WEB APPLICATION - QUOTATIONS</title>
    	<jsp:useBean id="agencyVO" scope="session" class="com.it.erpapp.framework.model.vos.AgencyVO"></jsp:useBean>
		<jsp:useBean id="cylinder_types_list" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="arb_types_list" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="qtn_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="cvo_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="staff_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="equipment_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="arb_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="refill_prices_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="cl_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="arb_prices_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="prod_types_list" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="dequipment_data" scope="request" class="java.lang.String"></jsp:useBean>
		<script type="text/javascript">
			var cat_cyl_types_data = <%= cylinder_types_list %>;
<%-- 			var cat_arb_types_data = <%= arb_types_list %>; --%>
			var cat_arb_types_data = <%= arb_data %>;
			var page_data = <%= qtn_data.length()>0? qtn_data : "[]" %>;
			var cvo_data = <%= cvo_data.length()>0? cvo_data : "[]" %>;
			var staff_data = <%= staff_data.length()>0? staff_data : "[]" %>;
			var equipment_data = <%= equipment_data.length()>0? equipment_data : "[]" %>;
			var arb_data = <%= arb_data.length()>0? arb_data : "[]" %>;
			var refill_prices_data = <%= refill_prices_data.length()>0? refill_prices_data : "[]" %>;
			var arb_prices_data = <%= arb_prices_data.length()>0? arb_prices_data : "[]" %>;
			var cust_cl_data = <%= cl_data.length()>0? cl_data: "[]" %>;
			var prod_types = <%= prod_types_list %>;
			var ctdatahtml = "";
			var vendordatahtml = "";
			var staffdatahtml = "";
			var months = ["JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"];
			
			
			//-------------------------mstart---------------------
            var cat_types_data = <%= cylinder_types_list %>;
            var del_equipment_data = <%= dequipment_data.length()>0? dequipment_data : "[]" %>;

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
 	     	
 var cat_types_data = <%= cylinder_types_list %>;
	var eus = ["NONE","NOS","KGS"];
	var gstyn = ["SELECT","YES","NO"];
	var years = ["2018","2019","2020"];
	var roles = ["DELIVERY STAFF","SHOWROOM STAFF","GODOWN STAFF","INSPECTOR","MECHANIC","OTHERS"];

	
//-------------------------mend---------------------
			//Construct Category Type html
			ctdatahtml = "<OPTION VALUE='0'>SELECT</OPTION>";
			ctdatahtml = ctdatahtml + "<OPTION VALUE='-1' disabled>---CYLINDER LIST---</OPTION>";
			if(cat_cyl_types_data.length>0) {
				for(var z=0; z<cat_cyl_types_data.length; z++){
					if(cat_cyl_types_data[z].cat_type==2) {
						ctdatahtml=ctdatahtml+"<OPTION VALUE='"+cat_cyl_types_data[z].id+"'>"
							+cat_cyl_types_data[z].cat_name+"-"+cat_cyl_types_data[z].cat_desc+"</OPTION>";
					}
				}
			}
			
			//Construct Customer Type html
			var custarr = new Array();
			var custdatahtml = "";
//			custdatahtml += "<OPTION DATA-VALUE='0'>SELECT</OPTION>";
			if(cvo_data.length>0) {
				for(var z=0; z<cvo_data.length; z++){
					if(cvo_data[z].cvo_cat==1 && cvo_data[z].deleted==0 && cvo_data[z].cvo_name != "UJWALA") {
						custdatahtml=custdatahtml+"<OPTION DATA-VALUE='"+cvo_data[z].id+"'>"+cvo_data[z].cvo_name+"</OPTION>";
						custarr.push(cvo_data[z].id);
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
          		<div><h1>QUOTATIONS</h1></div>
					<ul id="nav" class="nav nav-pills clearfix right" role="tablist">
          				<li class="dropdown"><span class="dropdown-toggle  btn-info" data-toggle="dropdown" id="ahelp">Help</span>
							<ul id="products-menu" class="dropdown-menu clearfix" role="menu">
								<li><a style="font-size: 14px;" href="https://www.youtube.com/watch?v=nZy54xDbV7g" target="_blank">English</a></li>
								<!-- <li><a style="font-size: 14px;" href="https://www.youtube.com/watch?v=nZy54xDbV7g" target="_blank">Hindi</a></li> -->
							</ul>
						</li>
					</ul>
        	</div>
        	<button name="b_sb" id="b_sb" class="btn btn-info color_btn bg_color4" onclick="showQuoFormDialog()">ADD QUOTATION</button>
			<button name="cancel_data" id="cancel_data"	class="btn btn-info color_btn bg_color3" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/home.jsp','2001')">BACK</button>
			<!-- Modal -->
			<div class="modal_popup_add fade in" id="myModal" style="display: none;height: 100%">					
		 		<div class="modal-dialog modal-lg">
			  		<!-- Modal content-->
			  			<div class="modal-content" id="modal-content">
							<div class="modal-header" id="modal-header">
					  			<span class="close" id="close" onclick="closeQuoFormDialog()">&times;</span>
					  		<!-- 	<h4 class="modal-title">QUOTATIONS</h4> -->
					  			
					  			<!-- -------------------------mstart--------------------- -->
   <b>	QUOTATIONS</b>	<button name="cvo_pop1" id="cvo_pop1" class=" btn-info color_popup_btn bg_color4 btnr" onclick="showCVOFormDialog()">CVO</button>
							<button name="equipment_pop1" id="equipment_pop1" class=" btn-info color_popup_btn bg_color4 btnr" onclick="showEquipmentFormDialog()">EQUIPMENT</button>
						    <button name="price_pop1" id="price_pop1" class=" btn-info color_popup_btn bg_color4 btnr" onclick="showPriceFormDialog()">PRICE</button>
						    <button name="staff_pop1" id="staff_pop1" class=" btn-info color_popup_btn bg_color4 btnr" onclick="showStaffFormDialog()">STAFF</button>
												
 	<!-- -------------------------mend--------------------- -->
					  			
							</div>
							<div class="modal-body">
								<form id="data_form" name="" method="post" action="TransactionsDataControlServlet">
									<input type="hidden" id="agencyId" name="agencyId" value="<%= agencyVO.getAgency_code() %>">
									<input type="hidden" id="page" name="page" value="jsp/pages/erp/transactions/sales/quotations.jsp">
									<input type="hidden" id="actionId" name="actionId" value="5142">
									<div class="row">
										<div class="col-adj-res">
											<label>QUOTATION DATE </label>
											<input type="date" id="qtn_date" name="qtn_date" class="form-control input_field tp" placeholder="DD-MM-YY">
										</div>
										<div class="col-adj-res">
											<label>CUSTOMER NAME</label>
<!-- 											<span id="cSpan"></span> -->

											<input list="cust_id" id="answerInput" onchange = "changeSaleTypeBasedOnCust()" class="form-control input_field" placeholder="SELECT">
											<datalist id="cust_id">
											<%String str1 = "<script>document.writeln(custdatahtml)</script>";
												out.println("value: " + str1);%>
											</datalist>
											<input type="hidden" name="cust_id" id="answerInputHidden">
											<input type="hidden" name="cust_name" id="cust_name">

										</div>
										<div class="col-adj-res">
											<label>STAFF NAME  </label>
											<span id="sSpan"></span>
										</div>
										<div class="col-adj-res">
											<label>FOOT NOTES  </label>
											<input type="text" class="form-control input_field" name='fn' id='fn' maxlength='200' size='8' placeholder="Foot Notes">
										</div>
										<div class="col-adj-res">
											<label>QUOTATION VALUE </label>
											<input type="text" class="form-control input_field" id="qtn_c_amt" name="qtn_c_amt" value="" readonly="readonly" placeholder="INV AMOUNT" style="background-color:#F3F3F3;">
										</div>				
										<div class="clearfix"></div>
									</div>
									<br>
									<div class="row">
										<div class="col-md-12">
											<div class="animated-radio-button">
													<label>SALE TYPE</label>&nbsp;&nbsp;
													<label>
						  								<input type="radio"  id="stype" name="stype"  value="ls"><span  id="ptype" class="label-text">LOCAL SALE</span>
													</label>&nbsp;&nbsp;
													<label>
						  								<input type="radio" id="stype" name="stype"  value="iss"><span  id="ptype" class="label-text">INTER-STATE SALE</span>
													</label>
											</div>
										</div>
									</div>
									<br>
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
					   											<th width="10%" class="text-center sml_input">DISCOUNT ON <br> UNIT PRICE</th>
					   											<th width="10%" class="text-center sml_input">QUANTITY</th>
					   											<th width="10%" class="text-center sml_input">TAXABLE AMOUNT</th>
					   											<th width="10%" class="text-center sml_input">IGST AMOUNT</th>					   											
					   											<th width="10%" class="text-center sml_input">CGST AMOUNT</th>
					   											<th width="10%" class="text-center sml_input">SGST AMOUNT</th>
					   											<th width="10%" class="text-center sml_input">TOTAL AMOUNT</th>
					   											<th width="10%" class="text-center sml_input">Actions</th>
                       										</tr>
                       									</thead>
                       									<tbody id="data_table_body">
                       										<tr>
                       											<td>
                       												<select class="form-control input_field select_dropdown sadd tp epid freez" name='epid' id='epid'>
						 												<%String str = "<script>document.writeln(ctdatahtml)</script>";
						   														out.println("value: " + str);%>
						   											</select>
					   											</td>
                       											<td>
                       												<input type="text" class="form-control input_field eadd" name='vp' id='vp' size='8' readonly='readonly' placeholder="Gst%" style='background-color:#FAFAC2;'>
                       											</td>
					   											<td>
					   												<input type="text" class="form-control input_field eadd" name='up' id='up' maxlength='7' size='8' readonly='readonly' placeholder="Unit Price" style='background-color:#FAFAC2;'>
					   											</td>
					   											<td>
					   												<input type="text" class="form-control input_field freez eadd" name='upd' id='upd' size='8' maxlength='7' size='8' placeholder=" Discount On Unit Price">
					   											</td>
					   											<td>
					   												<input type="text" class="form-control input_field qtyc freez eadd" name='qty' id='qty' size='8' maxlength='4' placeholder="Quantity">
					   											</td>
					   											<td>
					   												<input type="text" class="form-control input_field eadd" name='bp' id='bp' size='8' maxlength='8' size='8' value='0.00' readonly='readonly' placeholder="TAXABLE AMOUNT" style='background-color:#F3F3F3;'>
					   											</td>
					   											<td>
					   												<input type="text" class="form-control input_field eadd" name='igsta' id='igsta' size='8' readonly='readonly' placeholder="IGST" style='background-color:#F3F3F3;'>
					   											</td>
					   											<td>
					   												<input type="text" class="form-control input_field eadd" name='cgsta' id='cgsta' size='8' readonly='readonly' placeholder="CGST" style='background-color:#F3F3F3;'>
					   											</td>
					   											<td>
					   												<input type="text" class="form-control input_field eadd" name='sgsta' id='sgsta' size='8' readonly='readonly' placeholder="SGST" style='background-color:#F3F3F3;'>
					   											</td>
					   											<td>
					   												<input type="text" class="form-control input_field eadd" name='amt' id='amt' size='8' readonly='readonly' placeholder="Total Amount" style='background-color:#F3F3F3;'>
					   											</td>
                       											<td><img src='images/delete.png' onclick='doRowDeleteInTranxs(this,amt,qtn_c_amt,data_table_body)'></td>
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
											<button name="fetch_data" class="btn btn-info color_btn fetch" id="fetch_data"  onclick="fetchPriceAndVAT()">FETCH VALUES</button>
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
								<input type="hidden" id="page" name="page" value="jsp/pages/erp/transactions/sales/quotations.jsp">
								<input type="hidden" id="actionId" name="actionId" value="">
								<input type="hidden" id="dataId" name="dataId" value="">
                				<table class="table table-striped" id="m_data_table">
                  					<thead>
                    					<tr class="title_head">
					 						<th width="10%" class="text-center sml_input">QTN NUMBER</th>
                      						<th width="10%" class="text-center sml_input">QTN DATE</th>
                      						<th width="10%" class="text-center sml_input">CUSTOMER</th>
					  						<th width="10%" class="text-center sml_input">STAFF NAME</th>
					  						<th width="10%" class="text-center sml_input"> QTN AMT</th>
					   						<th width="10%" class="text-center sml_input">PRODUCT</th>
					   						<th width="10%" class="text-center sml_input">GST %</th>
					    					<th width="10%" class="text-center sml_input">UNIT PRICE</th>
											<th width="10%" class="text-center sml_input">DISCOUNT AMOUNT</th>
											<th width="10%" class="text-center sml_input">TAXABLE AMOUNT</th>
											<th width="10%" class="text-center sml_input">QUANTITY</th>
											<th width="10%" class="text-center sml_input">IGST</th>
											<th width="10%" class="text-center sml_input">CGST</th>											
											<th width="10%" class="text-center sml_input">SGST</th>
											<th width="10%" class="text-center sml_input">TOTAL AMOUNT</th>
											<th width="10%" class="text-center sml_input">FOOT NOTES</th>
						 					<th width="10%" class="text-center sml_input">Actions</th>
                    					</tr>
                  					</thead>
                  					<tbody id="m_data_table_body"></tbody>
                				</table>
                			</form>
              			</div>
        			</div>
        			
        					<!-- new popup 2202018 -->
			
			
				<div class="modal_popup_newadd fade in" id="cvoModal" style="display: none;height: 100%">
					<div class="modal-dialog modal-lg">
			  			<!-- Modal content-->
						<div class="modal-content" id="modal-content" style="top: -50px;">
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
							<input type="hidden" id="page" name="page" value="jsp/pages/erp/transactions/sales/quotations.jsp">
							<input type="hidden" id="actionId" name="actionId" value="9999">
							<input type="hidden" id="dataId" name="dataId" value="">
							<input type="hidden" id="popupPageId" name="popupPageId" value="13">
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
					
	<!-- EQUIPMENT -->		

	 <div class="modal_popup_newadd fade in" id="equipmentModal" style="display: none;height: 100%">
					<div class="modal-dialog modal-lg">
			  			<!-- Modal content-->
						<div class="modal-content" id="modal-content" style="top: -50px;">
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
									<input type="hidden" id="page" name="page" value="jsp/pages/erp/transactions/sales/quotations.jsp">
									<input type="hidden" id="actionId" name="actionId" value="9999">
									<input type="hidden" id="itemId" name="itemId" value="">
									<input type="hidden" id="popupPageId" name="popupPageId" value="13">
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
                  						<tbody id="mequipopup_page_add_table_body">
                   						</tbody>
                					</table>
                				</form>
              				</div>
            			</div>
						<div class="clearfix"></div>
          			</div>
        		</div>
				<button name="mequipopup_add_data" id="mequipopup_add_data"  class="btnr btn-info color_popup_btn bg_color2" onclick="addmEquipopupRow()">ADD</button>
				<span id="mequipopupsavediv" style="display:inline;"><button class="btnr btn-info color_popup_btn bg_color2" name="mequipopup_save_data" id="mequipopup_save_data" onclick="mequipoupsaveData(this)">SAVE</button></span>
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
					  					<!-- 	<th width="30%" class="text-center sml_input">Actions</th> -->
                    					</tr>
                  					</thead>
                  					<tbody id="mequipopup_page_data_table_body">                    
                  					</tbody>
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
						<div class="modal-content" id="modal-content" style="top: -50px;">
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
					<input type="hidden" id="page" name="page" value="jsp/pages/erp/transactions/sales/quotations.jsp">
					<input type="hidden" id="actionId" name="actionId" value="9999">
					<input type="hidden" id="itemId" name="itemId" value="">
					<input type="hidden" id="popupPageId" name="popupPageId" value="13">
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
                  		<tbody id="mrefillpricepopup_page_add_table_body">
                  		</tbody>
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
				   <!--     <th width="20%" class="text-center sml_input">ACTIONS</th> -->
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
									<input type="hidden" id="page" name="page" value="jsp/pages/erp/transactions/sales/quotations.jsp">
									<input type="hidden" id="actionId" name="actionId" value="9999">
									<input type="hidden" id="dataId" name="dataId" value="">
									<input type="hidden" id="popupPageId" name="popupPageId" value="13">
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
    		<div id = "dialog-1" title="Alert(s)"></div>
 			<div id="dialog-confirm"><div id="myDialogText" style="color:black;"></div></div>	
		</body>
		<script type="text/javascript" src="js/modal.js?<%=randomUUIDString%>"></script>
		<script type="text/javascript" src="js/commons/general_validations.js?<%=randomUUIDString%>"></script>
		<script type="text/javascript" src="js/modules/transactions/sales/quotations.js?<%=randomUUIDString%>"></script>
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
	    <script type="text/javascript" src="js/modules/multipopup/mcvo_data.js?<%=randomUUIDString%>"></script>
	    <script type="text/javascript" src="js/modules/multipopup/mequipment_data.js?<%=randomUUIDString%>"></script>
  		<script type="text/javascript" src="js/modules/multipopup/mrefill_prices_data.js?<%=randomUUIDString%>"></script>
  		<script type="text/javascript" src="js/modules/multipopup/mstaff_data.js?<%=randomUUIDString%>"></script>  		
  		<!--------   popup end 15052019  ---------------- --> 
		
		<script type="text/javascript">
 			document.getElementById("nameSpan").innerHTML = <%= agencyVO.getAgency_code() %>; 
 			var dedate = <%=agencyVO.getDayend_date()%>;
 	     	var effdate = <%=agencyVO.getEffective_date()%>;
 			var a_created_date = <%=agencyVO.getCreated_date()%>;
	    	var dealergstin =  "<%= agencyVO.getGstin_no() %>";
	    	
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
	//	   	    	"bPaginate" : $('#m_data_table tbody tr').length>5,
	//		   	    "iDisplayLength": 5,
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