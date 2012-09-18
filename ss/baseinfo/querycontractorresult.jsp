<%@page import="java.math.BigDecimal"%>
<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.domainobjects.*"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
	media="screen, print" />
<script language="javascript" type="text/javascript">
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
	//�༭ǩԼ��ͬ
	function toEditContract(idValue){
        var url = "${ctx}/contractAction.do?method=editContractForm&contractorId=" + idValue;
        self.location.replace(url);
	}
	//���ǩԼ��ͬ
	function toAddContract(idValue){
        var url = "${ctx}/contractAction.do?method=addContractForm&contractorId=" + idValue;
        self.location.replace(url);
	}
	//�鿴ǩԼ��ͬ
	function toViewContract(idValue){
        var url = "${ctx}/contractAction.do?method=viewContract&contractorId=" + idValue;
        self.location.replace(url);
	}
</script>
<%
	UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
			"LOGIN_USER");
%>
<template:titile value="��ѯ�ⲿ��λ��Ϣ" />
<display:table name="sessionScope.queryresult"
	requestURI="${ctx}/contractorAction.do?method=queryContractor" id="currentRowObject" pagesize="18">
	<!--display:column property="depttype" title="��λ����"maxLength="10"/>-->
	<display:column property="contractorname" title="��λ����" maxLength="19" />
	<display:column property="parentcontractorid" title="�ϼ���λ" maxLength="10" />
	<display:column property="linkmaninfo" title="��ϵ�绰" maxLength="11" />
	<display:column property="pactbegindate" title="ǩԼʱ��" maxLength="10" />
	<display:column property="pactterm" title="��ͬ����(��)" />
	<display:column property="regionid" title="��������" />
	<display:column media="html" title="����">
		<%
			BasicDynaBean object = (BasicDynaBean) pageContext
							.findAttribute("currentRowObject");
					String contractorid = (String) object.get("contractorid");
					String deptType = userinfo.getDeptype();
					String flag = (String)object.get("flag");
		%>
		<apptag:checkpower thirdmould="70304" ishead="0">
			<a href="javascript:toEdit('<%=contractorid%>')">�޸�</a>
		</apptag:checkpower>
		<apptag:checkpower thirdmould="70305" ishead="0">
			<a href="javascript:toDelete('<%=contractorid%>')">ɾ��</a>
		</apptag:checkpower>
		<%
			if ("1".equals(deptType)) {
		%>
			<a href="javascript:toAddContract('<%=contractorid%>')">��ӱ�װ�</a>
		<%
				if ("edit".equals(flag)) {
		%>
				<a href="javascript:toEditContract('<%=contractorid%>')">������װ�</a>
			<%}%>
			<a href="javascript:toViewContract('<%=contractorid%>')">�鿴��װ�</a>
		<%
			}
		%>
	</display:column>
</display:table>
<logic:empty name="queryresult">
</logic:empty>
<logic:notEmpty name="queryresult">
	<html:link action="/contractorAction.do?method=exportContractorResult">����ΪExcel�ļ�</html:link>
</logic:notEmpty>
