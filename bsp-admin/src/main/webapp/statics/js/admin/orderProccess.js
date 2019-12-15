$(function () {
    $('#msg-input').bootstrapValidator({
        message: '无效输入',
        //excluded:[":hidden",":disabled",":not(visible)"] ,//bootstrapValidator的默认配置
        excluded: ':disabled',//关键配置，表示只对于禁用域不进行验证，其他的表单元素都要验证
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',//显示验证成功或者失败时的一个小图标
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            subject: {
                message: '主题不能为空',//默认提示信息
                validators: {
                    notEmpty: {
                        message: '主题不能为空'
                    },
                    stringLength: {
                        min: 1,
                        max: 20,
                        message: '超过20个字符'
                    }
                }
            },
            content: {
                message: '正文不能为空',//默认提示信息
                validators: {
                    notEmpty: {
                        message: '正文不能为空'
                    },
                    stringLength: {
                        min: 1,
                        max: 1000,
                        message: '超过100个字符'
                    }
                }
            }
        }
    });
    $("#btn-send").click(function () {//非submit按钮点击后进行验证，如果是submit则无需此句直接验证
        $("#msg-input").bootstrapValidator('validate');//提交验证
    });

    var tab = $("#data-list").bootstrapTable({
        url: '/proccess/page',
        //url: '/static/data/orderData.json',
        method: 'get',
        contentType: 'application/json',
        dataType: 'json',
        detailView: true,
        detailFormatter: detailFormatter,
        cache: false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        striped: true,
        height: 705,
        icons: {
            paginationSwitchDown: 'glyphicon-collapse-down icon-chevron-down',
            paginationSwitchUp: 'glyphicon-collapse-up icon-chevron-up',
            refresh: 'glyphicon-refresh icon-refresh',
            toggle: 'glyphicon-list-alt icon-list-alt',
            columns: 'glyphicon-th icon-th',
            detailOpen: 'glyphicon-plus icon-plus',
            detailClose: 'glyphicon-minus icon-minus',
        },
        pagination: true, //是否显示分页（*）
        sortable: true, //是否启用排序
        /*sortOrder: 'asc', //排序方式*/
        queryParams: getQueryParams,
        pageSize: 14,
        pageList: [14, 28, 56, 100],
        showRefresh: true, // 是否显示刷新按钮
        showToggle: true,
        showFullscreen: false,
        showHeader: true,
        showFooter: false,
        showColumns:true,
        //showPaginationSwitch: true,
        smartDisplay: true,
        search: true,
        searchOnEnterKey: true,
        searchAlign: 'right',
        sidePagination: "server",
        toolbar: '#tab-toolbar',
        toolbarAlign: 'left',
        trimOnSearch: true,
        minimumCountColumns: 1, //最少允许的列数
        //clickToSelect: true,  //是否启用点击选中行
        idField: 'id',
        uniqueId: 'id',
        columns: [
            //{checkbox: true, visible: true},
            {field: 'lrId', title: '订单号',sortable:true},
            {field: 'createTime', title: '时间',formatter:createTimeFormatter,sortable:true},
            {field: 'loanableBook.lbName', title: '书名',sortable:false},
            {field: 'amount', title: '数量',sortable:false},
            {field: 'loanableBook.user.mail', title: '借出方',sortable:false},
            {field: 'user.mail', title: '借入方',sortable:false},
            //{field: 'takeAwayTime', title: '开始日期',formatter:takeAwayTimeFormatter,sortable:true},
            //{field: 'expectedReturnTime', title: '还书日期',formatter:expectedReturnTimeFormatter,sortable:true},
            {title: '状态', formatter:statusFormatter},
            {title: '操作',formatter:operationFormatter},
        ],
    })
})
var init_table = function() {

}

var createTimeFormatter= function (index,row) {
    return millisecondsToDateTime(row.createTime);
}

var takeAwayTimeFormatter= function (index,row) {
    return millisecondsToDateTime(row.takeAwayTime);
}

var expectedReturnTimeFormatter= function (index,row) {
    return millisecondsToDateTime(row.expectedReturnTime);
}

