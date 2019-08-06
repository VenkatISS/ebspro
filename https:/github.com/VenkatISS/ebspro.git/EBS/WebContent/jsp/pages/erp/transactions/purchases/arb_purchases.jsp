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
    	<title>BLPG/ARB/NFR PURCHASE INVOICES </title>
		<jsp:useBean id="agencyVO" scope="session" class="com.it.erpapp.framework.model.vos.AgencyVO"></jsp:useBean>
		
		<jsp:useBean id="arb_types_list" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="arb_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="arb_prices_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="arb_pi_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="cvo_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="pi_data" scope="request" class="java.lang.String"></jsp:useBean>

		<!-- Sidenav -->
		<jsp:include page="/jsp/pages/commons/sidenav.jsp"/>    
	 	<!---END Sidenav--->
		<!-- Header-->
		<jsp:include page="/jsp/pages/commons/header.jsp"/>      
	  	<!--header End--->

		<script type="text/javascript">
		    var product_cat_types_data = <%= arb_types_list%>;
			var cat_types_data = <%= arb_data %>;
			var arb_prices = <%= arb_prices_data %>;
			var page_data = <%= arb_pi_data.length()>0? arb_pi_data : "[]" %>;
			var pi_data = <%= pi_data.length()>0? pi_data : "[]" %>;
			var cvo_data = <%= cvo_data.length()>0? cvo_data : "[]" %>;
			var ctdatahtml = "";
			var vendordatahtml = "";
			var months = ["JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"];
			var rcs = ["SELECT","YES","NO"];

			
			//-------------------------mstart---------------------
			var arb_types_list = <%= arb_types_list %>;
			var arb_data = <%= arb_data.length()>0? arb_data : "[]" %>;
	    	var ctdatahtml, mvdatahtml, yvdatahtml = "";
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
			var eus = ["NONE","NOS","KGS","SET"];
			var years = ["2018","2019","2020"];
			var roles = ["DELIVERY STAFF","SHOWROOM STAFF","GODOWN STAFF","INSPECTOR","MECHANIC","OTHERS"];


//-------------------------mend---------------------

/* 			//Construct Category Type html
			ctdatahtml = "<OPTION VALUE='0'>SELECT</OPTION>";
			if(product_cat_types_data.length>0) {
				for(var z=0; z<product_cat_types_data.length; z++){
					for(var y=0; y<cat_types_data.length; y++){
						if(cat_types_data[y].prod_code == product_cat_types_data[z].id) {
							if(product_cat_types_data[z].id>12) {
								ctdatahtml=ctdatahtml+"<OPTION VALUE='"+cat_types_data[z].id+"'>"
								+getARBType(cat_types_data[z].prod_code)+" - "+cat_types_data[z].prod_brand+" - "+cat_types_data[z].prod_name
								+"</OPTION>";
								break;
							}
						}
					}
				}
			}
 */			
			
			//Construct Category Type html
			ctdatahtml = "<OPTION VALUE='0'>SELECT</OPTION>";
			if(product_cat_types_data.length>0) {
				for(var z=0; z<product_cat_types_data.length; z++) {
					if(product_cat_types_data[z].id>12) {
						for(var y=0; y<cat_types_data.length; y++){
							if((cat_types_data[y].deleted == 0) && (cat_types_data[y].prod_code == product_cat_types_data[z].id)) {
								ctdatahtml = ctdatahtml+"<OPTION VALUE='"+cat_types_data[y].id+"'>"
												+getARBType(cat_types_data[y].prod_code)+"-"+cat_types_data[y].prod_brand+"-"+cat_types_data[y].prod_name
												+"</OPTION>";
//								break;
							}
						}
					}
				}
			}
		</script>
