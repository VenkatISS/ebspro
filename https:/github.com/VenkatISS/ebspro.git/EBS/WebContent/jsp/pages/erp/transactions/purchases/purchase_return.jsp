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
    	<title>DEFECTIVE/PURCHASE RETURN DATA </title>
    	
		<!-- Sidenav -->
		<jsp:include page="/jsp/pages/commons/sidenav.jsp"/>
	 	<!---END Sidenav--->

		<!-- Header-->
		<jsp:include page="/jsp/pages/commons/header.jsp"/>      
	  	<!--header End--->
		
		<jsp:useBean id="agencyVO" scope="session" class="com.it.erpapp.framework.model.vos.AgencyVO"></jsp:useBean>
		<jsp:useBean id="bank_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="cvo_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="cylinder_types_list" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="prods_list" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="arb_types_list" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="arb_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="refill_prices_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="pr_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="pi_data" scope="request" class="java.lang.String"></jsp:useBean>		
		<jsp:useBean id="equipment_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="arb_pi_data" scope="request" class="java.lang.String"></jsp:useBean> 
		
		<script type="text/javascript" src="js/modal.js?<%=randomUUIDString%>"></script>
		<script type="text/javascript" src="js/commons/general_validations.js?<%=randomUUIDString%>"></script>
		<script type="text/javascript">
			var cat_cyl_types_data = <%= cylinder_types_list.length()>0? cylinder_types_list : "[]" %>;
			var equipment_data = <%= equipment_data.length()>0? equipment_data : "[]" %>;
			var page_data = <%= pr_data.length()>0? pr_data : "[]" %>;
	 		var purchases_data = <%= pi_data.length()>0? pi_data : "[]" %>;
	 		var arbPurchaseData=<%=arb_pi_data.length()>0? arb_pi_data : "[]" %>;
			var bank_data =  <%= bank_data.length()>0? bank_data : "[]" %>;
			var cvo_data = <%= cvo_data.length()>0? cvo_data : "[]" %>;
			var cat_arb_types_data = <%= arb_data.length()>0? arb_data : "[]" %>;
			var cat_types_data = <%= cylinder_types_list %>;
			var ctdatahtml = "";
			var bankdatahtml = "";
			var customerdatahtml = "";
			var mops = ["NONE","CASH","CHEQUE","ONLINE TRANSFER"];
			var months = ["JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"];
			var refill_prices_data = <%= refill_prices_data.length()>0? refill_prices_data : "[]" %>;
			
			//Construct Category Type html
			ctdatahtml = "<OPTION VALUE='0'>SELECT</OPTION>";
			ctdatahtml = ctdatahtml + "<OPTION VALUE='-1' disabled>---CYLINDER LIST---</OPTION>";
			if(cat_cyl_types_data.length>0) {
				for(var z=0; z<cat_cyl_types_data.length; z++){
					var catType=cat_cyl_types_data[z].cat_type;
					//if(!(cat_cyl_types_data[z].cat_type)==3)
					if(catType!==3) {
						for(var y=0; y<equipment_data.length; y++){
							if(equipment_data[y].deleted == 0){	
								if(equipment_data[y].prod_code == cat_cyl_types_data[z].id) {
									ctdatahtml=ctdatahtml+"<OPTION VALUE='"+cat_cyl_types_data[z].id+"'>"
										+cat_cyl_types_data[z].cat_name+"-"+cat_cyl_types_data[z].cat_desc+"</OPTION>";
									break;
								}
							}
						}
					}
				}
			}

			ctdatahtml = ctdatahtml + "<OPTION VALUE='-2' disabled>---ARB LIST---</OPTION>";
			if(cat_arb_types_data.length>0) {
				for(var z=0; z<cat_arb_types_data.length; z++){
					if(cat_arb_types_data[z].deleted == 0)
					ctdatahtml=ctdatahtml+"<OPTION VALUE='"+cat_arb_types_data[z].id+"'>"
					+getARBType(cat_arb_types_data[z].prod_code)+" - "+cat_arb_types_data[z].prod_brand+" - "+cat_arb_types_data[z].prod_name
					+"</OPTION>";
				}
			}

			//Construct Customer Type html	
		    var staffdatahtml ="";
			var payment_terms_values = ["NONE","CASH","CREDIT"];
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
			var payment_terms_types = ["NONE","CASH","CREDIT","ONLINE TRANSFER"];
			var rcs = ["SELECT","YES","NO"];

			
			/* //Construct Staff html
			staffdatahtml = "<OPTION VALUE='0'>SELECT</OPTION>";
			if(staff_data.length>0) {
				for(var z=0; z<staff_data.length; z++){
						staffdatahtml=staffdatahtml+"<OPTION VALUE='"+staff_data[z].id+"'>"+staff_data[z].emp_name+"</OPTION>";
					
				}
			} */
		
	/* 		//Area codes html
			areacodeshtml = "<OPTION VALUE='0'>SELECT</OPTION>";
			if(area_codes_data.length>0) {
				for(var z=0; z<area_codes_data.length; z++){
						areacodeshtml=areacodeshtml+"<OPTION VALUE='"+area_codes_data[z].id+"'>"+area_codes_data[z].area_code+" - "+area_codes_data[z].area_name+"</OPTION>";
				}
			} */
			
			custdatahtml = "<OPTION VALUE='-1'>SELECT</OPTION>";
			if(cvo_data.length>0) {
				for(var z=0; z<cvo_data.length; z++){
					if(cvo_data[z].deleted == 0){
						if(cvo_data[z].cvo_cat != 1 && cvo_data[z].cvo_cat!=3 ) {
							if(cvo_data[z].cvo_cat == 2)
								custdatahtml=custdatahtml+"<OPTION VALUE='"+cvo_data[z].id+"'>"+omc_name+"-"+cvo_data[z].cvo_name+"</OPTION>";
							else
								custdatahtml=custdatahtml+"<OPTION VALUE='"+cvo_data[z].id+"'>"+cvo_data[z].cvo_name+"</OPTION>";
						}
					}
				}
			}

			//Construct bank html
			bankdatahtml = "<OPTION VALUE='0'>SELECT</OPTION>";
			if(bank_data.length>0) {
				for(var z=0; z<bank_data.length; z++){
					if((bank_data[z].bank_code).startsWith("TAR")) {
						bankdatahtml=bankdatahtml+"<OPTION VALUE='"+bank_data[z].id+"'>"+bank_data[z].bank_code+" - "+bank_data[z].bank_acc_no+"</OPTION>";
					}
				}
			}
    	</script>
