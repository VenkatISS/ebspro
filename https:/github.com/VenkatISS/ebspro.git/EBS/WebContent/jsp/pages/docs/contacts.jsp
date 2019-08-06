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
    <title>LPG DEALER WEB APPLICATION - CONTACTS</title>
	<jsp:useBean id="agencyVO" scope="session" class="com.it.erpapp.framework.model.vos.AgencyVO"></jsp:useBean>
	<jsp:useBean id="service_types_list" scope="request" class="java.lang.String"></jsp:useBean>
	<jsp:useBean id="services_data" scope="request" class="java.lang.String"></jsp:useBean>
	<script src="js/commons/jquery-2.1.4.min.js?<%=randomUUIDString%>"></script>
	<script src="js/commons/checkBrowser.js?<%=randomUUIDString%>"></script>
	<script type="text/javascript"> 
		window.onload = setWidthHightNav("100%");
	</script>
  </head>
  <body class="sidebar-mini fixed">
    	<div class="wrapper">
    <!-- Header-->
		<jsp:include page="/jsp/pages/commons/header.jsp"/>      
	  	<!--header End--->
	<!-- Sidenav -->
		<jsp:include page="/jsp/pages/commons/sidenav.jsp"/>    
	  <!---END Sidenav--->
      		<div class="content-wrapper">
        		<div class="page-title">
          			<div>
            			<center><h1>CONTACT US </h1></center>            			
          			</div>
        		</div>
				<div class="row">
          			<div class="clearfix"></div>
          			<div class="col-md-12">
          			<center>
	          			<i style="font-size: 17px;">Email us with any questions or inquires or call us. We would be<br>happy to answer your questions and set up a meeting with you. <br>Our app can help set you apart from the flock!</i>
          			</center>
          			<br><br><br>
          			<p style="font-size: 18px;">Contact Details:</p>
          			<p><b>EmailId </b> <span style="padding:0px 35px 35px 35px;font-size: 16px;">: &nbsp&nbsp support@mylpgbooks.com </span></p>
					<p><b>Landline No </b> <span style="padding:5px;font-size: 16px;">: &nbsp&nbsp 040 23546767 </span></p>
					<p><b>Address </b> <span style="padding:0px 30px 30px 30px;font-size: 16px;">: &nbsp&nbsp Road No.41, Jubilee Hills, H.No.8-2-293/82/A,Plot No:1091, Hyderabad, Telangana 500033 </span> </p>
          			</div>
        		</div>
      		</div>
    	</div>
        <script src="js/commons/bootstrap.min.js?<%=randomUUIDString%>"></script>
		<script src="js/commons/plugins/pace.min.js?<%=randomUUIDString%>"></script>
        <script src="js/commons/main.js?<%=randomUUIDString%>"></script>
    	<script>
			document.getElementById("nameSpan").innerHTML = <%= agencyVO.getAgency_code() %>
		</script>
 	</body>
</html>