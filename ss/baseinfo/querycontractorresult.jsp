<%@page import="java.math.BigDecimal"%>
<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.domainobjects.*"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
	media="screen, print" />
<script language="javascript" type="text/javascript">
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
	//编辑签约合同
	function toEditContract(idValue){
        var url = "${ctx}/contractAction.do?method=editContractForm&contractorId=" + idValue;
        self.location.replace(url);
	}
	//添加签约合同
	function toAddContract(idValue){
        var url = "${ctx}/contractAction.do?method=addContractForm&contractorId=" + idValue;
        self.location.replace(url);
	}
	//查看签约合同
	function toViewContract(idValue){
        var url = "${ctx}/contractAction.do?method=viewContract&contractorId=" + idValue;
        self.location.replace(url);
	}
</script>
<%
	UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
			"LOGIN_USER");
%>
<template:titile value="查询外部单位信息" />
<display:table name="sessionScope.queryresult"
	requestURI="${ctx}/contractorAction.do?method=queryContractor" id="currentRowObject" pagesize="18">
	<!--display:column property="depttype" title="单位类型"maxLength="10"/>-->
	<display:column property="contractorname" title="单位名称" maxLength="19" />
	<display:column property="parentcontractorid" title="上级单位" maxLength="10" />
	<display:column property="linkmaninfo" title="联系电话" maxLength="11" />
	<display:column property="pactbegindate" title="签约时间" maxLength="10" />
	<display:column property="pactterm" title="合同期限(月)" />
	<display:column property="regionid" title="所属区域" />
	<display:column media="html" title="操作">
		<%
			BasicDynaBean object = (BasicDynaBean) pageContext
							.findAttribute("currentRowObject");
					String contractorid = (String) object.get("contractorid");
					String deptType = userinfo.getDeptype();
					String flag = (String)object.get("flag");
		%>
		<apptag:checkpower thirdmould="70304" ishead="0">
			<a href="javascript:toEdit('<%=contractorid%>')">修改</a>
		</apptag:checkpower>
		<apptag:checkpower thirdmould="70305" ishead="0">
			<a href="javascript:toDelete('<%=contractorid%>')">删除</a>
		</apptag:checkpower>
		<%
			if ("1".equals(deptType)) {
		%>
			<a href="javascript:toAddContract('<%=contractorid%>')">添加标底包</a>
		<%
				if ("edit".equals(flag)) {
		%>
				<a href="javascript:toEditContract('<%=contractorid%>')">更正标底包</a>
			<%}%>
			<a href="javascript:toViewContract('<%=contractorid%>')">查看标底包</a>
		<%
			}
		%>
	</display:column>
</display:table>
<logic:empty name="queryresult">
</logic:empty>
<logic:notEmpty name="queryresult">
	<html:link action="/contractorAction.do?method=exportContractorResult">导出为Excel文件</html:link>
</logic:notEmpty>
