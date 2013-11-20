<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script>
$(function(){
	<c:if test="${sessionScope.user!=null && sessionScope.user.id!=user.id}">
	$('a','#focus').click(function(){
		$.ajax({
	       type: "POST",
	       url: "${ctx}/user/focus/${user.id}",
	       success: function(msg){
	          if(msg == 0) {
	        	  $('#focus').removeClass('show');
	      		  $('#focus').addClass('hide');
	      		  $('#unfocus').removeClass('hide');
	      		  $('#unfocus').addClass('show');
	          } else if(msg == 404) {
	        	  document.location.href='${ctx}/login';
	          } else {
	        	  document.location.href='${ctx}/404';
	          }
	       }
	    });
		return false;
	});
	$('a','#unfocus').click(function(){
		$.ajax({
	       type: "POST",
	       url: "${ctx}/user/unfocus/${user.id}",
	       success: function(msg){
	          if(msg == 0) {
	        	  $('#focus').removeClass('hide');
	      		$('#focus').addClass('show');
	      		$('#unfocus').removeClass('show');
	      		$('#unfocus').addClass('hide');
	          } else if(msg == 404) {
	        	  document.location.href='${ctx}/login';
	          } else {
	        	  document.location.href='${ctx}/404';
	          }
	       }
	    });
		return false;
	});
	</c:if>
});
</script>
<div class="container">
	<div>
		<img src="${ctx}/user/${user.id}/logo" class="logo" width="224">
	</div>
	<div class="detail">
		<div class="introduce">
			<c:if test="${sessionScope.user.id==user.id}">
				<div class="align-right"><a href="${ctx}/user/profile">[编辑个人资料]</a></div>
			</c:if>
			<c:if test="${sessionScope.user!=null && sessionScope.user.id!=user.id}">
				<div class="align-right<c:if test="${focus==false}"> show</c:if><c:if test="${focus==true}"> hide</c:if>" id="focus"><a href="javascropt:void(0);">[我要关注]</a></div>
				<div class="align-right<c:if test="${focus==false}"> hide</c:if><c:if test="${focus==true}"> show</c:if>" id="unfocus"><a href="javascropt:void(0);">[取消关注]</a></div>
			</c:if>
			<p>昵称: <span class="text-content">${user.nickname}</span></p>
			<p>常居: <span class="text-content">${user.location}</span></p>
			<p>性别: <span class="text-content"><c:if test="${user.sex==0}">保密</c:if><c:if test="${user.sex==1}">男</c:if><c:if test="${user.sex==2}">女</c:if></span></p>
			<p>加入时间: <span class="text-content">${fn:substring(user.registerTime,0,19)}</span></p>
			<p>兴趣爱好: <span class="text-content">${user.favorite}</span></p>
			<p>自我介绍: <span class="text-content">${user.brief}</span></p>
		</div>
	</div>
</div>

