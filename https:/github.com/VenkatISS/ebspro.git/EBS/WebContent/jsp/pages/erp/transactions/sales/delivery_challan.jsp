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
    	<title>DELIVERY CHALLAN</title>
    	<jsp:useBean id="agencyVO" scope="session" class="com.it.erpapp.framework.model.vos.AgencyVO"></jsp:useBean>
		<jsp:useBean id="cylinder_types_list" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="arb_types_list" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="qtn_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="cvo_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="staff_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="fleet_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="equipment_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="arb_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="refill_prices_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="arb_prices_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="prod_types_list" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="dc_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="drs_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="crs_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="arbs_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="cyldp_data" scope="request" class="java.lang.String"></jsp:useBean>

		<script type="text/javascript">
			var cat_cyl_types_data = <%= cylinder_types_list %>;
			var cat_arb_types_data = <%= arb_data %>;
			var page_data = <%= qtn_data.length()>0? qtn_data : "[]" %>;
			var cvo_data = <%= cvo_data.length()>0? cvo_data : "[]" %>;
			var staff_data = <%= staff_data.length()>0? staff_data : "[]" %>;
			var fleet_data = <%= fleet_data.length()>0? fleet_data : "[]" %>;
			var equipment_data = <%= equipment_data.length()>0? equipment_data : "[]" %>;
			var arb_data = <%= arb_data.length()>0? arb_data : "[]" %>;
			var refill_prices_data = <%= refill_prices_data.length()>0? refill_prices_data : "[]" %>;
			var arb_prices_data = <%= arb_prices_data.length()>0? arb_prices_data : "[]" %>;
			var prod_types = <%= prod_types_list %>;
			var page_data = <%= dc_data.length()>0? dc_data : "[]" %>;
			var dom_salesInv = <%= drs_data.length()>0? drs_data : "[]" %>;
			var com_salesInv = <%= crs_data.length()>0? crs_data : "[]" %>;
			var arb_salesInv = <%= arbs_data.length()>0? arbs_data : "[]" %>;
			var cyld_purInv = <%= cyldp_data.length()>0? cyldp_data : "[]" %>;

			var ctdatahtml = "";
			var vendordatahtml = "";
			var staffdatahtml = "";
			var fleetdatahtml = "";
			var months = ["JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"];

			//Construct Category Type html
			ctdatahtml = "<OPTION VALUE='0'>SELECT</OPTION>";
			ctdatahtml = ctdatahtml + "<OPTION VALUE='-1' disabled>---CYLINDER LIST---</OPTION>";
			if(cat_cyl_types_data.length>0) {
				for(var z=0; z<cat_cyl_types_data.length; z++) {
					ctdatahtml = ctdatahtml+"<OPTION VALUE='"+cat_cyl_types_data[z].id+"'>"+cat_cyl_types_data[z].cat_name + "-"
						+ cat_cyl_types_data[z].cat_desc+"</OPTION>";
					if(cat_cyl_types_data[z].id < 10){
						ctdatahtml = ctdatahtml+"<OPTION VALUE='"+cat_cyl_types_data[z].id+"e'>"+cat_cyl_types_data[z].cat_name + "-"
							+ cat_cyl_types_data[z].cat_desc + "(Empties)" +"</OPTION>";
					}
				}
			}