<%-- 		<script src="js/commons/checkBrowser.js?<%=randomUUIDString%>"></script> --%>
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
      		<!-- HEADER-->
      		<!---HEADER END---->
      		<div class="content-wrapper">
        	<div class="page-title">
          		<div><h1>BLPG/ARB/NFR PURCHASE INVOICES </h1></div>
					<ul id="nav" class="nav nav-pills clearfix right" role="tablist">
          				<li class="dropdown"><span class="dropdown-toggle  btn-info" data-toggle="dropdown" id="ahelp">Help</span>
							<ul id="products-menu" class="dropdown-menu clearfix" role="menu">
								<li><a style="font-size: 14px;" title="arb-purchases" href="https://www.youtube.com/watch?v=b1FhsdzKv1E" target="_blank">English</a></li>
								<!-- <li><a style="font-size: 14px;" href="https://www.youtube.com/watch?v=b1FhsdzKv1E" target="_blank">Hindi</a></li> -->
							</ul>
						</li>
					</ul>
        	</div>
			<button name="b_sb" id="b_sb" class="btn btn-info color_btn bg_color4" onclick="showARBPFormDialog()">ADD</button>
			<button name="cancel_data" id="cancel_data" class="btn btn-info color_btn bg_color2" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/home.jsp','2001')">BACK</button>
			<!-- Modal -->
			<div class="modal_popup_add fade in" id="myModal" style="display: none;height: 100%">
				<div class="modal-dialog modal-lg">
			  		<!-- Modal content-->
					<div class="modal-content" id="modal-content">
						<div class="modal-header" id ="modal-header">
				  			<span class="close" id="close" onclick="closeARBPFormDialog()">&times;</span>
<!-- 				  			<h4 class="modal-title">BLPG/ARB/NFR PURCHASE INVOICES </h4> -->

