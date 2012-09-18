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
    <td class="titlefont" align="center">巡检员巡检明细查询</td>
  </tr>
</table>
<html:form action="queryBaseInfoAction?methord=queryPatrolDetailInfo">
    <table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
    <TR>
       <td class="pbutton" width="100" align="right">巡检员编码</td>
       <td class="pbutton">
        <html:text property="patroID"/>
      </TD>
     </TR>
    <TR>
       <td class="pbutton" width="100" align="right">巡检员名称</td>
       <td class="pbutton">
           <html:text property="patroName"/>
      </TD>
     </TR>

    <tr>
       <td class="pbutton" width="100" align="right">所属部门</td>
       <td class="pbutton">
           <html:text property="deptID"/>

      </TD>
    </tr>

      <TR>
       <td class="pbutton" width="100" align="right">起始时间</td>
       <td class="pbutton">
           <html:text property="beginTime"/>

      </TD>
     </TR>

      <TR>
       <td class="pbutton" width="100" align="right">终止时间</td>
       <td class="pbutton">
        <html:text property="endTime"/>

      </TD>
     </TR>
    </TABLE>

    <table  border="0" cellspacing="0" cellpadding="3" align="center">
     　<tr>
         <td valign="top">
           <html:submit property="submit">查询</html:submit>
	　    </td>
         <td>
            <html:reset>清除</html:reset>
         </td>
         <td width="20">&nbsp; </td>
       </tr>
   </table>
</html:form>

<table >
  <tr>
    <td align="center">区域信息</td>
  </tr>
</table>
<%
System.out.println("准备显示巡检员巡检明细表");
%>
<%--巡检员巡检明细表--%>
<h1>

<display:table name="requestScope.BaseInfoResultList" id="user" class="tabout"
    requestURI="regionAction.do?method=queryRegion" width="98%" border="0"
    align="center" cellpadding="3" cellspacing="0" class="tabout">
  <display:column property="patroid" title="巡检员编号"/>
  <display:column property="patroname" title="巡检员" />
  <display:column property="deptname" title="所属部门" />
</display:table>

</h1>
</body>
</html>
