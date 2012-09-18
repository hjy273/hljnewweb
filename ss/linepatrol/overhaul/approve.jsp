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
				alert('转审人不能为空');
				return false;
			}
			var url = '${ctx}/overHaulAction.do?method=transfer&id=${OverHaulApply.id}&approver='+approver;
			window.location.href = url;
		}
	</script>
</head>
<body onload="changeStyle()">
	<template:titile value="大修申请审核" />
	<template:formTable namewidth="150" contentwidth="300">
		<html:form action="/overHaulApplyAction.do?method=approve" styleId="form">
			<input type="hidden" name="applyId" value="${OverHaulApply.id}">
			<template:formTr name="大修项目名称">
				${OverHaul.projectName}
			</template:formTr>
			<template:formTr name="立项人">
				${OverHaul.projectCreator}
			</template:formTr>
			<template:formTr name="预算费用">
				${OverHaul.budgetFee}元
			</template:formTr>
			<template:formTr name="大修立项开始时间">
				<bean:write name="OverHaul" property="startTime" format="yyyy-MM-dd" />
			</template:formTr>
			<template:formTr name="大修立项结束时间">
				<bean:write name="OverHaul" property="endTime" format="yyyy-MM-dd" />
			</template:formTr>
			<template:formTr name="大修立项备注">
				${OverHaul.projectRemark}
			</template:formTr>
			<template:formTr name="申请部门">
				<apptag:assorciateAttr table="contractorinfo" label="contractorname" key="contractorid" keyValue="${OverHaulApply.contractorId}" />
			</template:formTr>
			<template:formTr name="申请人">
				<apptag:assorciateAttr table="userinfo" label="username" key="userid" keyValue="${OverHaulApply.creator}" />
			</template:formTr>
			<template:formTr name="申请时间">
				<bean:write property="createTime" name="OverHaulApply" format="yyyy-MM-dd" />
			</template:formTr>
			<template:formTr name="割接信息">
				<table width=100% border=1>
					<tr>
				   		<td align='center' width='50%'>割接名称</td>
						<td align='center' width='50%'>割接费用</td>
					</tr>
					<c:forEach var="cut" items="${OverHaulApply.overHaulCuts}">
						<tr>
							<td>${cut.cutName}</td>
							<td>${cut.cutFee}元</td>
						</tr>
					</c:forEach>
				</table>
			</template:formTr>
			<template:formTr name="工程信息">
				<table width=100% border=1>
					<tr>
						<td align='center' width='50%'>工程名称</td>
						<td align='center' width='50%'>工程费用</td>
					</tr>
					<c:forEach var="project" items="${OverHaulApply.overHaulProjects}">
						<tr>
							<td>${project.projectName}</td>
							<td>${project.projectFee}元</td>
						</tr>
					</c:forEach>
				</table>
			</template:formTr>
			<template:formTr name="申请总费用">
				${OverHaulApply.fee}元
			</template:formTr>
			<template:formTr name="附件">
				<apptag:upload state="look" cssClass="" entityId="${OverHaulApply.id}" entityType="LP_OVERHAUL"/>
			</template:formTr>
			<c:choose>
				<c:when test="${!OverHaulApply.readOnly}">
					<c:choose>
						<c:when test="${type eq '0'}">
							<apptag:approverselect label="转审人" inputName="approver" spanId="approverSpan" inputType="radio"/>
							<template:formSubmit>
								<td>
									<input type="button" class="button" value="提交" onclick="transfer()" />
								</td>
								<td>
									<input type="button" class="button" value="返回" onclick="history.back()" />
								</td>
							</template:formSubmit>
						</c:when>
						<c:otherwise>
							<apptag:approve labelClass="tdulleft" areaStyle="width: 300px;" displayType="input" />
							<template:formSubmit>
								<td>
									<html:submit property="action" styleClass="button">提交</html:submit>
								</td>
								<td>
									<input type="button" class="button" value="返回" onclick="history.back()" />
								</td>
							</template:formSubmit>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<template:formSubmit>
						<td>
							<input type="button" class="button" value="已阅读" onclick="read()" />
						</td>
						<td>
							<input type="button" class="button" value="返回" onclick="history.back()" />
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