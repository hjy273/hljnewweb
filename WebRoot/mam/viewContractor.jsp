<jsp:useBean id="contractorBean"
	class="com.cabletech.mam.beans.ContractorBean" scope="request" />
<%@ page language="java" contentType="text/html; charset=GBK"%>
<script language="javascript" src="${ctx}/js/validation/prototype.js"
	type=""></script>
<%@include file="/common/header.jsp"%>

<%
    String beanRegion = contractorBean.getRegionid(); //Ψһ����
%>
<input type="hidden" name="region" value="<%=beanRegion%>">
<script type="text/javascript" language="javascript">
function toGetBack(){
	location.href = "${ctx}/contractorAction.do?method=queryContractor&contractorName=&linkmanInfo=";
}
</script>
<template:titile value="�鿴�ⲿ����" />
<html:form onsubmit="return isValidForm()" method="Post"
	action="/contractorAction.do?method=updateContractor">
	<table width="720" border="0" align="center" cellpadding="3"
		cellspacing="0" class="tabout">
		<html:hidden property="contractorID" styleClass="inputtext"
			style="width:160" />
		<!-- ������Ϣ -->
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				��λ��ƣ�
			</td>
			<td class="tdulright" style="width: 35%">
				<bean:write name="contractorBean" property="contractorName" />
			</td>
			<td class="tdulleft" style="width: 15%">
				��λȫ�ƣ�
			</td>
			<td class="tdulright" style="width: 35%">
				<bean:write name="contractorBean" property="alias" />
			</td>
		</tr>
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				��λ���ͣ�
			</td>
			<td class="tdulright" style="width: 35%">
				<c:if test="${contractorBean.depttype=='2'}">
					��ά��λ
				</c:if>
				<c:if test="${contractorBean.depttype=='3'}">
					����λ
				</c:if>
			</td>
			<td class="tdulleft" style="width: 15%">
				�ϼ���λ��
			</td>
			<td class="tdulright" style="width: 35%">
				<c:if test="${contractorBean.parentcontractorid=='0'}">
					��
				</c:if>
				<c:if test="${contractorBean.parentcontractorid!='0'}">
					${parent_contractor_name }
				</c:if>
			</td>
		</tr>
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				��֯���ͣ�
			</td>
			<td class="tdulright" style="width: 35%">
				<c:if test="${contractorBean.organizationType=='1'}">
				��������
				</c:if>
				<c:if test="${contractorBean.organizationType=='2'}">
				��֧����
				</c:if>
			</td>
			<td class="tdulleft" style="width: 15%">
				��������
			</td>
			<td class="tdulright" style="width: 35%">
				<bean:write name="region_name" />
			</td>
		</tr>
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				����רҵ��
			</td>
			<td class="tdulright" style="width: 85%" colspan="3">
				<c:forEach var="res" items="${resources}">
					<c:forEach var="rsid" items="${contractorBean.resourcesId}">
						<c:if test="${rsid==res.code}">
							<c:out value="${res.resourcename}" />&nbsp;
						</c:if>
					</c:forEach>
				</c:forEach>
			</td>
		</tr>
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				��������
			</td>
			<td class="tdulright" style="width: 85%" colspan="3">
			</td>
		</tr>
		<tr class="trwhite">
			<td class="tdulleft" colspan="4"
				style="width: 100%; padding: 5px; text-align: center;">
				<table width="95%" border="0" align="center" cellpadding="3"
					cellspacing="0" class="tabout">
					<tr>
						<td class="tdulleft" style="width:20%;text-align: center;">��ͬ���</td>
						<td class="tdulleft" style="width:30%;text-align: center;">ά������</td>
						<td class="tdulleft" style="width:25%;text-align: center;">��Ч��</td>
						<td class="tdulleft" style="width:25%;text-align: center;">����</td>
					</tr>
					<c:if test="${regions!=null}">
						<c:forEach var="region" items="${regions}">
							<tr>
								<td class="tdulleft" style="width: 20%; text-align: center;">
									<bean:write name="region" property="cno" />
								</td>
								<td class="tdulleft" style="width: 30%; text-align: center;">
									<bean:write name="region" property="patrolregion" />
								</td>
								<td class="tdulleft" style="width: 25%; text-align: center;">
									<bean:write name="region" property="perioddate" />
								</td>
								<td class="tdulleft" style="width: 25%; text-align: center;">
									<bean:define id="entity_id" name="region" property="id"></bean:define>
									<apptag:upload state="look" entityId="${entity_id}"
										entityType="CONCORDATINFO"></apptag:upload>
								</td>
							</tr>
						</c:forEach>
					</c:if>
				</table>
			</td>
		</tr>
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				����֤�飺
			</td>
			<td class="tdulright" style="width: 85%" colspan="3">
			</td>
		</tr>
		<tr class="trwhite">
			<td class="tdulleft" colspan="4"
				style="width: 100%; padding: 5px; text-align: center;">
				<table width="95%" border="0" align="center" cellpadding="3"
					cellspacing="0" class="tabout">
					<tr>
						<td class="tdulleft" style="width:20%;text-align: center;">֤����</td>
						<td class="tdulleft" style="width:30%;text-align: center;">֤������</td>
						<td class="tdulleft" style="width:25%;text-align: center;">��Ч��</td>
						<td class="tdulleft" style="width:25%;text-align: center;">����</td>
					</tr>
					<c:if test="${certificates!=null}">
						<c:forEach var="certificate" items="${certificates}">
							<tr>
								<td class="tdulleft" style="width: 20%; text-align: center;">
									<bean:write name="certificate" property="certificatecode" />
								</td>
								<td class="tdulleft" style="width: 30%; text-align: center;">
									<bean:write name="certificate" property="certificatename" />
								</td>
								<td class="tdulleft" style="width: 25%; text-align: center;">
									<bean:write name="certificate" property="validperiod" />
								</td>
								<td class="tdulleft" style="width: 25%; text-align: center;">
									<bean:define id="entity_id" name="certificate" property="id"></bean:define>
									<apptag:upload state="look" entityId="${entity_id}"
										entityType="CERTIFICATE"></apptag:upload>
								</td>
							</tr>
						</c:forEach>
					</c:if>
				</table>
			</td>
		</tr>
		<!-- ��˾��Ϣ -->
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				���˴���
			</td>
			<td class="tdulright" style="width: 35%">
				<bean:write name="contractorBean" property="legalPerson" />
			</td>
			<td class="tdulleft" style="width: 15%">
				�Ǽ����ڣ�
			</td>
			<td class="tdulright" style="width: 35%">
				<bean:write name="contractorBean" property="recordDate" format="yyyy-MM-dd" />
			</td>
		</tr>
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				ע���ʽ�
			</td>
			<td class="tdulright" style="width: 85%" colspan="3">
				<bean:write name="contractorBean" property="capital" />
			</td>
		</tr>
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				��λ��飺
			</td>
			<td class="tdulright" style="width: 85%" colspan="3">
				<bean:write name="contractorBean" property="remark" />
			</td>
		</tr>
		<!-- ͨѶ��Ϣ -->
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				��λ��ַ��
			</td>
			<td class="tdulright" style="width: 35%">
				<bean:write name="contractorBean" property="address" />
			</td>
			<td class="tdulleft" style="width: 15%">
				�ʱࣺ
			</td>
			<td class="tdulright" style="width: 35%">
				<bean:write name="contractorBean" property="postcode" />
			</td>
		</tr>
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				�� ϵ �ˣ�
			</td>
			<td class="tdulright" style="width: 35%">
				<bean:write name="contractorBean" property="linkmanInfo" />
			</td>
			<td class="tdulleft" style="width: 15%">
				��ϵ�绰��
			</td>
			<td class="tdulright" style="width: 35%">
				<bean:write name="contractorBean" property="tel" />
			</td>
		</tr>
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				������A��
			</td>
			<td class="tdulright" style="width: 35%">
				<bean:write name="contractorBean" property="principalInfo" />
			</td>
			<td class="tdulleft" style="width: 15%">
				������B��
			</td>
			<td class="tdulright" style="width: 35%">
				<bean:write name="contractorBean" property="principalB" />
			</td>
		</tr>
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				�칫�绰��
			</td>
			<td class="tdulright" style="width: 35%">
				<bean:write name="contractorBean" property="workPhone" />
			</td>
			<td class="tdulleft" style="width: 15%">
				���棺
			</td>
			<td class="tdulright" style="width: 35%">
				<bean:write name="contractorBean" property="fax" />
			</td>
		</tr>
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				Email��
			</td>
			<td class="tdulright" style="width: 85%" colspan="3">
				<bean:write name="contractorBean" property="email" />
			</td>
		</tr>
		<template:formSubmit>
			<td>
				<input type="button" class="button" onclick="toGetBack()" value="����">
			</td>
		</template:formSubmit>
	</table>
</html:form>