/* 			ctdatahtml = ctdatahtml + "<OPTION VALUE='-2' disabled>---ARB LIST---</OPTION>";
			if(cat_arb_types_data.length>0) {
				for(var z=0; z<cat_arb_types_data.length; z++){
					ctdatahtml=ctdatahtml+"<OPTION VALUE='"+cat_arb_types_data[z].id+"'>"
							+getARBType(cat_arb_types_data[z].prod_code)+" - "+cat_arb_types_data[z].prod_brand+" - "+cat_arb_types_data[z].prod_name
							+"</OPTION>";
				}
			} */
			function getARBType(prod_code){
				if(prod_code==13)
					return "STOVE";
				if(prod_code==14)
					return "SURAKSHA";
				if(prod_code==15)
					return "LIGHTER";
				if(prod_code==16)
					return "KITCHENWARE";
				if(prod_code==17)
					return "OTHERS";
			}
			
			var agency_oc = <%= agencyVO.getAgency_oc() %>;
	     	var omc_name;
	     	if(agency_oc == 1)
				omc_name = "IOCL";
			else if(agency_oc == 2)
				omc_name = "HPCL";
			else if(agency_oc == 3)
				omc_name = "BPCL";

	     	//Construct Customer Type html
			custdatahtml = "<OPTION VALUE='0'>SELECT</OPTION>";
			if(cvo_data.length>0) {
				for(var z=0; z<cvo_data.length; z++){
					if((cvo_data[z].cvo_cat==1 || cvo_data[z].cvo_cat == 2) && (cvo_data[z].deleted==0) && (cvo_data[z].cvo_name != "UJWALA")) {
						if(cvo_data[z].cvo_cat == 2)
				 			custdatahtml = custdatahtml+"<OPTION VALUE='"+cvo_data[z].id+"'>"+omc_name+"-"+cvo_data[z].cvo_name+"</OPTION>";
				 		else
				 			custdatahtml = custdatahtml+"<OPTION VALUE='"+cvo_data[z].id+"'>"+cvo_data[z].cvo_name+"</OPTION>";
					}
			 	}
			 }
			 //document.getElementById("cSpan").innerHTML = "<select id='cust_id' class='form-control input_field select_dropdown' name='cust_id' onchange='changeSaleTypeBasedOnCust()'>"+custdatahtml+"</select>";

			 //Construct Staff html
			 staffdatahtml = "<OPTION VALUE='0'>SELECT</OPTION>";
			 if(staff_data.length>0) {
			 	for(var z=0; z<staff_data.length; z++){
			 		if(staff_data[z].deleted == 0){
			 		staffdatahtml=staffdatahtml+"<OPTION VALUE='"+staff_data[z].id+"'>"+staff_data[z].emp_name+"</OPTION>";
			 		}
			 	}
			 }
			// document.getElementById("sSpan").innerHTML = "<select id='staff_id' class='form-control input_field select_dropdown' name='staff_id'>"+staffdatahtml+"</select>";

			 //Construct Fleet html
			 fleetdatahtml = "<OPTION VALUE='0'>SELECT</OPTION>";
			 if(fleet_data.length>0) {
			 	for(var z=0; z<fleet_data.length; z++){
			 		if(fleet_data[z].deleted == 0){
			 		fleetdatahtml=fleetdatahtml+"<OPTION VALUE='"+fleet_data[z].id+"'>"+fleet_data[z].vehicle_no+"</OPTION>";
			 		}
			 	}
			 }
			 //document.getElementById("fSpan").innerHTML = "<select id='fleet_id' class='form-control input_field select_dropdown' name='fleet_id'>"+fleetdatahtml+"</select>";
	
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
         	 	<div><h1>DELIVERY CHALLAN   </h1></div>
					<ul id="nav" class="nav nav-pills clearfix right" role="tablist">
          				<li class="dropdown"><span class="dropdown-toggle  btn-info" data-toggle="dropdown" id="ahelp">Help</span>
							<ul id="products-menu" class="dropdown-menu clearfix" role="menu">
								<li><a style="font-size: 14px;" title="delivery-challan"href="https://www.youtube.com/watch?v=UZNEd2E9_IU" target="_blank">English</a></li>
								<!-- <li><a style="font-size: 14px;" href="https://www.youtube.com/watch?v=UZNEd2E9_IU" target="_blank">Hindi</a></li> -->
							</ul>
						</li>
					</ul>
        	</div>
			<button name="b_sb" id="b_sb" class="btn btn-info color_btn bg_color4" onclick="showDCFormDialog()">ADD </button>
			<button name="cancel_data" id="cancel_data" class="btn btn-info color_btn bg_color2" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/home.jsp','2001')">BACK</button>
			<!-- Modal -->
			<div class="modal_popup_add fade in" id="myModal" style="display: none;height: 100%">					
				<div class="modal-dialog modal-lg">
				<!-- Modal content-->
			  			<div class="modal-content" id="modal-content">
							<div class="modal-header" id="modal-header">
				  			<span class="close" id="close" onclick="closeDCFormDialog()">&times;</span>
				  			<h4 class="modal-title">DELIVERY CHALLAN </h4>
						</div>
						<div class="modal-body">
							<form id="data_form" name="" method="post" action="TransactionsDataControlServlet">
								<input type="hidden" id="agencyId" name="agencyId" value="<%= agencyVO.getAgency_code() %>">
								<input type="hidden" id="page" name="page" value="jsp/pages/erp/transactions/sales/delivery_challan.jsp">
								<input type="hidden" id="actionId" name="actionId" value="5152">
								<div class="row">
									<div class="col-adj-res-sub">
										<label>DELIVERY CHALLAN DATE </label>
										<input type="date" class="form-control input_field"  id="dc_date" name="dc_date" placeholder="DD-MM-YY">
										<input type="hidden" id="dcrefdate" name="dcrefdate" value="">
									</div>
									<div class="col-adj-res-sub">
										<label>CUSTOMER NAME  </label>
										<span id="cSpan"></span>
									</div>
									<div class="col-adj-res-sub">
										<label>STAFF NAME  </label>
										<span id="sSpan"></span>
									</div>
									<div class="col-adj-res-sub">
										<label>VEHICLE NUMBER  </label>
										<span id="fSpan"></span>
									</div>
									<br><br><br><br>
						 			<div class="col-adj-res-sub">
										<label>INVOICE NUMBER  </label>
										<input type="text" class="form-control input_field" id="inv_no" name="inv_no" value="" maxlength="12" placeholder="INVOICE NUMBER">
									</div>	
									<div class="col-adj-res-sub">
										<label>DELIVERY MODE  </label>
										<input type="text" class="form-control input_field" id="dm" name="dm" value="" maxlength="20" placeholder="DELIVERY MODE">
									</div>	
									<div class="col-adj-res-sub">
										<label>DELIVERY INSTRUCTIONS  </label>
										<input type="text" class="form-control input_field" id="dinstr" name="dinstr" value="" maxlength="50" placeholder="DELIVERY INSTRUCTIONS">
									</div>	 
						 			<div class="col-adj-res-sub">
										<label>DC VALUE  </label>
										<input type="text" class="form-control input_field" id="dc_amt" name="dc_amt" value="" readonly="readonly" style="background-color:#F3F3F3;" placeholder="DELIVERY CHALLAN VALUE">
									</div>	
									<div class="clearfix"></div>
								</div>
								<br/>
									<div class="row">
										<div class="col-md-12">
											<div class="animated-radio-button">