var statusFormatter = function(index,row){
    switch(row.lrStruts)
    {
        case 0:
            return '<span class="label label-success">正常</span>';
            break;
        case 1:
            return '<span class="label label-warning">失效</span>';
            break;
        case 2:
            return '<span class="label label-warning">失效</span>';
            break;
        case 3:
            return '<span class="label label-warning">失效</span>';
            break;
        case 4:
            return '<span class="label label-success">正常</span>';
            break;
        case 5:
            return '<span class="label label-warning">失效</span>';
            break;
        case 6:
            return '<span class="label label-success">正常</span>';
            break;
        case 7:
            return '<span class="label label-danger">异常</span>';
            break;
        case 8:
            return '<span class="label label-success">正常</span>';
            break;
        case 9:
            return '<span class="label label-danger">异常</span>';
            break;
        case 10:
            return '<span class="label label-success">正常</span>';
            break;
        case 11:
            return '<span class="label label-info">捐赠</span>';
            break;
        case 12:
            return '<span class="label label-primary">完成</span>';
            break;
        default:
            return '<span class="label label-default">未知</span>';
            break;
    };
}
var statusFormatterDetail = function(index,row){
    switch(row.lrStruts)
    {
        case 0:
            return '<span class="label label-success">正常</span> 等待借出方同意';
            break;
        case 1:
            return '<span class="label label-warning">失效</span> 借入方取消申请';
            break;
        case 2:
            return '<span class="label label-warning">失效</span> 借出方拒绝申请';
            break;
        case 3:
            return '<span class="label label-warning">失效</span> 申请超时';
            break;
        case 4:
            return '<span class="label label-success">正常</span> 等待借出方将图书送至中转站';
            break;
        case 5:
            return '<span class="label label-warning">失效</span> 借出方未按时将图书送至中转站';
            break;
        case 6:
            return '<span class="label label-success">正常</span> 等待借入方取走图书';
            break;
        case 7:
            return '<span class="label label-danger">异常</span> 借入方未按时取走图书，等待借出方取回';
            break;
        case 8:
            return '<span class="label label-success">正常</span> 等待借入方归还图书';
            break;
        case 9:
            return '<span class="label label-danger">异常</span> 借入方未按时归还图书';
            break;
        case 10:
            return '<span class="label label-success">正常</span> 等待借出方取回图书';
            break;
        case 11:
            return '<span class="label label-info">捐赠</span>';
            break;
        case 12:
            return '<span class="label label-primary">完成</span>';
            break;
        default:
            return '<span class="label label-default">未知</span>';
            break;
    };
}

var detailFormatter = function(index,row){
    var agreeTime = row.agreeTime ? millisecondsToDateTime(row.agreeTime) : '无';
    var sendToTime = row.sendToTime ? millisecondsToDateTime(row.sendToTime) : '无';
    var takeAwayTime = row.takeAwayTime ? millisecondsToDateTime(row.takeAwayTime) : '无';
    var expectedReturnTime = row.expectedReturnTime ? millisecondsToDateTime(row.expectedReturnTime) : '无';
    var actualReturnTime = row.actualReturnTime ? millisecondsToDateTime(row.actualReturnTime) : '无';
    var receiveAdmin=row.receiveAdmin ? row.receiveAdmin.aId : '无';
    var backAdmin=row.backAdmin ? row.backAdmin.aId : '无';
	return '<ul class="media-list">' +
        '  <li class="media">' +
        '    <div class="media-left">' +
        '      <a target="_blank" href="'+row.loanableBook.imagePath+'">' +
        '        <img width="319" class="thumbnail" src="/cover?imagePath='+row.loanableBook.imagePath+'" alt="...">' +
        '      </a>' +
        '    </div>' +
        '    <div class="media-body">' +
        '      <h4 class="media-heading">订单号：'+row.lrId+'</h4>' +
        '      订单时间：' +millisecondsToDateTime(row.createTime)+
        '      </br>状态：' +statusFormatterDetail(index,row)+
        '      </br>借出方：' +row.loanableBook.user.mail+
        '      ，手机号：' +row.loanableBook.phone+
        '      </br>借入方：' +row.user.mail+
        '      ，手机号：' +row.loanPhone+
        '      </br>书名：&laquo;' +row.loanableBook.lbName +'&raquo;'+
        '      </br>ISBN：' +row.loanableBook.isbn+
        '      </br>出版社：' +row.loanableBook.lbPublishing+
        '      </br>数量：' +row.amount+
        '      </br>借出方同意时间：' +agreeTime+
        '      </br>借出方送达中转站时间：' +sendToTime+
        '      </br>借入方取书时间：' +takeAwayTime+
        '      </br>应还时间：' +expectedReturnTime+
        '      </br>实际还书时间：' +actualReturnTime+
        '      </br>图书接收者（借出方->中转站）：' +receiveAdmin+
        '      </br>图书接收者（借入方->中转站）：' +receiveAdmin+
        '    </div>' +
        '  </li>' +
        '</ul>';
}

