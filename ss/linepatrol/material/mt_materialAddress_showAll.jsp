<%@include file="/common/header.jsp"%>
<!--%@include file="/common/listhander.jsp"%-->
<%@ page language="java" contentType="text/html; charset=GBK"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript">
function toDelete(idValue){
    if(confirm("ȷ��ɾ���ü�¼��")){
       var url = "${ctx}/MTAddressAction.do?method=deletePartAddress&id=" + idValue;
        self.location.replace(url);
    }
     //confirm("����ɾ���ü�¼!");

}


function toEdit(idValue){
        var url = "${ctx}/MTAddressAction.do?method=loadAddress&id=" + idValue;
        self.location.replace(url);

}
</script>
<template:titile value="��ѯ���ϴ����Ϣ���"/>
<display:table name="sessionScope.queryresult"
	requestURI="${ctx}/MTAddressAction.do?method=queryAddress"
	id="currentRowObject" pagesize="18">
	<display:column property="address" style="width:30%" title="���ϴ�ŵص�����" />
	<display:column property="contractorid" style="width:30%" title="��ά��λ" />
	<display:column property="remark" style="width:30%" title="��ע" />
	<display:column media="html" style="width:10%" title="����">
		<%
			BasicDynaBean object = (BasicDynaBean) pageContext
							.findAttribute("currentRowObject");
					Object id = null;
					if (object != null)
						id = object.get("id");
		%>
		<a href="javascript:toEdit('<%=id%>')">�޸�</a>
		<a href="javascript:toDelete('<%=id%>')">ɾ��</a>
	</display:column>
</display:table>
<logic:empty name="queryresult">
</logic:empty>
<logic:notEmpty name="queryresult">
<html:link action="/MTAddressAction.do?method=exportMaterialAddressResult">����ΪExcel�ļ�</html:link>
</logic:notEmpty>

