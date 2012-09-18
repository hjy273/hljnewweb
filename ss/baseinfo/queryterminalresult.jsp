<%@include file="/common/header.jsp"%>
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
function isInit(id,pw,sim,spid){
	if(confirm("ȷ��Ҫ��ʼ���豸["+id+"]��")){
		toInit(id, pw, sim, spid);
	}
}
function toInit(id, pw, sim, spid){
    //var url = "${ctx}/realtime/initMsg.jsp?id="+id+"&pw="+pw+"&sim="+sim+"&spid="+spid;
	var url = "${ctx}/terminalAction.do?method=initTerminal&sid="+sim+"&did="+id;
	new Ajax.Request(url,{
    	method:'post',
    	evalScripts:true,
    	onSuccess:function(transport){
				alert("�豸"+id+":"+transport.responseText);
    	},
    	onFailure: function(){ alert('��������쳣......'); }
	 	});
}

function toEdit(idValue){
        var url = "${ctx}/terminalAction.do?method=loadTerminal&id=" + idValue;
        self.location.replace(url);

}


</script>
<body>
<template:titile value="��ѯѲ���ն��豸��Ϣ���"/>
<display:table name="sessionScope.queryresult" requestURI="${ctx}/terminalAction.do?method=queryTerminal" pagesize="18" id="currentRowObject">
  <display:column property="deviceid" title="�豸���" sortable="true" headerClass="sortable"/>
  <display:column property="sim" title="SIM����" sortable="true" headerClass="sortable"/>
  <display:column property="holder" title="������" sortable="true" headerClass="sortable"/>

  <logic:equal value="group" name="PMType">
    <display:column property="ownerid" title="Ѳ��ά����"/>
  </logic:equal>
  <logic:notEqual value="group" name="PMType">
    <display:column property="ownerid" title="Ѳ����"/>
  </logic:notEqual>
  <display:column property="contractorid" title="��ά��λ"/>
  <display:column media="html" title="��ʼ��">
  <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String id = (String) object.get("deviceid");
    //  id = id.substring(id.length() - 4, id.length());
    String sim = (String) object.get("sim");
    String pw = (String) object.get("password");
    String spid = "05960ffffff";
    // <display:column property="terminalid" title="�豸���" sortable="true" headerClass="sortable"/> �������column������������ɾ��
    // <display:column property="terminaltype" title="�豸����"/>   �������column������������ɾ��
  %>
    <a href="javascript:isInit('<%=id%>','<%=pw%>','<%=sim%>','<%=spid%>')">��ʼ��</a>
  </display:column>
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
<logic:empty name="queryresult">
</logic:empty>
<logic:notEmpty name="queryresult">
<html:link action="/terminalAction.do?method=exportTerminalResult">����ΪExcel�ļ�</html:link>
</logic:notEmpty>
<%
  if (pageContext.findAttribute("currentRowObject") != null) {
    //System.out.println("���� ����Ϊ�գ�");
    out.println("<input type=hidden name=resultFlag value=1>");
  }
  else {
    //System.out.println("���� ���գ�");
    out.println("<input type=hidden name=resultFlag value=0>");
  }
%>
</body>
