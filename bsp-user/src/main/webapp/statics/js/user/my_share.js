jQuery(function () {
    initImageUploader();
});

var initImageUploader = function () {
    var editApplyUploader = imageWebUploader({
        container: '#editApply-image-uploader', // 容器
        server: '/share/cover', // 服务器
        fileVal:'cover',
        fileNumLimit: 1, // 文件数限制
        fileSizeLimit: 1024*1024,    // 文件总大小1 M
        fileSingleSizeLimit: 1024*1024,    // 单个文件大小1 M
        showContinueBtn: false, // 显示继续添加文件按钮
        multiple: false,
        tipText: '不修改封面请直接点击下一步</br>可将图片拽或截图粘贴，只允许上传1张图片，且大小不超过1024KB',
        accept: { // 指定接受哪些类型的文件。
            title: 'Images',
            extensions: 'gif,jpg,jpeg,bmp,png',
            mimeTypes: 'image/gif,image/jpg,image/jpeg,image/bmp,image/png,'
        }

    });
    var newApplyUploader = imageWebUploader({
        container: '#newApply-image-uploader', // 容器
        server: '/share/cover', // 服务器
        fileVal:'cover',
        fileNumLimit: 1, // 文件数限制
        fileSizeLimit: 1024*1024,    // 文件总大小1 M
        fileSingleSizeLimit: 1024*1024,    // 单个文件大小1 M
        showContinueBtn: false, // 显示继续添加文件按钮
        multiple: false,
        tipText: '可将图片拽或截图粘贴，只允许上传1张图片，且大小不超过1024KB',
        accept: { // 指定接受哪些类型的文件。
            title: 'Images',
            extensions: 'gif,jpg,jpeg,bmp,png',
            mimeTypes: 'image/gif,image/jpg,image/jpeg,image/bmp,image/png,'
        }

    });
}

var loadSharingPage = function (app) {
    $.ajax({
        url: '/shared_book/page',
        data: app.sharingPageParams,
        success: function (data) {
            if (data.code==0) {
                app.sharingPage=data.page;
                // 设置分页工具条,getPageIndex在common.js中
                var pageIndex = getPageIndex(app.sharingPageBar.pageBarSize, data.page.currPage, data.page.totalPage);
                app.sharingPageBar.startPageIndex=pageIndex.startPageIndex;
                app.sharingPageBar.endPageIndex=pageIndex.endPageIndex;

            } else if (data.code==401) { //未登录
                window.location.href='/login'
            } else {
                alert(data.msg);
            }
        }
    });
}

var loadApplyingPage = function (app) {
    $.ajax({
        url: '/share/page',
        data: app.applyingPageParams,
        success: function (data) {
            if (data.code==0) {
                app.applyingPage=data.page;
                // 设置分页工具条,getPageIndex在common.js中
                var pageIndex = getPageIndex(app.applyingPageBar.pageBarSize, data.page.currPage, data.page.totalPage);
                app.applyingPageBar.startPageIndex=pageIndex.startPageIndex;
                app.applyingPageBar.endPageIndex=pageIndex.endPageIndex;

            } else if (data.code==401) { //未登录
                window.location.href='/login'
            } else {
                alert(data.msg);
            }
        }
    });
}

/**
 * 裁剪文本，超过部分用 ‘......’代替
 * @param text 文本
 * @param length 目标长度
 */
var textCut = function (text, length) {
    if (text.length > length) {
        return text.substring(0, 99)+'......';
    } else {
        return text;
    }
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
                my_share_app.primaryClassifications=data.list;
            }

        }
    })

}

var loadSecondaryClassifications=function () {
    var pcId = my_share_app.pcId;
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
                    my_share_app.secondaryClassifications=data.list;
                }

            }
        })
    } else {
        my_share_app.secondaryClassifications=[];
    }
    my_share_app.newApply.secondaryClassification.scId=0;
    applyCheck();

}

var loadEditRecord = function (clbId) {
    $.ajax({
        url: '/share/findByClbId',
        data: {
            clbId: clbId
        },
        success: function (data) {
            if (data.code == 0) {
                my_share_app.editApply = data.record;
                my_share_app.pcId = data.record.secondaryClassification.primaryClassification.pcId;
                my_share_app.loadSecondaryClassifications();
            } else if (data.code==401){
                window.location.href='/login';
                return;
            } else if (data.code!=0){
                alert(data.msg);
            }

        }
    })
}

