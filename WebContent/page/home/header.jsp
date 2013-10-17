<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<div class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand" href="${ctx}"><span class="welcome">Tiny</span></a>
		</div>
		<div class="navbar-collapse collapse">
			<ul class="nav navbar-nav ${ctx}">
				<li class="active"><a href="${ctx}/home">首页</a></li>
				<li><a href="${ctx}/topic">话题</a></li>
				<li><a href="${ctx}/meeting">活动</a></li>
				<li><a href="${ctx}/ask">问答</a></li>
				<li><a href="${ctx}/about">关于我们</a></li>
			</ul>
			<form class="navbar-form navbar-right">
				<c:if test="${sessionScope.user!=null}">
					<span class="welcome">欢迎，${sessionScope.user.nickname}！</span>
					<a href="${ctx}/user/home"><button type="button" class="btn btn-success">个人主页</button></a>
					<a href="${ctx}/logout"><button type="button" class="btn btn-success">注销</button></a>
				</c:if>
				<c:if test="${sessionScope.user==null}">
					<a href="${ctx}/login"><button type="button" class="btn btn-success">登录</button></a>
					<a href="${ctx}/register"><button type="button" class="btn btn-success">注册</button></a>
				</c:if>
			</form>
		</div>
		<!--/.navbar-collapse -->
	</div>
</div>