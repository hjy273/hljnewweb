<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
	media="screen, print" />
<body style="width: 95%">
	<link rel='stylesheet' type='text/css'
		href='${ctx}/js/extjs/resources/css/ext-all.css' />
	<script type='text/javascript'
		src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
	<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
	<script type="text/javascript">
var sublineExecuteResultWin;
showPlanSublineExecuteResult=function(planId){
	var actionUrl="${ctx}/PlanMonthlyStatAction.do?method=showPlanSublineExecuteResult";
	var queryString="plan_id="+planId;
	//location=actionUrl+"&"+queryString+"&rnd="+Math.random();
	sublineExecuteResultWin = new Ext.Window({
		layout : 'fit',
		width:850,height:300, 
		resizable:true,
		closeAction : 'close', 
		modal:true,
		autoScroll:true,
		autoLoad:{url: actionUrl+"&"+queryString+"&rnd="+Math.random(),scripts:true}, 
		plain: true,
		title:"�鿴�ƻ���Ѳ���߶�ִ�н����Ϣ" 
	});
	sublineExecuteResultWin.show(Ext.getBody());
}
closePlanSublineExecuteResultWin=function(){
	sublineExecuteResultWin.close();
}
</script>
	<div class='title2'
		style='font-size: 14px; font-weight: 600; bottom: 1' align='center'>
		<bean:write name="queryCon" property="conName" />
		��˾
		<bean:write name="queryCon" property="endYear" />
		��
		<bean:write name="queryCon" property="endMonth" />
		�·�Ѳ��ƻ�ִ�н��
	</div>
	<hr width='100%' size='1'>
	<display:table name="sessionScope.planexceute" id="currentRowObject"
		pagesize="18">
		<display:column media="html" title="�ƻ�����" sortable="true">
			<bean:define id="planId" name="currentRowObject" property="planid"></bean:define>
			<bean:define id="planName" name="currentRowObject"
				property="planname"></bean:define>
			<a href="javascript:showPlanSublineExecuteResult('${planId }');">${planName
				}</a>
		</display:column>
		<display:column property="executorid" title="Ѳ����" sortable="true"></display:column>
		<display:column property="planpoint" title="�ƻ�Ѳ����" sortable="true"></display:column>
		<display:column property="factpoint" title="ʵ��Ѳ����" sortable="true"></display:column>
		<display:column property="patrolp" title="Ѳ����(%)" sortable="true"></display:column>
		<display:column media="html" title="���˽��">
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
	<logic:notEmpty name="planexceute" scope="session">
		<html:link
			action="/PlanMonthlyStatAction.do?method=exportPlanExecuteResult">����ΪExcel�ļ�</html:link>
	</logic:notEmpty>
</body>