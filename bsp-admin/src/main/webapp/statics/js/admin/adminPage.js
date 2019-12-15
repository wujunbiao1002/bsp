$(function() {
    $('#input-form-add').bootstrapValidator({
        message: '无效输入',
        //excluded:[":hidden",":disabled",":not(visible)"] ,//bootstrapValidator的默认配置
        excluded: ':disabled',//关键配置，表示只对于禁用域不进行验证，其他的表单元素都要验证
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',//显示验证成功或者失败时的一个小图标
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            id: {
                message: '限3-25个英文字母',//默认提示信息
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    },
                    regexp: {
                        regexp: /^[a-zA-Z]{3,25}$/,
                        message: '限3-25个英文字母'
                    },

                    threshold :  3 , //有3字符以上才发送ajax请求，（input中输入一个字符，插件会向服务器发送一次，设置限制，6字符以上才开始）
                    remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}
                        url: '/admin/valid',//验证地址
                        message: '用户已存在',//提示消息
                        delay :  1500,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
                        type: 'POST'//请求方式

                    }

                }
            },
            name: {
                message: '不能为空',//默认提示信息
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    },
                    stringLength: {
                        min: 1,
                        max: 50,
                        message: '限50个字符'
                    }
                }
            },
            phone: {
                message: '不能为空',//默认提示信息
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    },
                    regexp: {
                        regexp: /^1[3|4|5|7|8][0-9]{9}$/,
                        message: '请输入正确的手机号'
                    }

                }
            },
            password: {
                message: '不能为空',//默认提示信息
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    },
                    stringLength: {
                        min: 6,
                        max: 20,
                        message: '限6-20个字符'
                    },
                    regexp: {
                        regexp: /^[a-zA-Z0-9_\.]+$/,
                        message: '密码只能由字母、数字、点和下划线组成。'
                    }

                }
            },
            confirm: {
                message: '两次输入的密码不一致',//默认提示信息
                validators: {
                    identical: {
                        field: 'password',
                        message: '两次输入的密码不一致'
                    },
                    notEmpty: {
                        message: '不能为空'
                    }
                }
            }
        }
    });
    $('#input-form-update').bootstrapValidator({
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
                        message: '限50个字符'
                    }
                }
            },
            phone: {
                message: '不能为空',//默认提示信息
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    },
                    regexp: {
                        regexp: /^1[3|4|5|7|8][0-9]{9}$/,
                        message: '请输入正确的手机号'
                    }

                }
            },
            password: {
                message: '不能为空',//默认提示信息
                validators: {
                    stringLength: {
                        min: 6,
                        max: 20,
                        message: '限6-20个字符'
                    },
                    regexp: {
                        regexp: /^[a-zA-Z0-9_\.]+$/,
                        message: '密码只能由字母、数字、点和下划线组成。'
                    }

                }
            },
            confirm: {
                message: '两次输入的密码不一致',//默认提示信息
                validators: {
                    callback: {
                        message: '两次输入的密码不一致',
                        callback:function(value, validator,$field){
                            console.log(!vue_app.obj.aPassword)
                            if (!vue_app.obj.aPassword) {
                                return true;
                            }
                            return value==vue_app.obj.aPassword ? true : false;
                        }
                    }
                }
            }
        }
    });
	var tab = $("#data-list").bootstrapTable({
		url: '/admin/page',
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
			{field: 'aUuid', title: '用户ID',sortable:false},
			{field: 'aId', title: '用户名',sortable:false},
			{field: 'aName', title: '姓名',sortable:false},
			{field: 'aPhone', title: '手机号',sortable:false},
			{field: 'aLevel', title: '类型',formatter:levelFormatter,sortable:false},
			{title: '冻结',formatter:lockFormatter},
			{title: '操作',formatter:operationFormatter},
		],
		/*onLoadSuccess: toggleFormatter,
		onToggle: toggleFormatter*/
		onLoadSuccess: switchFormatter,
		onToggle: switchFormatter,
		onColumnSwitch: switchFormatter
	})
})

var doReload = function () {
    $("#data-list").bootstrapTable('refresh');
}

var toggleFormatter = function(){
	$(".switch").bootstrapToggle({
		size:'mini',
		onstyle: 'danger',
		offstyle: 'default'
	}).bind("change",freezeChange);
}

var freezeChange = function () {
	$.ajax({
		type: "get",
		url: "/admin/lockOrUnlock",
		data:{
			uuid: $(this)[0].id
		},
		success: function(data){
            if (data.code==401){
                window.location.href='/login';
                return;
            }
            if (data.code!=0){
                alert(data.msg);
                doReload();
            }
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			alert("操作失败");
			$("#data-list").bootstrapTable('refresh');
		}
	});
}

// 解决表格视图切换时bootstrap switch样式失效问题
var switchFormatter = function(){
	$(".switch").bootstrapSwitch({
		size:'mini',
		onColor: 'danger',
		offColor: 'primary'
	}).bind('switchChange.bootstrapSwitch',freezeChange);
} 

