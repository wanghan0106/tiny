<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<div class="div-search">
	<form class="form-inline" action="${ctx}/topic/search" method="post">
	  	<div class="input-group">
	      <input type="text" class="form-control" id="search" placeholder="搜索话题">
	      <span class="input-group-btn">
	        <button class="btn btn-default" id="btn-search" type="submit"><img class="icon-small" src="${ctx}/images/icons/search.png"/></button>
	      </span>
	    </div>
	</form>
</div>
<div>
	<h4>热门标签</h4>
	<legend></legend>
	<div>
	<c:forEach var="tag" items="${tags}" varStatus="status">
	<c:if test="${status.index==0}">
		<a href="${ctx}/topic"><span class="font-huge">${tag.name}</span></a>
	</c:if>
	<c:if test="${status.index>0 && status.index<4}">
		<a href="${ctx}/topic"><span class="font-big">${tag.name}</span></a>
	</c:if>
	<c:if test="${status.index>3 && status.index<7}">
		<a href="${ctx}/topic"><span class="font-mid">${tag.name}</span></a>
	</c:if>
	<c:if test="${status.index>6}">
		<a href="${ctx}/topic"><span class="font-small">${tag.name}</span></a>
	</c:if>
	</c:forEach>
	</div>
</div>