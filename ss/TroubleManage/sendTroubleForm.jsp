<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.troublemanage.beans.*"%>
<%@page import="com.cabletech.troublemanage.domainobjects.*"%>
<%@ page import="com.cabletech.baseinfo.domainobjects.*" %>
<%@ page import="com.cabletech.commons.hb.*" %>
<%@ page import="java.sql.*" %>
<%
	UserInfo userinfo=(UserInfo)session.getAttribute("LOGIN_USER");
	String type = userinfo.getDeptype();
    String breportman1 = "";
    String breportman2 = "";
    if("2".equals(type)){
    	breportman1 = userinfo.getUserName();
        breportman2 = "";
    }else{
    	breportman1 = "";
        breportman2 = userinfo.getUserName();
    }
%>
<%AccidentBean bean = (AccidentBean) request.getAttribute("AccidentBean");%>
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
<script language="javascript">
function mySubmit1(){
    var a=document.getElementsByTagName("td");

    for(var i=0;i<a.length;i++){
        if (a[i].innerText=="null"){
            a[i].innerText="";
        }
    }
     var b=document.getElementsByTagName("input");
    for(var i=0;i<b.length;i++){
        if (b[i].value=="null"){
            b[i].value="";
        }
    }
}
</script>
<script language="javascript" type="">
function postIt(flag){
    var url = "${ctx}/accidentAction.do?method=postTroubleTask&id=" + id.value+"&sendto="+sendto.value+"&whosend=<%=userinfo.getUserName()%>&breportman="+breportman.value+"&bconfirman="+bconfirman.value + "&flag="+ flag;
    self.location.replace(url);
}
</script>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>�ޱ����ĵ�</title>
</head>

<body>
<br>

<input type="hidden" name="id" value="<%=bean.getKeyid()%>">

<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="24" align="center" class="title2">��д��������֪ͨ��</td>
  </tr>
</table>

<table width="85%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
  <input type="hidden" name="breportman" class="hiInput" value="<%=userinfo.getUserName()%>">
  <input type="hidden" name="bconfirman" class="hiInput" value=" ">
  <input type="hidden" name="companyname" class="commonHiInput" style="width:120;" value="<%=bean.getContractorid()%>">
    <td><br>&nbsp;&nbsp;�������浥λ��<%=bean.getContractorid()%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

    �� �� �ˣ�<%=userinfo.getUserName()%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    �ʱ�䣺<input type="text" name="year" class="commonHiInput" style="width:50;" readonly>�� <input type="text" name="month" class="commonHiInput" style="width:50;" readonly>�� </td>
  </tr>

</table>
 <br>
<table width="85%"  border="0" align="center" cellpadding="5" cellspacing="0">

</table>
<table width="85%" align="center" cellpadding="5" cellspacing="1" bgcolor="#999999">
  <tr>
    <td width="15%" rowspan="4" align="center" class=trcolor >��������
    </td>
    <td class=trcolor width="25%" >�������� </td>
    <td colspan="3" bgcolor="#FFFFFF"><%=bean.getSendtime().substring(0,10) %></td>
  </tr>
  <tr>
    <td class=trcolor >����ʱ�� </td>
    <td colspan="3" bgcolor="#FFFFFF"><%=bean.getSendtime().substring(11) %></td>
  </tr>
  <tr>
    <td class=trcolor >�������� </td>
    <td colspan="3" bgcolor="#FFFFFF"><%=bean.getLid() %></td>
  </tr>
  <tr>
    <td class=trcolor >�ص��ʯ�� </td>
    <td colspan="3" bgcolor="#FFFFFF"><%=bean.getPid() %></td>
  </tr>
  <tr>
    <td width="15%" rowspan="2" align="center" class=trcolor >�ɷ�����
    </td>
    <td class=trcolor width="25%" >�ɵ��� </td>

    <td colspan="3" bgcolor="#FFFFFF"><%=userinfo.getUserName()%></td>
  </tr>
   <tr>
    <td class=trcolor >ָ�ɴ����� </td>
    <td colspan="3" bgcolor="#FFFFFF">
   <select name="sendto" id="sendto" style="inputtext">
       <%
    		QueryUtil qu= new QueryUtil();
            String sql = "select * from patrolmaninfo where state is null and regionid='"+userinfo.getRegionID()+"'";
            if(userinfo.getDeptype().equals("2"))
            	sql = sql + " and parentid='" + userinfo.getDeptID() + "'";
       		ResultSet rs;
            rs=qu.executeQuery(sql);
       		while(rs !=null && rs.next()){
       %>
       		<option value="<%=rs.getString("patrolid")%>"><%=rs.getString("patrolname")%></option>
       <%}%>
       </select>
    </td>
  </tr>

  <tr>
    <td align="center" class=trcolor >����ԭ�� </td>
    <td colspan="4" valign="top" bgcolor="#FFFFFF" class=trcolor ><%=bean.getResonandfix() %></td>
  </tr>
</table>

<p>
<p>
<template:formSubmit>
<td>
    <html:button property="action" styleClass="lbutton" onclick="postIt('1')">�·�֪ͨ��</html:button>
    &nbsp;&nbsp;&nbsp;&nbsp;
    <html:button property="action" styleClass="lbutton" onclick="postIt('2')">���Ը�����</html:button>

</td>
</template:formSubmit>
<br>
<br>
</body>
</html>
<script language="javascript" type="">
 var d=new Date();
 document.all.year.value=d.getFullYear();
 document.all.month.value=d.getMonth()+1;
</script>