<!-- 												<label>SALE TYPE</label>&nbsp;&nbsp; -->
												<label>LOCAL/INTER-STATE</label>&nbsp;&nbsp;
												<label>
						  							<input type="radio"  id="stype" name="stype"  value="ls"><span  id="stype" class="label-text">LOCAL</span>
												</label>&nbsp;&nbsp;
												<label>
						  							<input type="radio" id="stype" name="stype"  value="iss"><span  id="stype" class="label-text">INTER-STATE</span>
												</label>
											</div>
										</div>
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
															<th width="10%" class="text-center sml_input">QUANTITY</th>
															<th width="10%" class="text-center sml_input">TAXABLE AMOUNT</th>
													        <th width="10%" class="text-center sml_input">IGST AMOUNT</th>
															<th width="10%" class="text-center sml_input">SGST AMOUNT</th>
															<th width="10%" class="text-center sml_input">CGST AMOUNT</th>
															<th width="10%" class="text-center sml_input">TOTAL AMOUNT</th>
						 									<th width="10%" class="text-center sml_input">Actions</th>
                    									</tr>
                  									</thead>
                  									<tbody id="data_table_body">
                    									<tr>
                     										<td>
                     											<select class="form-control input_field select_dropdown sadd tp epid" name='epid' id='epid'>
						  											<%String str = "<script>document.writeln(ctdatahtml)</script>";
						   													out.println("value: " + str);%>
																</select>
															</td>
                      										<td>
                      											<input type="text" class="form-control input_field eadd" name='vp' id='vp' size='8' readonly='readonly' style='background-color:#FAFAC2;' placeholder="Gst%" />
                      										</td>
					    									<td>
					    										<input type="text" class="form-control input_field eadd" name='up' id='up' size='8'  maxlength='7' style='background-color:#FAFAC2;' placeholder="Unit Price" />
					    									</td>
															<td>
																<input type="text" class="form-control input_field qtyc freez eadd" name='qty' id='qty' size='8' maxlength='4' placeholder="Quantity" />
																<input type="hidden"  name="cqty" id="cqty" value="" />
															</td>
						 									<td>
						 										<input type="text" class="form-control input_field eadd" name='bp' id='bp' size='8' readonly='readonly' style='background-color:#F3F3F3;' placeholder="TAXABLE AMOUNT" />
						 									</td>
						 									<td>
						 										<input type="text" class="form-control input_field eadd" name='igsta' id='igsta' size='8' readonly='readonly' style='background-color:#F3F3F3;' placeholder="IGST Amount" />
						 									</td>
						 									<td>
						 										<input type="text" class="form-control input_field eadd" name='sgsta' id='sgsta' size='8' readonly='readonly' style='background-color:#F3F3F3;' placeholder="SGST Amount" />
						 									</td>
						 									<td>
						 										<input type="text" class="form-control input_field eadd" name='cgsta' id='cgsta' size='8' readonly='readonly' style='background-color:#F3F3F3;' placeholder="CGST Amount" />
						 									</td>
						  									<td>
						  										<input type="text" class="form-control input_field eadd" name='amt' id='amt' size='8' readonly='readonly' style='background-color:#F3F3F3;' placeholder="Total Amount" />
						  									</td>
                      										<td><img src='images/delete.png' onclick='doRowDeleteInTranxs(this,amt,dc_amt,data_table_body)'></td>
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
					<input type="hidden" id="page" name="page" value="jsp/pages/erp/transactions/sales/delivery_challan.jsp">
					<input type="hidden" id="actionId" name="actionId" value="">
					<input type="hidden" id="dataId" name="dataId" value="">
                	<table class="table table-striped" id="m_data_table">
                  		<thead>
                    		<tr class="title_head">
								<th width="10%" class="text-center sml_input">DC NO</th>
								<th width="10%" class="text-center sml_input">DC DATE</th>
								<th width="10%" class="text-center sml_input">INV REF NO</th>
                    			<th width="10%" class="text-center sml_input">CUSTOMER</th>
								<th width="10%" class="text-center sml_input">STAFF NAME</th>
								<th width="10%" class="text-center sml_input">VEHICLE NUMBER</th>
					  			<th width="10%" class="text-center sml_input">DC AMOUNT</th>
					  			<th width="10%" class="text-center sml_input">DELIVERY INSTRUCTIONS</th>
					  			<th width="10%" class="text-center sml_input">DELIVERY MODE</th>
					   			<th width="10%" class="text-center sml_input">PRODUCT</th>
					    		<th width="10%" class="text-center sml_input">GST %	</th>
								<th width="10%" class="text-center sml_input">UNIT PRICE</th>
								<th width="10%" class="text-center sml_input">TAXABLE AMOUNT</th>
								<th width="10%" class="text-center sml_input">QUANTITY</th>
							   <th width="10%" class="text-center sml_input">IGST AMOUNT</th>
								<th width="10%" class="text-center sml_input">CGST AMOUNT</th>
								<th width="10%" class="text-center sml_input">SGST AMOUNT</th>
					    		<th width="10%" class="text-center sml_input">TOTAL AMOUNT</th>
								<th width="10%" class="text-center sml_input">Actions</th>
                    		</tr>
                  		</thead>
                  		<tbody id="m_data_table_body"></tbody>
                	</table>
                </form>
            </div>
        </div>
   	</div>
	</div>
	<script type="text/javascript" src="js/modal.js?<%=randomUUIDString%>"></script>
	<script type="text/javascript" src="js/commons/general_validations.js?<%=randomUUIDString%>"></script>
