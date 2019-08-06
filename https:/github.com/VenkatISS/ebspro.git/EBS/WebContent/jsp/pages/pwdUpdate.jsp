<%@ page import="java.util.UUID, com.it.erpapp.utils.RandomAlphaNumericStringGenerator,
	com.it.erpapp.response.MessageObject"%> 
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
		<!-- For Password Masking-START --> 
		<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
		<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
		<script type="text/javascript" src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-show-password/1.0.3/bootstrap-show-password.min.js"></script>
		<!--Password Masking-END -->
		<title>MyLPGBooks DEALER ENTERPRISE SOLUTIONS WEB APPLICATION - DEALER LOGIN</title>
		<jsp:useBean id="showDIV" scope="request" class="java.lang.String"></jsp:useBean>
		<script src="js/commons/general_validations.js?<%=randomUUIDString%>"></script>
		<script type="text/javascript" src="js/global.js?<%=randomUUIDString%>"></script>
	  	<script src="js/commons/checkBrowser.js?<%=randomUUIDString%>"></script>
	 	<script src="js/commons/sidenav.js?<%=randomUUIDString%>"></script>
	  		  	
 		<script type="text/javascript">
			window.onload = checkBrowser();
	 		window.onbeforeunload = winClose();
		</script>
	  	<script src="js/commons/jquery-2.1.4.min.js?<%=randomUUIDString%>"></script>
		
  	</head>
   	<body class="bg_white">
    	<section class="login-content">
       		<div class="containr" style="width:100%;">
       			<img class="img-responsive" src="images/image3.jpg" style="width:100%;height:800px;opacity:0.8;">
      		</div>
      		<div class="contbox">
      		<div class="logo" style="margin-top:-10%;">
        		<h1><img src="images/logo.png" alt="" title="" width="100"/><br/>
					LPG DEALER PORTAL
				</h1>
      		</div>
 			<div style="text-align:center;">
 				<%
					MessageObject msgObj = (MessageObject) request.getAttribute("msg_obj");
					if ((null != msgObj)) {
						if ((msgObj.getMessageId() == 1001) || (msgObj.getMessageId() == 1000) || (msgObj.getMessageId() == 1002)) {
				%>
				<font color="red"><%=msgObj.getMessageText()%></font>
				<%
						}
					}
				%>
				<%
					if ((showDIV != null) && showDIV.equalsIgnoreCase("successDiv")) {
				%>
				<div id="successDiv" align="center">
					<font color="red" face="tahoma" size="2"> AGENCY REGISTRATIONSUCCUSSFUL. <BR>PLEASE CHECK YOUR REGISTERED EMAIL FOR ACTIVATION LINK</font>
				</div>
				<%
					} else {
				%>
			</div>
		  <div id="updateDiv" class="login-box">
			<div class="col-md-4 col-sm-4 col-xs-4 update-box" id="loginDiv" style="margin-top:1%;">
		  		<form name=update_pwd_form id="update_pwd_form" class="login-form" action="<%=request.getContextPath() %>/login" method="post" style="background-color:#F3FDFF;">
	      			<input type="hidden" name="actionId" value="1006">
					<input type="hidden" name="page" value="app">
	        		<h3 class="login-head"><img src="images/login_icon.png" alt="" title=""/> DEALER PASSWORD UPDATE</h3>
        			<div class="form-group">
                        <label class="control-label">CHOOSE NEW PASSWORD</label>
            			<input class="form-control" type="password" name="fnpwd" id="fnpwd" placeholder="New Password" data-toggle="password">  
            			        			</div>
          			<div class="form-group">
                    	<label class="control-label">CONFIRM NEW PASSWORD</label>
            			<input class="form-control" type="password" name="fcpwd" id="fcpwd" placeholder="Confirm Password" data-toggle="password">      
            		</div>
          			<div class="form-group btn-container">
            			<input type="button" value="SUBMIT" class="btn btn-success m-r-15" onclick="submitUpdatePasswordForm(this.form)">
          			</div>
          			<div class="form-group btn-container">
          			</div>
        		</form>
        	</div>
        		
        	<div style="margin-top:260px;float:right;">
        		<form action="" name="backToLoginform" id="backToLoginform">
        			<input type="hidden" name="actionId" value="1001">
					<input type="hidden" name="page" value="app">
<!--         	    	<a href="#eqpd" onclick="doAction('ControlServlet','jsp/pages/app.jsp','1001')">Back to Login</a>
 -->
      	       		<a href="#eqpd" onclick="javascript:doAction('jsp/pages/app.jsp','1001')"> Back to Login</a>
			
	        	</form>
			</div>
		</div>
	</div>
    <%} %>
    </section>
</body>
<script src="js/commons/bootstrap.min.js?<%=randomUUIDString%>"></script>
<script src="js/commons/plugins/pace.min.js?<%=randomUUIDString%>"></script>
<script src="js/commons/main.js?<%=randomUUIDString%>"></script>
<script type="text/javascript">
  //Show Login Form
   /*    function doAction(servlet,page, actionId)
    {
	  alert("hai");
    	href= servlet;	
    	document.getElementById('backToLoginform').action = href;
    	document.getElementById('backToLoginform').page.value = page;   
    	document.getElementById('backToLoginform').actionId.value = actionId;		
    	document.getElementById('backToLoginform').submit();
    } */
    	function doAction(page, actionId) {
 	 		href ="login";
 	 		document.getElementById('backToLoginform').action = href;
 	 		document.getElementById('backToLoginform').page.value = page;
 	 		document.getElementById('backToLoginform').actionId.value = actionId;
 	 		document.getElementById('backToLoginform').submit();
 	 	}
      	function showLoginForm(){
      		document.getElementById("loginDiv").style.display="block";
      		//document.getElementById("successDiv").style.display="none";
      	}
      	function submitUpdatePasswordForm(formobj){
			var errMsg = "";
    		var fnpwd = formobj.fnpwd.value.trim();
    		var fcpwd = formobj.fcpwd.value.trim();
    		if(fnpwd.length<0)
    			errMsg = errMsg+"Please Enter  Password.\n";
    		else if((fnpwd.length<8)||(fnpwd.length>12)){
    			errMsg = errMsg+"Please Enter Proper Password. 8-12 Characters.\n";
    		}else if(!fcpwd.length>0){
    			errMsg = errMsg+"Please Enter Confirm Password Same As Password.\n";
    		}else if(!(fnpwd==fcpwd)){
    			errMsg = errMsg+"Please Enter Same Value For PASSWORD and CONFIRM PASSWORD\n";
    		}
    		
    		if ( errMsg.length > 0 ){
    			alert(errMsg);
    			return false;
    		}

    		formobj.submit();
      	}
</script>
<script type="text/javascript">
	$("#pwd").password('toggle');
</script>

</html>