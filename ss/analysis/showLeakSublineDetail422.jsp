<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
<script language="javascript" type="">
  function addGoBack()
  {
      var url="${ctx}/LeakSublineAction.do?method=showLeakSubline422";
      self.location.replace(url);
  }
</script><br>
<div class='title2' style='font-size:14px; font-weight:600;bottom:1' align='center'><%=request.getAttribute("patrolName422") %>����©��Ѳ������֮�߶���ʾ���</div><hr width='100%' size='1'>

<display:table name="sessionScope.leakSublineDetail422"   id="currentRowObject"  pagesize="18">
     <display:column property="sublinename" title="�߶�����" sortable="true"/>
     <display:column property="linename" title="������·" sortable="true"/>
</display:table>
</br>
   <center>
   <input type="button" onclick="addGoBack()" value="����" />
   </center>