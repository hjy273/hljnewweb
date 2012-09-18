<%@page contentType="text/html;charset=UTF-8" %>
<%@page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
	<head>
		<title>jBPM4流程部署</title>
	</head>
	<body>
	    <form action="deploy?deploy=deploy" method="POST" enctype="multipart/form-data">
	    	<input type="file" name="upload">
	    	<input type="submit" value="发布">
	    </form>
	    <table width=480 border=1>
	  	    <tr><td colspan="5">流程定义</td></tr>
			<tr>
				<td>ID</td>
				<td>Key</td>
				<td>名称</td>
				<td>版本</td>
				<td>操作</td>
			</tr>
			<c:forEach var="process" items="${process}">
			<tr>
				<td>${process.id}</td>
				<td>${process.key}</td>
				<td>${process.name}</td>
				<td>${process.version}</td>
				<!--  <td><a href="deploy?deploy=query&id=${process.id }">查看</a></td>-->
			</tr>
			</c:forEach>
		</table>
		
		<br>
		<br>
		<table width=480 border=1>
	  	    <tr><td colspan="5">流程实例</td></tr>
			<tr>
				<td>ID</td>
				<td>Key</td>
				<td>状态</td>
				<td>操作</td>
			</tr>
			<c:forEach var="pi" items="${pi}">
			<tr>
				<td>${pi.id}</td>
				<td>${pi.key}</td>
				<td>${pi.state}</td>
				<td><a href="deploy?deploy=drawImage&piId=${pi.id }">追踪流程</a></td>
			</tr>
			</c:forEach>
		</table>
</body>
</html>