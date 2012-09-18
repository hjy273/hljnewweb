<%@page contentType="text/html; charset=GBK"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="cabletechtag" prefix="apptag"%>
<%@page import="com.cabletech.lineinfo.beans.*"%>
<jsp:useBean id="msginfoBean" class="com.cabletech.lineinfo.beans.MsginfoBean" scope="request"/>
<%@page import="com.cabletech.lineinfo.common.TableList"%>
<%
  pageContext.setAttribute("sublineList",
                           TableList.getLableValueCollection("sublineinfo", "sublinename", "sublineid"));
%>
<link href="${ctx}/css/style.css" rel="stylesheet" type="text/css">
<body text="#000000">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="24" align="center" class="title2">������Ϣ</td>
  </tr>
  <tr>
    <td height="2" background="${ctx}/images/bg_line.gif">
      <img src="${ctx}/images/1px.gif" width="1" height="1">
    </td>
  </tr>
</table>
<html:form method="Post" action="/msginfoAction.do?method=addMsginfo">
  <table width="98%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
    <tr>
      <th class="thlist">��Ŀ</th>
      <th class="thlist">��д</th>
    </tr>
    <tr class="trwhite">
      <td class="tdulleft">���ű��</td>
      <td class="tdulright">
        <html:text property="msgID" value="<%=msginfoBean.getMsgID()%>" size="20" maxlength="45"/>
      </td>
    </tr>
    <tr class="trcolor">
      <td class="tdulleft">������ID</td>
      <td class="tdulright">
        <html:text property="sendID" value="<%=msginfoBean.getSendID()%>" size="20" maxlength="45"/>
      </td>
    </tr>
    <tr class="trcolor">
      <td class="tdulleft">������ID</td>
      <td class="tdulright">
        <html:text property="acceptID" value="<%=msginfoBean.getAcceptID()%>" size="20" maxlength="45"/>
      </TD>
    </TR>
    <tr class="trcolor">
      <td class="tdulleft">SIM����</td>
      <td class="tdulright">
        <html:text property="simID" value="<%=msginfoBean.getSimID()%>" size="20" maxlength="45"/>
      </TD>
    </TR>
    <tr class="trcolor">
      <td class="tdulleft">����ʱ��</td>
      <td class="tdulright">
        <html:text property="msgTime" value="<%=msginfoBean.getMsgTime()%>" size="20" maxlength="45"/>
      </TD>
    </TR>
    <tr class="trcolor">
      <td class="tdulleft">��Ϣ</td>
      <td class="tdulright">
        <html:text property="msgInfo" value="<%=msginfoBean.getMsgInfo()%>" size="20" maxlength="45"/>
      </TD>
    </TR>
    <tr class="trcolor">
      <td class="tdulleft">���ͷ�ʽ</td>
      <td class="tdulright">
        <html:text property="msgType" value="<%=msginfoBean.getMsgType()%>" size="20" maxlength="45"/>
      </TD>
    </TR>
  </TABLE>
  <!--��ť���ʼ-->
  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td height="2" background="${ctx}/images/bg_line.gif">
        <img src="${ctx}/images/1px.gif" width="1" height="1">
      </td>
    </tr>
    <tr>
      <td align="right">
        <table border="0" align="center" cellpadding="0" cellspacing="0">
          <tr align="center">
            <td width="85">
              <html:submit property="action" tabindex="16">�� ��</html:submit>
            </td>
            <td width="85">
              <html:submit property="action" tabindex="17">ȡ ��</html:submit>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
  <!--��ť������-->
</html:form>

