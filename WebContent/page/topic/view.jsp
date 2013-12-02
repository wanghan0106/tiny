<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<div>
	<div>
		<div class="topic-content">
			<div class="user-logo"><a href="${ctx}/user/${topic.user.id}"><img src="${ctx}/user/${topic.user.id}/logo" class="logo-tiny" width="50px;"/></a></div>
			<div class="topic-doc">
				<h4>${topic.title}</h4>
				<legend></legend>
				<div class="topic-remark">来自&nbsp;&nbsp;<a href="${ctx}/user/${topic.user.id}">${topic.user.nickname}</a>&nbsp;&nbsp;&nbsp;&nbsp;${fn:substring(topic.createTime,0,19)}</div>
				<div class="topic-text">${topic.text.content}</div>
			</div>
			
		</div>
		<div class="topic-comment">
			<div class="user-logo"><a href="${ctx}/user/${topic.user.id}"><img src="${ctx}/user/${topic.user.id}/logo" class="logo-tiny" width="50px;"/></a></div>
			<div class="topic-comment-content">
				
			</div>
		</div>
		
	</div>
</div>