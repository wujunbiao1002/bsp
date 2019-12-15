var loadUnreadMsg = function(app) {
    $.ajax({
        url: '/msg/unread_list',
        data: app.unreadPageParams,
        success: function (data) {
            if (data.code==0) {
                app.unreadPage=data.page;
                // 设置分页工具条,getPageIndex在common.js中
                var pageIndex = getPageIndex(app.unreadPageBar.pageBarSize, data.page.currPage, data.page.totalPage);
                app.unreadPageBar.startPageIndex=pageIndex.startPageIndex;
                app.unreadPageBar.endPageIndex=pageIndex.endPageIndex;

            } else if (data.code==401) { //未登录
                window.location.href='/login'
            } else {
                alert(data.msg);
            }
        }
    });
    $.ajax({//加载消息数
        url: '/msg/amount',
        type:"get",
        success: function (data) {
            var newMsgNum = data.msgNum;
            app.newMsgNum=newMsgNum<100?newMsgNum:'99+';
        }
    })
}

var doReadMsg = function (app,id) {
    $.ajax({
        url: '/msg/read',
        data: {
            nId: id
        },
        success: function (data) {
            if (data.code==0) {
                app.loadUnreadMsg(app);
                return;
            } else if (data.code==401) {
                window.location.href='/login';
            } else {
                alert(data.msg);
            }
        }
    })
}

var loadReadMsg = function(app) {
    $.ajax({
        url: '/msg/read_list',
        data: app.readPageParams,
        success: function (data) {
            if (data.code==0) {
                app.readPage=data.page;
                // 设置分页工具条,getPageIndex在common.js中
                var pageIndex = getPageIndex(app.readPageBar.pageBarSize, data.page.currPage, data.page.totalPage);
                app.readPageBar.startPageIndex=pageIndex.startPageIndex;
                app.readPageBar.endPageIndex=pageIndex.endPageIndex;

            } else if (data.code==401) { //未登录
                window.location.href='/login'
            } else {
                alert(data.msg);
            }
        }
    });
}

var doDeleteRead = function (nId) {
    var app = this;
    confirm("操作无法恢复，确定删除？",function () {
        $.ajax({
            url: '/msg/deleteRead',
            data: {
                nId: nId
            },
            success: function (data) {
                if (data.code==0) {
                    app.loadReadMsg(app);
                    return;

                } else if (data.code==401) { //未登录
                    window.location.href='/login'
                } else {
                    alert(data.msg);
                }
            }
        });
    })
}

var init = function () {
    loadUnreadMsg(this);
}

var msg_app = new Vue({
    el: "#msg-app",
    data: {
        showPage: 0,//0-显示未读消息,1-显示已读消息
        newMsgNum: 0,//未读消息数
        unreadPage: {}, // 未读消息分页数据
        unreadPageBar: { // 未读消息分页工具条参数
            pageBarSize: 3,// 未读消息分页工具条大小
            startPageIndex: 1, // 未读消息分页工具条开始页码
            endPageIndex: 1, // 未读消息分分页工具条结束页码
        },
        unreadPageParams: {// 未读消息分页查询参数
            limit: 3, // 页大小
            pageNumber: 1 // 当前页
        },
        readPage: {}, // 已读消息分页数据
        readPageBar: { // 已读消息分页工具条参数
            pageBarSize: 3,// 分页工具条大小
            startPageIndex: 1, // 分页工具条开始页码
            endPageIndex: 1, // 分页工具条结束页码
        },
        readPageParams: {// 已读消息分页查询参数
            limit: 3, // 页大小
            pageNumber: 1 // 当前页
        },
    },
    methods: {
        switchPage: function (page) {//切换页
            if (page==0){
                this.loadUnreadMsg(this);
            } else if (page==1) {
                this.loadReadMsg(this);
            }
            this.showPage = page;
        },
        readMsg: function (id) {
            doReadMsg(this,id);
        },
        loadUnreadMsg: loadUnreadMsg,
        loadReadMsg: loadReadMsg,
        goUnreadPage: function (currPage) {// 页面跳转
            this.unreadPageParams.pageNumber = currPage;
            this.loadUnreadMsg(this);
        },
        goReadPage: function (currPage) {// 页面跳转
            this.readPageParams.pageNumber = currPage;
            this.loadReadMsg(this);
        },
        deleteRead: doDeleteRead,
        millisecondsToDateTime: function (ms){
            return new Date(ms).toLocaleString();
        }
    },
    created: init
})