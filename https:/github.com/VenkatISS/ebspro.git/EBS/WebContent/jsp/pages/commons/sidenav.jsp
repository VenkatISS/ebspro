<%@ page import="java.util.UUID" %>
<!DOCTYPE html>
<html>
<%
	UUID uuid = UUID.randomUUID();
	String randomUUIDString = uuid.toString();
%>

<script type="text/javascript" src="js/commons/sidenav.js?<%=randomUUIDString%>"></script>
<script type="text/javascript" src="js/global.js?<%=randomUUIDString%>"></script>
<script src="js/commons/detectmobilebrowser.js?<%=randomUUIDString%>"></script>
<script src="js/commons/checkBrowser.js?<%=randomUUIDString%>"></script>
<script src = "https://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
<link href = "https://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css" rel = "stylesheet">
<!-- css for  alert dialouge -->
<style>
  /* .ui-widget-content {
  	 color: #ec0909;
  	 background: #dadada;
  	 z-index: 19800  !important;
  	 
	} */

  .ui-dialog .ui-dialog-titlebar {
   	 background: #0b1e59;
   }
   .ui-widget-header {
     background: blue;
     border :none;
    }
    .ui-dialog .ui-dialog-titlebar-close {
      display: none;
     }
</style>
<style>
	.ui-widget-overlay{
		background:none;
		background-color : #919295;
		z-index: 1100  !important;
	}
</style>
<style type="text/css">	
	.ui-widget-content {
		 z-index: 19800  !important;
		 background: #fff;
	 	/* color: #ec0909; */
	 	/* color: black; */
  	 	/* background: #dadada; */
  	 	/*  background: #fffff; */  		
	 } 
	
</style>

<script type="text/javascript">
	window.onbeforeunload = winClose();
</script>
<aside class="main-sidebar hidden-print" style="min-height: 94vh;max-height: 94vh;">
	<section class="sidebar">
    	<!-- Sidebar Menu-->
        <form name="forwardForm" id="forwardForm" method="post">
			<input type="hidden" name="page"> 
			<input type="hidden" name="actionId"> 
			<input type="hidden" name="agencyId" value="">
          	<ul class="sidebar-menu">
            	<li class="active mcls"><a href="#hom" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/home.jsp','2001')" class = "tabhome"><i class="fa fa-home"></i><span >HOME</span></a></li>
            	<li class="treeview mcls"><a href="#md"><i class="fa fa-dashboard"></i><span>MASTER DATA</span><i class="fa fa-angle-right"></i></a>
                	<ul class="treeview-menu">
                 		<li class="tresat"><a href="#stat" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/masterdata/statutory_data.jsp','3001')"><i class="fa fa-table"></i><b> STATUTORY MASTER</b></a></li>
<!--                  		<li><a href="#pcd" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/masterdata/product_category_data.jsp','3006')"><i class="fa fa-table"></i> PRODUCT CAT MASTER</a></li> -->
                  		<li class="treeview"><a href="#pmd"><i class="fa fa-product-hunt"></i><span>PRODUCT MASTER</span><i class="fa fa-angle-right"></i></a>
                      		<ul class="treeview-menu">
                          		<li><a href="#eqpd" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/masterdata/products/equipment_data.jsp','3101')"><i class="fa fa-circle-o"></i> EQUIPMENT MASTER</a></li>
                          		<li><a href="#arbd" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/masterdata/products/arb_data.jsp','3111')"><i class="fa fa-circle-o"></i> BLPG/ARB/NFR MASTER</a></li>
                          		<li><a href="#srvd" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/masterdata/products/services_data.jsp','3121')"><i class="fa fa-circle-o"></i> SERVICES MASTER</a></li>
                          		<li><a href="#acd" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/masterdata/products/area_code_data.jsp','3131')"><i class="fa fa-circle-o"></i> AREA CODE MASTER</a></li>