var applyUpdateCheck = function () {
    var obj = my_share_app.editApply;
    var msg = my_share_app.editApplyMsg;
    var result = true;
    if (!obj.clbAuthor.trim()) {
        result = false;
        msg.author = '不能为空';
    } else {
        msg.author = '';
    }
    if (!obj.clbComment.trim()) {
        result = false;
        msg.comment = '不能为空';
    } else {
        msg.comment = '';
    }
    if (obj.clbDuration < 7 || obj.clbDuration > 365) {
        result = false;
        msg.duration = '不能为空，且大于7小于365';
    } else {
        msg.duration = '';
    }
    if (!obj.clbName.trim()) {
        result = false;
        msg.name = '不能为空';
    } else {
        msg.name = '';
    }
    if (obj.clbNumber < 1 || obj.clbNumber > 2147483647) {
        result = false;
        msg.number = '不能为空，且大于0小于2147483647';
    } else {
        msg.number = '';
    }
    if (!obj.clbPublishing.trim()) {
        result = false;
        msg.publishing = '不能为空';
    } else {
        msg.publishing = '';
    }
    if (!obj.isbn.trim()) {
        result = false;
        msg.isbn = '不能为空';
    } else {
        msg.isbn = '';
    }
    var e = new RegExp("^[1][3,4,5,6,7,8,9][0-9]{9}$");
    if (!e.test(obj.phone)) {
        result = false;
        msg.phone = '请填写正确的手机号';
    } else {
        msg.phone = '';
    }
    if (my_share_app.pcId == 0) {
        result = false;
        msg.primaryClassification = '请选择一级分类';
    } else {
        msg.primaryClassification = '';
    }
    if (obj.secondaryClassification.scId == 0) {
        result = false;
        msg.secondaryClassification = '请选择二级分类';
    } else {
        msg.secondaryClassification = '';
    }
    return result;
}

var applyCheck = function () {
    var obj = my_share_app.newApply;
    var msg = my_share_app.newApplyMsg;
    var result = true;
    if (!obj.clbAuthor.trim()) {
        result = false;
        msg.author = '不能为空';
    } else {
        msg.author = '';
    }
    if (!obj.clbComment.trim()) {
        result = false;
        msg.comment = '不能为空';
    } else {
        msg.comment = '';
    }
    if (!obj.clbDuration.trim()  || obj.clbDuration < 7 || obj.clbDuration > 365) {
        result = false;
        msg.duration = '不能为空，且大于7小于365';
    } else {
        msg.duration = '';
    }
    if (!obj.clbName.trim()) {
        result = false;
        msg.name = '不能为空';
    } else {
        msg.name = '';
    }
    if (!obj.clbNumber.trim() || obj.clbNumber < 1 || obj.clbNumber > 2147483647) {
        result = false;
        msg.number = '不能为空，且大于0小于2147483647';
    } else {
        msg.number = '';
    }
    if (!obj.clbPublishing.trim()) {
        result = false;
        msg.publishing = '不能为空';
    } else {
        msg.publishing = '';
    }
    if (!obj.isbn.trim()) {
        result = false;
        msg.isbn = '不能为空';
    } else {
        msg.isbn = '';
    }
    var e = new RegExp("^[1][3,4,5,6,7,8,9][0-9]{9}$");
    if (!e.test(obj.phone)) {
        result = false;
        msg.phone = '请填写正确的手机号';
    } else {
        msg.phone = '';
    }
    if (my_share_app.pcId == 0) {
        result = false;
        msg.primaryClassification = '请选择一级分类';
    } else {
        msg.primaryClassification = '';
    }
    if (obj.secondaryClassification.scId == 0) {
        result = false;
        msg.secondaryClassification = '请选择二级分类';
    } else {
        msg.secondaryClassification = '';
    }
    return result;
}

var apply = function () {
    var completed = applyCheck();
    if (!completed) {
        return;
    }
    confirm("提交申请?", function () {
        $.ajax({
            url: '/share/apply',
            type: 'post',
            dataType : "JSON",
            contentType:"application/json",
            data: JSON.stringify(my_share_app.newApply),
            success: function (data) {
                if(data.code==0){
                    my_share_app.newApplyStep = 2;
                } else if (data.code==1003) { // 封面未上传
                    alert(data.msg);
                    my_share_app.newApplyStep = 0; // 返回封面上传
                } else if (data.code==401){
                    window.location.href='/login';
                    return;
                } else {
                    alert(data.msg);
                }
            }
        })
    })
}

