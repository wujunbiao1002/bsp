/**
 * 图书详情
 */

new Vue({
    el: "#app",
    data: {
        datas: [],
        lbId: []
    },
    created: function () {
        //获取图书id
        var plbId = T.p('lbId');
        // 为了在内部函数能使用外部函数的this对象，要给它赋值了一个名叫self的变量。
        var self = this;
        lbId = plbId;
        $.ajax({
            type: "GET",// 请求方式
            url: "/loanble_book/detail?lbId=" + lbId,// 地址，就是json文件的请求路径
            dataType: "json",// 数据类型可以为 text xml json script jsonp
            success: function (result) {// 返回的参数就是 action里面所有的有get和set方法的参数
                if (result.code == 0) {
                    self.datas = result.detail;
                }
                else {
                    alert(result.msg);
                }
            }
        });
    }
})
