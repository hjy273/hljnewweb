<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
<html>
	<head>
		<title>ָ�������</title>
		<script type="text/javascript">
			toAddItem=function(){
            	window.location.href = "${ctx}/questAction.do?method=addItemForm";
			}
			toEditItem=function(itemId){
            	window.location.href = "${ctx}/questAction.do?method=editItemForm&itemId="+itemId;
			}
			toDeleteItem=function(itemId){
				if(confirm("ȷ��ɾ����ָ������")){
            		window.location.href = "${ctx}/questAction.do?method=deleteManagerItem&itemId="+itemId;
            	}
			}
		</script>
	</head>
	<body>
		<template:titile value="ָ�������"/>
		<display:table name="sessionScope.list" id="item" pagesize="18">
			<display:column property="row_num" title="���" headerClass="subject" sortable="true"/>
			<display:column property="sort_name" title="ָ�����" headerClass="subject"  sortable="true"/>
			<display:column property="item_name" title="ָ����" headerClass="subject"  sortable="true"/>
			<display:column media="html" title="����" >
				<a href="javascript:toEditItem('<bean:write name="item" property="id"/>')">�༭</a> | 
				<a href="javascript:toDeleteItem('<bean:write name="item" property="id"/>')">ɾ��</a>
			</display:column>
		</display:table>
		<br/>
		<div style="height:40px">
			<html:button property="action" styleClass="button" onclick="toAddItem()">���</html:button>
		</div>
	</body>
</html>
