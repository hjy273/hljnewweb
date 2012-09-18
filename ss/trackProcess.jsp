<%@page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
	<head>
		<title>追踪流程</title>
	</head>
	
	<body>
		<img style="position:absolute;left:0px;top:0px;" src="jpdl?piId=${param.piId }">
		
		<c:forEach var="coor" items="${coordinates}">
			<div style="position:absolute;left:${coor.x };top:${coor.y};width:${coor.width};height:${coor.height};border:2px red solid">
			</div>
		</c:forEach>
		
	</body>
</html>