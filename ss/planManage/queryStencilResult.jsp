<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
	media="screen, print" />
<script language="javascript" type="text/javascript">
function toDelete(idValue){
    if(confirm("ȷ��ɾ���ü�¼��")){
        var url = "${ctx}/StencilAction.do?method=delStencil&id=" + idValue;
        self.location.replace(url);
    }
}

function toGetForm(idValue){
        var url = "${ctx}/StencilAction.do?method=loadStencil&id=" + idValue;
        self.location.replace(url);

}

function toEdit(idValue){
        var url = "${ctx}/StencilAction.do?method=loadStencil&id=" + idValue;
        self.location.replace(url);

}
</script>
<body>

	<template:titile value="��ѯ�ƻ�ģ����Ϣ���" />
	<display:table name="sessionScope.queryresult" requestURI="${ctx}/StencilAction.do?method=queryStencil" id="currentRowObject" pagesize="18">
		<display:column property="stencilname" title="ģ������" sortable="true" />
		<logic:equal value="group" name="PMType">
			<display:column property="patrolid" title="Ѳ��ά����" sortable="true" />
		</logic:equal>
		<logic:notEqual value="group" name="PMType">
			<display:column property="patrolid" title="Ѳ��ά����" sortable="true" />
		</logic:notEqual>
		<display:column property="createdate" title="��������" sortable="true" />
		
		<apptag:checkpower thirdmould="20304" ishead="0">
			<display:column media="html" title="����">
				<%
					BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
					String id = (String) object.get("stencilid");
				%>
				<a
					href="javascript:toEdit('<%=id%>')">�޸�</a>
			</display:column>
		</apptag:checkpower>
		<apptag:checkpower thirdmould="20305" ishead="0">
			<display:column media="html" title="����">
				<%
					BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
					String id = (String) object.get("stencilid");
					
				%>
				<a
					href="javascript:toDelete('<%=id%>')">ɾ��</a>
			</display:column>
		</apptag:checkpower>

	</display:table>
	
</body>
