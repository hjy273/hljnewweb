<%@ page language="java" contentType="text/html; charset=GBK"%><%@include
	file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.domainobjects.*"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
	media="screen, print" />
<script language="javascript" type="">
function toDelete(idValue){
    if(confirm("确定删除该纪录？")){
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
<template:titile value="代维单位信息" />
<display:table name="sessionScope.RESULTLIST" id="currentRowObject"
	pagesize="18">
	<display:column property="contractorname" title="单位简称" maxLength="10" />
	<display:column property="parentcontractorid" title="上级单位"
		maxLength="10" />
	<display:column property="organization_type" title="组织类型" maxLength="10" />
	<display:column property="resource_id" title="合作专业" />
	<display:column property="regionid" title="区域" maxLength="10" />
	<display:column property="linkmaninfo" title="联 系 人" maxLength="10" />
	<display:column property="principalinfo" title="负责人A" maxLength="10" />
	<display:column property="tel" title="联系电话" maxLength="10" />
	<display:column property="work_phone" title="办公电话" maxLength="10" />
	<display:column media="html" title="操作">
		<%
		    object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
		    contractorid = (String) object.get("contractorid");
		    conregionid = (String) object.get("conregionid");
		%>
		<a href="javascript:toView('<%=contractorid%>')">查看</a>
	</display:column>
	<apptag:checkpower thirdmould="70304" ishead="0">
		<display:column media="html" title="操作">
			<%
			    object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
			    contractorid = (String) object.get("contractorid");
			    conregionid = (String) object.get("conregionid");
			    if (userinfo.getType().equals("11")) {
			        if (conregionid.substring(2, 6).equals("0000")) {
			%>
			<a href="javascript:toEdit('<%=contractorid%>')">修改</a>
			<%
			    } else {
			%>
			<a href="javascript:toEdit('<%=contractorid%>')">修改</a>
			<%
			    }
			    } else {
			%>
			<a href="javascript:toEdit('<%=contractorid%>')">修改</a>
			<%
			    }
			%>
		</display:column>
	</apptag:checkpower>
	<apptag:checkpower thirdmould="70305" ishead="0">

		<display:column media="html" title="操作">
			<%
			    object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
			    contractorid = (String) object.get("contractorid");
			    conregioniddel = (String) object.get("conregionid");
			    if (userinfo.getType().equals("11")) {
			        if (conregioniddel.substring(2, 6).equals("0000")) {
			%>
			<a href="javascript:toDelete('<%=contractorid%>')">删除</a>
			<%
			    } else {
			%>
			<a href="javascript:toDelete('<%=contractorid%>')">删除</a>
			<%
			    }
			    } else {
			%>
			<a href="javascript:toDelete('<%=contractorid%>')">删除</a>
			<%
			    }
			%>
		</display:column>
	</apptag:checkpower>
</display:table>
<html:link action="/contractorAction.do?method=exportContractorResult">导出为Excel文件</html:link>
