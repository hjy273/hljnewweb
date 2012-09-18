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
	<template:titile value="ά���������" />
	<template:formTable>
		<html:form action="/datumAction.do?method=approve" styleId="form">
			<html:hidden property="id" value="${datumInfo.id}"/>
			<template:formTr name="����">
				<apptag:assorciateAttr table="datum_type" label="name" key="id" keyValue="${datumInfo.typeId}" />
			</template:formTr>
			<template:formTr name="����">
				${datumInfo.name}
			</template:formTr>
			<template:formTr name="˵��">
				${datumInfo.info}
			</template:formTr>
			<template:formTr name="������ʷ">
				<c:choose>
					<c:when test="${empty addOnes}">
						����ʷ����
					</c:when>
					<c:otherwise>
						<table width="100%">
							<tr>
								<td align="center"><b>����</b></td>
								<td align="center"><b>������</b></td>
								<td align="center"><b>��������</b></td>
								<td align="center"><b>״̬</b></td>
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
													���
												</c:when>
												<c:otherwise>
													����
												</c:otherwise>
											</c:choose>
										</td>
									</tr>
								</c:if>
							</c:forEach>
								<tr>
									<td colspan=3></td>
									<td align="right"><a href="javascript:more();">����..</a></td>
								</tr>
						</table>
					</c:otherwise>
				</c:choose>
			</template:formTr>
			<template:formTr name="��������">
				<c:choose>
					<c:when test="${empty addOnesApprove}">
						δ�ϴ�����
					</c:when>
					<c:otherwise>
						<table width="100%">
							<tr>
								<td align="center"><b>����</b></td>
								<td align="center"><b>������</b></td>
								<td align="center"><b>��������</b></td>
								<td align="center"><b>״̬</b></td>
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
													���
												</c:when>
												<c:otherwise>
													����
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
			<template:formTr name="���">
				<input type="radio" name="approve" value="1" checked>ͨ�� 
				<input type="radio" name="approve" value="0">��ͨ��
			</template:formTr>
			<template:formSubmit>
				<td>
					<html:submit property="action" styleClass="button">�ύ</html:submit>
				</td>
				<td>
					<input type="button" class="button" value="����" onclick="history.back()" />
				</td>
			</template:formSubmit>
		</html:form>
	</template:formTable>
</body>
</html>