<%@include file="/common/header.jsp"%>
<html>
<head>
	<title></title>
	
	<script type='text/javascript' src='${ctx}/linepatrol/js/change_style.js'></script>
	
	<script type="text/javascript">
		function showMap(){
			var url = '/WEBGIS/gisextend/igis.jsp?userid=${LOGIN_USER.userID}&actiontype=205&x=${registBean.x}&y=${registBean.y}&label=${registBean.name}';
			window.open(url,'map','width=800,height=600,scrollbars=yes,resizable=yes,toolbar=no,menubar=no');
		}
		function viewPlan(id){
			window.location.href = '${ctx}/specialplan.do?method=loadPlan&type=view&ptype=001&isApply=false&id='+id;
		}
		function viewPlanStat(id){
			window.location.href = '${ctx}/linepatrol/specialplan/stat/stat.jsp?type=001&id='+id;
		}
		function viewAllPlanStat(id){
			window.location.href = '${ctx}/linepatrol/specialplan/stat/allStat.jsp?id='+id;
		}
	</script>
</head>
<body onload="changeStyle()"> 
	<template:titile value="隐患统计" />
	<html:form action="/hiddangerAction.do" styleId="form">
		<template:formTable namewidth="200" contentwidth="500">
			<template:formTr name="隐患编号">
				<bean:write name="registBean" property="hiddangerNumber" />
			</template:formTr>
			<template:formTr name="登记单位">
				<bean:write name="registBean" property="createrDept" />
			</template:formTr>
			<template:formTr name="登记人">
				<apptag:assorciateAttr table="userinfo" label="username" key="userid" keyValue="${registBean.creater}" />
			</template:formTr>
			<template:formTr name="登记名称">
				<bean:write name="registBean" property="name" />
			</template:formTr>
			<template:formTr name="隐患位置">
				<bean:write name="registBean" property="x" />,<bean:write name="registBean" property="y" /><input type="button" onclick="showMap();" value="查看位置" />
			</template:formTr>
			<template:formTr name="隐患发现时间">
				<bean:write name="registBean" property="findTime" />
			</template:formTr>
			<template:formTr name="隐患上报时间">
				<bean:write name="registBean" property="createTime" />
			</template:formTr>
			<template:formTr name="隐患处理单位">
				<apptag:assorciateAttr table="contractorinfo" label="contractorname" key="contractorid" keyValue="${registBean.treatDepartment}" />
			</template:formTr>
			<template:formTr name="隐患分类">
			    <apptag:assorciateAttr table="troubletype" label="typename" key="id" keyValue="${registBean.type}" />
			</template:formTr>
			<template:formTr name="隐患名称">
				<apptag:assorciateAttr table="troublecode" label="troublename" key="id" keyValue="${registBean.code}" />
			</template:formTr>
		<c:if test="${!empty registBean.hiddangerLevel}">
			<template:formTr name="隐患等级">
				<c:choose>
					<c:when test="${registBean.hiddangerLevel eq '0'}">
						忽略
					</c:when>
					<c:otherwise>
						<bean:write name="registBean" property="hiddangerLevel" />级
					</c:otherwise>
				</c:choose>
			</template:formTr>
			<template:formTr name="隐患评级备注">
				<bean:write name="registBean" property="remark" />
			</template:formTr>
		</c:if>
		<c:if test="${!empty reportBean}">
			<template:formTr name="隐患地点描述">
				<bean:write name="reportBean" property="address" />
			</template:formTr>
			<template:formTr name="计划隐患消除时间">
				<bean:write name="reportBean" property="planReliefTime" />
			</template:formTr>
			<template:formTr name="盯防介入时间">
				<bean:write name="reportBean" property="watchBeginTime" />
			</template:formTr>
			<template:formTr name="受影响光缆段">
				<apptag:trunk id="trunk" state="view" value="${reportBean.trunkIdsString}" />
			</template:formTr>
			<template:formTr name="其他受影响光缆段">
				<bean:write name="reportBean" property="otherImpressCable" />
			</template:formTr>
			<template:formTr name="隐患盯防原因">
				<bean:write name="reportBean" property="watchReason" />
			</template:formTr>
			<template:formTr name="隐患盯防措施">
				<bean:write name="reportBean" property="watchMeasure" />
			</template:formTr>
		</c:if>
		<template:formTr name="盯防计划">
			<jsp:include page="statIframe.jsp" />
		</template:formTr>
		<template:formTr name="盯防计划总体信息">
			<input type="button" value="查看" onclick="viewAllPlanStat('${planIds}')" />
		</template:formTr>
		<c:if test="${iswin != 'window'}">
			<template:formSubmit>
				<td>
					<html:button property="action" styleClass="button" onclick="history.back()">返回</html:button>
				</td>
			</template:formSubmit>
		</c:if>
		</template:formTable>
	</html:form>
</body>