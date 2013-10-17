<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script src="${ctx}/script/md5.js"></script>
<script>
$(function(){
	$('#form').bind('submit',function(){
		$('#password').val(hex_md5($('#password').val()).toUpperCase());
        if($('.has-error',this).length>0) {
            return false;
        }
    });
    $('#username').bind('blur',hideError);
    $('#password').bind('blur',hideError);
});
function hideError() {
	$('.alert').hide();
}
</script>
<div class="content">
	<c:if test="${error!=null}">
	<div class="row">
	    <div class="alert alert-danger">
	        <button class="close" data-dismiss="alert" type="button">×</button>
	        	${error}
	    </div>
    </div>
    </c:if>
	<div class="panel panel-default panel-login">
		<div class="panel-heading">
			<h4>
				欢迎登录<a href="${ctx}/home">Tiny</a>!
			</h4>
		</div>
		<div class="panel-body">
			<form id="form" class="form-horizontal" role="form" action="${ctx}/user/login"
				method="post">
				<div class="form-group">
					<div class="input-group input-group-lg">
						<span class="input-group-addon"><img class="icon-small" src="${ctx}/images/icons/user.png"/></span>
						<input id="username" name="username" type="text" required="true" class="form-control" placeholder="用户名">
					</div>
				</div>
				<div class="form-group">
					<div class="input-group input-group-lg">
						<span class="input-group-addon"><img class="icon-small" src="${ctx}/images/icons/password.png"/></span>
						<input id="password" name="password" type="password" required="true" class="form-control" placeholder="密码">
					</div>
				</div>
				<div class="form-group">
					<div class="form-btn-group">
						<button type="submit" class="btn btn-success btn-large">登录</button>
						<a href="${ctx}/register"><button type="button" class="btn btn-success btn-large">注册</button></a>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>

