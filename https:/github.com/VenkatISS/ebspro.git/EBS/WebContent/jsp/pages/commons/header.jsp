<%@ page import="java.util.UUID" %>
<!DOCTYPE html>
<html>
<%
	UUID uuid = UUID.randomUUID();
	String randomUUIDString = uuid.toString();
%>

	<script type="text/javascript">
		window.onload = setWidthHightNav("100%");
	</script>

	<style>
		a:focus ,a:hover {
			background-color: #9c9fa0;
		}
		span:focus{
			background-color: #fb0b37;
		}
		.namespacedropdown:focus, .namespacedropdown:hover{
			background-color: #fff;
    		color: #a47da7;
		}
		.top-nav:hover .dropdown-menu {display: block;}
	</style>

	<header class="main-header hidden-print" style="background-color:#f2ecec;">
	    <jsp:useBean id="agencyVO" scope="session" class="com.it.erpapp.framework.model.vos.AgencyVO"></jsp:useBean>

		<a class="logo hdlogo headerlogo" href="#hom" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/home.jsp','2001')"><img src="images/mylpgbooks.png" alt="" title="" style="width: 65px;margin-top:0px;" /></a>
<!-- 		<a class="logo hdlogo headerlogo" href="#hom" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/home.jsp','2001')"><img src="images/logo.png" alt="" title="" width="40" /> iLPG</a> -->
<!-- 		<a class="logo hdlogo headerlogo" href="#hom" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/home.jsp','2001')"><img src="https://wearberryworld.com/wp-content/uploads/2017/11/Wearberry-white-logo-.png" alt="" title="" style="width: 130px;margin-top: -10px;" /></a> -->
<!-- 		<a class="logo hdlogo headerlogo" href="#hom" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/home.jsp','2001')"><img src="images/logo.jpg" alt="" title="" style="width: 130px;margin-top: -10px;" /></a> -->
<!--  		<a class="logo hdlogo headerlogo" href="#hom" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/home.jsp','2001')"><img src="images/WbLogo.png" alt="" title="" style="width: 130px;margin-top: -10px;" /></a> -->
		<nav class="navbar navbar-static-top">
    		<!-- Sidebar toggle button start-->
     		<a class="sidebar-toggle stoggle headerstoggle" href="#" data-toggle="offcanvas" onclick="checkToggle()" ></a>
    		<!-- Sidebar toggle button end-->
	    	<a class="sidebar-toggle1 hdcpur headermenu headercp" style="font-size: 15px;" href="#cylpur" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/transactions/purchases/c_purchases.jsp','5101')" title="CYLINDER PURCHASES">
	    		CP</a>
			<a class="sidebar-toggle1 hdopur headermenu headerop" style="font-size: 15px;" href="#otrpur" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/transactions/purchases/other_purchases.jsp','5116')" title="OTHER PURCHASES">
	    		OP</a>	    	
			<a class="sidebar-toggle1 hddoms headermenu headerds" style="font-size: 15px;" href="#drs" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/transactions/sales/dom_refill_sales.jsp','5121')" title="DOMESTIC CYLINDER SALES">
				DS</a>
			<a class="sidebar-toggle1 headermenu" style="font-size: 15px;" href="#crs" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/transactions/sales/com_refill_sales.jsp','5126')" title="COMERCIAL CYLINDER SALES">
				CS</a>
			<a class="sidebar-toggle1 headermenu" style="font-size: 15px;" href="#dc" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/transactions/sales/delivery_challan.jsp','5151')" title="DELIVERY CHALLAN">
				DC</a>	
			<a class="sidebar-toggle1 headermenu" style="font-size: 15px;" href="#ncdbc" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/transactions/ncordbc/nc_dbc.jsp','5721')" title="NC/DBC">
				NC</a>
			<a class="sidebar-toggle1 headermenu" style="font-size: 15px;" href="#rectn" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/transactions/ncordbc/rc.jsp','5711')" title="ITV/RC">
				RC</a>
			<a class="sidebar-toggle1 headermenu" style="font-size: 15px;" href="#tvochr" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/transactions/ncordbc/tv.jsp','5701')" title="OTV/TTV/TV">
				TV</a>			
			<a class="sidebar-toggle1 headermenu" style="font-size: 15px;" href="#rcpts" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/cash&bank/receipts.jsp','5501')" title="RECEIPTS">
				RT</a>
			<a class="sidebar-toggle1 headermenu" style="font-size: 15px;" href="#btrans" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/cash&bank/payments.jsp','5511')" title="PAYMENTS">
				PT</a>
			<a class="sidebar-toggle1 headermenu" style="font-size: 15px;" href="#btrans" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/cash&bank/bank_tranxs.jsp','5521')" title="BANK TRANSACTIONS">
				BT</a>	
			<a class="sidebar-toggle1 headermenu" style="font-size: 15px;" href="#cvo" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/masterdata/cv_data.jsp','3501')" title="CUSTOMER/VENDOR MASTER">
				CVO</a>

			<!-- Navbar Right Menu-->
			<div class="navbar-custom-menu">
        		<div class="top-nav dropdown">
            		<!-- User Menu-->
            		
            		<%
	                    long aid=agencyVO.getAgency_code();
    	                int imgStatus=agencyVO.getImage_status();%>
					<%	if(imgStatus==1){ %>
						<a class="dropdown-toggle" href="#" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false" style="color:#333;">
							<div class="pull-left image" id="profile-container">
								<img  id="profileImage" class="img-circle mt-5" style="width:40px;height:40px;" src="https://www.mylpgbooks.com/profile_pics/<%=aid%>.jpg" alt="User Image">
								<span id="hi" hidden><input id="imageUpload" type="file"  name="profile_photo" placeholder="Photo" required="" capture/></span>
							</div>

							<div class="pull-left info">
								<span style="line-height: 50px;margin-left: 10px;" id="nameSpan"></span> <i class="fa fa-sort-desc" aria-hidden="true"></i>
							</div>
						</a>
						<%}
                        else{%>
							<a class="dropdown-toggle" href="#" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false" style="color:#333;">
							<div class="pull-left image" id="profile-container">
								<img  id="profileImage" class="img-circle mt-5" width="40" src="images/default_profile_img.png" alt="User Image">
								<span id="hi" hidden><input id="imageUpload" type="file"  name="profile_photo" placeholder="Photo" required="" capture/></span>
							</div>

                                    <div class="pull-left info">
                                            <span style="line-height: 50px;margin-left: 10px;" id="nameSpan"></span> <i class="fa fa-sort-desc" aria-hidden="true"></i>
                                    </div>
                            </a>
                            <%} %>
            		<!-- <a class="dropdown-toggle" href="#" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false" style="color:#333;">
            			<div class="pull-left image" id="profile-container">
            				<img  id="profileImage" class="img-circle mt-5" width="40" src="images/default_profile_img.png" alt="User Image">
            				<span id="hi" hidden><input id="imageUpload" type="file"  name="profile_photo" placeholder="Photo" required="" capture/></span>
            			</div>

	            		<div class="pull-left info">
    	        			<span style="line-height: 50px;margin-left: 10px;" id="nameSpan"></span> <i class="fa fa-sort-desc" aria-hidden="true"></i>
        	    		</div>
            		</a> -->
            		
            		
                	<ul class="dropdown-menu settings-menu">
                 		<li><a href="#prof" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/profile.jsp','9001')" class="namespacedropdown"><i class="fa fa-user fa-lg"></i>My Profile</a></li>
                  		<li>
	                  		<form id="logout_form" name="logout_form" action="<%=request.getContextPath() %>/login" method="post">
								<input type="hidden" id="actionId" value="1000">
								<input type="hidden" id="page" value="jsp/pages/app.jsp">
<!--                 		  		<a href="#" onclick="logoutDealer(logout_form)" id="logout" class="namespacedropdown"><i class="fa fa-sign-out fa-lg"></i> Logout</a> -->
                		  		<a href="#" id="logout" class="namespacedropdown"><i class="fa fa-sign-out fa-lg"></i> Logout</a>
                			</form>
                		</li>
                	</ul>
            	</div>
         	</div>
		</nav>
	</header>
	<script>
		$('#logout').click(function() {
			if(confirm("Are you sure You want to Logout? ")) {
				logoutDealer(document.getElementById("logout_form"));
			}
		});
	</script>
</html>
