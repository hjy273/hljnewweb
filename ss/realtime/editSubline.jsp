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
    <td height="24" align="center" class="title2">�޸�Ѳ�����Ϣ</td>
  </tr>
  <tr>
    <td height="2" background="${ctx}/images/bg_line.gif"><img src="${ctx}/images/1px.gif" width="1" height="1"></td>
  </tr>
</table>

<html:form method="Post" action="/sublineAction.do?method=updateSubline" >
 <html:hidden    property="subLineID" value="<%=tsBean.getSubLineID()%>"/>
  <table width="98%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
    <tr>
      <th class="thlist">��Ŀ</th>
      <th class="thlist">��д</th>
    </tr>
    <tr class="trcolor">
      <td class="tdulleft">Ѳ������ƣ�</td>
      <td class="tdulright">
         <html:text    property="subLineName" value="<%=tsBean.getSubLineName()%>"  size="20"      maxlength="45"/>
      </td>
    </tr>
  <tr class="trwhite">
      <td class="tdulleft">�����߱�ţ�</td>
      <td class="tdulright">
        <html:text         property="lineID"   value="<%=tsBean.getLineID()%>"  size="20"      maxlength="45"/>
      </td>
    </tr>

  <tr class="trcolor">
       <td class="tdulleft">��������</td>
       <td class="tdulright">
        <html:text    property="ruleDeptID"   value="<%=tsBean.getRuleDeptID()%>"  size="20"      maxlength="45"/>
      </TD>
     </TR>
  <tr class="trwhite">
       <td class="tdulleft">Ѳ�������</td>
       <td class="tdulright">
        <html:text    property="lineType" value="<%=tsBean.getLineType()%>"/>
      </TD>
     </TR>
	<tr class="trwhite">
       <td class="tdulleft">Ѳ�������</td>
       <td class="tdulright">
        <html:text    property="checkPoints" value="<%=java.lang.String.valueOf((tsBean.getCheckPoints()))%>"/>
      </TD>
     </TR>
  <tr class="trcolor">
       <td class="tdulleft">��������</td>
       <td class="tdulright">
        <html:text    property="regionID" value="<%=tsBean.getRegionID()%>"/>
      </TD>
     </TR>
    </TABLE>
<!--��ť���ʼ-->
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="2" background="${ctx}/images/bg_line.gif"><img src="${ctx}/images/1px.gif" width="1" height="1"></td>
  </tr>
  <tr>
    <td align="right"><table  border="0" align="center" cellpadding="0" cellspacing="0">
      <tr align="center">
        <td width="85">
             <html:submit property="action" tabindex="16">�� ��</html:submit>
        </td>
        <td width="85">
            <html:submit property="action" tabindex="17">ȡ ��</html:submit>
        </td>
      </tr>
    </table></td>
  </tr>
</table>
<!--��ť������-->
</html:form>
