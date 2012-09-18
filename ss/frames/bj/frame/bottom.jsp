<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>bottom</title>
		<link href="../css/header.css" rel="stylesheet" type="text/css" />
		
	</head>

	<body>
		<div class="down">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" >
				<tr>
					<td width="3%" align="center">
						<img src="../images/down_arrow.jpg" width="13" height="23" />
					</td>
					<td width="97%" height="23" style="font-size:12px;color:#FFFFFF;">
					<c:if test="${REGION_ROOT =='110000' }">
						${copyright } &nbsp;&nbsp; 通讯地址：北京市东城区东直门南大街7号 联系电话：13911996699
						52186699 传真：64075266 邮政编码：100007    &nbsp;&nbsp;&nbsp;&nbsp;  		[系统由<a style="color:#FFFFFF;" onclick="window.open('http://www.cabletech.com.cn');" href="#">北京鑫干线</a>提供]
					</c:if>
					<c:if test="${REGION_ROOT !='110000'}">
						${copyright } &nbsp;&nbsp;&nbsp;技术支持：010-82893092  售后服务：0551-5327532 
					</c:if>	
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>
