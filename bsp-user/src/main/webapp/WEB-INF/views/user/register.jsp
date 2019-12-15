<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>REGISTER-图书共享平台</title>
</head>
<body>
	<form action="">
		邮箱账号：<input id="email" type="text" name="email" placeholder="输入邮箱账号" onchange="javascript:checkMail();" /> <br /> <br /> 
		获取验证码：<input id="vcode" type="text" name="vcode" style="width: 50px;" placeholder="验证码" />
		<img id="vCode" src="${pageContext.request.contextPath}/getVerifyCode.do" border="1" onclick="javascript:_change();" />
		<button id="send" type="button" onclick="sendMail();">发送</button>
		<br /> <br />
		 邮箱验证码：<input id="mailVcode" type="text" name="mailVcode" style="width: 100px;" placeholder="输入邮箱验证码" /> <br />
		<br />
		<button id="next" type="button" onclick="nextStep();">下一步</button>
	</form>
	
	<script type="text/javascript" src='../../../js/jquery-1.12.4.js'></script>
	<script type="text/javascript">	
		/*
		 如果一个表单项的name和<img>的id相同，那么可能会出问题！一般只有IE出问题！
		 */
		function _change() {
			/*
			1. 获取<img>元素
			 */
			var ele = document.getElementById("vCode");
			ele.src = "${pageContext.request.contextPath}/getVerifyCode.do?xxx=" + new Date().getTime();
		}
		function checkMail() {
			//要用new 的
			var mail = $('#email').val();
			var e = new RegExp("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$");
			if (!e.test(mail)) {
				alert("邮箱格式错误");
				$('#mail').focus();
				return false;
			}
			$.post("${pageContext.request.contextPath}/user/checkMail.do", {
				"email" : $('#email').val()
			}, function(data) {
				alert(data.msg);
				if(!data.success) {
					$("#vcode").focus();
				}
			}, "json");
		}
		
		function sendMail() {
			/*  var mail =  document.getElementById('email').value; */
			var mail = $('#email').val();
			//要用new 的
			var e = new RegExp("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$");
			if (!e.test(mail)) {
				alert("邮箱格式错误");
				$('#mail').focus();
				return false;
			}  else {
				$.ajax({
					url : "${pageContext.request.contextPath}/user/sendMailCode.do",
					type : "post",
					data : {
						email : $("#email").val(),
						vcode : $("#vcode").val(),
					},
					dataType : "json",
					complete : function() {
	
					},
					success : function(data) {
						alert(data.msg);
						if (!data.success) {
							$("#vcode").val("");
							$("#vcode").focus();
							_change();
						}
					},
					error : function() {
					}
				});
			}
		}
		function nextStep() {
			var email = $("#email").val();
			var mailVcode = $("#mailVcode").val();
			if (email == "") {
				alert("请输入邮箱号码");
			}else if(mailVcode == ""){
				alert("请输入邮箱验证码");
				$("#mailVcode").val();
				$("#mailVcode").focus();
			}else {
				$.ajax({
					url : "${pageContext.request.contextPath}/user/checkMailCode.do",
					type : "post",
					data : {
						email : email,
						mailVcode : mailVcode,
					},
					dataType : "json",
					complete : function() {
	
					},
					success : function(data) {
						console.log(data);
						alert(data.msg);
						if(data.success) {
							window.location.href = "${pageContext.request.contextPath}/user/completeInfo"
						} else {
							$("#mailVcode").val("");
							$("#vcode").val("");
							$("#vcode").focus();
						}
					},
					error : function() {
					}
				});
			}
		}
	</script>
</body>
</html>