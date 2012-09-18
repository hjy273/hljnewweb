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
	</script>
</head>
<body onload="changeStyle()"> 
	<template:titile value="隐患查看" />
	<html:form action="/hiddangerAction.do" styleId="form">
		<template:formTable namewidth="170" contentwidth="380">
			<template:formTr name="隐患编号">
				<bean:write name="registBean" property="hiddangerNumber" />
			</template:formTr>
			<template:formTr name="登记单位">
				<bean:write name="registBean" property="createrDept" />
			</template:formTr>
			<template:formTr name="登记人">
				<apptag:assorciateAttr table="userinfo" label="username" key="userid" keyValue="${registBean.creater}" />
			</template:formTr>
			<template:formTr name="隐患位置">
				<bean:write name="registBean" property="x" />,<bean:write name="registBean" property="y" /><input type="button" onclick="showMap();" value="查看位置" />
			</template:formTr>
			<template:formTr name="隐患发现时间">
				<bean:write name="registBean" property="findTime" />
			</template:formTr>
			<template:formTr name="隐患发现方式">
				<bean:write name="registBean" property="findType" />
			</template:formTr>
			<template:formTr name="隐患上报时间">
				<bean:write name="registBean" property="createTime" />
			</template:formTr>
			<template:formTr name="发现隐患来源">
				<bean:write name="registBean" property="reporter" />
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
			<logic:notEmpty property="cancelUserId" name="registBean">
				<tr class=trcolor>
					<td class="tdr">
						取消人：
					</td>
					<td class="tdl">
						<bean:write property="cancelUserName" name="registBean" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						取消时间：
					</td>
					<td class="tdl">
						<bean:write property="cancelTime" name="registBean" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						取消原因：
					</td>
					<td class="tdl">
						<bean:write property="cancelReason" name="registBean" />
					</td>
				</tr>
			</logic:notEmpty>
			<template:formTr name="隐患地点描述">
				<bean:write name="reportBean" property="address" />
			</template:formTr>
			<template:formTr name="隐患点距光缆距离">
				<bean:write name="reportBean" property="distanceToCable" />米
			</template:formTr>
			<template:formTr name="隐患盯防负责人">
				<bean:write name="reportBean" property="watchPrincipal" />
			</template:formTr>
			<template:formTr name="隐患盯防负责人电话">
				<bean:write name="reportBean" property="watchPrincipalPhone" />
			</template:formTr>
			<template:formTr name="隐患盯防实施人">
				<bean:write name="reportBean" property="watchActualizeMan" />
			</template:formTr>
			<template:formTr name="隐患盯防实施人电话">
				<bean:write name="reportBean" property="watchActualizeManPhone" />
			</template:formTr>
			<template:formTr name="施工负责人">
				<bean:write name="reportBean" property="workPrincipal" />
			</template:formTr>
			<template:formTr name="施工负责人电话">
				<bean:write name="reportBean" property="workPrincipalPhone" />
			</template:formTr>
			<template:formTr name="施工单位">
				<bean:write name="reportBean" property="workDepart" />
			</template:formTr>
			<template:formTr name="施工阶段">
				<apptag:quickLoadList cssClass="input" style="width:200" name="workStage" listName="workstage" type="look" keyValue="${reportBean.workStage}"/>
			</template:formTr>
			<template:formTr name="计划隐患消除时间">
				<bean:write name="reportBean" property="planReliefTime" />
			</template:formTr>
			<template:formTr name="盯防介入时间">
				<bean:write name="reportBean" property="watchBeginTime" />
			</template:formTr>
			<template:formTr name="是否签署安全协议">
				<c:choose>
					<c:when test="${reportBean.signSecurityProtocol eq '1'}">
						是
					</c:when>
					<c:otherwise>
						否
					</c:otherwise>
				</c:choose>
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
			<template:formTr name="备注">
				<bean:write name="reportBean" property="reportRemark" />
			</template:formTr>
			<template:formTr name="附件">
				<apptag:upload state="look" cssClass="" entityId="${registBean.id}" entityType="LP_HIDDANGER_REPORT"/>
			</template:formTr>
			<template:formTr name="附件图片">
				<apptag:image entityId="${registBean.id}" entityType="LP_HIDDANGER_REPORT" />
			</template:formTr>
			<template:formSubmit>
				<td>
					<html:button property="action" styleClass="button" onclick="parent.close()">返回</html:button>
				</td>
			</template:formSubmit>
		</template:formTable>
	</html:form>
</body>