var applyUpdate = function () {
    var completed = applyUpdateCheck();
    if (!completed) {
        return;
    }
    confirm("提交申请?", function () {
        $.ajax({
            url: '/share/update',
            type: 'post',
            dataType : "JSON",
            contentType:"application/json",
            data: JSON.stringify(my_share_app.editApply),
            success: function (data) {
                if(data.code==0){
                    alert("更新成功");
                    my_share_app.applyingPageShow.showEdit = false;
                    my_share_app.loadApplyingPage(my_share_app);
                } else if (data.code==401){
                    window.location.href='/login';
                    return;
                } else {
                    alert(data.msg);
                }
            }
        })
    })
}

var openShare = function (lbId) {
    confirm("开启共享?", function () {
        $.ajax({
            url: '/shared_book/open',
            data: {
                lbId: lbId
            },
            success: function (data) {
                if(data.code==0) {
                    loadSharingPage(my_share_app);
                }else if (data.code==401){
                    window.location.href='/login';
                    return;
                } else {
                    alert(data.msg);
                }
            }
        })
    })
}

var closeShare = function (lbId) {
    confirm("关闭共享?", function () {
        $.ajax({
            url: '/shared_book/close',
            data: {
                lbId: lbId
            },
            success: function (data) {
                if(data.code==0) {
                    loadSharingPage(my_share_app);
                }else if (data.code==401){
                    window.location.href='/login';
                    return;
                } else {
                    alert(data.msg);
                }
            }
        })
    })
}

var deleteShare = function (lbId) {
    confirm("删除共享?", function () {
        $.ajax({
            url: '/shared_book/delete',
            data: {
                lbId: lbId
            },
            success: function (data) {
                if(data.code==0) {
                    loadSharingPage(my_share_app);
                }else if (data.code==401){
                    window.location.href='/login';
                    return;
                } else {
                    alert(data.msg);
                }
            }
        })
    })
}


var cancelShare = function (clbId) {
    confirm("删除共享?", function () {
        $.ajax({
            url: '/share/cancel',
            data: {
                clbId: clbId
            },
            success: function (data) {
                if(data.code==0) {
                    loadSharingPage(my_share_app);
                }else if (data.code==401){
                    window.location.href='/login';
                    return;
                } else {
                    alert(data.msg);
                }
            }
        })
    })
}

var cancelEditApply = function () {
    my_share_app.applyingPageShow.showEdit = false;
    my_share_app.applyingPageShow.editApplyStep = 0;
}

/**
 * 初始化添加共享页面
 */
var initNewApplyForm = function () {
    initImageUploader();
    my_share_app.newApplyStep = 0;
    my_share_app.newApply= {
        clbName: '', // 书名
        clbAuthor: '', // 作者
        clbPublishing: '', // 出版社
        isbn: '', // isbn号
        clbDuration: '', // 时长（天）
        clbNumber: '', // 数量（本）
        clbComment: '', // 评价
        phone: '', // 手机号
        secondaryClassification: {scId: 0} // 二级分类
    };
    my_share_app.newApplyMsg= { // 错误提示文本
        name: '', // 书名
        author: '', // 作者
        publishing: '', // 出版社
        isbn: '', // isbn号
        duration: '', // 时长（天）
        number: '', // 数量（本）
        comment: '', // 评价
        phone: '', // 手机号
        primaryClassification:  '', // 一级分类
        secondaryClassification: '' // 二级分类
    };
    my_share_app.pcId = 0;
}

var init = function () {
    loadSharingPage(this);
    loadPrimaryClassifications();
    //loadSecondaryClassifications();
}

