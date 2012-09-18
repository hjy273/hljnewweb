<%@include file="/common/header.jsp"%>
<html>
<head>
	<title></title>
	
	<script type='text/javascript' src='${ctx}/linepatrol/js/change_style.js'></script>
	
	<script type="text/javascript">
		function read(id){
			var url = '${ctx}/overHaulAction.do?method=read&id=${OverHaulApply.id}&type=LP_OVERHAUL';
			window.location.href = url;
		}
		function transfer(){
			var approver = $('approver').value;
			if(approver == ''){
				alert('ת���˲���Ϊ��');
				return false;
			}
			var url = '${ctx}/overHaulAction.do?method=transfer&id=${OverHaulApply.id}&approver='+approver;
			window.location.href = url;
		}
	</script>
</head>
<body onload="changeStyle()">
	<template:titile value="�����������" />
	<template:formTable namewidth="150" contentwidth="300">
		<html:form action="/overHaulApplyAction.do?method=approve" styleId="form">
			<input type="hidden" name="applyId" value="${OverHaulApply.id}">
			<template:formTr name="������Ŀ����">
				${OverHaul.projectName}
			</template:formTr>
			<template:formTr name="������">
				${OverHaul.projectCreator}
			</template:formTr>
			<template:formTr name="Ԥ�����">
				${OverHaul.budgetFee}Ԫ
			</template:formTr>
			<template:formTr name="�������ʼʱ��">
				<bean:write name="OverHaul" property="startTime" format="yyyy-MM-dd" />
			</template:formTr>
			<template:formTr name="�����������ʱ��">
				<bean:write name="OverHaul" property="endTime" format="yyyy-MM-dd" />
			</template:formTr>
			<template:formTr name="�������ע">
				${OverHaul.projectRemark}
			</template:formTr>
			<template:formTr name="���벿��">
				<apptag:assorciateAttr table="contractorinfo" label="contractorname" key="contractorid" keyValue="${OverHaulApply.contractorId}" />
			</template:formTr>
			<template:formTr name="������">
				<apptag:assorciateAttr table="userinfo" label="username" key="userid" keyValue="${OverHaulApply.creator}" />
			</template:formTr>
			<template:formTr name="����ʱ��">
				<bean:write property="createTime" name="OverHaulApply" format="yyyy-MM-dd" />
			</template:formTr>
			<template:formTr name="�����Ϣ">
				<table width=100% border=1>
					<tr>
				   		<td align='center' width='50%'>�������</td>
						<td align='center' width='50%'>��ӷ���</td>
					</tr>
					<c:forEach var="cut" items="${OverHaulApply.overHaulCuts}">
						<tr>
							<td>${cut.cutName}</td>
							<td>${cut.cutFee}Ԫ</td>
						</tr>
					</c:forEach>
				</table>
			</template:formTr>
			<template:formTr name="������Ϣ">
				<table width=100% border=1>
					<tr>
						<td align='center' width='50%'>��������</td>
						<td align='center' width='50%'>���̷���</td>
					</tr>
					<c:forEach var="project" items="${OverHaulApply.overHaulProjects}">
						<tr>
							<td>${project.projectName}</td>
							<td>${project.projectFee}Ԫ</td>
						</tr>
					</c:forEach>
				</table>
			</template:formTr>
			<template:formTr name="�����ܷ���">
				${OverHaulApply.fee}Ԫ
			</template:formTr>
			<template:formTr name="����">
				<apptag:upload state="look" cssClass="" entityId="${OverHaulApply.id}" entityType="LP_OVERHAUL"/>
			</template:formTr>
			<c:choose>
				<c:when test="${!OverHaulApply.readOnly}">
					<c:choose>
						<c:when test="${type eq '0'}">
							<apptag:approverselect label="ת����" inputName="approver" spanId="approverSpan" inputType="radio"/>
							<template:formSubmit>
								<td>
									<input type="button" class="button" value="�ύ" onclick="transfer()" />
								</td>
								<td>
									<input type="button" class="button" value="����" onclick="history.back()" />
								</td>
							</template:formSubmit>
						</c:when>
						<c:otherwise>
							<apptag:approve labelClass="tdulleft" areaStyle="width: 300px;" displayType="input" />
							<template:formSubmit>
								<td>
									<html:submit property="action" styleClass="button">�ύ</html:submit>
								</td>
								<td>
									<input type="button" class="button" value="����" onclick="history.back()" />
								</td>
							</template:formSubmit>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<template:formSubmit>
						<td>
							<input type="button" class="button" value="���Ķ�" onclick="read()" />
						</td>
						<td>
							<input type="button" class="button" value="����" onclick="history.back()" />
						</td>
					</template:formSubmit>
				</c:otherwise>
			</c:choose>
		</html:form>
	</template:formTable>
</body>
	<script type="text/javascript">
	jQuery(document).ready(function() {
	jQuery("#form").bind("submit",function(){processBar(form);});
})
	</script>