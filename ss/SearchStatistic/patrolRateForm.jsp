<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.statistics.domainobjects.*"%>
<link href="${ctx}/css/style.css" rel="stylesheet" type="text/css">
<script language="javascript" type="">
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
	self.location.replace("${ctx}/StatisticAction.do?method=ReportPatrolRateWithSession");
}

//-->
</script><%
  PatrolRate patrolrate = (PatrolRate) request.getSession().getAttribute("QueryResult");
  String flag = patrolrate.getIfhasrecord();
  if (flag.equals("0")) {
%>
<br>
<br>
<br>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="24" align="center" class="title2">û�мƻ���Ϣ���ã��������ɱ���</td>
  </tr>
</table>
<%} else {%>
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
  <tr class=trwhite>
    <td width="15%" bgcolor="#FFFFFF" class=trcolor><%=patrolrate.getStatype()%>    </td>
    <td width="45%" bgcolor="#FFFFFF"><%=patrolrate.getStaobject() %>    </td>
    <td width="300" rowspan="7"  bgcolor="#CCCCCC" >      <div><img src="${ctx}/images/1px.gif"><br>
        <iframe name=treemenu border=0 marginWidth=0 marginHeight=0 src="${ctx}/ShowChart?PRate=<%=patrolrate.getPatrolrate() %>&LRate=<%=patrolrate.getLossrate() %>" frameBorder=0 width="100%" scrolling=NO height="165">        </iframe>
      </div>
</td>
  </tr>
  <tr class=trcolor>
    <td class=trcolor bgcolor="#FFFFFF">ͳ��ʱ��</td>
    <td bgcolor="#FFFFFF"><%=patrolrate.getFormtime() %>    </td>
  </tr>
  <tr class=trwhite>
    <td class=trcolor bgcolor="#FFFFFF">�ƻ�������</td>
    <td bgcolor="#FFFFFF"><%=patrolrate.getPlancount() %>      ���
</td>
  </tr>
  <tr class=trwhite>
    <td class=trcolor bgcolor="#FFFFFF">ʵ�ʹ�����</td>
    <td bgcolor="#FFFFFF"><%=patrolrate.getRealcount() %>      ���
</td>
  </tr>
  <tr class=trwhite>
    <td class=trcolor bgcolor="#FFFFFF">©�칤����</td>
    <td bgcolor="#FFFFFF"><%=patrolrate.getLosscount() %>      ���
</td>
  </tr>
  <tr class=trwhite>
    <td class=trcolor bgcolor="#FFFFFF">Ѳ����</td>
    <td bgcolor="#FFFFFF"><%=patrolrate.getPatrolrate() %>    </td>
  </tr>
  <tr class=trwhite>
    <td class=trcolor bgcolor="#FFFFFF">©����</td>
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
  }
      //System.out.println(flag+"!!!!!!!!!!!!!");
%>
