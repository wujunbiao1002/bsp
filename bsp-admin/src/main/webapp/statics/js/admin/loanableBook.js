$(function () {
    var tab = $("#data-list").bootstrapTable({
        url: '/loanableBook/page',
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
            {field: 'lbId', title: 'ID',sortable:true},
            {field: 'lbName', title: '书名',sortable:true},
            {field: 'isbn', title: 'ISBN',sortable:true},
            {field: 'lbNumber', title: '数量',sortable:true},
            {field: 'totalLending', title: '借出次数',sortable:true},
            {field: 'user.mail', title: '所属用户',sortable:false},
            {field: 'lbStatus', title: '状态',formatter:statusFormatter,sortable:false},
            {title: '操作',formatter:operationFormatter},
        ],
    })
})
var init_table = function() {

}

var detailFormatter = function(index,row){
    return '<ul class="media-list">' +
        '  <li class="media">' +
        '    <div class="media-left">' +
        '      <a target="_blank" href="'+row.imagePath+'">' +
        '        <img width="319" class="thumbnail" src="/cover?imagePath='+row.imagePath+'" alt="...">' +
        '      </a>' +
        '    </div>' +
        '    <div class="media-body">' +
        '      <h4 class="media-heading">&laquo;'+row.lbName+'&raquo;</h4>' +
        '      作者：' +row.lbAuthor+
        '      </br>出版社：' +row.lbPublishing+
        '      </br>ISBN：' +row.isbn+
        '      </br>数量：' +row.lbNumber+'本'+
        '      </br>已借出：' +(row.lbNumber-row.left)+'本'+
        '      </br>剩余：' +row.left+'本'+
        '      </br>借出次数：' +row.totalLending+
        '      </br>一级分类：' +row.secondaryClassification.primaryClassification.pcName+
        '      </br>二级分类：' +row.secondaryClassification.scName+
        '      </br>所属用户：' +row.user.mail+
        '      </br>联系电话：' +row.phone+
        '      </br>可借时长：' +row.lbDuratuin+'天'+
        '      </br>评价：' +row.lbComment+
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

var operationFormatter = function(value,row,index){
    if (row.isDelete == 1) {
        return ;
    }
    if (row.lbStatus == 0) {
        return '<div id="tab-toolbar" class="btn-group" role="group" >' +
            '<button onclick="doUnshelve('+row.lbId+')" type="button" class="btn btn-defualt btn-xs" title="下架"><i class="fa fa-ban" aria-hidden="true"></i> 下架</button>' +
            '</div>';
    }
    if (row.lbStatus == 1) {
        return '<div id="tab-toolbar" class="btn-group" role="group" >' +
            '<button onclick="doUnshelve('+row.lbId+')" type="button" class="btn btn-defualt btn-xs" title="下架"><i class="fa fa-ban" aria-hidden="true"></i> 下架</button>' +
            '</div>';
    }
    if (row.lbStatus == 2) {
        return '<div id="tab-toolbar" class="btn-group" role="group" >' +
            '<button onclick="doShelve('+row.lbId+')" type="button" class="btn btn-defualt btn-xs" title="上架"><i class="fa fa-check-square-o" aria-hidden="true"></i> 上架</button>' +
            '</div>';
    }
}
var statusFormatter = function(value,row,index){
    if (row.isDelete == 1) {
        return '<span class="label label-default">已删除</span>';
    }
    if (row.lbStatus == 0) {
        return '<span class="label label-warning">已关闭</span>';
    }
    if (row.lbStatus == 1) {
        return '<span class="label label-primary">共享中</span>';
    }
    if (row.lbStatus == 2) {
        return '<span class="label label-danger">已下架</span>';
    }
}

var doReload = function () {
    $("#data-list").bootstrapTable('refresh');
}

var doOpenModal = function (id) {//打开模态框
    $('#input-modal').modal('show');
}

var doShelve=function (id) {//审核通过
    confirm("确认上架?", function () {
        $.ajax({
            url: '/loanableBook/shelve',
            data: {
                id: id
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

var doUnshelve=function (id) {//审核通过
    confirm("确认下架?", function () {
        $.ajax({
            url: '/loanableBook/unshelve',
            data: {
                id: id
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
        status: 1, // 显示数据
    },
    methods: {
        reload: doReload
    },
    created: function () {
        init_table();
    }
});