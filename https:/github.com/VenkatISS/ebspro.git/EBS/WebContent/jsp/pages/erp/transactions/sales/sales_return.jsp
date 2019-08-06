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
    	<title>MyLPGBooks DEALER WEB APPLICATION - SALES RETURN INVOICE ENTRY</title>

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
		<jsp:useBean id="arb_types_list" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="prods_list" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="arb_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="refill_prices_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="sr_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="equipment_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="drs_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="crs_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="arbs_data" scope="request" class="java.lang.String"></jsp:useBean>			
		<jsp:useBean id="staff_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="area_codes_data" scope="request" class="java.lang.String"></jsp:useBean>
		
		<script type="text/javascript" src="js/modal.js?<%=randomUUIDString%>"></script>

		<script type="text/javascript">
			var cat_cyl_types_data = <%= cylinder_types_list.length()>0? cylinder_types_list : "[]" %>;
			var equipment_data = <%= equipment_data.length()>0? equipment_data : "[]" %>;
			var page_data = <%= sr_data.length()>0? sr_data : "[]" %>;
			var bank_data =  <%= bank_data.length()>0? bank_data : "[]" %>;
			var cvo_data = <%= cvo_data.length()>0? cvo_data : "[]" %>;
			var dom_salesInv= <%= drs_data.length()>0? drs_data : "[]" %>;
			var com_salesInv= <%= crs_data.length()>0? crs_data : "[]" %>;
			var arb_salesInv= <%= arbs_data.length()>0? arbs_data : "[]" %>;
			var staff_data = <%= staff_data.length()>0? staff_data : "[]" %>;
			var area_codes_data = <%= area_codes_data.length()>0? area_codes_data : "[]" %>;

			var cat_arb_types_data = <%= arb_data.length()>0? arb_data : "[]" %>;
			var ctdatahtml = "";
			var bankdatahtml = "";
			var customerdatahtml = "";
			var mops = ["NONE","CASH","CHEQUE","ONLINE TRANSFER"];
			var months = ["JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"];
			var refill_prices_data = <%= refill_prices_data.length()>0? refill_prices_data : "[]" %>;

			var staffdatahtml ="";
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

			//Construct Category Type html
			ctdatahtml = "<OPTION VALUE='0'>SELECT</OPTION>";
			ctdatahtml = ctdatahtml + "<OPTION VALUE='-1' disabled>---CYLINDER LIST---</OPTION>";
			if(cat_cyl_types_data.length>0) {
				for(var z=0; z<cat_cyl_types_data.length; z++){
					var catType=cat_cyl_types_data[z].cat_type;
					//if(!(cat_cyl_types_data[z].cat_type)==3)
						if(catType!==3){
					for(var y=0; y<equipment_data.length; y++){
						if(equipment_data[y].prod_code == cat_cyl_types_data[z].id) {
							ctdatahtml=ctdatahtml+"<OPTION VALUE='"+cat_cyl_types_data[z].id+"'>"
								+cat_cyl_types_data[z].cat_name+"-"+cat_cyl_types_data[z].cat_desc+"</OPTION>";
							break;
						}}
					}
				}
			}
			ctdatahtml = ctdatahtml + "<OPTION VALUE='-2' disabled>---ARB LIST---</OPTION>";
			if(cat_arb_types_data.length>0) {
				for(var z=0; z<cat_arb_types_data.length; z++){
					ctdatahtml=ctdatahtml+"<OPTION VALUE='"+cat_arb_types_data[z].id+"'>"
					+ getARBType(cat_arb_types_data[z].prod_code)+" - "+cat_arb_types_data[z].prod_brand+" - "+cat_arb_types_data[z].prod_name
					+ "</OPTION>";
				}
			}

			//-------------------------------------------new popup 22/03/2018 start----------------//
			
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
		
			//Construct Staff html
			staffdatahtml = "<OPTION VALUE='0'>SELECT</OPTION>";
			if(staff_data.length>0) {
				for(var z=0; z<staff_data.length; z++){
					if(staff_data[z].deleted == 0){
						staffdatahtml=staffdatahtml+"<OPTION VALUE='"+staff_data[z].id+"'>"+staff_data[z].emp_name+"</OPTION>";
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
		
			//Construct Customer Type html
			custdatahtml = "<OPTION VALUE='-1'>SELECT</OPTION>";
			custdatahtml = custdatahtml + "<OPTION VALUE='0'>CASH SALES / HOUSEHOLDS</OPTION>";
			if(cvo_data.length>0) {
				for(var z=0; z<cvo_data.length; z++){
					if(cvo_data[z].cvo_cat==1 && cvo_data[z].deleted==0 && cvo_data[z].cvo_name != "UJWALA")
						custdatahtml=custdatahtml+"<OPTION VALUE='"+cvo_data[z].id+"'>"+cvo_data[z].cvo_name+"</OPTION>";
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
          			<div><h1>SALES RETURN INVOICE ENTRY </h1></div>
					<ul id="nav" class="nav nav-pills clearfix right" role="tablist">
          				<li class="dropdown"><span class="dropdown-toggle  btn-info" data-toggle="dropdown" id="ahelp">Help</span>
							<ul id="products-menu" class="dropdown-menu clearfix" role="menu">
								<li><a style="font-size: 14px;" title="sales-return"href="https://www.youtube.com/watch?v=4rMA4CpVwtM" target="_blank">English</a></li>
								<!-- <li><a style="font-size: 14px;" href="https://www.youtube.com/watch?v=4rMA4CpVwtM" target="_blank">Hindi</a></li> -->
							</ul>
						</li>
					</ul>
        		</div>
				<button name="b_sb" id="b_sb" class="btn btn-info color_btn bg_color4" onclick="showSRFormDialog()">ADD</button>
				<button name="cancel_data" id="cancel_data" class="btn btn-info color_btn bg_color2" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/home.jsp','2001')">BACK</button>
				<!-- Modal -->
				<div class="modal_popup_add fade in" id="myModal" style="display: none; height: 100%" >
					<div class="modal-dialog modal-lg">
			  			<!-- Modal content-->
			  			<div class="modal-content" id="modal-content">
							<div class="modal-header" id="modal-header">
					  			<span class="close" id="close" onclick="closeSRFormDialog()">&times;</span>
					  		<!-- 	<h4 class="modal-title">SALES RETURN INVOICE ENTRY</h4> -->
<!-- -------------------------mstart--------------------- -->
   <b>SALES RETURN INVOICE ENTRY</b><button name="ds_pop1" id="ds_pop1" class=" btn-info color_popup_btn bg_color4 btnr" onclick="showDSFormDialog()">DS</button>
							<button name="cs_pop1" id="cs_pop1" class=" btn-info color_popup_btn bg_color4 btnr" onclick="showCSFormDialog()">CS</button>
							<button name="arb_pop1" id="arb_pop1" class=" btn-info color_popup_btn bg_color4 btnr" onclick="showARBFormDialog()">ARB</button>

<!-- -------------------------mend--------------------- -->
							</div>
							<div class="modal-body">
								<form id="data_form" name="" method="post" action="TransactionsDataControlServlet">
									<input type="hidden" id="agencyId" name="agencyId" value="<%= agencyVO.getAgency_code() %>">
									<input type="hidden" id="page" name="page" value="jsp/pages/erp/transactions/sales/sales_return.jsp">
									<input type="hidden" id="actionId" name="actionId" value="5552">

									<div class="row">
										<div class="col-adj-res-sub">
											<label>SALES INVOICE</label>
											<input type="text"  id="sinvno" name="sinvno" class="form-control input_field sinvno csfrz" maxlength="25" placeholder="SALES INVOICE NO">
										</div>
										<div class="col-adj-res-sub">
											<label>INVOICE DATE</label>
											<input type="date" class="form-control input_field invdate" id="invdate" name="invdate" placeholder="DD-MM-YY">
										</div>
										<div class="col-adj-res-sub">
											<label>CUSTOMER</label>
											<span id="vSpan"></span>
										</div>
										<div class="col-adj-res-sub">
											<label>TOTAL AMOUNT</label>
											<input type="text"  id="sr_t_amt" class="form-control input_field sr_t_amt" name="sr_t_amt"  value="" readonly="readonly" style="background-color:#F3F3F3;" placeholder="TOTAL AMOUNT">
										</div>
										<div class="clearfix"></div>
									</div>
									<br/>
									<div class="row">
										<div class="col-md-12">
											<div class="animated-radio-button">
												<br>
												<label>SALE TYPE</label>&nbsp;&nbsp;
												<label>
													<input type="radio"  id="stype" name="stype"  value="lp"><span  id="stype" class="label-text">LOCAL SALE</span>
												</label>&nbsp;&nbsp;
												<label>
													<input type="radio" id="stype" name="stype"  value="isp"><span  id="stype" class="label-text">INTER-STATE SALE</span>
												</label>

												<div class="col-adj-res-sub" style="margin-top:-20px;margin-right:10px;float:right;">
													<label>NARRATION</label>
													<input type="text" id="nar" name="nar" size="40" class="form-control input_field nar" maxlength="25" placeholder="NARRATION">
												</div>										
												<div class="col-adj-res-sub" style="margin-top:-20px;margin-right:10px;float:right;">
													<label>PAYMENT TERMS  </label>
													<select class="form-control input_field select_dropdown" id='pt' name='pt' onchange = "validateBankAccountOnChangeInPT()">
														<option value='0'>SELECT</option>
														<option value="1">CASH</option>
														<option value="3">ONLINE TRANSFER</option>
														<option value="2">CHEQUE</option>
													</select>
												</div>
											</div>
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
																<th width="10%" class="text-center sml_input">RETURN QTY</th>
																<th width="10%" class="text-center sml_input">NET WEIGHT</th>
																<th width="10%" class="text-center sml_input">UNIT PRICE</th>
																<th width="10%" class="text-center sml_input">TAXABLE AMOUNT</th>
																<th width="10%" class="text-center sml_input">IGST<br>AMOUNT</th>
																<th width="10%" class="text-center sml_input">CGST<br>AMOUNT</th>
																<th width="10%" class="text-center sml_input">SGST<br>AMOUNT</th>
																<th width="10%" class="text-center sml_input">AMOUNT</th>
																<th width="10%" class="text-center sml_input">BANK ACCOUNT</th>
																<th width="10%" class="text-center sml_input">Actions</th>
                    										</tr>
                  										</thead>
                  										<tbody id="data_table_body">
                  											<tr>
                  												<td valign="top" height="4" align="center">
                  													<select name="pid"  class="form-control input_field tp freez sadd csfrz"  id="pid" style="width:100px;" onchange="changeNetWeight()">
                  														<%String str3 = "<script>document.writeln(ctdatahtml)</script>";
				   															out.println("value: " + str3);%>
				   													</select>
				   												</td>
				   												<td valign='top' height='4' align='center'><input type=text name='gstp' id='gstp' class='form-control input_field eadd'  size='8' readonly='readonly' style='background-color:#FAFAC2;' placeholder='GST %'></td>
				   												<td valign='top' height='4' align='center'><input type=text name='rqty' id='rqty' size='8' class='form-control input_field qtyc freez eadd' maxlength='4'  placeholder='RETURN QUANTITY'></td>
                   												<td valign='top' height='4' align='center'><input type=text name='nw' id='nw' size='8' class='form-control input_field freez eadd' maxlength='6'  placeholder="NET WEIGHT"></td>
                   												<td valign='top' height='4' align='center'><input type=text name='ur' id='ur' size='8' class='form-control input_field freez eadd' maxlength='7'  placeholder="UNIT PRICE"></td>																
                   												<td valign='top' height='4' align='center'><input type=text name='tamt' id='tamt' class='form-control input_field freez eadd' size='8' placeholder="TAXABLE AMOUNT" readonly="readonly" style="background-color:#F3F3F3;"></td>
                   												<td valign='top' height='4' align='center'><input type=text name='igsta' id='igsta' class='form-control input_field freez eadd' size='8' placeholder='IGST AMOUNT' readonly="readonly" style="background-color:#F3F3F3;"></td>
                   												<td valign='top' height='4' align='center'><input type=text name='cgsta' id='cgsta' class='form-control input_field freez eadd' size='8' placeholder='CGST AMOUNT' readonly="readonly" style="background-color:#F3F3F3;"></td>
                   												<td valign='top' height='4' align='center'><input type=text name='sgsta' id='sgsta' class='form-control input_field freez eadd' size='8' placeholder='SGST AMOUNT' readonly="readonly" style="background-color:#F3F3F3;"></td>
                   												<td valign='top' height='4' align='center'><input type=text name='namt' id='namt' class='form-control input_field freez eadd' size='8' placeholder='AMOUNT' readonly="readonly" style="background-color:#F3F3F3;"></td>
                   												<td valign='top' height='4' align='center'>
						 											<select name="bid" id="bid" class="form-control input_field select_dropdown freez">
                  														<%String str4 = "<script>document.writeln(bankdatahtml)</script>";
				   															out.println("value: " + str4);%>
				   													</select>
																</td>
                   												<td valign='top' height='4'><img src='images/delete.png' onclick='doRowDeleteInTranxs(this,namt,sr_t_amt,data_table_body)'></td>
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
										<button   name="add_data" id="add_data"  class="btn btn-info color_btn"  onclick="addRow()">ADD</button>
									</div>
									<div class="col-md-2">
										<button  name="fetch_data" id="fetch_data"  class="btn btn-info color_btn"  onclick="fetchGST()">FETCH GST</button>
									</div>
									<div class="col-md-2">
										<button   name="calc_data" id="calc_data" class="btn btn-info color_btn"  onclick="calculateValues()" disabled="disabled">CALCULATE</button>
									</div>
									<div class="col-md-2">
										<button name="save_data" id="save_data" class="btn btn-info color_btn bg_color2"  onclick="saveData(this)" disabled="disabled">SAVE</button>
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
							<input type="hidden" id="page" name="page" value="jsp/pages/erp/transactions/sales/sales_return.jsp">
							<input type="hidden" id="actionId" name="actionId" value="">
							<input type="hidden" id="dataId" name="dataId" value=""> 
           					<table class="table  table-striped" id="m_data_table">
           						<thead>
               						<tr class="title_head">
										<th width="10%" class="text-center sml_input">S RETURN<br>INVOICE<br>NUMBER</th>
               							<th width="10%" class="text-center sml_input">SALES<br>REFERENCE<br>NUMBER</th>
			  							<th width="10%" class="text-center sml_input">RETURN<br>INVOICE<br>DATE</th>
			  							<th width="10%" class="text-center sml_input">CUSTOMER</th>
			  							<th width="10%" class="text-center sml_input">TOTAL <br>AMOUNT</th>
			  							<th width="10%" class="text-center sml_input">NARRATION</th>
			  							<th width="10%" class="text-center sml_input">PRODUCT</th>
			  							<th width="10%" class="text-center sml_input">GST %</th>
			  							<th width="10%" class="text-center sml_input">RETURN<br>QTY</th>
			  							<th width="10%" class="text-center sml_input">NET WEIGHT</th>
			  							<th width="10%" class="text-center sml_input">UNIT<br>PRICE</th>
			  							<th width="10%" class="text-center sml_input">TAXABLE<br>AMOUNT</th>
			  							<th width="10%" class="text-center sml_input">IGST<br>AMOUNT</th>
					  					<th width="10%" class="text-center sml_input">CGST<br>AMOUNT</th>
					  					<th width="10%" class="text-center sml_input">SGST<br>AMOUNT</th>
					  					<th width="10%" class="text-center sml_input">AMOUNT</th>
					  					<th width="10%" class="text-center sml_input">BANK ACCOUNT</th>  										  					
				 						<th width="10%" class="text-center sml_input">Actions</th>
               						</tr>
           						</thead>
             					<tbody id="m_data_table_body"></tbody>
                			</table>
                		</form>
         	 		</div>
         	 		<div class="clearfix"></div>
        		</div>
        		
        		<!-- new popup 2202018 -->
			
			
			<!-- <button name="cvo_pop1" id="cvo_pop1" class="btn btn-info color_btn bg_color4" onclick="showCVOFormDialog()">CVO</button>
				<button name="cancel_data" id="cancel_data"	class="btn btn-info color_btn bg_color3" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/home.jsp','2001')">BACK</button>
			 -->	<!-- Modal -->
				<div class="modal_popup_newadd fade in" id="dspopupModal" style="display: none;height: 100%">
					<div class="modal-dialog modal-lg">
			  			<!-- Modal content-->
						<div class="modal-content" id="modal-content" style="top: -50px;font-size:10px;">
                            	<div class="modal-header" id="modal-header" style="padding:5px;">
					  			<span class="close" id="closedspopup" onclick="closeDSFormDialog()">&times;</span>
					  			<h4 class="modal-title">DOMESTIC CYLINDER SALES </h4>
							</div>
				
	<div class="row">
        	<div class="clearfix"></div>
          	<div class="col-md-15">
	            <div class="main_table"  style="margin-left: 37px;margin-right:37px;">
              		<div class="table-mpopup_responsive">
                		<table class="table table-striped" id="ds_data_table">
                  					<thead>
                    					<tr class="title_head_mpopup">
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
											
                   						</tr>
                  					</thead>
                  					<tbody id="ds_data_table_body"></tbody>
                				</table>
              		</div>
            	</div>
          	</div>
        </div>
      </div>
    </div>
</div>
					
	<!-- COM SALES -->		

	 <div class="modal_popup_newadd fade in" id="cspopupModal" style="display: none;height: 100%">
					<div class="modal-dialog modal-lg">
			  			<!-- Modal content-->
<!-- 			  			<div class="modal-mulpopup_content" id="modal-content" style="top: -50px;">
 -->						<div class="modal-content" id="modal-content" style="top: -50px;font-size:10px;">
                            	<div class="modal-header" id="modal-header" style="padding:5px;">
					  			<span class="close" id="closecspopup" onclick="closeCSFormDialog()">&times;</span>
					  			<h4 class="modal-title">COMMERCIAL CYLINDER SALES</h4>
							</div>
			
           		<div class="row">
          			<div class="clearfix"></div>
          			<div class="col-md-15">payment_terms_types
            			<div class="main_table" style="margin-left: 37px;margin-right:37px;">
              				<div class="table-mpopup_responsive">
                				<table class="table  table-striped" id="cs_popup_data_table">
                  					<thead>
                    					<tr class="title_head_mpopup">
										<th width="10%" class="text-center sml_input"> INV NO</th>
                      					<th width="10%" class="text-center sml_input">INVOICE DATE</th>
                      					<th width="10%" class="text-center sml_input">CUSTOMER</th>
					  					<th width="10%" class="text-center sml_input">PAYMENT TERMS</th>                      
					  					<th width="10%" class="text-center sml_input">INV AMOUNT</th>
					  					<th width="10%" class="text-center sml_input">PRODUCT</th>
					  					<th width="10%" class="text-center sml_input">UNIT PRICE</th>
					  					<th width="10%" class="text-center sml_input">DISCOUNT AMOUNT</th>
                      					<th width="10%" class="text-center sml_input">QUANTITY</th>
                      		          	<th width="10%" class="text-center sml_input">IGST AMOUNT</th>
                      		          	<th width="10%" class="text-center sml_input">CGST AMOUNT</th>
					   					<th width="10%" class="text-center sml_input">SGST AMOUNT</th>
					   					<th width="10%" class="text-center sml_input">TOTAL AMOUNT</th>
					    				<th width="10%" class="text-center sml_input">PSV CYLINDERS</th>
					  					<th width="10%" class="text-center sml_input">EMPTIES RECEIVED</th>
										<th width="10%" class="text-center sml_input">BANK ACCOUNT</th>
					  					<th width="10%" class="text-center sml_input">DELIVERED BY</th>
										<th width="10%" class="text-center sml_input">AREA CODE</th>
                    				</tr>
                    					
                  					</thead>
                  					<tbody id="cs_data_table_body">                    
                  					</tbody>
                				</table>
              				</div>
           				 </div>
          			</div>
        		</div>
			</div>
		</div>
	</div>		


<!-- ARB SALES -->		

	 <div class="modal_popup_newadd fade in" id="arbpopupModal" style="display: none;height: 100%">
					<div class="modal-dialog modal-lg">
			  			<!-- Modal content-->
<!-- 			  			<div class="modal-mulpopup_content" id="modal-content" style="top: -50px;">
 -->						<div class="modal-content" id="modal-content" style="top: -50px;font-size:10px;">
                            	<div class="modal-header" id="modal-header" style="padding:5px;">
					  			<span class="close" id="closecspopup" onclick="closeARBFormDialog()">&times;</span>
					  			<h4 class="modal-title">BLPG/ARB/NFR SALES</h4>
							</div>
			
           		<div class="row">
          			<div class="clearfix"></div>
          			<div class="col-md-15">
            			<div class="main_table" style="margin-left: 37px;margin-right:37px;">
              				<div class="table-mpopup_responsive">
                				<table class="table  table-striped" id="arb_popup_data_table">
                  					<thead>
                    					<tr class="title_head_mpopup">
									<th width="10%" class="text-center sml_input">INV NO</th>
                      				<th width="10%" class="text-center sml_input">INVOICE DATE</th>
                      				<th width="10%" class="text-center sml_input">CUSTOMER</th>
					   				<th width="10%" class="text-center sml_input">STAFF NAME</th>
					   				<th width="10%" class="text-center sml_input">PAYMENT TERMS</th>
					  				<th width="10%" class="text-center sml_input">INVOICE AMOUNT</th>
					  				<th width="10%" class="text-center sml_input">PRODUCT</th>
					  				<th width="10%" class="text-center sml_input">UNIT PRICE</th>
					  				<th width="10%" class="text-center sml_input">DISCOUNT AMOUNT</th>
                      				<th width="10%" class="text-center sml_input">QUANTITY</th>
                      				<th width="10%" class="text-center sml_input">BANK ACCOUNT</th>
					  				<th width="10%" class="text-center sml_input">IGST AMOUNT</th>
					  				<th width="10%" class="text-center sml_input">CGST AMOUNT</th>
					   				<th width="10%" class="text-center sml_input">SGST AMOUNT</th>
					 				<th width="10%" class="text-center sml_input">TOTAL AMOUNT</th>
                   				</tr>                    					
                  					</thead>
                  					<tbody id="arb_data_table_body">                    
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
	<script type="text/javascript" src="js/modal.js?<%=randomUUIDString%>"></script>
	<script type="text/javascript" src="js/commons/general_validations.js?<%=randomUUIDString%>"></script>		
	<script type="text/javascript" src="js/modules/transactions/sales/sales_return.js?<%=randomUUIDString%>"></script>
	<script type="text/javascript" src="js/modules/multipopup/mdom_refill_sales.js?<%=randomUUIDString%>"></script>
    <script src="js/commons/main.js?<%=randomUUIDString%>"></script>
    <script type="text/javascript">
     	document.getElementById("nameSpan").innerHTML = <%= agencyVO.getAgency_code() %>
     	var dedate = <%=agencyVO.getDayend_date()%>;
     	var effdate = <%=agencyVO.getEffective_date()%>;
		var a_created_date = <%=agencyVO.getCreated_date()%>;
    	var dealergstin =  "<%= agencyVO.getGstin_no() %>";
    	document.getElementById("vSpan").innerHTML = "<select id='cvo_id' name='cvo_id' class='form-control input_field' onchange='selectSaleType()'>"+custdatahtml+"</select>";
 
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
       	    
       	 $('#ds_data_table').DataTable( {
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
    	 
    	$('#cs_popup_data_table').DataTable( {
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
    	    
    	$('#arb_popup_data_table').DataTable( {
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