<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.watchinfo.beans.*"%>
<script language="javascript" type="">
<!--

function loadWatch(idValue){
	var url = "${ctx}/watchAction.do?method=loadWatch&id=" + idValue;
	self.location.replace(url);
}

function toExpotr(){
	var url = "${ctx}/watchAction.do?method=exportWatchInfo";
	self.location.replace(url);
}

function RedirectPage(){
	var url = "${ctx}/watchAction.do?method=queryWatch";
	self.location.replace(url);
}

//-->
</script>
<%
	WatchBean bean = (WatchBean) request.getSession().getAttribute("watchBean");

%>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="24" align="center" class="title2">      ����������Ϣ����
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    </td>
  </tr>
</table>
<br>
<table width="95%" border="0" align="center" cellpadding="3" cellspacing="1" bgcolor="#999999">
  <!-- �ƻ���Ϣ -->
  <tr bgcolor="#FFFFFF">
    <td width="11%" class=trcolor rowspan="14" align="center">������Ϣ</td>
    <td class=trcolor width="14%">��������</td>
    <td><%=bean.getPlaceName() %>    </td>
  </tr>
  <tr class=trwhite>
    <td class=trcolor bgcolor="#FFFFFF">��������</td>
    <td bgcolor="#FFFFFF"><%=bean.getInnerregion()%>    </td>
  </tr>
  <tr class=trwhite>
    <td class=trcolor bgcolor="#FFFFFF">��������</td>
    <td bgcolor="#FFFFFF"><%=bean.getPlacetype()%>    </td>
  </tr>
  <tr class=trwhite>
    <td class=trcolor bgcolor="#FFFFFF">��������</td>
    <td bgcolor="#FFFFFF"><%=bean.getDangerlevel()%>    </td>
  </tr>
  <tr class=trwhite>
    <td class=trcolor bgcolor="#FFFFFF">����λ��</td>
    <td bgcolor="#FFFFFF"><%=bean.getWatchplace()%>    </td>
  </tr>
  <tr class=trwhite>
    <td class=trcolor bgcolor="#FFFFFF">����ԭ��</td>
    <td bgcolor="#FFFFFF"><%=bean.getWatchreason()%>    </td>
  </tr>
  <tr class=trwhite>
    <td class=trcolor bgcolor="#FFFFFF">��γ��</td>
    <td bgcolor="#FFFFFF">���ȣ�<%=bean.getX()%> <br> γ�ȣ�<%=bean.getY()%>  </td>
  </tr>
  <tr class=trwhite>
    <td class=trcolor bgcolor="#FFFFFF">������Χ</td>
    <td bgcolor="#FFFFFF"><%=bean.getWatchwidth() %>    </td>
  </tr>
  <tr class=trwhite>
    <td class=trcolor bgcolor="#FFFFFF">����������</td>
    <td bgcolor="#FFFFFF"><%=bean.getPrincipal() %>    </td>
  </tr>
  <tr class=trwhite>
    <td class=trcolor bgcolor="#FFFFFF">��ʼ����</td>
    <td bgcolor="#FFFFFF"><%=bean.getBeginDate() %>    </td>
  </tr>
  <tr class=trwhite>
    <td class=trcolor bgcolor="#FFFFFF">��������</td>
    <td bgcolor="#FFFFFF"><%=bean.getEndDate() %>    </td>
  </tr>
  <tr class=trwhite>
    <td bgcolor="#FFFFFF" class=trcolor>ֵ�࿪ʼʱ��</td>
    <td bgcolor="#FFFFFF"><%=bean.getOrderlyBeginTime() %>    </td>
  </tr>
  <tr class=trwhite>
    <td bgcolor="#FFFFFF" class=trcolor>ֵ�����ʱ��</td>
    <td bgcolor="#FFFFFF"><%=bean.getOrderlyEndTime() %>    </td>
  </tr>
  <tr class=trwhite>
    <td bgcolor="#FFFFFF" class=trcolor>ֵ��ʱ����</td>
    <td bgcolor="#FFFFFF"><%=bean.getError() %>    </td>
  </tr>
  <tr bgcolor="#FFFFFF" class=trwhite>
    <td rowspan="2" align="center" class=trcolor>�쵼��ʾ</td>
    <td height="63" colspan="2">&nbsp;</td>
  </tr>
</table>
<br>
<br>
<template:formSubmit>
  <td width="85">
    <html:button property="action" styleClass="button" onclick="toExpotr()">Excel����</html:button>
  </td>
  <td width="85">
  	<input type="button" class="button" value="����" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')"/>
  </td>
</template:formSubmit>
<br>
<br>