<!-- -------------------------mstart--------------------- -->
              <b>BLPG/ARB/NFR PURCHASE INVOICES</b>	<button name="cvo_pop1" id="cvo_pop1" class=" btn-info color_popup_btn bg_color4 btnr" onclick="showCVOFormDialog()">CVO</button>
					                           		<button name="equipment_pop1" id="equipment_pop1" class=" btn-info color_popup_btn bg_color4 btnr" onclick="showARBFormDialog()">ARB</button>
         <!-------------- mend ------------------------------->
						</div>

						<div class="modal-body">
							<form id="data_form" name="" method="post" action="TransactionsDataControlServlet">
								<input type="hidden" id="agencyId" name="agencyId" value="<%= agencyVO.getAgency_code() %>">
								<input type="hidden" id="page" name="page" value="jsp/pages/erp/transactions/purchases/arb_purchases.jsp">
								<input type="hidden" id="actionId" name="actionId" value="5112">
								<div class="row">
									<div class="col-adj-res">
										<label>INV REF NO</label>
										<input type="text" class="form-control input_field"  id="inv_ref_no" name="inv_ref_no" maxlength="15" placeholder="INV REF NO">
									</div>
									<div class="col-adj-res">
										<label>INV DATE</label>
										<input type="date" class="form-control input_field freez" id="inv_date" name="inv_date" placeholder="DD-MM-YY">
									</div>
									<div class="col-adj-res">
										<label>STOCK RECEIPT DATE</label>
										<input type="date" class="form-control input_field blkcalc" id="s_r_date" name="s_r_date" placeholder="DD-MM-YY">
									</div>
									<div class="col-adj-res">
										<label>VENDOR NAME </label>
										<span id="vSpan" onchange="changeVendor()"></span>
									</div>
									<div class="col-adj-res">
										<label>INV AMOUNT</label>
										<input type="text" class="form-control input_field" id="inv_amt" name="inv_amt" value="" readonly="readonly" style="background-color:#F3F3F3;" placeholder="INV AMOUNT">
									</div>
									<div class="clearfix"></div>
								</div>	
								<br><br>
								<div class="row">
									<div class="col-md-12" style="width:480px;float:left;">
										<div class="animated-radio-button">
											<label>PURCHASE TYPE</label>&nbsp;&nbsp;
											<label>
						  						<input type="radio" id="ptype" name="ptype" value="lp"><span  id="ptype" class="label-text">LOCAL PURCHASE</span>
											</label>&nbsp;&nbsp;
											<label>
						  						<input type="radio" id="ptype" name="ptype" value="isp"><span id="ptype" class="label-text">INTER-STATE PURCHASE</span>
											</label>
										</div>
									</div>
									<div class="col-adj-res" style="margin-top:-20px;margin-right:20px;float:right;">
										<label>REVERSE CHARGE </label>
										<select id="rcyn" class="form-control input_field"  name="rcyn">
							    				<option value="0">SELECT</option>
							    				<option value="1">YES</option>
							    				<option value="2">NO</option>
							    		</select>
									</div>
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
															<th width="10%" class="text-center sml_input">GST%</th>
															<th width="10%" class="text-center sml_input">UNIT RATE</th>
															<th width="10%" class="text-center sml_input">QUANTITY</th>
					 										<th width="10%" class="text-center sml_input">OTHER CHARGES</th>
					 										<th width="10%" class="text-center sml_input">TAXABLE AMOUNT</th>
					 										<th width="10%" class="text-center sml_input">GST AMOUNT</th>
															<th width="10%" class="text-center sml_input">AMOUNT</th>
						 									<th width="10%" class="text-center sml_input">Actions</th>
                    									</tr>
                  									</thead>
                  									<tbody id="data_table_body">
                  										<tr>
                  											<td valign='top' height='4' align='center'>
                  												<select name='epid' id='epid' class='form-control input_field tp sadd' style='width:100px;'>
                  													<%String str3 = "<script>document.writeln(ctdatahtml)</script>";
				   														out.println("value: " + str3);%>
				   												</select>
				   											</td>
				   											<td valign='top' height='4' align='center'><input type=text name='vp' id='vp'  class='form-control input_field eadd' size='8' readonly='readonly' style='background-color:#FAFAC2;' placeholder='Gst%'></td>
				   											<td valign='top' height='4' align='center'><input type=text name='up' id='up'  class='form-control input_field freez eadd' size='8' maxlength='7'  placeholder='Unit Price'></td>
				   											<td valign='top' height='4' align='center'><input type=text name='qty' id='qty'  class='form-control input_field qtyc freez eadd'  size='8'  maxlength='4'  placeholder='Quantity'></td>
				   											<td valign='top' height='4' align='center'><input type=text name='oc' id='oc'class='form-control input_field freez eadd' size='8' value='0.00' placeholder='Other Charges'></td>
				   											<td valign='top' height='4' align='center'><input type=text name='bp' id='bp' class='form-control input_field eadd' size='8' readonly='readonly' style='background-color:#F3F3F3;'  placeholder='Taxable Amount' ></td>
				   											<td valign='top' height='4' align='center'><input type=text name='gsta' id='gsta'  class='form-control input_field eadd'  size='8' readonly='readonly' style='background-color:#F3F3F3;'  placeholder='Gst Amount'></td>
				   											<td valign='top' height='4' align='center'><input type=text name='amt' id='amt' class='form-control input_field eadd' size='8' readonly='readonly' style='background-color:#F3F3F3;'  placeholder='Amount'></td>
				   											<td valign='top' height='4' align='center'><img src='images/delete.png' onclick='doRowDeleteInTranxs(this,amt,inv_amt,data_table_body)'>
																<input type=hidden name='igsta' id='igsta'>
																<input type=hidden name='sgsta' id='sgsta'>
																<input type=hidden name='cgsta' id='cgsta'>
															</td>
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
									<button  name="add_data" id="add_data" class="btn btn-info color_btn" onclick="addRow()" >ADD</button>
								</div>
								<div class="col-md-2">
									<button  name="fetch_data" id="fetch_data"  class="btn btn-info color_btn" onclick="fetchGSTValues()">FETCH GST</button>
								</div>
								<div class="col-md-2">
									<button  name="calc_data" id="calc_data" class="btn btn-info color_btn" onclick="calculateValues()" disabled="disabled">CALCULATE</button>
								</div>
								<div class="col-md-2">
									<button  name="save_data" id="save_data" class="btn btn-info color_btn bg_color2" onclick="saveData(this)" disabled="disabled">SAVE</button>
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
						<input type="hidden" id="page" name="page" value="jsp/pages/erp/transactions/purchases/arb_purchases.jsp">
						<input type="hidden" id="actionId" name="actionId" value="">
						<input type="hidden" id="dataId" name="dataId" value="">
                
                		<table class="table  table-striped" id="m_data_table">
                  			<thead>
                    			<tr class="title_head">
									<th width="10%" class="text-center sml_input"> INV NO</th>
                      				<th width="10%" class="text-center sml_input">INVOICE DATE</th>
					  				<th width="10%" class="text-center sml_input">STOCK RECVD DATE</th>
					  				<th width="10%" class="text-center sml_input">PRODUCT</th>
					  				<th width="10%" class="text-center sml_input">VENDOR</th>
					  				<th width="10%" class="text-center sml_input">REVERSE CHARGE</th>
					  				<th width="10%" class="text-center sml_input">UNIT PRICE</th>
					  				<th width="10%" class="text-center sml_input">QUANTITY</th>
					  				<th width="10%" class="text-center sml_input">GST%</th>
					  				<th width="10%" class="text-center sml_input">TAXABLE AMOUNT</th>
					  				<th width="10%" class="text-center sml_input">IGST</th>
					  				<th width="10%" class="text-center sml_input">SGST</th>
					  				<th width="10%" class="text-center sml_input">CGST</th>
					  				<th width="10%" class="text-center sml_input">OTHER CHARGES	</th>
							  		<th width="10%" class="text-center sml_input">TOTAL AMOUNT	</th>
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
			  			<!-- Modal content-->
