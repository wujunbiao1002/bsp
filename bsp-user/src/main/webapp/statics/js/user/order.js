/**
 * 判读借阅数量是否大于剩余借阅数量
 */
var doJudge = function() {
	var app = this;
	if(app.amount>app.datas.left){
		alert("输入数量不可大于剩余数量！");
		app.amount = 1;
	}
}

/**
 * 提交订单
 */
var doSubmit = function() {
	var app = this;
	var phone = $('#phone').val();
	app.lendingRecord.loanPhone = phone;
	app.lendingRecord.amount = app.amount;
	app.lendingRecord.loanableBook.lbId = plbId;
	$.ajax({
        url: "/order/submit",
        type: "post",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(app.lendingRecord),
　　 	success: function(result){
             if(result.code == 0){
            	 alert("提交申请成功！");
             }
             else{
            	 alert(result.msg);
             }
        }
	});
}

/**
 * 订单页面
 */
var plbId = T.p('lbId');
var order = new Vue({
	el:"#order",
	data:{
		step :1,
		datas:[],//书的信息
		lbId:[],//当前书的id
		user:[],//当前登录的用户
		amount:1,//借书的数量
		lendingRecord:{
			"amount":[],
		 	"loanPhone":[],
			"loanableBook":{"lbId":[]},
		}
	},
	created: function () {
			var self = this;
			lbId = plbId;
			$.ajax({
            type: "GET",// 请求方式
            url: "/order/detail?lbId="+lbId,// 地址，就是json文件的请求路径
            dataType: "json",// 数据类型可以为 text xml json script jsonp
　　　　　　   success: function(result){// 返回的参数就是 action里面所有的有get和set方法的参数
                 if(result.code == 0){
                	 self.datas = result.detail;
                	 self.user = result.user;
                 }else if(result.code == 401){
                	 alert(result.msg);
                	 window.location.href = "login";
                 }else{
                	 alert(result.msg);
                 }
            }
			});
	},
	methods:{
		judge: doJudge,
		submit: doSubmit
	}
})