var levelFormatter = function(value,index,row){
    switch (value) {
        case 0:
            return '<span class="label label-primary">永久</span>';
            break;
        case 1:
            return '<span class="label label-info">超级</span>';
            break;
        case 2:
            return '<span class="label label-warning">普通</span>';
            break;
    }
    return '<span class="label label-default">未知</span>';
}
var detailFormatter = function(index,row){
	var nickname = row.details.uNickname?row.details.uNickname:'无';
	var gender = row.details.uSex?row.details.uSex:'无';
	var phone = row.details.uPhone?row.details.uPhone:'无';
	var address = row.details.uAddress?row.details.uAddress:'无';
	var wechat = row.details.uWechat?row.details.uWechat:'无';
	var qq = row.details.uQq?row.details.uQq:'无';
	var signature = row.details.uSignature?row.details.uSignature:'无';
    return '<ul class="media-list">' +
        '  <li class="media">' +
        '    <div class="media-body">' +
        '      <h4 class="media-heading">'+row.user.mail +'</h4>' +
        '      ID：' +row.user.aUuid+
        '      <br/>昵称：' +nickname+
        '      <br/>性别：' +gender+
        '      </br>手机：' +phone+
        '      </br>QQ：' +qq+
        '      </br>微信：' +wechat+
        '      </br>地址：' +address+
        '      </br>个性签名：' +signature+
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
		search: params.search
	};
	return p;
}

var lockFormatter = function(value,row,index){
	return '<input class="switch" type="checkbox" id="'+ row.aUuid +'"'+ (row.isDelete==1?'checked':'') + '/>';
}

var operationFormatter = function(value,row,index){
	return '<button onclick="doOpenUpdateModal(\''+row.aUuid+'\')" type="button" class="btn btn-default btn-xs" title="编辑"><i class="fa fa-edit" aria-hidden="true"></i> 编辑</button>';
}

var doOpenAddModal = function () {//打开添加模态框
    vue_app.obj={ // 表单参数
        aId: '',
        aPassword: '',
        aName: '',
        aPhone: '',
        aLevel:-1
    };
    // 初始化表单校验状态
    $('#input-form-add').data('bootstrapValidator')
        .updateStatus('id', 'NOT_VALIDATED', null)
        .updateStatus('name', 'NOT_VALIDATED', null)
        .updateStatus('password', 'NOT_VALIDATED', null)
        .updateStatus('confirm', 'NOT_VALIDATED', null)
        .updateStatus('phone', 'NOT_VALIDATED', null)
    vue_app.selectMsg='';
    vue_app.confirmPwd='';
    $('#input-modal-add').modal('show');
}

var doOpenUpdateModal = function (id) {//打开编辑模态框
    console.log(id)
    if (id){ //修改操作，查询记录
       $.ajax({
            url: '/admin/findByKey',
            dataType : "JSON",
            data: {
                uuid: id
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
                vue_app.obj=data.obj;
            }
        })
    } else {// 初始化
        vue_app.obj={ // 表单参数
            aId: '',
            aPassword: '',
            aName: '',
            aPhone: '',
            aLevel:-1
        };
    }
    // 初始化表单校验状态
    $('#input-form-update').data('bootstrapValidator')
        .updateStatus('name', 'NOT_VALIDATED', null)
        .updateStatus('password', 'NOT_VALIDATED', null)
        .updateStatus('confirm', 'NOT_VALIDATED', null)
        .updateStatus('phone', 'NOT_VALIDATED', null)
    vue_app.selectMsgUpdate='';
    vue_app.confirmPwd='';
    $('#input-modal-update').modal('show');
}

var doUpdate=function (id) {
    $('#input-form-update').data('bootstrapValidator').validate();
    if (!$('#input-form-update').data('bootstrapValidator').isValid()) {// 判断是否验证通过
        return;
    }
    if (vue_app.obj.aLevel==-1) {
        vue_app.selectMsgUpdate='（请选择类型）';
        return;
    } else {
        vue_app.selectMsgUpdate='';
    }
    if (vue_app.obj.aPassword) {
        if (vue_app.confirmPwd!=vue_app.obj.aPassword) {
            return;
        };
    }
    confirm("确认保存?", function () {
        $.ajax({
            url: '/admin/update',
            type: 'post',
            dataType: "JSON",
            contentType: "application/json",
            data: JSON.stringify(vue_app.obj),
            success: function (data) {
                if (data.code == 401) {
                    window.location.href = '/login';
                    return;
                }
                if (data.code != 0) {
                    alert(data.msg);
                }
                doReload();
                $('#input-modal-update').modal('hide');
            }
        })
    })
}
var doAdd=function () {
    $('#input-form-add').data('bootstrapValidator').validate();
    if (!$('#input-form-add').data('bootstrapValidator').isValid()) {// 判断是否验证通过
        return;
    }
    if (vue_app.obj.aLevel==-1) {
        vue_app.selectMsg='（请选择类型）';
        return;
    } else {
        vue_app.selectMsg='';
    }
    confirm("确认保存?", function () {
        $.ajax({
            url: '/admin/add',
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
                $('#input-modal-add').modal('hide');
            }
        })
    });

}

var vue_app=new Vue({
    el: '#vue-app',
    data: {
        status: 0, // 显示数据
        obj: { // 表单参数
            aId: '',
            aPassword: '',
            aName: '',
            aPhone: '',
            aLevel:-1
        },
        selectMsg: '',
        selectMsgUpdate: '',
        confirmPwd: '',
    },
    methods: {
        reload: doReload,
        openAddModal: doOpenAddModal,
        add: doAdd,
        update: doUpdate
    },
    created: function () {
    }
});