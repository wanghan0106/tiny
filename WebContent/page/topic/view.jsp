<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script>
function page(p){
	document.location.href='${ctx}/topic/${topic.id}/'+p;
}
</script>
<div>
	<div class="topic-content">
		<div class="user-logo"><a href="${ctx}/user/${topic.user.id}"><img src="${ctx}/user/${topic.user.id}/logo" class="logo-tiny" width="50px;"/></a></div>
		<div class="topic-doc">
			<h4>${topic.title}</h4>
			<legend></legend>
			<div class="topic-remark">来自&nbsp;&nbsp;<a href="${ctx}/user/${topic.user.id}">${topic.user.nickname}</a>&nbsp;&nbsp;&nbsp;&nbsp;${fn:substring(topic.createTime,0,19)}</div>
			<div class="topic-text">${topic.text.content}</div>
			<div class="topic-tail"><span class="topic-reply-number"><b>${topic.replyNumber}</b>回复</span></div>
		</div>
		
	</div>
	<c:if test="${sessionScope.user!=null || fn:length(comments) gt 0}">
	<legend></legend>
	</c:if>
	<c:forEach var="comment" items="${comments}">
	<div class="topic-comment">
		<div class="user-logo"><a href="${ctx}/user/${comment.user.id}"><img src="${ctx}/user/${comment.user.id}/logo" class="logo-tiny" width="50px;"/></a></div>
		<div class="topic-comment-content">
			<div class="topic-comment-remark">
				来自&nbsp;&nbsp;<a href="${ctx}/user/${comment.user.id}">${comment.user.nickname}</a>&nbsp;&nbsp;&nbsp;&nbsp;${fn:substring(comment.createTime,0,19)}
				<c:if test="${sessionScope.user!=null && (comment.user.id==sessionScope.user.id || topic.user.id==sessionScope.user.id)}">
				&nbsp;&nbsp;<a href="${ctx}/topic/comment/del/${comment.id}">删除</a>
				</c:if>
			</div>
			<div class="topic-comment-text">${comment.text.content}</div>
		</div>
	</div>
	</c:forEach>
	<div class="topic-comment-pager">	
	<jsp:include page="/page/pager.jsp"/>
	</div>
	<c:if test="${sessionScope.user!=null}">
	<div class="topic-add-comment">
	<form id="form" class="form-horizontal" role="form" action="${ctx}/topic/comment/add" method="post">
	<input type="hidden" name="topic.id" value="${topic.id}"/>
		<div class="form-group">
			<div class="col-lg-1 input-title">内容</div>
			<div class="col-lg-11 read-input">
				<textarea name="text.content" required="true" class="form-control" rows="6"></textarea>
			</div>
		</div>
		<div class="form-group">
			<div class="col-lg-1">&nbsp;</div>
			<div class="col-lg-11">
				<button type="submit" class="btn btn-success btn-large">回复</button>
			</div>
		</div>
	</form>
	</div>
	</c:if>
</div>
