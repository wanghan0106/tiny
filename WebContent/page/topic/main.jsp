<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script>
$(function(){
	$('#btn-add').click(function(){
		document.location.href = '${ctx}/topic/add';
	});
});

function page(p){
	document.location.href='${ctx}/topic/page/'+p;
}

</script>

<div>
	<div class="float-left align-left">
		<h4>最新话题</h4>
	</div>
	<div class="float-right align-right">
		<c:if test="${sessionScope.user!=null}">
		<button class="btn btn-default" id="btn-add" type="button"><img class="icon-tiny" src="${ctx}/images/icons/plus.png"/>&nbsp;&nbsp;发表话题</button>
		</c:if>
	</div>
</div>
<div class="div-topic-list">
	<table class="table table-topic">
		<thead>
    	<tr>
        	<td style="width: 60%">话题</td>
            <td style="width: 15%">作者</td>
            <td style="width: 10%">回复</td>
            <td style="width: 15%">最后回复</td>
    	</tr>
    	</thead>
    	<c:forEach var="topic" items="${topicList}">
    	<tr>
    		<td><a href="${ctx}/topic/${topic.id}">${topic.title}</a></td>
    		<td><a href="${ctx}/user/1">${topic.user.nickname}</a></td>
    		<td>${topic.replyNumber}</td>
    		<td>${fn:substring(topic.updateTime,5,16)}</td>
    	</tr>
    	</c:forEach>
    	<tr>
    		<td colspan="4">
    			<jsp:include page="/page/pager.jsp"/>
    		</td>
    	</tr>
	</table>
</div>