<%@include file="/common/header.jsp"%>
<html>
<head>
	<title></title>
	
	<script type='text/javascript' src='${ctx}/linepatrol/js/change_style.js'></script>
	<script type='text/javascript'>
		function more(){
			window.location.href = '${ctx}/linepatrol/resource/fileDetail.jsp';
		}		    
	</script>
</head>
<body onload="changeStyle()">
	<template:titile value="资料更新" />
	<template:formTable>
		<template:formTr name="历史资料">
			<c:choose>
				<c:when test="${empty addOnes}">
					无资料历史
				</c:when>
				<c:otherwise>
					<table width="100%">
						<tr>
							<td align="center"><b>名称</b></td>
							<td align="center"><b>更新人</b></td>
							<td align="center"><b>更新日期</b></td>
							<td align="center"><b>状态</b></td>
						</tr>
						<c:forEach var="f" items="${addOnes}" varStatus="status">
							<tr>
								<td align="center"><a href="${ctx}/downloadAction.do?fileid=${f.FILEID}">${f.ORIGINALNAME}</a></td>
								<td align="center">
									<apptag:assorciateAttr table="userinfo" label="username" key="userid" keyValue="${f.UPLOADER}" />
								</td>
								<td align="center">
									<bean:write name="f" property="ONCREATE" format="yyyy/MM/dd" />
								</td>
								<td align="center">
									<c:choose>
										<c:when test="${f.IS_USABLE eq '1'}">
											入库
										</c:when>
										<c:otherwise>
											新增
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</c:forEach>
					</table>
				</c:otherwise>
			</c:choose>
		</template:formTr>
		<template:formSubmit>
			<td>
				<input type="button" class="button" value="返回" onclick="history.back()" />
			</td>
		</template:formSubmit>
	</template:formTable>
</body>
</html>