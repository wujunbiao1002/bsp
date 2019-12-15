function loadUser() {
    $.ajax({
        url: "/token",
        success: function (data) {
            if(data.code==0) {
                user_app.userVo = data.token;
            }else if (data.code==401){
                window.location.href='/login';
            } else {
                alert(data.msg);
            }
        }
    })
}

function updateUserInfo() {
    confirm("确认保存", function () {
        $.ajax({
            url: "/user/update",
            type: 'post',
            dataType : "JSON",
            contentType:"application/json",
            data: JSON.stringify(user_app.userVo),
            success: function (data) {
                if(data.code==0) {
                    layer.msg("保存成功");
                    loadUser();
                }else if (data.code==401){
                    window.location.href='/login';
                } else {
                    alert(data.msg);
                }
            }
        })
    });

}

function checkPwd() {
    var pwd = user_app.pwd;
    if (!pwd.currentPassword.length) {
        layer.msg("请输入原密码", () => {});
        return false;
    }
    if(pwd.newPassword.length < 8 || pwd.newPassword.length > 20) {
        layer.msg("新密码长度为8-20位", () => {});
        return false;
    }
    if(pwd.newPassword != pwd.confirmPassword) {
        layer.msg("两次输入密码不一致", () => {});
        return false;
    }
    return true;
}

function changePwd() {
    var ok = checkPwd();
    if (!ok) {
        return;
    }
    $.ajax({
        url: "/user/password",
        type: "POST",
        data: user_app.pwd,
        success: function (data) {
            if(data.code==0) {
                layer.msg("修改成功");
                user_app.pwd = {
                    currentPassword: '',
                    newPassword: '',
                    confirmPassword: ''
                };
            }else if (data.code==401){
                window.location.href='/login';
            } else {
                layer.msg(data.msg);
            }
        }
    })
}

function initPwdForm() {

}

const user_app = new Vue({
    el: '#user_app',
    data: {
        showPage: 0, // 0-账户资料 1-修改密码
        userVo: {
            mail: '',
            userInfor: {

            }
        },
        pwd: {
            currentPassword: '',
            newPassword: '',
            confirmPassword: ''
        },
        seePwd: false
    },
    methods: {
        switchPage: function (page) {//切换页
            if (page==0){
                this.loadUser();
            } else if (page==1) {
                this.initPwdForm();
            }
            this.showPage = page;
        },
        loadUser: loadUser,
        initPwdForm: initPwdForm,
        updateUserInfo: updateUserInfo,
        changePwd: changePwd
    },
    created: function () {
        loadUser();
    }
})
