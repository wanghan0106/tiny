<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<div class="container">
	<div>
		<img src="${ctx}<c:if test="${user.logo==null}">/images/landscape.jpg</c:if><c:if test="${user.logo!=null}">${user.logo}</c:if>" class="logo">
	</div>
	<div class="detail">
		<div class="introduce">
			<c:if test="${sessionScope.user.id==user.id}">
				<div style="text-align:right;"><a href="${ctx}/user/profile">[编辑个人资料]</a></div>
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

