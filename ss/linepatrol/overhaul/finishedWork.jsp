<%@include file="/common/header.jsp"%>
<html>
<head>
	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
	<title></title>
		
	<script type="text/javascript">
		function view(id){
			var url = '${ctx}/overHaulAction.do?method=view&type=history&id='+id;
			window.location.href = url;
		}
		//ȡ������
//		toCancelForm=function(overHaulId){
//			var url;
//			if(confirm("ȷ��Ҫȡ���ô�����Ŀ������")){
//				url="${ctx}/overHaulAction.do?method=cancelOverHaulForm";
//				var queryString="overhaul_id="+overHaulId;
//				//location=url+"&"+queryString+"&rnd="+Math.random();
//				window.open(url+"&"+queryString+"&rnd="+Math.random(),'','width=300,height=200,top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no');
//			}
//		}
	</script>
</head>
<body>
	<template:titile value="�����б�" />
	<logic:notEmpty name="result">
	<display:table name="sessionScope.result" id="row" pagesize="18" export="false" defaultsort="1" defaultorder="descending" sort="list">
		<bean:define id="sendUserId" name="row" property="creator" />
		<bean:define id="id" name="row" property="id"></bean:define>
		<bean:define id="applyState" name="row" property="state"></bean:define>
		<display:setProperty name="sort.amount" value="list"/>
		<display:column property="projectName" title="��Ŀ����" sortable="true"/>
		<display:column property="projectCreator" title="������" sortable="true"/>
		<display:column property="budgetFee" title="Ԥ����ã�Ԫ��" sortable="true"/>
		<display:column property="useFee" title="Ŀǰ��Ŀʹ�÷��ã�Ԫ��" sortable="true"/>
		<display:column property="startTime" title="��ʼʱ��" sortable="true" format="{0,date,yyyy-MM-dd}"/>
		<display:column property="endTime" title="����ʱ��" sortable="true" format="{0,date,yyyy-MM-dd}"/>
		<display:column media="html" title="����">
			<a href="javascript:view('${row.id}')">�鿴</a>
	         <!--         <c:if test="${sessionScope.LOGIN_USER.userID==sendUserId&&applyState!='999'}">
						| <a href="javascript:toCancelForm('${row.id}')">ȡ��</a>
					</c:if>
					-->
		</display:column>
	</display:table>
	</logic:notEmpty>
</body>