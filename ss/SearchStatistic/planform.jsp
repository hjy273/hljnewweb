<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.statistics.domainobjects.*"%>
<link href="${ctx}/css/style.css" rel="stylesheet" type="text/css">
<script language="javascript">
<!--
var enlargeFlag = 0;

function makeSize(){
	if(enlargeFlag == 0){
		parent.makeSize(0);
		enlargeIcon.src = "${ctx}/images/icon_arrow_down.gif";
		enlargeIcon.alt = "��ʾ��ѯ����";
		enlargeFlag = 1;
	}else{
		parent.makeSize(1);
		enlargeIcon.src = "${ctx}/images/icon_arrow_up.gif";
		enlargeIcon.alt = "���ز�ѯ����";
		enlargeFlag = 0;
	}
}

function toCloseThis(){
	try{
		parent.makeSize(1);
	}catch(e){}

	self.location.replace("${ctx}/common/blank.html");
}

function toExpotr(){
  	self.location.replace("${ctx}/StatisticAction.do?method=ExportPlanFormWithSession");
}

//-->
</script>
	<%
	  PlanStatisticForm planform = (PlanStatisticForm) request.getSession().getAttribute("QueryResult");
	  String flag = planform.getIfhasrecord();
	  String cyctype = planform.getCyctype();
	  if (flag.equals("0")) {
	%>
				<br>
				<br>
				<br>
				<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				  <tr>
				    <td height="24" align="center" class="title2">û�и�ʱ��εļƻ����������ɱ���</td>
				  </tr>
				</table>
<%} else {%>
				<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				  <tr>
				    <td height="24" align="center" class="title2"><%=planform.getFormname()%>      &nbsp;&nbsp;
				      <a href="javascript:makeSize()">
				        <img id="enlargeIcon" src="${ctx}/images/icon_arrow_up.gif" width="14" height="16" border="0" alt="���ز�ѯ����">
				      </a>
				    </td>
				  </tr>
				</table>
				<br>
<%
  int comPlanUnitL = 6;
  Vector planVct = planform.getPlanvct();
  //Vector dailyVct = planform.getDailypatrolvct();
%>
				<table width="95%" border="0" align="center" cellpadding="3" cellspacing="1" bgcolor="#999999">
  <!-- �ƻ���Ϣ -->
<%
  for (int i = 0; i < planVct.size(); i++) {
    Plan plan = (Plan) planVct.get(i);
    Vector taskVct = plan.getTaskvct();
    int callPlanRSpan = 0;
    if( taskVct != null)
        callPlanRSpan = comPlanUnitL + taskVct.size();
    else
    	callPlanRSpan = comPlanUnitL ;
%>
					  <tr bgcolor="#FFFFFF">
					    <td width="11%" class=trcolor rowspan="<%=callPlanRSpan%>" align="center"><%=plan.getPlanname()%>
						</td>
					    <td class=trcolor width="14%">�ƻ�����</td>
					    <td colspan="3"><%=plan.getPlanname()%>    </td>
					  </tr>
					  <tr class=trwhite>
                        <logic:equal value="group" name="PMType">
					    <td class=trcolor bgcolor="#FFFFFF">Ѳ��ά����</td>
                        </logic:equal>
                        <logic:notEqual value="group" name="PMType">
					    <td class=trcolor bgcolor="#FFFFFF">ִ �� ��</td>
                        </logic:notEqual>
					    <td colspan="3" bgcolor="#FFFFFF"><%=plan.getPatrolname()%>    </td>
					  </tr>
					  <tr class=trcolor>
					    <td class=trcolor bgcolor="#FFFFFF">�ƻ�ʱ��</td>
					    <td colspan="3" bgcolor="#FFFFFF"><%=plan.getBegindateStr()%> &nbsp;�� &nbsp;<%=plan.getEnddateStr()%>    </td>
					  </tr>
					  <tr class=trwhite>
					    <td class=trcolor bgcolor="#FFFFFF">�� �� ��</td>
					    <td colspan="3" bgcolor="#FFFFFF"><%=plan.getPlanpointCount()%>      ���
					</td>
					  </tr>
<%
    if( taskVct != null){
		for (int k = 0; k < taskVct.size(); k++) {
    	Task task = (Task) taskVct.get(k);
%>
  					<tr class=trwhite>
  <%if (k == 0) {  %>
    					<td class=trcolor rowspan="<%=taskVct.size()%>" bgcolor="#FFFFFF">�ƻ�����</td>
  <%}  %>
    					<td colspan="3" bgcolor="#FFFFFF"><%=task.getTaskname() %>  &nbsp;&nbsp;&nbsp; <%=task.getTaskdisc()%>    &nbsp;&nbsp;&nbsp;<%=task.getExecutes()%>      ��
						</td>
    			    </tr>
<%}}%>
					  <tr class=trwhite>
					    <td class=trcolor bgcolor="#FFFFFF">�� �� ��</td>
					    <td colspan="3" bgcolor="#FFFFFF"><%=plan.getContractorname()%>    </td>
					  </tr>
					  <tr class=trwhite>
					    <td class=trcolor bgcolor="#FFFFFF">�ƶ�����</td>
					    <td colspan="3" bgcolor="#FFFFFF"><%=plan.getCreatedate()%>    </td>
					  </tr>
<%}%>

						<tr bgcolor="#FFFFFF" class=trwhite>
					    <td rowspan="5" class=trcolor align="center">�ƻ�ͳ��</td>
					    <td class=trcolor>Ӧ����</td>
					    <td><%=planform.getPlancount()%>  ���  </td>
					    <td rowspan="5" colspan="2" align="center" bgcolor="#CCCCCC" width="300">
					      <div>
					        <img src="${ctx}/images/1px.gif">
					        <br>
					        <iframe name=treemenu border=0 marginWidth=0 marginHeight=0 src="${ctx}/ShowChart?PRate=<%=planform.getPatrolrate() %>&LRate=<%=planform.getLossrate() %>" frameBorder=0 width="100%" scrolling=NO height="165">        </iframe>
					      </div>
					    </td>
					  </tr>
					  <tr bgcolor="#FFFFFF" class=trwhite>
					    <td class=trcolor>ʵ����</td>
					    <td><%=planform.getRealcount()%> ���   </td>
					  </tr>
					  <tr bgcolor="#FFFFFF" class=trwhite>
					    <td class=trcolor>©����</td>
					    <td><%=planform.getLosscount() %> ���   </td>
					  </tr>
					  <tr bgcolor="#FFFFFF" class=trwhite>
					    <td class=trcolor>Ѳ �� ��</td>
					    <td><%=planform.getPatrolrate() %>    </td>
					  </tr>
					  <tr bgcolor="#FFFFFF" class=trwhite>
					    <td class=trcolor>© �� ��</td>
					    <td><%=planform.getLossrate() %>    </td>
					  </tr>
  <!--
    <tr bgcolor="#FFFFFF" class=trwhite>
    <td rowspan="2" align="center" class=trcolor>�쵼��ʾ</td>
    <td height="63" colspan="3">&nbsp;</td>
    </tr>
    <tr bgcolor="#FFFFFF" class=trwhite>
    <td colspan="3">&nbsp;</td>
    </tr>
  -->
					</table>
					<br>
					<br>
					<template:formSubmit>
					  <td width="85">
					    <html:button property="action" styleClass="button" onclick="toExpotr()">Excel����</html:button>
					  </td>
					  <td width="85">
					    <html:button property="action" styleClass="button" onclick="toCloseThis()">�رմ���</html:button>
					  </td>
					</template:formSubmit>
					<br>
					<br>
<%
  }
      //System.out.println(flag+"!!!!!!!!!!!!!");
%>