<%-- 		<script src="js/commons/checkBrowser.js?<%=randomUUIDString%>"></script> --%>
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
      		<!-- HEADER-->
      		<!---HEADER END---->
      		<div class="content-wrapper">
        		<div class="page-title">
          			<div><h1>DEFECTIVE/PURCHASE RETURN DATA   </h1></div>
					<ul id="nav" class="nav nav-pills clearfix right" role="tablist">
          				<li class="dropdown"><span class="dropdown-toggle  btn-info" data-toggle="dropdown" id="ahelp">Help</span>
							<ul id="products-menu" class="dropdown-menu clearfix" role="menu">
								<li><a style="font-size: 14px;" href="https://www.youtube.com/watch?v=gERufpqwGFg" target="_blank">English</a></li>
								<!-- <li><a style="font-size: 14px;" href="https://www.youtube.com/watch?v=gERufpqwGFg" target="_blank">Hindi</a></li> -->
							</ul>
						</li>
					</ul>
        		</div>
				<button name="b_sb" id="b_sb" class="btn btn-info color_btn bg_color4" onclick="showPRFormDialog()">ADD PURCHASE RETURN</button>
				<button class="btn btn-info color_btn bg_color3" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/home.jsp','2001')">BACK</button>	
				<!-- Modal -->
				<div class="modal_popup_add fade in" id="myModal" style="display: none;height: 100%">
					<div class="modal-dialog modal-lg">
			  			<!-- Modal content-->
			  			<div class="modal-content" id="modal-content">
							<div class="modal-header" id ="modal-header">
					  			<span class="close" id="close" onclick="closePRFormDialog()">&times;</span>
<!-- 					  			<h4 class="modal-title">DEFECTIVE/PURCHASE RETURN DATA   </h4> -->
<!-- -------------------------mstart--------------------- -->
   <b>DEFECTIVE/PURCHASE RETURN DATA</b><button name="cp_pop1" id="cp_pop1" class=" btn-info color_popup_btn bg_color4 btnr" onclick="showCPFormDialog()">CP</button>
							<button name="arbp_pop1" id="arbp_pop1" class=" btn-info color_popup_btn bg_color4 btnr" onclick="showARBPFormDialog()">ARBP</button>

