<%@include file="/common/header.jsp"%>
<html>
<head>
	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
	
	<title></title>
	<script type="text/javascript">
		function recordData(id, objectId, type){
			window.location.href = '${ctx}/acceptanceAction.do?method=recordData&id='+id+'&objectId='+objectId+'&type='+type;
		}
		function editData(id, objectId, type){
			window.location.href = '${ctx}/acceptanceAction.do?method=editData&id='+id+'&objectId='+objectId+'&type='+type;
		}
		function check(){
			if($('approver').value == ''){
				alert('��׼�˲���Ϊ��');
				return false;
			}
			return true;
		}
		function back(){
			window.location.href='${ctx}/acceptanceAction.do?method=findToDoWork';
		}
		function exportExcel(){
			window.location.href = '${ctx}/acceptanceAction.do?method=export';
		}
	</script>
</head>
<body>
	<template:titile value="���ս�ά����¼��" />
	<html:form action="/acceptanceAction.do?method=push" styleId="form" onsubmit="return check()">
		<input type="hidden" name="id" value="${apply.id}">
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
			<template:formTr name="��������">
				<input type="button" class="button" value="����" onclick="exportExcel();" />
			</template:formTr>	
			<tr class=trcolor>
				<apptag:approve tableClass="tdlist" labelClass="tdulleft" displayType="view" objectId="${apply.subflowId}" objectType="LP_ACCEPTANCE_APPROVE" />
			</tr>
		</template:formTable>
		<c:choose>
			<c:when test="${apply.resourceType eq '1'}">
				<display:table name="sessionScope.apply.cables" id="row"  pagesize="8" export="false" defaultsort="2" defaultorder="ascending">
					<display:column property="cableNo" title="���±��"/>
					<display:column property="trunk" title="�����м̶�"/>
					<display:column property="fibercoreNo" title="��о��"/>
					<display:column media="html" title="���¼���">
						<apptag:quickLoadList style="width:130px" keyValue="${row.cableLevel}" cssClass="select" name="cableLevel" listName="cabletype" type="look" />
					</display:column>
					<display:column media="html" title="���³���">
						${row.cableLength}ǧ��
					</display:column>
					<display:column media="html" title="����">
						<c:choose>
							<c:when test="${row.isrecord eq '0'}">
								<a href="javascript:recordData('${apply.subflowId}','${row.id}','${type}')">¼������</a>
							</c:when>
							<c:otherwise>
								<a href="javascript:editData('${apply.subflowId}','${row.id}','${type}')">�޸�����</a>
							</c:otherwise>
						</c:choose>
					</display:column>
				</display:table>
			</c:when>
			<c:otherwise>
				<display:table name="sessionScope.apply.pipes" id="row"  pagesize="8" export="false" defaultsort="1" defaultorder="ascending">
					<display:column property="projectName" title="��������"/>
					<display:column property="pipeAddress" title="�ܵ��ص�"/>
					<display:column property="pipeRoute" title="��ϸ·��"/>
					<display:column property="builder" title="ʩ����λ"/>
					<display:column media="html" title="��Ȩ����">
						<apptag:quickLoadList style="width:130px" keyValue="${row.pipeProperty}" cssClass="select" name="pipeProperty" listName="property_right" type="look" />
					</display:column>
					<display:column media="html" title="�ܵ�����">
						<apptag:quickLoadList style="width:130px" keyValue="${row.pipeType}" cssClass="select" name="pipeType" listName="pipe_type" type="look" />
					</display:column>
					<display:column media="html" title="����">
						<c:choose>
							<c:when test="${row.isrecord eq '0'}">
								<a href="javascript:recordData('${apply.subflowId}','${row.id}','${type}')">¼������</a>
							</c:when>
							<c:otherwise>
								<a href="javascript:editData('${apply.subflowId}','${row.id}','${type}')">�޸�����</a>
							</c:otherwise>
						</c:choose>
					</display:column>
				</display:table>
			</c:otherwise>
		</c:choose>
		<apptag:approverselect label="��׼��" inputName="approver" 
		 approverType="approver" objectType="LP_ACCEPTANCE_APPLY" objectId="${apply.id}" 
		 spanId="approverSpan" inputType="radio"/>
		<table class="tdlist">
			<tr><td class="tdlist">
				<input type="submit" value="�ύ" id="submit" class="button" />
				<input type="button" value="����" class="button" onclick="back();"/>
			</td></tr>
		</table>
	</html:form>
</body>