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
	    <script type="text/javascript" src="js/modal.js?<%=randomUUIDString%>"></script>
	    <script type="text/javascript" src="js/local.js?<%=randomUUIDString%>"></script>
		<link href="https://cdn.datatables.net/1.10.15/css/jquery.dataTables.min.css" rel="stylesheet" type="text/css">
    	<title>EXPENDITURE/OTHER PURCHASES INVOICE ENTRY</title>
		<jsp:useBean id="agencyVO" scope="session" class="com.it.erpapp.framework.model.vos.AgencyVO"></jsp:useBean>
		<jsp:useBean id="oth_pi_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="cvo_data" scope="request" class="java.lang.String"></jsp:useBean>
		<script type="text/javascript">
			var cvo_data = <%= cvo_data.length()>0? cvo_data : "[]" %>;
			var page_data = <%= oth_pi_data.length()>0? oth_pi_data : "[]" %>;
			var ctdatahtml = "";
			var vendordatahtml = "";
			var months = ["JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"];
			var rcs = ["SELECT","YES","NO"];
			var taxbls = ["SELECT","YES","NO"];
			
			
			//-------------------------mstart 04042019---------------------
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

		//	---------------------------------------------------------------------
		</script>
<%-- 		<script src="js/commons/checkBrowser.js?<%=randomUUIDString%>"></script> --%>

		<!-- Sidenav -->
		<jsp:include page="/jsp/pages/commons/sidenav.jsp"/>    
		<!---END Sidenav--->

		<script type="text/javascript"> 
			window.onload = setWidthHightNav("100%");
		</script>
		<script>
			//Construct Vendor Type html
			var venarr = new Array();
			vendordatahtml = "";
			if(cvo_data.length>0) {
				for(var z= 0; z<cvo_data.length; z++) {
					if(cvo_data[z].cvo_cat==3 && cvo_data[z].deleted==0) {
						vendordatahtml = vendordatahtml+"<OPTION DATA-VALUE='"+cvo_data[z].id+"'>"+cvo_data[z].cvo_name+"</OPTION>";
						venarr.push(cvo_data[z].id);
					}
				}
			}
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
          			<div><h1>EXPENDITURE / OTHER PURCHASES INVOICE ENTRY </h1></div>
					<ul id="nav" class="nav nav-pills clearfix right" role="tablist">
          				<li class="dropdown"><span class="dropdown-toggle  btn-info" data-toggle="dropdown" id="ahelp">Help</span>
							<ul id="products-menu" class="dropdown-menu clearfix" role="menu">
								<li><a style="font-size: 14px;" title="other-purchases" href="https://www.youtube.com/watch?v=C4JmI9QRwG0" target="_blank">English</a></li>
								<!-- <li><a style="font-size: 14px;" href="https://www.youtube.com/watch?v=C4JmI9QRwG0" target="_blank">Hindi</a></li> -->
							</ul>
						</li>
					</ul>
        		</div>
<!-- 				<a class="btn btn-info color_btn bg_color4" href="#" data-toggle="modal" data-target="#myModal">ADD</a> -->
				<button name="b_sb" id="b_sb" class="btn btn-info color_btn bg_color4" onclick="showOTHRPFormDialog()">ADD</button>
				<button name="cancel_data" id="cancel_data" class="btn btn-info color_btn bg_color2" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/home.jsp','2001')">BACK</button>
				<!-- Modal -->
				<div class="modal_popup_add fade in" id="myModal" style="display: none; height: 100%" >
					<div class="modal-dialog modal-lg">
			  			<!-- Modal content-->
			  			<div class="modal-content" id="modal-content">
							<div class="modal-header" id="modal-header">
					  			<span class="close" id="close" onclick="closeOTHRPFormDialog()">&times;</span>
					  			<!-- <h4 class="modal-title">EXPENDITURE / OTHER PURCHASES INVOICE ENTRY</h4> -->
					  			<!-- -------------------------mstart--------------------- -->
   <b>	EXPENDITURE / OTHER PURCHASES INVOICE ENTRY</b>	<button name="cvo_pop1" id="cvo_pop1" class=" btn-info color_popup_btn bg_color4 btnr" onclick="showCVOFormDialog()">CVO</button>

