$(function () {
    $('#register-form').bootstrapValidator({
        message: '无效输入',
        //excluded:[":hidden",":disabled",":not(visible)"] ,//bootstrapValidator的默认配置
        excluded: ':disabled',//关键配置，表示只对于禁用域不进行验证，其他的表单元素都要验证
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',//显示验证成功或者失败时的一个小图标
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            pwd: {
                message: '密码不能为空',//默认提示信息
                validators: {
                    notEmpty: {
                        message: '密码不能为空'
                    },
                    stringLength: {
                        min: 6,
                        max: 20,
                        message: '密码长度不能小于6位或超过20位'
                    },
                    regexp: {
                        regexp: /^[a-zA-Z0-9_\.]+$/,
                        message: '密码只能由字母、数字、点和下划线组成。'
                    }

                }
            },
            cpwd: {
                message: '两次输入的密码不相符',//默认提示信息
                validators: {
                    identical: {
                        field: 'pwd',
                        message: '两次输入的密码不相符'
                    },
                    notEmpty: {
                        message: '确认密码密码不能为空'
                    },
                }
            }
        }
    });
})
function _change() {//重新获取验证码
    var ele = document.getElementById("codeImg");
    ele.src = "/verifyCode?xxx=" + new Date().getTime();
}

var disableSecondsInterval = function (app){
    app.step1.seconds = 60;
    var set=setInterval(function(){
        if (app.step1.seconds==0) {
            return;
        }
        app.step1.seconds--;
    }, 1000);
}

var doCheckMail = function () {
    var app=this;
    var email = app.step1.email.trim();
    var e = new RegExp("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$");
    if (!email) {
        app.step1.msg= '';
        return;
    };
    if (!e.test(email)) {
        app.step1.msg= '<span class="text-danger">邮箱格式错误</span>';
        $('#inputEmail').focus();
        return;
    }
    $.ajax({
        url: '/sign_up/checkEmail',
        data: {
            email: email
        },
        success: function (data){
            if (data.available) {
                app.step1.msg= '';
            } else {
                app.step1.msg='<span class="pull-left text-danger">'+data.msg+'</span>';
            }
        }
    })
}
var doSendMail = function () {
    var app=this;
    var vcode=app.step1.vcode.trim();
    var email=app.step1.email.trim();
    var e = new RegExp("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$");
    if(!vcode){
        app.step1.msg='<span class="text-danger">请填写验证码</span>';
        return;
    }
    if (!email) {
        app.step1.msg= '<span class="text-danger">请填写邮箱</span>';
        return;
    }
    if (!e.test(email)) {
        app.step1.msg= '<span class="text-danger">邮箱格式错误</span>';
        $('#inputEmail').focus();
        return;
    }
    app.step1.msg='<span class="pull-left text-success">正在发送验证码......</span>';
    app.step1.seconds=-1;
    $.ajax({
        url: '/sign_up/sendEmailCode',
        data: {
            email: email,
            vcode:app.step1.vcode
        },
        success: function (data){
            if (data.code==0) {
                app.step1.msg= '<span class="pull-left text-success">验证码发送成功，请登录邮箱查看</span>';
                disableSecondsInterval(app);
                app.step1.nextStep=false;
            } else {
                _change();//重新获取验证码
                app.step1.msg='<span class="text-danger">'+data.msg+'</span>';
                app.step1.seconds=0;
            }
        }
    })
}

var doCheckMailCode= function () {
    var app=this;
    var email=app.step1.email.trim();
    var mailVcode=app.step1.mailVcode.trim();
    if (!email) {
        app.step1.msg= '<span class="text-danger">请填写邮箱</span>';
        return;
    };
    if(!mailVcode){
        app.step1.msg='<span class="text-danger">请填写邮箱验证码</span>';
        return;
    }
    $.ajax({
        url: '/sign_up/checkMailCode',
        data: {
            email: email,
            mailVcode: mailVcode
        },
        success: function (data){
            if (data.code==0) {
                app.step1.msg='<span class="pull-left text-success">验证成功</span>';
                app.step=2;
            } else {
                app.step1.msg='<span class="text-danger">'+data.msg+'</span>';
            }
        }
    })
}

var doRegister = function () {
    var app=this;
    if (!$('#register-form').data('bootstrapValidator').isValid()) {// 判断是否验证通过
        return;
    }
    $.ajax({
        url: '/sign_up/register',
        data: app.step2.params,
        success: function (data){
            if (data.code==0) {
                app.step2.msg='<span class="pull-left text-success">注册成功</span>';
                app.step=3;
            } else {
                _change();//重新获取验证码
                app.step1.msg='<span class="text-danger">'+data.msg+'</span>';

            }
        }
    })

}

var register_app = new Vue({
    el:'#register-app',
    data: {
        step: 1,// 注册步骤，1-验证邮箱，2-填写个人资料,3-注册成功
        step1: {// 步骤1
            seconds: 0, // 获取验证码按钮冻结时间，！=0时冻结
            nextStep: true,// 是否可以点击下一步
            vcode: '', //验证码
            email: '', // 邮箱
            mailVcode: '', // 邮箱验证码
            msg: '' // <span class="text-success"></span> <span class="text-danger"></span>
        },
        step2: {// 步骤2
            params: {
                password1: '',
                password2: '',
                uNickname: '', // 用户的昵称
                uSex: '', // 用户性别
                uPhone: '', // 用户联系手机号码
                uAddress: '', // 用户联系地址
                uQq: '', // 用户QQ号
                uWechat: '', // 用户微信号
                uSignature: '', // 用户个性签名
            },
            msg: ''
        }
    },
    methods: {
        checkMail: doCheckMail,
        sendMail: doSendMail,
        checkMailCode: doCheckMailCode,
        register: doRegister
    }
})