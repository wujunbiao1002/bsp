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
    cache: false,
    error: function (jqXHR, textStatus, errorThrown) {
        alert('请求出错');
    }
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
			layer.closeAll('dialog');
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
/**
 * 首页 上页 [1] 2 3 4 5 7 8 9 9 10 下页 末页 当前第 1/18 页 共100条记录 每页x条
 *
 * @param totalIndexCount
 *            总索引数
 * @param currentPage
 *            当前页
 * @param totalPage
 *            总页数
 * @return
 */
var getPageIndex = function (totalIndexCount, currentPage, totalPage) {
    var startPageIndex = currentPage - (totalIndexCount % 2 == 0 ? Math.floor(totalIndexCount/2) - 1 : Math.floor(totalIndexCount/2));
    var endPageIndex = currentPage + Math.floor(totalIndexCount/2);
    if (startPageIndex < 1) {
        startPageIndex = 1;
        if (totalPage >= totalIndexCount) {
            endPageIndex = totalIndexCount;
        } else {
            endPageIndex = totalPage;
        }
    }
    if (endPageIndex > totalPage) {
        endPageIndex = totalPage;
        if (endPageIndex - totalIndexCount > 0) {
            startPageIndex = endPageIndex - totalIndexCount + 1;
        } else {
            startPageIndex = 1;
        }
    }
    return {'startPageIndex':parseInt(startPageIndex),'endPageIndex':parseInt(endPageIndex)};
}