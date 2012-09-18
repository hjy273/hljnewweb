<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
	media="screen, print" />
<link rel='stylesheet' type='text/css'
	href='${ctx}/js/extjs/resources/css/ext-all.css' />
<script type='text/javascript'
	src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
<script type="text/javascript">
var planExecuteResultWin;
showPlanExecuteResult=function(patrolId){
	var actionUrl="${ctx}/PlanMonthlyStatAction.do?method=showPlanExecuteResult";
	var queryString="patrol_id="+patrolId;
	//location=actionUrl+"&"+queryString+"&rnd="+Math.random();
	planExecuteResultWin = new Ext.Window({
		layout : 'fit',
		width:850,height:300, 
		resizable:true,
		closeAction : 'close', 
		modal:true,
		autoScroll:true,
		autoLoad:{url: actionUrl+"&"+queryString+"&rnd="+Math.random(),scripts:true}, 
		plain: true,
		title:"查看巡检组的巡检计划执行结果信息" 
	});
	planExecuteResultWin.show(Ext.getBody());
}
closePlanExecuteResultWin=function(){
	planExecuteResultWin.close();
}
</script>
<body style="width: 95%">
	<div class='title2'
		style='font-size: 14px; font-weight: 600; bottom: 1' align='center'>
		<bean:write name="queryCon" property="conName" />
		公司
		<bean:write name="queryCon" property="endYear" />
		年
		<bean:write name="queryCon" property="endMonth" />
		月份巡检组统计结果
	</div>
	<hr width='100%' size='1'>
	<display:table name="sessionScope.patrolmanlist" id="currentRowObject"
		pagesize="18">
		<display:column media="html" title="巡检组" sortable="true">
			<bean:define id="patrolId" name="currentRowObject"
				property="patrolid"></bean:define>
			<bean:define id="patrolName" name="currentRowObject"
				property="executorid"></bean:define>
			<a href="javascript:showPlanExecuteResult('${patrolId }');">${patrolName
				}</a>
		</display:column>
		<display:column property="terminalnumber" title="巡检设备总数"
			sortable="true"></display:column>
		<display:column property="planpoint" title="计划巡检点次" sortable="true"></display:column>
		<display:column property="factpoint" title="实际巡检点次" sortable="true"></display:column>
		<display:column property="patrolp" title="巡检率(%)" sortable="true"></display:column>
		<display:column media="html" title="考核结果">
			<%
			    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
			    String examineresult = object.get("examineresult").toString();
			    int i = Integer.parseInt(examineresult);
			    String path = (String) application.getAttribute("ctx");
			    switch (i) {
			    case 1:
			        out.print("<img src=\"" + path + "/images/1.jpg\"/>");
			        break;
			    case 2:
			        out.print("<img src=\"" + path + "/images/2.jpg\"/>");
			        break;
			    case 3:
			        out.print("<img src=\"" + path + "/images/3.jpg\"/>");
			        break;
			    case 4:
			        out.print("<img src=\"" + path + "/images/4.jpg\"/>");
			        break;
			    case 5:
			        out.print("<img src=\"" + path + "/images/5.jpg\"/>");
			        break;
			    default:
			        out.print("<img src=\"" + path + "/images/0.jpg\"/>");
			    }
			%>
		</display:column>
	</display:table>
</body>