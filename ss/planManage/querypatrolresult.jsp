<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
	media="screen, print" />
	<link rel="stylesheet" href="${ctx}/css/display_style.css" type="text/css"
	media="screen, print" />
<script language="javascript" type="text/javascript">
function toDelete(idValue){
    if(confirm("确定删除该纪录？")){
        var url = "${ctx}/batchPlan.do?method=delBatchPlan&id=" + idValue;
        self.location.replace(url);
    }
}
</script>
<body>
<%Date nowDate = new Date(); %>
	<template:titile value="查询计划信息结果" />
	<display:table name="sessionScope.batch" id="currentRowObject" pagesize="18">
		<display:column property="batchname" title="批量计划名称" sortable="true" />
		<display:column property="startdate" title="开始日期" sortable="true" />
		<display:column property="enddate" title="结束日期" sortable="true" />
		<apptag:checkpower thirdmould="21605" ishead="0">
			<display:column media="html" title="操作">
				<%
					DateUtil util = new DateUtil();
					BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
					String id = (String) object.get("id");
					String startdate = (String) object.get("startdate");
					String enddate = (String) object.get("enddate");
					Date sdate = util.parseDate(startdate);
					Date edate = util.parseDate(enddate);
					if(sdate.before(nowDate) && edate.after(nowDate)){
						out.print("--");
					}else if(sdate.before(nowDate) && edate.before(nowDate)){
						out.print("--");
					}else{
					
				%>
				<a
					href="javascript:toDelete('<%=id%>')">删除</a>
			    <%} %>
			</display:column>
		</apptag:checkpower>

	</display:table>
	
</body>
