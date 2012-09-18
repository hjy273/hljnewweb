<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<%@include file="/common/header.jsp"%>
<html>
<head>
	<title></title>

	<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
	<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
	<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
	
	<script type='text/javascript' src='${ctx}/linepatrol/js/change_style.js'></script>
	
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
				html:'<iframe scrolling=auto frameborder=0 width=100% height=100% src="'+url+'" />',
				plain: true
			});
			win.show(Ext.getBody());
		}
		function close(){
			win.close();
		}
		function viewCable(id){
			var url = '${ctx}/acceptanceAction.do?method=viewCable&id='+id;
			showWin(url);
		}
		function viewPipe(id){
			var url = '${ctx}/acceptanceAction.do?method=viewPipe&id='+id;
			showWin(url);
		}
	</script>
</head>
<body onload="changeStyle()">
	<template:titile value="�����׼" />
	<html:form action="/acceptanceAction.do?method=recheck" styleId="form">
		<template:formTable namewidth="200" contentwidth="400">
			<input type="hidden" name="id" value="${apply.id}" />
			<input type="hidden" name="chooseContractor" id="chooseContractor" />
			<template:formTr name="��Ŀ����">
				<apptag:assorciateAttr table="userinfo" label="username" key="userid" keyValue="${apply.applicant}" />
			</template:formTr>
			<template:formTr name="��������">
				${apply.name}
			</template:formTr>
			<template:formTr name="������Դ����">
				<c:choose>
					<c:when test="${apply.resourceType eq '1'}">
						����
					</c:when>
					<c:otherwise>
						�ܵ�
					</c:otherwise>
				</c:choose>
			</template:formTr>
			<tr>
				<td colspan=2>
					<c:choose>
						<c:when test="${apply.resourceType eq '1'}">
							<display:table name="sessionScope.apply.cables" id="row" export="false" pagesize="10" sort="list" defaultsort="1" defaultorder="ascending">
								<display:column property="cableNo" title="���±��" sortable="true"/>
								<display:column property="trunk" title="�����м̶�" sortable="true"/>
								<display:column property="fibercoreNo" title="��о��" sortable="true"/>
								<display:column media="html" title="���¼���" sortable="true">
									<apptag:quickLoadList style="width:130px" keyValue="${row.cableLevel}" cssClass="select" name="cableLevel" listName="cabletype" type="look" />
								</display:column>
								<display:column media="html" title="���³���" sortable="true">
									${row.cableLength}ǧ��
								</display:column>
								<display:column media="html" title="����" sortable="true">
									<a href="javascript:viewCable('${row.id}')">�鿴</a>
								</display:column>
							</display:table>
						</c:when>
						<c:otherwise>
							<display:table name="sessionScope.apply.pipes" id="row" export="false" pagesize="10" sort="list" defaultsort="1" defaultorder="ascending">
								<display:column property="projectName" title="��������" sortable="true"/>
								<display:column property="pipeAddress" title="�ܵ��ص�" sortable="true"/>
								<display:column property="pipeRoute" title="��ϸ·��" sortable="true"/>
								<display:column property="builder" title="ʩ����λ" sortable="true"/>
								<display:column media="html" title="�ܵ�����" sortable="true">
									<apptag:quickLoadList style="width:130px" keyValue="${row.pipeProperty}" cssClass="select" name="pipeProperty" listName="property_right" type="look" />
								</display:column>
								<display:column media="html" title="����" sortable="true">
									<a href="javascript:viewPipe('${row.id}')">�鿴</a>
								</display:column>
							</display:table>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
		</template:formTable>
		<table class="tdlist">
			<tr>
				<td class="tdlist">
					<html:submit property="action" styleId="submit" styleClass="button">�ύ</html:submit>
					<input type="button" class="button" value="����" onclick="history.back()" />
				</td>
			</tr>
		</table>
	</html:form>
</body>