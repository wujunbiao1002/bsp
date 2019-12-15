//工具集合Tools
window.T = {};
//服务器地址
window.server = '127.0.1.0:8080';

// 获取请求参数
// 使用示例
// location.href = http://localhost:8080/index.html?id=123
// T.p('id') --> 123;
var url = function(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if(r != null) return unescape(r[2]);
	return null;
};
T.p = url;

//全局配置
$.ajaxSetup({
    dataType: "json",
    cache: false
});

//重写alert
window.alert = function(msg, callback) {
	parent.layer.alert(msg, function(index) {
		parent.layer.close(index);
		if(typeof(callback) === "function") {
			callback("ok");
		}
	});
}

//重写confirm式样框
window.confirm = function(msg, callback) {
	parent.layer.confirm(msg, {
			btn: ['确定', '取消']
		},
		function() { //确定事件
			if(typeof(callback) === "function") {
				callback("ok");
			}
            parent.layer.closeAll('dialog');
		});
}
//iframe
var promptContent = function() {
	//iframe窗
	layer.open({
		type: 2,
		content: $('#page-container'),
		title: false,
		closeBtn: 0, //不显示关闭按钮
		shade: [0],
		area: ['340px', '215px'],
		//offset: 'rb', //右下角弹出
		time: 2000, //2秒后自动关闭
		anim: 2,
		content: ['/bsp/templates/indexContent.html', 'no'], //iframe的url，no代表不显示滚动条
		end: function() { //此处用于演示
			layer.open({
				type: 2,
				title: '很多时候，我们想最大化看，比如像这个页面。',
				shadeClose: true,
				shade: false,
				maxmin: true, //开启最大化最小化按钮
				area: ['893px', '600px'],
				content: '/bsp/templates/indexContent.html'
			});
		}
	});
}

//选择一条记录
/*function getSelectedRow() {
    var grid = $("#jqGrid");
    var rowKey = grid.getGridParam("selrow");
    if(!rowKey){
        alert("请选择一条记录");
        return ;
    }

    var selectedIDs = grid.getGridParam("selarrrow");
    if(selectedIDs.length > 1){
        alert("只能选择一条记录");
        return ;
    }

    return selectedIDs[0];
}*/

//选择多条记录
/*function getSelectedRows() {
    var grid = $("#jqGrid");
    var rowKey = grid.getGridParam("selrow");
    if(!rowKey){
        alert("请选择一条记录");
        return ;
    }

    return grid.getGridParam("selarrrow");
}*/

//判断是否为空
function isBlank(value) {
	return !value || !/\S/.test(value)
}

function millisecondsToDateTime(ms){
	return new Date(ms).toLocaleString();
}
