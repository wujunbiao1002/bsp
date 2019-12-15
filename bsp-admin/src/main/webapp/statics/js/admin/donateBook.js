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
                        max: 250,
                        message: '限250个字符'
                    }
                }
            },
            isbn: {
                message: '不能为空',//默认提示信息
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    },
                    digits: {
                        message: '只允许填写数字。'
                    },
                    stringLength: {
                        min: 1,
                        max: 250,
                        message: '限250个字符'
                    }
                }
            },
            number: {
                message: '不能为空',//默认提示信息
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    },
                    greaterThan: {
                        value: 1,
                        message:'数值必须大于0'
                    },
                    lessThan: {
                        value: 2147483647,
                        message:'数值必须小于2147483648'
                    },
                    digits: {
                        message: '只允许填写数字。'
                    }
                }
            },
            source: {
                message: '不能为空',//默认提示信息
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    },
                    stringLength: {
                        min: 1,
                        max: 250,
                        message: '限250个字符'
                    }
                }
            },
            donor: {
                message: '不能为空',//默认提示信息
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    },
                    stringLength: {
                        min: 1,
                        max: 250,
                        message: '限250个字符'
                    }
                }
            },
            phone: {
                message: '不能为空',//默认提示信息
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    },
                    stringLength: {
                        min: 1,
                        max: 20,
                        message: '限20个字符'
                    }
                }
            }
        }
    });
    var tab = $("#data-list").bootstrapTable({
        url: '/donate/page',
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
            //{field: 'dobId', title: 'ID',sortable:true},
            {field: 'dobName', title: '书名',sortable:true},
            {field: 'number', title: '数量(册/套)',sortable:true},
            {field: 'time', title: '捐赠时间',formatter:timeFormatter,sortable:true},
            {field: 'isbn', title: 'ISBN',sortable:false},
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

var detailFormatter = function(index,row){
    var user = row.user ? row.user.mail : '无';
    var phone = row.user ? row.user.phone : '无';
    return '<ul class="media-list">' +
        '  <li class="media">' +
        '    <div class="media-body">' +
        '      <h4 class="media-heading">&laquo;'+row.dobName +'&raquo;'+'</h4>' +
        '      ID：' +row.dobId+
        '      <br/>捐赠时间：' +millisecondsToDateTime(row.time)+
        '      </br>平台用户：' +user+
        '      </br>捐赠人：' +row.donor+
        '      ，手机号：' +row.phone+
        '      </br>捐赠来源：' +row.source+
        '      </br>所属一级分类：' +row.secondaryClassification.primaryClassification.pcName+
        '      </br>所属二级分类：' +row.secondaryClassification.scName+
        '      </br>数量(册/套)：' +row.number+
        '    </div>' +
        '  </li>' +
        '</ul>';
}

var timeFormatter=function (value, row, index) {
    return millisecondsToDateTime(row.time);
}

var operationFormatter = function(value,row,index){
    return '<div id="tab-toolbar" class="btn-group" role="group" >' +
        '<button onclick="doOpenModal('+row.dobId+')" type="button" class="btn btn-primary btn-xs" title="编辑"><i class="fa fa-edit" aria-hidden="true"></i> 编辑</button>' +
        '</div>';
}

var doReload = function () {
    $("#data-list").bootstrapTable('refresh');
}

var doOpenModal = function (id) {//打开模态框
    if (id){ //修改操作，查询记录
        $.ajax({
            url: '/donate/findByKey',
            dataType : "JSON",
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
                    return;
                }
                if (data.obj.secondaryClassification) {
                    // 加载一级分类
                    vue_app.pcId=data.obj.secondaryClassification.primaryClassification.pcId;
                    loadSecondaryClassifications();// 加载二级分类
                }
                vue_app.obj=data.obj;
            }
        })
    } else {// 初始化
        vue_app.obj={
            dobId: '',
            dobName: '',
            isbn: '',
            number:'',
            source:'',
            donor:'',
            time:'',
            phone:'',
            secondaryClassification:{scId:0}
        };
        vue_app.pcId=0;
        loadSecondaryClassifications();// 加载二级分类
    }
    $('#input-modal').modal('show');
    // 初始化
    $('#input-form').data('bootstrapValidator')
        .updateStatus('name', 'NOT_VALIDATED', null)
        .updateStatus('isbn', 'NOT_VALIDATED', null)
        .updateStatus('number', 'NOT_VALIDATED', null)
        .updateStatus('source', 'NOT_VALIDATED', null)
        .updateStatus('donor', 'NOT_VALIDATED', null)
        .updateStatus('phone', 'NOT_VALIDATED', null);
    vue_app.pcMsg='';
    vue_app.scMsg='';
}

var doUpdate=function () {
    $('#input-form').data('bootstrapValidator').validate();
    if (!$('#input-form').data('bootstrapValidator').isValid()) {// 判断是否验证通过
        return;
    }
    if (vue_app.pcId==0) {
        vue_app.pcMsg='（请选择一级分类）';
        return;
    } else {
        vue_app.pcMsg='';
    }
    if (vue_app.obj.secondaryClassification.scId==0) {
        vue_app.scMsg='（请选择二级分类）';
        return;
    } else {
        vue_app.scMsg='';
    }
    confirm("确认保存?", function () {
        $.ajax({
            url: '/donate/update',
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
    if (vue_app.pcId==0) {
        vue_app.pcMsg='（请选择一级分类）';
        return;
    } else {
        vue_app.pcMsg='';
    }
    if (vue_app.obj.secondaryClassification.scId==0) {
        vue_app.scMsg='（请选择二级分类）';
        return;
    } else {
        vue_app.scMsg='';
    }
    confirm("确认保存?", function () {
        $.ajax({
            url: '/donate/add',
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

var loadSecondaryClassifications=function () {
    var pcId = vue_app.pcId;
    if (pcId>0) {
        $.ajax({
            url: '/sc/findByPcId',
            data: {
                pcId: pcId
            },
            success: function (data) {
                if (data.code==401){
                    window.location.href='/login';
                    return;
                } else if (data.code!=0){
                    alert(data.msg);
                } else {
                    vue_app.secondaryClassifications=data.list;
                }

            }
        })
    } else {
        vue_app.secondaryClassifications=[];
    }
    vue_app.obj.secondaryClassification.scId=0;

}

var vue_app=new Vue({
    el: '#vue-app',
    data: {
        status: 0, // 显示数据
        pcMsg: '', // 一级分类提示
        scMsg: '', // 二级分类选择提示
        obj: {
            dobId: '',
            dobName: '',
            isbn: '',
            number:'',
            source:'',
            donor:'',
            time:'',
            phone:'',
            secondaryClassification:{scId:0}
        },
        pcId:0,
        primaryClassifications: [],
        secondaryClassifications: []
    },
    methods: {
        reload: doReload,
        openModal: doOpenModal,
        update: doUpdate,
        add: doAdd,
        loadSecondaryClassifications: loadSecondaryClassifications,
    },
    created: function () {
        loadPrimaryClassifications();
    }
});