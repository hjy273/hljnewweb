<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="cabletechtag" prefix="apptag" %>
<%@ page import="com.cabletech.baseinfo.beans.*" %>
<jsp:useBean id="tsBean" class="com.cabletech.baseinfo.beans.SublineBean" scope="request"/>

<link href="${ctx}/css/style.css" rel="stylesheet" type="text/css">
<body text="#000000">

<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="24" align="center" class="title2">修改巡检段信息</td>
  </tr>
  <tr>
    <td height="2" background="${ctx}/images/bg_line.gif"><img src="${ctx}/images/1px.gif" width="1" height="1"></td>
  </tr>
</table>

<html:form method="Post" action="/sublineAction.do?method=updateSubline" >
 <html:hidden    property="subLineID" value="<%=tsBean.getSubLineID()%>"/>
  <table width="98%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
    <tr>
      <th class="thlist">项目</th>
      <th class="thlist">填写</th>
    </tr>
    <tr class="trcolor">
      <td class="tdulleft">巡检段名称：</td>
      <td class="tdulright">
         <html:text    property="subLineName" value="<%=tsBean.getSubLineName()%>"  size="20"      maxlength="45"/>
      </td>
    </tr>
  <tr class="trwhite">
      <td class="tdulleft">所属线编号：</td>
      <td class="tdulright">
        <html:text         property="lineID"   value="<%=tsBean.getLineID()%>"  size="20"      maxlength="45"/>
      </td>
    </tr>

  <tr class="trcolor">
       <td class="tdulleft">所属部门</td>
       <td class="tdulright">
        <html:text    property="ruleDeptID"   value="<%=tsBean.getRuleDeptID()%>"  size="20"      maxlength="45"/>
      </TD>
     </TR>
  <tr class="trwhite">
       <td class="tdulleft">巡检段类型</td>
       <td class="tdulright">
        <html:text    property="lineType" value="<%=tsBean.getLineType()%>"/>
      </TD>
     </TR>
	<tr class="trwhite">
       <td class="tdulleft">巡检点数量</td>
       <td class="tdulright">
        <html:text    property="checkPoints" value="<%=java.lang.String.valueOf((tsBean.getCheckPoints()))%>"/>
      </TD>
     </TR>
  <tr class="trcolor">
       <td class="tdulleft">所属区域</td>
       <td class="tdulright">
        <html:text    property="regionID" value="<%=tsBean.getRegionID()%>"/>
      </TD>
     </TR>
    </TABLE>
<!--按钮表格开始-->
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="2" background="${ctx}/images/bg_line.gif"><img src="${ctx}/images/1px.gif" width="1" height="1"></td>
  </tr>
  <tr>
    <td align="right"><table  border="0" align="center" cellpadding="0" cellspacing="0">
      <tr align="center">
        <td width="85">
             <html:submit property="action" tabindex="16">修 改</html:submit>
        </td>
        <td width="85">
            <html:submit property="action" tabindex="17">取 消</html:submit>
        </td>
      </tr>
    </table></td>
  </tr>
</table>
<!--按钮表格结束-->
</html:form>
