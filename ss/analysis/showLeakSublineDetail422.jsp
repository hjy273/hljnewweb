<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
<script language="javascript" type="">
  function addGoBack()
  {
      var url="${ctx}/LeakSublineAction.do?method=showLeakSubline422";
      self.location.replace(url);
  }
</script><br>
<div class='title2' style='font-size:14px; font-weight:600;bottom:1' align='center'><%=request.getAttribute("patrolName422") %>本月漏做巡检任务之线段显示结果</div><hr width='100%' size='1'>

<display:table name="sessionScope.leakSublineDetail422"   id="currentRowObject"  pagesize="18">
     <display:column property="sublinename" title="线段名称" sortable="true"/>
     <display:column property="linename" title="所属线路" sortable="true"/>
</display:table>
</br>
   <center>
   <input type="button" onclick="addGoBack()" value="返回" />
   </center>