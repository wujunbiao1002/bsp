<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>REGISTER-图书共享平台</title>
</head>
<body>
	<form action="">
		昵称：<input id="uNickname" type="text" name="uNickname"><br />
		密码：<input id="password1" type="password" name="password1"><br />
		确认密码：<input id="password2" type="password" name="password2"><br />
		性别： <input type="radio" name="sex" value="男" /> 男 <input type="radio"
			name="sex" value="女" /> 女<br /> 手机号码：<input id="uPhone" type="text"
			name="uPhone" /><br />
	</form>
	<button id="submit" type="button" onclick="register();">完成注册</button>
</body>
<script type="text/javascript" src='../../../js/jquery-1.12.4.js'></script>
<script type="text/javascript">
	function register() {
		var password1 = $("#password1").val();
		var password2 = $("#password2").val();
		if (password1 != password2) {
			alert("两次密码不一致");
			$("#password1").val("");
			$("#password2").val("");
			$("#password1").focus();
			return false;
		}
		var uPhone = $("#uPhone").val();
		var reg = /^1[345789]\d{9}$/;
		if (!reg.test(uPhone)) {
			alert("手机格式错误！！！");
			$('#uPhone').focus();
			return false;
		} 
		var uNickname = $("#uNickname").val();
		var uSex = $('input:radio:checked').val();
			$.ajax({
				url : "/user/register.do",
				type : "post",
				data : {
					uNickname :uNickname,
					uSex :uSex,
					uPhone:uPhone,
					password1:password1,
					password2:password2,
				},
				dataType : "json",
				success : function(data) {
					alert(data.msg);
				},
			});
	}
</script>
</html>