<%@ page language="java" contentType="text/html; charset=GBK"%><%@include
	file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.domainobjects.*"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
	media="screen, print" />
<script language="javascript" type="">
function toDelete(idValue){
    if(confirm("ȷ��ɾ���ü�¼��")){
        var url = "${ctx}/contractorAction.do?method=deleteContractor&id=" + idValue;
        self.location.replace(url);
    }

}

function toEdit(idValue){
        var url = "${ctx}/contractorAction.do?method=loadContractor&id=" + idValue;
        self.location.replace(url);

}

function toView(idValue){
        var url = "${ctx}/contractorAction.do?method=viewContractor&id=" + idValue;
        self.location.replace(url);

}

</script>
<%
    UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
    BasicDynaBean object;
    String contractorid;
    String conregionid;
    String conregioniddel;
%>
<template:titile value="��ά��λ��Ϣ" />
<display:table name="sessionScope.RESULTLIST" id="currentRowObject"
	pagesize="18">
	<display:column property="contractorname" title="��λ���" maxLength="10" />
	<display:column property="parentcontractorid" title="�ϼ���λ"
		maxLength="10" />
	<display:column property="organization_type" title="��֯����" maxLength="10" />
	<display:column property="resource_id" title="����רҵ" />
	<display:column property="regionid" title="����" maxLength="10" />
	<display:column property="linkmaninfo" title="�� ϵ ��" maxLength="10" />
	<display:column property="principalinfo" title="������A" maxLength="10" />
	<display:column property="tel" title="��ϵ�绰" maxLength="10" />
	<display:column property="work_phone" title="�칫�绰" maxLength="10" />
	<display:column media="html" title="����">
		<%
		    object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
		    contractorid = (String) object.get("contractorid");
		    conregionid = (String) object.get("conregionid");
		%>
		<a href="javascript:toView('<%=contractorid%>')">�鿴</a>
	</display:column>
	<apptag:checkpower thirdmould="70304" ishead="0">
		<display:column media="html" title="����">
			<%
			    object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
			    contractorid = (String) object.get("contractorid");
			    conregionid = (String) object.get("conregionid");
			    if (userinfo.getType().equals("11")) {
			        if (conregionid.substring(2, 6).equals("0000")) {
			%>
			<a href="javascript:toEdit('<%=contractorid%>')">�޸�</a>
			<%
			    } else {
			%>
			<a href="javascript:toEdit('<%=contractorid%>')">�޸�</a>
			<%
			    }
			    } else {
			%>
			<a href="javascript:toEdit('<%=contractorid%>')">�޸�</a>
			<%
			    }
			%>
		</display:column>
	</apptag:checkpower>
	<apptag:checkpower thirdmould="70305" ishead="0">

		<display:column media="html" title="����">
			<%
			    object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
			    contractorid = (String) object.get("contractorid");
			    conregioniddel = (String) object.get("conregionid");
			    if (userinfo.getType().equals("11")) {
			        if (conregioniddel.substring(2, 6).equals("0000")) {
			%>
			<a href="javascript:toDelete('<%=contractorid%>')">ɾ��</a>
			<%
			    } else {
			%>
			<a href="javascript:toDelete('<%=contractorid%>')">ɾ��</a>
			<%
			    }
			    } else {
			%>
			<a href="javascript:toDelete('<%=contractorid%>')">ɾ��</a>
			<%
			    }
			%>
		</display:column>
	</apptag:checkpower>
</display:table>
<html:link action="/contractorAction.do?method=exportContractorResult">����ΪExcel�ļ�</html:link>
