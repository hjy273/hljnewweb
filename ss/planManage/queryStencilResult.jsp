<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
	media="screen, print" />
<script language="javascript" type="text/javascript">
function toDelete(idValue){
    if(confirm("确定删除该纪录？")){
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

	<template:titile value="查询计划模板信息结果" />
	<display:table name="sessionScope.queryresult" requestURI="${ctx}/StencilAction.do?method=queryStencil" id="currentRowObject" pagesize="18">
		<display:column property="stencilname" title="模板名称" sortable="true" />
		<logic:equal value="group" name="PMType">
			<display:column property="patrolid" title="巡检维护组" sortable="true" />
		</logic:equal>
		<logic:notEqual value="group" name="PMType">
			<display:column property="patrolid" title="巡检维护人" sortable="true" />
		</logic:notEqual>
		<display:column property="createdate" title="创建日期" sortable="true" />
		
		<apptag:checkpower thirdmould="20304" ishead="0">
			<display:column media="html" title="操作">
				<%
					BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
					String id = (String) object.get("stencilid");
				%>
				<a
					href="javascript:toEdit('<%=id%>')">修改</a>
			</display:column>
		</apptag:checkpower>
		<apptag:checkpower thirdmould="20305" ishead="0">
			<display:column media="html" title="操作">
				<%
					BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
					String id = (String) object.get("stencilid");
					
				%>
				<a
					href="javascript:toDelete('<%=id%>')">删除</a>
			</display:column>
		</apptag:checkpower>

	</display:table>
	
</body>
