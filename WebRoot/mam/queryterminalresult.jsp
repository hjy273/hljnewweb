<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.beans.SMSBean"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript" type="text/javascript">
function toDelete(idValue){
    if(confirm("ȷ��ɾ���ü�¼��")){
        var url = "${ctx}/terminalAction.do?method=deleteTerminal&id=" + idValue;
        self.location.replace(url);
    }
//confirm("����ɾ���ü�¼!");
}

function toInit(id, pw, sim, spid){

    var url = "${ctx}/linepatrol/realtime/initMsg.jsp?id="+id+"&pw="+pw+"&sim="+sim+"&spid="+spid;

    var smsPop = window.open(url,'smsPop','width=300,height=100,scrollbars=yes');
    smsPop.focus();
}
/*
function toInit(id, sim, pw, path){

    var terL = id.length;

    id = id.substring(terL-4, terL);

    var url = path + "&SimIDList="+sim;

    var smsPop = window.open(url,'smsPop','width=300,height=100,scrollbars=yes');
    smsPop.focus();
}*/

function toEdit(idValue){
        var url = "${ctx}/terminalAction.do?method=loadTerminal&id=" + idValue;
        self.location.replace(url);
}
function toRelease (deviceid,id){
	if(confirm("ȷ�������Դ����"+deviceid+"���豸�󶨹�ϵ��")){
		var url = "${ctx}/terminalAction.do?method=release&id=" + id;
        self.location.replace(url);
	}
}

</script>
<body>
<template:titile value="��ѯ�ֳ��ն��豸��Ϣ���"/>
<display:table name="sessionScope.queryresult" pagesize="18" id="currentRowObject">
  <display:column property="sim" title="SIM����" sortable="true" headerClass="sortable"/>
  <display:column property="deviceid" title="�豸���"/>
  <display:column property="produceman" title="��������"/>
  <logic:equal value="group" name="PMType">
    <display:column property="ownerid" title="Ѳ��ά����"/>
  </logic:equal>
  <logic:notEqual value="group" name="PMType">
    <display:column property="ownerid" title="Ѳ����"/>
  </logic:notEqual>
  <display:column media="html" title="�Ƿ���豸">
  	<%
  	 BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
  		String imei = (String)object.get("imei");
  		if(imei != null && !"".equals(imei)){
  	%>
  		��(<%=imei %>)
  	<%
  		}else{
  			out.print("��");
  		}
  		
  	%>
  </display:column>
  <display:column property="contractorid" title="��ά��λ"/>
<%
  BasicDynaBean object1 = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
  String terminalid = "";
  if (object1 != null)
    terminalid = (String) object1.get("terminalid");
%>
  <apptag:checkpower thirdmould="70804" ishead="0">
    <display:column media="html" title="�޸�">
      <a href="javascript:toEdit('<%=terminalid%>')">�޸�</a>
    </display:column>
  </apptag:checkpower>
  <apptag:checkpower thirdmould="70805" ishead="0">
    <display:column media="html" title="ɾ��">
    <%
      BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
      String id = (String) object.get("terminalid");
    %>
      <a href="javascript:toDelete('<%=id%>')">ɾ��</a>
    </display:column>
  </apptag:checkpower>
</display:table>
<html:link action="/terminalAction.do?method=exportTerminalResult">����ΪExcel�ļ�</html:link>

</body>
