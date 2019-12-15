var loadIngOrder = function(app) {
    $.ajax({
        url: '/in_record/query',
        data: app.ingParams,
        success: function (data) {
            if (data.code==0) {
                app.ingPage=data.page.rows;
                app.newOrderNum=data.page.total;
                // 设置分页工具条,getPageIndex在common.js中
                var pageIndex = getPageIndex(app.ingPageBar.pageBarSize, data.page.currPage, data.page.totalPage);
                app.ingPageBar.startPageIndex=pageIndex.startPageIndex;
                app.ingPageBar.endPageIndex=pageIndex.endPageIndex;
                app.currPage = data.page.currPage;
                app.page = data.page.totalPage;

            } else if (data.code==401) { //未登录
                window.location.href='/login'
            } else {
                alert(data.msg);
            }
        }
    });
}

var loadBugOrder = function(app){
	 $.ajax({
	        url: '/in_record/query',
	        data: app.bugPageParams,
	        success: function (data) {
	            if (data.code==0) {
	                app.bugPage=data.page.rows;
	                app.newOrderNum=data.page.total;
	                // 设置分页工具条,getPageIndex在common.js中
	                var pageIndex = getPageIndex(app.bugPageBar.pageBarSize, data.page.currPage, data.page.totalPage);
	                app.bugPageBar.startPageIndex=pageIndex.startPageIndex;
	                app.bugPageBar.endPageIndex=pageIndex.endPageIndex;
	                app.currPage = data.page.currPage;
	                app.page = data.page.totalPage;

	            } else if (data.code==401) { //未登录
	                window.location.href='/login'
	            } else {
	                alert(data.msg);
	            }
	        }
	    });
}

var loadEdgOrder = function(app){
	 $.ajax({
	        url: '/in_record/query',
	        data: app.edPageParams,
	        success: function (data) {
	            if (data.code==0) {
	                app.edPage=data.page.rows;
	                app.newOrderNum=data.page.total;
	                // 设置分页工具条,getPageIndex在common.js中
	                var pageIndex = getPageIndex(app.edPageBar.pageBarSize, data.page.currPage, data.page.totalPage);
	                app.edPageBar.startPageIndex=pageIndex.startPageIndex;
	                app.edPageBar.endPageIndex=pageIndex.endPageIndex;
	                app.currPage = data.page.currPage;
	                app.page = data.page.totalPage;

	            } else if (data.code==401) { //未登录
	                window.location.href='/login'
	            } else {
	                alert(data.msg);
	            }
	        }
	    });
}

var init = function () {
	loadIngOrder(this);
}

var record_app = new Vue({
    el: "#record_app",
    data: {
        showPage: 0,//0-显示正在进行,1-显示异常记录,2-显示已经结束
        newOrderNum: 0,//
        currPage:{},//当前页码
        page:{},//总页数
        ingPage: {}, // 正在进行分页数据
        ingPageBar: { // 正在进行分页工具条参数
            pageBarSize: 3,// 正在进行分页工具条大小
            startPageIndex: 1, // 正在进行分页工具条开始页码
            endPageIndex: 1, // 正在进行分分页工具条结束页码
        },
        ingParams: {// 正在进行分页查询参数
            limit: 3, // 页大小
            pageNumber: 1, // 当前页
            status: 0,
            search: []
        },
        bugPage: {}, // 异常记录分页数据
        bugPageBar: { // 异常记录分页工具条参数
            pageBarSize: 3,// 分页工具条大小
            startPageIndex: 1, // 分页工具条开始页码
            endPageIndex: 1, // 分页工具条结束页码
        },
        bugPageParams: {// 异常记录分页查询参数
            limit: 3, // 页大小
            pageNumber: 1, // 当前页
            status: 1,
            search: []
        },
        edPage: {}, // 已经结束分页数据
        edPageBar: { // 已经结束分页工具条参数
            pageBarSize: 3,// 分页工具条大小
            startPageIndex: 1, // 分页工具条开始页码
            endPageIndex: 1, // 分页工具条结束页码
        },
        edPageParams: {// 已经结束分页查询参数
            limit: 3, // 页大小
            pageNumber: 1, // 当前页
            status: 2,
            search: []
        }
    },
    methods: {
        switchPage: function (page) {//切换页
        	 if (page==0){
        		 this.ingParams.search = null;
                 this.loadIngOrder(this);
             } else if (page==1) {
            	 this.bugPageParams.search = null;
                 this.loadBugOrder(this);
             } else if (page==2) {
            	 this.edPageParams.search = null;
                 this.loadEdgOrder(this);
             }
            this.showPage = page;
        },
        searchPage: function (page) {//切换页
       	 if (page==0){
       		 	this.ingParams.search = $("#r_search0").val().trim();
                this.loadIngOrder(this);
            } else if (page==1) {
            	this.bugPageParams.search = $("#r_search1").val().trim();
                this.loadBugOrder(this);
            } else if (page==2) {
            	this.edPageParams.search = $("#r_search2").val().trim();
                this.loadEdgOrder(this);
            }
           this.showPage = page;
        },
        cancel: function(t_lrId){
        	var app = this;
        	confirm("确定取消？", function () {
                $.ajax({
                    url: '/in_record/cancel',
                    data: {
                        lrId : t_lrId
                    },
                    success: function (data) {
                        if (data.code==0) {
                            layer.msg("取消成功");
                            app.switchPage(2);
                        } else if (data.code==401) { //未登录
                            window.location.href='/login'
                        } else {
                            alert(data.msg);
                        }
                    }
                });
            });
        },
        loadIngOrder:loadIngOrder,
        loadBugOrder:loadBugOrder,
        loadEdgOrder:loadEdgOrder,
        goIngPage: function (currPage) {// 页面跳转
            this.ingParams.pageNumber = currPage;
            this.loadIngOrder(this);
        },
        goBugPage: function (currPage) {// 页面跳转
            this.bugPageParams.pageNumber = currPage;
            this.loadBugOrder(this);
        },
        goEdPage: function (currPage) {// 页面跳转
            this.edPageParams.pageNumber = currPage;
            this.loadEdgOrder(this);
        },
        millisecondsToDateTime: function (ms){
            return new Date(ms).toLocaleString();
        }
    },
    created: init
})