var my_share_app = new Vue({
    el: '#my_share_app',
    data: {
        showPage: 0, // 0-共享中 1-正在申请 2-添加申请
        sharingPage: {}, // 页数据
        sharingPageParams: { // 正在共享页面参数
            limit: 3, // 页大小
            pageNumber: 1 // 当前页
        },
        sharingPageBar: { // 共享中分页工具条参数
            pageBarSize: 3,
            startPageIndex: 1,
            endPageIndex: 1,
        },
        applyingPage: {}, // 页数据
        applyingPageParams: { // 正在申请页面参数
            limit: 3, // 页大小
            pageNumber: 1 // 当前页
        },
        applyingPageBar: { // 共享中分页工具条参数
            pageBarSize: 3,
            startPageIndex: 1,
            endPageIndex: 1,
        },
        newApplyStep: 0, // 步骤, 0-上传封面, 1-填写表单, 3-提示成功
        newApply: {
            clbName: '', // 书名
            clbAuthor: '', // 作者
            clbPublishing: '', // 出版社
            isbn: '', // isbn号
            clbDuration: '', // 时长（天）
            clbNumber: '', // 数量（本）
            clbComment: '', // 评价
            phone: '', // 手机号
            secondaryClassification: {scId: 0} // 二级分类
        },
        newApplyMsg: { // 错误提示文本
            name: '', // 书名
            author: '', // 作者
            publishing: '', // 出版社
            isbn: '', // isbn号
            duration: '', // 时长（天）
            number: '', // 数量（本）
            comment: '', // 评价
            phone: '', // 手机号
            primaryClassification: '', // 一级分类
            secondaryClassification: '' // 二级分类
        },
        applyingPageShow: {
            editApplyStep: 0, // 步骤, 0-上传封面, 1-填写表单, 3-提示成功
            showEdit: false
        },
        editApply: {
            clbName: '', // 书名
            clbAuthor: '', // 作者
            clbPublishing: '', // 出版社
            isbn: '', // isbn号
            clbDuration: '', // 时长（天）
            clbNumber: '', // 数量（本）
            clbComment: '', // 评价
            phone: '', // 手机号
            secondaryClassification: {
                scId: 0,
                primaryClassification: {
                    pcId: 0
                }
            } // 二级分类
        },
        editApplyMsg: { // 错误提示文本
            name: '', // 书名
            author: '', // 作者
            publishing: '', // 出版社
            isbn: '', // isbn号
            duration: '', // 时长（天）
            number: '', // 数量（本）
            comment: '', // 评价
            phone: '', // 手机号
            primaryClassification: '', // 一级分类
            secondaryClassification: '' // 二级分类
        },
        pcId: 0,
        primaryClassifications: [],
        secondaryClassifications: []
    },
    methods: {
        switchPage: function (page) {//切换页
            if (page==0){
                this.sharingPageParams.pageNumber = 1;
                this.loadSharingPage(this);
            } else if (page==1) {
                this.applyingPageParams.pageNumber = 1;
                this.applyingPageShow.showEdit = false;
                this.loadApplyingPage(this);
                initImageUploader();
            } else if (page==2) {
                initNewApplyForm();
                initImageUploader();
            }
            this.showPage = page;
        },
        openShare: openShare,
        closeShare: closeShare,
        deleteShare: deleteShare,
        cancelShare: cancelShare,
        cancelEditApply: cancelEditApply,
        apply: apply,
        applyUpdate: applyUpdate,
        applyCheck: applyCheck, // 添加申请的表单验证
        applyUpdateCheck: applyUpdateCheck, // 修改申请的表单验证
        loadSharingPage: loadSharingPage,
        loadApplyingPage: loadApplyingPage,
        loadPrimaryClassifications: loadPrimaryClassifications,
        loadSecondaryClassifications: loadSecondaryClassifications,
        loadEditRecord: loadEditRecord,
        initNewApplyForm: initNewApplyForm,
        goSharingPage: function (currPage) {// 页面跳转
            this.sharingPageParams.pageNumber = currPage;
            this.loadSharingPage(this);
        },
        goApplyingPage: function (currPage) {// 页面跳转
            this.applyingPageParams.pageNumber = currPage;
            this.loadApplyingPage(my_share_app);
        },
        goEditPage: function (clbId) {// 跳转到编辑页
            this.loadEditRecord(clbId);
            this.applyingPageShow.editApplyStep = 0;
            this.applyingPageShow.showEdit = true;
            initImageUploader();
        },
        changeStepOfEditApply: function (step) {
            this.applyingPageShow.editApplyStep = step;
        },
        changeStepOfNewApply: function (step) {
            this.newApplyStep = step;
        },
        millisecondsToDateTime: function (ms){
            return new Date(ms).toLocaleString();
        },
        textCut: textCut
    },
    created: init
});