<!--                           		<li><a href="#bmd" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/masterdata/products/bom_data.jsp','3141')"><i class="fa fa-circle-o"></i> BOM/NC PACKAGING</a></li> -->
                          		<li><a href="#bmd" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/masterdata/products/bom_data.jsp','3141')"><i class="fa fa-circle-o"></i> NC/DBC PACKAGING</a></li>
                      		</ul>
                  		</li>
                    	<li class="treeview"><a href="#prmd"><i class="fa fa-money"></i><span>PRICE MASTER</span><i class="fa fa-angle-right"></i></a>
                       		<ul class="treeview-menu">
                             	<li><a href="#rpd" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/masterdata/prices/refill_prices_data.jsp','3201')"><i class="fa fa-circle-o"></i> REFILL PRICE</a></li>
                             	<li><a href="#arbpd" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/masterdata/prices/arb_prices_data.jsp','3211')"><i class="fa fa-circle-o"></i> BLPG/ARB/NFR PRICE</a></li>
                        	</ul>
                    	</li>
                    	<li><a href="#cvd" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/masterdata/cv_data.jsp','3501')"><i class="fa fa-table"></i> CUST/VEND MASTER</a></li>
                    	<li><a href="#cdld" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/masterdata/credit_limits_data.jsp','3551')"><i class="fa fa-table"></i> SET CREDIT LIMIT</a></li>                    	
                    	<li><a href="#bd" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/masterdata/bank_data.jsp','3511')"><i class="fa fa-table"></i> BANK MASTER</a></li>
                    	<li><a href="#bd" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/masterdata/opening_balance.jsp','3556')"><i class="fa fa-table"></i> OPENING BALANCES MASTER</a></li>
<!--                     	<li><a href="#fltd" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/masterdata/fleet_data.jsp','3531')"><i class="fa fa-table"></i> FLEET MASTER</a></li> -->
                    	<li><a href="#fltd" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/masterdata/fleet_data.jsp','3531')"><i class="fa fa-table"></i> VEHICLES MASTER</a></li>
                    	<li><a href="#stfd" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/masterdata/staff_data.jsp','3541')"><i class="fa fa-table"></i> STAFF MASTER</a></li>
                 	</ul>
            	</li>
            	<li class="treeview mcls"><a href="#trns"><i class="fa fa-file-text-o"></i><span>TRANSACTIONS</span><i class="fa fa-angle-right"></i></a>
              		<ul class="treeview-menu">
              			<li class="treeview"><a href="#pur"><i class="fa fa-sticky-note-o"></i><span>PURCHASES</span><i class="fa fa-angle-right"></i></a>
                  			<ul class="treeview-menu">
                    			<li><a href="#cylpur" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/transactions/purchases/c_purchases.jsp','5101')"><i class="fa fa-circle-o"></i> CYLINDER PURCHASES </a></li>
                    			<li><a href="#arbpur" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/transactions/purchases/arb_purchases.jsp','5111')"><i class="fa fa-circle-o"></i> BLPG/ARB/NFR PUR </a></li>
       		  					<li><a href="#otrpur" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/transactions/purchases/other_purchases.jsp','5116')"><i class="fa fa-circle-o"></i> EXP/OTHER PURCHASES </a></li>               
       		  					<li><a href="#purret" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/transactions/purchases/purchase_return.jsp','5571')"><i class="fa fa-circle-o"></i> DEFECTIVE/PUR RETURN </a></li>               
                  			</ul>
                		</li>
                		<li class="treeview"><a href="#"><i class="fa fa-sticky-note-o"></i><span>SALES</span><i class="fa fa-angle-right"></i></a>
                  			<ul class="treeview-menu">
                  				<li><a href="#quo" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/transactions/sales/quotations.jsp','5141')"><i class="fa fa-circle-o"></i> QUOTATIONS </a></li>