<!-- -------------------------mend--------------------- -->
							</div>
							<div class="modal-body">
								<form id="data_form" name="" method="post" action="TransactionsDataControlServlet">
									<input type="hidden" id="agencyId" name="agencyId" value="<%= agencyVO.getAgency_code() %>">
									<input type="hidden" id="page" name="page" value="jsp/pages/erp/transactions/purchases/purchase_return.jsp">
									<input type="hidden" id="actionId" name="actionId" value="5572">
									<input type="hidden" id="cvo_cat" name="cvo_cat" value=""/>
									<div class="row">
										<div class="col-adj-res-sub">
											<label>PURCHASE INVOICE </label>
											<input type="text" class="form-control input_field freez" id="pinvno" name="pinvno" placeholder="PURCHASE INVOICE">
										</div>
										<div class="col-adj-res-sub">
											<label>INVOICE DATE </label>
											<input type="date" class="form-control input_field" id="invdate" name="invdate">
											<input type="hidden" id="refdate" name="refdate">
										</div>
										<div class="col-adj-res-sub">
											<label>VENDOR NAME </label>
											<select class="form-control input_field select_dropdown" id="cvo_id" name="cvo_id" onchange="selectPurchaseType()">
												<%  String vndr="<script>document.writeln(custdatahtml)</script>";
								    					out.println("value: "+vndr);%>
											</select>
										</div>
										<div class="col-adj-res-sub">
											<label>TOTAL AMOUNT  </label>
											<input type="text" class="form-control input_field" id="pr_t_amt" name="pr_t_amt" value="" readonly="readonly"  placeholder="INVOICE AMOUNT">
										</div>
										<br><br><br><br><br>
										<div class="row">
										<div class="col-md-12" style="width:535px;margin-left:20px;">
											<div class="animated-radio-button">
													<label>PURCHASE TYPE</label>&nbsp;&nbsp;
													<label>
						  								<input type="radio"  id="ptype" name="ptype"  value="lp"><span  id="ptype" class="label-text">LOCAL PURCHASE</span>
													</label>&nbsp;&nbsp;
													<label>
						  								<input type="radio" id="ptype" name="ptype"  value="isp"><span  id="ptype" class="label-text">INTER-STATE PURCHASE</span>
													</label>
											</div>	
										</div>
										<div class="col-adj-res-sub" style="margin-top:-20px;margin-right:30px;float:right;">
											<br><label>NARRATION </label>
											<input type="text" class="form-control input_field" id="nar" name="nar" placeholder="NARRATION">
										</div>
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
					  											<th width="10%" class="text-center sml_input">UNIT PRICE</th>
					  											<th width="10%" class="text-center sml_input">GST %</th>
					  											<th width="10%" class="text-center sml_input">RETURN QUANTITY</th>
					  											<th width="10%" class="text-center sml_input">NET WEIGHT(Kgs)</th>
					  											<th width="10%" class="text-center sml_input">TAXABLE AMOUNT</th>
					  											<th width="10%" class="text-center sml_input">IGST AMOUNT</th>
					  											<th width="10%" class="text-center sml_input">CGST AMOUNT</th>
					  											<th width="10%" class="text-center sml_input">SGST AMOUNT</th>
					  											<th width="10%" class="text-center sml_input">AMOUNT</th>					  											
					  											<th width="10%" class="text-center sml_input">VEHICLE NUMBER</th>
						 										<th width="10%" class="text-center sml_input">ACTIONS</th>
						  									</tr>
				                  						</thead>
                  				  						<tbody id="data_table_body">
                  				  							<tr>
                      											<td>
                      												<select class="form-control input_field select_dropdown sadd tp" name="pid" id="pid" onchange="changeNetWeight()">
						  												<%  String prod="<script>document.writeln(ctdatahtml)</script>";
																				out.println("value: "+prod);%>
																	</select>
																</td>
					   											<td><input type="text" class="form-control input_field eadd" name="ur" id="ur" placeholder="UNIT PRICE" readonly="readonly"></td>
						 										<td><input type="text" class="form-control input_field eadd" name="gstp" id="gstp" placeholder="GST %" readonly="readonly"></td>					   											
                      											<td>
                      												<input type="text" name="rqty" id="rqty" class="form-control input_field qtyc freez eadd" maxlength="4" placeholder="RETURN QUANTITY">
                      												<input type="hidden" name="cpqty" id="cpqty" value=""><input type="hidden" name="apqty" id="apqty" value="">
                      											</td>			
																<td><input type="text" class="form-control input_field freez eadd" name="nw" id="nw" maxlength="8" placeholder="NET WEIGHT"></td>
						 										<td><input type="text" class="form-control input_field eadd" name="namt" id="ramt" placeholder="TAXABLE AMOUNT" readonly="readonly"></td>
						 										<td><input type="text" class="form-control input_field eadd" name="igsta" id="igst" placeholder="IGST AMOUNT" readonly="readonly"></td>
						 										<td><input type="text" class="form-control input_field eadd" name="cgsta" id="cgst" placeholder="CGST AMOUNT" readonly="readonly"></td>
						 										<td><input type="text" class="form-control input_field eadd" name="sgsta" id="sgst" placeholder="SGST AMOUNT" readonly="readonly"></td>
						 										<td><input type="text" class="form-control input_field eadd" name="tamt" id="tamt" placeholder="TOTAL AMOUNT" readonly="readonly"></td>
						 										<td><input type="text" class="form-control input_field eadd" name="vno" id="vno" maxlength="12" placeholder="VEHICLE NUMBER"></td>
   					                   							<td><img src="images/delete.png" onclick="doRowDeleteInTranxs(this,ramt,pr_t_amt,data_table_body)"></td>
                    										</tr>
                  				  						</tbody>
                									</table>
              									</div>
           				 					</div>
											<div class="clearfix"></div>
											<br>			
          								</div>
        							</div>
        						</form>
        						<div class="row">
        							<div class="col-md-2">
										<button name="add_data" id="add_data" class="btn btn-info color_btn" onclick="addRow()">ADD</button>
									</div>
 									<div class="col-md-2">
										<button  name="fetch_data" id="fetch_data" class="btn btn-info color_btn" onclick="fetchValues()" style="padding-left: 10px;">FETCH VALUES</button>
									</div>
 									<div class="col-md-2">
										<button name="calc_data" id="calc_data" class="btn btn-info color_btn" onclick="calculateValues()">CALCULATE</button>
									</div>
									<div class="col-md-2">
										<button name="save_data" id="save_data" class="btn btn-info color_btn bg_color2" onclick="saveData(this)" disabled="disabled">SAVE</button>
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
								<input type="hidden" id="page" name="page" value="jsp/pages/erp/transactions/purchases/purchase_return.jsp">
								<input type="hidden" id="actionId" name="actionId" value="">
								<input type="hidden" id="dataId" name="dataId" value="">
                				<table class="table table-striped" id="m_data_table">
									<thead>
										<tr class="title_head">
											<th width="10%" class="text-center sml_input">PURCHASE INVOICE</th>
											<th width="10%" class="text-center sml_input">PURCHASE REFERENCE NUMBER</th>
											<th width="10%" class="text-center sml_input"> RETURN INVOICE DATE</th>
											<th width="10%" class="text-center sml_input">VENDOR</th>
											<th width="10%" class="text-center sml_input">TOTAL AMOUNT</th>
											<th width="10%" class="text-center sml_input">NARRATION</th>
											<th width="10%" class="text-center sml_input">PRODUCT</th>
											<th width="10%" class="text-center sml_input">GST %</th>
											<th width="10%" class="text-center sml_input">RETURN QUANTITY</th>
											<th width="10%" class="text-center sml_input">NET WEIGHT(Kgs)</th>
											<th width="10%" class="text-center sml_input">UNIT RATE</th>
											<th width="10%" class="text-center sml_input">TAXABLE AMOUNT</th>
											<th width="10%" class="text-center sml_input">IGST AMOUNT</th>
											<th width="10%" class="text-center sml_input">CGST AMOUNT</th>
											<th width="10%" class="text-center sml_input">SGST AMOUNT</th>
											<th width="10%" class="text-center sml_input">AMOUNT</th>
											<th width="10%" class="text-center sml_input">VEHICLE NUMBER</th>
											<th width="10%" class="text-center sml_input">ACTIONS</th>
										</tr>			
										
									</thead>
									<tbody id="m_data_table_body"></tbody>
           						</table>
           					</form>	
              			</div>
        			</div>
        			
        			<!-- new popup 2202018 -->
			
			
				<!-- Modal -->
				<div class="modal_popup_newadd fade in" id="cppopupModal" style="display: none;height: 100%">
					<div class="modal-dialog modal-lg">
			  			<!-- Modal content-->
						<div class="modal-content" id="modal-content" style="top: -50px;font-size:10px;">
                            	<div class="modal-header" id="modal-header" style="padding:5px;">
					  			<span class="close" id="closecppopup" onclick="closeCPFormDialog()">&times;</span>
					  			<h4 class="modal-title">CYLINDERS PURCHASE INVOICE ENTRY </h4>
							</div>
				
	<div class="row">
        	<div class="clearfix"></div>
          	<div class="col-md-15">
	            <div class="main_table"  style="margin-left: 37px;margin-right:37px;">
              		<div class="table-mpopup_responsive">
                		<table class="table table-striped" id="cp_data_table">
                  					<thead>
                    					<tr class="title_head">
										<th width="10%" class="text-center sml_input">OMC</th>
										<th width="10%" class="text-center sml_input">INV NO</th>
               							<th width="10%" class="text-center sml_input">INVOICE DATE</th>
			  							<th width="10%" class="text-center sml_input">STOCK RECVD DATE</th>
			  							<th width="10%" class="text-center sml_input">PRODUCT</th>
			  							<th width="10%" class="text-center sml_input">ONE WAY/TWO WAY LOAD</th>
			  							<th width="10%" class="text-center sml_input">UNIT PRICE</th>
			  							<th width="10%" class="text-center sml_input">QUANTITY</th>
			  							<th width="10%" class="text-center sml_input">GST%</th>
			  							<th width="10%" class="text-center sml_input">TAXABLE AMOUNT</th>
			  							<th width="10%" class="text-center sml_input">IGST</th>
			  							<th width="10%" class="text-center sml_input">SGST</th>
			  							<th width="10%" class="text-center sml_input">CGST</th>
			  							<th width="10%" class="text-center sml_input">OTHER CHARGES	</th>
					  					<th width="10%" class="text-center sml_input">TOTAL AMOUNT	</th>
				 		
                   						</tr>
                  					</thead>
                  					<tbody id="cp_data_table_body"></tbody>
                				</table>
              		</div>
            	</div>
          	</div>
        </div>
      </div>
    </div>
