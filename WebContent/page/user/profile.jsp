<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<div class="container main-panel profile-container">
	<h4>个人资料</h4>
	<legend></legend>
	<form id="form" class="form-horizontal form-weight" role="form" enctype="multipart/form-data" action="${ctx}/user/save" method="post">
		<input type="hidden" name="id" value="${user.id}">
		<div class="form-group">
			<div class="col-lg-2 input-title">用户名</div>
			<div class="col-lg-10 read-input">${user.username}</div>
		</div>
		<div class="form-group">
			<div class="col-lg-2 input-title">昵称</div>
			<div class="col-lg-10 read-input">${user.nickname}</div>
		</div>
		<div class="form-group">
			<div class="col-lg-2 input-title">常居</div>
			<div class="col-lg-10 read-input">
				<input type="text" class="form-control" name="location" maxlength="100" id="location" value="${user.location}" placeholder="请填写您所在的城市">
			</div>
		</div>
		<div class="form-group">
			<div class="col-lg-2 input-title">性别</div>
			<div class="col-lg-10 read-input">
				<div class="radio">
					<label><input type="radio" name="sex" id="sex1" value="1" <c:if test="${user.sex==1}">checked</c:if>>男</label>
				</div>
				<div class="radio">
					<label><input type="radio" name="sex" id="sex2" value="2" <c:if test="${user.sex==2}">checked</c:if>>女</label>
				</div>
				<div class="radio">
					<label><input type="radio" name="sex" id="sex0" value="0" <c:if test="${user.sex==0}">checked</c:if>>保密</label>
				</div>
			</div>
		</div>
		<div class="form-group">
			<div class="col-lg-2 input-title">兴趣爱好</div>
			<div class="col-lg-10 read-input">
				<textarea name="favorite" maxlength="200" class="form-control" rows="3">${user.favorite}</textarea>
			</div>
		</div>
		<div class="form-group">
			<div class="col-lg-2 input-title">自我介绍</div>
			<div class="col-lg-10 read-input">
				<textarea name="brief" maxlength="200" class="form-control" rows="3">${user.brief}</textarea>
			</div>
		</div>
		<div class="form-group">
			<div class="col-lg-2 input-title">上传头像</div>
			<div class="col-lg-10 read-input">
				<input type="file" name="logoFile"/>
				<p class="help-block">请不要上传超过10M的图片.</p>
			</div>
		</div>
		<div class="form-group">
			<div class="col-lg-2">&nbsp;</div>
			<div class="col-lg-10">
				<button type="submit" class="btn btn-success btn-large">保存</button>
				<a href="${ctx}/user/home"><button class="btn btn-success btn-large">返回</button></a>
			</div>
		</div>
	</form>
</div>
