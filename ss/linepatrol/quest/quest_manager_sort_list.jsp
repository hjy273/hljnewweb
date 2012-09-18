<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
<html>
	<head>
		<title>ָ��������</title>
		<script type="text/javascript">
			toAddSort=function(){
            	window.location.href = "${ctx}/questAction.do?method=addSortForm";
			}
			toEditSort=function(sortId){
            	window.location.href = "${ctx}/questAction.do?method=editSortForm&sortId="+sortId;
			}
			toDeleteSort=function(sortId){
				if(confirm("ȷ��ɾ����ָ�������")){
            		window.location.href = "${ctx}/questAction.do?method=deleteSort&sortId="+sortId;
            	}
			}
		</script>
	</head>
	<body>
		<template:titile value="ָ��������"/>
		<display:table name="sessionScope.list" id="sort" pagesize="18">
			<display:column property="row_num" title="���" headerClass="subject" sortable="true"/>
			<display:column property="class_name" title="ָ�����" headerClass="subject"  sortable="true"/>
			<display:column property="sort_name" title="ָ�����" headerClass="subject"  sortable="true"/>
			<display:column media="html" title="����" >
				<a href="javascript:toEditSort('<bean:write name="sort" property="id"/>')">�༭</a> | 
				<a href="javascript:toDeleteSort('<bean:write name="sort" property="id"/>')">ɾ��</a>
			</display:column>
		</display:table>
		<br/>
		<div style="height:40px">
			<html:button property="action" styleClass="button" onclick="toAddSort()">���</html:button>
		</div>
	</body>
</html>