<%-- 	<script type="text/javascript" src="js/modules/transactions/sales/delivery_challan.js?<%=randomUUIDString%>"></script> --%>
	<script type="text/javascript" src="js/modules/transactions/sales/delivery_challan.js"></script>
    <script src="js/commons/bootstrap.min.js?<%=randomUUIDString%>"></script>
	<script src="js/commons/plugins/pace.min.js?<%=randomUUIDString%>"></script>
    <script src="js/commons/main.js?<%=randomUUIDString%>"></script>
    <script type="text/javascript">
	 	document.getElementById("nameSpan").innerHTML = <%= agencyVO.getAgency_code() %>; 
	 	var dedate = <%=agencyVO.getDayend_date()%>;
     	var effdate = <%=agencyVO.getEffective_date()%>;
		var a_created_date = <%=agencyVO.getCreated_date()%>;
    	var dealergstin =  "<%= agencyVO.getGstin_no() %>";
    	document.getElementById("cSpan").innerHTML = "<select id='cust_id' class='form-control input_field select_dropdown' name='cust_id' onchange='changeSaleTypeBasedOnCust()'>"+custdatahtml+"</select>";
		document.getElementById("sSpan").innerHTML = "<select id='staff_id' class='form-control input_field select_dropdown' name='staff_id'>"+staffdatahtml+"</select>";
		document.getElementById("fSpan").innerHTML = "<select id='fleet_id' class='form-control input_field select_dropdown' name='fleet_id'>"+fleetdatahtml+"</select>";
    	
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
	   	} );  
	</script>
	<div id = "dialog-1" title="Alert(s)"></div>
 	<div id="dialog-confirm"><div id="myDialogText" style="color:black;"></div></div>
 	</body>
</html>
