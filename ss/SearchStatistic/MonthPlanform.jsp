<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.statistics.domainobjects.PatrolRate"%>
<%@page import="com.cabletech.planinfo.beans.*"%>

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
	self.location.replace("${ctx}/StatisticAction.do?method=ExportContractorPlanForm");
}

//-->
</script>


			<%
			  PatrolRate patrolrate = (PatrolRate) request.getSession().getAttribute("QueryResult");
			  String flag = patrolrate.getIfhasrecord();
              YearMonthPlanBean bean = (YearMonthPlanBean)request.getSession().getAttribute("YearMonthPlanBean");
			  if (flag.equals("0")) {
    				if(bean == null){
			%>
		            <br>
			      <br>
			      <br>
			      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
			        <tr>
			          <td height="24" align="center" class="title2">���¼ƻ���û���ƶ�,�������ɱ���</td>
			        </tr>
			      </table>
            <%     }else{%>
						<br>
						<br>
						<br>
						<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
						  <tr>
						    <td height="24" align="center" class="title2">����û���ƶ��ܼƻ����������ɱ���</td>
						  </tr>
						</table>
			<%  }
          }else {

				YearMonthPlanBean planbean = (YearMonthPlanBean) request.getSession().getAttribute("YearMonthPlanBean");
                if(bean == null){
              %>
              		<br>
			      <br>
			      <br>
			      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
			        <tr>
			          <td height="24" align="center" class="title2">���¼ƻ���û���ƶ�,�������ɱ���!</td>
			        </tr>
			      </table>

              <%
                }else{
				Vector taskVct = (Vector) request.getSession().getAttribute("tasklist");
				int taskRowSpanL = taskVct.size();

				String fID = "2";
				String titleName = "�¶�Ѳ��ƻ���";

			%>
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
			  <tr>
			    <td height="24" align="center" class="title2"><%=patrolrate.getFormname()%>      &nbsp;&nbsp;
			      <a href="javascript:makeSize()"><img id="enlargeIcon" src="${ctx}/images/icon_arrow_up.gif" width="14" height="16" border="0" alt="���ز�ѯ����"></a>
			    </td>
			  </tr>
			</table>
			<br>
			<table width="95%" border="0" align="center" cellpadding="3" cellspacing="1" bgcolor="#999999">
			  <!-- �ƻ���Ϣ -->
			  <tr bgcolor="#FFFFFF">
			    <td width="116" class=trcolor rowspan="<%=8+taskRowSpanL%>" align="center">�ƻ���Ϣ</td>
			    <td width="113" class=trcolor >�ƻ�����1</td>
			    <td colspan="2"><%=planbean.getPlanname() %></td>
			  </tr>
			  <tr class=trcolor>
			    <td bgcolor="#FFFFFF" class=trcolor>�ƻ�ʱ��</td>
			    <td bgcolor="#FFFFFF" colspan="2">      <%=planbean.getYear() %> �� <%=planbean.getMonth()%>
			</td>
			  </tr>
			<%
			  for (int i = 0; i < taskRowSpanL; i++) {
			    TaskBean taskB = (TaskBean) taskVct.get(i);
			%>
			  <tr class=trwhite>
			  <%if (i == 0) {  %>
			    <td class=trcolor rowspan="<%=taskRowSpanL%>" bgcolor="#FFFFFF">����</td>
			  <%}  %>
			    <td bgcolor="#FFFFFF" colspan="2"><%=taskB.getLineleveltext()%>      &nbsp;&nbsp;&nbsp;<%=taskB.getDescribtion()%>      &nbsp;&nbsp;&nbsp;
			<%=taskB.getExcutetimes()%>      ��
			</td>
			  </tr>
			<%}%>
			  <tr class=trwhite>
			    <td class=trcolor bgcolor="#FFFFFF">�ƻ��ƶ���</td>
			    <td bgcolor="#FFFFFF" colspan="2"><%=planbean.getCreator()%></td>
			  </tr>
			  <tr class=trwhite>
			    <td class=trcolor bgcolor="#FFFFFF">�ƻ��ƶ�����</td>
			    <td bgcolor="#FFFFFF" colspan="2"><%=planbean.getCreatedate() %></td>
			  </tr>
			  <tr class=trwhite>
			    <td class=trcolor bgcolor="#FFFFFF">��ά��λ�������</td>
			    <td bgcolor="#FFFFFF" colspan="2"><%=planbean.getIfinnercheck() %></td>
			  </tr>
			  <tr class=trwhite>
			    <td class=trcolor bgcolor="#FFFFFF">�ƶ���˾�������</td>
			    <td bgcolor="#FFFFFF" colspan="2"><%=planbean.getStatus() %></td>
			  </tr>
			  <tr class=trwhite>
			    <td bgcolor="#FFFFFF" class=trcolor>������</td>
			    <td bgcolor="#FFFFFF" colspan="2"><%=planbean.getApprover() %></td>
			  </tr>
			  <tr class=trwhite>
			    <td bgcolor="#FFFFFF" class=trcolor>��������</td>
			    <td bgcolor="#FFFFFF" colspan="2"><%=planbean.getApprovedate() %></td>
			  </tr>
			  <!-- �ƻ���Ϣ -->
			  <tr class=trwhite>
			    <td width="116" rowspan="7" bgcolor="#FFFFFF" class=trcolor  align="center">�ƻ�ִ�����    </td>
			    <td width="113" bgcolor="#FFFFFF" class=trcolor><%=patrolrate.getStatype()%></td>
			    <td width="25%" bgcolor="#FFFFFF"><%=patrolrate.getStaobject() %>    </td>
			    <td width="300" rowspan="7"  bgcolor="#CCCCCC" >      <div><img src="${ctx}/images/1px.gif"><br>
			        <iframe name=treemenu border=0 marginWidth=0 marginHeight=0 src="${ctx}/ShowChart?PRate=<%=patrolrate.getPatrolrate() %>&LRate=<%=patrolrate.getLossrate() %>" frameBorder=0 width="100%" scrolling=NO height="165">        </iframe>
			    </div></td>
			  </tr>
			  <tr class=trcolor>
			    <td bgcolor="#FFFFFF" class=trcolor>ͳ��ʱ��</td>
			    <td bgcolor="#FFFFFF"><%=patrolrate.getFormtime() %>    </td>
			  </tr>
			  <tr class=trwhite>
			    <td bgcolor="#FFFFFF" class=trcolor>�ƻ�������</td>
			    <td bgcolor="#FFFFFF"><%=patrolrate.getPlancount() %>      ���
			</td>
			  </tr>
			  <tr class=trwhite>
			    <td bgcolor="#FFFFFF" class=trcolor>ʵ�ʹ�����</td>
			    <td bgcolor="#FFFFFF"><%=patrolrate.getRealcount() %>      ���
			</td>
			  </tr>
			  <tr class=trwhite>
			    <td bgcolor="#FFFFFF" class=trcolor>©�칤����</td>
			    <td bgcolor="#FFFFFF"><%=patrolrate.getLosscount() %>      ���
			</td>
			  </tr>
			  <tr class=trwhite>
			    <td bgcolor="#FFFFFF" class=trcolor>Ѳ����</td>
			    <td bgcolor="#FFFFFF"><%=patrolrate.getPatrolrate() %>    </td>
			  </tr>
			  <tr class=trwhite>
			    <td bgcolor="#FFFFFF" class=trcolor>©����</td>
			    <td bgcolor="#FFFFFF"><%=patrolrate.getLossrate() %>    </td>
			  </tr>
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
			  }}
			%>
