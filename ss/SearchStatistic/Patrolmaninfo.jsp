<%@ page contentType="text/html; charset=GB2312" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<html>
<head>
<title>
Patrolmaninfo
</title>
</head>
<body bgcolor="#ffffff">
<table width="95%" border="0" cellspacing="3" cellpadding="3" align="center" class="dbutton">
  <tr>
    <td class="titlefont" align="center">Ѳ��ԱѲ����ϸ��ѯ</td>
  </tr>
</table>
<html:form action="queryBaseInfoAction?methord=queryPatrolDetailInfo">
    <table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
    <TR>
       <td class="pbutton" width="100" align="right">Ѳ��Ա����</td>
       <td class="pbutton">
        <html:text property="patroID"/>
      </TD>
     </TR>
    <TR>
       <td class="pbutton" width="100" align="right">Ѳ��Ա����</td>
       <td class="pbutton">
           <html:text property="patroName"/>
      </TD>
     </TR>

    <tr>
       <td class="pbutton" width="100" align="right">��������</td>
       <td class="pbutton">
           <html:text property="deptID"/>

      </TD>
    </tr>

      <TR>
       <td class="pbutton" width="100" align="right">��ʼʱ��</td>
       <td class="pbutton">
           <html:text property="beginTime"/>

      </TD>
     </TR>

      <TR>
       <td class="pbutton" width="100" align="right">��ֹʱ��</td>
       <td class="pbutton">
        <html:text property="endTime"/>

      </TD>
     </TR>
    </TABLE>

    <table  border="0" cellspacing="0" cellpadding="3" align="center">
     ��<tr>
         <td valign="top">
           <html:submit property="submit">��ѯ</html:submit>
	��    </td>
         <td>
            <html:reset>���</html:reset>
         </td>
         <td width="20">&nbsp; </td>
       </tr>
   </table>
</html:form>

<table >
  <tr>
    <td align="center">������Ϣ</td>
  </tr>
</table>
<%
System.out.println("׼����ʾѲ��ԱѲ����ϸ��");
%>
<%--Ѳ��ԱѲ����ϸ��--%>
<h1>

<display:table name="requestScope.BaseInfoResultList" id="user" class="tabout"
    requestURI="regionAction.do?method=queryRegion" width="98%" border="0"
    align="center" cellpadding="3" cellspacing="0" class="tabout">
  <display:column property="patroid" title="Ѳ��Ա���"/>
  <display:column property="patroname" title="Ѳ��Ա" />
  <display:column property="deptname" title="��������" />
</display:table>

</h1>
</body>
</html>