<!-------------------------- mend ------------------------------------------------------->
			  			
							</div>
							<div class="modal-body">
								<form id="data_form" name="" method="post" action="TransactionsDataControlServlet">
									<input type="hidden" id="agencyId" name="agencyId" value="<%= agencyVO.getAgency_code() %>">
									<input type="hidden" id="page" name="page" value="jsp/pages/erp/transactions/purchases/other_purchases.jsp">
									<input type="hidden" id="actionId" name="actionId" value="5117">
										
										<div class="row">
										<div class="col-adj-res-sub">
											<label>INV REF NO</label>
											<input type="text"  id="inv_ref_no" name="inv_ref_no" class="form-control input_field inv_ref_no" maxlength="25" placeholder="INV REF NO">
										</div>
										<div class="col-adj-res-sub">
											<label>INV DATE</label>
											<input type="date" class="form-control input_field inv_date" id="inv_date" name="inv_date" placeholder="DD-MM-YY">
										</div>
										
<!-- 										<div class="col-sm-3" style="width:180px;">
										<label>VENDOR NAME </label>
										<span id="vid" onchange = "changeVendor()"></span>
										</div> -->

										<div class="col-adj-res-sub">
											<label>VENDOR NAME </label>
											<input list="vid" id="answerInput" onchange = "changeVendor()" class="form-control input_field" placeholder="SELECT">
											<datalist id="vid">
											<%String str1 = "<script>document.writeln(vendordatahtml)</script>";
												out.println("value: " + str1);%>
											</datalist>
											<input type="hidden" name="vid" id="answerInputHidden">
										</div>										
										
										<div class="col-adj-res-sub">
											<label>INV AMOUNT</label>
											<input type="text"  id="inv_amt" class="form-control input_field inv_amt" name="inv_amt"  value="" readonly="readonly" style="background-color:#F3F3F3;" placeholder="INV AMOUNT">
										</div>
										<div class="clearfix"></div>
									</div>
									<br/>
									<div class="row">
										<div class="col-md-12" style="width:550px;float:left;">
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
										<div class="col-adj-res-sub" style="margin-top:-5px;margin-right:20px;float:right;">
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
																<th width="10%" class="text-center sml_input">TAXABLE</th>
																<th width="10%" class="text-center sml_input">HSN/SAC CODE</th>
																<th width="10%" class="text-center sml_input">GST%</th>
																<th width="10%" class="text-center sml_input">MINOR HEAD</th>
																<th width="10%" class="text-center sml_input">ACCOUNT HEAD</th>
																<th width="10%" class="text-center sml_input">QTY</th>
																<th width="10%" class="text-center sml_input">UOM</th>
																<th width="10%" class="text-center sml_input">UNIT RATE</th>
																<th width="10%" class="text-center sml_input">TAXABLE VALUE</th>
																<th width="10%" class="text-center sml_input">IGST AMOUNT</th>
																<th width="10%" class="text-center sml_input">CGST AMOUNT</th>
																<th width="10%" class="text-center sml_input">SGST AMOUNT</th>
																<th width="10%" class="text-center sml_input">TOTAL AMOUNT</th>
															    <th width="10%" class="text-center sml_input">ACTIONS</th>	  
                    										</tr> 
                  										</thead>
                  										<tbody id="data_table_body">
                  											<tr>
																<td valign='top' height='4' align='center'><input type=text name='pname' id='pname' size='6' class='form-control input_field  pn freez eadd' maxlength='15'  placeholder='NAME'></td>
				   												<td valign='top' height='4' align='center'>
				   													<SELECT name='tbl' id='tbl'  class='form-control input_field freez sadd' onchange='settaxv()' style='width:100px;'>
																		<OPTION VALUE='0'>SELECT</OPTION>
																		<OPTION VALUE='1'>YES</OPTION>
																		<OPTION VALUE='2'>NO</OPTION>
																	</SELECT>
																</td>
				   												<td valign='top' height='4' align='center'><input type=text name='hsnc' id='hsnc'  class='form-control input_field freez hsnr eadd'  size='8'  maxlength='10' placeholder='HSN/SAC CODE'></td>
																<td valign='top' height='4' align='center'>
				   													<SELECT name='egst' id='egst'  class='form-control input_field egstd freez' style='width:100px;'>
																		<OPTION VALUE='-1'>SELECT</OPTION>
																		<OPTION VALUE='0'>0</OPTION>
																		<OPTION VALUE='5'>5</OPTION>
																		<OPTION VALUE='12'>12</OPTION>
																		<OPTION VALUE='18'>18</OPTION>
																		<OPTION VALUE='28'>28</OPTION>
																	</SELECT>
																</td>				   												
                   												<td valign='top' height='4' align='center'>
				   													<SELECT name='minh' id='minh'  class='form-control input_field freez' style='width:100px;' onchange='setCH()'>
																		<%String str4 = "<script>document.writeln(eshdatahtml)</script>";
				   															out.println("value: " + str4);%>
																	</SELECT>
																</td>	
																<td valign='top' height='4' align='center'>
				   													<select name='ah' id='ah'  class='form-control input_field freez sadd' style='width:100px;'>
																		<%String str5 = "<script>document.writeln(opahsdatahtml)</script>";
				   															out.println("value: " + str5);%>
																	</select>
																</td>
																<td valign='top' height='4' align='center'><input type=text name='qty' id='qty' size='8' class='form-control input_field qtyc freez eadd' maxlength='4' placeholder='Quantity'></td>
                   												<td valign='top' height='4' align='center'>
				   													<select name='uom' id='uom'  class='form-control input_field freez sadd' style='width:100px;'>
																		<%String str6 = "<script>document.writeln(uomdatahtml)</script>";
				   															out.println("value: " + str6);%>
																	</select>
																</td>                                                    
																<td valign='top' height='4' align='center'><input type=text name='ur' id='ur' class='form-control input_field freez eadd' size='8' maxlength='7' placeholder='UNIT RATE'><input type='hidden' id='ch' name='ch'></td>	
                   												<td valign='top' height='4' align='center'><input type=text name='tval' id='tval'  class='form-control input_field eadd' maxlength='8' size='8'readonly='readonly' style='background-color:#F3F3F3;' placeholder='TAXABLE VALUE' readonly="readonly"></td>
																<td valign='top' height='4' align='center'><input type=text name='igsta'  id='igsta' class='form-control input_field eadd' size='8' readonly='readonly' style='background-color:#F3F3F3;' placeholder='IGst Amount' ></td>
                   												<td valign='top' height='4' align='center'><input type=text name='cgsta'  id='cgsta' class='form-control input_field eadd' size='8' readonly='readonly' style='background-color:#F3F3F3;' placeholder='CGst Amount' ></td>
                   												<td valign='top' height='4' align='center'><input type=text name='sgsta'  id='sgsta' class='form-control input_field eadd' size='8' readonly='readonly' style='background-color:#F3F3F3;' placeholder='SGst Amount' ></td>
                   												<td valign='top' height='4' align='center'><input type=text name='ta'  id='ta' class='form-control input_field eadd' size='8' readonly='readonly' style='background-color:#F3F3F3;' placeholder='TOTAL Amount' ></td>
                   												<td valign='top' height='4' align='center'><img src='images/delete.png' onclick='doRowDeleteInTranxs(this,ta,inv_amt,data_table_body)'></td>
                   												<!-- <td valign='top' height='4' align='center'>
																	<input type=hidden name='igsta' id='igsta'>
																	<input type=hidden name='sgsta' id='sgsta'>
																	<input type=hidden name='cgsta' id='cgsta'>
																</td> -->
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
										<button   name="calc_data" id="calc_data" class="btn btn-info color_btn"  onclick="calculateValues()">CALCULATE</button>
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
								<input type="hidden" id="page" name="page" value="jsp/pages/erp/transactions/purchases/other_purchases.jsp">
								<input type="hidden" id="actionId" name="actionId" value="">
								<input type="hidden" id="dataId" name="dataId" value="">
           					<table class="table  table-striped" id="m_data_table">
           						<thead>
               						<tr class="title_head">
										<th width="10%" class="text-center sml_input"> INV NO</th>
               							<th width="10%" class="text-center sml_input">INVOICE DATE</th>
               							<th width="10%" class="text-center sml_input">VENDOR</th>
               							<th width="10%" class="text-center sml_input">REVERSE CHARGE</th>
			  							<th width="10%" class="text-center sml_input">PRODUCT</th>
			  							<th width="10%" class="text-center sml_input">TAXABLE</th>
			  							<th width="10%" class="text-center sml_input">HSN/SAC CODE</th>
			  							<th width="10%" class="text-center sml_input">GST%</th>
			  							<th width="10%" class="text-center sml_input">MINOR HEAD</th>
			  							<th width="10%" class="text-center sml_input">ACCOUNT HEAD</th>
			  							<th width="10%" class="text-center sml_input">QTY</th>
			  							<th width="10%" class="text-center sml_input">UOM</th>
			  							<th width="10%" class="text-center sml_input">UNIT PRICE</th>
                                        <th width="10%" class="text-center sml_input">TAXABLE VALUE</th>
			  							<th width="10%" class="text-center sml_input">IGST AMONT</th>
			  							<th width="10%" class="text-center sml_input">SGST AMOUNT</th>
			  							<th width="10%" class="text-center sml_input">CGST AMOUT</th>
					  					<th width="10%" class="text-center sml_input">TOTAL AMOUNT	</th>
				 						<th width="10%" class="text-center sml_input">ACTIONS</th>
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
							<input type="hidden" id="page" name="page" value="jsp/pages/erp/transactions/purchases/other_purchases.jsp">
							<input type="hidden" id="actionId" name="actionId" value="9999">
							<input type="hidden" id="dataId" name="dataId" value="">
							<input type="hidden" id="popupPageId" name="popupPageId" value="6">
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
<!-- 		<button name="mcvo_cancel_data" id="mcvo_cancel_data"	class="btnr btn-info color_popup_btn bg_color2" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/transactions/purchases/other_purchases.jsp','5116')">BACK PAGE</button> -->
		 	
				
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
					
	<!-- END -->		
      		</div>
    	</div>
    	<div id = "dialog-1" title="Alert(s)"></div>
 		<div id="dialog-confirm"><div id="myDialogText" style="color:black;"></div></div>
    </body>
	<script src="js/commons/bootstrap.min.js?<%=randomUUIDString%>"></script>
	<script src="js/commons/plugins/pace.min.js?<%=randomUUIDString%>"></script>
	<script type="text/javascript" src="js/modal.js?<%=randomUUIDString%>"></script>
	<script type="text/javascript" src="js/commons/general_validations.js?<%=randomUUIDString%>"></script>		
	<script type="text/javascript" src="js/modules/transactions/purchases/other_purchases.js?<%=randomUUIDString%>"></script>
	<script type="text/javascript" src="js/modules/multipopup/mcvo_data.js?<%=randomUUIDString%>"></script>
    <script src="js/commons/main.js?<%=randomUUIDString%>"></script>
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
//   	    	"bPaginate" : $('#m_data_table tbody tr').length>5,
//	   	    "iDisplayLength": 5,
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
   	    
   	 $('#mcvo_popup_data_table').DataTable({
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
