<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href='<c:url value="/style/css/bootstrap.min.css"/>' rel="stylesheet" type="text/css" />
<style type="text/css">  

</style>
</head>
<body>
	<!-- 	<form action="">
		<input id="bName" type="text" name="bName" placeholder="图书名称">
		<input id="bAuthor" type="text" name="bAuthor" placeholder="图书作者">
		<input id="bPublishing" type="text" name="bPublishing"
			placeholder="图书出版社"> <input id="bDuration" type="text"
			name="bDuration" placeholder="可借阅时长">
	</form>-->
<div class="form-group">
     <label for="name"  class="col-sm-3 control-label">上传头像</label>
           <div class="col-sm-8">
                <img id="image" class="cover-radius"  src="<c:url value="/images/upload_img.png"/>"
                           width="50%" style="cursor: pointer;" />
                <input id="picture_upload" name="file" type="file" onchange="upload_cover(this)"
                           style="position: absolute; left: 0px; top: 0px; width: 100%; height: 100%; opacity: 0; cursor: pointer;"/>
                <small class="help-block cover-tips" style="color: #dd4b39;display: none;">请上传照片</small>
           </div>
 </div>
</body>

<script type="text/javascript"
	src='<c:url value="/style/js/jquery-1.12.4.js"/>'></script>
<script type="text/javascript"
	src='<c:url value="/style/js/ajaxfileupload.js"/>'></script>
	<script type="text/javascript"
	src='<c:url value="/style/js/bootstrap.min.js"/>'></script>
<script type="text/javascript">  
function upload_cover(obj) {
    ajax_upload(obj.id, function(data) { //function(data)是上传图片的成功后的回调方法
        var isrc = data.relatPath.replace(/\/\//g, '/'); //获取的图片的绝对路径
        $('#image').attr('src', <c:url value="/"/>+isrc).data('img-src', isrc); //给<input>的src赋值去显示图片
    });
}
function ajax_upload(feid, callback) { //具体的上传图片方法
    if (image_check(feid)) { //自己添加的文件后缀名的验证
        $.ajaxFileUpload({
            fileElementId: feid,    //需要上传的文件域的ID，即<input type="file">的ID。
            url: <c:url value="/book_upload"/>, //后台方法的路径
            type: 'post',   //当要提交自定义参数时，这个参数要设置成post
            dataType: 'json',   //服务器返回的数据类型。可以为xml,script,json,html。如果不填写，jQuery会自动判断。
            secureuri: false,   //是否启用安全提交，默认为false。
            async : true,   //是否是异步
            success: function(data) {   //提交成功后自动执行的处理函数，参数data就是服务器返回的数据。
                if (callback) callback.call(this, data);
            },                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
            error: function(data, status, e) {  //提交失败自动执行的处理函数。
                console.error(e);
            }
        });
    }
}
function image_check(feid) { //自己添加的文件后缀名的验证
    var img = document.getElementById(feid);
    return /.(jpg|png|gif|bmp)$/.test(img.value)?true:(function() {
        modals.info('图片格式仅支持jpg、png、gif、bmp格式，且区分大小写。');
        return false;
    })();
}
</script>  
</html>