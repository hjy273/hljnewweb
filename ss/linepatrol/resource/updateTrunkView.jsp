<%@include file="/common/header.jsp"%>
<html>
<head>
	<title></title>
	
	<script type='text/javascript' src='/WebApp/linepatrol/js/change_style.js'></script>
	<script type='text/javascript'>
		function more(){
			window.location.href = '/WebApp/linepatrol/resource/fileDetail.jsp';
		}		    
	</script>
</head>
<body onload="changeStyle()">
	<template:titile value="���ϲ鿴" />
	<template:formTable >
		<html:form action="/trunkAction.do?method=updateApprove" styleId="form">
			<input type="hidden" name="type" value="${type}" />
			<input type="hidden" name="id" value="${id}" />
			<c:choose>
				<c:when test="${type eq 'cable'}">
					<template:formTr name="�м̶�����">
						${name}
					</template:formTr>
				</c:when>
				<c:otherwise>
					<template:formTr name="�ܵ�����">
						${name}
					</template:formTr>
				</c:otherwise>
			</c:choose>
			<template:formTr name="��ʷ����">
				<c:choose>
					<c:when test="${empty addOnes}">
						��������ʷ
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
										<td align="center"><a href="/WebApp/downloadAction.do?fileid=${f.FILEID}">${f.ORIGINALNAME}</a></td>
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
									<td align="right"><a href="javascript:more();">����</a></td>
								</tr>
						</table>
					</c:otherwise>
				</c:choose>
			</template:formTr>
			<template:formTr name="��������">
				<apptag:upload state="look" cssClass="" entityId="${id}" entityType="LP_ACCEPTANCE_CABLE" useable="0"/>
			</template:formTr>
			<template:formSubmit>
				<td>
					<html:button property="action" styleClass="button" onclick="javascript:parent.close();">�ر�</html:button>
				</td>
			</template:formSubmit>
		</html:form>
	</template:formTable>
</body>
</html>