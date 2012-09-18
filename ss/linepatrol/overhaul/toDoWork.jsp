<%@include file="/common/header.jsp"%>
<html>
<head>
	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
	<title></title>
		
	<script type="text/javascript">
		function apply(id){
			var url = '${ctx}/overHaulAction.do?method=applyForm&id='+id;
			window.location.href = url;
		}
		function approve(id, type){
			var url = '${ctx}/overHaulApplyAction.do?method=approveForm&id='+id+'&type='+type;
			window.location.href = url;
		}
		function editApply(id){
			var url = '${ctx}/overHaulAction.do?method=editApplyForm&id='+id;
			window.location.href = url;
		}
		function treat(id){
			var url = '${ctx}/overHaulAction.do?method=treatForm&id='+id;
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
	<template:titile value="�����б�(<font color='red'>��${number}������</font>)" />
	<div style="text-align:center;">
		<iframe src="${ctx}/overHaulAction.do?method=processMap&task_name=${param.task_name}"
			frameborder="0" id="processGraphFrame" height="70" scrolling="no" width="99%" ></iframe>
	</div>
	<logic:notEmpty name="result">
	<display:table name="sessionScope.result" id="row" pagesize="18" export="false" defaultsort="1" defaultorder="descending" sort="list">
		<bean:define id="sendUserId" name="row" property="creator" />
		<bean:define id="applyState" name="row" property="state"></bean:define>
		<display:setProperty name="sort.amount" value="list"/>
		<display:column property="projectName" title="��Ŀ����" sortable="true"/>
		<display:column property="projectCreator" title="������" sortable="true"/>
		<display:column property="budgetFee" title="Ԥ����ã�Ԫ��" sortable="true"/>
		<display:column property="useFee" title="Ŀǰ��Ŀʹ�÷��ã�Ԫ��" sortable="true"/>
		<display:column property="startTime" title="��ʼʱ��" sortable="true" format="{0,date,yyyy-MM-dd}"/>
		<display:column property="endTime" title="����ʱ��" sortable="true" format="{0,date,yyyy-MM-dd}"/>
		<display:column media="html" title="����">
			<c:if test="${row.state eq '10'}">
				<c:choose>
					<c:when test="${LOGIN_USER.deptype eq '1'}">
						<a href="javascript:treat('${row.id}')">����</a>
					</c:when>
					<c:otherwise>
						<a href="javascript:apply('${row.id}')">����</a>
					</c:otherwise>
				</c:choose>
			</c:if>
			<c:if test="${row.state eq '20'}">
				<c:choose>
					<c:when test="${row.readOnly}">
						<a href="javascript:approve('${row.subflowId}')">�鿴</a>
					</c:when>
					<c:otherwise>
						<a href="javascript:approve('${row.subflowId}','1')">���</a> | 
						<a href="javascript:approve('${row.subflowId}','0')">ת��</a>
					</c:otherwise>
				</c:choose>
			</c:if>
			<c:if test="${row.state eq '30'}">
				<a href="javascript:editApply('${row.subflowId}')">�޸�����</a>
			</c:if>
	         <!--        <c:if test="${sessionScope.LOGIN_USER.deptype=='1'&&applyState!='999'}">
						| <a href="javascript:toCancelForm('${row.id}')">ȡ��</a>
					</c:if>
					--> 
		</display:column>
	</display:table>
	</logic:notEmpty>
</body>
</html>