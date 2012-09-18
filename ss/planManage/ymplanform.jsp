<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.planinfo.beans.*"%>
<%@page import="com.cabletech.planinfo.domainobjects.*"%>
<%@page import="com.cabletech.planstat.beans.*"%>
<%
  YearMonthPlanBean planbean = (YearMonthPlanBean) request.getSession().getAttribute("YearMonthPlanBean");
  Vector taskVct = (Vector) request.getSession().getAttribute("tasklist");
  int taskRowSpanL = taskVct.size();

  String fID = (String) request.getAttribute("fID");
  String titleName = "���Ѳ��ƻ���";
  if(fID.equals("2")){
	  titleName = "�¶�Ѳ��ƻ���";
  }
%>

<script language="javascript" type="">
<!--
function loadEditPlan(idValue,fID){


    var url="/";
  	if(fID=="1")
         url = "${ctx}/YearMonthPlanAction.do?method=queryYMPlan&fID=1&year=";
     else
     	 url = "${ctx}/YearMonthPlanAction.do?method=queryYMPlan&fID=2&year=";
    self.location.replace(url);

}

function toExpotr(){
	self.location.replace("${ctx}/YearMonthPlanAction.do?method=ExportYMPlanform");
}

//-->
</script>
<style type="text/css">
<!--
.hiInput {
    width: 100%;
    background-color:transparent;
    border-bottom-width: 1px;
    border-top-style: none;
    border-right-style: none;
    border-bottom-style: solid;
    border-left-style: none;
    border-bottom-color: #000000;
}

.commonHiInput {
    background-color:transparent;
    border-bottom-width: 1px;
    border-top-style: none;
    border-right-style: none;
    border-bottom-style: solid;
    border-left-style: none;
    border-bottom-color: #000000;
}
-->
</style>
<body>
<%
	String looktype = (String)request.getAttribute("looktype");
	//System.out.println("=="+looktype);
	if(looktype == null || !looktype.equals("stat")){
 %>
<table width="90%"  border="0" cellspacing="0" cellpadding="0" align="center">
  <tr>
    <td width="15%">�������� ��</td>
    <td width="85%"><input type="text" class="hiInput" value="<%=planbean.getPlanname() %>" readonly></td>
  </tr>
  <tr>
    <td>�������� ��</td>
    <td><input type="text" class="hiInput" value="��ά��λ���/�¶�Ѳ��ƻ���Ϣ����" readonly></td>
  </tr>
  <tr>
    <td>������ ��</td>
    <td><input type="text" class="hiInput" value="<%=planbean.getId()%>" readonly></td>
  </tr>
  <tr>
    <td>����� ��</td>
    <td><input type="text" class="hiInput" value="<%=planbean.getApplyformdate()%>" readonly></td>
  </tr>
</table>
<br>

<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="24" align="center" class="title2"><%=titleName%>
    </td>
  </tr>
</table>
<br>
<%}else{ %>
	<%PatrolStatConditionBean pbean = (PatrolStatConditionBean)session.getAttribute("queryCon"); %>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="24" align="center" class="title2"><bean:write name="queryCon" property="conName"/>��˾<bean:write name="queryCon" property="endYear"/>��<%=pbean.getEndMonth() %>�·ݼƻ���Ϣ
    </td>
  </tr>
</table>
<hr width='100%' size='1'>
<%} %>
<table width="95%" border="0" align="center" cellpadding="3" cellspacing="1" bgcolor="#999999">
  <!-- �ƻ���Ϣ -->
  <tr bgcolor="#FFFFFF">
    <td width="11%" class=trcolor rowspan="<%=8+taskRowSpanL%>" align="center">�ƻ���Ϣ</td>
    <td width="14%" class=trcolor>�ƻ�����</td>
    <td><%=planbean.getPlanname() %></td>
  </tr>
  <tr class=trcolor>
    <td bgcolor="#FFFFFF" class=trcolor>�ƻ�ʱ��</td>
    <td bgcolor="#FFFFFF">      <%=planbean.getYear() %> �� <%=planbean.getMonth()%>
</td>
  </tr>
<%
  for (int i = 0; i < taskRowSpanL; i++) {
    TaskBean task = (TaskBean) taskVct.get(i);
%>
  <tr class=trwhite>
  <%if (i == 0) {  %>
    <td class=trcolor rowspan="<%=taskRowSpanL%>" bgcolor="#FFFFFF">����</td>
  <%}  %>
    <td bgcolor="#FFFFFF"><%=task.getLineleveltext()%>      &nbsp;&nbsp;&nbsp;<%=task.getDescribtion()%>      &nbsp;&nbsp;&nbsp;
<%=task.getExcutetimes()%>      ��
</td>
  </tr>
<%}%>
  <tr class=trwhite>
    <td class=trcolor bgcolor="#FFFFFF">�� �� ��</td>
    <td bgcolor="#FFFFFF"><%=planbean.getCreator()%></td>
  </tr>
  <tr class=trwhite>
    <td class=trcolor bgcolor="#FFFFFF">�ƶ�����</td>
    <td bgcolor="#FFFFFF">
    ${ctime}
    </td>
  </tr>
  <!--
  <tr class=trwhite>
    <td class=trcolor bgcolor="#FFFFFF">��ά��λ�������</td>
    <td bgcolor="#FFFFFF"><%=planbean.getIfinnercheck() %></td>
  </tr>
  -->
  <tr class=trwhite>
    <td class=trcolor bgcolor="#FFFFFF">�ƶ�����</td>
    <td bgcolor="#FFFFFF"><%=planbean.getStatus() %></td>
  </tr>
  <tr class=trwhite>
    <td bgcolor="#FFFFFF" class=trcolor>�� �� ��</td>
    <td bgcolor="#FFFFFF"><%=planbean.getApprover() %></td>
  </tr>
  <tr class=trwhite>
    <td bgcolor="#FFFFFF" class=trcolor>��������</td>
    <td bgcolor="#FFFFFF"><%=planbean.getApprovedate() %></td>
  </tr>
  <!--
  <tr bgcolor="#FFFFFF" class=trwhite>
    <td rowspan="2" align="center" class=trcolor>��ά��λ<br>�쵼ȷ��</td>
    <td height="63" colspan="2">&nbsp;</td>
  </tr>-->

    <tr bgcolor="#FFFFFF" class=trwhite>
    <td rowspan="2" align="center" class=trcolor>�ƶ���˾<br>�쵼��ʾ</td>
<% if(planbean.getApprovecontent()!= null){%>
<td height="63" colspan="2" bgcolor="#FFFFFF"><%=planbean.getApprovecontent()%></td>
<%}else{
%>
<td height="63" colspan="2" bgcolor="#FFFFFF"> </td>

<% }%>
  </tr>

</table>
<%
	if(looktype == null || !looktype.equals("stat")){
 %>
<br>
<br>
<template:formSubmit>
    <td width="85">
        <html:button property="action" styleClass="button" onclick="toExpotr()">Excel���� </html:button>
    </td>
    <td>
        <input type="button" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')" value="����" >
      </td>
</template:formSubmit>
<br>
<br>
<%} %>
</body>
