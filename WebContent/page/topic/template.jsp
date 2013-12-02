<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><c:if test="${topic!=null}">${topic.title}-</c:if>话题-Tiny</title>
</head>
<link href="${ctx}/css/bootstrap/bootstrap.css" rel="stylesheet">
<link href="${ctx}/css/common.css" rel="stylesheet">
<link href="${ctx}/css/topic/style.css" rel="stylesheet">
<script src="${ctx}/script/jquery.js"></script>
<script src="${ctx}/script/bootstrap.js"></script>
<body>
<div class="container">
	<tiles:insertAttribute name="header"/>
	<div class="content">
		<div class="col-lg-9">
			<tiles:insertAttribute name="left"/>
		</div>
		<div class="col-lg-3">
			<tiles:insertAttribute name="right"/>
		</div>
	</div>
	<tiles:insertAttribute name="footer"/>
</div>
</body>
</html>