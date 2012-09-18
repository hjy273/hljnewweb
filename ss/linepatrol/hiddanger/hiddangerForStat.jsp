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
	<template:titile value="����ͳ��" />
	<html:form action="/hiddangerAction.do" styleId="form">
		<template:formTable namewidth="200" contentwidth="500">
			<template:formTr name="�������">
				<bean:write name="registBean" property="hiddangerNumber" />
			</template:formTr>
			<template:formTr name="�Ǽǵ�λ">
				<bean:write name="registBean" property="createrDept" />
			</template:formTr>
			<template:formTr name="�Ǽ���">
				<apptag:assorciateAttr table="userinfo" label="username" key="userid" keyValue="${registBean.creater}" />
			</template:formTr>
			<template:formTr name="�Ǽ�����">
				<bean:write name="registBean" property="name" />
			</template:formTr>
			<template:formTr name="����λ��">
				<bean:write name="registBean" property="x" />,<bean:write name="registBean" property="y" /><input type="button" onclick="showMap();" value="�鿴λ��" />
			</template:formTr>
			<template:formTr name="��������ʱ��">
				<bean:write name="registBean" property="findTime" />
			</template:formTr>
			<template:formTr name="�����ϱ�ʱ��">
				<bean:write name="registBean" property="createTime" />
			</template:formTr>
			<template:formTr name="��������λ">
				<apptag:assorciateAttr table="contractorinfo" label="contractorname" key="contractorid" keyValue="${registBean.treatDepartment}" />
			</template:formTr>
			<template:formTr name="��������">
			    <apptag:assorciateAttr table="troubletype" label="typename" key="id" keyValue="${registBean.type}" />
			</template:formTr>
			<template:formTr name="��������">
				<apptag:assorciateAttr table="troublecode" label="troublename" key="id" keyValue="${registBean.code}" />
			</template:formTr>
		<c:if test="${!empty registBean.hiddangerLevel}">
			<template:formTr name="�����ȼ�">
				<c:choose>
					<c:when test="${registBean.hiddangerLevel eq '0'}">
						����
					</c:when>
					<c:otherwise>
						<bean:write name="registBean" property="hiddangerLevel" />��
					</c:otherwise>
				</c:choose>
			</template:formTr>
			<template:formTr name="����������ע">
				<bean:write name="registBean" property="remark" />
			</template:formTr>
		</c:if>
		<c:if test="${!empty reportBean}">
			<template:formTr name="�����ص�����">
				<bean:write name="reportBean" property="address" />
			</template:formTr>
			<template:formTr name="�ƻ���������ʱ��">
				<bean:write name="reportBean" property="planReliefTime" />
			</template:formTr>
			<template:formTr name="��������ʱ��">
				<bean:write name="reportBean" property="watchBeginTime" />
			</template:formTr>
			<template:formTr name="��Ӱ����¶�">
				<apptag:trunk id="trunk" state="view" value="${reportBean.trunkIdsString}" />
			</template:formTr>
			<template:formTr name="������Ӱ����¶�">
				<bean:write name="reportBean" property="otherImpressCable" />
			</template:formTr>
			<template:formTr name="��������ԭ��">
				<bean:write name="reportBean" property="watchReason" />
			</template:formTr>
			<template:formTr name="����������ʩ">
				<bean:write name="reportBean" property="watchMeasure" />
			</template:formTr>
		</c:if>
		<template:formTr name="�����ƻ�">
			<jsp:include page="statIframe.jsp" />
		</template:formTr>
		<template:formTr name="�����ƻ�������Ϣ">
			<input type="button" value="�鿴" onclick="viewAllPlanStat('${planIds}')" />
		</template:formTr>
		<c:if test="${iswin != 'window'}">
			<template:formSubmit>
				<td>
					<html:button property="action" styleClass="button" onclick="history.back()">����</html:button>
				</td>
			</template:formSubmit>
		</c:if>
		</template:formTable>
	</html:form>
</body>