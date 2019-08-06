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
    <title>LPG DEALER WEB APPLICATION - DOWNLOADS</title>
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
            			<h1>DOWNLOADS </h1>
          			</div>
        		</div>
				<div class="row">
          			<div class="clearfix"></div>
          			<div class="col-md-12">
          			<center>
          			<i>Here you can download all the files that you need.</i> 
          			</center>
<br><br>          			
          			
						<h5>USER MANUAL</h5>
						<a style="font-size: 15px;" href="files/User Manual.pdf" download>User Manual: version 1.0</a>
                        <hr><br>
			          	
			          	<h5>PRIVACY POLICY</h5>
	                    <a style="font-size: 15px;" href="files/Privacy Policy MyLPGBooks.pdf" download>Privacy Policy: version 1.0</a>
                        <hr><br>
                        
                        <h5>TERMS OF SERVICES</h5>
						<a style="font-size: 15px;" href="files/Terms of Service MyLPGBooks.pdf" download>Terms Of Services: version 1.0</a>
                        <hr><br>
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






<%-- <!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- CSS-->
    <link rel="stylesheet" type="text/css" href="css/main.css">
    <title>LPG DEALER WEB APPLICATION - DOWNLOADS</title>
	<jsp:useBean id="agencyVO" scope="session" class="com.it.erpapp.framework.model.vos.AgencyVO"></jsp:useBean>
	<jsp:useBean id="service_types_list" scope="request" class="java.lang.String"></jsp:useBean>
	<jsp:useBean id="services_data" scope="request" class="java.lang.String"></jsp:useBean>
	<script src="js/commons/jquery-2.1.4.min.js"></script>
	<script src="js/commons/checkBrowser.js"></script>
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
            			<h1>DOWNLOADS </h1>
          			</div>
        		</div>
				<div class="row">
          			<div class="clearfix"></div>
          			<div class="col-md-12">
<!-- 						<h5 onclick="displaytext('usrmnl')"><span>USER MANUAL</span><i class="fa fa-angle-up" style="float:right;"></i></h5> -->
						<h5 onclick="displaytext('usrmnl')"><span>USER MANUAL</span><i class="fa fa-angle-up" style="float:right;"></i></h5>
                        <div class="usermanual" id="usrmnl" style="display:none;"><br>
                        	wsmdkejsdf fwsjlekr wclkrfwjlfmcrlwer lruweilrjxekjrewrj,w rowizerjomwrowr oiwjr wkejrmxwrjm wjeroxeoiwe oeiurweoiruowieruow ewioauerwniriow owiuearonweri xoruoiwc unriowru
                        	wjdxweruowirmiw cw noiwuxroiwro oiueroiwuoer,kzxutev owiudtiuwoemkxdjaseynv owiutsxpromrn8wo jerhwiux rxoiuw3wkjduchoq hcrwx wuhruio2jeiosukrj9yot78ywihrtx3yt b i34tr2ijero
                        	xnhr783yr9znm werhuiyxiowhruiwt3rwkr5yd7834r59q23x hiey sdjtnhwerouiow3h4tr8uhqnr htud3eyr9tqx3h r38ury37trq38ue ryx82y825273x 763t45r8x2y 787264eyu928347b 239876xq38mr0q23
                        	9urq983w7r9iqwjweriuxriw3 rw3uiuw3uro2iq3ruq iruoywojfka awurhiwuhrfkawjrguq23yerio wiuhruhawkljefnawebiryaw9 awtyr8awuy fuawy89rawu 98wytoawijer3ioy6589 n8qw3t5pwoiuoa 3y5
                        	689w3put w3puiytuw83ou59q3dmu5375c yunv5i3487v5c3v.

                        	<br><br><a style="font-size: 15px;" href="jsp/pages/docs/userManual.jsp" target="_blank">Read More...</a>
                        </div><br>
                        <hr><br>
			          	
			          	
			          	<h5 onclick="displaytext('pripolicy')"><span>PRIVACY POLICY</span><i class="fa fa-angle-up" style="float:right;"></i></h5>
                        <div class="privacypolicy" id="pripolicy" style="display:none;"><br>
                        	wsmdkejsdf fwsjlekr wclkrfwjlfmcrlwer lruweilrjxekjrewrj,w rowizerjomwrowr oiwjr wkejrmxwrjm wjeroxeoiwe oeiurweoiruowieruow ewioauerwniriow owiuearonweri xoruoiwc unriowru
                        	wjdxweruowirmiw cw noiwuxroiwro oiueroiwuoer,kzxutev owiudtiuwoemkxdjaseynv owiutsxpromrn8wo jerhwiux rxoiuw3wkjduchoq hcrwx wuhruio2jeiosukrj9yot78ywihrtx3yt b i34tr2ijero
                        	xnhr783yr9znm werhuiyxiowhruiwt3rwkr5yd7834r59q23x hiey sdjtnhwerouiow3h4tr8uhqnr htud3eyr9tqx3h r38ury37trq38ue ryx82y825273x 763t45r8x2y 787264eyu928347b 239876xq38mr0q23
                        	9urq983w7r9iqwjweriuxriw3 rw3uiuw3uro2iq3ruq iruoywojfka awurhiwuhrfkawjrguq23yerio wiuhruhawkljefnawebiryaw9 awtyr8awuy fuawy89rawu 98wytoawijer3ioy6589 n8qw3t5pwoiuoa 3y5
                        	689w3put w3puiytuw83ou59q3dmu5375c yunv5i3487v5c3v. 

                        	<br><br><a style="font-size: 15px;" href="jsp/pages/docs/privacyPolicy.jsp" target="_blank">Read More...</a>
                        </div><br>
                        <hr><br>
          			</div>
        		</div>
      		</div>
    	</div>
        <script src="js/commons/bootstrap.min.js"></script>
		<script src="js/commons/plugins/pace.min.js"></script>
        <script src="js/commons/main.js"></script>
    	<script>
			document.getElementById("nameSpan").innerHTML = <%= agencyVO.getAgency_code() %>
			
			function displaytext(text){
				var x = document.getElementById(text);
				if (x.style.display === "none") {
					x.style.display = "block";					
				} else {
					x.style.display = "none";
				}
				
				
				$('h5').click(function() {
					  var $el = $(this);
					  $('.' + text).toggle('slow', function() {
					    if ($el.html() == '<i class="fa fa-angle-up"></i>') { 
					      $el.html('<i class="fa fa-angle-down"></i>'); 
					    } 
					    else { 
					      $el.html('<i class="fa fa-angle-up"></i>'); 
					    } 
					  });
					});
			}
		</script>
 	</body>
</html> --%>