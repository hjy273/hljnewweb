<%@include file="/common/header.jsp"%>
<html>
<head>
	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
	<title></title>
	<script type="text/javascript">
		function eval(id){
			window.location.href = '${ctx}/hiddangerAction.do?method=evalLink&id='+id;
		}
		function beginTreat(id){
			window.location.href = '${ctx}/hiddangerAction.do?method=beginTreat&id='+id;
		}
		function approve(id,istransfer){
			window.location.href = '${ctx}/hiddangerAction.do?method=approveLink&id='+id+'&transfer='+istransfer;
		}
		function toClose(id){
			window.location.href = '${ctx}/hiddangerAction.do?method=closeLink&id='+id;
		}
		function closeApprove(id,istransfer){
			window.location.href = '${ctx}/hiddangerAction.do?method=closeApproveLink&id='+id+'&transfer='+istransfer;
		}
		function makeplan(id,name,flag,planId){
			window.location.href = '${ctx}/specialplan.do?method=addFrom&ptype=001&isApply='+flag+'&pName='+name+'&id='+id;
		}
		function editplan(id,hiddangerId){
			window.location.href = '${ctx}/specialplan.do?method=loadPlan&ptype=001&isApply=true&type=edit&id='+id+'&businessId='+hiddangerId;;
		}
		function endplan(id){
			window.location.href = '${ctx}/watchAction.do?method=approvePlanLink&end=1&id='+id;
		}
		function endapproveplan(id,istransfer){
			window.location.href = '${ctx}/watchAction.do?method=approvePlanLink&endapprove=1&id='+id+'&transfer='+istransfer;;
		}
		function approveplan(id,istransfer){
			window.location.href = '${ctx}/watchAction.do?method=approvePlanLink&id='+id+'&transfer='+istransfer;
		}
		function toExam(id){
			window.location.href = '${ctx}/hiddangerExamAction.do?method=examLink&id='+id;
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
	<template:titile value="�����б�(<font color='red'>��${number}������</font>)" />
	<div style="text-align:center;">
		<iframe src="${ctx}/hiddangerAction.do?method=viewHideDangerProcess&&task_name=${param.task_name}"
			frameborder="0" id="processGraphFrame" height="100" scrolling="no" width="99%"></iframe>
	</div>
	<display:table name="sessionScope.result" id="row" pagesize="18" export="false" defaultsort="9" defaultorder="descending" sort="list">
		<logic:notEmpty name="row">
			<bean:define id="sendUserId" name="row" property="creater" />
			<bean:define id="id" name="row" property="id"></bean:define>
			<bean:define id="applyState" name="row" property="hiddangerState"></bean:define>
		</logic:notEmpty>.
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
		<display:column media="html" title="����λ" sortable="true">
			<c:out value="${depts[row.treatDepartment]}" />
		</display:column>
		<display:column media="html" title="״̬" sortable="true">
			<c:if test="${row.hiddangerState eq '10'}">
				��Ҫ����
			</c:if>
			<c:if test="${row.hiddangerState eq '20'}">
				��Ҫ�ϱ�
			</c:if>
			<c:if test="${row.hiddangerState eq '30'}">
				��Ҫ�ϱ�
			</c:if>
			<c:if test="${row.hiddangerState eq '40'}">
				��Ҫ�Դ���
			</c:if>
			<c:if test="${row.hiddangerState eq '50'}">
				��Ҫ�ϱ����
			</c:if>
			<c:if test="${row.hiddangerState eq '51'}">
				��Ҫ�ƶ��ƻ�
			</c:if>
			<c:if test="${row.hiddangerState eq '52'}">
				��Ҫ��˼ƻ�
			</c:if>
			<c:if test="${row.hiddangerState eq '53'}">
				�ƻ�ִ����
			</c:if>
			<c:if test="${row.hiddangerState eq '54'}">
				��Ҫ��ֹ���
			</c:if>
			<c:if test="${row.hiddangerState eq '60'}">
				��Ҫ�ر�
			</c:if>
			<c:if test="${row.hiddangerState eq '70'}">
				��Ҫ�ر����
			</c:if>
			<c:if test="${row.hiddangerState eq '00'}">
				��Ҫ��������
			</c:if>
		</display:column>
		<display:column media="html" title="�Ǽ�ʱ��" sortable="true">
			<bean:write name="row" property="createTime" format="yy-MM-dd HH:mm:ss"/>
		</display:column>
		<display:column media="html" title="����" sortable="true">
			<c:if test="${row.hiddangerState eq '10'}">
				<a href="javascript:eval('${row.id}')">��������</a>
			</c:if>
			<c:if test="${row.hiddangerState eq '20' || row.hiddangerState eq '30'}">
				<a href="javascript:beginTreat('${row.id}')">�����ϱ�</a>
			</c:if>
			<c:if test="${row.hiddangerState eq '40'}">
				<a href="javascript:beginTreat('${row.id}')">���д���</a>
			</c:if>
			<c:if test="${row.hiddangerState eq '50'}">
				<c:choose>
					<c:when test="${row.readOnly}">
						<a href="javascript:approve('${row.id}','0')">�����鿴</a>
					</c:when>
					<c:otherwise>
						<a href="javascript:approve('${row.id}','0')">�ϱ����</a> | 
						<a href="javascript:approve('${row.id}','1')">�ϱ�ת��</a>
					</c:otherwise>
				</c:choose>
			</c:if>
			<c:if test="${row.hiddangerState eq '51'}">
				<c:choose>
					<c:when test="${row.hiddangerLevel eq '1'}">					
						<c:choose>
							<c:when test="${empty row.splanId}">
								<a href="javascript:makeplan('${row.id}','${row.name}',true)">�ƶ��ƻ�</a>
							</c:when>
							<c:otherwise>
								<a href="javascript:editplan('${row.splanId}','${row.id}')">�޸ļƻ�</a>
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>
						<a href="javascript:makeplan('${row.id}','${row.name}',false,'${row.splanId}')">�ƶ��ƻ�</a>
					</c:otherwise>
				</c:choose>
			</c:if>
			<c:if test="${row.hiddangerState eq '52'}">
				<c:choose>
					<c:when test="${row.readOnly}">
						<a href="javascript:approveplan('${row.id}','0')">�����鿴</a>
					</c:when>
					<c:otherwise>
						<a href="javascript:approveplan('${row.id}','0')">�ƻ����</a> | 
						<a href="javascript:approveplan('${row.id}','1')">�ƻ�ת��</a>
					</c:otherwise>
				</c:choose>
			</c:if>
			<c:if test="${row.hiddangerState eq '53'}">
				<a href="javascript:endplan('${row.id}')">�ƻ���ֹ</a>
			</c:if>
			<c:if test="${row.hiddangerState eq '54'}">
				<c:choose>
					<c:when test="${row.readOnly}">
						<a href="javascript:endapproveplan('${row.id}','0')">�����鿴</a>
					</c:when>
					<c:otherwise>
						<a href="javascript:endapproveplan('${row.id}','0')">�ƻ���ֹ���</a> | 
						<a href="javascript:endapproveplan('${row.id}','1')">�ƻ���ֹת��</a>
					</c:otherwise>
				</c:choose>
			</c:if>
			<c:if test="${row.hiddangerState eq '60'}">
				<a href="javascript:toClose('${row.id}')">�����ر�</a>
			</c:if>
			<c:if test="${row.hiddangerState eq '70'}">
				<c:choose>
					<c:when test="${row.readOnly}">
						<a href="javascript:closeApprove('${row.id}','0')">�����鿴</a>
					</c:when>
					<c:otherwise>
						<a href="javascript:closeApprove('${row.id}','0')">�ر����</a> | 
						<a href="javascript:closeApprove('${row.id}','1')">�ر�ת��</a>
					</c:otherwise>
				</c:choose>
			</c:if>
			<c:if test="${row.hiddangerState eq '00'}">
				<a href="javascript:toExam('${row.id}')">��������</a>
			</c:if>
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
</html>