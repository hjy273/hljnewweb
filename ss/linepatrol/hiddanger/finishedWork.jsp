<%@include file="/common/header.jsp"%>
<html>
<head>
	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
	<title></title>
	<script type="text/javascript">
		function view(id){
			window.location.href = '${ctx}/hiddangerQueryAction.do?method=view&param=finished&id='+id;
		}
		//ȡ������
		toCancelForm=function(hideDangerId){
			var url;
			if(confirm("ȷ��Ҫȡ��������������")){
				url="${ctx}/hiddangerAction.do?method=cancelHideDangerForm";
				var queryString="hide_danger_id="+hideDangerId;
				//location=url+"&"+queryString+"&rnd="+Math.random();
				window.open(url+"&"+queryString+"&rnd="+Math.random(),'','width=300,height=200,top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no');
			}
		}
	</script>
</head>
<body>
	<template:titile value="�����Ѱ칤���б�(<font color='red'>��${number}���Ѱ�</font>)" />
	<display:table name="sessionScope.result" id="row" pagesize="18" export="false" defaultsort="9" defaultorder="descending" sort="list">
		<logic:notEmpty name="row">
			<bean:define id="id" name="row" property="id"></bean:define>
			<bean:define id="applyState" name="row" property="hiddangerState"></bean:define>
		</logic:notEmpty>
		
		<display:setProperty name="sort.amount" value="list"/>
		<display:column property="hiddangerNumber" title="�������" sortable="true"/>
		<display:column property="name" title="����" sortable="true"/>
		<display:column media="html" title="����" sortable="true">
			<c:out value="${types[row.type]}" />
		</display:column>
		<display:column media="html" title="����" sortable="true">
			<c:out value="${codes[row.code]}" />
		</display:column>
		<display:column media="html" title="����ʱ��" sortable="true">
			<bean:write name="row" property="findTime" format="yy-MM-dd HH:mm:ss"/>
		</display:column>
		<display:column property="reporter" title="������" sortable="true"/>
		<display:column media="html" title="����λ" maxWords="8" sortable="true">
			<c:out value="${depts[row.treatDepartment]}" />
		</display:column>
		<display:column media="html" title="�Ǽ�ʱ��" sortable="true">
			<bean:write name="row" property="createTime" format="yy-MM-dd HH:mm:ss"/>
		</display:column>
		<display:column media="html" title="����" sortable="true">
			<a href="javascript:view('${row.id}')">�鿴</a>
			<c:if test="${sessionScope.LOGIN_USER.deptype=='1'}">
						<c:if test="${row.hiddangerState eq '10'}">
						  	<a href="javascript:toCancelForm('${row.id}')">|ȡ��</a>
						</c:if>
						<c:if test="${row.hiddangerState eq '20'}">
							<a href="javascript:toCancelForm('${row.id}')">|ȡ��</a>
						</c:if>
						<c:if test="${row.hiddangerState eq '30'}">
							<a href="javascript:toCancelForm('${row.id}')">|ȡ��</a>
						</c:if>
						<c:if test="${row.hiddangerState eq '40'}">
							<a href="javascript:toCancelForm('${row.id}')">|ȡ��</a>
						</c:if>
					</c:if>		
		</display:column>
	</display:table>
</body>