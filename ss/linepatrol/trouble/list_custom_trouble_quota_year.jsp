<%@include file="/common/header.jsp"%>
<!--%@include file="/common/listhander.jsp"%-->
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>�Զ��������ָ��</title>
		<script type="text/javascript">
		addYearQuota = function (){
			window.location.href="${ctx}/customTroubleQuotaActon.do?method=customYearTroubleQuotaForm";
		};
		editYearQuota = function(id){
			window.location.href="${ctx}/customTroubleQuotaActon.do?method=customYearTroubleQuotaForm&id="+id;
		};
		</script>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
	</head>
	<body>
	<template:titile value="�Զ��������ָ��" />
	<display:table name="sessionScope.yearQuotaList" id="yearquota" pagesize="15">
			<display:column media="html"  title="���" >
				<a href="javascript:editYearQuota('${yearquota.id}')">${yearquota.year}</a>
			</display:column>
			<display:column media="html"  title="ָ������" >
				<c:if test="${yearquota.guideType==1}">
				һ�ɹ���ָ��
				</c:if>
				<c:if test="${yearquota.guideType==0}">
				����������ָ��
				</c:if>
			</display:column>
			<display:column property="maintenanceLength" sortable="true" title="ά��������" headerClass="subject"/>
			<display:column property="interdictionNormTimes" sortable="true" title="ǧ������ϴ�����׼" headerClass="subject" />
			<display:column property="troubleTimes" sortable="true" title="��ϴ������ֵ" headerClass="subject" />
			<display:column property="interdictionNormTime" sortable="true" title="ǧ�������ʱ����׼ֵ" headerClass="subject" />
			<display:column property="interdictionTime" sortable="true" title="���ʱ�����ֵ" headerClass="subject" />
			<display:column property="rtrTimeNormValue" sortable="true" title="��������ƽ��ʱ����׼ֵ" headerClass="subject" />
			<display:column property="rtrTimeFinishValue" sortable="true" title="��������ƽ��ʱ�����ֵ" headerClass="subject" />
			
	</display:table>
	<div align="right">
	<input type="button" class="button" onclick="addYearQuota()" value="���"/>
	</div>
	</body>
</html>