</div>
					
	<!-- ARB Purchase -->		

	 <div class="modal_popup_newadd fade in" id="arbppopupModal" style="display: none;height: 100%">
					<div class="modal-dialog modal-lg">
			  			<!-- Modal content-->
<!-- 			  			<div class="modal-mulpopup_content" id="modal-content" style="top: -50px;">
 -->						<div class="modal-content" id="modal-content" style="top: -50px;font-size:10px;">
                            	<div class="modal-header" id="modal-header" style="padding:5px;">
					  			<span class="close" id="closearbppopup" onclick="closeARBPFormDialog()">&times;</span>
					  			<h4 class="modal-title">BLPG/ARB/NFR PURCHASE INVOICES</h4>
							</div>
			
           		<div class="row">
          			<div class="clearfix"></div>
          			<div class="col-md-15">
            			<div class="main_table" style="margin-left: 37px;margin-right:37px;">
              				<div class="table-mpopup_responsive">
                				<table class="table  table-striped" id="arbp_popup_data_table">
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
									</tr>
                    					
                  					</thead>
                  					<tbody id="arbp_data_table_body">                    
                  					</tbody>
                				</table>
              				</div>
           				 </div>
          			</div>
        		</div>
			</div>
		</div>
	</div>		


        
        <!---------------------------------- new popup end ---------------------------------------------------->
        			
      			</div>
    		</div>
    		<div id = "dialog-1" title="Alert(s)"></div>
 			<div id="dialog-confirm"><div id="myDialogText" style="color:black;"></div></div>
    	</body>
		<script src="js/commons/bootstrap.min.js?<%=randomUUIDString%>"></script>
		<script src="js/commons/plugins/pace.min.js?<%=randomUUIDString%>"></script>
    	<script src="js/commons/main.js?<%=randomUUIDString%>"></script>
		<script type="text/javascript" src="js/modules/transactions/purchases/purchase_return.js?<%=randomUUIDString%>"></script>
		<script type="text/javascript" src="js/modules/multipopup/mrefill_purchases.js?<%=randomUUIDString%>"></script>
	    <script type="text/javascript">
			document.getElementById("nameSpan").innerHTML = <%= agencyVO.getAgency_code() %>;
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
		   	 $('#cp_data_table').DataTable( {
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
	 		 
	 		$('#arbp_popup_data_table').DataTable( {
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
	       	} );
			
		</script>
</html>