<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<link href="${ctx}/css/bootstrap/glyphicon.css" rel="stylesheet">
<script src="${ctx}/script/md5.js"></script>
<script>
$(function(){
	$('#form').bind('submit',function(){
		$('#password').val(hex_md5($('#password').val()).toUpperCase());
        $('#confirmPassword').val(hex_md5($('#confirmPassword').val()).toUpperCase());
        if($('.has-error',this).length>0) {
            return false;
        }
    });
	$('#password').bind('blur',checkConfirmPassword);
	$('#confirmPassword').bind('blur',checkConfirmPassword);
	$('#username').bind('blur',checkUserName);
});

function checkConfirmPassword() {
	if($('#confirmPassword').val()!='') {
		var input = $('#confirmPassword');
		if(input.val()!=$('#password').val()) {
			input.parent().parent().addClass('has-error');
			$('.error-span',input.parent().parent()).html('确认密码必须和密码保持一致！');
		} else {
			input.parent().parent().removeClass('has-error');
			$('.error-span',input.parent().parent()).html('');
		}
	}
}

function checkUserName() {
	var input = this;
	var value = input.value;
	if(value.length==0) {
        $(input).parent().parent().removeClass('has-error');
        $('.error-span',$(input).parent().parent()).html('');
    }
    $.ajax({
       type: "GET",
       url: "${ctx}/user/check/"+value,
       success: function(msg){
          if(msg > 0) {
             $(input).parent().parent().addClass('has-error');
             $('.error-span',$(input).parent().parent()).html('此用户名已经存在！');
          } else {
             $(input).parent().parent().removeClass('has-error');
             $('.error-span',$(input).parent().parent()).html('');
          }
       }
    });
}

</script>
<div class="content">
	<div class="panel panel-default panel-center">
		<div class="panel-heading">
			<h4>
				欢迎加入<a href="${ctx}/home">Tiny</a>!
			</h4>
		</div>
		<div class="panel-body">
			<form id="form" class="form-horizontal" role="form"
				action="${ctx}/user/add" method="post">
				<div class="form-group">
					<label class="control-label col-lg-3" for="username">用户名</label>
					<div class="col-lg-9">
						<input type="text" required="true" class="form-control input-lg"
							name="username" id="username" placeholder="请输入您的用户名">
						<span class="error-span"></span>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-lg-3" for="nickname">昵称</label>
					<div class="col-lg-9">
						<input type="text" required="true" class="form-control input-lg"
							name="nickname" id="nickname" placeholder="请输入您的昵称">
						<span class="error-span"></span>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-lg-3" for="password">密码</label>
					<div class="col-lg-9">
						<input type="password" required="true" class="form-control input-lg"
							name="password" id="password" placeholder="请输入登录密码">
						<span class="error-span"></span>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-lg-3" for="confirmPassword">确认密码</label>
					<div class="col-lg-9">
						<input type="password" required="true" class="form-control input-lg"
							id="confirmPassword" placeholder="请确认登录密码">
						<span class="error-span"></span>
					</div>
				</div>
				<div class="form-group">
					<div class="register-btn-div">
						<button type="submit" class="btn btn-success btn-large">注册</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
