<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.watchinfo.beans.*"%>
<script language="javascript" type="">
<!--
var enlargeFlag = 0;

function makeSize(){
    if(enlargeFlag == 0){
        parent.makeSize(0);
        enlargeIcon.src = "${ctx}/images/icon_arrow_down.gif";
        enlargeIcon.alt = "��ʾ��ѯ����";
        enlargeFlag = 1;
    }else{
        parent.makeSize(1);
        enlargeIcon.src = "${ctx}/images/icon_arrow_up.gif";
        enlargeIcon.alt = "���ز�ѯ����";
        enlargeFlag = 0;
    }
}

function toCloseThis(){
    try{
        parent.makeSize(1);
    }catch(e){}

    self.location.replace("${ctx}/common/blank.html");
}

function toExpotr(){
    self.location.replace("${ctx}/WatchExeStaAction.do?method=ExportWatchSta");
}

//-->
</script><%
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
      <a href="javascript:makeSize()">
        <img id="enlargeIcon" src="${ctx}/images/icon_arrow_up.gif" width="14" height="16" border="0" alt="���ز�ѯ����">
      </a>
    </td>
  </tr>
</table>
<table width="95%" border="0" align="center" cellpadding="3" cellspacing="1" bgcolor="#999999">
  <tr bgcolor="#FFFFFF">
    <td width="22%">ͳ��ʱ�䣺</td>
    <td colspan="3" width="35%"><%=resultBean.getDaterange()%>    </td>
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
    <td colspan="3"><%=resultBean.getContractor()%>    </td>
  </tr>
  <tr bgcolor="#FFFFFF">
    <td>��������������</td>
    <td colspan="3"><%=watchSize%>    </td>
  </tr>
  <tr bgcolor="#FFFFFF">
  <%OneStaWatchBean watchBean = (OneStaWatchBean) watchList.get(0);  
    String total = resultBean.getInfoneeded();
  %>
    <td rowspan="<%=watchSize + 1%>">���������б�</td>   	
    <td>��������</td>
   	<td>������������</td>
   	<td>��ռ����</td>
  </tr>		    
   	<%
 		for (int i = 0; i < watchSize; i++) {
   		watchBean = (OneStaWatchBean) watchList.get(i);
   		String name = watchBean.getPlaceName();
   		String num = watchBean.getInfoneed();
   		double p = Double.parseDouble(num) / Double.parseDouble(total);
  			double r = (int)(p * 1000) + (p * 10000 % 10 < 5 ? 0 : 1);
  	 
	%>
	<tr bgcolor="#FFFFFF">
 		<td><%=name%>   </td>
 		<td><%=num %> </td>
 		<td><%=r/10+"%" %> </td>
	</tr>
	<%}%>
	 

  
  <!--
    <tr bgcolor="#FFFFFF">
    <td>&nbsp;</td>
    </tr>
  -->

  <tr bgcolor="#FFFFFF">
    <td>��������������Ϣ��������</td>
    <td colspan="3"><%=total%>    </td>
  </tr>
  <tr bgcolor="#FFFFFF">
    <td>��������ִ����Ϣ��������</td>
    <td colspan="3"><%=resultBean.getInfodid()%>    </td>
  </tr>
  <tr bgcolor="#FFFFFF">
    <td>��������ִ������ʣ�</td>
    <td colspan="3"><%=resultBean.getWatchexecuterate()%>    </td>
  </tr>
  <tr bgcolor="#FFFFFF">
    <td>��������δ����ʣ�</td>
    <td colspan="3"><%=resultBean.getUndorate()%>    </td>
  </tr>
  <tr bgcolor="#FFFFFF">
    <td>������������������</td>
    <td colspan="3"><%=resultBean.getAlertcount()%>    </td>
  </tr>
</table>
<br>
<br>
<template:formSubmit>
  <td width="85">
    <html:button property="action" styleClass="button" onclick="toExpotr()">Excel����</html:button>
  </td>
  <td width="85">
    <html:button property="action" styleClass="button" onclick="toCloseThis()">�رմ���</html:button>
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
<%}%>
