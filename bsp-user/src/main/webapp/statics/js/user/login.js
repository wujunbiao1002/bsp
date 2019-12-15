function _change() {//重新获取验证码
    var ele = document.getElementById("codeImg");
    ele.src = "/verifyCode?xxx=" + new Date().getTime();
}

var doLogin = function () {
    var app=this; // 调用者，即login_app
    var uname=app.username.trim();
    var upassword=app.password.trim();
    var vcode=app.vcode.trim();
    if (!uname){
        app.msg='<span class="pull-left text-danger">请填写用户名</span>';
        return;
    } else if (!upassword){
        app.msg='<span class="pull-left text-danger">请填写密码</span>';
        return;
    } else if(!vcode){
        app.msg='<span class="pull-left text-danger">请填写验证码</span>';
        return;
    } else {
        app.msg='';
    }
    $.ajax({
        url: '/login',
        type: 'post',
        data: {
            username: uname,
            password: upassword,
            vcode: vcode
        },
        success: function (data) {
            if (data.code==0) {
                app.msg='<span class="pull-left text-success">登录成功，正在跳转...</span>';
                if (data.url) {//跳转到登录前访问的页面
                    window.location.href=data.url;
                } else {
                    window.location.href='/';
                }
            } else {
                _change();//重新获取验证码
                app.msg='<span class="pull-left text-danger">'+data.msg+'</span>';
            }
        }
    });
}

var login_app = new  Vue({
    el: '#login-page',
    data: {
        username: '',
        password: '',
        vcode: '',// 验证码
        msg: '', //提示信息
        email: ''
    },
    methods: {
        login: doLogin
    }
})