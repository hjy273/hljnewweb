<jsp:useBean id="contractorBean"
	class="com.cabletech.mam.beans.ContractorBean" scope="request" />
<%@ page language="java" contentType="text/html; charset=GBK"%>
<script language="javascript" src="${ctx}/js/validation/prototype.js"
	type=""></script>
<%@include file="/common/header.jsp"%>

<%
    String beanRegion = contractorBean.getRegionid(); //唯一区域
%>
<input type="hidden" name="region" value="<%=beanRegion%>">
<script type="text/javascript" language="javascript">
function toGetBack(){
	location.href = "${ctx}/contractorAction.do?method=queryContractor&contractorName=&linkmanInfo=";
}
</script>
<template:titile value="查看外部部门" />
<html:form onsubmit="return isValidForm()" method="Post"
	action="/contractorAction.do?method=updateContractor">
	<table width="720" border="0" align="center" cellpadding="3"
		cellspacing="0" class="tabout">
		<html:hidden property="contractorID" styleClass="inputtext"
			style="width:160" />
		<!-- 基础信息 -->
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				单位简称：
			</td>
			<td class="tdulright" style="width: 35%">
				<bean:write name="contractorBean" property="contractorName" />
			</td>
			<td class="tdulleft" style="width: 15%">
				单位全称：
			</td>
			<td class="tdulright" style="width: 35%">
				<bean:write name="contractorBean" property="alias" />
			</td>
		</tr>
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				单位类型：
			</td>
			<td class="tdulright" style="width: 35%">
				<c:if test="${contractorBean.depttype=='2'}">
					代维单位
				</c:if>
				<c:if test="${contractorBean.depttype=='3'}">
					监理单位
				</c:if>
			</td>
			<td class="tdulleft" style="width: 15%">
				上级单位：
			</td>
			<td class="tdulright" style="width: 35%">
				<c:if test="${contractorBean.parentcontractorid=='0'}">
					无
				</c:if>
				<c:if test="${contractorBean.parentcontractorid!='0'}">
					${parent_contractor_name }
				</c:if>
			</td>
		</tr>
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				组织类型：
			</td>
			<td class="tdulright" style="width: 35%">
				<c:if test="${contractorBean.organizationType=='1'}">
				独立法人
				</c:if>
				<c:if test="${contractorBean.organizationType=='2'}">
				分支机构
				</c:if>
			</td>
			<td class="tdulleft" style="width: 15%">
				所属区域：
			</td>
			<td class="tdulright" style="width: 35%">
				<bean:write name="region_name" />
			</td>
		</tr>
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				合作专业：
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
				合作区域：
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
						<td class="tdulleft" style="width:20%;text-align: center;">合同编号</td>
						<td class="tdulleft" style="width:30%;text-align: center;">维护区域</td>
						<td class="tdulleft" style="width:25%;text-align: center;">有效期</td>
						<td class="tdulleft" style="width:25%;text-align: center;">附件</td>
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
				资质证书：
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
						<td class="tdulleft" style="width:20%;text-align: center;">证书编号</td>
						<td class="tdulleft" style="width:30%;text-align: center;">证书名称</td>
						<td class="tdulleft" style="width:25%;text-align: center;">有效期</td>
						<td class="tdulleft" style="width:25%;text-align: center;">附件</td>
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
		<!-- 公司信息 -->
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				法人代表：
			</td>
			<td class="tdulright" style="width: 35%">
				<bean:write name="contractorBean" property="legalPerson" />
			</td>
			<td class="tdulleft" style="width: 15%">
				登记日期：
			</td>
			<td class="tdulright" style="width: 35%">
				<bean:write name="contractorBean" property="recordDate" format="yyyy-MM-dd" />
			</td>
		</tr>
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				注册资金：
			</td>
			<td class="tdulright" style="width: 85%" colspan="3">
				<bean:write name="contractorBean" property="capital" />
			</td>
		</tr>
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				单位简介：
			</td>
			<td class="tdulright" style="width: 85%" colspan="3">
				<bean:write name="contractorBean" property="remark" />
			</td>
		</tr>
		<!-- 通讯信息 -->
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				单位地址：
			</td>
			<td class="tdulright" style="width: 35%">
				<bean:write name="contractorBean" property="address" />
			</td>
			<td class="tdulleft" style="width: 15%">
				邮编：
			</td>
			<td class="tdulright" style="width: 35%">
				<bean:write name="contractorBean" property="postcode" />
			</td>
		</tr>
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				联 系 人：
			</td>
			<td class="tdulright" style="width: 35%">
				<bean:write name="contractorBean" property="linkmanInfo" />
			</td>
			<td class="tdulleft" style="width: 15%">
				联系电话：
			</td>
			<td class="tdulright" style="width: 35%">
				<bean:write name="contractorBean" property="tel" />
			</td>
		</tr>
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				负责人A：
			</td>
			<td class="tdulright" style="width: 35%">
				<bean:write name="contractorBean" property="principalInfo" />
			</td>
			<td class="tdulleft" style="width: 15%">
				负责人B：
			</td>
			<td class="tdulright" style="width: 35%">
				<bean:write name="contractorBean" property="principalB" />
			</td>
		</tr>
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				办公电话：
			</td>
			<td class="tdulright" style="width: 35%">
				<bean:write name="contractorBean" property="workPhone" />
			</td>
			<td class="tdulleft" style="width: 15%">
				传真：
			</td>
			<td class="tdulright" style="width: 35%">
				<bean:write name="contractorBean" property="fax" />
			</td>
		</tr>
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				Email：
			</td>
			<td class="tdulright" style="width: 85%" colspan="3">
				<bean:write name="contractorBean" property="email" />
			</td>
		</tr>
		<template:formSubmit>
			<td>
				<input type="button" class="button" onclick="toGetBack()" value="返回">
			</td>
		</template:formSubmit>
	</table>
</html:form>