<!-- 			  			<div class="modal-mulpopup_content" id="modal-content" style="top: -50px;">
 -->						<div class="modal-content" id="modal-content" style="top: -50px;font-size:10px;">
                            	<div class="modal-header" id="modal-header" style="padding:5px;">
					  			<span class="close" id="closeCVOpopup" onclick="closeCVOFormDialog()">&times;</span>
					  			<h4 class="modal-title">CUSTOMER / VENDOR MASTER </h4>
							</div>
							<!--  <button name="b_sb" id="b_sb" class="btn btn-info color_btn bg_color4" onclick="addCVOSalesFormDialog()">ADD</button>
				            <button name="cancel_data" id="cancel_data"	class="btn btn-info color_btn bg_color3" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/home.jsp','2001')">BACK</button> 
				 -->
			
			
			
			 <div class="row">
        	<div class="clearfix"></div>
          	<div class="col-md-15" id="mymCVOPopupDIV" style="display:none;">
            	<div class="main_table"  style="margin-left: 37px;margin-right:37px;">
              		<div class="table-responsive">
             	 		<form id="mcvopopup_data_form" name="" method="post" action="MasterDataControlServlet">
			  				<input type="hidden" id="agencyId" name="agencyId" value="<%= agencyVO.getAgency_code() %>">
							<input type="hidden" id="page" name="page" value="jsp/pages/erp/transactions/purchases/arb_purchases.jsp">
							<input type="hidden" id="actionId" name="actionId" value="9999">
							<input type="hidden" id="dataId" name="dataId" value="">
							<input type="hidden" id="popupPageId" name="popupPageId" value="5">
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
<!-- 		<button name="mcvo_cancel_data" id="mcvo_cancel_data"	class="btnr btn-info color_popup_btn bg_color2" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/transactions/purchases/arb_purchases.jsp','5111')">BACK PAGE</button> -->
		 	
				
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
					
			<!-- ARB DATA -->		

	 <div class="modal_popup_newadd fade in" id="arbModal" style="display: none;height: 100%">
					<div class="modal-dialog modal-lg">

			  			<!-- Modal content-->
						<div class="modal-content" id="modal-content" style="top: -50px;font-size:10px;">
                            	<div class="modal-header" id="modal-header" style="padding:5px;">
					  			<span class="close" id="closeARBpopup" onclick="closeARBFormDialog()">&times;</span>
					  			<h4 class="modal-title">BLPG/ARB/NFR PRODUCTS MASTER</h4>
							</div>
			
           		<div class="row">
          			<div class="clearfix"></div>
          			<div class="col-md-15" id="marb_page_add_table_div" style="display:none;">
            			<div class="main_table" style="margin-left: 37px;margin-right:37px;">
              				<div class="table-responsive">
              					<form id="marb_page_add_form" name="" method="post" action="MasterDataControlServlet">
									<input type="hidden" id="agencyId" name="agencyId" value="<%= agencyVO.getAgency_code() %>">
									<input type="hidden" id="page" name="page" value="jsp/pages/erp/transactions/purchases/arb_purchases.jsp">
									<input type="hidden" id="actionId" name="actionId" value="9999">
									<input type="hidden" id="itemId" name="itemId" value="">
									<input type="hidden" id="popupPageId" name="popupPageId" value="5">
						        	<input type="hidden" id="popupId" name="popupId" value="7">
						
                					<table class="table" id="marb_page_add_table">
                  						<thead>
                    						<tr class="title_head">
                      							<th width="20%" class="text-center sml_input">PRODUCT CATEGORY</th>
                      							<th width="10%" class="text-center sml_input">Brand</th>
                      							<th width="10%" class="text-center sml_input">PRODUCT NAME</th>
                      							<th width="10%" class="text-center sml_input">HSN CODE</th>
                      							<th width="10%" class="text-center sml_input">UNITS</th>
                      							<th width="10%" class="text-center sml_input">GST %</th>
					  							<th width="10%" class="text-center sml_input">PURCHASE PRICE</th>
					  							<th width="10%" class="text-center sml_input">OPENING STOCK</th>
					  							<th width="10%" class="text-center sml_input">EFFECTIVE DATE</th>
					  							<th width="5%" class="text-center sml_input">Actions</th>
                    						</tr>
                  						</thead>
                  						<tbody id="marb_page_add_table_body">
                  						</tbody>
                					</table>
                				</form>
             			 	</div>
           	 			</div>
						<div class="clearfix"></div>
		 			</div>
        		</div>
				<button name="marb_add_data" id="marb_add_data"  class="btnr btn-info color_popup_btn bg_color2" onclick="mARBaddRow()">ADD</button>
				<span id="marbSaveData" style="display:inline;"><button class="btnr btn-info color_popup_btn bg_color2" name="marb_save_data" id="marb_save_data" onclick="mARBsaveData(this)">SAVE</button></span>
