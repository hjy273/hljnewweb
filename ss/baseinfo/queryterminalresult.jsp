<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.beans.SMSBean"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript" type="text/javascript">
function toDelete(idValue){
    if(confirm("确定删除该纪录？")){
        var url = "${ctx}/terminalAction.do?method=deleteTerminal&id=" + idValue;
        self.location.replace(url);
    }
//confirm("不能删除该纪录!");
}
function isInit(id,pw,sim,spid){
	if(confirm("确认要初始化设备["+id+"]吗？")){
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
				alert("设备"+id+":"+transport.responseText);
    	},
    	onFailure: function(){ alert('请求服务异常......'); }
	 	});
}

function toEdit(idValue){
        var url = "${ctx}/terminalAction.do?method=loadTerminal&id=" + idValue;
        self.location.replace(url);

}


</script>
<body>
<template:titile value="查询巡检终端设备信息结果"/>
<display:table name="sessionScope.queryresult" requestURI="${ctx}/terminalAction.do?method=queryTerminal" pagesize="18" id="currentRowObject">
  <display:column property="deviceid" title="设备编号" sortable="true" headerClass="sortable"/>
  <display:column property="sim" title="SIM卡号" sortable="true" headerClass="sortable"/>
  <display:column property="holder" title="持有人" sortable="true" headerClass="sortable"/>

  <logic:equal value="group" name="PMType">
    <display:column property="ownerid" title="巡检维护组"/>
  </logic:equal>
  <logic:notEqual value="group" name="PMType">
    <display:column property="ownerid" title="巡检人"/>
  </logic:notEqual>
  <display:column property="contractorid" title="代维单位"/>
  <display:column media="html" title="初始化">
  <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String id = (String) object.get("deviceid");
    //  id = id.substring(id.length() - 4, id.length());
    String sim = (String) object.get("sim");
    String pw = (String) object.get("password");
    String spid = "05960ffffff";
    // <display:column property="terminalid" title="设备编号" sortable="true" headerClass="sortable"/> 由上面的column移至而来，可删除
    // <display:column property="terminaltype" title="设备类型"/>   由上面的column移至而来，可删除
  %>
    <a href="javascript:isInit('<%=id%>','<%=pw%>','<%=sim%>','<%=spid%>')">初始化</a>
  </display:column>
<%
  BasicDynaBean object1 = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
  String terminalid = "";
  if (object1 != null)
    terminalid = (String) object1.get("terminalid");
%>
  <apptag:checkpower thirdmould="70804" ishead="0">
    <display:column media="html" title="修改">
      <a href="javascript:toEdit('<%=terminalid%>')">修改</a>
    </display:column>
  </apptag:checkpower>
  <apptag:checkpower thirdmould="70805" ishead="0">
    <display:column media="html" title="删除">
    <%
      BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
      String id = (String) object.get("terminalid");
    %>
      <a href="javascript:toDelete('<%=id%>')">删除</a>
    </display:column>
  </apptag:checkpower>
</display:table>
<logic:empty name="queryresult">
</logic:empty>
<logic:notEmpty name="queryresult">
<html:link action="/terminalAction.do?method=exportTerminalResult">导出为Excel文件</html:link>
</logic:notEmpty>
<%
  if (pageContext.findAttribute("currentRowObject") != null) {
    //System.out.println("搜索 ：不为空！");
    out.println("<input type=hidden name=resultFlag value=1>");
  }
  else {
    //System.out.println("搜索 ：空！");
    out.println("<input type=hidden name=resultFlag value=0>");
  }
%>
</body>
