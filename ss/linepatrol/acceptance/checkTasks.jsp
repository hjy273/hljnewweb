<%@include file="/common/header.jsp"%>
<html>
<head>
	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
	<title></title>
	
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
		function setChoose(id, obj){
			var val = obj.options[obj.selectedIndex].value;
			var url = "${ctx}/acceptanceAction.do?method=saveSpecify&objectId="+id+'&contractorId='+val;
			sendAjax(url);
		}
		function sendAjax(url){
			$('submit').disabled = "disabled";
			var myAjax = new Ajax.Request(
				url, 
				{method:"post", onComplete:callback, asynchronous:true}
			);
		}
		function callback(){
			$('submit').disabled = "";
		}
	</script>
</head>
<body>
	<template:titile value="��׼���ս�ά����" />
	<html:form action="/acceptanceAction.do?method=check" styleId="form">
		<input type="hidden" name="id" value="${id}" />
		<template:formTable namewidth="200" contentwidth="400">
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
			<template:formTr name="��ע">
				${apply.remark}
			</template:formTr>
			<template:formTr name="�����ά">
				 <c:forEach items="${dept}" var="map" >
				 	${map["contractorName"]}
				 </c:forEach>
			</template:formTr>
		</template:formTable>
		<c:choose>
			<c:when test="${apply.resourceType eq '1'}">
				<display:table name="sessionScope.apply.cables" id="row" export="false" sort="list" pagesize="10" defaultsort="1" defaultorder="ascending">
					<display:column property="cableNo" title="���±��" sortable="true"/>
					<display:column property="trunk" title="�����м̶�" sortable="true"/>
					<display:column property="fibercoreNo" title="��о��" sortable="true"/>
					<display:column media="html" title="���¼���" sortable="true">
						${cableLevels[row.cableLevel]}
					</display:column>
					<display:column media="html" title="���³���" sortable="true">
						${row.cableLength}ǧ��
					</display:column>
					<display:column media="html" title="����" sortable="true">
						<a href="javascript:viewCable('${row.id}')">�鿴</a>
					</display:column>
					<display:column media="html" title="���쵥λ" sortable="true">
						${deptMap[row.id]}
					</display:column>
					<display:column media="html" title="���쵥λ����" sortable="true">
						��${numberMap[row.id]}��
					</display:column>
					<display:column media="html" title="��׼">
						<select name="contractor" onchange="setChoose('${row.id}',this)">
							<option value="">��ѡ��</option>
							<c:forEach var="dept" items="${dept}">
								<option value="${dept.contractorId}" <c:if test="${specify[row.id] eq dept.contractorId}">selected</c:if>>${dept.contractorName}</option>
							</c:forEach>
						</select>
					</display:column>
				</display:table>
			</c:when>
			<c:otherwise>
				<display:table name="sessionScope.apply.pipes" id="row" export="false" sort="list" pagesize="10" defaultsort="1" defaultorder="ascending">
					<display:column property="projectName" title="��������" sortable="true"/>
					<display:column property="pipeAddress" title="�ܵ��ص�" sortable="true"/>
					<display:column property="pipeRoute" title="��ϸ·��" sortable="true"/>
					<display:column property="builder" title="ʩ����λ" sortable="true"/>
					<display:column media="html" title="�ܵ�����" sortable="true">
						${pipePropertys[row.pipeProperty]}
					</display:column>
					<display:column media="html" title="����" sortable="true">
						<a href="javascript:viewPipe('${row.id}')">�鿴</a>
					</display:column>
					<display:column media="html" title="���쵥λ" sortable="true">
						${deptMap[row.id]}
					</display:column>
					<display:column media="html" title="���쵥λ����" sortable="true">
						��${numberMap[row.id]}��
					</display:column>
					<display:column media="html" title="��׼">
						<select name="contractor" onchange="setChoose('${row.id}',this)">
							<option value="">��ѡ��</option>
							<c:forEach var="dept" items="${dept}">
								<option value="${dept.contractorId}" <c:if test="${specify[row.id] eq dept.contractorId}">selected</c:if>>${dept.contractorName}</option>
							</c:forEach>
						</select>
					</display:column>
				</display:table>
			</c:otherwise>
		</c:choose>
		<table class="tdlist">
			<tr><td class="tdlist">
				<input type="submit" id="submit" value="�ύ" class="button" />
				<input type="button" value="����" class="button" onclick="history.back();"/>
			</td></tr>
		</table>
	</html:form>
</body>
</html>