var getQueryParams = function(params){
	var p = {
		limit: params.limit,
		pageNumber: params.offset/params.limit+1,
		order: params.order,
		sort: params.sort,
		search: params.search,
        status: vue_app.status,
	};
	return p;
}

var operationFormatter = function(value,row,index){// 根据订单状态生成操作按钮
    var html = '<div id="tab-toolbar" class="btn-group" role="group" >';
    html+='<button onclick="doOpenModal('+row.lrId+')" type="button" class="btn btn-primary btn-xs" title="发送消息"><i class="fa fa-send-o" aria-hidden="true"></i> 消息</button>';
    var btnTxt = '下一步'
    switch(row.lrStruts)
    {
        case 4:
            btnTxt='接收';
            break;
        case 6:
            btnTxt='取书';
            break;
        case 7:
            btnTxt='取回';
            break;
        case 8:
            btnTxt='归还';
            break;
        case 9:
            btnTxt='归还';
            break;
        case 10:
            btnTxt='取回';
            break;
    };
    if (row.lrStruts==4 || row.lrStruts==6 || row.lrStruts==7 || row.lrStruts==8 || row.lrStruts==9 || row.lrStruts==10) {
        html+='<button onclick="doNextStep('+row.lrId+')" type="button" class="btn btn-defualt btn-xs" title="下一步"><i class="fa fa-arrow-right" aria-hidden="true"></i> '+btnTxt+'</button>';
    }
    if(row.lrStruts==7 || row.lrStruts==10) {
        html+='<button onclick="doDonate('+row.lrId+')" type="button" class="btn btn-danger btn-xs" title="捐赠"><i class="fa fa-share-alt" aria-hidden="true"></i> 捐赠</button>';
    }
    html+='</div>';
    return html;
}

var doReload = function () {
    $("#data-list").bootstrapTable('refresh');
}

var doSendMsg=function () {
    if (!$('#msg-input').data('bootstrapValidator').isValid()) {// 判断是否验证通过
        return;
    }
    confirm("确认发送？", function () {
        $('#input-modal').modal('hide');
        $.ajax({
            url: '/proccess/sendMsg',
            data: vue_app.msg,
            success: function (data) {
                if (data.code==401){
                    window.location.href='/login';
                    return;
                }
                if (data.code!=0){
                    alert(data.msg);
                } else {
                    alert("消息发送成功");
                }
                vue_app.msg.lrId='';
            }
        })
    })
}

var doDonate=function (id) {
    confirm("确认捐赠？", function () {
        $.ajax({
            url: '/proccess/donate',
            data: {
                lrId: id
            },
            success: function (data) {
                if (data.code==401){
                    window.location.href='/login';
                    return;
                }
                if (data.code!=0){
                    alert(data.msg);
                }
                doReload();
            }
        })
    })
}

var doOpenModal = function (id) {//打开模态框
    vue_app.msg.lrId=id;
    $('#input-modal').modal('show');
    // 清除上次验证提示
    $('#msg-input').data('bootstrapValidator')
        .updateStatus('subject', 'NOT_VALIDATED', null)
        .updateStatus('content', 'NOT_VALIDATED', null);
}

var doNextStep=function (id) {//审核通过
    confirm("确认操作？", function () {
        $.ajax({
            url: '/proccess/next_step',
            data: {
                lrId: id
            },
            success: function (data) {
                if (data.code==401){
                    window.location.href='/login';
                    return;
                }
                if (data.code!=0){
                    alert(data.msg);
                }
                doReload();
            }
        })
    })
}

var vue_app=new Vue({
    el: '#vue-app',
    data: {
        status: 0, // 订单状态，0-进行中 1-异常 2-已结束 3-全部
        msg: {
            sendTo: 0, // 发送消息对象，0-借入方 1-借出方
            lrId: '', // 订单Id
            subject: '', // 主题
            content: '' // 正文
        },
        inputMsg: ''
    },
	methods: {
		reload: doReload,
        sendMsg: doSendMsg
	},
	created: function () {
        init_table();
    }
});