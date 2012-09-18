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
	<template:titile value="�����鿴" />
	<html:form action="/hiddangerAction.do" styleId="form">
		<template:formTable namewidth="170" contentwidth="380">
			<template:formTr name="�������">
				<bean:write name="registBean" property="hiddangerNumber" />
			</template:formTr>
			<template:formTr name="�Ǽǵ�λ">
				<bean:write name="registBean" property="createrDept" />
			</template:formTr>
			<template:formTr name="�Ǽ���">
				<apptag:assorciateAttr table="userinfo" label="username" key="userid" keyValue="${registBean.creater}" />
			</template:formTr>
			<template:formTr name="����λ��">
				<bean:write name="registBean" property="x" />,<bean:write name="registBean" property="y" /><input type="button" onclick="showMap();" value="�鿴λ��" />
			</template:formTr>
			<template:formTr name="��������ʱ��">
				<bean:write name="registBean" property="findTime" />
			</template:formTr>
			<template:formTr name="�������ַ�ʽ">
				<bean:write name="registBean" property="findType" />
			</template:formTr>
			<template:formTr name="�����ϱ�ʱ��">
				<bean:write name="registBean" property="createTime" />
			</template:formTr>
			<template:formTr name="����������Դ">
				<bean:write name="registBean" property="reporter" />
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
			<logic:notEmpty property="cancelUserId" name="registBean">
				<tr class=trcolor>
					<td class="tdr">
						ȡ���ˣ�
					</td>
					<td class="tdl">
						<bean:write property="cancelUserName" name="registBean" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						ȡ��ʱ�䣺
					</td>
					<td class="tdl">
						<bean:write property="cancelTime" name="registBean" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						ȡ��ԭ��
					</td>
					<td class="tdl">
						<bean:write property="cancelReason" name="registBean" />
					</td>
				</tr>
			</logic:notEmpty>
			<template:formTr name="�����ص�����">
				<bean:write name="reportBean" property="address" />
			</template:formTr>
			<template:formTr name="���������¾���">
				<bean:write name="reportBean" property="distanceToCable" />��
			</template:formTr>
			<template:formTr name="��������������">
				<bean:write name="reportBean" property="watchPrincipal" />
			</template:formTr>
			<template:formTr name="�������������˵绰">
				<bean:write name="reportBean" property="watchPrincipalPhone" />
			</template:formTr>
			<template:formTr name="��������ʵʩ��">
				<bean:write name="reportBean" property="watchActualizeMan" />
			</template:formTr>
			<template:formTr name="��������ʵʩ�˵绰">
				<bean:write name="reportBean" property="watchActualizeManPhone" />
			</template:formTr>
			<template:formTr name="ʩ��������">
				<bean:write name="reportBean" property="workPrincipal" />
			</template:formTr>
			<template:formTr name="ʩ�������˵绰">
				<bean:write name="reportBean" property="workPrincipalPhone" />
			</template:formTr>
			<template:formTr name="ʩ����λ">
				<bean:write name="reportBean" property="workDepart" />
			</template:formTr>
			<template:formTr name="ʩ���׶�">
				<apptag:quickLoadList cssClass="input" style="width:200" name="workStage" listName="workstage" type="look" keyValue="${reportBean.workStage}"/>
			</template:formTr>
			<template:formTr name="�ƻ���������ʱ��">
				<bean:write name="reportBean" property="planReliefTime" />
			</template:formTr>
			<template:formTr name="��������ʱ��">
				<bean:write name="reportBean" property="watchBeginTime" />
			</template:formTr>
			<template:formTr name="�Ƿ�ǩ��ȫЭ��">
				<c:choose>
					<c:when test="${reportBean.signSecurityProtocol eq '1'}">
						��
					</c:when>
					<c:otherwise>
						��
					</c:otherwise>
				</c:choose>
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
			<template:formTr name="��ע">
				<bean:write name="reportBean" property="reportRemark" />
			</template:formTr>
			<template:formTr name="����">
				<apptag:upload state="look" cssClass="" entityId="${registBean.id}" entityType="LP_HIDDANGER_REPORT"/>
			</template:formTr>
			<template:formTr name="����ͼƬ">
				<apptag:image entityId="${registBean.id}" entityType="LP_HIDDANGER_REPORT" />
			</template:formTr>
			<template:formSubmit>
				<td>
					<html:button property="action" styleClass="button" onclick="parent.close()">����</html:button>
				</td>
			</template:formSubmit>
		</template:formTable>
	</html:form>
</body>