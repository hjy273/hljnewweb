<%@ page language="java" contentType="text/html; charset=GBK"%><%@include
	file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
	media="screen, print" />
<script language="javascript">
function toDelete(idValue){
    if(confirm("ȷ��ɾ���ü�¼��")){
        var url = "${ctx}/patrolAction.do?method=deletePatrol&id=" + idValue;
        self.location.replace(url);
    }
}
function toEdit(idValue){
        var url = "${ctx}/patrolAction.do?method=loadPatrol&id=" + idValue;
        self.location.replace(url);

}
function toView(idValue){
        var url = "${ctx}/patrolAction.do?method=viewPatrol&id=" + idValue;
        self.location.replace(url);

}
</script>
<template:titile value="Ѳ��ά������Ϣһ����" />
<display:table name="sessionScope.queryresult" id="currentRowObject"
	pagesize="18">
	<%
	    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
	    String patrolid = "";
	    if (object != null)
	        patrolid = (String) object.get("patrolid");
	%>
	<display:column property="patrolname" title="Ѳ��ά����" />
	<display:column property="parentid" title="��ά��λ" />
	<display:column property="maintenance_area" title="ά������" />
	<display:column property="principal" title="������" />
	<display:column property="phone" title="��ϵ�绰" />
	<display:column property="work_phone" title="פ�ص绰" />
	<display:column media="html" title="����">
		<a href="javascript:toView('<%=patrolid%>')">�鿴</a>
	</display:column>
	<apptag:checkpower thirdmould="70604" ishead="0">
		<display:column media="html" title="����">
			<a href="javascript:toEdit('<%=patrolid%>')">�޸�</a>
		</display:column>
	</apptag:checkpower>
	<apptag:checkpower thirdmould="70605" ishead="0">
		<display:column media="html" title="����">
			<a href="javascript:toDelete('<%=patrolid%>')">ɾ��</a>
		</display:column>
	</apptag:checkpower>
</display:table>
<html:link action="/patrolAction.do?method=exportPatrolMan">����ΪExcel�ļ�</html:link>


