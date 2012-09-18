<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript" type="">
function doUpAccident(idValue, statusFlag){
    var url = "${ctx}/accidentAction.do?method=loadAccident&id=" + idValue +"&statusFlag=" + statusFlag;
    self.location.replace(url);
}
</script><template:titile value="处理障碍列表"/>
<display:table name="sessionScope.queryresult" pagesize="20" id="currentRowObject">
  <display:column property="sendtime" title="上报时间" sortable="true"/>
  <display:column property="reason" title="障碍原因" sortable="true"/>
  <display:column property="emlevel" title="严重程度" sortable="true"/>
  <display:column property="subline" title="巡检段" sortable="true"/>
  <display:column property="point" title="巡检点" sortable="true"/>
  <display:column property="contractor" title="代维单位" sortable="true"/>
  <display:column property="status" title="状态" sortable="true"/>
  <display:column media="html" title="处理">
  <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String id = (String) object.get("id");
    String statusFlag = (String) object.get("statusflag");
    String lableName = "下发处理通知单";
    if (statusFlag.equals("0")) {
      lableName = "下发处理通知单";
    }
    if (statusFlag.equals("1")) {
      lableName = "响应通知";
    }
    if (statusFlag.equals("2")) {
      lableName = "填写完成报告单";
    }
    if (statusFlag.equals("3")) {
      lableName = "查看完成报告单";
    }
  %>
    <a href="javascript:doUpAccident('<%=id%>','<%=statusFlag%>')"><%=lableName%>    </a>
  </display:column>
</display:table>
 <html:link action="/accidentAction.do?method=exportAccidentResult">导出为Excel文件</html:link>
