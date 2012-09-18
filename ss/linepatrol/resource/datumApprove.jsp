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
<body onload="changeStyle();">
	<template:titile value="维护资料审核" />
	<template:formTable>
		<html:form action="/datumAction.do?method=approve" styleId="form">
			<html:hidden property="id" value="${datumInfo.id}"/>
			<template:formTr name="分类">
				<apptag:assorciateAttr table="datum_type" label="name" key="id" keyValue="${datumInfo.typeId}" />
			</template:formTr>
			<template:formTr name="名称">
				${datumInfo.name}
			</template:formTr>
			<template:formTr name="说明">
				${datumInfo.info}
			</template:formTr>
			<template:formTr name="资料历史">
				<c:choose>
					<c:when test="${empty addOnes}">
						无历史资料
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
								<c:if test="${status.index < 3}">
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
								</c:if>
							</c:forEach>
								<tr>
									<td colspan=3></td>
									<td align="right"><a href="javascript:more();">更多..</a></td>
								</tr>
						</table>
					</c:otherwise>
				</c:choose>
			</template:formTr>
			<template:formTr name="更新资料">
				<c:choose>
					<c:when test="${empty addOnesApprove}">
						未上传资料
					</c:when>
					<c:otherwise>
						<table width="100%">
							<tr>
								<td align="center"><b>名称</b></td>
								<td align="center"><b>更新人</b></td>
								<td align="center"><b>更新日期</b></td>
								<td align="center"><b>状态</b></td>
							</tr>
							<c:forEach var="f" items="${addOnesApprove}" varStatus="status">
								<c:if test="${status.index < 3}">
									<tr>
										<td align="center"><a href="${ctx}/downloadAction.do?fileid=${f.FILEID}">${f.ORIGINALNAME}</a></td>
										<td align="center">
											<input type="hidden" name="addOneId" value="${f.ID}" />
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
								</c:if>
							</c:forEach>
						</table>
					</c:otherwise>
				</c:choose>
			</template:formTr>
			<template:formTr name="审核">
				<input type="radio" name="approve" value="1" checked>通过 
				<input type="radio" name="approve" value="0">不通过
			</template:formTr>
			<template:formSubmit>
				<td>
					<html:submit property="action" styleClass="button">提交</html:submit>
				</td>
				<td>
					<input type="button" class="button" value="返回" onclick="history.back()" />
				</td>
			</template:formSubmit>
		</html:form>
	</template:formTable>
</body>
</html>