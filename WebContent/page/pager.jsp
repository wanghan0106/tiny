<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:if test="${pager.totalPage > 1}">
<span class="pager">
	<a href="javascript:void(0)" onclick="page(1)"><span>首页</span></a>&nbsp;&nbsp;
	<c:if test="${pager.page > 1}">
	<c:set var="prev" value="${pager.page-1}"/>
	<a href="javascript:void(0)" onclick="page(${prev})"><span>上一页</span></a>&nbsp;&nbsp;
	</c:if>
	<c:if test="${pager.page == 1}">
	<span>上一页</span>&nbsp;&nbsp;
	</c:if>
	<c:forEach var="p" items="${pager.pages}">
		<c:if test="${p==pager.page}">
		<span>${pager.page}<span class="sr-only"></span></span>&nbsp;&nbsp;
		</c:if>
		<c:if test="${p!=pager.page}">
		<a href="javascript:void(0)" onclick="page(${p})"><span>${p}</span></a>&nbsp;&nbsp;
		</c:if>
	</c:forEach>
	<c:if test="${pager.page < pager.totalPage}">
	<c:set var="next" value="${pager.page+1}"/>
	<a href="javascript:void(0)" onclick="page(${next})"><span>下一页</span></a>&nbsp;&nbsp;
	</c:if>
	<c:if test="${pager.page == pager.totalPage}">
	<span>下一页</span>&nbsp;&nbsp;
	</c:if>
	<a href="javascript:void(0)" onclick="page(${pager.totalPage})"><span>末页</span></a>&nbsp;&nbsp;
</span>
</c:if>