<!-- 				<button name="marb_cancel_data" id="marb_cancel_data"	class="btnr btn-info color_popup_btn bg_color2" onclick="doAction('TransactionsDataControlServlet','jsp/pages/erp/transactions/purchases/arb_purchases.jsp','5111')">BACK PAGE</button> -->
				<div class="row">
          			<div class="clearfix"></div>
          			<div class="col-md-15">
            			<div class="main_table" style="margin-left: 37px;margin-right:37px;">
              				<div class="table-mpopup_responsive">
                				<table class="table  table-striped" id="marb-page_data_table">
                  					<thead>
                    					<tr class="title_head_mpopup">
                      						<th width="30%" class="text-center sml_equippopup_input">PRODUCT CATEGORY</th>
                      						<th width="30%" class="text-center sml_equippopup_input">Brand</th>
                      						<th width="30%" class="text-center sml_equippopup_input">PRODUCT NAME</th>
                      						<th width="30%" class="text-center sml_equippopup_input">HSN CODE</th>
                      						<th width="20%" class="text-center sml_equippopup_input">UNITS</th>
                      						<th width="20%" class="text-center sml_equippopup_input">GST %</th>
					   						<th width="30%" class="text-center sml_equippopup_input">PURCHASE PRICE</th>
					    					<th width="30%" class="text-center sml_equippopup_input">OPENING STOCK</th>
											<th width="30%" class="text-center sml_equippopup_input">EFFECTIVE DATE</th>
						 					<!-- <th width="10%" class="text-center sml_input" >ACTIONS</th> -->
                    					</tr>
                  					</thead>
                  					<tbody id="marb_page_data_table_body">
                  					</tbody>
                				</table>
              				</div>
            			</div>
          			</div>
        		</div>
			</div>
		</div>
	</div>		
	
		<!--------------------- 	New popup end --------------------------------------------->
        <div class="clearfix"></div>
       </div>
	</div>
	<div id = "dialog-1" title="Alert(s)"></div>
 	<div id="dialog-confirm"><div id="myDialogText" style="color:black;"></div></div>
</body>

<script src="js/commons/bootstrap.min.js?<%=randomUUIDString%>"></script>
<script src="js/commons/plugins/pace.min.js?<%=randomUUIDString%>"></script>    
<script src="js/commons/main.js?<%=randomUUIDString%>"></script>
<script type="text/javascript" src="js/modal.js?<%=randomUUIDString%>"></script>
<script type="text/javascript" src="js/commons/general_validations.js?<%=randomUUIDString%>"></script>
<script type="text/javascript" src="js/modules/transactions/purchases/arbPurchases.js?<%=randomUUIDString%>"></script>
<script type="text/javascript" src="js/modules/multipopup/mcvo_data.js?<%=randomUUIDString%>"></script>
<script type="text/javascript" src="js/modules/multipopup/marb_data.js?<%=randomUUIDString%>"></script>
<script type="text/javascript">
	document.getElementById("nameSpan").innerHTML = <%= agencyVO.getAgency_code() %>;
	var dedate = <%=agencyVO.getDayend_date()%>;
 	var effdate = <%=agencyVO.getEffective_date()%>;
	var a_created_date = <%=agencyVO.getCreated_date()%>;
	var dealergstin =  "<%= agencyVO.getGstin_no() %>";
	document.getElementById("vSpan").innerHTML = "<select id='vid' name='vid' class='form-control input_field'>"+vendordatahtml+"</select>";
	$(document).ready(function() {
		$('#m_data_table').DataTable({
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
	     });
		
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
    	
	      	 
	     	$('#marb-page_data_table').DataTable( {
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
 	</script>
	<script>
		//Make the DIV element draggagle:
		dragElement(document.getElementById("modal-content"));
	</script>
</html>
