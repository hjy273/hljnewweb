<%@include file="/common/header.jsp"%>
<%
  Hashtable ht = new Hashtable();
  String flag = "0";
  if (request.getAttribute("pmBean") != null) {
    ht = (Hashtable) request.getAttribute("pmBean");
    flag = (String) ht.get("hasrecord");
  }
  String patrolid = "";
  String patrolname = "";
  String contractor = "";
  String jobinfo = "";
  String terminalid = "";
  String password = "";
  String sim = "";
  String state = "";
  if (flag.equals("1")) {
    patrolid = (String) ht.get("patrolid");
    patrolname = (String) ht.get("patrolname");
    contractor = (String) ht.get("contractor");
    jobinfo = (String) ht.get("jobinfo");
    terminalid = (String) ht.get("terminalid");
    terminalid = terminalid.substring(terminalid.length() - 4, terminalid.length());
    password = (String) ht.get("password");
    sim = (String) ht.get("sim");
    state = (String) ht.get("state");
  }
%>
<script language="javascript" src="${ctx}/realtime/realtime.js" type="">
</script><link href="${ctx}/css/style.css" rel="stylesheet" type="text/css">
<template:titile value="Ѳ��Ա��Ϣ"/>
<form name="pmBean" method="Post" action="">
<table width="98%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
  <tr>
    <th class="thlist">��Ŀ</th>
    <th class="thlist">��д</th>
  </tr>
  <tr class="trcolor">
    <td class="tdulleft">Ѳ��Ա����</td>
    <td class="tdulright">
      <!-- ������Ϣ -->
      <input type="hidden" name="UserID" class="inputtext" value="<%=patrolid%>"/>
      <input type="hidden" name="MessageType" class="inputtext" value="LOCALIZE_MSG"/>
      <input type="hidden" name="DeviceID" class="inputtext" value="<%=terminalid%>"/>
      <input type="hidden" name="Password" class="inputtext" value="<%=password%>"/>
      <input type="text" name="patrolName" class="inputtext" style="width:100;font-family: Geneva, Arial;font-size: 9pt;" value="<%=patrolname%>" readonly/>
    </td>
  </tr>
  <tr class="trwhite">
    <td class="tdulleft">��������</td>
    <td class="tdulright">
      <input type="text" name="contractor" class="inputtext" style="width:100;font-family: Geneva, Arial;font-size: 9pt;" value="<%=contractor%>" readonly/>
    </td>
  </tr>
  <tr class="trcolor">
    <td class="tdulleft">�豸״̬</td>
    <td class="tdulright">
      <input type="text" name="state" class="inputtext" style="width:100;font-family: Geneva, Arial;font-size: 9pt;" value="<%=state%>" readonly/>
    </td>
  </tr>
  <tr class="trwhite">
    <td class="tdulleft">SIM����</td>
    <td class="tdulright">
      <input type="text" name="SimIDList" class="inputtext" style="width:100;font-family: Geneva, Arial;font-size: 9pt;" value="<%=sim%>" readonly/>
    </td>
  </tr>
  <tr class="trcolor">
    <td class="tdulleft">ְ������</td>
    <td class="tdulright">
      <input type="text" name="jobinfo" class="inputtext" style="width:100;font-family: Geneva, Arial;font-size: 9pt;" value="<%=jobinfo%>" readonly/>
    </td>
  </tr>
</table>
<br>
<!--��ť���ʼ-->
<template:formSubmit>
  <td>
    <input type="button" name="action" value="���Ͷ���" class="lbutton" onclick="toSMS('<%=terminalid%>','<%=sim%>','<%=password%>')">
  </td>
</template:formSubmit>
<!--��ť������-->
</form>
