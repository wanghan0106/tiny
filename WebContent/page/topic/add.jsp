<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<div class="container">
	<h4>发表话题</h4>
	<legend></legend>
	<form id="form" class="form-horizontal" role="form" action="${ctx}/topic/save" method="post">
		<div class="form-group">
			<div class="col-lg-1 input-title">标题</div>
			<div class="col-lg-11 read-input">
				<input type="text" class="form-control" required="true" name="title" maxlength="100" id="title">
			</div>
		</div>
		<div class="form-group">
			<div class="col-lg-1 input-title">标签</div>
			<div class="col-lg-11 read-input">
				<input type="text" class="form-control" required="true" name="tagNames" maxlength="100" id="tag">
			</div>
		</div>
		<div class="form-group">
			<div class="col-lg-1 input-title">内容</div>
			<div class="col-lg-11 read-input">
				<textarea name="text.content" required="true" class="form-control" rows="10"></textarea>
			</div>
		</div>
		<div class="form-group">
			<div class="col-lg-1">&nbsp;</div>
			<div class="col-lg-11">
				<button type="submit" class="btn btn-success btn-large">保存</button>
				<a href="${ctx}/topic"><button type="button" class="btn btn-success btn-large">返回</button></a>
			</div>
		</div>
	</form>
</div>