<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>LOGIN-图书共享平台</title>
</head>
<body>
	<form action="">
		账号：<input type="text" name="mail" id="mail" placeholder="邮箱账号" /><br />
		<br/> 密码：<input type="password" name="password" id="pwd" placeholder="密码" /><br /><br /> 
		验证码：<input type="text"
			name="vcode" id="vcode" placeholder="验证码" style="width: 50px;" /> <img
			id="vCode" src="${pageContext.request.contextPath}/getVerifyCode.do"
			border="1" onclick="javascript:_change();" /><br /> <br />
	</form>
	<button id="login" type="button" onclick="javascript:login();">发送</button>
</body>
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
	function login() {
		var mail = $("#mail").val();
		var password = $("#pwd").val();
		var vcode = $("#vcode").val();
		//要用new 的
		var e = new RegExp("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$");
		if (!e.test(mail)) {
			alert("邮箱格式错误！！！");
			$('#mail').focus();
			return false;
		} 
 		if (mail == "") {
			alert("账号不能为空");
			return false;
		} 
		if (password == "") {
			alert("密码不能为空");
			return false;
		}
		if (password.length<8||password.length>20) {
			alert("密码长度错误，正确为8-20位")
			$("#pwd").val("");
			return false;
		}1
 		if (vcode == "") {
			alert("验证码不能为空");
			return false;
		} 
		$.ajax({
			url : "/user/login.do",
			type : "post",
			data : {
				mail : mail,
				password:password,
				vcode : vcode,
			},
			dataType : "json",
			complete : function() {
			},
			success : function(data) {
				alert(data.msg);
				if (!data.success) {//验证码错误
					_change();
				} 
			},
			error : function() {
			}
		});
	}
</script>
</html>