$(function () {
    var tab = $("#data-list").bootstrapTable({
        url: '/clb/page',
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
            {field: 'clbId', title: 'ID',sortable:true},
            {field: 'clbName', title: '书名',sortable:true},
            {field: 'isbn', title: 'ISBN',sortable:true},
            {field: 'clbNumber', title: '数量',sortable:true},
            {field: 'user.mail', title: '所属用户',sortable:false},
            {title: '审核',formatter:operationFormatter},
        ],
    })
})
var init_table = function() {

}

var detailFormatter = function(index,row){
    var failureCause = vue_app.clbStatus == 1 ? '</br>审核不通过原因：'+row.failureCause:'';
	return '<ul class="media-list">' +
        '  <li class="media">' +
        '    <div class="media-left">' +
        '      <a target="_blank" href="'+row.imagePath+'">' +
        '        <img width="319" class="thumbnail" src="/cover?imagePath='+row.imagePath+'" alt="...">' +
        '      </a>' +
        '    </div>' +
        '    <div class="media-body">' +
        '      <h4 class="media-heading">&laquo;'+row.clbName+'&raquo;</h4>' +
        '      作者：' +row.clbAuthor+
        '      </br>出版社：' +row.clbPublishing+
        '      </br>ISBN：' +row.isbn+
        '      </br>数量：' +row.clbNumber+
        '      </br>一级分类：' +row.secondaryClassification.primaryClassification.pcName+
        '      </br>二级分类：' +row.secondaryClassification.scName+
        '      </br>所属用户：' +row.user.mail+
        '      </br>联系电话：' +row.phone+
        '      </br>可借时长：' +row.clbDuration+'天'+
        '      </br>评价：' +row.clbComment+
        failureCause+
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
        clbStatus: vue_app.clbStatus,
	};
	return p;
}

var operationFormatter = function(value,row,index){
    if (vue_app.clbStatus == 0) {
        return '<div id="tab-toolbar" class="btn-group" role="group" >' +
            '<button onclick="doApprove('+row.clbId+')" type="button" class="btn btn-defualt btn-xs" title="通过"><i class="fa fa-check-square-o" aria-hidden="true"></i> 通过</button>' +
            '<button onclick="doOpenModal('+row.clbId+')" type="button" class="btn btn-danger btn-xs" title="失败"><i class="fa fa-times" aria-hidden="true"></i> 失败</button>' +
            '</div>';
    }
    if (vue_app.clbStatus == 1) {
        return '<div id="tab-toolbar" class="btn-group" role="group" >' +
            '<button onclick="doApprove('+row.clbId+')" type="button" class="btn btn-defualt btn-xs" title="通过"><i class="fa fa-check-square-o" aria-hidden="true"></i> 通过</button>' +
            '</div>';
    }
}

var doReload = function () {
    $("#data-list").bootstrapTable('refresh');
}

var doDeny=function () {//审核不通过
    if (!vue_app.inputMsg.trim()) {
        vue_app.inputMsg='请填写失败原因';
        return;
    }
    if (vue_app.inputMsg.length>250) {
        vue_app.inputMsg='长度必须小于250个字符';
        return;
    }
    $.ajax({
        url: '/clb/deny',
        data: {
            clbId: vue_app.deny_clbId,
            failureCause: vue_app.failureCause
        },
        success: function (data) {
            if (data.code==401){
                window.location.href='/login';
            }
            if (data.code!=0){
                alert(data.msg);
            }
            $('#input-modal').modal('hide');
            vue_app.inputMsg='';
            vue_app.deny_clbId={};
            doReload();
        }
    })
}

var doOpenModal = function (id) {//打开模态框
    vue_app.deny_clbId= id;
    vue_app.inputMsg='';
    vue_app.failureCause='';
    $('#input-modal').modal('show');
}

var doApprove=function (id) {//审核通过
    confirm("确认操作", function () {
        $.ajax({
            url: '/clb/approve',
            data: {
                clbId: id
            },
            success: function (data) {
                if (data.code==401){
                    window.location.href='/login';
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
        clbStatus: 0, // 显示数据,0-未审核，1-审核失败，2-审核通过
        failureCause: '', // 审核失败原因
        deny_clbId: {}, // 审核失败记录id
        inputMsg: '' // 审核失败原因文本域提示
    },
	methods: {
		reload: doReload,
        deny: doDeny
	},
	created: function () {
        init_table();
    }
});