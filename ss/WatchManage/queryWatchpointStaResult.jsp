<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.watchinfo.beans.*"%>
<script language="javascript" type="">
<!--
var enlargeFlag = 0;

function toCloseThis(){
    try{
        parent.makeSize(1);
    }catch(e){}

    self.location.replace("${ctx}/common/blank.html");
}

function toExpotr(){
    self.location.replace("${ctx}/WatchExeStaAction.do?method=exportWatchPointSta");
}
function toGetBack(){
                  try{
                location.href = "${ctx}/watchAction.do?method=queryWatch";
                return true;
            }
            catch(e){
                alert(e);
            }
}

//-->
</script>
<%
  int constantRowspan = 8;
  int watchSize = 0;
  WatchStaResultBean resultBean = new WatchStaResultBean();
  Vector watchList = new Vector();
  if (request.getSession().getAttribute("QueryResult") != null) {
    resultBean = (WatchStaResultBean) request.getSession().getAttribute("QueryResult");
    watchList = resultBean.getWatchlist();
    watchSize = watchList.size();
  }
  if (watchSize > 0) {
%>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="24" align="center" class="title2">      ��������ִ�����ͳ�Ʊ���
      &nbsp;&nbsp;
    </td>
  </tr>
</table>
<table width="95%" border="0" align="center" cellpadding="3" cellspacing="1" bgcolor="#999999">
  <tr bgcolor="#FFFFFF">
    <td width="22%">����ʱ�䣺</td>
    <td width="35%"><%=resultBean.getDaterange()%></td>
    <td width="43%" rowspan="<%=constantRowspan + watchSize%>">
       <div>
        <img src="${ctx}/images/1px.gif" alt="">
        <br>
        <iframe name=treemenu border=0 marginWidth=0 marginHeight=0 src="${ctx}/showWatchChart?PRate=<%=resultBean.getWorkratenumber() %>&LRate=<%=resultBean.getUndoratenumber()%>" frameBorder=0 width="100%" scrolling=NO height="165">        </iframe>
      </div>
    </td>
  </tr>
  <tr bgcolor="#FFFFFF">
    <td>���δ�ά��λ��</td>
    <td><%=resultBean.getContractor()%>    </td>
  </tr>
  <tr bgcolor="#FFFFFF">
  <%OneStaWatchBean watchBean = (OneStaWatchBean) watchList.get(0);  %>
    <td rowspan="<%=watchSize%>">�����������ƣ�</td>
    <td><%=watchBean.getPlaceName()%>    </td>
  </tr>
  <!--
    <tr bgcolor="#FFFFFF">
    <td>&nbsp;</td>
    </tr>
  -->
<%
  for (int i = 1; i < watchSize; i++) {
    watchBean = (OneStaWatchBean) watchList.get(i);
%>
  <tr bgcolor="#FFFFFF">
    <td><%=watchBean.getPlaceName()%>    </td>
  </tr>
<%}%>
  <tr bgcolor="#FFFFFF">
    <td>��������������Ϣ������</td>
    <td><%=resultBean.getInfoneeded()%>    </td>
  </tr>
  <tr bgcolor="#FFFFFF">
    <td>��������ִ����Ϣ������</td>
    <td><%=resultBean.getInfodid()%>    </td>
  </tr>
  <tr bgcolor="#FFFFFF">
    <td>��������ִ������ʣ�</td>
    <td><%=resultBean.getWatchexecuterate()%>    </td>
  </tr>
  <tr bgcolor="#FFFFFF">
    <td>��������δ����ʣ�</td>
    <td><%=resultBean.getUndorate()%>    </td>
  </tr>
  <tr bgcolor="#FFFFFF">
    <td>������������������</td>
    <td><%=resultBean.getAlertcount()%>    </td>
  </tr>
</table>
<br>
<br>
<template:formSubmit>
  <td width="85">
    <html:button property="action" styleClass="button" onclick="toExpotr()">Excel����</html:button>
  </td>
  <td width="85">
    <input type="button" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')" value="����" >
  </td>
</template:formSubmit>

<%} else {%>
<br>
<br>
<br>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="24" align="center" class="title2">û������������Ϣ���ã��������ɱ���</td>
  </tr>
</table>
<%}
%>
