<%@ page import="java.util.UUID, com.it.erpapp.utils.RandomAlphaNumericStringGenerator,
	com.it.erpapp.response.MessageObject"%>
<!DOCTYPE html>
<html style="overflow :hidden;">
<%
	UUID uuid = UUID.randomUUID();
	String randomUUIDString = uuid.toString();
%>

	<head>
    	<meta charset="utf-8">
    	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    	<meta name="viewport" content="width=device-width, initial-scale=1">
    	<!-- CSS-->

    	<link rel="stylesheet" type="text/css" href="css/main.css?v=<%= randomUUIDString%>">
		<title>LPG DEALER WEB APPLICATION - DEALER LOGIN</title>
				
		<style>
			@media screen and (max-width: 1024px) {
				#reg_errmsg {
					font-size : 11px;
				}
			}
			@media screen and (min-width: 1025px) {
				#reg_errmsg {
					font-size : 11.5px;
				}
			}
		</style>
  	</head>
   	<body class="bg_white">
		<section class="login-content">
      		<div class="contbox" id="contbox" >
	      		<div class="logo" style="margin-top:-10%;">
    	    		<h1><img src="images/logo.png" alt="" title="" width="100"/><br/>
						LPG DEALER ENTERPRISE BUSINESS SOLUTIONS PORTAL
					</h1>
      			</div>
      			
      			<div style="text-align:center;" class="msgcls">
 				<%
					MessageObject msgObj = (MessageObject) request.getAttribute("msg_obj");
					if ((null != msgObj)) {
						if ((msgObj.getMessageId() == 1005)) {
				%>
					<font color="red" id="reg_errmsg"><%=msgObj.getMessageText()%></font>
				<%
						}
					}
				%>
      			</div>
				<div class="col-md-4 col-sm-4 col-xs-4 login-box" id="loginDiv" style="margin-top:1%;">
        			<form class="login-form" name="forget-form" id="forget-form" action="<%=request.getContextPath() %>/login" method="post" onsubmit="return validateEmailForForgotPwd(this)">
	        			<input type="hidden" name="actionId" value="1005">
						<input type="hidden" name="page" value="app">
          				<h3 class="login-head"><i class="fa fa-lg fa-fw fa-lock"></i>Forgot Password ?</h3>
          				<p style="font-size:14px; text-align:center; ">Enter Your Registered Email Address And We Will Send <br>You The Link To Reset Your Password</p>
          				<div class="form-group">
            				<label class="control-label">EMAIL</label>
            				<input class="form-control" type="text" name="femailId" id="femailId" placeholder="Email">
          				</div>
          				<div class="form-group btn-container">
          					<input type="submit" value="RESET PASSWORD" class="btn btn-info">
		          		</div>
          				<div class="form-group mt-20">
	          				<p class="semibold-text mb-0"><a href="<%=request.getContextPath() %>/login" style="font-size:14px;"><i class="fa fa-angle-left fa-fw"></i> Back to Login</a></p>
          				</div>
        			</form>
      			</div>
	        </div>
		</section>
	</body>
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>	
 	<script>
		//Validate UPDATE PASSWORD Form and submit
		function validateEmailForForgotPwd(formobj){
			var errMsg = "";
			var femailId = formobj.femailId.value.trim();

			if(!femailId.length>0)
				errMsg = errMsg+"EMAIL ID Can't be empty.\n";
			else {
				if(!checkEmail(femailId))
					errMsg = errMsg+"Please Enter Proper Email.\n";
			}

			if ( errMsg.length > 0 ){
				alert(errMsg);
				return false;
			}
		}
		
		function checkEmail(emailV) {
			var regemail=/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
			if (regemail.test(emailV)){
			return true;
			}
			return false;
		}
		
		$('.msgcls').click(function(e) { //button click class name is msgcls
			e.stopPropagation();
		});
		$(function(){
			$(document).click(function(){  
				$('.msgcls').hide();
			});
			$('.forgotpwd').click(function(){  
				$('.msgcls').hide();
			});
		});
		
	</script>

</html>