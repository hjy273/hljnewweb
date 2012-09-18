<%@include file="/common/header.jsp"%>
<html>
<head>
	<title></title>
	
	<script type='text/javascript' src='${ctx}/linepatrol/js/change_style.js'></script>
	
	<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
	<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
	<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
	
	<script type="text/javascript">
		var win;
		function showWin(url){
			win = new Ext.Window({
				layout : 'fit',
				width:600,
				height:400,
				resizable:true,
				closeAction : 'close',
				modal:true,
				autoScroll:true,
				autoLoad:{url:url, scripts:true},
				plain: true
			});
			win.show(Ext.getBody());
		}
		function closeProcessWin(){
			win.close();
		}
		function his(id){
			var url = "${ctx}/process_history.do?method=showProcessHistoryList&object_type=overhaul&is_close=0&object_id="+id;
			showWin(url);
		}
		function viewExamInfo(taskId,contractorId){
			var url = "${ctx}/overHaulAction.do?method=viewExamInfo&taskId="+taskId+"&contractorId="+contractorId;
			showWin(url);
		}
	</script>
</head>
<body onload="changeStyle()">
	<template:titile value="���޲鿴" />
	<template:formTable namewidth="150" contentwidth="400">
		<html:form action="/overHaulAction.do" styleId="form">
			<template:formTr name="������Ŀ����">
				${OverHaul.projectName}
			</template:formTr>
			<template:formTr name="������">
				${OverHaul.projectCreator}
			</template:formTr>
			<template:formTr name="Ԥ�����">
				${OverHaul.budgetFee}Ԫ
			</template:formTr>
			<template:formTr name="�������ʼʱ��">
				<bean:write name="OverHaul" property="startTime" format="yyyy-MM-dd" />
			</template:formTr>
			<template:formTr name="�����������ʱ��">
				<bean:write name="OverHaul" property="endTime" format="yyyy-MM-dd" />
			</template:formTr>
			<template:formTr name="��ά��˾">
				${cons}
			</template:formTr>
			<template:formTr name="�������ע">
				${OverHaul.projectRemark}
			</template:formTr>
			<template:formTr name="����״̬">
				<c:choose>
					<c:when test="${OverHaul.state eq '00'}">
						�ѽ���
					</c:when>
					<c:otherwise>
						δ����
					</c:otherwise>
				</c:choose>
			</template:formTr>
			<logic:notEmpty property="cancelUserId" name="OverHaul">
				<tr class=trcolor>
					<td class="tdr">
						ȡ���ˣ�
					</td>
					<td class="tdl">
						<bean:write property="cancelUserName" name="OverHaul" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						ȡ��ʱ�䣺
					</td>
					<td class="tdl">
						<bean:write property="cancelTimeDis" name="OverHaul" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						ȡ��ԭ��
					</td>
					<td class="tdl">
						<bean:write property="cancelReason" name="OverHaul" />
					</td>
				</tr>
			</logic:notEmpty>
			<template:formTr name="�����Ϣ">
				<table width=100% border=1>
					<tr>
						<td align='center' width='15%'>��ά</td>
				   		<td align='center' width='40%'>�������</td>
						<td align='center' width='15%'>��ӷ���</td>
						<td align='center' width='20%'>��������</td>
						<td align='center' width='10%'>״̬</td>
					</tr>
					<c:forEach var="apply" items="${OverHaul.applys}">
						<c:forEach var="cut" items="${apply.overHaulCuts}">
							<tr>
								<td>
									<apptag:assorciateAttr table="contractorinfo" label="contractorname" key="contractorid" keyValue="${apply.contractorId}" />
								</td>
						   		<td>${cut.cutName}</td>
								<td>${cut.cutFee}Ԫ</td>
								<td>
									<bean:write name="apply" property="createTime" format="yyyy-MM-dd"/>
								</td>
								<td>
									<c:choose>
										<c:when test="${apply.state eq '40'}">
											<font color="blue">ͨ��</font>
										</c:when>
										<c:otherwise>
											--
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</c:forEach>
					</c:forEach>
				</table>
			</template:formTr>
			<template:formTr name="������Ϣ">
				<table width=100% border=1>
					<tr>
						<td align='center' width='15%'>��ά</td>
						<td align='center' width='40%'>��������</td>
						<td align='center' width='15%'>���̷���</td>
						<td align='center' width='20%'>��������</td>
						<td align='center' width='10%'>״̬</td>
					</tr>
					<c:forEach var="apply" items="${OverHaul.applys}">
						<c:forEach var="project" items="${apply.overHaulProjects}">
							<tr>
						   		<td>
						   			<apptag:assorciateAttr table="contractorinfo" label="contractorname" key="contractorid" keyValue="${apply.contractorId}" />
						   		</td>
						   		<td>${project.projectName}</td>
								<td>${project.projectFee}Ԫ</td>
								<td>
									<bean:write name="apply" property="createTime" format="yyyy-MM-dd"/>
								</td>
								<td>
									<c:choose>
										<c:when test="${apply.state eq '40'}">
											<font color="blue">ͨ��</font>
										</c:when>
										<c:otherwise>
											--
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</c:forEach>
					</c:forEach>
				</table>	
			</template:formTr>
			<template:formTr name="Ŀǰ��Ŀ����">
				<div id="useFee">${OverHaul.useFee}Ԫ</div>
			</template:formTr>
			<template:formTr name="ʣ�����">
				<div id="remainFee">
					<c:choose>
						<c:when test="${OverHaul.remainFee >= 0}">
							<font color=blue>${OverHaul.remainFee}Ԫ</font>
						</c:when>
						<c:otherwise>
							<font color=red>${OverHaul.remainFee}Ԫ</font>
						</c:otherwise>
					</c:choose>
				</div>
			</template:formTr>
			<template:formTr name="������Ϣ">
				<c:forEach var="apply" items="${OverHaul.applys}">
					<c:if test="${apply.state=='50'}">
						<a onclick="viewExamInfo('${apply.id}','${apply.contractorId}')" style="cursor: pointer; color: blue;">
							<apptag:assorciateAttr table="contractorinfo" label="contractorname" key="contractorid" keyValue="${apply.contractorId}" />
						</a>
					</c:if>
				</c:forEach>
			</template:formTr>
			<template:formSubmit>
				<c:if test="${type eq 'history'}">
					<td>
						<input type="button" class="button" value="������ʷ" onclick="his('${OverHaul.id}')" />
					</td>
				</c:if>
					<td>
						<input type="button" class="button" value="����" onclick="history.back()" />
					</td>
			</template:formSubmit>
		</html:form>
	</template:formTable>
</body>