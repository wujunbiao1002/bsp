$(function () {
    $('#input-form').bootstrapValidator({
        message: '无效输入',
        //excluded:[":hidden",":disabled",":not(visible)"] ,//bootstrapValidator的默认配置
        excluded: ':disabled',//关键配置，表示只对于禁用域不进行验证，其他的表单元素都要验证
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',//显示验证成功或者失败时的一个小图标
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            name: {
                message: '不能为空',//默认提示信息
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    },
                    stringLength: {
                        min: 1,
                        max: 50,
                        message: '超过50个字符'
                    }
                }
            }
        }
    });
    var tab = $("#data-list").bootstrapTable({
        url: '/sc/page',
        method: 'get',
        contentType: 'application/json',
        dataType: 'json',
        //detailView: true,
        //detailFormatter: detailFormatter,
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
            {field: 'scId', title: 'ID',sortable:true},
            {field: 'scName', title: '名称',sortable:true},
            {field: 'primaryClassification.pcName', title: '所属一级分类',sortable:false},
            {field: 'isDelete', title: '状态',formatter:statusFormatter,sortable:false},
            {title: '操作',formatter:operationFormatter},
        ],
    })
})

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
    if (row.isDelete == 0) {
        return '<div id="tab-toolbar" class="btn-group" role="group" >' +
            '<button onclick="doOpenModal('+row.scId+','+row.primaryClassification.pcId+',\''+row.scName+'\')" type="button" class="btn btn-primary btn-xs" title="编辑"><i class="fa fa-edit" aria-hidden="true"></i> 编辑</button>' +
            '<button onclick="doDelete('+row.scId+')" type="button" class="btn btn-defualt btn-xs" title="删除"><i class="fa fa-times" aria-hidden="true"></i> 删除</button>' +
            '</div>';
    }
    if (row.isDelete == 1) {
        return '<div id="tab-toolbar" class="btn-group" role="group" >' +
            '<button onclick="doReuse('+row.scId+')" type="button" class="btn btn-defualt btn-xs" title="恢复"><i class="fa fa-share" aria-hidden="true"></i> 恢复</button>' +
            '</div>';
    }

}
var statusFormatter = function(value,row,index){
    if (row.isDelete == 0) {
        return '<span class="label label-primary">使用中</span>';
    }
    if (row.isDelete == 1) {
        return '<span class="label label-default">已删除</span>';
    }
}

var doReload = function () {
    $("#data-list").bootstrapTable('refresh');
}

var doOpenModal = function (scId,pcId,scName) {//打开模态框
    vue_app.obj.scId=scId?scId:'';
    vue_app.obj.scName=scName?scName:'';
    vue_app.obj.primaryClassification.pcId=pcId?pcId:0;
    $('#input-modal').modal('show');
    $('#input-form').data('bootstrapValidator')
        .updateStatus('name', 'NOT_VALIDATED', null);
}

var doReuse=function (id) {
    confirm("确认恢复?", function () {
        $.ajax({
            url: '/sc/reuse',
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

var doUpdate=function () {
    $('#input-form').data('bootstrapValidator').validate();
    if (!$('#input-form').data('bootstrapValidator').isValid()) {// 判断是否验证通过
        return;
    }
    if (vue_app.obj.primaryClassification.pcId==0) {
        vue_app.selectMsg='（请选择一级分类）';
        return;
    } else {
        vue_app.selectMsg='';
    }
    confirm("确认保存?", function () {
        $.ajax({
            url: '/sc/update',
            type: 'post',
            dataType : "JSON",
            contentType:"application/json",
            data: JSON.stringify(vue_app.obj),
            success: function (data) {
                if (data.code==401){
                    window.location.href='/login';
                    return;
                }
                if (data.code!=0){
                    alert(data.msg);
                }
                doReload();
                $('#input-modal').modal('hide');
            }
        })
    })
}
var doAdd=function () {
    $('#input-form').data('bootstrapValidator').validate();
    if (!$('#input-form').data('bootstrapValidator').isValid()) {// 判断是否验证通过
        return;
    }
    if (vue_app.obj.primaryClassification.pcId==0) {
        vue_app.selectMsg='（请选择一级分类）';
        return;
    } else {
        vue_app.selectMsg='';
    }
    confirm("确认保存?", function () {
        $.ajax({
            url: '/sc/add',
            type: 'post',
            dataType : "JSON",
            contentType:"application/json",
            data: JSON.stringify(vue_app.obj),
            success: function (data) {
                if (data.code==401){
                    window.location.href='/login';
                    return;
                }
                if (data.code!=0){
                    alert(data.msg);
                }
                doReload();
                $('#input-modal').modal('hide');
            }
        })
    })
}

var doDelete=function (id) {//审核通过
    confirm("确认删除?", function () {
        $.ajax({
            url: '/sc/delete',
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

var loadPrimaryClassifications=function () {
    $.ajax({
        url: '/pc/all',
        success: function (data) {
            if (data.code==401){
                window.location.href='/login';
                return;
            } else if (data.code!=0){
                alert(data.msg);
            } else {
                vue_app.primaryClassifications=data.list;
            }

        }
    })

}

var vue_app=new Vue({
    el: '#vue-app',
    data: {
        status: 0, // 显示数据
        selectMsg: '',
        obj: {
            scId: '',
            scName: '',
            isDelete: 0,
            primaryClassification:{pcId:''}
        },
        primaryClassifications: []
    },
    methods: {
        reload: doReload,
        openModal: doOpenModal,
        update: doUpdate,
        add: doAdd
    },
    created: function () {
        loadPrimaryClassifications();
    }
});