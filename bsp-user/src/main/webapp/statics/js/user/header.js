var init = function () {
    var app = this;
    $.ajax({
        type:"get",
        url: "/token",
        success: function(data){
            if(data.code === 0){
                if (data.code!=0) {//未登录
                    return;
                }
                app.token=data.token;
                app.login=true;//显示消息链接
                $.ajax({//加载消息数
                    url: '/msg/amount',
                    type:"get",
                    success: function (data) {
                        var msgNum = data.msgNum;
                        app.msgNum=msgNum<100?msgNum:'99+';
                    }
                })
            }

        }
    });
}

var logout = function () {
    confirm("退出登录？", function () {
        $.ajax({
            url: "/logout",
            success: function (data) {
                if (data.code==0) {
                    window.location.href="/";
                } else {
                    alert(data.msg);
                }
            }
        })
    })
}

var doSearch = function () {
    if (header_app.search.trim()) {
        var search = encodeURI(header_app.search.trim());
        window.location.href='/p/repository?search='+search;
    }
}

var header_app=new Vue({
    el: "#header-app",
    data: {
        token: {},
        msgNum: 0,
        login: false,
        search: ''
    },
    methods: {
        logout: logout,
        doSearch: doSearch
    },
    created: init
})