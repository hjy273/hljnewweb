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
		title:"�鿴Ѳ�����Ѳ��ƻ�ִ�н����Ϣ" 
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
		��˾
		<bean:write name="queryCon" property="endYear" />
		��
		<bean:write name="queryCon" property="endMonth" />
		�·�Ѳ����ͳ�ƽ��
	</div>
	<hr width='100%' size='1'>
	<display:table name="sessionScope.patrolmanlist" id="currentRowObject"
		pagesize="18">
		<display:column media="html" title="Ѳ����" sortable="true">
			<bean:define id="patrolId" name="currentRowObject"
				property="patrolid"></bean:define>
			<bean:define id="patrolName" name="currentRowObject"
				property="executorid"></bean:define>
			<a href="javascript:showPlanExecuteResult('${patrolId }');">${patrolName
				}</a>
		</display:column>
		<display:column property="terminalnumber" title="Ѳ���豸����"
			sortable="true"></display:column>
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
</body>