<!--                     			<li><a href="#drs" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/transactions/sales/dom_refill_sales.jsp','5121')"><i class="fa fa-circle-o"></i> DOMESTIC CYLINDER SALES</a></li>
                    			<li><a href="#crs" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/transactions/sales/com_refill_sales.jsp','5126')"><i class="fa fa-circle-o"></i> COMMERCIAL CYLINDER SALES</a></li> -->
                    			<li><a href="#drs" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/transactions/sales/dom_refill_sales.jsp','5121')"><i class="fa fa-circle-o"></i> DOMESTIC CYL SALES</a></li>
                    			<li><a href="#crs" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/transactions/sales/com_refill_sales.jsp','5126')"><i class="fa fa-circle-o"></i> COMMERCIAL CYL SALES</a></li>
                    			<li><a href="#arbs" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/transactions/sales/arb_sales.jsp','5131')"><i class="fa fa-circle-o"></i> BLPG/ARB/NFR SALES</a></li>
                    			<li><a href="#dc" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/transactions/sales/delivery_challan.jsp','5151')"><i class="fa fa-circle-o"></i> DELIVERY CHALLAN</a></li>
		                		<li><a href="#sret" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/transactions/sales/sales_return.jsp','5551')"><i class="fa fa-circle-o"></i> SALES RETURN </a></li>
                  			</ul>
                		</li>
                 		<li class="treeview"><a href="#nrt"><i class="fa fa-info-circle"></i><span>NC / DBC / RC / TV</span><i class="fa fa-angle-right"></i></a>
                   			<ul class="treeview-menu">
                     			<li><a href="#ncdbc" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/transactions/ncordbc/nc_dbc.jsp','5721')"><i class="fa fa-circle-o"></i>NC / DBC</a></li>
                     			<li><a href="#rectn" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/transactions/ncordbc/rc.jsp','5711')"><i class="fa fa-circle-o"></i>ITV/RC</a></li>
					 			<li><a href="#trnsfv" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/transactions/ncordbc/tv.jsp','5701')"><i class="fa fa-circle-o"></i>OTV/TTV/TV</a></li>
                  			</ul>
                 		</li>
              		</ul>
            	</li>  
                 <li class="treeview mcls"><a href="#cb"><i class="fa fa-university"></i><span>CASH & BANK</span><i class="fa fa-angle-right"></i></a>
              		<ul class="treeview-menu">
                		<li><a href="#rcpts" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/cash&bank/receipts.jsp','5501')"><i class="fa fa-circle-o"></i> RECEIPTS </a></li>
                		<li><a href="#pmts" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/cash&bank/payments.jsp','5511')"><i class="fa fa-circle-o"></i> PAYMENTS </a></li>
                		<li><a href="#btrans" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/cash&bank/bank_tranxs.jsp','5521')"><i class="fa fa-circle-o"></i> BANK TRANSACTIONS</a></li>
                		<li><a href="#crn" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/cash&bank/credit_notes.jsp','5531')"><i class="fa fa-circle-o"></i> CREDIT NOTE </a></li>
                		<li><a href="#drn" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/cash&bank/debit_notes.jsp','5541')"><i class="fa fa-circle-o"></i> DEBIT NOTE </a></li>                		                		
              		</ul>
            	</li>
            	<li class="treeview mcls"><a href="#rprts"><i class="fa fa-university"></i><span>TRANSACTION REPORTS</span><i class="fa fa-angle-right"></i></a>
              		<ul class="treeview-menu">
                 		<li><a href="#prr" onclick="doAction('ReportsDataControlServlet','jsp/pages/erp/reports/transactionalReports/purchasesr.jsp','7001')"><i class="fa fa-circle-o"></i> PURCHASE REPORT</a></li>
                		<li><a href="#slsr" onclick="doAction('ReportsDataControlServlet','jsp/pages/erp/reports/transactionalReports/salesr.jsp','7011')"><i class="fa fa-circle-o"></i> SALES REPORT</a></li>
                		<li><a href="#ndrtr" onclick="doAction('ReportsDataControlServlet','jsp/pages/erp/reports/transactionalReports/ncdbcrctvr.jsp','7051')"><i class="fa fa-circle-o"></i> NC / DBC / RC / TV REPORT</a></li>                		
                		<li><a href="#stkr" onclick="doAction('ReportsDataControlServlet','jsp/pages/erp/reports/transactionalReports/stockr.jsp','7021')"><i class="fa fa-circle-o"></i> STOCK REPORT</a></li>
                		<li><a href="#gstr" onclick="doAction('ReportsDataControlServlet','jsp/pages/erp/reports/transactionalReports/gstr.jsp','7031')"><i class="fa fa-circle-o"></i> GST REPORT</a></li>
                		<li><a href="#bbr" onclick="doAction('ReportsDataControlServlet','jsp/pages/erp/reports/transactionalReports/bankbookr.jsp','7041')"><i class="fa fa-circle-o"></i> BANK BOOK</a></li>
                		<li><a href="#ldgr" onclick="doAction('ReportsDataControlServlet','jsp/pages/erp/reports/transactionalReports/ledger.jsp','7201')"><i class="fa fa-circle-o"></i> LEDGER</a></li>
                		<li><a href="#rbls" onclick="doAction('ReportsDataControlServlet','jsp/pages/erp/reports/transactionalReports/receivablesr.jsp','7351')"><i class="fa fa-circle-o"></i> RECEIVABLES</a></li>
                		<li><a href="#pbls" onclick="doAction('ReportsDataControlServlet','jsp/pages/erp/reports/transactionalReports/payablesr.jsp','7361')"><i class="fa fa-circle-o"></i> PAYABLES</a></li>
                		<li><a href="#profa" onclick="doAction('ReportsDataControlServlet','jsp/pages/erp/reports/transactionalReports/par.jsp','7061')"><i class="fa fa-circle-o"></i> PROFITABILITY ANALYSIS </a></li>
					</ul>
            	</li>
		        <li class="treeview mcls"><a href="#fnrprts"><i class="fa fa-file-text-o"></i><span>FINANCIAL REPORTS</span><i class="fa fa-angle-right"></i></a>
        			<ul class="treeview-menu">
              			<li class="treeview"><a href="#capa"><i class="fa fa-sticky-note-o"></i><span>CAPITAL ACCOUNT</span><i class="fa fa-angle-right"></i></a>
                  			<ul class="treeview-menu">
                    			<li><a href="#pi" onclick="doAction('ReportsDataControlServlet','jsp/pages/erp/reports/finreports/capitalAcct/partners_info.jsp','8501')"><i class="fa fa-circle-o"></i> PARTNERS DATA </a></li>
                    			<li><a href="#ptrns" onclick="doAction('ReportsDataControlServlet','jsp/pages/erp/reports/finreports/capitalAcct/partners_trans.jsp','8511')"><i class="fa fa-circle-o"></i> PARTNER TRANSACTIONS </a></li>
       		  					<li><a href="#cas" onclick="doAction('ReportsDataControlServlet','jsp/pages/erp/reports/finreports/capitalAcct/captlacs.jsp','8521')"><i class="fa fa-circle-o"></i> CAPITAL ACC SEARCH </a></li>               
       		  					<li><a href="#car" onclick="doAction('ReportsDataControlServlet','jsp/pages/erp/reports/finreports/capitalAcct/captlrpt.jsp','8531')"><i class="fa fa-circle-o"></i> CAPITAL ACC REPORT </a></li>               
                  			</ul>
                		</li>
              			<li class="treeview"><a href="#da"><i class="fa fa-sticky-note-o"></i><span>DEPRECIATION ACCOUNT</span><i class="fa fa-angle-right"></i></a>
                  			<ul class="treeview-menu">
                    			<li><a href="#am" onclick="doAction('ReportsDataControlServlet','jsp/pages/erp/reports/finreports/depAcct/assets_management.jsp','5561')"><i class="fa fa-circle-o"></i> ASSETS MANAGEMENT </a></li>
                    			<li><a href="#dr" onclick="doAction('ReportsDataControlServlet','jsp/pages/erp/reports/finreports/depAcct/depreciationr.jsp','5566')"><i class="fa fa-circle-o"></i> DEPRECIATION REPORT </a></li>               
                  			</ul>
                		</li>
                		<li><a href="#pla" onclick="doAction('ReportsDataControlServlet','jsp/pages/erp/reports/finreports/depAcct/palap.jsp','8541')"><i class="fa fa-sticky-note-o"></i> PROFIT AND LOSS ACCOUNT </a></li>               
       		  			<li><a href="#bs" onclick="doAction('ReportsDataControlServlet','jsp/pages/erp/reports/finreports/depAcct/bs.jsp','8551')"><i class="fa fa-sticky-note-o"></i> BALANCE SHEET </a></li>
					</ul>
				</li>
            	<li class="treeview mcls"><a href="#dnd" onclick="doAction('ReportsDataControlServlet','jsp/pages/erp/dayend.jsp','8001')"><i class="fa fa-dashboard"></i><span>DAY END</span></a>
            	
<!--             	<li class="treeview"><a href="#"><i class="fa fa-dashboard"></i><span>DASHBOARD</span></a> -->
          	</ul>
		</form>
	</section>
</aside>

<script type="text/javascript" src="js/commons/sckeys.js?<%=randomUUIDString%>"></script>

</html>