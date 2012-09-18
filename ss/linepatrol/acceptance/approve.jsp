<%@include file="/common/header.jsp"%>
<html>
<head>
	<title></title>
	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
	
	<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
	<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
	<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
	
	<script type="text/javascript">
		function viewCable(id){
			var url = '${ctx}/acceptanceAction.do?method=viewCableData&id=${apply.id}&cableId='+id;
			window.location.href = url;
		}
		function viewPipe(id){
			var url = '${ctx}/acceptanceAction.do?method=viewPipeData&id=${apply.id}&pipeId='+id;
			window.location.href = url;
		}
		function setValue(obj){
			$('submit').disabled = "disabled";
			var url = "${ctx}/acceptanceAction.do?method=setValue&value="+obj.value+"&type="+obj.checked;
			var myAjax = new Ajax.Request(
				url, 
				{method:"post", onComplete:callback, asynchronous:true}
			);
		}
		function callback(){
			$('submit').disabled = "";
		}
	</script>
</head>
<body>
	<template:titile value="验收交维审批" />
	<html:form action="/acceptanceAction.do?method=approve" styleId="form">
		<template:formTable namewidth="200" contentwidth="400">
			<input type="hidden" name="id" value="${subflowId}" />
			<template:formTr name="项目经理">
				<apptag:assorciateAttr table="userinfo" label="username" key="userid" keyValue="${apply.applicant}" />
			</template:formTr>
			<template:formTr name="工程名称">
				${apply.name}
			</template:formTr>
			<template:formTr name="验收交维编号">
				${apply.code}
			</template:formTr>
			<template:formTr name="申请时间">
				<bean:write name="apply" property="applyDate" format="yyyy/MM/dd"/>
			</template:formTr>
			<template:formTr name="验收资源类型">
				<c:choose>
					<c:when test="${apply.resourceType eq '1'}">
						光缆
					</c:when>
					<c:otherwise>
						管道
					</c:otherwise>
				</c:choose>
			</template:formTr>
			<apptag:approve labelClass="tdulleft" areaStyle="width: 400px;" colSpan="1" displayType="input" objectId="${apply.id}" objectType="LP_ACCEPTANCE_APPROVE" />
			<tr>
				<td colspan=2>
					<c:choose>
						<c:when test="${apply.resourceType eq '1'}">
							<jsp:include page="cableList_inc.jsp" />
						</c:when>
						<c:otherwise>
							<jsp:include page="pipeList_inc.jsp" />
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
		</template:formTable>
		<table class="tdlist">
			<tr><td class="tdlist" colspan=2>
				<input type="submit" value="提交" id="submit" class="button" />
				<input type="button" value="返回" class="button" onclick="history.back();"/>
			</td></tr>
		</table>
	</html:form>
</body>