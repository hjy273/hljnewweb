<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript" type="">
function doUpAccident(idValue, statusFlag){
    if(statusFlag == "待处理"){
      var url = "${ctx}/accidentAction.do?method=loadTrouble&statusFlag=0&id=" + idValue;
      self.location.replace(url);
    }
    if(statusFlag == "处理中"){
     var url = "${ctx}/accidentAction.do?method=loadTrouble&id=" + idValue +"&statusFlag=2" ;
      self.location.replace(url);
    }
    if(statusFlag == "已处理"){
      var url = "${ctx}/accidentAction.do?method=loadTrouble&id=" + idValue +"&statusFlag=3";
      self.location.replace(url);
    }
    if(statusFlag == "已忽略"){
      alert("该隐患已经忽略！");
      return;
    }

}
</script><template:titile value="隐患查询列表"/>
<display:table name="sessionScope.queryresult" pagesize="20" id="currentRowObject">
  <display:column property="contractor" title="代维单位" sortable="true"/>
  <display:column property="sendtime" title="上报时间" sortable="true"/>
  <display:column property="reason" title="障碍原因" sortable="true"/>
  <display:column property="emlevel" title="严重程度" sortable="true"/>
  <display:column property="subline" title="巡检段" sortable="true"/>
  <display:column property="point" title="巡检点" sortable="true"/>
  <display:column property="status" title="状态" sortable="true"/>
  <display:column media="html" title="处理">
  <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String id = (String) object.get("id");
    String statusFlag = (String) object.get("status");
    String lableName = "处理该隐患";
    if (statusFlag.equals("待处理")) {
      lableName = "处理该隐患";
    }

    if (statusFlag.equals("处理中")) {
      lableName = "填写完成报告单";
    }
    if (statusFlag.equals("已忽略")) {
      lableName = "－－－－";
    }
    if (statusFlag.equals("已处理")) {
      lableName = "－－查看详细－－";
    }
  %>
    <a href="javascript:doUpAccident('<%=id%>','<%=statusFlag%>')"><%=lableName%>    </a>
  </display:column>
</display:table>

